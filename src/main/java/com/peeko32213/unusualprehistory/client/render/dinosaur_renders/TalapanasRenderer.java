package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.peeko32213.unusualprehistory.client.model.TalapanasModel;
import com.peeko32213.unusualprehistory.common.entity.EntityTalapanas;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class TalapanasRenderer extends GeoEntityRenderer<EntityTalapanas> {

    public TalapanasRenderer(EntityRendererProvider.Context context) {
        super(context, new TalapanasModel());
    }

}
