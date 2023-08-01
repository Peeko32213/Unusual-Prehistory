package com.peeko32213.unusualprehistory.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.peeko32213.unusualprehistory.common.entity.EntityUlughbegsaurus;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityTameableBaseDinosaurAnimal;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

import java.util.Locale;

public class JebLayer<T extends EntityTameableBaseDinosaurAnimal> extends GeoLayerRenderer<T> {
    private final ResourceLocation overlayLocation;
    private final ResourceLocation modelLocation;

    public JebLayer(IGeoRenderer<T> entityRendererIn, ResourceLocation overlayLocation, ResourceLocation modelLocation) {
        super(entityRendererIn);
        this.overlayLocation = overlayLocation;
        this.modelLocation = modelLocation;
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        float f;
        float f1;
        float f2;
        if (entityLivingBaseIn.hasCustomName() && "jeb_".equals(entityLivingBaseIn.getName().getString().toLowerCase(Locale.ROOT))) {
            int i1 = 25;
            int i = entityLivingBaseIn.tickCount / 25 + entityLivingBaseIn.getId();
            int j = DyeColor.values().length;
            int k = i % j;
            int l = (i + 1) % j;
            float f3 = ((float) (entityLivingBaseIn.tickCount % 25) + partialTicks) / 25.0F;
            float[] afloat1 = EntityUlughbegsaurus.getColorArray(DyeColor.byId(k));
            float[] afloat2 = EntityUlughbegsaurus.getColorArray(DyeColor.byId(l));
            f = afloat1[0] * (1.0F - f3) + afloat2[0] * f3;
            f1 = afloat1[1] * (1.0F - f3) + afloat2[1] * f3;
            f2 = afloat1[2] * (1.0F - f3) + afloat2[2] * f3;
            RenderType cameo = RenderType.entityCutout(overlayLocation);
            this.getRenderer().render(this.getEntityModel().getModel(modelLocation), entityLivingBaseIn, partialTicks, cameo, matrixStackIn, bufferIn,
                    bufferIn.getBuffer(cameo), packedLightIn, LivingEntityRenderer.getOverlayCoords(entityLivingBaseIn, 0.0F),  f, f1, f2, 1.0F);
        }
    }
}

