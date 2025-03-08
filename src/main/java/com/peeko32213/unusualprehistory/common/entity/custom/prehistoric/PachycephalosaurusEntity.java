package com.peeko32213.unusualprehistory.common.entity.custom.prehistoric;

import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.util.goal.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.util.goal.CustomRandomStrollGoal;
import com.peeko32213.unusualprehistory.common.entity.util.helper.HitboxHelper;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;
import java.util.EnumSet;
public class PachycephalosaurusEntity extends PrehistoricEntity {
    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(PachycephalosaurusEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COMBAT_STATE = SynchedEntityData.defineId(PachycephalosaurusEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ENTITY_STATE = SynchedEntityData.defineId(PachycephalosaurusEntity.class, EntityDataSerializers.INT);
    private static final RawAnimation PACHY_HEADSWING_1 = RawAnimation.begin().thenLoop("animation.pachy.headswing1");
    private static final RawAnimation PACHY_HEADSWING_2 = RawAnimation.begin().thenLoop("animation.pachy.headswing2");
    private static final RawAnimation PACHY_ATTACK = RawAnimation.begin().thenLoop("animation.pachy.attack");
    private static final RawAnimation PACHY_KICK = RawAnimation.begin().thenLoop("animation.pachy.kick");
    private static final RawAnimation PACHY_SWIM = RawAnimation.begin().thenLoop("animation.pachy.swim");
    private static final RawAnimation PACHY_WALK = RawAnimation.begin().thenLoop("animation.pachy.walk");
    private static final RawAnimation PACHY_IDLE = RawAnimation.begin().thenLoop("animation.pachy.idle");
    public PachycephalosaurusEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.ATTACK_KNOCKBACK, 2.0D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PachycephalosaurusEntity.PachyMeleeAttackGoal(this,  1.3F, true));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, MajungasaurusEntity.class, 8.0F, 1.6D, 1.4D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, TyrannosaurusEntity.class, 8.0F, 1.6D, 1.4D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.0D, Ingredient.of(Items.WHEAT), false));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 15.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, 100, true, false, this::isAngryAt));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.PACHY_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.PACHY_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.PACHY_DEATH.get();
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
    }

    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

    public boolean isAngryAt(LivingEntity p_21675_) {
        return this.canAttack(p_21675_);
    }
    @Override
    public boolean isAlliedTo(Entity pEntity) {
        return pEntity.is(this);
    }
    @Override
    public boolean canAttack(LivingEntity entity) {
        boolean prev = super.canAttack(entity);
        if(prev && isBaby()){
            return false;
        }
        return prev;
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

    public boolean isUlti() {
        String s = ChatFormatting.stripFormatting(this.getName().getString());
        return s != null && (s.toLowerCase().contains("ulti"));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION_STATE, 0);
        this.entityData.define(COMBAT_STATE, 0);
        this.entityData.define(ENTITY_STATE, 0);

    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }


    public int getAnimationState() {

        return this.entityData.get(ANIMATION_STATE);
    }

    public void setAnimationState(int anim) {

        this.entityData.set(ANIMATION_STATE, anim);
    }

    public int getCombatState() {

        return this.entityData.get(COMBAT_STATE);
    }

    public void setCombatState(int anim) {

        this.entityData.set(COMBAT_STATE, anim);
    }

    public int getEntityState() {

        return this.entityData.get(ENTITY_STATE);
    }

    public void setEntityState(int anim) {

        this.entityData.set(ENTITY_STATE, anim);
    }



    public boolean isFood(ItemStack stack) {
        return stack.is(UPTags.PACHY_FOOD);
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if (isFood(itemstack) && (this.getPassiveTicks() <= 0 || this.getHealth() < this.getMaxHealth())) {
            if(!this.level().isClientSide) {
                if (!player.isCreative()) {
                    itemstack.shrink(1);
                }
                this.heal(2);
                this.setPassiveTicks(this.getPassiveTicks() + 1500);
                player.addEffect(new MobEffectInstance(UPEffects.PACHYS_MIGHT.get(), 2400));
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return UPEntities.PACHY.get().create(serverLevel);
    }

    public void tick() {
        super.tick();
        if (this.getTarget() != null && this.getTarget().hasEffect(UPEffects.PACHYS_MIGHT.get())) {
            this.setTarget(null);
            this.setLastHurtByMob(null);
        }

    }

    public boolean hurt(DamageSource source, float amount) {
        boolean prev = super.hurt(source, amount);
        if (prev) {
            if (source.getEntity() != null) {
                if (source.getEntity() instanceof LivingEntity hurter) {
                    if (hurter.hasEffect(UPEffects.PACHYS_MIGHT.get())) {
                        hurter.removeEffect(UPEffects.PACHYS_MIGHT.get());
                    }
                }
            }
            return prev;
        }
        return prev;
    }

    protected <E extends PachycephalosaurusEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if(this.isFromBook()){
            return event.setAndContinue(PACHY_IDLE);
        }
        int animState = this.getAnimationState();
        {
            switch (animState) {

                case 21:
                    event.setAndContinue(PACHY_HEADSWING_1);
                    break;
                case 22:
                    event.setAndContinue(PACHY_HEADSWING_2);
                    break;
                case 23:
                    event.setAndContinue(PACHY_ATTACK);
                    break;
                case 24:
                    event.setAndContinue(PACHY_KICK);
                    break;
                default:
                    if (this.isInWater()) {
                        event.setAndContinue(PACHY_SWIM);
                        event.getController().setAnimationSpeed(1.0F);
                        return PlayState.CONTINUE;
                    }
                    else if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F && !this.isInWater())) {
                        event.setAndContinue(PACHY_WALK);
                        return PlayState.CONTINUE;
                    } else if(!this.isInWater()) {
                        event.setAndContinue(PACHY_IDLE);
                        event.getController().setAnimationSpeed(1.0F);
                        return PlayState.CONTINUE;
                    }
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }

    static class PachyMeleeAttackGoal extends Goal {

        protected final PachycephalosaurusEntity mob;
        private final double speedModifier;
        private final boolean followingTargetEvenIfNotSeen;
        private Path path;
        private double pathedTargetX;
        private double pathedTargetY;
        private double pathedTargetZ;
        private int ticksUntilNextPathRecalculation;
        private int ticksUntilNextAttack;
        private long lastCanUseCheck;
        private int failedPathFindingPenalty = 0;
        private boolean canPenalize = false;
        private int animTime = 0;


        public PachyMeleeAttackGoal(PachycephalosaurusEntity p_i1636_1_, double p_i1636_2_, boolean p_i1636_4_) {
            this.mob = p_i1636_1_;
            this.speedModifier = p_i1636_2_;
            this.followingTargetEvenIfNotSeen = p_i1636_4_;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean canUse() {
            long i = this.mob.level().getGameTime();

            if (i - this.lastCanUseCheck < 20L) {
                return false;
            } else {
                this.lastCanUseCheck = i;
                LivingEntity livingentity = this.mob.getTarget();
                if (livingentity == null) {
                    return false;
                } else if (!livingentity.isAlive()) {
                    return false;
                } else {
                    if (canPenalize) {
                        if (--this.ticksUntilNextPathRecalculation <= 0) {
                            this.path = this.mob.getNavigation().createPath(livingentity, 0);
                            this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                            return this.path != null;
                        } else {
                            return true;
                        }
                    }
                    this.path = this.mob.getNavigation().createPath(livingentity, 0);
                    if (this.path != null) {
                        return true;
                    } else {
                        return this.getAttackReachSqr(livingentity) >= this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
                    }
                }
            }
        }

        public boolean canContinueToUse() {

            LivingEntity livingentity = this.mob.getTarget();

            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else if (!this.followingTargetEvenIfNotSeen) {
                return !this.mob.getNavigation().isDone();
            } else if (!this.mob.isWithinRestriction(livingentity.blockPosition())) {
                return false;
            } else {
                return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player) livingentity).isCreative();
            }
        }

        public void start() {
            this.mob.getNavigation().moveTo(this.path, this.speedModifier);
            this.mob.setAggressive(true);
            this.ticksUntilNextPathRecalculation = 0;
            this.ticksUntilNextAttack = 0;
            this.animTime = 0;
            this.mob.setAnimationState(0);
        }

        public void stop() {
            LivingEntity livingentity = this.mob.getTarget();
            if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
                this.mob.setTarget((LivingEntity) null);
            }
            this.mob.setAnimationState(0);
            this.mob.setAggressive(false);
        }

        public void tick() {

            LivingEntity target = this.mob.getTarget();
            double distance = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
            double reach = this.getAttackReachSqr(target);
            int animState = this.mob.getAnimationState();
            Vec3 aim = this.mob.getLookAngle();
            Vec2 aim2d = new Vec2((float) (aim.x / (1 - Math.abs(aim.y))), (float) (aim.z / (1 - Math.abs(aim.y))));

            switch (animState) {
                case 21 -> tickLightAttack1();
                case 22 -> tickLightAttack2();
                case 23 -> tickStrongHeadbuttAttack();
                case 24 -> tickKickAttack();
                default -> {
                    this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                    this.ticksUntilNextAttack = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                    this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    this.doMovement(target, distance);
                    this.checkForCloseRangeAttack(distance, reach);
                }
            }
        }

            protected void doMovement (LivingEntity livingentity, Double d0){

                this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);

                if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D || this.mob.getRandom().nextFloat() < 0.05F)) {
                    this.pathedTargetX = livingentity.getX();
                    this.pathedTargetY = livingentity.getY();
                    this.pathedTargetZ = livingentity.getZ();
                    this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                    if (this.canPenalize) {
                        this.ticksUntilNextPathRecalculation += failedPathFindingPenalty;
                        if (this.mob.getNavigation().getPath() != null) {
                            Node finalPathPoint = this.mob.getNavigation().getPath().getEndNode();
                            if (finalPathPoint != null && livingentity.distanceToSqr(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1)
                                failedPathFindingPenalty = 0;
                            else
                                failedPathFindingPenalty += 10;
                        } else {
                            failedPathFindingPenalty += 10;
                        }
                    }
                    if (d0 > 1024.0D) {
                        this.ticksUntilNextPathRecalculation += 10;
                    } else if (d0 > 256.0D) {
                        this.ticksUntilNextPathRecalculation += 5;
                    }

                    if (!this.mob.getNavigation().moveTo(livingentity, this.speedModifier)) {
                        this.ticksUntilNextPathRecalculation += 15;
                    }
                }
            }

            protected void checkForCloseRangeAttack ( double distance, double reach){
                if (distance <= reach && this.ticksUntilNextAttack <= 0) {
                    int r = this.mob.getRandom().nextInt(2048);
                    if (r <= 800) {
                        this.mob.setAnimationState(21);
                    } else if (r <= 1300) {
                        this.mob.setAnimationState(22);
                    } else if (r <= 1850) {
                        this.mob.setAnimationState(23);
                    } else {
                        this.mob.setAnimationState(24);
                    }

                }
            }
            protected boolean getRangeCheck () {

                return
                this.mob.distanceToSqr(this.mob.getTarget().getX(), this.mob.getTarget().getY(), this.mob.getTarget().getZ())
                        <=
                        1.3F * this.getAttackReachSqr(this.mob.getTarget());
            }

        protected void tickLightAttack1 () {
            animTime++;
            if(animTime==4) {
                performLightAttack();
            }
            if(animTime>=8) {
                animTime=0;
                if (this.getRangeCheck()) {
                    this.mob.setAnimationState(22);
                }else {
                    this.mob.setAnimationState(0);
                    this.resetAttackCooldown();
                    this.ticksUntilNextPathRecalculation = 0;
                }
            }
        }

        protected void tickLightAttack2 () {
            animTime++;

            if(animTime==4) {
                performLightAttack();
            }
            if(animTime>=7) {
                animTime=0;

                this.mob.setAnimationState(0);
                this.resetAttackCooldown();
                this.ticksUntilNextPathRecalculation = 0;

            }
        }

            protected void tickStrongHeadbuttAttack () {
                animTime++;
                if(animTime==8) {
                    performStrongAttack();
                }
                if(animTime>=14) {
                    animTime=0;
                    this.mob.setAnimationState(0);
                    this.resetAttackCooldown();
                    this.ticksUntilNextPathRecalculation = 0;
                }
            }
            protected void tickKickAttack () {
                animTime++;
                if(animTime==7) {
                    performAttackKick();
                }
                if(animTime>=12) {
                    animTime=0;
                    this.mob.setAnimationState(0);
                    this.resetAttackCooldown();
                    this.ticksUntilNextPathRecalculation = 0;
                }
            }

            protected void performLightAttack () {
                Vec3 pos = mob.position();
                this.mob.playSound(UPSounds.PACHY_HEADBUTT.get(), 2.0f, 0.2f);
                HitboxHelper.LargeAttackWithTargetCheck(this.mob.damageSources().mobAttack(mob),8.0f, 0.1f, mob, pos,  2.1F, -Math.PI/5, Math.PI/3, -1.0f, 3.0f);

            }

        protected void performStrongAttack () {
            Vec3 pos = mob.position();
            this.mob.playSound(UPSounds.PACHY_HEADBUTT.get(), 0.5F, 0.5F);
            HitboxHelper.LargeAttackWithTargetCheck(this.mob.damageSources().mobAttack(mob),12.0f, 0.1f, mob, pos,  2.1F, -Math.PI/5, Math.PI/3, -1.0f, 3.0f);

        }

        protected void performAttackKick () {
            Vec3 pos = mob.position();
            this.mob.playSound(UPSounds.PACHY_KICK.get(), 0.5F, 0.5F);
            HitboxHelper.LargeAttackWithTargetCheck(this.mob.damageSources().mobAttack(mob),15.0f, 1.0f, mob, pos,  2.1F, -Math.PI/5, Math.PI/3, -1.0f, 3.0f);

        }

            protected void resetAttackCooldown () {
                this.ticksUntilNextAttack = 0;
            }

            protected boolean isTimeToAttack () {
                return this.ticksUntilNextAttack <= 0;
            }

            protected int getTicksUntilNextAttack () {
                return this.ticksUntilNextAttack;
            }

            protected int getAttackInterval () {
                return 3;
            }

        protected double getAttackReachSqr(LivingEntity p_25556_) {
            return (double)(this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 1.3F + p_25556_.getBbWidth());
        }
        }


    }

