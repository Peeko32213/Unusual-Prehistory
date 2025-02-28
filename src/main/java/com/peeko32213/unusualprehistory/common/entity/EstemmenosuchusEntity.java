package com.peeko32213.unusualprehistory.common.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.BaseStatedDinosaurAnimalEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.util.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.msc.util.state.RandomStateGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.msc.util.state.WeightedState;
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
import net.minecraft.tags.TagKey;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
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

public class EstemmenosuchusEntity extends BaseStatedDinosaurAnimalEntity implements GeoEntity, GeoAnimatable {

    private static final Ingredient FOOD_ITEMS = Ingredient.of(UPTags.ESTEMME_FOOD_ITEMS);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    // Movement animations
    private static final RawAnimation ESTEMME_WALK = RawAnimation.begin().thenLoop("animation.estemmenosuchus.walk");
    private static final RawAnimation ESTEMME_RUN = RawAnimation.begin().thenLoop("animation.estemmenosuchus.run");
    private static final RawAnimation ESTEMME_SWIM = RawAnimation.begin().thenLoop("animation.estemmenosuchus.swim");

    // Idle animations
    private static final RawAnimation ESTEMME_IDLE = RawAnimation.begin().thenLoop("animation.estemmenosuchus.idle");
    private static final RawAnimation ESTEMME_BELLOW = RawAnimation.begin().thenPlay("animation.estemmenosuchus.bellow_blend");
    private static final RawAnimation ESTEMME_YAWN = RawAnimation.begin().thenPlay("animation.estemmenosuchus.yawn_blend");
    private static final RawAnimation ESTEMME_SIT = RawAnimation.begin().thenLoop("animation.estemmenosuchus.sit");
    private static final RawAnimation ESTEMME_SLEEP = RawAnimation.begin().thenLoop("animation.estemmenosuchus.sleep");

    // Attack animations
    private static final RawAnimation BITE_1 = RawAnimation.begin().thenPlay("animation.estemmenosuchus.attack1_blend");
    private static final RawAnimation BITE_2 = RawAnimation.begin().thenPlay("animation.estemmenosuchus.attack2_blend");
    private static final RawAnimation RAMMING_START = RawAnimation.begin().thenPlay("animation.estemmenosuchus.ramming_start");
    private static final RawAnimation RAMMING = RawAnimation.begin().thenLoop("animation.estemmenosuchus.ramming");

    // Misc animations
    private static final RawAnimation CRUSH = RawAnimation.begin().thenPlay("animation.estemmenosuchus.crush_blend");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> IDLE_1_AC = SynchedEntityData.defineId(EstemmenosuchusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_2_AC = SynchedEntityData.defineId(EstemmenosuchusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_3_AC = SynchedEntityData.defineId(EstemmenosuchusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_4_AC = SynchedEntityData.defineId(EstemmenosuchusEntity.class, EntityDataSerializers.BOOLEAN);

    // Idle actions
    private static final EntityAction ESTEMME_IDLE_1_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper ESTEMME_IDLE_1_STATE =
            StateHelper.Builder.state(IDLE_1_AC, "estemmenosuchus_bellow")
                .playTime(60)
                .stopTime(120)
                .entityAction(ESTEMME_IDLE_1_ACTION)
                .build();

    private static final EntityAction ESTEMME_IDLE_2_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper ESTEMME_IDLE_2_STATE =
            StateHelper.Builder.state(IDLE_2_AC, "estemmenosuchus_yawn")
                .playTime(60)
                .stopTime(140)
                .entityAction(ESTEMME_IDLE_2_ACTION)
                .build();

    private static final EntityAction ESTEMME_IDLE_3_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper ESTEMME_IDLE_3_STATE =
            StateHelper.Builder.state(IDLE_3_AC, "estemmenosuchus_sit")
                .playTime(160)
                .stopTime(200)
                .affectsAI(true)
                .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                .entityAction(ESTEMME_IDLE_3_ACTION)
                .build();

    private static final EntityAction ESTEMME_IDLE_4_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper ESTEMME_IDLE_4_STATE =
            StateHelper.Builder.state(IDLE_4_AC, "estemmenosuchus_sleep")
                .playTime(320)
                .stopTime(400)
                .affectsAI(true)
                .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                .entityAction(ESTEMME_IDLE_4_ACTION)
                .build();

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                ESTEMME_IDLE_1_STATE.getName(), ESTEMME_IDLE_1_STATE,
                ESTEMME_IDLE_2_STATE.getName(), ESTEMME_IDLE_2_STATE,
                ESTEMME_IDLE_3_STATE.getName(), ESTEMME_IDLE_3_STATE,
                ESTEMME_IDLE_4_STATE.getName(), ESTEMME_IDLE_4_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(ESTEMME_IDLE_1_STATE, 20),
                WeightedState.of(ESTEMME_IDLE_2_STATE, 18),
                WeightedState.of(ESTEMME_IDLE_3_STATE, 8),
                WeightedState.of(ESTEMME_IDLE_4_STATE, 4)
        );
    }

    @Override
    public boolean getAction() {
        return false;
    }

    @Override
    public void setAction(boolean action) {}

    public EstemmenosuchusEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 80.0D)
            .add(Attributes.ARMOR, 8.0)
            .add(Attributes.MOVEMENT_SPEED, 0.14D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
            .add(Attributes.FOLLOW_RANGE, 12.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(2, new RandomStateGoal<>(this));
        this.goalSelector.addGoal(0, new FloatGoal(this));
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
        return 10;
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
        return UPEntities.ESTEMMENOSUCHUS.get().create(serverLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IDLE_1_AC, false);
        this.entityData.define(IDLE_2_AC, false);
        this.entityData.define(IDLE_3_AC, false);
        this.entityData.define(IDLE_4_AC, false);
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
        return stack.is(UPTags.ESTEMME_FOOD_ITEMS);
    }

    // Heal mob
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;

        if (isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {

            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            if(!this.level().isClientSide) {
                this.heal(10);
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
        controllers.add(new AnimationController<>(this, "normal", 5, this::controller));
        controllers.add(new AnimationController<>(this, "blend", 5, this::controller)
            .triggerableAnim("bellow", ESTEMME_BELLOW)
            .triggerableAnim("yawn", ESTEMME_YAWN));
    }

    protected <E extends EstemmenosuchusEntity> PlayState controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

        if(this.isFromBook()) {
            return event.setAndContinue(ESTEMME_IDLE);
        }

        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInWater() && !getBooleanState(IDLE_3_AC) && !getBooleanState(IDLE_4_AC)) {

            event.setAndContinue(ESTEMME_WALK);

            if(this.isSprinting()){
                event.setAndContinue(ESTEMME_RUN);
                event.getController().setAnimationSpeed(2.0F);
            }
            else {
                event.getController().setAnimationSpeed(1.0F);
            }
            return PlayState.CONTINUE;
        }

        if (this.isInWater() && !getBooleanState(IDLE_3_AC) && !getBooleanState(IDLE_4_AC)) {
            event.setAndContinue(ESTEMME_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }

        if (!this.isInWater()) {
            if (getBooleanState(IDLE_1_AC)) {
                event.setControllerSpeed(1.0F);
                triggerAnim("blend", "bellow");
                return PlayState.CONTINUE;
            }
            if (getBooleanState(IDLE_2_AC)) {
                event.setControllerSpeed(1.0F);
                triggerAnim("blend", "yawn");
                return PlayState.CONTINUE;
            }
            if (getBooleanState(IDLE_3_AC)) {
                event.setControllerSpeed(0.35F);
                return event.setAndContinue(ESTEMME_SIT);
            }
            if (getBooleanState(IDLE_4_AC)) {
                event.setControllerSpeed(0.35F);
                return event.setAndContinue(ESTEMME_SLEEP);
            }
            event.setControllerSpeed(1.0F);
            return event.setAndContinue(ESTEMME_IDLE);
        }

        event.setControllerSpeed(1.0F);
        return PlayState.CONTINUE;
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.ESTEMME_IDLE.get();
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UPSounds.ESTEMME_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.ESTEMME_DEATH.get();
    }

    protected void playStepSound(@NotNull BlockPos p_28301_, @NotNull BlockState p_28302_) {
        this.playSound(UPSounds.MAJUNGA_STEP.get(), 0.3F, 1.0F);
    }

    @Override
    public float getSoundVolume() {
        return 1.0F;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 135;
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
            {
                this.setPersistenceRequired();
            }
        }
        return p_28137_;
    }
}
