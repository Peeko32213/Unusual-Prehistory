package com.peeko32213.unusualprehistory.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.peeko32213.unusualprehistory.common.block.entity.CultivatorBlockEntity;
import com.peeko32213.unusualprehistory.common.block.entity.IncubatorBlockEntity;
import com.peeko32213.unusualprehistory.common.recipe.IncubatorRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;

public class IncubatorBlockEntityRenderer implements BlockEntityRenderer<IncubatorBlockEntity> {
    public IncubatorBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }


    @Override
    public void render(IncubatorBlockEntity blockEntity, float partialtick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        float age = blockEntity.tickCount + partialtick;

        float rotateAngleY = age / 5;



        ItemStack itemStack = blockEntity.getRenderStack();

        poseStack.pushPose();
        poseStack.translate(0.5, 1 + Math.sin(age * 0.05) * 0.2, 0.5);
        poseStack.scale(0.5F, 0.5F, 0.5F);

        poseStack.mulPose(Vector3f.YP.rotationDegrees(rotateAngleY * (45F / (float) Math.PI)));


        itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.GUI, packedLight,
                OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, 1);

        poseStack.popPose();
        // poseStack.scale(0.1F, 0.1F, 0.1F);
        // poseStack.mulPose(Vector3f.YP.rotationDegrees(rotateAngleY * (45F / (float) Math.PI) ));
        // poseStack.mulPose(Vector3f.XP.rotationDegrees(rotateAngleX * (90 / (float) Math.PI)));
//
        // itemRenderer.renderStatic(itemStack2, ItemTransforms.TransformType.GUI, packedLight,
        //         OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, 1);
        // poseStack.clear();
        // for (int c = 0; c < 1; c++) {
        //     poseStack.pushPose();

        //     // shift to the torso

        //     float scale = 0.2f;
        //     // invert Y
        //     poseStack.scale(scale, scale, scale);

        //     // perform the rotations, accounting for the fact that baked models are corner-based
        //     poseStack.translate(0.5 / scale, 1 / scale, 0.5 / scale);
        //     //stack.mulPose(Vector3f.ZP.rotationDegrees(rotateAngleZ * (180F / (float) Math.PI)));
        //     poseStack.mulPose(Vector3f.YP.rotationDegrees(rotateAngleY * (45F / (float) Math.PI) + (c * (360F / count))));
        //     poseStack.mulPose(Vector3f.XP.rotationDegrees(rotateAngleX * (90 / (float) Math.PI)));


        //     // push the books outwards from the center of rotation
        //     poseStack.translate(0, 0, 1);

        //     poseStack.translate(Math.cos(age * 0.0005) * 0.5, 0, 0);
        //     poseStack.translate(0, Math.cos(age * 0.0005) * 0.5, 0);
        //     poseStack.translate(0, 0,  Math.sin(age*0.1));

        //     itemRenderer.renderStatic(itemStack2, ItemTransforms.TransformType.GUI, packedLight,
        //             OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, 1);

        //     poseStack.popPose();
        // }


    }


}