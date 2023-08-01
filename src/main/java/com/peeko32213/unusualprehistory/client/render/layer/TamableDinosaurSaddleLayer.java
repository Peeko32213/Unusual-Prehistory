package com.peeko32213.unusualprehistory.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityTameableBaseDinosaurAnimal;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class TamableDinosaurSaddleLayer<T extends EntityTameableBaseDinosaurAnimal> extends GeoLayerRenderer<T> {
    private final ResourceLocation overlayLocation;
    private final ResourceLocation modelLocation;
    //private final GeoModel model;
    public TamableDinosaurSaddleLayer(IGeoRenderer<T> entityRendererIn, ResourceLocation overlayLocation, ResourceLocation modelLocation) {
        super(entityRendererIn);

        this.overlayLocation = overlayLocation;
        this.modelLocation = modelLocation;
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        //ResourceLocation boneLoc = this.getEntityModel().getModelResource(entityLivingBaseIn);
        //GeoModel model = this.getEntityModel().getModel(boneLoc);

        if (entityLivingBaseIn.isSaddled()) {
            RenderType cameo = RenderType.entityCutout(overlayLocation);
            this.getRenderer().render(this.getEntityModel().getModel(modelLocation), entityLivingBaseIn, partialTicks, cameo, matrixStackIn, bufferIn,
                    bufferIn.getBuffer(cameo), packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
