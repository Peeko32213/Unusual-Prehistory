package com.peeko32213.unusualprehistory.client.render.prehistoric;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.*;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.semi_aquatic.BrachiosaurusEntity;
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
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutout(getTextureLocation(animatable));
    }

    @Override
    public void preRender(PoseStack stackIn, T animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(stackIn, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        if (animatable instanceof VelociraptorEntity velociraptor) {

            if (velociraptor.hasCustomName() && "gigantoraptor".equals(velociraptor.getName().getString().toLowerCase(Locale.ROOT)) && !velociraptor.isBaby()) {
                stackIn.scale(2F, 2F, 2F);
                return;
            }
        }
        if(animatable instanceof AustroraptorEntity austroraptor) {
            if(austroraptor.isBaby()) stackIn.scale(0.3F, 0.3F, 0.3F);
            return;
        }

        if(animatable instanceof MegalaniaEntity megalania) {
            if(megalania.isBaby()) stackIn.scale(1.0F, 1.0F, 1.0F);
            return;
        }

        if(animatable instanceof SmilodonEntity smilodon) {
            if(smilodon.isBaby()) stackIn.scale(1.0F, 1.0F, 1.0F);
            return;
        }

        if(animatable instanceof BrachiosaurusEntity brachiosaurus) {
            if(brachiosaurus.isBaby()) stackIn.scale(1.0F, 1.0F, 1.0F);
            return;
        }

        if(animatable instanceof MammothEntity mammoth) {
            if(mammoth.isBaby()) stackIn.scale(0.5F, 0.5F, 0.5F);
            return;
        }

        if(animatable instanceof ParaceratheriumEntity paraceratherium) {
            if(paraceratherium.isBaby()) stackIn.scale(0.5F, 0.5F, 0.5F);
            return;
        }

        if(animatable instanceof GigantopithicusEntity gigantopithicus) {
            if(gigantopithicus.isBaby()) stackIn.scale(0.35F, 0.35F, 0.35F);
            return;
        }

        if (animatable.isBaby()) {
            stackIn.scale(0.5F, 0.5F, 0.5F);
        }
    }
}
