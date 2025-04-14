package com.peeko32213.unusualprehistory.client.render.prehistoric;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.peeko32213.unusualprehistory.client.model.entity.prehistoric.aquatic.PanacanthocarisModel;
import com.peeko32213.unusualprehistory.client.render.base.PrehistoricRenderer;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.PanacanthocarisEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class PanacanthocarisRenderer extends PrehistoricRenderer<PanacanthocarisEntity> {

    public PanacanthocarisRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PanacanthocarisModel());
    }

    @Override
    protected void applyRotations(PanacanthocarisEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        if (animatable.isInWater()){
            poseStack.mulPose(Axis.ZP.rotationDegrees(animatable.currentRoll * 360 / 4));
        }
    }
}
