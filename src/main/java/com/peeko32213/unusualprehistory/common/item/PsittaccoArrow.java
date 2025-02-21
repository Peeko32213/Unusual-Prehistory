package com.peeko32213.unusualprehistory.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PsittaccoArrow extends ArrowItem {
    public PsittaccoArrow(Properties pProperties) {
        super(pProperties);
    }

    public @NotNull AbstractArrow createArrow(@NotNull Level level, @NotNull ItemStack stack, @NotNull LivingEntity entity) {
        return new com.peeko32213.unusualprehistory.common.entity.msc.projectile.PsittaccoArrow(entity, level);
    }

    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pFlag) {
        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("item.unusualprehistory.psittacco_arrow.flavor_text").withStyle(ChatFormatting.DARK_PURPLE).withStyle(ChatFormatting.ITALIC));
        } else {
            pTooltipComponents.add(Component.translatable("item.unusualprehistory.psittacco_arrow.hover_info").withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
