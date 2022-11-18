package com.peeko32213.unusualprehistory.client.model.tool;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.tool.ItemVelociShield;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class VelociShieldModel extends AnimatedGeoModel<ItemVelociShield> {
    @Override
    public ResourceLocation getModelLocation(ItemVelociShield object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/velocishield.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ItemVelociShield object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/item/veloci_shield.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ItemVelociShield animatable) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/armor.animation.json");
    }
}
