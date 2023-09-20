package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.EntityBabyMammoth;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BabyMammothModel extends AnimatedGeoModel<EntityBabyMammoth>
{
    @Override
    public ResourceLocation getModelResource(EntityBabyMammoth object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/baby_mammoth.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityBabyMammoth object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/baby_mammoth.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBabyMammoth object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/baby_mammoth.animation.json");
    }

}

