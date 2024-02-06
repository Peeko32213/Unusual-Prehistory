package com.peeko32213.unusualprehistory.client.model.tool;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.loot.tool.ItemTrikeShield;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TrikeShieldModel extends GeoModel<ItemTrikeShield> {
    @Override
    public ResourceLocation getModelResource(ItemTrikeShield object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/trike_shield.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ItemTrikeShield object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/item/trike_shield.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ItemTrikeShield animatable) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/armor.animation.json");
    }
}
