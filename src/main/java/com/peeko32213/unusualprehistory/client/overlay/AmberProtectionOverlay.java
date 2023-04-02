package com.peeko32213.unusualprehistory.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.client.ClientAmberProtectionData;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class AmberProtectionOverlay {
    private static final ResourceLocation AMBER_PROTECTION_TEXTURE = new ResourceLocation(UnusualPrehistory.MODID, "textures/amber_protection/amber_protection.png");
    private static final ResourceLocation TEST_TEXTURE = new ResourceLocation("minecraft", "textures/mob_effect/absorption.png");
    private static final int MAX_PROTECTION_LEVELS = 10;
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

        RenderSystem.setShaderTexture(0, TEST_TEXTURE);
        for (int level = 0; level < MAX_PROTECTION_LEVELS && shouldBlit && !isCreative; level++) {
            int blitX = centerX - 91 + (level * BLIT_SPACING);
            int blitY = bottomY - 39;

            if (level < numProtectionLevels) {
                gui.blit(poseStack, blitX, blitY, 0, 0, BLIT_SIZE, BLIT_SIZE, BLIT_SIZE, BLIT_SIZE);
            } else {
                break;
            }
        }
    };

}
