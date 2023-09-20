package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.peeko32213.unusualprehistory.client.model.PalaeophisModel;
import com.peeko32213.unusualprehistory.common.entity.EntityPalaeophis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PalaeophisRenderer extends GeoEntityRenderer<EntityPalaeophis> {

    public PalaeophisRenderer(EntityRendererProvider.Context context) {
        super(context, new PalaeophisModel());
    }
}
