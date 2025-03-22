package com.peeko32213.unusualprehistory.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.client.ClientAmberProtectionData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class AmberProtectionOverlay {
    // The textures used to render the amber protection icons
    private static final ResourceLocation AMBER_PROTECTION_TEXTURE = new ResourceLocation(UnusualPrehistory.MODID, "textures/amber_protection/amber_protection.png");
    private static final ResourceLocation AMBER_PROTECTION_HALF_TEXTURE = new ResourceLocation(UnusualPrehistory.MODID, "textures/amber_protection/amber_protection_half.png");

    // The maximum number of protection levels that can be displayed in a single row
    private static final int MAX_PROTECTION_LEVELS_PER_ROW = 20;
    // The size of each heart icon
    private static final int BLIT_SIZE = 9;
    // The spacing between each heart icon in a row
    private static final int BLIT_SPACING = 8;
    // The vertical offset between each row of heart icons
    private static final int ROW_HEIGHT_OFFSET = 10;
    // The horizontal offset for each row of heart icons
    private static final int HEART_WIDTH_OFFSET = 80;

    // The overlay that renders the amber protection icons on the player's HUD
    public static final IGuiOverlay HUD_AMBER_PROTECTION = (gui, poseStack, partialTick, width, height) -> {
        // Calculate the center of the screen and the bottom of the screen
        int centerX = width / 2;
        int bottomY = height;

        // Get the current number of amber protection levels
        int numProtectionLevels = ClientAmberProtectionData.getAmberProtection();

        // Determine whether the amber protection icons should be rendered
        boolean shouldBlit = numProtectionLevels > 0;
        boolean isCreative = Minecraft.getInstance().player.isCreative();
        boolean isSpectator = Minecraft.getInstance().player.isSpectator();
        // Calculate the initial position for the first heart icon
        int blitX = centerX - 91;
        int blitY = bottomY - 39;
        int xOffset = 0;

        // Loop through each protection level and render the corresponding heart icon
        for (int level = 0; level <= numProtectionLevels && shouldBlit && !isCreative && !isSpectator; level++) {

            // Calculate the row, height, and offset for the current heart icon
            int rowMultpl = Mth.abs(level / MAX_PROTECTION_LEVELS_PER_ROW);
            blitY = blitY - (rowMultpl * ROW_HEIGHT_OFFSET);
            xOffset = (HEART_WIDTH_OFFSET * rowMultpl);

            // Calculate the horizontal position for the current heart icon
            if (level % 2 == 0) {
                blitX = centerX - 91 + ((level / 2) * BLIT_SPACING) - xOffset;
            }

            // Render the current heart icon, half heart of full heart
            if (level < numProtectionLevels) {
                if (level % 2 == 0) {
                    renderTextureOverlay(poseStack, AMBER_PROTECTION_HALF_TEXTURE,1,blitX, blitY, BLIT_SIZE, BLIT_SIZE);
                } else {
                    renderTextureOverlay(poseStack, AMBER_PROTECTION_TEXTURE, 1, blitX, blitY,  BLIT_SIZE, BLIT_SIZE);
                }

            } else {
                break;
            }
        }
    };


    protected static void renderTextureOverlay(GuiGraphics pGuiGraphics, ResourceLocation pShaderLocation, float pAlpha, int x , int y, int sizeX, int sizeY) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, pAlpha);
        pGuiGraphics.blit(pShaderLocation, x, y, -90, 0.0F, 0.0F, sizeX, sizeY, sizeX, sizeY);
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}









