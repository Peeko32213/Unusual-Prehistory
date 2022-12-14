package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityBeelzebufo;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBabyRex;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BabyRexModel extends AnimatedGeoModel<EntityBabyRex>
{
    @Override
    public ResourceLocation getModelLocation(EntityBabyRex object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/baby_rex.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityBabyRex object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/babyrex.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityBabyRex object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/babyrex.animation.json");
    }

}

