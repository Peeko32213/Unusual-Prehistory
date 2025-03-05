package com.peeko32213.unusualprehistory.common.entity.custom.base;

import com.peeko32213.unusualprehistory.UnusualPrehistoryConfig;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.TyrannosaurusEntity;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IBookEntity;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IHatchableEntity;
import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.IAttackStateAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.IStateAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

// TODO: new attack ai?
public abstract class StatedAggressivePrehistoricEntity extends Animal implements GeoAnimatable, IHatchableEntity, IBookEntity, IStateAction, IAttackStateAction {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Boolean> HUNGRY = SynchedEntityData.defineId(StatedAggressivePrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> TIME_TILL_HUNGRY = SynchedEntityData.defineId(StatedAggressivePrehistoricEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(StatedAggressivePrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> PASSIVE = SynchedEntityData.defineId(StatedAggressivePrehistoricEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_FROM_EGG = SynchedEntityData.defineId(StatedAggressivePrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> TRADING = SynchedEntityData.defineId(StatedAggressivePrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FROM_BOOK = SynchedEntityData.defineId(StatedAggressivePrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(StatedAggressivePrehistoricEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> RANDOM_NUMBER = SynchedEntityData.defineId(StatedAggressivePrehistoricEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> RANDOM_BOOL = SynchedEntityData.defineId(StatedAggressivePrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ANIM_TIMER = SynchedEntityData.defineId(StatedAggressivePrehistoricEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> PERFORMING_ACTION = SynchedEntityData.defineId(StatedAggressivePrehistoricEntity.class, EntityDataSerializers.BOOLEAN);

    private boolean tradingAndGottenItem;
    int lastTimeSinceHungry;
    private int tradingCooldownTimer;

    public float prevAttackProgress;
    public float attackProgress;
    private StateHelper attack;
    private StateHelper attackOld;
    private int attackTick;
    private int timesAttacked;
    private StateHelper specialAttack;
    private StateHelper specialAttackOld;
    private int specialAttackTick;
    private int timesSpecialAttacked;
    private int specialAttackCooldown;

    //5 minutes for trading cooldown for now
    public final int TRADING_COOLDOWN = 6000;
    protected StatedAggressivePrehistoricEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        if(hasAvoidEntity()) {
            this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, TyrannosaurusEntity.class, 8.0F, 1.6D, 1.4D, EntitySelector.NO_SPECTATORS::test));
        }
        if(hasTargets()) {
            this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, false, false, entity -> entity.getType().is(getTargetTag())) {
                        @Override
                        public boolean canUse() {
                            return ((StatedAggressivePrehistoricEntity) this.mob).isHungry() && super.canUse();
                        }
                    }
            );
        }
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        if(tradingCooldownTimer > 0){
            tradingCooldownTimer--;
        }
        tickHunger();

        if(playingAnimation()) {
            setAnimationTimer(getAnimationTimer() - 1);
        }
        attackManager();
        specialAttackManager();
    }

    public void tickHunger(){
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

    public void killed() {
        this.heal(getKillHealAmount());
    }

    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

    @Override
    public boolean canAttack(@NotNull LivingEntity entity) {
        boolean prev = super.canAttack(entity);
        if(prev && isBaby()){
            return false;
        }
        return prev;
    }

    public boolean doHurtTarget(@NotNull Entity entityIn) {
        if (super.doHurtTarget(entityIn) && getAttackSound() != null) {
            this.playSound(getAttackSound() , 0.1F, 1.0F);
            return true;
        } else {
            return false;
        }
    }

    public boolean causeFallDamage(float pFallDistance, float pMultiplier, @NotNull DamageSource pSource) {

        int i = this.calculateFallDamage(pFallDistance, pMultiplier);
        if (i <= 0) {
            return false;
        } else {
            this.hurt(pSource, (float)i);
            if (this.isVehicle()) {
                for(Entity entity : this.getIndirectPassengers()) {
                    entity.hurt(pSource, (float)i);
                }
            }

            this.playBlockFallSound();
            return true;
        }
    }

    public void attackManager() {

        if(this.attackOld != null) {
            boolean playing = this.entityData.get(this.attackOld.getState());
            //reset it to old state if it was still playing and attack == null
            if (playing && this.attack == null) {
                this.entityData.set(this.attackOld.getState(), false);
            }
        }
        if (attack == null && attackTick <= 0) {
            this.attack = getRandomAttackState(this);
        }
        if (attack != null && testAttackOrFindSuitable() && this.attack.getPlayTime() >= attackTick) {
            EntityAction action = this.attack.getEntityAction();
            this.entityData.set(this.attack.getState(), true);
            if (action.getTimeToPerformAction() <= attackTick && action.getTimesToPerform() >= timesAttacked) {
                action.getAction().accept(this);
                timesAttacked++;
            }
            this.attackOld = this.attack;
            this.attackTick++;
        } else {
            if (this.attack != null) {
                this.entityData.set(this.attack.getState(), false);
            }
            this.attack = null;
            this.attackTick = 0;
            this.timesAttacked = 0;
        }
    }

    public void specialAttackManager() {

        if(this.specialAttackOld!= null) {
            boolean playing = this.entityData.get(this.specialAttackOld.getState());
            //reset it to old state if it was still playing and attack == null
            if (playing && this.specialAttack == null) {
                this.entityData.set(this.specialAttackOld.getState(), false);
            }
        }

        if (specialAttackCooldown <= 0 && specialAttack == null && specialAttackTick <= 0) {
            this.specialAttack = getSpecialAttackToPerform();
        }

        if (specialAttackCooldown <= 0 && this.specialAttack != null && this.specialAttack.getPlayTime() >= specialAttackTick) {
            EntityAction action = this.specialAttack.getEntityAction();
            this.entityData.set(this.specialAttack.getState(), true);
            if (action.getTimeToPerformAction() <= specialAttackTick && action.getTimesToPerform() >= timesSpecialAttacked) {
                action.getAction().accept(this);
                timesSpecialAttacked++;
            }

            this.specialAttackOld = this.specialAttack;
            this.specialAttackTick++;
            if(this.specialAttack.getPlayTime() <= specialAttackTick) {
                this.specialAttackCooldown = this.specialAttack.getStopTime();
            }

        } else {
            if (this.specialAttack != null) {
                this.entityData.set(this.specialAttack.getState(), false);
            }
            this.specialAttack = null;
            this.specialAttackTick = 0;
            this.timesSpecialAttacked = 0;
            specialAttackCooldown--;
        }

    }

    private static final int MAX_ATTACK_SEARCH = 10;

    private boolean testAttackOrFindSuitable() {
        if (attack != null) {
            boolean canAttack = this.attack.getStartingPredicate().test(this);
            if (canAttack) {
                return true;
            } else {
                for (int i = 0; i <= MAX_ATTACK_SEARCH; i++) {
                    this.attack = getRandomAttackState(this);
                    if (this.attack.getStartingPredicate().test(this)) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    public boolean getPerformingAction() {
        return this.entityData.get(PERFORMING_ACTION);
    }

    public void setPerformingAction(boolean action) {
        this.entityData.set(PERFORMING_ACTION, action);
    }

    @Override
    public boolean getAction() {
        return getPerformingAction();
    }

    @Override
    public void setAction(boolean action) {
        setPerformingAction(action);
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
        this.entityData.define(PASSIVE, 0);
        this.entityData.define(IS_FROM_EGG, false);
        this.entityData.define(TRADING, false);
        this.entityData.define(FROM_BOOK, false);
        this.entityData.define(VARIANT, 0);
        this.entityData.define(RANDOM_BOOL, false);
        this.entityData.define(RANDOM_NUMBER,0);
        this.entityData.define(ANIM_TIMER, 0);
        this.entityData.define(PERFORMING_ACTION, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("IsHungry", this.isHungry());
        compound.putInt("TimeTillHungry", this.getTimeTillHungry());
        compound.putBoolean("Saddle", this.isSaddled());
        compound.putInt("PassiveTicks", this.getPassiveTicks());
        compound.putBoolean("fromEgg", this.isFromEgg());
        compound.putBoolean("trading", this.isTrading());
        compound.putInt("tradingCooldown", this.getTradingCooldownTimer());
        compound.putBoolean("tradingAndGotItem", this.getTradingAndGottenItem());
        compound.putInt("variant", this.getVariant());
        compound.putInt("randomNr", this.getRandomNumber());
        compound.putInt("animTimer", this.getAnimationTimer());
        compound.putBoolean("randomBool", this.getRandomBool());
        if (this.attack != null) {
            compound.putString("attack", attack.getName());
        }
        if(attackOld != null) {
            compound.putString("attackOld", attackOld.getName());
        }
        compound.putInt("attackTick", attackTick);
        compound.putInt("timesAttacked", timesAttacked);
        if (this.specialAttack != null) {
            compound.putString("specialAttack", specialAttack.getName());
        }
        if(specialAttackOld != null) {
            compound.putString("specialAttackOld", specialAttackOld.getName());
        }
        compound.putInt("specialAttackTick", specialAttackTick);
        compound.putInt("timesSpecialAttacked", timesSpecialAttacked);
        compound.putInt("specialAttackCooldown", specialAttackCooldown);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setHungry(compound.getBoolean("IsHungry"));
        this.setTimeTillHungry(compound.getInt("TimeTillHungry"));
        this.setSaddled(compound.getBoolean("Saddle"));
        this.setPassiveTicks(compound.getInt("PassiveTicks"));
        this.setIsFromEgg(compound.getBoolean("fromEgg"));
        this.setIsTrading(compound.getBoolean("trading"));
        this.setTradingCooldownTimer(compound.getInt("tradingCooldown"));
        this.setTradingAndGottenItem(compound.getBoolean("tradingAndGotItem"));
        this.setVariant(compound.getInt("variant"));
        this.setRandomNumber(compound.getInt("randomNr"));
        this.setRandomBool(compound.getBoolean("randomBool"));
        this.setAnimationTimer(compound.getInt("animTimer"));
        if (compound.contains("attack", 8)) {
            String attackName = compound.getString("attack");
            this.attack = getAttackStateForName(this, attackName);
        }
        if (compound.contains("attackOld", 8)) {
            String attackOldName = compound.getString("attackOld");
            this.attackOld = getAttackStateForName(this, attackOldName);
        }
        attackTick = compound.getInt("attackTick");
        timesAttacked = compound.getInt("timesAttacked");
        if (compound.contains("specialAttack", 8)) {
            String specialAttackName = compound.getString("specialAttack");
            this.specialAttack = getSpecialStateForName(this, specialAttackName);
        }

        if (compound.contains("specialAttackOld", 8)) {
            String specialAttackOldName = compound.getString("specialAttackOld");
            this.specialAttackOld = getSpecialStateForName(this, specialAttackOldName);
        }
        specialAttackTick = compound.getInt("specialAttackTick");
        timesSpecialAttacked = compound.getInt("timesSpecialAttacked");
        this.specialAttackCooldown = compound.getInt("specialAttackCooldown");
    }

    public boolean getBooleanState(EntityDataAccessor<Boolean> pKey) {
        return this.entityData.get(pKey);
    }

    public void setBooleanState(EntityDataAccessor<Boolean> pKey, boolean state) {
        this.entityData.set(pKey, state);
    }

    /**
     * Checks if the entity is hungry.
     *
     * @return true if the entity is hungry, otherwise false.
     */
    public boolean isHungry() {
        return this.entityData.get(HUNGRY);
    }

    /**
     * Sets the hungry state of the entity.
     *
     * @param hungry true if the entity should be set as hungry, false otherwise.
     */
    public void setHungry(boolean hungry) {
        this.entityData.set(HUNGRY, hungry);
    }

    /**
     * Gets the remaining time till the entity gets hungry.
     *
     * @return The remaining time till the entity gets hungry, in ticks.
     */
    public int getTimeTillHungry() {
        return this.entityData.get(TIME_TILL_HUNGRY);
    }

    /**
     * Sets the remaining time till the entity gets hungry.
     *
     * @param ticks The time in ticks to set as the remaining time till the entity gets hungry.
     */
    public void setTimeTillHungry(int ticks) {
        this.entityData.set(TIME_TILL_HUNGRY, ticks);
    }

    /**
     * Checks if the entity is saddled.
     *
     * @return true if the entity is saddled, otherwise false.
     */
    public boolean isSaddled() {
        return this.entityData.get(SADDLED);
    }

    /**
     * Sets the saddled state of the entity.
     *
     * @param saddled true if the entity should be set as saddled, false otherwise.
     */
    public void setSaddled(boolean saddled) {
        this.entityData.set(SADDLED, saddled);
    }

    /**
     * Checks if the entity is currently trading.
     *
     * @return true if the entity is currently trading, otherwise false.
     */
    public boolean isTrading() {
        return this.entityData.get(TRADING);
    }

    /**
     * Sets the trading state of the entity.
     *
     * @param trading true if the entity should be set as currently trading, false otherwise.
     */
    public void setIsTrading(boolean trading) {
        this.entityData.set(TRADING, trading);
    }

    /**
     * Gets the passive ticks for the entity.
     *
     * @return The number of passive ticks for the entity.
     */
    public int getPassiveTicks() {
        return this.entityData.get(PASSIVE);
    }

    /**
     * Sets the passive ticks for the entity.
     *
     * @param passiveTicks The number of passive ticks to set for the entity.
     */
    public void setPassiveTicks(int passiveTicks) {
        this.entityData.set(PASSIVE, passiveTicks);
    }

    /**
     * Checks if the entity is from an egg.
     *
     * @return true if the entity is from an egg, otherwise false.
     */
    public boolean isFromEgg() {
        return this.entityData.get(IS_FROM_EGG);
    }

    /**
     * Sets whether the entity is from an egg.
     *
     * @param fromEgg true if the entity should be set as from an egg, false otherwise.
     */
    public void setIsFromEgg(boolean fromEgg) {
        this.entityData.set(IS_FROM_EGG, fromEgg);
    }

    /**
     * Checks if the entity is from a book.
     *
     * @return true if the entity is from a book, otherwise false.
     */
    public boolean isFromBook() {
        return this.entityData.get(FROM_BOOK);
    }

    /**
     * Sets whether the entity is from a book.
     *
     * @param fromBook true if the entity should be set as from a book, false otherwise.
     */
    public void setIsFromBook(boolean fromBook) {
        this.entityData.set(FROM_BOOK, fromBook);
    }

    @Override
    public void setFromBook(boolean fromBook) {
        this.entityData.set(FROM_BOOK, fromBook);
    }

    /**
     * Checks if the entity requires custom persistence.
     * This is equivalent to calling {@link #isFromEgg()}.
     *
     * @return true if the entity requires custom persistence, otherwise false.
     */
    public boolean requiresCustomPersistence() {
        return this.isFromEgg();
    }

    /**
     * Checks if the entity should be removed when far away.
     * This is the opposite of calling {@link #isFromEgg()}.
     *
     * @param d The distance parameter (not used in this implementation).
     * @return true if the entity should be removed when far away, otherwise false.
     */
    public boolean removeWhenFarAway(double d) {
        return !this.isFromEgg();
    }

    /**
     * Sets the trading and gotten item state of the entity.
     *
     * @param tradingAndGottenItem true if the entity has trading and gotten items, false otherwise.
     */
    public void setTradingAndGottenItem(boolean tradingAndGottenItem) {
        this.tradingAndGottenItem = tradingAndGottenItem;
    }

    /**
     * Gets the trading and gotten item state of the entity.
     *
     * @return true if the entity has trading and gotten items, otherwise false.
     */
    public boolean getTradingAndGottenItem() {
        return tradingAndGottenItem;
    }

    /**
     * Gets the trading cooldown timer for the entity.
     *
     * @return The trading cooldown timer value for the entity.
     */
    public int getTradingCooldownTimer() {
        return tradingCooldownTimer;
    }

    /**
     * Sets the trading cooldown timer for the entity.
     *
     * @param tradingCooldownTimer The trading cooldown timer value to set for the entity.
     */
    public void setTradingCooldownTimer(int tradingCooldownTimer) {
        this.tradingCooldownTimer = tradingCooldownTimer;
    }

    /**
     * Gets the variant of the entity.
     *
     * @return The variant of the entity.
     */
    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    /**
     * Sets the variant of the entity.
     *
     * @param variant The variant to set for the entity.
     */
    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    public void determineVariant(int variantChange) {
    }

    public int getRandomAnimationNumber() {
        setRandomNumber(random.nextInt(100));
        return getRandomNumber();
    }

    public int getRandomNumber() {
        return this.entityData.get(RANDOM_NUMBER);
    }

    public void setRandomNumber(int nr) {
        this.entityData.set(RANDOM_NUMBER,nr);
    }

    public boolean getRandomAnimationBool() {
        setRandomBool(random.nextBoolean());
        return getRandomBool();
    }

    public boolean getRandomBool() {
        return this.entityData.get(RANDOM_BOOL);
    }

    public void setRandomBool(boolean bool) {
        this.entityData.set(RANDOM_BOOL,bool);
    }

    public boolean playingAnimation() {
        return getAnimationTimer() > 0;
    }

    public int getAnimationTimer() {
        return this.entityData.get(ANIM_TIMER);
    }

    public void setAnimationTimer(int time) {
        this.entityData.set(ANIM_TIMER,time);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance difficultyInstance, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
        spawnGroupData = super.finalizeSpawn(levelAccessor, difficultyInstance, spawnType, spawnGroupData, tag);
        Level level = levelAccessor.getLevel();
        determineVariant(random.nextInt(100));
        if (level instanceof ServerLevel) {
            this.setPersistenceRequired();
        }
        return spawnGroupData;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }

    public void makeStuckInBlock(@NotNull BlockState blockstate, @NotNull Vec3 vec3) {
        if(!hasMakeStuckInBlock()){
            super.makeStuckInBlock(blockstate, vec3);
        }
        if (customMakeStuckInBlockCheck(blockstate)) {
            super.makeStuckInBlock(blockstate, vec3);
        }
    }

    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        if(hasCustomNavigation()){
            return new StatedPrehistoricEntity.DinoCustomNavigation(this, level);
        }
        else return super.createNavigation(level);
    }

    static class DinoCustomNavigation extends GroundPathNavigation {
        public DinoCustomNavigation(Mob p_33379_, Level p_33380_) {
            super(p_33379_, p_33380_);
        }
        protected @NotNull PathFinder createPathFinder(int p_33382_) {
            this.nodeEvaluator = new StatedPrehistoricEntity.CustomNodeEvaluator();
            return new PathFinder(this.nodeEvaluator, p_33382_);
        }
    }

    static class CustomNodeEvaluator extends WalkNodeEvaluator {
        protected @NotNull BlockPathTypes evaluateBlockPathType(@NotNull BlockGetter p_33387_, @NotNull BlockPos p_33390_, @NotNull BlockPathTypes p_33391_) {
            return p_33391_ == BlockPathTypes.LEAVES ? BlockPathTypes.OPEN : super.evaluateBlockPathType(p_33387_, p_33390_, p_33391_);
        }
    }

    public static boolean checkSurfaceDinoSpawnRules(EntityType<? extends StatedPrehistoricEntity> dino, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource p_186242_) {
        boolean canSpawn = level.getBlockState(pos.below()).is(UPTags.DINO_NATURAL_SPAWNABLE) && isBrightEnoughToSpawn(level, pos) && UnusualPrehistoryConfig.DINO_NATURAL_SPAWNING.get();
        return canSpawn;
    }

}

