package com.peeko32213.unusualprehistory.common.effect;

import com.peeko32213.unusualprehistory.common.capabilities.UPCapabilities;
import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.JarateFindWaterGoal;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class EffectRabies extends MobEffect {
    public EffectRabies() {
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

                //System.out.println(capability.playersRabiesHadTime);
                capability.playersRabiesHadTime += 1;
                //increase tick
            });

        } else {
            pLivingEntity.getCapability(UPCapabilities.ANIMAL_CAPABILITY).ifPresent(capability -> {
                //System.out.println(capability.entityRabiesHadTime);
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