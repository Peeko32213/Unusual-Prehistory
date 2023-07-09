package com.peeko32213.unusualprehistory.client.render.armor;

import com.peeko32213.unusualprehistory.client.model.armor.ShedscaleChestplateModel;
import com.peeko32213.unusualprehistory.client.model.armor.ShedscaleHelmetModel;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleChestplate;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleHelmet;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class ShedscaleChestplateRenderer extends GeoArmorRenderer<ItemShedscaleChestplate> {
    public ShedscaleChestplateRenderer() {
        super(new ShedscaleChestplateModel());

        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";

    }
}
