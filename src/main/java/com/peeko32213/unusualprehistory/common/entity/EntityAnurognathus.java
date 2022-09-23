package com.peeko32213.unusualprehistory.common.entity;

import com.google.common.base.Predicate;
import com.peeko32213.unusualprehistory.common.entity.util.AbstractFlockingPathfinderMob;
import com.peeko32213.unusualprehistory.common.entity.util.CustomFollowFlockLeaderGoal;
import com.peeko32213.unusualprehistory.common.entity.util.FlyingWanderGoal;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.*;

public class EntityAnurognathus extends PathfinderMob implements IAnimatable, FlyingAnimal, NeutralMob {
    private final AnimationFactory factory = new AnimationFactory(this);
    private static final TargetingConditions ENTITY_PREDICATE = TargetingConditions.forNonCombat().range(24.0D).ignoreLineOfSight();
    private int ticksSinceEaten;
    private boolean isSchool = true;
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    @javax.annotation.Nullable
    private UUID persistentAngerTarget;
    private static final int ALERT_RANGE_Y = 10;
    private static final UniformInt ALERT_INTERVAL = TimeUtil.rangeOfSeconds(4, 6);
    private int ticksUntilNextAlert;
    private int remainingPersistentAngerTime;


    public EntityAnurognathus(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setCanPickUpLoot(true);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_CACTUS, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_CACTUS, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0F)
                .add(Attributes.FLYING_SPEED, 0.6F)
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.ATTACK_DAMAGE, 4.0F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AnuroAttackGoal(this, 1.2F, true));
        this.goalSelector.addGoal(3, new AnuroWanderGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new HealMobGoal());
        this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Bee.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Silverfish.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Spider.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Endermite.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, CaveSpider.class, true));

    }

    public boolean isMaxGroupSizeReached(int p_30035_) {
        return !this.isSchool;
    }

    public int getMaxSchoolSize() {
        return 10;
    }

    @Override
    public boolean isFlying() {
        return !this.isOnGround();
    }

    public int getMaxSpawnClusterSize() {
        return 2;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }


    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    public boolean onClimbable() {
        return false;
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
        return shouldHurt;
    }


    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        AnuroPathNavigation navigation = new AnuroPathNavigation(this, pLevel);
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(true);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.isFlying()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.anuro.fly", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.anuro.idle", true));
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if (this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.anuro.bite", false));
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(5);
        AnimationController<EntityAnurognathus> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        data.addAnimationController(controller);
        data.addAnimationController(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    @Override
    public void setRemainingPersistentAngerTime(int p_21673_) {
        this.remainingPersistentAngerTime = p_21673_;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@org.jetbrains.annotations.Nullable UUID p_21672_) {
        this.persistentAngerTarget = p_21672_;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    static class AnuroPathNavigation extends FlyingPathNavigation {
        public AnuroPathNavigation(Mob mob, Level level) {
            super(mob, level);
        }

        @Override
        public boolean isStableDestination(BlockPos pos) {
            return super.isStableDestination(pos) && this.mob.level.getBlockState(pos).is(UPTags.ANURO_PERCH_BLOCKS);
        }
    }

    static class AnuroWanderGoal extends FlyingWanderGoal {
        public AnuroWanderGoal(PathfinderMob mob) {
            super(mob);
        }

        @Nullable
        @Override
        protected Vec3 findPos() {
            Vec3 viewVector = mob.getViewVector(0.0F);
            return AirAndWaterRandomPos.getPos(mob, 12, 12, -1, viewVector.x, viewVector.z, Math.PI);
        }
    }

    static class AnuroAttackGoal extends MeleeAttackGoal {

        public AnuroAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
            super(mob, speedModifier, followingTargetEvenIfNotSeen);
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.mob.getTarget();
            if (target instanceof Player player) {
                if (player.getHealth() > 6 || player.getMainHandItem().isEmpty())  {
                    return false;
                }
            }
            return this.mob.getMainHandItem().isEmpty() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = this.mob.getTarget();
            if (target instanceof Player player) {
                if (player.getHealth() > 6 || player.getMainHandItem().isEmpty())  {
                    return false;
                }
            }
            return this.mob.getMainHandItem().isEmpty() && super.canContinueToUse();
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            double reach = this.getAttackReachSqr(enemy);
            if (distToEnemySqr <= reach && this.getTicksUntilNextAttack() <= 0) {
                this.resetAttackCooldown();
                this.mob.swing(InteractionHand.MAIN_HAND);
                if (!(enemy instanceof Player)) {
                    this.mob.doHurtTarget(enemy);
                }
            }
        }
    }

    private class HealMobGoal extends Goal {
        protected final Sorter theNearestAttackableTargetSorter;
        protected final Predicate<? super Entity> targetEntitySelector;
        protected int executionChance = 20;
        protected boolean mustUpdate;
        private Entity targetEntity;
        private int cooldown = 0;

        HealMobGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
            this.theNearestAttackableTargetSorter = new Sorter(EntityAnurognathus.this);
            this.targetEntitySelector = new Predicate<Entity>() {
                @Override
                public boolean apply(@Nullable Entity e) {
                    return e.isAlive() && e.getType().is(UPTags.ANURO_TARGETS) && (!(e instanceof LivingEntity) || ((LivingEntity) e).getHealth() >= 2D);
                }
            };
        }

        @Override
        public boolean canUse() {
            if (EntityAnurognathus.this.isPassenger() || EntityAnurognathus.this.isVehicle()) {
                return false;
            }
            if (!this.mustUpdate) {
                long worldTime = EntityAnurognathus.this.level.getGameTime() % 10;
                if (EntityAnurognathus.this.getNoActionTime() >= 100 && worldTime != 0) {
                    return false;
                }
                if (EntityAnurognathus.this.getRandom().nextInt(this.executionChance) != 0 && worldTime != 0) {
                    return false;
                }
            }
            List<Entity> list = EntityAnurognathus.this.level.getEntitiesOfClass(Entity.class, this.getTargetableArea(this.getTargetDistance()), this.targetEntitySelector);
            if (list.isEmpty()) {
                return false;
            } else {
                Collections.sort(list, this.theNearestAttackableTargetSorter);
                this.targetEntity = list.get(0);
                this.mustUpdate = false;
                return true;
            }
        }

        @Override
        public boolean canContinueToUse() {
            return targetEntity != null;
        }

        public void stop() {
            this.targetEntity = null;
        }

        @Override
        public void tick() {
            if(cooldown > 0){
                cooldown--;
            }
            if(targetEntity != null){
                if (EntityAnurognathus.this.getNavigation().isDone()) {
                    int i = EntityAnurognathus.this.getRandom().nextInt(3) - 1;
                    int k = EntityAnurognathus.this.getRandom().nextInt(3) - 1;
                    int l = (int) ((EntityAnurognathus.this.getRandom().nextInt(3) - 1) * Math.ceil(targetEntity.getBbHeight()));
                    EntityAnurognathus.this.getNavigation().moveTo(this.targetEntity.getX() + i, this.targetEntity.getY() + l, this.targetEntity.getZ() + k, 1);
                }
                if(EntityAnurognathus.this.distanceToSqr(targetEntity) < 3.0F){
                    if(targetEntity instanceof LivingEntity && ((LivingEntity) targetEntity).getHealth() > 2D){
                        if(cooldown == 0){
                            ((LivingEntity) targetEntity).heal(2.0F);
                            cooldown = 100;
                        }
                    }else{
                        this.stop();
                    }

                }
            }
        }

        protected double getTargetDistance() {
            return 16D;
        }

        protected AABB getTargetableArea(double targetDistance) {
            Vec3 renderCenter = new Vec3(EntityAnurognathus.this.getX() + 0.5, EntityAnurognathus.this.getY() + 0.5, EntityAnurognathus.this.getZ() + 0.5D);
            double renderRadius = 5;
            AABB aabb = new AABB(-renderRadius, -renderRadius, -renderRadius, renderRadius, renderRadius, renderRadius);
            return aabb.move(renderCenter);
        }


        public class Sorter implements Comparator<Entity> {
            private final Entity theEntity;

            public Sorter(Entity theEntityIn) {
                this.theEntity = theEntityIn;
            }

            public int compare(Entity p_compare_1_, Entity p_compare_2_) {
                double d0 = this.theEntity.distanceToSqr(p_compare_1_);
                double d1 = this.theEntity.distanceToSqr(p_compare_2_);
                return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
            }
        }
    }



}
