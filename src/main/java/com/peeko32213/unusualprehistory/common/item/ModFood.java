package com.peeko32213.unusualprehistory.common.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFood {


    //Raw
    public static final FoodProperties RAW_STETHA = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.1F).meat().fast().build();
    public static final FoodProperties RAW_COTY = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.3F).meat().build();
    public static final FoodProperties RAW_SCAU = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.3F).meat().build();

    //Cooked
    public static final FoodProperties COOKED_STETHA = (new FoodProperties.Builder()).nutrition(5).saturationMod(0.4F).meat().fast().build();
    public static final FoodProperties COOKED_COTY = (new FoodProperties.Builder()).nutrition(12).saturationMod(1.4F).meat().build();

    public static final FoodProperties COOKED_SCAU = (new FoodProperties.Builder()).nutrition(12).saturationMod(1.6F).meat().build();


    //Special

    public static final FoodProperties GOLDEN_SCAU = (new FoodProperties.Builder()).nutrition(15).saturationMod(2.2F).meat().build();

    //Grogs
    public static final FoodProperties GROG = (new FoodProperties.Builder())
            .alwaysEat()
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 1200, 0), 0.5F)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 0), 0.5F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 1200, 0), 0.5F)
            .build();

}
