package com.peeko32213.unusualprehistory.client.render.armor;

import com.peeko32213.unusualprehistory.client.model.armor.ShedscaleBootsModel;
import com.peeko32213.unusualprehistory.client.model.armor.ShedscaleLeggingsModel;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleBoots;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleLeggings;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class ShedscaleBootsRenderer extends GeoArmorRenderer<ItemShedscaleBoots> {
    public ShedscaleBootsRenderer() {
        super(new ShedscaleBootsModel());

        this.rightBootBone = "armorRightBoot";
        this.leftBootBone = "armorLeftBoot";

    }
}
