package com.peeko32213.unusualprehistory.client.render.egg;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.model.entity.egg.PrehistoricEggModel;
import com.peeko32213.unusualprehistory.common.entity.custom.eggs.PrehistoricEggEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.part.LeedsichthysPartEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PrehistoricEggRenderer extends GeoEntityRenderer<PrehistoricEggEntity> {

    public PrehistoricEggRenderer(EntityRendererProvider.Context renderManager, GeoModel<PrehistoricEggEntity> modelProvider) {
        super(renderManager, modelProvider);
        this.addRenderLayer(new PrehistoricEggBaseColorLayer(this));
        this.addRenderLayer(new PrehistoricEggSpotColorLayer(this));
        this.addRenderLayer(new PrehistoricEggCrackLayer(this));
    }

    @Override
    public RenderType getRenderType(PrehistoricEggEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

    @Override
    public void preRender(PoseStack stackIn, PrehistoricEggEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(stackIn, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public boolean shouldShowName(@NotNull PrehistoricEggEntity entity) {
        return super.shouldShowName(entity) && (entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity);
    }
}
