package com.peeko32213.unusualprehistory.client.model.armor;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.armor.ItemMajungaHelmet;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MajungaHelmetModel extends AnimatedGeoModel<ItemMajungaHelmet> {
    @Override
    public ResourceLocation getModelLocation(ItemMajungaHelmet object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/majunga_helmet.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(ItemMajungaHelmet object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/armor/majunga_helmet.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ItemMajungaHelmet animatable) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/armor.animation.json");

    }
}
