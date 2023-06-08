package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityPalaeophis;
import com.peeko32213.unusualprehistory.common.entity.msc.part.EntityPalaeophisPart;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PalaeophisPartModel extends AnimatedGeoModel<EntityPalaeophisPart>
{
    @Override
    public ResourceLocation getModelResource(EntityPalaeophisPart object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/palaeophis.geo.json");

    }

    @Override
    public ResourceLocation getTextureResource(EntityPalaeophisPart object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/palaeophis.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityPalaeophisPart object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/palaeophis.animation.json");
    }

}

