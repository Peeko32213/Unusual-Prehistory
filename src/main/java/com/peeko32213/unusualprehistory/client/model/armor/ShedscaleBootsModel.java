package com.peeko32213.unusualprehistory.client.model.armor;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleBoots;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleLeggings;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShedscaleBootsModel extends AnimatedGeoModel<ItemShedscaleBoots> {
    @Override
    public ResourceLocation getModelResource(ItemShedscaleBoots object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/shedscale_armor.geo.json");

    }

    @Override
    public ResourceLocation getTextureResource(ItemShedscaleBoots object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/armor/shedscale_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ItemShedscaleBoots animatable) {
        return null;
    }

}
