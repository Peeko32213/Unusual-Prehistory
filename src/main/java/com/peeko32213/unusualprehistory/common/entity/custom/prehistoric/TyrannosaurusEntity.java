package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.UnusualPrehistoryConfig;
import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.RandomStateGoal;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.TyrannosaurusAttackGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmoothGroundNavigation;
import com.peeko32213.unusualprehistory.core.other.tags.UPBlockTags;
import com.peeko32213.unusualprehistory.core.registry.*;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nonnull;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class TyrannosaurusEntity extends PrehistoricEntity {

    private static final EntityDataAccessor<Boolean> AGGRO = SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ENRAGED = SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ANGRY = SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> TACKLING = SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> STOMPING = SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SWIPING = SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ROARING = SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN);

    private int rageTime = 0;

    private static final EntityDataAccessor<Boolean> SKELETAL = SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private UUID lastLightningBoltUUID;

    private static final EntityDataAccessor<Boolean> EEPY = SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> PASSIVE = SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN);

    private final ServerBossEvent bossEvent = (ServerBossEvent) new ServerBossEvent(Component.translatable("bar.unusualprehistory.tyrannosaurus" , this.getDisplayName().getString()), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS).setDarkenScreen(true).setPlayBossMusic(true);

    // Body control / navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return new SmartBodyHelper(this);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return new SmoothGroundNavigation(this, levelIn);
    }

    public TyrannosaurusEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.25F);
    }

    // Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 300.0D)
            .add(Attributes.ARMOR, 10.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.2D)
            .add(Attributes.ATTACK_DAMAGE, 16.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
            .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    // Goals
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomStateGoal<>(this));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TyrannosaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D, 20));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(11, (new HurtByTargetGoal(this) {
            public boolean canUse() {
                return !isEepy() && !isBaby() && !isEepy() && !isPassive() && super.canUse();
            }
        }));
//        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 100, true, true, entity -> entity.getType().is(UPEntityTypeTags.TYRANNOSAURUS_TARGETS)) {
//            public boolean canUse() {
//                return !isEepy() && !isBaby() && !isPassive() && !isEepy() && !isSleeping() && super.canUse();
//            }
//        });
    }

    // Mob interactions
    public @NotNull InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if(item == UPItems.ADORNED_STAFF.get() && this.isEepy()) {
            itemstack.hurtAndBreak(1, player, (player1) -> {
                player1.broadcastBreakEvent(hand);
            });
            if(!this.level().isClientSide) {
                if(!this.isPassive()) {
                    this.spawnAtLocation(new ItemStack(UPItems.TYRANNO_SCALE.get(), random.nextInt(10) + 8), 2);
                    this.spawnAtLocation(new ItemStack(UPItems.TYRANNO_TOOTH.get(), random.nextInt(5) + 4), 2);
                }
                this.heal(300);
                this.level().broadcastEntityEvent(this, (byte) 18);
                this.playSound(UPSounds.TYRANNO_REVIVE.get(), 1.0F, 1.0F);
                this.setTarget(null);
                this.setEepy(false);
                this.setPassive(true);
                if (player.getStringUUID().equals("97399daf-aecd-45c9-a6f2-c18e9c9b18a2")) {
                    this.tame(player);
                }
                player.displayClientMessage(Component.translatable("entity.tyrannosaurus.revive.message" , this.getDisplayName().getString()).withStyle(ChatFormatting.GOLD), true);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected boolean isImmobile() {
        return super.isImmobile() && this.isVehicle();
    }

    @Override
    public boolean canBeLeashed(@NotNull Player p_21418_) {
        return this.isBaby();
    }

    // Eepy
    public void setEepy(boolean eepy) {
        this.entityData.set(EEPY, eepy);
    }
    public boolean isEepy() {
        return this.entityData.get(EEPY);
    }

    public boolean shouldBeEepy() {
        if (!this.isBaby() && !this.isPassive()) {
            return this.getHealth() <= this.getMaxHealth() / 12.0F;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean isNoAi() {
        return this.isEepy() || super.isNoAi();
    }

    public void setPassive(boolean passive) {
        this.entityData.set(PASSIVE, passive);
    }

    public boolean isPassive() {
        return this.entityData.get(PASSIVE);
    }

    // Synched data
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SKELETAL, false);

        this.entityData.define(SHAKE, false);
        this.entityData.define(SNIFF, false);
        this.entityData.define(ROAR, false);

        this.entityData.define(AGGRO, false);
        this.entityData.define(ANGRY, false);
        this.entityData.define(ENRAGED, false);
        this.entityData.define(EEPY, false);
        this.entityData.define(PASSIVE, false);

        this.entityData.define(TACKLING, false);
        this.entityData.define(STOMPING, false);
        this.entityData.define(SWIPING, false);
        this.entityData.define(ROARING, false);
    }

    // Save data
    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Aggro", this.isAggro());
        compound.putBoolean("Angry", this.isAngry());
        compound.putBoolean("Enraged", this.isEnraged());
        compound.putInt("RageTime", this.getRageTime());

        compound.putBoolean("Eepy", this.isEepy());
        compound.putBoolean("Passive", this.isPassive());

        compound.putBoolean("skeletal", this.isSkeletal());

        compound.putBoolean("Tackling", this.isTackling());
        compound.putBoolean("Stomping", this.isStomping());
        compound.putBoolean("TailSwipe", this.isSwiping());
        compound.putBoolean("Roaring", this.isRoaring());

        this.bossEvent.setName(Component.translatable("bar.unusualprehistory.tyrannosaurus" , this.getDisplayName().getString()));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setAggro(compound.getBoolean("Aggro"));
        this.setAngry(compound.getBoolean("Angry"));
        this.setEnraged(compound.getBoolean("Enraged"));
        this.setRageTime(compound.getInt("RageTime"));

        this.setEepy(compound.getBoolean("Eepy"));
        this.setPassive(compound.getBoolean("Passive"));

        this.setSkeletal(compound.getBoolean("skeletal"));

        this.setTackling(compound.getBoolean("Tackling"));
        this.setStomping(compound.getBoolean("Stomping"));
        this.setSwiping(compound.getBoolean("TailSwipe"));
        this.setRoaring(compound.getBoolean("Roaring"));

        if (this.hasCustomName()) {
            this.bossEvent.setName(Component.translatable("bar.unusualprehistory.tyrannosaurus" , this.getDisplayName().getString()));
        }
    }

    // Aggro
    public boolean isAggro() {return this.entityData.get(AGGRO);}
    public void setAggro(boolean isAggro) {this.entityData.set(AGGRO, isAggro);}

    // Rage tick
    public int getRageTime() {
        return this.rageTime;
    }
    public void setRageTime(int rageTime) {
        this.rageTime = rageTime;
    }

    // Stage 2
    public void setAngry(boolean anger) {
        this.entityData.set(ANGRY, anger);
    }
    public boolean isAngry() {
        return this.entityData.get(ANGRY);
    }

    // Rage
    public void setEnraged(boolean rage) {
        this.entityData.set(ENRAGED, rage);
    }
    public boolean isEnraged() {
        return this.entityData.get(ENRAGED);
    }

    // Tackle
    public void setTackling(boolean tackling) {
        this.entityData.set(TACKLING, tackling);
    }
    public boolean isTackling() {
        return this.entityData.get(TACKLING);
    }

    // Stomp
    public void setStomping(boolean stomping) {
        this.entityData.set(STOMPING, stomping);
    }
    public boolean isStomping() {
        return this.entityData.get(STOMPING);
    }

    // Tail Swipe
    public void setSwiping(boolean swiping) {
        this.entityData.set(SWIPING, swiping);
    }
    public boolean isSwiping() {
        return this.entityData.get(SWIPING);
    }

    // Roar
    public void setRoaring(boolean roar) {
        this.entityData.set(ROARING, roar);
    }
    public boolean isRoaring() {
        return this.entityData.get(ROARING);
    }

    public void tick() {
        super.tick();

        if (this.shouldBeEepy()) {
            this.setEepy(true);
            this.setTarget(null);
        }

        this.setEnraged(this.getTarget() instanceof Player);
        this.setAngry(this.getHealth() <= this.getMaxHealth() / 2);

        if (isEnraged()) {
            this.setRageTime(rageTime++);
        }
        else {
            this.setRageTime(rageTime = 0);
        }
    }

    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        if (this.isEnraged()) {
            this.bossEvent.addPlayer(serverPlayer);
        }
    }
    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        this.bossEvent.removePlayer(serverPlayer);
    }

    // Boss bar
    @Override
    public void customServerAiStep() {
        super.customServerAiStep();

        if (this.isEepy() && !this.bossEvent.getPlayers().isEmpty()) {
            this.bossEvent.removeAllPlayers();
        }
        if (this.isEepy()) return;

        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.shouldBeEepy()) {
            this.setEepy(true);
            this.setAggressive(false);
            this.setTarget(null);
        }

        // Break blocks when sprinting
        if (this.horizontalCollision && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), this) && this.isRunning() || this.isSprinting() && !this.isBaby()) {
            boolean flag = false;
            AABB axisalignedbb = this.getBoundingBox().inflate(0.2D);
            for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(axisalignedbb.minX), Mth.floor(axisalignedbb.minY), Mth.floor(axisalignedbb.minZ), Mth.floor(axisalignedbb.maxX), Mth.floor(axisalignedbb.maxY), Mth.floor(axisalignedbb.maxZ))) {
                BlockState blockstate = this.level().getBlockState(blockpos);
                if (blockstate.is(UPBlockTags.TYRANNO_BREAKABLES)) {
                    flag = this.level().destroyBlock(blockpos, true, this) || flag;
                }
            }
        }
    }

    @Override
    public boolean isPushable() {
        return !this.isEnraged();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypeTags.IS_PROJECTILE)) {
            amount *= 0.75F;
        }
        return super.hurt(source, amount);
    }

    @Override
    public int getMaxHeadYRot() {
        return 20;
    }

    @Override
    public int getMaxHeadXRot() {
        return 20;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        TyrannosaurusEntity tyrannosaurus = UPEntities.TYRANNOSAURUS.get().create(serverLevel);
        tyrannosaurus.setVariant(this.getVariant());
        return tyrannosaurus;
    }

    protected void ageBoundaryReached() {
        super.ageBoundaryReached();
        if (!this.isBaby() && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            this.spawnAtLocation(UPItems.TYRANNO_SCALE.get(), 1);
        }
    }

    // Collision config
    public boolean canBeCollidedWith() {
        return UnusualPrehistoryConfig.REX_COLLISION.get();
    }

    // Damage invulnerability
    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.CACTUS) || super.isInvulnerableTo(source);
    }

    // Sounds
    protected SoundEvent getAmbientSound() {
        if (this.isAngry()) {
            return UPSounds.TYRANNO_AGGRO.get();
        }
        else return UPSounds.TYRANNO_IDLE.get();
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        if(this.isSkeletal()) {
            return SoundEvents.SKELETON_HURT;
        }
        else return UPSounds.TYRANNO_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        if(this.isSkeletal()) {
            return SoundEvents.SKELETON_DEATH;
        }
        else return UPSounds.TYRANNO_DEATH.get();
    }

    protected void playStepSound(@NotNull BlockPos p_28301_, @NotNull BlockState p_28302_) {
        if (this.getAnimationState() == 23) {
            this.playSound(UPSounds.MAJUNGA_STEP.get(), 0.75F, 0.9F);
        }
        else this.playSound(UPSounds.MAJUNGA_STEP.get(), 0.4F, 0.9F);
    }

    @Override
    public float getSoundVolume() {
        if (this.isBaby()){
            return 0.75F;
        }
        else if (this.isAngry()) {
            return 1.5F;
        }
        else {
            return 1.25F;
        }
    }

    @Override
    public int getAmbientSoundInterval() {
        return 140;
    }

    // Variants
    public void determineVariant(int variantChange){
        if (variantChange <= 25) {
            this.setVariant(1);
        }
        else {
            this.setVariant(0);
        }
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

    // Movement animations
    private static final RawAnimation TYRANNO_SWIM = RawAnimation.begin().thenLoop("animation.tyrannosaurus.swim");
    private static final RawAnimation TYRANNO_CHARGE = RawAnimation.begin().thenLoop("animation.tyrannosaurus.run");
    private static final RawAnimation TYRANNO_WALK = RawAnimation.begin().thenLoop("animation.tyrannosaurus.walk");

    // Attack animations
    private static final RawAnimation TYRANNO_BITE1 = RawAnimation.begin().thenPlay("animation.tyrannosaurus.bite_blend1");
    private static final RawAnimation TYRANNO_BITE2 = RawAnimation.begin().thenPlay("animation.tyrannosaurus.bite_blend2");
    private static final RawAnimation TYRANNO_TAIL_SWIPE = RawAnimation.begin().thenPlay("animation.tyrannosaurus.whip");
    private static final RawAnimation TYRANNO_STOMP_L = RawAnimation.begin().thenPlay("animation.tyrannosaurus.stomp_left");
    private static final RawAnimation TYRANNO_STOMP_R = RawAnimation.begin().thenPlay("animation.tyrannosaurus.stomp_right");
    private static final RawAnimation TYRANNO_TACKLE = RawAnimation.begin().thenPlay("animation.tyrannosaurus.tackle");

    // Idle animations
    private static final RawAnimation TYRANNO_IDLE = RawAnimation.begin().thenLoop("animation.tyrannosaurus.idle");
    private static final RawAnimation TYRANNO_SHAKE = RawAnimation.begin().thenPlay("animation.tyrannosaurus.shake_blend");
    private static final RawAnimation TYRANNO_SNIFF = RawAnimation.begin().thenPlay("animation.tyrannosaurus.sniff_blend");
    private static final RawAnimation TYRANNO_ROAR = RawAnimation.begin().thenPlay("animation.tyrannosaurus.roar_blend");
    private static final RawAnimation TYRANNO_EEPY = RawAnimation.begin().thenLoop("animation.tyrannosaurus.knockout");
    private static final RawAnimation TYRANNO_SIT_START = RawAnimation.begin().thenPlay("animation.tyrannosaurus.sit_start");
    private static final RawAnimation TYRANNO_SIT = RawAnimation.begin().thenLoop("animation.tyrannosaurus.sit");
    private static final RawAnimation TYRANNO_SIT_END = RawAnimation.begin().thenPlay("animation.tyrannosaurus.sit_end");
    private static final RawAnimation TYRANNO_SLEEP = RawAnimation.begin().thenLoop("animation.tyrannosaurus.sleep");

    // Misc animations
    private static final RawAnimation TYRANNO_AGGRO = RawAnimation.begin().thenPlay("animation.tyrannosaurus.aggro_blend");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> SHAKE = SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SNIFF = SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ROAR = SynchedEntityData.defineId(TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN);

    // Starting predicates
    private static final Predicate<LivingEntity> TYRANNO_STARTING_PREDICATE = (e -> {
        if(e instanceof TyrannosaurusEntity entity) {
            return !entity.isRunning() && !entity.isSprinting() && !entity.isInWater() && !entity.getMoveControl().hasWanted();
        }
        return false;
    });

    // Idle actions
    private static final EntityAction TYRANNO_SHAKE_ACTION = new EntityAction(0, (e) -> {}, 1);
    private static final StateHelper TYRANNO_SHAKE_STATE =
            StateHelper.Builder.state(SHAKE, "tyrannosaurus_shake")
                    .playTime(90)
                    .stopTime(200)
                    .startingPredicate(TYRANNO_STARTING_PREDICATE)
                    .entityAction(TYRANNO_SHAKE_ACTION)
                    .build();

    private static final EntityAction TYRANNO_SNIFF_ACTION = new EntityAction(0, (e) -> {}, 1);
    private static final StateHelper TYRANNO_SNIFF_STATE =
            StateHelper.Builder.state(SNIFF, "tyrannosaurus_sniff")
                    .playTime(60)
                    .stopTime(160)
                    .startingPredicate(TYRANNO_STARTING_PREDICATE)
                    .affectsAI(true)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(TYRANNO_SNIFF_ACTION)
                    .build();

    private static final EntityAction TYRANNO_ROAR_ACTION = new EntityAction(0, (e) -> {}, 1);
    private static final StateHelper TYRANNO_ROAR_STATE =
            StateHelper.Builder.state(ROAR, "tyrannosaurus_roar")
                    .playTime(80)
                    .stopTime(220)
                    .startingPredicate(TYRANNO_STARTING_PREDICATE)
                    .entityAction(TYRANNO_ROAR_ACTION)
                    .build();

    // States
    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                TYRANNO_SHAKE_STATE.getName(), TYRANNO_SHAKE_STATE,
                TYRANNO_SNIFF_STATE.getName(), TYRANNO_SNIFF_STATE,
                TYRANNO_ROAR_STATE.getName(), TYRANNO_ROAR_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(TYRANNO_SHAKE_STATE, 11),
                WeightedState.of(TYRANNO_SNIFF_STATE, 12),
                WeightedState.of(TYRANNO_ROAR_STATE, 8)
        );
    }

    // Animation sounds
    private void soundListener(SoundKeyframeEvent<TyrannosaurusEntity> event) {
        TyrannosaurusEntity tyrannosaurus = event.getAnimatable();
        if (tyrannosaurus.level().isClientSide) {
            if (event.getKeyframeData().getSound().equals("tyrannosaurus_sniff")) {
                tyrannosaurus.level().playLocalSound(tyrannosaurus.getX(), tyrannosaurus.getY(), tyrannosaurus.getZ(), UPSounds.TYRANNO_SNIFF.get(), tyrannosaurus.getSoundSource(), 1.0F, tyrannosaurus.getVoicePitch(), false);
            }
            if (event.getKeyframeData().getSound().equals("tyrannosaurus_roar")) {
                tyrannosaurus.level().playLocalSound(tyrannosaurus.getX(), tyrannosaurus.getY(), tyrannosaurus.getZ(), UPSounds.TYRANNO_ROAR.get(), tyrannosaurus.getSoundSource(), 2.5F, tyrannosaurus.getVoicePitch() * 0.75F, false);
            }
            if (event.getKeyframeData().getSound().equals("tyrannosaurus_bite")) {
                tyrannosaurus.level().playLocalSound(tyrannosaurus.getX(), tyrannosaurus.getY(), tyrannosaurus.getZ(), UPSounds.TYRANNO_BITE.get(), tyrannosaurus.getSoundSource(), 1.25F, tyrannosaurus.getVoicePitch(), false);
            }
            if (event.getKeyframeData().getSound().equals("tyrannosaurus_stomp")) {
                tyrannosaurus.level().playLocalSound(tyrannosaurus.getX(), tyrannosaurus.getY(), tyrannosaurus.getZ(), UPSounds.TYRANNO_STOMP_ATTACK.get(), tyrannosaurus.getSoundSource(), 1.25F, tyrannosaurus.getVoicePitch(), false);
            }
            if (event.getKeyframeData().getSound().equals("tyrannosaurus_tail_swipe")) {
                tyrannosaurus.level().playLocalSound(tyrannosaurus.getX(), tyrannosaurus.getY(), tyrannosaurus.getZ(), UPSounds.TYRANNO_TAIL_SWIPE.get(), tyrannosaurus.getSoundSource(), 1.25F, tyrannosaurus.getVoicePitch(), false);
            }
            if (event.getKeyframeData().getSound().equals("tyrannosaurus_tackle")) {
                tyrannosaurus.level().playLocalSound(tyrannosaurus.getX(), tyrannosaurus.getY(), tyrannosaurus.getZ(), UPSounds.TYRANNO_TAIL_SWIPE.get(), tyrannosaurus.getSoundSource(), 1.25F, tyrannosaurus.getVoicePitch(), false);
            }
        }
    }

    // Animation control
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<TyrannosaurusEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);

        AnimationController<TyrannosaurusEntity> idle = new AnimationController<>(this, "idleController", 0, this::idlePredicate);
        idle.setSoundKeyframeHandler(this::soundListener);
        controllers.add(idle);

        AnimationController<TyrannosaurusEntity> attack = new AnimationController<>(this, "attackController", 5, this::attackPredicate);
        attack.setSoundKeyframeHandler(this::soundListener);
        controllers.add(attack);
    }

    protected <E extends TyrannosaurusEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.isEepy()) {
            event.setAndContinue(TYRANNO_EEPY);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }

        if(!this.isEepy()) {
            if (this.isInWater()) {
                event.setAndContinue(TYRANNO_SWIM);
                event.getController().setAnimationSpeed(1.0F);
                return PlayState.CONTINUE;
            }
            else if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming() && !this.isInWater() && !this.isTackling() && !this.isSwiping() && !this.isStomping()) {
                if (this.hasControllingPassenger()) {
                    if (this.getControllingPassenger().isSprinting()) {
                        event.setAndContinue(TYRANNO_WALK);
                        event.getController().setAnimationSpeed(1.65F);
                    }
                    else {
                        event.setAndContinue(TYRANNO_WALK);
                        event.getController().setAnimationSpeed(1.15F);
                    }
                }
                else {
                    if (this.isRunning() || this.isSprinting()) {
                        event.setAndContinue(TYRANNO_WALK);
                        event.getController().setAnimationSpeed(2.0F);
                    }
                    else {
                        event.setAndContinue(TYRANNO_WALK);
                        event.getController().setAnimationSpeed(1.0F);
                    }
                }
                return PlayState.CONTINUE;
            }

            if (!this.isInWater()) {
                return event.setAndContinue(TYRANNO_IDLE);
            }
        }
        return PlayState.CONTINUE;
    }

    // Idle animations
    protected <E extends TyrannosaurusEntity> PlayState idlePredicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (getBooleanState(SHAKE)) {
            event.getController().setAnimation(TYRANNO_SHAKE);
            return PlayState.CONTINUE;
        }
        if (getBooleanState(SNIFF)) {
            event.getController().setAnimation(TYRANNO_SNIFF);
            return PlayState.CONTINUE;
        }
        if (getBooleanState(ROAR)) {
            event.getController().setAnimation(TYRANNO_ROAR);
            return PlayState.CONTINUE;
        }
        event.getController().forceAnimationReset();
        return PlayState.STOP;
    }

    // Attack animations
    protected <E extends TyrannosaurusEntity> PlayState attackPredicate(final AnimationState<E> event) {
        int animState = this.getAnimationState();
        if (!this.isEepy()) {
            if (animState == 21) {
                event.setAndContinue(TYRANNO_BITE1);
                return PlayState.CONTINUE;
            }
            else if (animState == 22) {
                event.setAndContinue(TYRANNO_BITE2);
                return PlayState.CONTINUE;
            }
            else if (animState == 23) {
                event.setAndContinue(TYRANNO_STOMP_L);
                return PlayState.CONTINUE;
            }
            else if (animState == 24) {
                event.setAndContinue(TYRANNO_STOMP_R);
                return PlayState.CONTINUE;
            }
            else if (animState == 25) {
                event.setAndContinue(TYRANNO_TAIL_SWIPE);
                return PlayState.CONTINUE;
            }
            else if (animState == 26 && !this.isTackling()) {
                event.setAndContinue(TYRANNO_CHARGE);
                return PlayState.CONTINUE;
            }
            else if (this.isTackling()) {
                event.setAndContinue(TYRANNO_TACKLE);
                return PlayState.CONTINUE;
            }
            else if (animState == 27 && this.isRoaring()) {
                event.setAndContinue(TYRANNO_ROAR);
                return PlayState.CONTINUE;
            }
            else if (this.isAggro()) {
                event.setAndContinue(TYRANNO_AGGRO);
                return PlayState.CONTINUE;
            }
            else if (animState == 0 || this.isEepy()) {
                event.getController().forceAnimationReset();
                return PlayState.STOP;
            }
        }
        return PlayState.CONTINUE;
    }
}