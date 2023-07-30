package com.peeko32213.unusualprehistory.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.client.ClientAmberProtectionData;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Min;

public class TarOverlay {
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
    public static final IGuiOverlay TAR_OVERLAY = (gui, poseStack, partialTick, width, height) -> {
        // Calculate the center of the screen and the bottom of the screen
        int centerX = width / 2;
        int bottomY = height;


        // Determine whether the tar overlay should be rendered
        Player player = Minecraft.getInstance().player;
        boolean isCreative = player.isCreative();
        Level level = Minecraft.getInstance().level;

        BlockState state = level.getBlockState(new BlockPos(player.getEyePosition()));
        boolean isTar = state.is(UPBlocks.TAR.get());
        // Calculate the initial position for the first heart icon
        int blitX = centerX - 90;
        int blitY = bottomY - 90;
        int xOffset = 0;
        if (isTar) {
            //RenderSystem.setShaderTexture(0, AMBER_PROTECTION_HALF_TEXTURE);
            //gui.blit(poseStack, blitX, blitY, 0, 0, blitX, blitY, blitX, blitY);
            renderTextureOverlay(AMBER_PROTECTION_HALF_TEXTURE, 1, height, width);
        }


    };

    protected static void renderTextureOverlay(ResourceLocation pTextureLocation, float pAlpha, int screenHeight, int screenWidth) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, pAlpha);
        RenderSystem.setShaderTexture(0, pTextureLocation);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(0.0D, (double)screenHeight, -90.0D).uv(0.0F, 1.0F).endVertex();
        bufferbuilder.vertex((double)screenWidth, (double)screenHeight, -90.0D).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex((double)screenWidth, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
        tesselator.end();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}









