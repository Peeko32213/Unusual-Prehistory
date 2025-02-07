package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.config.UnusualPrehistoryConfig;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.CustomRandomStrollGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.helper.HitboxHelper;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class EntityTyrannosaurusRex extends EntityBaseDinosaurAnimal {

    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(EntityTyrannosaurusRex.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COMBAT_STATE = SynchedEntityData.defineId(EntityTyrannosaurusRex.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ENTITY_STATE = SynchedEntityData.defineId(EntityTyrannosaurusRex.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> EEPY = SynchedEntityData.defineId(EntityTyrannosaurusRex.class, EntityDataSerializers.BOOLEAN);
    public int timeUntilDrops = this.random.nextInt(12000) + 24000;

//    private int bladder = 3000;
    private int passiveFor = 0;
    private int shakeCooldown = 0;

    private int stunnedTick;
    private static final RawAnimation REX_BITE = RawAnimation.begin().thenLoop("rex.bite");
    private static final RawAnimation REX_WHIP = RawAnimation.begin().thenLoop("rex.whip");
    private static final RawAnimation REX_STOMP_L = RawAnimation.begin().thenLoop("rex.stompl");
    private static final RawAnimation REX_STOMP_R = RawAnimation.begin().thenLoop("rex.stompr");
    private static final RawAnimation REX_EEPY = RawAnimation.begin().thenLoop("rex.eepy");
    private static final RawAnimation REX_SWIM = RawAnimation.begin().thenLoop("rex.swim");
    private static final RawAnimation REX_CHARGE = RawAnimation.begin().thenLoop("rex.charge");
    private static final RawAnimation REX_WALK = RawAnimation.begin().thenLoop("rex.walk");
    private static final RawAnimation REX_IDLE = RawAnimation.begin().thenLoop("rex.idle");

    public EntityTyrannosaurusRex(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 300.0D)
                .add(Attributes.ARMOR, 15.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 10.5D)
                .add(Attributes.ATTACK_KNOCKBACK, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 50D);


    }



    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new EntityTyrannosaurusRex.RexMeleeAttackGoal(this, 2F, true){
            public boolean canUse() {
                return !isBaby() && passiveFor == 0 && level().getDifficulty() != Difficulty.PEACEFUL && super.canUse()   ;
            }
        });
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this) {
            public boolean canUse() {
                return !hasEepy() && passiveFor == 0 && super.canUse();
            }
        }));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, false, false, entity -> entity.getType().is(UPTags.REX_TARGETS)){
            public boolean canUse() {
                return !hasEepy() && passiveFor == 0 && super.canUse();
            }
        });
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
    }

    public InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if(item == UPItems.ADORNED_STAFF.get() && this.hasEepy()) {

            itemstack.hurtAndBreak(5, player, (p_29822_) -> {
                p_29822_.broadcastBreakEvent(hand);
            });
            if(!this.level().isClientSide) {
                this.heal(200);
                this.setTarget(null);
                this.setEepy(false);
                this.passiveFor = 1000000000 + random.nextInt(1000000000);
            }
            return InteractionResult.SUCCESS;
        }

//        if(item == UPItems.FLASK.get() && this.bladder >= 600) {
//            //each jarate harvest consumes 600 piss
//
//            player.getItemInHand(hand).shrink(1);
//            player.addItem(new ItemStack(UPItems.T_JARATE.get()));
//            //add jarate if the rex is ready to piss
//
//            double anger = Math.random();
//            //between 0 and 1
//            if (anger <= 0.25) {
//                this.setTarget(player);
//                //rex will randomly be pissed off if it had been harvested
//            }
//
//            this.bladder -= 600;
//            //bladder drain
//            return InteractionResult.SUCCESS;
//        }

        return InteractionResult.FAIL;
    }

    @Override
    public boolean canBeLeashed(Player p_21418_) {
        return this.hasEepy();
    }

    @Override
    public boolean canAttack(LivingEntity entity) {
        boolean prev = super.canAttack(entity);
        if(hasEepy()){
            return false;
        }
        return prev;
    }

    @Override
    protected SoundEvent getAttackSound() {
        //Rex has custom so null
        return null;
    }

    @Override
    protected int getKillHealAmount() {
        return 50;
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
        return true;
    }

    @Override
    protected boolean hasMakeStuckInBlock() {
        return false;
    }

    @Override
    protected boolean customMakeStuckInBlockCheck(BlockState blockState) {
        return false;
    }
    //This one has custom goal so just return null since we wont be using goal in super class
    @Override
    protected TagKey<EntityType<?>> getTargetTag() {
        return null;
    }


    public void setEepy(boolean eepy) {
        this.entityData.set(EEPY, Boolean.valueOf(eepy));
    }

    public boolean hasEepy() {
        return this.entityData.get(EEPY).booleanValue();
    }

    public boolean shouldBeEepy() {
        return this.getHealth() <= this.getMaxHealth() / 15.0F;
    }

    public void travel(Vec3 vec3d) {
        if (this.shouldBeEepy()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();

            }
            vec3d = Vec3.ZERO;
        }
        super.travel(vec3d);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("StunTick", this.stunnedTick);
        compound.putBoolean("Eepy", this.hasEepy());
        compound.putInt("PassiveFor", passiveFor);
        compound.putInt("DropTime", this.timeUntilDrops);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.stunnedTick = compound.getInt("StunTick");
        this.setEepy(compound.getBoolean("Eepy"));
        passiveFor = compound.getInt("PassiveFor");
        if (compound.contains("SpitTime")) {
            this.timeUntilDrops = compound.getInt("DropTime");
        }

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION_STATE, 0);
        this.entityData.define(COMBAT_STATE, 0);
        this.entityData.define(ENTITY_STATE, 0);
        this.entityData.define(EEPY, false);
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 39) {
            this.stunnedTick = 60;
        }
        else {
            super.handleEntityEvent(pId);
        }
    }

    public void tick() {
        super.tick();

        if (this.shouldBeEepy()) {
            this.setEepy(true);
        }

//        if (this.bladder < 6000) {
//            // a rex can stash a max of 10 jars of jarate inside itself
//            this.bladder += 1;
//        }


        if (!this.level().isClientSide && this.isAlive() && this.passiveFor > 0 && --this.timeUntilDrops <= 0) {
            this.spawnAtLocation(UPItems.REX_TOOTH.get(), 4);
            this.spawnAtLocation(UPItems.REX_SCALE.get(), 9);
            this.timeUntilDrops = this.random.nextInt(12000) + 24000;
        }
        if(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming() && !this.isInWater()) {
            if(this.shakeCooldown <= 0 && UnusualPrehistoryConfig.SCREEN_SHAKE_REX.get()) {
                double rexShakeRange = UnusualPrehistoryConfig.SCREEN_SHAKE_REX_RANGE.get();
                int rexShakeAmp= UnusualPrehistoryConfig.SCREEN_SHAKE_REX_AMPLIFIER.get();
                float rexMoveSoundVolume= UnusualPrehistoryConfig.REX_SOUND_VOLUME.get();
                List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(rexShakeRange));
                for (LivingEntity e : list) {
                    if (!(e instanceof EntityTyrannosaurusRex) && e.isAlive()) {
                        e.addEffect(new MobEffectInstance(UPEffects.SCREEN_SHAKE.get(), 20, rexShakeAmp, false, false, false));
                        this.playSound(UPSounds.REX_STEP.get(), rexMoveSoundVolume, 0.70F);
                    }
                }
                shakeCooldown = 30;
            }
        }
        shakeCooldown--;
    }

    @Override
    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.5D);
        } else {
            this.setSprinting(false);
        }
        super.customServerAiStep();
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.shouldBeEepy()) {
            this.stunnedTick = 60;
            this.level().broadcastEntityEvent(this, (byte)39);
            this.setEepy(true);
            this.setAggressive(false);
            this.setTarget(null);
        }

        if (this.horizontalCollision && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), this) && this.isSprinting()) {
            boolean flag = false;
            AABB axisalignedbb = this.getBoundingBox().inflate(0.2D);
            for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(axisalignedbb.minX), Mth.floor(axisalignedbb.minY), Mth.floor(axisalignedbb.minZ), Mth.floor(axisalignedbb.maxX), Mth.floor(axisalignedbb.maxY), Mth.floor(axisalignedbb.maxZ))) {
                BlockState blockstate = this.level().getBlockState(blockpos);
                if (blockstate.is(UPTags.TRIKE_BREAKABLES)) {
                    flag = this.level().destroyBlock(blockpos, true, this) || flag;
                }
            }
        }

    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }


    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.hasCustomName();
    }

    public boolean removeWhenFarAway(double d) {
        return !this.hasCustomName();
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



    static class RexMeleeAttackGoal extends Goal {

        protected final EntityTyrannosaurusRex mob;
        private final double speedModifier;
        private final boolean followingTargetEvenIfNotSeen;
        private Path path;
        private double pathedTargetX;
        private double pathedTargetY;
        private double pathedTargetZ;
        private int ticksUntilNextPathRecalculation;
        private int ticksUntilNextAttack;
        private long lastCanUseCheck;

        private int animTime = 0;


        public RexMeleeAttackGoal(EntityTyrannosaurusRex p_i1636_1_, double p_i1636_2_, boolean p_i1636_4_) {
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
            }
            else if (!livingentity.isAlive()) {
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
                this.mob.setAnimationState(0);
                this.mob.setTarget((LivingEntity) null);
            }
            this.mob.setAnimationState(0);
        }

        public void tick() {


            LivingEntity target = this.mob.getTarget();

            double distance = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
            double reach = this.getAttackReachSqr(target);
            int animState = this.mob.getAnimationState();

            switch (animState) {
                case 21 -> tickBiteAttack();
                case 22 -> tickWhipAttack();
                case 23 -> tickStompAttack();
                case 24 -> tickStompAttack();
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
                if (r <= 600) {
                    this.mob.setAnimationState(21);
                } else if (r > 600 && r <= 800) {
                    this.mob.setAnimationState(22);
                } else if (r > 800 && r <= 1400) {
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
                            1.8F * this.getAttackReachSqr(this.mob.getTarget());
        }



        protected void tickBiteAttack () {
            animTime++;
            if(animTime>=6 && animTime < 9) {
                preformBiteAttack();

            }
            if(animTime>=9) {
                animTime=0;

                this.mob.setAnimationState(0);
                this.resetAttackCooldown();
                this.ticksUntilNextPathRecalculation = 0;
            }
        }

        protected void tickWhipAttack () {
            animTime++;
            //this.mob.getNavigation().stop();
            if(animTime>=15 && animTime < 18) {
                preformWhipAttack();
            }
            if(animTime>=16) {
                animTime=0;

                this.mob.setAnimationState(0);
                this.resetAttackCooldown();
                this.ticksUntilNextPathRecalculation = 0;

            }

        }

        protected void tickStompAttack () {
            animTime++;
            //this.mob.getNavigation().stop();
            if(animTime>=25 && animTime < 30) {
                preformStompAttack();
            }
            if(animTime>=30) {
                animTime=0;
                this.mob.setAnimationState(0);
                this.resetAttackCooldown();
                this.ticksUntilNextPathRecalculation = 0;
            }

        }


        protected void preformBiteAttack () {
            Vec3 pos = mob.position();
            this.mob.playSound(UPSounds.REX_BITE.get(), 1.0F, 1.0F);
            HitboxHelper.LargeAttack(this.mob.damageSources().mobAttack(mob),10.0f, 0.1f, mob, pos,  5.0F, -Math.PI/2, Math.PI/2, -1.0f, 3.0f);

        }

        protected void preformWhipAttack () {
            Vec3 pos = mob.position();
            this.mob.playSound(UPSounds.REX_TAIL_SWIPE.get(), 1.0F, 1.0F);
            HitboxHelper.LargeAttack(this.mob.damageSources().mobAttack(mob),10.0f, 2.0f, mob, pos,  8.0F, -Math.PI/2, Math.PI/2, -1.0f, 3.0f);

        }

        protected void preformStompAttack () {
            Vec3 pos = mob.position();
            this.mob.playSound(UPSounds.REX_STOMP_ATTACK.get(), 1.9F, 1.9F);
            HitboxHelper.LargeAttack(this.mob.damageSources().mobAttack(mob),25.0f, 2.5f, mob, pos,  7.0F, -Math.PI/2, Math.PI/2, -1.0f, 3.0f);
            if(this.mob.shakeCooldown <= 0 && UnusualPrehistoryConfig.SCREEN_SHAKE_REX.get()) {
                double rexShakeRange = UnusualPrehistoryConfig.SCREEN_SHAKE_BRACHI_RANGE.get();
                List<LivingEntity> list = this.mob.level().getEntitiesOfClass(LivingEntity.class, this.mob.getBoundingBox().inflate(rexShakeRange));
                for (LivingEntity e : list) {
                    if (!(e instanceof EntityTyrannosaurusRex) && e.isAlive()) {
                        e.addEffect(new MobEffectInstance(UPEffects.SCREEN_SHAKE.get(), 10, 3, false, false, false));
                    }
                }
                mob.shakeCooldown = 100;
            }
            mob.shakeCooldown--;
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
            return 5;
        }

        protected double getAttackReachSqr(LivingEntity p_179512_1_) {
            return (double)(this.mob.getBbWidth() * 2.5F * this.mob.getBbWidth() * 1.8F + p_179512_1_.getBbWidth());
        }
    }

    public boolean canBeCollidedWith() {
        return true;
    }


    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL) ||source.is(DamageTypes.MAGIC) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.FALLING_BLOCK) || source.is(DamageTypes.CACTUS) || source.is(DamageTypes.MOB_PROJECTILE) || super.isInvulnerableTo(source);
    }

    @Override
    public void kill() {
        this.remove(RemovalReason.KILLED);
        this.gameEvent(GameEvent.ENTITY_DIE);
    }

    public boolean canBeAffected(MobEffectInstance p_33809_) {
        if (p_33809_.getEffect() == MobEffects.WEAKNESS) {
            net.minecraftforge.event.entity.living.MobEffectEvent.Applicable event = new net.minecraftforge.event.entity.living.MobEffectEvent.Applicable(this, p_33809_);
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
            return event.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW;
        }
        return super.canBeAffected(p_33809_);
    }


    protected SoundEvent getAmbientSound() {
        return UPSounds.REX_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.REX_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.REX_DEATH.get();
    }


    protected <E extends EntityTyrannosaurusRex> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if(this.isFromBook()){
            return PlayState.CONTINUE;
        }
        int animState = this.getAnimationState();
        {
            switch (animState) {

                case 21:
                    event.setAndContinue(REX_BITE);
                    break;
                case 22:
                    event.setAndContinue(REX_WHIP);
                    break;
                case 23:
                    event.setAndContinue(REX_STOMP_L);
                    break;
                case 24:
                    event.setAndContinue(REX_STOMP_R);
                    break;
                default:
                    if (this.hasEepy()) {
                        event.setAndContinue(REX_EEPY);
                        event.getController().setAnimationSpeed(1.0F);
                        return PlayState.CONTINUE;
                    }
                    if (this.isInWater()) {
                        event.setAndContinue(REX_SWIM);
                        event.getController().setAnimationSpeed(1.0F);
                        return PlayState.CONTINUE;
                    }
                    else if(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming() && ! this.isInWater()){
                        if(this.isSprinting()) {
                            event.setAndContinue(REX_CHARGE);
                            event.getController().setAnimationSpeed(1.0F);
                            return PlayState.CONTINUE;
                        } else {
                            event.setAndContinue(REX_WALK);
                            event.getController().setAnimationSpeed(1.0F);
                            return PlayState.CONTINUE;
                        }
                    }else{

                        if(!this.isInWater()) {
                            event.setAndContinue(REX_IDLE);
                            event.getController().setAnimationSpeed(1.0F);
                            return PlayState.CONTINUE;
                        }
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

}