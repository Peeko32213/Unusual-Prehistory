package com.peeko32213.unusualprehistory.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.peeko32213.unusualprehistory.client.ClientUtils;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.RenderUtils;

public class RideLayer<T extends PrehistoricEntity> extends GeoRenderLayer<T> {

    public RideLayer(GeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void renderForBone(PoseStack poseStack, T entity, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer bufferIn, float partialTicks, int packedLight, int packedOverlay) {
        super.renderForBone(poseStack, entity, bone, renderType, bufferSource, bufferIn, partialTicks, packedLight, packedOverlay);
        if(entity instanceof PrehistoricEntity) {
            if (entity.isVehicle() && bone.getName().equals("Rider")) {
                for (Entity passenger : entity.getPassengers()) {
                    if (passenger == Minecraft.getInstance().player && Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
                        continue;
                    }
                    ClientUtils.releaseRenderingEntity(passenger.getUUID());
                    poseStack.pushPose();
                    RenderUtils.translateToPivotPoint(poseStack, bone);
                    RenderUtils.translateMatrixToBone(poseStack, bone);
                    rotateMatrixAroundBone(poseStack, bone);
                    poseStack.mulPose(Axis.YP.rotationDegrees(passenger.getYRot()));
                    poseStack.mulPose(Axis.YP.rotationDegrees(180F));
                    poseStack.translate(0, -1F, 0);
                    ////RenderUtils.translateAndRotateMatrixForBone(poseStack, bone);
                    ////RenderUtils.rotateMatrixAroundBone(poseStack, bone);
//
                    renderPassenger(passenger, 0, 0, 0, 0, partialTicks, poseStack, bufferSource, packedLight);

                    bufferIn = bufferSource.getBuffer(renderType);
                    poseStack.popPose();
                    ClientUtils.blockRenderingEntity(passenger.getUUID());
                }
            }

            if (bone.getName().equals("Passenger")) {
                for (Entity passenger : entity.getPassengers()) {
                        ClientUtils.releaseRenderingEntity(passenger.getUUID());
                        poseStack.pushPose();
                        RenderUtils.translateToPivotPoint(poseStack, bone);
                        RenderUtils.translateMatrixToBone(poseStack, bone);
                        rotateMatrixAroundBone(poseStack, bone);
                        poseStack.mulPose(Axis.YP.rotationDegrees(passenger.getYRot()));
                        poseStack.mulPose(Axis.YP.rotationDegrees(180F));
                        poseStack.translate(0, -passenger.getBbHeight(), 0);
                        ////RenderUtils.translateAndRotateMatrixForBone(poseStack, bone);
                        ////RenderUtils.rotateMatrixAroundBone(poseStack, bone);

                        renderPassenger(passenger, 0, 0, 0, 0, partialTicks, poseStack, bufferSource, packedLight);

                        bufferIn = bufferSource.getBuffer(renderType);
                        poseStack.popPose();
                        ClientUtils.blockRenderingEntity(passenger.getUUID());
                }
            }
        }

    }
    public static void rotateMatrixAroundBone(PoseStack poseStack, CoreGeoBone bone) {
        if (bone.getRotZ() != 0)
            poseStack.mulPose(Axis.ZP.rotation(bone.getRotZ()));

        if (bone.getRotX() != 0)
            poseStack.mulPose(Axis.XP.rotation(bone.getRotX()));
    }

    public static <E extends Entity> void renderPassenger(E entityIn, double x, double y, double z, float yaw, float partialTicks, PoseStack matrixStack, MultiBufferSource bufferIn, int packedLight) {
        EntityRenderer<? super E> render = null;
        EntityRenderDispatcher manager = Minecraft.getInstance().getEntityRenderDispatcher();
        try {
            render = manager.getRenderer(entityIn);

            if (render != null) {
                try {
                    render.render(entityIn, yaw, partialTicks, matrixStack, bufferIn, packedLight);
                } catch (Throwable throwable1) {
                    throw new ReportedException(CrashReport.forThrowable(throwable1, "Rendering entity in world"));
                }
            }
        } catch (Throwable throwable3) {
            CrashReport crashreport = CrashReport.forThrowable(throwable3, "Rendering entity in world");
            CrashReportCategory crashreportcategory = crashreport.addCategory("Entity being rendered");
            entityIn.fillCrashReportCategory(crashreportcategory);
            CrashReportCategory crashreportcategory1 = crashreport.addCategory("Renderer details");
            crashreportcategory1.setDetail("Assigned renderer", render);
            crashreportcategory1.setDetail("Rotation", Float.valueOf(yaw));
            crashreportcategory1.setDetail("Delta", Float.valueOf(partialTicks));
            throw new ReportedException(crashreport);
        }
    }
}
