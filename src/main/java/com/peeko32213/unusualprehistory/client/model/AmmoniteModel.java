package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityAmmonite;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class AmmoniteModel extends DefaultedEntityGeoModel<EntityAmmonite>
{
    public AmmoniteModel() {
        super(prefix("ammonite"));
    }

}

