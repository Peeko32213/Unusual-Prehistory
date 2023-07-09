package com.peeko32213.unusualprehistory.client.render.armor;

import com.peeko32213.unusualprehistory.client.model.armor.ShedscaleHelmetModel;
import com.peeko32213.unusualprehistory.client.model.armor.ShedscaleLeggingsModel;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleHelmet;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleLeggings;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class ShedscaleLeggingsRenderer extends GeoArmorRenderer<ItemShedscaleLeggings> {
    public ShedscaleLeggingsRenderer() {
        super(new ShedscaleLeggingsModel());

        this.rightLegBone = "armorRightBoot";
        this.leftLegBone = "armorLeftBoot";

    }
}
