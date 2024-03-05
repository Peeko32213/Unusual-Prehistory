package com.peeko32213.unusualprehistory.client.model;

import com.peeko32213.unusualprehistory.common.entity.ICustomAnimationsEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class CustomAnimationsModel<T extends EntityBaseDinosaurAnimal & GeoAnimatable & ICustomAnimationsEntity> extends GeoModel<T>{
    private ResourceLocation model;
    private ResourceLocation texture;
    private ResourceLocation animation;

    public CustomAnimationsModel(ModelLocations.ModelData modelData){
        this.model = modelData.getModel();
        this.texture = modelData.getTexture();
        this.animation = modelData.getAnimation();
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return model;
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return animation;
    }


    @Override
    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        animatable.<T>setCustomAnimation(this, animatable, instanceId, animationState);
    }
}
