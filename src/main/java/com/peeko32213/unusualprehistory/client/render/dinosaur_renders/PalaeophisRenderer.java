package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.peeko32213.unusualprehistory.client.model.PalaeophisModel;
import com.peeko32213.unusualprehistory.common.entity.EntityPalaeophis;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PalaeophisRenderer extends GeoEntityRenderer<EntityPalaeophis> {

    public PalaeophisRenderer(EntityRendererProvider.Context context) {
        super(context, new PalaeophisModel());
        //this.addLayer(new PaleolophisSheddingLayer(this));
    }

    public boolean shouldRender(EntityPalaeophis livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
        if (super.shouldRender(livingEntityIn, camera, camX, camY, camZ)) {
            return true;
        } else {
            //for (EntityPalaeophisPart part : livingEntityIn.snakeParts) {
            //    if (camera.isVisible(part.getBoundingBox())) {
            //        return true;
            //    }
            //}
            return false;
        }
    }

}
