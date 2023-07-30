package com.peeko32213.unusualprehistory.client.model.armor;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemshedScaleArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShedscaleArmorModel extends AnimatedGeoModel<ItemshedScaleArmor> {
    @Override
    public ResourceLocation getModelResource(ItemshedScaleArmor object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/shedscale_armor.geo.json");

    }

    @Override
    public ResourceLocation getTextureResource(ItemshedScaleArmor object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/armor/shedscale_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ItemshedScaleArmor animatable) {
        return null;
    }

}
