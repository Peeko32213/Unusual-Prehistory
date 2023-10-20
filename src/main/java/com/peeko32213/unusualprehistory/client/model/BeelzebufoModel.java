package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityBeelzebufo;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BeelzebufoModel extends GeoModel<EntityBeelzebufo>
{
    @Override
    public ResourceLocation getModelResource(EntityBeelzebufo object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/beelzebufo.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityBeelzebufo object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/beelzebufo.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBeelzebufo object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/beelz.animation.json");
    }

}

