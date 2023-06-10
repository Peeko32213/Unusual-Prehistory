package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.model.PalaeophisModel;
import com.peeko32213.unusualprehistory.client.model.PalaeophisPartModel;
import com.peeko32213.unusualprehistory.common.entity.EntityPalaeophis;
import com.peeko32213.unusualprehistory.common.entity.msc.part.EntityPalaeophisPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class PalaeophisPartRenderer extends GeoEntityRenderer<EntityPalaeophisPart> {

    public PalaeophisPartRenderer(EntityRendererProvider.Context context) {
        super(context, new PalaeophisPartModel());
    }

    @Override
    public RenderType getRenderType(EntityPalaeophisPart animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

}
