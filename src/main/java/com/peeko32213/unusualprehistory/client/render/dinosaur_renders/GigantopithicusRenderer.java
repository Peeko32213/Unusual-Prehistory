package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.peeko32213.unusualprehistory.client.model.GigantopithicusModel;
import com.peeko32213.unusualprehistory.common.entity.EntityGigantopithicus;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GigantopithicusRenderer extends GeoEntityRenderer<EntityGigantopithicus> {

    public GigantopithicusRenderer(EntityRendererProvider.Context context) {
        super(context, new GigantopithicusModel());
    }

}
