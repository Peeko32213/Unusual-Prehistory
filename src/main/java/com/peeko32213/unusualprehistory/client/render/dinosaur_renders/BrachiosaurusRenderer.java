package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.client.model.DefaultModel;
import com.peeko32213.unusualprehistory.client.model.ModelLocations;
import com.peeko32213.unusualprehistory.client.render.layer.BaseDinosaurSaddleLayer;
import com.peeko32213.unusualprehistory.common.entity.EntityBrachiosaurus;
import com.peeko32213.unusualprehistory.common.entity.msc.part.EntityBrachiosaurusPart;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BrachiosaurusRenderer extends GeoEntityRenderer<EntityBrachiosaurus> {
    private static final ResourceLocation OVERLAY = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/brachiosaurus_saddle.png");
    private static final ResourceLocation MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/brachiosaurus.geo.json");
    public BrachiosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultModel<>(ModelLocations.BRACHIOSAURUS));
        this.addRenderLayer(new BaseDinosaurSaddleLayer<>(this, OVERLAY, MODEL));
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
