package com.peeko32213.unusualprehistory.common.entity.custom.skeleton;

import com.peeko32213.unusualprehistory.common.entity.custom.base.SkeletonEntity;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class UnicornSkeleton extends SkeletonEntity {

    private static final Item SKELETON_ITEM = UPItems.UNICORN_SKELETON.get();

    private static final EntityDataAccessor<Boolean> BASE = SynchedEntityData.defineId(UnicornSkeleton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ROAR = SynchedEntityData.defineId(UnicornSkeleton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> WALK = SynchedEntityData.defineId(UnicornSkeleton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CHARGE = SynchedEntityData.defineId(UnicornSkeleton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RECONSTRUCTION_1 = SynchedEntityData.defineId(UnicornSkeleton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RECONSTRUCTION_2 = SynchedEntityData.defineId(UnicornSkeleton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FUCKED = SynchedEntityData.defineId(UnicornSkeleton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> WORM = SynchedEntityData.defineId(UnicornSkeleton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DEAD = SynchedEntityData.defineId(UnicornSkeleton.class, EntityDataSerializers.BOOLEAN);

    // Poses
    private static final RawAnimation POSE_BASE = RawAnimation.begin().thenLoop("animation.unicorn_skeleton.base_pose");
    private static final RawAnimation POSE_ROAR = RawAnimation.begin().thenLoop("animation.unicorn_skeleton.roar_pose");
    private static final RawAnimation POSE_WALK = RawAnimation.begin().thenLoop("animation.unicorn_skeleton.walk_pose");
    private static final RawAnimation POSE_CHARGE = RawAnimation.begin().thenLoop("animation.unicorn_skeleton.charge_pose");
    private static final RawAnimation POSE_RECONSTRUCTION_1 = RawAnimation.begin().thenLoop("animation.unicorn_skeleton.reconstruction_1_pose");
    private static final RawAnimation POSE_RECONSTRUCTION_2 = RawAnimation.begin().thenLoop("animation.unicorn_skeleton.reconstruction_2_pose");
    private static final RawAnimation POSE_FUCKED = RawAnimation.begin().thenLoop("animation.unicorn_skeleton.fucked_up_reconstruction_pose");
    private static final RawAnimation POSE_WORM = RawAnimation.begin().thenLoop("animation.unicorn_skeleton.worm_pose");
    private static final RawAnimation POSE_DEAD = RawAnimation.begin().thenLoop("animation.unicorn_skeleton.dead_pose");

    public UnicornSkeleton(EntityType<? extends UnicornSkeleton> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        if (!this.isLocked()) {
            if (itemStack.isEmpty() && pHand == InteractionHand.MAIN_HAND && !this.isNatural() && pPlayer.isShiftKeyDown() && !this.isWaxed()) {
                this.playSound(SoundEvents.SKELETON_STEP, 0.15F, 1.0F);

                if (!this.isBase() && !this.isRoar() && !this.isWorm() && !this.isFucked() && !this.isWalk() && !this.isCharge() && !this.isConstruct1() && !this.isConstruct2() && !this.isDead()) {
                    this.setBase(true);
                }

                if (this.isBase()) {
                    this.setBase(false);
                    this.setRoar(true);
                } else if (this.isRoar()) {
                    this.setRoar(false);
                    this.setWalk(true);
                } else if (this.isWalk()) {
                    this.setWalk(false);
                    this.setCharge(true);
                } else if (this.isCharge()) {
                    this.setCharge(false);
                    this.setConstruct1(true);
                } else if (this.isConstruct1()) {
                    this.setConstruct1(false);
                    this.setConstruct2(true);
                } else if (this.isConstruct2()) {
                    this.setConstruct2(false);
                    this.setFucked(true);
                } else if (this.isFucked()) {
                    this.setFucked(false);
                    this.setWorm(true);
                } else if (this.isWorm()) {
                    this.setWorm(false);
                    this.setDead(true);
                } else if (this.isDead()) {
                    this.setDead(false);
                    this.setBase(true);
                }
            }
        }
        return super.interact(pPlayer, pHand);
    }

    public ItemStack getPickResult() {
        return new ItemStack(UPItems.UNICORN_SKELETON.get());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BASE, false);
        this.entityData.define(ROAR, false);
        this.entityData.define(WALK, false);
        this.entityData.define(CHARGE, false);
        this.entityData.define(RECONSTRUCTION_1, false);
        this.entityData.define(RECONSTRUCTION_2, false);
        this.entityData.define(FUCKED, false);
        this.entityData.define(WORM, false);
        this.entityData.define(DEAD, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setBase(compound.getBoolean("basePose"));
        this.setRoar(compound.getBoolean("roarPose"));
        this.setWalk(compound.getBoolean("walkPose"));
        this.setCharge(compound.getBoolean("chargePose"));
        this.setConstruct1(compound.getBoolean("reconstruction1Pose"));
        this.setConstruct2(compound.getBoolean("reconstruction2Pose"));
        this.setFucked(compound.getBoolean("fuckedPose"));
        this.setWorm(compound.getBoolean("wormPose"));
        this.setDead(compound.getBoolean("deadPose"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("basePose", this.isBase());
        compound.putBoolean("roarPose", this.isRoar());
        compound.putBoolean("walkPose", this.isWalk());
        compound.putBoolean("chargePose", this.isCharge());
        compound.putBoolean("reconstruction1Pose", this.isConstruct1());
        compound.putBoolean("reconstruction2Pose", this.isConstruct2());
        compound.putBoolean("fuckedPose", this.isFucked());
        compound.putBoolean("wormPose", this.isWorm());
        compound.putBoolean("deadPose", this.isDead());
    }

    @Override
    public void broken(DamageSource pDamageSource) {
        ItemStack itemstack = new ItemStack(SKELETON_ITEM);
        Block.popResource(this.level(), this.blockPosition(), itemstack);
        super.broken(pDamageSource);
    }

    public boolean isBase() {
        return this.entityData.get(BASE);
    }

    private void setBase(boolean isBase) {
        this.entityData.set(BASE, isBase);
    }

    public boolean isRoar() {
        return this.entityData.get(ROAR);
    }

    private void setRoar(boolean isRoar) {
        this.entityData.set(ROAR, isRoar);
    }

    public boolean isWalk() {
        return this.entityData.get(WALK);
    }

    private void setWalk(boolean isWalk) {
        this.entityData.set(WALK, isWalk);
    }

    public boolean isCharge() {
        return this.entityData.get(CHARGE);
    }

    private void setCharge(boolean isCharge) {
        this.entityData.set(CHARGE, isCharge);
    }

    public boolean isConstruct1() {
        return this.entityData.get(RECONSTRUCTION_1);
    }

    private void setConstruct1(boolean isConstruct1) {
        this.entityData.set(RECONSTRUCTION_1, isConstruct1);
    }

    public boolean isConstruct2() {
        return this.entityData.get(RECONSTRUCTION_2);
    }

    private void setConstruct2(boolean isConstruct2) {
        this.entityData.set(RECONSTRUCTION_2, isConstruct2);
    }

    public boolean isFucked() {
        return this.entityData.get(FUCKED);
    }

    private void setFucked(boolean isFucked) {
        this.entityData.set(FUCKED, isFucked);
    }

    public boolean isWorm() {
        return this.entityData.get(WORM);
    }

    private void setWorm(boolean isWorm) {
        this.entityData.set(WORM, isWorm);
    }

    public boolean isDead() {
        return this.entityData.get(DEAD);
    }

    private void setDead(boolean isDead) {
        this.entityData.set(DEAD, isDead);
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 3, this::predicate));
    }

    private <E extends SkeletonEntity> PlayState predicate(AnimationState<E> event) {
        if(this.isNatural() || this.isDead()) {
            return event.setAndContinue(POSE_DEAD);
        }
        else if(this.isRoar()) {
            return event.setAndContinue(POSE_ROAR);
        }
        else if(this.isWalk()) {
            return event.setAndContinue(POSE_WALK);
        }
        else if(this.isCharge()) {
            return event.setAndContinue(POSE_CHARGE);
        }
        else if(this.isConstruct1()) {
            return event.setAndContinue(POSE_RECONSTRUCTION_1);
        }
        else if(this.isConstruct2()) {
            return event.setAndContinue(POSE_RECONSTRUCTION_2);
        }
        else if(this.isFucked()) {
            return event.setAndContinue(POSE_FUCKED);
        }
        else if(this.isWorm()) {
            return event.setAndContinue(POSE_WORM);
        }
        else return event.setAndContinue(POSE_BASE);
    }
}
