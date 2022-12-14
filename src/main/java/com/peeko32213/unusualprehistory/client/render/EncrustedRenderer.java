package com.peeko32213.unusualprehistory.client.render;


import com.peeko32213.unusualprehistory.client.model.BeelzebufoModel;
import com.peeko32213.unusualprehistory.client.model.EncrustedModel;
import com.peeko32213.unusualprehistory.client.render.layer.BeelzebufoSaddleLayer;
import com.peeko32213.unusualprehistory.common.entity.EntityBeelzebufo;
import com.peeko32213.unusualprehistory.common.entity.EntityEncrusted;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EncrustedRenderer extends GeoEntityRenderer<EntityEncrusted> {

    public EncrustedRenderer(EntityRendererProvider.Context context) {
        super(context, new EncrustedModel());
    }
}
