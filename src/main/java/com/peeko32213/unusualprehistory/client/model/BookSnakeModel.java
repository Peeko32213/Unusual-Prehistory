package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityScaumenacia;
import com.peeko32213.unusualprehistory.common.entity.msc.util.EntityBookSnake;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BookSnakeModel extends AnimatedGeoModel<EntityBookSnake>
{

    @Override
    public ResourceLocation getModelResource(EntityBookSnake object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/palaeophis.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityBookSnake object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/palaeophis.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBookSnake object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/palaeophis.animation.json");
    }


}

