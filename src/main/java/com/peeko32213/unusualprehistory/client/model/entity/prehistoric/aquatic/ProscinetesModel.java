package com.peeko32213.unusualprehistory.client.model.entity.prehistoric.aquatic;

import com.peeko32213.unusualprehistory.core.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.ProscinetesEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class ProscinetesModel extends GeoModel<ProscinetesEntity> {

    @Override
    public ResourceLocation getModelResource(ProscinetesEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/proscinetes.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ProscinetesEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/proscinetes.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ProscinetesEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/proscinetes.animation.json");
    }

    @Override
    public void setCustomAnimations(ProscinetesEntity animatable, long instanceId, AnimationState<ProscinetesEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
