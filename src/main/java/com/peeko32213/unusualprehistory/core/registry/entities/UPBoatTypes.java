package com.peeko32213.unusualprehistory.core.registry.entities;

import com.peeko32213.unusualprehistory.core.registry.blocks.UPBlocks;
import net.minecraft.world.level.block.Block;

public enum UPBoatTypes {
    DRYO(UPBlocks.DRYO_PLANKS.get(), "dryo"),
    FOXXI(UPBlocks.FOXII_PLANKS.get(), "foxxi"),
    GINKGO(UPBlocks.GINKGO_PLANKS.get(), "ginkgo");

    public final Block block;
    public final String name;

    UPBoatTypes(Block block, String name) {
        this.block = block;
        this.name = name;
    }

}