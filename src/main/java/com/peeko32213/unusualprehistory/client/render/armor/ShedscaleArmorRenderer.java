package com.peeko32213.unusualprehistory.client.render.armor;

import com.peeko32213.unusualprehistory.common.item.armor.ShedscaleArmorItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class ShedscaleArmorRenderer extends GeoArmorRenderer<ShedscaleArmorItem> {
    public ShedscaleArmorRenderer() {
        super(new DefaultedItemGeoModel<>(prefix("armor/shedscale_armor")));
    }
}
