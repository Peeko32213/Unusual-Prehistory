package com.peeko32213.unusualprehistory.client.render.armor;

import com.peeko32213.unusualprehistory.common.item.armor.ItemAustroBoots;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class AustroBootsRenderer extends GeoArmorRenderer<ItemAustroBoots> {
    public AustroBootsRenderer() {
        super(new DefaultedItemGeoModel<>(prefix("armor/austro_boots")));
    }

}
