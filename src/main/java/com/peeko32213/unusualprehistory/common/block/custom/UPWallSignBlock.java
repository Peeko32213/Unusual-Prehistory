package com.peeko32213.unusualprehistory.common.block.custom;

import com.peeko32213.unusualprehistory.common.block.entity.UPSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class UPWallSignBlock extends WallSignBlock {

    public UPWallSignBlock(Properties pProperties, WoodType pType) {
        super(pProperties, pType);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new UPSignBlockEntity(pPos, pState);
    }
}
