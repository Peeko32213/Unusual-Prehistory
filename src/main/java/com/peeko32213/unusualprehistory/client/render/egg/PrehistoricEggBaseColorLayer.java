package com.peeko32213.unusualprehistory.client.render.egg;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.eggs.PrehistoricEggEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class PrehistoricEggBaseColorLayer extends GeoRenderLayer<PrehistoricEggEntity> {
    private static final ResourceLocation EGG_BASE_COLOR = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/kimmer/kimmeridgebrachypteraeschnidium_pattern_a.png");

    public PrehistoricEggBaseColorLayer(GeoRenderer<PrehistoricEggEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, PrehistoricEggEntity entityLivingBaseIn, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        ResourceLocation MODEL = entityLivingBaseIn.getModel();
        ResourceLocation BASE = entityLivingBaseIn.getTexture();
        RenderType cameo = RenderType.entityCutout(BASE);
        float[] fs = entityLivingBaseIn.getEggColorFromVector(entityLivingBaseIn.getEggBaseColor());
        getRenderer().reRender(this.getGeoModel().getBakedModel(MODEL), poseStack, bufferSource, entityLivingBaseIn, renderType, bufferSource.getBuffer(cameo), partialTick, packedLight, OverlayTexture.NO_OVERLAY, fs[0], fs[1], fs[2], 1);
    }
}
