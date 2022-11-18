package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.util.AnuroPolinateGoal;
import com.peeko32213.unusualprehistory.common.entity.util.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.util.LandCreaturePathNavigation;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Fox;
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

public class EntityVelociraptor extends Animal implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    protected boolean pushingState = false;

    public EntityVelociraptor(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.maxUpStep = 2.0f;
    }


    @Override
    protected PathNavigation createNavigation(Level level) {
        return new LandCreaturePathNavigation(this, level);
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
        this.goalSelector.addGoal(5, new PushButtonsGoal(this, 0.8D, 10));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new EntityVelociraptor.IMeleeAttackGoal());
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
        this.goalSelector.addGoal(2, new EntityVelociraptor.RaptorOpenDoorGoal(this));

    }


    protected void customServerAiStep() {
        if (!this.isNoAi() && GoalUtils.hasGroundPathNavigation(this)) {
            ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
        }

        super.customServerAiStep();
    }


        class IMeleeAttackGoal extends MeleeAttackGoal {
        public IMeleeAttackGoal() {
            super(EntityVelociraptor.this, 1.6D, true);
        }

        protected double getAttackReachSqr(LivingEntity p_25556_) {
            return (double)(this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 0.66F + p_25556_.getBbWidth());
        }

    }

    protected class RaptorOpenDoorGoal extends OpenDoorGoal {
        public RaptorOpenDoorGoal(EntityVelociraptor p_32128_) {
            super(p_32128_, false);
        }

        public boolean canUse() {
            return super.canUse();
        }
    }

    public class PushButtonsGoal extends MoveToBlockGoal {

        private static final int WAIT_TICKS = 20;
        protected int ticksWaited;
        public PushButtonsGoal(PathfinderMob pathAwareEntity, double d, int i) {
            super(pathAwareEntity, d, i);
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
                } else {
                    ++this.ticksWaited;
                }
            } else if (!this.isReachedTarget() && EntityVelociraptor.this.random.nextFloat() < 0.05F) {
                EntityVelociraptor.this.playSound(SoundEvents.FOX_SNIFF, 1.0F, 1.0F);
            }

            super.tick();
        }

        protected void onReachedTarget() {
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(EntityVelociraptor.this.level, EntityVelociraptor.this)) {
                BlockState blockstate = EntityVelociraptor.this.level.getBlockState(this.blockPos);
                if (blockstate.is(UPBlocks.FOSSIL_BUTTON.get())) {
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
            return blockState.getBlock() instanceof ButtonBlock;
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



    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return UPEntities.VELOCI.get().create(serverLevel);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
            {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.velociraptor.walk", true));
                event.getController().setAnimationSpeed(1.5D);
            }
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.velociraptor.idle", true));
            event.getController().setAnimationSpeed(1.0D);
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if (this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.velociraptor.attack", false));
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
        return factory;
    }


}
