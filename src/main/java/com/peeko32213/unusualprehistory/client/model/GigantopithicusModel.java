package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityBeelzebufo;
import com.peeko32213.unusualprehistory.common.entity.EntityGigantopithicus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GigantopithicusModel extends AnimatedGeoModel<EntityGigantopithicus>
{
    @Override
    public ResourceLocation getModelResource(EntityGigantopithicus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/gigantopithicus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityGigantopithicus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/gigantopithicus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityGigantopithicus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/gigantopithicus.animation.json");
    }

}

