package com.peeko32213.unusualprehistory.common.block.custom;

import com.peeko32213.unusualprehistory.core.registry.blocks.UPBlocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class UPWoodBlocks extends RotatedPillarBlock {
    public UPWoodBlocks(Properties properties) {
        super(properties);
    }

    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        ItemStack itemStack = context.getItemInHand();
        if (!itemStack.canPerformAction(toolAction))
            return null;

        if (ToolActions.AXE_STRIP == toolAction) {
            if (this == UPBlocks.DRYO_LOG.get()){
                return UPBlocks.STRIPPED_DRYO_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
            }
            if (this == UPBlocks.DRYO_WOOD.get()) {
                return UPBlocks.STRIPPED_DRYO_WOOD.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
            }
            if (this == UPBlocks.FOXII_LOG.get()) {
                return UPBlocks.STRIPPED_FOXII_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
            }
            if (this == UPBlocks.FOXII_WOOD.get()) {
                return UPBlocks.STRIPPED_FOXII_WOOD.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
            }
            if (this == UPBlocks.GINKGO_LOG.get()) {
                return UPBlocks.STRIPPED_GINKGO_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
            }
            if (this == UPBlocks.GINKGO_WOOD.get()) {
                return UPBlocks.STRIPPED_GINKGO_WOOD.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
            }
            if (this == UPBlocks.PETRIFIED_LOG.get()) {
                return UPBlocks.STRIPPED_PETRIFIED_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
            }
            if (this == UPBlocks.PETRIFIED_WOOD.get()) {
                return UPBlocks.STRIPPED_PETRIFIED_WOOD.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
            }
            if (this == UPBlocks.ZULOAGAE_BLOCK.get()) {
                return UPBlocks.STRIPPED_ZULOAGAE_BLOCK.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
            }
        }
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
