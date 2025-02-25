package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.HynerpetonEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class HynerpetonModel extends GeoModel<HynerpetonEntity> {
    @Override
    public ResourceLocation getModelResource(HynerpetonEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/hynerpeton.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HynerpetonEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/hynerpeton.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HynerpetonEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/hynerpeton.animation.json");
    }

    @Override
    public void setCustomAnimations(HynerpetonEntity animatable, long instanceId, AnimationState<HynerpetonEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }

}

