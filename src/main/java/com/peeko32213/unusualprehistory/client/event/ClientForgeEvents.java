package com.peeko32213.unusualprehistory.client.event;

import com.mojang.blaze3d.shaders.FogShape;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class ClientForgeEvents {
    final static Minecraft minecraft = Minecraft.getInstance();

    @SubscribeEvent
    public static void renderFog(ViewportEvent.RenderFog event)
    {
        if(minecraft == null) return;
        Player player = minecraft.player;
        Level level = player.level;
        Vec3 vec3 = player.getEyePosition();
        BlockState blockState = level.getBlockState(new BlockPos(vec3));
        boolean isCreative = player.isCreative();
        boolean isSpectator = player.isSpectator();
        if(blockState.is(UPBlocks.TAR.get()) && event.getMode().equals(FogRenderer.FogMode.FOG_TERRAIN) && !isCreative && !isSpectator)
        {
            event.setFarPlaneDistance(1.3F);
            event.setNearPlaneDistance(0.5F);
            event.setFogShape(FogShape.SPHERE);
            event.setCanceled(true);
        }
    }

    private static final float[] spoopColors = new float[3];
    private static float spoopColor = 0F;


    @SubscribeEvent
    public static void fogColors(ViewportEvent.ComputeFogColor event) {
        if(minecraft == null) return;
        Player player = minecraft.player;
        Level level = player.level;
        Vec3 vec3 = player.getEyePosition();
        BlockState blockState = level.getBlockState(new BlockPos(vec3));
        boolean isCreative = player.isCreative();
        boolean isSpectator = player.isSpectator();
        boolean insideTar = blockState.is(UPBlocks.TAR.get());
        if(insideTar && !isCreative && !isSpectator)
        {
            final float[] realColors = {event.getRed(), event.getGreen(), event.getBlue()};
            final float[] lerpColors = {0.2F, 0.2F, 0.2F};
            for (int i = 0; i < 3; i++) {
                final float real = realColors[i];
                final float spoop = lerpColors[i];
                final boolean inverse = real > spoop;
                spoopColors[i] = real == spoop ? spoop : Mth.clampedLerp(inverse ? spoop : real, inverse ? real : spoop, spoopColor);
            }
            float shift = (float) (0.01F * event.getPartialTick());
            if (insideTar)
                spoopColor += shift;
            else
                spoopColor -= shift;
            spoopColor = Mth.clamp(spoopColor, 0F, 1F);
            event.setRed(0.1F);
            event.setGreen(0.1F);
            event.setBlue(0.1F);
        }
    }



}
