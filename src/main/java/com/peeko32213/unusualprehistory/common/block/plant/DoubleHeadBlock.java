package com.peeko32213.unusualprehistory.common.block.plant;

import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class DoubleHeadBlock extends GrowingPlantDoubleHeadBlock implements LiquidBlockContainer {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
    private static final double GROW_PER_TICK_PROBABILITY = 0.14D;

    public DoubleHeadBlock(BlockBehaviour.Properties p_54300_) {
        super(p_54300_, Direction.UP, SHAPE, true, 0.14D);
    }

    protected boolean canGrowInto(BlockState p_54321_) {
        return p_54321_.is(Blocks.WATER);
    }

    protected Block getBodyBlock() {
        return UPBlocks.QUEREUXIA_PLANT.get();
    }

    public boolean canAttachTo(BlockState p_153455_) {
        return !p_153455_.is(Blocks.MAGMA_BLOCK);
    }

    public boolean canPlaceLiquid(BlockGetter p_54304_, BlockPos p_54305_, BlockState p_54306_, Fluid p_54307_) {
        return false;
    }

    public boolean placeLiquid(LevelAccessor p_54309_, BlockPos p_54310_, BlockState p_54311_, FluidState p_54312_) {
        return false;
    }

    protected int getBlocksToGrowWhenBonemealed(RandomSource p_221366_) {
        return 1;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(blockPos);
        BlockState blockstate = context.getLevel().getBlockState(blockPos);
        BlockState blockstateBelow = context.getLevel().getBlockState(blockPos.below());

        Level level = context.getLevel();


        if(blockstate.is(Blocks.AIR) && blockstateBelow.is(UPBlocks.QUEREUXIA.get())){
            level.setBlockAndUpdate(context.getClickedPos(),UPBlocks.QUEREUXIA_TOP.get().defaultBlockState());
            level.setBlockAndUpdate(context.getClickedPos().below(),UPBlocks.QUEREUXIA_PLANT.get().defaultBlockState());
            level.playSound(context.getPlayer(),blockPos, SoundEvents.LILY_PAD_PLACE, SoundSource.BLOCKS,1.0F, 1.0F );
            return null;
        }
        return fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8 ? super.getStateForPlacement(context) : null;
    }

    public FluidState getFluidState(BlockState p_54319_) {
        return Fluids.WATER.getSource(false);
    }
}
