package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.model.KimmeridgebrachypteraeschnidiumModel;
import com.peeko32213.unusualprehistory.client.model.TyrannosaurusRexModel;
import com.peeko32213.unusualprehistory.client.render.layer.KimmeridgebrachypteraeschnidiumPatternLayer;
import com.peeko32213.unusualprehistory.client.render.layer.KimmeridgebrachypteraeschnidiumWingLayer;
import com.peeko32213.unusualprehistory.client.render.layer.TyrannosaurusRexEepyLayer;
import com.peeko32213.unusualprehistory.common.entity.EntityKimmeridgebrachypteraeschnidium;
import com.peeko32213.unusualprehistory.common.entity.EntityTyrannosaurusRex;
import net.minecraft.client.model.ColorableHierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TropicalFishRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class KimmeridgebrachypteraeschnidiumRenderer extends GeoEntityRenderer<EntityKimmeridgebrachypteraeschnidium> {



    private float r = 1.0F;
    private float g = 1.0F;
    private float b = 1.0F;


    public KimmeridgebrachypteraeschnidiumRenderer(EntityRendererProvider.Context context) {
        super(context, new KimmeridgebrachypteraeschnidiumModel());
        this.addRenderLayer(new KimmeridgebrachypteraeschnidiumPatternLayer(this));
        this.addRenderLayer(new KimmeridgebrachypteraeschnidiumWingLayer(this));
    }

    @Override
    public Color getRenderColor(EntityKimmeridgebrachypteraeschnidium animatable, float partialTick, int packedLight) {
        return getColor();
    }

    @Override
    public RenderType getRenderType(EntityKimmeridgebrachypteraeschnidium animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void render(EntityKimmeridgebrachypteraeschnidium kimmer, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        super.render(kimmer, f, g, poseStack, multiBufferSource, i);
        float[] fs = kimmer.getBaseColor().getTextureDiffuseColors();
        this.setColor(fs[0], fs[1], fs[2]);
    }

    public void setColor(float pR, float pG, float pB) {
        this.r = pR;
        this.g = pG;
        this.b = pB;
    }

    public Color getColor() {
        return Color.ofRGBA(r,g,b,1);
    }

}
