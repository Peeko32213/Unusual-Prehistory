package com.peeko32213.unusualprehistory.common.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFood {
    //Extra Raw
    public static final FoodProperties GINKGO_FRUIT = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.1F).meat().effect(new MobEffectInstance(MobEffects.CONFUSION, 600, 1), 1.0F).effect(new MobEffectInstance(MobEffects.WEAKNESS, 600, 1), 1.0F).build();
    public static final FoodProperties DRYO_NUTS = new FoodProperties.Builder().nutrition(1).saturationMod(1.2F).effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400, 0), 0.8F).alwaysEat().fast().build();

    //Raw
    public static final FoodProperties RAW_STETHA = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.1F).meat().fast().build();
    public static final FoodProperties RAW_COTY = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.3F).meat().build();
    public static final FoodProperties RAW_SCAU = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.3F).meat().build();
    public static final FoodProperties RAW_GINKGO_SEEDS = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.2F).meat().fast().effect(new MobEffectInstance(MobEffects.CONFUSION, 300, 1), 1.0F).effect(new MobEffectInstance(MobEffects.WEAKNESS, 300, 1), 1.0F).build();
    public static final FoodProperties RAW_AUSTRO = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.3F).meat().effect(new MobEffectInstance(MobEffects.HUNGER, 600, 1), 1.0F).build();
    public static final FoodProperties RAW_MAMMOTH = (new FoodProperties.Builder()).nutrition(5).saturationMod(0.3F).meat().build();


    //Cooked
    public static final FoodProperties COOKED_STETHA = (new FoodProperties.Builder()).nutrition(5).saturationMod(0.4F).meat().fast().build();
    public static final FoodProperties COOKED_COTY = (new FoodProperties.Builder()).nutrition(12).saturationMod(1.4F).meat().build();
    public static final FoodProperties COOKED_SCAU = (new FoodProperties.Builder()).nutrition(12).saturationMod(1.6F).meat().build();
    public static final FoodProperties COOKED_GINKGO_SEEDS = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.6F).meat().fast().build();
    public static final FoodProperties COOKED_AUSTRO = (new FoodProperties.Builder()).nutrition(12).saturationMod(1.2F).meat().build();
    public static final FoodProperties COOKED_MAMMOTH = (new FoodProperties.Builder()).nutrition(10).saturationMod(1.6F).meat().build();
    public static final FoodProperties MAMMOTH_MEATBALL = (new FoodProperties.Builder()).nutrition(20).saturationMod(10F).meat().build();

    //Special
    public static final FoodProperties GOLDEN_SCAU = (new FoodProperties.Builder()).nutrition(15).saturationMod(2.2F).meat().build();
    public static final FoodProperties AMBER_GUMMY = (new FoodProperties.Builder()).nutrition(2).saturationMod(2.2F).alwaysEat().fast().build();
    public static final FoodProperties MEATY_BUFFET = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.5F).meat().build();

    //Loot Fruits
    public static final FoodProperties RED_FRUIT = (new FoodProperties.Builder()).nutrition(5).saturationMod(0.4F).build();
    public static final FoodProperties WHITE_FRUIT = (new FoodProperties.Builder()).nutrition(7).saturationMod(0.8F).build();
    public static final FoodProperties YELLOW_FRUIT = (new FoodProperties.Builder()).nutrition(9).saturationMod(1.6F).build();
    public static final FoodProperties BLUE_FRUIT = (new FoodProperties.Builder()).nutrition(11).saturationMod(3.2F).build();

    //Special Food
    public static final FoodProperties DEFROSTED_FOSSIL = (new FoodProperties.Builder())
            .alwaysEat()
            .effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, 200, 1), 1F)
            .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 200, 1), 1F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 200, 1), 1F)
            .nutrition(15)
            .saturationMod(15F)
            .build();


    //Grogs
    public static final FoodProperties GROG = (new FoodProperties.Builder())
            .alwaysEat()
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 1200, 0), 0.5F)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 0), 0.5F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 1200, 0), 0.5F)
            .build();


}
