package com.peeko32213.unusualprehistory.common.block.plant;

import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
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
        if(context.getLevel().isClientSide){
            return null;
        }

        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        BlockState blockstateBelow = context.getLevel().getBlockState(context.getClickedPos().below());
        Level level = context.getLevel();


        if(blockstate.is(Blocks.AIR) && blockstateBelow.is(UPBlocks.QUEREUXIA.get())){
            level.setBlockAndUpdate(context.getClickedPos(),UPBlocks.QUEREUXIA_TOP.get().defaultBlockState());
            level.setBlockAndUpdate(context.getClickedPos().below(),UPBlocks.QUEREUXIA_PLANT.get().defaultBlockState());
            return null;
        }
        return super.getStateForPlacement(context);
    }
}
