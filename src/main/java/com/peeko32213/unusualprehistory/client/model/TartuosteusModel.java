package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityTartuosteus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;


public class TartuosteusModel extends GeoModel<EntityTartuosteus>
{
    @Override
    public ResourceLocation getModelResource(EntityTartuosteus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/tartuosteus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityTartuosteus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tartuosteus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityTartuosteus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/tartuosteus.animation.json");
    }

}

