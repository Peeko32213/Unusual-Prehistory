package com.peeko32213.unusualprehistory.common.entity.msc.util.dino;

import com.peeko32213.unusualprehistory.common.entity.EntityTyrannosaurusRex;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public abstract class EntityTameableBaseDinosaurAnimal extends TamableAnimal implements IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private static final EntityDataAccessor<Boolean> HUNGRY = SynchedEntityData.defineId(EntityTameableBaseDinosaurAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> TIME_TILL_HUNGRY = SynchedEntityData.defineId(EntityTameableBaseDinosaurAnimal.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(EntityTameableBaseDinosaurAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SWINGING = SynchedEntityData.defineId(EntityTameableBaseDinosaurAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_SWUNG = SynchedEntityData.defineId(EntityTameableBaseDinosaurAnimal.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> PASSIVE = SynchedEntityData.defineId(EntityTameableBaseDinosaurAnimal.class, EntityDataSerializers.INT);
    private boolean orderedToSit;

    int lastTimeSinceHungry;

    protected EntityTameableBaseDinosaurAnimal(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.reassessTameGoals();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        if(hasAvoidEntity()) {
            this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, EntityTyrannosaurusRex.class, 8.0F, 1.6D, 1.4D, EntitySelector.NO_SPECTATORS::test));
        }
        if(hasTargets()) {
            this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, false, false, entity -> entity.getType().is(getTargetTag())) {
                        @Override
                        public boolean canUse() {
                            return ((EntityTameableBaseDinosaurAnimal) this.mob).isHungry() && super.canUse();
                        }
                    }
            );
        }
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        if(!canGetHungry()) {
            return;
        }
        if (!this.isHungry() && lastTimeSinceHungry < this.getTimeTillHungry()) {
            lastTimeSinceHungry++;
        }
        if (lastTimeSinceHungry >= this.getTimeTillHungry()) {
            this.setHungry(true);
            lastTimeSinceHungry = 0;
        }
    }

    public void killed(ServerLevel world, LivingEntity entity) {
        this.heal(getKillHealAmount());
    }

    public void checkDespawn() {
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }


    @Override
    public boolean canAttack(LivingEntity entity) {
        boolean prev = super.canAttack(entity);
        if(prev && isBaby()){
            return false;
        }
        if (this.isOwnedBy(entity))
        {
            return false;
        }
        return prev;
    }

    public boolean doHurtTarget(Entity entityIn) {
        if (super.doHurtTarget(entityIn)) {
            this.playSound(getAttackSound() , 0.1F, 1.0F);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 4) {
            this.swinging = true;
            setSwinging(true);
            setHasSwung(true);
        } else if (pId == 5) {
            this.swinging = false;
            setSwinging(false);
            setHasSwung(false);
        } else {
            super.handleEntityEvent(pId);

        }
    }
    protected abstract SoundEvent getAttackSound();
    protected abstract int getKillHealAmount();
    protected abstract boolean canGetHungry();
    protected abstract boolean hasTargets();
    protected abstract boolean hasAvoidEntity();
    protected abstract boolean hasCustomNavigation();
    protected abstract boolean hasMakeStuckInBlock();
    protected abstract boolean customMakeStuckInBlockCheck(BlockState blockState);

    protected abstract TagKey<EntityType<?>> getTargetTag();
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HUNGRY, true);
        this.entityData.define(TIME_TILL_HUNGRY, 0);
        this.entityData.define(SADDLED, false);
        this.entityData.define(SWINGING, false);
        this.entityData.define(HAS_SWUNG, false);
        this.entityData.define(PASSIVE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("IsHungry", this.isHungry());
        compound.putInt("TimeTillHungry", this.getTimeTillHungry());
        compound.putBoolean("Saddle", this.isSaddled());
        compound.putBoolean("IsSwinging", this.isSwinging());
        compound.putBoolean("HasSwung", this.hasSwung());
        compound.putInt("PassiveTicks", this.getPassiveTicks());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setHungry(compound.getBoolean("IsHungry"));
        this.setSwinging(compound.getBoolean("IsSwinging"));
        this.setHasSwung(compound.getBoolean("HasSwung"));
        this.setTimeTillHungry(compound.getInt("TimeTillHungry"));
        this.setSaddled(compound.getBoolean("Saddle"));
        this.setPassiveTicks(compound.getInt("PassiveTicks"));
    }

    public boolean canBeLeashed(Player p_21813_) {
        return !this.isLeashed();
    }


    public boolean isHungry() {
        return this.entityData.get(HUNGRY);
    }

    public void setHungry(boolean hungry) {
        this.entityData.set(HUNGRY, hungry);
    }

    public int getTimeTillHungry() {
        return this.entityData.get(TIME_TILL_HUNGRY);
    }

    public void setTimeTillHungry(int ticks) {
        this.entityData.set(TIME_TILL_HUNGRY, ticks);
    }

    public boolean isSaddled() {
        return this.entityData.get(SADDLED).booleanValue();
    }

    public void setSaddled(boolean saddled) {
        this.entityData.set(SADDLED, Boolean.valueOf(saddled));
    }

    public boolean isSwinging() {
        return this.entityData.get(SWINGING).booleanValue();
    }

    public void setSwinging(boolean swinging) {
        this.entityData.set(SWINGING, Boolean.valueOf(swinging));
    }
    public boolean hasSwung() {
        return this.entityData.get(HAS_SWUNG).booleanValue();
    }

    public void setHasSwung(boolean swung) {
        this.entityData.set(HAS_SWUNG, Boolean.valueOf(swinging));
    }
    public int getPassiveTicks() {
        return this.entityData.get(PASSIVE);
    }

    public void setPassiveTicks(int passiveTicks) {
        this.entityData.set(PASSIVE, passiveTicks);
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.hasCustomName();
    }

    public boolean removeWhenFarAway(double d) {
        return !this.hasCustomName();
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance difficultyInstance, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
        spawnGroupData = super.finalizeSpawn(levelAccessor, difficultyInstance, spawnType, spawnGroupData, tag);
        Level level = levelAccessor.getLevel();
        if (level instanceof ServerLevel) {
            this.setPersistenceRequired();
        }
        return spawnGroupData;
    }


   @Override
   public AnimationFactory getFactory() {
       return this.factory;
   }

    public void makeStuckInBlock(BlockState blockstate, Vec3 vec3) {
        if(!hasMakeStuckInBlock()){
             super.makeStuckInBlock(blockstate, vec3);
        }
        if (customMakeStuckInBlockCheck(blockstate)) {
            super.makeStuckInBlock(blockstate, vec3);
        }
    }


    protected PathNavigation createNavigation(Level level) {
        if(hasCustomNavigation()){
            return new EntityTameableBaseDinosaurAnimal.DinoCustomNavigation(this, level);
        }
        else return super.createNavigation(level);
    }

    static class DinoCustomNavigation extends GroundPathNavigation {
        public DinoCustomNavigation(Mob p_33379_, Level p_33380_) {
            super(p_33379_, p_33380_);
        }
        protected PathFinder createPathFinder(int p_33382_) {
            this.nodeEvaluator = new EntityTameableBaseDinosaurAnimal.CustomNodeEvaluator();
            return new PathFinder(this.nodeEvaluator, p_33382_);
        }
    }

    static class CustomNodeEvaluator extends WalkNodeEvaluator {
        protected BlockPathTypes evaluateBlockPathType(BlockGetter p_33387_, boolean p_33388_, boolean p_33389_, BlockPos p_33390_, BlockPathTypes p_33391_) {
            return p_33391_ == BlockPathTypes.LEAVES ? BlockPathTypes.OPEN : super.evaluateBlockPathType(p_33387_, p_33388_, p_33389_, p_33390_, p_33391_);
        }
    }

}
