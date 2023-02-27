package com.peeko32213.unusualprehistory.client.render;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.model.BrachiosaurusTeenModel;
import com.peeko32213.unusualprehistory.common.entity.baby.EntityBrachiosaurusTeen;
import com.peeko32213.unusualprehistory.common.entity.part.EntityBrachiosaurusTeenPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BrachiosaurusTeenRenderer extends GeoEntityRenderer<EntityBrachiosaurusTeen> {

    public BrachiosaurusTeenRenderer(EntityRendererProvider.Context context) {
        super(context, new BrachiosaurusTeenModel());
    }

    public boolean shouldRender(EntityBrachiosaurusTeen livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
        if (super.shouldRender(livingEntityIn, camera, camX, camY, camZ)) {
            return true;
        } else {
            for (EntityBrachiosaurusTeenPart part : livingEntityIn.allParts) {
                if (camera.isVisible(part.getBoundingBox())) {
                    return true;
                }
            }
            return false;
        }
    }


}
