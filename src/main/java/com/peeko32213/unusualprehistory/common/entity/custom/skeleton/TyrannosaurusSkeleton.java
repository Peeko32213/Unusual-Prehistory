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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class TyrannosaurusSkeleton extends SkeletonEntity {

    private static final EntityDataAccessor<Boolean> BASE = SynchedEntityData.defineId(TyrannosaurusSkeleton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ROAR = SynchedEntityData.defineId(TyrannosaurusSkeleton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> EAT = SynchedEntityData.defineId(TyrannosaurusSkeleton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> REFERENCE = SynchedEntityData.defineId(TyrannosaurusSkeleton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> WALK = SynchedEntityData.defineId(TyrannosaurusSkeleton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RUN = SynchedEntityData.defineId(TyrannosaurusSkeleton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RETRO = SynchedEntityData.defineId(TyrannosaurusSkeleton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DEAD = SynchedEntityData.defineId(TyrannosaurusSkeleton.class, EntityDataSerializers.BOOLEAN);

    // Poses
    private static final RawAnimation POSE_BASE = RawAnimation.begin().thenLoop("animation.tyrannosaurus_skeleton.pose_base");
    private static final RawAnimation POSE_ROAR = RawAnimation.begin().thenLoop("animation.tyrannosaurus_skeleton.pose_roar");
    private static final RawAnimation POSE_EAT = RawAnimation.begin().thenLoop("animation.tyrannosaurus_skeleton.pose_eat");
    private static final RawAnimation POSE_REFERENCE = RawAnimation.begin().thenLoop("animation.tyrannosaurus_skeleton.pose_reference");
    private static final RawAnimation POSE_WALK = RawAnimation.begin().thenLoop("animation.tyrannosaurus_skeleton.pose_walk");
    private static final RawAnimation POSE_RUN = RawAnimation.begin().thenLoop("animation.tyrannosaurus_skeleton.pose_run");
    private static final RawAnimation POSE_RETRO = RawAnimation.begin().thenLoop("animation.tyrannosaurus_skeleton.pose_retro");
    private static final RawAnimation POSE_DEAD = RawAnimation.begin().thenLoop("animation.tyrannosaurus_skeleton.pose_dead");

    public TyrannosaurusSkeleton(EntityType<? extends TyrannosaurusSkeleton> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        if (itemStack.isEmpty() && pHand == InteractionHand.MAIN_HAND && !this.isNatural() && pPlayer.isShiftKeyDown()) {
            this.playSound(SoundEvents.SKELETON_STEP, 0.15F, 1.0F);

            if (!this.isBase() && !this.isRun() && !this.isRoar() && !this.isEat() && !this.isReference() && !this.isWalk() && !this.isRetro() && !this.isDead()) {
                this.setBase(true);
            }

            if (this.isBase()) {
                this.setBase(false);
                this.setRun(true);
            }
            else if (this.isRun()) {
                this.setRun(false);
                this.setRoar(true);
            }
            else if (this.isRoar()) {
                this.setRoar(false);
                this.setEat(true);
            }
            else if (this.isEat()) {
                this.setEat(false);
                this.setReference(true);
            }
            else if (this.isReference()) {
                this.setReference(false);
                this.setWalk(true);
            }
            else if (this.isWalk()) {
                this.setWalk(false);
                this.setRetro(true);
            }
            else if (this.isRetro()) {
                this.setRetro(false);
                this.setDead(true);
            }
            else if (this.isDead()) {
                this.setDead(false);
                this.setBase(true);
            }
        }
        return super.interact(pPlayer, pHand);
    }

    public ItemStack getPickResult() {
        return new ItemStack(UPItems.TYRANNO_SKELETON.get());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BASE, false);
        this.entityData.define(ROAR, false);
        this.entityData.define(EAT, false);
        this.entityData.define(REFERENCE, false);
        this.entityData.define(WALK, false);
        this.entityData.define(RUN, false);
        this.entityData.define(RETRO, false);
        this.entityData.define(DEAD, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setBase(compound.getBoolean("basePose"));
        this.setRoar(compound.getBoolean("roarPose"));
        this.setEat(compound.getBoolean("eatPose"));
        this.setReference(compound.getBoolean("referencePose"));
        this.setWalk(compound.getBoolean("walkPose"));
        this.setRun(compound.getBoolean("runPose"));
        this.setRetro(compound.getBoolean("retroPose"));
        this.setDead(compound.getBoolean("deadPose"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("basePose", this.isBase());
        compound.putBoolean("roarPose", this.isRoar());
        compound.putBoolean("eatPose", this.isEat());
        compound.putBoolean("referencePose", this.isReference());
        compound.putBoolean("walkPose", this.isWalk());
        compound.putBoolean("runPose", this.isReference());
        compound.putBoolean("retroPose", this.isRetro());
        compound.putBoolean("deadPose", this.isDead());
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

    public boolean isEat() {
        return this.entityData.get(EAT);
    }

    private void setEat(boolean isEat) {
        this.entityData.set(EAT, isEat);
    }

    public boolean isReference() {
        return this.entityData.get(REFERENCE);
    }

    private void setReference(boolean isReference) {
        this.entityData.set(REFERENCE, isReference);
    }

    public boolean isWalk() {
        return this.entityData.get(WALK);
    }

    private void setWalk(boolean isWalk) {
        this.entityData.set(WALK, isWalk);
    }

    public boolean isRun() {
        return this.entityData.get(RUN);
    }

    private void setRun(boolean isRun) {
        this.entityData.set(RUN, isRun);
    }

    public boolean isRetro() {
        return this.entityData.get(RETRO);
    }

    private void setRetro(boolean isRetro) {
        this.entityData.set(RETRO, isRetro);
    }

    public boolean isDead() {
        return this.entityData.get(DEAD);
    }

    private void setDead(boolean isDead) {
        this.entityData.set(DEAD, isDead);
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::predicate));
    }

    private <E extends SkeletonEntity> PlayState predicate(AnimationState<E> event) {
        if(this.isNatural() || this.isDead()) {
            return event.setAndContinue(POSE_DEAD);
        }
        else if(this.isRoar()) {
            return event.setAndContinue(POSE_ROAR);
        }
        else if(this.isEat()) {
            return event.setAndContinue(POSE_EAT);
        }
        else if(this.isReference()) {
            return event.setAndContinue(POSE_REFERENCE);
        }
        else if(this.isWalk()) {
            return event.setAndContinue(POSE_WALK);
        }
        else if(this.isRun()) {
            return event.setAndContinue(POSE_RUN);
        }
        else if(this.isRetro()) {
            return event.setAndContinue(POSE_RETRO);
        }
        else return event.setAndContinue(POSE_BASE);
    }
}
