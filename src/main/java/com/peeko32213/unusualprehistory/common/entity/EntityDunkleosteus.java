package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityDunkleosteus extends WaterAnimal implements IAnimatable {
    private static final EntityDataAccessor<Integer> PASSIVETICKS = SynchedEntityData.defineId(EntityDunkleosteus.class, EntityDataSerializers.INT);
    private final AnimationFactory factory = new AnimationFactory(this);
    protected int attackCooldown = 0;
    private boolean wasOnGround;

    public EntityDunkleosteus(EntityType<? extends WaterAnimal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 45, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.ARMOR, 10.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6D)
                .add(Attributes.FOLLOW_RANGE, 12.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new EntityDunkleosteus.IMeleeAttackGoal());
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, EntityAmmonite.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, EntityStethacanthus.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, 100, true, false, this::isAngryAt));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 1.0D, 1) {
            @Override
            public boolean canUse() {
                return super.canUse() && isInWater();
            }
        });
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D, 15) {
            @Override
            public boolean canUse() {
                return !this.mob.isInWater() && super.canUse();
            }
        });
    }

    public boolean isAngryAt(LivingEntity p_21675_) {
        return this.canAttack(p_21675_);
    }

    @Override
    public boolean canAttack(LivingEntity entity) {
        boolean prev = super.canAttack(entity);

        if (entity instanceof Player && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().equals(entity))) {
            if (this.getPassiveTicks() > 0 || isStetha(entity.getItemInHand(InteractionHand.MAIN_HAND)) || isStetha(entity.getItemInHand(InteractionHand.OFF_HAND))) {
                return false;
            }
        }
        return prev;
    }

    public boolean isStetha(ItemStack stack) {
        return stack.is(UPItems.RAW_STETHA.get());
    }

    public int getPassiveTicks() {
        return this.entityData.get(PASSIVETICKS);
    }

    private void setPassiveTicks(int passiveTicks) {
        this.entityData.set(PASSIVETICKS, passiveTicks);
    }

    protected PathNavigation createNavigation(Level p_27480_) {
        return new WaterBoundPathNavigation(this, p_27480_);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.COD_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource p_28281_) {
        return SoundEvents.COD_HURT;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(PASSIVETICKS, 0);
    }

    public void addAdditionalSaveData(CompoundTag p_31808_) {
        super.addAdditionalSaveData(p_31808_);
        p_31808_.putInt("PassiveTicks", this.getPassiveTicks());
    }

    public void readAdditionalSaveData(CompoundTag p_31795_) {
        super.readAdditionalSaveData(p_31795_);
        this.setPassiveTicks(p_31795_.getInt("PassiveTicks"));
    }


    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if (isStetha(itemstack) && (this.getPassiveTicks() <= 0 || this.getHealth() < this.getMaxHealth())) {
            if (!player.isCreative()) {
                itemstack.shrink(1);
            }
            this.heal(6);
            this.setPassiveTicks(this.getPassiveTicks() + 1200);
            return InteractionResult.SUCCESS;
        }
        InteractionResult type = super.mobInteract(player, hand);
        return type;
    }

    public void tick() {
        super.tick();

        if (this.level.isClientSide && this.isInWater() && this.getDeltaMovement().lengthSqr() > 0.03D) {
            Vec3 vec3 = this.getViewVector(0.0F);
            float f = Mth.cos(this.getYRot() * ((float) Math.PI / 320F)) * 0.3F;
            float f1 = Mth.sin(this.getYRot() * ((float) Math.PI / 320F)) * 0.3F;
        }
    }

    public void aiStep() {

        super.aiStep();
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dunk.swim", true));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dunk.idle", true));
        }
        return PlayState.CONTINUE;
    }



    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if (this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dunk.bite", false));
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(5);
        AnimationController<EntityDunkleosteus> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        data.addAnimationController(controller);
        data.addAnimationController(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.hasCustomName();
    }

    public boolean removeWhenFarAway(double d) {
        return !this.hasCustomName();
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }


    class IMeleeAttackGoal extends MeleeAttackGoal {
        public IMeleeAttackGoal() {
            super(EntityDunkleosteus.this, 1.5D, true);
        }

        protected double getAttackReachSqr(LivingEntity p_25556_) {
            return (double)(this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 0.7F + p_25556_.getBbWidth());
        }

    }
}
