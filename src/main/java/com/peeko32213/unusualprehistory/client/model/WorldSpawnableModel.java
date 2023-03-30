package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityEryon;
import com.peeko32213.unusualprehistory.common.entity.EntityWorldSpawnable;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WorldSpawnableModel extends AnimatedGeoModel<EntityWorldSpawnable> {
    private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation("unusualprehistory:textures/entity/eryon.png");
    private static final ResourceLocation TEXTURE_BLUE = new ResourceLocation("unusualprehistory:textures/entity/eryon_blue.png");

    @Override
    public ResourceLocation getModelResource(EntityWorldSpawnable object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/eryon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityWorldSpawnable object)
    {
        return TEXTURE_NORMAL;
    }

    @Override
    public ResourceLocation getAnimationResource(EntityWorldSpawnable object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/eryon.animation.json");
    }

}

