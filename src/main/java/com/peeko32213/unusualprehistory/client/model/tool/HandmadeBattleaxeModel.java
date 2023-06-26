package com.peeko32213.unusualprehistory.client.model.tool;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.tool.ItemHandmadeBattleaxe;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HandmadeBattleaxeModel extends AnimatedGeoModel<ItemHandmadeBattleaxe> {
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
