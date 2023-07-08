package com.peeko32213.unusualprehistory.client.model.tool;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.tool.ItemHandmadeBattleaxe;
import com.peeko32213.unusualprehistory.common.item.tool.ItemHandmadeClub;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HandmadeClubModel extends AnimatedGeoModel<ItemHandmadeClub> {
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
