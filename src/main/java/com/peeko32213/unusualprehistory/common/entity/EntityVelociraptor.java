package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.util.AnuroPolinateGoal;
import com.peeko32213.unusualprehistory.common.entity.util.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.util.CustomRandomStrollGoal;
import com.peeko32213.unusualprehistory.common.entity.util.LandCreaturePathNavigation;
import com.peeko32213.unusualprehistory.core.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.util.GeckoLibUtil;
public class EntityVelociraptor extends Animal implements IAnimatable {
    private static final EntityDataAccessor<Boolean> PRESS = SynchedEntityData.defineId(EntityVelociraptor.class, EntityDataSerializers.BOOLEAN);

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);    protected boolean pushingState = false;

    public EntityVelociraptor(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
        this.maxUpStep = 1.0f;
    }





    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 13.0D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new PushButtonsGoal(this, 1.0F, 5, 2));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new EntityVelociraptor.IMeleeAttackGoal());
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
        this.goalSelector.addGoal(2, new EntityVelociraptor.RaptorOpenDoorGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, false, false, entity -> entity.getType().is(UPTags.RAPTOR_TARGETS)));

    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.RAPTOR_IDLE;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.RAPTOR_HURT;
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.RAPTOR_DEATH;
    }

    public boolean doHurtTarget(Entity entityIn) {
        if (super.doHurtTarget(entityIn)) {
            this.playSound(UPSounds.RAPTOR_ATTACK, 0.1F, 1.0F);
            return true;
        } else {
            return false;
        }
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
    }

    @Override
    public boolean canAttack(LivingEntity entity) {
        boolean prev = super.canAttack(entity);
        if(prev && isBaby()){
            return false;
        }
        return prev;
    }

    public void checkDespawn() {
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }



        class IMeleeAttackGoal extends MeleeAttackGoal {
        public IMeleeAttackGoal() {
            super(EntityVelociraptor.this, 1.6D, true);
        }

        protected double getAttackReachSqr(LivingEntity p_25556_) {
            return (double)(this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 0.66F + p_25556_.getBbWidth());
        }

    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Press", this.hasPressed());


    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setPress(compound.getBoolean("Press"));

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PRESS, false);
    }

    protected class RaptorOpenDoorGoal extends OpenDoorGoal {
        public RaptorOpenDoorGoal(EntityVelociraptor p_32128_) {
            super(p_32128_, false);
        }

        public boolean canUse() {
            return isAlive() && super.canUse();
        }
    }

    public void setPress(boolean eepy) {
        this.entityData.set(PRESS, Boolean.valueOf(eepy));
    }

    public boolean hasPressed() {
        return this.entityData.get(PRESS).booleanValue();
    }

    private static class PushButtonsGoal extends MoveToBlockGoal {
        private final EntityVelociraptor raptor;

        private static final int WAIT_TICKS = 20;
        protected int ticksWaited;

        public PushButtonsGoal(EntityVelociraptor pMob, double pSpeedModifier, int pSearchRange, int pVerticalSearchRange) {
            super(pMob, pSpeedModifier, pSearchRange, pVerticalSearchRange);
            this.raptor = pMob;
        }

        public double getDesiredSquaredDistanceToTarget() {
            return 1.5D;
        }

        public boolean canUse() {
            return super.canUse();
        }

        public boolean canContinueToUse() {
            return super.canContinueToUse();
        }

        public void tick() {
            if (this.isReachedTarget()) {
                if (this.ticksWaited >= 40) {
                    this.onReachedTarget();
                    if (raptor.random.nextFloat() <= 0.05F) {
                        if (raptor.random.nextFloat() < 0.1F) {
                            raptor.spawnAtLocation(UPItems.RAPTOR_FEATHERS.get());
                        }
                    }
                } else {
                    ++this.ticksWaited;
                }
            } else if (!this.isReachedTarget() && raptor.random.nextFloat() < 0.05F) {
                raptor.playSound(UPSounds.RAPTOR_SEARCH, 0.1F, 1.0F);
            }

            super.tick();
        }

        protected void onReachedTarget() {
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(raptor.level, raptor)) {
                BlockState blockstate = raptor.level.getBlockState(this.blockPos);
                if (blockstate.is(UPBlocks.GINKGO_BUTTON.get())) {
                    this.pushButton(blockstate);
                }

            }
        }

        @Override
        protected BlockPos getMoveToTarget() {
            return this.blockPos;
        }

        @Override
        protected boolean isValidTarget(LevelReader world, BlockPos pos) {
            BlockState blockState = world.getBlockState(pos);
            return (blockState.is(UPBlocks.GINKGO_BUTTON.get()));
        }

        private void pushButton(BlockState p_148929_) {
            ((EntityVelociraptor) this.mob).pushingState = true;
            this.nextStartTick = this.nextStartTick(this.mob);
            BlockState state = this.mob.level.getBlockState(this.blockPos);
            ((ButtonBlock) state.getBlock()).use(state, this.mob.level, this.blockPos, null, null, null);

        }

        public void start() {
            this.ticksWaited = 0;
            super.start();
        }

    }

    public void killed(ServerLevel world, LivingEntity entity) {
        this.heal(5);
        super.killed(world, entity);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return UPEntities.VELOCI.get().create(serverLevel);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
            {
                event.getController().setAnimation(new AnimationBuilder().loop("animation.velociraptor.walk"));
                event.getController().setAnimationSpeed(1.5D);
            }
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.velociraptor.idle"));
            event.getController().setAnimationSpeed(1.0D);
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if (this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().playOnce("animation.velociraptor.attack"));
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(5);
        AnimationController<EntityVelociraptor> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        data.addAnimationController(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
        data.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }


}
