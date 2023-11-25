package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityBarinasuchus;
import com.peeko32213.unusualprehistory.common.entity.EntityPsittacosaurus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PsittacosaurusModel extends GeoModel<EntityPsittacosaurus>
{
    @Override
    public ResourceLocation getModelResource(EntityPsittacosaurus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/psittacosaurus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityPsittacosaurus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/psittacosaurus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityPsittacosaurus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/psittacosaurus.animation.json");
    }

}

