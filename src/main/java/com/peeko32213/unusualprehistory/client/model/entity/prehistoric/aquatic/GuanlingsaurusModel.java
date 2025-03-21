package com.peeko32213.unusualprehistory.client.model.entity.prehistoric.aquatic;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.GuanlingsaurusEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class GuanlingsaurusModel extends GeoModel<GuanlingsaurusEntity> {

    @Override
    public ResourceLocation getModelResource(GuanlingsaurusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/guanlingsaurus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GuanlingsaurusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/guanlingsaurus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GuanlingsaurusEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/guanlingsaurus.animation.json");
    }

    @Override
    public void setCustomAnimations(GuanlingsaurusEntity animatable, long instanceId, AnimationState<GuanlingsaurusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}