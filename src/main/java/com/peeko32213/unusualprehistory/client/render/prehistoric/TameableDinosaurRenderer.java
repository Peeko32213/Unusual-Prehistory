package com.peeko32213.unusualprehistory.client.render.prehistoric;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.HwachavenatorEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.MegatheriumEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.UlughbegsaurusEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.base.TamablePrehistoricEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TameableDinosaurRenderer<T extends TamablePrehistoricEntity> extends GeoEntityRenderer<T> {


    public TameableDinosaurRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> modelProvider) {
        super(renderManager, modelProvider);
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutout(getTextureLocation(animatable));
    }

    @Override
    public void preRender(PoseStack stackIn, T animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(stackIn, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        if(animatable instanceof UlughbegsaurusEntity ulughbegsaurus) {
            if(ulughbegsaurus.isBaby()) stackIn.scale(0.5F, 0.5F, 0.5F);
            return;
        }

        if(animatable instanceof HwachavenatorEntity hwachavenator) {
            if(hwachavenator.isBaby()) stackIn.scale(0.3F, 0.3F, 0.3F);
            return;
        }

        if(animatable instanceof MegatheriumEntity megatherium) {
            if(megatherium.isBaby()) stackIn.scale(1.0F, 1.0F, 1.0F);
            return;
        }

        if (animatable.isBaby()) {
            stackIn.scale(0.5F, 0.5F, 0.5F);
        }
    }
}
