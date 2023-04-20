package com.peeko32213.unusualprehistory.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityTyrannosaurusRex;
import com.peeko32213.unusualprehistory.common.entity.EntityUlughbegsaurus;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class UlughbegsaurusJebLayer extends GeoLayerRenderer<EntityUlughbegsaurus> {
    private static final ResourceLocation OVERLAY = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/ulughbegsaurus_jeb.png");
    private static final ResourceLocation MODEL = new ResourceLocation(UnusualPrehistory.MODID, "geo/ulughbegsaurus.geo.json");

    public UlughbegsaurusJebLayer(IGeoRenderer<EntityUlughbegsaurus> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, EntityUlughbegsaurus entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        float f;
        float f1;
        float f2;
        if (entityLivingBaseIn.hasCustomName() && "jeb_".equals(entityLivingBaseIn.getName().getString())) {
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
    }
    }


}