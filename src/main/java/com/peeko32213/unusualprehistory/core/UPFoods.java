package com.peeko32213.unusualprehistory.core;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class UPFoods {
    public static final FoodProperties DRYO_NUTS = new FoodProperties.Builder()
            .nutrition(1)
            .saturationMod(1.2F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400, 0), 0.8F)
            .alwaysEat()
            .fast()
            .build();
}
