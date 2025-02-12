package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityTameableBaseDinosaurAnimalNoFloat;
import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.*;
import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.SemiAquatic;
import com.peeko32213.unusualprehistory.common.entity.msc.util.navigator.SemiAquaticPathNavigation;
import com.peeko32213.unusualprehistory.common.entity.msc.util.navigator.WaterMoveController;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nonnull;

public class EntityHyneria extends EntityTameableBaseDinosaurAnimalNoFloat implements GeoEntity, SemiAquatic {
    private static final RawAnimation HYNERIA_SWIM_IDLE = RawAnimation.begin().thenLoop("animation.hyneria.swim_idle");
    private static final RawAnimation HYNERIA_SWIM = RawAnimation.begin().thenLoop("animation.hyneria.swim");
    private static final RawAnimation HYNERIA_SWIM_FAST = RawAnimation.begin().thenLoop("animation.hyneria.swim_fast");
    private static final RawAnimation HYNERIA_IDLE = RawAnimation.begin().thenLoop("animation.hyneria.idle");
    private static final RawAnimation HYNERIA_WALK = RawAnimation.begin().thenLoop("animation.hyneria.swim_idle");
    private static final RawAnimation HYNERIA_JUMP = RawAnimation.begin().thenLoop("animation.hyneria.swim");
    private static final RawAnimation HYNERIA_ATTACK_BLEND = RawAnimation.begin().thenLoop("animation.hyneria.idle");
    private static final RawAnimation HYNERIA_YAWN_BLEND = RawAnimation.begin().thenLoop("animation.hyneria.idle");

    private boolean isLandNavigator;

    public EntityHyneria(EntityType<? extends EntityTameableBaseDinosaurAnimalNoFloat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
        switchNavigator(false);
        this.moveControl = new EntityHyneria.MoveHelperController(this);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.ARMOR, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D);
    }

    protected void registerGoals() {
        super.registerGoals();
        if(!this.isBaby()) {
            this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.5F, false));
        }
        this.goalSelector.addGoal(2, new FindWaterGoal(this));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        if(!this.isBaby()) {
            this.goalSelector.addGoal(2, new HyneriaJumpGoal(this, 200));
        }
        if(!this.isBaby()) {
            this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 50, true, true, entity -> entity.getType().is(UPTags.HYNERIA_TARGETS)));
        }
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public void travel(Vec3 travelVector) {
        super.travel(travelVector);
    }

    protected PathNavigation createNavigation(Level p_27480_) {
        return new WaterBoundPathNavigation(this, p_27480_);
    }

    @Override
    protected void performAttack() {
    }

    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new WaterMoveController(this, 1.2F);
            this.navigation = new SemiAquaticPathNavigation(this, level());
            this.isLandNavigator = false;
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return UPEntities.HYNERIA.get().create(serverLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }

    static class MoveHelperController extends MoveControl {
        private final EntityHyneria dolphin;

        public MoveHelperController(EntityHyneria dolphinIn) {
            super(dolphinIn);
            this.dolphin = dolphinIn;
        }

        public void tick() {
            if (this.dolphin.isInWater()) {
                this.dolphin.setDeltaMovement(this.dolphin.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }

            if (this.operation == MoveControl.Operation.MOVE_TO && !this.dolphin.getNavigation().isDone()) {
                double d0 = this.wantedX - this.dolphin.getX();
                double d1 = this.wantedY - this.dolphin.getY();
                double d2 = this.wantedZ - this.dolphin.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d3 < (double) 2.5000003E-7F) {
                    this.mob.setZza(0.0F);
                } else {
                    float f = (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
                    this.dolphin.setYRot(this.rotlerp(this.dolphin.getYRot(), f, 10.0F));
                    this.dolphin.yBodyRot = this.dolphin.getYRot();
                    this.dolphin.yHeadRot = this.dolphin.getYRot();
                    float f1 = (float) (this.speedModifier * this.dolphin.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    if (this.dolphin.isInWater()) {
                        this.dolphin.setSpeed(f1 * 0.02F);
                        float f2 = -((float) (Mth.atan2(d1, Mth.sqrt((float) (d0 * d0 + d2 * d2))) * (double) (180F / (float) Math.PI)));
                        f2 = Mth.clamp(Mth.wrapDegrees(f2), -85.0F, 85.0F);
                        this.dolphin.setXRot(this.rotlerp(this.dolphin.getXRot(), f2, 5.0F));
                        float f3 = Mth.cos(this.dolphin.getXRot() * ((float) Math.PI / 180F));
                        float f4 = Mth.sin(this.dolphin.getXRot() * ((float) Math.PI / 180F));
                        this.dolphin.zza = f3 * f1;
                        this.dolphin.yya = -f4 * f1;
                    } else {
                        this.dolphin.setSpeed(f1 * 0.1F);
                    }

                }
            } else {
                this.dolphin.setSpeed(0.0F);
                this.dolphin.setXxa(0.0F);
                this.dolphin.setYya(0.0F);
                this.dolphin.setZza(0.0F);
            }
        }
    }

    @Override
    protected SoundEvent getAttackSound() {
        return UPSounds.HYNERIA_ATTACK.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.HYNERIA_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.HYNERIA_DEATH.get();
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(SoundEvents.FROG_STEP, 0.1F, 1.0F);
    }

    @Override
    public float getSoundVolume() {
        return 0.5F;
    }

    @Override
    protected int getKillHealAmount() {
        return 10;
    }

    @Override
    protected boolean canGetHungry() {
        return true;
    }

    @Override
    protected boolean hasTargets() {
        return true;
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
        return UPTags.HYNERIA_TARGETS;
    }

    public boolean isJumping() {
        return this.jumping;
    }

    protected <E extends EntityHyneria> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.isFromBook()) {
            return PlayState.CONTINUE;
        }

        if (event.isMoving() && !this.isInWater() && !this.isSwimming()) {
            event.setAnimation(HYNERIA_WALK);
            event.getController().setAnimationSpeed(1.0D);
            return PlayState.CONTINUE;
        }
        if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F) && this.isInWater()) {
            event.setAnimation(HYNERIA_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            if(this.isSprinting()){
                event.setAnimation(HYNERIA_SWIM_FAST);
                event.getController().setAnimationSpeed(2.0F);
            }
            return PlayState.CONTINUE;
        }
        if(this.isFallFlying() || this.isJumping()){
            event.setAnimation(HYNERIA_JUMP);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }

        if(playingAnimation())
        {
            return PlayState.CONTINUE;
        }

        if (isStillEnough() && getRandomAnimationNumber() == 0 && !this.isSwimming()) {
            int rand = getRandomAnimationNumber();
            if (rand < 10) {
                setAnimationTimer(300);
                return event.setAndContinue(HYNERIA_YAWN_BLEND);
            }
            return event.setAndContinue(HYNERIA_IDLE);
        }

        if (isStillEnough() && getRandomAnimationNumber() == 0 && this.isInWater()) {
            int rand = getRandomAnimationNumber();
            if (rand < 50) {
                setAnimationTimer(300);
                return event.setAndContinue(HYNERIA_SWIM_IDLE);
            }
            return event.setAndContinue(HYNERIA_SWIM_IDLE);
        }

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
    }

    private boolean isStillEnough() {
        return this.getDeltaMovement().horizontalDistance() < 0.05;
    }

    @Override
    public boolean shouldEnterWater() {
        return true;
    }

    @Override
    public boolean shouldLeaveWater() {
        return true;
    }

    @Override
    public boolean shouldStopMoving() {
        return true;
    }

    @Override
    public int getWaterSearchRange() {
        return 50;
    }
}