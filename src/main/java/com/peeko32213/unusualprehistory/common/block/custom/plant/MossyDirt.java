package com.peeko32213.unusualprehistory.common.block.custom.plant;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class MossyDirt extends GrassBlock {

    public MossyDirt(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }

    private static boolean canPropagate(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return false;
    }
}
