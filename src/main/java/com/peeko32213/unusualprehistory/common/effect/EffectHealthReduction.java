package com.peeko32213.unusualprehistory.common.effect;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class EffectHealthReduction extends MobEffect {
    public EffectHealthReduction() {
        super(MobEffectCategory.HARMFUL, 8171462);
    }
    public static final UUID HP_MOD_UUID = UUID.fromString("d253dba8-41b5-11ee-be56-0242ac120002");
    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        double hpMin = 1 + pAmplifier;
        //AttributeModifier attributeModifier = new AttributeModifier(HP_MOD_UUID, "hpmod", -hpMin, AttributeModifier.Operation.ADDITION);
        //UnusualPrehistory.LOGGER.info("What am I ?" + attributeModifier.getAmount() + " hpMin ");
        //if(!pLivingEntity.getAttributes().hasModifier(Attributes.MAX_HEALTH, HP_MOD_UUID) ) {
        //    pLivingEntity.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(attributeModifier);
        //} else if(attributeModifier.getAmount() != hpMin){
        //    pLivingEntity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(-hpMin);
        //}

        if(pLivingEntity.getHealth() > (pLivingEntity.getMaxHealth() - hpMin)){
            pLivingEntity.setHealth((float) (pLivingEntity.getMaxHealth() - hpMin));
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration > 0;
    }
}
