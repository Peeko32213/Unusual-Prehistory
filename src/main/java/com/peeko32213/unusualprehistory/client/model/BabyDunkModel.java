package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.EntityBabyDunk;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;


public class BabyDunkModel extends GeoModel<EntityBabyDunk>
{
    @Override
    public ResourceLocation getModelResource(EntityBabyDunk object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/babydunk.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityBabyDunk object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/baby_dunkleosteus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBabyDunk object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/babydunk.animation.json");
    }

}

