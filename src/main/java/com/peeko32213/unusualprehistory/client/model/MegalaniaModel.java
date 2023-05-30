package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityMammoth;
import com.peeko32213.unusualprehistory.common.entity.EntityMegalania;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MegalaniaModel extends AnimatedGeoModel<EntityMegalania>
{
    @Override
    public ResourceLocation getModelResource(EntityMegalania object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/megalania.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityMegalania object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/megalania.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityMegalania object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/megalania.animation.json");
    }

}

