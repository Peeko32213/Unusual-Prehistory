package com.peeko32213.unusualprehistory.core.other;

import com.peeko32213.unusualprehistory.core.UnusualPrehistory;
import com.teamabnormals.blueprint.core.api.BlockSetTypeRegistryHelper;
import com.teamabnormals.blueprint.core.api.WoodTypeRegistryHelper;
import com.teamabnormals.blueprint.core.util.PropertyUtil;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;

public class UPProperties {
    public static final BlockSetType DRYO_BLOCK_SET = blockSetType("dryo");
    public static final BlockSetType FOXII_BLOCK_SET = blockSetType("foxii");

    public static final WoodType DRYO_WOOD_TYPE = woodSetType(DRYO_BLOCK_SET);
    public static final WoodType FOXII_WOOD_TYPE = woodSetType(FOXII_BLOCK_SET);

    public static final PropertyUtil.WoodSetProperties DRYO = PropertyUtil.WoodSetProperties.builder(MapColor.TERRACOTTA_PINK, MapColor.WOOD).build();
    public static final PropertyUtil.WoodSetProperties FOXII = PropertyUtil.WoodSetProperties.builder(MapColor.TERRACOTTA_RED, MapColor.WOOD).build();

    public static BlockSetType blockSetType(String name) {
        return BlockSetTypeRegistryHelper.register(new BlockSetType(UnusualPrehistory.MODID + ":" + name));
    }

    public static WoodType woodSetType(BlockSetType type) {
        return WoodTypeRegistryHelper.registerWoodType(new WoodType(type.name(), type));
    }
}
