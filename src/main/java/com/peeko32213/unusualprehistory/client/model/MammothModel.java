package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityMammoth;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MammothModel extends AnimatedGeoModel<EntityMammoth>
{
    @Override
    public ResourceLocation getModelResource(EntityMammoth object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/mammoth.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityMammoth object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/mammoth.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityMammoth object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/mammoth.animation.json");
    }

}

