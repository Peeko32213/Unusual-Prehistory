package com.peeko32213.unusualprehistory.client.render.prehistoric;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.peeko32213.unusualprehistory.client.model.entity.prehistoric.flying.PterodaustroModel;
import com.peeko32213.unusualprehistory.client.model.entity.prehistoric.flying.TelecrexModel;
import com.peeko32213.unusualprehistory.client.render.base.PrehistoricRenderer;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying.PterodaustroEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying.TelecrexEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class PterodaustroRenderer extends PrehistoricRenderer<PterodaustroEntity> {

    public PterodaustroRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PterodaustroModel());
    }

    @Override
    protected void applyRotations(PterodaustroEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        if (animatable.isFlying() && !animatable.onGround()){
            poseStack.mulPose(Axis.ZP.rotationDegrees(animatable.currentRoll * 360 / 3));
        }
    }
}
