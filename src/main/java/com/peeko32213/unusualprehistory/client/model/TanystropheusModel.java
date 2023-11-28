package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityBarinasuchus;
import com.peeko32213.unusualprehistory.common.entity.EntityTanystropheus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TanystropheusModel extends GeoModel<EntityTanystropheus>
{
    @Override
    public ResourceLocation getModelResource(EntityTanystropheus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/tanystropheus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityTanystropheus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tanystropheus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityTanystropheus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/tanystropheus.animation.json");
    }

}

