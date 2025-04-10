package com.peeko32213.unusualprehistory.client.render.prehistoric;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.peeko32213.unusualprehistory.client.model.entity.prehistoric.aquatic.XiphactinusModel;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.XiphactinusEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class XiphactinusRenderer extends GeoEntityRenderer<XiphactinusEntity> {

    public XiphactinusRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new XiphactinusModel());
    }

    @Override
    public void render(XiphactinusEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLightIn) {
        if(entity.isBaby()) {
            poseStack.scale(0.4F, 0.4F, 0.4F);
        }
        else {
            poseStack.scale(1.0F, 1.0F, 1.0F);
        }
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLightIn);
    }

    @Override
    protected void applyRotations(XiphactinusEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        if (animatable.isInWater()){
            poseStack.mulPose(Axis.ZP.rotationDegrees(animatable.currentRoll * 360 / 4));
        }
    }
}
