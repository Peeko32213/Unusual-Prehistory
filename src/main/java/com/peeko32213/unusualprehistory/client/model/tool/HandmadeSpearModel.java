package com.peeko32213.unusualprehistory.client.model.tool;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.tool.ItemHandmadeSpear;
import com.peeko32213.unusualprehistory.common.item.tool.ItemTrikeShield;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HandmadeSpearModel extends AnimatedGeoModel<ItemHandmadeSpear> {
    @Override
    public ResourceLocation getModelResource(ItemHandmadeSpear object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/handmade_spear.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ItemHandmadeSpear object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/item/handmade_spear.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ItemHandmadeSpear animatable) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/handmade_spear.animation.json");
    }
}
