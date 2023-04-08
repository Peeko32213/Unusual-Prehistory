package com.peeko32213.unusualprehistory.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityHwachavenator;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class HwachavenatorSaddleLayer extends GeoLayerRenderer<EntityHwachavenator> {
    private static final ResourceLocation OVERLAY = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/hwachavenator_saddled.png");
    private static final ResourceLocation MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/hwachavenator.geo.json");

    public HwachavenatorSaddleLayer(IGeoRenderer<EntityHwachavenator> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, EntityHwachavenator entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityLivingBaseIn.isSaddled()) {
            RenderType cameo = RenderType.entityCutout(OVERLAY);
            this.getRenderer().render(this.getEntityModel().getModel(MODEL), entityLivingBaseIn, partialTicks, cameo, matrixStackIn, bufferIn,
                    bufferIn.getBuffer(cameo), packedLightIn, OverlayTexture.NO_OVERLAY,  1.0F, 1.0F, 1.0F, 1.0F);
        }
    }


    }


