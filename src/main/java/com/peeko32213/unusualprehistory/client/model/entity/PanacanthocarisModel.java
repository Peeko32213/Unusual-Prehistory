package com.peeko32213.unusualprehistory.client.model.entity;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.PanacanthocarisEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class PanacanthocarisModel extends GeoModel<PanacanthocarisEntity> {

    private static final ResourceLocation TEXTURE_SOLAR = new ResourceLocation("unusualprehistory:textures/entity/panacanthocaris/panacanthocaris_solar.png");
    private static final ResourceLocation TEXTURE_RADIANCE = new ResourceLocation("unusualprehistory:textures/entity/panacanthocaris/panacanthocaris_radiance.png");
    private static final ResourceLocation MODEL = new ResourceLocation("unusualprehistory:geo/panacanthocaris.geo.json");
    private static final ResourceLocation ANIMATION = new ResourceLocation("unusualprehistory:animations/panacanthocaris.animation.json");

    @Override
    public ResourceLocation getModelResource(PanacanthocarisEntity object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(PanacanthocarisEntity object) {
        if(object.getVariant() == 1) {
            return TEXTURE_RADIANCE;
        }
        else return TEXTURE_SOLAR;
    }

    @Override
    public ResourceLocation getAnimationResource(PanacanthocarisEntity object) {
        return ANIMATION;
    }

    @Override
    public void setCustomAnimations(PanacanthocarisEntity animatable, long instanceId, AnimationState<PanacanthocarisEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}