package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.config.UnusualPrehistoryConfig;
import com.peeko32213.unusualprehistory.common.entity.msc.util.helper.PublicMoveHelperController;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class EntityGuanlingsaurus extends WaterAnimal implements GeoAnimatable {
    //TODO:model, texture, animation

    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(EntityGuanlingsaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COMBAT_STATE = SynchedEntityData.defineId(EntityGuanlingsaurus.class, EntityDataSerializers.INT);

    public double prevYRot = 0;
    public double deltaYRot = 0;

    public Vec3 prevPos = new Vec3(0, 0, 0);
    public Vec3 deltaPos = new Vec3(0, 0, 0);
    public double deltaDist = 0;

    private static final RawAnimation GUANLINGSAURUS_SWIM = RawAnimation.begin().thenLoop("animation.guanlingsaurus.swim");
    private static final RawAnimation GUANLINGSAURUS_IDLE = RawAnimation.begin().thenLoop("animation.guanlingsaurus.idle");
    private static final RawAnimation GUANLINGSAURUS_LAND = RawAnimation.begin().thenLoop("animation.guanlingsaurus.land");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public EntityGuanlingsaurus(EntityType<EntityGuanlingsaurus> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

        // tilt control segment

        this.moveControl = new PublicMoveHelperController(this);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);

        this.setMaxUpStep(0.9f);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(0, new RandomSwimmingGoal(this, 0.6, 10));
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0)
                .add(Attributes.MOVEMENT_SPEED, 1.2)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION_STATE, 0);
        this.entityData.define(COMBAT_STATE, 0);
    }

    protected PathNavigation createNavigation(Level pLevel) {
        return new WaterBoundPathNavigation(this, pLevel);
    }

    public void tick() {
        super.tick();

        deltaYRot = this.getYHeadRot() - prevYRot;
        prevYRot = this.getYHeadRot();
        //MAKE SURE YOU USE HEADROT


        deltaPos = this.position().subtract(prevPos);
        deltaDist = prevPos.distanceTo(this.position());
        prevPos = this.position();

    }

    public void travel(Vec3 pTravelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(pTravelVector);
        }

    }

    protected <E extends EntityGuanlingsaurus> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        int animState = this.getAnimationState();

        switch (animState) {
            default:


                if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F) && this.isInWater()) {
                    event.setAnimation(GUANLINGSAURUS_SWIM);
                    event.getController().setAnimationSpeed(1.0F);
                    return PlayState.CONTINUE;
                }
                if (!this.isInWater()) {
                    event.setAnimation(GUANLINGSAURUS_LAND);
                    event.getController().setAnimationSpeed(1.0F);
                    return PlayState.CONTINUE;
                }
                else if (this.isInWater()) {
                    event.setAnimation(GUANLINGSAURUS_SWIM);
                    return PlayState.CONTINUE;
                }
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.hasCustomName();
    }

    public boolean removeWhenFarAway(double d) {
        return false;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }

    public int getAnimationState() {

        return this.entityData.get(ANIMATION_STATE);
    }

    public void setAnimationState(int anim) {

        this.entityData.set(ANIMATION_STATE, anim);
    }

}
