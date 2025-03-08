package com.peeko32213.unusualprehistory.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.common.entity.custom.base.TamablePrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.base.TamableStatedPrehistoricEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class TamableStatedPrehistoricSaddleLayer<T extends TamableStatedPrehistoricEntity> extends GeoRenderLayer<T> {
    private final ResourceLocation overlayLocation;
    private final ResourceLocation modelLocation;
    //private final GeoModel model;
    public TamableStatedPrehistoricSaddleLayer(GeoRenderer<T> entityRendererIn, ResourceLocation overlayLocation, ResourceLocation modelLocation) {
        super(entityRendererIn);

        this.overlayLocation = overlayLocation;
        this.modelLocation = modelLocation;
    }

    @Override
    public void render(PoseStack poseStack, T entityLivingBaseIn, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (entityLivingBaseIn.isSaddled()) {
            RenderType cameo = RenderType.entityCutout(overlayLocation);
            getRenderer().reRender(this.getGeoModel().getBakedModel(modelLocation), poseStack, bufferSource, entityLivingBaseIn, renderType,
                    bufferSource.getBuffer(cameo), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                    1, 1, 1, 1);
        }
    }


}
