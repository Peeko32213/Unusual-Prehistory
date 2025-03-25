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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class TriceratopsSkeleton extends SkeletonEntity {

    private static final EntityDataAccessor<Boolean> BASE = SynchedEntityData.defineId(TriceratopsSkeleton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> WARN = SynchedEntityData.defineId(TriceratopsSkeleton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> EAT = SynchedEntityData.defineId(TriceratopsSkeleton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> REGAL = SynchedEntityData.defineId(TriceratopsSkeleton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FIGHT = SynchedEntityData.defineId(TriceratopsSkeleton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RUN = SynchedEntityData.defineId(TriceratopsSkeleton.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DEAD = SynchedEntityData.defineId(TriceratopsSkeleton.class, EntityDataSerializers.BOOLEAN);

    // Poses
    private static final RawAnimation POSE_BASE = RawAnimation.begin().thenLoop("animation.triceratops_skeleton.pose_base");
    private static final RawAnimation POSE_WARN = RawAnimation.begin().thenLoop("animation.triceratops_skeleton.pose_warn");
    private static final RawAnimation POSE_EAT = RawAnimation.begin().thenLoop("animation.triceratops_skeleton.pose_eat");
    private static final RawAnimation POSE_REGAL = RawAnimation.begin().thenLoop("animation.triceratops_skeleton.pose_regal");
    private static final RawAnimation POSE_FIGHT = RawAnimation.begin().thenLoop("animation.triceratops_skeleton.pose_fight");
    private static final RawAnimation POSE_RUN = RawAnimation.begin().thenLoop("animation.triceratops_skeleton.pose_run");
    private static final RawAnimation POSE_DEAD = RawAnimation.begin().thenLoop("animation.triceratops_skeleton.pose_dead");

    public TriceratopsSkeleton(EntityType<? extends TriceratopsSkeleton> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        if (itemStack.isEmpty() && pHand == InteractionHand.MAIN_HAND && !this.isNatural() && pPlayer.isShiftKeyDown()) {
            this.playSound(SoundEvents.SKELETON_STEP, 0.15F, 1.0F);

            if (!this.isBase() && !this.isRun() && !this.isWarn() && !this.isEat() && !this.isRegal() && !this.isFight() && !this.isDead()) {
                this.setBase(true);
            }

            if (this.isBase()) {
                this.setBase(false);
                this.setRun(true);
            }
            else if (this.isRun()) {
                this.setRun(false);
                this.setWarn(true);
            }

            else if (this.isWarn()) {
                this.setWarn(false);
                this.setEat(true);
            }
            else if (this.isEat()) {
                this.setEat(false);
                this.setRegal(true);
            }
            else if (this.isRegal()) {
                this.setRegal(false);
                this.setFight(true);
            }
            else if (this.isFight()) {
                this.setFight(false);
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
        return new ItemStack(UPItems.TRIKE_SKELETON.get());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BASE, false);
        this.entityData.define(WARN, false);
        this.entityData.define(EAT, false);
        this.entityData.define(REGAL, false);
        this.entityData.define(FIGHT, false);
        this.entityData.define(RUN, false);
        this.entityData.define(DEAD, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setBase(compound.getBoolean("basePose"));
        this.setWarn(compound.getBoolean("warnPose"));
        this.setEat(compound.getBoolean("eatPose"));
        this.setRegal(compound.getBoolean("regalPose"));
        this.setFight(compound.getBoolean("fightPose"));
        this.setRun(compound.getBoolean("runPose"));
        this.setDead(compound.getBoolean("deadPose"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("basePose", this.isBase());
        compound.putBoolean("warnPose", this.isWarn());
        compound.putBoolean("eatPose", this.isEat());
        compound.putBoolean("regalPose", this.isRegal());
        compound.putBoolean("fightPose", this.isFight());
        compound.putBoolean("runPose", this.isRegal());
        compound.putBoolean("deadPose", this.isDead());
    }

    public boolean isBase() {
        return this.entityData.get(BASE);
    }

    private void setBase(boolean isBase) {
        this.entityData.set(BASE, isBase);
    }

    public boolean isWarn() {
        return this.entityData.get(WARN);
    }

    private void setWarn(boolean isWarn) {
        this.entityData.set(WARN, isWarn);
    }

    public boolean isEat() {
        return this.entityData.get(EAT);
    }

    private void setEat(boolean isEat) {
        this.entityData.set(EAT, isEat);
    }

    public boolean isRegal() {
        return this.entityData.get(REGAL);
    }

    private void setRegal(boolean isRegal) {
        this.entityData.set(REGAL, isRegal);
    }

    public boolean isFight() {
        return this.entityData.get(FIGHT);
    }

    private void setFight(boolean isFight) {
        this.entityData.set(FIGHT, isFight);
    }

    public boolean isRun() {
        return this.entityData.get(RUN);
    }

    private void setRun(boolean isRun) {
        this.entityData.set(RUN, isRun);
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
        else if(this.isWarn()) {
            return event.setAndContinue(POSE_WARN);
        }
        else if(this.isEat()) {
            return event.setAndContinue(POSE_EAT);
        }
        else if(this.isRegal()) {
            return event.setAndContinue(POSE_REGAL);
        }
        else if(this.isFight()) {
            return event.setAndContinue(POSE_FIGHT);
        }
        else if(this.isRun()) {
            return event.setAndContinue(POSE_RUN);
        }
        else return event.setAndContinue(POSE_BASE);
    }
}
