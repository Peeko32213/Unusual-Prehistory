package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.model.TyrannosaurusRexModel;
import com.peeko32213.unusualprehistory.client.render.layer.TyrannosaurusRexEepyLayer;
import com.peeko32213.unusualprehistory.common.entity.EntityTyrannosaurusRex;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TyrannosaurusRexRenderer extends GeoEntityRenderer<EntityTyrannosaurusRex> {

    public TyrannosaurusRexRenderer(EntityRendererProvider.Context context) {
        super(context, new TyrannosaurusRexModel());
        this.addRenderLayer(new TyrannosaurusRexEepyLayer(this));
    }

    @Override
    public void renderEarly(EntityTyrannosaurusRex animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
        if (animatable.isBaby()) {
            stackIn.scale(0.5F, 0.5F, 0.5F);
        }
    }

}
