package com.peeko32213.unusualprehistory.core.registry;

import net.minecraft.world.level.block.Block;

public enum UPBoatTypes {
    DRYO(UPBlocks.DRYO_PLANKS.get(), "dryo"),
    FOXXI(UPBlocks.FOXXI_PLANKS.get(), "foxxi"),
    GINKGO(UPBlocks.GINKGO_PLANKS.get(), "ginkgo");

    public final Block block;
    public final String name;

    UPBoatTypes(Block block, String name) {
        this.block = block;
        this.name = name;
    }

}