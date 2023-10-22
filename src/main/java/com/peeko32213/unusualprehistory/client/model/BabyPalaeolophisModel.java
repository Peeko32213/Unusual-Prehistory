package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.EntityBabyPalaeolophis;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BabyPalaeolophisModel extends GeoModel<EntityBabyPalaeolophis>
{
    @Override
    public ResourceLocation getModelResource(EntityBabyPalaeolophis object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/baby_palaeophis.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityBabyPalaeolophis object)
    {
        if(object.getVariant() == 1){
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/baby_deep_palaeophis.png");
        }
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/baby_palaeophis.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBabyPalaeolophis object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/baby_palaeophis.animation.json");
    }

}

