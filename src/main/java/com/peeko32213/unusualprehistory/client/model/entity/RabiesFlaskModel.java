package com.peeko32213.unusualprehistory.client.model.entity;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.projectile.RabiesFlaskEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class RabiesFlaskModel extends GeoModel<RabiesFlaskEntity> {

    @Override
    public ResourceLocation getModelResource(RabiesFlaskEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/rabies.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RabiesFlaskEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/rabies.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RabiesFlaskEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/jarate.animation.json");
    }
}