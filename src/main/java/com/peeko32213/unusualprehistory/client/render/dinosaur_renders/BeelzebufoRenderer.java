package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.peeko32213.unusualprehistory.client.model.BeelzebufoModel;
import com.peeko32213.unusualprehistory.client.render.layer.BeelzebufoSaddleLayer;
import com.peeko32213.unusualprehistory.common.entity.EntityBeelzebufo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BeelzebufoRenderer extends GeoEntityRenderer<EntityBeelzebufo> {

    public BeelzebufoRenderer(EntityRendererProvider.Context context) {
        super(context, new BeelzebufoModel());
        this.addLayer(new BeelzebufoSaddleLayer(this));
    }
}
