package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityDunkleosteus;
import com.peeko32213.unusualprehistory.common.entity.EntityPalaeophis;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PalaeophisModel extends AnimatedGeoModel<EntityPalaeophis>
{
    @Override
    public ResourceLocation getModelResource(EntityPalaeophis object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/palaeophis.geo.json");

    }

    @Override
    public ResourceLocation getTextureResource(EntityPalaeophis object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/palaeophis.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityPalaeophis object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/palaeophis.animation.json");
    }

}

