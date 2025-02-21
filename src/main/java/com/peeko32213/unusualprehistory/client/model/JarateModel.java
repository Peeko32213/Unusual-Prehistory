package com.peeko32213.unusualprehistory.client.model;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.projectile.JarateEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class JarateModel extends GeoModel<JarateEntity> {

    @Override
    public ResourceLocation getModelResource(JarateEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/jarate.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(JarateEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/jarate_entity.png");
    }

    @Override
    public ResourceLocation getAnimationResource(JarateEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/jarate.animation.json");
    }
}