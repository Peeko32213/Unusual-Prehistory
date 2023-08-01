package com.peeko32213.unusualprehistory.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockAsphalt extends Block {
    public BlockAsphalt(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if(pEntity.hasControllingPassenger()){
            pEntity.setDeltaMovement(pEntity.getDeltaMovement().normalize().scale(2));
        } else {
           // pEntity.setDeltaMovement(pEntity.getDeltaMovement().normalize().scale(1.2));
        }
        super.stepOn(pLevel, pPos, pState, pEntity);
    }
}
