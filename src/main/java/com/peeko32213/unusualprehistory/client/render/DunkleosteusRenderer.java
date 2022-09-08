package com.peeko32213.unusualprehistory.client.render;


import com.peeko32213.unusualprehistory.client.model.AmmoniteModel;
import com.peeko32213.unusualprehistory.client.model.DunkleosteusModel;
import com.peeko32213.unusualprehistory.common.entity.EntityAmmonite;
import com.peeko32213.unusualprehistory.common.entity.EntityDunkleosteus;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DunkleosteusRenderer extends GeoEntityRenderer<EntityDunkleosteus> {

    public DunkleosteusRenderer(EntityRendererProvider.Context context) {
        super(context, new DunkleosteusModel());
    }
}
