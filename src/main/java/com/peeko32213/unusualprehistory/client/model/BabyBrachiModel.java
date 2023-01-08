package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBabyBrachi;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBabyRex;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BabyBrachiModel extends AnimatedGeoModel<EntityBabyBrachi>
{
    @Override
    public ResourceLocation getModelLocation(EntityBabyBrachi object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/baby_brachi.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityBabyBrachi object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/babybrachi.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityBabyBrachi object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/babybrachi.animation.json");
    }

}

