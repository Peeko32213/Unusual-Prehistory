package com.peeko32213.unusualprehistory.client.model.armor;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleChestplate;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleHelmet;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShedscaleChestplateModel extends AnimatedGeoModel<ItemShedscaleChestplate> {
    @Override
    public ResourceLocation getModelResource(ItemShedscaleChestplate object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/shedscale_armor.geo.json");

    }

    @Override
    public ResourceLocation getTextureResource(ItemShedscaleChestplate object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/armor/shedscale_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ItemShedscaleChestplate animatable) {
        return null;
    }

}
