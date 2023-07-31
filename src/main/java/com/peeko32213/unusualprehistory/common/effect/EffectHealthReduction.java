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
        if(pLivingEntity.getHealth() > (pLivingEntity.getMaxHealth() - pAmplifier)){
            pLivingEntity.setHealth(pLivingEntity.getMaxHealth() - pAmplifier);
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration > 0;
    }

    //public static final UUID HEALTH_MOD = UUID.fromString("c4a0be3e-0242-11ee-be56-0242ac120002");
//
    //public static final AttributeModifier HEALTH_MODIFIER = new AttributeModifier(HEALTH_MOD, "Health Modifier", -10F, AttributeModifier.Operation.ADDITION);
//
    //public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
    //    pLivingEntity.getAttribute(Attributes.MAX_HEALTH).removeModifier(HEALTH_MODIFIER);
    //    super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    //}
//
    //public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
    //    pLivingEntity.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier(HEALTH_MOD, "Health Modifier", -1F*pAmplifier, AttributeModifier.Operation.ADDITION));
    //    super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    //}
}
