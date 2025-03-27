package com.peeko32213.unusualprehistory.client.render.prehistoric;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.render.layer.RideLayer;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.OviraptorEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Locale;

public class PrehistoricRenderer<T extends PrehistoricEntity> extends GeoEntityRenderer<T> {

    public PrehistoricRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> modelProvider) {
        super(renderManager, modelProvider);
        addRenderLayer(new <PrehistoricEntity>RideLayer(this));
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutout(getTextureLocation(animatable));
    }

    @Override
    public void preRender(PoseStack stackIn, T animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(stackIn, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        if (animatable.isBaby()) {
            stackIn.scale(0.5F, 0.5F, 0.5F);
        }

        if (animatable instanceof OviraptorEntity oviraptor) {

            if (oviraptor.hasCustomName() && "gigantoraptor".equals(oviraptor.getName().getString().toLowerCase(Locale.ROOT)) && !oviraptor.isBaby()) {
                stackIn.scale(2F, 2F, 2F);
            }
        }

    }

//    @Override
//    public void renderRecursively(PoseStack poseStack, T animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
//        if(animatable.isAlive()) {
//            super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
//            return;
//        }
//
//        if (bufferSource != null) {
//            buffer = bufferSource.getBuffer(renderType);
//        }
//
//        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, OverlayTexture.pack(0,
//                OverlayTexture.v(animatable.hurtTime > 0)), red, green, blue, alpha);
//
//    }
}
