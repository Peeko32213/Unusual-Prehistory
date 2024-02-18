package com.peeko32213.unusualprehistory.client.model;

import com.peeko32213.unusualprehistory.common.entity.IVariantModelTextureEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class VariantModelTextureModel<T extends GeoAnimatable & IVariantModelTextureEntity> extends GeoModel<T>{
    private ResourceLocation model;
    private ResourceLocation texture;
    private ResourceLocation animation;

    public VariantModelTextureModel(ModelLocations.ModelData modelData){
        this.model = modelData.getModel();
        this.texture = modelData.getTexture();
        this.animation = modelData.getAnimation();
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        if(animatable.getVariantModel() != null) {
            return animatable.getVariantModel();
        }
        return model;
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        if(animatable.getVariantTexture() != null) {
            return animatable.getVariantTexture();
        }
        return texture;
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return animation;
    }
}
