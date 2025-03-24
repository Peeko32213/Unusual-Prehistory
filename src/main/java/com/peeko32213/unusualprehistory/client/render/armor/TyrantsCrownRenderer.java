package com.peeko32213.unusualprehistory.client.render.armor;

import com.peeko32213.unusualprehistory.common.item.armor.TyrantsCrownItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.modPrefix;

public class TyrantsCrownRenderer extends GeoArmorRenderer<TyrantsCrownItem> {
    public TyrantsCrownRenderer() {
        super(new DefaultedItemGeoModel<>(modPrefix("armor/tyrants_crown")));
    }
}
