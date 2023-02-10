package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBabyBrachi;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBabyRex;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BabyBrachiModel extends AnimatedGeoModel<EntityBabyBrachi>
{
    @Override
    public ResourceLocation getModelResource(EntityBabyBrachi object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/baby_brachi.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityBabyBrachi object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/baby_brachiosaurus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBabyBrachi object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/babybrachi.animation.json");
    }

}

