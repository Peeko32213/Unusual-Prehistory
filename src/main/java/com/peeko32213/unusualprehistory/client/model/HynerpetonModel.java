package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityHynerpeton;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HynerpetonModel extends GeoModel<EntityHynerpeton>
{
    @Override
    public ResourceLocation getModelResource(EntityHynerpeton object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/hynerpeton.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityHynerpeton object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/hynerpeton.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityHynerpeton object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/hynerpeton.animation.json");
    }



}

