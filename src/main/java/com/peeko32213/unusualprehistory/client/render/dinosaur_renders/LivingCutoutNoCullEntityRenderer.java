package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.common.entity.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class LivingCutoutNoCullEntityRenderer<T extends LivingEntity & GeoAnimatable> extends GeoEntityRenderer<T> {

    public LivingCutoutNoCullEntityRenderer(EntityRendererProvider.Context context, GeoModel<T> model) {
        super(context, model);
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

    @Override
    public void preRender(PoseStack stackIn, T animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(stackIn, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        if(animatable instanceof EntityDiplocaulus diplocaulus) {
            if(diplocaulus.isBaby()) stackIn.scale(0.7F, 0.7F, 0.7F);
        }

        if(animatable instanceof EntityHyneria hyneria) {
            if(hyneria.isBaby()) stackIn.scale(0.65F, 0.65F, 0.65F);
        }

        if(animatable instanceof EntityBarinasuchus barinasuchus) {
            if(barinasuchus.isBaby()) stackIn.scale(0.55F, 0.55F, 0.55F);
        }

        if(animatable instanceof EntityOtarocyon otarocyon) {
            if(otarocyon.isBaby()) stackIn.scale(0.5F, 0.5F, 0.5F);
        }

    }
}