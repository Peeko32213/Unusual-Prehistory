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
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Locale;

public class DinosaurRenderer<T extends EntityBaseDinosaurAnimal> extends GeoEntityRenderer<T> {


    public DinosaurRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> modelProvider) {
        super(renderManager, modelProvider);
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutout(getTextureLocation(animatable));
    }

    @Override
    public void preRender(PoseStack stackIn, T animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(stackIn, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
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
