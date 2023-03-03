package com.peeko32213.unusualprehistory.common.block.plant;

import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Nullable;

public class WaterLilyUpdate extends WaterlilyBlock {
    public WaterLilyUpdate(Properties p_58162_) {
        super(p_58162_);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(blockPos);
        BlockState blockstate = context.getLevel().getBlockState(blockPos);
        BlockState blockstateBelow = context.getLevel().getBlockState(blockPos.below());
        Level level = context.getLevel();


        if(blockstate.is(Blocks.AIR) && blockstateBelow.is(UPBlocks.QUEREUXIA.get())){
            level.setBlockAndUpdate(context.getClickedPos(),UPBlocks.QUEREUXIA_TOP.get().defaultBlockState());
            level.setBlockAndUpdate(context.getClickedPos().below(),UPBlocks.QUEREUXIA_PLANT.get().defaultBlockState());
            level.playSound(context.getPlayer(), blockPos, SoundEvents.LILY_PAD_PLACE, SoundSource.BLOCKS,1.0F, 1.0F );
            return null;
        }
        return super.getStateForPlacement(context);
    }
}
