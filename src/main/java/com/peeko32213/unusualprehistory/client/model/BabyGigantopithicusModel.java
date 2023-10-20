package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.EntityBabyGigantopithicus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BabyGigantopithicusModel extends GeoModel<EntityBabyGigantopithicus>
{
    @Override
    public ResourceLocation getModelResource(EntityBabyGigantopithicus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/baby_giganto.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityBabyGigantopithicus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/baby_gigantopithicus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityBabyGigantopithicus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/baby_gigantopithicus.animation.json");
    }

}

