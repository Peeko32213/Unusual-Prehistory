package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.EntityBrachiosaurusTeen;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BrachiosaurusTeenModel extends AnimatedGeoModel<EntityBrachiosaurusTeen>
{
    @Override
    public ResourceLocation getModelResource(EntityBrachiosaurusTeen object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/brachi_teen.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityBrachiosaurusTeen object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/brachiosaurus_teen.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBrachiosaurusTeen object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/brachi.animation.json");
    }

}

