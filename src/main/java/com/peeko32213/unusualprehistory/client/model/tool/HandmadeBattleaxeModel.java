package com.peeko32213.unusualprehistory.client.model.tool;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.loot.tool.ItemHandmadeBattleaxe;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HandmadeBattleaxeModel extends GeoModel<ItemHandmadeBattleaxe> {
    @Override
    public ResourceLocation getModelResource(ItemHandmadeBattleaxe object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/handmade_battleaxe.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ItemHandmadeBattleaxe object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/item/handmade_battleaxe.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ItemHandmadeBattleaxe animatable) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/handmade_battleaxe.animation.json");
    }
}
