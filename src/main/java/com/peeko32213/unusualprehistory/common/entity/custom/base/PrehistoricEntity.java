package com.peeko32213.unusualprehistory.common.entity.custom.base;

import com.peeko32213.unusualprehistory.UnusualPrehistoryConfig;
import com.peeko32213.unusualprehistory.common.entity.animation.state.IStateAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.RandomStateGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.TyrannosaurusEntity;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IBookEntity;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IHatchableEntity;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
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
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public abstract class PrehistoricEntity extends TamableAnimal implements GeoEntity, GeoAnimatable, IHatchableEntity, IBookEntity, IStateAction {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final EntityDataAccessor<Boolean> HUNGRY = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> TIME_TILL_HUNGRY = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> PASSIVE = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_FROM_EGG = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FROM_BOOK = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> RANDOM_NUMBER = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> RANDOM_BOOL = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ANIM_TIMER = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> ASLEEP = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);

    int lastTimeSinceHungry;
    public int alertTicks = 0;

    private boolean orderedToSit;
    public float sitProgress;

    protected PrehistoricEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        if(!this.isAsleep() && this.getTarget() == null) {
            this.goalSelector.addGoal(2, new RandomStateGoal<>(this));
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
    public void aiStep() {
        super.aiStep();
        if (this.isAsleep()) {
            this.navigation.stop();
            this.navigation.setSpeedModifier(0);
        }
    }

    @Override
    public boolean canSprint() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        tickHunger();

        if(playingAnimation()) {
            setAnimationTimer(getAnimationTimer() - 1);
        }

        if (this.isOrderedToSit() && sitProgress < 5F) {
            sitProgress++;
        }
        if (!this.isOrderedToSit() && sitProgress > 0F) {
            sitProgress--;
        }

        this.setOrderedToSit(this.getCommand() == 2 && !this.isVehicle());
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

    @Nullable
    protected abstract SoundEvent getAttackSound();
    protected abstract int getKillHealAmount();
    protected abstract boolean canGetHungry();
    protected abstract boolean hasTargets();
    protected abstract boolean hasAvoidEntity();
    protected abstract boolean hasCustomNavigation();
    protected abstract boolean hasMakeStuckInBlock();
    protected abstract boolean customMakeStuckInBlockCheck(BlockState blockState);

    @Nullable
    protected abstract TagKey<EntityType<?>> getTargetTag();

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HUNGRY, true);
        this.entityData.define(TIME_TILL_HUNGRY, 0);
        this.entityData.define(SADDLED, false);
        this.entityData.define(IS_FROM_EGG, false);
        this.entityData.define(FROM_BOOK, false);
        this.entityData.define(VARIANT, 0);
        this.entityData.define(RANDOM_BOOL, false);
        this.entityData.define(RANDOM_NUMBER,0);
        this.entityData.define(ANIM_TIMER, 0);
        this.entityData.define(ASLEEP, false);
        this.entityData.define(COMMAND, 0);
        this.entityData.define(ANIMATION_STATE, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("IsHungry", this.isHungry());
        compound.putInt("TimeTillHungry", this.getTimeTillHungry());
        compound.putBoolean("Saddle", this.isSaddled());
        compound.putBoolean("fromEgg", this.isFromEgg());
        compound.putInt("variant", this.getVariant());
        compound.putInt("randomNr", this.getRandomNumber());
        compound.putInt("animTimer", this.getAnimationTimer());
        compound.putBoolean("randomBool", this.getRandomBool());
        compound.putBoolean("IsAsleep", this.isAsleep());
        compound.putInt("command", this.getCommand());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setHungry(compound.getBoolean("IsHungry"));
        this.setTimeTillHungry(compound.getInt("TimeTillHungry"));
        this.setSaddled(compound.getBoolean("Saddle"));
        this.setIsFromEgg(compound.getBoolean("fromEgg"));
        this.setVariant(compound.getInt("variant"));
        this.setRandomNumber(compound.getInt("randomNr"));
        this.setRandomBool(compound.getBoolean("randomBool"));
        this.setAnimationTimer(compound.getInt("animTimer"));
        this.setAsleep(compound.getBoolean("IsAsleep"));
        this.setCommand(compound.getInt("command"));
    }

    public boolean getBooleanState(EntityDataAccessor<Boolean> pKey) {
        return this.entityData.get(pKey);
    }

    public void setBooleanState(EntityDataAccessor<Boolean> pKey, boolean state) {
        this.entityData.set(pKey, state);
    }

    public int getCommand() {
        return this.entityData.get(COMMAND);
    }

    public void setCommand(int command) {
        this.entityData.set(COMMAND, command);
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

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        ResourceLocation entityLoc = EntityType.getKey(this.getType());
        populateEntityFromData(entityLoc, true);
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

    public int getAnimationState() {
        return this.entityData.get(ANIMATION_STATE);
    }

    public void setAnimationState(int anim) {
        this.entityData.set(ANIMATION_STATE, anim);
    }

    public boolean isStillEnough() {
        return this.getDeltaMovement().horizontalDistance() < 0.05;
    }

    public void populateEntityFromData(ResourceLocation location, boolean onlyGoals) {
            //SomeDataManager manager = SomeDataManager;
            //SomeEntityData someEntityData = manager.get(location);
            //someEntityData.apply(this);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        int variantChange = this.random.nextInt(0, 100);
        this.determineVariant(variantChange);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
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

    public boolean isAsleep() {
        return this.entityData.get(ASLEEP);
    }

    public void setAsleep(boolean isAsleep) {
        this.entityData.set(ASLEEP, isAsleep);
    }

    public double getPassengersRidingOffset() {
        return 0;
    }

    public boolean isAlliedTo(@NotNull Entity entityIn) {
        if (this.isTame()) {
            LivingEntity livingentity = this.getOwner();
            if (entityIn == livingentity) {
                return true;
            }
            if (entityIn instanceof TamableAnimal) {
                return ((TamableAnimal) entityIn).isOwnedBy(livingentity);
            }
            if (livingentity != null) {
                return livingentity.isAlliedTo(entityIn);
            }
        }
        return entityIn.is(this);
    }

    public void setAwakeTicks(int ticks) {
        if (!this.level().isClientSide) {
            this.alertTicks = ticks;
        }
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
