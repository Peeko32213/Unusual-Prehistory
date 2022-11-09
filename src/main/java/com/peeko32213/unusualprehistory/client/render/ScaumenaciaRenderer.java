package com.peeko32213.unusualprehistory.client.render;


import com.peeko32213.unusualprehistory.client.model.AmmoniteModel;
import com.peeko32213.unusualprehistory.client.model.ScaumenaciaModel;
import com.peeko32213.unusualprehistory.common.entity.EntityAmmonite;
import com.peeko32213.unusualprehistory.common.entity.EntityScaumenacia;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ScaumenaciaRenderer extends GeoEntityRenderer<EntityScaumenacia> {

    public ScaumenaciaRenderer(EntityRendererProvider.Context context) {
        super(context, new ScaumenaciaModel());
    }
}
