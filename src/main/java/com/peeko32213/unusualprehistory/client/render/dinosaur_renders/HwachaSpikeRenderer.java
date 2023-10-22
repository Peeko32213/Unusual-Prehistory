package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.model.HwachaSpikeModel;
import com.peeko32213.unusualprehistory.common.entity.msc.projectile.EntityHwachaSpike;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class HwachaSpikeRenderer extends GeoEntityRenderer<EntityHwachaSpike> {

    public HwachaSpikeRenderer(EntityRendererProvider.Context context) {
        super(context, new HwachaSpikeModel());
    }

    public RenderType getRenderType(EntityHwachaSpike animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

    public void render(EntityHwachaSpike p_113839_, float p_113840_, float p_113841_, PoseStack p_113842_, MultiBufferSource p_113843_, int p_113844_) {
        super.render(p_113839_, p_113840_, p_113841_, p_113842_, p_113843_, p_113844_);
    }


}
