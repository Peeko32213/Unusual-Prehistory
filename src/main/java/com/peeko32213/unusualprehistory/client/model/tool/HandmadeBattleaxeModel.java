package com.peeko32213.unusualprehistory.client.model.tool;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.tool.HandmadeBattleaxeItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HandmadeBattleaxeModel extends GeoModel<HandmadeBattleaxeItem> {
    @Override
    public ResourceLocation getModelResource(HandmadeBattleaxeItem object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/handmade_battleaxe.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HandmadeBattleaxeItem object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/item/handmade_battleaxe.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HandmadeBattleaxeItem animatable) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/handmade_battleaxe.animation.json");
    }
}
