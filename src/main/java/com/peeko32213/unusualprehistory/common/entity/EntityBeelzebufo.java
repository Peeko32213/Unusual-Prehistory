package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.util.CustomRandomStrollGoal;
import com.peeko32213.unusualprehistory.common.entity.util.CustomRideGoal;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;

public class EntityBeelzebufo extends Animal implements IAnimatable, PlayerRideableJumping {
    private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(EntityBeelzebufo.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Byte> DATA_FLAG = SynchedEntityData.defineId(EntityBeelzebufo.class, EntityDataSerializers.BYTE);
    public static final Ingredient FOOD_ITEMS = Ingredient.of(Items.BEEF, Items.PORKCHOP, Items.CHICKEN);

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);    protected float playerJumpPendingScale;
    private boolean allowStandSliding;
    private int standCounter;
    protected boolean isJumping;



    public EntityBeelzebufo(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1F)
                .add(Attributes.JUMP_STRENGTH, 1.5);
    }


    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 2D, false));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, EntityTyrannosaurusRex.class, 8.0F, 1.6D, 1.4D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(2, new CustomRideGoal(this, 1.5D));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new EntityBeelzebufo.IMeleeAttackGoal());
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, false, false, entity -> entity.getType().is(UPTags.BEELZE_TARGETS)));

    }

    public void checkDespawn() {
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

    public void killed(ServerLevel world, LivingEntity entity) {
        this.heal(10);
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SADDLED, false);
        this.entityData.define(DATA_FLAG, (byte)0);

    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSaddled(compound.getBoolean("Saddle"));

    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Saddle", this.isSaddled());
    }

    public void tick() {
        super.tick();
        if (this.getControllingPassenger() != null && this.getControllingPassenger() instanceof Player rider) {

        }
        if ((this.isControlledByLocalInstance() || this.isEffectiveAi()) && this.standCounter > 0 && ++this.standCounter > 20) {
            this.standCounter = 0;
            this.setStanding(false);
        }
    }

    public void travel(Vec3 p_30633_) {
        if (this.isAlive()) {
            LivingEntity livingentity = (LivingEntity)this.getControllingPassenger();
            if (this.isVehicle() && this.isSaddled() && livingentity != null) {
                this.setYRot(livingentity.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(livingentity.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;
                float f = livingentity.xxa * 0.5F;
                float f1 = livingentity.zza;

                if (this.onGround && this.playerJumpPendingScale == 0.0F && this.isStanding() && !this.allowStandSliding) {
                    f = 0.0F;
                    f1 = 0.0F;
                }

                if (this.playerJumpPendingScale > 0.0F && !this.isJumping() && this.onGround) {
                    double d0 = this.getCustomJump() * (double)this.playerJumpPendingScale * (double)this.getBlockJumpFactor();
                    double d1 = d0 + this.getJumpBoostPower();
                    Vec3 vec3 = this.getDeltaMovement();
                    this.setDeltaMovement(vec3.x, d1, vec3.z);
                    this.setIsJumping(true);
                    this.hasImpulse = true;
                    ForgeHooks.onLivingJump(this);
                    if (f1 > 0.0F) {
                        float f2 = Mth.sin(this.getYRot() * ((float)Math.PI / 180F));
                        float f3 = Mth.cos(this.getYRot() * ((float)Math.PI / 180F));
                        this.setDeltaMovement(this.getDeltaMovement().add((-0.4F * f2 * this.playerJumpPendingScale), 0.0D, (0.4F * f3 * this.playerJumpPendingScale)));
                    }

                    this.playerJumpPendingScale = 0.0F;
                }

                this.flyingSpeed = this.getSpeed() * 0.1F;
                if (this.isControlledByLocalInstance()) {
                    this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    super.travel(new Vec3(f, p_30633_.y, f1));
                } else if (livingentity instanceof Player) {
                    this.setDeltaMovement(Vec3.ZERO);
                }

                if (this.onGround) {
                    this.playerJumpPendingScale = 0.0F;
                    this.setIsJumping(false);
                }

                this.calculateEntityAnimation(this, false);
                this.tryCheckInsideBlocks();
            } else {
                this.flyingSpeed = 0.02F;
                super.travel(p_30633_);
            }
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean shouldHurt;
        float damage = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float knockback = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (target instanceof LivingEntity livingEntity) {
            damage += livingEntity.getMobType().equals(MobType.ARTHROPOD) ? damage : 0;
            knockback += (float) EnchantmentHelper.getKnockbackBonus(this);
        }
        if (shouldHurt = target.hurt(DamageSource.mobAttack(this), damage)) {
            if (knockback > 0.0f && target instanceof LivingEntity) {
                ((LivingEntity)target).knockback(knockback * 0.5f, Mth.sin(this.getYRot() * ((float)Math.PI / 180)), -Mth.cos(this.getYRot() * ((float)Math.PI / 180)));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
            }
            this.doEnchantDamageEffects(this, target);
            this.setLastHurtMob(target);
        }
        if (shouldHurt && target instanceof LivingEntity livingEntity) {
            this.playSound(UPSounds.BEELZE_ATTACK.get(), 0.1F, 1.0F);
            if(random.nextInt(15) == 0 && this.getTarget() instanceof LivingEntity){
                this.spawnAtLocation(UPItems.FROG_SALIVA.get());
            }
        }
        return shouldHurt;
    }

    @Nullable
    public Entity getControllingPassenger() {
        for (Entity passenger : this.getPassengers()) {
            if (passenger instanceof Player) {
                Player player = (Player) passenger;
                if (player.getMainHandItem().getItem() == UPItems.MEAT_ON_A_STICK.get() || player.getOffhandItem().getItem() == UPItems.MEAT_ON_A_STICK.get()) {
                    return player;
                }
            }
        }
        return null;
    }

    public void positionRider(Entity passenger) {
        float ySin = Mth.sin(this.yBodyRot * ((float)Math.PI / 180F));
        float yCos = Mth.cos(this.yBodyRot * ((float)Math.PI / 180F));
        passenger.setPos(this.getX() + (double)(0.5F * ySin), this.getY() + this.getPassengersRidingOffset() + passenger.getMyRidingOffset() - 0.1F, this.getZ() - (double)(0.5F * yCos));
    }

    public double getPassengersRidingOffset() {
        return 0.6D;
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if (item == Items.SADDLE && !this.isSaddled()) {
            if (!player.isCreative()) {
                itemstack.shrink(1);
            }
            this.setSaddled(true);
            return InteractionResult.SUCCESS;
        }
        else if (itemstack.getItem() == Items.SHEARS && this.isSaddled()) {
            this.setSaddled(false);
            this.spawnAtLocation(Items.SADDLE);
            return InteractionResult.SUCCESS;
        }
        InteractionResult type = super.mobInteract(player, hand);
        if (type != InteractionResult.SUCCESS && !isFood(itemstack)) {
            if (!player.isShiftKeyDown() && this.isSaddled()) {
                player.startRiding(this);
                return InteractionResult.SUCCESS;
            }
        }
        return type;
    }

    public boolean isFood(ItemStack stack) {
        return stack.getItem() == Items.PORKCHOP;
    }

    public boolean isSaddled() {
        return this.entityData.get(SADDLED).booleanValue();
    }

    public void setSaddled(boolean saddled) {
        this.entityData.set(SADDLED, saddled);
    }

    protected float getWaterSlowDown() {
        return 0.98F;
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.hasCustomName();
    }

    public boolean removeWhenFarAway(double d) {
        return !this.hasCustomName();
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.BEELZE_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.BEELZE_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.BEELZE_DEATH.get();
    }

    protected void dropEquipment() {
        super.dropEquipment();
        if (this.isSaddled()) {
            if (!this.level.isClientSide) {
                this.spawnAtLocation(Items.SADDLE);
            }
        }
        this.setSaddled(false);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
            {
                event.getController().setAnimation(new AnimationBuilder().loop("animation.beelzebufo.walk"));
                event.getController().setAnimationSpeed(0.8D);
            }
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.beelzebufo.idle"));
            event.getController().setAnimationSpeed(1.0D);
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if (this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().playOnce("animation.beelzebufo.bite"));
            event.getController().setAnimationSpeed(0.9D);

            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(5);
        AnimationController<EntityBeelzebufo> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        data.addAnimationController(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
        data.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public void onPlayerJump(int p_30591_) {
        if (this.isSaddled()) {
            if (p_30591_ < 0) {
                p_30591_ = 0;
            } else {
                this.allowStandSliding = true;
                this.stand();
            }

            if (p_30591_ >= 90) {
                this.playerJumpPendingScale = 1.0F;
            } else {
                this.playerJumpPendingScale = 0.4F + 0.4F * (float)p_30591_ / 90.0F;
            }

        }
    }

    public void setStanding(boolean p_30666_) {
        this.setFlag(32, p_30666_);
    }

    private void stand() {
        if (this.isControlledByLocalInstance() || this.isEffectiveAi()) {
            this.standCounter = 1;
            this.setStanding(true);
        }

    }

    protected boolean getFlag(int p_30648_) {
        return (this.entityData.get(DATA_FLAG) & p_30648_) != 0;
    }

    public boolean isStanding() {
        return this.getFlag(32);
    }

    protected void setFlag(int p_30598_, boolean p_30599_) {
        byte b0 = this.entityData.get(DATA_FLAG);
        if (p_30599_) {
            this.entityData.set(DATA_FLAG, (byte)(b0 | p_30598_));
        } else {
            this.entityData.set(DATA_FLAG, (byte)(b0 & ~p_30598_));
        }

    }

    @Override
    public boolean canJump() {
        return this.isSaddled();
    }

    @Override
    public void handleStartJump(int p_21695_) {
        this.allowStandSliding = true;
        this.stand();
    }

    @Override
    public void handleStopJump() {

    }

    protected double generateRandomJumpStrength() {
        return (double)0.4F + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D;
    }

    public double getCustomJump() {
        return this.getAttributeValue(Attributes.JUMP_STRENGTH);
    }

    public boolean isJumping() {
        return this.isJumping;
    }

    public void setIsJumping(boolean p_30656_) {
        this.isJumping = p_30656_;
    }

    class IMeleeAttackGoal extends MeleeAttackGoal {
        public IMeleeAttackGoal() {
            super(EntityBeelzebufo.this, 1.0D, true);
        }

        protected double getAttackReachSqr(LivingEntity p_25556_) {
            return (this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 0.66F + p_25556_.getBbWidth());
        }

    }

    protected void randomizeAttributes() {
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_28134_, DifficultyInstance p_28135_, MobSpawnType p_28136_, @Nullable SpawnGroupData p_28137_, @Nullable CompoundTag p_28138_) {
        p_28137_ = super.finalizeSpawn(p_28134_, p_28135_, p_28136_, p_28137_, p_28138_);
        Level level = p_28134_.getLevel();
        if (level instanceof ServerLevel) {
            this.setPersistenceRequired();
        }
        this.randomizeAttributes();
        return p_28137_;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    public boolean causeFallDamage(float p_149499_, float p_149500_, DamageSource p_149501_) {

        int i = this.calculateFallDamage(p_149499_, p_149500_);
        if (i <= 0) {
            return false;
        } else {
            this.hurt(p_149501_, (float)i);
            if (this.isVehicle()) {
                for(Entity entity : this.getIndirectPassengers()) {
                    entity.hurt(p_149501_, (float)i);
                }
            }

            this.playBlockFallSound();
            return true;
        }
    }

    protected int calculateFallDamage(float p_149389_, float p_149390_) {
        return super.calculateFallDamage(p_149389_, p_149390_) - 10;
    }

}
