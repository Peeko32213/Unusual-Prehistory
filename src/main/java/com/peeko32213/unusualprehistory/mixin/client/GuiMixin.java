package com.peeko32213.unusualprehistory.mixin.client;

import com.peeko32213.unusualprehistory.client.render.gui.DebilitatingToxinHeartType;
import com.peeko32213.unusualprehistory.client.render.gui.DrainingVenomHeartType;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;

@Mixin(Gui.class)
public class GuiMixin {

    private static boolean drawForHeartType(Gui.HeartType type) {
        return type != Gui.HeartType.CONTAINER && type != Gui.HeartType.ABSORBING && type != Gui.HeartType.FROZEN;
    }

    private static boolean hasAnyCustomHearts(Player player) {
        if (player.hasEffect(UPEffects.HEALTH_REDUCTION.get())) {
            return true;
        }
        return player.hasEffect(UPEffects.PREVENT_CLICK.get());
    }

    @Inject(method = "renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIIZZ)V", at = @At("HEAD"), cancellable = true)
    private void renderHeart(GuiGraphics stack, Gui.HeartType __, int x, int y, int v, boolean blinking, boolean halfHeart, CallbackInfo cbi) {
        if (!blinking && drawForHeartType(__) && Minecraft.getInstance().cameraEntity instanceof Player player && hasAnyCustomHearts(player)) {

            DrainingVenomHeartType type1 = DrainingVenomHeartType.getType(player);
            DebilitatingToxinHeartType type2 = DebilitatingToxinHeartType.getType(player);

            if (type1 != null) {
                boolean hardcore = player.level().getLevelData().isHardcore();
                Pair<Integer, Integer> pos = type1.getHeartPos(hardcore);
                if (halfHeart) {
                    pos = type1.getHalfHeartPos(hardcore);
                }
                RenderSystem.setShaderTexture(0, DrainingVenomHeartType.ATLAS);
                stack.blit(DrainingVenomHeartType.ATLAS, x, y, pos.getLeft(), pos.getRight(), 9, 9, DrainingVenomHeartType.ATLAS_W, DrainingVenomHeartType.ATLAS_H);
                RenderSystem.setShaderTexture(0, Gui.GUI_ICONS_LOCATION);
                cbi.cancel();
            }
            if (type2 != null) {
                boolean hardcore = player.level().getLevelData().isHardcore();
                Pair<Integer, Integer> pos = type2.getHeartPos(hardcore);
                if (halfHeart) {
                    pos = type2.getHalfHeartPos(hardcore);
                }
                RenderSystem.setShaderTexture(0, DebilitatingToxinHeartType.ATLAS);
                stack.blit(DebilitatingToxinHeartType.ATLAS, x, y, pos.getLeft(), pos.getRight(), 9, 9, DebilitatingToxinHeartType.ATLAS_W, DebilitatingToxinHeartType.ATLAS_H);
                RenderSystem.setShaderTexture(0, Gui.GUI_ICONS_LOCATION);
                cbi.cancel();
            }
        }
    }
}
