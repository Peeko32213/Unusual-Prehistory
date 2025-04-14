package com.peeko32213.unusualprehistory.common.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class UPFood {
    public static final FoodProperties GINKGO_FRUIT = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.1F).meat().effect(new MobEffectInstance(MobEffects.CONFUSION, 600, 1), 1.0F).effect(new MobEffectInstance(MobEffects.WEAKNESS, 600, 1), 1.0F).build();
    public static final FoodProperties DRYO_NUTS = new FoodProperties.Builder().nutrition(1).saturationMod(1.2F).effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400, 0), 0.8F).alwaysEat().fast().build();

    public static final FoodProperties RAW_GINKGO_SEEDS = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.2F).meat().fast().effect(new MobEffectInstance(MobEffects.CONFUSION, 300, 1), 1.0F).effect(new MobEffectInstance(MobEffects.WEAKNESS, 300, 1), 1.0F).build();
    public static final FoodProperties COOKED_GINKGO_SEEDS = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.6F).meat().fast().build();

    public static final FoodProperties LEEDS_CAVIAR = (new FoodProperties.Builder()).nutrition(7).saturationMod(0.4F).meat().build();
    public static final FoodProperties MAMMOTH_MEATBALL = (new FoodProperties.Builder()).nutrition(14).saturationMod(0.7F).meat().build();

    // Special
    public static final FoodProperties AMBER_GUMMY = (new FoodProperties.Builder()).nutrition(3).saturationMod(2.2F).alwaysEat().fast().build();

    // Loot Fruits
    public static final FoodProperties RED_FRUIT = (new FoodProperties.Builder()).nutrition(5).saturationMod(0.6F).build();
    public static final FoodProperties WHITE_FRUIT = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.65F).build();
    public static final FoodProperties YELLOW_FRUIT = (new FoodProperties.Builder()).nutrition(7).saturationMod(0.7F).build();
    public static final FoodProperties BLUE_FRUIT = (new FoodProperties.Builder()).nutrition(8).saturationMod(0.75F).build();

    public static final FoodProperties DEFROSTED_FOSSIL = (new FoodProperties.Builder())
            .alwaysEat()
            .effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, 200, 1), 0.8F)
            .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 200, 0), 0.8F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 0.8F)
            .nutrition(2)
            .saturationMod(0.1F)
            .build();

    // Grogs
    public static final FoodProperties GROG = (new FoodProperties.Builder())
            .alwaysEat()
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 1200, 0), 0.34F)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 0), 0.33F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 1200, 0), 0.33F)
            .nutrition(5)
            .saturationMod(0.3F)
            .build();
}
