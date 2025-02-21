package com.peeko32213.unusualprehistory.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class PachysMightEffect extends MobEffect {

    public PachysMightEffect() {
        super(MobEffectCategory.BENEFICIAL, 0Xc61f1f);
        this.addAttributeModifier(Attributes.ATTACK_KNOCKBACK, "03C3C89D-7037-4B42-869F-B146BCB64D2F", 1.5D, AttributeModifier.Operation.ADDITION);
    }

    public void applyEffectTick(LivingEntity LivingEntityIn, int amplifier) {
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }

    public String getDescriptionId() {
        return "unusualprehistory.potion.pachys_might";
    }

}