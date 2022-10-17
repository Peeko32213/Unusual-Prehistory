package com.peeko32213.unusualprehistory.common.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ItemModFood {

    //Raw
    public static final FoodProperties RAW_STETHA = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.1F).meat().fast().build();
    public static final FoodProperties RAW_COTY = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.3F).meat().build();

    //Cooked
    public static final FoodProperties COOKED_STETHA = (new FoodProperties.Builder()).nutrition(5).saturationMod(0.4F).meat().fast().build();
    public static final FoodProperties COOKED_COTY = (new FoodProperties.Builder()).nutrition(10).saturationMod(1.1F).meat().build();


}
