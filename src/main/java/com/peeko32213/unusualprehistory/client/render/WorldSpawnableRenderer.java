package com.peeko32213.unusualprehistory.client.render;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.model.PachycephalosaurusModel;
import com.peeko32213.unusualprehistory.client.model.WorldSpawnableModel;
import com.peeko32213.unusualprehistory.common.entity.EntityWorldSpawnable;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WorldSpawnableRenderer extends GeoEntityRenderer<EntityWorldSpawnable> {

    public WorldSpawnableRenderer(EntityRendererProvider.Context context) {
        super(context, new WorldSpawnableModel());
    }

    @Override
    public void renderEarly(EntityWorldSpawnable animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

}
