package com.peeko32213.unusualprehistory.common.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.BaseStatedDinosaurAnimalEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.util.state.*;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;
import java.util.List;

public class EdaphosaurusEntity extends BaseStatedDinosaurAnimalEntity implements GeoEntity, GeoAnimatable {

    private static final Ingredient FOOD_ITEMS = Ingredient.of(UPTags.EDAPHO_FOOD_ITEMS);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    // Movement animations
    private static final RawAnimation EDAPHO_WALK = RawAnimation.begin().thenLoop("animation.edaphosaurus.walk");
    private static final RawAnimation EDAPHO_SWIM = RawAnimation.begin().thenLoop("animation.edaphosaurus.swim");

    // Idle animations
    private static final RawAnimation EDAPHO_IDLE = RawAnimation.begin().thenLoop("animation.edaphosaurus.idle");
    private static final RawAnimation EDAPHO_SIT = RawAnimation.begin().thenLoop("animation.edaphosaurus.sit");
    private static final RawAnimation EDAPHO_SLEEP = RawAnimation.begin().thenLoop("animation.edaphosaurus.sleep");
    private static final RawAnimation EDAPHO_GRAZE = RawAnimation.begin().thenLoop("animation.edaphosaurus.graze");
    private static final RawAnimation EDAPHO_STRETCH = RawAnimation.begin().thenLoop("animation.edaphosaurus.stretch");
    private static final RawAnimation EDAPHO_SNEEZE = RawAnimation.begin().thenLoop("animation.edaphosaurus.sneeze");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> IDLE_1_AC = SynchedEntityData.defineId(EdaphosaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_2_AC = SynchedEntityData.defineId(EdaphosaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_3_AC = SynchedEntityData.defineId(EdaphosaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_4_AC = SynchedEntityData.defineId(EdaphosaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_5_AC = SynchedEntityData.defineId(EdaphosaurusEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityAction EDAPHO_IDLE_1_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper EDAPHO_IDLE_1_STATE =
            StateHelper.Builder.state(IDLE_1_AC, "edaphosaurus_sneeze")
                    .playTime(40)
                    .stopTime(60)
                    .affectsAI(true)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(EDAPHO_IDLE_1_ACTION)
                    .build();

    private static final EntityAction EDAPHO_IDLE_2_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper EDAPHO_IDLE_2_STATE =
            StateHelper.Builder.state(IDLE_2_AC, "edaphosaurus_graze")
                    .playTime(80)
                    .stopTime(100)
                    .affectsAI(true)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(EDAPHO_IDLE_2_ACTION)
                    .build();

    private static final EntityAction EDAPHO_IDLE_3_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper EDAPHO_IDLE_3_STATE =
            StateHelper.Builder.state(IDLE_3_AC, "edaphosaurus_stretch")
                    .playTime(60)
                    .stopTime(80)
                    .affectsAI(true)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(EDAPHO_IDLE_3_ACTION)
                    .build();

    private static final EntityAction EDAPHO_IDLE_4_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper EDAPHO_IDLE_4_STATE =
            StateHelper.Builder.state(IDLE_4_AC, "edaphosaurus_sit")
                    .playTime(160)
                    .stopTime(180)
                    .affectsAI(true)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(EDAPHO_IDLE_4_ACTION)
                    .build();

    private static final EntityAction EDAPHO_IDLE_5_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper EDAPHO_IDLE_5_STATE =
            StateHelper.Builder.state(IDLE_5_AC, "edaphosaurus_sleep")
                    .playTime(320)
                    .stopTime(340)
                    .affectsAI(true)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(EDAPHO_IDLE_5_ACTION)
                    .build();

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                EDAPHO_IDLE_1_STATE.getName(), EDAPHO_IDLE_1_STATE,
                EDAPHO_IDLE_2_STATE.getName(), EDAPHO_IDLE_2_STATE,
                EDAPHO_IDLE_3_STATE.getName(), EDAPHO_IDLE_3_STATE,
                EDAPHO_IDLE_3_STATE.getName(), EDAPHO_IDLE_4_STATE,
                EDAPHO_IDLE_3_STATE.getName(), EDAPHO_IDLE_5_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(EDAPHO_IDLE_1_STATE, 20),
                WeightedState.of(EDAPHO_IDLE_2_STATE, 40),
                WeightedState.of(EDAPHO_IDLE_3_STATE, 30),
                WeightedState.of(EDAPHO_IDLE_4_STATE, 10),
                WeightedState.of(EDAPHO_IDLE_5_STATE, 5)
        );
    }

    @Override
    public boolean getAction() {
        return false;
    }

    @Override
    public void setAction(boolean action) {}

    public EdaphosaurusEntity(EntityType<? extends BaseStatedDinosaurAnimalEntity> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 16.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.16D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.15D)
            .add(Attributes.FOLLOW_RANGE, 12.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(3, new RandomStateGoal<>(this));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(6, new PanicGoal(this, 1.35D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0F, 30));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    public float getStepHeight() {
        return 1.25F;
    }

    @Override
    protected SoundEvent getAttackSound() {
        return null;
    }

    @Override
    protected int getKillHealAmount() {
        return 0;
    }

    @Override
    protected boolean canGetHungry() {
        return false;
    }

    @Override
    protected boolean hasTargets() {
        return false;
    }

    @Override
    protected boolean hasAvoidEntity() {
        return false;
    }

    @Override
    protected boolean hasCustomNavigation() {
        return false;
    }

    @Override
    protected boolean hasMakeStuckInBlock() {
        return false;
    }

    @Override
    protected boolean customMakeStuckInBlockCheck(BlockState blockState) {
        return false;
    }

    @Override
    protected TagKey<EntityType<?>> getTargetTag() {
        return null;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob p_146744_) {
        return UPEntities.EDAPHOSAURUS.get().create(serverLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IDLE_1_AC, false);
        this.entityData.define(IDLE_2_AC, false);
        this.entityData.define(IDLE_3_AC, false);
        this.entityData.define(IDLE_4_AC, false);
        this.entityData.define(IDLE_5_AC, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }

    public void tick() {
        super.tick();
    }

    public boolean isFood(ItemStack stack) {
        return stack.is(UPTags.EDAPHO_FOOD_ITEMS);
    }

    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;

        if (isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {

            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            if(!this.level().isClientSide) {
                this.heal(4);
            }

            this.level().broadcastEntityEvent(this, (byte) 18);
            this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
            this.gameEvent(GameEvent.EAT, this);

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::controller));
    }

    protected <E extends EdaphosaurusEntity> PlayState controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

        if(this.isFromBook()) {
            return event.setAndContinue(EDAPHO_IDLE);
        }

        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInWater() && !getBooleanState(IDLE_1_AC) && !getBooleanState(IDLE_2_AC) && !getBooleanState(IDLE_3_AC) && !getBooleanState(IDLE_4_AC) && !getBooleanState(IDLE_5_AC)) {
            event.setAndContinue(EDAPHO_WALK);
            if(this.isSprinting()){
                event.getController().setAnimationSpeed(2.0F);
            }
            else {
                event.getController().setAnimationSpeed(1.0F);
            }
            return PlayState.CONTINUE;
        }

        if (this.isInWater() && !getBooleanState(IDLE_1_AC) && !getBooleanState(IDLE_2_AC) && !getBooleanState(IDLE_3_AC) && !getBooleanState(IDLE_4_AC) && !getBooleanState(IDLE_5_AC)) {
            event.setAndContinue(EDAPHO_SWIM);
            event.getController().setAnimationSpeed(0.5F);
            return PlayState.CONTINUE;
        }

        if (!this.isInWater()) {
            if (getBooleanState(IDLE_1_AC)) {
                return event.setAndContinue(EDAPHO_SNEEZE);
            }
            if (getBooleanState(IDLE_2_AC)) {
                return event.setAndContinue(EDAPHO_GRAZE);
            }
            if (getBooleanState(IDLE_3_AC)) {
                return event.setAndContinue(EDAPHO_STRETCH);
            }
            if (getBooleanState(IDLE_4_AC)) {
                return event.setAndContinue(EDAPHO_SIT);
            }
            if (getBooleanState(IDLE_5_AC)) {
                return event.setAndContinue(EDAPHO_SLEEP);
            }
            return event.setAndContinue(EDAPHO_IDLE);
        }

        return PlayState.CONTINUE;
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.EDAPHO_IDLE.get();
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UPSounds.EDAPHO_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.EDAPHO_DEATH.get();
    }

    protected void playStepSound(@NotNull BlockPos p_28301_, @NotNull BlockState p_28302_) {
        this.playSound(SoundEvents.WOLF_STEP, 0.25F, 1.0F);
    }

    @Override
    public float getSoundVolume() {
        return 0.75F;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 150;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.hasCustomName();
    }

    public boolean removeWhenFarAway(double d) {
        return !this.hasCustomName();
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_28134_, DifficultyInstance p_28135_, MobSpawnType p_28136_, @Nullable SpawnGroupData p_28137_, @Nullable CompoundTag p_28138_) {
        p_28137_ = super.finalizeSpawn(p_28134_, p_28135_, p_28136_, p_28137_, p_28138_);
        Level level = p_28134_.getLevel();
        if (level instanceof ServerLevel) {
            this.setPersistenceRequired();
        }
        return p_28137_;
    }

}
