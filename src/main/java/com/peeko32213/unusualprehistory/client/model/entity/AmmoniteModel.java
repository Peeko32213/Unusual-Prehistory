package com.peeko32213.unusualprehistory.client.model.entity;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.AmmoniteEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class AmmoniteModel extends GeoModel<AmmoniteEntity> {

    @Override
    public ResourceLocation getModelResource(AmmoniteEntity ammonite) {
        if(ammonite.getVariant() == 1) {
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/ammonite/ammonite_pinacoceras.geo.json");
        } else {
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/ammonite/ammonite.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureResource(AmmoniteEntity ammonite) {
        if(ammonite.getVariant() == 1) {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/ammonite/ammonite_pinacoceras.png");
        } else {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/ammonite/ammonite.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(AmmoniteEntity ammonite) {
        if(ammonite.getVariant() == 1) {
            return new ResourceLocation(UnusualPrehistory.MODID, "animations/ammonite/ammonite_pinacoceras.animation.json");
        } else {
            return new ResourceLocation(UnusualPrehistory.MODID, "animations/ammonite/ammonite.animation.json");
        }
    }

    @Override
    public void setCustomAnimations(AmmoniteEntity animatable, long instanceId, AnimationState<AmmoniteEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        if (animatable.isFromBook()) return;
    }
}

