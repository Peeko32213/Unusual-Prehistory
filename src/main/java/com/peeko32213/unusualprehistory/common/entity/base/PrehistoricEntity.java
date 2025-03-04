package com.peeko32213.unusualprehistory.common.entity.base;

import com.peeko32213.unusualprehistory.UnusualPrehistoryConfig;
import com.peeko32213.unusualprehistory.common.entity.TyrannosaurusEntity;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IBookEntity;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IHatchableEntity;
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
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public abstract class PrehistoricEntity extends Animal implements GeoAnimatable, IHatchableEntity, IBookEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final EntityDataAccessor<Boolean> HUNGRY = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> TIME_TILL_HUNGRY = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> PASSIVE = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_FROM_EGG = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> TRADING = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FROM_BOOK = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> RANDOM_NUMBER = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> RANDOM_BOOL = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ANIM_TIMER = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);

    private boolean tradingAndGottenItem;
    int lastTimeSinceHungry;
    private int tradingCooldownTimer;

    //5 minutes for trading cooldown for now
    public final int TRADING_COOLDOWN = 6000;
    protected PrehistoricEntity(EntityType<? extends Animal> entityType, Level level) {
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
                            return ((PrehistoricEntity) this.mob).isHungry() && super.canUse();
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
    public boolean canAttack(LivingEntity entity) {
        boolean prev = super.canAttack(entity);
        if(prev && isBaby()){
            return false;
        }
        return prev;
    }

    public boolean doHurtTarget(Entity entityIn) {
        if (super.doHurtTarget(entityIn) && getAttackSound() != null) {
            this.playSound(getAttackSound() , 0.1F, 1.0F);
            return true;
        } else {
            return false;
        }
    }

    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {

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
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
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
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
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
        return this.entityData.get(SADDLED).booleanValue();
    }

    /**
     * Sets the saddled state of the entity.
     *
     * @param saddled true if the entity should be set as saddled, false otherwise.
     */
    public void setSaddled(boolean saddled) {
        this.entityData.set(SADDLED, Boolean.valueOf(saddled));
    }

    /**
     * Checks if the entity is currently trading.
     *
     * @return true if the entity is currently trading, otherwise false.
     */
    public boolean isTrading() {
        return this.entityData.get(TRADING).booleanValue();
    }

    /**
     * Sets the trading state of the entity.
     *
     * @param trading true if the entity should be set as currently trading, false otherwise.
     */
    public void setIsTrading(boolean trading) {
        this.entityData.set(TRADING, Boolean.valueOf(trading));
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
        return this.entityData.get(IS_FROM_EGG).booleanValue();
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
        return this.entityData.get(FROM_BOOK).booleanValue();
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

    @javax.annotation.Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance difficultyInstance, MobSpawnType spawnType, @javax.annotation.Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
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
            return new PrehistoricEntity.DinoCustomNavigation(this, level);
        }
        else return super.createNavigation(level);
    }

    static class DinoCustomNavigation extends GroundPathNavigation {
        public DinoCustomNavigation(Mob p_33379_, Level p_33380_) {
            super(p_33379_, p_33380_);
        }
        protected PathFinder createPathFinder(int p_33382_) {
            this.nodeEvaluator = new PrehistoricEntity.CustomNodeEvaluator();
            return new PathFinder(this.nodeEvaluator, p_33382_);
        }
    }

    static class CustomNodeEvaluator extends WalkNodeEvaluator {
        protected BlockPathTypes evaluateBlockPathType(BlockGetter p_33387_, BlockPos p_33390_, BlockPathTypes p_33391_) {
            return p_33391_ == BlockPathTypes.LEAVES ? BlockPathTypes.OPEN : super.evaluateBlockPathType(p_33387_, p_33390_, p_33391_);
        }
    }

    public static boolean checkSurfaceDinoSpawnRules(EntityType<? extends PrehistoricEntity> dino, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource p_186242_) {
        boolean canSpawn = level.getBlockState(pos.below()).is(UPTags.DINO_NATURAL_SPAWNABLE) && isBrightEnoughToSpawn(level, pos) && UnusualPrehistoryConfig.DINO_NATURAL_SPAWNING.get();
        return canSpawn;
    }

}
