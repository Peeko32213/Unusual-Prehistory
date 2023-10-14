/*package com.peeko32213.unusualprehistory.core.registry;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class UPDamageTypes {
    public static final DamageSource causeHwachaDamage(LivingEntity attacker){
        return new DamageSourceRandomMessages("hwacha", attacker).setScalesWithDifficulty().bypassArmor().setMagic().setProjectile();
    }

    private static class DamageSourceRandomMessages extends EntityDamageSource {

        public DamageSourceRandomMessages(String message, Entity entity) {
            super(message, entity);
        }

        @Override
        public Component getLocalizedDeathMessage(LivingEntity attacked) {
            int type = attacked.getRandom().nextInt(3);
            LivingEntity livingentity = attacked.getKillCredit();
            String s = "death.attack." + this.msgId + "_" + type;
            String s1 = s + ".player";
            return livingentity != null ? Component.translatable(s1, attacked.getDisplayName(), livingentity.getDisplayName()) : Component.translatable(s, attacked.getDisplayName());
        }
    }
}*/
