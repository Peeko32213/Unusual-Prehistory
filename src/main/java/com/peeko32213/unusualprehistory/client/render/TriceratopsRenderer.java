package com.peeko32213.unusualprehistory.client.render;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.model.TriceratopsModel;
import com.peeko32213.unusualprehistory.client.render.layer.TriceratopsSaddleLayer;
import com.peeko32213.unusualprehistory.common.entity.EntityTriceratops;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class TriceratopsRenderer extends GeoEntityRenderer<EntityTriceratops> {

    public TriceratopsRenderer(EntityRendererProvider.Context context) {
        super(context, new TriceratopsModel());
        this.addLayer(new TriceratopsSaddleLayer(this));
    }

    @Override
    public void renderEarly(EntityTriceratops animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
        if (animatable.isBaby()) {
            stackIn.scale(0.5F, 0.5F, 0.5F);
        }
    }

}
