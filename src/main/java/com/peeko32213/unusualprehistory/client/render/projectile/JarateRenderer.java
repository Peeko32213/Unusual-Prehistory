package com.peeko32213.unusualprehistory.client.render.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.model.DefaultModel;
import com.peeko32213.unusualprehistory.client.model.ModelLocations;
import com.peeko32213.unusualprehistory.common.entity.msc.projectile.JarateEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class JarateRenderer extends GeoEntityRenderer<JarateEntity> {
    public JarateRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultModel<>(ModelLocations.JARATE));
    }

    public RenderType getRenderType(JarateEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    protected void applyRotations(JarateEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        float f = 4.3F * Mth.sin(0.6F * ageInTicks);
        if (animatable.getOwner() != null) {
            super.applyRotations(animatable, poseStack, ageInTicks, animatable.getOwner().getYRot(), partialTick);
        }
    }

}