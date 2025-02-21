package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.msc.projectile.HwachavenatorSpikeEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.TameableBaseDinosaurAnimalEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.*;
import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.CustomFollower;
import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.IAttackEntity;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
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
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
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
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;

public class HwachavenatorEntity extends TameableBaseDinosaurAnimalEntity implements RangedAttackMob, CustomFollower, IAttackEntity {
    private static final EntityDataAccessor<Boolean> SHOOTING = SynchedEntityData.defineId(HwachavenatorEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(HwachavenatorEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(HwachavenatorEntity.class, EntityDataSerializers.BOOLEAN);

    public static final Logger LOGGER = LogManager.getLogger();
    public float shootProgress;
    public float sitProgress;
    public int soundTimer = 0;
    private int attackCooldown;
    public static final int ATTACK_COOLDOWN = 30;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation HWACHA_WALK = RawAnimation.begin().thenLoop("animation.hwacha.walk");
    private static final RawAnimation HWACHA_IDLE = RawAnimation.begin().thenLoop("animation.hwacha.idle");
    private static final RawAnimation HWACHA_SPRINT = RawAnimation.begin().thenLoop("animation.hwacha.sprinting");
    private static final RawAnimation HWACHA_SIT = RawAnimation.begin().thenLoop("animation.hwacha.sitting");
    private static final RawAnimation HWACHA_TURRET_FIRE = RawAnimation.begin().thenLoop("animation.hwacha.turret_firing");
    private static final RawAnimation HWACHA_SWIM = RawAnimation.begin().thenLoop("animation.hwacha.swim");

    public HwachavenatorEntity(EntityType<? extends TameableBaseDinosaurAnimalEntity> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 80D)
                .add(Attributes.FOLLOW_RANGE, 16D)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.3D)
                .add(Attributes.MOVEMENT_SPEED, 0.17F);

    }

    protected void registerGoals() {
        super.registerGoals();
        if(!this.hasControllingPassenger()) {
            this.goalSelector.addGoal(1, new RangedAttackGoal(this, 0D, 1, 16.0F));
        }
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new CustomRideGoal(this, 3D));
        this.goalSelector.addGoal(4, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(4, new TameableFollowOwner(this, 1.2D, 5.0F, 2.0F, false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.targetSelector.addGoal(5, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(5, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(6, new OwnerHurtTargetGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOTING, false);
        this.entityData.define(COMMAND, 0);
        this.entityData.define(SADDLED, Boolean.valueOf(false));
    }


    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("isShooting", this.isShooting());
        pCompound.putBoolean("Saddle", this.isSaddled());
        pCompound.putInt("HwachaCommand", this.getCommand());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setIsShooting(pCompound.getBoolean("isShooting"));
        this.setSaddled(pCompound.getBoolean("Saddle"));
        this.setCommand(pCompound.getInt("HwachaCommand"));
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
        if(!this.isInSittingPose()) {
            pPassenger.setPos(this.getX() + (double) (0.5F * ySin), this.getY() + this.getPassengersRidingOffset() + pPassenger.getMyRidingOffset() + 0.4F, this.getZ() - (double) (0.5F * yCos));
            return;
        }
        pPassenger.setPos(this.getX() + (double) (0.5F * ySin), this.getY() + this.getPassengersRidingOffset() + pPassenger.getMyRidingOffset() - 1.0, this.getZ() - (double) (0.5F * yCos));

    }

    public double getPassengersRidingOffset() {
        if (this.isInWater()) {
            return -0.5;
        }
        else {
            return 2.0D;
        }
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.HWACHA_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.HWACHA_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.HWACHA_DEATH.get();
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



    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if(this.level().isClientSide) return InteractionResult.PASS;
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if (isFood(itemstack) && !isTame()) {

            this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
            this.level().broadcastEntityEvent(this, (byte) 6);

            if(random.nextBoolean()) {
                this.tame(player);
                this.level().broadcastEntityEvent(this, (byte) 7);
            }

            itemstack.shrink(1);
            if(!player.isCreative()) {
                player.addItem(new ItemStack(Items.BOWL));
            }
            return InteractionResult.SUCCESS;
        }
        if (isTame() && isOwnedBy(player)) {
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                if(!this.level().isClientSide) {
                    this.heal((float) itemstack.getFoodProperties(this).getNutrition());
                }
                if(!player.isCreative()) {
                    player.addItem(new ItemStack(Items.BOWL));
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
                this.setSaddled(false);
                this.playSound(SoundEvents.SHEEP_SHEAR, 1.0F, 1.0F);
                this.spawnAtLocation(Items.SADDLE);
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
                if (!player.isShiftKeyDown() && !this.isBaby() && this.isSaddled()) {
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
        return stack.is(UPTags.HWACHA_FOOD);
    }

    public boolean isSaddled() {
        return this.entityData.get(SADDLED).booleanValue();
    }

    public void setSaddled(boolean saddled) {
        this.entityData.set(SADDLED, Boolean.valueOf(saddled));
    }

    public int getCommand() {
        return this.entityData.get(COMMAND).intValue();
    }

    public void setCommand(int command) {
        this.entityData.set(COMMAND, Integer.valueOf(command));
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

        shootProgress = 0;
        this.setIsShooting(false);
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
    public void travel(Vec3 pos) {
        if (this.isAlive()) {
            LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();


            if((this.isShooting() || this.isInSittingPose()) && livingentity != null){
                this.setYRot(livingentity.getYRot());
                this.setXRot(livingentity.getXRot() * 0.5F);
                this.setRot(livingentity.getYRot(), livingentity.getXRot());
                this.yBodyRot = livingentity.getYRot();
                this.yHeadRot = livingentity.yHeadRot;
                return;
            }

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

                this.setSpeed(0.3F);
                super.travel(new Vec3((double) f, pos.y, (double) f1));
                if (this.isShooting() && this.isVehicle()){
                    this.setSpeed(0.0F);
                }
            } else {
                super.travel(pos);
            }
        }
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(UPSounds.MAJUNGA_STEP.get(), 0.1F, 1.0F);
    }

    @Override
    public float getStepHeight() {
        return 1.25F;
    }
    @Override
    public void killed() {
        super.killed();
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

    @Override
    protected SoundEvent getAttackSound() {
        return null;
    }

    @Override
    protected int getKillHealAmount() {
        return 5;
    }

    @Override
    protected boolean canGetHungry() {
        return false;
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
        return UPTags.RAPTOR_TARGETS;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    public void performRangedAttack(LivingEntity p_30762_, float p_30763_) {
        if(!this.hasControllingPassenger() && !this.isInWater() && !this.isSwimming() && !this.isInSittingPose()) {
            this.setIsShooting(true);
        }
    }

    protected <E extends HwachavenatorEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if(this.isFromBook()){
            return PlayState.CONTINUE;
        }
        if (event.isMoving() && !isShooting() && !this.isInSittingPose() && !this.isInWater()) {
            if (this.isSprinting() || !this.getPassengers().isEmpty() ) {
                event.setAndContinue(HWACHA_SPRINT);
                event.getController().setAnimationSpeed(2.0D);
                return PlayState.CONTINUE;
            } else if (event.isMoving()){
                event.setAndContinue(HWACHA_WALK);
                event.getController().setAnimationSpeed(1.0D);
                return PlayState.CONTINUE;
            }
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
            event.setAndContinue(HWACHA_IDLE);
            event.getController().setAnimationSpeed(1.0D);
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

    @Override
    public double getTick(Object o) {
        return tickCount;
    }

    @Override
    public boolean shouldFollow() {
        return this.getCommand() == 1;
    }
}