package com.peeko32213.unusualprehistory.client.render.armor;

import com.peeko32213.unusualprehistory.common.item.armor.MajungasaurusHelmetItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.modPrefix;

public class MajungaHelmetRenderer extends GeoArmorRenderer<MajungasaurusHelmetItem> {
    public MajungaHelmetRenderer() {
        super(new DefaultedItemGeoModel<>(modPrefix("armor/majunga_helmet")));
    }
}
