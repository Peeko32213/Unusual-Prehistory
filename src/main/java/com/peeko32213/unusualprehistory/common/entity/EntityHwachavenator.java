package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.msc.projectile.EntityAmberShot;
import com.peeko32213.unusualprehistory.common.entity.msc.projectile.EntityHwachaSpike;
import com.peeko32213.unusualprehistory.common.entity.msc.util.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.CustomRandomStrollGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.EntityRangedMeleeBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.ranged.*;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.slf4j.Log4jLogger;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.EnumSet;

public class EntityHwachavenator extends EntityRangedMeleeBaseDinosaurAnimal implements RangedAttackMob {
    private static final EntityDataAccessor<Boolean> SHOOTING = SynchedEntityData.defineId(EntityHwachavenator.class, EntityDataSerializers.BOOLEAN);
    public static final Logger LOGGER = LogManager.getLogger();
    public float prevShootProgress;
    public float shootProgress;

    public EntityHwachavenator(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30D)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.3D)
                .add(Attributes.MOVEMENT_SPEED, 0.17F);

    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        //this.goalSelector.addGoal(1, new MoveAwayFromTarget(this));
        this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.25D, 40, 20.0F));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));

    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOTING, false);
    }


    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("isShooting", this.isShooting());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setIsShooting(pCompound.getBoolean("isShooting"));
    }

    public boolean isShooting() {
        return this.entityData.get(SHOOTING);
    }

    public void setIsShooting(boolean shooting) {
        this.entityData.set(SHOOTING, shooting);
    }

    public void tick() {
        super.tick();
        LOGGER.info("shootprogress " + shootProgress);

        if (this.isShooting() && shootProgress < 50) {
            this.spit(this.getTarget());
            shootProgress += 1;
            return;
        }

        shootProgress = 0;
        this.setIsShooting(false);

        // prevShootProgress = shootProgress;
        // boolean shooting = entityData.get(SHOOTING);
        // if (shooting && shootProgress < 25) {
        //     shootProgress += 1;
        // }
        // if (!shooting && shootProgress > 0) {
        //     shootProgress -= 1;
        // }
        // if (!level.isClientSide && shooting && shootProgress == 25F) {
        //     if (this.getTarget() != null) {
        //         this.spit(this.getTarget());
        //         this.getNavigation().stop();
        //     }
        //     this.entityData.set(SHOOTING, false);
        // }
    }

    private void setupShooting() {
        this.entityData.set(SHOOTING, true);
    }


    private void spit(LivingEntity target) {
        if(target == null){
            return;
        }
        this.lookAt(target, 100, 100);
        for (int i = 0; i < 2 + random.nextInt(2); i++) {
            EntityHwachaSpike llamaspitentity = new EntityHwachaSpike(this.level, this);
            double d0 = target.getX() - this.getX();
            double d1 = target.getY() - llamaspitentity.getY();
            double d2 = target.getZ() - this.getZ();
            float f = Mth.sqrt((float) (d0 * d0 + d2 * d2)) * 0.2F;
            llamaspitentity.shoot(d0, d1 + (double) f, d2, 2.0F, 5.0F);
            if (!this.isSilent()) {
                this.gameEvent(GameEvent.PROJECTILE_SHOOT);
                this.level.playSound(null, this.getX(), this.getY(), this.getZ(), UPSounds.ENCRUSTED_SPIT.get(), this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
            }
            this.level.addFreshEntity(llamaspitentity);
        }
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
        return UPTags.RAPTOR_TARGETS;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    public void performRangedAttack(LivingEntity p_30762_, float p_30763_) {
        this.setIsShooting(true);
    }

    private class MoveAwayFromTarget extends Goal {

        private final EntityHwachavenator parentEntity;
        private int spitCooldown = 0;
        private BlockPos shootPos = null;

        public MoveAwayFromTarget(EntityHwachavenator entityHwachavenator) {
            this.parentEntity = entityHwachavenator;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean canUse() {
            return parentEntity.getTarget() != null && parentEntity.getTarget().isAlive() && !parentEntity.isBaby();
        }

        public void tick() {
            if (spitCooldown > 0) {
                spitCooldown--;
            }
            if (parentEntity.getTarget() != null) {
                if (shootPos == null || parentEntity.distanceTo(parentEntity.getTarget()) >= 10F || parentEntity.getTarget().distanceToSqr(shootPos.getX(), shootPos.getY(), shootPos.getZ()) < 4) {
                    shootPos = getShootFromPos(parentEntity.getTarget());
                }
                if (shootPos != null) {
                    this.parentEntity.getMoveControl().setWantedPosition(shootPos.getX() + 0.0D, shootPos.getY() + 0.0D, shootPos.getZ() + 0.0D, 0D);
                }
                if (parentEntity.distanceTo(parentEntity.getTarget()) < 25F) {
                    this.parentEntity.lookAt(parentEntity.getTarget(), 30.0F, 30.0F);
                    if (spitCooldown == 0) {
                        parentEntity.setupShooting();
                        spitCooldown = 1 + random.nextInt(1);
                    }
                    shootPos = null;
                }
            }

        }

        public BlockPos getShootFromPos(LivingEntity target) {
            float radius = 3 + parentEntity.getRandom().nextInt(5);
            float angle = (0.01745329251F * (target.yHeadRot + 90F + parentEntity.getRandom().nextInt(180)));
            double extraX = radius * Mth.sin((float) (Math.PI + angle));
            double extraZ = radius * Mth.cos(angle);
            BlockPos radialPos = new BlockPos(target.getX() + extraX, target.getY() + 0, target.getZ() + extraZ);
            if (!parentEntity.isTargetBlocked(Vec3.atCenterOf(radialPos))) {
                return radialPos;
            }
            return parentEntity.blockPosition().above((int) Math.ceil(target.getBbHeight() + 0));
        }
    }

    public boolean isTargetBlocked(Vec3 target) {
        Vec3 Vector3d = new Vec3(this.getX(), this.getEyeY(), this.getZ());
        return this.level.clip(new ClipContext(Vector3d, target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() != HitResult.Type.MISS;
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !isShooting()) {
            {
                event.getController().setAnimation(new AnimationBuilder().loop("animation.hwacha.walk"));
            }
        } else if (!isShooting()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.hwacha.idle"));
            event.getController().setAnimationSpeed(1.0D);
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState predicate2(AnimationEvent<E> event) {
        LOGGER.info("shooting " + isShooting());
        if (isShooting()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.hwacha.turret_firing"));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(5);
        AnimationController<EntityHwachavenator> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        AnimationController<EntityHwachavenator> controller2 = new AnimationController<EntityHwachavenator>(this, "controller2", 0, this::predicate2);
        data.addAnimationController(controller);
        data.addAnimationController(controller2);
    }
}
