package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityLongisquama;
import com.peeko32213.unusualprehistory.common.entity.EntityOtarocyon;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class LongisquamaModel extends GeoModel<EntityLongisquama>
{
    @Override
    public ResourceLocation getModelResource(EntityLongisquama object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/longisquama.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityLongisquama object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/longisquama.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityLongisquama object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/longisquama.animation.json");
    }

}

