package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.peeko32213.unusualprehistory.client.model.EryonModel;
import com.peeko32213.unusualprehistory.common.entity.EntityEryon;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EryonRenderer extends GeoEntityRenderer<EntityEryon> {

    public EryonRenderer(EntityRendererProvider.Context context) {
        super(context, new EryonModel());
    }

}
