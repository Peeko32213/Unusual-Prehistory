package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.EntityBabySmilodon;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BabySmilodonModel extends AnimatedGeoModel<EntityBabySmilodon>
{
    @Override
    public ResourceLocation getModelResource(EntityBabySmilodon object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/baby_smilodon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityBabySmilodon object)
    {
        if(object.getVariant() == 1){
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/baby_ocelot_smilodon.png");
        }
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/baby_smilodon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBabySmilodon object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/baby_smilodon.animation.json");
    }

}

