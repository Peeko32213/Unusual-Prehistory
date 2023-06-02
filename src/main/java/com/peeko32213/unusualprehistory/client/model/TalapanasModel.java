package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityTalapanas;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TalapanasModel extends AnimatedGeoModel<EntityTalapanas>
{
    @Override
    public ResourceLocation getModelResource(EntityTalapanas object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/talapanas.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityTalapanas object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/talapanas.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityTalapanas object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/talapanas.animation.json");
    }

}

