package com.peeko32213.unusualprehistory.common.block.entity;

import com.peeko32213.unusualprehistory.core.registry.UPBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class UPHangingSignBlockEntity extends SignBlockEntity {

    public UPHangingSignBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(UPBlockEntities.UP_HANGING_SIGN.get(), pPos, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return UPBlockEntities.UP_HANGING_SIGN.get();
    }
}