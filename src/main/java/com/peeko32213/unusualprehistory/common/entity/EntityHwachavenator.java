package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.msc.projectile.EntityHwachaSpike;
import com.peeko32213.unusualprehistory.common.entity.msc.util.*;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityTameableRangedBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.Optional;

public class EntityHwachavenator extends EntityTameableRangedBaseDinosaurAnimal implements RangedAttackMob, CustomFollower {
    private static final EntityDataAccessor<Boolean> SHOOTING = SynchedEntityData.defineId(EntityHwachavenator.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(EntityHwachavenator.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(EntityHwachavenator.class, EntityDataSerializers.BOOLEAN);

    public static final Logger LOGGER = LogManager.getLogger();
    public float shootProgress;
    public float sitProgress;
    public int messageTimer = 0;

    public EntityHwachavenator(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 80D)
                .add(Attributes.FOLLOW_RANGE, 50D)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.3D)
                .add(Attributes.MOVEMENT_SPEED, 0.17F);

    }


    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.25D, 10, 20.0F));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(1, new CustomRideGoal(this, 3D));
        this.goalSelector.addGoal(3, new TameableFollowOwner(this, 1.2D, 6.0F, 100.0F, false));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this) {

            public boolean canUse() {
                return !isTame();
            }

        }));
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
    public Entity getControllingPassenger() {
        for (Entity passenger : this.getPassengers()) {
            if (passenger instanceof Player) {
                return (Player) passenger;
            }
        }
        return null;
    }

    public void positionRider(Entity passenger) {
        float ySin = Mth.sin(this.yBodyRot * ((float) Math.PI / 180F));
        float yCos = Mth.cos(this.yBodyRot * ((float) Math.PI / 180F));
        passenger.setPos(this.getX() + (double) (0.5F * ySin), this.getY() + this.getPassengersRidingOffset() + passenger.getMyRidingOffset() + 0.4F, this.getZ() - (double) (0.5F * yCos));
    }

    public double getPassengersRidingOffset() {
        return 2;
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

        return super.isAlliedTo(entityIn);
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        InteractionResult type = super.mobInteract(player, hand);
        Item item = itemstack.getItem();
        if (isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
            if (!player.isCreative()) {
                itemstack.shrink(1);
            }
            this.heal(6);
            return InteractionResult.SUCCESS;
        }
        if (isFood(itemstack) && !isTame()) {
            int size = itemstack.getCount();
            this.tame(player);
            itemstack.shrink(size);
            return InteractionResult.SUCCESS;
        }
        InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, hand);
        if (interactionresult != InteractionResult.SUCCESS && type != InteractionResult.SUCCESS && isTame() && isOwnedBy(player)) {
            if (isFood(itemstack)) {
                this.usePlayerItem(player, hand, itemstack);
                return InteractionResult.SUCCESS;
            } else if (itemstack.getItem() == Items.SADDLE && !this.isSaddled()) {
                this.usePlayerItem(player, hand, itemstack);
                this.setSaddled(true);
                return InteractionResult.SUCCESS;
            } else if (itemstack.getItem() == Items.SHEARS && this.isSaddled()) {
                this.setSaddled(false);
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
                    player.startRiding(this);
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
        return type;
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

        if (this.isShooting() && shootProgress < 50 && !this.isSaddled()) {
            this.spit(this.getTarget());
            this.getNavigation().stop();
            shootProgress += 1;
            this.playSound(UPSounds.HWACHA_SHOOT.get(), 0.5F, 1.0F);

            return;
        }

        if (this.isShooting() && shootProgress < 50 && this.isSaddled()) {
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
            if(this.isShooting()){
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
                double d8 = this.getY();
                Vec3 vec34 = this.getDeltaMovement();
                //Travel up when there is a horizontal collission  and enough space, tried jumping but was a bit funky
                if (this.horizontalCollision  && this.isFree(vec34.x, vec34.y + (double) 0.5F - this.getY() + d8, vec34.z)) {
                    this.setDeltaMovement(vec34.x, (double) 0.5F, vec34.z);
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

    private void setupShooting() {
        this.entityData.set(SHOOTING, true);
    }


    public void spit(LivingEntity target) {
        if (target == null) {
            return;
        }
        this.lookAt(target, 100, 100);
        for (int i = 0; i < 2 + random.nextInt(2); i++) {
            EntityHwachaSpike projectile = new EntityHwachaSpike(this.level, this);
            float bodyFacingAngle = ((this.yBodyRot * Mth.PI));
            float radius = this.getBbHeight();
            double sx = getX() + (radius * Mth.cos(bodyFacingAngle) * 0.65D);
            double sy = getY() + (this.getBbHeight());
            double sz = getZ() + (radius * Mth.sin(bodyFacingAngle) * 0.65D);

            double tx = target.getX() - sx;
            double ty = (target.getY() + target.getBbHeight()) - (this.getY() + this.getBbHeight());
            double tz = target.getZ() - sz;

            this.playSound(SoundEvents.ARROW_SHOOT, this.getSoundVolume(), (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F);

            projectile.moveTo(sx, sy, sz, getYRot(), getXRot());
            projectile.shoot(tx, ty, tz, 1.0F, 1.0F);

            this.getLevel().addFreshEntity(projectile);
        }
    }

    public void spitNoTarget() {
        final int MAX_SHOTS = 2;
        final int MIN_SHOTS = 2;
        final int VIEW_VECTOR_SCALE = 15;
        final int NO_SPIKE_ZONE_INFLATE = 2;
        final int MESSAGE_TIMER_LIMIT = 6000;
        int messageTimer = 0;
        for (int i = 0; i < MIN_SHOTS + random.nextInt(MAX_SHOTS); i++) {
            try {
                if (this.level.isClientSide) {
                    return;
                }
                EntityHwachaSpike llamaspitentity = new EntityHwachaSpike(this.level, this);
                Player player = (Player) this.getControllingPassenger();
                if (player == null) {
                    return;
                }
                //You can change the entity pov here, so for hitresult we check the hit using the player
                //and for entity we use hwacha entity, these can be changed so see what you think is best
                HitResult hitresult = getEntityPOVHitResult(this.level, player, ClipContext.Fluid.ANY);
                Optional<Entity> entity = DebugRenderer.getTargetedEntity(this, VIEW_VECTOR_SCALE);
                BlockPos blockpos = null;
                double d0 = 0;
                double d1 = 0;
                double d2 = 0;

                if (hitresult.getType() == HitResult.Type.BLOCK) {
                    blockpos = ((BlockHitResult) hitresult).getBlockPos();
                }

                if (entity.isPresent()) {
                    var entity1 = entity.get();
                    BlockPos blockPosEntity = entity.get().getOnPos();

                    Vec3 eyePosition = entity1.getEyePosition();
                    BlockPos blockPos = new BlockPos(eyePosition.x, eyePosition.y, eyePosition.z);
                    d0 = blockPos.getX() - this.getX();
                    d1 = blockPos.getY() - this.getY();
                    d2 = blockPos.getZ() - this.getZ();

                }  if (blockpos != null) {
                    //I added a check here that if the arrow is trying to be shot too close to hwacha it wont shoot and gives a message
                    AABB aabb = player.getBoundingBox().inflate(NO_SPIKE_ZONE_INFLATE);
                    if (aabb.contains(Vec3.atCenterOf(blockpos))) {
                        player.displayClientMessage(Component.translatable("hwachavenator.shooting_too_close.message").withStyle(ChatFormatting.RED), true);
                        //player.displayClientMessage();
                        return;
                    }
                    d0 = blockpos.getX() - this.getX();
                    d1 = blockpos.getY() - this.getY();
                    d2 = blockpos.getZ() - this.getZ();
                }

                //I added a check here that if the arrow or hitresult will result in a miss it wont shoot and fails with a message
                if (d0 == 0 && d1 == 0 && d2 == 0 && messageTimer == 0 || hitresult.getType() == HitResult.Type.MISS) {
                    player.displayClientMessage(Component.translatable("hwachavenator.fail_shoot.message").withStyle(ChatFormatting.RED), true);
                    return;
                }

                llamaspitentity.shoot(d0, d1, d2, 2.0F, 0.0F);
                //llamaspitentity.isNoGravity();
                this.level.addFreshEntity(llamaspitentity);

            } catch (NullPointerException exception) {
                LOGGER.error("Something went wrong shooting a spike due to: {}", exception.getMessage());
            }

        }
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
        this.setIsShooting(true);
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !isShooting() && !this.isInSittingPose()) {
            if (this.isSprinting() || !this.getPassengers().isEmpty() ) {
                event.getController().setAnimation(new AnimationBuilder().loop("animation.hwacha.sprinting"));
                event.getController().setAnimationSpeed(2.0D);
                return PlayState.CONTINUE;
            } else if (event.isMoving()) {
                event.getController().setAnimation(new AnimationBuilder().loop("animation.hwacha.walk"));
                event.getController().setAnimationSpeed(1.0D);
                return PlayState.CONTINUE;
            }
        } else if (!isShooting() && !this.isInSittingPose()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.hwacha.idle"));
            event.getController().setAnimationSpeed(1.0D);
        } else if (isShooting() && !this.isInSittingPose()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.hwacha.turret_firing"));
            return PlayState.CONTINUE;
        }
        if (this.isInSittingPose() && !isShooting()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.hwacha.sitting"));
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(5);
        AnimationController<EntityHwachavenator> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        data.addAnimationController(controller);
    }

    @Override
    public boolean shouldFollow() {
        return this.getCommand() == 1;
    }
}
