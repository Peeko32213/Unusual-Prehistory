package com.peeko32213.unusualprehistory.client.render.prehistoric;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.peeko32213.unusualprehistory.client.model.entity.prehistoric.aquatic.XiphactinusModel;
import com.peeko32213.unusualprehistory.client.render.base.PrehistoricRenderer;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.XiphactinusEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class XiphactinusRenderer extends PrehistoricRenderer<XiphactinusEntity> {

    public XiphactinusRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new XiphactinusModel());
    }

    @Override
    protected void applyRotations(XiphactinusEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        if (animatable.isInWater()){
            poseStack.mulPose(Axis.ZP.rotationDegrees(animatable.currentRoll * 360 / 4));
        }
    }
}
