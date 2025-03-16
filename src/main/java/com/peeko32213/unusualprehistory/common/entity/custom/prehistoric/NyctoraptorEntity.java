package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.goal.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.util.goal.PrehistoricFollowOwnerGoal;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.ICustomFollower;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmoothGroundNavigation;
import com.peeko32213.unusualprehistory.core.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class NyctoraptorEntity extends PrehistoricEntity implements ICustomFollower {

    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    public float flapping = 1.0F;
    private float nextFlap = 1.0F;

    // Movement animations
    private static final RawAnimation NYCTO_WALK = RawAnimation.begin().thenLoop("animation.nyctoraptor.walk");
    private static final RawAnimation NYCTO_RUN = RawAnimation.begin().thenLoop("animation.nyctoraptor.run");
    private static final RawAnimation NYCTO_SWIM = RawAnimation.begin().thenLoop("animation.nyctoraptor.swim");
    private static final RawAnimation NYCTO_FALL = RawAnimation.begin().thenLoop("animation.nyctoraptor.fall");

    // Idle animations
    private static final RawAnimation NYCTO_IDLE = RawAnimation.begin().thenLoop("animation.nyctoraptor.idle");
    private static final RawAnimation NYCTO_SIT = RawAnimation.begin().thenLoop("animation.nyctoraptor.sit");
    private static final RawAnimation NYCTO_SLEEP = RawAnimation.begin().thenLoop("animation.nyctoraptor.sleep");

    // Attack animations

    // Misc animations
    private static final RawAnimation NYCTO_EAT = RawAnimation.begin().thenLoop("animation.nyctoraptor.eat");

    // Idle accessors

    // States
    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
        );
    }

    // Actions
    @Override
    public boolean getAction() {
        return false;
    }

    @Override
    public void setAction(boolean action) {}

    // Animation control
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<NyctoraptorEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);

        AnimationController<NyctoraptorEntity> blend = new AnimationController<>(this, "blend", 5, this::predicate)
                ;
        controllers.add(blend);

//        AnimationController<NyctoraptorEntity> flap = new AnimationController<>(this, "flapController", 5, this::flapPredicate);
//        controllers.add(flap);
    }

    protected <E extends NyctoraptorEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

        if (this.isFromBook()) {
            event.setAndContinue(NYCTO_IDLE);
        }

        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInSittingPose() && !this.isInWater()) {
            if (this.isSprinting()) {
                event.setAndContinue(NYCTO_RUN);
                event.getController().setAnimationSpeed(1.0D);
                return PlayState.CONTINUE;
            } else if (event.isMoving()) {
                event.setAndContinue(NYCTO_WALK);
                event.getController().setAnimationSpeed(1.0D);
                return PlayState.CONTINUE;
            }
        }

        if (this.isInWater()) {
            event.setAndContinue(NYCTO_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }

        if (this.isInSittingPose() && !this.isInWater() && !this.isSwimming()) {
            return event.setAndContinue(NYCTO_SIT);
        }

        if (!this.isInSittingPose()) {
            event.setAndContinue(NYCTO_SLEEP);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }

        if (!this.isInWater()) {
            return event.setAndContinue(NYCTO_IDLE);
        }
        return PlayState.CONTINUE;
    }

//    protected <E extends NyctoraptorEntity> PlayState flapPredicate(final AnimationState<E> event) {
//        if (!this.onGround() && !this.isInWater()) {
//            event.getController().setAnimation(NYCTO_FALL);
//            event.getController().setAnimationSpeed(1.0D);
//            return PlayState.CONTINUE;
//        }
//        event.getController().forceAnimationReset();
//        return PlayState.STOP;
//    }

    // Body control / navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        SmartBodyHelper helper = new SmartBodyHelper(this);
        helper.bodyLagMoving = 0.65F;
        helper.bodyLagStill = 0.2F;
        return helper;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return new SmoothGroundNavigation(this, levelIn);
    }

    public NyctoraptorEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.25F);
    }

    // Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 30D)
            .add(Attributes.MOVEMENT_SPEED, 0.21D)
            .add(Attributes.ATTACK_DAMAGE, 9D);
    }

    // Goals
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0F, 30));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new PrehistoricFollowOwnerGoal(this, 1.2D, 5.0F, 2.0F, false));
        this.targetSelector.addGoal(8, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(8, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(8, new OwnerHurtTargetGoal(this));
    }

    // Flap
    protected boolean isFlapping() {
        return this.flyDist > this.nextFlap;
    }

    protected void onFlap() {
        this.nextFlap = this.flyDist + this.flapSpeed / 2.0F;
    }

    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed += (this.onGround() ? -1.0F : 4.0F) * 0.3F;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround() && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }

        this.flapping *= 0.9F;
        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && vec3.y < 0.0) {
            this.setDeltaMovement(vec3.multiply(1.0, 0.85, 1.0));
        }

        this.flap += this.flapping * 2.0F;
    }

    // Sprinting
    @Override
    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.25D);
        } else {
            this.setSprinting(false);
        }
        super.customServerAiStep();
    }

    // Mob interactions
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, hand);
        if (itemstack.is(UPItems.RAW_MAMMOTH.get())) {
            if (!isTame()) {
                this.usePlayerItem(player, hand, itemstack);
                if (getRandom().nextInt(3) == 0) {
                    this.tame(player);
                    this.level().broadcastEntityEvent(this, (byte) 7);
                    itemstack.shrink(1);
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                }
            }
        }
        if (isTame() && isOwnedBy(player)) {
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                if (!this.level().isClientSide) {
                    this.heal((float) Objects.requireNonNull(itemstack.getFoodProperties(this)).getNutrition());
                }
                this.gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;
            } else {
                this.setCommand((this.getCommand() + 1) % 3);

                if (this.getCommand() == 3) {
                    this.setCommand(0);
                }
                player.displayClientMessage(Component.translatable("entity.unusualprehistory.all.command_" + this.getCommand(), this.getName()), true);
                boolean sit = this.getCommand() == 2;
                if (sit) {
                    this.setOrderedToSit(true);
                    return InteractionResult.SUCCESS;
                } else {
                    this.setOrderedToSit(false);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void tick() {
        super.tick();
    }

    // Sounds
    protected SoundEvent getAmbientSound() {
        return UPSounds.NYCTORAPTOR_IDLE.get();
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UPSounds.NYCTORAPTOR_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.NYCTORAPTOR_DEATH.get();
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
    }

    @Override
    public float getSoundVolume() {
        if(this.isBaby()){
            return 0.75F;
        }
        else{
            return 1.0F;
        }
    }

    @Override
    protected int getKillHealAmount() {
        return 4;
    }

    // Save data
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public boolean shouldFollow() {
        return true;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        NyctoraptorEntity nyctoraptor = UPEntities.NYCTORAPTOR.get().create(serverLevel);
        nyctoraptor.setVariant(this.getVariant());
        return nyctoraptor;
    }

    // Variants
    public void determineVariant(int variantChange){
        if (variantChange <= 50) {
            this.setVariant(1);
        }
        else {
            this.setVariant(0);
        }
    }
}
