package com.peeko32213.unusualprehistory.client.model.entity;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.CoronodonEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class CoronodonModel extends GeoModel<CoronodonEntity> {

    @Override
    public ResourceLocation getModelResource(CoronodonEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/coronodon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CoronodonEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/coronodon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CoronodonEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/coronodon.animation.json");
    }

    @Override
    public void setCustomAnimations(CoronodonEntity animatable, long instanceId, AnimationState<CoronodonEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
