package com.peeko32213.unusualprehistory.client.render.armor;

import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleArmor;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class ShedscaleArmorRenderer extends GeoArmorRenderer<ItemShedscaleArmor> {
    public ShedscaleArmorRenderer() {
        super(new DefaultedItemGeoModel<>(prefix("armor/shedscale_armor")));
    }
}
