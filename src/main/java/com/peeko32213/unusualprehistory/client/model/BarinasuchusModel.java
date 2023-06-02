package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityBarinasuchus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BarinasuchusModel extends AnimatedGeoModel<EntityBarinasuchus>
{
    @Override
    public ResourceLocation getModelResource(EntityBarinasuchus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/barinasuchus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityBarinasuchus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/barinasuchus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBarinasuchus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/barinasuchus.animation.json");
    }

}

