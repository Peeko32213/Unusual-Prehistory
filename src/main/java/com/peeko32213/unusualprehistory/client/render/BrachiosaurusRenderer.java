package com.peeko32213.unusualprehistory.client.render;


import com.peeko32213.unusualprehistory.client.model.BrachiosaurusModel;
import com.peeko32213.unusualprehistory.client.render.layer.BrachiosaurusSaddleLayer;
import com.peeko32213.unusualprehistory.common.entity.EntityBrachiosaurus;
import com.peeko32213.unusualprehistory.common.entity.msc.part.EntityBrachiosaurusPart;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BrachiosaurusRenderer extends GeoEntityRenderer<EntityBrachiosaurus> {

    public BrachiosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new BrachiosaurusModel());
        this.addLayer(new BrachiosaurusSaddleLayer(this));
    }

    public boolean shouldRender(EntityBrachiosaurus livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
        if (super.shouldRender(livingEntityIn, camera, camX, camY, camZ)) {
            return true;
        } else {
            for (EntityBrachiosaurusPart part : livingEntityIn.allParts) {
                if (camera.isVisible(part.getBoundingBox())) {
                    return true;
                }
            }
            return false;
        }
    }


}
