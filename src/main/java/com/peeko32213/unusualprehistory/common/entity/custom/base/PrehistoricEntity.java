package com.peeko32213.unusualprehistory.common.entity.custom.base;

import com.peeko32213.unusualprehistory.UnusualPrehistoryConfig;
import com.peeko32213.unusualprehistory.common.data.entity.synced.SerializableSynchedEntityData;
import com.peeko32213.unusualprehistory.common.entity.animation.state.IStateAction;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IBookEntity;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IHatchableEntity;
import com.peeko32213.unusualprehistory.core.other.tags.UPBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public abstract class PrehistoricEntity extends TamableAnimal implements GeoEntity, GeoAnimatable, IHatchableEntity, IBookEntity, IStateAction {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Boolean> PERFORMING_ACTION = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_STANDING_UP = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_FROM_EGG = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FROM_BOOK = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> RANDOM_BOOL = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ANIM_TIMER = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(PrehistoricEntity.class, EntityDataSerializers.INT);

    public float sitProgress;
    protected int standingUpTime;

    protected PrehistoricEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Override
    public boolean canSprint() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if(playingAnimation()) {
            setAnimationTimer(getAnimationTimer() - 1);
        }

        if(!this.level().isClientSide) {
            if(this.isStandingUp() && standingUpTime++ > getStandingUpTime()) {
                this.setOrderedToSit(false);
                this.setIsStandingUp(false);
                standingUpTime = 0;
            }
        }
    }

    // Interact
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, hand);
        InteractionResult type = super.mobInteract(player, hand);

        if (!interactionresult.consumesAction() && !type.consumesAction()) {
            if (isTame() && isOwnedBy(player) && !isFood(itemstack) && hand == InteractionHand.MAIN_HAND) {
                if (!player.isShiftKeyDown() && !isBaby() && isSaddled() && isTame() && isOwnedBy(player)) {
                    if(!level().isClientSide) {
                        player.startRiding(this);
                    }
                }
                else if (canOwnerCommand(player)) {
                    this.setCommand(this.getCommand() + 1);
                    if (this.getCommand() == 3) {
                        this.setCommand(0);
                    }
                    player.displayClientMessage(Component.translatable("entity.unusualprehistory.all.command_" + this.getCommand(), this.getName()), true);
                    boolean sit = this.getCommand() == 1;
                    if (sit) {
                        this.setOrderedToSit(true);
                    }
                    else if(this.isInSittingPose()) {
                        this.setIsStandingUp(true);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return type;
    }

    // Heal on kill
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

    public boolean doHurtTarget(Entity entityIn) {
        return super.doHurtTarget(entityIn);
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

    @Nullable
    protected abstract int getKillHealAmount();

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PERFORMING_ACTION, false);
        this.entityData.define(SADDLED, false);
        this.entityData.define(IS_FROM_EGG, false);
        this.entityData.define(FROM_BOOK, false);
        this.entityData.define(VARIANT, 0);
        this.entityData.define(RANDOM_BOOL, false);
        this.entityData.define(ANIM_TIMER, 0);
        this.entityData.define(COMMAND, 0);
        this.entityData.define(IS_STANDING_UP, false);
        this.entityData.define(ANIMATION_STATE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Saddle", this.isSaddled());
        compound.putBoolean("fromEgg", this.isFromEgg());
        compound.putInt("variant", this.getVariant());
        compound.putInt("animTimer", this.getAnimationTimer());
        compound.putBoolean("randomBool", this.getRandomBool());
        compound.putInt("command", this.getCommand());
        compound.putBoolean("standingUp", this.isStandingUp());
        compound.putInt("standingUpTime", this.standingUpTime);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSaddled(compound.getBoolean("Saddle"));
        this.setIsFromEgg(compound.getBoolean("fromEgg"));
        this.setVariant(compound.getInt("variant"));
        this.setRandomBool(compound.getBoolean("randomBool"));
        this.setAnimationTimer(compound.getInt("animTimer"));
        this.setCommand(compound.getInt("command"));
        this.setIsStandingUp(compound.getBoolean("standingUp"));
        this.standingUpTime = compound.getInt("standingUpTime");
    }

    // Animation states
    public boolean getBooleanState(EntityDataAccessor<Boolean> pKey) {
        return this.entityData.get(pKey);
    }

    public void setBooleanState(EntityDataAccessor<Boolean> pKey, boolean state) {
        this.entityData.set(pKey, state);
    }

    // Actions
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

    public boolean isSDataTrue(SerializableSynchedEntityData data) {
        return data.isEqualToValue(this, true);
    }

    // Tame command
    public int getCommand() {
        return this.entityData.get(COMMAND);
    }
    public void setCommand(int command) {
        this.entityData.set(COMMAND, command);
    }

    public boolean canOwnerCommand(Player ownerPlayer) {
        return false;
    }

    public boolean isInSittingPose() {
        return super.isInSittingPose() && !(this.isVehicle() || this.isPassenger());
    }

    // Standing up
    public boolean isStandingUp() {
        return this.entityData.get(IS_STANDING_UP);
    }

    public void setIsStandingUp(boolean standingUp) {
        this.entityData.set(IS_STANDING_UP, standingUp);
    }

    public int getStandingUpTime() {
        return 20;
    }

    // Saddled
    public boolean isSaddled() {
        return this.entityData.get(SADDLED);
    }
    public void setSaddled(boolean saddled) {
        this.entityData.set(SADDLED, saddled);
    }

    // From egg
    public boolean isFromEgg() {
        return this.entityData.get(IS_FROM_EGG);
    }
    public void setIsFromEgg(boolean fromEgg) {
        this.entityData.set(IS_FROM_EGG, fromEgg);
    }

    // From book
    public boolean isFromBook() {
        return this.entityData.get(FROM_BOOK);
    }
    public void setIsFromBook(boolean fromBook) {
        this.entityData.set(FROM_BOOK, fromBook);
    }

    @Override
    public void setFromBook(boolean fromBook) {
        this.entityData.set(FROM_BOOK, fromBook);
    }

    // Persistence
    public boolean requiresCustomPersistence() {
        return this.isFromEgg();
    }
    public boolean removeWhenFarAway(double d) {
        return !this.isFromEgg();
    }

    // Variant
    public int getVariant() {
        return this.entityData.get(VARIANT);
    }
    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }
    public void determineVariant(int variantChange) {}

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        ResourceLocation entityLoc = EntityType.getKey(this.getType());
        populateEntityFromData(entityLoc, true);
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

    // Riding offset
    public double getPassengersRidingOffset() {
        return 0;
    }

    // Allied to self or owner
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

    // Spawn rules
    public static boolean checkSurfaceDinoSpawnRules(EntityType<? extends PrehistoricEntity> dino, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource p_186242_) {
        boolean canSpawn = level.getBlockState(pos.below()).is(UPBlockTags.DINO_NATURAL_SPAWNABLE) && isBrightEnoughToSpawn(level, pos) && UnusualPrehistoryConfig.DINO_NATURAL_SPAWNING.get();
        return canSpawn;
    }
}
