package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.EntityBabyParaceratherium;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BabyParaceratheriumModel extends AnimatedGeoModel<EntityBabyParaceratherium>
{
    @Override
    public ResourceLocation getModelResource(EntityBabyParaceratherium object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/baby_paracer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityBabyParaceratherium object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/baby_paraceratherium.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBabyParaceratherium object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/baby_paraceratherium.animation.json");
    }

}

