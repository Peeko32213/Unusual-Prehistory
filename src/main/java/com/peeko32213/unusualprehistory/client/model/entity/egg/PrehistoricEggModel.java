package com.peeko32213.unusualprehistory.client.model.entity.egg;

import com.peeko32213.unusualprehistory.common.entity.custom.eggs.PrehistoricEggEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class PrehistoricEggModel extends GeoModel<PrehistoricEggEntity> {

    @Override
    public ResourceLocation getModelResource(PrehistoricEggEntity animatable) {
        return animatable.getModel();
    }

    @Override
    public ResourceLocation getTextureResource(PrehistoricEggEntity animatable) {
        return animatable.getTexture();
    }

    @Override
    public ResourceLocation getAnimationResource(PrehistoricEggEntity animatable) {
        return animatable.getAnimation();
    }

    @Override
    public void setCustomAnimations(PrehistoricEggEntity animatable, long instanceId, AnimationState<PrehistoricEggEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
