package com.peeko32213.unusualprehistory.common.block.custom.plant;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.phys.shapes.VoxelShape;

public class UPPlantBlock extends BushBlock {
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    public UPPlantBlock(Properties props) {
        super(props);
    }
}
