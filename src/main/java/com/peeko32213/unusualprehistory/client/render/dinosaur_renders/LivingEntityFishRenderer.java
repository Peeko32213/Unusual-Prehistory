package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class LivingEntityFishRenderer<T extends LivingEntity & GeoAnimatable> extends GeoEntityRenderer<T> {

    public LivingEntityFishRenderer(EntityRendererProvider.Context context, GeoModel<T> model) {
        super(context, model);
    }

    public RenderType getRenderType(T animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityCutoutNoCullZOffset(getTextureLocation(animatable));
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.scale(-1,-1,-1);
    }
    @Override
    protected void applyRotations(T animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        float f = 4.3F * Mth.sin(0.6F * ageInTicks);
        poseStack.mulPose(Axis.YP.rotationDegrees(f));
        if (!animatable.isInWater()) {
            poseStack.translate(0.1F, 0.1F, 0.1F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        }
    }
}
