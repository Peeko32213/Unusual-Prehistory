package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityTameableBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.*;
import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.CustomFollower;
import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.SemiAquatic;
import com.peeko32213.unusualprehistory.common.entity.msc.util.navigator.SemiAquaticPathNavigation;
import com.peeko32213.unusualprehistory.common.entity.msc.util.navigator.WaterMoveController;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nonnull;

public class EntityArchelon extends EntityTameableBaseDinosaurAnimal implements SemiAquatic, CustomFollower {
    private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(EntityArchelon.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(EntityArchelon.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> CHILL_TIME = SynchedEntityData.defineId(EntityArchelon.class, EntityDataSerializers.INT);

    private static final RawAnimation ARCHELON_IDLE = RawAnimation.begin().thenLoop("animation.archelon.idle");
    private static final RawAnimation ARCHELON_WALK = RawAnimation.begin().thenLoop("animation.archelon.walk");
    private static final RawAnimation ARCHELON_SWIM_IDLE = RawAnimation.begin().thenLoop("animation.archelon.swim_idle");
    private static final RawAnimation ARCHELON_SWIM = RawAnimation.begin().thenLoop("animation.archelon.swim");
    private static final RawAnimation ARCHELON_BITE_BLEND = RawAnimation.begin().thenLoop("animation.archelon.bite_blend");
    private static final RawAnimation ARCHELON_RAMMING = RawAnimation.begin().thenLoop("animation.archelon.ramming");
    private static final RawAnimation ARCHELON_SHAKE = RawAnimation.begin().thenLoop("animation.archelon.shake");
    private static final RawAnimation ARCHELON_SPIN1 = RawAnimation.begin().thenLoop("animation.archelon.spin1");
    private static final RawAnimation ARCHELON_SPIN2 = RawAnimation.begin().thenLoop("animation.archelon.spin2");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public float prevSwimProgress;
    public float swimProgress;
    private int swimTimer = -1000;
    private boolean isLandNavigator;
    public float sitProgress;

    public EntityArchelon(EntityType<? extends EntityTameableBaseDinosaurAnimal> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
        switchNavigator(false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 60D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.ARMOR, 10D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FindWaterGoal(this));
        this.goalSelector.addGoal(8, new LeaveWaterGoal(this));
        this.goalSelector.addGoal(5, new SemiAquaticSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(5, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new CustomRideGoal(this, 3D));
        this.goalSelector.addGoal(3, new TameableFollowOwner(this, 1.2D, 5.0F, 2.0F, false));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
    }

    public boolean canBreatheUnderwater() {
        return true;
    }


    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new WaterMoveController(this, 1.1F);
            this.navigation = new SemiAquaticPathNavigation(this, level());
            this.isLandNavigator = false;
        }
    }

    public void tick() {
        super.tick();
        this.prevSwimProgress = swimProgress;

        final boolean ground = !this.isInWaterOrBubble();

        if (!ground && this.isLandNavigator) {
            switchNavigator(false);
        }
        if (ground && !this.isLandNavigator) {
            switchNavigator(true);
        }
        if (ground && swimProgress > 0) {
            swimProgress--;
        }
        if (!ground && swimProgress < 5F) {
            swimProgress++;
        }
        if (!this.level().isClientSide) {
            if (isInWater()) {
                swimTimer++;
            } else {
                swimTimer--;
            }
        }
        if (this.isOrderedToSit() && sitProgress < 5F) {
            sitProgress++;
        }
        if (!this.isOrderedToSit() && sitProgress > 0F) {

            sitProgress--;
        }
        if (this.getCommand() == 2 && !this.isVehicle()) {
            this.setOrderedToSit(true);
        } else {
            this.setOrderedToSit(false);
        }
        if (!this.level().isClientSide) {
            if (this.getChillTime() > 0) {
                this.setChillTime(this.getChillTime() - 1);
            } else if (this.shouldSwim()) {
                if (random.nextInt(this.isVehicle() ? 200 : 2000) == 0) {
                    this.setChillTime(100 + random.nextInt(500));
                }
            }
        }
    }

    public boolean isAlliedTo(Entity entityIn) {
        if (this.isTame()) {
            LivingEntity livingentity = this.getOwner();
            if (entityIn == livingentity) {
                return true;
            }
            if (entityIn instanceof TamableAnimal) {
                return ((TamableAnimal) entityIn).isOwnedBy(livingentity);
            }
            if (livingentity != null) {
                return livingentity.isAlliedTo(entityIn);
            }
        }

        return entityIn.is(this);
    }

    public void travel(Vec3 travelVector) {
        if (this.isAlive()) {
            LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();
            if (this.isVehicle() && livingentity != null) {
                this.setYRot(livingentity.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(livingentity.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;
                float f = livingentity.xxa * 0.5F;
                float f1 = livingentity.zza;
                if (f1 <= 0.0F) {
                    f1 *= 0.25F;
                }

                this.setSpeed(0.3F);
                super.travel(new Vec3((double) f, travelVector.y, (double) f1));
            }
            if (this.isEffectiveAi() && this.isInWater()) {
                this.moveRelative(this.getSpeed(), travelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
                if (this.getTarget() == null) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
                }
            }
            else {
                super.travel(travelVector);
            }
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(COMMAND, 0);
        this.entityData.define(SADDLED, Boolean.valueOf(false));
        this.entityData.define(CHILL_TIME, 0);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("SwimTimer", this.swimTimer);
        compound.putBoolean("Saddle", this.isSaddled());
        compound.putInt("Command", this.getCommand());
        compound.putInt("ChillTime", this.getChillTime());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.swimTimer = compound.getInt("SwimTimer");
        this.setSaddled(compound.getBoolean("Saddle"));
        this.setCommand(compound.getInt("TrikeCommand"));
        this.setChillTime(compound.getInt("ChillTime"));
    }

    public int getChillTime() {
        return this.entityData.get(CHILL_TIME);
    }

    public void setChillTime(int chillTime) {
        this.entityData.set(CHILL_TIME, chillTime);
    }

    public int getCommand() {
        return this.entityData.get(COMMAND).intValue();
    }

    public void setCommand(int command) {
        this.entityData.set(COMMAND, Integer.valueOf(command));
    }

    @Override
    protected void performAttack() {

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

    @Override
    public boolean shouldFollow() {
        return false;
    }

    @Override
    public boolean shouldEnterWater() {
        return !shouldLeaveWater() && swimTimer <= -1000;
    }

    public boolean shouldLeaveWater() {
        LivingEntity target = this.getTarget();
        if (target != null && !target.isInWater()) {
            return true;
        }
        return swimTimer > 600;
    }

    @Override
    public int getWaterSearchRange() {
        return 12;
    }

    @Override
    public boolean shouldStopMoving() {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    protected <E extends EntityArchelon> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.isFromBook()) {
            return PlayState.CONTINUE;
        }
        if (event.isMoving() && !this.isInWater() && !this.isSwimming()) {
            event.setAnimation(ARCHELON_WALK);
            event.getController().setAnimationSpeed(1.0D);
            return PlayState.CONTINUE;
        }
        if (event.isMoving() && this.isInWater() && random.nextInt(100) == 0)  {
            float rand = random.nextFloat();
            event.setAnimation(ARCHELON_SWIM);
            if (rand < 0.55F) {
                return event.setAndContinue(ARCHELON_SPIN1);
            }
            if (rand < 0.56F) {
                return event.setAndContinue(ARCHELON_SPIN2);
            }
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        if (isStillEnough() && random.nextInt(100) == 0 && !this.isSwimming()) {
            float rand = random.nextFloat();
            return event.setAndContinue(ARCHELON_IDLE);
        }
        if (isStillEnough() && random.nextInt(100) == 0 && !this.isSwimming()) {
            float rand = random.nextFloat();
            if (rand < 0.45F) {
                return event.setAndContinue(ARCHELON_SHAKE);
            }
            return  event.setAndContinue(ARCHELON_SWIM_IDLE);
        }
        return PlayState.CONTINUE;
    }


    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
    }

    private boolean isStillEnough() {
        return this.getDeltaMovement().horizontalDistance() < 0.05;
    }

    static class MoveController extends MoveControl {
        private final EntityArchelon archelon;

        public MoveController(EntityArchelon dolphinIn) {
            super(dolphinIn);
            this.archelon = dolphinIn;
        }

        public void tick() {
            float speed = (float) (this.speedModifier * 3 * archelon.getAttributeValue(Attributes.MOVEMENT_SPEED));
            if (!archelon.isVehicle()) {
                if (this.archelon.isChilling()) {
                    speed *= 0.5F;
                } else if (this.archelon.shouldSwim()) {
                    this.archelon.setDeltaMovement(this.archelon.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
                }
            }
            if (this.operation == Operation.MOVE_TO && (!this.archelon.getNavigation().isDone() || archelon.getControllingPassenger() != null)) {
                double lvt_1_1_ = this.wantedX - archelon.getX();
                double lvt_3_1_ = this.wantedY - archelon.getY();
                double lvt_5_1_ = this.wantedZ - archelon.getZ();

                double lvt_7_1_ = lvt_1_1_ * lvt_1_1_ + lvt_3_1_ * lvt_3_1_ + lvt_5_1_ * lvt_5_1_;
                if (lvt_7_1_ < 2.5F) {
                    this.archelon.setZza(0.0F);
                } else {
                    float lvt_9_1_ = (float) (Mth.atan2(lvt_5_1_, lvt_1_1_) * 57.2957763671875D) - 90.0F;
                    this.archelon.setYRot(this.rotlerp(this.archelon.getYRot(), lvt_9_1_, 5F));
                    this.archelon.setYHeadRot(this.rotlerp(this.archelon.getYHeadRot(), lvt_9_1_, 90.0F));
                    //BlockPos blockpos = this.mob.blockPosition();
                    //BlockState blockstate = this.mob.level().getBlockState(blockpos);
                    //VoxelShape voxelshape = blockstate.getCollisionShape(this.mob.level(), blockpos);
                    if (lvt_3_1_ >= 0 && archelon.horizontalCollision) {
                        archelon.setDeltaMovement(archelon.getDeltaMovement().add(0.0D, 0.5D, 0.0D));
                    } else {
                        archelon.setDeltaMovement(archelon.getDeltaMovement().add(0.0D, (double) archelon.getSpeed() * lvt_3_1_ * 0.6D, 0.0D));
                    }
                    if (archelon.shouldSwim()) {
                        archelon.setSpeed(speed * 0.03F);
                        float lvt_11_1_ = -((float) (Mth.atan2(lvt_3_1_, Mth.sqrt((float) (lvt_1_1_ * lvt_1_1_ + lvt_5_1_ * lvt_5_1_))) * 57.2957763671875D));
                        lvt_11_1_ = Mth.clamp(Mth.wrapDegrees(lvt_11_1_), -85.0F, 85.0F);
                        archelon.setXRot(this.rotlerp(archelon.getXRot(), lvt_11_1_, 25.0F));
                        float lvt_12_1_ = Mth.cos(archelon.getXRot() * 0.017453292F);
                        float lvt_13_1_ = Mth.sin(archelon.getXRot() * 0.017453292F);
                        archelon.zza = lvt_12_1_ * speed;
                        archelon.yya = -lvt_13_1_ * speed;
                    } else {
                        archelon.setSpeed(speed * 0.1F);
                    }

                }
            }else if(!archelon.level().getBlockState(this.archelon.blockPosition().above()).getFluidState().isEmpty() && archelon.getChillTime() <= 0){
                this.archelon.setDeltaMovement(this.archelon.getDeltaMovement().add(0.0D, -0.05D, 0.0D));
            }
        }
    }

    public boolean shouldSwim() {
        return getMaxFluidHeight() >= 0.1F || this.isInLava() || this.isInWaterOrBubble();
    }

    private double getMaxFluidHeight() {
        return Math.max(this.getFluidHeight(FluidTags.LAVA), this.getFluidHeight(FluidTags.WATER));
    }

    public boolean isChilling() {
        return this.getChillTime() > 0 && this.getMaxFluidHeight() <= this.getBbHeight() * 0.5F;
    }
}
