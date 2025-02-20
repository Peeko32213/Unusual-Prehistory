package com.peeko32213.unusualprehistory.common.block;

import com.peeko32213.unusualprehistory.core.registry.UPBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BlockUPHangingSign extends CeilingHangingSignBlock {

    public BlockUPHangingSign(Properties properties, WoodType type) {
        super(properties, type);
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos p_154556_, @NotNull BlockState p_154557_) {
        return Objects.requireNonNull(UPBlockEntities.UP_HANGING_SIGN.get().create(p_154556_, p_154557_));
    }
}