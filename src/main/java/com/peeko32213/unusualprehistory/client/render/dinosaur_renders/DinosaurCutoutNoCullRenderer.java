package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.common.entity.EntityAustroraptor;
import com.peeko32213.unusualprehistory.common.entity.EntityVelociraptor;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Locale;

public class DinosaurCutoutNoCullRenderer<T extends EntityBaseDinosaurAnimal> extends GeoEntityRenderer<T> {


    public DinosaurCutoutNoCullRenderer(EntityRendererProvider.Context renderManager, AnimatedGeoModel<T> modelProvider) {
        super(renderManager, modelProvider);
    }

    @Override
    public RenderType getRenderType(T animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int PackedLightIn, ResourceLocation textureLocation){
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

    @Override
    public void renderEarly(T animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
        if (animatable instanceof EntityVelociraptor) {
            EntityVelociraptor velociraptor = (EntityVelociraptor) animatable;

            if (velociraptor.hasCustomName() && "gigantoraptor".equals(velociraptor.getName().getString().toLowerCase(Locale.ROOT)) && !velociraptor.isBaby()) {
                stackIn.scale(2F, 2F, 2F);
                return;
            }
        }
        if(animatable instanceof EntityAustroraptor austroraptor)
        {
            if(austroraptor.isBaby()) stackIn.scale(0.3F, 0.3F, 0.3F);
            return;
        }



        if (animatable.isBaby()) {
            stackIn.scale(0.5F, 0.5F, 0.5F);
            return;
        }
    }
}
