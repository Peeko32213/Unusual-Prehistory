package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityFurcacauda;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.EntityBabyDunk;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;


public class FurcacaudaModel extends GeoModel<EntityFurcacauda>
{
    @Override
    public ResourceLocation getModelResource(EntityFurcacauda object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/furacacauda.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityFurcacauda object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/furacacauda.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityFurcacauda object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/furacacauda.animation.json");
    }

}

