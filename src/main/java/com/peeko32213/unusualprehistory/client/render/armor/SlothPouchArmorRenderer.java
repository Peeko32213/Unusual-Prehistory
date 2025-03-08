package com.peeko32213.unusualprehistory.client.render.armor;

import com.peeko32213.unusualprehistory.common.item.armor.SlothPouchItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class SlothPouchArmorRenderer extends GeoArmorRenderer<SlothPouchItem> {
    public SlothPouchArmorRenderer() {
        super(new DefaultedItemGeoModel<>(prefix("armor/sloth_pouch_armor")));
    }
}
