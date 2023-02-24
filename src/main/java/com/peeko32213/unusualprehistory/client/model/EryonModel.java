package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityEryon;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EryonModel extends AnimatedGeoModel<EntityEryon> {
    private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation("unusualprehistory:textures/entity/eryon.png");
    private static final ResourceLocation TEXTURE_BLUE = new ResourceLocation("unusualprehistory:textures/entity/eryon_blue.png");

    @Override
    public ResourceLocation getModelResource(EntityEryon object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/eryon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityEryon object)
    {
        switch (object.getVariant()){
            case 1:
                return TEXTURE_BLUE;
            default:
                return TEXTURE_NORMAL;
        }
    }

    @Override
    public ResourceLocation getAnimationResource(EntityEryon object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/eryon.animation.json");
    }

}

