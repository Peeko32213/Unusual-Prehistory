package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityAnurognathus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AnurognathusModel extends AnimatedGeoModel<EntityAnurognathus>
{
    @Override
    public ResourceLocation getModelResource(EntityAnurognathus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/anuro.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityAnurognathus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/anurognathus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityAnurognathus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/anuro.animation.json");
    }

}

