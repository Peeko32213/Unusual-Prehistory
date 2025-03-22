package com.peeko32213.unusualprehistory.datagen.entitydata;

import com.peeko32213.unusualprehistory.common.data.entity.EntityGoals;
import com.scouter.goalsmith.data.GoalCodec;
import com.scouter.goalsmith.data.GoalOperation;
import com.scouter.goalsmith.data.TargetGoalCodec;
import com.scouter.goalsmith.data.TargetGoalOperation;
import com.scouter.goalsmith.data.operation.goal.*;
import com.scouter.goalsmith.data.operation.target.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;

import java.util.ArrayList;
import java.util.List;

public class EntityGoalsBuilder {
    private final List<GoalOperation> goalOperations = new ArrayList<>();
    private final List<TargetGoalOperation> targetGoalOperations = new ArrayList<>();



    public EntityGoalsBuilder() {
    }


    public EntityGoalsBuilder addGoalOperation(GoalOperation operation) {
        goalOperations.add(operation);
        return this;
    }

    public EntityGoalsBuilder addTargetGoalOperation(TargetGoalOperation operation) {
        targetGoalOperations.add(operation);
        return this;
    }

    public EntityGoalsBuilder addGoals(GoalCodec... goals) {
        goalOperations.add(new AddOperation(List.of(goals)));
        return this;
    }

    public EntityGoalsBuilder addAddOperation(List<GoalCodec> goals) {
        goalOperations.add(new AddOperation(goals));
        return this;
    }

    public EntityGoalsBuilder addGoal(GoalCodec goals) {
        goalOperations.add(new AddOperation(List.of(goals)));
        return this;
    }

    public EntityGoalsBuilder addRemoveAllOperation() {
        goalOperations.add(new RemoveAllOperation());
        return this;
    }

    public EntityGoalsBuilder addRemoveSpecificOperation(int priority, Class<? extends Goal> goal) {
        goalOperations.add(new RemoveSpecificOperation(new RemoveSpecificOperation.ReplacementGoal(priority, goal)));
        return this;
    }

    public EntityGoalsBuilder addRemoveSpecificPriorityOperation(int priorityToRemove) {
        goalOperations.add(new RemoveSpecificPriorityOperation(priorityToRemove));
        return this;
    }

    public EntityGoalsBuilder addReplaceOperation(int priority, Class<? extends Goal> goal, GoalCodec replacementGoal) {
        goalOperations.add(new ReplaceOperation(new ReplaceOperation.ReplacementGoal(priority, goal), replacementGoal));
        return this;
    }


    ///////////////////
    public EntityGoalsBuilder addAddTargetOperation(List<TargetGoalCodec> goals) {
        targetGoalOperations.add(new AddTargetOperation(goals));
        return this;
    }

    public EntityGoalsBuilder addTargetGoal(TargetGoalCodec goals) {
        targetGoalOperations.add(new AddTargetOperation(List.of(goals)));
        return this;
    }

    public EntityGoalsBuilder addTargetGoals(TargetGoalCodec... goals) {
        targetGoalOperations.add(new AddTargetOperation(List.of(goals)));
        return this;
    }

    public EntityGoalsBuilder addRemoveAllTargetOperation() {
        targetGoalOperations.add(new RemoveAllTargetOperation());
        return this;
    }

    public EntityGoalsBuilder addRemoveSpecificTargetOperation(int priority, Class<? extends TargetGoal> goal) {
        targetGoalOperations.add(new RemoveSpecificTargetOperation(new RemoveSpecificTargetOperation.ReplacementGoal(priority, goal)));
        return this;
    }

    public EntityGoalsBuilder addRemoveSpecificTargetPriorityOperation(int priorityToRemove) {
        targetGoalOperations.add(new RemoveSpecificTargetPriorityOperation(priorityToRemove));
        return this;
    }

    public EntityGoalsBuilder addReplaceOperation(int priority, Class<? extends TargetGoal> goal, TargetGoalCodec replacementGoal) {
        targetGoalOperations.add(new ReplaceTargetOperation(new ReplaceTargetOperation.ReplacementGoal(priority, goal), replacementGoal));
        return this;
    }

    public EntityGoals build() {
        return new EntityGoals(goalOperations, targetGoalOperations);
    }
}