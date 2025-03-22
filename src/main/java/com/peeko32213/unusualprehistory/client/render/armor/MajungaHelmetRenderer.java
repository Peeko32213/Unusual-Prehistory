package com.peeko32213.unusualprehistory.client.render.armor;

import com.peeko32213.unusualprehistory.common.item.armor.MajungasaurusHelmetItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class MajungaHelmetRenderer extends GeoArmorRenderer<MajungasaurusHelmetItem> {
    public MajungaHelmetRenderer() {
        super(new DefaultedItemGeoModel<>(prefix("armor/majunga_helmet")));
    }
}
