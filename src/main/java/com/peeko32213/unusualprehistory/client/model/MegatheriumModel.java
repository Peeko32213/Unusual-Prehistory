package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityBarinasuchus;
import com.peeko32213.unusualprehistory.common.entity.EntityMegatherium;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MegatheriumModel extends AnimatedGeoModel<EntityMegatherium>
{
    @Override
    public ResourceLocation getModelResource(EntityMegatherium object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/megatherium.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityMegatherium object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/megatherium.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityMegatherium object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/megatherium.animation.json");
    }

}

