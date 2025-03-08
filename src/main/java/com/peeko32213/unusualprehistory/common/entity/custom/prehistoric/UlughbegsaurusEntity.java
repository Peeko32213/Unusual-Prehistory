package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.RandomStateGoal;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.base.TamableStatedPrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.goal.*;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IAttackEntity;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.ICustomFollower;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IVariantEntity;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

public class UlughbegsaurusEntity extends TamableStatedPrehistoricEntity implements GeoEntity, GeoAnimatable, IAttackEntity, IVariantEntity, ICustomFollower {

    private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(UlughbegsaurusEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(UlughbegsaurusEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> EATING_TIME = SynchedEntityData.defineId(UlughbegsaurusEntity.class, EntityDataSerializers.INT);
    public static final Logger LOGGER = LogManager.getLogger();

    private static final Map<DyeColor, float[]> COLORARRAY_BY_COLOR = Maps.<DyeColor, float[]>newEnumMap(Arrays.stream(DyeColor.values()).collect(Collectors.toMap((p_29868_) -> {return p_29868_;}, UlughbegsaurusEntity::createSheepColor)));

    private int attackCooldown;
    public static final int ATTACK_COOLDOWN = 30;

    public float eatProgress;
    public float sitProgress;

    // Movement animations
    private static final RawAnimation ULUGH_SPRINT = RawAnimation.begin().thenLoop("animation.ulughbegsaurus.sprint");
    private static final RawAnimation ULUGH_WALK = RawAnimation.begin().thenLoop("animation.ulughbegsaurus.walk");
    private static final RawAnimation ULUGH_SWIM = RawAnimation.begin().thenLoop("animation.ulughbegsaurus.swim");

    // Idle animations
    private static final RawAnimation ULUGH_IDLE = RawAnimation.begin().thenLoop("animation.ulughbegsaurus.idle");
    private static final RawAnimation ULUGH_SIT = RawAnimation.begin().thenLoop("animation.ulughbegsaurus.sit");
    private static final RawAnimation ULUGH_EAT = RawAnimation.begin().thenPlay("animation.ulughbegsaurus.eat");
    private static final RawAnimation ULUGH_SLAY = RawAnimation.begin().thenPlay("animation.ulughbegsaurus.slay");
    private static final RawAnimation ULUGH_SCRATCH = RawAnimation.begin().thenPlay("animation.ulughbegsaurus.scratch");
    private static final RawAnimation ULUGH_SHAKE = RawAnimation.begin().thenPlay("animation.ulughbegsaurus.shake");
    private static final RawAnimation ULUGH_VOCAL = RawAnimation.begin().thenPlay("animation.ulughbegsaurus.vocal");
    private static final RawAnimation ULUGH_SLEEP = RawAnimation.begin().thenLoop("animation.ulughbegsaurus.sleep");

    // Attack animations
    private static final RawAnimation ULUGH_BITE = RawAnimation.begin().thenPlay("animation.ulughbegsaurus.bite");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> IDLE_1_AC = SynchedEntityData.defineId(UlughbegsaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_2_AC = SynchedEntityData.defineId(UlughbegsaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_3_AC = SynchedEntityData.defineId(UlughbegsaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_4_AC = SynchedEntityData.defineId(UlughbegsaurusEntity.class, EntityDataSerializers.BOOLEAN);

    // Idle actions
    private static final EntityAction ULUGH_IDLE_1_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper ULUGH_IDLE_1_STATE =
            StateHelper.Builder.state(IDLE_1_AC, "ulughbegsaurus_slay")
                    .playTime(15)
                    .stopTime(50)
                    .entityAction(ULUGH_IDLE_1_ACTION)
                    .build();

    private static final EntityAction ULUGH_IDLE_2_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper ULUGH_IDLE_2_STATE =
            StateHelper.Builder.state(IDLE_2_AC, "ulughbegsaurus_scratch")
                    .playTime(40)
                    .stopTime(90)
                    .entityAction(ULUGH_IDLE_2_ACTION)
                    .build();

    private static final EntityAction ULUGH_IDLE_3_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper ULUGH_IDLE_3_STATE =
            StateHelper.Builder.state(IDLE_3_AC, "ulughbegsaurus_shake")
                    .playTime(40)
                    .stopTime(120)
                    .entityAction(ULUGH_IDLE_3_ACTION)
                    .build();

    private static final EntityAction ULUGH_IDLE_4_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper ULUGH_IDLE_4_STATE =
            StateHelper.Builder.state(IDLE_4_AC, "ulughbegsaurus_vocal")
                    .playTime(40)
                    .stopTime(140)
                    .entityAction(ULUGH_IDLE_4_ACTION)
                    .build();

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                ULUGH_IDLE_1_STATE.getName(), ULUGH_IDLE_1_STATE,
                ULUGH_IDLE_2_STATE.getName(), ULUGH_IDLE_2_STATE,
                ULUGH_IDLE_3_STATE.getName(), ULUGH_IDLE_3_STATE,
                ULUGH_IDLE_4_STATE.getName(), ULUGH_IDLE_4_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(ULUGH_IDLE_1_STATE, 14),
                WeightedState.of(ULUGH_IDLE_2_STATE, 12),
                WeightedState.of(ULUGH_IDLE_3_STATE, 11),
                WeightedState.of(ULUGH_IDLE_4_STATE, 10)
        );
    }

    @Override
    public boolean getAction() {
        return false;
    }

    @Override
    public void setAction(boolean action) {}

    public UlughbegsaurusEntity(EntityType<? extends TamableStatedPrehistoricEntity> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.25F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 60.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.2D)
            .add(Attributes.ATTACK_DAMAGE, 8.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.25D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new RandomStateGoal<>(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 2D, false));
        this.goalSelector.addGoal(2, new UlughbegsaurusEntity.IMeleeAttackGoal());
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(1, new CustomRideGoal(this, 2D));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0F, 30) {
                    @Override
                    public boolean canUse() {
                        if (this.mob.isVehicle()) {
                            return false;
                        } else {
                            if (!this.forceTrigger) {
                                if (this.mob.getNoActionTime() >= 100) {
                                    return false;
                                }
                                if (((UlughbegsaurusEntity) this.mob).isHungry()) {
                                    if (this.mob.getRandom().nextInt(60) != 0) {
                                        return false;
                                    }
                                } else {
                                    if (this.mob.getRandom().nextInt(30) != 0) {
                                        return false;
                                    }
                                }
                            }

                            Vec3 vec3d = this.getPosition();
                            if (vec3d == null) {
                                return false;
                            } else {
                                this.wantedX = vec3d.x;
                                this.wantedY = vec3d.y;
                                this.wantedZ = vec3d.z;
                                this.forceTrigger = false;
                                return true;
                            }
                        }
                    }
                }

        );
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(3, new TameableStatedFollowOwner(this, 1.2D, 5.0F, 2.0F, false));
    }

    protected void playStepSound(@NotNull BlockPos p_28301_, @NotNull BlockState p_28302_) {
        this.playSound(UPSounds.MAJUNGA_STEP.get(), 0.1F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.ULUGH_IDLE.get();
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UPSounds.ULUGH_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.ULUGH_DEATH.get();
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

    public boolean isFood(ItemStack stack) {
        return stack.is(UPTags.ULUGH_FOOD);
    }

    public boolean isEating() {
        return this.getEatingTime() > 0;
    }

    public int getEatingTime() {
        return this.entityData.get(EATING_TIME);
    }

    public void setEatingTime(int shaking) {
        this.entityData.set(EATING_TIME, shaking);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean shouldHurt;
        float damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float knockback = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (shouldHurt = target.hurt(this.damageSources().mobAttack(this), damage)) {
            if (knockback > 0.0f && target instanceof LivingEntity) {
                ((LivingEntity) target).knockback(knockback * 0.5f, Mth.sin(this.getYRot() * ((float) Math.PI / 180)), -Mth.cos(this.getYRot() * ((float) Math.PI / 180)));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
            }
            this.doEnchantDamageEffects(this, target);
            this.setLastHurtMob(target);
        }
        this.level().broadcastEntityEvent(this, (byte) 4);
        return shouldHurt;
    }

    public void performAttack() {
        if (this.level().isClientSide) {
            return;
        }
        this.setSwinging(true);
        for (Entity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.0D))) {
            if (!this.hasSwung() && this.isSaddled() && this.isTame() && this.hasControllingPassenger()) {
                if (entity instanceof UlughbegsaurusEntity ulughbegsaurus) {
                    if (ulughbegsaurus.isTame()) {
                        continue;
                    }
                }
                if (entity.is(Objects.requireNonNull(this.getControllingPassenger()))) {
                    continue;
                }

                entity.hurt(this.damageSources().mobAttack(this), 8.0F);
            }
        }
    }

    @Override
    public void afterAttack() {
        this.level().broadcastEntityEvent(this, (byte) 5);
        this.setSwinging(false);
    }

    @Override
    public int getMaxHeadYRot() {
        return 15;
    }

    @Override
    public int getMaxAttackCooldown() {
        return ATTACK_COOLDOWN;
    }

    @Override
    public int getAttackCooldown() {
        return attackCooldown;
    }

    @Override
    public void setAttackCooldown(int cooldown) {
        this.attackCooldown = cooldown;
    }

    public @NotNull InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if (isFood(itemstack) && !isTame()) {
            if(!this.level().isClientSide) {
                this.level().broadcastEntityEvent(this, (byte) 7);
                this.tame(player);
                itemstack.shrink(1);
                this.setEatingTime(50 + random.nextInt(30));
            }
            this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }
        if (isTame() && isOwnedBy(player)) {
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                if(!this.level().isClientSide) {
                    this.heal((float) Objects.requireNonNull(itemstack.getFoodProperties(this)).getNutrition());
                }
                this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
                this.level().broadcastEntityEvent(this, (byte) 7);
                this.gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;
            }
            else if (itemstack.getItem() == Items.SADDLE && !this.isSaddled()) {
                this.usePlayerItem(player, hand, itemstack);
                this.playSound(SoundEvents.HORSE_SADDLE, 1.0F, 1.0F);
                this.setSaddled(true);
                return InteractionResult.SUCCESS;
            }
            else if (itemstack.getItem() == Items.SHEARS && this.isSaddled()) {
                this.setSaddled(false);
                this.playSound(SoundEvents.SHEEP_SHEAR, 1.0F, 1.0F);
                this.spawnAtLocation(Items.SADDLE);
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

    public @NotNull SoundEvent getEatingSound(@NotNull ItemStack p_28540_) {
        return SoundEvents.GENERIC_EAT;
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Saddle", this.isSaddled());
        compound.putInt("UlughCommand", this.getCommand());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSaddled(compound.getBoolean("Saddle"));
        this.setCommand(compound.getInt("UlughCommand"));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IDLE_1_AC, false);
        this.entityData.define(IDLE_2_AC, false);
        this.entityData.define(IDLE_3_AC, false);
        this.entityData.define(IDLE_4_AC, false);
        this.entityData.define(EATING_TIME, 0);
        this.entityData.define(SADDLED, Boolean.FALSE);
        this.entityData.define(COMMAND, 0);
    }

    public boolean isSaddled() {
        return this.entityData.get(SADDLED);
    }

    public void setSaddled(boolean saddled) {
        this.entityData.set(SADDLED, saddled);
    }

    @Nullable
    public LivingEntity getControllingPassenger() {
        for (Entity passenger : this.getPassengers()) {
            if (passenger instanceof Player) {
                return (Player) passenger;
            }
        }
        return null;
    }

    @Override
    protected void positionRider(Entity pPassenger, @NotNull MoveFunction pCallback) {
        float ySin = Mth.sin(this.yBodyRot * ((float) Math.PI / 180F));
        float yCos = Mth.cos(this.yBodyRot * ((float) Math.PI / 180F));
        pPassenger.setPos(this.getX() + (double) (0.35F * ySin), this.getY() + this.getPassengersRidingOffset() + pPassenger.getMyRidingOffset() + 0.35F, this.getZ() - (double) (0.35F * yCos));
    }

    public double getPassengersRidingOffset() {
        return 1.85;
    }


    public void tick() {
        super.tick();

        if(attackCooldown > 0){
            attackCooldown--;
        }

        if (this.isOrderedToSit() && sitProgress < 5F) {
            sitProgress++;
        }
        if (!this.isOrderedToSit() && sitProgress > 0F) {
            sitProgress--;
        }
        if (this.isEating() && eatProgress < 5F) {
            eatProgress++;
        }
        if (!this.isEating() && eatProgress > 0F) {
            eatProgress--;
        }
        if (this.isEating()) {
            this.setEatingTime(this.getEatingTime() - 1);
            this.getNavigation().stop();
        }
        if (this.getCommand() == 2 && !this.isVehicle()) {
            this.setOrderedToSit(true);
        } else {
            this.setOrderedToSit(false);
        }
    }

    @Override
    public void travel(@NotNull Vec3 pos) {
        if (this.isAlive()) {
            LivingEntity livingentity = this.getControllingPassenger();
            if (this.isVehicle() && livingentity != null) {
                this.setYRot(livingentity.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(livingentity.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;
                float f = livingentity.xxa;
                float f1 = livingentity.zza;
                if (f1 <= 0.0F) {
                    f1 *= 0.25F;
                }
                if(Objects.requireNonNull(this.getControllingPassenger()).isSprinting()) {
                    this.setSpeed(((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 1.25F));
                } else {
                    this.setSpeed(((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.5F));
                }
                super.travel(new Vec3(f, pos.y, f1));
            } else {
                super.travel(pos);
            }
        }
    }

    public boolean canSprint() {
        return true;
    }

    @Override
    public float getStepHeight() {
        return 1.25F;
    }

    public boolean isAlliedTo(@NotNull Entity entityIn) {
        if (this.isTame()) {
            LivingEntity livingentity = this.getOwner();
            if (entityIn == livingentity) {
                return true;
            }
            if (entityIn instanceof TamableAnimal) {
                assert livingentity != null;
                return ((TamableAnimal) entityIn).isOwnedBy(livingentity);
            }
            if (livingentity != null) {
                return livingentity.isAlliedTo(entityIn);
            }
        }
        return entityIn.is(this);
    }

    public int getCommand() {
        return this.entityData.get(COMMAND);
    }

    public void setCommand(int command) {
        this.entityData.set(COMMAND, command);
    }

    public void determineVariant(int variantChange){
        if (variantChange <= 10) {
            this.setVariant(1);
        }
        else if (variantChange <= 30 && variantChange > 20) {
            this.setVariant(2);
        }
        else if (variantChange <= 40 && variantChange > 30) {
            this.setVariant(3);
        }
        else if (variantChange <= 50 && variantChange > 40) {
            this.setVariant(4);
        }
        else if (variantChange <= 60 && variantChange > 50) {
            this.setVariant(5);
        }
        else if (variantChange <= 70 && variantChange > 60) {
            this.setVariant(6);
        }
        else if (variantChange <= 80 && variantChange > 70) {
            this.setVariant(7);
        }
        else if (variantChange <= 90 && variantChange > 80) {
            this.setVariant(8);
        }
        else if (variantChange <= 100 && variantChange > 90) {
            this.setVariant(9);
        }
        else if (variantChange > 100) {
            this.setVariant(10);
        }
        else {
            this.setVariant(0);
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        int variantChange = this.random.nextInt(0, 100);
        this.determineVariant(variantChange);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    protected SoundEvent getAttackSound() {
        return UPSounds.ULUGH_BITE.get();
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
        return true;
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
        return UPTags.RAPTOR_TARGETS;
    }

    private void attack(LivingEntity entity) {
        entity.hurt(this.damageSources().mobAttack(this), 5.0F);
    }

    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getEntity();
            this.setOrderedToSit(false);
            if (entity != null && this.isTame() && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
                amount = (amount + 1.0F) / 3.0F;
            }
            return super.hurt(source, amount);
        }
    }

    @Override
    public ResourceLocation getVariantTexture() {
        return null;
    }

    @Override
    public boolean shouldFollow() {
        return this.getCommand() == 1;
    }

    class IMeleeAttackGoal extends MeleeAttackGoal {
        public IMeleeAttackGoal() {
            super(UlughbegsaurusEntity.this, 1.6D, true);
        }

        protected double getAttackReachSqr(LivingEntity p_25556_) {
            return this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 0.66F + p_25556_.getBbWidth();
        }

        @Override
        protected void checkAndPerformAttack(@NotNull LivingEntity enemy, double distToEnemySqr) {
            double d0 = this.getAttackReachSqr(enemy);
            if (distToEnemySqr <= d0 && this.getTicksUntilNextAttack() <= 0) {
                this.resetAttackCooldown();
                ((UlughbegsaurusEntity) this.mob).setHungry(false);
                ((UlughbegsaurusEntity) this.mob).attack(enemy);
                ((UlughbegsaurusEntity) this.mob).setTimeTillHungry(mob.getRandom().nextInt(300) + 300);
            }
        }
    }

    public static float[] getColorArray(DyeColor pDyeColor) {
        return COLORARRAY_BY_COLOR.get(pDyeColor);
    }

    private static float[] createSheepColor(DyeColor p_29866_) {
        if (p_29866_ == DyeColor.WHITE) {
            return new float[]{0.9019608F, 0.9019608F, 0.9019608F};
        } else {
            float[] afloat = p_29866_.getTextureDiffuseColors();
            float f = 0.75F;
            return new float[]{afloat[0] * 0.75F, afloat[1] * 0.75F, afloat[2] * 0.75F};
        }
    }

    @Override
    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.2D);
        } else {
            this.setSprinting(false);
        }
        super.customServerAiStep();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        UlughbegsaurusEntity ulugh = UPEntities.ULUG.get().create(serverLevel);
        assert ulugh != null;
        ulugh.setVariant(this.getVariant());
        return ulugh;
    }

    private void soundListener(SoundKeyframeEvent<UlughbegsaurusEntity> event) {
        UlughbegsaurusEntity ulughbegsaurus = event.getAnimatable();
        if (ulughbegsaurus.level().isClientSide) {
        }
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<UlughbegsaurusEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);
        AnimationController<UlughbegsaurusEntity> blend = new AnimationController<>(this, "blend", 5, this::predicate)
                .triggerableAnim("slay", ULUGH_SLAY)
                .triggerableAnim("scratch", ULUGH_SCRATCH)
                .triggerableAnim("shake", ULUGH_SHAKE)
                .triggerableAnim("vocal", ULUGH_VOCAL);
        blend.setSoundKeyframeHandler(this::soundListener);
        controllers.add(blend);
    }

    protected <E extends UlughbegsaurusEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

        if(this.isFromBook()){
            return event.setAndContinue(ULUGH_IDLE);
        }

        else if(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming() && !this.isInWater() && !this.hasControllingPassenger() && !this.isInSittingPose()){
            if(this.isSprinting() && !this.isBaby()) {
                event.setAndContinue(ULUGH_SPRINT);
                event.getController().setAnimationSpeed(1.0F);
            } else {
                event.setAndContinue(ULUGH_WALK);
                event.getController().setAnimationSpeed(1.0F);
            }
            return PlayState.CONTINUE;
        }

        else if(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming() && !this.isInWater() && this.hasControllingPassenger() && !this.isInSittingPose()){

            if(Objects.requireNonNull(this.getControllingPassenger()).isSprinting()){
                event.setAndContinue(ULUGH_SPRINT);
                event.getController().setAnimationSpeed(1.0F);
            }
            else {
                event.setAndContinue(ULUGH_WALK);
                event.getController().setAnimationSpeed(1.5F);
            }
            return PlayState.CONTINUE;
        }

        if (this.isInSittingPose() && !this.isInWater()) {
            event.setAndContinue(ULUGH_SIT);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        if (this.isInWater()) {
            event.setAndContinue(ULUGH_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }

        if (!this.isInWater()) {
            if (getBooleanState(IDLE_1_AC)) {
                triggerAnim("blend", "slay");
                return PlayState.CONTINUE;
            }
            if (getBooleanState(IDLE_2_AC)) {
                triggerAnim("blend", "scratch");
                return PlayState.CONTINUE;
            }
            if (getBooleanState(IDLE_3_AC)) {
                triggerAnim("blend", "shake");
                return PlayState.CONTINUE;
            }
            if (getBooleanState(IDLE_4_AC)) {
                triggerAnim("blend", "vocal");
                return PlayState.CONTINUE;
            }
            return event.setAndContinue(ULUGH_IDLE);
        }
        return PlayState.CONTINUE;
    }
}