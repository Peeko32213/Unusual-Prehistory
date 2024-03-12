package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseAquaticAnimal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityTameableBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.*;
import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.CustomFollower;
import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.SemiAquatic;
import com.peeko32213.unusualprehistory.common.entity.msc.util.navigator.SemiAquaticPathNavigation;
import com.peeko32213.unusualprehistory.common.entity.msc.util.navigator.WaterMoveController;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nonnull;

public class EntityHyneria extends EntityTameableBaseDinosaurAnimal implements CustomFollower, GeoEntity, SemiAquatic {
    private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(EntityHyneria.class, EntityDataSerializers.INT);
    private static final RawAnimation HYNERIA_SWIM_IDLE = RawAnimation.begin().thenLoop("animation.hyneria.swim_idle");
    private static final RawAnimation HYNERIA_SWIM = RawAnimation.begin().thenLoop("animation.hyneria.swim");
    private static final RawAnimation HYNERIA_SWIM_FAST = RawAnimation.begin().thenLoop("animation.hyneria.swim_fast");
    private static final RawAnimation HYNERIA_IDLE = RawAnimation.begin().thenLoop("animation.hyneria.idle");
    private static final RawAnimation HYNERIA_WALK = RawAnimation.begin().thenLoop("animation.hyneria.swim_idle");
    private static final RawAnimation HYNERIA_JUMP = RawAnimation.begin().thenLoop("animation.hyneria.swim");
    private static final RawAnimation HYNERIA_BASK = RawAnimation.begin().thenLoop("animation.hyneria.swim_fast");
    private static final RawAnimation HYNERIA_ATTACK_BLEND = RawAnimation.begin().thenLoop("animation.hyneria.idle");
    private static final RawAnimation HYNERIA_YAWN_BLEND = RawAnimation.begin().thenLoop("animation.hyneria.idle");

    public float prevSwimProgress;
    public float swimProgress;
    private int swimTimer = -1000;
    private boolean isLandNavigator;
    private int baskingTimer = 0;



    public EntityHyneria(EntityType<? extends EntityTameableBaseDinosaurAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
        switchNavigator(false);
        this.moveControl = new EntityHyneria.MoveHelperController(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.ARMOR, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.18D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.2F, false));
        this.goalSelector.addGoal(7, new FindWaterGoal(this));
        this.goalSelector.addGoal(7, new LeaveWaterGoal(this));
        this.goalSelector.addGoal(9, new SemiAquaticWaterAnimalSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(3, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new RandomSwimmingGoal(this, 1.8D, 10));
        this.goalSelector.addGoal(5, new HyneriaJumpGoal(this, 20));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 50, true, true, entity -> entity.getType().is(UPTags.HYNERIA_TARGETS)));

    }

    @Override
    protected void performAttack() {
    }

    public InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if (isFood(itemstack)) {
            if (!isTame()) {
                if(!this.level().isClientSide) {
                    int size = itemstack.getCount();
                    this.tame(player);
                    itemstack.shrink(size);
                }
                this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }
        if (isTame() && isOwnedBy(player)) {
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                if(!this.level().isClientSide) {
                    this.heal((float) itemstack.getFoodProperties(this).getNutrition());
                }
                this.gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;
            }
            else {
                if (!player.isShiftKeyDown() && !this.isBaby() && this.isSaddled()) {
                    if(!this.level().isClientSide) {
                        player.startRiding(this);
                    }
                    return InteractionResult.SUCCESS;
                } else {
                    this.setCommand((this.getCommand() + 1) % 3);

                    if (this.getCommand() == 3) {
                        this.setCommand(0);
                    }
                    player.displayClientMessage(Component.translatable("entity.unusualprehistory.all.command_" + this.getCommand(), this.getName()), true);
                    boolean sit = this.getCommand() == 2;
                    if (sit) {
                        this.setOrderedToSit(true);
                        return InteractionResult.SUCCESS;
                    } else {
                        this.setOrderedToSit(false);
                        return InteractionResult.SUCCESS;
                    }
                }
            }

        }
        return InteractionResult.PASS;
    }

    @javax.annotation.Nullable
    public LivingEntity getControllingPassenger() {
        for (Entity passenger : this.getPassengers()) {
            if (passenger instanceof Player) {
                return (Player) passenger;
            }
        }
        return null;
    }

    @Override
    protected void positionRider(Entity pPassenger, MoveFunction pCallback) {
        float ySin = Mth.sin(this.yBodyRot * ((float) Math.PI / 180F));
        float yCos = Mth.cos(this.yBodyRot * ((float) Math.PI / 180F));
        pPassenger.setPos(this.getX() + (double) (0.5F * ySin), this.getY() + this.getPassengersRidingOffset() + pPassenger.getMyRidingOffset() + 0.4F, this.getZ() - (double) (0.5F * yCos));
    }

    public double getPassengersRidingOffset() {
        if (this.isInWater()) {
            return 0.535;
        }
        else {
            return 2.35;
        }
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

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(COMMAND, 0);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Saddle", this.isSaddled());
        compound.putInt("Command", this.getCommand());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSaddled(compound.getBoolean("Saddle"));
        this.setCommand(compound.getInt("HyneriaCommand"));
    }

    public int getCommand() {
        return this.entityData.get(COMMAND).intValue();
    }

    public void setCommand(int command) {
        this.entityData.set(COMMAND, Integer.valueOf(command));
    }

    static class MoveHelperController extends MoveControl {
        private final EntityHyneria dolphin;

        public MoveHelperController(EntityHyneria dolphinIn) {
            super(dolphinIn);
            this.dolphin = dolphinIn;
        }

        public void tick() {
            if (this.dolphin.isInWater()) {
                this.dolphin.setDeltaMovement(this.dolphin.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }

            if (this.operation == MoveControl.Operation.MOVE_TO && !this.dolphin.getNavigation().isDone()) {
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

    @Override
    protected SoundEvent getAttackSound() {
        return null;
    }

    @Override
    protected int getKillHealAmount() {
        return 10;
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
        return UPTags.HYNERIA_TARGETS;
    }

    protected <E extends EntityHyneria> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.isFromBook()) {
            return PlayState.CONTINUE;
        }
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInWater() && !this.isSwimming()) {
            event.setAnimation(HYNERIA_WALK);
            event.getController().setAnimationSpeed(1.0D);
            return PlayState.CONTINUE;
        }
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInSittingPose() && this.isInWater()) {
            if (this.isSprinting()) {
                event.setAndContinue(HYNERIA_SWIM_FAST);
                event.getController().setAnimationSpeed(2.0D);
                return PlayState.CONTINUE;
            } else if (event.isMoving()) {
                event.setAndContinue(HYNERIA_SWIM);
                event.getController().setAnimationSpeed(1.0D);
                return PlayState.CONTINUE;
            }
        }
        if (isStillEnough() && !this.isSwimming() && random.nextInt(100) == 0) {
            float rand = random.nextFloat();
            if (rand < 0.3F) {
                return event.setAndContinue(HYNERIA_BASK);
            }
            return event.setAndContinue(HYNERIA_IDLE);
        }
        else if (this.isFallFlying()) {
            return event.setAndContinue(HYNERIA_JUMP);
        } else if (isStillEnough() && random.nextInt(100) == 0 && this.isInWaterOrBubble()) {
            float rand = random.nextFloat();
            if (rand < 0.3F) {
                return event.setAndContinue(HYNERIA_YAWN_BLEND);
            }
            event.setAndContinue(HYNERIA_SWIM_IDLE);
        }
        return PlayState.CONTINUE;
    }

    protected <E extends EntityHyneria> PlayState attackController(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.swinging && event.getController().getAnimationState().equals(AnimationController.State.PAUSED)) {
            return event.setAndContinue(HYNERIA_ATTACK_BLEND);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
        controllers.add(new AnimationController<>(this, "Attack", 0, this::attackController));
    }


    private boolean isStillEnough() {
        return this.getDeltaMovement().horizontalDistance() < 0.05;
    }

    @Override
    public boolean shouldFollow() {
        return this.getCommand() == 1;
    }

    @Override
    public boolean shouldEnterWater() {
        return true;
    }

    @Override
    public boolean shouldLeaveWater() {
        return true;
    }

    @Override
    public boolean shouldStopMoving() {
        return true;
    }

    @Override
    public int getWaterSearchRange() {
        return 50;
    }
}
