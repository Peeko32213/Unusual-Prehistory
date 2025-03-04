package com.peeko32213.unusualprehistory.client.render.prehistoric;


import com.mojang.blaze3d.vertex.PoseStack;
import com.peeko32213.unusualprehistory.client.model.DefaultModel;
import com.peeko32213.unusualprehistory.client.model.ModelLocations;
import com.peeko32213.unusualprehistory.client.render.layer.KimmeridgebrachypteraeschnidiumBaseLayer;
import com.peeko32213.unusualprehistory.client.render.layer.KimmeridgebrachypteraeschnidiumPatternLayer;
import com.peeko32213.unusualprehistory.client.render.layer.KimmeridgebrachypteraeschnidiumWingLayer;
import com.peeko32213.unusualprehistory.common.entity.KimmeridgebrachypteraeschnidiumEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class KimmeridgebrachypteraeschnidiumRenderer extends GeoEntityRenderer<KimmeridgebrachypteraeschnidiumEntity> {

    public KimmeridgebrachypteraeschnidiumRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultModel<>(ModelLocations.KIMMER));
        this.addRenderLayer(new KimmeridgebrachypteraeschnidiumBaseLayer(this));
        this.addRenderLayer(new KimmeridgebrachypteraeschnidiumPatternLayer(this));
        this.addRenderLayer(new KimmeridgebrachypteraeschnidiumWingLayer(this));
    }

    @Override
    public RenderType getRenderType(KimmeridgebrachypteraeschnidiumEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

    @Override
    public void render(KimmeridgebrachypteraeschnidiumEntity kimmer, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        super.render(kimmer, f, g, poseStack, multiBufferSource, i);
    }

}
