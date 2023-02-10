package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityBeelzebufo;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBabyRex;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BabyRexModel extends AnimatedGeoModel<EntityBabyRex>
{
    @Override
    public ResourceLocation getModelResource(EntityBabyRex object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/baby_rex.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityBabyRex object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/baby_tyrannosaurus_rex.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBabyRex object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/babyrex.animation.json");
    }

}

