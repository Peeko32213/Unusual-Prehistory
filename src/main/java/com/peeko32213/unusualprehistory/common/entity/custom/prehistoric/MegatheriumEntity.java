package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.CustomRideGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.PrehistoricFollowOwnerGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.ICustomFollower;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmoothGroundNavigation;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.List;

public class MegatheriumEntity extends PrehistoricEntity implements ICustomFollower {

    private Ingredient temptationItems;
    public float sitProgress;

    // Movement animations
    private static final RawAnimation MEGATHERIUM_WALK = RawAnimation.begin().thenLoop("animation.megatherium.walk");
    private static final RawAnimation MEGATHERIUM_RUN = RawAnimation.begin().thenLoop("animation.megatherium.run");
    private static final RawAnimation MEGATHERIUM_SWIM = RawAnimation.begin().thenLoop("animation.megatherium.swim");

    // Idle animations
    private static final RawAnimation MEGATHERIUM_IDLE = RawAnimation.begin().thenLoop("animation.megatherium.idle");
    private static final RawAnimation MEGATHERIUM_SIT = RawAnimation.begin().thenLoop("animation.megatherium.sit");
    private static final RawAnimation MEGATHERIUM_SIT_START = RawAnimation.begin().thenPlay("animation.megatherium.sit_start");
    private static final RawAnimation MEGATHERIUM_SIT_END = RawAnimation.begin().thenPlay("animation.megatherium.sit_end");
    private static final RawAnimation MEGATHERIUM_MBLEM_1 = RawAnimation.begin().thenLoop("animation.megatherium.mblem_blend1");
    private static final RawAnimation MEGATHERIUM_MBLEM_2 = RawAnimation.begin().thenLoop("animation.megatherium.mblem_blend2");
    private static final RawAnimation MEGATHERIUM_SCRATCH_1 = RawAnimation.begin().thenLoop("animation.megatherium.scratch_blend1");
    private static final RawAnimation MEGATHERIUM_SCRATCH_2 = RawAnimation.begin().thenLoop("animation.megatherium.scratch_blend2");
    private static final RawAnimation MEGATHERIUM_SHAKE = RawAnimation.begin().thenLoop("animation.megatherium.shake");
    private static final RawAnimation MEGATHERIUM_YAWN = RawAnimation.begin().thenLoop("animation.megatherium.yawn_blend");

    // Attack animations
    private static final RawAnimation MEGATHERIUM_ATTACK_1 = RawAnimation.begin().thenPlay("animation.megatherium.attack1");
    private static final RawAnimation MEGATHERIUM_ATTACK_2 = RawAnimation.begin().thenPlay("animation.megatherium.attack2");

    // Misc animations
    private static final RawAnimation MEGATHERIUM_BULLDOZE = RawAnimation.begin().thenLoop("animation.megatherium.bulldoze");

    // Body control / navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return new SmartBodyHelper(this);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return new SmoothGroundNavigation(this, levelIn);
    }

    public MegatheriumEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.25F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 80.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.16D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1.5D)
        ;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, getTemptationItems(), false));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(1, new CustomRideGoal(this, 2D));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(3, new PrehistoricFollowOwnerGoal(this, 1.2D, 5.0F, 2.0F, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D, 10));
    }

    private Ingredient getTemptationItems() {
        if (temptationItems == null)
            temptationItems = Ingredient.merge(Lists.newArrayList(
                    Ingredient.of(ItemTags.LEAVES)
            ));

        return temptationItems;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    public void tick() {
        super.tick();

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
    }


    //TODO add positionRider to base class so we dont have to do all this again we can just use a method to fetch the offset
    @Override
    protected void positionRider(Entity pPassenger, @NotNull MoveFunction pCallback) {
        float ySin = Mth.sin(this.yBodyRot * ((float) Math.PI / 180F));
        float yCos = Mth.cos(this.yBodyRot * ((float) Math.PI / 180F));
        pPassenger.setPos(this.getX() + (double) (0.5F * ySin), this.getY() + this.getPassengersRidingOffset() + pPassenger.getMyRidingOffset() + 0.4F, this.getZ() - (double) (0.5F * yCos));
    }

    //TODO add getPassengersRidingOffset to base class so we dont have to do all this again
    public double getPassengersRidingOffset() {
        if (this.isInWater()) {
            return 0.99;
        }
        else {
            return 2.6;
        }
    }

    //TODO add mobinteract to base class so we dont have to do all this again
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if (isFood(itemstack) && !isTame()) {

            this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
            this.level().broadcastEntityEvent(this, (byte) 6);

            if(random.nextBoolean()) {
                this.tame(player);
                this.level().broadcastEntityEvent(this, (byte) 7);
            }
            itemstack.shrink(1);

            return InteractionResult.SUCCESS;
        }
        if (isTame() && isOwnedBy(player)) {
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                if(!this.level().isClientSide) {
                    this.heal(6);
                }
                this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
                this.level().broadcastEntityEvent(this, (byte) 7);
                this.gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;
            } else if (itemstack.getItem() == Items.SADDLE && !this.isSaddled()) {
                this.usePlayerItem(player, hand, itemstack);
                this.playSound(SoundEvents.HORSE_SADDLE, 1.0F, 1.0F);
                this.setSaddled(true);
                return InteractionResult.SUCCESS;
            } else if (itemstack.getItem() == Items.SHEARS && this.isSaddled()) {
                this.playSound(SoundEvents.SHEEP_SHEAR, 1.0F, 1.0F);
                this.setSaddled(false);
                this.spawnAtLocation(Items.SADDLE);
                return InteractionResult.SUCCESS;
            } else {
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

    @Override
    public boolean shouldFollow() {
        return this.getCommand() == 1;
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(UPSounds.MAJUNGA_STEP.get(), 0.1F, 1.0F);
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

    @Override
    public void travel(Vec3 pos) {
        if (this.isAlive()) {
            LivingEntity livingentity = this.getControllingPassenger();

            if (this.isVehicle() && livingentity != null) {
                double d0 = 0.08D;
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
                this.setSpeed(0.1F);
                super.travel(new Vec3(f, pos.y, f1));

            } else {
                super.travel(pos);
            }
        }
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Saddle", this.isSaddled());
        compound.putInt("TrikeCommand", this.getCommand());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSaddled(compound.getBoolean("Saddle"));
        this.setCommand(compound.getInt("TrikeCommand"));
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.MEGATHER_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.MEGATHER_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.MEGATHER_DEATH.get();
    }

    @Override
    public float getSoundVolume() {
        if(this.isBaby()){
            return 0.75F;
        }
        else{
            return 1.0F;
        }
    }

    @Override
    protected int getKillHealAmount() {
        return 10;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return UPEntities.MEGATHERIUM.get().create(serverLevel);
    }

    protected <E extends MegatheriumEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.isFromBook()) {
            return event.setAndContinue(MEGATHERIUM_IDLE);
        }
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInWater() && !this.isInSittingPose() && !this.isSwimming()) {
            event.getController().setAnimationSpeed(1.0F);
            event.setAndContinue(MEGATHERIUM_WALK);
            return PlayState.CONTINUE;
        }
        if (this.isInWater() || this.isSwimming()) {
            event.getController().setAnimationSpeed(1.0F);
            event.setAndContinue(MEGATHERIUM_SWIM);
            return PlayState.CONTINUE;
        }

        if (this.isInSittingPose()) {
            event.getController().setAnimationSpeed(1.0F);
            event.setAndContinue(MEGATHERIUM_SIT);
            return PlayState.CONTINUE;
        }
        event.setAndContinue(MEGATHERIUM_IDLE);
        event.getController().setAnimationSpeed(1.0F);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return null;
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return List.of();
    }
}
