package com.peeko32213.unusualprehistory.common.data.entity.goal;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.data.attack.NoneAttack;
import com.peeko32213.unusualprehistory.common.data.entity.synced.SerializableSynchedDataRegistry;
import com.peeko32213.unusualprehistory.common.data.entity.synced.SerializableSynchedEntityData;
import com.peeko32213.unusualprehistory.core.registry.util.CodecUtils;
import com.scouter.goalsmith.data.PredicateCodec;
import com.scouter.goalsmith.data.predicates.TruePredicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class SerializableRandomMeleeAttackHelper {


    public static final Codec<SerializableRandomMeleeAttackHelper> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            SerializableSynchedDataRegistry.CODEC.fieldOf("state").forGetter(SerializableRandomMeleeAttackHelper::getState),
            Codec.STRING.fieldOf("name").forGetter(SerializableRandomMeleeAttackHelper::getName),
            Codec.INT.fieldOf("play_time").forGetter(SerializableRandomMeleeAttackHelper::getPlayTime),
            Codec.INT.fieldOf("goal_stop_time").forGetter(SerializableRandomMeleeAttackHelper::getStopTime),
            Codec.BOOL.fieldOf("affects_ai").forGetter(SerializableRandomMeleeAttackHelper::isAffectsAI),
            CodecUtils.enumSetCodec(CodecUtils.GOAL_FLAG_CODEC).optionalFieldOf("affected_flags", EnumSet.noneOf(Goal.Flag.class)).forGetter(SerializableRandomMeleeAttackHelper::getAffectedFlags),
            PredicateCodec.DIRECT_CODEC.fieldOf("starting_predicate").forGetter(SerializableRandomMeleeAttackHelper::getStartingPredicate),
            MeleeEntityAction.CODEC.fieldOf("melee_entity_action").forGetter(SerializableRandomMeleeAttackHelper::getMeleeEntityAction)
    ).apply(instance, (serializableSynchedEntityData, s, integer, integer2, aBoolean, flags, predicateCodec, entityAction1) -> new SerializableRandomMeleeAttackHelper(serializableSynchedEntityData, s, integer, integer2, aBoolean, flags, (PredicateCodec<Entity>)predicateCodec, entityAction1)));


    private final SerializableSynchedEntityData state;
    private final int playTime;
    private final int stopTime;
    private final boolean affectsAI;
    private final String name;
    private final EnumSet<Goal.Flag> affectedFlags;
    private final PredicateCodec<Entity> startingPredicate;
    private final MeleeEntityAction meleeEntityAction;

    public SerializableRandomMeleeAttackHelper(SerializableSynchedEntityData state, String name) {
        this(state, name, 0, 0, false, EnumSet.noneOf(Goal.Flag.class), defaultStartingPredicate(), defaultEntityAction());
    }

    public SerializableRandomMeleeAttackHelper(SerializableSynchedEntityData state, String name, int playTime) {
        this(state,name, playTime, 0, false, EnumSet.noneOf(Goal.Flag.class), defaultStartingPredicate(), defaultEntityAction());
    }

    public SerializableRandomMeleeAttackHelper(SerializableSynchedEntityData state, String name, int playTime, int stopTime) {
        this(state,name, playTime, stopTime, false, EnumSet.noneOf(Goal.Flag.class), defaultStartingPredicate(), defaultEntityAction());
    }

    public SerializableRandomMeleeAttackHelper(SerializableSynchedEntityData state, String name, int playTime, int stopTime, boolean affectsAI) {
        this(state,name, playTime, stopTime, affectsAI, EnumSet.noneOf(Goal.Flag.class), defaultStartingPredicate(), defaultEntityAction());
    }

    public SerializableRandomMeleeAttackHelper(SerializableSynchedEntityData state, String name , int playTime, int stopTime, boolean affectsAI,
                                               EnumSet<Goal.Flag> affectedFlags) {
        this(state,name, playTime, stopTime, affectsAI, affectedFlags, defaultStartingPredicate(), defaultEntityAction());
    }

    public SerializableRandomMeleeAttackHelper(SerializableSynchedEntityData state, String name, int playTime, int stopTime, boolean affectsAI,
                                               EnumSet<Goal.Flag> affectedFlags, PredicateCodec<Entity> startingPredicate) {
        this(state,name, playTime, stopTime, affectsAI, affectedFlags, startingPredicate, defaultEntityAction());
    }
    
    public SerializableRandomMeleeAttackHelper(SerializableSynchedEntityData state, String name, int playTime, int stopTime, boolean affectsAI,
                                               EnumSet<Goal.Flag> affectedFlags, PredicateCodec<Entity> startingPredicate, MeleeEntityAction meleeEntityAction) {
        this.state = state;
        this.playTime = playTime;
        this.stopTime = stopTime;
        this.affectsAI = affectsAI;
        this.affectedFlags = affectedFlags;
        this.startingPredicate = startingPredicate;
        this.meleeEntityAction = meleeEntityAction;
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

    public MeleeEntityAction getMeleeEntityAction() {
        return meleeEntityAction;
    }

    public String getName() {
        return name;
    }

    private static PredicateCodec<Entity> defaultStartingPredicate() {
        return new TruePredicate<>();
    }
    
    private static MeleeEntityAction defaultEntityAction() {
        return new MeleeEntityAction(0, new NoneAttack());
    }


    public static class Builder {
        private SerializableSynchedEntityData state;
        private int playTime = 0;
        private int stopTime = 0;
        private boolean affectsAI = false;
        private EnumSet<Goal.Flag> affectedFlags = EnumSet.noneOf(Goal.Flag.class);
        private PredicateCodec<Entity> startingPredicate = new TruePredicate<>();
        private String name;
        private MeleeEntityAction meleeEntityAction = new MeleeEntityAction(0, new NoneAttack()); // Default action that does nothing

        public static SerializableRandomMeleeAttackHelper.Builder state(SerializableSynchedEntityData state, String name) {
            return new SerializableRandomMeleeAttackHelper.Builder(state, name);
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

        public Builder meleeEntityAction(MeleeEntityAction MeleeEntityAction) {
            if (meleeEntityAction == null) {
                throw new IllegalArgumentException("Entity action cannot be null.");
            }
            this.meleeEntityAction = MeleeEntityAction;
            return this;
        }

        public SerializableRandomMeleeAttackHelper build() {
            return new SerializableRandomMeleeAttackHelper(state,name ,playTime, stopTime, affectsAI, affectedFlags, startingPredicate, meleeEntityAction);
        }
    }
}
