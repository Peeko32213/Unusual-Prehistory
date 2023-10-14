package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityTalpanas;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TalpanasModel extends AnimatedGeoModel<EntityTalpanas>
{
    @Override
    public ResourceLocation getModelResource(EntityTalpanas object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/talpanas.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityTalpanas object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/talpanas.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityTalpanas object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/talpanas.animation.json");
    }

}

