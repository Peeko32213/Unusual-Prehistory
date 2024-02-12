package com.peeko32213.unusualprehistory.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.client.model.KimmeridgebrachypteraeschnidiumModel;
import com.peeko32213.unusualprehistory.common.entity.EntityKimmeridgebrachypteraeschnidium;
import com.peeko32213.unusualprehistory.common.entity.EntityTyrannosaurusRex;
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
        EntityKimmeridgebrachypteraeschnidium.Pattern pattern = EntityKimmeridgebrachypteraeschnidium.getVariant();
        KimmeridgebrachypteraeschnidiumModel entityModel = new KimmeridgebrachypteraeschnidiumModel();

        ResourceLocation resourceLocation = switch (pattern) {
            case COLORED_BODY -> COLORED_BODY;
            case COLORED_HEAD -> COLORED_HEAD;
            case COLORED_LEG -> COLORED_LEG;
            case COLORED_BUTT -> COLORED_BUTT;
        };

        float[] fs = EntityKimmeridgebrachypteraeschnidium.getPatternColor().getTextureDiffuseColors();
        coloredCutoutModelCopyLayerRender(this.getParentModel(), entityModel, resourceLocation, poseStack, multiBufferSource, i, EntityKimmeridgebrachypteraeschnidium, f, g, j, k, l, h, fs[0], fs[1], fs[2]);
    }


}
