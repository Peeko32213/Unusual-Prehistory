package com.peeko32213.unusualprehistory.common.entity;

import com.mojang.serialization.Codec;
import com.peeko32213.unusualprehistory.common.entity.msc.util.helper.KimmerVariant;
import com.peeko32213.unusualprehistory.common.entity.msc.util.navigator.FlyingMoveController;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.IntFunction;

//TODO LIST
// - Add in Kimmer in a Flask
// - Add in randomized color selection akin to tropical fish
// - Add in Flask + Eggs
// - Make Larvae State
public class EntityKimmeridgebrachypteraeschnidium extends AgeableMob implements GeoEntity, IBookEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    @Nullable
    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(EntityKimmeridgebrachypteraeschnidium.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FROM_BOOK = SynchedEntityData.defineId(EntityKimmeridgebrachypteraeschnidium.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(EntityKimmeridgebrachypteraeschnidium.class, EntityDataSerializers.INT);

    public final float[] ringBuffer = new float[64];
    public float prevFlyProgress;
    public float flyProgress;
    public int ringBufferIndex = -1;
    private boolean isLandNavigator;
    private int timeFlying;
    private static final RawAnimation KIMMER_HOVER = RawAnimation.begin().thenLoop("animation.kimmeridgebrachypteraeschnidium.hover");
    private static final RawAnimation KIMMER_FLY = RawAnimation.begin().thenLoop("animation.kimmeridgebrachypteraeschnidium.fly");
    private static final RawAnimation KIMMER_IDLE_1 = RawAnimation.begin().thenLoop("animation.kimmeridgebrachypteraeschnidium.idle1");
    private static final RawAnimation KIMMER_IDLE_2 = RawAnimation.begin().thenPlay("animation.kimmeridgebrachypteraeschnidium.idle2");
    private static final RawAnimation KIMMER_PREEN = RawAnimation.begin().thenPlay("animation.kimmeridgebrachypteraeschnidium.preen");

    public EntityKimmeridgebrachypteraeschnidium(EntityType<? extends AgeableMob> entityType, Level level) {
        super(entityType, level);
        switchNavigator(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2F);

    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AIFlyIdle());
    }

    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }


    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new FlyingMoveController(this, 0.6F, false, true);
            this.navigation = new FlyingPathNavigation(this, level()) {
                public boolean isStableDestination(BlockPos pos) {
                    return !this.level.getBlockState(pos.below(2)).isAir();
                }
            };
            navigation.setCanFloat(false);
            this.isLandNavigator = false;
        }
    }

    public void tick() {
        super.tick();
        this.prevFlyProgress = flyProgress;
        if (this.isFlying() && flyProgress < 5F) {
            flyProgress++;
        }
        if (!this.isFlying() && flyProgress > 0F) {
            flyProgress--;
        }
        if (this.ringBufferIndex < 0) {
            //initial population of buffer
            for (int i = 0; i < this.ringBuffer.length; ++i) {
                this.ringBuffer[i] = 15;
            }
        }
        this.ringBufferIndex++;
        if (this.ringBufferIndex == this.ringBuffer.length) {
            this.ringBufferIndex = 0;
        }
        if (!level().isClientSide) {
            if (isFlying() && this.isLandNavigator) {
                switchNavigator(false);
            }
            if (!isFlying() && !this.isLandNavigator) {
                switchNavigator(true);
            }
            if (this.isFlying()) {
                if (this.isFlying() && !this.onGround()) {
                    if (!this.isInWaterOrBubble()) {
                        this.setDeltaMovement(this.getDeltaMovement().multiply(1F, 0.6F, 1F));
                    }
                }
                if (this.onGround() && timeFlying > 20) {
                    this.setFlying(false);
                }
                this.timeFlying++;
            } else {
                this.timeFlying = 0;
            }
        }
    }

    public boolean hurt(DamageSource source, float amount) {
        boolean prev = super.hurt(source, amount);
        return prev;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.IN_WALL)  || source.is(DamageTypes.FALL) || source.is(DamageTypes.CACTUS) || super.isInvulnerableTo(source);
    }
    public boolean isFlying() {
        return this.entityData.get(FLYING);
    }

    public void setFlying(boolean flying) {
        if (flying && isBaby()) {
            return;
        }
        this.entityData.set(FLYING, flying);
    }

    public boolean canBlockBeSeen(BlockPos pos) {
        double x = pos.getX() + 0.5F;
        double y = pos.getY() + 0.5F;
        double z = pos.getZ() + 0.5F;
        HitResult result = this.level().clip(new ClipContext(new Vec3(this.getX(), this.getY() + (double) this.getEyeHeight(), this.getZ()), new Vec3(x, y, z), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        double dist = result.getLocation().distanceToSqr(x, y, z);
        return dist <= 1.0D || result.getType() == HitResult.Type.MISS;
    }

    public void killed(ServerLevel world, LivingEntity entity) {
        this.heal(10);
    }


    public Vec3 getBlockGrounding(Vec3 fleePos) {
        final float radius = 3.15F * -3 - this.getRandom().nextInt(24);
        float neg = this.getRandom().nextBoolean() ? 1 : -1;
        float renderYawOffset = this.yBodyRot;
        float angle = (0.01745329251F * renderYawOffset) + 3.15F + (this.getRandom().nextFloat() * neg);
        final double extraX = radius * Mth.sin(Mth.PI + angle);
        final double extraZ = radius * Mth.cos(angle);
        final BlockPos radialPos = new BlockPos((int) (fleePos.x() + extraX), (int) getY(), (int) (fleePos.z() + extraZ));
        BlockPos ground = this.getAnuroGround(radialPos);
        if (ground.getY() == -64) {
            return this.position();
        } else {
            ground = this.blockPosition();
            while (ground.getY() > -64 && !level().getBlockState(ground).isSolid()) {
                ground = ground.below();
            }
        }
        if (!this.isTargetBlocked(Vec3.atCenterOf(ground.above()))) {
            return Vec3.atCenterOf(ground);
        }
        return null;
    }

    public boolean isTargetBlocked(Vec3 target) {
        Vec3 Vector3d = new Vec3(this.getX(), this.getEyeY(), this.getZ());

        return this.level().clip(new ClipContext(Vector3d, target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() != HitResult.Type.MISS;
    }

    public Vec3 getBlockInViewAway(Vec3 fleePos, float radiusAdd) {
        float radius = 5 + radiusAdd + this.getRandom().nextInt(5);
        float neg = this.getRandom().nextBoolean() ? 1 : -1;
        float renderYawOffset = this.yBodyRot;
        float angle = (0.01745329251F * renderYawOffset) + 3.15F + (this.getRandom().nextFloat() * neg);
        double extraX = radius * Mth.sin((float) (Math.PI + angle));
        double extraZ = radius * Mth.cos(angle);
        final BlockPos radialPos = new BlockPos((int) (fleePos.x() + extraX), (int) getY(), (int) (fleePos.z() + extraZ));
        BlockPos ground = getAnuroGround(radialPos);
        int distFromGround = (int) this.getY() - ground.getY();
        int flightHeight = 5 + this.getRandom().nextInt(5);
        int j = this.getRandom().nextInt(5) + 5;

        BlockPos newPos = ground.above(distFromGround > 5 ? flightHeight : j);
        if (!this.isTargetBlocked(Vec3.atCenterOf(newPos)) && this.distanceToSqr(Vec3.atCenterOf(newPos)) > 1) {
            return Vec3.atCenterOf(newPos);
        }
        return null;
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    private boolean isOverWaterOrVoid() {
        BlockPos position = this.blockPosition();
        while (position.getY() > -65 && level().isEmptyBlock(position)) {
            position = position.below();
        }
        return !level().getFluidState(position).isEmpty() || level().getBlockState(position).is(Blocks.VINE) || position.getY() <= -65;
    }

    public BlockPos getAnuroGround(BlockPos in) {
        BlockPos position = new BlockPos(in.getX(), (int) this.getY(), in.getZ());
        while (position.getY() > -64 && !level().getBlockState(position).isSolid() && level().getFluidState(position).isEmpty()) {
            position = position.below();
        }
        return position;
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
        if (shouldHurt = target.hurt(this.damageSources().mobAttack(this), damage)) {
            if (knockback > 0.0f && target instanceof LivingEntity) {
                ((LivingEntity)target).knockback(knockback * 0.5f, Mth.sin(this.getYRot() * ((float)Math.PI / 180)), -Mth.cos(this.getYRot() * ((float)Math.PI / 180)));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
            }
            this.doEnchantDamageEffects(this, target);
            this.setLastHurtMob(target);
        }
        return shouldHurt;
    }

    protected <E extends EntityKimmeridgebrachypteraeschnidium> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if(this.isFromBook()){
            return PlayState.CONTINUE;
        }
        if (event.isMoving() && this.onGround() && this.onGround()) {
            return event.setAndContinue(KIMMER_HOVER);

        }
        if (!event.isMoving() && this.onGround() && this.onGround()) {
            return event.setAndContinue(KIMMER_IDLE_1);
        }
        return event.setAndContinue(KIMMER_FLY);
    }


    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }

    @Override
    public void setFromBook(boolean fromBook) {
        this.entityData.set(FROM_BOOK, fromBook);
    }

    private class AIFlyIdle extends Goal {
        protected double x;
        protected double y;
        protected double z;

        public AIFlyIdle() {
            super();
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (EntityKimmeridgebrachypteraeschnidium.this.isVehicle() || (EntityKimmeridgebrachypteraeschnidium.this.getTarget() != null && EntityKimmeridgebrachypteraeschnidium.this.getTarget().isAlive()) || EntityKimmeridgebrachypteraeschnidium.this.isPassenger()) {
                return false;
            } else {
                if (EntityKimmeridgebrachypteraeschnidium.this.getRandom().nextInt(45) != 0 && !EntityKimmeridgebrachypteraeschnidium.this.isFlying()) {
                    return false;
                }

                Vec3 lvt_1_1_ = this.getPosition();
                if (lvt_1_1_ == null) {
                    return false;
                } else {
                    this.x = lvt_1_1_.x;
                    this.y = lvt_1_1_.y;
                    this.z = lvt_1_1_.z;
                    return true;
                }
            }
        }

        public void tick() {
            EntityKimmeridgebrachypteraeschnidium.this.getMoveControl().setWantedPosition(this.x, this.y, this.z, 1F);
            if (isFlying() && EntityKimmeridgebrachypteraeschnidium.this.onGround() && EntityKimmeridgebrachypteraeschnidium.this.timeFlying > 10) {
                EntityKimmeridgebrachypteraeschnidium.this.setFlying(false);
            }
        }

        @Nullable
        protected Vec3 getPosition() {
            Vec3 vector3d = EntityKimmeridgebrachypteraeschnidium.this.position();
            if (EntityKimmeridgebrachypteraeschnidium.this.timeFlying < 200 || EntityKimmeridgebrachypteraeschnidium.this.isOverWaterOrVoid()) {
                return EntityKimmeridgebrachypteraeschnidium.this.getBlockInViewAway(vector3d, 0);
            } else {
                return EntityKimmeridgebrachypteraeschnidium.this.getBlockGrounding(vector3d);
            }
        }

        public boolean canContinueToUse() {
            return EntityKimmeridgebrachypteraeschnidium.this.isFlying() && EntityKimmeridgebrachypteraeschnidium.this.distanceToSqr(x, y, z) > 5F;
        }

        public void start() {
            EntityKimmeridgebrachypteraeschnidium.this.setFlying(true);
            EntityKimmeridgebrachypteraeschnidium.this.getMoveControl().setWantedPosition(this.x, this.y, this.z, 1F);
        }

        public void stop() {
            EntityKimmeridgebrachypteraeschnidium.this.getNavigation().stop();
            x = 0;
            y = 0;
            z = 0;
            super.stop();
        }

    }

    public boolean isFromBook() {
        return this.entityData.get(FROM_BOOK).booleanValue();
    }

    public void setIsFromBook(boolean fromBook) {
        this.entityData.set(FROM_BOOK, fromBook);
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.hasCustomName();
    }

    public boolean removeWhenFarAway(double d) {
        return !this.hasCustomName();
    }


    @org.jetbrains.annotations.Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        SpawnGroupData spawnGroupData2 = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
        if (mobSpawnType == MobSpawnType.BUCKET && compoundTag != null && compoundTag.contains("BucketVariantTag", 3)) {
            this.setPackedVariant(compoundTag.getInt("BucketVariantTag"));
        } else {
            RandomSource randomSource = serverLevelAccessor.getRandom();
            Variant variant;
            Pattern[] patterns = Pattern.values();
            DyeColor[] dyeColors = DyeColor.values();
            Pattern pattern = Util.getRandom(patterns, randomSource);
            DyeColor dyeColor = Util.getRandom(dyeColors, randomSource);
            DyeColor dyeColor2 = Util.getRandom(dyeColors, randomSource);
            variant = new Variant(pattern, dyeColor, dyeColor2);

            this.setPackedVariant(variant.getPackedId());
        }
        return spawnGroupData2;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FLYING, false);
        this.entityData.define(FROM_BOOK, false);
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Flying", this.isFlying());
        compound.putInt("Variant", this.getPackedVariant());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setFlying(compound.getBoolean("Flying"));
        this.setPackedVariant(compound.getInt("Variant"));
    }

    public void setPackedVariant(int i) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, i);
    }

    public int getPackedVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    public static String getPredefinedName(int i) {
        return "entity.unusualprehistory.kimmer.variant.predefined." + i;
    }

    static int packVariant(Pattern pattern, DyeColor dyeColor, DyeColor dyeColor2) {
        return pattern.getPackedId() & '\uffff' | (dyeColor.getId() & 255) << 16 | (dyeColor2.getId() & 255) << 24;
    }

    public static DyeColor getBaseColor(int i) {
        return DyeColor.byId(i >> 16 & 255);
    }

    public static DyeColor getPatternColor(int i) {
        return DyeColor.byId(i >> 24 & 255);
    }

    //TODO keep or not?
    public static DyeColor getWingColor(int i) {
        return DyeColor.byId((i * 2) >> 24 & 255);
    }

    public static Pattern getPattern(int i) {
        return Pattern.byId(i & '\uffff');
    }

    public DyeColor getBaseColor() {
        return getBaseColor(this.getPackedVariant());
    }

    public DyeColor getPatternColor() {
        return getPatternColor(this.getPackedVariant());
    }

    public DyeColor getWingColor() {
        return getWingColor(this.getPackedVariant());
    }

    public Pattern getVariant() {
        return getPattern(this.getPackedVariant());
    }

    public void setVariant(Pattern pattern) {
        int i = this.getPackedVariant();
        DyeColor dyeColor = getBaseColor(i);
        DyeColor dyeColor2 = getPatternColor(i);
        this.setPackedVariant(packVariant(pattern, dyeColor, dyeColor2));
    }

    public enum Pattern implements StringRepresentable {
        COLORED_BODY("body", EntityKimmeridgebrachypteraeschnidium.Base.SMALL, 0);

        public static final Codec<Pattern> CODEC = StringRepresentable.fromEnum(Pattern::values);
        private static final IntFunction<Pattern> BY_ID = ByIdMap.sparse(Pattern::getPackedId, values(), COLORED_BODY);
        private final String name;
        private final Component displayName;
        private final EntityKimmeridgebrachypteraeschnidium.Base base;
        private final int packedId;

        Pattern(String string2, EntityKimmeridgebrachypteraeschnidium.Base base, int j) {
            this.name = string2;
            this.base = base;
            this.packedId = base.id | j << 8;
            this.displayName = Component.translatable("entity.unusualprehistory.kimmer.variant." + this.name);
        }

        public static Pattern byId(int i) {
            return BY_ID.apply(i);
        }

        public EntityKimmeridgebrachypteraeschnidium.Base base() {
            return this.base;
        }

        public int getPackedId() {
            return this.packedId;
        }

        public String getSerializedName() {
            return this.name;
        }

        public Component displayName() {
            return this.displayName;
        }
    }

    public record Variant(Pattern pattern, DyeColor dyeColor, DyeColor dyeColor2) {
        public Variant(Pattern pattern, DyeColor dyeColor, DyeColor dyeColor2) {
            this.pattern = pattern;
            this.dyeColor = dyeColor;
            this.dyeColor2 = dyeColor2;
        }

        public int getPackedId() {
            return packVariant(this.pattern, this.dyeColor, this.dyeColor2);
        }

        public Pattern pattern() {
            return this.pattern;
        }

        public DyeColor dyeColor() {
            return this.dyeColor;
        }

        public DyeColor dyeColor2() {
            return this.dyeColor2;
        }
    }

    public enum Base {
        SMALL(0),
        LARGE(1);

        final int id;

        Base(int j) {
            this.id = j;
        }
    }

}
