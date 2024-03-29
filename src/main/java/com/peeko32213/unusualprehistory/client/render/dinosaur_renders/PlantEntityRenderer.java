package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PlantEntityRenderer<T extends LivingEntity & IAnimatable> extends GeoEntityRenderer<T> {
    private float scale;
    public PlantEntityRenderer(EntityRendererProvider.Context context, AnimatedGeoModel<T> model, float scale) {
        super(context, model);
        this.scale = scale;
    }

    @Override
    public void renderEarly(T animatable, PoseStack poseStack, float partialTick, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, partialTicks);
        poseStack.scale(this.scale, this.scale, this.scale);
    }

    @Override
    public RenderType getRenderType(T animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

    protected int getBlockLightLevel(T pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    protected int getSkyLightLevel(T pEntity, BlockPos pPos) {
        return 15;
    }
}
