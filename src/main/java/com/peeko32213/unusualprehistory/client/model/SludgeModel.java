package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntitySludge;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SludgeModel extends GeoModel<EntitySludge>
{
    @Override
    public ResourceLocation getModelResource(EntitySludge object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/sludge.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntitySludge object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/sludge.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntitySludge object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/sludge.animation.json");
    }

}

