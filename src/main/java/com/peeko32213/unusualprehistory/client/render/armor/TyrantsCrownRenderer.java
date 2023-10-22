package com.peeko32213.unusualprehistory.client.render.armor;

import com.peeko32213.unusualprehistory.common.item.armor.ItemTyrantsCrown;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class TyrantsCrownRenderer extends GeoArmorRenderer<ItemTyrantsCrown> {
    public TyrantsCrownRenderer() {
        super(new DefaultedItemGeoModel<>(prefix("armor/tyrants_crown")));
    }
}
