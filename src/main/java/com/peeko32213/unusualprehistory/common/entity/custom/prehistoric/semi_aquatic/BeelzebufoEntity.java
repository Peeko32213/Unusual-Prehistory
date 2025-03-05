package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.semi_aquatic;

import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.BeelzebufoTadpoleEntity;
import com.peeko32213.unusualprehistory.common.entity.util.goal.CustomRandomStrollGoal;
import com.peeko32213.unusualprehistory.common.entity.util.goal.CustomRideGoal;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import com.peeko32213.unusualprehistory.core.registry.util.UPMath;
import net.minecraft.ChatFormatting;
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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class BeelzebufoEntity extends PrehistoricEntity implements PlayerRideableJumping {
    private static final EntityDataAccessor<Byte> DATA_FLAG = SynchedEntityData.defineId(BeelzebufoEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> IS_SWALLOWING = SynchedEntityData.defineId(BeelzebufoEntity.class, EntityDataSerializers.BOOLEAN);

    public static final Ingredient FOOD_ITEMS = Ingredient.of(Items.BEEF, Items.PORKCHOP, Items.CHICKEN);
    protected float playerJumpPendingScale;
    private boolean allowStandSliding;
    private int standCounter;
    protected boolean isJumping;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation BEELZE_BITE = RawAnimation.begin().thenLoop("animation.beelzebufo.bite");
    private static final RawAnimation BEELZE_WALK = RawAnimation.begin().thenLoop("animation.beelzebufo.walk");
    private static final RawAnimation BEELZE_SWIM = RawAnimation.begin().thenLoop("animation.beelzebufo.swim");
    private static final RawAnimation BEELZE_JUMP = RawAnimation.begin().thenPlay("animation.beelzebufo.jump");
    private static final RawAnimation BEELZE_JUMP_HOLD = RawAnimation.begin().thenLoop("animation.beelzebufo.jump_hold");
    private static final RawAnimation BEELZE_IDLE = RawAnimation.begin().thenPlay("animation.beelzebufo.idle");
    private int eatCooldown = 0;
    public BeelzebufoEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1F)
                .add(Attributes.JUMP_STRENGTH, 1);
    }


    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new CustomRideGoal(this, 1.5D));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BeelzebufoEntity.EatFoodGoal(this));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34)
                {
                    @Override
                    public boolean canUse() {
                        if (this.mob.isVehicle()) {
                            return false;
                        } else {
                            if (!this.forceTrigger) {
                                if (this.mob.getNoActionTime() >= 100) {
                                    return false;
                                }
                                if (((BeelzebufoEntity) this.mob).isHungry()) {
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
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        //this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        //If its attacked they will now fight back
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAG, (byte) 0);
        this.entityData.define(IS_SWALLOWING, false);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setIsSwallowing(compound.getBoolean("swallowing"));

    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("swallowing", this.isSwallowing());
    }

    private void attack(LivingEntity entity) {
        entity.hurt(this.damageSources().mobAttack(this), 8.0F);
    }

    public void tick() {
        super.tick();
        if ((this.isControlledByLocalInstance() || this.isEffectiveAi()) && this.standCounter > 0 && ++this.standCounter > 20) {
            this.standCounter = 0;
            this.setStanding(false);
        }
        if(this.onGround() && this.isJumping()){
            setIsJumping(false);
        }

        if(eatCooldown > 0){
            eatCooldown--;
        }
    }

    public void travel(Vec3 p_30633_) {
        if (this.isAlive()) {
            LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();
            if (this.isVehicle() && this.isSaddled() && livingentity != null) {
                this.setYRot(livingentity.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(livingentity.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;
                float f = livingentity.xxa * 0.5F;
                float f1 = livingentity.zza;

                if (this.onGround() && this.playerJumpPendingScale == 0.0F && this.isStanding() && !this.allowStandSliding) {
                    f = 0.0F;
                    f1 = 0.0F;
                }

                if (this.playerJumpPendingScale > 0.0F && !this.isJumping() && this.onGround()) {
                    double d0 = this.getCustomJump() * (double) this.playerJumpPendingScale * (double) this.getBlockJumpFactor();
                    double d1 = d0 + this.getJumpBoostPower();
                    Vec3 vec3 = this.getDeltaMovement();
                    this.setDeltaMovement(vec3.x, d1, vec3.z);
                    this.setIsJumping(true);
                    this.hasImpulse = true;
                    ForgeHooks.onLivingJump(this);
                    if (f1 > 0.0F) {
                        float f2 = Mth.sin(this.getYRot() * ((float) Math.PI / 180F));
                        float f3 = Mth.cos(this.getYRot() * ((float) Math.PI / 180F));
                        this.setDeltaMovement(this.getDeltaMovement().add((-0.4F * f2 * this.playerJumpPendingScale), 0.0D, (0.4F * f3 * this.playerJumpPendingScale)));
                    }

                    this.playerJumpPendingScale = 0.0F;
                }

                this.flyDist = this.getSpeed() * 0.1F;
                if (this.isControlledByLocalInstance()) {
                    this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    super.travel(new Vec3(f, p_30633_.y, f1));
                } else if (livingentity instanceof Player) {
                    this.setDeltaMovement(Vec3.ZERO);
                }

                if (this.onGround()) {
                    this.playerJumpPendingScale = 0.0F;
                    this.setIsJumping(false);
                }

                this.calculateEntityAnimation(false);
                this.tryCheckInsideBlocks();
            } else {
                this.flyDist = 0.02F;
                super.travel(p_30633_);
            }
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean shouldHurt;
        float damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float knockback = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (target instanceof LivingEntity livingEntity) {
            damage += livingEntity.getMobType().equals(MobType.ARTHROPOD) ? damage : 0;
            knockback += (float) EnchantmentHelper.getKnockbackBonus(this);
        }
        if (shouldHurt = target.hurt(this.damageSources().mobAttack(this), damage)) {
            if (knockback > 0.0f && target instanceof LivingEntity) {
                ((LivingEntity) target).knockback(knockback * 0.5f, Mth.sin(this.getYRot() * ((float) Math.PI / 180)), -Mth.cos(this.getYRot() * ((float) Math.PI / 180)));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
            }
            this.doEnchantDamageEffects(this, target);
            this.setLastHurtMob(target);
        }
        if (shouldHurt && target instanceof LivingEntity livingEntity) {
            this.playSound(UPSounds.BEELZE_ATTACK.get(), 0.1F, 1.0F);
            if (random.nextInt(15) == 0 && this.getTarget() instanceof LivingEntity) {
                this.spawnAtLocation(UPItems.FROG_SALIVA.get());
            }
        }
        return shouldHurt;
    }

    @Nullable
    public LivingEntity getControllingPassenger() {
        for (Entity passenger : this.getPassengers()) {
            if (passenger instanceof Player player) {
                if (player.getMainHandItem().getItem() == UPItems.MEAT_ON_A_STICK.get() || player.getOffhandItem().getItem() == UPItems.MEAT_ON_A_STICK.get()) {
                    return player;
                }
            }
        }
        return null;
    }

    @Override
    protected void positionRider(Entity pPassenger, MoveFunction pCallback) {
        float ySin = Mth.sin(this.yBodyRot * ((float) Math.PI / 180F));
        float yCos = Mth.cos(this.yBodyRot * ((float) Math.PI / 180F));
        pPassenger.setPos(this.getX() + (double) (0.5F * ySin), this.getY() + this.getPassengersRidingOffset() + pPassenger.getMyRidingOffset() - 0.1F, this.getZ() - (double) (0.5F * yCos));
    }

    public double getPassengersRidingOffset() {
        if (this.isInWater()) {
            return 0.3;
        }
        else {
            return 0.65D;
        }
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if(hand == InteractionHand.MAIN_HAND) {
            if (item == Items.SADDLE && !this.isSaddled()) {
                if (!player.isCreative()) {
                    itemstack.shrink(1);
                }
                this.setSaddled(true);
                return InteractionResult.SUCCESS;
            } else if (itemstack.getItem() == Items.SHEARS && this.isSaddled()) {
                this.setSaddled(false);
                this.spawnAtLocation(Items.SADDLE);
                return InteractionResult.SUCCESS;
            }
            if (!isFood(itemstack)) {
                if (!player.isShiftKeyDown() && this.isSaddled()) {
                    if (!this.level().isClientSide) {
                        player.startRiding(this);
                        //Todo fix this: Somehow display client message doesnt work anymore....
                        player.sendSystemMessage(Component.translatable("unusualprehistory.beelzebufo.meat_stick").withStyle(ChatFormatting.WHITE));

                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }


    private boolean isFood(Entity entity) {
        return entity instanceof Mob && !(entity instanceof BeelzebufoEntity) && entity.getBbHeight() <= 1.0F || entity instanceof ItemEntity && ((ItemEntity) entity).getItem().is(Items.PORKCHOP);
    }

    public boolean isFood(ItemStack stack) {
        return stack.getItem() == Items.PORKCHOP;
    }

    private Vec3 getMouthVec(){
        final Vec3 vec3 = new Vec3(0, this.getBbHeight() * 0.25F, this.getBbWidth() * 0.8F).xRot(this.getXRot() * UPMath.piDividedBy180).yRot(-this.getYRot() * UPMath.piDividedBy180);
        return this.position().add(vec3);
    }

    public boolean swallowEntity(Entity entity) {
        this.setIsSwallowing(true);
        if (entity instanceof final LivingEntity mob) {
            if(!entity.level().isClientSide) {
                ExperienceOrb.award((ServerLevel) entity.level(), entity.position(), ((LivingEntity) entity).getExperienceReward());
                mob.remove(RemovalReason.KILLED);
                this.spawnAtLocation(UPItems.FROG_SALIVA.get());
            }
            this.gameEvent(GameEvent.EAT);
            this.playSound(SoundEvents.GENERIC_EAT, this.getSoundVolume(), this.getVoicePitch());
            this.setHungry(false);
            //setIsSwallowing(false);
            return true;
        }
        if (entity instanceof final ItemEntity item) {
            this.pickUpItem(item);
        }
        return false;
    }


    @Override
    protected void pickUpItem(ItemEntity itemEntity) {
        if (isHungry()) {
            this.onItemPickup(itemEntity);
            this.take(itemEntity, 1);
            itemEntity.discard();
            this.spawnAtLocation(UPItems.FROG_SALIVA.get());
            this.gameEvent(GameEvent.EAT);
            this.playSound(SoundEvents.GENERIC_EAT, this.getSoundVolume(), this.getVoicePitch());
            this.setHungry(false);
            //setIsSwallowing(false);
        }
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

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(SoundEvents.FROG_STEP, 0.1F, 1.0F);
    }

    @Override
    protected SoundEvent getAttackSound() {
        return null;
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
        return UPTags.BEELZE_TARGETS;
    }

    protected void dropEquipment() {
        super.dropEquipment();
        if (this.isSaddled()) {
            if (!this.level().isClientSide) {
                this.spawnAtLocation(Items.SADDLE);
            }
        }
        this.setSaddled(false);
    }


    protected <E extends BeelzebufoEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if(this.isFromBook()){
            return event.setAndContinue(BEELZE_IDLE);
        }
        if(this.isSwallowing()){
            event.setAndContinue(BEELZE_BITE);
            event.getController().setAnimationSpeed(0.9D);
            return PlayState.CONTINUE;
        }
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isJumping && !this.isInWater()) {
            event.setAndContinue(BEELZE_WALK);
            event.getController().setAnimationSpeed(0.8D);
            return PlayState.CONTINUE;
        }
        if (this.isInWater() && !this.isJumping()) {
            event.setAndContinue(BEELZE_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        else if (this.isJumping()) {
            event.setAndContinue(BEELZE_JUMP);
            return PlayState.CONTINUE;
        }
        event.setAndContinue(BEELZE_IDLE);
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
                this.playerJumpPendingScale = 0.4F + 0.4F * (float) p_30591_ / 90.0F;
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
            this.entityData.set(DATA_FLAG, (byte) (b0 | p_30598_));
        } else {
            this.entityData.set(DATA_FLAG, (byte) (b0 & ~p_30598_));
        }

    }

    public boolean isSwallowing() {
        return this.entityData.get(IS_SWALLOWING);
    }

    public void setIsSwallowing(boolean swallowing) {
        this.entityData.set(IS_SWALLOWING, swallowing);
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


    //TODO keep or remove? This is never used
    protected double generateRandomJumpStrength() {
        return (double) 0.4F + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D;
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




    class EatFoodGoal extends Goal{

        private final BeelzebufoEntity beelzebufo;
        private Entity food;

        private int executionCooldown = 50;


        public EatFoodGoal (BeelzebufoEntity beelzebufo){
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
            this.beelzebufo = beelzebufo;
        }

        @Override
        public boolean canUse() {
            if (beelzebufo.eatCooldown > 0) {
                return false;
            }
            if (executionCooldown > 0) {
                executionCooldown--;
            } else {
                executionCooldown = 50 + random.nextInt(50);
                if(beelzebufo.isHungry()){
                    final List<Entity> list = beelzebufo.level().getEntitiesOfClass(Entity.class, beelzebufo.getBoundingBox().inflate(8, 8, 8), EntitySelector.NO_SPECTATORS.and(entity -> entity != beelzebufo && beelzebufo.isFood(entity) && !(entity instanceof BeelzebufoTadpoleEntity)));
                    list.sort(Comparator.comparingDouble(beelzebufo::distanceToSqr));
                    if (!list.isEmpty()) {
                        food = list.get(0);
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            return food != null && food.isAlive() && !this.beelzebufo.isHungry();
        }

        public void stop() {
            executionCooldown = 5;
            setIsSwallowing(false);
        }

        @Override
        public void tick() {
            beelzebufo.getNavigation().moveTo(food.getX(), food.getY(0.5F), food.getZ(), 1.0F);
            beelzebufo.lookAt(food, 10F, 10F);
            final float eatDist = beelzebufo.getBbWidth() * 0.65F + food.getBbWidth();
            if (beelzebufo.distanceTo(food) < eatDist + 3 && beelzebufo.hasLineOfSight(food)) {
                //final Vec3 delta = beelzebufo.getMouthVec().subtract(food.position()).normalize().scale(0.1F);
                //food.setDeltaMovement(food.getDeltaMovement().add(delta));
                if (beelzebufo.distanceTo(food) < eatDist) {
                    if (food instanceof Player) {
                        food.hurt(this.beelzebufo.damageSources().mobAttack(beelzebufo), 12000);
                    } else if (beelzebufo.swallowEntity(food)) {
                        beelzebufo.gameEvent(GameEvent.EAT);
                        beelzebufo.playSound(SoundEvents.GENERIC_EAT, beelzebufo.getSoundVolume(), beelzebufo.getVoicePitch());
                        food.discard();
                    }
                }
            }
        }

    }
    class IMeleeAttackGoal extends MeleeAttackGoal {



        public IMeleeAttackGoal(BeelzebufoEntity beelzebufo) {
            super(beelzebufo, 1.0D, true);

        }



        protected double getAttackReachSqr(Entity p_25556_) {
            return (this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 0.66F + p_25556_.getBbWidth());
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            double d0 = this.getAttackReachSqr(enemy);
            if (distToEnemySqr <= d0 && this.getTicksUntilNextAttack() <= 0) {
                this.resetAttackCooldown();
                ((BeelzebufoEntity) this.mob).setHungry(false);
                ((BeelzebufoEntity) this.mob).attack(enemy);
                ((BeelzebufoEntity) this.mob).setTimeTillHungry(mob.getRandom().nextInt(100) + 100);
            }
        }

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
            this.hurt(p_149501_, (float) i);
            if (this.isVehicle()) {
                for (Entity entity : this.getIndirectPassengers()) {
                    entity.hurt(p_149501_, (float) i);
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
