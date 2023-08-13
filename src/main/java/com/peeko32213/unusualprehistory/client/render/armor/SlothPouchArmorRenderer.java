package com.peeko32213.unusualprehistory.client.render.armor;

import com.peeko32213.unusualprehistory.client.model.armor.ShedscaleArmorModel;
import com.peeko32213.unusualprehistory.client.model.armor.SlothPouchArmorModel;
import com.peeko32213.unusualprehistory.common.item.armor.ItemSlothPouchArmor;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleArmor;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class SlothPouchArmorRenderer extends GeoArmorRenderer<ItemSlothPouchArmor> {
    public SlothPouchArmorRenderer() {
        super(new SlothPouchArmorModel());
        this.bodyBone = "armorBody";
    }
}
