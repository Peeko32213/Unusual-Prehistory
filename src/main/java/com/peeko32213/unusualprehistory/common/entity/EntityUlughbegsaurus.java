package com.peeko32213.unusualprehistory.common.entity;

import com.google.common.collect.Maps;
import com.peeko32213.unusualprehistory.common.entity.msc.util.*;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityTameableBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.Wolf;
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
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityUlughbegsaurus extends EntityTameableBaseDinosaurAnimal implements CustomFollower {
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(EntityUlughbegsaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> BLUE = SynchedEntityData.defineId(EntityUlughbegsaurus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> YELLOW = SynchedEntityData.defineId(EntityUlughbegsaurus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> WHITE = SynchedEntityData.defineId(EntityUlughbegsaurus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ORANGE = SynchedEntityData.defineId(EntityUlughbegsaurus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> BROWN = SynchedEntityData.defineId(EntityUlughbegsaurus.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(EntityUlughbegsaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(EntityUlughbegsaurus.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> EATING_TIME = SynchedEntityData.defineId(EntityUlughbegsaurus.class, EntityDataSerializers.INT);
    public static final Logger LOGGER = LogManager.getLogger();
    private static final Map<DyeColor, float[]> COLORARRAY_BY_COLOR = Maps.<DyeColor, float[]>newEnumMap(Arrays.stream(DyeColor.values()).collect(Collectors.toMap((p_29868_) -> {
        return p_29868_;
    }, EntityUlughbegsaurus::createSheepColor)));
    private static final int ATTACK_COOLDOWN = 30;
    private boolean hasBlueAttributes = false;
    private boolean hasYellowAttributes = false;
    private boolean hasWhiteAttributes = false;
    private boolean hasOrangeAttributes = false;
    public float prevSitProgress;
    public float prevEatProgress;
    public float eatProgress;
    public float sitProgress;

    public EntityUlughbegsaurus(EntityType<? extends EntityTameableBaseDinosaurAnimal> entityType, Level level) {
        super(entityType, level);
        this.maxUpStep = 1.2F;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 2D, false));
        this.goalSelector.addGoal(2, new EntityUlughbegsaurus.IMeleeAttackGoal());
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(1, new CustomRideGoal(this, 2D));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34) {
                    @Override
                    public boolean canUse() {
                        if (this.mob.isVehicle()) {
                            return false;
                        } else {
                            if (!this.forceTrigger) {
                                if (this.mob.getNoActionTime() >= 100) {
                                    return false;
                                }
                                if (((EntityUlughbegsaurus) this.mob).isHungry()) {
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
        this.goalSelector.addGoal(3, new TameableFollowOwner(this, 1.2D, 5.0F, 2.0F, false));

    }



    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(UPSounds.MAJUNGA_STEP.get(), 0.1F, 1.0F);
    }


    protected SoundEvent getAmbientSound() {
        return UPSounds.ULUGH_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.ULUGH_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.ULUGH_DEATH.get();
    }

    public boolean isYellowFood(ItemStack stack) {
        return stack.is(UPTags.YELLOW_ULUGH_FOOD);
    }

    public boolean isBlueFood(ItemStack stack) {
        return stack.is(UPTags.BLUE_ULUGH_FOOD);
    }

    public boolean isWhiteFood(ItemStack stack) {
        return stack.is(UPTags.WHITE_ULUGH_FOOD);
    }

    public boolean isOrangeFood(ItemStack stack) {
        return stack.is(UPTags.ORANGE_ULUGH_FOOD);
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
        if (shouldHurt = target.hurt(DamageSource.mobAttack(this), damage)) {
            if (knockback > 0.0f && target instanceof LivingEntity) {
                ((LivingEntity) target).knockback(knockback * 0.5f, Mth.sin(this.getYRot() * ((float) Math.PI / 180)), -Mth.cos(this.getYRot() * ((float) Math.PI / 180)));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
            }
            if (target instanceof LivingEntity && this.isYellow()) {
                ((LivingEntity) target).addEffect(new MobEffectInstance(UPEffects.PREVENT_CLICK.get(), 200), this);
            }
            this.doEnchantDamageEffects(this, target);
            this.setLastHurtMob(target);
        }
        this.level.broadcastEntityEvent(this, (byte) 4);
        return shouldHurt;
    }



    public void performAttack() {
        if (this.level.isClientSide) {
            return;
        }
        for (Entity entity : this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.0D))) {
            if (!this.hasSwung() && this.isSaddled() && this.isTame() && this.hasControllingPassenger()) {
                if (entity instanceof EntityUlughbegsaurus ulughbegsaurus) {
                    if (ulughbegsaurus.isTame()) {
                        continue;
                    }
                }
                if (entity.is(this.getControllingPassenger())) {
                    continue;
                }

                if (this.isOrange()) {
                    entity.hurt(DamageSource.mobAttack(this), 12.0F);
                } else
                    entity.hurt(DamageSource.mobAttack(this), 8.0F);
            }
        }
    }


    public InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        // LOGGER.info("Yellow " +this.isYellow() );
        // LOGGER.info("Blue " +this.isBlue() );
        // LOGGER.info("White " +this.isWhite() );
        // LOGGER.info("Orange " +this.isOrange() );
        // LOGGER.info("hp " + this.getMaxHealth());
        // LOGGER.info("Speed" + this.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
        // LOGGER.info("Speed" + this.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
        if (this.isYellow()) {
            if (isYellowFood(itemstack) && !isTame()) {
                int size = itemstack.getCount();
                this.tame(player);
                itemstack.shrink(size);
                this.setEatingTime(50 + random.nextInt(30));
                this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }
        if (this.isBlue()) {
            if (isBlueFood(itemstack) && !isTame()) {
                int size = itemstack.getCount();
                this.tame(player);
                itemstack.shrink(size);
                this.setEatingTime(50 + random.nextInt(30));
                this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }
        if (this.isOrange()) {
            if (isOrangeFood(itemstack) && !isTame()) {
                int size = itemstack.getCount();
                this.tame(player);
                itemstack.shrink(size);
                this.setEatingTime(50 + random.nextInt(30));
                this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }
        if (this.isWhite()) {
            if (isWhiteFood(itemstack) && !isTame()) {
                int size = itemstack.getCount();
                this.tame(player);
                itemstack.shrink(size);
                this.setEatingTime(50 + random.nextInt(30));
                this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }
        if (this.isBrown()) {
            if (isBrownFood(itemstack) && !isTame()) {
                int size = itemstack.getCount();
                this.tame(player);
                itemstack.shrink(size);
                this.setEatingTime(50 + random.nextInt(30));
                this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }
        InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, hand);
        if (interactionresult != InteractionResult.SUCCESS && isTame() && isOwnedBy(player)) {
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                this.heal((float)itemstack.getFoodProperties(this).getNutrition());
                this.gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;
            } else if (itemstack.getItem() == Items.SADDLE && !this.isSaddled()) {
                this.usePlayerItem(player, hand, itemstack);
                this.setSaddled(true);
                return InteractionResult.SUCCESS;
            } else if (itemstack.getItem() == Items.SHEARS && this.isSaddled()) {
                this.setSaddled(false);
                this.spawnAtLocation(Items.SADDLE);
                return InteractionResult.SUCCESS;
            } else {
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
        return super.mobInteract(player, hand);
    }

    public boolean isFood(ItemStack pStack) {
        Item item = pStack.getItem();
        return item.isEdible() && pStack.getFoodProperties(this).isMeat();
    }

    public SoundEvent getEatingSound(ItemStack p_28540_) {
        return SoundEvents.GENERIC_EAT;
    }

    protected void spawnTamingParticles(boolean p_21835_) {
        ParticleOptions particleoptions = ParticleTypes.HEART;
        if (!p_21835_) {
            particleoptions = ParticleTypes.SMOKE;
        }

        for (int i = 0; i < 7; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level.addParticle(particleoptions, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
        }

    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", getVariant());
        compound.putBoolean("Blue", this.isBlue());
        compound.putBoolean("Yellow", this.isYellow());
        compound.putBoolean("White", this.isWhite());
        compound.putBoolean("Orange", this.isOrange());
        compound.putBoolean("Saddle", this.isSaddled());
        compound.putBoolean("Brown", this.isBrown());
        compound.putInt("TrikeCommand", this.getCommand());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setVariant(compound.getInt("Variant"));
        this.setBlue(compound.getBoolean("Blue"));
        this.setYellow(compound.getBoolean("Yellow"));
        this.setWhite(compound.getBoolean("White"));
        this.setOrange(compound.getBoolean("Orange"));
        this.setBrown(compound.getBoolean("Brown"));
        this.setSaddled(compound.getBoolean("Saddle"));
        this.setCommand(compound.getInt("TrikeCommand"));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(EATING_TIME, 0);
        this.entityData.define(BLUE, Boolean.valueOf(false));
        this.entityData.define(YELLOW, Boolean.valueOf(false));
        this.entityData.define(WHITE, Boolean.valueOf(false));
        this.entityData.define(ORANGE, Boolean.valueOf(false));
        this.entityData.define(BROWN, Boolean.valueOf(false));
        this.entityData.define(SADDLED, Boolean.valueOf(false));
        this.entityData.define(COMMAND, 0);

    }

    public boolean isBrownFood(ItemStack stack) {
        return stack.is(UPTags.BROWN_ULUGH_FOOD);
    }

    public boolean isSaddled() {
        return this.entityData.get(SADDLED).booleanValue();
    }

    public void setSaddled(boolean saddled) {
        this.entityData.set(SADDLED, Boolean.valueOf(saddled));
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
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
            return 2.1;
        } else {
            return 2.35;
        }
    }


    public void tick() {
        super.tick();

        if (this.isSwinging() && !hasSwung()) {
            setSwinging(false);
            setHasSwung(true);
            performAttack();
            this.attackCooldown = ATTACK_COOLDOWN;
        }

        if (attackCooldown <= 0) {
            setHasSwung(false);
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

    private static final double DELTA_Y_FOR_COLLISION = 0.5;
    private static final float ROTATION_MULTIPLIER = 0.5F;
    private static final float MOVEMENT_SPEED_MULTIPLIER = 1.5F;


    @Override
    public void travel(Vec3 destination) {
        LivingEntity passenger = (LivingEntity) this.getControllingPassenger();
        if (this.isVehicle() && passenger != null) {
            double delta = DELTA_Y_FOR_COLLISION;
            this.setYRot(passenger.getYRot());
            this.yRotO = this.getYRot();
            this.setXRot(passenger.getXRot() * ROTATION_MULTIPLIER);
            this.setRot(this.getYRot(), this.getXRot());
            this.yBodyRot = this.getYRot();
            this.yHeadRot = this.yBodyRot;

            float f = (float) (passenger.xxa * 0.5);
            float f1 = passenger.zza;

            if (f1 <= 0.0F) {
                f1 *= 0.25;
            }


            this.setSpeed((float) (this.getAttributeValue(Attributes.MOVEMENT_SPEED) * MOVEMENT_SPEED_MULTIPLIER));
            super.travel(new Vec3((double) f, destination.y, (double) f1));
        } else {
            super.travel(destination);
        }
    }

    @Override
    public float getStepHeight() {
        return 1.2F;
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

    public int getCommand() {
        return this.entityData.get(COMMAND).intValue();
    }

    public void setCommand(int command) {
        this.entityData.set(COMMAND, Integer.valueOf(command));
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    //Blue

    public boolean isBlue() {
        return this.entityData.get(BLUE).booleanValue();
    }

    public void setBlue(boolean green) {
        boolean prev = isBlue();
        if (!prev && green) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
            this.setHealth(30.0F);
        } else {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        }
        this.heal(this.getMaxHealth());
        this.entityData.set(BLUE, green);
    }

    //Yellow

    public boolean isYellow() {
        return this.entityData.get(YELLOW).booleanValue();
    }

    public void setYellow(boolean green) {
        boolean prev = isYellow();
        if (!prev && green) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
            this.setHealth(25.0F);
        } else {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(8.0D);
        }
        this.heal(this.getMaxHealth());
        this.entityData.set(YELLOW, green);
    }

    //White

    public boolean isWhite() {
        return this.entityData.get(WHITE).booleanValue();
    }

    public void setWhite(boolean green) {
        boolean prev = isWhite();
        if (!prev && green) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(50.0D);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
            this.setHealth(25.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(40.0D);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(8.0D);
        }
        this.heal(this.getMaxHealth());
        this.entityData.set(WHITE, green);
    }

    //Orange

    public boolean isOrange() {
        return this.entityData.get(ORANGE).booleanValue();
    }

    public void setOrange(boolean green) {
        boolean prev = isOrange();
        if (!prev && green) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(12.0D);
            this.setHealth(25.0F);
        } else {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(8.0D);
        }
        this.heal(this.getMaxHealth());
        this.entityData.set(ORANGE, green);
    }

    //Brown

    public boolean isBrown() {
        return this.entityData.get(BROWN).booleanValue();
    }

    public void setBrown(boolean green) {
        boolean prev = isBrown();
        if (!prev && green) {
            this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(10.0D);
            this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(10.0D);
            this.getAttribute(Attributes.ARMOR).setBaseValue(10.0D);
            this.setHealth(25.0F);
        } else {
            this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.6D);
            this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(0.0D);
            this.getAttribute(Attributes.ARMOR).setBaseValue(0.0D);
        }
        this.heal(this.getMaxHealth());
        this.entityData.set(BROWN, green);
    }
    public void determineVariant(int variantChange){
        if (variantChange <= 30) {
            this.setWhite(true);
            this.setVariant(1);
        } else if (variantChange <= 40 && variantChange > 30) {
            this.setYellow(true);
            this.setVariant(2);
        } else if (variantChange <= 60 && variantChange > 40) {
            this.setOrange(true);
            this.setVariant(3);

        }else if (variantChange <= 80 && variantChange > 60) {
            this.setBrown(true);
            this.setVariant(4);
        }else {
            this.setBlue(true);
            this.setVariant(0);
        }
    }

    @javax.annotation.Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @javax.annotation.Nullable SpawnGroupData spawnDataIn, @javax.annotation.Nullable CompoundTag dataTag) {
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
        return 5;
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
        entity.hurt(DamageSource.mobAttack(this), 5.0F);
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
    public boolean shouldFollow() {
        return this.getCommand() == 1;
    }


    class IMeleeAttackGoal extends MeleeAttackGoal {
        public IMeleeAttackGoal() {
            super(EntityUlughbegsaurus.this, 1.6D, true);
        }

        protected double getAttackReachSqr(LivingEntity p_25556_) {
            return (double) (this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 0.66F + p_25556_.getBbWidth());
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            double d0 = this.getAttackReachSqr(enemy);
            if (distToEnemySqr <= d0 && this.getTicksUntilNextAttack() <= 0) {
                this.resetAttackCooldown();
                ((EntityUlughbegsaurus) this.mob).setHungry(false);
                ((EntityUlughbegsaurus) this.mob).attack(enemy);
                ((EntityUlughbegsaurus) this.mob).setTimeTillHungry(mob.getRandom().nextInt(300) + 300);
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
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInSittingPose()) {
            if (this.isSprinting() || !this.getPassengers().isEmpty()) {
                event.getController().setAnimation(new AnimationBuilder().loop("animation.ulugh.sprint"));
                event.getController().setAnimationSpeed(2.0D);
                return PlayState.CONTINUE;
            } else if (event.isMoving()) {
                event.getController().setAnimation(new AnimationBuilder().loop("animation.ulugh.walk"));
                event.getController().setAnimationSpeed(1.0D);
                return PlayState.CONTINUE;
            }
        }


        if (this.isInSittingPose()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.ulugh.sitting"));
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().loop("animation.ulugh.idle"));
        event.getController().setAnimationSpeed(1.0F);
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState eatPredicate(AnimationEvent<E> event) {
        if (this.getEatingTime() > 0) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.ulugh.eating"));
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if ((isSwinging()) && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            //setSwinging(false);
            //setHasSwung(true);
            //this.swinging = false;
            event.getController().setAnimation(new AnimationBuilder().playOnce("animation.ulugh.bite"));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(5);
        AnimationController<EntityUlughbegsaurus> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        data.addAnimationController(new AnimationController<>(this, "eatController", 5, this::eatPredicate));
        data.addAnimationController(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
        data.addAnimationController(controller);
    }

}