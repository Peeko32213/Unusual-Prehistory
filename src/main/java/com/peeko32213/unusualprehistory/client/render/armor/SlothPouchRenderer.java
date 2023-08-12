package com.peeko32213.unusualprehistory.client.render.armor;

import com.peeko32213.unusualprehistory.client.model.armor.SlothPouchModel;
import com.peeko32213.unusualprehistory.common.item.armor.ItemSlothPouch;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class SlothPouchRenderer extends GeoArmorRenderer<ItemSlothPouch> {
    public SlothPouchRenderer() {
        super(new SlothPouchModel());

        this.bodyBone = "armorBody";
    }
}
