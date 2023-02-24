package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityEncrusted;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EncrustedModel extends AnimatedGeoModel<EntityEncrusted>
{
    @Override
    public ResourceLocation getModelResource(EntityEncrusted object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/encrusted.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityEncrusted object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/encrusted.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityEncrusted object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/encrusted.animation.json");
    }

}

