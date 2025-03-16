package com.peeko32213.unusualprehistory.client.model.entity;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.SchlumbergeritesEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class SchlumbergeritesModel extends GeoModel<SchlumbergeritesEntity> {

    @Override
    public ResourceLocation getModelResource(SchlumbergeritesEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/schlumbergerites.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SchlumbergeritesEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/schlumbergerites.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SchlumbergeritesEntity object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/schlumbergerites.animation.json");
    }

    @Override
    public void setCustomAnimations(SchlumbergeritesEntity animatable, long instanceId, AnimationState<SchlumbergeritesEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
