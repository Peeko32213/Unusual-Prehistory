package com.peeko32213.unusualprehistory.client.render.prehistoric;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.render.layer.RideLayer;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.OviraptorEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Locale;

public class PrehistoricRenderer<T extends PrehistoricEntity> extends GeoEntityRenderer<T> {
    private int currentTick = -1;

    public PrehistoricRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> modelProvider) {
        super(renderManager, modelProvider);
        addRenderLayer(new <PrehistoricEntity>RideLayer(this));
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

    @Override
    public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack, T animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
        super.scaleModelForRender(widthScale, heightScale, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
        if (animatable.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }
        if (animatable instanceof OviraptorEntity oviraptor) {
            if (oviraptor.hasCustomName() && "gigantoraptor".equals(oviraptor.getName().getString().toLowerCase(Locale.ROOT)) && !oviraptor.isBaby()) {
                poseStack.scale(2F, 2F, 2F);
            }
        }
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public void renderFinal(PoseStack poseStack, T animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.renderFinal(poseStack, animatable, model, bufferSource, buffer, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        if (this.currentTick < 0 || this.currentTick != animatable.tickCount) {
            this.currentTick = animatable.tickCount;
        }
    }
}
