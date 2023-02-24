package com.peeko32213.unusualprehistory.client.render.tool;


import com.peeko32213.unusualprehistory.client.model.tool.TrikeShieldModel;
import com.peeko32213.unusualprehistory.common.item.tool.ItemTrikeShield;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class TrikeShieldRenderer extends GeoItemRenderer<ItemTrikeShield> {
    public TrikeShieldRenderer() {
        super(new TrikeShieldModel());
    }
}
