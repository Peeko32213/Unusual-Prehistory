package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.config.UnusualPrehistoryConfig;
import com.peeko32213.unusualprehistory.common.entity.msc.part.EntityPalaeophisPart;
import com.peeko32213.unusualprehistory.common.entity.msc.part.PalaeophisPartIndex;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseAquaticAnimal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.helper.HitboxHelper;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.util.UPMath;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class EntityPalaeophis extends EntityBaseDinosaurAnimal implements GeoAnimatable {
    private ResourceLocation DEEP_ONE_SHED = prefix("textures/entity/palaeophis_deep_head_shed.png");
    private ResourceLocation DEEP_ONE = prefix("textures/entity/palaeophis_deep_head.png");
    private ResourceLocation NORMAL = prefix("textures/entity/palaeophis_head.png");
    private ResourceLocation NORMAL_SHED = prefix("textures/entity/palaeophis_head_shed.png");
    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(EntityPalaeophis.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COMBAT_STATE = SynchedEntityData.defineId(EntityPalaeophis.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ENTITY_STATE = SynchedEntityData.defineId(EntityPalaeophis.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<UUID>> CHILD_UUID = SynchedEntityData.defineId(EntityPalaeophis.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> CHILD_ID = SynchedEntityData.defineId(EntityPalaeophis.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SHEDDING_TIME = SynchedEntityData.defineId(EntityPalaeophis.class, EntityDataSerializers.INT);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public final float[] ringBuffer = new float[64];
    public int ringBufferIndex = -1;
    private int sheddingCooldown = 0;
    private EntityPalaeophisPart[] parts;
    private float prevStrangleProgress = 0F;
    private float strangleProgress = 0F;
    private int strangleTimer = 0;
    private boolean isLandNavigator;
    private int swimTimer = -1000;
    private static final RawAnimation PALAEO_BITE = RawAnimation.begin().thenLoop("animation.palaophis_head.bite");
    private static final RawAnimation PALAEO_IDLE_TOUNGE = RawAnimation.begin().thenLoop("animation.palaophis_head.idle_tounge");


    public EntityPalaeophis(EntityType<? extends EntityBaseDinosaurAnimal> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
        this.moveControl = new EntityPalaeophis.MoveHelperController(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.ARMOR, 10.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6D)
                .add(Attributes.FOLLOW_RANGE, 12.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new EntityPalaeophis.DunkMeleeAttackGoal(this, 2F, true));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.25D, Ingredient.of(Items.CHICKEN, Items.COOKED_CHICKEN), false));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));

    }

    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }


    public void pushEntities() {
        final List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().expandTowards(0.2D, 0.0D, 0.2D));
        entities.stream().filter(entity -> !(entity instanceof EntityPalaeophisPart) && entity.isPushable()).forEach(entity -> entity.push(this));
    }


    @Override
    public boolean canAttack(LivingEntity entity) {
        boolean prev = super.canAttack(entity);
        if (prev && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().getUUID().equals(entity.getUUID()))) {
            return false;
        }
        return prev;
    }

    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(travelVector);
        }

    }

    protected PathNavigation createNavigation(Level p_27480_) {
        return new WaterBoundPathNavigation(this, p_27480_);
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.PALAEO_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.PALAEO_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.PALAEO_DEATH.get();
    }


    protected SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION_STATE, 0);
        this.entityData.define(COMBAT_STATE, 0);
        this.entityData.define(ENTITY_STATE, 0);
        this.entityData.define(CHILD_UUID, Optional.empty());
        this.entityData.define(CHILD_ID, -1);
        this.entityData.define(SHEDDING_TIME, 0);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.getChildId() != null) {
            compound.putUUID("ChildUUID", this.getChildId());
        }
        compound.putInt("ShedTime", getSheddingTime());
        compound.putInt("ShedCooldown", sheddingCooldown);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.hasUUID("ChildUUID")) {
            this.setChildId(compound.getUUID("ChildUUID"));
        }
        this.setSheddingTime(compound.getInt("ShedTime"));
        sheddingCooldown = compound.getInt("SheddingCooldown");
    }

    public int getSheddingTime() {
        return this.entityData.get(SHEDDING_TIME);
    }

    public void setSheddingTime(int shedtime) {
        this.entityData.set(SHEDDING_TIME, shedtime);
    }


    public Entity getChild() {
        UUID id = getChildId();
        if (id != null && !level().isClientSide) {
            return ((ServerLevel) level()).getEntity(id);
        }
        return null;
    }

    public void tick() {
        super.tick();
        if (this.ringBufferIndex < 0) {
            for (int i = 0; i < this.ringBuffer.length; ++i) {
                this.ringBuffer[i] = this.getYRot();
            }
        }
        this.ringBufferIndex++;
        if (this.ringBufferIndex == this.ringBuffer.length) {
            this.ringBufferIndex = 0;
        }
        this.ringBuffer[this.ringBufferIndex] = this.getYRot();
        if(!this.level().isClientSide){
        final int segments = 15;
        final Entity child = getChild();
        if (child == null) {
            LivingEntity partParent = this;
            parts = new EntityPalaeophisPart[segments];
            PalaeophisPartIndex partIndex = PalaeophisPartIndex.HEAD;
            Vec3 prevPos = this.position();
            for (int i = 0; i < segments; i++) {
                final float prevReqRot = calcPartRotation(i) + getYawForPart(i);
                final float reqRot = calcPartRotation(i + 1) + getYawForPart(i);
                EntityPalaeophisPart part = new EntityPalaeophisPart(UPEntities.PALAEOPHIS_PART.get(), this);
                part.setParent(partParent);
                part.copyDataFrom(this);
                part.setBodyIndex(i);
                part.setPartType(PalaeophisPartIndex.sizeAt(1 + i));
                if (partParent == this) {
                    this.setChildId(part.getUUID());
                    this.entityData.set(CHILD_ID, part.getId());
                }
                if (partParent instanceof EntityPalaeophisPart) {
                    ((EntityPalaeophisPart) partParent).setChildId(part.getUUID());
                }
                part.setPos(part.tickMultipartPosition(this.getId(), partIndex, prevPos, this.getXRot(), prevReqRot, reqRot, true));
                partParent = part;
                level().addFreshEntity(part);
                parts[i] = part;
                partIndex = part.getPartType();
                prevPos = part.position();
            }
        }
        if (shouldReplaceParts() && this.getChild() instanceof EntityPalaeophisPart) {
            parts = new EntityPalaeophisPart[segments];
            parts[0] = (EntityPalaeophisPart) this.getChild();
            this.entityData.set(CHILD_ID, parts[0].getId());
            int i = 1;
            while (i < parts.length && parts[i - 1].getChild() instanceof EntityPalaeophisPart) {
                parts[i] = (EntityPalaeophisPart) parts[i - 1].getChild();
                i++;
            }
        }
        PalaeophisPartIndex partIndex = PalaeophisPartIndex.HEAD;
        Vec3 prev = this.position();
        float xRot = this.getXRot();
        for (int i = 0; i < segments; i++) {
            if (this.parts[i] != null) {
                final float prevReqRot = calcPartRotation(i) + getYawForPart(i);
                final float reqRot = calcPartRotation(i + 1) + getYawForPart(i);
                parts[i].setStrangleProgress(this.strangleProgress);
                parts[i].copyDataFrom(this);
                prev = parts[i].tickMultipartPosition(this.getId(), partIndex, prev, xRot, prevReqRot, reqRot, true);
                partIndex = parts[i].getPartType();
                xRot = parts[i].getXRot();
            }
        }

        if (isInWater()) swimTimer = Math.max(swimTimer + 1, 0);
        else swimTimer = Math.min(swimTimer - 1, 0);
    }
        if (sheddingCooldown >= 0) {
            sheddingCooldown--;
        }

        if (this.getSheddingTime() >= 0) {
            int shedTime = getSheddingTime();
            setSheddingTime(shedTime - 1);

            if (shedTime == 0) {
                // The snake was in "shed mode," and the shedding time has expired.
                // Reset sheddingCooldown for the next shedding event.
                sheddingCooldown = this.getRandom().nextInt(6000) + 1500;
            }
            return;
        }

        if (!this.level().isClientSide && this.isAlive() && this.getSheddingTime() < 0 && sheddingCooldown < 0) {
            // The snake is not in "shed mode," so let's start shedding.
            this.spawnAtLocation(UPItems.PALAEO_SKIN.get(), 1);

            // Set the shedding time for the snake (how long it will be in "shed mode").
            this.setSheddingTime(this.getRandom().nextInt(1000) + 1500);

        }


    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }

    private float calcPartRotation(int i) {
        final float f = 1 - (this.strangleProgress * 0.2F);
        final float strangleIntensity = (float) (Mth.clamp(strangleTimer * 3, 0, 100F) * (1.0F + 0.2F * Math.sin(0.15F * strangleTimer)));
        //change this value to increase the sway of the snake
        float snakeSway = 25;
        //if(!this.isInWater()){
        //    snakeSway = 5;
        //    if(i <= 5){
        //        return 10 * i;
        //    }
//
        //    if(i > 5 && i < 10){
        //        return 15*i;
        //    }
//
        //    if(i >= 10){
        //        return 20*i;
        //    }
        //}


        return (float) (snakeSway * -Math.sin(this.walkDist * 3 - (i))) * f + this.strangleProgress * 0.2F * i * strangleIntensity;
    }


    @Override
    public boolean canCollideWith(Entity pEntity) {
        return true;
    }

    private boolean shouldReplaceParts() {
        if (parts == null || parts[0] == null)
            return true;

        for (int i = 0; i < 7; i++) {
            if (parts[i] == null) {
                return true;
            }
        }

        return false;
    }

    private float getYawForPart(int i) {
        return this.getRingBuffer(4 + i * 2, 1.0F);
    }

    public float getRingBuffer(int bufferOffset, float partialTicks) {
        if (this.isDeadOrDying()) {
            partialTicks = 0.0F;
        }

        partialTicks = 1.0F - partialTicks;
        final int i = this.ringBufferIndex - bufferOffset & 63;
        final int j = this.ringBufferIndex - bufferOffset - 1 & 63;
        final float d0 = this.ringBuffer[i];
        final float d1 = this.ringBuffer[j] - d0;
        return Mth.wrapDegrees(d0 + d1 * partialTicks);
    }

    public boolean isShedding() {
        return this.getSheddingTime() >= 0;
    }

    @Nullable
    public UUID getChildId() {
        return this.entityData.get(CHILD_UUID).orElse(null);
    }

    public void setChildId(@Nullable UUID uniqueId) {
        this.entityData.set(CHILD_UUID, Optional.ofNullable(uniqueId));
    }

    public int getMaxHeadXRot() {
        return 1;
    }

    public int getMaxHeadYRot() {
        return 1;
    }

    protected <E extends EntityPalaeophis> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        int animState = this.getAnimationState();
        if (this.isFromBook()) {
            return PlayState.CONTINUE;
        }
        switch (animState) {

            case 21:
                event.setAndContinue(PALAEO_BITE);
                break;
            default:
                if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F)) {
                    event.setAndContinue(PALAEO_IDLE_TOUNGE);
                    event.getController().setAnimationSpeed(0.6D);
                    return PlayState.CONTINUE;
                }
                if (!this.isInWater()) {
                    event.setAndContinue(PALAEO_IDLE_TOUNGE);
                    event.getController().setAnimationSpeed(0.3D);
                    return PlayState.CONTINUE;
                } else {
                    event.setAndContinue(PALAEO_IDLE_TOUNGE);
                    return PlayState.CONTINUE;
                }

        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.hasCustomName();
    }

    public boolean removeWhenFarAway(double d) {
        return !this.hasCustomName();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return null;
    }

    @Override
    protected int getKillHealAmount() {
        return 0;
    }

    @Override
    protected boolean canGetHungry() {
        return false;
    }

    @Override
    protected boolean hasTargets() {
        return false;
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
        return null;
    }

    static class MoveHelperController extends MoveControl {
        private final EntityPalaeophis dolphin;

        public MoveHelperController(EntityPalaeophis dolphinIn) {
            super(dolphinIn);
            this.dolphin = dolphinIn;
        }

        public void tick() {
            if (this.dolphin.isInWater()) {
                this.dolphin.setDeltaMovement(this.dolphin.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }

            if (this.operation == MoveControl.Operation.MOVE_TO && !this.dolphin.getNavigation().isDone()) {
                final double d0 = this.wantedX - this.dolphin.getX();
                final double d1 = this.wantedY - this.dolphin.getY();
                final double d2 = this.wantedZ - this.dolphin.getZ();
                final double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d3 < (double) 2.5000003E-7F) {
                    this.mob.setZza(0.0F);
                } else {
                    float f = (float) (Mth.atan2(d2, d0) * UPMath.oneEightyDividedByFloatPi) - 90.0F;
                    this.dolphin.setYRot(this.rotlerp(this.dolphin.getYRot(), f, 10.0F));
                    this.dolphin.yBodyRot = this.dolphin.getYRot();
                    this.dolphin.yHeadRot = this.dolphin.getYRot();
                    float f1 = (float) (this.speedModifier * this.dolphin.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    if (this.dolphin.isInWater() || this.dolphin.isInLava()) {
                        this.dolphin.setSpeed(f1 * 0.02F);
                        float f2 = -((float) (Mth.atan2(d1, Mth.sqrt((float) (d0 * d0 + d2 * d2))) * UPMath.oneEightyDividedByFloatPi));
                        f2 = Mth.clamp(Mth.wrapDegrees(f2), -85.0F, 85.0F);
                        this.dolphin.setDeltaMovement(this.dolphin.getDeltaMovement().add(0.0D, (double) this.dolphin.getSpeed() * d1 * 0.6D, 0.0D));
                        this.dolphin.setXRot(this.rotlerp(this.dolphin.getXRot(), f2, 1.0F));
                        final float f3 = Mth.cos(this.dolphin.getXRot() * UPMath.piDividedBy180);
                        final float f4 = Mth.sin(this.dolphin.getXRot() * UPMath.piDividedBy180);
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

    static class DunkMeleeAttackGoal extends Goal {

        protected final EntityPalaeophis mob;
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


        public DunkMeleeAttackGoal(EntityPalaeophis p_i1636_1_, double p_i1636_2_, boolean p_i1636_4_) {
            this.mob = p_i1636_1_;
            this.speedModifier = p_i1636_2_;
            this.followingTargetEvenIfNotSeen = p_i1636_4_;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
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
                default:
                    this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                    this.ticksUntilNextAttack = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                    this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    this.doMovement(target, distance);
                    this.checkForCloseRangeAttack(distance, reach);
                    break;

            }
        }

        protected void doMovement(LivingEntity livingentity, Double d0) {


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


        protected void checkForCloseRangeAttack(double distance, double reach) {
            if (distance <= reach && this.ticksUntilNextAttack <= 0) {
                int r = this.mob.getRandom().nextInt(2048);
                if (r <= 600) {
                    this.mob.setAnimationState(21);
                }

            }
        }


        protected boolean getRangeCheck() {

            return
                    this.mob.distanceToSqr(this.mob.getTarget().getX(), this.mob.getTarget().getY(), this.mob.getTarget().getZ())
                            <=
                            1.8F * this.getAttackReachSqr(this.mob.getTarget());
        }


        protected void tickBiteAttack() {
            animTime++;
            if (animTime == 4) {
                preformBiteAttack();
            }
            if (animTime >= 8) {
                animTime = 0;
                if (this.getRangeCheck()) {
                    this.mob.setAnimationState(22);
                } else {
                    this.mob.setAnimationState(0);
                    this.resetAttackCooldown();
                    this.ticksUntilNextPathRecalculation = 0;
                }
            }
        }


        protected void preformBiteAttack() {
            Vec3 pos = mob.position();
            this.mob.playSound(UPSounds.PALAEO_BITE.get(), 0.1F, 1.0F);
            HitboxHelper.LargeAttackWithTargetCheck(this.mob.damageSources().mobAttack(mob), 10.0f, 0.2f, mob, pos, 5.0F, -Math.PI / 2, Math.PI / 2, -1.0f, 3.0f);
        }

        protected void resetAttackCooldown() {
            this.ticksUntilNextAttack = 0;
        }

        protected boolean isTimeToAttack() {
            return this.ticksUntilNextAttack <= 0;
        }

        protected int getTicksUntilNextAttack() {
            return this.ticksUntilNextAttack;
        }

        protected int getAttackInterval() {
            return 5;
        }

        protected double getAttackReachSqr(LivingEntity p_179512_1_) {
            return (double) (this.mob.getBbWidth() * 2.5F * this.mob.getBbWidth() * 1.8F + p_179512_1_.getBbWidth());
        }
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

    @Override
    @Nullable
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UPEntities.PALAEOPHIS.get().create(serverLevel);
    }

    public void killed() {
        this.heal(15);
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

    public static boolean checkSurfaceWaterDinoSpawnRules(EntityType<? extends WaterAnimal> pWaterAnimal, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        int i = pLevel.getSeaLevel();
        int j = i - 13;
        return pPos.getY() >= j && pPos.getY() <= i && pLevel.getFluidState(pPos.below()).is(FluidTags.WATER) && pLevel.getBlockState(pPos.above()).is(Blocks.WATER) && UnusualPrehistoryConfig.DINO_NATURAL_SPAWNING.get();
    }
}