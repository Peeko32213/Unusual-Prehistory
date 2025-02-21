package com.peeko32213.unusualprehistory.client.model;

import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.IVariantEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class VariantModel<T extends GeoAnimatable & IVariantEntity> extends GeoModel<T>{
    private ResourceLocation model;
    private ResourceLocation texture;
    private ResourceLocation animation;

    public VariantModel(ModelLocations.ModelData modelData){
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
