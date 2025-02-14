package com.peeko32213.unusualprehistory.common.block;

import com.peeko32213.unusualprehistory.common.entity.IHatchableEntity;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class BlockDinosaurLandEggs extends Block {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
    public static final IntegerProperty EGGS = BlockStateProperties.EGGS;
    private VoxelShape ONE_SHAPE;
    private VoxelShape MULTI_SHAPE;
    public static final VoxelShape EMPTY_BLOCK_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    public static final VoxelShape ONE_EGG_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D);
    public static final VoxelShape MULTI_EGG_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 7.0D, 15.0D);
    private static int hatchTimeMax;
    private static int hatchTimeMin;
    private Supplier<? extends EntityType> dinosaur;
    private int eggCount;

    //This can be called for eggs that have multiple eggs and different egg shapes
    public BlockDinosaurLandEggs(Properties properties, Supplier<? extends EntityType> dinosaur, int eggCount, VoxelShape oneEgg, VoxelShape multiEgg, int hatchTimeMin, int hatchTimeMax) {
        super(BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG).strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion());
        this.eggCount = eggCount;
        this.dinosaur = dinosaur;
        this.ONE_SHAPE = oneEgg;
        this.MULTI_SHAPE = multiEgg;
        if(hatchTimeMin > hatchTimeMax) {
            try {
                LOGGER.debug("Min higher than Max, for block with entity {}", dinosaur.get());
                throw new Exception("Something went wrong setting creating block");
            }
            catch (Exception e) {
                CrashReport crashreport = CrashReport.forThrowable(e, "Something went wrong setting creating block");
                crashreport.addCategory("Min higher than Max");
                throw new ReportedException(crashreport);
            }
        }

        this.hatchTimeMax = hatchTimeMax;
        this.hatchTimeMin = hatchTimeMin;

        this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, Integer.valueOf(0)).setValue(EGGS, Integer.valueOf(1)));
    }

    //This can be called for eggs that have only a single egg and egg shape
    public BlockDinosaurLandEggs(Properties properties, Supplier<? extends EntityType> dinosaur, int eggCount, VoxelShape oneEgg) {
        this(properties, dinosaur, eggCount, oneEgg, EMPTY_BLOCK_SHAPE,3,5);
    }

    //This can be called for eggs that have only a single egg and egg shape
    public BlockDinosaurLandEggs(Properties properties, Supplier<? extends EntityType> dinosaur, int eggCount, VoxelShape oneEgg, int hatchTimeMin, int hatchTimeMax) {
        this(properties, dinosaur, eggCount, oneEgg, EMPTY_BLOCK_SHAPE,hatchTimeMin,hatchTimeMax);
    }

    //This can be called for eggs that have the standard egg shape/ the most common one
    public BlockDinosaurLandEggs(Properties properties, Supplier<? extends EntityType> dinosaur, int eggCount) {
        this(properties, dinosaur, eggCount, ONE_EGG_SHAPE, MULTI_EGG_SHAPE, 3,5);
    }

    //This can be called for eggs that have the standard egg shape/ the most common one
    public BlockDinosaurLandEggs(Properties properties, Supplier<? extends EntityType> dinosaur, int eggCount, int hatchTimeMin, int hatchTimeMax) {
        this(properties, dinosaur, eggCount, ONE_EGG_SHAPE, MULTI_EGG_SHAPE, hatchTimeMin,hatchTimeMax);
    }

    public BlockDinosaurLandEggs(Properties properties, Supplier<? extends EntityType> dinosaur, int eggCount, VoxelShape oneEgg, VoxelShape multiEgg) {
        this(properties, dinosaur, eggCount, oneEgg, multiEgg, 3,5);
    }

    public static boolean hasProperHabitat(BlockGetter reader, BlockPos blockReader) {
        return isProperHabitat(reader, blockReader.below());
    }

    public static boolean isProperHabitat(BlockGetter reader, BlockPos pos) {
        return reader.getBlockState(pos).is(UPTags.DINO_HATCHABLE_BLOCKS);
    }

    protected boolean mayPlaceOn(BlockState p_56127_, BlockGetter p_56128_, BlockPos p_56129_) {
        return !p_56127_.getCollisionShape(p_56128_, p_56129_).getFaceShape(Direction.UP).isEmpty() || p_56127_.isFaceSturdy(p_56128_, p_56129_, Direction.UP);
    }

    public boolean canSurvive(BlockState p_56109_, LevelReader p_56110_, BlockPos p_56111_) {
        BlockPos blockpos = p_56111_.below();
        return this.mayPlaceOn(p_56110_.getBlockState(blockpos), p_56110_, blockpos);
    }

    public void stepOn(Level worldIn, BlockPos pos, BlockState state, Entity entityIn) {
        this.tryTrample(worldIn, pos, entityIn, 100);
        super.stepOn(worldIn, pos, state, entityIn);
    }

    public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, float fallDistance) {
        if (!(entityIn instanceof Zombie)) {
            this.tryTrample(worldIn, pos, entityIn, 3);
        }

        super.fallOn(worldIn, state, pos, entityIn, fallDistance);
    }

    private void tryTrample(Level worldIn, BlockPos pos, Entity trampler, int chances) {
        if (this.canTrample(worldIn, trampler)) {
            if (!worldIn.isClientSide && worldIn.random.nextInt(chances) == 0) {
                BlockState blockstate = worldIn.getBlockState(pos);
                this.removeOneEgg(worldIn, pos, blockstate);
            }
        }
    }

    public void growEgg(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        if (this.canGrow(worldIn) && hasProperHabitat(worldIn, pos)) {
            int i = state.getValue(HATCH);
            if (i < 2) {
                worldIn.playSound(null, pos, SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                worldIn.setBlock(pos, state.setValue(HATCH, Integer.valueOf(i + 1)), 2);
                //Schedule tick between 2 to 10 minutes when its not hatched yet
                worldIn.scheduleTick(pos, this, worldIn.getRandom().nextInt(hatchTimeMin,hatchTimeMax));
            } else {
                worldIn.playSound(null, pos, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                worldIn.removeBlock(pos, false);
                for (int j = 0; j < state.getValue(EGGS); ++j) {
                    worldIn.levelEvent(4001, pos, Block.getId(state));
                    Mob dinosaurToSpawn = (Mob) dinosaur.get().create(worldIn);
                    if(dinosaurToSpawn instanceof IHatchableEntity hatchableEntity){
                        hatchableEntity.determineVariant(random.nextInt(100));
                    }

                    if(dinosaurToSpawn instanceof Animal animal){
                        animal.setAge(-24000);
                    }

                    dinosaurToSpawn.restrictTo(pos, 20);
                    dinosaurToSpawn.moveTo((double) pos.getX() + 0.3D + (double) j * 0.2D, pos.getY(), (double) pos.getZ() + 0.3D, 0.0F, 0.0F);
                    if (!worldIn.isClientSide) {
                        worldIn.addFreshEntity(dinosaurToSpawn);
                    }
                }
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (!pState.canSurvive(pLevel, pCurrentPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        growEgg(state, level, pos, random);
    }
    //We also add a randomTick and if they are lucky it will give them some more progress
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if(random.nextBoolean() && hasProperHabitat(level, pos)) {
            this.tick(state, level, pos, random);
        }
    }

    public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (hasProperHabitat(worldIn, pos) && !worldIn.isClientSide) {
            worldIn.levelEvent(2005, pos, 0);
            //Schedule tick between 2 to 10 minutes on place (I believe for every hatch it "places" it again so this will trigger again.
            worldIn.scheduleTick(pos, this, worldIn.getRandom().nextInt(hatchTimeMin,hatchTimeMax));
        }
    }

    private boolean canGrow(Level worldIn) {
        return worldIn.random.nextInt(5) == 0;
    }

    public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
        super.playerDestroy(worldIn, player, pos, state, te, stack);
        this.removeOneEgg(worldIn, pos, state);
    }

    private void removeOneEgg(Level worldIn, BlockPos pos, BlockState state) {
        worldIn.playSound(null, pos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + worldIn.random.nextFloat() * 0.2F);
        int i = state.getValue(EGGS);
        if (i <= 1) {
            worldIn.destroyBlock(pos, false);
        } else {
            worldIn.setBlock(pos, state.setValue(EGGS, Integer.valueOf(i - 1)), 2);
            worldIn.levelEvent(2001, pos, Block.getId(state));
        }
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return useContext.getItemInHand().getItem() == this.asItem() && state.getValue(EGGS) < eggCount || super.canBeReplaced(state, useContext);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        return blockstate.getBlock() == this ? blockstate.setValue(EGGS, Integer.valueOf(Math.min(eggCount, blockstate.getValue(EGGS) + 1))) : super.getStateForPlacement(context);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return state.getValue(EGGS) > 1 ? MULTI_SHAPE : ONE_SHAPE;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HATCH, EGGS);
    }

    private boolean canTrample(Level worldIn, Entity trampler) {
        if ((trampler instanceof Bat) || trampler.getType() == dinosaur.get() || !(trampler instanceof LivingEntity)) {
            return false;
        }
        return trampler instanceof Player || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(worldIn, trampler);
    }
}