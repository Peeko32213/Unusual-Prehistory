package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.PalaeophisEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class PalaeophisModel extends GeoModel<PalaeophisEntity> {

    @Override
    public ResourceLocation getModelResource(PalaeophisEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/palaeophis/palaeophis_head.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PalaeophisEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/palaeophis/palaeophis_head.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PalaeophisEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/palaeophis/palaeophis_head.animation.json");
    }

    @Override
    public void setCustomAnimations(PalaeophisEntity animatable, long instanceId, AnimationState<PalaeophisEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
    }
}

