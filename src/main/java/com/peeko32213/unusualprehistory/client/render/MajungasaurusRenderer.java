package com.peeko32213.unusualprehistory.client.render;


import com.peeko32213.unusualprehistory.client.model.MajungasaurusModel;
import com.peeko32213.unusualprehistory.client.model.StethacanthusModel;
import com.peeko32213.unusualprehistory.common.entity.EntityMajungasaurus;
import com.peeko32213.unusualprehistory.common.entity.EntityStethacanthus;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MajungasaurusRenderer extends GeoEntityRenderer<EntityMajungasaurus> {

    public MajungasaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new MajungasaurusModel());
    }
}
