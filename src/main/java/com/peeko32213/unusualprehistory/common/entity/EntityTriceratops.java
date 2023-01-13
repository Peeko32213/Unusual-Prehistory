package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.util.*;
import com.peeko32213.unusualprehistory.core.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
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
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.util.GeckoLibUtil;
public class EntityTriceratops extends TamableAnimal implements IAnimatable, CustomFollower {
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.of(UPItems.GINKGO_FRUIT.get());

    private static final TagKey<Item> FOOD = ForgeRegistries.ITEMS.tags().createTagKey(new ResourceLocation(UnusualPrehistory.MODID, "trike_food"));

    private EatBlockGoal eatBlockGoal;
    private int eatAnimationTick;
    private int riderAttackCooldown = 0;
    public float prevSitProgress;
    public float sitProgress;
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);    private static final EntityDataAccessor<Integer> CHARGE_COOLDOWN_TICKS = SynchedEntityData.defineId(EntityTriceratops.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_TARGET = SynchedEntityData.defineId(EntityTriceratops.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(EntityTriceratops.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(EntityTriceratops.class, EntityDataSerializers.BOOLEAN);

    private int stunnedTick;
    private boolean canBePushed = true;

    public EntityTriceratops(EntityType<? extends TamableAnimal > entityType, Level level) {
        super(entityType, level);
        this.maxUpStep = 1.0f;
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 90.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.3D);
    }

    public void checkDespawn() {
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.eatBlockGoal = new EatBlockGoal(this);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new EntityTriceratops.TrikeMeleeAttackGoal(this, 1.5D, false));
        this.goalSelector.addGoal(2, new EntityTriceratops.TrikePrepareChargeGoal(this));
        this.goalSelector.addGoal(3, new EntityTriceratops.TrikeChargeGoal(this, 2.5F));
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, this.eatBlockGoal);
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(2, new TrikeNearestAttackablePlayerTargetGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new CustomRideGoal(this, 2.5F));
        this.goalSelector.addGoal(3, new TameableFollowOwner(this, 1.2D, 6.0F, 3.0F, false));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(5, new TameableTempt(this, 1.1D, TEMPTATION_ITEMS, false));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, EntityTyrannosaurusRex.class, false));


    }

    public boolean isFood(ItemStack stack) {
        return stack.is(FOOD);
    }

    public boolean isAngryAt(LivingEntity p_21675_) {
        return this.canAttack(p_21675_);
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.TRIKE_IDLE;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.TRIKE_HURT;
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.TRIKE_DEATH;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return UPEntities.TRIKE.get().create(serverLevel);
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
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CHARGE_COOLDOWN_TICKS, 0);
        this.entityData.define(HAS_TARGET, false);
        this.entityData.define(COMMAND, 0);
        this.entityData.define(SADDLED, Boolean.valueOf(false));

    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Saddle", this.isSaddled());
        compound.putInt("TrikeCommand", this.getCommand());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSaddled(compound.getBoolean("Saddle"));
        this.setCommand(compound.getInt("TrikeCommand"));


    }

    @Override
    public boolean canAttack(LivingEntity entity) {
        boolean prev = super.canAttack(entity);
        if(prev && isBaby()){
            return false;
        }
        return prev;
    }

    public int getCommand() {
        return this.entityData.get(COMMAND).intValue();
    }

    public void setCommand(int command) {
        this.entityData.set(COMMAND, Integer.valueOf(command));
    }

    public void tick() {
        super.tick();
        if(riderAttackCooldown > 0){
            riderAttackCooldown--;
        }
        if(this.getControllingPassenger() != null && this.getControllingPassenger() instanceof Player){
            Player rider = (Player)this.getControllingPassenger();
            if(rider.getLastHurtMob() != null && this.distanceTo(rider.getLastHurtMob()) < this.getBbWidth() + 3F && !this.isAlliedTo(rider.getLastHurtMob())){
                UUID preyUUID = rider.getLastHurtMob().getUUID();
                if (!this.getUUID().equals(preyUUID) && riderAttackCooldown == 0) {
                    doHurtTarget(rider.getLastHurtMob());
                    riderAttackCooldown = 20;
                }
            }
        }
        if (this.isOrderedToSit() && sitProgress < 5F) {
            sitProgress++;
        }
        if (!this.isOrderedToSit() && sitProgress > 0F) {
            sitProgress--;
        }
        if(this.getCommand() == 2 && !this.isVehicle()){
            this.setOrderedToSit(true);
        }else{
            this.setOrderedToSit(false);
        }
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

    @Nullable
    public Entity getControllingPassenger() {
        for (Entity passenger : this.getPassengers()) {
            if (passenger instanceof Player) {
                Player player = (Player) passenger;
                return player;
            }
        }
        return null;
    }

    public void positionRider(Entity passenger) {
        float ySin = Mth.sin(this.yBodyRot * ((float)Math.PI / 180F));
        float yCos = Mth.cos(this.yBodyRot * ((float)Math.PI / 180F));
        passenger.setPos(this.getX() + (double)(0.5F * ySin), this.getY() + this.getPassengersRidingOffset() + passenger.getMyRidingOffset() + 0.4F, this.getZ() - (double)(0.5F * yCos));
    }

    public double getPassengersRidingOffset() {
        float f = Math.min(0.25F, this.animationSpeed);
        float f1 = this.animationPosition;
        return (double)this.getBbHeight() - 0.2D + (double)(0.12F * Mth.cos(f1 * 0.7F) * 0.7F * f);
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
            } else {
                if (!player.isShiftKeyDown() && !this.isBaby() && this.isSaddled()) {
                    player.startRiding(this);
                    return InteractionResult.SUCCESS;
                } else {
                    this.setCommand((this.getCommand() + 1) % 3);

                    if (this.getCommand() == 3) {
                        this.setCommand(0);
                    }
                    player.displayClientMessage(new TranslatableComponent("entity.unusualprehistory.all.command_" + this.getCommand(), this.getName()), true);
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

    public boolean isSaddled() {
        return this.entityData.get(SADDLED).booleanValue();
    }

    public void setSaddled(boolean saddled) {
        this.entityData.set(SADDLED, Boolean.valueOf(saddled));
    }


    private boolean isWithinYRange(LivingEntity target) {
        if (target == null) {
            return false;
        }
        return Math.abs(target.getY() - this.getY()) < 3;
    }

    @Override
    public boolean isPushable() {
        return this.canBePushed;
    }

    public void resetChargeCooldownTicks() {
        this.entityData.set(CHARGE_COOLDOWN_TICKS, 20);
    }

    public boolean hasChargeCooldown() {
        return this.entityData.get(CHARGE_COOLDOWN_TICKS) > 0;
    }

    public void setChargeCooldownTicks(int ticks) {
        this.entityData.set(CHARGE_COOLDOWN_TICKS, ticks);
    }

    public int getChargeCooldownTicks() {
        return this.entityData.get(CHARGE_COOLDOWN_TICKS);
    }

    public void setHasTarget(boolean hasTarget) {
        this.entityData.set(HAS_TARGET, hasTarget);
    }


    static class TrikePrepareChargeGoal extends Goal {
        protected final EntityTriceratops majunga;

        public TrikePrepareChargeGoal(EntityTriceratops majunga) {
            this.majunga = majunga;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.majunga.getTarget();
            if (target == null || !target.isAlive() || !this.majunga.isWithinYRange(target)) {
                this.majunga.resetChargeCooldownTicks();
                return false;
            }
            return target instanceof Player && majunga.hasChargeCooldown();
        }

        @Override
        public void start() {
            LivingEntity target = this.majunga.getTarget();
            if (target == null) {
                return;
            }
            this.majunga.setHasTarget(true);
            this.majunga.resetChargeCooldownTicks();
            this.majunga.canBePushed = false;
        }

        @Override
        public void stop() {
            this.majunga.setHasTarget(false);
            this.majunga.canBePushed = true;
        }

        @Override
        public void tick() {
            LivingEntity target = this.majunga.getTarget();
            if (target == null) {
                return;
            }
            this.majunga.getLookControl().setLookAt(target);
            this.majunga.setChargeCooldownTicks(Math.max(0, this.majunga.getChargeCooldownTicks() - 1));
        }
    }

    static class TrikeMeleeAttackGoal extends MeleeAttackGoal {
        public TrikeMeleeAttackGoal(PathfinderMob pathfinderMob, double speedMultiplier, boolean followingTargetEvenIfNotSeen) {
            super(pathfinderMob, speedMultiplier, followingTargetEvenIfNotSeen);
        }

        protected double getAttackReachSqr(LivingEntity p_179512_1_) {
            return (double)(this.mob.getBbWidth() * 1.8F * this.mob.getBbWidth() * 1.1F + p_179512_1_.getBbWidth());
        }
    }

    protected void ageBoundaryReached() {
        super.ageBoundaryReached();
        if (!this.isBaby() && this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            this.spawnAtLocation(UPItems.TRIKE_HORN.get(), 1);
        }

    }

    @Override
    public void customServerAiStep() {
        this.eatAnimationTick = this.eatBlockGoal.getEatAnimationTick();
        if (this.getMoveControl().hasWanted()) {
            this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.5D);
        } else {
            this.setSprinting(false);
        }
        super.customServerAiStep();
    }

    @Override
    public float getVoicePitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f;
    }

    static class TrikeChargeGoal extends Goal {
        protected final EntityTriceratops mob;
        private final double speedModifier;
        private Path path;
        private Vec3 chargeDirection;

        public TrikeChargeGoal(EntityTriceratops pathfinderMob, double speedModifier) {
            this.mob = pathfinderMob;
            this.speedModifier = speedModifier;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
            this.chargeDirection = Vec3.ZERO;
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.mob.getTarget();
            if (target == null || !target.isAlive() || this.mob.hasChargeCooldown()) {
                return false;
            }
            this.path = this.mob.getNavigation().createPath(target, 0);
            return target instanceof Player && this.path != null;
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = this.mob.getTarget();
            if (target == null || !target.isAlive() || this.mob.hasChargeCooldown()) {
                return false;
            }
            return !this.mob.getNavigation().isDone();
        }

        @Override
        public void start() {
            BlockPos blockPosition = this.mob.blockPosition();
            BlockPos target = this.path.getTarget();
            this.chargeDirection = new Vec3(blockPosition.getX() - target.getX(), 0.0, blockPosition.getZ() - target.getZ()).normalize();
            this.mob.getNavigation().moveTo(this.path, this.speedModifier);
            this.mob.setAggressive(true);
        }

        @Override
        public void stop() {
            this.mob.resetChargeCooldownTicks();
            this.mob.getNavigation().stop();
        }


        @Override
        public void tick() {
            this.mob.getLookControl().setLookAt(Vec3.atCenterOf(this.path.getTarget()));
            if (this.mob.horizontalCollision && this.mob.onGround) {
                this.mob.jumpFromGround();
            }
            if (this.mob.level.getGameTime() % 2L == 0L) {
                this.mob.playSound(UPSounds.REX_STEP, 0.5F, this.mob.getVoicePitch());
            }
            this.tryToHurt();
        }

        protected void tryToHurt() {
            List<LivingEntity> nearbyEntities = this.mob.level.getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), this.mob, this.mob.getBoundingBox());
            if (!nearbyEntities.isEmpty()) {
                LivingEntity livingEntity = nearbyEntities.get(0);
                if (!(livingEntity instanceof EntityMajungasaurus)) {
                    livingEntity.hurt(DamageSource.mobAttack(this.mob), (float) this.mob.getAttributeValue(Attributes.ATTACK_DAMAGE));
                    float speed = Mth.clamp(this.mob.getSpeed() * 1.65f, 0.2f, 3.0f);
                    float shieldBlockModifier = livingEntity.isDamageSourceBlocked(DamageSource.mobAttack(this.mob)) ? 0.5f : 1.0f;
                    livingEntity.knockback(shieldBlockModifier * speed * 2.0D, this.chargeDirection.x(), this.chargeDirection.z());
                    double knockbackResistance = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                    livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(0.0, 0.4f * knockbackResistance, 0.0));
                    this.mob.swing(InteractionHand.MAIN_HAND);
                    if (livingEntity.equals(this.mob.getTarget())) {
                        this.stop();
                    }
                }
            }
        }
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


    public boolean hasTarget() {
        return this.entityData.get(HAS_TARGET);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.level.isClientSide) {
            this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
        }
        if (this.isVehicle()) {
            if (this.horizontalCollision && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this)) {
                boolean flag = false;
                AABB axisalignedbb = this.getBoundingBox().inflate(0.2D);
                for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(axisalignedbb.minX), Mth.floor(axisalignedbb.minY), Mth.floor(axisalignedbb.minZ), Mth.floor(axisalignedbb.maxX), Mth.floor(axisalignedbb.maxY), Mth.floor(axisalignedbb.maxZ))) {
                    BlockState blockstate = this.level.getBlockState(blockpos);
                    if (blockstate.is(UPTags.TRIKE_BREAKABLES)) {
                        flag = this.level.destroyBlock(blockpos, true, this) || flag;
                    }
                }
            }
        }
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 10) {
            this.eatAnimationTick = 40;
        } else {
            super.handleEntityEvent(pId);
        }
    }

    public boolean isEating() {
        return this.eatAnimationTick > 0;
    }

    @Override
    public void ate() {
        if (this.isBaby()) {
            this.ageUp(60);
        }
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInSittingPose()) {
            if (this.isSprinting() || !this.getPassengers().isEmpty()) {
                event.getController().setAnimation(new AnimationBuilder().loop("animation.trike.run"));
                event.getController().setAnimationSpeed(2.0D);
                return PlayState.CONTINUE;
            } else if (event.isMoving()) {
                event.getController().setAnimation(new AnimationBuilder().loop("animation.trike.walk"));
                event.getController().setAnimationSpeed(1.0D);
                return PlayState.CONTINUE;
            }
        } else if (this.hasChargeCooldown() && this.hasTarget() && !this.isInSittingPose()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.trike.prep"));
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }

        if (this.isInSittingPose()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.trike.sit"));
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }


        event.getController().setAnimation(new AnimationBuilder().loop("animation.trike.idle"));
        event.getController().setAnimationSpeed(1.0F);
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if (this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().playOnce("animation.trike.attack"));
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState eatPredicate(AnimationEvent<E> event) {
        if (this.isEating()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.trike.eat"));
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(5);
        AnimationController<EntityTriceratops> controller = new AnimationController<>(this, "controller", 2, this::predicate);
        data.addAnimationController(new AnimationController<>(this, "eatController", 2, this::eatPredicate));
        data.addAnimationController(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
        data.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    static class TrikeNearestAttackablePlayerTargetGoal extends NearestAttackableTargetGoal<Player> {
        private final EntityTriceratops trike;

        public TrikeNearestAttackablePlayerTargetGoal(EntityTriceratops mob) {
            super(mob, Player.class, 10, true, true, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
            this.trike = mob;
        }

        @Override
        public boolean canUse() {
            if (this.trike.isBaby()) {
                return false;
            }
            if (super.canUse()) {
                if (!trike.isWithinYRange(target)) {
                    return false;
                }
                List<EntityTriceratops> nearbyEntities = this.trike.level.getEntitiesOfClass(EntityTriceratops.class, this.trike.getBoundingBox().inflate(8.0, 4.0, 8.0));
                for (EntityTriceratops mob : nearbyEntities) {
                    if (!mob.isBaby()) continue;
                    return true;
                }
            }
            return false;
        }
    }


    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_28134_, DifficultyInstance p_28135_, MobSpawnType p_28136_, @Nullable SpawnGroupData p_28137_, @Nullable CompoundTag p_28138_) {
        p_28137_ = super.finalizeSpawn(p_28134_, p_28135_, p_28136_, p_28137_, p_28138_);
        Level level = p_28134_.getLevel();
        if (level instanceof ServerLevel) {
            {
                this.setPersistenceRequired();
            }
        }
        return p_28137_;
    }

    @Override
    public boolean shouldFollow() {
        return this.getCommand() == 1;
    }


}
