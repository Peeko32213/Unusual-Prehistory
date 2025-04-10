package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.unusualprehistory.common.entity.animation.state.EntityAction;
import com.peeko32213.unusualprehistory.common.entity.animation.state.RandomStateGoal;
import com.peeko32213.unusualprehistory.common.entity.animation.state.StateHelper;
import com.peeko32213.unusualprehistory.common.entity.animation.state.WeightedState;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.CustomRideGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.ai.goal.PrehistoricFollowOwnerGoal;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.projectile.HwachavenatorSpikeEntity;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IAttackEntity;
import com.peeko32213.unusualprehistory.common.entity.util.interfaces.ICustomFollower;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmartBodyHelper;
import com.peeko32213.unusualprehistory.common.entity.util.navigator.SmoothGroundNavigation;
import com.peeko32213.unusualprehistory.core.other.tags.UPItemTags;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class HwachavenatorEntity extends PrehistoricEntity implements RangedAttackMob, ICustomFollower, IAttackEntity {

    private static final EntityDataAccessor<Boolean> SHOOTING = SynchedEntityData.defineId(HwachavenatorEntity.class, EntityDataSerializers.BOOLEAN);

    public static final Logger LOGGER = LogManager.getLogger();
    public float shootProgress;
    public int soundTimer = 0;
    private int attackCooldown;
    public static final int ATTACK_COOLDOWN = 30;

    // Movement animations
    private static final RawAnimation HWACHA_WALK = RawAnimation.begin().thenLoop("animation.hwachavenator.walk");
    private static final RawAnimation HWACHA_SPRINT = RawAnimation.begin().thenLoop("animation.hwachavenator.run");
    private static final RawAnimation HWACHA_SWIM = RawAnimation.begin().thenLoop("animation.hwachavenator.swim");

    // Idle animations
    private static final RawAnimation HWACHA_IDLE = RawAnimation.begin().thenLoop("animation.hwachavenator.idle");
    private static final RawAnimation HWACHA_SIT = RawAnimation.begin().thenLoop("animation.hwachavenator.sit");
    private static final RawAnimation HWACHA_SLEEP = RawAnimation.begin().thenLoop("animation.hwachavenator.sleep");
    private static final RawAnimation HWACHA_ROAR = RawAnimation.begin().thenPlay("animation.hwachavenator.blend_roar");
    private static final RawAnimation HWACHA_YAWN = RawAnimation.begin().thenPlay("animation.hwachavenator.blend_yawn");
    private static final RawAnimation HWACHA_DANCE1 = RawAnimation.begin().thenPlay("animation.hwachavenator.blend_dance1");
    private static final RawAnimation HWACHA_DANCE2 = RawAnimation.begin().thenPlay("animation.hwachavenator.blend_dance2");

    // Attack animations
    private static final RawAnimation HWACHA_TURRET_FIRE = RawAnimation.begin().thenLoop("animation.hwachavenator.blent_shake_attack");
    private static final RawAnimation HWACHA_BITE_1 = RawAnimation.begin().thenPlay("animation.hwachavenator.blend_bite_1");
    private static final RawAnimation HWACHA_BITE_2 = RawAnimation.begin().thenPlay("animation.hwachavenator.blend_bite_2");

    // Misc animations
    private static final RawAnimation HWACHA_EAT = RawAnimation.begin().thenPlay("animation.hwachavenator.blend_eat");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> IDLE_1_AC = SynchedEntityData.defineId(HwachavenatorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_2_AC = SynchedEntityData.defineId(HwachavenatorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_3_AC = SynchedEntityData.defineId(HwachavenatorEntity.class, EntityDataSerializers.BOOLEAN);

    // Idle actions
    private static final EntityAction HWACHA_IDLE_1_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper HWACHA_IDLE_1_STATE =
            StateHelper.Builder.state(IDLE_1_AC, "hwachavenator_roar")
                    .playTime(60)
                    .stopTime(180)
                    .entityAction(HWACHA_IDLE_1_ACTION)
                    .build();

    private static final EntityAction HWACHA_IDLE_2_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper HWACHA_IDLE_2_STATE =
            StateHelper.Builder.state(IDLE_2_AC, "hwachavenator_yawn")
                    .playTime(100)
                    .stopTime(200)
                    .entityAction(HWACHA_IDLE_2_ACTION)
                    .build();

    private static final EntityAction HWACHA_IDLE_3_ACTION = new EntityAction(0, (e) -> {}, 1);

    private static final StateHelper HWACHA_IDLE_3_STATE =
            StateHelper.Builder.state(IDLE_3_AC, "hwachavenator_lookout")
                    .playTime(60)
                    .stopTime(150)
                    .entityAction(HWACHA_IDLE_3_ACTION)
                    .build();

    // States
    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                HWACHA_IDLE_1_STATE.getName(), HWACHA_IDLE_1_STATE,
                HWACHA_IDLE_2_STATE.getName(), HWACHA_IDLE_2_STATE,
                HWACHA_IDLE_3_STATE.getName(), HWACHA_IDLE_3_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(HWACHA_IDLE_1_STATE, 9),
                WeightedState.of(HWACHA_IDLE_2_STATE, 10),
                WeightedState.of(HWACHA_IDLE_3_STATE, 11)
        );
    }

    // Body control / navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        SmartBodyHelper helper = new SmartBodyHelper(this);
        helper.bodyLagMoving = 0.35F;
        helper.bodyLagStill = 0.1F;
        return helper;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return new SmoothGroundNavigation(this, levelIn);
    }

    public HwachavenatorEntity(EntityType<? extends PrehistoricEntity> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.25F);
    }

    // Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 50D)
            .add(Attributes.FOLLOW_RANGE, 16D)
            .add(Attributes.ARMOR, 5D)
            .add(Attributes.ATTACK_DAMAGE, 6D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.25D)
            .add(Attributes.MOVEMENT_SPEED, 0.17D);
    }

    // Goals
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new RandomStateGoal<>(this));
        if(!this.hasControllingPassenger()) {
            this.goalSelector.addGoal(1, new RangedAttackGoal(this, 0D, 1, 16.0F));
        }
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1, 30));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new CustomRideGoal(this, 3D));
        this.goalSelector.addGoal(4, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(4, new PrehistoricFollowOwnerGoal(this, 1.2D, 5.0F, 2.0F, false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.targetSelector.addGoal(5, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(5, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(6, new OwnerHurtTargetGoal(this));
    }

    // Synched data
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IDLE_1_AC, false);
        this.entityData.define(IDLE_2_AC, false);
        this.entityData.define(IDLE_3_AC, false);
        this.entityData.define(SHOOTING, false);
    }

    // Save data
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("isShooting", this.isShooting());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setIsShooting(pCompound.getBoolean("isShooting"));
    }

    // Passenger
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
    protected void positionRider(Entity pPassenger, MoveFunction pCallback) {
        float ySin = Mth.sin(this.yBodyRot * ((float) Math.PI / 180F));
        float yCos = Mth.cos(this.yBodyRot * ((float) Math.PI / 180F));
        if(!this.isInSittingPose()) {
            pPassenger.setPos(this.getX() + (double) (0.5F * ySin), this.getY() + this.getPassengersRidingOffset() + pPassenger.getMyRidingOffset() + 0.4F, this.getZ() - (double) (0.5F * yCos));
            return;
        }
        pPassenger.setPos(this.getX() + (double) (0.5F * ySin), this.getY() + this.getPassengersRidingOffset() + pPassenger.getMyRidingOffset() - 1.0, this.getZ() - (double) (0.5F * yCos));

    }

    public double getPassengersRidingOffset() {
        return 2.0D;
    }

    // Sounds
    protected SoundEvent getAmbientSound() {
        return UPSounds.HWACHA_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.HWACHA_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.HWACHA_DEATH.get();
    }

    @Override
    public float getSoundVolume() {
        if(this.isBaby()){
            return 0.8F;
        }
        else{
            return 1.15F;
        }
    }

    // Mob interactions
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        if(this.level().isClientSide) return InteractionResult.PASS;
        ItemStack itemstack = player.getItemInHand(hand);

        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if (isTameFood(itemstack) && !isTame()) {

            this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
            this.level().broadcastEntityEvent(this, (byte) 6);

            if(random.nextBoolean()) {
                this.tame(player);
                this.level().broadcastEntityEvent(this, (byte) 7);
            }

            itemstack.shrink(1);
            if(!player.isCreative()) {
                if(!player.addItem(new ItemStack(Items.BOWL))){
                    player.spawnAtLocation(Items.BOWL);
                }
            }
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
                if(!player.isCreative()) {
                    player.addItem(new ItemStack(Items.BOWL));
                }
                this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
                this.level().broadcastEntityEvent(this, (byte) 7);
                this.gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;
            }
            if (this.isHealingFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                this.heal((float)itemstack.getFoodProperties(this).getNutrition());
                this.gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;
            }
            else {
                if (!player.isShiftKeyDown() && !this.isBaby()) {
                    if(!this.level().isClientSide) {
                        player.startRiding(this);

                        //Todo fix this: displayClientMessage not working!!
                        player.sendSystemMessage(Component.translatable("dinosaur.start_riding.attack_key").withStyle(ChatFormatting.WHITE));
                    }
                    return InteractionResult.SUCCESS;
                } else {
                    if(!this.level().isClientSide) {
                        this.setCommand((this.getCommand() + 1));
                    }
                    if (this.getCommand() >= 3) {
                        if(!this.level().isClientSide) {
                            this.setCommand(0);
                        }
                    }
                    player.displayClientMessage(Component.translatable("entity.unusualprehistory.all.command_" + this.getCommand(), this.getName()), true);
                    boolean sit = this.getCommand() == 2;
                    if (sit) {
                        if(!this.level().isClientSide) {
                            this.setInSittingPose(true);
                        }
                        return InteractionResult.SUCCESS;
                    } else {
                        if(!this.level().isClientSide) {
                            this.setOrderedToSit(false);
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }

        }
        return InteractionResult.FAIL;
    }

    @Override
    public boolean startRiding(Entity pVehicle) {
        return super.startRiding(pVehicle);
    }

    public boolean isHealingFood(ItemStack pStack) {
        Item item = pStack.getItem();
        return item.isEdible() && pStack.getFoodProperties(this).isMeat();
    }

    public boolean isFood(ItemStack stack) {
        return stack.is(UPItemTags.HWACHA_FOOD);
    }

    public boolean isTameFood(ItemStack stack) {
        return stack.is(UPItemTags.HWACHA_FOOD);
    }

    public boolean isShooting() {
        return this.entityData.get(SHOOTING);
    }

    public void setIsShooting(boolean shooting) {
        this.entityData.set(SHOOTING, shooting);
    }

    public void tick() {
        super.tick();

        if(attackCooldown > 0){
            attackCooldown--;
        }

        if(soundTimer > 0){
            soundTimer--;
        }

       if (this.isShooting() && shootProgress < 50 && !this.hasControllingPassenger() && !this.isInWater() && !this.isSwimming()) {
           this.spit(this.getTarget());
           this.getNavigation().stop();
           shootProgress += 1;
           return;
       }

       if (this.isShooting() && shootProgress < 50 && this.hasControllingPassenger() && !this.isInWater() && !this.isSwimming()) {
           this.spitNoTarget();
           this.getNavigation().stop();
           shootProgress += 1;
           return;
       }

        shootProgress = 0;
        this.setIsShooting(false);

        boolean ridden = !this.getPassengers().isEmpty();
        boolean water = this.isInWater();
        if(ridden && water) {
            boolean waterBelow = this.level().isWaterAt(this.blockPosition().below());

            if(waterBelow) {
                this.move(MoverType.PLAYER, new Vec3(0, 0.08, 0));
            }
        }
    }

    @Override
    public void performAttack() {
        this.setIsShooting(true);
    }

    @Override
    public void afterAttack() {
        this.setIsShooting(false);
        this.soundTimer = 0;
        ClientboundStopSoundPacket clientboundstopsoundpacket = new ClientboundStopSoundPacket(UPSounds.HWACHA_SHOOT.getId(), SoundSource.NEUTRAL);
        ServerPlayer serverPlayer = (ServerPlayer) this.getControllingPassenger();
        serverPlayer.connection.send(clientboundstopsoundpacket);
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

    public boolean hurt(DamageSource source, float amount) {
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
                if(!this.isInSittingPose()) {
                    if (this.isShooting() && this.isVehicle()) {
                        this.setSpeed(0.0F);
                    } else if (this.getControllingPassenger().isSprinting()) {
                        this.setSpeed(((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 1.15F));
                    } else {
                        this.setSpeed(((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.5F));
                    }
                }
                super.travel(new Vec3(f, pos.y, f1));
            } else {
                super.travel(pos);
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

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(UPSounds.MAJUNGA_STEP.get(), 0.1F, 1.0F);
    }

    @Override
    public float getStepHeight() {
        return 1.25F;
    }

    public void killed() {
        this.setIsShooting(false);
        this.shootProgress = 0;
    }

    public void spit(LivingEntity target) {
        if (target == null) {
            return;
        }
        this.lookAt(target, 100, 100);
        for (int i = 0; i < 2 + random.nextInt(2); i++) {
            HwachavenatorSpikeEntity llamaspitentity = new HwachavenatorSpikeEntity(this.level(), this);
            double d0 = target.getX() - this.getX();
            double d1 = target.getY() - llamaspitentity.getY();
            double d2 = target.getZ() - this.getZ();
            float f = Mth.sqrt((float) (d0 * d0 + d2 * d2)) * 0.2F;
            llamaspitentity.shoot(d0, d1 , d2, 2.0F, 4.0F);

            playShootSound();

            this.level().addFreshEntity(llamaspitentity);
        }
    }

    public void spitNoTarget() {
        final int MAX_SHOTS = 2;
        final int MIN_SHOTS = 2;
        final int VIEW_VECTOR_SCALE = 15;
        final int NO_SPIKE_ZONE_INFLATE = 2;
        final int MESSAGE_TIMER_LIMIT = 6000;
        final int MAX_DISTANCE = 320;
        int messageTimer = 0;
        for (int i = 0; i < MIN_SHOTS + random.nextInt(MAX_SHOTS); i++) {
            try {
                if (this.level().isClientSide) {
                    return;
                }
                HwachavenatorSpikeEntity llamaspitentity = new HwachavenatorSpikeEntity(this.level(), this);
                Player player = (Player) this.getControllingPassenger();
                if (player == null) {
                    return;
                }
                //You can change the entity pov here, so for hitresult we check the hit using the player
                //and for entity we use hwacha entity, these can be changed so see what you think is best

                BlockPos blockPlayerIsLookingAt = level().clip(new ClipContext(player.getEyePosition(1f),
                        (player.getEyePosition(1f).add(player.getViewVector(1f).scale(MAX_DISTANCE))),
                        ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player)).getBlockPos();

                //HitResult hitresult = getEntityPOVHitResult(this.level, player, ClipContext.Fluid.ANY);
                Optional<Entity> entity = getEntityHitResult(MAX_DISTANCE);
                BlockPos blockpos = blockPlayerIsLookingAt;
                double d0 = 0;
                double d1 = 0;
                double d2 = 0;



                if (entity.isPresent()) {
                    var entity1 = entity.get();
                    BlockPos blockPosEntity = entity.get().getOnPos();

                    Vec3 eyePosition = entity1.getEyePosition();
                    BlockPos blockPos = BlockPos.containing(eyePosition.x, eyePosition.y, eyePosition.z);
                    d0 = blockPos.getX() - this.getX();
                    d1 = blockPos.getY() - this.getY();
                    d2 = blockPos.getZ() - this.getZ();

                }  if (blockpos != null) {
                    //I added a check here that if the arrow is trying to be shot too close to hwacha it wont shoot and gives a message
                    AABB aabb = player.getBoundingBox().inflate(NO_SPIKE_ZONE_INFLATE);
                    if (aabb.contains(Vec3.atCenterOf(blockpos))) {
                        player.displayClientMessage(Component.translatable("hwachavenator.shooting_too_close.message").withStyle(ChatFormatting.RED), true);
                        return;
                    }
                    d0 = blockpos.getX() - this.getX();
                    d1 = blockpos.getY() - this.getY();
                    d2 = blockpos.getZ() - this.getZ();
                }

                //I added a check here that if the arrow or hitresult will result in a miss it wont shoot and fails with a message
                if (d0 == 0 && d1 == 0 && d2 == 0 && messageTimer == 0) {
                    player.displayClientMessage(Component.translatable("hwachavenator.fail_shoot.message").withStyle(ChatFormatting.RED), true);
                    return;
                }

                playShootSound();
                llamaspitentity.shoot(d0, d1, d2, 2.0F, 0.0F);
                //llamaspitentity.isNoGravity();
                this.level().addFreshEntity(llamaspitentity);

            } catch (NullPointerException exception) {
                LOGGER.error("Something went wrong shooting a spike due to: {}", exception.getMessage());
            }

        }
    }

    private void playShootSound(){
        if(this.soundTimer <= 0){
            this.playSound(UPSounds.HWACHA_SHOOT.get(), this.getSoundVolume(), (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F);
            soundTimer = 80;
        }
    }

    protected Optional<Entity> getEntityHitResult(int range){
        Vec3 vec3 = this.getEyePosition();
        Vec3 vec31 = this.getViewVector(1.0F);
        Vec3 vec32 = vec3.add(vec31.x * range, vec31.y * range, vec31.z * range);
        float f = 1.0F;
        AABB aabb = this.getBoundingBox().expandTowards(vec31.scale(range)).inflate(1.0D, 1.0D, 1.0D);
        EntityHitResult entityhitresult = ProjectileUtil.getEntityHitResult(this, vec3, vec32, aabb, (p_234237_) -> {
            return !p_234237_.isSpectator() && p_234237_.isPickable();
        }, range);

        if(entityhitresult == null){
            return Optional.empty();
        }

        return Optional.of(entityhitresult.getEntity());
    }

    //Vanilla Copy from Item
    protected static BlockHitResult getEntityPOVHitResult(Level p_41436_, Entity p_41437_, ClipContext.Fluid p_41438_) {
        float f = p_41437_.getXRot();
        float f1 = p_41437_.getYRot();
        Vec3 vec3 = p_41437_.getEyePosition();
        float f2 = Mth.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = Mth.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -Mth.cos(-f * ((float) Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float) Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;

        //Range!
        double d0 = 35;
        Vec3 vec31 = vec3.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
        return p_41436_.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, p_41438_, p_41437_));
    }

    public void performRangedAttack(LivingEntity p_30762_, float p_30763_) {
        if(!this.hasControllingPassenger() && !this.isInWater() && !this.isSwimming() && !this.isInSittingPose()) {
            this.setIsShooting(true);
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        HwachavenatorEntity hwacha = UPEntities.HWACHA.get().create(serverLevel);
        hwacha.setVariant(this.getVariant());
        return hwacha;
    }

    // Variants
    public void determineVariant(int variantChange){
        if (variantChange <= 25) {
            this.setVariant(1);
        }
        else if (variantChange <= 50) {
            this.setVariant(2);
        }
        else if (variantChange <= 75) {
            this.setVariant(3);
        }
        else {
            this.setVariant(0);
        }
    }

    @Override
    public boolean shouldFollow() {
        return this.getCommand() == 1;
    }

    // Animation sounds
    private void soundListener(SoundKeyframeEvent<HwachavenatorEntity> event) {
        HwachavenatorEntity hwacha = event.getAnimatable();
        if (event.getKeyframeData().getSound().equals("hwachavenator_roar")) {
            hwacha.level().playLocalSound(hwacha.getX(), hwacha.getY(), hwacha.getZ(), UPSounds.HWACHA_ROAR.get(), hwacha.getSoundSource(), 1.5F, hwacha.getVoicePitch(), false);
        }
    }

    // Animation control
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<HwachavenatorEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);

        AnimationController<HwachavenatorEntity> blend = new AnimationController<>(this, "blend", 10, this::predicate)
                .triggerableAnim("roar", HWACHA_ROAR)
                .triggerableAnim("yawn", HWACHA_YAWN)
                .triggerableAnim("lookout", HWACHA_DANCE1)
                .triggerableAnim("bite_1", HWACHA_BITE_1)
                .triggerableAnim("bite_2", HWACHA_BITE_2);
        blend.setSoundKeyframeHandler(this::soundListener);
        controllers.add(blend);
    }

    protected <E extends HwachavenatorEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

        if(this.isFromBook()){
            return event.setAndContinue(HWACHA_IDLE);
        }

        if(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming() && !this.isInWater() && !this.hasControllingPassenger() && !this.isInSittingPose()){
            if(this.isSprinting() && !this.isBaby()) {
                event.setAndContinue(HWACHA_SPRINT);
                event.getController().setAnimationSpeed(1.0F);
            } else {
                event.setAndContinue(HWACHA_WALK);
                event.getController().setAnimationSpeed(1.0F);
            }
            return PlayState.CONTINUE;
        }

        else if(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming() && !this.isInWater() && this.hasControllingPassenger() && !this.isInSittingPose()){

            if(Objects.requireNonNull(this.getControllingPassenger()).isSprinting()){
                event.setAndContinue(HWACHA_SPRINT);
                event.getController().setAnimationSpeed(1.15F);
            }
            else {
                event.setAndContinue(HWACHA_WALK);
                event.getController().setAnimationSpeed(1.5F);
            }
            return PlayState.CONTINUE;
        }

        if (isShooting() && !this.isInSittingPose() && !this.isInWater() && !this.isSwimming()) {
            event.setAndContinue(HWACHA_TURRET_FIRE);
            return PlayState.CONTINUE;
        }

        if (this.isInSittingPose() && !isShooting()) {
            event.setAndContinue(HWACHA_SIT);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }

        if (this.isInWater()  && !isShooting() && !this.isInSittingPose()) {
            event.setAndContinue(HWACHA_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }

        if(!this.isInWater()) {
            if (getBooleanState(IDLE_1_AC)) {
                if (this.isStillEnough()) {
                    triggerAnim("blend", "roar");
                    return event.setAndContinue(HWACHA_IDLE);
                }
                else {
                    triggerAnim("blend", "roar");
                    return PlayState.CONTINUE;
                }
            }
            if (getBooleanState(IDLE_2_AC)) {
                if (this.isStillEnough()) {
                    triggerAnim("blend", "yawn");
                    return event.setAndContinue(HWACHA_IDLE);
                }
                else {
                    triggerAnim("blend", "yawn");
                    return PlayState.CONTINUE;
                }
            }
            if (getBooleanState(IDLE_3_AC)) {
                if (this.isStillEnough()) {
                    triggerAnim("blend", "lookout");
                    return event.setAndContinue(HWACHA_IDLE);
                }
                else {
                    triggerAnim("blend", "lookout");
                    return PlayState.CONTINUE;
                }
            }
            return event.setAndContinue(HWACHA_IDLE);
        }
        return PlayState.CONTINUE;
    }
}