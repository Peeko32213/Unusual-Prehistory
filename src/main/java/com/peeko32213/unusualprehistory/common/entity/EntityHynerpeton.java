package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.CustomRandomStrollGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.FindWaterGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.LeaveWaterGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.SemiAquaticSwimmingGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.SemiAquatic;
import com.peeko32213.unusualprehistory.common.entity.msc.util.navigator.SemiAquaticPathNavigation;
import com.peeko32213.unusualprehistory.common.entity.msc.util.navigator.WaterMoveController;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class EntityHynerpeton extends EntityBaseDinosaurAnimal implements SemiAquatic {
    private static final RawAnimation HYNERPETON_IDLE = RawAnimation.begin().thenLoop("animation.hynerpeton.idle");
    private static final RawAnimation HYNERPETON_BASK_1 = RawAnimation.begin().thenLoop("animation.hynerpeton.bask1");
    private static final RawAnimation HYNERPETON_BASK_2 = RawAnimation.begin().thenLoop("animation.hynerpeton.bask2");
    private static final RawAnimation HYNERPETON_WALK = RawAnimation.begin().thenLoop("animation.hynerpeton.walk");
    private static final RawAnimation HYNERPETON_RUN = RawAnimation.begin().thenLoop("animation.hynerpeton.run");
    private static final RawAnimation HYNERPETON_SWIM_IDLE = RawAnimation.begin().thenLoop("animation.hynerpeton.swim_idle");
    private static final RawAnimation HYNERPETON_SWIM = RawAnimation.begin().thenLoop("animation.hynerpeton.swim");
    private static final RawAnimation HYNERPETON_BELLOW = RawAnimation.begin().thenLoop("animation.hynerpeton.bellow");
    private static final RawAnimation HYNERPETON_EXPLODE = RawAnimation.begin().thenLoop("animation.hynerpeton.explode");


    public float prevSwimProgress;
    public float swimProgress;
    private int swimTimer = -1000;
    private boolean isLandNavigator;

    public EntityHynerpeton(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
        switchNavigator(false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.18D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.2F, false));
        this.goalSelector.addGoal(7, new FindWaterGoal(this));
        this.goalSelector.addGoal(7, new LeaveWaterGoal(this));
        this.goalSelector.addGoal(9, new SemiAquaticSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
    }


    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new WaterMoveController(this, 1.1F);
            this.navigation = new SemiAquaticPathNavigation(this, level());
            this.isLandNavigator = false;
        }
    }

    public void tick() {
        super.tick();
        this.prevSwimProgress = swimProgress;

        final boolean ground = !this.isInWaterOrBubble();

        if (!ground && this.isLandNavigator) {
            switchNavigator(false);
        }
        if (ground && !this.isLandNavigator) {
            switchNavigator(true);
        }
        if (ground && swimProgress > 0) {
            swimProgress--;
        }
        if (!ground && swimProgress < 5F) {
            swimProgress++;
        }
        if (!this.level().isClientSide) {
            if (isInWater()) {
                swimTimer++;
            } else {
                swimTimer--;
            }
        }
    }

    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        }
        else {
            super.travel(travelVector);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("SwimTimer", this.swimTimer);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.swimTimer = compound.getInt("SwimTimer");
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected SoundEvent getAttackSound() {
        return null;
    }

    @Override
    protected int getKillHealAmount() {
        return 5;
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
    protected TagKey<EntityType<?>> getTargetTag() {
        return UPTags.PISCIVORE_DIET;
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

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    public boolean shouldEnterWater() {
        return !shouldLeaveWater() && swimTimer <= -1000;
    }

    public boolean shouldLeaveWater() {
        LivingEntity target = this.getTarget();
        if (target != null && !target.isInWater()) {
            return true;
        }
        return swimTimer > 600;
    }

    @Override
    public int getWaterSearchRange() {
        return 12;
    }

    @Override
    public boolean shouldStopMoving() {
        return false;
    }


    protected <E extends EntityHynerpeton> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.isFromBook()) {
            return PlayState.CONTINUE;
        }
        if (event.isMoving() && !this.isInWater()) {
            event.setAndContinue(HYNERPETON_WALK);
            event.getController().setAnimationSpeed(1.0D);
            return PlayState.CONTINUE;
        }
        if (this.isInWater()) {
            event.setAndContinue(HYNERPETON_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        if (isStillEnough() && random.nextInt(100) == 0 && !this.isSwimming()) {
            float rand = random.nextFloat();
            if (rand < 0.45F) {
                return event.setAndContinue(HYNERPETON_BELLOW);
            }
            if (rand < 0.55F) {
                return event.setAndContinue(HYNERPETON_BASK_2);
            }
            if (rand < 0.65F) {
                return event.setAndContinue(HYNERPETON_BASK_1);
            }
            event.setAndContinue(HYNERPETON_IDLE);
        }
        if (isStillEnough() && this.isInWater()) {
            event.setAndContinue(HYNERPETON_SWIM_IDLE);
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



}