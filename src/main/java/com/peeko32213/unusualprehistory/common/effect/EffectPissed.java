package com.peeko32213.unusualprehistory.common.effect;

import com.peeko32213.unusualprehistory.common.entity.msc.util.JarateFindWaterGoal;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class EffectPissed extends MobEffect {
    public EffectPissed() {
        super(MobEffectCategory.HARMFUL, 16646010);
    }
    public static final UUID PISS_UUID = UUID.fromString("9fd631b8-dbfa-477f-963a-e9a14c6b92f4");
    private int duration = 0;
    private final int color = 16646010;

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        //TODO: Forces pLivingEntity to find the closest water and get into it
        if (pLivingEntity.isInWaterRainOrBubble()) {
            pLivingEntity.removeEffect(UPEffects.PISSED_UPON.get());
        }

        if (pLivingEntity instanceof PathfinderMob) {
            ((PathfinderMob) pLivingEntity).setTarget(null);
        }

        duration ++;
    }

    private boolean checkContainGoal(Set<WrappedGoal> availableGoals) {
        WrappedGoal[] arring = availableGoals.toArray(new WrappedGoal[0]);

        for (int i = 0; i < arring.length; i++) {
            if (arring[i].getGoal() instanceof JarateFindWaterGoal) {
                return true;
            }
        }

        return false;
        //remember this check also happens clientside and if that's the case it returns false.
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {

        this.duration = pDuration;
        return pDuration > 0;
    }

    public String getDescriptionId() {
        return "unusualprehistory.potion.pissed_upon";
    }
}