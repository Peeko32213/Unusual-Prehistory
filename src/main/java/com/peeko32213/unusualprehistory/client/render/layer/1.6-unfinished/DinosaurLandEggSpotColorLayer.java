// package com.peeko32213.unusualprehistory.client.render.layer;

// import com.mojang.blaze3d.vertex.PoseStack;
// import com.mojang.blaze3d.vertex.VertexConsumer;
// import com.peeko32213.unusualprehistory.UnusualPrehistory;
// import com.peeko32213.unusualprehistory.common.entity.eggs.DinosaurLandEgg;
// import net.minecraft.client.renderer.MultiBufferSource;
// import net.minecraft.client.renderer.RenderType;
// import net.minecraft.client.renderer.texture.OverlayTexture;
// import net.minecraft.resources.ResourceLocation;
// import software.bernie.geckolib.cache.object.BakedGeoModel;
// import software.bernie.geckolib.renderer.GeoRenderer;
// import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

// public class DinosaurLandEggSpotColorLayer extends GeoRenderLayer<DinosaurLandEgg> {


//     public DinosaurLandEggSpotColorLayer(GeoRenderer<DinosaurLandEgg> entityRendererIn) {
//         super(entityRendererIn);
//     }

//     @Override
//     public void render(PoseStack poseStack, DinosaurLandEgg entityLivingBaseIn, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
//         ResourceLocation MODEL = entityLivingBaseIn.getModel();
//         ResourceLocation SPOT_LAYER = entityLivingBaseIn.getSpotTexture();
//         RenderType cameo = RenderType.entityCutout(SPOT_LAYER);
//         float[] fs = entityLivingBaseIn.getEggColorFromVector(entityLivingBaseIn.getEggSpotColor());
//         getRenderer().reRender(this.getGeoModel().getBakedModel(MODEL), poseStack, bufferSource, entityLivingBaseIn, renderType, bufferSource.getBuffer(cameo), partialTick, packedLight, OverlayTexture.NO_OVERLAY, fs[0], fs[1], fs[2], 1);
//     }
// }
