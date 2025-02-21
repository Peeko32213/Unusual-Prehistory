package com.peeko32213.unusualprehistory.common.effect;

import com.peeko32213.unusualprehistory.common.capabilities.UPCapabilities;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class RampageEffect extends MobEffect {
    public RampageEffect() {
        super(MobEffectCategory.HARMFUL, 6685988);
    }
    public static final UUID RABIES_UUID = UUID.fromString("fb0dea16-48c8-4968-8a01-9e12c2d6f381");
    private int duration = 0;
    private final int color = 6685988;
    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        //TODO: Players have unique parameters against humans. Namely, they will take damage more often the longer they are infected.
        //TODO: Players will randomly attack, more often the longer they are infected.
        //TODO: Afflicted will be unable to drink or dive, and they will take damage upon entering water. Splash and lingering still works.

        if (!pLivingEntity.level().isClientSide && pLivingEntity instanceof ServerPlayer serverPlayer) {
            serverPlayer.getCapability(UPCapabilities.PLAYER_CAPABILITY).ifPresent(capability -> {

                double probability = -(Math.exp(-capability.playersRabiesHadTime*0.00001) - 1);
                double hitRand = Math.random();
                double dmgRand = Math.random();
                double headRand = Math.random();
                int dmg = (int) (5*Math.random());

                if (hitRand < probability) {
                    ((Player) pLivingEntity).swing(InteractionHand.MAIN_HAND);
                    //players randomly attacks
                }
                if (dmgRand < probability) {
                    pLivingEntity.hurt(pLivingEntity.damageSources().generic(), dmg);
                    //deals a random amount of damage between 0 and 10 to player
                }
                if (headRand < probability) {
                    pLivingEntity.setYRot((pLivingEntity).yHeadRotO += (15*Math.random()));
                    //player's head randomly twitches
                }

                System.out.println(capability.playersRabiesHadTime);
                capability.playersRabiesHadTime += 1;
                //increase tick
            });

        } else {
            pLivingEntity.getCapability(UPCapabilities.ANIMAL_CAPABILITY).ifPresent(capability -> {
                System.out.println(capability.entityRabiesHadTime);
                capability.entityRabiesHadTime += 1;
            });
        }

        if (pLivingEntity.isInWaterRainOrBubble()) {
            pLivingEntity.hurt(pLivingEntity.damageSources().generic(), 0);
            pLivingEntity.setDeltaMovement(pLivingEntity.getDeltaMovement().x, Math.min(0, pLivingEntity.getDeltaMovement().y), pLivingEntity.getDeltaMovement().z);
            //cannot dive
        }
        //randomly toss the entity's head
        duration ++;
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        //btw this function is necessary for an effect to work

        return true;
        //effect will never expire
    }

    public String getDescriptionId() {
        return "unusualprehistory.potion.rabies";
    }
}