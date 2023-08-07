package com.peeko32213.unusualprehistory.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EffectHealthReduction extends MobEffect {
    public EffectHealthReduction() {
        super(MobEffectCategory.HARMFUL, 8171462);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        float hpMin = 1 + pAmplifier;
        if(pLivingEntity.getHealth() > (pLivingEntity.getMaxHealth() - hpMin)){
            pLivingEntity.setHealth(pLivingEntity.getMaxHealth() - hpMin);
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration > 0;
    }
}
