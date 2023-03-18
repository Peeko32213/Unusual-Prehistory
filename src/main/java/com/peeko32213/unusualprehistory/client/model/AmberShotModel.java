package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.projectile.EntityAmberShot;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AmberShotModel extends AnimatedGeoModel<EntityAmberShot>
{
    @Override
    public ResourceLocation getModelResource(EntityAmberShot object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/amber_shot.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityAmberShot object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/amber_shot.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityAmberShot object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/amber_shot.animation.json");
    }

}

