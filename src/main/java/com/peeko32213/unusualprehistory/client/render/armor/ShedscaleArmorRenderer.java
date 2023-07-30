package com.peeko32213.unusualprehistory.client.render.armor;

import com.peeko32213.unusualprehistory.client.model.armor.ShedscaleArmorModel;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemshedScaleArmor;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class ShedscaleArmorRenderer extends GeoArmorRenderer<ItemshedScaleArmor> {
    public ShedscaleArmorRenderer() {
        super(new ShedscaleArmorModel());
        this.rightBootBone = "armorRightBoot";
        this.leftBootBone = "armorLeftBoot";
        this.rightLegBone = "armorRightLeg";
        this.leftLegBone = "armorLeftLeg";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";
        this.headBone = "armorHead";
    }
}
