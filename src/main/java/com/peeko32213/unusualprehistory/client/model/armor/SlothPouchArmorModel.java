package com.peeko32213.unusualprehistory.client.model.armor;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.armor.ItemAustroBoots;
import com.peeko32213.unusualprehistory.common.item.armor.ItemSlothPouchArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SlothPouchArmorModel extends AnimatedGeoModel<ItemSlothPouchArmor> {
    @Override
    public ResourceLocation getModelResource(ItemSlothPouchArmor object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/sloth_pouch_armor.geo.json");

    }

    @Override
    public ResourceLocation getTextureResource(ItemSlothPouchArmor object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/armor/sloth_pouch_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ItemSlothPouchArmor animatable) {
        return null;
    }

}
