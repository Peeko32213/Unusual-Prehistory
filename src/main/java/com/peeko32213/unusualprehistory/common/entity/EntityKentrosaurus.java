package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.util.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.CustomRandomStrollGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.EntityBaseDinosaurAnimal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Pufferfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nonnull;
import java.util.EnumSet;
import java.util.function.Predicate;

public class EntityKentrosaurus extends EntityBaseDinosaurAnimal {
    protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(EntityKentrosaurus.class, EntityDataSerializers.BYTE);
    private static final TagKey<Item> KENTRO_FOOD = ForgeRegistries.ITEMS.tags().createTagKey(new ResourceLocation(UnusualPrehistory.MODID, "kentro_food"));
    private static final Predicate<LivingEntity> SCARY_MOB = (p_29634_) -> {
        if (p_29634_ instanceof Player && ((Player)p_29634_).isCreative()) {
            return false;
        } else {
            return p_29634_.getType() == EntityType.AXOLOTL || p_29634_.getMobType() != MobType.WATER;
        }
    };
    static final TargetingConditions targetingConditions = TargetingConditions.forNonCombat().ignoreInvisibilityTesting().ignoreLineOfSight().selector(SCARY_MOB);

    private boolean orderedToSit;
    protected int attackCooldown = 0;


    public EntityKentrosaurus(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 35.0D)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 12.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.3D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(1, new KentroSitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
    }


    public InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        InteractionResult type = super.mobInteract(player, hand);
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, hand);
        if (interactionresult != InteractionResult.SUCCESS && type != InteractionResult.SUCCESS) {
            if (isKentroFood(itemstack)) {
                int size = itemstack.getCount();
                this.usePlayerItem(player, hand, itemstack);
                itemstack.shrink(size);
                this.setOrderedToSit(true);
                this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            } else {
                this.setOrderedToSit(false);
                return InteractionResult.SUCCESS;
            }
        }
        return type;
    }

    public boolean isKentroFood(ItemStack stack) {
        return stack.is(KENTRO_FOOD);
    }

    public void tick() {
        super.tick();
        if (this.attackCooldown > 0) {
            this.attackCooldown--;
        }
    }


        @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Sitting", this.orderedToSit);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.orderedToSit = compound.getBoolean("Sitting");
        this.setInSittingPose(this.orderedToSit);
    }

    public boolean isInSittingPose() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public void setInSittingPose(boolean p_21838_) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (p_21838_) {
            this.entityData.set(DATA_FLAGS_ID, (byte)(b0 | 1));
        } else {
            this.entityData.set(DATA_FLAGS_ID, (byte)(b0 & -2));
        }

    }

    @Override
    public void playerTouch(Player entity) {
        super.playerTouch(entity);
        if (!entity.isCreative() && this.attackCooldown == 0 && entity.level.getDifficulty() != Difficulty.PEACEFUL && isOrderedToSit()) {
            entity.hurt(DamageSource.mobAttack(this), 5.0F);
            this.attackCooldown = 80;
        }
    }

    private void touch() {
        this.hurt(DamageSource.mobAttack(this), (float) (5.0D));

    }

    public void aiStep() {
        super.aiStep();
        if (this.isAlive() && this.isInSittingPose()) {
            for(Mob mob : this.level.getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(0.3D), (p_149013_) -> {
                return targetingConditions.test(this, p_149013_);
            })) {
                if (mob.isAlive() && isInSittingPose()) {
                    this.touch();
                }
            }
        }

    }

    public SoundEvent getEatingSound(ItemStack p_28540_) {
        return SoundEvents.GENERIC_EAT;
    }


    public boolean canBeCollidedWith() {
        return this.isAlive() && this.isInSittingPose();
    }


    public boolean isOrderedToSit() {
        return this.orderedToSit;
    }

    public void setOrderedToSit(boolean p_21840_) {
        this.orderedToSit = p_21840_;
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
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    public class KentroSitWhenOrderedToGoal extends Goal {
        private final EntityKentrosaurus mob;

        public KentroSitWhenOrderedToGoal(EntityKentrosaurus p_25898_) {
            this.mob = p_25898_;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        public boolean canContinueToUse() {
            return this.mob.isOrderedToSit();
        }

        public boolean canUse() {
            if (this.mob.isInWaterOrBubble()) {
                return false;
            } else if (!this.mob.isOnGround()) {
                return false;
            } else {
                return this.mob.isOrderedToSit();
            }
        }

        public void start() {
            this.mob.getNavigation().stop();
            this.mob.setInSittingPose(true);
        }

        public void stop() {
            this.mob.setInSittingPose(false);
        }
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInSittingPose()) {
             if (event.isMoving()) {
                event.getController().setAnimation(new AnimationBuilder().loop("animation.kentro.walk"));
                event.getController().setAnimationSpeed(1.0D);
                return PlayState.CONTINUE;
            }
        }
        if (this.isInSittingPose()) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.kentro.laying"));
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().loop("animation.kentro.idle"));
        event.getController().setAnimationSpeed(1.0F);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(5);
        AnimationController<EntityKentrosaurus> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        data.addAnimationController(controller);
    }
}
