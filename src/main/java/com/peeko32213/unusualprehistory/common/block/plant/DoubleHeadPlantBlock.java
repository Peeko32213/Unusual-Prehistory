package com.peeko32213.unusualprehistory.common.block.plant;

import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.Shapes;

public class DoubleHeadPlantBlock extends GrowingPlantBodyBlock implements LiquidBlockContainer {
    public DoubleHeadPlantBlock(BlockBehaviour.Properties p_54323_) {
        super(p_54323_, Direction.UP, Shapes.block(), true);
    }

    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) UPBlocks.QUEREUXIA.get();
    }

    public FluidState getFluidState(BlockState p_54336_) {
        return Fluids.WATER.getSource(false);
    }

    public boolean canAttachTo(BlockState p_153457_) {
        return this.getHeadBlock().canAttachTo(p_153457_);
    }

    public boolean canPlaceLiquid(BlockGetter p_54325_, BlockPos p_54326_, BlockState p_54327_, Fluid p_54328_) {
        return false;
    }

    public boolean placeLiquid(LevelAccessor p_54330_, BlockPos p_54331_, BlockState p_54332_, FluidState p_54333_) {
        return false;
    }
}