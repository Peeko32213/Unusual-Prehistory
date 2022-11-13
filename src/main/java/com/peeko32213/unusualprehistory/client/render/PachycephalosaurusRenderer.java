package com.peeko32213.unusualprehistory.client.render;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.model.PachycephalosaurusModel;
import com.peeko32213.unusualprehistory.client.model.TriceratopsModel;
import com.peeko32213.unusualprehistory.common.entity.EntityPachycephalosaurus;
import com.peeko32213.unusualprehistory.common.entity.EntityTriceratops;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PachycephalosaurusRenderer extends GeoEntityRenderer<EntityPachycephalosaurus> {

    public PachycephalosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new PachycephalosaurusModel());
    }

    @Override
    public void renderEarly(EntityPachycephalosaurus animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
        if (animatable.isBaby()) {
            stackIn.scale(0.5F, 0.5F, 0.5F);
        }
    }

}
