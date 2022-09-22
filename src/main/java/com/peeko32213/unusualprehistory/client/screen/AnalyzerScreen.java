package com.peeko32213.unusualprehistory.client.screen;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.screen.AnalyzerMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AnalyzerScreen extends AbstractContainerScreen<AnalyzerMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(UnusualPrehistory.MODID, "textures/gui/analyzer_gui.png");

    private static final int SCREEN_WIDTH = 176;
    private static final int SCREEN_HEIGHT = 166;

    private static final int LEFT_PROGRESS_X = 16;
    private static final int LEFT_PROGRESS_Y = 25;
    private static final int RIGHT_PROGRESS_X = 149;
    private static final int RIGHT_PROGRESS_Y = LEFT_PROGRESS_Y;
    private static final int PROGRESS_WIDTH = 11;
    private static final int PROGRESS_HEIGHT = 44;

    public AnalyzerScreen(AnalyzerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        imageWidth = SCREEN_WIDTH;
        imageHeight = SCREEN_HEIGHT;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        Lighting.setupForFlatItems();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        // render background image
        this.blit(pPoseStack, leftPos, topPos, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        // render progress bars
        if(menu.isCrafting()) {
            int vHeight = menu.getScaledProgress(PROGRESS_HEIGHT);
            int blitY = PROGRESS_HEIGHT - vHeight;
            blit(pPoseStack, leftPos + LEFT_PROGRESS_X, topPos + LEFT_PROGRESS_Y + blitY, 176, blitY, PROGRESS_WIDTH, vHeight);
            blit(pPoseStack, leftPos + RIGHT_PROGRESS_X, topPos + RIGHT_PROGRESS_Y + blitY, 187, blitY, PROGRESS_WIDTH, vHeight);
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
