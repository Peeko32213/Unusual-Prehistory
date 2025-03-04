package com.peeko32213.unusualprehistory.common.entity.animation;

import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.animatable.GeoEntity;

import java.util.EnumSet;
import java.util.function.Predicate;

public class SimpleAnimationAI<T extends GeoEntity> extends AnimationAI {



    public SimpleAnimationAI(T entity, AnimationHelper helper, int animTime) {
        super(entity, animTime, helper);
    }

    public SimpleAnimationAI(T entity, AnimationHelper helper, int animTime, int cooldownTime, boolean interruptsAI) {
        super(entity, animTime, cooldownTime,interruptsAI, helper);
    }

    public SimpleAnimationAI(T entity, AnimationHelper helper, int animTime, Predicate<LivingEntity> entityPredicate) {
        super(entity, animTime, helper, entityPredicate);
    }


    public SimpleAnimationAI(T entity, AnimationHelper helper, int animTime, int cooldownTime, Predicate<LivingEntity> entityPredicate) {
        super(entity, animTime, cooldownTime,helper, entityPredicate);
    }

    public SimpleAnimationAI(T entity, AnimationHelper helper, int animTime, int cooldownTime, boolean interruptsAI, Predicate<LivingEntity> entityPredicate) {
        super(entity, animTime, cooldownTime,interruptsAI, helper, entityPredicate);
    }

    public SimpleAnimationAI(T entity, AnimationHelper helper, int animTime, EnumSet<Flag> flags) {
        this(entity,helper ,animTime, 0, flags, (l) -> {return true;});
    }

    public SimpleAnimationAI(T entity, AnimationHelper helper, int animTime, int cooldownTime, EnumSet<Flag> flags, Predicate<LivingEntity> entityPredicate) {
        super(entity, animTime, cooldownTime, helper, entityPredicate);
        setFlags(flags);
    }
}
