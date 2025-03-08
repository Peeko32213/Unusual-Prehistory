package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UPBlockSetType {

    public static final BlockSetType DRYO_TYPE = BlockSetType.register(new BlockSetType(new ResourceLocation(UnusualPrehistory.MODID, "dryo").toString()));
    public static final BlockSetType FOXII_TYPE = BlockSetType.register(new BlockSetType(new ResourceLocation(UnusualPrehistory.MODID, "foxii").toString()));
    public static final BlockSetType GINKGO_TYPE = BlockSetType.register(new BlockSetType(new ResourceLocation(UnusualPrehistory.MODID, "ginkgo").toString()));
    public static final BlockSetType PETRIFIED_TYPE = BlockSetType.register(new BlockSetType(new ResourceLocation(UnusualPrehistory.MODID, "petrified").toString()));
    public static final BlockSetType ZULOAGAE_TYPE = BlockSetType.register(new BlockSetType(new ResourceLocation(UnusualPrehistory.MODID, "zuloagae").toString()));

    public static final WoodType DRYO = WoodType.register(new WoodType("dryo", UPBlockSetType.DRYO_TYPE));
    public static final WoodType FOXII = WoodType.register(new WoodType("foxii", UPBlockSetType.FOXII_TYPE));
    public static final WoodType GINKGO = WoodType.register(new WoodType("ginkgo", UPBlockSetType.GINKGO_TYPE));
    public static final WoodType PETRIFIED = WoodType.register(new WoodType("petrified", UPBlockSetType.PETRIFIED_TYPE));
    public static final WoodType ZULOAGAE = WoodType.register(new WoodType("zuloagae", UPBlockSetType.ZULOAGAE_TYPE));

    @OnlyIn(Dist.CLIENT)
    public static void init() {
        Sheets.addWoodType(DRYO);
        Sheets.addWoodType(FOXII);
        Sheets.addWoodType(GINKGO);
        Sheets.addWoodType(PETRIFIED);
        Sheets.addWoodType(ZULOAGAE);
    }

}
