package com.peeko32213.unusualprehistory.common.entity;


import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityTameableBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.*;
import com.peeko32213.unusualprehistory.common.entity.msc.util.helper.HitboxHelper;
import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.CustomFollower;
import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.SemiAquatic;
import com.peeko32213.unusualprehistory.common.entity.msc.util.navigator.SemiAquaticPathNavigation;
import com.peeko32213.unusualprehistory.common.entity.msc.util.navigator.WaterMoveController;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.EnumSet;

public class EntityKaprosuchus extends EntityTameableBaseDinosaurAnimal implements CustomFollower, SemiAquatic {

    private static final RawAnimation KAPROSUCHUS_WALK = RawAnimation.begin().thenLoop("animation.kaprosuchus.walking");
    private static final RawAnimation KAPROSUCHUS_RUN = RawAnimation.begin().thenLoop("animation.kaprosuchus.run");
    private static final RawAnimation KAPROSUCHUS_SIT = RawAnimation.begin().thenLoop("animation.kaprosuchus.sit");
    private static final RawAnimation KAPROSUCHUS_SWIM = RawAnimation.begin().thenLoop("animation.kaprosuchus.swim");
    private static final RawAnimation KAPROSUCHUS_SCRATCH = RawAnimation.begin().thenLoop("animation.kaprosuchus.scratch");
    private static final RawAnimation KAPROSUCHUS_IDLE = RawAnimation.begin().thenLoop("animation.kaprosuchus.idle");
    private static final RawAnimation KAPROSUCHUS_ATTACK_1 = RawAnimation.begin().thenLoop("animation.kaprosuchus.attack1");
    private static final RawAnimation KAPROSUCHUS_ATTACK_2 = RawAnimation.begin().thenLoop("animation.kaprosuchus.attack2");
    private static final RawAnimation KAPROSUCHUS_ATTACK_SWIM = RawAnimation.begin().thenLoop("animation.kaprosuchus.swimattack");
    private static final RawAnimation KAPROSUCHUS_ROAR = RawAnimation.begin().thenLoop("animation.kaprosuchus.roar");
    private static final RawAnimation KAPROSUCHUS_SWIM_IDLE = RawAnimation.begin().thenLoop("animation.kaprosuchus.swimidle");

    private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(EntityKaprosuchus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COMBAT_STATE = SynchedEntityData.defineId(EntityKaprosuchus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ENTITY_STATE = SynchedEntityData.defineId(EntityKaprosuchus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(EntityKaprosuchus.class, EntityDataSerializers.INT);
    public float sitProgress;
    public float prevSwimProgress;
    public float swimProgress;
    private int swimTimer = -1000;
    private boolean isLandNavigator;
    public EntityKaprosuchus(EntityType<? extends EntityTameableBaseDinosaurAnimal> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
        switchNavigator(false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.22D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 10.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(7, new FindWaterGoal(this));
        this.goalSelector.addGoal(7, new LeaveWaterGoal(this));
        this.goalSelector.addGoal(9, new SemiAquaticSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(1, new EntityKaprosuchus.KaproMeleeAttackGoal(this, 1.5F, true));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 50, 1.0D, 100, 34) {
                    @Override
                    public boolean canUse() {
                        if (this.mob.isVehicle()) {
                            return false;
                        } else {
                            if (!this.forceTrigger) {
                                if (this.mob.getNoActionTime() >= 100) {
                                    return false;
                                }
                                if (((EntityKaprosuchus) this.mob).isHungry()) {
                                    if (this.mob.getRandom().nextInt(60) != 0) {
                                        return false;
                                    }
                                } else {
                                    if (this.mob.getRandom().nextInt(30) != 0) {
                                        return false;
                                    }
                                }
                            }

                            Vec3 vec3d = this.getPosition();
                            if (vec3d == null) {
                                return false;
                            } else {
                                this.wantedX = vec3d.x;
                                this.wantedY = vec3d.y;
                                this.wantedZ = vec3d.z;
                                this.forceTrigger = false;
                                return true;
                            }
                        }
                    }
                }
        );
        this.goalSelector.addGoal(3, new TameableFollowOwner(this, 1.2D, 5.0F, 2.0F, false));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 60, 1.0D, 100, 34));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, false, false, entity -> entity.getType().is(UPTags.PSITTACO_TARGETS)));
    }

    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new WaterMoveController(this, 1.1F);
            this.navigation = new SemiAquaticPathNavigation(this, level());
            this.isLandNavigator = false;
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(UPItems.ENCYLOPEDIA.get())) {
            InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, hand);
        }
        if (itemstack.is(UPItems.RAW_AUSTRO.get())) {
            if (!isTame()) {
                this.usePlayerItem(player, hand, itemstack);
                if (getRandom().nextInt(3) == 0) {
                    this.tame(player);
                    this.level().broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                }
            }
        }
        if (isTame() && isOwnedBy(player)) {
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                if(!this.level().isClientSide) {
                    this.heal((float) itemstack.getFoodProperties(this).getNutrition());
                }
                this.gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;
            }
            else  {
                this.setCommand((this.getCommand() + 1) % 3);

                if (this.getCommand() == 3) {
                    this.setCommand(0);
                }
                player.displayClientMessage(Component.translatable("entity.unusualprehistory.all.command_" + this.getCommand(), this.getName()), true);
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
        return InteractionResult.SUCCESS;

    }


    public void tick() {
        super.tick();
        this.prevSwimProgress = swimProgress;

        final boolean ground = !this.isInWaterOrBubble();

        if (!ground && this.isLandNavigator) {
            switchNavigator(false);
        }
        if (ground && !this.isLandNavigator) {
            switchNavigator(true);
        }
        if (ground && swimProgress > 0) {
            swimProgress--;
        }
        if (!ground && swimProgress < 5F) {
            swimProgress++;
        }
        if (!this.level().isClientSide) {
            if (isInWater()) {
                swimTimer++;
            } else {
                swimTimer--;
            }
        }
        if(attackCooldown > 0){
            attackCooldown--;
        }

        if (this.isOrderedToSit() && sitProgress < 5F) {
            sitProgress++;
        }
        if (!this.isOrderedToSit() && sitProgress > 0F) {
            sitProgress--;
        }
        if (this.getCommand() == 2 && !this.isVehicle()) {
            this.setOrderedToSit(true);
        } else {
            this.setOrderedToSit(false);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(COMMAND, 0);
        this.entityData.define(ANIMATION_STATE, 0);
        this.entityData.define(COMBAT_STATE, 0);
        this.entityData.define(ENTITY_STATE, 0);
    }

    public int getCommand() {
        return this.entityData.get(COMMAND).intValue();
    }

    public void setCommand(int command) {
        this.entityData.set(COMMAND, Integer.valueOf(command));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Command", this.getCommand());
        compound.putInt("SwimTimer", this.swimTimer);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setCommand(compound.getInt("Command"));
        this.swimTimer = compound.getInt("SwimTimer");
    }

    @Override
    public boolean shouldFollow() {
        return this.getCommand() == 1;
    }

    @Override
    protected void performAttack() {

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
        return true;
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

        return entityIn.is(this);
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

    @Override
    public boolean shouldEnterWater() {
        return !shouldLeaveWater() && swimTimer <= -1000;
    }

    public boolean shouldLeaveWater() {
        LivingEntity target = this.getTarget();
        if (target != null && !target.isInWater()) {
            return true;
        }
        return swimTimer > 600;
    }

    @Override
    public int getWaterSearchRange() {
        return 12;
    }
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean shouldStopMoving() {
        return false;
    }


    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        }
        else {
            super.travel(travelVector);
        }
    }

    static class KaproMeleeAttackGoal extends Goal {

        protected final EntityKaprosuchus mob;
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


        public KaproMeleeAttackGoal(EntityKaprosuchus p_i1636_1_, double p_i1636_2_, boolean p_i1636_4_) {
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

        }

        public void tick() {


            LivingEntity target = this.mob.getTarget();
            double distance = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
            double reach = this.getAttackReachSqr(target);
            int animState = this.mob.getAnimationState();
            Vec3 aim = this.mob.getLookAngle();
            Vec2 aim2d = new Vec2((float) (aim.x / (1 - Math.abs(aim.y))), (float) (aim.z / (1 - Math.abs(aim.y))));


            switch (animState) {
                case 21 -> tickHeadbuttAttack();
                case 22 -> tickBiteAttack();
                case 23 -> tickWaterBiteAttack();
                default -> {
                    this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                    this.ticksUntilNextAttack = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                    this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    this.doMovement(target, distance);
                    this.checkForCloseRangeAttack(distance, reach);
                }
            }
        }

        protected void doMovement(LivingEntity livingentity, Double d0) {


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


        protected void checkForCloseRangeAttack(double distance, double reach) {
            if (distance <= reach && this.ticksUntilNextAttack <= 0) {
                int r = this.mob.getRandom().nextInt(2048);
                if (r <= 1200) {
                    this.mob.setAnimationState(21);
                }
            }
        }


        protected boolean getRangeCheck() {

            return
                    this.mob.distanceToSqr(this.mob.getTarget().getX(), this.mob.getTarget().getY(), this.mob.getTarget().getZ())
                            <=
                            1.8F * this.getAttackReachSqr(this.mob.getTarget());
        }




        protected void tickHeadbuttAttack () {
            animTime++;
            if(animTime==4) {
                this.mob.lookAt(this.mob.getTarget(), 100000, 100000);
                preformHeadbuttAttack();
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


        protected void tickBiteAttack () {
            animTime++;
            if(animTime==4) {
                this.mob.lookAt(this.mob.getTarget(), 100000, 100000);
                preformBiteAttack();
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

        protected void tickWaterBiteAttack () {
            if(this.mob.isInWater()) {
                animTime++;
                if (animTime == 4) {
                    this.mob.lookAt(this.mob.getTarget(), 100000, 100000);
                    preformWaterBiteAttack();
                }
                if (animTime >= 8) {
                    animTime = 0;
                    if (this.getRangeCheck()) {
                        this.mob.setAnimationState(22);
                    } else {
                        this.mob.setAnimationState(0);
                        this.resetAttackCooldown();
                        this.ticksUntilNextPathRecalculation = 0;
                    }
                }
            }
        }



        protected void preformHeadbuttAttack() {
            Vec3 pos = mob.position();
            HitboxHelper.LargeAttack(this.mob.damageSources().mobAttack(mob), 5.0f, 1.3f, mob, pos, 1.5F, -Math.PI / 2, Math.PI / 2, -1.0f, 3.0f);
        }

        protected void  preformBiteAttack() {
            Vec3 pos = mob.position();
            HitboxHelper.LargeAttack(this.mob.damageSources().mobAttack(mob), 8.0f, 0.2f, mob, pos, 1.5F, -Math.PI / 2, Math.PI / 2, -1.0f, 3.0f);
        }

        protected void  preformWaterBiteAttack() {
            Vec3 pos = mob.position();
            if(this.mob.isInWater()) {
                HitboxHelper.LargeAttack(this.mob.damageSources().mobAttack(mob), 8.0f, 0.1f, mob, pos, 1.5F, -Math.PI / 2, Math.PI / 2, -1.0f, 3.0f);
            }

        }

        protected void resetAttackCooldown() {
            this.ticksUntilNextAttack = 0;
        }

        protected boolean isTimeToAttack() {
            return this.ticksUntilNextAttack <= 0;
        }

        protected int getTicksUntilNextAttack() {
            return this.ticksUntilNextAttack;
        }

        protected int getAttackInterval() {
            return 5;
        }

        protected double getAttackReachSqr(LivingEntity p_179512_1_) {
            return (double) (this.mob.getBbWidth() * 2.5F * this.mob.getBbWidth() * 1.8F + p_179512_1_.getBbWidth());
        }
    }


    private boolean isStillEnough() {
        return this.getDeltaMovement().horizontalDistance() < 0.05;
    }


    protected <E extends EntityKaprosuchus> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if(this.isFromBook()){
            return PlayState.CONTINUE;
        }
        int animState = this.getAnimationState();
        {
            switch (animState) {

                case 21:
                    event.setAndContinue(KAPROSUCHUS_ATTACK_1);
                    break;
                case 22:
                    event.setAndContinue(KAPROSUCHUS_ATTACK_2);
                    break;
                case 23:
                    event.setAndContinue(KAPROSUCHUS_ATTACK_SWIM);
                    break;

                default:
                    if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isInSittingPose() && !this.isInWater() ) {
                        if (this.isSprinting()) {
                            event.setAndContinue(KAPROSUCHUS_RUN);
                            event.getController().setAnimationSpeed(2.0D);
                            return PlayState.CONTINUE;
                        } else if (event.isMoving()) {
                            event.setAndContinue(KAPROSUCHUS_WALK);
                            event.getController().setAnimationSpeed(1.0D);
                            return PlayState.CONTINUE;
                        }
                    }
                    if (this.isInSittingPose() && !this.isInWater() && !this.isSwimming()) {
                        return event.setAndContinue(KAPROSUCHUS_SIT);
                    }
                    if (this.isInSittingPose() && this.isInWater() && this.isSwimming()) {
                        return event.setAndContinue(KAPROSUCHUS_SWIM_IDLE);
                    }
                    if (this.isInWater()) {
                        event.setAndContinue(KAPROSUCHUS_SWIM);
                        event.getController().setAnimationSpeed(1.0F);
                        return PlayState.CONTINUE;
                    }
                     else if (isStillEnough() && random.nextInt(100) == 0 && !this.isInSittingPose() && !this.isSwimming()) {
                        float rand = random.nextFloat();
                        if (rand < 0.15F) {
                            return event.setAndContinue(KAPROSUCHUS_ROAR);
                        }
                        if (rand < 0.66F) {
                            return event.setAndContinue(KAPROSUCHUS_SCRATCH);
                        }
                        event.setAndContinue(KAPROSUCHUS_IDLE);
                    }
            }
        }
        return PlayState.CONTINUE;
    }


    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 10, this::Controller));
    }

}
