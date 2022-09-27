package com.peeko32213.unusualprehistory.client.render.armor;

import com.peeko32213.unusualprehistory.client.model.armor.MajungaHelmetModel;
import com.peeko32213.unusualprehistory.common.item.armor.ItemMajungaHelmet;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class MajungaHelmetRenderer extends GeoArmorRenderer<ItemMajungaHelmet> {
    public MajungaHelmetRenderer() {
        super(new MajungaHelmetModel());

        this.headBone = "armorHead";
    }
}
