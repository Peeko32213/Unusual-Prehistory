package com.peeko32213.unusualprehistory.client.screen;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.screen.CultivatorMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CultivatorScreen extends AbstractContainerScreen<CultivatorMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(UnusualPrehistory.MODID, "textures/gui/cultivator_gui.png");

    private static final int SCREEN_WIDTH = 176;
    private static final int SCREEN_HEIGHT = 166;

    private static final int PROGRESS_X = 65;
    private static final int PROGRESS_Y = 29;
    private static final int PROGRESS_WIDTH = 48;
    private static final int PROGRESS_HEIGHT = 20;

    private static final int FUEL_X = 100;
    private static final int FUEL_Y = 66;
    private static final int FUEL_WIDTH = 44;
    private static final int FUEL_HEIGHT = 11;

    public CultivatorScreen(CultivatorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
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
        this.renderBackground(pPoseStack);
        Lighting.setupForFlatItems();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        // render background image
        this.blit(pPoseStack, leftPos, topPos, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        // render progress bar
        if(menu.isCrafting()) {
            int uWidth = menu.getScaledProgress(PROGRESS_WIDTH);
            blit(pPoseStack, leftPos + PROGRESS_X, topPos + PROGRESS_Y, 176, 0, uWidth, PROGRESS_HEIGHT);
        }
        // render fuel
        int uWidth = menu.getScaledFuel(FUEL_WIDTH);
        blit(pPoseStack, leftPos + FUEL_X, topPos + FUEL_Y, 176, 20, uWidth, FUEL_HEIGHT);
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
