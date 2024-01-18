package com.peeko32213.unusualprehistory.common.block;

import com.peeko32213.unusualprehistory.common.block.entity.DinosaurLandEggsBlockEntity;
import com.peeko32213.unusualprehistory.core.registry.UPBlockEntities;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEventListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class BlockDinosaurLandEggsBase extends BaseEntityBlock implements IDinosaurEgg {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
    public static final IntegerProperty EGGS = BlockStateProperties.EGGS;
    private final int hatchTimeMax;
    private final int hatchTimeMin;
    private final Supplier<? extends EntityType<?>> dinosaur;
    private final int eggCount;

    public BlockDinosaurLandEggsBase(Properties properties) {
        this(properties, 10, 0, 1, () -> EntityType.PIG);
    }

    public BlockDinosaurLandEggsBase(Properties pProperties, int hatchTimeMax, int hatchTimeMin, int eggCount, Supplier<? extends EntityType<?>> dinosaur) {
        super(pProperties);
        this.dinosaur = dinosaur;
        this.eggCount = eggCount;

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

    public int getEggCount() {
        return eggCount;
    }

    public int getHatchTimeMax() {
        return hatchTimeMax;
    }

    public int getHatchTimeMin() {
        return hatchTimeMin;
    }

    @Override
    public Supplier<? extends EntityType<?>> getDinosaur() {
        return dinosaur;
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return UPBlockEntities.DINO_LAND_EGGS.get().create(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, UPBlockEntities.DINO_LAND_EGGS.get(), DinosaurLandEggsBlockEntity::tick);
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

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (!pState.canSurvive(pLevel, pCurrentPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }


    public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, @javax.annotation.Nullable BlockEntity te, ItemStack stack) {
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


    @javax.annotation.Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        return blockstate.getBlock() == this ? blockstate.setValue(EGGS, Integer.valueOf(Math.min(eggCount, blockstate.getValue(EGGS) + 1))) : super.getStateForPlacement(context);
    }

    private boolean canTrample(Level worldIn, Entity trampler) {
        if ((trampler instanceof Bat) || trampler.getType() == dinosaur.get() || !(trampler instanceof LivingEntity)) {
            return false;
        }
        return trampler instanceof Player || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(worldIn, trampler);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HATCH, EGGS);
    }
}
