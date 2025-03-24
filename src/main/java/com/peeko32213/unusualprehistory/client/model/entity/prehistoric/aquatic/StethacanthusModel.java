package com.peeko32213.unusualprehistory.client.model.entity.prehistoric.aquatic;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.StethacanthusEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class StethacanthusModel extends GeoModel<StethacanthusEntity> {

    @Override
    public ResourceLocation getModelResource(StethacanthusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/stethacanthus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(StethacanthusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/stethacanthus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(StethacanthusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/stethacanthus.animation.json");
    }

    @Override
    public void setCustomAnimations(StethacanthusEntity animatable, long instanceId, AnimationState<StethacanthusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}

