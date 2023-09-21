package com.peeko32213.unusualprehistory.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.peeko32213.unusualprehistory.common.entity.EntityGigantopithicus;
import com.peeko32213.unusualprehistory.common.entity.EntityMammoth;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.util.RenderUtils;

public class ItemHoldingLayer<T extends EntityBaseDinosaurAnimal> extends GeoLayerRenderer<T> {
    private ItemStack mainHand;

    public ItemHoldingLayer(IGeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T dino, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        this.mainHand = dino.getItemBySlot(EquipmentSlot.MAINHAND);
        ResourceLocation boneLoc = this.getEntityModel().getModelResource(dino);
        GeoModel model = this.getEntityModel().getModel(boneLoc);

        if (dino instanceof EntityGigantopithicus gigantopithicus) {
            renderRecursivelyGiganto(gigantopithicus, model.topLevelBones.get(0), matrixStackIn, bufferIn, packedLightIn, OverlayTexture.pack(OverlayTexture.u(0),
                    OverlayTexture.v(gigantopithicus.hurtTime > 0)));
        }
        if (dino instanceof EntityMammoth mammoth) {
            renderRecursivelyMammoth(mammoth, model.getBone("Fur").get(), matrixStackIn, bufferIn, packedLightIn, OverlayTexture.pack(OverlayTexture.u(0),
                    OverlayTexture.v(mammoth.hurtTime > 0)));
        }
    }

    private void renderRecursivelyMammoth(Entity entity, GeoBone bone, PoseStack stack, MultiBufferSource renderBuffer, int packedLightIn,
                                          int packedOverlayIn) {

        stack.pushPose();
        RenderUtils.translate(bone, stack);
        RenderUtils.moveToPivot(bone, stack);
        RenderUtils.rotate(bone, stack);
        RenderUtils.scale(bone, stack);
        RenderUtils.moveBackFromPivot(bone, stack);

        if(entity instanceof EntityMammoth mammoth){
            //UnusualPrehistory.LOGGER.info("bones " + bone.getName());
            if (!mammoth.getHoldItemStack().isEmpty()) {
                stack.pushPose();
                //You'll need to play around with these to get item to render in the correct orientation
                stack.mulPose(Vector3f.XP.rotationDegrees(-75));
                stack.mulPose(Vector3f.YP.rotationDegrees(0));
                stack.mulPose(Vector3f.ZP.rotationDegrees(0));
                //You'll need to play around with this to render the item in the correct spot.
                stack.translate(0D, 0D, 4.0D);
                //Sets the scaling of the item.
                stack.scale(0.5f, 0.5f, 0.5f);
                // Change mainHand to predefined Itemstack and TransformType to what transform you would want to use.
                ItemStack itemStack = mammoth.getHoldItemStack();
                Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND,
                        packedLightIn, packedOverlayIn, stack, renderBuffer, entity.getId());
                stack.popPose();
                //Stops unnecessary further recursive method calling. Only works if I am rendering one thing per layer.
                stack.popPose();
                return;
            }
        }



        if (!bone.isHidden) {
            for (GeoBone childBone : bone.childBones) {
                renderRecursivelyMammoth(entity, childBone, stack, renderBuffer, packedLightIn, packedOverlayIn);
            }
        }

        stack.popPose();
    }

    private void renderRecursivelyGiganto(Entity entity, GeoBone bone, PoseStack stack, MultiBufferSource renderBuffer, int packedLightIn,
                                          int packedOverlayIn) {

        stack.pushPose();
        RenderUtils.translate(bone, stack);
        RenderUtils.moveToPivot(bone, stack);
        RenderUtils.rotate(bone, stack);
        RenderUtils.scale(bone, stack);
        RenderUtils.moveBackFromPivot(bone, stack);
        if(entity instanceof EntityBaseDinosaurAnimal entityBaseDinosaurAnimal) {
            if (bone.getName().equals("Arm1") && entityBaseDinosaurAnimal.isTrading() && entityBaseDinosaurAnimal instanceof EntityGigantopithicus) {
                stack.pushPose();
                //You'll need to play around with these to get item to render in the correct orientation
                stack.mulPose(Vector3f.XP.rotationDegrees(-45));
                stack.mulPose(Vector3f.YP.rotationDegrees(0));
                stack.mulPose(Vector3f.ZP.rotationDegrees(0));

                //You'll need to play around with this to render the item in the correct spot.
                stack.translate(1.55D, 0.1D, -0.75D);
                //Sets the scaling of the item.
                stack.scale(2.5f, 2.5f, 2.5f);

                //stack.mulPose(Vector3f.XP.rotationDegrees(25));
                stack.mulPose(Vector3f.YP.rotationDegrees(180));
                //stack.mulPose(Vector3f.ZP.rotationDegrees(0));

                // Change mainHand to predefined Itemstack and TransformType to what transform you would want to use.
                Minecraft.getInstance().getItemRenderer().renderStatic(mainHand, ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND,
                        packedLightIn, packedOverlayIn, stack, renderBuffer, entity.getId());
                stack.popPose();
                //Stops unnecessary further recursive method calling. Only works if I am rendering one thing per layer.
                stack.popPose();
                return;
            }
        }


        if (!bone.isHidden) {
            for (GeoBone childBone : bone.childBones) {
                renderRecursivelyGiganto(entity, childBone, stack, renderBuffer, packedLightIn, packedOverlayIn);
            }
        }

        stack.popPose();
    }
}