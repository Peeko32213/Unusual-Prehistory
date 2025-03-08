package com.peeko32213.unusualprehistory.common.entity.util.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.WaterAnimal;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class ShoalHunterTargetingGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    private final WaterAnimal tamableMob;

    public ShoalHunterTargetingGoal(WaterAnimal tamableAnimal, Class<T> clazz, int chance, boolean seeCheck, boolean reachCheck, @Nullable Predicate<LivingEntity> entityPredicate) {
        super(tamableAnimal, clazz, chance, seeCheck, reachCheck, entityPredicate);
        this.tamableMob = tamableAnimal;
    }

    public boolean canUse() {
        return super.canUse();
    }

    public boolean canContinueToUse() {
        return this.targetConditions != null ? this.targetConditions.test(this.mob, this.target) : super.canContinueToUse();
    }
}
