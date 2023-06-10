package com.peeko32213.unusualprehistory.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.peeko32213.unusualprehistory.client.screen.util.AbstractBookScreenOld;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EncyclopediaScreen extends AbstractBookScreenOld {
    private static final ResourceLocation ROOT = new ResourceLocation("unusualprehistory:book/encyclopedia/root.json");

    public EncyclopediaScreen(ItemStack bookStack) {
        super(bookStack, Component.translatable("encyclopedia.title"));
    }

    public EncyclopediaScreen(ItemStack bookStack, String page) {
        super(bookStack, Component.translatable("encyclopedia.title"));
        this.currentPageJSON = new ResourceLocation(this.getTextFileDirectory() + page + ".json");
    }

    public void render(PoseStack matrixStack, int x, int y, float partialTicks) {
        if(this.currentPageJSON == getRootPage() && currentPageCounter == 0){
            int k = (this.width - this.xSize) / 2;
            int l = (this.height - this.ySize + 128) / 2;
            RenderSystem.applyModelViewMatrix();
            PoseStack stack = RenderSystem.getModelViewStack();
            stack.pushPose();
            stack.translate((double)k, (double)l, 0.0D);
            stack.scale(2.75F, 2.75F, 2.75F);
            this.itemRenderer.renderGuiItem(new ItemStack(UPItems.AMMONITE_SHELL_ICON.get()), 25, 14);
            this.itemRenderer.blitOffset = 0.0F;
            stack.popPose();
            RenderSystem.applyModelViewMatrix();
        }
        super.render(matrixStack, x, y, partialTicks);
    }

    protected int getBindingColor() {
        return 0X6A3B16;
    }

    public ResourceLocation getRootPage() {
        return ROOT;
    }

    public String getTextFileDirectory() {
        return "unusualprehistory:book/encyclopedia/";
    }
}
