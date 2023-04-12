package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.model.AustroraptorModel;
import com.peeko32213.unusualprehistory.common.entity.EntityAustroraptor;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class AustroraptorRenderer extends GeoEntityRenderer<EntityAustroraptor> {

    public AustroraptorRenderer(EntityRendererProvider.Context context) {
        super(context, new AustroraptorModel());
    }

    @Override
    public RenderType getRenderType(EntityAustroraptor animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int PackedLightIn, ResourceLocation textureLocation){
        return RenderType.entityCutout(getTextureLocation(animatable));
    }

    @Override
    public void renderEarly(EntityAustroraptor animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
        if (animatable.isBaby()) {
            stackIn.scale(0.3F, 0.3F, 0.3F);
        }
    }

}
