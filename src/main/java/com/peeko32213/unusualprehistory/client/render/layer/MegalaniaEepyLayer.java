package com.peeko32213.unusualprehistory.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityMegalania;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class MegalaniaEepyLayer extends GeoRenderLayer<EntityMegalania> {
    private static final ResourceLocation OVERLAY = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/megalania_resting.png");
    private static final ResourceLocation MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/megalania.geo.json");

    private static final ResourceLocation HOT_LOCATION = prefix("textures/entity/megalania_hot_resting.png");
    private static final ResourceLocation COLD_LOCATION = prefix("textures/entity/megalania_cold_resting.png");

    private static final ResourceLocation NETHER_LOCATION = prefix("textures/entity/megalania_nether_resting.png");

    public MegalaniaEepyLayer(GeoRenderer<EntityMegalania> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, EntityMegalania entityLivingBaseIn, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (entityLivingBaseIn.isAsleep()) {
            RenderType cameo = RenderType.entityCutout(OVERLAY);
            if (entityLivingBaseIn.getVariant() == 1) {
                cameo = RenderType.entityCutout(COLD_LOCATION);
            }

            if (entityLivingBaseIn.getVariant() == 2) {
                cameo = RenderType.entityCutout(HOT_LOCATION);
            }

            if (entityLivingBaseIn.getVariant() == 3) {
                cameo = RenderType.entityCutout(NETHER_LOCATION);
            }

            this.getRenderer().reRender(this.getGeoModel().getBakedModel(MODEL), poseStack, bufferSource, entityLivingBaseIn, renderType,
                    bufferSource.getBuffer(cameo), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                    1, 1, 1, 1);


        }
    }
}



