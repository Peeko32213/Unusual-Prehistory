package com.peeko32213.unusualprehistory.client.render.armor;

import com.peeko32213.unusualprehistory.client.model.armor.AustroBootsModel;
import com.peeko32213.unusualprehistory.common.item.armor.ItemAustroBoots;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class AustroBootsRenderer extends GeoArmorRenderer<ItemAustroBoots> {
    public AustroBootsRenderer() {
        super(new AustroBootsModel());

        this.leftBootBone = "armorLeftBoot";
        this.rightBootBone = "armorRightBoot";

    }
}
