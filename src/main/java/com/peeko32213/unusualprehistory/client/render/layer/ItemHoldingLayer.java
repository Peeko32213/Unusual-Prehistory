package com.peeko32213.unusualprehistory.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.peeko32213.unusualprehistory.common.entity.EntityGigantopithicus;
import com.peeko32213.unusualprehistory.common.entity.EntityMammoth;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.RenderUtils;

public class ItemHoldingLayer<T extends EntityBaseDinosaurAnimal> extends GeoRenderLayer<T> {
    private ItemStack mainHand;

    public ItemHoldingLayer(GeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, T dino, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        this.mainHand = dino.getItemBySlot(EquipmentSlot.MAINHAND);
        ResourceLocation boneLoc = this.getGeoModel().getModelResource(dino);
        BakedGeoModel model = this.getGeoModel().getBakedModel(boneLoc);

        if (dino instanceof EntityGigantopithicus gigantopithicus) {
            renderRecursivelyGiganto(gigantopithicus, model.topLevelBones().get(0), poseStack, bufferSource, packedLight, OverlayTexture.pack(OverlayTexture.u(0),
                    OverlayTexture.v(gigantopithicus.hurtTime > 0)));
        }
        if (dino instanceof EntityMammoth mammoth) {
            renderRecursivelyMammoth(mammoth, model.getBone("Fur").get(), poseStack, bufferSource, packedLight, OverlayTexture.pack(OverlayTexture.u(0),
                    OverlayTexture.v(mammoth.hurtTime > 0)));
        }
    }

    private void renderRecursivelyMammoth(Entity entity, GeoBone bone, PoseStack stack, MultiBufferSource renderBuffer, int packedLightIn,
                                          int packedOverlayIn) {

        stack.pushPose();
        RenderUtils.translateToPivotPoint(stack,bone);
        RenderUtils.translateToPivotPoint(stack,bone);
        //RenderUtils.rotateMatrixAroundCube(stack,bone);
        RenderUtils.scaleMatrixForBone(stack, bone);
        RenderUtils.translateAwayFromPivotPoint(stack, bone);

        if(entity instanceof EntityMammoth mammoth){
            //UnusualPrehistory.LOGGER.info("bones " + bone.getName());
            if (!mammoth.getHoldItemStack().isEmpty()) {
                stack.pushPose();
                //You'll need to play around with these to get item to render in the correct orientation
                stack.mulPose(Axis.XP.rotationDegrees(-75));
                stack.mulPose(Axis.YP.rotationDegrees(0));
                stack.mulPose(Axis.ZP.rotationDegrees(0));
                //You'll need to play around with this to render the item in the correct spot.
                stack.translate(0D, 0D, 2.3D);
                //Sets the scaling of the item.
                stack.scale(mammoth.getHoldItemStack().getCount(), mammoth.getHoldItemStack().getCount(), mammoth.getHoldItemStack().getCount());
                // Change mainHand to predefined Itemstack and TransformType to what transform you would want to use.
                ItemStack itemStack = mammoth.getHoldItemStack();
                Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemDisplayContext.GUI, packedLightIn,
                        OverlayTexture.NO_OVERLAY, stack, renderBuffer, entity.level(),1);

                stack.popPose();

                //Stops unnecessary further recursive method calling. Only works if I am rendering one thing per layer.
                stack.popPose();
                return;
            }
        }



        if (!bone.isHidden()) {
            for (GeoBone childBone : bone.getChildBones()) {
                renderRecursivelyMammoth(entity, childBone, stack, renderBuffer, packedLightIn, packedOverlayIn);
            }
        }

        stack.popPose();
    }

    private void renderRecursivelyGiganto(Entity entity, GeoBone bone, PoseStack stack, MultiBufferSource renderBuffer, int packedLightIn,
                                          int packedOverlayIn) {

        stack.pushPose();
        RenderUtils.translateToPivotPoint(stack,bone);
        RenderUtils.translateToPivotPoint(stack,bone);
        //RenderUtils.rotateMatrixAroundCube(stack,bone);
        RenderUtils.scaleMatrixForBone(stack, bone);
        RenderUtils.translateAwayFromPivotPoint(stack, bone);
        if(entity instanceof EntityBaseDinosaurAnimal entityBaseDinosaurAnimal) {
            if (bone.getName().equals("Arm1") && entityBaseDinosaurAnimal.isTrading() && entityBaseDinosaurAnimal instanceof EntityGigantopithicus) {
                stack.pushPose();
                //You'll need to play around with these to get item to render in the correct orientation
                stack.mulPose(Axis.XP.rotationDegrees(-45));
                stack.mulPose(Axis.YP.rotationDegrees(0));
                stack.mulPose(Axis.ZP.rotationDegrees(0));

                //You'll need to play around with this to render the item in the correct spot.
                stack.translate(1.55D, 0.1D, -0.75D);
                //Sets the scaling of the item.
                stack.scale(2.5f, 2.5f, 2.5f);

                //stack.mulPose(Vector3f.XP.rotationDegrees(25));
                stack.mulPose(Axis.YP.rotationDegrees(180));
                //stack.mulPose(Vector3f.ZP.rotationDegrees(0));

                // Change mainHand to predefined Itemstack and TransformType to what transform you would want to use.
                Minecraft.getInstance().getItemRenderer().renderStatic(mainHand, ItemDisplayContext.GUI, packedLightIn,
                        OverlayTexture.NO_OVERLAY, stack, renderBuffer, entity.level(),1);
                stack.popPose();
                //Stops unnecessary further recursive method calling. Only works if I am rendering one thing per layer.
                stack.popPose();
                return;
            }
        }


        if (!bone.isHidden()) {
            for (GeoBone childBone : bone.getChildBones()) {
                renderRecursivelyGiganto(entity, childBone, stack, renderBuffer, packedLightIn, packedOverlayIn);
            }
        }

        stack.popPose();
    }
}