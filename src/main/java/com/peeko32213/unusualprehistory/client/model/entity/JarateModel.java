package com.peeko32213.unusualprehistory.client.model.entity;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.LeedsichthysEntity;
import com.peeko32213.unusualprehistory.common.entity.projectile.JarateEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class JarateModel extends GeoModel<JarateEntity> {

    @Override
    public ResourceLocation getModelResource(JarateEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/jarate.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(JarateEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/jarate.png");
    }

    @Override
    public ResourceLocation getAnimationResource(JarateEntity object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/jarate.animation.json");
    }

}