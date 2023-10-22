package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityStethacanthus;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class StethacanthusModel extends GeoModel<EntityStethacanthus> {
    @Override
    public ResourceLocation getModelResource(EntityStethacanthus object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/stethacanthus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityStethacanthus object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/stethacanthus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityStethacanthus object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/stethacanthus.animation.json");
    }
}

