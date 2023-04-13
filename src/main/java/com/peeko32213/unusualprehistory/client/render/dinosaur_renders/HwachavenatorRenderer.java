package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.model.HwachavenatorModel;
import com.peeko32213.unusualprehistory.client.render.layer.HwachavenatorSaddleLayer;
import com.peeko32213.unusualprehistory.common.entity.EntityHwachavenator;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class HwachavenatorRenderer extends GeoEntityRenderer<EntityHwachavenator> {

    public HwachavenatorRenderer(EntityRendererProvider.Context context) {
        super(context, new HwachavenatorModel());
        this.addLayer(new HwachavenatorSaddleLayer(this));
    }

    @Override
    public void renderEarly(EntityHwachavenator animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
        if (animatable.isBaby()) {
            stackIn.scale(0.3F, 0.3F, 0.3F);
        }
    }

}