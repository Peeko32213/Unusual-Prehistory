package com.peeko32213.unusualprehistory.common.effect;

import com.peeko32213.unusualprehistory.common.capabilities.UPCapabilities;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import java.util.UUID;

public class RampageRemedyEffect extends MobEffect {
    public RampageRemedyEffect() {
        super(MobEffectCategory.HARMFUL, 8837561);
    }

    public static final UUID RABIES_VACCINE_UUID = UUID.fromString("10d5920b-6d86-4db2-8562-dedbb59e812e");
    private final int color = 8837561;

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        //Comes into effect after 15 minutes(18000 ticks), removes all rabies. Before that, you can also milk away the rabies.

        if (!pLivingEntity.level().isClientSide && pLivingEntity instanceof ServerPlayer serverPlayer) {

            serverPlayer.getCapability(UPCapabilities.PLAYER_CAPABILITY).ifPresent(capability -> {
                if (capability.playerVaccinationTime >= 100 && pLivingEntity.hasEffect(UPEffects.RABIES.get())) {
                    pLivingEntity.removeEffect(UPEffects.RABIES.get());
                    capability.playersRabiesHadTime = 0;
                }//remove rabies after sufficent time has passed, and SET RABIES TIMER TO ZERO

                capability.playerVaccinationTime += 1;
                //increase tick
            });

        } else {
            pLivingEntity.getCapability(UPCapabilities.ANIMAL_CAPABILITY).ifPresent(capability -> {
                if (capability.entityVaccinationTime >= 100 && pLivingEntity.hasEffect(UPEffects.RABIES.get())) {
                    pLivingEntity.removeEffect(UPEffects.RABIES.get());
                    capability.entityRabiesHadTime = 0;
                }//remove rabies after sufficent time has passed, and SET RABIES TIMER TO ZERO

                capability.entityVaccinationTime += 1;
            });
        }
    }

    @Override
    public boolean isDurationEffectTick ( int pDuration, int pAmplifier){
        //btw this function is necessary for an effect to work
        //System.out.println(pDuration);
        return pDuration > 0;
        //effect will never expire
    }

    public String getDescriptionId () {
        return "unusualprehistory.potion.vaccine_rabies";
    }

}