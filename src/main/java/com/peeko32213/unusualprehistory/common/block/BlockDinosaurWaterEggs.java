package com.peeko32213.unusualprehistory.common.block;

import com.peeko32213.unusualprehistory.common.entity.IHatchableEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseAquaticAnimal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityTameableBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class BlockDinosaurWaterEggs extends Block {
    public static final Logger LOGGER = LogManager.getLogger();
    protected static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 1.5, 16);
    private Supplier<?extends EntityType> dinosaur;

    private boolean properHabitat;
    private static int hatchTimeMax;
    private static int hatchTimeMin;
    public BlockDinosaurWaterEggs(Properties properties, Supplier<?extends EntityType> dinosaur, boolean placableOnLand) {
        this(properties, dinosaur, placableOnLand, 1200 , 3600);
    }

    public BlockDinosaurWaterEggs(Properties properties, Supplier<?extends EntityType> dinosaur, boolean placableOnLand, int hatchTimeMin, int hatchTimeMax) {
        super(properties);
        this.dinosaur = dinosaur;
        this.properHabitat = placableOnLand;
        if(hatchTimeMin > hatchTimeMax)
        {
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
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }


    @Override
    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
        if(properHabitat){
            return isProperHabitat(reader, pos.below());
        }
        return mayPlaceOn(reader, pos.below());
    }

    public void onPlace(BlockState p_221227_, Level p_221228_, BlockPos p_221229_, BlockState p_221230_, boolean p_221231_) {
        p_221228_.scheduleTick(p_221229_, this, getSpawnHatchDelay(p_221228_.getRandom()));
    }

    public static boolean isProperHabitat(BlockGetter reader, BlockPos pos) {
        return reader.getBlockState(pos).is(UPTags.DINO_HATCHABLE_BLOCKS);
    }

    private static int getSpawnHatchDelay(RandomSource randomSource) {
        return randomSource.nextInt(hatchTimeMin, hatchTimeMax);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor access, BlockPos pos, BlockPos neighborPos) {
        return !this.canSurvive(state, access, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, access, pos, neighborPos);
    }


    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!this.canSurvive(state, level, pos)) {
            this.hatch(level, pos);
        } else {
            this.onHatch(level, pos, random);
        }
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity.getType().equals(EntityType.FALLING_BLOCK)) {
            this.hatch(level, pos);
        }
    }

    private static boolean mayPlaceOn(LevelReader reader, BlockPos pos) {
        FluidState fluidState = reader.getFluidState(pos);
        FluidState topFluidState = reader.getFluidState(pos.above());
        return fluidState.getType() == Fluids.WATER && topFluidState.getType() == Fluids.EMPTY;
    }



    private void onHatch(ServerLevel level, BlockPos pos, RandomSource random) {
        this.hatch(level, pos);
        level.playSound(null, pos, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 1.0F, 1.0F);
        this.createDinosaur(level, pos, random);
    }

    private void hatch(Level level, BlockPos blockPos) {
        level.destroyBlock(blockPos, false);
    }

    private void createDinosaur(ServerLevel level, BlockPos pos, RandomSource random) {
        int i = random.nextInt(1, 2);
        for (int index = 1; index <= i; ++index) {
            Mob entityToSpawn = (Mob) dinosaur.get().create(level);

            if(entityToSpawn instanceof IHatchableEntity hatchableEntity){
                hatchableEntity.determineVariant(random.nextInt(100));
            }

            if(entityToSpawn instanceof Animal animal){
                animal.setAge(-24000);
                animal.restrictTo(pos, 20);
            }

            if (entityToSpawn != null) {
                double x = (double)pos.getX() + this.getSpawnOffset(random);
                double z = (double)pos.getZ() + this.getSpawnOffset(random);
                int yaw = random.nextInt(1, 361);
                entityToSpawn.moveTo(x, (double)pos.getY() - 0.5, z, yaw, 0.0F);
                entityToSpawn.setPersistenceRequired();
                level.addFreshEntity(entityToSpawn);
            }
        }
    }

    private double getSpawnOffset(RandomSource random) {
        return Mth.clamp(random.nextDouble(), 0.2F, 0.8F);
    }
}
