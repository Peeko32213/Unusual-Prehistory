package com.peeko32213.unusualprehistory.client.model.armor;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.armor.ItemMajungaHelmet;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleHelmet;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShedscaleHelmetModel extends AnimatedGeoModel<ItemShedscaleHelmet> {
    @Override
    public ResourceLocation getModelResource(ItemShedscaleHelmet object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/shedscale_armor.geo.json");

    }

    @Override
    public ResourceLocation getTextureResource(ItemShedscaleHelmet object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/armor/shedscale_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ItemShedscaleHelmet animatable) {
        return null;
    }

}
