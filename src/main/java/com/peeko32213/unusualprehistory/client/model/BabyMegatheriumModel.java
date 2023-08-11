package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.EntityBabyBrachi;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.EntityBabyMegatherium;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BabyMegatheriumModel extends AnimatedGeoModel<EntityBabyMegatherium>
{
    @Override
    public ResourceLocation getModelResource(EntityBabyMegatherium object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/baby_megatherium.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityBabyMegatherium object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/baby_megatherium.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBabyMegatherium object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/baby_megatherium.animation.json");
    }

}

