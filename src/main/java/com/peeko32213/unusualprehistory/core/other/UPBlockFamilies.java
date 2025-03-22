package com.peeko32213.unusualprehistory.core.other;

import net.minecraft.data.BlockFamily;

import static com.peeko32213.unusualprehistory.core.registry.UPBlocks.*;

public class UPBlockFamilies {

    public static final BlockFamily DRYO_PLANKS_FAMILY = new BlockFamily.Builder(DRYO_PLANKS.get()).button(DRYO_BUTTON.get()).fence(DRYO_FENCE.get()).fenceGate(DRYO_FENCE_GATE.get()).pressurePlate(DRYO_PRESSURE_PLATE.get()).sign(DRYO_SIGNS.getFirst().get(), DRYO_SIGNS.getSecond().get()).slab(DRYO_SLAB.get()).stairs(DRYO_STAIRS.get()).door(DRYO_DOOR.get()).trapdoor(DRYO_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
    public static final BlockFamily FOXII_PLANKS_FAMILY = new BlockFamily.Builder(FOXII_PLANKS.get()).button(FOXII_BUTTON.get()).fence(FOXII_FENCE.get()).fenceGate(FOXII_FENCE_GATE.get()).pressurePlate(FOXII_PRESSURE_PLATE.get()).sign(FOXII_SIGNS.getFirst().get(), FOXII_SIGNS.getSecond().get()).slab(FOXII_SLAB.get()).stairs(FOXII_STAIRS.get()).door(FOXII_DOOR.get()).trapdoor(FOXII_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();

}
