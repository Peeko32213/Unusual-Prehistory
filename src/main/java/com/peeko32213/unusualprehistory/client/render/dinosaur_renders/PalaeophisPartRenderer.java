package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.peeko32213.unusualprehistory.client.model.PalaeophisModel;
import com.peeko32213.unusualprehistory.client.model.PalaeophisPartModel;
import com.peeko32213.unusualprehistory.common.entity.EntityPalaeophis;
import com.peeko32213.unusualprehistory.common.entity.msc.part.EntityPalaeophisPart;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PalaeophisPartRenderer extends GeoEntityRenderer<EntityPalaeophisPart> {

    public PalaeophisPartRenderer(EntityRendererProvider.Context context) {
        super(context, new PalaeophisPartModel());
    }


}
