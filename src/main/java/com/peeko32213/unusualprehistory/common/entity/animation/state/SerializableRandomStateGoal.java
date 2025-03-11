package com.peeko32213.unusualprehistory.common.entity.animation.state;

import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.Optional;

public class SerializableRandomStateGoal<T extends LivingEntity & IStateAction> extends Goal {

    private final T entity;
    private final Optional<WeightedSerializableStateHelper> helper;
    private int playTicks;
    private int cooldownTicks;
    private int timesActionPerformed;

    //available in newer java version, sadly we're not there yet :(
    //public RandomStateGoal(T entity) {
    //  int states = entity.getStatesToPerform().size();
    //  StateHelper stateHelper = entity.getStatesToPerform().get(entity.getRandom().nextInt(states));
    //  this(entity, stateHelper);
    //}

    public SerializableRandomStateGoal(T entity, WeightedRandomList<WeightedSerializableStateHelper> randomList) {
        this.entity = entity;
        this.helper = randomList.getRandom(entity.getRandom());

        helper.ifPresent(entityHelper -> {
            if( entityHelper.getSerializableStateHelper().isAffectsAI()) {
                this.entity.setAction(true);
                this.setFlags(entityHelper.getSerializableStateHelper().getAffectedFlags());
            }
        });
    }

    @Override
    public boolean canUse() {
        if(this.helper.isEmpty()) return false;
        boolean hasConditions = this.helper.get().getSerializableStateHelper().getStartingPredicate().getPredicate().test(this.entity);
        return  playTicks < this.helper.get().getSerializableStateHelper().getPlayTime() && cooldownTicks-- < 0 && hasConditions;
    }

    @Override
    public void start() {
        if(this.helper.isPresent()){
            this.helper.get().getSerializableStateHelper().getState().setValue(this.entity,true);
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.playTicks++;
        if(this.helper.isEmpty()) return;
        if(this.timesActionPerformed < this.helper.get().getSerializableStateHelper().getEntityAction().getTimesToPerform() &&
                this.playTicks >= this.helper.get().getSerializableStateHelper().getEntityAction().getTimeToPerformAction()) {
            this.helper.get().getSerializableStateHelper().getEntityAction().getAction().accept(entity);
            this.timesActionPerformed++;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.helper.isPresent() && playTicks < this.helper.get().getSerializableStateHelper().getPlayTime();
    }


    @Override
    public void stop() {
        if(this.helper.isEmpty()) return;
        this.helper.get().getSerializableStateHelper().getState().setValue(this.entity,false);
        this.entity.setAction(false);
        this.cooldownTicks = this.helper.get().getSerializableStateHelper().getStopTime();
        this.playTicks = 0;
        this.timesActionPerformed = 0;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
