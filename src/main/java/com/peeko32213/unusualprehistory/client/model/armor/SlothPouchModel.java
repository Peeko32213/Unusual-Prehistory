package com.peeko32213.unusualprehistory.client.model.armor;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.armor.ItemSlothPouch;
import com.peeko32213.unusualprehistory.common.item.armor.ItemTyrantsCrown;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SlothPouchModel extends AnimatedGeoModel<ItemSlothPouch> {
    @Override
    public ResourceLocation getModelResource(ItemSlothPouch object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/sloth_pouch_armor.geo.json");

    }

    @Override
    public ResourceLocation getTextureResource(ItemSlothPouch object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/armor/slothpouch_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ItemSlothPouch animatable) {
        return null;
    }

}
