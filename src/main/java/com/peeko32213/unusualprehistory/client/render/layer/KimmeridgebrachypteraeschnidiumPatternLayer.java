package com.peeko32213.unusualprehistory.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityKimmeridgebrachypteraeschnidium;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class KimmeridgebrachypteraeschnidiumPatternLayer extends GeoRenderLayer<EntityKimmeridgebrachypteraeschnidium> {
    private static final ResourceLocation COLORED_PATTERNS = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/kimmer/kimmeridgebrachypteraeschnidium_pattern_a_1.png");
    private static final ResourceLocation MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/kimmeridgebrachypteraeschnidium.geo.json");

    public KimmeridgebrachypteraeschnidiumPatternLayer(GeoRenderer<EntityKimmeridgebrachypteraeschnidium> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, EntityKimmeridgebrachypteraeschnidium entityLivingBaseIn, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        EntityKimmeridgebrachypteraeschnidium.Pattern pattern = entityLivingBaseIn.getVariant();


        RenderType cameo = RenderType.entityCutout(COLORED_PATTERNS);

        float[] fs = entityLivingBaseIn.getPatternColor().getTextureDiffuseColors();
        getRenderer().reRender(this.getGeoModel().getBakedModel(MODEL), poseStack, bufferSource, entityLivingBaseIn, renderType, bufferSource.getBuffer(cameo), partialTick, packedLight, OverlayTexture.NO_OVERLAY, fs[0], fs[1], fs[2], 1);
    }

}
