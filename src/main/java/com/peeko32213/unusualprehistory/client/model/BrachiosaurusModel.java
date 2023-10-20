package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityBrachiosaurus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BrachiosaurusModel extends GeoModel<EntityBrachiosaurus>
{
    @Override
    public ResourceLocation getModelResource(EntityBrachiosaurus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/brachi.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityBrachiosaurus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/brachiosaurus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBrachiosaurus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/brachi_adult.animation.json");
    }

}

