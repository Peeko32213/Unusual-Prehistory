package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.EntityBabyGigantopithicus;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.EntityBabyMegalania;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BabyMegalaniaModel extends AnimatedGeoModel<EntityBabyMegalania>
{
    @Override
    public ResourceLocation getModelResource(EntityBabyMegalania object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/baby_megalania.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityBabyMegalania object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/baby_megalania.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBabyMegalania object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/baby_megalania.animation.json");
    }

}

