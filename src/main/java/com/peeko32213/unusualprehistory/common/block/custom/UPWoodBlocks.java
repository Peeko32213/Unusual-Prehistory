package com.peeko32213.unusualprehistory.common.block.custom;

import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;

import javax.annotation.Nullable;

public class UPWoodBlocks extends RotatedPillarBlock {

    public UPWoodBlocks(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return false;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 0;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 0;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if(context.getItemInHand().getItem() instanceof AxeItem) {
            if(state.is(UPBlocks.GINKGO_LOG.get())) {
                return UPBlocks.STRIPPED_GINKGO_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }

            if(state.is(UPBlocks.GINKGO_WOOD.get())) {
                return UPBlocks.STRIPPED_GINKGO_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }

            if(state.is(UPBlocks.PETRIFIED_WOOD_LOG.get())) {
                return UPBlocks.STRIPPED_PETRIFIED_WOOD_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }

            if(state.is(UPBlocks.PETRIFIED_WOOD.get())) {
                return UPBlocks.STRIPPED_PETRIFIED_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
            if(state.is(UPBlocks.FOXXI_LOG.get())) {
                return UPBlocks.STRIPPED_FOXXI_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }

            if(state.is(UPBlocks.FOXXI_WOOD.get())) {
                return UPBlocks.STRIPPED_FOXXI_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
            if(state.is(UPBlocks.DRYO_LOG.get())) {
                return UPBlocks.STRIPPED_DRYO_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }

            if(state.is(UPBlocks.DRYO_WOOD.get())) {
                return UPBlocks.STRIPPED_DRYO_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
            if(state.is(UPBlocks.ZULOAGAE_BLOCK.get())) {
                return UPBlocks.STRIPPED_ZULOAGAE_BLOCK.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
        }
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
