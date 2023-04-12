package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.model.UlughbegsaurusModel;
import com.peeko32213.unusualprehistory.client.render.layer.UlughbegsaurusSaddleLayer;
import com.peeko32213.unusualprehistory.common.entity.EntityUlughbegsaurus;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class UlughbegsaurusRenderer extends GeoEntityRenderer<EntityUlughbegsaurus> {

    public UlughbegsaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new UlughbegsaurusModel());
        this.addLayer(new UlughbegsaurusSaddleLayer(this));

    }

    @Override
    public void renderEarly(EntityUlughbegsaurus animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
        if (animatable.isBaby()) {
            stackIn.scale(0.3F, 0.3F, 0.3F);
        }
    }

}
