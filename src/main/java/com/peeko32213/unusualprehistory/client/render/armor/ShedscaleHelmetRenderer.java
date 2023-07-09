package com.peeko32213.unusualprehistory.client.render.armor;

import com.peeko32213.unusualprehistory.client.model.armor.MajungaHelmetModel;
import com.peeko32213.unusualprehistory.client.model.armor.ShedscaleHelmetModel;
import com.peeko32213.unusualprehistory.common.item.armor.ItemMajungaHelmet;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleHelmet;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class ShedscaleHelmetRenderer extends GeoArmorRenderer<ItemShedscaleHelmet> {
    public ShedscaleHelmetRenderer() {
        super(new ShedscaleHelmetModel());

        this.headBone = "armorHead";
    }
}
