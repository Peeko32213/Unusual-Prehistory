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
    private static final ResourceLocation COLORED_BODY = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/kimmer/kimmeridgebrachypteraeschnidium_pattern_a_1.png");
    private static final ResourceLocation COLORED_HEAD = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/kimmer/kimmeridgebrachypteraeschnidium_pattern_a_2.png");
    private static final ResourceLocation COLORED_LEG = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/kimmer/kimmeridgebrachypteraeschnidium_pattern_a_3.png");
    private static final ResourceLocation COLORED_BUTT = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/kimmer/kimmeridgebrachypteraeschnidium_pattern_a_4.png");

    private static final ResourceLocation OVERLAY = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus_rex_eepy.png");
    private static final ResourceLocation MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/rex.geo.json");

    public KimmeridgebrachypteraeschnidiumPatternLayer(GeoRenderer<EntityKimmeridgebrachypteraeschnidium> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, EntityKimmeridgebrachypteraeschnidium entityLivingBaseIn, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        EntityKimmeridgebrachypteraeschnidium.Pattern pattern = entityLivingBaseIn.getVariant();

        ResourceLocation resourceLocation = switch (pattern) {
            case COLORED_BODY -> COLORED_BODY;
            case COLORED_HEAD -> COLORED_HEAD;
            case COLORED_LEG -> COLORED_LEG;
            case COLORED_BUTT -> COLORED_BUTT;
        };

        RenderType cameo = RenderType.entityCutout(resourceLocation);

        float[] fs = entityLivingBaseIn.getPatternColor().getTextureDiffuseColors();
        getRenderer().reRender(this.getGeoModel().getBakedModel(MODEL), poseStack, bufferSource, entityLivingBaseIn, renderType,
                bufferSource.getBuffer(cameo), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                fs[0], fs[1], fs[2], 1);
    }


}
