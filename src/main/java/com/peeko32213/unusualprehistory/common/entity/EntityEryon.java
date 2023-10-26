package com.peeko32213.unusualprehistory.common.entity;

import com.google.common.collect.Lists;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class EntityEryon extends EntityBaseDinosaurAnimal{
    private static final EntityDataAccessor<Optional<BlockPos>> FEEDING_POS = SynchedEntityData.defineId(EntityEryon.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
    private static final EntityDataAccessor<Integer> FEEDING_TIME = SynchedEntityData.defineId(EntityEryon.class, EntityDataSerializers.INT);
    public static final ResourceLocation ERYON_REWARD = new ResourceLocation("unusualprehistory", "gameplay/eryon_reward");
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(EntityEryon.class, EntityDataSerializers.INT);
    private Ingredient temptationItems;
    public float prevFeedProgress;
    public float feedProgress;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation ERYON_WALK = RawAnimation.begin().thenLoop("animation.eryon.walk");
    private static final RawAnimation ERYON_IDLE = RawAnimation.begin().thenLoop("animation.eryon.idle");
    private static final RawAnimation ERYON_DIG = RawAnimation.begin().thenLoop("animation.eryon.dig");

    public EntityEryon(EntityType<? extends EntityBaseDinosaurAnimal> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, getTemptationItems(), false));
        this.goalSelector.addGoal(5, new DigSandGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.ARMOR, 3.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 2.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.ERYON_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.ERYON_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.ERYON_DEATH.get();
    }

    public static String getVariantName(int variant) {
        if (variant == 1) {
            return "blue";
        }
        return "normal";
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getVariant());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(compound.getInt("Variant"));
    }

    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
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

    public boolean removeWhenFarAway(double d) {
        return !this.hasCustomName();
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.hasCustomName();
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_28134_, DifficultyInstance p_28135_, MobSpawnType p_28136_, @Nullable SpawnGroupData p_28137_, @Nullable CompoundTag p_28138_) {
        p_28137_ = super.finalizeSpawn(p_28134_, p_28135_, p_28136_, p_28137_, p_28138_);
        Level level = p_28134_.getLevel();
        this.setPersistenceRequired();
        float variantChange = this.getRandom().nextFloat();
        if (variantChange <= 0.001) {
            this.setVariant(1);
        } else {
            this.setVariant(0);
        }
        return p_28137_;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    public boolean isFood(ItemStack stack) {
        return !stack.isEmpty() && getTemptationItems().test(stack);
    }

    private Ingredient getTemptationItems() {
        if (temptationItems == null)
            temptationItems = Ingredient.merge(Lists.newArrayList(
                    Ingredient.of(ItemTags.FISHES)
            ));

        return temptationItems;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FEEDING_TIME, 0);
        this.entityData.define(VARIANT, 0);
        this.entityData.define(FEEDING_POS, Optional.empty());
    }

    public int getVariant() {
        return this.entityData.get(VARIANT).intValue();
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, Integer.valueOf(variant));
    }

    public int getFeedingTime() {
        return this.entityData.get(FEEDING_TIME);
    }

    public void setFeedingTime(int feedingTime) {
        this.entityData.set(FEEDING_TIME, feedingTime);
    }

    public void tick() {
        super.tick();
        this.prevFeedProgress = feedProgress;
        if (this.getFeedingTime() > 0 && feedProgress < 5F) {
            feedProgress++;
        }
        if (this.getFeedingTime() <= 0 && feedProgress > 0F) {
            feedProgress--;
        }
    }

    private class DigSandGoal extends Goal {
        private final int searchLength;
        private final int verticalSearchRange;
        protected BlockPos destinationBlock;
        private EntityEryon crab;
        private int runDelay = 70;
        private int maxFeedTime = 200;
        private final int MAX_TIME = 6000;
        private int timer = 0;
        private int cooldownTime;
        private DigSandGoal(EntityEryon crab) {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
            this.crab = crab;
            searchLength = 16;
            verticalSearchRange = 6;
        }

        public boolean canContinueToUse() {
            return destinationBlock != null && isDigBlock(crab.level(), destinationBlock.mutable()) && isCloseToMoss(16) && !(timer > MAX_TIME) && !(cooldownTime > 0);
        }

        public boolean isCloseToMoss(double dist) {
            return destinationBlock == null || crab.distanceToSqr(Vec3.atCenterOf(destinationBlock)) < dist * dist;
        }

        @Override
        public boolean canUse() {
            if (--this.runDelay > 0 || --this.cooldownTime > 0) {
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
            if (destinationBlock == null) stop();
            Vec3 vec = Vec3.atCenterOf(destinationBlock);
            if (vec != null) {
                timer++;
                double dist = crab.blockPosition().distToCenterSqr(vec.x, vec.y, vec.z);
                if (dist > 2D) {
                    crab.getNavigation().moveTo(vec.x - 0.5, vec.y + 1, vec.z - 0.5, 1F);
                } else if (crab.distanceToSqr(vec) <= 2F) {
                    crab.entityData.set(FEEDING_POS, Optional.of(destinationBlock));
                    Vec3 face = vec.subtract(crab.position());
                    crab.setDeltaMovement(crab.getDeltaMovement().add(face.normalize().scale(0.1F)));
                    crab.setFeedingTime(crab.getFeedingTime() + 1);
                    crab.playSound(SoundEvents.SAND_BREAK, crab.getSoundVolume(), crab.getVoicePitch());
                    if (crab.getFeedingTime() > maxFeedTime) {
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
                        stop();
                    } else {
                        BlockPos feedingPos = this.crab.entityData.get(FEEDING_POS).orElse(null);
                        double d0 = face.horizontalDistance();
                        this.crab.setXRot((float) (-Mth.atan2(face.y, d0) * (double) (180F / (float) Math.PI)));
                        this.crab.setYRot(((float) Mth.atan2(face.z, face.x)) * (180F / (float) Math.PI) - 90F);
                        this.crab.yBodyRot = this.crab.getYRot();
                        this.crab.yHeadRot = this.crab.getYRot();
                        BlockState state = level().getBlockState(feedingPos);
                        if (random.nextInt(2) == 0 && !state.isAir()) {
                            Vec3 mouth = new Vec3(0, this.crab.getBbHeight() * 0.5F, 0.4F * -0.5).xRot(this.crab.getXRot() * ((float) Math.PI / 180F)).yRot(-this.crab.getYRot() * ((float) Math.PI / 180F));
                            for (int i = 0; i < 4 + random.nextInt(2); i++) {
                                double motX = this.crab.random.nextGaussian() * 0.02D;
                                double motY = 0.1F + random.nextFloat() * 0.2F;
                                double motZ = this.crab.random.nextGaussian() * 0.02D;
                                ((ServerLevel)level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), this.crab.getX() - mouth.x, this.crab.getY() + mouth.y, this.crab.getZ() - mouth.z, 1,  motX, motY, motZ, 0.1D);
                            }
                        }
                    }
                } else {
                    crab.entityData.set(FEEDING_POS, Optional.empty());
                }
            }


        }

        public void stop() {
            super.stop();
            crab.entityData.set(FEEDING_POS, Optional.empty());
            crab.setFeedingTime(0);
            this.cooldownTime = 3000;
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
                            if (this.isDigBlock(crab.level(), lvt_4_1_) && crab.canSeeBlock(lvt_4_1_)) {
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
            return world.getBlockState(pos).is(UPTags.ERYON_DIGGABLES);
        }

    }

    private boolean canSeeBlock(BlockPos destinationBlock) {
        Vec3 Vector3d = new Vec3(this.getX(), this.getEyeY(), this.getZ());
        Vec3 blockVec = net.minecraft.world.phys.Vec3.atCenterOf(destinationBlock);
        BlockHitResult result = this.level().clip(new ClipContext(Vector3d, blockVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        return result.getBlockPos().equals(destinationBlock);
    }

    private static List<ItemStack> getDigLoot(EntityEryon crab) {
        LootTable loottable = crab.level().getServer().getLootData().getLootTable(ERYON_REWARD);
        return loottable.getRandomItems((new LootParams.Builder((ServerLevel) crab.level())).withParameter(LootContextParams.THIS_ENTITY, crab).create(LootContextParamSets.PIGLIN_BARTER));
    }

    public boolean canBreatheUnderwater() {
        return true;
    }



    protected <E extends EntityEryon> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.isFromBook()) {
            return PlayState.CONTINUE;
        }
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
             {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.eryon.walk"));
                event.getController().setAnimationSpeed(1.5D);
            }
        } else {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.eryon.idle"));
            event.getController().setAnimationSpeed(1.0D);
        }
        return PlayState.CONTINUE;
    }

    protected <E extends EntityEryon> PlayState digController(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.getFeedingTime() > 0) {
            event.setAndContinue(ERYON_DIG);
            return PlayState.CONTINUE;
        }
        event.getController().forceAnimationReset();
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
        controllers.add(new AnimationController<>(this, "Dig", 10, this::digController));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }



}
