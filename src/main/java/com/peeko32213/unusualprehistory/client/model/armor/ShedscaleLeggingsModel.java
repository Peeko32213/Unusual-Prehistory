package com.peeko32213.unusualprehistory.client.model.armor;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleChestplate;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleLeggings;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShedscaleLeggingsModel extends AnimatedGeoModel<ItemShedscaleLeggings> {
    @Override
    public ResourceLocation getModelResource(ItemShedscaleLeggings object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/shedscale_armor.geo.json");

    }

    @Override
    public ResourceLocation getTextureResource(ItemShedscaleLeggings object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/armor/shedscale_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ItemShedscaleLeggings animatable) {
        return null;
    }

}
