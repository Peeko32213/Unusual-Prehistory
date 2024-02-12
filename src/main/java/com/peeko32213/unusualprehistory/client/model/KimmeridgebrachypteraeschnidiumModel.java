package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.client.model.util.ColorableGeoModel;
import com.peeko32213.unusualprehistory.common.entity.EntityKimmeridgebrachypteraeschnidium;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class KimmeridgebrachypteraeschnidiumModel extends ColorableGeoModel<EntityKimmeridgebrachypteraeschnidium>
{

    private static final ResourceLocation KIMMER_YELLOW_GREEN_LOCATION = prefix("textures/entity/kimmer/kimmeridgebrachypteraeschnidium_yellow_green.png");
    private static final ResourceLocation KIMMER_BLUE_LIGHTBLUE_LOCATION = prefix("textures/entity/kimmer/kimmeridgebrachypteraeschnidium_blue_lightblue.png");
    private static final ResourceLocation KIMMER_RED_DARKRED_LOCATION = prefix("textures/entity/kimmer/kimmeridgebrachypteraeschnidium_red_darkred.png");
    private static final ResourceLocation KIMMER_PURPLE_PINK_LOCATION = prefix("textures/entity/kimmer/kimmeridgebrachypteraeschnidium_purple_pink.png");

    @Override
    public ResourceLocation getModelResource(EntityKimmeridgebrachypteraeschnidium object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/kimmeridgebrachypteraeschnidium.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityKimmeridgebrachypteraeschnidium object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/kimmeridgebrachypteraeschnidium_pattern_a.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityKimmeridgebrachypteraeschnidium object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/kimmeridgebrachypteraeschnidium.animation.json");
    }


}

