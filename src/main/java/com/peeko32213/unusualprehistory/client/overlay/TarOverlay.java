package com.peeko32213.unusualprehistory.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class TarOverlay {
    private static final ResourceLocation AMBER_PROTECTION_HALF_TEXTURE = new ResourceLocation(UnusualPrehistory.MODID, "textures/overlay/tar_overlay.png");


    public static final IGuiOverlay TAR_OVERLAY = (gui, poseStack, partialTick, width, height) -> {

        // Determine whether the tar overlay should be rendered
        Player player = Minecraft.getInstance().player;
        boolean isCreative = player.isCreative();
        Level level = Minecraft.getInstance().level;

        BlockState state = level.getBlockState(new BlockPos(player.getEyePosition()));
        boolean isTar = state.is(UPBlocks.TAR.get());

        if (isTar) {
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









