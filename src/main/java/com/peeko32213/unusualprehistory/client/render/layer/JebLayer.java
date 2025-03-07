package com.peeko32213.unusualprehistory.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.UlughbegsaurusEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.base.TamablePrehistoricEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import java.util.Locale;

public class JebLayer<T extends TamablePrehistoricEntity> extends GeoRenderLayer<T> {
    private final ResourceLocation overlayLocation;
    private final ResourceLocation modelLocation;

    public JebLayer(GeoRenderer<T> entityRendererIn, ResourceLocation overlayLocation, ResourceLocation modelLocation) {
        super(entityRendererIn);
        this.overlayLocation = overlayLocation;
        this.modelLocation = modelLocation;
    }

    @Override
    public void render(PoseStack poseStack, T entityLivingBaseIn, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        float f;
        float f1;
        float f2;
        if (entityLivingBaseIn.hasCustomName() && "jeb_".equals(entityLivingBaseIn.getName().getString().toLowerCase(Locale.ROOT))) {
            int i1 = 10;
            int i = entityLivingBaseIn.tickCount / 10 + entityLivingBaseIn.getId();
            int j = DyeColor.values().length;
            int k = i % j;
            int l = (i + 1) % j;
            float f3 = ((float) (entityLivingBaseIn.tickCount % 10) + partialTick) / 10.0F;
            float[] afloat1 = UlughbegsaurusEntity.getColorArray(DyeColor.byId(k));
            float[] afloat2 = UlughbegsaurusEntity.getColorArray(DyeColor.byId(l));
            f = afloat1[0] * (1.0F - f3) + afloat2[0] * f3;
            f1 = afloat1[1] * (1.0F - f3) + afloat2[1] * f3;
            f2 = afloat1[2] * (1.0F - f3) + afloat2[2] * f3;
            RenderType cameo = RenderType.entityCutout(overlayLocation);
            getRenderer().reRender(this.getGeoModel().getBakedModel(modelLocation), poseStack, bufferSource, entityLivingBaseIn, renderType,
                    bufferSource.getBuffer(cameo), partialTick, packedLight, LivingEntityRenderer.getOverlayCoords(entityLivingBaseIn, 0.0F),
                    f, f1, f2, 1);

        }
    }

}

