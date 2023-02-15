package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityAnurognathus;
import com.peeko32213.unusualprehistory.common.entity.EntityEryon;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EryonModel extends AnimatedGeoModel<EntityEryon>
{
    @Override
    public ResourceLocation getModelResource(EntityEryon object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/eryon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityEryon object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/eryon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityEryon object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/eryon.animation.json");
    }

}

