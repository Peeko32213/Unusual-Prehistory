package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.BaseDinosaurAnimalEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.CustomRandomStrollGoal;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class MajungasaurusEntity extends BaseDinosaurAnimalEntity {

    private static final EntityDataAccessor<Integer> CHARGE_COOLDOWN_TICKS = SynchedEntityData.defineId(MajungasaurusEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_TARGET = SynchedEntityData.defineId(MajungasaurusEntity.class, EntityDataSerializers.BOOLEAN);

    private int stunnedTick;
    private boolean canBePushed = true;
    private static final RawAnimation MAJUNGA_WALK = RawAnimation.begin().thenLoop("animation.majungasaurus.walk");
    private static final RawAnimation MAJUNGA_IDLE = RawAnimation.begin().thenLoop("animation.majungasaurus.idle");
    private static final RawAnimation MAJUNGA_RUN = RawAnimation.begin().thenLoop("animation.majungasaurus.charge");
    private static final RawAnimation MAJUNGA_CHARGE_PREP = RawAnimation.begin().thenLoop("animation.majungasaurus.prep");
    private static final RawAnimation MAJUNGA_STUNNED = RawAnimation.begin().thenLoop("animation.majungasaurus.stunned");
    private static final RawAnimation MAJUNGA_SWIM = RawAnimation.begin().thenLoop("animation.majungasaurus.swim");
    private static final RawAnimation MAJUNGA_BITE = RawAnimation.begin().thenLoop("animation.majungasaurus.bite");

    public MajungasaurusEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.0f);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.25D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MajungaMeleeAttackGoal(this, 1.5D, false));
        this.goalSelector.addGoal(2, new MajungaPrepareChargeGoal(this));
        this.goalSelector.addGoal(3, new MajungaChargeGoal(this, 2.0F));
        this.goalSelector.addGoal(4, new BabyPanicGoal(this, 2.0D));
        if(this.isBaby()) {
            this.goalSelector.addGoal(6, new AvoidEntityGoal<>(this, MajungasaurusEntity.class, 12.0F, 2.0D, 1.4D, EntitySelector.NO_SPECTATORS::test));
        }
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0F, 30) {
                    @Override
                    public boolean canUse() {
                        if (this.mob.isVehicle()) {
                            return false;
                        } else {
                            if (!this.forceTrigger) {
                                if (this.mob.getNoActionTime() >= 100) {
                                    return false;
                                }
                                if (((MajungasaurusEntity) this.mob).isHungry()) {
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
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, 100, true, false, this::isAngryAt));
    }


    @Override
    public boolean doHurtTarget(Entity target) {
        boolean shouldHurt;
        float damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float knockback = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (shouldHurt = target.hurt(this.damageSources().mobAttack(this), damage)) {
            if (knockback > 0.0f && target instanceof LivingEntity) {
                ((LivingEntity) target).knockback(knockback * 0.5f, Mth.sin(this.getYRot() * ((float) Math.PI / 180)), -Mth.cos(this.getYRot() * ((float) Math.PI / 180)));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
            }
            this.doEnchantDamageEffects(this, target);
            this.setLastHurtMob(target);
        }
        if (shouldHurt && target instanceof LivingEntity livingEntity) {
            this.playSound(UPSounds.MAJUNGA_ATTACK.get(), 0.1F, 1.0F);
        }
        return shouldHurt;
    }


    public boolean isAngryAt(LivingEntity p_21675_) {
        return this.canAttack(p_21675_);
    }


    @Override
    public boolean canAttack(LivingEntity entity) {
        boolean prev = super.canAttack(entity);
        if (isBaby() || getPassiveTicks() > 0) {
            return false;
        }
        return prev;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return UPEntities.MAJUNGA.get().create(serverLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CHARGE_COOLDOWN_TICKS, 0);
        this.entityData.define(HAS_TARGET, false);

    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("StunTick", this.stunnedTick);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.stunnedTick = compound.getInt("StunTick");
    }


    public boolean isFood(ItemStack stack) {
        return stack.is(UPTags.MAJUNGA_FOOD);
    }

    private void attack(LivingEntity entity) {
        entity.hurt(this.damageSources().mobAttack(this), 8.0F);
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if (hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if (isFood(itemstack) && (this.getPassiveTicks() <= 0 || this.getHealth() < this.getMaxHealth())) {
            if (!player.isCreative()) {
                itemstack.shrink(1);
            }
            this.heal(10);
            this.setPassiveTicks(this.getPassiveTicks() + 2500);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.MAJUNGA_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.MAJUNGA_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.MAJUNGA_DEATH.get();
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(UPSounds.MAJUNGA_STEP.get(), 0.1F, 1.0F);
    }

    @Override
    protected SoundEvent getAttackSound() {
        return UPSounds.MAJUNGA_ATTACK.get();
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
    protected TagKey<EntityType<?>> getTargetTag() {
        return UPTags.MAJUNGA_TARGETS;
    }


    @Override
    protected boolean hasAvoidEntity() {
        return true;
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

    public void setChargeCooldownTicks(int ticks) {
        this.entityData.set(CHARGE_COOLDOWN_TICKS, ticks);
    }

    public int getChargeCooldownTicks() {
        return this.entityData.get(CHARGE_COOLDOWN_TICKS);
    }

    public boolean hasChargeCooldown() {
        return this.entityData.get(CHARGE_COOLDOWN_TICKS) > 0;
    }

    public void resetChargeCooldownTicks() {
        this.entityData.set(CHARGE_COOLDOWN_TICKS, 50);
    }

    public void setHasTarget(boolean hasTarget) {
        this.entityData.set(HAS_TARGET, hasTarget);
    }

    public boolean hasTarget() {
        return this.entityData.get(HAS_TARGET);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.isAlive()) {
            return;
        }
        Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(this.isImmobile() ? 0.0 : 0.2);
        if (this.stunnedTick > 0) {
            --this.stunnedTick;
            this.stunEffect();
            this.spawnAtLocation(UPItems.MAJUNGA_SCUTE.get());
        }
    }

    private void stunEffect() {
        if (this.random.nextInt(6) == 0) {
            double d = this.getX() - (double) this.getBbWidth() * Math.sin(this.yBodyRot * ((float) Math.PI / 180)) + (this.random.nextDouble() * 0.6 - 0.3);
            double e = this.getY() + (double) this.getBbHeight() - 0.3;
            double f = this.getZ() + (double) this.getBbWidth() * Math.cos(this.yBodyRot * ((float) Math.PI / 180)) + (this.random.nextDouble() * 0.6 - 0.3);
            level().addParticle(ParticleTypes.CRIT, true, this.getX(), this.getEyeY() + 0.5F, this.getZ(), 0, 0, 0);
        }
    }

    @Override
    protected boolean isImmobile() {
        return super.isImmobile() || this.stunnedTick > 0;
    }

    @Override
    protected void blockedByShield(LivingEntity defender) {
        this.stunnedTick = 60;
        this.resetChargeCooldownTicks();
        this.getNavigation().stop();
        this.playSound(SoundEvents.RAVAGER_STUNNED, 1.0f, 1.0f);
        this.level().broadcastEntityEvent(this, (byte) 39);
        defender.push(this);
        defender.hurtMarked = true;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 39) {
            this.stunnedTick = 60;
        }
        super.handleEntityEvent(id);
    }

    @Override
    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.5D);
        } else {
            this.setSprinting(false);
        }
        super.customServerAiStep();
    }

    private boolean isWithinYRange(LivingEntity target) {
        if (target == null) {
            return false;
        }
        return Math.abs(target.getY() - this.getY()) < 3;
    }

    @Override
    public boolean isPushable() {
        return this.canBePushed;
    }

    @Override
    public float getVoicePitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f;
    }

    static class MajungaPrepareChargeGoal extends Goal {
        protected final MajungasaurusEntity majunga;

        public MajungaPrepareChargeGoal(MajungasaurusEntity majunga) {
            this.majunga = majunga;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.majunga.getTarget();
            if (target == null || !target.isAlive() || this.majunga.stunnedTick > 0 || !this.majunga.isWithinYRange(target)) {
                this.majunga.resetChargeCooldownTicks();
                return false;
            }
            return target instanceof Player && majunga.hasChargeCooldown();
        }

        @Override
        public void start() {
            LivingEntity target = this.majunga.getTarget();
            if (target == null) {
                return;
            }
            this.majunga.setHasTarget(true);
            this.majunga.resetChargeCooldownTicks();
            this.majunga.canBePushed = false;
        }

        @Override
        public void stop() {
            this.majunga.setHasTarget(false);
            this.majunga.canBePushed = true;
        }

        @Override
        public void tick() {
            LivingEntity target = this.majunga.getTarget();
            if (target == null) {
                return;
            }
            this.majunga.getLookControl().setLookAt(target);
            this.majunga.setChargeCooldownTicks(Math.max(0, this.majunga.getChargeCooldownTicks() - 1));
        }
    }

    static class MajungaChargeGoal extends Goal {
        protected final MajungasaurusEntity mob;
        private final double speedModifier;
        private Path path;
        private Vec3 chargeDirection;

        public MajungaChargeGoal(MajungasaurusEntity pathfinderMob, double speedModifier) {
            this.mob = pathfinderMob;
            this.speedModifier = speedModifier;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
            this.chargeDirection = Vec3.ZERO;
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.mob.getTarget();
            if (target == null || !target.isAlive() || this.mob.hasChargeCooldown() || this.mob.stunnedTick > 0) {
                return false;
            }
            this.path = this.mob.getNavigation().createPath(target, 0);
            return target instanceof Player && this.path != null;
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = this.mob.getTarget();
            if (target == null || !target.isAlive() || this.mob.hasChargeCooldown() || this.mob.stunnedTick > 0) {
                return false;
            }
            return !this.mob.getNavigation().isDone();
        }

        @Override
        public void start() {
            BlockPos blockPosition = this.mob.blockPosition();
            BlockPos target = this.path.getTarget();
            this.chargeDirection = new Vec3(blockPosition.getX() - target.getX(), 0.0, blockPosition.getZ() - target.getZ()).normalize();
            this.mob.getNavigation().moveTo(this.path, this.speedModifier);
            this.mob.setAggressive(true);
        }

        @Override
        public void stop() {
            this.mob.resetChargeCooldownTicks();
            this.mob.getNavigation().stop();
        }


        @Override
        public void tick() {
            this.mob.getLookControl().setLookAt(Vec3.atCenterOf(this.path.getTarget()));
            if (this.mob.horizontalCollision && this.mob.onGround()) {
                this.mob.jumpFromGround();
            }
            if (this.mob.level().getGameTime() % 2L == 0L) {
                this.mob.playSound(UPSounds.MAJUNGA_STEP.get(), 0.5F, this.mob.getVoicePitch());
            }
            this.tryToHurt();
        }

        protected void tryToHurt() {
            List<LivingEntity> nearbyEntities = this.mob.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), this.mob, this.mob.getBoundingBox());
            if (!nearbyEntities.isEmpty()) {
                LivingEntity livingEntity = nearbyEntities.get(0);
                if (!(livingEntity instanceof MajungasaurusEntity)) {
                    livingEntity.hurt(this.mob.damageSources().mobAttack(this.mob), (float) this.mob.getAttributeValue(Attributes.ATTACK_DAMAGE));
                    float speed = Mth.clamp(this.mob.getSpeed() * 1.65f, 0.2f, 3.0f);
                    float shieldBlockModifier = livingEntity.isDamageSourceBlocked(this.mob.damageSources().mobAttack(this.mob)) ? 0.5f : 1.0f;
                    livingEntity.knockback(shieldBlockModifier * speed * 2.0D, this.chargeDirection.x(), this.chargeDirection.z());
                    double knockbackResistance = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                    livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(0.0, 0.4f * knockbackResistance, 0.0));
                    this.mob.swing(InteractionHand.MAIN_HAND);
                    if (livingEntity.equals(this.mob.getTarget())) {
                        this.stop();
                    }
                }
            }
        }
    }

    static class MajungaMeleeAttackGoal extends MeleeAttackGoal {

        public MajungaMeleeAttackGoal(PathfinderMob pathfinderMob, double speedModifier, boolean followEvenIfNotSeen) {
            super(pathfinderMob, speedModifier, followEvenIfNotSeen);
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.mob.getTarget();
            if (target instanceof Player) {
                return false;
            }
            return super.canUse();
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            double d0 = this.getAttackReachSqr(enemy);
            if (distToEnemySqr <= d0 && this.getTicksUntilNextAttack() <= 0) {
                this.resetAttackCooldown();
                ((MajungasaurusEntity) this.mob).setHungry(false);
                ((MajungasaurusEntity) this.mob).attack(enemy);
                ((MajungasaurusEntity) this.mob).setTimeTillHungry(mob.getRandom().nextInt(300) + 300);
            }
        }

        protected double getAttackReachSqr(LivingEntity p_25556_) {
            return this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 0.9F + p_25556_.getBbWidth();
        }

    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
        controllers.add(new AnimationController<>(this, "Attack", 5, this::attackController));
    }

    protected <E extends MajungasaurusEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.isFromBook()) {
            return event.setAndContinue(MAJUNGA_CHARGE_PREP);
        }
        if (this.isInWater()) {
            event.setAndContinue(MAJUNGA_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        if (this.stunnedTick > 0) {
            event.setAndContinue(MAJUNGA_STUNNED);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        } else if (event.isMoving()) {
            if (this.isSprinting() && !this.isInWater()) {
                event.setAndContinue(MAJUNGA_RUN);
                event.getController().setAnimationSpeed(1.0F);
                return PlayState.CONTINUE;
            } else {
                event.setAndContinue(MAJUNGA_WALK);
                event.getController().setAnimationSpeed(1.0F);
                return PlayState.CONTINUE;
            }
        } else if (this.hasChargeCooldown() && this.hasTarget()) {
            event.setAndContinue(MAJUNGA_CHARGE_PREP);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        } else if (!this.isInWater()) {
            event.setAndContinue(MAJUNGA_IDLE);
            event.getController().setAnimationSpeed(1.0F);
        }
        return PlayState.CONTINUE;
    }

    protected <E extends MajungasaurusEntity> PlayState attackController(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.swinging && event.getController().getAnimationState().equals(AnimationController.State.PAUSED)) {
            event.getController().forceAnimationReset();
            event.setAndContinue(MAJUNGA_BITE);
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }

}
