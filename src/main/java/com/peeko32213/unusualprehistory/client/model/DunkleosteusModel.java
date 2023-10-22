package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityCotylorhynchus;
import com.peeko32213.unusualprehistory.common.entity.EntityDunkleosteus;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class DunkleosteusModel extends GeoModel<EntityDunkleosteus>
{
    @Override
    public ResourceLocation getModelResource(EntityDunkleosteus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/dunk.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityDunkleosteus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/dunkleosteus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityDunkleosteus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/dunk.animation.json");
    }

}

