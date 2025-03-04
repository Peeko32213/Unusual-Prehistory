package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.goal.CustomRandomStrollGoal;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;


public class CotylorhynchusEntity extends PrehistoricEntity {
    private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.MELON, Items.MELON_SLICE, Items.MELON_SEEDS, Items.GLISTERING_MELON_SLICE);
    private static final EntityDataAccessor<Boolean> FERMENTED = SynchedEntityData.defineId(CotylorhynchusEntity.class, EntityDataSerializers.BOOLEAN);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation COTY_WALK = RawAnimation.begin().thenLoop("animation.cotylorhynchus.walk");
    private static final RawAnimation COTY_IDLE = RawAnimation.begin().thenLoop("animation.cotylorhynchus.idle");
    private static final RawAnimation COTY_SWIM = RawAnimation.begin().thenLoop("animation.cotylorhynchus.swim");

    public CotylorhynchusEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.16D)
                .add(Attributes.FOLLOW_RANGE, 12.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, TyrannosaurusEntity.class, 8.0F, 1.6D, 1.4D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, MajungasaurusEntity.class, 8.0F, 1.6D, 1.4D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
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
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob p_146744_) {
        return UPEntities.COTY.get().create(serverLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FERMENTED, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Fermented", this.isFermented());


    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setFermented(compound.getBoolean("Fermented"));

    }

    public void setFermented(boolean fermented) {
        this.entityData.set(FERMENTED, fermented);
    }

    public boolean isFermented() {
        return this.entityData.get(FERMENTED);
    }


    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if (item == Items.SWEET_BERRIES && !this.isFermented()) {

            if (!player.isCreative()) {
                itemstack.shrink(1);
            }
            int brewAmount = random.nextInt(0, 100);
            if (brewAmount >= 70) {
                this.playSound(SoundEvents.BREWING_STAND_BREW, 1.0F, 1.0F);
                this.setFermented(true);
            } else {
                this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
            }
            return InteractionResult.SUCCESS;
        } else if (item == UPItems.FLASK.get() && this.isFermented()) {
            if (!player.isCreative()) {
                itemstack.shrink(1);
            }
            if(!player.addItem(new ItemStack(UPItems.GROG.get()))){
                player.spawnAtLocation(UPItems.GROG.get());
            }
            this.setFermented(false);
            this.playSound(this.getBurpSound(itemstack), 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.FAIL;
        }
    }
    private void spawnFluidParticle(Level pLevel, double pStartX, double pEndX, double pStartZ, double pEndZ, double pPosY) {
        pLevel.addParticle(ParticleTypes.DRIPPING_HONEY, Mth.lerp(pLevel.random.nextDouble(), pStartX, pEndX), pPosY, Mth.lerp(pLevel.random.nextDouble(), pStartZ, pEndZ), 0.0D, 0.0D, 0.0D);
    }

    public void tick() {
        super.tick();
        if (this.isFermented() && this.random.nextFloat() < 0.05F) {
            for(int i = 0; i < this.random.nextInt(2) + 1; ++i) {
                this.spawnFluidParticle(this.level(), this.getX() - (double)1.6F, this.getX() + (double)1.6F, this.getZ() - (double)1.6F, this.getZ() + (double)1.6F, this.getY(0.8D));
            }
        }
    }

    public SoundEvent getEatingSound(ItemStack p_28540_) {
        return SoundEvents.GENERIC_EAT;
    }

    public SoundEvent getBurpSound(ItemStack p_28540_) {
        return SoundEvents.PLAYER_BURP;
    }


    protected <E extends CotylorhynchusEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if(this.isFromBook()){
            return event.setAndContinue(COTY_IDLE);
        }
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInWater()) {
            {
                event.setAndContinue(COTY_WALK);
                event.getController().setAnimationSpeed(1.0F);
                return PlayState.CONTINUE;
            }
        }
        if (this.isInWater()) {
            event.setAndContinue(COTY_SWIM);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        } else if (!this.isInWater()) {
            event.setAndContinue(COTY_IDLE);
            event.getController().setAnimationSpeed(1.0F);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.COTY_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.COTY_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.COTY_DEATH.get();
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(SoundEvents.CAMEL_STEP, 1.0F, 1.0F);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.hasCustomName();
    }

    public boolean removeWhenFarAway(double d) {
        return !this.hasCustomName();
    }

    @javax.annotation.Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_28134_, DifficultyInstance p_28135_, MobSpawnType p_28136_, @javax.annotation.Nullable SpawnGroupData p_28137_, @javax.annotation.Nullable CompoundTag p_28138_) {
        p_28137_ = super.finalizeSpawn(p_28134_, p_28135_, p_28136_, p_28137_, p_28138_);
        Level level = p_28134_.getLevel();
        if (level instanceof ServerLevel) {
            {
                this.setPersistenceRequired();
            }
        }
        return p_28137_;
    }

}
