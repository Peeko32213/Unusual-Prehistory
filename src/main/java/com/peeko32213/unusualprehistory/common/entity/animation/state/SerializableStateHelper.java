package com.peeko32213.unusualprehistory.common.entity.animation.state;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.data.entity.synced.SerializableSynchedDataRegistry;
import com.peeko32213.unusualprehistory.common.data.entity.synced.SerializableSynchedEntityData;
import com.peeko32213.unusualprehistory.core.other.util.CodecUtils;
import com.scouter.goalsmith.data.PredicateCodec;
import com.scouter.goalsmith.data.predicates.TruePredicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class SerializableStateHelper {


    public static final Codec<SerializableStateHelper> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            SerializableSynchedDataRegistry.CODEC.fieldOf("state").forGetter(SerializableStateHelper::getState),
            Codec.STRING.fieldOf("name").forGetter(SerializableStateHelper::getName),
            Codec.INT.fieldOf("play_time").forGetter(SerializableStateHelper::getPlayTime),
            Codec.INT.fieldOf("goal_stop_time").forGetter(SerializableStateHelper::getStopTime),
            Codec.BOOL.fieldOf("affects_ai").forGetter(SerializableStateHelper::isAffectsAI),
            CodecUtils.enumSetCodec(CodecUtils.GOAL_FLAG_CODEC).optionalFieldOf("affected_flags", EnumSet.noneOf(Goal.Flag.class)).forGetter(SerializableStateHelper::getAffectedFlags),
            PredicateCodec.DIRECT_CODEC.fieldOf("starting_predicate").forGetter(SerializableStateHelper::getStartingPredicate),
            EntityAction.CODEC.fieldOf("entity_action").forGetter(SerializableStateHelper::getEntityAction)
    ).apply(instance, (serializableSynchedEntityData, s, integer, integer2, aBoolean, flags, predicateCodec, entityAction1) -> new SerializableStateHelper(serializableSynchedEntityData, s, integer, integer2, aBoolean, flags, (PredicateCodec<Entity>)predicateCodec, entityAction1)));


    private final SerializableSynchedEntityData state;
    private final int playTime;
    private final int stopTime;
    private final boolean affectsAI;
    private final String name;
    private final EnumSet<Goal.Flag> affectedFlags;
    private final PredicateCodec<Entity> startingPredicate;
    private final EntityAction entityAction;

    public SerializableStateHelper(SerializableSynchedEntityData state, String name) {
        this(state, name, 0, 0, false, EnumSet.noneOf(Goal.Flag.class), defaultStartingPredicate(), defaultEntityAction());
    }

    public SerializableStateHelper(SerializableSynchedEntityData state, String name, int playTime) {
        this(state,name, playTime, 0, false, EnumSet.noneOf(Goal.Flag.class), defaultStartingPredicate(), defaultEntityAction());
    }

    public SerializableStateHelper(SerializableSynchedEntityData state, String name, int playTime, int stopTime) {
        this(state,name, playTime, stopTime, false, EnumSet.noneOf(Goal.Flag.class), defaultStartingPredicate(), defaultEntityAction());
    }

    public SerializableStateHelper(SerializableSynchedEntityData state, String name, int playTime, int stopTime, boolean affectsAI) {
        this(state,name, playTime, stopTime, affectsAI, EnumSet.noneOf(Goal.Flag.class), defaultStartingPredicate(), defaultEntityAction());
    }

    public SerializableStateHelper(SerializableSynchedEntityData state, String name , int playTime, int stopTime, boolean affectsAI,
                       EnumSet<Goal.Flag> affectedFlags) {
        this(state,name, playTime, stopTime, affectsAI, affectedFlags, defaultStartingPredicate(), defaultEntityAction());
    }

    public SerializableStateHelper(SerializableSynchedEntityData state, String name,  int playTime, int stopTime, boolean affectsAI,
                       EnumSet<Goal.Flag> affectedFlags, PredicateCodec<Entity> startingPredicate) {
        this(state,name, playTime, stopTime, affectsAI, affectedFlags, startingPredicate, defaultEntityAction());
    }
    
    public SerializableStateHelper(SerializableSynchedEntityData state, String name, int playTime, int stopTime, boolean affectsAI,
                                   EnumSet<Goal.Flag> affectedFlags, PredicateCodec<Entity> startingPredicate, EntityAction entityAction) {
        this.state = state;
        this.playTime = playTime;
        this.stopTime = stopTime;
        this.affectsAI = affectsAI;
        this.affectedFlags = affectedFlags;
        this.startingPredicate = startingPredicate;
        this.entityAction = entityAction;
        this.name = name;
    }

    public SerializableSynchedEntityData getState() {
        return state;
    }

    public int getPlayTime() {
        return playTime;
    }

    public int getStopTime() {
        return stopTime;
    }

    public boolean isAffectsAI() {
        return affectsAI;
    }

    public EnumSet<Goal.Flag> getAffectedFlags() {
        return affectedFlags;
    }

    public PredicateCodec<Entity> getStartingPredicate() {
        return startingPredicate;
    }

    public EntityAction getEntityAction() {
        return entityAction;
    }

    public String getName() {
        return name;
    }

    private static PredicateCodec<Entity> defaultStartingPredicate() {
        return new TruePredicate<>();
    }
    
    private static EntityAction defaultEntityAction() {
        return new EntityAction(0, (e) -> {}, 0);
    }


    public static class Builder {
        private SerializableSynchedEntityData state;
        private int playTime = 0;
        private int stopTime = 0;
        private boolean affectsAI = false;
        private EnumSet<Goal.Flag> affectedFlags = EnumSet.noneOf(Goal.Flag.class);
        private PredicateCodec<Entity> startingPredicate = new TruePredicate<>();
        private String name;
        private EntityAction entityAction = new EntityAction(0, (e) -> {}, 0); // Default action that does nothing

        public static SerializableStateHelper.Builder state(SerializableSynchedEntityData state, String name) {
            return new SerializableStateHelper.Builder(state, name);
        }
        public Builder(SerializableSynchedEntityData state, String name) {
            if (state == null) {
                throw new IllegalArgumentException("State cannot be null.");
            }
            this.state = state;
            this.name = name;
        }

        public Builder playTime(int playTimeTicks) {
            this.playTime = playTimeTicks;
            return this;
        }

        public Builder stopTime(int stopTimeTicks) {
            this.stopTime = stopTimeTicks;
            return this;
        }

        public Builder affectsAI(boolean affectsAI) {
            this.affectsAI = affectsAI;
            return this;
        }

        public Builder affectedFlags(EnumSet<Goal.Flag> affectedFlags) {
            this.affectedFlags = affectedFlags;
            return this;
        }

        public Builder startingPredicate(PredicateCodec<Entity> startingPredicate) {
            if (startingPredicate == null) {
                throw new IllegalArgumentException("Starting predicate cannot be null.");
            }
            this.startingPredicate = startingPredicate;
            return this;
        }

        public Builder entityAction(EntityAction entityAction) {
            if (entityAction == null) {
                throw new IllegalArgumentException("Entity action cannot be null.");
            }
            this.entityAction = entityAction;
            return this;
        }

        public SerializableStateHelper build() {
            return new SerializableStateHelper(state,name ,playTime, stopTime, affectsAI, affectedFlags, startingPredicate, entityAction);
        }
    }
}
