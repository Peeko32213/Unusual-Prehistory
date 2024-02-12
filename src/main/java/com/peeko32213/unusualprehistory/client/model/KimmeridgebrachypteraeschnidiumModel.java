package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityKimmeridgebrachypteraeschnidium;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class KimmeridgebrachypteraeschnidiumModel extends GeoModel<EntityKimmeridgebrachypteraeschnidium>
{


    @Override
    public ResourceLocation getModelResource(EntityKimmeridgebrachypteraeschnidium object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/kimmeridgebrachypteraeschnidium.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityKimmeridgebrachypteraeschnidium object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/kimmer/kimmeridgebrachypteraeschnidium_pattern_a.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityKimmeridgebrachypteraeschnidium object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/kimmeridgebrachypteraeschnidium.animation.json");
    }


}

