package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.goal.GroomGoal;
import com.peeko32213.unusualprehistory.common.entity.util.goal.PounceGoal;
import com.peeko32213.unusualprehistory.common.entity.util.goal.SmilodonAttackGoal;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IVariantEntity;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.EnumSet;

public class SmilodonEntity extends PrehistoricEntity implements IVariantEntity {

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(SmilodonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(SmilodonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> CAN_GROOM = SynchedEntityData.defineId(SmilodonEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> GROOM_1 = SynchedEntityData.defineId(SmilodonEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> GROOM_2 = SynchedEntityData.defineId(SmilodonEntity.class, EntityDataSerializers.BOOLEAN);

    private Ingredient temptationItems;
    private static final RawAnimation SMILO_GROOM_1 = RawAnimation.begin().thenLoop("animation.smilodon.groom_1");
    private static final RawAnimation SMILO_GROOM_2 = RawAnimation.begin().thenLoop("animation.smilodon.groom_2");
    private static final RawAnimation SMILO_SPRINT = RawAnimation.begin().thenLoop("animation.smilodon.sprint");
    private static final RawAnimation SMILO_SNEAK = RawAnimation.begin().thenLoop("animation.smilodon.sneak");
    private static final RawAnimation SMILO_MOVE = RawAnimation.begin().thenLoop("animation.smilodon.move");
    private static final RawAnimation SMILO_SWIM = RawAnimation.begin().thenLoop("animation.smilodon.swim");
    private static final RawAnimation SMILO_IDLE = RawAnimation.begin().thenLoop("animation.smilodon.idle");
    private static final RawAnimation SMILO_BITE = RawAnimation.begin().thenLoop("animation.smilodon.bite");

    private static final RawAnimation SMILO_BABY_IDLE = RawAnimation.begin().thenLoop("animation.baby_smilodon.idle");
    private static final RawAnimation SMILO_BABY_WALK = RawAnimation.begin().thenLoop("animation.baby_smilodon.move");
    private static final RawAnimation SMILO_BABY_SWIM = RawAnimation.begin().thenLoop("animation.baby_smilodon.swim");

    public SmilodonEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.25F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 15.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.15D)
            .add(Attributes.ARMOR, 10.0D)
            .add(Attributes.ATTACK_DAMAGE, 10.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.1D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        if(!this.isBaby()) {
            this.goalSelector.addGoal(1, new GroomGoal(this, 1.5));
        }
        this.goalSelector.addGoal(2, new SmilodonAttackGoal(this){
            public boolean canUse() {
                return !isBaby() && super.canUse();
            }
        });
        this.goalSelector.addGoal(1, new PounceGoal(this, 5){
            public boolean canUse() {
                return !isBaby() && super.canUse();
            }
        });
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 25.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, false, false, entity -> entity.getType().is(UPTags.SMILODON_TARGETS)));
    }

    public int groomTimer;

    @Override
    public void tick() {
        super.tick();

        if (groomTimer-- < 0) {
            groomTimer = 6000;
            setCanGroom(true);
        }
    }

    @Override
    public boolean isAlliedTo(Entity pEntity) {
        return pEntity.is(this);
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.SMILODON_IDLE.get();
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UPSounds.SMILODON_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.SMILODON_DEATH.get();
    }

    protected void playStepSound(@NotNull BlockPos p_28301_, @NotNull BlockState p_28302_) {
        this.playSound(SoundEvents.WOLF_STEP, 0.15F, 0.5F);
    }

    @Override
    public float getSoundVolume() {
        if(this.isBaby()){
            return 0.4F;
        }
        else{
            return 0.8F;
        }
    }

    @Override
    protected SoundEvent getAttackSound() {
        return null;
    }

    @Override
    protected int getKillHealAmount() {
        return 0;
    }

    @Override
    protected boolean canGetHungry() {
        return false;
    }

    @Override
    protected boolean hasTargets() {
        return false;
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
        return null;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        SmilodonEntity smilodon = UPEntities.SMILODON.get().create(serverLevel);
        assert smilodon != null;
        smilodon.setVariant(this.getVariant());
        return smilodon;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CAN_GROOM, false);
        this.entityData.define(GROOM_1, false);
        this.entityData.define(GROOM_2, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("canGroom", canGroom());
        compound.putBoolean("groom1", groom1());
        compound.putBoolean("groom2", groom2());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setCanGroom(compound.getBoolean("canGroom"));
        setGroom1(compound.getBoolean("groom1"));
        setGroom2(compound.getBoolean("groom2"));
    }

    public int getAnimationState() {
        return this.entityData.get(ANIMATION_STATE);
    }

    public void setAnimationState(int anim) {
        this.entityData.set(ANIMATION_STATE, anim);
    }

    public boolean canGroom() {
        return this.entityData.get(CAN_GROOM);
    }

    public void setCanGroom(boolean canGroom) {
        this.entityData.set(CAN_GROOM, canGroom);
    }

    public boolean groom1() {
        return this.entityData.get(GROOM_1);
    }

    public void setGroom1(boolean groom1) {
        this.entityData.set(GROOM_1, groom1);
    }

    public boolean groom2() {
        return this.entityData.get(GROOM_1);
    }

    public void setGroom2(boolean groom2) {
        this.entityData.set(GROOM_2, groom2);
    }

    public boolean canGroom(SmilodonEntity pOtherAnimal) {
        if(!this.isBaby()) {
            if (pOtherAnimal == this) {
                return false;
            } else if (pOtherAnimal.getClass() != this.getClass()) {
                return false;
            } else {
                return this.canGroom() && pOtherAnimal.canGroom();
            }
        }
        return false;
    }

    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            double d0 = this.getMoveControl().getSpeedModifier();
            if (d0 == 0.8D) {
                this.setPose(Pose.CROUCHING);
                this.setSprinting(false);
            } else if (d0 == 2.0D) {
                this.setPose(Pose.STANDING);
                this.setSprinting(true);
            } else {
                this.setPose(Pose.STANDING);
                this.setSprinting(false);
            }
        } else {
            this.setPose(Pose.STANDING);
            this.setSprinting(false);
        }
    }

    private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation("unusualprehistory:textures/entity/smilodon/smilodon.png");
    private static final ResourceLocation TEXTURE_OCELOT = new ResourceLocation("unusualprehistory:textures/entity/smilodon/smilodon_ocelot.png");

    private static final ResourceLocation TEXTURE_NORMAL_BABY = new ResourceLocation("unusualprehistory:textures/entity/smilodon/smilodon_baby.png");
    private static final ResourceLocation TEXTURE_OCELOT_BABY = new ResourceLocation("unusualprehistory:textures/entity/smilodon/smilodon_ocelot_baby.png");

    @Override
    public ResourceLocation getVariantTexture() {
        if(getVariant() == 1){
            return TEXTURE_OCELOT;
        }
        return TEXTURE_NORMAL;
    }

    static class SmilodonStalkGoal extends Goal {
        protected final PathfinderMob mob;
        private double speedModifier = 0.5D;
        private Path path;
        private double pathedTargetX;
        private double pathedTargetY;
        private double pathedTargetZ;
        private int ticksUntilNextPathRecalculation;
        private int ticksUntilNextAttack;
        private long lastCanUseCheck;

        public SmilodonStalkGoal(PathfinderMob pathfinderMob) {
            this.mob = pathfinderMob;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (this.mob.isBaby()) {
                return false;
            }
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity == null) {
                return false;
            }
            if (!livingEntity.isAlive()) {
                return false;
            }
            this.path = this.mob.getNavigation().createPath(livingEntity, 0);
            if (this.path != null) {
                return true;
            }
            return this.getAttackReachSqr(livingEntity) >= this.mob.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity == null) {
                return false;
            }
            if (!livingEntity.isAlive()) {
                return false;
            }
            return !this.mob.getNavigation().isDone();
        }

        @Override
        public void start() {
            LivingEntity target = this.mob.getTarget();
            if (target == null) {
                return;
            }
            this.speedModifier = this.mob.distanceTo(target) > 12 ? 0.5D : 1.7D;
            this.mob.getNavigation().moveTo(this.path, this.speedModifier);
            this.mob.setAggressive(true);
            this.ticksUntilNextPathRecalculation = 0;
            this.ticksUntilNextAttack = 0;
        }

        @Override
        public void stop() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingEntity)) {
                this.mob.setTarget(null);
            }

            this.mob.setAggressive(false);
            this.mob.getNavigation().stop();
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = this.mob.getTarget();
            if (target == null) {
                return;
            }
            this.speedModifier = this.mob.distanceTo(target) > 12 ? 0.5D : 1.7D;
            this.mob.getLookControl().setLookAt(target, 30.0f, 30.0f);
            double d = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
            this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
            if (this.mob.getSensing().hasLineOfSight(target) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0 || target.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0 || this.mob.getRandom().nextFloat() < 0.05f)) {
                this.pathedTargetX = target.getX();
                this.pathedTargetY = target.getY();
                this.pathedTargetZ = target.getZ();
                this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                if (d > 1024.0) {
                    this.ticksUntilNextPathRecalculation += 10;
                } else if (d > 256.0) {
                    this.ticksUntilNextPathRecalculation += 5;
                }
                if (!this.mob.getNavigation().moveTo(target, this.speedModifier)) {
                    this.ticksUntilNextPathRecalculation += 15;
                }
                this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
            }
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
            this.checkAndPerformAttack(target, d);
        }

        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            double d = this.getAttackReachSqr(enemy);
            if (distToEnemySqr <= d && this.ticksUntilNextAttack <= 0) {
                this.mob.setSprinting(false);
                this.resetAttackCooldown();
                this.mob.swing(InteractionHand.MAIN_HAND);
                this.mob.doHurtTarget(enemy);
            }
        }

        protected void resetAttackCooldown() {
            this.ticksUntilNextAttack = this.adjustedTickDelay(20);
        }

        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return this.mob.getBbWidth() * 0.9f * (this.mob.getBbWidth() * 0.9f) + attackTarget.getBbWidth();
        }
    }

    @Override
    public boolean isSteppingCarefully() {
        return this.isCrouching() || super.isSteppingCarefully();
    }

    public double getVisibilityPercent(@Nullable Entity lookingEntity) {
        if (this.isCrouching()) {
            return 0.2D;
        }
        return super.getVisibilityPercent(lookingEntity);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL);
    }

    protected <E extends SmilodonEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.isFromBook()) {
            return event.setAndContinue(SMILO_IDLE);
        }

        if (groom1() && !this.isSwimming()) {
            event.setAndContinue(SMILO_GROOM_1);
            event.getController().setAnimationSpeed(2.5F);
            return PlayState.CONTINUE;
        }
        if (groom2() && !this.isSwimming()) {
            event.setAndContinue(SMILO_GROOM_2);
            event.getController().setAnimationSpeed(2.5F);
            return PlayState.CONTINUE;
        }

        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming()) {
            if (this.isSprinting() && !this.isBaby()) {
                event.setAndContinue(SMILO_SPRINT);
                event.getController().setAnimationSpeed(2.5F);
                return PlayState.CONTINUE;
            }
            else if (this.isCrouching() && !this.isBaby()) {
                event.setAndContinue(SMILO_SNEAK);
                event.getController().setAnimationSpeed(0.8F);
                return PlayState.CONTINUE;
            }
            else if(this.isBaby()){
                event.setAndContinue(SMILO_BABY_WALK);
            }
            else {
                event.setAndContinue(SMILO_MOVE);
            }
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        if (this.isInWater()) {
            if(this.isBaby()){
                event.setAndContinue(SMILO_BABY_SWIM);
            }
            else {
                event.setAndContinue(SMILO_SWIM);
            }
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }

        if(this.isBaby()){
            event.setAndContinue(SMILO_BABY_IDLE);
        }
        else {
            event.setAndContinue(SMILO_IDLE);
        }
        event.getController().setAnimationSpeed(1.0F);

        return PlayState.CONTINUE;
    }

    protected <E extends SmilodonEntity> PlayState attackController(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.swinging && event.getController().getAnimationState().equals(AnimationController.State.PAUSED)) {
            return event.setAndContinue(SMILO_BITE);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 10, this::Controller));
        controllers.add(new AnimationController<>(this, "Attack", 0, this::attackController));
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }

}
