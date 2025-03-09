package com.peeko32213.unusualprehistory.client.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.peeko32213.unusualprehistory.common.item.PrehistoricEggItem;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class PrehistoricEggItemRenderer extends BlockEntityWithoutLevelRenderer {

    private final ItemRenderer itemRenderer;


    public PrehistoricEggItemRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet, final ItemRenderer itemRenderer) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
        this.itemRenderer = itemRenderer;
    }


    @Override
    public void renderByItem(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        super.renderByItem(pStack, pDisplayContext, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
    }




    public boolean renderItemStack(ItemStack pStack, ItemDisplayContext pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        // validate item stack
        if(pStack.isEmpty() || !pStack.hasTag() || !pStack.getTag().contains(AxolootlEntity.KEY_VARIANT_ID, Tag.TAG_STRING) || !(pStack.getItem() instanceof PrehistoricEggItem)) {
            return false;
        }
        // load variant
        final ResourceLocation variantId = new ResourceLocation(pStack.getTag().getString(AxolootlEntity.KEY_VARIANT_ID));
        // load model
        final ResourceLocation modelId = AxolootlBucketItemModelLoader.getModelForVariant(variantId);
        final BakedModel model = Minecraft.getInstance().getModelManager().getModel(modelId);
        // prepare item model
        final VertexConsumer vertexconsumer = ItemRenderer.getFoilBuffer(pBuffer, RenderType.cutout(), true, pStack.hasFoil());
        // render item model
        itemRenderer.renderModelLists(model, pStack, pPackedLight, pPackedOverlay, pPoseStack, vertexconsumer);
        // all checks passed
        return true;
    }
}
