package com.peeko32213.unusualprehistory.client.render.prehistoric;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.peeko32213.unusualprehistory.client.model.entity.prehistoric.aquatic.StethacanthusModel;
import com.peeko32213.unusualprehistory.client.render.base.PrehistoricRenderer;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.StethacanthusEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class StethacanthusRenderer extends PrehistoricRenderer<StethacanthusEntity> {

    public StethacanthusRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new StethacanthusModel());
    }

    @Override
    protected void applyRotations(StethacanthusEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        if (animatable.isInWater()){
            poseStack.mulPose(Axis.ZP.rotationDegrees(animatable.currentRoll * 360 / 4));
        }
    }
}
