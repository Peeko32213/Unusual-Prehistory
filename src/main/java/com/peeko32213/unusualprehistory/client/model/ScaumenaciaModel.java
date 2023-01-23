package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityAnurognathus;
import com.peeko32213.unusualprehistory.common.entity.EntityScaumenacia;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ScaumenaciaModel extends AnimatedGeoModel<EntityScaumenacia>
{

    @Override
    public ResourceLocation getModelLocation(EntityScaumenacia object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/scaumenacia.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityScaumenacia object)
    {
        if (object.isGolden()) {
            return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/scaumenacia_buddah.png");
        }
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/scaumenacia.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityScaumenacia object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/scaumenacia.animation.json");
    }

}

