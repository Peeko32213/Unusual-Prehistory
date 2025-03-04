package com.peeko32213.unusualprehistory.common.entity.animation;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import software.bernie.geckolib.animatable.GeoEntity;

import java.util.EnumSet;
import java.util.function.Predicate;

public abstract class AnimationAI<T extends GeoEntity> extends Goal {

    protected final T entity;
    private int animTime;
    private int animTicks;
    private boolean triggered;
    private int cooldownTime;
    private int cooldownTicks;
    private AnimationHelper animationHelper;
    private Predicate<LivingEntity> entityPredicate;



    protected AnimationAI(T entity, int animTime, int cooldownTime, AnimationHelper helper, Predicate<LivingEntity> entityPredicate) {
        this(entity, animTime,cooldownTime,false, helper, entityPredicate);
    }

    protected AnimationAI(T entity, int animTime, AnimationHelper helper, Predicate<LivingEntity> entityPredicate) {
        this(entity, animTime, 0,false, helper, entityPredicate);
    }

    protected AnimationAI(T entity, int animTime, int cooldownTime, AnimationHelper helper) {
        this(entity, animTime,cooldownTime,false, helper, (l) -> {return true;});
    }


    protected AnimationAI(T entity, int animTime, int cooldownTime, boolean interruptsAI, AnimationHelper helper) {
        this(entity, animTime,cooldownTime, interruptsAI, helper, (l) -> {return true;});
    }

    protected AnimationAI(T entity, int animTime, AnimationHelper helper) {
        this(entity, animTime, 0,false, helper, (l) -> {return true;});
    }


    public AnimationAI(T entity, int animTime, int cooldownTime, boolean interruptsAI, AnimationHelper helper, Predicate<LivingEntity> entityPredicate) {
        this.entity = entity;
        if (interruptsAI) this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        this.animTime = animTime;
        this.animationHelper = helper;
        this.cooldownTime = cooldownTime;
        this.entityPredicate = entityPredicate;
    }


    @Override
    public boolean canUse() {
        boolean hasConditions = this.entityPredicate.test((LivingEntity) this.entity);
        return !triggered && animTicks < animTime && cooldownTicks-- < 0 && hasConditions;
    }

    @Override
    public void start() {
        triggered = true;
        //if(((LivingEntity)this.entity).level().isClientSide)
            this.entity.triggerAnim(animationHelper.getControllerName(), animationHelper.getAnimName());
    }

    @Override
    public void tick() {
        super.tick();
        this.animTicks++;
    }

    @Override
    public boolean canContinueToUse() {
        return  animTicks < animTime;
    }


    @Override
    public void stop() {
        this.cooldownTicks = cooldownTime;
        this.triggered = false;
        this.animTicks = 0;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
