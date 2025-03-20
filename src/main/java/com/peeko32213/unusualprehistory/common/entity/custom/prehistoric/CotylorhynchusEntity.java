package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.PrehistoricPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmoothGroundNavigation;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;

import java.util.List;

public class CotylorhynchusEntity extends PrehistoricEntity {

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.of(UPTags.COTY_FOOD);
    private static final Ingredient FERMENTATION_ITEMS = Ingredient.of(UPTags.COTY_FERMENTERS);
    private static final EntityDataAccessor<Boolean> FERMENTED = SynchedEntityData.defineId(CotylorhynchusEntity.class, EntityDataSerializers.BOOLEAN);

    // Movement animations
    private static final RawAnimation COTY_WALK = RawAnimation.begin().thenLoop("animation.cotylorhynchus.walk");
    private static final RawAnimation COTY_RUN = RawAnimation.begin().thenLoop("animation.cotylorhynchus.run");
    private static final RawAnimation COTY_SWIM = RawAnimation.begin().thenLoop("animation.cotylorhynchus.swim");

    // Idle animaitons
    private static final RawAnimation COTY_IDLE = RawAnimation.begin().thenLoop("animation.cotylorhynchus.idle");
    private static final RawAnimation COTY_GRAZE = RawAnimation.begin().thenPlay("animation.cotylorhynchus.graze_blend");
    private static final RawAnimation COTY_SIT = RawAnimation.begin().thenLoop("animation.cotylorhynchus.sit");
    private static final RawAnimation COTY_SLEEP = RawAnimation.begin().thenLoop("animation.cotylorhynchus.sleep");

    // Misc animations
    private static final RawAnimation COTY_GROG = RawAnimation.begin().thenPlay("animation.cotylorhynchus.grog_blend");

    private static final EntityDataAccessor<Boolean> IDLE_1_AC = SynchedEntityData.defineId(CotylorhynchusEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityAction COTY_IDLE_1_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper COTY_IDLE_1_STATE =
            StateHelper.Builder.state(IDLE_1_AC, "cotylorhynchus_graze")
                    .playTime(40)
                    .stopTime(100)
                    .entityAction(COTY_IDLE_1_ACTION)
                    .build();

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                COTY_IDLE_1_STATE.getName(), COTY_IDLE_1_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(COTY_IDLE_1_STATE, 10)
        );
    }

    @Override
    public boolean getAction() {
        return false;
    }

    @Override
    public void setAction(boolean action) {}

    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        SmartBodyHelper helper = new SmartBodyHelper(this);
        helper.bodyLagMoving = 0.75F;
        helper.bodyLagStill = 0.25F;
        return helper;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return new SmoothGroundNavigation(this, levelIn);
    }

    public CotylorhynchusEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.25F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 16.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.16D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(8, new PrehistoricPanicGoal(this, 1.5D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, TyrannosaurusEntity.class, 8.0F, 1.6D, 1.4D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, MajungasaurusEntity.class, 8.0F, 1.6D, 1.4D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0, 30));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
    }

    @Override
    protected int getKillHealAmount() {
        return 0;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob p_146744_) {
        return UPEntities.COTY.get().create(serverLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IDLE_1_AC, false);
        this.entityData.define(FERMENTED, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Fermented", this.isFermented());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setFermented(compound.getBoolean("Fermented"));
    }

    public void setFermented(boolean fermented) {
        this.entityData.set(FERMENTED, fermented);
    }

    public boolean isFermented() {
        return this.entityData.get(FERMENTED);
    }

    public boolean isFood(ItemStack stack) {
        return stack.is(UPTags.COTY_FOOD);
    }

    public boolean isFermentingFood(ItemStack stack) {
        return stack.is(UPTags.COTY_FERMENTERS);
    }

    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;

        if (isFermentingFood(itemstack) && !this.isFermented()) {

            if (!player.isCreative()) {
                itemstack.shrink(1);
            }
            int brewAmount = random.nextInt(0, 100);
            if (brewAmount >= 70) {
                this.playSound(SoundEvents.BREWING_STAND_BREW, 0.5F, 1.0F);
                triggerAnim("blend", "grog");
                this.setFermented(true);
            } else {
                this.playSound(this.getEatingSound(itemstack), 0.5F, 1.0F);
            }
            return InteractionResult.SUCCESS;
        }
        else if (item == UPItems.FLASK.get() && this.isFermented()) {
            if (!player.isCreative()) {
                itemstack.shrink(1);
            }
            if(!player.addItem(new ItemStack(UPItems.GROG.get()))){
                player.spawnAtLocation(UPItems.GROG.get());
            }
            this.setFermented(false);
            this.playSound(SoundEvents.BOTTLE_FILL, 0.5F, 1.0F);
            return InteractionResult.SUCCESS;
        }
        else if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {

            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            if(!this.level().isClientSide) {
                this.heal((float) this.getAttribute(Attributes.MAX_HEALTH).getValue() / 4);
            }

            this.playSound(this.getEatingSound(itemstack), 0.5F, 1.0F);
            this.level().broadcastEntityEvent(this, (byte) 7);
            this.gameEvent(GameEvent.EAT, this);
            return InteractionResult.SUCCESS;

        }
        else return InteractionResult.FAIL;
    }

    private void spawnFluidParticle(Level pLevel, double pStartX, double pEndX, double pStartZ, double pEndZ, double pPosY) {
        pLevel.addParticle(ParticleTypes.DRIPPING_HONEY, Mth.lerp(pLevel.random.nextDouble(), pStartX, pEndX), pPosY, Mth.lerp(pLevel.random.nextDouble(), pStartZ, pEndZ), 0.0D, 0.0D, 0.0D);
    }

    public void tick() {
        super.tick();
        if (this.isFermented() && this.random.nextFloat() < 0.05F) {
            for(int i = 0; i < this.random.nextInt(2) + 1; ++i) {
                this.spawnFluidParticle(this.level(), this.getX() - (double)1.6F, this.getX() + (double)1.6F, this.getZ() - (double)1.6F, this.getZ() + (double)1.6F, this.getY(0.8D));
            }
        }
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

    private void soundListener(SoundKeyframeEvent<CotylorhynchusEntity> event) {
        CotylorhynchusEntity cotylorhynchus = event.getAnimatable();
        if (cotylorhynchus.level().isClientSide) {
            if (event.getKeyframeData().getSound().equals("cotylorhynchus_burp")) {
                cotylorhynchus.level().playLocalSound(cotylorhynchus.getX(), cotylorhynchus.getY(), cotylorhynchus.getZ(), UPSounds.COTY_BURP.get(), cotylorhynchus.getSoundSource(), 1.0F, cotylorhynchus.getVoicePitch(), false);
            }
        }
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<CotylorhynchusEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);

        AnimationController<CotylorhynchusEntity> blend = new AnimationController<>(this, "blend", 5, this::predicate)
                .triggerableAnim("graze", COTY_GRAZE)
                .triggerableAnim("grog", COTY_GROG)
            ;
        blend.setSoundKeyframeHandler(this::soundListener);
        controllers.add(blend);
    }

    protected <E extends CotylorhynchusEntity> PlayState predicate(final AnimationState<E> event) {

        if (this.isFromBook()) {
            return event.setAndContinue(COTY_IDLE);
        }

        if (this.isInWater()) {
            event.setAndContinue(COTY_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        else if(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming() && !this.isInWater()){
            if(this.isSprinting()) {
                event.setAndContinue(COTY_RUN);
                event.getController().setAnimationSpeed(1.0F);
            } else {
                event.setAndContinue(COTY_WALK);
                event.getController().setAnimationSpeed(1.0F);
            }
            return PlayState.CONTINUE;
        }

        if (!this.isInWater()) {
            if (getBooleanState(IDLE_1_AC)) {
                if (this.isStillEnough() && !this.isFermented() && level().getBlockState(this.blockPosition().below()).is(UPTags.COTY_GRAZING_BLOCKS)) {
                    triggerAnim("blend", "graze");
                    return event.setAndContinue(COTY_IDLE);
                }
                else if(!this.isFermented() && level().getBlockState(this.blockPosition().below()).is(UPTags.COTY_GRAZING_BLOCKS)) {
                    triggerAnim("blend", "graze");
                    return PlayState.CONTINUE;
                }
                else return PlayState.CONTINUE;
            }
            return event.setAndContinue(COTY_IDLE);
        }
        return PlayState.CONTINUE;
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.COTY_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.COTY_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.COTY_DEATH.get();
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(SoundEvents.CAMEL_STEP, 1.0F, 1.0F);
    }
}