package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.util.*;
import com.peeko32213.unusualprehistory.core.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.util.GeckoLibUtil;
public class EntityTyrannosaurusRex extends Animal implements IAnimatable {

    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(EntityTyrannosaurusRex.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COMBAT_STATE = SynchedEntityData.defineId(EntityTyrannosaurusRex.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ENTITY_STATE = SynchedEntityData.defineId(EntityTyrannosaurusRex.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> EEPY = SynchedEntityData.defineId(EntityTyrannosaurusRex.class, EntityDataSerializers.BOOLEAN);
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private int stunnedTick;

    public EntityTyrannosaurusRex(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.maxUpStep = 1.0f;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.ARMOR, 15.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 10.5D)
                .add(Attributes.ATTACK_KNOCKBACK, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 50D);


    }



    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, LivingEntity.class,10, false, false, (entity) -> entity.getType().is(UPTags.REX_TARGETS)));
        this.goalSelector.addGoal(1, new EntityTyrannosaurusRex.RexMeleeAttackGoal(this, 2F, true));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 15.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this) {

            public boolean canUse() {
                return !isEepy();
            }

        }));
    }



    public InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        InteractionResult type = super.mobInteract(player, hand);
        if(item == UPItems.ADORNED_STAFF.get() && this.isEepy()) {
            itemstack.hurtAndBreak(5, player, (p_29822_) -> {
                p_29822_.broadcastBreakEvent(hand);
            });
            this.heal(200);
            this.setTarget(null);
            this.setEepy(false);
            this.spawnAtLocation(UPItems.REX_TOOTH.get(), 4);
            this.spawnAtLocation(UPItems.REX_SCALE.get(), 9);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean canAttack(LivingEntity entity) {
        boolean prev = super.canAttack(entity);
        if(isEepy()){
            return false;
        }
        return prev;
    }

    public void setEepy(boolean eepy) {
        this.entityData.set(EEPY, Boolean.valueOf(eepy));
    }

    public boolean hasEepy() {
        return this.entityData.get(EEPY).booleanValue();
    }

    public boolean isEepy() {
        this.setTarget(null);
        this.setAggressive(false);
        return this.getHealth() <= this.getMaxHealth() / 4.0F;
    }

    public void travel(Vec3 vec3d) {
        if (this.isEepy()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            vec3d = Vec3.ZERO;
        }
        super.travel(vec3d);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("StunTick", this.stunnedTick);
        compound.putBoolean("Eepy", this.hasEepy());
        compound.putInt("animation_state", this.getAnimationState());
        compound.putInt("combat_state", this.getCombatState());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.stunnedTick = compound.getInt("StunTick");
        this.setEepy(compound.getBoolean("Eepy"));
        this.setAnimationState(compound.getInt("animation_state"));
        this.setCombatState(compound.getInt("combat_state"));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION_STATE, 0);
        this.entityData.define(COMBAT_STATE, 0);
        this.entityData.define(ENTITY_STATE, 0);
        this.entityData.define(EEPY, false);
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 39) {
            this.stunnedTick = 60;
        }
        else {
            super.handleEntityEvent(pId);
        }
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

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.isEepy() ) {
            this.stunnedTick = 60;
            this.level.broadcastEntityEvent(this, (byte)39);
            this.setEepy(true);
            this.setAggressive(false);
        }
        if (this.horizontalCollision && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this) && this.isSprinting()) {
            boolean flag = false;
            AABB axisalignedbb = this.getBoundingBox().inflate(0.2D);
            for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(axisalignedbb.minX), Mth.floor(axisalignedbb.minY), Mth.floor(axisalignedbb.minZ), Mth.floor(axisalignedbb.maxX), Mth.floor(axisalignedbb.maxY), Mth.floor(axisalignedbb.maxZ))) {
                BlockState blockstate = this.level.getBlockState(blockpos);
                if (blockstate.is(UPTags.TRIKE_BREAKABLES)) {
                    flag = this.level.destroyBlock(blockpos, true, this) || flag;
                }
            }
        }

    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }


    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.hasCustomName();
    }

    public boolean removeWhenFarAway(double d) {
        return !this.hasCustomName();
    }

    public int getAnimationState() {

        return this.entityData.get(ANIMATION_STATE);
    }

    public void setAnimationState(int anim) {

        this.entityData.set(ANIMATION_STATE, anim);
    }

    public int getCombatState() {

        return this.entityData.get(COMBAT_STATE);
    }

    public void setCombatState(int anim) {

        this.entityData.set(COMBAT_STATE, anim);
    }

    public int getEntityState() {

        return this.entityData.get(ENTITY_STATE);
    }

    public void setEntityState(int anim) {

        this.entityData.set(ENTITY_STATE, anim);
    }

    static class RexMeleeAttackGoal extends Goal {

        protected final EntityTyrannosaurusRex mob;
        private final double speedModifier;
        private final boolean followingTargetEvenIfNotSeen;
        private Path path;
        private double pathedTargetX;
        private double pathedTargetY;
        private double pathedTargetZ;
        private int ticksUntilNextPathRecalculation;
        private int ticksUntilNextAttack;
        private long lastCanUseCheck;
        private int failedPathFindingPenalty = 0;
        private boolean canPenalize = false;
        private int animTime = 0;

        private int animState;


        public RexMeleeAttackGoal(EntityTyrannosaurusRex p_i1636_1_, double p_i1636_2_, boolean p_i1636_4_) {
            this.mob = p_i1636_1_;
            this.speedModifier = p_i1636_2_;
            this.followingTargetEvenIfNotSeen = p_i1636_4_;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean canUse() {
            long i = this.mob.level.getGameTime();

            if (i - this.lastCanUseCheck < 20L) {
                return false;
            } else {
                this.lastCanUseCheck = i;
                LivingEntity livingentity = this.mob.getTarget();
                if (livingentity == null) {
                    return false;
                } else if (!livingentity.isAlive()) {
                    return false;
                } else {
                    if (canPenalize) {
                        if (--this.ticksUntilNextPathRecalculation <= 0) {
                            this.path = this.mob.getNavigation().createPath(livingentity, 0);
                            this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                            return this.path != null;
                        } else {
                            return true;
                        }
                    }
                    this.path = this.mob.getNavigation().createPath(livingentity, 0);
                    if (this.path != null) {
                        return true;
                    } else {
                        return this.getAttackReachSqr(livingentity) >= this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
                    }
                }
            }


        }

        public boolean canContinueToUse() {

            LivingEntity livingentity = this.mob.getTarget();

            if (livingentity == null) {
                return false;
            }
            else if (!livingentity.isAlive()) {
                return false;
            } else if (!this.followingTargetEvenIfNotSeen) {
                return !this.mob.getNavigation().isDone();
            } else if (!this.mob.isWithinRestriction(livingentity.blockPosition())) {
                return false;
            } else {
                return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player) livingentity).isCreative();
            }


        }

        public void start() {
            this.mob.getNavigation().moveTo(this.path, this.speedModifier);
            this.ticksUntilNextPathRecalculation = 0;
            this.ticksUntilNextAttack = 0;

            //this.mob.setAnimationState(0);


        }

        public void stop() {
            LivingEntity livingentity = this.mob.getTarget();
            if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
                this.mob.setTarget((LivingEntity) null);
            }

        }

        public void tick() {


            LivingEntity target = this.mob.getTarget();
            double distance = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
            double reach = this.getAttackReachSqr(target);

            Vec3 aim = this.mob.getLookAngle();
            Vec2 aim2d = new Vec2((float) (aim.x / (1 - Math.abs(aim.y))), (float) (aim.z / (1 - Math.abs(aim.y))));
            this.animState = this.mob.getAnimationState();



            switch (animState) {
                case 21:
                    tickBiteAttack();
                    return;
                case 22:
                    tickWhipAttack();
                    return;
                case 23:
                    tickStompLeftAttack();
                    return;
                case 24:
                    tickStompRightAttack();
                    return;
                default:
                    this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                    this.ticksUntilNextAttack = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                    this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    this.doMovement(target, distance);
                    this.checkForCloseRangeAttack(distance, reach);
                    return;

            }
        }

        protected void doMovement (LivingEntity livingentity, Double d0){


            this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);


            if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D || this.mob.getRandom().nextFloat() < 0.05F)) {
                this.pathedTargetX = livingentity.getX();
                this.pathedTargetY = livingentity.getY();
                this.pathedTargetZ = livingentity.getZ();
                this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                if (this.canPenalize) {
                    this.ticksUntilNextPathRecalculation += failedPathFindingPenalty;
                    if (this.mob.getNavigation().getPath() != null) {
                        Node finalPathPoint = this.mob.getNavigation().getPath().getEndNode();
                        if (finalPathPoint != null && livingentity.distanceToSqr(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1)
                            failedPathFindingPenalty = 0;
                        else
                            failedPathFindingPenalty += 10;
                    } else {
                        failedPathFindingPenalty += 10;
                    }
                }
                if (d0 > 1024.0D) {
                    this.ticksUntilNextPathRecalculation += 10;
                } else if (d0 > 256.0D) {
                    this.ticksUntilNextPathRecalculation += 5;
                }

                if (!this.mob.getNavigation().moveTo(livingentity, this.speedModifier)) {
                    this.ticksUntilNextPathRecalculation += 15;
                }
            }

        }


        protected void checkForCloseRangeAttack ( double distance, double reach){
            if (distance <= reach && this.ticksUntilNextAttack <= 0) {
                int r = this.mob.getRandom().nextInt(100);
                if (r <= 25) {
                    this.mob.setAnimationState(21);
                    return;
                } else if (r <= 50 && r > 25) {
                    this.mob.setAnimationState(22);
                    return;
                } else if (r <= 75 && r > 50) {
                    this.mob.setAnimationState(23);
                    return;
                } else {
                    this.mob.setAnimationState(24);
                    return;
                }

            }
        }


        protected boolean getRangeCheck () {

            return
                    this.mob.distanceToSqr(this.mob.getTarget().getX(), this.mob.getTarget().getY(), this.mob.getTarget().getZ())
                            <=
                            1.8F * this.getAttackReachSqr(this.mob.getTarget());
        }



        protected void tickBiteAttack () {
            animTime++;
            if(animTime==5) {
                preformBiteAttack();
            }
            if(animTime>=9) {
                animTime=0;
                if (this.getRangeCheck()) {
                    this.mob.setAnimationState(22);
                }else {
                    this.mob.setAnimationState(0);
                    this.resetAttackCooldown();
                    this.ticksUntilNextPathRecalculation = 0;
                }
            }
        }

        protected void tickWhipAttack () {
            animTime++;
            this.mob.getNavigation().stop();
            if(animTime==8) {
                preformWhipAttack();
            }
            if(animTime>=11) {
                animTime=0;

                this.mob.setAnimationState(0);
                this.resetAttackCooldown();
                this.ticksUntilNextPathRecalculation = 0;

            }

        }

        protected void tickStompLeftAttack () {
            animTime++;
            this.mob.getNavigation().stop();
            if(animTime==20) {
                preformStompAttack();
            }
            if(animTime>=21) {
                animTime=0;
                this.mob.setAnimationState(0);
                this.resetAttackCooldown();
                this.ticksUntilNextPathRecalculation = 0;
            }

        }

        protected void tickStompRightAttack () {
            animTime++;
            this.mob.getNavigation().stop();
            if(animTime==20) {
                preformStompAttack();
            }
            if(animTime>=21) {
                animTime=0;
                this.mob.setAnimationState(0);
                this.resetAttackCooldown();
                this.ticksUntilNextPathRecalculation = 0;
            }

        }


        protected void preformBiteAttack () {


            Vec3 pos = mob.position();
            this.mob.playSound(UPSounds.REX_BITE, 0.5F, 0.5F);
            HitboxHelper.LargeAttack(DamageSource.mobAttack(mob),20.0f, 0.1f, mob, pos,  5.0F, -Math.PI/2, Math.PI/2, -1.0f, 3.0f);

        }

        protected void preformWhipAttack () {


            Vec3 pos = mob.position();
            this.mob.playSound(UPSounds.REX_TAIL_SWIPE, 0.5F, 0.5F);
            HitboxHelper.LargeAttack(DamageSource.mobAttack(mob),10.0f, 2.0f, mob, pos,  8.0F, -Math.PI/2, Math.PI/2, -1.0f, 3.0f);

        }

        protected void preformStompAttack () {
            Vec3 pos = mob.position();
            List<LivingEntity> list = this.mob.level.getEntitiesOfClass(LivingEntity.class, this.mob.getBoundingBox().inflate(4.0D, 2.0D, 4.0D));
            this.mob.playSound(UPSounds.REX_STOMP_ATTACK, 0.5F, 0.5F);
            HitboxHelper.LargeAttack(DamageSource.mobAttack(mob),5.0f, 0.1f, mob, pos,  5.0F, -Math.PI/2, Math.PI/2, -1.0f, 3.0f);
            AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.mob.level, this.mob.getX(), this.mob.getY(), this.mob.getZ());
            areaeffectcloud.setParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE);
            areaeffectcloud.setRadius(0.5F);
            areaeffectcloud.setDuration(200);
            areaeffectcloud.setRadiusPerTick((1.0F - areaeffectcloud.getRadius()) / (float)areaeffectcloud.getDuration());
            areaeffectcloud.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 10));
            if (!list.isEmpty()) {
                for(LivingEntity livingentity : list) {
                    double d0 = this.mob.distanceToSqr(livingentity);
                    if (d0 < 16.0D) {
                        areaeffectcloud.setPos(livingentity.getX(), livingentity.getY(), livingentity.getZ());
                        break;
                    }
                }
            }

            this.mob.level.addFreshEntity(areaeffectcloud);
        }


        protected void resetAttackCooldown () {
            this.ticksUntilNextAttack = 0;
        }

        protected boolean isTimeToAttack () {
            return this.ticksUntilNextAttack <= 0;
        }

        protected int getTicksUntilNextAttack () {
            return this.ticksUntilNextAttack;
        }

        protected int getAttackInterval () {
            return 5;
        }

        protected double getAttackReachSqr(LivingEntity p_179512_1_) {
            return (double)(this.mob.getBbWidth() * 2.5F * this.mob.getBbWidth() * 1.8F + p_179512_1_.getBbWidth());
        }
    }

    public void killed(ServerLevel world, LivingEntity entity) {
        this.heal(50);
        super.killed(world, entity);
    }

    public boolean canBeCollidedWith() {
        return true;
    }

    protected void collideWithEntity(Entity entityIn) {
        entityIn.push(this);
    }

    public void collideWithNearbyEntities() {

    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source == DamageSource.FALL || source == DamageSource.MAGIC || source == DamageSource.IN_WALL || source == DamageSource.FALLING_BLOCK || source == DamageSource.CACTUS || source.isProjectile() || source == DamageSource.OUT_OF_WORLD || source.isFire() || super.isInvulnerableTo(source);
    }

    public boolean canBeAffected(MobEffectInstance p_33809_) {
        if (p_33809_.getEffect() == MobEffects.WEAKNESS) {
            net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent event = new net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent(this, p_33809_);
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
            return event.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW;
        }
        return super.canBeAffected(p_33809_);
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.REX_IDLE;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.REX_HURT;
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.REX_DEATH;
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(UPSounds.REX_STEP, 0.3F, 1.0F);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(event.isMoving()){
            if(this.isSprinting()) {
                event.getController().setAnimation(new AnimationBuilder().loop("rex.charge"));
                event.getController().setAnimationSpeed(1.0F);
                return PlayState.CONTINUE;
            } else {
                event.getController().setAnimation(new AnimationBuilder().loop("rex.walk"));
                event.getController().setAnimationSpeed(1.0F);
                return PlayState.CONTINUE;
            }
        }
        event.getController().setAnimation(new AnimationBuilder().loop("rex.idle"));
        event.getController().setAnimationSpeed(1.0F);
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        int animState = this.getAnimationState();
        {
            switch (animState) {

                case 21:
                    event.getController().setAnimation(new AnimationBuilder().playOnce("rex.bite"));
                    return PlayState.CONTINUE;
                case 22:
                    event.getController().setAnimation(new AnimationBuilder().playOnce("rex.whip"));
                    return PlayState.CONTINUE;
                case 23:
                    event.getController().setAnimation(new AnimationBuilder().playOnce("rex.stompl"));
                    return PlayState.CONTINUE;
                case 24:
                    event.getController().setAnimation(new AnimationBuilder().playOnce("rex.stompr"));
                    return PlayState.CONTINUE;
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(1);
        AnimationController<EntityTyrannosaurusRex> controller = new AnimationController<>(this, "controller", 1, this::predicate);
        AnimationController<EntityTyrannosaurusRex> attackController = new AnimationController<>(this, "attackController", 1, this::attackPredicate);

        data.addAnimationController(controller);
        data.addAnimationController(attackController);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_28134_, DifficultyInstance p_28135_, MobSpawnType p_28136_, @Nullable SpawnGroupData p_28137_, @Nullable CompoundTag p_28138_) {
        p_28137_ = super.finalizeSpawn(p_28134_, p_28135_, p_28136_, p_28137_, p_28138_);
        Level level = p_28134_.getLevel();
        if (level instanceof ServerLevel) {
            {
                this.setPersistenceRequired();
            }
        }
        return p_28137_;
    }

    public void checkDespawn() {
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

    protected PathNavigation createNavigation(Level p_33348_) {
        return new EntityTyrannosaurusRex.RexNavigation(this, p_33348_);
    }

    static class RexNavigation extends GroundPathNavigation {
        public RexNavigation(Mob p_33379_, Level p_33380_) {
            super(p_33379_, p_33380_);
        }

        protected PathFinder createPathFinder(int p_33382_) {
            this.nodeEvaluator = new EntityTyrannosaurusRex.RexNodeEvaluator();
            return new PathFinder(this.nodeEvaluator, p_33382_);
        }
    }

    static class RexNodeEvaluator extends WalkNodeEvaluator {
        protected BlockPathTypes evaluateBlockPathType(BlockGetter p_33387_, boolean p_33388_, boolean p_33389_, BlockPos p_33390_, BlockPathTypes p_33391_) {
            return p_33391_ == BlockPathTypes.LEAVES ? BlockPathTypes.OPEN : super.evaluateBlockPathType(p_33387_, p_33388_, p_33389_, p_33390_, p_33391_);
        }
    }


}