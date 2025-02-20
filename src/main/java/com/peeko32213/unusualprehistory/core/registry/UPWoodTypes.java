package com.peeko32213.unusualprehistory.core.registry;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UPWoodTypes {

    public static final WoodType GINKGO = WoodType.register(new WoodType("ginkgo", UPBlockSetType.GINKGO));
    public static final WoodType PETRIFIED = WoodType.register(new WoodType("petrified", UPBlockSetType.PETRIFIED));
    public static final WoodType FOXXI = WoodType.register(new WoodType("foxxi", UPBlockSetType.FOXXI));
    public static final WoodType DRYO = WoodType.register(new WoodType("dryo", UPBlockSetType.DRYO));

    @OnlyIn(Dist.CLIENT)
    public static void init() {
        Sheets.addWoodType(DRYO);
        Sheets.addWoodType(FOXXI);
        Sheets.addWoodType(GINKGO);
        Sheets.addWoodType(PETRIFIED);
    }

}
