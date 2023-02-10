package com.peeko32213.unusualprehistory.common.effect;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.Vec3;

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