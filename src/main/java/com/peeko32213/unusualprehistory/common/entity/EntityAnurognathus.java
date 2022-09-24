package com.peeko32213.unusualprehistory.common.entity;

import com.google.common.base.Predicate;
import com.peeko32213.unusualprehistory.common.entity.util.*;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.*;

public class EntityAnurognathus extends PathfinderMob implements IAnimatable, NeutralMob {
    private final AnimationFactory factory = new AnimationFactory(this);
    @javax.annotation.Nullable
    private UUID persistentAngerTarget;
    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(EntityAnurognathus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> PERCHING = SynchedEntityData.defineId(EntityAnurognathus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Optional<BlockPos>> PERCH_POS = SynchedEntityData.defineId(EntityAnurognathus.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
    private static final EntityDataAccessor<Direction> PERCH_DIRECTION = SynchedEntityData.defineId(EntityAnurognathus.class, EntityDataSerializers.DIRECTION);
    private static final EntityDataAccessor<Integer> CROPS_POLLINATED = SynchedEntityData.defineId(EntityAnurognathus.class, EntityDataSerializers.INT);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private static final UniformInt ALERT_INTERVAL = TimeUtil.rangeOfSeconds(4, 6);
    private static final int ALERT_RANGE_Y = 10;
    private int remainingPersistentAngerTime;
    private boolean isSchool = true;
    private int ticksUntilNextAlert;
    public int pollinateCooldown = 0;
    public final float[] ringBuffer = new float[64];
    public float prevFlyProgress;
    public float flyProgress;
    public float prevPerchProgress;
    public float perchProgress;
    public int ringBufferIndex = -1;
    private int perchCooldown = 100;
    private boolean isLandNavigator;
    private int timeFlying;

    public EntityAnurognathus(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        switchNavigator(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2F)
                .add(Attributes.ATTACK_DAMAGE, 2.0F);

    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new PanicGoal(this, 1D));
        this.goalSelector.addGoal(4, new AIPerch());
        this.goalSelector.addGoal(4, new AnuroPolinateGoal(this));
        this.goalSelector.addGoal(6, new AIFlyIdle());
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0D, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, false, false, entity -> entity.getType().is(UPTags.ANURO_TARGETS)));
    }


    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level);
            this.isLandNavigator = true;
        } else {
            this.moveControl = new FlyingMoveController(this, 0.6F, false, true);
            this.navigation = new FlyingPathNavigation(this, level) {
                public boolean isStableDestination(BlockPos pos) {
                    return !this.level.getBlockState(pos.below(2)).isAir();
                }
            };
            navigation.setCanFloat(false);
            this.isLandNavigator = false;
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FLYING, false);
        this.entityData.define(PERCHING, false);
        this.entityData.define(CROPS_POLLINATED, 0);
        this.entityData.define(PERCH_POS, Optional.empty());
        this.entityData.define(PERCH_DIRECTION, Direction.NORTH);;
    }

    public void tick() {
        super.tick();
        this.prevPerchProgress = perchProgress;
        this.prevFlyProgress = flyProgress;
        if (this.isFlying() && flyProgress < 5F) {
            flyProgress++;
        }
        if (!this.isFlying() && flyProgress > 0F) {
            flyProgress--;
        }
        if (this.isPerching() && perchProgress < 5F) {
            perchProgress++;
        }
        if (!this.isPerching() && perchProgress > 0F) {
            perchProgress--;
        }
        if (this.ringBufferIndex < 0) {
            //initial population of buffer
            for (int i = 0; i < this.ringBuffer.length; ++i) {
                this.ringBuffer[i] = 15;
            }
        }
        if(pollinateCooldown > 0){
            pollinateCooldown--;
        }
        this.ringBufferIndex++;
        if (this.ringBufferIndex == this.ringBuffer.length) {
            this.ringBufferIndex = 0;
        }
        if (perchCooldown > 0) {
            perchCooldown--;
        }
        if (!level.isClientSide) {
            if (isFlying() && this.isLandNavigator) {
                switchNavigator(false);
            }
            if (!isFlying() && !this.isLandNavigator) {
                switchNavigator(true);
            }
            if (this.isFlying()) {
                if (this.isFlying() && !this.onGround) {
                    if (!this.isInWaterOrBubble()) {
                        this.setDeltaMovement(this.getDeltaMovement().multiply(1F, 0.6F, 1F));
                    }
                }
                if (this.isOnGround() && timeFlying > 20) {
                    this.setFlying(false);
                }
                this.timeFlying++;
            } else {
                this.timeFlying = 0;
            }
            if(isPerching() && this.getPerchPos() != null) {
                if ((!level.getBlockState(this.getPerchPos()).is(UPTags.ANURO_PERCHES) || this.distanceToSqr(Vec3.atCenterOf(this.getPerchPos())) > 2.25F)) {
                    this.setPerching(false);
                } else {
                    slideTowardsPerch();
                }
            }
        }
    }

    public boolean hurt(DamageSource source, float amount) {
        boolean prev = super.hurt(source, amount);
        if (prev && source.getDirectEntity() instanceof LivingEntity) {
            this.setPerching(false);
        }
        return prev;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source == DamageSource.IN_WALL || source == DamageSource.FALLING_BLOCK || super.isInvulnerableTo(source);
    }

    public boolean isFlying() {
        return this.entityData.get(FLYING);
    }

    public void setFlying(boolean flying) {
        if (flying && isBaby()) {
            return;
        }
        this.entityData.set(FLYING, flying);
    }

    public BlockPos getPerchPos() {
        return this.entityData.get(PERCH_POS).orElse(null);
    }

    public void setPerchPos(BlockPos pos) {
        this.entityData.set(PERCH_POS, Optional.ofNullable(pos));
    }

    public Direction getPerchDirection() {
        return this.entityData.get(PERCH_DIRECTION);
    }

    public void setPerchDirection(Direction direction) {
        this.entityData.set(PERCH_DIRECTION, direction);
    }

    public boolean isPerching() {
        return this.entityData.get(PERCHING).booleanValue();
    }

    public void setPerching(boolean perching) {
        this.entityData.set(PERCHING, perching);
    }

    public int getCropsPollinated() {
        return this.entityData.get(CROPS_POLLINATED).intValue();
    }

    public void setCropsPollinated(int crops) {
        this.entityData.set(CROPS_POLLINATED, Integer.valueOf(crops));
    }

    public boolean canBlockBeSeen(BlockPos pos) {
        double x = pos.getX() + 0.5F;
        double y = pos.getY() + 0.5F;
        double z = pos.getZ() + 0.5F;
        HitResult result = this.level.clip(new ClipContext(new Vec3(this.getX(), this.getY() + (double) this.getEyeHeight(), this.getZ()), new Vec3(x, y, z), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        double dist = result.getLocation().distanceToSqr(x, y, z);
        return dist <= 1.0D || result.getType() == HitResult.Type.MISS;
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Flying", this.isFlying());
        compound.putBoolean("Perching", this.isPerching());
        compound.putInt("CropsPollinated", this.getCropsPollinated());
        compound.putInt("PollinateCooldown", this.pollinateCooldown);
        compound.putInt("PerchDir", this.getPerchDirection().ordinal());
        if (this.getPerchPos() != null) {
            compound.putInt("PerchX", this.getPerchPos().getX());
            compound.putInt("PerchY", this.getPerchPos().getY());
            compound.putInt("PerchZ", this.getPerchPos().getZ());
        }
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setFlying(compound.getBoolean("Flying"));
        this.setPerching(compound.getBoolean("Perching"));
        this.setCropsPollinated(compound.getInt("CropsPollinated"));
        this.pollinateCooldown = compound.getInt("PollinateCooldown");
        this.setPerchDirection(Direction.from3DDataValue(compound.getInt("PerchDir")));
        if (compound.contains("PerchX") && compound.contains("PerchY") && compound.contains("PerchZ")) {
            this.setPerchPos(new BlockPos(compound.getInt("PerchX"), compound.getInt("PerchY"), compound.getInt("PerchZ")));
        }
    }

    public boolean isValidPerchFromSide(BlockPos pos, Direction direction) {
        BlockPos offset = pos.relative(direction);
        BlockState state = level.getBlockState(pos);
        return state.is(UPTags.ANURO_PERCHES) && (!level.getBlockState(pos.above()).isCollisionShapeFullBlock(level, pos.above()) || level.isEmptyBlock(pos.above())) && (!level.getBlockState(offset).isCollisionShapeFullBlock(level, offset) && !level.getBlockState(offset).is(UPTags.ANURO_PERCHES) || level.isEmptyBlock(offset));
    }

    private void slideTowardsPerch() {
        Vec3 block = Vec3.upFromBottomCenterOf(this.getPerchPos(), 1.0F);
        Vec3 look = block.subtract(this.position()).normalize();
        Vec3 onBlock = block.add(this.getPerchDirection().getStepX() * 0.35F, 0F, this.getPerchDirection().getStepZ() * 0.35F);
        Vec3 diff = onBlock.subtract(this.position());
        float f = (float)diff.length();
        float f1 = f > 1F ? 0.25F : f * 0.1F;
        Vec3 sub = diff.normalize().scale(f1);
        float f2 = -(float) (Mth.atan2(look.x, look.z) * (double) (180F / (float) Math.PI));
        EntityAnurognathus.this.setYRot(f2);
        EntityAnurognathus.this.yHeadRot = f2;
        EntityAnurognathus.this.yBodyRot = f2;

        this.setDeltaMovement(this.getDeltaMovement().add(sub));

    }

    public Vec3 getBlockGrounding(Vec3 fleePos) {
        float radius = 10 + this.getRandom().nextInt(15);
        float neg = this.getRandom().nextBoolean() ? 1 : -1;
        float renderYawOffset = this.yBodyRot;
        float angle = (0.01745329251F * renderYawOffset) + 3.15F + (this.getRandom().nextFloat() * neg);
        double extraX = radius * Mth.sin((float) (Math.PI + angle));
        double extraZ = radius * Mth.cos(angle);
        BlockPos radialPos = new BlockPos(fleePos.x() + extraX, getY(), fleePos.z() + extraZ);
        BlockPos ground = this.getToucanGround(radialPos);
        if (ground.getY() < -64) {
            return null;
        } else {
            ground = this.blockPosition();
            while (ground.getY() > -64 && !level.getBlockState(ground).getMaterial().isSolidBlocking()) {
                ground = ground.below();
            }
        }
        if (!this.isTargetBlocked(Vec3.atCenterOf(ground.above()))) {
            return Vec3.atCenterOf(ground.below());
        }
        return null;
    }

    public boolean isTargetBlocked(Vec3 target) {
        Vec3 Vector3d = new Vec3(this.getX(), this.getEyeY(), this.getZ());

        return this.level.clip(new ClipContext(Vector3d, target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() != HitResult.Type.MISS;
    }

    public Vec3 getBlockInViewAway(Vec3 fleePos, float radiusAdd) {
        float radius = 5 + radiusAdd + this.getRandom().nextInt(5);
        float neg = this.getRandom().nextBoolean() ? 1 : -1;
        float renderYawOffset = this.yBodyRot;
        float angle = (0.01745329251F * renderYawOffset) + 3.15F + (this.getRandom().nextFloat() * neg);
        double extraX = radius * Mth.sin((float) (Math.PI + angle));
        double extraZ = radius * Mth.cos(angle);
        BlockPos radialPos = new BlockPos(fleePos.x() + extraX, 0, fleePos.z() + extraZ);
        BlockPos ground = getToucanGround(radialPos);
        int distFromGround = (int) this.getY() - ground.getY();
        int flightHeight = 5 + this.getRandom().nextInt(5);
        int j = this.getRandom().nextInt(5) + 5;

        BlockPos newPos = ground.above(distFromGround > 5 ? flightHeight : j);
        if (level.getBlockState(ground).is(BlockTags.LEAVES)) {
            newPos = ground.above(1 + this.getRandom().nextInt(3));
        }
        if (!this.isTargetBlocked(Vec3.atCenterOf(newPos)) && this.distanceToSqr(Vec3.atCenterOf(newPos)) > 1) {
            return Vec3.atCenterOf(newPos);
        }
        return null;
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    private boolean isOverWaterOrVoid() {
        BlockPos position = this.blockPosition();
        while (position.getY() > -65 && level.isEmptyBlock(position)) {
            position = position.below();
        }
        return !level.getFluidState(position).isEmpty() || level.getBlockState(position).is(Blocks.VINE) || position.getY() <= -65;
    }

    public BlockPos getToucanGround(BlockPos in) {
        BlockPos position = new BlockPos(in.getX(), this.getY(), in.getZ());
        while (position.getY() < 320 && !level.getFluidState(position).isEmpty()) {
            position = position.above();
        }
        while (position.getY() > -64 && !level.getBlockState(position).getMaterial().isSolidBlocking() && level.getFluidState(position).isEmpty()) {
            position = position.below();
        }
        return position;
    }


    @Override
    public boolean doHurtTarget(Entity target) {
        boolean shouldHurt;
        float damage = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float knockback = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (target instanceof LivingEntity livingEntity) {
            damage += livingEntity.getMobType().equals(MobType.ARTHROPOD) ? damage : 0;
            knockback += (float)EnchantmentHelper.getKnockbackBonus(this);
        }
        if (shouldHurt = target.hurt(DamageSource.mobAttack(this), damage)) {
            if (knockback > 0.0f && target instanceof LivingEntity) {
                ((LivingEntity)target).knockback(knockback * 0.5f, Mth.sin(this.getYRot() * ((float)Math.PI / 180)), -Mth.cos(this.getYRot() * ((float)Math.PI / 180)));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
            }
            this.doEnchantDamageEffects(this, target);
            this.setLastHurtMob(target);
        }
        return shouldHurt;
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        if (event.isMoving() && this.isOnGround() && this.onGround) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.anuro.walk", true));
            event.getController().setAnimationSpeed(1.05);
            return PlayState.CONTINUE;
        }
        if (!event.isMoving() && this.isOnGround() && this.onGround) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.anuro.idle", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.anuro.fly", true));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if (this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.anuro.bite", false));
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(5);
        AnimationController<EntityAnurognathus> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        data.addAnimationController(controller);
        data.addAnimationController(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
    }

    private class AIFlyIdle extends Goal {
        protected double x;
        protected double y;
        protected double z;

        public AIFlyIdle() {
            super();
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (EntityAnurognathus.this.isVehicle() || EntityAnurognathus.this.isPerching() || (EntityAnurognathus.this.getTarget() != null && EntityAnurognathus.this.getTarget().isAlive()) || EntityAnurognathus.this.isPassenger()) {
                return false;
            } else {
                if (EntityAnurognathus.this.getRandom().nextInt(45) != 0 && !EntityAnurognathus.this.isFlying()) {
                    return false;
                }

                Vec3 lvt_1_1_ = this.getPosition();
                if (lvt_1_1_ == null) {
                    return false;
                } else {
                    this.x = lvt_1_1_.x;
                    this.y = lvt_1_1_.y;
                    this.z = lvt_1_1_.z;
                    return true;
                }
            }
        }

        public void tick() {
            EntityAnurognathus.this.getMoveControl().setWantedPosition(this.x, this.y, this.z, 1F);
            if (isFlying() && EntityAnurognathus.this.onGround && EntityAnurognathus.this.timeFlying > 10) {
                EntityAnurognathus.this.setFlying(false);
            }
        }

        @javax.annotation.Nullable
        protected Vec3 getPosition() {
            Vec3 vector3d = EntityAnurognathus.this.position();
            if (EntityAnurognathus.this.timeFlying < 200 || EntityAnurognathus.this.isOverWaterOrVoid()) {
                return EntityAnurognathus.this.getBlockInViewAway(vector3d, 0);
            } else {
                return EntityAnurognathus.this.getBlockGrounding(vector3d);
            }
        }

        public boolean canContinueToUse() {
            return EntityAnurognathus.this.isFlying() && EntityAnurognathus.this.distanceToSqr(x, y, z) > 5F;
        }

        public void start() {
            EntityAnurognathus.this.setFlying(true);
            EntityAnurognathus.this.getMoveControl().setWantedPosition(this.x, this.y, this.z, 1F);
        }

        public void stop() {
            EntityAnurognathus.this.getNavigation().stop();
            x = 0;
            y = 0;
            z = 0;
            super.stop();
        }

    }

    private class AIPerch extends Goal {
        private BlockPos perch = null;
        private Direction perchDirection = null;
        private int perchingTime = 0;
        private int runCooldown = 0;

        public AIPerch() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (EntityAnurognathus.this.getTarget() != null && EntityAnurognathus.this.getTarget().isAlive()) {
                return false;
            }
            if(runCooldown > 0){
                runCooldown--;
            }else if (!EntityAnurognathus.this.isPerching() && EntityAnurognathus.this.perchCooldown == 0 && EntityAnurognathus.this.random.nextInt(35) == 0) {
                this.perchingTime = 0;
                if (EntityAnurognathus.this.getPerchPos() != null && EntityAnurognathus.this.isValidPerchFromSide(EntityAnurognathus.this.getPerchPos(), EntityAnurognathus.this.getPerchDirection())) {
                    perch = EntityAnurognathus.this.getPerchPos();
                    perchDirection = EntityAnurognathus.this.getPerchDirection();
                } else {
                    findPerch();
                }
                runCooldown = 120 + EntityAnurognathus.this.getRandom().nextInt(140);
                return perch != null && perchDirection != null;
            }
            return false;
        }

        private void findPerch() {
            Random random = EntityAnurognathus.this.getRandom();
            Direction[] horiz = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
            if (isValidPerchFromSide(EntityAnurognathus.this.getBlockPosBelowThatAffectsMyMovement(), EntityAnurognathus.this.getDirection())) {
                perch = EntityAnurognathus.this.getBlockPosBelowThatAffectsMyMovement();
                perchDirection = EntityAnurognathus.this.getDirection();
                return;
            }
            for(Direction dir : horiz){
                if (isValidPerchFromSide(EntityAnurognathus.this.getBlockPosBelowThatAffectsMyMovement(), dir)) {
                    perch = EntityAnurognathus.this.getBlockPosBelowThatAffectsMyMovement();
                    perchDirection = dir;
                    return;
                }
            }
            int range = 14;
            for (int i = 0; i < 15; i++) {
                BlockPos blockpos1 = EntityAnurognathus.this.blockPosition().offset(random.nextInt(range) - range / 2, 3, random.nextInt(range) - range / 2);
                while (EntityAnurognathus.this.level.isEmptyBlock(blockpos1) && blockpos1.getY() > -64) {
                    blockpos1 = blockpos1.below();
                }
                Direction dir = Direction.from2DDataValue(random.nextInt(3));
                if (isValidPerchFromSide(blockpos1, dir)) {
                    perch = blockpos1;
                    perchDirection = dir;
                    break;
                }
            }
        }

        @Override
        public boolean canContinueToUse() {
            return (perchingTime < 300 || EntityAnurognathus.this.level.isDay()) && (EntityAnurognathus.this.getTarget() == null || !EntityAnurognathus.this.getTarget().isAlive()) && !EntityAnurognathus.this.isPassenger();
        }

        public void tick() {
            if (EntityAnurognathus.this.isPerching()) {
                perchingTime++;
                EntityAnurognathus.this.getNavigation().stop();
                Vec3 block = Vec3.upFromBottomCenterOf(EntityAnurognathus.this.getPerchPos(), 1.0F);
                Vec3 onBlock = block.add(EntityAnurognathus.this.getPerchDirection().getStepX() * 0.35F, 0F, EntityAnurognathus.this.getPerchDirection().getStepZ() * 0.35F);
                double dist = EntityAnurognathus.this.distanceToSqr(onBlock);
                Vec3 dirVec = block.subtract(EntityAnurognathus.this.position());
                if (perchingTime > 10 && (dist > 2.3F || !EntityAnurognathus.this.isValidPerchFromSide(EntityAnurognathus.this.getPerchPos(), EntityAnurognathus.this.getPerchDirection()))) {
                    EntityAnurognathus.this.setPerching(false);
                } else if (dist > 1F) {
                    EntityAnurognathus.this.slideTowardsPerch();
                    if (EntityAnurognathus.this.getPerchPos().getY() + 1.2F > EntityAnurognathus.this.getBoundingBox().minY) {
                        EntityAnurognathus.this.setDeltaMovement(EntityAnurognathus.this.getDeltaMovement().add(0, 0.2F, 0));
                    }
                    float f = -(float) (Mth.atan2(dirVec.x, dirVec.z) * (double) (180F / (float) Math.PI));
                    EntityAnurognathus.this.setYRot(f);
                    EntityAnurognathus.this.yHeadRot = f;
                    EntityAnurognathus.this.yBodyRot = f;
                }
            } else if (perch != null) {
                if (EntityAnurognathus.this.distanceToSqr(Vec3.atCenterOf(perch)) > 100) {
                    EntityAnurognathus.this.setFlying(true);
                }
                double distX = perch.getX() + 0.5F - EntityAnurognathus.this.getX();
                double distZ = perch.getZ() + 0.5F - EntityAnurognathus.this.getZ();
                if (distX * distX + distZ * distZ < 1F || !EntityAnurognathus.this.isFlying()) {
                    EntityAnurognathus.this.getNavigation().moveTo(perch.getX() + 0.5F, perch.getY() + 1.5F, perch.getZ() + 0.5F, 1F);
                    if(EntityAnurognathus.this.getNavigation().isDone()){
                        EntityAnurognathus.this.getMoveControl().setWantedPosition(perch.getX() + 0.5F, perch.getY() + 1.5F, perch.getZ() + 0.5F, 1F);

                    }
                } else {
                    EntityAnurognathus.this.getNavigation().moveTo(perch.getX() + 0.5F, perch.getY() + 2.5F, perch.getZ() + 0.5F, 1F);
                }
                if (EntityAnurognathus.this.getBlockPosBelowThatAffectsMyMovement().equals(perch)) {
                    EntityAnurognathus.this.setDeltaMovement(Vec3.ZERO);
                    EntityAnurognathus.this.setPerching(true);
                    EntityAnurognathus.this.setFlying(false);
                    EntityAnurognathus.this.setPerchPos(perch);
                    EntityAnurognathus.this.setPerchDirection(perchDirection);
                    EntityAnurognathus.this.getNavigation().stop();
                    perch = null;
                } else {
                    EntityAnurognathus.this.setPerching(false);
                }
            }
        }

        public void stop() {
            EntityAnurognathus.this.setPerching(false);
            EntityAnurognathus.this.perchCooldown = 120 + random.nextInt(1200);
            this.perch = null;
            this.perchDirection = null;
        }
    }



    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    @Override
    public void setRemainingPersistentAngerTime(int p_21673_) {
        this.remainingPersistentAngerTime = p_21673_;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@org.jetbrains.annotations.Nullable UUID p_21672_) {
        this.persistentAngerTarget = p_21672_;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }


}
