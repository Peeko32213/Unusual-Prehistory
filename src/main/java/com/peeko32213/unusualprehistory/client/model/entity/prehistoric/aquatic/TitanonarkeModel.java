package com.peeko32213.unusualprehistory.client.model.entity.prehistoric.aquatic;

import com.peeko32213.unusualprehistory.core.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.TitanonarkeEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class TitanonarkeModel extends GeoModel<TitanonarkeEntity> {

    @Override
    public ResourceLocation getModelResource(TitanonarkeEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/titanonarke.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TitanonarkeEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/titanonarke.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TitanonarkeEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/titanonarke.animation.json");
    }

    @Override
    public void setCustomAnimations(TitanonarkeEntity animatable, long instanceId, AnimationState<TitanonarkeEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
