package com.peeko32213.unusualprehistory.client.render.projectile;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.model.DefaultModel;
import com.peeko32213.unusualprehistory.client.model.ModelLocations;
import com.peeko32213.unusualprehistory.common.entity.projectile.AmberShotEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AmberShotRenderer extends GeoEntityRenderer<AmberShotEntity> {

    public AmberShotRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultModel<>(ModelLocations.AMBER_SHOT));
    }

    public RenderType getRenderType(AmberShotEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }




}
