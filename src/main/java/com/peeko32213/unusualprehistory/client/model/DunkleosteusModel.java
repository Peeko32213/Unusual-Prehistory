package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityDunkleosteus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;


public class DunkleosteusModel extends GeoModel<EntityDunkleosteus>
{
    @Override
    public ResourceLocation getModelResource(EntityDunkleosteus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/dunk.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityDunkleosteus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/dunkleosteus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityDunkleosteus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/dunk.animation.json");
    }

}

