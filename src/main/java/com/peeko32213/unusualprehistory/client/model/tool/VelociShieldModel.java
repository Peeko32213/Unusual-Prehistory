package com.peeko32213.unusualprehistory.client.model.tool;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.tool.ItemVelociShield;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class VelociShieldModel extends GeoModel<ItemVelociShield> {
    @Override
    public ResourceLocation getModelResource(ItemVelociShield object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/veloci_shield.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ItemVelociShield object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/item/veloci_shield.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ItemVelociShield animatable) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/armor.animation.json");
    }
}
