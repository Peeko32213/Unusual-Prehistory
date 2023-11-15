package com.peeko32213.unusualprehistory.common.block;

import com.peeko32213.unusualprehistory.core.registry.UPBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.WoodType;

public class BlockUPStandingSign extends StandingSignBlock {

    public BlockUPStandingSign(Properties properties, WoodType type) {
        super(properties, type);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos p_154556_, BlockState p_154557_) {
        return UPBlockEntities.UP_SIGN.get().create(p_154556_, p_154557_);
    }
}