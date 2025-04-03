package com.peeko32213.unusualprehistory.common.effect;

import com.peeko32213.unusualprehistory.common.capabilities.UPCapabilities;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
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
        //Players will take damage more often the longer they are infected
        //Players will randomly swing their arms, more often the longer they are infected
        //Players will randomly shake their head

        if (pLivingEntity.isDeadOrDying() && pLivingEntity.tickCount%5 == 0) {
            pLivingEntity.level().playSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), SoundEvents.GLOW_SQUID_HURT, SoundSource.NEUTRAL, 0.5F, 1F / (pLivingEntity.level().getRandom().nextFloat() * 0.4F + 0.8F));
            pLivingEntity.level().playSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), SoundEvents.SLIME_BLOCK_BREAK, SoundSource.NEUTRAL, 0.5F, 1F / (pLivingEntity.level().getRandom().nextFloat() * 0.4F + 0.8F));

            if (!pLivingEntity.level().isClientSide) {
                //this makes the potion effect cloud
                AreaEffectCloud areaeffectcloud = new AreaEffectCloud(pLivingEntity.level(), pLivingEntity.getX(), pLivingEntity.getY() + 0.2F, pLivingEntity.getZ());
                areaeffectcloud.setFixedColor(6685988);
                areaeffectcloud.setRadius(4F);
                areaeffectcloud.setDuration(1);
                areaeffectcloud.addEffect(new MobEffectInstance(UPEffects.YIXIAN_RAMPAGE.get(), -1));

                pLivingEntity.level().addFreshEntity(areaeffectcloud);
            }
        }

        if (!pLivingEntity.level().isClientSide && pLivingEntity instanceof ServerPlayer serverPlayer) {
            serverPlayer.getCapability(UPCapabilities.PLAYER_CAPABILITY).ifPresent(capability -> {

                double probability = -(Math.exp(-capability.playersRabiesHadTime*0.00001) - 1);
                double hitRand = Math.random();
                double dmgRand = Math.random();
                double headRand = Math.random();
                int dmg = (int) (5*Math.random());

                if (hitRand < probability) {

                    //players randomly attacks
                }
                if (dmgRand < probability) {
                    pLivingEntity.hurt(pLivingEntity.damageSources().generic(), dmg);
                    //deals a random amount of damage between 0 and 10 to player
                }
                if (headRand < probability) {
                    pLivingEntity.setYRot((pLivingEntity).yHeadRotO += (float) (15*Math.random()));
                    //player's head randomly twitches
                }

                capability.playersRabiesHadTime += 1;
                //increase tick
            });

        } else {
            pLivingEntity.getCapability(UPCapabilities.ANIMAL_CAPABILITY).ifPresent(capability -> {
                capability.entityRabiesHadTime += 1;
            });
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

    public @NotNull String getDescriptionId() {
        return "unusualprehistory.potion.rampage";
    }
}