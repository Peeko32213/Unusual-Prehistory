package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.*;
import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.SemiAquatic;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.navigator.SemiAquaticPathNavigation;
import com.peeko32213.unusualprehistory.common.entity.msc.util.navigator.WaterMoveController;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.client.model.PandaModel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.EnumSet;
import java.util.List;
//TODO LIST
// - Figure Out How to make Catching Fish Unique
// - Basking is implemented, however the actual animation logic is being funky
public class EntityTanystropheus extends EntityBaseDinosaurAnimal implements SemiAquatic {
    private static final EntityDataAccessor<Boolean> BASKING = SynchedEntityData.defineId(EntityTanystropheus.class, EntityDataSerializers.BOOLEAN);
    private static final RawAnimation TANY_IDLE = RawAnimation.begin().thenLoop("animation.tanystropheus.idle");
    private static final RawAnimation TANY_BASK = RawAnimation.begin().thenLoop("animation.tanystropheus.bask");
    private static final RawAnimation TANY_VIBE_START = RawAnimation.begin().thenLoop("animation.tanystropheus.vibe_start");
    private static final RawAnimation TANY_VIBE_HOLD = RawAnimation.begin().thenLoop("animation.tanystropheus.vibe_hold");
    private static final RawAnimation TANY_VIBE_END = RawAnimation.begin().thenLoop("animation.tanystropheus.vibe_end");
    private static final RawAnimation TANY_LOOKOUT = RawAnimation.begin().thenLoop("animation.tanystropheus.lookout");
    private static final RawAnimation TANY_WALK = RawAnimation.begin().thenLoop("animation.tanystropheus.walk");
    private static final RawAnimation TANY_SWIM = RawAnimation.begin().thenLoop("animation.tanystropheus.swim");
    private static final RawAnimation TANY_CATCH_1 = RawAnimation.begin().thenLoop("animation.tanystropheus.catch1");
    private static final RawAnimation TANY_CATCH_2 = RawAnimation.begin().thenLoop("animation.tanystropheus.catch2");

    public float prevSwimProgress;
    public float swimProgress;
    private int swimTimer = -1000;
    private boolean isLandNavigator;
    private int baskingTimer = 0;
    public float prevBaskProgress;
    public float baskProgress;
    public EntityTanystropheus(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
        switchNavigator(false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.18D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.2F, false));
        this.goalSelector.addGoal(7, new FindWaterGoal(this));
        this.goalSelector.addGoal(7, new LeaveWaterGoal(this));
        this.goalSelector.addGoal(9, new SemiAquaticSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34) {
                    @Override
                    public boolean canUse() {
                        if (this.mob.isVehicle() || ((EntityTanystropheus)this.mob).isBasking()) {
                            return false;
                        } else {
                            if (!this.forceTrigger) {
                                if (this.mob.getNoActionTime() >= 100) {
                                    return false;
                                }
                                if (((EntityTanystropheus) this.mob).isHungry()) {
                                    if (this.mob.getRandom().nextInt(60) != 0) {
                                        return false;
                                    }
                                } else {
                                    if (this.mob.getRandom().nextInt(30) != 0) {
                                        return false;
                                    }
                                }
                            }

                            Vec3 vec3d = this.getPosition();
                            if (vec3d == null) {
                                return false;
                            } else {
                                this.wantedX = vec3d.x;
                                this.wantedY = vec3d.y;
                                this.wantedZ = vec3d.z;
                                this.forceTrigger = false;
                                return true;
                            }
                        }
                    }
                }
        );
        this.goalSelector.addGoal(5, new BaskRandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F)
        {
            @Override
            public boolean canUse() {
                if(this.mob instanceof EntityTanystropheus entityTanystropheus)
                {
                    if(entityTanystropheus.isBasking()) return false;
                }

                return super.canUse();
            }
        });

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
        if (isBasking()) {
            baskingTimer++;
        } else {
            baskingTimer = 0;
        }
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
        if (!this.level().isClientSide) {
            if (isBasking()) {
                if (this.getLastHurtByMob() != null || this.isInWaterOrBubble() || this.getTarget() != null || baskingTimer > 1000 && this.getRandom().nextInt(100) == 0) {
                    this.setBasking(false);
                }
            } else {
                if (this.getTarget() == null && !isInLove() && this.getLastHurtByMob() == null && !isBasking() && this.level().isDay() && baskingTimer == 0 && this.getRandom().nextInt(1) == 0) {
                    if (!isInWaterOrBubble()) {
                        this.setBasking(true);
                    }
                }
            }
        }
    }

    public void travel(Vec3 travelVector) {
         if (this.isEffectiveAi() && this.isInWater() && !this.isBasking()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        }
        if (this.isBasking()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            this.getLookControl().setLookAt(this.position().add(2,0,2));
            travelVector = Vec3.ZERO;
            super.travel(travelVector);
        }

         else {
            super.travel(travelVector);
        }
    }

    public boolean hurt(DamageSource source, float amount) {
        boolean prev = super.hurt(source, amount);
        if (prev) {
            double range = 15;
            List<? extends EntityTanystropheus> list = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(range, range / 2, range));
            for (EntityTanystropheus gaz : list) {
                gaz.setBasking(false);
            }
            this.setBasking(false);
        }
        return prev;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BASKING, false);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Basking", this.isBasking());
        compound.putInt("BaskingTimer", this.baskingTimer);
        compound.putInt("SwimTimer", this.swimTimer);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setBasking(compound.getBoolean("Basking"));
        this.baskingTimer = compound.getInt("BaskingTimer");
        this.swimTimer = compound.getInt("SwimTimer");
    }

    public boolean isBasking() {
        return this.entityData.get(BASKING);
    }

    public void setBasking(boolean basking) {
        this.entityData.set(BASKING, basking);
    }

    @Override
    public boolean shouldStopMoving() {
        return isBasking();
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


    protected <E extends EntityTanystropheus> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.isFromBook()) {
            return PlayState.CONTINUE;
        }

        if (this.isBasking() && !this.isInWater()) {
            event.setAndContinue(TANY_VIBE_HOLD);
        }

        if (event.isMoving() && !this.isInWater()) {
            event.setAndContinue(TANY_WALK);
            event.getController().setAnimationSpeed(1.0D);
            return PlayState.CONTINUE;
        }
        if (this.isInWater()) {
            event.setAndContinue(TANY_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        if (isStillEnough() && random.nextInt(100) == 0 && !this.isSwimming()) {
            float rand = random.nextFloat();
            if (rand < 0.45F) {
                return event.setAndContinue(TANY_LOOKOUT);
            }
            if (rand < 0.55F) {
                return event.setAndContinue(TANY_BASK);
            }
            event.setAndContinue(TANY_IDLE);
        }
        return PlayState.CONTINUE;
    }

    protected <E extends EntityTanystropheus> PlayState baskController(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
        controllers.add(new AnimationController<>(this, "Bask", 5, this::baskController));
    }

    private boolean isStillEnough() {
        return this.getDeltaMovement().horizontalDistance() < 0.05;
    }



}
