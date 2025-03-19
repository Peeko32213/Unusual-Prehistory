package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.MathHelpers;
import com.peeko32213.unusualprehistory.UnusualPrehistoryConfig;
import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.RandomStateGoal;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.CustomizableRandomSwimGoal;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxAttacks;
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
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;


public class GlobidensEntity extends PrehistoricEntity {
    //START of necessary IK shit

    public double prevYHeadRot;
    public double deltaYHeadRot;

    public Vec3 rightRefPoint;
    public Vec3 rightRefOffset = new Vec3(1, 0, 0);

    public Vec3 leftRefPoint;
    public Vec3 leftRefOffset = new Vec3(-1, 0, 0);

    public Vec3 upRefPoint;
    public Vec3 upRefOffset = new Vec3(0, -1, 0);

    public Vec3 downRefPoint;
    public Vec3 downRefOffset = new Vec3(0, 1, 0);


    public Vec3 nosePoint;
    public Vec3 tail0Point;
    public Vec3 tail1Point;
    public Vec3 tail2Point;
    public Vec3 tail3Point;


    //Offset to the points relative to their parent point
    public Vec3 noseOffset = new Vec3(0.0, -1, -3);
    public Vec3 tail0Offset = new Vec3(0.0, -1, 3);
    public Vec3 tail1Offset = new Vec3(0.0, -1, 3);
    //technically the second segment's bone position offset, but affects the segment before it
    public Vec3 tail2Offset = new Vec3(0.0, -1, 3);
    public Vec3 tail3Offset = new Vec3(0.0, -1, 3);
//x = side to side offset
//y = vert offset
//z = fore to back offset(pos is back)

    public double bodyPitch = 0;
    public double currentBodyPitch = 0;

    public double tail1Angle;
    public double tail2Angle;
    public double currentTail1Yaw = Mth.PI;
    public double currentTail2Yaw = Mth.PI;

    //Yaw starts at pi
    public double currentTail1Pitch = 0;
    public double currentTail2Pitch = 0;
    public double tail1Pitch;
    public double tail2Pitch;
    //END of necessary IK shit

    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(GlobidensEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COMBAT_STATE = SynchedEntityData.defineId(GlobidensEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ENTITY_STATE = SynchedEntityData.defineId(GlobidensEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> FROM_BOOK = SynchedEntityData.defineId(GlobidensEntity.class, EntityDataSerializers.BOOLEAN);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    // Movement animations
    private static final RawAnimation GLO_SWIM = RawAnimation.begin().thenLoop("animation.globidens.swim");
    private static final RawAnimation GLO_SWIM_SPRINT = RawAnimation.begin().thenLoop("animation.globidens.swim_fast");
    private static final RawAnimation GLO_FLOP = RawAnimation.begin().thenLoop("animation.globidens.beached");

    // Idle animations
    private static final RawAnimation GLO_IDLE = RawAnimation.begin().thenLoop("animation.globidens.idle");
    private static final RawAnimation GLO_LOOP = RawAnimation.begin().thenPlay("animation.globidens.loop");
    private static final RawAnimation GLO_YAWN = RawAnimation.begin().thenPlay("animation.globidens.yawn_blend");

    // Attack animations
    private static final RawAnimation GLO_BITE_1 = RawAnimation.begin().thenPlay("animation.globidens.attack_blend1");
    private static final RawAnimation GLO_BITE_2 = RawAnimation.begin().thenPlay("animation.globidens.attack_blend2");
    private static final RawAnimation GLO_SLAP = RawAnimation.begin().thenPlay("animation.globidens.tailsmack");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> IDLE_1_AC = SynchedEntityData.defineId(GlobidensEntity.class, EntityDataSerializers.BOOLEAN);

    // Idle actions
    private static final EntityAction GLO_IDLE_1_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper GLO_IDLE_1_STATE =
            StateHelper.Builder.state(IDLE_1_AC, "glo_yawn")
                    .playTime(40)
                    .stopTime(120)
                    .entityAction(GLO_IDLE_1_ACTION)
                    .build();

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                GLO_IDLE_1_STATE.getName(), GLO_IDLE_1_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(GLO_IDLE_1_STATE, 12)
        );
    }

    @Override
    public boolean getAction() {
        return false;
    }

    @Override
    public void setAction(boolean action) {}

    private int passiveFor = 0;

    public GlobidensEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
        this.moveControl = new SmoothSwimmingMoveControl(this, 360, 2, 0.02F, 0.1F, false);

        leftRefPoint = MathHelpers.rotateAroundCenterFlatDeg(this.position(), this.position().subtract(leftRefOffset), (double) -this.getYRot());
        rightRefPoint = MathHelpers.rotateAroundCenterFlatDeg(this.position(), this.position().subtract(rightRefOffset), (double) -this.getYRot());
        upRefPoint = MathHelpers.rotateAroundCenterFlatDeg(this.position(), this.position().subtract(upRefOffset), (double) -this.getYRot());
        downRefPoint = MathHelpers.rotateAroundCenterFlatDeg(this.position(), this.position().subtract(downRefOffset), (double) -this.getYRot());

        nosePoint = MathHelpers.rotateAroundCenterFlatDeg(this.position(), this.position().subtract(noseOffset), (double) -this.getYRot());
        tail0Point = MathHelpers.rotateAroundCenterFlatDeg(this.position(), this.position().subtract(tail0Offset), (double) -this.getYRot());
        tail1Point = MathHelpers.rotateAroundCenterFlatDeg(tail0Point, tail0Point.subtract(tail1Offset), (double) -this.getYRot());
        tail2Point = MathHelpers.rotateAroundCenterFlatDeg(tail1Point, tail1Point.subtract(tail2Offset), (double) -this.getYRot());
        tail3Point = MathHelpers.rotateAroundCenterFlatDeg(tail2Point, tail2Point.subtract(tail3Offset), (double) -this.getYRot());
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 60.0D)
            .add(Attributes.ATTACK_DAMAGE, 10.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
            .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(2, new RandomStateGoal<>(this));
        this.goalSelector.addGoal(1, new GlobidensEntity.GloMeleeAttackGoal(this, 2F, true));
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new CustomizableRandomSwimGoal(this, 1.0, 1, 70, 70, 2));
        this.targetSelector.addGoal(9, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 50, true, true, entity -> entity.getType().is(UPTags.GLO_TARGETS)));
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean canAttack(@NotNull LivingEntity entity) {
        boolean prev = super.canAttack(entity);
        if(prev && passiveFor > 0 && entity instanceof LivingEntity && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().getUUID().equals(entity.getUUID()))){
            return false;
        }
        return prev;
    }

    @Override
    protected int getKillHealAmount() {
        return 6;
    }

    public void travel(@NotNull Vec3 travelVector) {
        super.travel(travelVector);
    }
 
    protected @NotNull PathNavigation createNavigation(@NotNull Level p_27480_) {
        return new WaterBoundPathNavigation(this, p_27480_);
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.GLOBIDENS_IDLE.get();
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UPSounds.GLOBIDENS_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.GLOBIDENS_DEATH.get();
    }

    @Override
    public float getSoundVolume() {
        return 0.75F;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 150;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IDLE_1_AC, false);
        this.entityData.define(ANIMATION_STATE, 0);
        this.entityData.define(COMBAT_STATE, 0);
        this.entityData.define(ENTITY_STATE, 0);
        this.entityData.define(FROM_BOOK, false);
    }

    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }

    public void tick() {
        super.tick();

        if (this.isInWater()) {
            //START of IK
            //the entity rotations must be negativized because we want the points to be transformed relative to the entity

            tail1Angle = (MathHelpers.angleClamp(MathHelpers.getAngleForLinkTopDownFlat(this.tail1Point, this.tail0Point, this.tail2Point, this.leftRefPoint, this.rightRefPoint), Mth.PI * 0.75));
            tail2Angle = (MathHelpers.angleClamp(MathHelpers.getAngleForLinkTopDownFlat(this.tail2Point, this.tail1Point, this.tail3Point, this.leftRefPoint, this.rightRefPoint), Mth.PI * 0.75));

            bodyPitch = ((float) (Mth.PI * MathHelpers.angleFromYdiff(this.nosePoint, this.position(), this.tail0Point)));

            tail1Pitch = ((float) (Mth.PI * MathHelpers.angleFromYdiff(this.position(), this.tail0Point, this.tail1Point)));
            tail2Pitch = ((float) (Mth.PI * MathHelpers.angleFromYdiff(this.tail0Point, this.tail1Point, this.tail2Point)));

            nosePoint = MathHelpers.rotateAroundCenter3dDeg(this.position(), this.position().subtract(noseOffset), -this.getYRot(), -this.getXRot());
            tail0Point = MathHelpers.rotateAroundCenter3dDeg(this.position(), this.position().subtract(tail0Offset), -this.getYRot(), -this.getXRot());
            tail1Point = MathHelpers.rotateAroundCenter3dDeg(tail0Point, tail0Point.subtract(tail1Offset), (float) (-MathHelpers.angleTo(tail0Point, tail1Point).y - deltaYHeadRot*Mth.DEG_TO_RAD), -MathHelpers.angleTo(tail0Point, tail1Point).x);
            tail2Point = MathHelpers.rotateAroundCenter3dDeg(tail1Point, tail1Point.subtract(tail2Offset), -MathHelpers.angleTo(tail1Point, tail2Point).y, -MathHelpers.angleTo(tail1Point, tail2Point).x);
            tail3Point = MathHelpers.rotateAroundCenter3dDeg(tail2Point, tail2Point.subtract(tail3Offset), -MathHelpers.angleTo(tail2Point, tail3Point).y, -MathHelpers.angleTo(tail2Point, tail3Point).x);

            deltaYHeadRot = prevYHeadRot-this.getYHeadRot();
            prevYHeadRot = this.getYHeadRot();
            //this value is in degrees

            if (!this.level().isClientSide()) {
                ServerLevel llel = (ServerLevel) this.level();
                llel.sendParticles(ParticleTypes.BUBBLE_POP, (nosePoint.x), (nosePoint.y), (nosePoint.z), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                llel.sendParticles(ParticleTypes.BUBBLE_POP, (tail0Point.x), (tail0Point.y), (tail0Point.z), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                llel.sendParticles(ParticleTypes.BUBBLE_POP, (tail1Point.x), (tail1Point.y), (tail1Point.z), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                llel.sendParticles(ParticleTypes.BUBBLE_POP, (tail2Point.x), (tail2Point.y), (tail2Point.z), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                llel.sendParticles(ParticleTypes.BUBBLE_POP, (tail3Point.x), (tail3Point.y), (tail3Point.z), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }


            //side refs don't move vertically
            leftRefPoint = MathHelpers.rotateAroundCenterFlatDeg(this.position(), this.position().subtract(leftRefOffset), (double) -this.getYRot());
            rightRefPoint = MathHelpers.rotateAroundCenterFlatDeg(this.position(), this.position().subtract(rightRefOffset), (double) -this.getYRot());
            upRefPoint = MathHelpers.rotateAroundCenterFlatDeg(this.position(), this.position().subtract(upRefOffset), (double) -this.getYRot());
            downRefPoint = MathHelpers.rotateAroundCenterFlatDeg(this.position(), this.position().subtract(downRefOffset), (double) -this.getYRot());
            //END of IK
        }
    }

    public void aiStep() {
        super.aiStep();
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.hasCustomName();
    }

    public boolean removeWhenFarAway(double d) {
        return !this.hasCustomName();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted() && !this.isBaby()) {
            this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.25D);
        } else {
            this.setSprinting(false);
        }
        super.customServerAiStep();
    }

    static class MoveHelperController extends MoveControl {
        private final GlobidensEntity dolphin;

        public MoveHelperController(GlobidensEntity dolphinIn) {
            super(dolphinIn);
            this.dolphin = dolphinIn;
        }

        public void tick() {
            if (this.dolphin.isInWater()) {
                this.dolphin.setDeltaMovement(this.dolphin.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }

            if (this.operation == Operation.MOVE_TO && !this.dolphin.getNavigation().isDone()) {
                double d0 = this.wantedX - this.dolphin.getX();
                double d1 = this.wantedY - this.dolphin.getY();
                double d2 = this.wantedZ - this.dolphin.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d3 < (double) 2.5000003E-7F) {
                    this.mob.setZza(0.0F);
                } else {
                    float f = (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
                    this.dolphin.setYRot(this.rotlerp(this.dolphin.getYRot(), f, 10.0F));
                    this.dolphin.yBodyRot = this.dolphin.getYRot();
                    this.dolphin.yHeadRot = this.dolphin.getYRot();
                    float f1 = (float) (this.speedModifier * this.dolphin.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    if (this.dolphin.isInWater()) {
                        this.dolphin.setSpeed(f1 * 0.02F);
                        float f2 = -((float) (Mth.atan2(d1, Mth.sqrt((float) (d0 * d0 + d2 * d2))) * (double) (180F / (float) Math.PI)));
                        f2 = Mth.clamp(Mth.wrapDegrees(f2), -85.0F, 85.0F);
                        this.dolphin.setXRot(this.rotlerp(this.dolphin.getXRot(), f2, 5.0F));
                        float f3 = Mth.cos(this.dolphin.getXRot() * ((float) Math.PI / 180F));
                        float f4 = Mth.sin(this.dolphin.getXRot() * ((float) Math.PI / 180F));
                        this.dolphin.zza = f3 * f1;
                        this.dolphin.yya = -f4 * f1;
                    } else {
                        this.dolphin.setSpeed(f1 * 0.1F);
                    }

                }
            } else {
                this.dolphin.setSpeed(0.0F);
                this.dolphin.setXxa(0.0F);
                this.dolphin.setYya(0.0F);
                this.dolphin.setZza(0.0F);
            }
        }
    }

    static class GloMeleeAttackGoal extends Goal {

        protected final GlobidensEntity mob;
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

        public GloMeleeAttackGoal(GlobidensEntity p_i1636_1_, double p_i1636_2_, boolean p_i1636_4_) {
            this.mob = p_i1636_1_;
            this.speedModifier = p_i1636_2_;
            this.followingTargetEvenIfNotSeen = p_i1636_4_;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            long i = this.mob.level().getGameTime();

             {
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

            if ((livingentity == this.mob || livingentity == null) && (this.mob.getAnimationState() == 0)){
                this.mob.setAnimationState(0);
                this.mob.setTarget(null);
                return false;
            }

            if (!(this.mob.getAnimationState() == 0)){
                if (livingentity == null) {
                    this.mob.setTarget(this.mob);
                }
                return true;
            }

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
            this.animTime = 0;
            this.mob.setAnimationState(0);
        }

        public void stop() {
            LivingEntity livingentity = this.mob.getTarget();
            if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
                this.mob.setTarget(null);
            }
            this.mob.setAnimationState(0);
        }

        public void tick() {

            LivingEntity target = this.mob.getTarget();
            assert target != null;
            double distance = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
            double reach = this.getAttackReachSqr(target);
            int animState = this.mob.getAnimationState();
            Vec3 aim = this.mob.getLookAngle();
            Vec2 aim2d = new Vec2((float) (aim.x / (1 - Math.abs(aim.y))), (float) (aim.z / (1 - Math.abs(aim.y))));

            if (animState == 1) {
                tickBiteAttack();
            }
            if (animState == 2) {
                tickBiteAttack();
            }
            if (animState == 3) {
                tickSlapAttack();
            }
            else {
                this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                this.ticksUntilNextAttack = Math.max(this.animTime - 1, 0);
                this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
                this.doMovement(target, distance);
                this.checkForCloseRangeAttack(distance, reach);
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
                    this.mob.setAnimationState(1);
                }
                else if (r <= 1200) {
                    this.mob.setAnimationState(2);
                }
                else if (r <= 1600) {
                    this.mob.setAnimationState(3);
                }
            }
        }

        protected boolean getRangeCheck () {
            return this.mob.distanceToSqr(this.mob.getTarget().getX(), this.mob.getTarget().getY(), this.mob.getTarget().getZ()) <= 1.8F * this.getAttackReachSqr(this.mob.getTarget());
        }

        protected void tickSlapAttack () {
            animTime++;

            if (animTime <= 3) {
                this.mob.lookAt(this.mob.getTarget(), 100000, 100000);
                this.mob.yBodyRot = this.mob.yHeadRot;
            }

            if(animTime==15) {
                preformSlapAttack();
            }

            if(animTime>=38) {
                animTime=0;
                this.mob.setAnimationState(0);
                this.resetAttackCooldown();
                this.ticksUntilNextPathRecalculation = 0;
            }
        }

        protected void tickBiteAttack () {
            animTime++;

            if (animTime <= 3) {
                this.mob.lookAt(this.mob.getTarget(), 100000, 100000);
                this.mob.yBodyRot = this.mob.yHeadRot;
            }

            if(animTime==13) {
                preformBiteAttack();
            }

            if(animTime>=18) {
                animTime=0;
                this.mob.setAnimationState(0);
                this.resetAttackCooldown();
                this.ticksUntilNextPathRecalculation = 0;
            }
        }

        protected void preformSlapAttack () {
            Vec3 pos = mob.position();
            this.mob.playSound(UPSounds.DUNK_ATTACK.get(), 0.3F, 1.0F);
            HitboxAttacks.largeAttackWithTargetCheck(this.mob.damageSources().mobAttack(mob), (float) mob.getAttribute(Attributes.ATTACK_DAMAGE).getValue() + 2, 0.45f, mob, pos,  3.5F, -Math.PI/2, 15, -1.0f, 1.0f, false, true);
        }

        protected void preformBiteAttack () {
            Vec3 pos = mob.position();
            this.mob.playSound(UPSounds.DUNK_ATTACK.get(), 0.15F, 1.0F);
            HitboxAttacks.largeAttackWithTargetCheck(this.mob.damageSources().mobAttack(mob), (float) mob.getAttribute(Attributes.ATTACK_DAMAGE).getValue(), 0.15f, mob, pos,  2.0F, -Math.PI/2, Math.PI/2, -1.0f, 3.0f, false, true);
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
            return this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + p_179512_1_.getBbWidth();
        }
    }

    private void soundListener(SoundKeyframeEvent<GlobidensEntity> event) {
        GlobidensEntity globidens = event.getAnimatable();
        if (globidens.level().isClientSide) {
        }
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
        controllers.add(new AnimationController<>(this, "blend", 5, this::Controller)
                .triggerableAnim("yawn", GLO_YAWN));

        AnimationController<GlobidensEntity> attack = new AnimationController<>(this, "attackController", 5, this::attackPredicate)
                .triggerableAnim("attack_1", GLO_BITE_1)
                .triggerableAnim("attack_2", GLO_BITE_2)
                ;
        attack.setSoundKeyframeHandler(this::soundListener);
        controllers.add(attack);
    }

    protected <E extends GlobidensEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

        if (this.isFromBook()) {
            return event.setAndContinue(GLO_IDLE);
        }

        if(!this.isFromBook()) {
            if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F) && this.isInWater()) {
                if(this.isSprinting()){
                    event.setAndContinue(GLO_SWIM_SPRINT);
                    event.getController().setAnimationSpeed(1.0F);
                } else {
                    event.setAndContinue(GLO_SWIM);
                    event.getController().setAnimationSpeed(1.0F);
                }
                return PlayState.CONTINUE;
            }
            if (!this.isInWater()) {
                event.setAndContinue(GLO_FLOP);
                event.getController().setAnimationSpeed(2.0F);
                return PlayState.CONTINUE;
            }
            else if (this.isInWater()) {
                if (getBooleanState(IDLE_1_AC)) {
                    triggerAnim("blend", "yawn");
                    return PlayState.CONTINUE;
                }
                else {
                    event.setAndContinue(GLO_IDLE);
                }
                return PlayState.CONTINUE;
            }
        }
        return PlayState.CONTINUE;
    }

    protected <E extends GlobidensEntity> PlayState attackPredicate(final AnimationState<E> event) {
        int animState = this.getAnimationState();

        if (animState == 1) {
            event.setAnimation(GLO_BITE_1.begin().then("animation.globidens.attack_blend1", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }
        if (animState == 2) {
            event.setAnimation(GLO_BITE_2.begin().then("animation.globidens.attack_blend2", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }
        if (animState == 3) {
            event.setAnimation(GLO_SLAP.begin().then("animation.globidens.tailsmack", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }

    public void killed() {
        passiveFor = 2400 + random.nextInt(100, 1200);
        this.heal(15);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor p_28134_, @NotNull DifficultyInstance p_28135_, @NotNull MobSpawnType p_28136_, @Nullable SpawnGroupData p_28137_, @Nullable CompoundTag p_28138_) {
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
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel pLevel, @NotNull AgeableMob pOtherParent) {
        return null;
    }

    public static boolean checkSurfaceWaterDinoSpawnRules(EntityType<? extends GlobidensEntity> pWaterAnimal, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        int i = pLevel.getSeaLevel();
        int j = i - 13;
        return pPos.getY() >= j && pPos.getY() <= i && pLevel.getFluidState(pPos.below()).is(FluidTags.WATER) && pLevel.getBlockState(pPos.above()).is(Blocks.WATER) && UnusualPrehistoryConfig.DINO_NATURAL_SPAWNING.get();
    }
}
