package com.peeko32213.unusualprehistory.client.model.tool;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.tool.HandmadeClubItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HandmadeClubModel extends GeoModel<HandmadeClubItem> {
    @Override
    public ResourceLocation getModelResource(HandmadeClubItem object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/handmade_club.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HandmadeClubItem object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/item/handmade_club.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HandmadeClubItem animatable) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/handmade_club.animation.json");
    }
}
