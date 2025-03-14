package com.peeko32213.unusualprehistory.client.model.entity;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.MegalamprisEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class MegalamprisModel extends GeoModel<MegalamprisEntity> {

    @Override
    public ResourceLocation getModelResource(MegalamprisEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/megalampris.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MegalamprisEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/megalampris.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MegalamprisEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/megalampris.animation.json");
    }

    @Override
    public void setCustomAnimations(MegalamprisEntity animatable, long instanceId, AnimationState<MegalamprisEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
