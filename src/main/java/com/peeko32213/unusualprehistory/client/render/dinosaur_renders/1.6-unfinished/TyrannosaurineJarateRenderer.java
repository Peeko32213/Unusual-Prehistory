//package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import com.mojang.math.Axis;
//import com.peeko32213.unusualprehistory.client.model.DefaultModel;
//import com.peeko32213.unusualprehistory.client.model.ModelLocations;
//import com.peeko32213.unusualprehistory.client.model.TyrannosaurineJarateModel;
//import com.peeko32213.unusualprehistory.common.entity.msc.projectile.EntityAmberShot;
//import com.peeko32213.unusualprehistory.common.entity.msc.projectile.EntityTyrannosaurineJarate;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.renderer.entity.EntityRendererProvider;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.util.Mth;
//import software.bernie.geckolib.renderer.GeoEntityRenderer;
//import software.bernie.geckolib.renderer.GeoRenderer;
//
//public class TyrannosaurineJarateRenderer extends GeoEntityRenderer<EntityTyrannosaurineJarate> {
//    public TyrannosaurineJarateRenderer(EntityRendererProvider.Context context) {
//        super(context, new DefaultModel<>(ModelLocations.JARATE));
//    }
//
//    public RenderType getRenderType(EntityTyrannosaurineJarate animatable, float partialTicks, PoseStack stack,
//                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
//                                    ResourceLocation textureLocation) {
//        return RenderType.entityTranslucent(getTextureLocation(animatable));
//    }
//
//    @Override
//    protected void applyRotations(EntityTyrannosaurineJarate animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
//        float f = 4.3F * Mth.sin(0.6F * ageInTicks);
//        if (animatable.getOwner() != null) {
//            super.applyRotations(animatable, poseStack, ageInTicks, animatable.getOwner().getYRot(), partialTick);
//        }
//    }
//
//}