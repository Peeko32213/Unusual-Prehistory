package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityParaceratherium;
import com.peeko32213.unusualprehistory.common.entity.EntitySmilodon;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ParaceratheriumModel extends AnimatedGeoModel<EntityParaceratherium>
{
    @Override
    public ResourceLocation getModelResource(EntityParaceratherium object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/paraceratherium.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityParaceratherium object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/paraceratherium.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityParaceratherium object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/paraceratherium.animation.json");
    }

}

