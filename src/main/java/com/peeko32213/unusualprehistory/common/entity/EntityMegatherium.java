package com.peeko32213.unusualprehistory.common.entity;

import com.google.common.collect.Lists;
import com.peeko32213.unusualprehistory.common.entity.msc.util.*;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityTameableBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class EntityMegatherium extends EntityTameableBaseDinosaurAnimal implements CustomFollower, IAttackEntity {
    private static final EntityDataAccessor<Boolean> EATING = SynchedEntityData.defineId(EntityMegatherium.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(EntityMegatherium.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(EntityMegatherium.class, EntityDataSerializers.INT);
    private Ingredient temptationItems;
    private int eatingTime;


    public EntityMegatherium(EntityType<? extends EntityTameableBaseDinosaurAnimal> entityType, Level level) {
        super(entityType, level);
    }
    private int attackCooldown;
    public static final int ATTACK_COOLDOWN = 30;

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 35.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.16D)
                .add(Attributes.ARMOR, 3.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 3.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 3.5D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(1, new EatLeavesGoal(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, getTemptationItems(), false));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(1, new CustomRideGoal(this, 2D));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(3, new TameableFollowOwner(this, 1.2D, 5.0F, 2.0F, false));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34));
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
        this.entityData.define(EATING, Boolean.valueOf(false));
        this.entityData.define(SADDLED, Boolean.valueOf(false));
        this.entityData.define(COMMAND, 0);
    }

    public boolean isEating() {
        return this.entityData.get(EATING).booleanValue();
    }

    public void setEating(boolean eating) {
        this.entityData.set(EATING, Boolean.valueOf(eating));
    }

    public void tick() {
        super.tick();

        if(attackCooldown > 0){
            attackCooldown--;
        }

        if (!this.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
            this.setEating(true);
        }

        if (isEating()) {
            eatingTime++;
            if (!this.getMainHandItem().is(ItemTags.LEAVES)) {
                for (int i = 0; i < 3; i++) {
                    double d2 = this.random.nextGaussian() * 0.02D;
                    double d0 = this.random.nextGaussian() * 0.02D;
                    double d1 = this.random.nextGaussian() * 0.02D;
                    this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItemInHand(InteractionHand.MAIN_HAND)), this.getX() + (double) (this.random.nextFloat() * this.getBbWidth()) - (double) this.getBbWidth() * 0.5F, this.getY() + this.getBbHeight() * 0.5F + (double) (this.random.nextFloat() * this.getBbHeight() * 0.5F), this.getZ() + (double) (this.random.nextFloat() * this.getBbWidth()) - (double) this.getBbWidth() * 0.5F, d0, d1, d2);
                }
            }
            if (eatingTime % 5 == 0) {
                this.gameEvent(GameEvent.EAT);
                this.playSound(SoundEvents.PANDA_EAT, this.getSoundVolume(), this.getVoicePitch());
            }
            if (eatingTime > 100) {
                this.setEating(false);
                eatingTime = 0;
            }
        }
    }


    //TODO add positionRider to base class so we dont have to do all this again we can just use a method to fetch the offset
    public void positionRider(Entity passenger) {
            float ySin = Mth.sin(this.yBodyRot * ((float) Math.PI / 180F));
            float yCos = Mth.cos(this.yBodyRot * ((float) Math.PI / 180F));
            if (!this.isInSittingPose()) {
                passenger.setPos(this.getX() + (double) (0.5F * ySin), this.getY() + this.getPassengersRidingOffset() + passenger.getMyRidingOffset() + 0.4F, this.getZ() - (double) (0.5F * yCos));
                return;
            }
            passenger.setPos(this.getX() + (double) (0.5F * ySin), this.getY() + this.getPassengersRidingOffset() + passenger.getMyRidingOffset() - 1.0, this.getZ() - (double) (0.5F * yCos));
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
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(itemstack.is(UPItems.ENCYLOPEDIA.get())){
            InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, hand);
        }
        if (hand == InteractionHand.MAIN_HAND && !this.level.isClientSide) {
            if (isTame() && isOwnedBy(player)) {
                if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                    if (!player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    this.heal((float) itemstack.getFoodProperties(this).getNutrition());
                    this.gameEvent(GameEvent.EAT, this);
                } else if (itemstack.getItem() == Items.SADDLE && !this.isSaddled()) {
                    this.usePlayerItem(player, hand, itemstack);
                    this.setSaddled(true);
                } else if (itemstack.getItem() == Items.SHEARS && this.isSaddled()) {
                    this.setSaddled(false);
                    this.spawnAtLocation(Items.SADDLE);
                } else {
                    if (!player.isShiftKeyDown() && !this.isBaby() && this.isSaddled() && !this.isInSittingPose()) {
                        player.startRiding(this);
                    } else {
                        this.setCommand((this.getCommand() + 1) % 3);

                        if (this.getCommand() == 3) {
                            this.setCommand(0);
                        }
                        player.displayClientMessage(Component.translatable("entity.unusualprehistory.all.command_" + this.getCommand(), this.getName()), true);
                        boolean sit = this.getCommand() == 2;
                        if (sit) {
                            this.setOrderedToSit(true);
                        } else {
                            this.setOrderedToSit(false);
                        }
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    public int getCommand() {
        return this.entityData.get(COMMAND).intValue();
    }

    public void setCommand(int command) {
        this.entityData.set(COMMAND, Integer.valueOf(command));
    }

    @Override
    public boolean shouldFollow() {
        return this.getCommand() == 1;
    }

    @Override
    public void performAttack() {
        if (!this.level().isClientSide) {
            this.setSwinging(true);
            ServerLevel serverLevel = (ServerLevel) this.level();
            float angle = (0.01745329251F * this.yBodyRot);
            double radius = this.getBbWidth();
            double extraX = radius * Mth.sin((float) (Math.PI + angle));
            double extraZ = radius * Mth.cos(angle);
            BlockPos targetPos = BlockPos.containing(this.getX() + extraX, this.getY(), this.getZ() + extraZ);
            if (((Player) this.getControllingPassenger()).getItemInHand(InteractionHand.MAIN_HAND).is(Items.WOODEN_SHOVEL)) {
                targetPos = targetPos.offset(0, -1, 0);
            }
            if (((Player) this.getControllingPassenger()).getItemInHand(InteractionHand.MAIN_HAND).is(Items.IRON_SHOVEL)) {
                targetPos = targetPos.offset(0, 1, 0);
            }

            for (int x = -2; x < 2; x++) {
                for (int z = -2; z < 2; z++) {
                    for (int y = 0; y < 5; y++) {
                        if (serverLevel.getBlockState(targetPos.offset(x, y, z)).is(UPTags.MEGATHERIUM_MINEABLES))
                            serverLevel.destroyBlock(targetPos.offset(x, y, z), true);
                    }
                }
            }
        }
    }

    @Override
    public void afterAttack() {
        this.level().broadcastEntityEvent(this, (byte)5);
        this.setSwinging(false);
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

    @Override
    public float getStepHeight() {
        return 1.2F;
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
            LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();

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
                super.travel(new Vec3((double) f, pos.y, (double) f1));

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

    public boolean isSaddled() {
        return this.entityData.get(SADDLED).booleanValue();
    }

    public void setSaddled(boolean saddled) {
        this.entityData.set(SADDLED, Boolean.valueOf(saddled));
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

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.isFromBook()) {
            return PlayState.CONTINUE;
        }
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInWater() && !this.isInSittingPose() && !this.isSwimming()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.megatherium.move"));
            return PlayState.CONTINUE;
        }
        if (this.isInWater()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.megatherium.swim"));
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        if (this.isInSittingPose() && !this.isInWater() && !this.isSwimming()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.megatherium.sitting"));
            return PlayState.CONTINUE;
        } else if (!this.isInWater() && !this.isSwimming()){
            event.getController().setAnimation(new AnimationBuilder().loop("animation.megatherium.idle"));
        }
        return PlayState.CONTINUE;

    }

    private <E extends IAnimatable> PlayState eatPredicate(AnimationEvent<E> event) {
        if (this.isEating() && !this.isInSittingPose() && !this.isSwimming()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.megatherium.eating"));
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState diggingPredicate(AnimationEvent<E> event) {
        if ((isSwinging()) && event.getController().getAnimationState().equals(AnimationState.Stopped) && !this.isInSittingPose()) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().playOnce("animation.megatherium.digging"));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(5);
        AnimationController<EntityMegatherium> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        data.addAnimationController(new AnimationController<>(this, "attackController", 2, this::diggingPredicate));
        data.addAnimationController(new AnimationController<>(this, "eatController", 5, this::eatPredicate));
        data.addAnimationController(controller);
    }
}
