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

public class KimmeridgebrachypteraeschnidiumWingLayer extends GeoRenderLayer<EntityKimmeridgebrachypteraeschnidium> {
    private static final ResourceLocation WING = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/kimmer/kimmeridgebrachypteraeschnidium_wing_layer.png");
    private static final ResourceLocation MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/kimmeridgebrachypteraeschnidium.geo.json");

    public KimmeridgebrachypteraeschnidiumWingLayer(GeoRenderer<EntityKimmeridgebrachypteraeschnidium> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, EntityKimmeridgebrachypteraeschnidium entityLivingBaseIn, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        RenderType cameo = RenderType.entityCutout(WING);
        getRenderer().reRender(this.getGeoModel().getBakedModel(MODEL), poseStack, bufferSource, entityLivingBaseIn, renderType,
                bufferSource.getBuffer(cameo), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                1, 1, 1, 1);
    }


}
