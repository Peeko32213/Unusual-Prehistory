package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.msc.util.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.CustomRandomStrollGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class EntityAustroraptor extends EntityBaseDinosaurAnimal {
    private static final EntityDataAccessor<Integer> PREENING_TIME = SynchedEntityData.defineId(EntityAustroraptor.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> PREENING = SynchedEntityData.defineId(EntityAustroraptor.class, EntityDataSerializers.BOOLEAN);

    public int timeUntilDrops = this.random.nextInt(200) + 400;
    public float prevPreenProgress;
    public float preenProgress;
    public float preenSoundTimer = 100;
    public static final Logger LOGGER = LogManager.getLogger();
    public EntityAustroraptor(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.maxUpStep = 1.0f;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 2D, false));
        this.goalSelector.addGoal(2, new EntityAustroraptor.IMeleeAttackGoal());
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34)
                {
                    @Override
                    public boolean canUse() {
                        if (this.mob.isVehicle()) {
                            return false;
                        } else {
                            if (!this.forceTrigger) {
                                if (this.mob.getNoActionTime() >= 100) {
                                    return false;
                                }
                                if (((EntityAustroraptor) this.mob).isHungry()) {
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

                    @Override
                    public boolean canContinueToUse() {
                        return !((EntityAustroraptor) this.mob).getIsPreening() && super.canContinueToUse();
                    }
                }


        );
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this)));
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.AUSTRO_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.AUSTRO_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.AUSTRO_DEATH.get();
    }

    public boolean getIsPreening() {
        return this.entityData.get(PREENING);
    }

    public void setIsPreening(boolean preening) {
        this.entityData.set(PREENING, preening);
    }

    public int getPreeningTime() {
        return this.entityData.get(PREENING_TIME);
    }

    public void setPreeningTime(int shaking) {
        this.entityData.set(PREENING_TIME, shaking);
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if(this.getIsPreening()) {
            super.travel(new Vec3(0,0,0));
        }
        else{
            super.travel(pTravelVector);
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean shouldHurt;
        float damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float knockback = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (shouldHurt = target.hurt(DamageSource.mobAttack(this), damage)) {
            if (knockback > 0.0f && target instanceof LivingEntity) {
                ((LivingEntity) target).knockback(knockback * 0.5f, Mth.sin(this.getYRot() * ((float) Math.PI / 180)), -Mth.cos(this.getYRot() * ((float) Math.PI / 180)));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
            }
            this.doEnchantDamageEffects(this, target);
            this.setLastHurtMob(target);
            this.setPreeningTime(0);
        }
        return shouldHurt;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PREENING_TIME, 0);
        this.entityData.define(PREENING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("DropTime", this.timeUntilDrops);
        compound.putInt("preeningTime", this.getPreeningTime());
        compound.putBoolean("isPreening", this.getIsPreening());

    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setPreeningTime(compound.getInt("preeningTime"));
        this.setIsPreening(compound.getBoolean("isPreening"));

        if (compound.contains("SpitTime")) {
            this.timeUntilDrops = compound.getInt("DropTime");
        }

    }



    public void tick() {
        super.tick();
        prevPreenProgress = preenProgress;
        //LOGGER.info("preeningTime " + this.getPreeningTime());



        if (random.nextInt(2000) == 0 && !this.getIsPreening()) {
            this.setPreeningTime(100 + random.nextInt(30));
            this.setIsPreening(true);
        }

        if(this.getPreeningTime() <= 0 && this.getIsPreening()){
            this.setIsPreening(false);
            this.goalSelector.getRunningGoals().forEach(WrappedGoal::start);
        }

        if(this.getIsPreening()) {
            this.setPreeningTime(this.getPreeningTime() - 1);
            this.getNavigation().stop();
            this.goalSelector.getRunningGoals().forEach(WrappedGoal::stop);
            if(preenSoundTimer <= 0){
                preenSoundTimer = 100;
            }

            if(preenSoundTimer >= 100) {
                this.playSound(UPSounds.AUSTRO_PREEN.get(), 0.1F, 1.0F);
            }
            preenSoundTimer--;


            if (random.nextInt(90) == 0) {
                this.spawnAtLocation(UPItems.AUSTRO_FEATHER.get());
            }
        }
    }

    @Override
    protected SoundEvent getAttackSound() {
        return UPSounds.AUSTRO_BITE.get();
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

    @Override
    protected TagKey<EntityType<?>> getTargetTag() {
        return UPTags.RAPTOR_TARGETS;
    }

    private void attack(LivingEntity entity) {
        entity.hurt(DamageSource.mobAttack(this), 5.0F);
    }

    class IMeleeAttackGoal extends MeleeAttackGoal {
        public IMeleeAttackGoal() {
            super(EntityAustroraptor.this, 1.6D, true);
        }

        protected double getAttackReachSqr(LivingEntity p_25556_) {
            return (double)(this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 0.66F + p_25556_.getBbWidth());
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            double d0 = this.getAttackReachSqr(enemy);
            if (distToEnemySqr <= d0 && this.getTicksUntilNextAttack() <= 0) {
                this.resetAttackCooldown();
                ((EntityAustroraptor) this.mob).setHungry(false);
                ((EntityAustroraptor) this.mob).attack(enemy);
                ((EntityAustroraptor) this.mob).setTimeTillHungry(mob.getRandom().nextInt(300) + 300);
            }
        }

    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 || this.isSwimming()) {
            {
                event.getController().setAnimation(new AnimationBuilder().loop("animation.austroraptor.walk"));
            }
        }
        if (this.isInWater()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.austroraptor.swim"));
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.austroraptor.idle"));
            event.getController().setAnimationSpeed(1.0D);
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState eatPredicate(AnimationEvent<E> event) {
        if (this.getIsPreening()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.austroraptor.preening"));
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if (this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().playOnce("animation.austroraptor.attack"));

            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(5);
        data.addAnimationController(new AnimationController<>(this, "eatController", 5, this::eatPredicate));
        AnimationController<EntityAustroraptor> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        data.addAnimationController(new AnimationController<>(this, "attackController", 3, this::attackPredicate));
        data.addAnimationController(controller);
    }

}
