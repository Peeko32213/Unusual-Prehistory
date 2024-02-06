package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityDiplocaulus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DiplocaulusModel extends GeoModel<EntityDiplocaulus>
{
    @Override
    public ResourceLocation getModelResource(EntityDiplocaulus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/diplocaulus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityDiplocaulus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/diplocaulus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityDiplocaulus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/diplocaulus.animation.json");
    }

}

