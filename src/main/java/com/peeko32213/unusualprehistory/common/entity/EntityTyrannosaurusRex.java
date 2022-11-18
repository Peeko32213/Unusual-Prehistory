package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.util.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.util.HitboxHelper;
import com.peeko32213.unusualprehistory.common.entity.util.LandCreaturePathNavigation;
import com.peeko32213.unusualprehistory.common.entity.util.NearestTargetAI;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
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
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
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

public class EntityTyrannosaurusRex extends Animal implements IAnimatable {

    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(EntityTyrannosaurusRex.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COMBAT_STATE = SynchedEntityData.defineId(EntityTyrannosaurusRex.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ENTITY_STATE = SynchedEntityData.defineId(EntityTyrannosaurusRex.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> EEPY = SynchedEntityData.defineId(EntityTyrannosaurusRex.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> PASSIVE = SynchedEntityData.defineId(EntityTyrannosaurusRex.class, EntityDataSerializers.INT);
    private final AnimationFactory factory = new AnimationFactory(this);
    private int roarAnimationTick;
    private int stunnedTick;

    private int passiveFor = 0;

    public EntityTyrannosaurusRex(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.maxUpStep = 1.0f;
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new LandCreaturePathNavigation(this, level);
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.ARMOR, 15.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 10.5D)
                .add(Attributes.ATTACK_KNOCKBACK, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 50.0D);

    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new EntityTyrannosaurusRex.RexMeleeAttackGoal(this, 2.0F, true));
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 15.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this) {
            public boolean canUse() {
                return !isEepy() && passiveFor == 0 ;
            }

        }));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, false, false, entity -> entity.getType().is(UPTags.REX_TARGETS)));

    }

    public InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        InteractionResult type = super.mobInteract(player, hand);
        if(item == UPItems.GOLDEN_SCAU.get() && this.hasEepy()) {
            if (!player.isCreative()) {
                itemstack.shrink(1);
            }
            this.heal(200);
            this.setTarget(null);
            this.passiveFor = 1000000000 + random.nextInt(1000000000);
            this.setEepy(false);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);

    }

    public void setEepy(boolean eepy) {
        this.entityData.set(EEPY, Boolean.valueOf(eepy));
    }

    public boolean hasEepy() {
        return this.entityData.get(EEPY).booleanValue();
    }



    public int getPassiveTicks() {
        return this.entityData.get(PASSIVE);
    }

    private void setPassiveTicks(int passiveTicks) {
        this.entityData.set(PASSIVE, passiveTicks);
    }

    public boolean isEepy() {
        return this.getHealth() <= this.getMaxHealth() / 4.0F;

    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("StunTick", this.stunnedTick);
        compound.putInt("PassiveFor", passiveFor);
        compound.putBoolean("Eepy", this.hasEepy());


    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.stunnedTick = compound.getInt("StunTick");
        passiveFor = compound.getInt("PassiveFor");
        this.setEepy(compound.getBoolean("Eepy"));

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION_STATE, 0);
        this.entityData.define(COMBAT_STATE, 0);
        this.entityData.define(ENTITY_STATE, 0);
        this.getEntityData().define(PASSIVE, 0);
        this.entityData.define(EEPY, false);
    }

    public void tick() {
        super.tick();
        if (this.passiveFor > 0) {
            passiveFor--;
        }
    }

    @Override
    public boolean hurt(DamageSource dmg, float i) {
        if (!level.isClientSide && dmg.getEntity() != null && this.getRandom().nextInt(4) == 0) {
            this.roar();
        }
        return super.hurt(dmg, i);
    }

    public boolean requiresUpdateEveryTick() {
        return false;
    }

    protected static int reducedTickDelay(int p_186074_) {
        return Mth.positiveCeilDiv(p_186074_, 2);
    }

    protected int adjustedTickDelay(int p_186072_) {
        return this.requiresUpdateEveryTick() ? p_186072_ : reducedTickDelay(p_186072_);
    }

    public void roar() {
        this.roarAnimationTick = Math.max(0, this.roarAnimationTick - 1);
        this.level.broadcastEntityEvent(this, (byte)10);
        if (isAggressive() & random.nextBoolean()) {
            if (this.roarAnimationTick == this.adjustedTickDelay(4)) {
                this.playSound(SoundEvents.RAVAGER_ROAR, 10.0F, 10.0F);
                final List<Entity> entities = level.getEntities(this, this.getBoundingBox());
                for (final Entity entity : entities) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity living = (LivingEntity) entity;
                        living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100));
                    }
                }
            }

        }
    }

    public int getRoarAnimationTick() {
        return this.roarAnimationTick;
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 10) {
            this.roarAnimationTick = 40;
        }
        if (pId == 39) {
            this.stunnedTick = 60;
        }
        else {
            super.handleEntityEvent(pId);
        }
    }

    public boolean isRoaring() {
        return this.roarAnimationTick > 0;
    }


    @Override
    public void customServerAiStep() {
        this.roarAnimationTick = this.getRoarAnimationTick();
        if (this.getMoveControl().hasWanted()) {
            this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.5D);
        } else {
            this.setSprinting(false);
        }
        super.customServerAiStep();
    }

    public boolean canDisableShield() {
        return true;
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
    public void aiStep() {
        super.aiStep();
        if (this.level.isClientSide) {
            this.roarAnimationTick = Math.max(0, this.roarAnimationTick - 1);
        }

        if (this.isEepy() ) {
            this.stunnedTick = 60;
            this.level.broadcastEntityEvent(this, (byte)39);
            this.setEepy(true);
            this.setAggressive(false);
            this.stunEffect();
        }

        if (this.passiveFor > 0) {
            this.setEepy(false);
        }

        if (this.isAggressive()) {
            if (this.horizontalCollision && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this)) {
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

    }

    private void stunEffect() {
        if (this.random.nextInt(6) == 0) {
            double d = this.getX() - (double)this.getBbWidth() * Math.sin(this.yBodyRot * ((float)Math.PI / 180)) + (this.random.nextDouble() * 0.3 - 0.3);
            double e = this.getY() + (double)this.getBbHeight() - 0.3;
            double f = this.getZ() + (double)this.getBbWidth() * Math.cos(this.yBodyRot * ((float)Math.PI / 180)) + (this.random.nextDouble() * 0.3 - 0.3);
            level.addParticle(ParticleTypes.EFFECT, true, this.getX(), this.getEyeY() + 0.2F, this.getZ(), 0, 0, 0);
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return UPEntities.REX.get().create(serverLevel);
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
            } else if (!livingentity.isAlive()) {
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
            this.mob.setAggressive(true);
            this.ticksUntilNextPathRecalculation = 0;
            this.ticksUntilNextAttack = 0;
            this.animTime = 0;
            this.mob.setAnimationState(0);

        }

        public void stop() {
            LivingEntity livingentity = this.mob.getTarget();
            if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
                this.mob.setTarget((LivingEntity) null);
            }
            this.mob.setAnimationState(0);
            this.mob.setAggressive(false);
        }

        public void tick() {


            LivingEntity target = this.mob.getTarget();
            double distance = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
            double reach = this.getAttackReachSqr(target);
            int animState = this.mob.getAnimationState();
            Vec3 aim = this.mob.getLookAngle();
            Vec2 aim2d = new Vec2((float) (aim.x / (1 - Math.abs(aim.y))), (float) (aim.z / (1 - Math.abs(aim.y))));


            switch (animState) {
                case 21:
                    tickBiteAttack();
                    break;
                case 22:
                    tickWhipAttack();
                    break;
                case 23:
                    tickStompLeftAttack();
                    break;
                case 24:
                    tickStompRightAttack();
                    break;
                default:
                    this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                    this.ticksUntilNextAttack = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                    this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    this.doMovement(target, distance);
                    this.checkForCloseRangeAttack(distance, reach);
                    break;

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
                int r = this.mob.getRandom().nextInt(2048);
                if (r <= 600) {
                    this.mob.setAnimationState(21);
                } else if (r <= 800) {
                    this.mob.setAnimationState(22);
                } else if (r <= 1400) {
                    this.mob.setAnimationState(23);
                } else {
                    this.mob.setAnimationState(24);
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
            if(animTime==4) {
                preformBiteAttack();
            }
            if(animTime>=8) {
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
            if(animTime==4) {
                preformWhipAttack();
            }
            if(animTime>=7) {
                animTime=0;

                this.mob.setAnimationState(0);
                this.resetAttackCooldown();
                this.ticksUntilNextPathRecalculation = 0;

            }

        }

        protected void tickStompLeftAttack () {
            animTime++;
            if(animTime==8) {
                preformStompAttack();
            }
            if(animTime>=14) {
                animTime=0;
                this.mob.setAnimationState(0);
                this.resetAttackCooldown();
                this.ticksUntilNextPathRecalculation = 0;
            }

        }

        protected void tickStompRightAttack () {
            animTime++;
            if(animTime==7) {
                preformStompAttack();
            }
            if(animTime>=13) {
                animTime=0;
                this.mob.setAnimationState(0);
                this.resetAttackCooldown();
                this.ticksUntilNextPathRecalculation = 0;
            }

        }


        protected void preformBiteAttack () {


            Vec3 pos = mob.position();
            this.mob.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 2.0f, 0.2f);
            HitboxHelper.LargeAttackWithTargetCheck(DamageSource.mobAttack(mob),20.0f, 0.1f, mob, pos,  7.0F, -Math.PI/2, Math.PI/2, -1.0f, 3.0f);

        }

        protected void preformWhipAttack () {


            Vec3 pos = mob.position();
            this.mob.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 2.0f, 0.2f);
            HitboxHelper.LargeAttackWithTargetCheck(DamageSource.mobAttack(mob),15.0f, 2.0f, mob, pos,  8.0F, -Math.PI/2, Math.PI/2, -1.0f, 3.0f);

        }

        protected void preformStompAttack () {


            Vec3 pos = mob.position();
            this.mob.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 2.0f, 0.2f);
            HitboxHelper.LargeAttackWithTargetCheck(DamageSource.mobAttack(mob),5.0f, 0.1f, mob, pos,  7.0F, -Math.PI/2, Math.PI/2, -1.0f, 3.0f);

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
            return 2;
        }

        protected double getAttackReachSqr(LivingEntity p_179512_1_) {
            return (double)(this.mob.getBbWidth() * 2.5F * this.mob.getBbWidth() * 1.8F + p_179512_1_.getBbWidth());
        }
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        int animState = this.getAnimationState();
        {
            switch (animState) {

                case 21:
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rex.bite", false));
                    break;
                case 22:
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rex.whip", false));
                    break;
                case 23:
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rex.stompleft", false));
                    break;
                case 24:
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rex.stompright", false));
                    break;
                default:
                    if (this.isEepy()) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rex.eepy", true));
                        event.getController().setAnimationSpeed(1.0F);
                    }
                    else if(!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F)){
                        if(this.isAggressive()) {
                            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rex.sprint", true));
                        }else {
                            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rex.walk", true));
                        }
                    }else{
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rex.idle", true));
                    }
                    break;

            }
        }
        return PlayState.CONTINUE;
    }



    private <E extends IAnimatable> PlayState roarPredicate(AnimationEvent<E> event) {
        if (this.isRoaring()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rex.roar", false));
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(1);
        AnimationController<EntityTyrannosaurusRex> controller = new AnimationController<>(this, "controller", 1, this::predicate);
        data.addAnimationController(new AnimationController<>(this, "roarController", 1, this::roarPredicate));
        data.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
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

}
