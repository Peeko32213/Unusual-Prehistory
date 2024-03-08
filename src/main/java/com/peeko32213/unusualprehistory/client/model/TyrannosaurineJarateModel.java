package com.peeko32213.unusualprehistory.client.model;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.projectile.EntityAmberShot;
import com.peeko32213.unusualprehistory.common.entity.msc.projectile.EntityTyrannosaurineJarate;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TyrannosaurineJarateModel extends AnimatedGeoModel<EntityTyrannosaurineJarate> {

    @Override
    public ResourceLocation getModelResource(EntityTyrannosaurineJarate object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/jarate.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityTyrannosaurineJarate object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/jarate_entity.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityTyrannosaurineJarate object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/jarate.animation.json");
    }
}
