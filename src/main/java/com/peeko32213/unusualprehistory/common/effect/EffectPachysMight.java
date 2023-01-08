package com.peeko32213.unusualprehistory.common.effect;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class EffectPachysMight extends MobEffect {

    public EffectPachysMight() {
        super(MobEffectCategory.BENEFICIAL, 0Xc61f1f);
        this.setRegistryName(UnusualPrehistory.MODID, "pachys_might");
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