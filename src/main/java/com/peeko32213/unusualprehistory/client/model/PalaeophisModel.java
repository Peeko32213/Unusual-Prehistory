package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityPalaeophis;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class PalaeophisModel extends GeoModel<EntityPalaeophis> {

    @Override
    public ResourceLocation getModelResource(EntityPalaeophis object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/palaeophis/palaeophis_head.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityPalaeophis object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/palaeophis/palaeophis_head.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityPalaeophis object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/palaeophis/palaeophis_head.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityPalaeophis animatable, long instanceId, AnimationState<EntityPalaeophis> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
    }
}

