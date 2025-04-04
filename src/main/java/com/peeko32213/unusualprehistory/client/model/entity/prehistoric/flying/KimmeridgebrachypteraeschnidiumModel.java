package com.peeko32213.unusualprehistory.client.model.entity.prehistoric.flying;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying.KimmeridgebrachypteraeschnidiumEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class KimmeridgebrachypteraeschnidiumModel extends GeoModel<KimmeridgebrachypteraeschnidiumEntity> {
    @Override
    public ResourceLocation getModelResource(KimmeridgebrachypteraeschnidiumEntity kimmer) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/kimmeridgebrachypteraeschnidium.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(KimmeridgebrachypteraeschnidiumEntity kimmer) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/kimmeridgebrachypteraeschnidium/base_"+kimmer.getVariantSkin()+".png");
    }

    @Override
    public ResourceLocation getAnimationResource(KimmeridgebrachypteraeschnidiumEntity kimmer) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/kimmeridgebrachypteraeschnidium.animation.json");
    }

    @Override
    public void setCustomAnimations(KimmeridgebrachypteraeschnidiumEntity animatable, long instanceId, AnimationState<KimmeridgebrachypteraeschnidiumEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
