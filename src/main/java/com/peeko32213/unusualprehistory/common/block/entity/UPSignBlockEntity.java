package com.peeko32213.unusualprehistory.common.block.entity;

import com.peeko32213.unusualprehistory.core.registry.blocks.UPBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;

public class UPSignBlockEntity extends SignBlockEntity {
	public static final HashSet<Block> VALID_BLOCKS = new HashSet<>();

	public UPSignBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state);
	}

	@Override
	public BlockEntityType<?> getType() {
		return UPBlockEntities.SIGN.get();
	}
}