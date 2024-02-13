package com.peeko32213.unusualprehistory.client.render.dinosaur_renders;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.model.DefaultModel;
import com.peeko32213.unusualprehistory.client.model.ModelLocations;
import com.peeko32213.unusualprehistory.client.render.layer.TyrannosaurusRexEepyLayer;
import com.peeko32213.unusualprehistory.common.entity.EntityTyrannosaurusRex;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TyrannosaurusRexRenderer extends GeoEntityRenderer<EntityTyrannosaurusRex> {

    public TyrannosaurusRexRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultModel<>(ModelLocations.REX));
        this.addRenderLayer(new TyrannosaurusRexEepyLayer(this));
    }

    @Override
    public void preRender(PoseStack poseStack, EntityTyrannosaurusRex animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        if (animatable.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }
    }
}
