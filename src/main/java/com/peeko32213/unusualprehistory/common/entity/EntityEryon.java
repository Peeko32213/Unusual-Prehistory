package com.peeko32213.unusualprehistory.common.entity;

import com.google.common.collect.Lists;
import com.peeko32213.unusualprehistory.common.config.UnusualPrehistoryConfig;
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
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class EntityEryon extends PathfinderMob implements IAnimatable {
    private static final EntityDataAccessor<Optional<BlockPos>> FEEDING_POS = SynchedEntityData.defineId(EntityEryon.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
    private static final EntityDataAccessor<Integer> FEEDING_TIME = SynchedEntityData.defineId(EntityEryon.class, EntityDataSerializers.INT);
    public static final ResourceLocation ERYON_REWARD = new ResourceLocation("unusualprehistory", "gameplay/eryon_reward");
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(EntityEryon.class, EntityDataSerializers.INT);

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private Ingredient temptationItems;
    public float prevFeedProgress;
    public float feedProgress;
    public EntityEryon(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
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
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
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
        if(variantChange <= 0.001){
            this.setVariant(1);
        }else{
            this.setVariant(0);
        }
        return p_28137_;
    }

    public boolean isFood(ItemStack stack) {
        return !stack.isEmpty() && getTemptationItems().test(stack);
    }

    private Ingredient getTemptationItems() {
        if(temptationItems == null)
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
        BlockPos feedingPos = this.entityData.get(FEEDING_POS).orElse(null);
        if(feedingPos == null){
            float f2 = (float) -((float) this.getDeltaMovement().y * 2.2F * (double) (180F / (float) Math.PI));
            this.setXRot(f2);
        }else if(this.getFeedingTime() > 0){
            Vec3 face = Vec3.atCenterOf(feedingPos).subtract(this.position());
            double d0 = face.horizontalDistance();
            this.setXRot((float)(-Mth.atan2(face.y, d0) * (double)(180F / (float)Math.PI)));
            this.setYRot(((float) Mth.atan2(face.z, face.x)) * (180F / (float) Math.PI) - 90F);
            this.yBodyRot = this.getYRot();
            this.yHeadRot = this.getYRot();
            BlockState state = level.getBlockState(feedingPos);
            if(random.nextInt(2) == 0 && !state.isAir()){
                Vec3 mouth = new Vec3(0, this.getBbHeight() * 0.5F, 0.4F * -0.5).xRot(this.getXRot() * ((float)Math.PI / 180F)).yRot(-this.getYRot() * ((float)Math.PI / 180F));
                for (int i = 0; i < 4 + random.nextInt(2); i++) {
                    double motX = this.random.nextGaussian() * 0.02D;
                    double motY = 0.1F + random.nextFloat() * 0.2F;
                    double motZ = this.random.nextGaussian() * 0.02D;
                    level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), this.getX() + mouth.x, this.getY() + mouth.y, this.getZ() + mouth.z, motX, motY, motZ);
                }
            }
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
        private DigSandGoal(EntityEryon crab) {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
            this.crab = crab;
            searchLength = 16;
            verticalSearchRange = 6;
        }

        public boolean canContinueToUse() {
            return destinationBlock != null && isDigBlock(crab.level, destinationBlock.mutable()) && isCloseToMoss(16) || !(timer > MAX_TIME);
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

        public void start(){
            maxFeedTime = 60 + random.nextInt(60);
        }

        public void tick() {
            Vec3 vec = Vec3.atCenterOf(destinationBlock);
            if (vec != null) {
                timer++;
                if(crab.blockPosition().offset(0,-1,0) != destinationBlock){
                    crab.getNavigation().moveTo(vec.x, vec.y, vec.z, 1F);
                } else if(crab.distanceToSqr(vec) < 1F){
                    crab.entityData.set(FEEDING_POS, Optional.of(destinationBlock));
                    Vec3 face = vec.subtract(crab.position());
                    crab.setDeltaMovement(crab.getDeltaMovement().add(face.normalize().scale(0.1F)));
                    crab.setFeedingTime(crab.getFeedingTime() + 1);
                    crab.playSound(SoundEvents.SAND_BREAK, crab.getSoundVolume(), crab.getVoicePitch());
                    if(crab.getFeedingTime() > maxFeedTime){
                        destinationBlock = null;
                        if(random.nextInt(1) == 0) {
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
                }else{
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
            return world.getBlockState(pos).is(UPTags.ERYON_DIGGABLES);
        }

    }

    private boolean canSeeBlock(BlockPos destinationBlock) {
        Vec3 Vector3d = new Vec3(this.getX(), this.getEyeY(), this.getZ());
        Vec3 blockVec = net.minecraft.world.phys.Vec3.atCenterOf(destinationBlock);
        BlockHitResult result = this.level.clip(new ClipContext(Vector3d, blockVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        return result.getBlockPos().equals(destinationBlock);
    }

    private static List<ItemStack> getDigLoot(EntityEryon crab) {
        LootTable loottable = crab.level.getServer().getLootTables().get(ERYON_REWARD);
        return loottable.getRandomItems((new LootContext.Builder((ServerLevel) crab.level)).withParameter(LootContextParams.THIS_ENTITY, crab).withRandom(crab.level.random).create(LootContextParamSets.PIGLIN_BARTER));
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
            {
                event.getController().setAnimation(new AnimationBuilder().loop("animation.eryon.walk"));
                event.getController().setAnimationSpeed(1.5D);
            }
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.eryon.idle"));
            event.getController().setAnimationSpeed(1.0D);
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState eatPredicate(AnimationEvent<E> event) {
        if (this.getFeedingTime() > 0) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.eryon.dig"));
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(10);
        data.addAnimationController(new AnimationController<>(this, "eatController", 5, this::eatPredicate));
        data.addAnimationController(new AnimationController<>(this, "controller", 10, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
    public static boolean checkSurfaceDinoSpawnRules(EntityType<? extends EntityEryon> p_186238_, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource p_186242_) {
        return level.getBlockState(pos.below()).is(UPTags.DINO_NATURAL_SPAWNABLE)  && UnusualPrehistoryConfig.DINO_NATURAL_SPAWNING.get();    }

}
