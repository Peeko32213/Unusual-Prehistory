package com.peeko32213.unusualprehistory.client.model.tool;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.tool.TriceratopsShieldItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TrikeShieldModel extends GeoModel<TriceratopsShieldItem> {
    @Override
    public ResourceLocation getModelResource(TriceratopsShieldItem object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/triceratops_shield.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TriceratopsShieldItem object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/item/triceratops_shield.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TriceratopsShieldItem animatable) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/armor.animation.json");
    }
}
