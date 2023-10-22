package com.peeko32213.unusualprehistory.client.render.armor;

import com.peeko32213.unusualprehistory.common.item.armor.ItemSlothPouchArmor;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class SlothPouchArmorRenderer extends GeoArmorRenderer<ItemSlothPouchArmor> {
    public SlothPouchArmorRenderer() {
        super(new DefaultedItemGeoModel<>(prefix("armor/sloth_pouch_armor")));
    }
}
