package com.peeko32213.unusualprehistory.client.model.tool;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.tool.ItemHandmadeClub;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HandmadeClubModel extends GeoModel<ItemHandmadeClub> {
    @Override
    public ResourceLocation getModelResource(ItemHandmadeClub object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/handmade_club.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ItemHandmadeClub object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/item/handmade_club.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ItemHandmadeClub animatable) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/handmade_club.animation.json");
    }
}
