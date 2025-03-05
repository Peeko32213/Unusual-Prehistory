package com.peeko32213.unusualprehistory.common.entity.util.goal;

import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class PackHunterTargetingGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    private final PrehistoricEntity tamableMob;

    public PackHunterTargetingGoal(PrehistoricEntity tamableAnimal, Class<T> clazz, int chance, boolean seeCheck, boolean reachCheck, @Nullable Predicate<LivingEntity> entityPredicate) {
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
