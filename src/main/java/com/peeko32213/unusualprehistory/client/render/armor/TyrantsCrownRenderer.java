package com.peeko32213.unusualprehistory.client.render.armor;

import com.peeko32213.unusualprehistory.client.model.armor.MajungaHelmetModel;
import com.peeko32213.unusualprehistory.client.model.armor.TyrantsCrownModel;
import com.peeko32213.unusualprehistory.common.item.armor.ItemMajungaHelmet;
import com.peeko32213.unusualprehistory.common.item.armor.ItemTyrantsCrown;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class TyrantsCrownRenderer extends GeoArmorRenderer<ItemTyrantsCrown> {
    public TyrantsCrownRenderer() {
        super(new TyrantsCrownModel());

        this.headBone = "armorHead";
    }
}
