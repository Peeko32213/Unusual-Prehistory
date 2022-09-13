package com.peeko32213.unusualprehistory.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class EntityBeelzebufo extends Animal implements Saddleable, IAnimatable, ItemSteerable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private static final EntityDataAccessor<Boolean> DATA_SADDLE_ID = SynchedEntityData.defineId(EntityBeelzebufo.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_BOOST_TIME = SynchedEntityData.defineId(EntityBeelzebufo.class, EntityDataSerializers.INT);
    public static final Ingredient FOOD_ITEMS = Ingredient.of(Items.BEEF, Items.PORKCHOP, Items.CHICKEN);
    private final ItemBasedSteering steering = new ItemBasedSteering(this.entityData, DATA_BOOST_TIME, DATA_SADDLE_ID);

    public EntityBeelzebufo(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }



    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.16D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.FOLLOW_RANGE, 12.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Bee.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Silverfish.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Chicken.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Rabbit.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, CaveSpider.class, true));
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        this.level.broadcastEntityEvent(this, (byte)4);
        float f = this.getAttackDamage();
        float f1 = (int)f > 0 ? f / 2.0F + (float)this.random.nextInt((int)f) : f;
        boolean flag = entityIn.hurt(DamageSource.mobAttack(this), f1);
        if (flag) {
            entityIn.setDeltaMovement(entityIn.getDeltaMovement().add(0.0D, (double)0.4F, 0.0D));
            this.doEnchantDamageEffects(this, entityIn);
        }
        return flag;
    }

    private float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }


    @Nullable
    public Entity getControllingPassenger() {
        return this.getFirstPassenger();
    }

    public boolean canBeControlledByRider() {
        Entity entity = this.getControllingPassenger();
        if (!(entity instanceof Player)) {
            return false;
        } else {
            Player player = (Player)entity;
            return player.getMainHandItem().is(Items.CARROT_ON_A_STICK) || player.getOffhandItem().is(Items.CARROT_ON_A_STICK);
        }
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> p_29480_) {
        if (DATA_BOOST_TIME.equals(p_29480_) && this.level.isClientSide) {
            this.steering.onSynced();
        }

        super.onSyncedDataUpdated(p_29480_);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_SADDLE_ID, false);
        this.entityData.define(DATA_BOOST_TIME, 0);
    }

    public void addAdditionalSaveData(CompoundTag p_29495_) {
        super.addAdditionalSaveData(p_29495_);
        this.steering.addAdditionalSaveData(p_29495_);
    }

    public void readAdditionalSaveData(CompoundTag p_29478_) {
        super.readAdditionalSaveData(p_29478_);
        this.steering.readAdditionalSaveData(p_29478_);
    }

    public InteractionResult mobInteract(Player p_29489_, InteractionHand p_29490_) {
        boolean flag = this.isFood(p_29489_.getItemInHand(p_29490_));
        if (!flag && this.isSaddled() && !this.isVehicle() && !p_29489_.isSecondaryUseActive()) {
            if (!this.level.isClientSide) {
                p_29489_.startRiding(this);
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else {
            InteractionResult interactionresult = super.mobInteract(p_29489_, p_29490_);
            if (!interactionresult.consumesAction()) {
                ItemStack itemstack = p_29489_.getItemInHand(p_29490_);
                return itemstack.is(Items.SADDLE) ? itemstack.interactLivingEntity(p_29489_, this, p_29490_) : InteractionResult.PASS;
            } else {
                return interactionresult;
            }
        }
    }

    public boolean isSaddleable() {
        return this.isAlive() && !this.isBaby();
    }

    protected void dropEquipment() {
        super.dropEquipment();
        if (this.isSaddled()) {
            this.spawnAtLocation(Items.SADDLE);
        }

    }

    public boolean isSaddled() {
        return this.steering.hasSaddle();
    }

    public void equipSaddle(@Nullable SoundSource p_29476_) {
        this.steering.setSaddle(true);
        if (p_29476_ != null) {
            this.level.playSound((Player)null, this, SoundEvents.PIG_SADDLE, p_29476_, 0.5F, 1.0F);
        }

    }

    public Vec3 getDismountLocationForPassenger(LivingEntity p_29487_) {
        Direction direction = this.getMotionDirection();
        if (direction.getAxis() == Direction.Axis.Y) {
            return super.getDismountLocationForPassenger(p_29487_);
        } else {
            int[][] aint = DismountHelper.offsetsForDirection(direction);
            BlockPos blockpos = this.blockPosition();
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for(Pose pose : p_29487_.getDismountPoses()) {
                AABB aabb = p_29487_.getLocalBoundsForPose(pose);

                for(int[] aint1 : aint) {
                    blockpos$mutableblockpos.set(blockpos.getX() + aint1[0], blockpos.getY(), blockpos.getZ() + aint1[1]);
                    double d0 = this.level.getBlockFloorHeight(blockpos$mutableblockpos);
                    if (DismountHelper.isBlockFloorValid(d0)) {
                        Vec3 vec3 = Vec3.upFromBottomCenterOf(blockpos$mutableblockpos, d0);
                        if (DismountHelper.canDismountTo(this.level, p_29487_, aabb.move(vec3))) {
                            p_29487_.setPose(pose);
                            return vec3;
                        }
                    }
                }
            }

            return super.getDismountLocationForPassenger(p_29487_);
        }
    }

    public void travel(Vec3 p_29506_) {
        this.travel(this, this.steering, p_29506_);
    }

    public float getSteeringSpeed() {
        return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.225F;
    }

    public void travelWithInput(Vec3 p_29482_) {
        super.travel(p_29482_);
    }

    public boolean boost() {
        return this.steering.boost(this.getRandom());
    }

    public Pig getBreedOffspring(ServerLevel p_149001_, AgeableMob p_149002_) {
        return null;
    }

    public boolean isFood(ItemStack p_29508_) {
        return FOOD_ITEMS.test(p_29508_);
    }




    public void positionRider(Entity passenger) {
        float ySin = Mth.sin(this.yBodyRot * ((float)Math.PI / 180F));
        float yCos = Mth.cos(this.yBodyRot * ((float)Math.PI / 180F));
        passenger.setPos(this.getX() + (double)(0.5F * ySin), this.getY() + this.getPassengersRidingOffset() + passenger.getMyRidingOffset() - 0.1F, this.getZ() - (double)(0.5F * yCos));
    }

    public double getPassengersRidingOffset() {
        return 0.6D;
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
            {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.beelzebufo.walk", true));
                event.getController().setAnimationSpeed(1.5D);
            }
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.beelzebufo.idle", true));
            event.getController().setAnimationSpeed(1.0D);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(10);
        data.addAnimationController(new AnimationController<>(this, "controller", 10, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

}
