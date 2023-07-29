package com.peeko32213.unusualprehistory.client.event;

import com.mojang.blaze3d.shaders.FogShape;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.client.overlay.AmberProtectionOverlay;
import com.peeko32213.unusualprehistory.client.render.IncubatorBlockEntityRenderer;
import com.peeko32213.unusualprehistory.client.render.armor.*;
import com.peeko32213.unusualprehistory.client.render.block.CultivatorBlockEntityRenderer;
import com.peeko32213.unusualprehistory.client.render.dinosaur_renders.*;
import com.peeko32213.unusualprehistory.client.render.trail.EntityTrailRenderer;
import com.peeko32213.unusualprehistory.client.screen.AnalyzerScreen;
import com.peeko32213.unusualprehistory.client.screen.CultivatorScreen;
import com.peeko32213.unusualprehistory.client.screen.DNAFridgeScreen;
import com.peeko32213.unusualprehistory.common.block.entity.FruitLootBoxEntity;
import com.peeko32213.unusualprehistory.common.config.UnusualPrehistoryConfig;
import com.peeko32213.unusualprehistory.common.item.armor.ItemAustroBoots;
import com.peeko32213.unusualprehistory.common.item.armor.ItemMajungaHelmet;
import com.peeko32213.unusualprehistory.common.item.armor.ItemTyrantsCrown;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleBoots;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleChestplate;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleHelmet;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleLeggings;
import com.peeko32213.unusualprehistory.core.registry.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Min;

import java.awt.event.KeyEvent;

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
        if(blockState.is(UPBlocks.TAR.get()) && event.getMode().equals(FogRenderer.FogMode.FOG_TERRAIN))
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
        boolean insideTar = blockState.is(UPBlocks.TAR.get());
        if(insideTar)
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
