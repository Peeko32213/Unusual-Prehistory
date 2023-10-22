package com.peeko32213.unusualprehistory.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.peeko32213.unusualprehistory.common.screen.DNAFridgeMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

//Vanilla copy
public class DNAFridgeScreen extends AbstractContainerScreen<DNAFridgeMenu> implements MenuAccess<DNAFridgeMenu> {
        private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation("textures/gui/container/generic_54.png");
        private final int containerRows;

        public DNAFridgeScreen(DNAFridgeMenu p_98409_, Inventory p_98410_, Component p_98411_) {
                super(p_98409_, p_98410_, p_98411_);
                //this.passEvents = false;
                int i = 222;
                int j = 114;
                this.containerRows = p_98409_.getRowCount();
                this.imageHeight = 114 + this.containerRows * 18;
                this.inventoryLabelY = this.imageHeight - 94;
        }

        public void render(GuiGraphics p_98418_, int p_98419_, int p_98420_, float p_98421_) {
                this.renderBackground(p_98418_);
                super.render(p_98418_, p_98419_, p_98420_, p_98421_);
                this.renderTooltip(p_98418_, p_98419_, p_98420_);
        }

        @Override
        protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, CONTAINER_BACKGROUND);
                int i = (this.width - this.imageWidth) / 2;
                int j = (this.height - this.imageHeight) / 2;
                pGuiGraphics.blit(CONTAINER_BACKGROUND, i, j, 0, 0, this.imageWidth, this.containerRows * 18 + 17);
                pGuiGraphics.blit(CONTAINER_BACKGROUND, i, j + this.containerRows * 18 + 17, 0, 126, this.imageWidth, 96);
        }

}

