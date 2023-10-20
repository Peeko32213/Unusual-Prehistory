package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.projectile.EntityHwachaSpike;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HwachaSpikeModel extends GeoModel<EntityHwachaSpike>
{
    @Override
    public ResourceLocation getModelResource(EntityHwachaSpike object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/pin.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityHwachaSpike object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/pin.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityHwachaSpike object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/pin.animation.json");
    }

}

