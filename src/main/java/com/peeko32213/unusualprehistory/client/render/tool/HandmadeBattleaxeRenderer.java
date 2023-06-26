package com.peeko32213.unusualprehistory.client.render.tool;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.unusualprehistory.client.model.tool.HandmadeBattleaxeModel;
import com.peeko32213.unusualprehistory.common.item.tool.ItemHandmadeBattleaxe;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class HandmadeBattleaxeRenderer extends GeoItemRenderer<ItemHandmadeBattleaxe> {
    public HandmadeBattleaxeRenderer() {
        super(new HandmadeBattleaxeModel());
    }

    @Override
    public RenderType getRenderType(ItemHandmadeBattleaxe animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }
}
