package com.peeko32213.unusualprehistory.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying.KimmeridgebrachypteraeschnidiumEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class KimmeridgebrachypteraeschnidiumBaseLayer extends GeoRenderLayer<KimmeridgebrachypteraeschnidiumEntity> {
    private static final ResourceLocation COLORED_BODY = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/kimmer/kimmeridgebrachypteraeschnidium_pattern_a.png");
    private static final ResourceLocation MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/kimmeridgebrachypteraeschnidium.geo.json");

    public KimmeridgebrachypteraeschnidiumBaseLayer(GeoRenderer<KimmeridgebrachypteraeschnidiumEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, KimmeridgebrachypteraeschnidiumEntity entityLivingBaseIn, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        KimmeridgebrachypteraeschnidiumEntity.Pattern pattern = entityLivingBaseIn.getPattern();

        RenderType cameo = RenderType.entityCutout(COLORED_BODY);

        float[] fs = entityLivingBaseIn.getBaseColor().getTextureDiffuseColors();
        getRenderer().reRender(this.getGeoModel().getBakedModel(MODEL), poseStack, bufferSource, entityLivingBaseIn, renderType, bufferSource.getBuffer(cameo), partialTick, packedLight, OverlayTexture.NO_OVERLAY, fs[0], fs[1], fs[2], 1);
    }

}
