package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.config.UnusualPrehistoryConfig;
import com.peeko32213.unusualprehistory.common.entity.msc.projectile.EntityAmberShot;
import com.peeko32213.unusualprehistory.common.entity.msc.util.CustomRandomStrollGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.ranged.AttackSound;
import com.peeko32213.unusualprehistory.common.entity.msc.util.ranged.CustomAbstractRangedAttack;
import com.peeko32213.unusualprehistory.common.entity.msc.util.ranged.CustomRangedAttackGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.ranged.RangedMeleeMob;
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
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class EntityEncrusted extends RangedMeleeMob implements IAnimatable {
    private static final EntityDataAccessor<Boolean> SPITTING = SynchedEntityData.defineId(EntityEncrusted.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FROM_BOOK = SynchedEntityData.defineId(EntityEncrusted.class, EntityDataSerializers.BOOLEAN);
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public EntityEncrusted(EntityType<? extends RangedMeleeMob> entityType, Level level) {
        super(entityType, level);
        this.maxUpStep = 1.0f;
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.FOLLOW_RANGE, 25.0D);

    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new CustomRangedAttackGoal(this, new RangedAttack(this)
                                .setProjectileOriginOffset(0.8, 0.4, 0.8)
                                .setDamage(5)
                                .setSound(UPSounds.ENCRUSTED_SPIT.get(), 1.0F, 1.0F),
                        1.1D));
        this.goalSelector.addGoal(2, new EntityEncrusted.IMeleeAttackGoal());
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 15.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, false, false, entity -> entity.getType().is(UPTags.ENCRUSTED_TARGETS)));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static class RangedAttack extends CustomAbstractRangedAttack {

        public RangedAttack(RangedMeleeMob parentEntity, double xOffSetModifier, double entityHeightFraction, double zOffSetModifier, float damage) {
            super(parentEntity, xOffSetModifier, entityHeightFraction, zOffSetModifier, damage);
        }



        public RangedAttack(RangedMeleeMob parentEntity) {
            super(parentEntity);
        }

        @Override
        public AttackSound getDefaultAttackSound() {
            return new AttackSound(UPSounds.ENCRUSTED_SPIT.get(), 1, 1);
        }

        @Override
        public Projectile getProjectile(Level world, double d2, double d3, double d4) {
            return new EntityAmberShot(world, this.parentEntity, d2, d3, d4, damage);
        }
    }

    class IMeleeAttackGoal extends MeleeAttackGoal {
        public IMeleeAttackGoal() {
            super(EntityEncrusted.this, 1.6D, true);
        }

        protected double getAttackReachSqr(LivingEntity p_25556_) {
            return (double)(this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 0.9F + p_25556_.getBbWidth());
        }
    }

    public void checkDespawn() {
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

    public boolean doHurtTarget(Entity entityIn) {
        if (super.doHurtTarget(entityIn)) {
            this.playSound(UPSounds.ENCRUSTED_MELEE.get(), 0.1F, 1.0F);
            return true;
        } else {
            return false;
        }
    }

    public void killed(ServerLevel world, LivingEntity entity) {
        this.heal(10);
    }


    protected SoundEvent getAmbientSound() {
        return UPSounds.ENCRUSTED_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.ENCRUSTED_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.ENCRUSTED_DEATH.get();
    }

    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }


    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.hasCustomName();
    }

    public boolean removeWhenFarAway(double d) {
        return !this.hasCustomName();
    }

    @javax.annotation.Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_28134_, DifficultyInstance p_28135_, MobSpawnType p_28136_, @javax.annotation.Nullable SpawnGroupData p_28137_, @javax.annotation.Nullable CompoundTag p_28138_) {
        p_28137_ = super.finalizeSpawn(p_28134_, p_28135_, p_28136_, p_28137_, p_28138_);
        Level level = p_28134_.getLevel();
        if (level instanceof ServerLevel) {
            {
                this.setPersistenceRequired();
            }
        }
        return p_28137_;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BOOK, false);

    }

    public boolean isFromBook() {
        return this.entityData.get(FROM_BOOK).booleanValue();
    }
    public void setIsFromBook(boolean fromBook) {
        this.entityData.set(FROM_BOOK, fromBook);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(this.isFromBook()){
            return PlayState.CONTINUE;
        }
        if(event.isMoving()){
            if(this.isAggressive()) {
                event.getController().setAnimation(new AnimationBuilder().loop("animation.encrusted.sprint"));
                event.getController().setAnimationSpeed(1.0F);
            } else {
                event.getController().setAnimation(new AnimationBuilder().loop("animation.encrusted.walk"));
                event.getController().setAnimationSpeed(1.0F);
            }
        }else{
            event.getController().setAnimation(new AnimationBuilder().loop("animation.encrusted.idle"));
            event.getController().setAnimationSpeed(1.0F);
        }
        return PlayState.CONTINUE;
    }





    private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {


        if (this.entityData.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.encrusted.attack"));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState predicate2(AnimationEvent<E> event) {
        if (this.entityData.get(STATE) == 2 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.encrusted.shoot"));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(5);
        AnimationController<EntityEncrusted> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        AnimationController<EntityEncrusted> controller1 = new AnimationController<EntityEncrusted>(this, "controller1", 0, this::predicate1);
        AnimationController<EntityEncrusted> controller2 = new AnimationController<EntityEncrusted>(this, "controller2", 0, this::predicate2);
        data.addAnimationController(controller);
        data.addAnimationController(controller1);
        data.addAnimationController(controller2);
    }

    public static boolean checkSurfaceDinoSpawnRules(EntityType<? extends EntityEncrusted> p_186238_, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource p_186242_) {
        return level.getBlockState(pos.below()).is(UPTags.DINO_NATURAL_SPAWNABLE)  && UnusualPrehistoryConfig.DINO_NATURAL_SPAWNING.get();    }


}
