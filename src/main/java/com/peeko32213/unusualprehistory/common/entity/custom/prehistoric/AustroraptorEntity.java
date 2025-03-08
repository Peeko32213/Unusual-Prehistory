package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.goal.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.util.goal.CustomRandomStrollGoal;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.ICustomAnimationsEntity;
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
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.util.GeckoLibUtil;


public class AustroraptorEntity extends PrehistoricEntity implements ICustomAnimationsEntity {
    private static final EntityDataAccessor<Integer> PREENING_TIME = SynchedEntityData.defineId(AustroraptorEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> PREENING = SynchedEntityData.defineId(AustroraptorEntity.class, EntityDataSerializers.BOOLEAN);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public int timeUntilDrops = this.random.nextInt(200) + 400;
    public float prevPreenProgress;
    public float preenProgress;
    public float preenSoundTimer = 100;
    public static final Logger LOGGER = LogManager.getLogger();

    private static final RawAnimation AUSTRO_WALK = RawAnimation.begin().thenLoop("animation.austroraptor.walk");
    private static final RawAnimation AUSTRO_IDLE = RawAnimation.begin().thenLoop("animation.austroraptor.idle");
    private static final RawAnimation AUSTRO_SWIM = RawAnimation.begin().thenLoop("animation.austroraptor.swim");
    private static final RawAnimation AUSTRO_PREEN = RawAnimation.begin().thenPlay("animation.austroraptor.preening");
    private static final RawAnimation AUSTRO_ATTACK = RawAnimation.begin().thenPlay("animation.austroraptor.attack");

    public AustroraptorEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
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
        this.goalSelector.addGoal(2, new AustroraptorEntity.IMeleeAttackGoal());
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
                                if (((AustroraptorEntity) this.mob).isHungry()) {
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
                        return !((AustroraptorEntity) this.mob).getIsPreening() && super.canContinueToUse();
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
                if (this.getNavigation().getPath() != null) {
                    this.getNavigation().stop();

                }
            pTravelVector = Vec3.ZERO;
            super.travel(pTravelVector);
        }
        else{
            super.travel(pTravelVector);
        }
    }

    @Override
    public boolean isAlliedTo(Entity pEntity) {
        return pEntity.is(this);
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



        if (random.nextInt(5000) == 0 && !this.getIsPreening()) {
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
        entity.hurt(this.damageSources().mobAttack(this), 5.0F);
    }

    class IMeleeAttackGoal extends MeleeAttackGoal {
        public IMeleeAttackGoal() {
            super(AustroraptorEntity.this, 1.6D, true);
        }

        protected double getAttackReachSqr(LivingEntity p_25556_) {
            return (double)(this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 0.66F + p_25556_.getBbWidth());
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            double d0 = this.getAttackReachSqr(enemy);
            if (distToEnemySqr <= d0 && this.getTicksUntilNextAttack() <= 0) {
                this.resetAttackCooldown();
                ((AustroraptorEntity) this.mob).setHungry(false);
                ((AustroraptorEntity) this.mob).attack(enemy);
                ((AustroraptorEntity) this.mob).setTimeTillHungry(mob.getRandom().nextInt(300) + 300);
            }
        }

    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }



    protected <E extends AustroraptorEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if(this.isFromBook()){
            event.setAndContinue(AUSTRO_IDLE);
        }
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming()) {
            event.setAndContinue(AUSTRO_WALK);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        if (this.isInWater()) {
            event.setAndContinue(AUSTRO_SWIM);
            event.getController().setAnimationSpeed(1.0F);
        }
        else {
            event.setAndContinue(AUSTRO_IDLE);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }

    protected <E extends AustroraptorEntity> PlayState attackController(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.swinging && event.getController().getAnimationState().equals(AnimationController.State.PAUSED)) {
            return event.setAndContinue(AUSTRO_ATTACK);
        }
        return PlayState.CONTINUE;
    }

    protected <E extends AustroraptorEntity> PlayState preenController(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.getIsPreening() && !this.isInWater()) {
            event.setAndContinue(AUSTRO_PREEN);
            return PlayState.CONTINUE;
        }
        event.getController().forceAnimationReset();
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
        controllers.add(new AnimationController<>(this, "Attack", 3, this::attackController));
        controllers.add(new AnimationController<>(this, "Preen", 5, this::preenController));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }

    @Override
    public void setCustomAnimation(GeoModel model, PrehistoricEntity animatable, long instanceId, AnimationState animationState) {
        if (animationState == null) return;

        EntityModelData extraDataOfType = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = model.getAnimationProcessor().getBone("Neck");

        if (!animatable.isSprinting()) {
            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
