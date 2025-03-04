package com.peeko32213.unusualprehistory.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PsittaccoArrow extends ArrowItem {
    public PsittaccoArrow(Properties pProperties) {
        super(pProperties);
    }

    public @NotNull AbstractArrow createArrow(@NotNull Level level, @NotNull ItemStack stack, @NotNull LivingEntity entity) {
        return new com.peeko32213.unusualprehistory.common.entity.projectile.PsittaccoArrow(entity, level);
    }
}
