package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.model.VelociraptorModel;
import com.peeko32213.unusualprehistory.common.entity.EntityVelociraptor;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Locale;

public class VelociraptorRenderer extends GeoEntityRenderer<EntityVelociraptor> {

    public VelociraptorRenderer(EntityRendererProvider.Context context) {
        super(context, new VelociraptorModel());
    }

    @Override
    public void renderEarly(EntityVelociraptor animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);

        if(animatable.hasCustomName() && "gigantoraptor".equals(animatable.getName().getString().toLowerCase(Locale.ROOT)) && !animatable.isBaby()){
            stackIn.scale(2F, 2F, 2F);
        }

        if (animatable.isBaby()) {
            stackIn.scale(0.5F, 0.5F, 0.5F);
        }
    }

}
