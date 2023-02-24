package com.peeko32213.unusualprehistory.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EffectBrachisProtection extends MobEffect {

    public EffectBrachisProtection() {
        super(MobEffectCategory.BENEFICIAL, 0X5e6f9a);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        entity.resetFallDistance();
    }



    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }

    public String getDescriptionId() {
        return "unusualprehistory.potion.brachi_protection";
    }


}