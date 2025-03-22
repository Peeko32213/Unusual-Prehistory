package com.peeko32213.unusualprehistory.client.model.tool;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.tool.HandmadeSpearItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HandmadeSpearModel extends GeoModel<HandmadeSpearItem> {
    @Override
    public ResourceLocation getModelResource(HandmadeSpearItem object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/handmade_spear.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HandmadeSpearItem object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/item/handmade_spear.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HandmadeSpearItem animatable) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/handmade_spear.animation.json");
    }
}
