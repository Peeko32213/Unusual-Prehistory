package com.peeko32213.unusualprehistory.client.render.prehistoric;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.peeko32213.unusualprehistory.client.model.entity.PalaeophisPartModel;
import com.peeko32213.unusualprehistory.common.entity.*;
import com.peeko32213.unusualprehistory.common.entity.part.PalaeophisPartEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Locale;

public class PalaeophisPartRender extends GeoEntityRenderer<PalaeophisPartEntity> {
    public PalaeophisPartRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PalaeophisPartModel());
    }

    @Override
    protected void applyRotations(PalaeophisPartEntity entity, PoseStack stack, float ageInTicks, float rotationYaw, float partialTickTime) {
        float newYaw = entity.yHeadRot;
        //if (this.isShaking(entity)) {
        //    newYaw += (float)(Math.cos((double)entity.tickCount * 3.25D) * Math.PI * (double)0.4F);
        //}

        Pose pose = entity.getPose();
        if (pose != Pose.SLEEPING) {
            //   stack.mulPose(Vector3f.YP.rotationDegrees(180.0F - yawIn));
            stack.mulPose(Axis.YP.rotationDegrees(180.0F - newYaw));
            stack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
        }

        if (entity.deathTime > 0) {
            float f = ((float)entity.deathTime + partialTickTime - 1.0F) / 20.0F * 1.6F;
            f = Mth.sqrt(f);
            if (f > 1.0F) {
                f = 1.0F;
            }

            stack.mulPose(Axis.ZP.rotationDegrees(f * 90F));
        } else if (entity.hasCustomName()) {
            String s = ChatFormatting.stripFormatting(entity.getName().getString());
            if (("Dinnerbone".equals(s) || "Grumm".equals(s))) {
                stack.translate(0.0D, entity.getBbHeight() + 0.1F, 0.0D);
                stack.mulPose(Axis.ZP.rotationDegrees(180.0F));
            }
        }
    }

    public boolean shouldShowName(PalaeophisPartEntity entity) {
        return super.shouldShowName(entity) && (entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity);
    }
}