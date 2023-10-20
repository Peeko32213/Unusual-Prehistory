package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityEryon;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class EryonModel extends GeoModel<EntityEryon> {
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
        if (object.getVariant() == 1) {
            return TEXTURE_BLUE;
        }
        return TEXTURE_NORMAL;
    }

    @Override
    public ResourceLocation getAnimationResource(EntityEryon object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/eryon.animation.json");
    }

}

