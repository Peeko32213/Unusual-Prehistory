package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityBrachiosaurus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BrachiosaurusModel extends AnimatedGeoModel<EntityBrachiosaurus>
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

