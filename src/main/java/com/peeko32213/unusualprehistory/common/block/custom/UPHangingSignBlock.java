package com.peeko32213.unusualprehistory.common.block.custom;

import com.peeko32213.unusualprehistory.common.block.entity.UPHangingSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class UPHangingSignBlock extends CeilingHangingSignBlock {

    public UPHangingSignBlock(Properties pProperties, WoodType pType) {
        super(pProperties, pType);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new UPHangingSignBlockEntity(pPos, pState);
    }
}