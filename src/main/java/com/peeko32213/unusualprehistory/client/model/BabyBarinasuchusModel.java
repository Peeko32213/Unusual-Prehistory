package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.EntityBabyBarinasuchus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BabyBarinasuchusModel extends GeoModel<EntityBabyBarinasuchus>
{
    @Override
    public ResourceLocation getModelResource(EntityBabyBarinasuchus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/baby_barina.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityBabyBarinasuchus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/baby_barinasuchus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBabyBarinasuchus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/baby_barinasuchus.animation.json");
    }

}

