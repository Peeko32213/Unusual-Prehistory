package com.peeko32213.unusualprehistory.core.other;

import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.teamabnormals.blueprint.core.util.DataUtil;

public class UPCompat {

    public static void registerCompat() {
        registerCompostables();
        registerFlammables();
        registerDispenserBehaviors();
    }

    public static void registerCompostables() {
        DataUtil.registerCompostable(UPBlocks.DRYO_LEAVES.get(), 0.30F);
        DataUtil.registerCompostable(UPBlocks.DRYO_SAPLING.get(), 0.30F);
        DataUtil.registerCompostable(UPBlocks.FOXII_LEAVES.get(), 0.30F);
        DataUtil.registerCompostable(UPBlocks.FOXII_SAPLING.get(), 0.30F);
    }

    public static void registerFlammables() {
        DataUtil.registerFlammable(UPBlocks.DRYO_LEAVES.get(), 30, 60);
        DataUtil.registerFlammable(UPBlocks.DRYO_LOG.get(), 5, 5);
        DataUtil.registerFlammable(UPBlocks.DRYO_WOOD.get(), 5, 5);
        DataUtil.registerFlammable(UPBlocks.STRIPPED_DRYO_LOG.get(), 5, 5);
        DataUtil.registerFlammable(UPBlocks.STRIPPED_DRYO_WOOD.get(), 5, 5);
        DataUtil.registerFlammable(UPBlocks.DRYO_PLANKS.get(), 5, 20);
        DataUtil.registerFlammable(UPBlocks.DRYO_SLAB.get(), 5, 20);
        DataUtil.registerFlammable(UPBlocks.DRYO_STAIRS.get(), 5, 20);
        DataUtil.registerFlammable(UPBlocks.DRYO_FENCE.get(), 5, 20);
        DataUtil.registerFlammable(UPBlocks.DRYO_FENCE_GATE.get(), 5, 20);

        DataUtil.registerFlammable(UPBlocks.FOXII_LEAVES.get(), 30, 60);
        DataUtil.registerFlammable(UPBlocks.FOXII_LOG.get(), 5, 5);
        DataUtil.registerFlammable(UPBlocks.FOXII_WOOD.get(), 5, 5);
        DataUtil.registerFlammable(UPBlocks.STRIPPED_FOXII_LOG.get(), 5, 5);
        DataUtil.registerFlammable(UPBlocks.STRIPPED_FOXII_WOOD.get(), 5, 5);
        DataUtil.registerFlammable(UPBlocks.FOXII_PLANKS.get(), 5, 20);
        DataUtil.registerFlammable(UPBlocks.FOXII_SLAB.get(), 5, 20);
        DataUtil.registerFlammable(UPBlocks.FOXII_STAIRS.get(), 5, 20);
        DataUtil.registerFlammable(UPBlocks.FOXII_FENCE.get(), 5, 20);
        DataUtil.registerFlammable(UPBlocks.FOXII_FENCE_GATE.get(), 5, 20);
    }

    public static void registerDispenserBehaviors() {
    }
}
