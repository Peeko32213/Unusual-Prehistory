package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.util.LandCreaturePathNavigation;
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
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityCotylorhynchus extends Animal implements IAnimatable {
    private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.MELON, Items.MELON_SLICE, Items.MELON_SEEDS, Items.GLISTERING_MELON_SLICE);
    private static final EntityDataAccessor<Boolean> FERMENTED = SynchedEntityData.defineId(EntityTyrannosaurusRex.class, EntityDataSerializers.BOOLEAN);

    private final AnimationFactory factory = new AnimationFactory(this);

    public EntityCotylorhynchus(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.maxUpStep = 1.0f;
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
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, EntityMajungasaurus.class, 8.0F, 1.6D, 1.4D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
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
        this.entityData.set(FERMENTED, Boolean.valueOf(fermented));
    }

    public boolean isFermented() {
        return this.entityData.get(FERMENTED).booleanValue();
    }


    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        InteractionResult type = super.mobInteract(player, hand);

        if (item == Items.SWEET_BERRIES) {
            int size = itemstack.getCount();
            if (!player.isCreative()) {
                itemstack.shrink(1);
            }
            int brewAmount = 58 + random.nextInt(16);
            if(size > brewAmount){
                this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
                this.fullEffect();
                this.setFermented(true);
            }
            return InteractionResult.SUCCESS;

        }

        if (item == UPItems.FLASK.get() && this.isFermented()) {
            if (!player.isCreative()) {
                itemstack.shrink(1);
            }
            this.spawnAtLocation(UPItems.GROG.get());
            this.setFermented(false);
            this.playSound(this.getBurpSound(itemstack), 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    private void fullEffect() {
        if (this.random.nextInt(6) == 0) {
            double d = this.getX() - (double)this.getBbWidth() * Math.sin(this.yBodyRot * ((float)Math.PI / 180)) + (this.random.nextDouble() * 0.6 - 0.3);
            double e = this.getY() + (double)this.getBbHeight() - 0.3;
            double f = this.getZ() + (double)this.getBbWidth() * Math.cos(this.yBodyRot * ((float)Math.PI / 180)) + (this.random.nextDouble() * 0.6 - 0.3);
            level.addParticle(ParticleTypes.COMPOSTER, true, this.getX(), this.getEyeY() + 0.5F, this.getZ(), 0, 0, 0);
        }
    }

    public SoundEvent getEatingSound(ItemStack p_28540_) {
        return SoundEvents.GENERIC_EAT;
    }

    public SoundEvent getBurpSound(ItemStack p_28540_) {
        return SoundEvents.PLAYER_BURP;
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new LandCreaturePathNavigation(this, level);
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
            {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cotylorhynchus.walk", true));
                event.getController().setAnimationSpeed(1.5D);
            }
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cotylorhynchus.idle", true));
            event.getController().setAnimationSpeed(1.0D);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(10);
        data.addAnimationController(new AnimationController<>(this, "controller", 10, this::predicate));
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.COTY_IDLE;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.COTY_HURT;
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.COTY_DEATH;
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
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
