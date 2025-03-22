package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.PrehistoricFollowOwnerGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.attack.BarinasuchusMeleeAttackGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.ICustomFollower;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmoothGroundNavigation;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nonnull;
import java.util.List;

public class BarinasuchusEntity extends PrehistoricEntity implements ICustomFollower {

    public boolean isFood(ItemStack stack) {
        return stack.is(UPTags.BARINA_FOOD);
    }

    // Movement animations
    private static final RawAnimation BARINA_WALK = RawAnimation.begin().thenLoop("animation.barinasuchus.walk");
    private static final RawAnimation BARINA_SPRINT = RawAnimation.begin().thenLoop("animation.barinasuchus.run");
    private static final RawAnimation BARINA_SWIM = RawAnimation.begin().thenLoop("animation.barinasuchus.swim");

    // Idle animations
    private static final RawAnimation BARINA_IDLE = RawAnimation.begin().thenLoop("animation.barinasuchus.idle");
    private static final RawAnimation BARINA_SIT = RawAnimation.begin().thenPlay("animation.barinasuchus.sit_start").thenLoop("animation.barinasuchus.sit");
    private static final RawAnimation BARINA_SIT_END = RawAnimation.begin().thenPlay("animation.barinasuchus.sit_end");
    private static final RawAnimation BARINA_SLEEP = RawAnimation.begin().thenPlay("animation.barinasuchus.sleep");
    private static final RawAnimation BARINA_YAWN = RawAnimation.begin().thenPlay("animation.barinasuchus.yawn_blend");
    private static final RawAnimation BARINA_SHAKE = RawAnimation.begin().thenPlay("animation.barinasuchus.shake_blend");
    private static final RawAnimation BARINA_SCRATCH_1 = RawAnimation.begin().thenPlay("animation.barinasuchus.scratch_blend1");
    private static final RawAnimation BARINA_SCRATCH_2 = RawAnimation.begin().thenPlay("animation.barinasuchus.scratch_blend2");

    // Attack animations
    private static final RawAnimation BARINA_BITE_1 = RawAnimation.begin().thenPlay("animation.barinasuchus.bite_blend1");
    private static final RawAnimation BARINA_BITE_2 = RawAnimation.begin().thenPlay("animation.barinasuchus.bite_blend2");

    // Misc animations
    private static final RawAnimation BARINA_SNAP = RawAnimation.begin().thenPlay("animation.barinasuchus.snap_blend");
    private static final RawAnimation BARINA_THREATEN = RawAnimation.begin().thenPlay("animation.barinasuchus.threaten");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> IDLE_1_AC = SynchedEntityData.defineId(BarinasuchusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_2_AC = SynchedEntityData.defineId(BarinasuchusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_3_AC = SynchedEntityData.defineId(BarinasuchusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_4_AC = SynchedEntityData.defineId(BarinasuchusEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityAction BARINA_IDLE_1_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper BARINA_IDLE_1_STATE =
            StateHelper.Builder.state(IDLE_1_AC, "barinasuchus_yawn")
                    .playTime(80)
                    .stopTime(170)
                    .entityAction(BARINA_IDLE_1_ACTION)
                    .build();

    private static final EntityAction BARINA_IDLE_2_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper BARINA_IDLE_2_STATE =
            StateHelper.Builder.state(IDLE_2_AC, "barinasuchus_shake")
                    .playTime(60)
                    .stopTime(150)
                    .entityAction(BARINA_IDLE_2_ACTION)
                    .build();

    private static final EntityAction BARINA_IDLE_3_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper BARINA_IDLE_3_STATE =
            StateHelper.Builder.state(IDLE_3_AC, "barinasuchus_scratch_1")
                    .playTime(30)
                    .stopTime(100)
                    .entityAction(BARINA_IDLE_3_ACTION)
                    .build();

    private static final EntityAction BARINA_IDLE_4_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper BARINA_IDLE_4_STATE =
            StateHelper.Builder.state(IDLE_4_AC, "barinasuchus_scratch_2")
                    .playTime(30)
                    .stopTime(100)
                    .entityAction(BARINA_IDLE_4_ACTION)
                    .build();

    // Idle states
    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                BARINA_IDLE_1_STATE.getName(), BARINA_IDLE_1_STATE,
                BARINA_IDLE_2_STATE.getName(), BARINA_IDLE_2_STATE,
                BARINA_IDLE_3_STATE.getName(), BARINA_IDLE_3_STATE,
                BARINA_IDLE_4_STATE.getName(), BARINA_IDLE_4_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(BARINA_IDLE_1_STATE, 8),
                WeightedState.of(BARINA_IDLE_2_STATE, 9),
                WeightedState.of(BARINA_IDLE_3_STATE, 7),
                WeightedState.of(BARINA_IDLE_4_STATE, 7)
        );
    }

    // Actions
    @Override
    public boolean getAction() {
        return false;
    }

    @Override
    public void setAction(boolean action) {}

    // Animation sounds
    private void soundListener(SoundKeyframeEvent<BarinasuchusEntity> event) {
        BarinasuchusEntity barina = event.getAnimatable();
        if (barina.level().isClientSide) {
            if (event.getKeyframeData().getSound().equals("barina_bite")) {
                barina.level().playLocalSound(barina.getX(), barina.getY(), barina.getZ(), UPSounds.BARINA_BITE.get(), barina.getSoundSource(), 0.5F, barina.getVoicePitch(), false);
            }
        }
    }

    // Animation control
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<BarinasuchusEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);

        AnimationController<BarinasuchusEntity> blend = new AnimationController<>(this, "blend", 5, this::predicate)
                .triggerableAnim("yawn", BARINA_YAWN)
                .triggerableAnim("shake", BARINA_SHAKE)
                .triggerableAnim("scratch1", BARINA_SCRATCH_1)
                .triggerableAnim("scratch2", BARINA_SCRATCH_2)
            ;
        blend.setSoundKeyframeHandler(this::soundListener);
        controllers.add(blend);

        AnimationController<BarinasuchusEntity> attack = new AnimationController<>(this, "attackController", 5, this::attackPredicate);
        attack.setSoundKeyframeHandler(this::soundListener);
        controllers.add(attack);
    }

    protected <E extends BarinasuchusEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

        if(this.isFromBook()){
            return event.setAndContinue(BARINA_IDLE);
        }

        if(!this.isFromBook()) {

            if (this.isInSittingPose()) {
                if(this.isStandingUp()) {
                    event.setAndContinue(BARINA_SIT_END);
                    return PlayState.CONTINUE;
                }
                event.setAndContinue(BARINA_SIT);
                return PlayState.CONTINUE;
            }

            if (this.isInWater()) {
                event.setAndContinue(BARINA_SWIM);
                event.getController().setAnimationSpeed(1.0F);
                return PlayState.CONTINUE;
            }

            else if(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming() && !this.isInWater()) {
                if(this.isSprinting() && !this.isBaby()) {
                    event.setAndContinue(BARINA_SPRINT);
                } else {
                    event.setAndContinue(BARINA_WALK);
                }
                event.getController().setAnimationSpeed(1.0F);
                return PlayState.CONTINUE;
            }

            else {
                if (!this.isInWater()) {
                    if (getBooleanState(IDLE_1_AC)) {
                        if (this.isStillEnough()) {
                            triggerAnim("blend", "yawn");
                            return event.setAndContinue(BARINA_IDLE);
                        }
                        else {
                            triggerAnim("blend", "yawn");
                            return PlayState.CONTINUE;
                        }
                    }
                    if (getBooleanState(IDLE_2_AC)) {
                        if (this.isStillEnough()) {
                            triggerAnim("blend", "shake");
                            return event.setAndContinue(BARINA_IDLE);
                        }
                        else {
                            triggerAnim("blend", "shake");
                            return PlayState.CONTINUE;
                        }
                    }
                    if (getBooleanState(IDLE_3_AC)) {
                        if (this.isStillEnough()) {
                            triggerAnim("blend", "scratch1");
                            return event.setAndContinue(BARINA_IDLE);
                        }
                        else {
                            triggerAnim("blend", "scratch1");
                            return PlayState.CONTINUE;
                        }
                    }
                    if (getBooleanState(IDLE_4_AC)) {
                        if (this.isStillEnough()) {
                            triggerAnim("blend", "scratch2");
                            return event.setAndContinue(BARINA_IDLE);
                        }
                        else {
                            triggerAnim("blend", "scratch2");
                            return PlayState.CONTINUE;
                        }
                    }
                    return event.setAndContinue(BARINA_IDLE);
                }
            }
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }

    // Attack animations
    protected <E extends BarinasuchusEntity> PlayState attackPredicate(final AnimationState<E> event) {
        int animState = this.getAnimationState();

        if (animState == 21) {
            event.setAndContinue(BARINA_BITE_1);
            return PlayState.CONTINUE;
        }
        else if (animState == 22) {
            event.setAndContinue(BARINA_BITE_2);
            return PlayState.CONTINUE;
        }
        else if (animState == 0) {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }
        else return PlayState.CONTINUE;
    }

    // Body control / navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        SmartBodyHelper helper = new SmartBodyHelper(this);
        helper.bodyLagMoving = 0.5F;
        helper.bodyLagStill = 0.25F;
        return helper;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return new SmoothGroundNavigation(this, levelIn);
    }

    public BarinasuchusEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
        super(entityType, level);
    }

    // Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 36.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.185D)
            .add(Attributes.ARMOR, 8.0D)
            .add(Attributes.ATTACK_DAMAGE, 12.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.2D);
    }

    @Override
    public float getStepHeight() {
        return 1.25F;
    }

    // Goals
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BarinasuchusMeleeAttackGoal(this, 1.75, true));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D, 30));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 100, true, false, this::canAttack));
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.targetSelector.addGoal(6, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(7, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(9, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(3, new PrehistoricFollowOwnerGoal(this, 1.2D, 5.0F, 2.0F, false));
    }

    // Mob interactions
    public @NotNull InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if (isTame() && isOwnedBy(player)) {
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                this.heal((float)itemstack.getFoodProperties(this).getNutrition());
                this.gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.5D);
        } else {
            this.setSprinting(false);
        }
        super.customServerAiStep();
    }

    // Sounds
    protected void playStepSound(@NotNull BlockPos p_28301_, @NotNull BlockState p_28302_) {
        this.playSound(UPSounds.MAJUNGA_STEP.get(), 0.1F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.BARINA_IDLE.get();
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UPSounds.BARINA_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.BARINA_DEATH.get();
    }

    @Override
    public float getSoundVolume() {
        if(this.isBaby()) {
            return 0.5F;
        } else {
            return 0.8F;
        }
    }

    @Override
    public int getMaxHeadYRot() {
        return 12;
    }

    @Override
    protected int getKillHealAmount() {
        return 9;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UPEntities.BARINASUCHUS.get().create(serverLevel);
    }

    // Data
    @Override
    protected void defineSynchedData() {
        this.entityData.define(IDLE_1_AC, false);
        this.entityData.define(IDLE_2_AC, false);
        this.entityData.define(IDLE_3_AC, false);
        this.entityData.define(IDLE_4_AC, false);
        super.defineSynchedData();
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }

    public void tick() {
        super.tick();
    }

    // Follow
    @Override
    public boolean shouldFollow() {
        return this.getCommand() == 1;
    }

    // Command
    @Override
    public boolean canOwnerCommand(Player ownerPlayer) {
        return true;
    }
}
