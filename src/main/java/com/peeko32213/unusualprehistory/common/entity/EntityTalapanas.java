package com.peeko32213.unusualprehistory.common.entity;

import com.google.common.collect.Lists;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class EntityTalapanas extends EntityBaseDinosaurAnimal {
    private static final EntityDataAccessor<Optional<BlockPos>> FEEDING_POS = SynchedEntityData.defineId(EntityTalapanas.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
    private static final EntityDataAccessor<Integer> FEEDING_TIME = SynchedEntityData.defineId(EntityTalapanas.class, EntityDataSerializers.INT);
    public static final ResourceLocation TALAPANAS_REWARD = new ResourceLocation("unusualprehistory", "gameplay/talapanas_reward");
    private Ingredient temptationItems;
    public float prevFeedProgress;
    public float feedProgress;
    private int rideCooldown = 0;
    public int soundTimer = 0;

    public EntityTalapanas(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, getTemptationItems(), false));
        this.goalSelector.addGoal(5, new DigRootedDirtGoal(this));
        this.goalSelector.addGoal(1, new FleeLightGoal(this, 1.5D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    private Ingredient getTemptationItems() {
        if (temptationItems == null)
            temptationItems = Ingredient.merge(Lists.newArrayList(
                    Ingredient.of(ItemTags.LEAVES)
            ));

        return temptationItems;
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.TALAPANAS_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.TALAPANAS_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.TALAPANAS_DEATH.get();
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.1F, 1.0F);
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

    public int getFeedingTime() {
        return this.entityData.get(FEEDING_TIME);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FEEDING_TIME, 0);
        this.entityData.define(FEEDING_POS, Optional.empty());
    }


    public void setFeedingTime(int feedingTime) {
        this.entityData.set(FEEDING_TIME, feedingTime);
    }

    public void tick() {
        super.tick();
        super.tick();
        if (soundTimer > 0) {
            soundTimer--;
        }
        this.prevFeedProgress = feedProgress;
        if (this.getFeedingTime() > 0 && feedProgress < 5F) {
            feedProgress++;
        }
        if (this.getFeedingTime() <= 0 && feedProgress > 0F) {
            feedProgress--;
        }
        BlockPos feedingPos = this.entityData.get(FEEDING_POS).orElse(null);
        if (feedingPos == null) {
            float f2 = (float) -((float) this.getDeltaMovement().y * 2.2F * (double) (180F / (float) Math.PI));
            this.setXRot(f2);
        } else if (this.getFeedingTime() > 0) {
            Vec3 face = Vec3.atCenterOf(feedingPos).subtract(this.position());
            double d0 = face.horizontalDistance();
            this.setXRot((float) (-Mth.atan2(face.y, d0) * (double) (180F / (float) Math.PI)));
            this.setYRot(((float) Mth.atan2(face.z, face.x)) * (180F / (float) Math.PI) - 90F);
            this.yBodyRot = this.getYRot();
            this.yHeadRot = this.getYRot();
            BlockState state = level.getBlockState(feedingPos);
            if (random.nextInt(2) == 0 && !state.isAir()) {
                Vec3 mouth = new Vec3(0, this.getBbHeight() * 0.5F, 0.4F * -0.5).xRot(this.getXRot() * ((float) Math.PI / 180F)).yRot(-this.getYRot() * ((float) Math.PI / 180F));
                for (int i = 0; i < 4 + random.nextInt(2); i++) {
                    double motX = this.random.nextGaussian() * 0.02D;
                    double motY = 0.1F + random.nextFloat() * 0.2F;
                    double motZ = this.random.nextGaussian() * 0.02D;
                    level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), this.getX() + mouth.x, this.getY() + mouth.y, this.getZ() + mouth.z, motX, motY, motZ);
                }
            }
        }
        if (rideCooldown > 0) {
            rideCooldown--;
        }
    }

    public void rideTick() {
        Entity mount = this.getVehicle();

        if (this.isPassenger() && !mount.isAlive()) {
            this.stopRiding();
        } else if (mount instanceof Player player && this.isPassenger()) {
            this.setDeltaMovement(0, 0, 0);
            //this.yBodyRot = ((LivingEntity) player).yBodyRot;
            //this.setYRot(player.getYRot());
            //this.yHeadRot = ((LivingEntity) player).yHeadRot;
            //this.yRotO = ((LivingEntity) player).yHeadRot;
            float radius = 0F;
            float angle = (0.01745329251F * (((LivingEntity) player).yBodyRot - 180F));
            double extraX = radius * Mth.sin((float) (Math.PI + angle));
            double extraZ = radius * Mth.cos(angle);
            playPanicSound();
            this.setPos(player.getX() + extraX, Math.max(player.getY() + player.getBbHeight() + 0.1, player.getY()), player.getZ() + extraZ);
            if (!player.isAlive() || rideCooldown == 0 || player.isShiftKeyDown()) {
                this.stopRiding();
            }
        } else {
            super.rideTick();
        }

    }

    private void playPanicSound() {
        if (this.soundTimer <= 0) {
            this.playSound(UPSounds.TALAPANAS_PANIC.get(), this.getSoundVolume(), (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F);
            soundTimer = 80;
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source == DamageSource.IN_WALL || super.isInvulnerableTo(source);
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        ItemStack itemstack2 = player.getItemInHand(InteractionHand.OFF_HAND);
        Item item = itemstack.getItem();
        if (!isFood(itemstack)) {
            if (player.getPassengers().isEmpty()) {
                this.startRiding(player);
                rideCooldown = 20;
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }

    private class DigRootedDirtGoal extends Goal {
        private final int searchLength;
        private final int verticalSearchRange;
        protected BlockPos destinationBlock;
        private EntityTalapanas crab;
        private int runDelay = 70;
        private int maxFeedTime = 200;

        private DigRootedDirtGoal(EntityTalapanas crab) {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
            this.crab = crab;
            searchLength = 16;
            verticalSearchRange = 6;
        }

        public boolean canContinueToUse() {
            return destinationBlock != null && isDigBlock(crab.level, destinationBlock.mutable()) && isCloseToMoss(16);
        }

        public boolean isCloseToMoss(double dist) {
            return destinationBlock == null || crab.distanceToSqr(Vec3.atCenterOf(destinationBlock)) < dist * dist;
        }

        @Override
        public boolean canUse() {
            if (this.runDelay > 0) {
                --this.runDelay;
                return false;
            } else {
                this.runDelay = 200 + crab.random.nextInt(150);
                return this.searchForDestination();
            }
        }

        public void start() {
            maxFeedTime = 60 + random.nextInt(60);
        }

        public void tick() {
            Vec3 vec = Vec3.atCenterOf(destinationBlock);
            if (vec != null) {
                crab.getNavigation().moveTo(vec.x, vec.y, vec.z, 1F);
                if (crab.distanceToSqr(vec) < 1.15F) {
                    crab.entityData.set(FEEDING_POS, Optional.of(destinationBlock));
                    Vec3 face = vec.subtract(crab.position());
                    crab.setDeltaMovement(crab.getDeltaMovement().add(face.normalize().scale(0.1F)));
                    crab.setFeedingTime(crab.getFeedingTime() + 1);
                    crab.playSound(SoundEvents.ROOTED_DIRT_BREAK, crab.getSoundVolume(), crab.getVoicePitch());
                    if (crab.getFeedingTime() > maxFeedTime) {
                        destinationBlock = null;
                        if (random.nextInt(1) == 0) {
                            List<ItemStack> lootList = getDigLoot(crab);
                            if (lootList.size() > 0) {
                                for (ItemStack stack : lootList) {
                                    ItemEntity e = crab.spawnAtLocation(stack.copy());
                                    e.hasImpulse = true;
                                    e.setDeltaMovement(e.getDeltaMovement().multiply(0.2, 0.2, 0.2));
                                }
                            }
                        }
                    }
                } else {
                    crab.entityData.set(FEEDING_POS, Optional.empty());
                }
            }
        }

        public void stop() {
            crab.entityData.set(FEEDING_POS, Optional.empty());
            destinationBlock = null;
            crab.setFeedingTime(0);
        }

        protected boolean searchForDestination() {
            int lvt_1_1_ = this.searchLength;
            int lvt_2_1_ = this.verticalSearchRange;
            BlockPos lvt_3_1_ = crab.blockPosition();
            BlockPos.MutableBlockPos lvt_4_1_ = new BlockPos.MutableBlockPos();

            for (int lvt_5_1_ = -8; lvt_5_1_ <= 2; lvt_5_1_++) {
                for (int lvt_6_1_ = 0; lvt_6_1_ < lvt_1_1_; ++lvt_6_1_) {
                    for (int lvt_7_1_ = 0; lvt_7_1_ <= lvt_6_1_; lvt_7_1_ = lvt_7_1_ > 0 ? -lvt_7_1_ : 1 - lvt_7_1_) {
                        for (int lvt_8_1_ = lvt_7_1_ < lvt_6_1_ && lvt_7_1_ > -lvt_6_1_ ? lvt_6_1_ : 0; lvt_8_1_ <= lvt_6_1_; lvt_8_1_ = lvt_8_1_ > 0 ? -lvt_8_1_ : 1 - lvt_8_1_) {
                            lvt_4_1_.setWithOffset(lvt_3_1_, lvt_7_1_, lvt_5_1_ - 1, lvt_8_1_);
                            if (this.isDigBlock(crab.level, lvt_4_1_) && crab.canSeeBlock(lvt_4_1_)) {
                                this.destinationBlock = lvt_4_1_;
                                return true;
                            }
                        }
                    }
                }
            }

            return false;
        }

        private boolean isDigBlock(Level world, BlockPos.MutableBlockPos pos) {
            return world.getBlockState(pos).is(UPTags.TALAPANAS_DIGGABLES);
        }

    }

    @Override
    public boolean canBeLeashed(Player pPlayer) {
        return false;
    }

    private boolean canSeeBlock(BlockPos destinationBlock) {
        Vec3 Vector3d = new Vec3(this.getX(), this.getEyeY(), this.getZ());
        Vec3 blockVec = net.minecraft.world.phys.Vec3.atCenterOf(destinationBlock);
        BlockHitResult result = this.level.clip(new ClipContext(Vector3d, blockVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        return result.getBlockPos().equals(destinationBlock);
    }

    private static List<ItemStack> getDigLoot(EntityTalapanas crab) {
        LootTable loottable = crab.level.getServer().getLootTables().get(TALAPANAS_REWARD);
        return loottable.getRandomItems((new LootContext.Builder((ServerLevel) crab.level)).withParameter(LootContextParams.THIS_ENTITY, crab).withRandom(crab.level.random).create(LootContextParamSets.PIGLIN_BARTER));
    }

    @Override
    protected boolean isImmobile() {
        return this.isPassenger();
    }

    public Vec3 getDismountLocationForPassenger(LivingEntity pLivingEntity) {
        Vec3 vec3 = getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)pLivingEntity.getBbWidth(), this.getYRot() + (pLivingEntity.getMainArm() == HumanoidArm.RIGHT ? 90.0F : -90.0F));
        Vec3 vec31 = this.getDismountLocationInDirection(vec3, pLivingEntity);
        if (vec31 != null) {
            return vec31;
        } else {
            Vec3 vec32 = getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)pLivingEntity.getBbWidth(), this.getYRot() + (pLivingEntity.getMainArm() == HumanoidArm.LEFT ? 90.0F : -90.0F));
            Vec3 vec33 = this.getDismountLocationInDirection(vec32, pLivingEntity);
            return vec33 != null ? vec33 : this.position();
        }
    }

    @javax.annotation.Nullable
    private Vec3 getDismountLocationInDirection(Vec3 pDirection, LivingEntity pPassenger) {
        double d0 = this.getX() + pDirection.x;
        double d1 = this.getBoundingBox().minY;
        double d2 = this.getZ() + pDirection.z;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(Pose pose : pPassenger.getDismountPoses()) {
            blockpos$mutableblockpos.set(d0, d1, d2);
            double d3 = this.getBoundingBox().maxY + 0.75D;

            while(true) {
                double d4 = this.level.getBlockFloorHeight(blockpos$mutableblockpos);
                if ((double)blockpos$mutableblockpos.getY() + d4 > d3) {
                    break;
                }

                if (DismountHelper.isBlockFloorValid(d4)) {
                    AABB aabb = pPassenger.getLocalBoundsForPose(pose);
                    Vec3 vec3 = new Vec3(d0, (double)blockpos$mutableblockpos.getY() + d4, d2);
                    if (DismountHelper.canDismountTo(this.level, pPassenger, aabb.move(vec3))) {
                        pPassenger.setPose(pose);
                        return vec3;
                    }
                }

                blockpos$mutableblockpos.move(Direction.UP);
                if (!((double)blockpos$mutableblockpos.getY() < d3)) {
                    break;
                }
            }
        }

        return null;
    }

    public class FleeLightGoal extends Goal {
        protected final PathfinderMob creature;
        private double shelterX;
        private double shelterY;
        private double shelterZ;
        private final double movementSpeed;
        private final Level world;
        private int executeChance = 50;
        private int lightLevel = 10;

        public FleeLightGoal(PathfinderMob p_i1623_1_, double p_i1623_2_) {
            this.creature = p_i1623_1_;
            this.movementSpeed = p_i1623_2_;
            this.world = p_i1623_1_.level;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public FleeLightGoal(PathfinderMob p_i1623_1_, double p_i1623_2_, int chance, int level) {
            this.creature = p_i1623_1_;
            this.movementSpeed = p_i1623_2_;
            this.world = p_i1623_1_.level;
            this.executeChance = chance;
            this.lightLevel = level;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            if (this.creature.getTarget() != null || this.creature.getRandom().nextInt(executeChance) != 0) {
                return false;
            } else if (this.world.getMaxLocalRawBrightness(this.creature.blockPosition()) < lightLevel) {
                return false;
            } else {
                return this.isPossibleShelter();
            }
        }

        protected boolean isPossibleShelter() {
            Vec3 lvt_1_1_ = this.findPossibleShelter();
            if (lvt_1_1_ == null) {
                return false;
            } else {
                this.shelterX = lvt_1_1_.x;
                this.shelterY = lvt_1_1_.y;
                this.shelterZ = lvt_1_1_.z;
                return true;
            }
        }

        public boolean canContinueToUse() {
            return !this.creature.getNavigation().isDone();
        }

        public void start() {
            this.creature.getNavigation().moveTo(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
        }

        @Nullable
        protected Vec3 findPossibleShelter() {
            RandomSource lvt_1_1_ = this.creature.getRandom();
            BlockPos lvt_2_1_ = this.creature.blockPosition();

            for (int lvt_3_1_ = 0; lvt_3_1_ < 10; ++lvt_3_1_) {
                BlockPos lvt_4_1_ = lvt_2_1_.offset(lvt_1_1_.nextInt(20) - 10, lvt_1_1_.nextInt(6) - 3, lvt_1_1_.nextInt(20) - 10);
                if (this.creature.level.getMaxLocalRawBrightness(lvt_4_1_) < lightLevel) {
                    return Vec3.atBottomCenterOf(lvt_4_1_);
                }
            }

            return null;
        }
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.isFromBook()) {
            return PlayState.CONTINUE;
        }
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isPassenger()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.talapanas.walk"));
            return PlayState.CONTINUE;
        }
        if (this.isPassenger()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.talapanas.sit"));
            return PlayState.CONTINUE;
        } else {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.talapanas.idle"));
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState eatPredicate(AnimationEvent<E> event) {
        if (this.getFeedingTime() > 0) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.talapanas.forge"));
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(1);
        AnimationController<EntityTalapanas> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        data.addAnimationController(new AnimationController<>(this, "eatController", 5, this::eatPredicate));
        data.addAnimationController(controller);
    }
}
