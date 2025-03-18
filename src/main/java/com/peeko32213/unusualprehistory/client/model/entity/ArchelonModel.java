package com.peeko32213.unusualprehistory.client.model.entity;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.ArchelonEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class ArchelonModel extends GeoModel<ArchelonEntity> {

    @Override
    public ResourceLocation getModelResource(ArchelonEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/archelon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ArchelonEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/archelon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ArchelonEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/archelon.animation.json");
    }

    @Override
    public void setCustomAnimations(ArchelonEntity animatable, long instanceId, AnimationState<ArchelonEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;

        CoreGeoBone saddle = this.getAnimationProcessor().getBone("Saddle");

        saddle.setHidden(!animatable.isSaddled());
    }
}

