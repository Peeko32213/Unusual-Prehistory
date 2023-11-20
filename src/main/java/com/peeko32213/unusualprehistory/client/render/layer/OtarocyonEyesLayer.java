package com.peeko32213.unusualprehistory.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityOtarocyon;
import com.peeko32213.unusualprehistory.common.entity.EntityTyrannosaurusRex;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EndermanRenderer;
import net.minecraft.client.renderer.entity.GlowSquidRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.GlowSquid;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class OtarocyonEyesLayer extends GeoRenderLayer<EntityOtarocyon> {
    private static final ResourceLocation OVERLAY = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/otarocyon_glow.png");
    private static final ResourceLocation MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/otarocyon.geo.json");

    public OtarocyonEyesLayer(GeoRenderer<EntityOtarocyon> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, EntityOtarocyon entityLivingBaseIn, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (entityLivingBaseIn.level().isNight()) {
            RenderType cameo = RenderType.entityCutout(OVERLAY);
            getRenderer().reRender(this.getGeoModel().getBakedModel(MODEL), poseStack, bufferSource, entityLivingBaseIn, renderType,
                    bufferSource.getBuffer(cameo), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                    1, 1, 1, 1);
        }
    }

}


