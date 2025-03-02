package com.peeko32213.unusualprehistory.common.entity.msc.util.dino;

import com.peeko32213.unusualprehistory.UnusualPrehistoryConfig;
import com.peeko32213.unusualprehistory.common.entity.TyrannosaurusEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.IBookEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.IHatchableEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.util.state.IStateAction;
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
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public abstract class TameableBaseStatedDinosaurAnimalEntity extends TamableAnimal implements GeoAnimatable, IBookEntity, IHatchableEntity, IStateAction {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final EntityDataAccessor<Boolean> HUNGRY = SynchedEntityData.defineId(TameableBaseStatedDinosaurAnimalEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> TIME_TILL_HUNGRY = SynchedEntityData.defineId(TameableBaseStatedDinosaurAnimalEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(TameableBaseStatedDinosaurAnimalEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SWINGING = SynchedEntityData.defineId(TameableBaseStatedDinosaurAnimalEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_SWUNG = SynchedEntityData.defineId(TameableBaseStatedDinosaurAnimalEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> PASSIVE = SynchedEntityData.defineId(TameableBaseStatedDinosaurAnimalEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> IS_FROM_EGG = SynchedEntityData.defineId(TameableBaseStatedDinosaurAnimalEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FROM_BOOK = SynchedEntityData.defineId(TameableBaseStatedDinosaurAnimalEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(TameableBaseStatedDinosaurAnimalEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> ASLEEP = SynchedEntityData.defineId(TameableBaseStatedDinosaurAnimalEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> RANDOM_NUMBER = SynchedEntityData.defineId(TameableBaseStatedDinosaurAnimalEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> RANDOM_BOOL = SynchedEntityData.defineId(TameableBaseStatedDinosaurAnimalEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ANIM_TIMER = SynchedEntityData.defineId(TameableBaseStatedDinosaurAnimalEntity.class, EntityDataSerializers.INT);

    public static final Logger LOGGER = LogManager.getLogger();
    private boolean orderedToSit;
    public int attackCooldown = 0;
    int lastTimeSinceHungry;
    public int alertTicks = 0;
    protected TameableBaseStatedDinosaurAnimalEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.reassessTameGoals();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new RandomSwimmingGoal(this, 1.5, 10));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        if(hasAvoidEntity()) {
            this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, TyrannosaurusEntity.class, 8.0F, 1.6D, 1.4D, EntitySelector.NO_SPECTATORS::test));
        }
        if(hasTargets() && !this.hasControllingPassenger()) {
            this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, false, false, entity -> entity.getType().is(getTargetTag())) {
                        @Override
                        public boolean canUse() {
                            return ((TameableBaseStatedDinosaurAnimalEntity) this.mob).isHungry() && super.canUse();
                        }
                    }
            );
        }
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }
    private static final int ATTACK_COOLDOWN = 30;
    @Override
    public void tick() {
        super.tick();

        if(attackCooldown > 0) {
            attackCooldown--;

        }

       // if (this.isSwinging() && !hasSwung()) {
       //     setSwinging(false);
       //     setHasSwung(true);
       //     performAttack();
       //     this.attackCooldown = ATTACK_COOLDOWN;
       // }

        if (attackCooldown <= 0) {
            //setHasSwung(false);
            setSwinging(false);
        }

        if(playingAnimation()) {
            setAnimationTimer(getAnimationTimer() - 1);
        }

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

    protected abstract void performAttack();

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


    @Nullable
    public LivingEntity getControllingPassenger() {
        for (Entity passenger : this.getPassengers()) {
            if (passenger instanceof Player) {
                return (Player) passenger;
            }
        }
        return null;
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
        if (super.doHurtTarget(entityIn) && getAttackSound() != null) {
            if(getAttackSound() != null) this.playSound(getAttackSound() , 0.1F, 1.0F);
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
        this.entityData.define(SWINGING, false);
        this.entityData.define(HAS_SWUNG, false);
        this.entityData.define(PASSIVE, 0);
        this.entityData.define(IS_FROM_EGG, false);
        this.entityData.define(FROM_BOOK, false);
        this.entityData.define(VARIANT, 0);
        this.entityData.define(ASLEEP, false);
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
        compound.putBoolean("IsSwinging", this.isSwinging());
        compound.putBoolean("HasSwung", this.hasSwung());
        compound.putInt("PassiveTicks", this.getPassiveTicks());
        compound.putBoolean("fromEgg", this.isFromEgg());
        compound.putInt("variant", this.getVariant());
        compound.putBoolean("IsAsleep", this.isAsleep());
        compound.putInt("randomNr", this.getRandomNumber());
        compound.putInt("animTimer", this.getAnimationTimer());
        compound.putBoolean("randomBool", this.getRandomBool());
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
        this.setIsFromEgg(compound.getBoolean("fromEgg"));
        this.setVariant(compound.getInt("variant"));
        this.setAsleep(compound.getBoolean("IsAsleep"));
        this.setRandomNumber(compound.getInt("randomNr"));
        this.setRandomBool(compound.getBoolean("randomBool"));
        this.setAnimationTimer(compound.getInt("animTimer"));
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

    public boolean isAsleep() {
        return this.entityData.get(ASLEEP);
    }

    public void setAsleep(boolean isAsleep) {
        this.entityData.set(ASLEEP, isAsleep);
    }
    public int getRandomAnimationNumber(int nr) {
        setRandomNumber(random.nextInt(nr));
        return getRandomNumber();
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

    /**
     * Determines the variant of the entity based on the provided variant change value.
     * The variant change value is used to determine the specific variant of the entity.
     * The method sets the appropriate attributes and variant number based on the variant change value.
     *
     * @param variantChange The variant change value used to determine the entity's variant.
     *                      The value should be within the range [0, 100].
     */
    public void determineVariant(int variantChange) {
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
    public void aiStep() {
        super.aiStep();
        if (this.isAsleep()) this.navigation.setSpeedModifier(0);
        if (!this.level().isClientSide) {
            if (this.alertTicks != 0) alertTicks--;
        }
    }

    public void setAwakeTicks(int ticks) {
        if (!this.level().isClientSide) {
            this.alertTicks = ticks;
        }
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
            return new TameableBaseStatedDinosaurAnimalEntity.DinoCustomNavigation(this, level);
        }
        else return super.createNavigation(level);
    }

    static class DinoCustomNavigation extends GroundPathNavigation {
        public DinoCustomNavigation(Mob p_33379_, Level p_33380_) {
            super(p_33379_, p_33380_);
        }
        protected PathFinder createPathFinder(int p_33382_) {
            this.nodeEvaluator = new TameableBaseStatedDinosaurAnimalEntity.CustomNodeEvaluator();
            return new PathFinder(this.nodeEvaluator, p_33382_);
        }
    }

    static class CustomNodeEvaluator extends WalkNodeEvaluator {
        protected BlockPathTypes evaluateBlockPathType(BlockGetter p_33387_, BlockPos p_33390_, BlockPathTypes p_33391_) {
            return p_33391_ == BlockPathTypes.LEAVES ? BlockPathTypes.OPEN : super.evaluateBlockPathType(p_33387_, p_33390_, p_33391_);
        }
    }


    public static boolean checkSurfaceDinoSpawnRules(EntityType<? extends TameableBaseStatedDinosaurAnimalEntity> p_186238_, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource p_186242_) {
        return level.getBlockState(pos.below()).is(UPTags.DINO_NATURAL_SPAWNABLE) && isBrightEnoughToSpawn(level, pos) && UnusualPrehistoryConfig.DINO_NATURAL_SPAWNING.get();    }

}
