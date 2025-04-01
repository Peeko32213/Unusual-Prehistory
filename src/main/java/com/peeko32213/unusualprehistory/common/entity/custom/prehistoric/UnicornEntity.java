package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.PrehistoricPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmoothGroundNavigation;
import com.peeko32213.unusualprehistory.core.other.tags.UPItemTags;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.List;
import java.util.UUID;

public class UnicornEntity extends PrehistoricEntity {

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.of(UPItemTags.UNICORN_FOOD_ITEMS);

    private static final EntityDataAccessor<Boolean> SKELETAL = SynchedEntityData.defineId(UnicornEntity.class, EntityDataSerializers.BOOLEAN);
    private UUID lastLightningBoltUUID;

    // Movement animations
    private static final RawAnimation UNICORN_WALK = RawAnimation.begin().thenLoop("animation.unicorn.walk");
    private static final RawAnimation UNICORN_RUN = RawAnimation.begin().thenLoop("animation.unicorn.run");
    private static final RawAnimation UNICORN_SWIM = RawAnimation.begin().thenLoop("animation.unicorn.swim");

    // Idle animations
    private static final RawAnimation UNICORN_IDLE = RawAnimation.begin().thenLoop("animation.unicorn.idle");

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of();
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of();
    }

    // Body control / navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        SmartBodyHelper helper = new SmartBodyHelper(this);
        if (this.isSprinting()) {
            helper.bodyLagMoving = 0.55F;
            helper.bodyLagStill = 0.35F;
        }
        else {
            helper.bodyLagMoving = 0.4F;
            helper.bodyLagStill = 0.3F;
        }
        return helper;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return new SmoothGroundNavigation(this, levelIn);
    }

    public UnicornEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.25F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 12.0D).add(Attributes.MOVEMENT_SPEED, 0.16D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(6, new PrehistoricPanicGoal(this, 1.5D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0F));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.25D);
        } else {
            this.setSprinting(false);
        }
        super.customServerAiStep();
    }

    // Mob interactions
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            if(!this.level().isClientSide) {
                this.level().broadcastEntityEvent(this, (byte) 7);
            }
            this.playSound(SoundEvents.GOAT_EAT, 0.5F, this.getVoicePitch());
            this.gameEvent(GameEvent.EAT, this);
            this.heal(4);
            return InteractionResult.SUCCESS;
        }
        if (itemstack.is(Items.BUCKET) && !this.isBaby()) {
            player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
            ItemStack result = ItemUtils.createFilledResult(itemstack, player, Items.MILK_BUCKET.getDefaultInstance());
            player.setItemInHand(hand, result);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (itemstack.is(Items.BOWL) && !this.isBaby()) {
            player.playSound(SoundEvents.MOOSHROOM_MILK, 1.0F, 1.0F);
            ItemStack result = ItemUtils.createFilledResult(itemstack, player, Items.BEETROOT_SOUP.getDefaultInstance());
            player.setItemInHand(hand, result);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        else return InteractionResult.FAIL;
    }

    @Override
    protected int getKillHealAmount() {
        return 0;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob p_146744_) {
        return UPEntities.UNICORN.get().create(serverLevel);
    }

    public boolean isFood(ItemStack stack) {
        return stack.is(UPItemTags.UNICORN_FOOD_ITEMS);
    }

    protected <E extends UnicornEntity> PlayState controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        return PlayState.CONTINUE;
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.UNICORN_IDLE.get();
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        if(this.isSkeletal()) {
            return SoundEvents.SKELETON_HURT;
        }
        else return UPSounds.UNICORN_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        if(this.isSkeletal()) {
            return SoundEvents.SKELETON_DEATH;
        }
        else return UPSounds.UNICORN_DEATH.get();
    }

    protected void playStepSound(@NotNull BlockPos p_28301_, @NotNull BlockState p_28302_) {
        this.playSound(SoundEvents.WOLF_STEP, 0.25F, 1.0F);
    }

    @Override
    public float getSoundVolume() {
        if(this.isBaby()) {
            return 0.65F;
        }
        else return 1.0F;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 150;
    }

    // Save data
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("skeletal", this.isSkeletal());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSkeletal(compound.getBoolean("skeletal"));
    }

    // Synched data
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SKELETAL, false);
    }

    public boolean isSkeletal() {
        return this.entityData.get(SKELETAL);
    }

    private void setSkeletal(boolean isSkeletal) {
        this.entityData.set(SKELETAL, isSkeletal);
    }

    public void thunderHit(ServerLevel pLevel, LightningBolt pLightning) {
        UUID uuid = pLightning.getUUID();
        if (!uuid.equals(this.lastLightningBoltUUID)) {
            this.setSkeletal((!this.isSkeletal() || this.isSkeletal()) != this.isSkeletal());
            this.lastLightningBoltUUID = uuid;
            this.playSound(SoundEvents.SKELETON_DEATH, 2.0F, 1.0F);
        }
    }

    // Animation control
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<UnicornEntity> controller = new AnimationController<>(this, "controller", 8, this::predicate);
        controllers.add(controller);
    }

    protected <E extends UnicornEntity> PlayState predicate(final AnimationState<E> event) {

        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInWater() && !this.isSwimming()) {
            if (this.isSprinting()) {
                event.setAndContinue(UNICORN_RUN);
                event.getController().setAnimationSpeed(1.0D);
            } else {
                event.setAndContinue(UNICORN_WALK);
                event.getController().setAnimationSpeed(1.0D);
            }
            return PlayState.CONTINUE;
        }

        if (this.isInWater()) {
            return event.setAndContinue(UNICORN_SWIM);
        }

        if (this.isStillEnough() && !this.isInWater() && !this.isSwimming()) {
            return event.setAndContinue(UNICORN_IDLE);
        }
        return PlayState.CONTINUE;
    }
}
