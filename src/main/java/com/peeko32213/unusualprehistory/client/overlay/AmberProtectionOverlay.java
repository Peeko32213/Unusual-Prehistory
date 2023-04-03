package com.peeko32213.unusualprehistory.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.client.ClientAmberProtectionData;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class AmberProtectionOverlay {
    private static final ResourceLocation AMBER_PROTECTION_TEXTURE = new ResourceLocation(UnusualPrehistory.MODID, "textures/amber_protection/amber_protection.png");
    private static final ResourceLocation AMBER_PROTECTION_HALF_TEXTURE = new ResourceLocation(UnusualPrehistory.MODID, "textures/amber_protection/amber_protection_half.png");

    private static final ResourceLocation TEST_TEXTURE = new ResourceLocation("minecraft", "textures/mob_effect/absorption.png");
    //Should be equal to max health
    private static final int MAX_PROTECTION_LEVELS = 20;
    private static final int BLIT_SIZE = 9;
    private static final int BLIT_SPACING = 8;
    public static final IGuiOverlay HUD_AMBER_PROTECTION = (gui, poseStack, partialTick, width, height) -> {

        int centerX = width / 2;
        int bottomY = height;
        int numProtectionLevels = ClientAmberProtectionData.getAmberProtection();
        //If you want to debug put this on 10
        //int numProtectionLevels = 10;
        boolean shouldBlit = numProtectionLevels > 0;
        boolean isCreative = Minecraft.getInstance().player.isCreative();
        int blitX = centerX - 91;
        int blitY = bottomY - 39;

        //Should be equal to max health
        for (int level = 0; level < MAX_PROTECTION_LEVELS && shouldBlit && !isCreative; level++) {
            if(level % 2 == 0) {
                blitX = centerX - 91 + ((level/2) * BLIT_SPACING);
            }

            if (level < numProtectionLevels) {
                // Render a cracked heart over the first player heart
                    if (level % 2 == 0) {
                        RenderSystem.setShaderTexture(0, AMBER_PROTECTION_HALF_TEXTURE);
                        gui.blit(poseStack, blitX, blitY, 0, 0, BLIT_SIZE, BLIT_SIZE, BLIT_SIZE, BLIT_SIZE);

                    } else {
                        RenderSystem.setShaderTexture(0, AMBER_PROTECTION_TEXTURE);
                        gui.blit(poseStack, blitX, blitY, 0, 0, BLIT_SIZE, BLIT_SIZE, BLIT_SIZE, BLIT_SIZE);
                    }
            } else {
                break;
            }
        }


    };
}
