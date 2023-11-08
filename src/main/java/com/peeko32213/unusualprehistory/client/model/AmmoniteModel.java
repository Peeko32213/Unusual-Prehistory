package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityAmmonite;
import com.peeko32213.unusualprehistory.common.entity.EntityScaumenacia;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class AmmoniteModel extends GeoModel<EntityAmmonite>
{

    @Override
    public ResourceLocation getModelResource(EntityAmmonite object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/ammonite.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityAmmonite object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/ammonite.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityAmmonite object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/ammonite.animation.json");
    }


}

