package com.peeko32213.unusualprehistory.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

public class HealthReductionEffect extends MobEffect {
    public HealthReductionEffect() {
        super(MobEffectCategory.HARMFUL, 8171462);
        addAttributeModifier(Attributes.MAX_HEALTH, "04448cbf-ee2c-4f36-b71f-e641a312834a", -2f, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entityLivingBaseIn, int amplifier) {
    }
}