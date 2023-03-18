package com.peeko32213.unusualprehistory.client.render.trail;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.trail.EntityTrail;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class EntityTrailRenderer extends EntityRenderer<EntityTrail> {
    public EntityTrailRenderer(EntityRendererProvider.Context context) {
        super(context);
    }
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(UnusualPrehistory.MODID,"textures/entity/trails/brachi_footprint.png");
    @Override
    public void render(EntityTrail pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.pushPose();
        pMatrixStack.scale((float)pEntity.getScale(),(float)pEntity.getScale(),(float)pEntity.getScale());
        //pMatrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        pMatrixStack.mulPose(Vector3f.YP.rotation(pEntity.getAngle()));
        //pMatrixStack.mulPose(Vector3f.YP.rotation(180));
        pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(90));

        PoseStack.Pose posestack$pose = pMatrixStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE_LOCATION));
        vertex(vertexconsumer, matrix4f, matrix3f, pPackedLight, 0.0F, 0, 0, 1);
        vertex(vertexconsumer, matrix4f, matrix3f, pPackedLight, 1.0F, 0, 1, 1);
        vertex(vertexconsumer, matrix4f, matrix3f, pPackedLight, 1.0F, 1, 1, 0);
        vertex(vertexconsumer, matrix4f, matrix3f, pPackedLight, 0.0F, 1, 0, 0);
        pMatrixStack.popPose();
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    private static void vertex(VertexConsumer p_114712_, Matrix4f p_114713_, Matrix3f p_114714_, int p_114715_, float p_114716_, int p_114717_, int p_114718_, int p_114719_) {
        p_114712_.vertex(p_114713_, p_114716_ - 0.5F, (float) p_114717_ - 0.5F, 0.0F).color(255, 255, 255, 255).uv((float) p_114718_, (float) p_114719_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_114715_).normal(p_114714_, 0.0F, 1.0F, 0.0F).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(EntityTrail p_114482_) {
        return TEXTURE_LOCATION;
    }
}
