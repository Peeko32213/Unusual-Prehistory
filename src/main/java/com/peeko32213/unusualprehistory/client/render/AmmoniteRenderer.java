package com.peeko32213.unusualprehistory.client.render;


import com.peeko32213.unusualprehistory.client.model.AmmoniteModel;
import com.peeko32213.unusualprehistory.common.entity.EntityAmmonite;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class AmmoniteRenderer extends GeoEntityRenderer<EntityAmmonite> {

    public AmmoniteRenderer(EntityRendererProvider.Context context) {
        super(context, new AmmoniteModel());
    }
}
