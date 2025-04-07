package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic;

import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.CustomizableRandomSwimGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.attack.DunkleosteusAttackGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricAquaticEntity;
import com.peeko32213.unusualprehistory.common.entity.util.kinematics.IKSolver;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
import com.peeko32213.unusualprehistory.core.other.tags.UPItemTags;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.other.tags.UPEntityTypeTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;
import java.util.List;

public class DunkleosteusEntity extends PrehistoricAquaticEntity {

    public IKSolver TailKinematics;

    private static final EntityDataAccessor<Integer> DUNK_SIZE = SynchedEntityData.defineId(DunkleosteusEntity.class, EntityDataSerializers.INT);

    private static final EntityDimensions SMALL_SIZE = EntityDimensions.scalable(0.75F, 0.6F);
    private static final EntityDimensions MEDIUM_SIZE = EntityDimensions.scalable(1.2F, 1.1F);
    private static final EntityDimensions LARGE_SIZE = EntityDimensions.scalable(2.2F, 2.1F);

    // Movement animations
    private static final RawAnimation DUNK_SWIM = RawAnimation.begin().thenLoop("animation.dunkleosteus.swim");
    private static final RawAnimation DUNK_SWIM_SPRINT = RawAnimation.begin().thenLoop("animation.dunkleosteus.swim_sprint");

    // Idle animations
    private static final RawAnimation DUNK_IDLE = RawAnimation.begin().thenLoop("animation.dunkleosteus.idle");
    private static final RawAnimation DUNK_BEACHED = RawAnimation.begin().thenLoop("animation.dunkleosteus.flop");

    // Attack animations
    private static final RawAnimation DUNK_ATTACK = RawAnimation.begin().thenLoop("animation.dunkleosteus.bite");

    private int passiveFor = 0;

    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        SmartBodyHelper helper = new SmartBodyHelper(this);
        helper.bodyLagMoving = 0.25F;
        helper.bodyLagStill = 0.18F;
        return helper;
    }

    public DunkleosteusEntity(EntityType<? extends PrehistoricAquaticEntity> entityType, Level level) {
        super(entityType, level);
        this.lookControl = new SmoothSwimmingLookControl(this, 2);
        this.moveControl = new SmoothSwimmingMoveControl(this, 1000, 2, 0.02F, 0.1F, true);
        this.TailKinematics = new IKSolver(this, 3, 4, true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 10.0D)
            .add(Attributes.ATTACK_DAMAGE, 2.0D)
            .add(Attributes.ARMOR, 2.0)
            .add(Attributes.FOLLOW_RANGE, 20.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(0, new CustomizableRandomSwimGoal(this, 1.2, 1, 70, 70, 3));
        this.goalSelector.addGoal(1, new DunkleosteusAttackGoal(this));
        this.targetSelector.addGoal(7, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 100, true, false, this::canAttack) {
        @Override
        public boolean canUse() {
            if(this.mob instanceof DunkleosteusEntity dunkleosteus) {
                if(dunkleosteus.getDunkSize() > 0) return false;
            }
            return super.canUse();
        }});
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 50, true, true, entity -> entity.getType().is(UPEntityTypeTags.BIG_DUNK_TARGETS)){
        @Override
        public boolean canUse() {
            if(this.mob instanceof DunkleosteusEntity dunkleosteus) {
                if(dunkleosteus.getDunkSize() == 2) return false;
            }
            return super.canUse();
        }});
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 50, true, true, entity -> entity.getType().is(UPEntityTypeTags.MEDIUM_DUNK_TARGETS)){
        @Override
        public boolean canUse() {
            if(this.mob instanceof DunkleosteusEntity dunkleosteus) {
                if(dunkleosteus.getDunkSize() == 1) return false;
            }
            return super.canUse();
        }});
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 50, true, true, entity -> entity.getType().is(UPEntityTypeTags.SMALL_DUNK_TARGETS)){
        @Override
        public boolean canUse() {
            if(this.mob instanceof DunkleosteusEntity dunkleosteus) {
                if(dunkleosteus.getDunkSize() == 0) return false;
            }
            return super.canUse();
        }});
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if(getDunkSize() == 2.0F) {
            return source.is(DamageTypeTags.IS_PROJECTILE) | super.isInvulnerableTo(source);
        }
        return super.isInvulnerableTo(source);
    }

    public boolean passive = false;

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(UPItemTags.DUNK_FOOD_PACIFY) && !this.passive) {

            if (!this.level().isClientSide) {

                if (!player.isCreative()) {
                    itemstack.shrink(1);
                }

                this.heal(20.0F);
                this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
                this.passive = true;
                return InteractionResult.SUCCESS;
            }
        }
        else if (itemstack.is(UPItemTags.DUNK_FOOD) && this.passive) {

            if (!this.level().isClientSide) {

                if (!player.isCreative()) {
                    itemstack.shrink(1);
                }

                this.heal(10.0F);
                this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
                this.passive = false;
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    public boolean canAttack(@NotNull LivingEntity entity) {
        boolean prev = super.canAttack(entity);
        if(prev && passiveFor > 0 && entity instanceof LivingEntity && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().getUUID().equals(entity.getUUID()))){
            return false;
        }
        return prev;
    }

    public void travel(@NotNull Vec3 travelVector) {
        super.travel(travelVector);
    }
 
    protected @NotNull PathNavigation createNavigation(@NotNull Level p_27480_) {
        return new WaterBoundPathNavigation(this, p_27480_);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UPSounds.DUNK_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.DUNK_DEATH.get();
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    public float getVoicePitch() {
        final float f = (3 - this.getDunkSize()) * 0.33F;
        return (float) (super.getVoicePitch() * Math.sqrt(f) * 1.2F);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DUNK_SIZE, 0);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("PassiveFor", passiveFor);
        compound.putFloat("DunkSize", this.getDunkSize());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        passiveFor = compound.getInt("PassiveFor");
        this.setDunkSize(compound.getInt("DunkSize"));
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (DUNK_SIZE.equals(accessor)) {
            this.refreshDimensions();
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10F * this.getDunkSize() + 10F);
            this.getAttribute(Attributes.ARMOR).setBaseValue(2F * this.getDunkSize() + 6F);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2F * this.getDunkSize() + 6F);
            this.heal(50F);
        }
        super.onSyncedDataUpdated(accessor);
    }

    public int getDunkSize() {
        return Mth.clamp(this.entityData.get(DUNK_SIZE), 0, 2);
    }

    public void setDunkSize(int dunkSize) {
        this.entityData.set(DUNK_SIZE, dunkSize);
    }

    @Override
    public EntityDimensions getDimensions(Pose poseIn) {
        return getDimsForDunk().scale(this.getScale());
    }

    private EntityDimensions getDimsForDunk() {
        return switch (this.getDunkSize()) {
            case 1 -> MEDIUM_SIZE;
            case 2 -> LARGE_SIZE;
            default -> SMALL_SIZE;
        };
    }

    public void tick() {
        super.tick();

        if (this.passiveFor > 0) {
            passiveFor--;
        }

        this.TailKinematics.calculateTailAnglesNoConstraint(this);
    }

    @Override
    public void aiStep() {
        if (!this.isInWater() && this.onGround() && this.verticalCollision) {
            this.setDeltaMovement(this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4F, (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F));
            this.setOnGround(false);
            this.hasImpulse = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
        }
        super.aiStep();
    }

    // Animation control
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<DunkleosteusEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);

        AnimationController<DunkleosteusEntity> attack = new AnimationController<>(this, "attackController", 5, this::attackPredicate);
        controllers.add(attack);
    }

    protected <E extends DunkleosteusEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.isFromBook()) {
            return event.setAndContinue(DUNK_IDLE);
        }

        if(!this.isFromBook()) {
            if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F) && this.isInWater()) {
                if(this.isSprinting()){
                    event.setAndContinue(DUNK_SWIM_SPRINT);
                    event.getController().setAnimationSpeed(1.0F + event.getLimbSwingAmount());
                } else {
                    event.setAndContinue(DUNK_SWIM);
                    event.getController().setAnimationSpeed(1.0F + event.getLimbSwingAmount());
                }
                return PlayState.CONTINUE;
            }
            if (!this.isInWater()) {
                event.setAndContinue(DUNK_BEACHED);
                event.getController().setAnimationSpeed(1.0F + event.getLimbSwingAmount());
                return PlayState.CONTINUE;
            } else if (this.isInWater()) {
                event.setAndContinue(DUNK_IDLE);
                return PlayState.CONTINUE;
            }
        }
        return PlayState.CONTINUE;
    }

    // Attack animations
    protected <E extends DunkleosteusEntity> PlayState attackPredicate(final AnimationState<E> event) {
        int animState = this.getAnimationState();

        if (animState == 21) {
            event.setAndContinue(DUNK_ATTACK);
            return PlayState.CONTINUE;
        }
        else if (animState == 0) {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }
        else return PlayState.CONTINUE;
    }

    @Override
    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted() && !this.isBaby()) {
            this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.25D);
        } else {
            this.setSprinting(false);
        }
        super.customServerAiStep();
    }

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return null;
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return List.of();
    }

    @Override
    public boolean getAction() {
        return false;
    }

    @Override
    public void setAction(boolean action) {}

    public void killed() {
        passiveFor = 2400 + random.nextInt(100, 1200);
        this.heal(15);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {

        int variantChange = this.random.nextInt(0, 100);

        if (variantChange <= 30) {
            this.setDunkSize(1);
            this.TailKinematics = new IKSolver(this, 3, 3);
        }
        else if (variantChange <= 60) {
            this.setDunkSize(2);
            this.TailKinematics = new IKSolver(this, 3, 4);
        }
        else {
            this.setDunkSize(0);
            this.TailKinematics = new IKSolver(this, 3, 1);
        }
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        DunkleosteusEntity dunkleosteus = UPEntities.DUNK.get().create(serverLevel);
        dunkleosteus.setDunkSize(this.getDunkSize());
        return dunkleosteus;
    }
}
