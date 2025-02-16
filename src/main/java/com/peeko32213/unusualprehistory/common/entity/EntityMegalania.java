package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.CustomRandomStrollGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.SleepRandomLookAroundGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.helper.HitboxHelper;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class EntityMegalania extends EntityBaseDinosaurAnimal implements IVariantEntity{
    private static final ResourceLocation TEXTURE_TEMPERATE = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania.png");
    private static final ResourceLocation TEXTURE_COLD = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania_cold.png");
    private static final ResourceLocation TEXTURE_HOT = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania_hot.png");
    private static final ResourceLocation TEXTURE_NETHER = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania_nether.png");
    private static final ResourceLocation TEXTURE_TEMPERATE_BABY = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania_baby.png");
    private static final ResourceLocation TEXTURE_COLD_BABY = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania_cold_baby.png");
    private static final ResourceLocation TEXTURE_HOT_BABY = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania_hot_baby.png");
    private static final ResourceLocation TEXTURE_NETHER_BABY = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania_nether_baby.png");
    private static final EntityDataAccessor<Integer> COMBAT_STATE = SynchedEntityData.defineId(EntityMegalania.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ENTITY_STATE = SynchedEntityData.defineId(EntityMegalania.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(EntityMegalania.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> ASLEEP = SynchedEntityData.defineId(EntityMegalania.class, EntityDataSerializers.BOOLEAN);

    private Ingredient temptationItems;
    private float sleepProgress = 0.0F;
    private float prevSleepProgress = 0.0F;
    private int stunnedTick;
    private static final RawAnimation MEGALANIA_WALK = RawAnimation.begin().thenLoop("animation.megalania.walk");
    private static final RawAnimation MEGALANIA_IDLE = RawAnimation.begin().thenLoop("animation.megalania.idle");
    private static final RawAnimation MEGALANIA_SPRINT = RawAnimation.begin().thenLoop("animation.megalania.sprint");
    private static final RawAnimation MEGALANIA_SWIM = RawAnimation.begin().thenLoop("animation.megalania.swim");
    private static final RawAnimation MEGALANIA_REST = RawAnimation.begin().thenLoop("animation.megalania.resting");
    private static final RawAnimation MEGALANIA_BITE = RawAnimation.begin().thenLoop("animation.megalania.bite");

    private static final RawAnimation MEGALANIA_BABY_WALK = RawAnimation.begin().thenLoop("animation.baby_megalania.walk");
    private static final RawAnimation MEGALANIA_BABY_IDLE = RawAnimation.begin().thenLoop("animation.baby_megalania.idle");
    private static final RawAnimation MEGALANIA_BABY_SWIM = RawAnimation.begin().thenLoop("animation.baby_megalania.swim");

    public EntityMegalania(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 35.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.16D)
            .add(Attributes.ARMOR, 10.0D)
            .add(Attributes.ARMOR_TOUGHNESS, 5.0D)
            .add(Attributes.ATTACK_DAMAGE, 15.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 3.5D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new EntityMegalania.MegaMeleeAttackGoal(this,  1.6F, true));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, false, false, entity -> entity.getType().is(UPTags.MEGALANIA_TARGETS)));
        //Todo Doesnt seem to work correctly, attacks megalania when it got attacked by it
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));

        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F)
        {
            @Override
            public boolean canUse() {
                if(this.mob instanceof EntityMegalania entityMegalania)
                {
                    if(entityMegalania.isAsleep()) return false;
                }

                return super.canUse();
            }
        });
        this.goalSelector.addGoal(8, new SleepRandomLookAroundGoal(this));

        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, 100, true, false, this::isAngryAt));
    }

    public boolean isAngryAt(LivingEntity p_21675_) {
        return this.canAttack(p_21675_);
    }

    @Override
    public boolean canAttack(LivingEntity entity) {
        boolean prev = super.canAttack(entity);
        if(prev && isBaby() || this.isAsleep()){
            return false;
        }
        if( entity.is(this))
        {
            return false;
        }
        return prev;
    }

    @Override
    public boolean isAlliedTo(Entity pEntity) {
        return pEntity.is(this);
    }

    public void travel(Vec3 vec3d) {
        if (this.isAsleep()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            this.getLookControl().setLookAt(this.position().add(2,0,2));
            vec3d = Vec3.ZERO;
        }
        super.travel(vec3d);
    }

    public boolean hurt(DamageSource source, float amount) {
        this.setAsleep(false);
        return super.hurt(source, amount);
    }

    @Override
    protected SoundEvent getAttackSound() {
        return null;
    }

    @Override
    protected int getKillHealAmount() {
        return 10;
    }

    @Override
    protected boolean canGetHungry() {
        return true;
    }

    @Override
    protected boolean hasTargets() {
        return true;
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
        return UPTags.MEGALANIA_TARGETS;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        if(pHand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if(itemStack.is(Tags.Items.TOOLS)) {
         CompoundTag compoundTag = itemStack.getTag();
            assert compoundTag != null;
            compoundTag.putInt("megalania_damage", 30);
         itemStack.setTag(compoundTag);
        }
        return super.mobInteract(pPlayer, pHand);
    }

    public boolean isHotBiome() {
        if (this.level().dimension() == Level.NETHER) {
            return true;
        } else {
            return this.level().getBiome(this.blockPosition()).is(BiomeTags.SNOW_GOLEM_MELTS);
        }
    }

    public boolean isColdBiome() {
        if (this.level().dimension() == Level.NETHER) {
            return false;
        } else {
            int i = Mth.floor(this.getX());
            int j = Mth.floor(this.getY());
            int k = Mth.floor(this.getZ());
            return this.level().getBiome(new BlockPos(i, 0, k)).value().coldEnoughToSnow(new BlockPos(i, j, k));
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        EntityMegalania megalania = UPEntities.MEGALANIA.get().create(serverLevel);
        assert megalania != null;
        megalania.setVariant(this.getVariant());
        return megalania;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION_STATE, 0);
        this.entityData.define(COMBAT_STATE, 0);
        this.entityData.define(ENTITY_STATE, 0);
        this.entityData.define(ASLEEP, false);
    }

    public boolean isAsleep() {
        return this.entityData.get(ASLEEP);
    }

    public void setAsleep(boolean isAsleep) {
        this.entityData.set(ASLEEP, isAsleep);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("IsAsleep", this.isAsleep());
        compound.putInt("StunTick", this.stunnedTick);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setAsleep(compound.getBoolean("IsAsleep"));
        this.stunnedTick = compound.getInt("StunTick");
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

    private void setColdVariant(){
        this.setVariant(1);
    }

    private void setHotVariant(){
        this.setVariant(2);
    }

    private void setNetherVariant(){
        this.setVariant(3);
    }

    private void setNormalVariant(){
        this.setVariant(0);
    }

    public void tick() {
        super.tick();

        if (this.isAsleep() && sleepProgress < 1.0F) {
            sleepProgress = Math.min(sleepProgress + 0.2F, 1.0F);
            this.stunnedTick = 60;
        }
        if (!this.isAsleep() && sleepProgress > 0.0F) {
            sleepProgress = Math.max(sleepProgress - 0.2F, 0.0F);
        }

        if(this.isAsleep()) {
            this.getLookControl().setLookAt(this.position().add(2,0,2));
        }

        if(!this.level().isClientSide){
            if (this.isHotBiome() && !isInWaterRainOrBubble()) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2);
                this.setAsleep(false);
            }
            if (this.isColdBiome() && !isInWaterRainOrBubble()) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.13);
                this.setAsleep(true);
                this.stunnedTick = 60;
            }
            else {
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.16);
            }
        }
    }

    public float getSleepProgress(float partialTick) {
        return prevSleepProgress + (sleepProgress - prevSleepProgress) * partialTick;
    }


    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance difficultyInstance, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
        SpawnGroupData data = super.finalizeSpawn(levelAccessor, difficultyInstance, spawnType, spawnGroupData, tag);

        if (levelAccessor.getLevel().dimension() == Level.NETHER) {
            setNetherVariant();
            return data;
        } else if (this.isHotBiome()) {
            setHotVariant();
        } else if (this.isColdBiome()) {
            setColdVariant();
        } else {
            setNormalVariant();
        }
        return data;
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        if(!this.isBaby()) {
            this.playSound(UPSounds.MAJUNGA_STEP.get(), 0.15F, 1.0F);
        }
    }

    protected SoundEvent getAmbientSound() { return UPSounds.MEGALANIA_IDLE.get(); }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.MEGALANIA_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.MEGALANIA_DEATH.get();
    }

    @Override
    public float getSoundVolume() {
        if(this.isBaby()){
            return 0.5F;
        }
        else{
            return 1.0F;
        }
    }

    // Variant textures
    @Override
    public ResourceLocation getVariantTexture() {
        if(getVariant() == 1){
            if(this.isBaby()) {
                return TEXTURE_COLD_BABY;
            }
            return TEXTURE_COLD;
        }
        else if(getVariant() == 2) {
            if(this.isBaby()) {
                return TEXTURE_HOT_BABY;
            }
            return TEXTURE_HOT;
        }
        else if(getVariant() == 3) {
            if(this.isBaby()) {
                return TEXTURE_NETHER_BABY;
            }
            return TEXTURE_NETHER;
        }
        else if(this.isBaby()){
            return TEXTURE_TEMPERATE_BABY;
        }
        else return TEXTURE_TEMPERATE;
    }

    // Melee attack
    static class MegaMeleeAttackGoal extends Goal {

        protected final EntityMegalania mob;
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

        Vec3 biteOffSet = new Vec3(2, 0, 0);

        public MegaMeleeAttackGoal(EntityMegalania p_i1636_1_, double p_i1636_2_, boolean p_i1636_4_) {
            this.mob = p_i1636_1_;
            this.speedModifier = p_i1636_2_;
            this.followingTargetEvenIfNotSeen = p_i1636_4_;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
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
                this.mob.setTarget(null);
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
                this.mob.setAnimationState(21);
            }
        }

        protected boolean getRangeCheck () {
            return this.mob.distanceToSqr(this.mob.getTarget().getX(), this.mob.getTarget().getY(), this.mob.getTarget().getZ()) <= 1.05F * this.getAttackReachSqr(this.mob.getTarget());
        }

        protected void tickBiteAttack () {
            animTime++;

            if (animTime <= 3) {
                this.mob.lookAt(Objects.requireNonNull(this.mob.getTarget()), 100000, 100000);
                this.mob.yBodyRot = this.mob.yHeadRot;
            }

            if(animTime==8) {
                preformBiteAttack();
            }

            if(animTime>=11) {
                animTime=0;
                this.mob.setAnimationState(0);
                this.resetAttackCooldown();
                this.ticksUntilNextPathRecalculation = 0;
            }
        }

        protected void preformBiteAttack () {
            this.mob.playSound(UPSounds.MEGALANIA_BITE.get(), 0.75F, 1.0F);
            HitboxHelper.PivotedPolyHitCheck(this.mob, this.biteOffSet, 3f, 2f, 3.5f, (ServerLevel)this.mob.level(), 5f, this.mob.damageSources().mobAttack(mob), 0.5f, false);
            List<LivingEntity> list = this.mob.level().getEntitiesOfClass(LivingEntity.class, this.mob.getBoundingBox().inflate(1));
            for (LivingEntity e : list) {
                if (!(e instanceof EntityMegalania) && e.isAlive()) {
                    e.addEffect(new MobEffectInstance(UPEffects.HEALTH_REDUCTION.get(), 400, 1, false, true, true));
                }
            }
        }

        protected void resetAttackCooldown () {
            this.ticksUntilNextAttack = 3;
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
            return this.mob.getBbWidth() * 2.5F * this.mob.getBbWidth() * 1.8F + p_179512_1_.getBbWidth();
        }
    }

    protected <E extends EntityMegalania> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        int animState = this.getAnimationState();
        if(this.isFromBook()){
            return PlayState.CONTINUE;
        }
        {
            switch (animState) {

                case 21:
                    event.setAndContinue(MEGALANIA_BITE);
                    event.getController().setAnimationSpeed(0.75F);
                    break;
                default:
                    if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isAsleep()  && !this.isSwimming()) {
                        if (this.isSprinting() || !this.getPassengers().isEmpty() && !this.isSwimming() && !this.isBaby()) {
                            event.setAndContinue(MEGALANIA_SPRINT);
                            return PlayState.CONTINUE;
                        } else if (event.isMoving() && !this.isAsleep() && !this.isSwimming()) {
                            if (this.isBaby()) {
                                event.setAndContinue(MEGALANIA_BABY_WALK);
                            }
                            else{
                                event.setAndContinue(MEGALANIA_WALK);
                            }
                            return PlayState.CONTINUE;
                        }
                    }
                    if (this.isInWater()) {
                        if (this.isBaby()) {
                            event.setAndContinue(MEGALANIA_BABY_SWIM);
                        }
                        else{
                            event.setAndContinue(MEGALANIA_SWIM);
                        }
                        event.getController().setAnimationSpeed(1.0F);
                        return PlayState.CONTINUE;
                    }

                    if (isAsleep() && !this.isSwimming()) {
                        event.setAndContinue(MEGALANIA_REST);
                        return PlayState.CONTINUE;
                    }

                    if (this.isBaby()) {
                        event.setAndContinue(MEGALANIA_BABY_IDLE);
                    }
                    else {
                        event.setAndContinue(MEGALANIA_IDLE);
                    }
                return PlayState.CONTINUE;
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
