package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.peeko32213.unusualprehistory.client.model.StethacanthusModel;
import com.peeko32213.unusualprehistory.common.entity.EntityStethacanthus;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class StethacanthusRenderer extends GeoEntityRenderer<EntityStethacanthus> {

    public StethacanthusRenderer(EntityRendererProvider.Context context) {
        super(context, new StethacanthusModel());
    }
}
