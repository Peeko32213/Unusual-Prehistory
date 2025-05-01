package com.peeko32213.unusualprehistory.common.block.sign;

import com.peeko32213.unusualprehistory.common.block.entity.UPSignBlockEntity;
import com.peeko32213.unusualprehistory.core.registry.blocks.UPBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

import javax.annotation.Nullable;

public class UPStandingSignBlock extends StandingSignBlock {

	public UPStandingSignBlock(Properties properties, WoodType woodType) {
		super(properties, woodType);
		UPSignBlockEntity.VALID_BLOCKS.add(this);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return UPBlockEntities.SIGN.get().create(pos, state);
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_277367_, BlockState p_277896_, BlockEntityType<T> p_277724_) {
		return createTickerHelper(p_277724_, UPBlockEntities.SIGN.get(), SignBlockEntity::tick);
	}
}