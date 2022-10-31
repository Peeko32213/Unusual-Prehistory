package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityAnurognathus;
import com.peeko32213.unusualprehistory.common.entity.EntityBeelzebufo;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BeelzebufoModel extends AnimatedGeoModel<EntityBeelzebufo>
{
    @Override
    public ResourceLocation getModelLocation(EntityBeelzebufo object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/beelzebufo.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityBeelzebufo object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/beelze2.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityBeelzebufo object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/beelz.animation.json");
    }

}

