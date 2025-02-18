package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.config.UnusualPrehistoryConfig;
import com.peeko32213.unusualprehistory.common.entity.msc.part.EntityBrachiosaurusPart;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.goal.*;
import com.peeko32213.unusualprehistory.common.entity.msc.util.helper.HitboxHelper;
import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.SemiAquatic;
import com.peeko32213.unusualprehistory.common.entity.msc.util.navigator.SemiAquaticPathNavigation;
import com.peeko32213.unusualprehistory.common.entity.msc.util.navigator.WaterMoveController;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class EntityBrachiosaurus extends EntityBaseDinosaurAnimal implements SemiAquatic {
    private static final EntityDataAccessor<Boolean> LAUNCHING = SynchedEntityData.defineId(EntityBrachiosaurus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> HEAD_HEIGHT = SynchedEntityData.defineId(EntityBrachiosaurus.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> COMBAT_STATE = SynchedEntityData.defineId(EntityBrachiosaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ENTITY_STATE = SynchedEntityData.defineId(EntityBrachiosaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(EntityBrachiosaurus.class, EntityDataSerializers.INT);
//    private static final EntityDataAccessor<Boolean> TEEN = SynchedEntityData.defineId(EntityBrachiosaurus.class, EntityDataSerializers.BOOLEAN);

    public float prevHeadHeight = 0F;
    private int headPeakCooldown = 0;
    public final EntityBrachiosaurusPart neck;
    public final EntityBrachiosaurusPart[] theEntireNeck;
    public final EntityBrachiosaurusPart[] allParts;
    private int ridingTime = 0;
    private int entityToLaunchId = -1;

    private int shakeCooldown = 0;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public float prevSwimProgress;
    public float swimProgress;
    private int swimTimer = -1000;
    private boolean isLandNavigator;

    private static final RawAnimation BRACHI_LAUNCH = RawAnimation.begin().thenLoop("animation.brachiosaurus.launch");
    private static final RawAnimation BRACHI_WALK = RawAnimation.begin().thenLoop("animation.brachiosaurus.walk");
    private static final RawAnimation BRACHI_SWIM = RawAnimation.begin().thenLoop("animation.brachiosaurus.swim");
    private static final RawAnimation BRACHI_ATTACK = RawAnimation.begin().thenPlay("animation.brachiosaurus.attack");
    private static final RawAnimation BRACHI_IDLE = RawAnimation.begin().thenPlay("animation.brachiosaurus.idle");

    private static final RawAnimation BRACHI_BABY_WALK = RawAnimation.begin().thenLoop("animation.babybrachy.walk");
    private static final RawAnimation BRACHI_BABY_IDLE = RawAnimation.begin().thenPlay("animation.babybrachy.idle");
    private static final RawAnimation BRACHI_BABY_SWIM = RawAnimation.begin().thenPlay("animation.babybrachy.swim");

    public EntityBrachiosaurus(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.neck = new EntityBrachiosaurusPart(this, 2,9);
        this.theEntireNeck = new EntityBrachiosaurusPart[]{this.neck};
        this.allParts = new EntityBrachiosaurusPart[]{this.neck};
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 400.0D)
            .add(Attributes.ARMOR, 15.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.2D)
            .add(Attributes.ATTACK_DAMAGE, 20.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 10.0D)
            .add(Attributes.ATTACK_KNOCKBACK, 2.0D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new EntityBrachiosaurus.BrachiMeleeAttackGoal(this,  1.3F, true));
        this.goalSelector.addGoal(3, new CustomRideGoal(this, 1.2D, false) {
            @Override
            public boolean shouldMoveForward() {
                return true;
            }

            @Override
            public boolean shouldMoveBackwards() {
                return false;
            }
        });
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.0D, Ingredient.of(Items.MELON), false));
        this.goalSelector.addGoal(6, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34));

        this.goalSelector.addGoal(7, new LeaveWaterGoal(this));
        this.goalSelector.addGoal(7, new FindWaterGoal(this));
        this.goalSelector.addGoal(9, new SemiAquaticSwimmingGoal(this, 1.0D, 10));

        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(7, (new HurtByTargetGoal(this)));
    }

    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new WaterMoveController(this, 0.25F);
            this.navigation = new SemiAquaticPathNavigation(this, level());
            this.isLandNavigator = false;
        }
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.4F;
    }

    @Override
    public double getFluidJumpThreshold() {
        if (this.isBaby()) {
            return 1;
        } else {
            return 14;
        }
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public float getStepHeight() {
        if (this.isBaby()) {
            return 1.25F;
        }
        else {
            return 2.5F;
        }
    }

    @Override
    public boolean isPushable() {
        return this.isBaby();
    }

    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        }
        else {
            super.travel(travelVector);
        }
    }

    @Nullable
    public LivingEntity getControllingPassenger() {
        if (this.isSaddled()) {
            for (Entity passenger : this.getPassengers()) {
                if (passenger instanceof Player) {
                    return (Player) passenger;
                }
            }
        }
        return null;
    }

    @Override
    protected void positionRider(Entity pPassenger, MoveFunction pCallback) {
        float ySin = Mth.sin(this.yBodyRot * ((float)Math.PI / 180F));
        float yCos = Mth.cos(this.yBodyRot * ((float)Math.PI / 180F));
        pPassenger.setPos(this.getX() + (double)(-1.5F * ySin), this.getY() + this.getPassengersRidingOffset() + pPassenger.getMyRidingOffset() - 0.1F, this.getZ() - (double)(-1.5F * yCos));
    }

    public double getPassengersRidingOffset() {
        return 15.3D;
    }

    public boolean canBeCollidedWith() {
        if (this.isBaby()) {
            return false;
        }
        else {
            return UnusualPrehistoryConfig.BRACHI_COLLISON.get();
        }
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if(hand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if (item == Items.SADDLE && !this.isSaddled() && !this.isBaby()) {
            if (!player.isCreative()) {
                itemstack.shrink(1);
            }
            this.playSound(SoundEvents.HORSE_SADDLE, 1.0F, 1.0F);
            this.setSaddled(true);
            return InteractionResult.SUCCESS;
        } else if (itemstack.getItem() == Items.SHEARS && this.isSaddled()) {
            this.playSound(SoundEvents.SHEEP_SHEAR, 1.0F, 1.0F);
            this.setSaddled(false);
            this.spawnAtLocation(Items.SADDLE);
            return InteractionResult.SUCCESS;
        }
        if (!isFood(itemstack) && isSaddled()) {
            if (!player.isShiftKeyDown()) {
                if(notClientSide()) {
                    player.startRiding(this);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public boolean notClientSide(){
        return !this.level().isClientSide;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HEAD_HEIGHT, 0F);
        this.entityData.define(LAUNCHING, Boolean.FALSE);
        this.entityData.define(ANIMATION_STATE, 0);
        this.entityData.define(COMBAT_STATE, 0);
        this.entityData.define(ENTITY_STATE, 0);
//        this.entityData.define(TEEN, false);
    }

//    public void getTeen(boolean teen) {
//        this.entityData.set(TEEN, teen);
//    }
//
//    public boolean isTeen() {
//        return this.entityData.get(TEEN);
//    }
//
//    // gets teen, doesn't work
//    public boolean shouldBeTeen() {
//        return this.getAge() < 0 && this.getAge() > -12000 && this.isBaby();
//    }

    public void addAdditionalSaveData(CompoundTag p_31808_) {
        super.addAdditionalSaveData(p_31808_);
//        p_31808_.putBoolean("Teen", this.isTeen());
    }

    public void readAdditionalSaveData(CompoundTag p_31795_) {
        super.readAdditionalSaveData(p_31795_);
//        this.getTeen(p_31795_.getBoolean("Teen"));
    }

    public boolean isLaunching() {
        return this.entityData.get(LAUNCHING);
    }

    public void setLaunching(boolean charging) {
        this.entityData.set(LAUNCHING, charging);
    }

    protected void dropEquipment() {
        super.dropEquipment();
        if (this.isSaddled()) {
            if (!this.level().isClientSide) {
                this.spawnAtLocation(Items.SADDLE);
            }
        }
        this.setSaddled(false);
    }

    public int getMaxHeadYRot() {
        return 45;
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.BRACHI_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.BRACHI_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.BRACHI_DEATH.get();
    }

    @Override
    public float getSoundVolume() {
        if (this.isBaby()) {
            return 0.5F;
        }
        else {
            return 1.25F;
        }
    }

    @Override
    protected SoundEvent getAttackSound() {
        //Not sure if you want this you can leave it null otherwise
        return UPSounds.BRACHI_TOSS.get();
    }

    @Override
    protected int getKillHealAmount() {
        return 5;
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
    protected TagKey<EntityType<?>> getTargetTag() {
        //Since it has no targets you can leave this null
        return null;
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
        return true;
    }

    @Override
    protected boolean customMakeStuckInBlockCheck(BlockState blockState) {
        return !(blockState.getBlock() == Blocks.BAMBOO) || blockState.is(BlockTags.LEAVES);
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

//        if (this.shouldBeTeen()) {
//            this.isTeen();
//        }

        prevHeadHeight = this.getHeadHeight();
        float neckBase = 5.5F;
        if (!this.isNoAi() && !this.isBaby()) {
            Vec3[] avector3d = new Vec3[this.allParts.length];
            for (int j = 0; j < this.allParts.length; ++j) {
                this.allParts[j].collideWithNearbyEntities();
                avector3d[j] = new Vec3(this.allParts[j].getX(), this.allParts[j].getY(), this.allParts[j].getZ());
            }
            float yaw = this.getYRot() * ((float) Math.PI / 180F);
            float neckContraction = 0.3F * Math.abs(getHeadHeight() / 3) + 0.5F * Math.abs(getHeadYaw(0) / 50F);

            for (int l = 0; l < this.theEntireNeck.length; ++l) {
                float f = l / ((float) this.theEntireNeck.length);
                float f1 = -(2.2F + l - f * neckContraction);
                float f2 = Mth.sin(yaw + (float) Math.toRadians(f * getHeadYaw(0))) * (1 - Math.abs((this.getXRot()) / 90F));
                float f3 = Mth.cos(yaw + (float) Math.toRadians(f * getHeadYaw(0))) * (1 - Math.abs((this.getXRot()) / 90F));
                this.setPartPosition(this.theEntireNeck[l], f2 * f1, neckBase + Math.sin(f * Math.PI * 0.5F) * (getHeadHeight() * -11.1F), -f3 * f1);
            }

            for (int l = 0; l < this.allParts.length; ++l) {
                this.allParts[l].xo = avector3d[l].x;
                this.allParts[l].yo = avector3d[l].y;
                this.allParts[l].zo = avector3d[l].z;
                this.allParts[l].xOld = avector3d[l].x;
                this.allParts[l].yOld = avector3d[l].y;
                this.allParts[l].zOld = avector3d[l].z;
            }
        }
        if (entityToLaunchId != -1 && this.isAlive()) {
            Entity launch = this.level().getEntity(entityToLaunchId);
            this.ejectPassengers();
            entityToLaunchId = -1;
            if (launch != null && !launch.isPassenger()) {
                if (launch instanceof LivingEntity) {
                    this.playSound(UPSounds.BRACHI_TOSS.get(), 0.5F, 0.5F);
                    launch.setPos(this.getEyePosition().add(0, 1, 0));
                    float rot = 180F + this.getYRot();
                    float strength = (float) (getLaunchStrength() * (1.0D - ((LivingEntity) launch).getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)));
                    float x = Mth.sin(rot * ((float) Math.PI / 180F));
                    float z = -Mth.cos(rot * ((float) Math.PI / 180F));
                    if (!(strength <= 0.0D)) {
                        launch.hasImpulse = true;
                        Vec3 vec3 = this.getDeltaMovement();
                        Vec3 vec31 = vec3.add((new Vec3(x, 0.0D, z)).normalize().scale(strength));
                        launch.setDeltaMovement(vec31.x, strength, vec31.z);
                    }
                }
            }
        }
        if (this.isLaunching()) {
            Entity passenger = this.getControllingPassenger();
            if (passenger instanceof LivingEntity) {
                entityToLaunchId = passenger.getId();
                this.setLaunching(false);
                for (Entity rider : this.getPassengers()) {
                    if (rider instanceof Player player) {
                        player.addEffect(new MobEffectInstance(UPEffects.BRACHI_PROTECTION.get(), 200));
                    }
                }
            }
        }
        if (!level().isClientSide) {
            if (this.isVehicle()) {
                ridingTime++;
                if (ridingTime >= this.getMaxRidingTime()) {
                    this.setLaunching(true);
                    this.isLaunching();
                }
            } else {
                ridingTime = 0;
                this.setLaunching(false);
            }
            if (this.isAlive() && ridingTime > 0 && this.getDeltaMovement().horizontalDistanceSqr() > 0.1D && this.isLaunching()) {
                for (Entity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(1.0D))) {
                    if (!(entity instanceof EntityBrachiosaurus) && !entity.isPassengerOfSameVehicle(this)) {
                        entity.hurt(this.damageSources().mobAttack(this), 4F + random.nextFloat() * 3.0F);
                        if (entity.onGround()) {
                            double d0 = entity.getX() - this.getX();
                            double d1 = entity.getZ() - this.getZ();
                            double d2 = Math.max(d0 * d0 + d1 * d1, 0.001D);
                            float f = 0.5F;
                            entity.push(d0 / d2 * f, f, d1 / d2 * f);
                        }
                    }
                }
            }
        }
        if ( headPeakCooldown == 0) {
            float low = getLowHeadHeight();
            this.setHeadHeight(this.getHeadHeight() + (0.5F + ((getLowHeadHeight() + getHighHeadHeight(low)) / 2F) - this.getHeadHeight()) * 0.2F);
        } else {
            if (getMaxFluidHeight() <= this.getBbHeight() * 0.5F && getMaxFluidHeight() >= this.getBbHeight() * 0.25F) {
                float mot = (float) this.getDeltaMovement().lengthSqr();
                this.setHeadHeight(Mth.clamp(this.getHeadHeight() + 0.1F - 0.2F * mot, 0, 2));
                headPeakCooldown = 5;
            }
        }
        if(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !this.isSwimming() && !this.isInWater() && !this.isBaby()) {
            if (this.shakeCooldown <= 0 && UnusualPrehistoryConfig.SCREEN_SHAKE_BRACHI.get()) {
                double brachiShakeRange = UnusualPrehistoryConfig.SCREEN_SHAKE_BRACHI_RANGE.get();
                int brachiShakeAmp = UnusualPrehistoryConfig.SCREEN_SHAKE_BRACHI_AMPLIFIER.get();
                float brachiMoveSoundVolume = UnusualPrehistoryConfig.BRACHI_SOUND_VOLUME.get();
                List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(brachiShakeRange));
                for (LivingEntity e : list) {
                    if ((e instanceof Player)) {
                        e.addEffect(new MobEffectInstance(UPEffects.SCREEN_SHAKE.get(), 8, brachiShakeAmp, false, false, false));
                        this.playSound(UPSounds.BRACHI_STEP.get(), brachiMoveSoundVolume, 0.75F);
                    }
                }
                shakeCooldown = 15;
            }
        }
        shakeCooldown--;
    }

    private float getLaunchStrength() {
        return 5.0F;
    }

    private int getMaxRidingTime() {
        return 40;
    }

    private double getMaxFluidHeight() {
        return 11.5f;
    }

    public float getLowHeadHeight() {
        float checkAt = 0F;
        while (checkAt > -3F && !isHeadInWall((float) this.getY() + checkAt)) {
            checkAt -= 0.2F;
        }
        return checkAt;
    }

    public float getHighHeadHeight(float low) {
        float checkAt = 3F;
        while (checkAt > 0) {
            if(isHeadInWall((float) this.getY() + checkAt)) {
                break;
            }
            checkAt -= 0.2F;
        }

        return checkAt;
    }

    public boolean isHeadInWall(float offset) {
        if (this.noPhysics) {
            return false;
        } else {
            float f = 0.8F;
            Vec3 vec3 = new Vec3(neck.getX(), offset, neck.getZ());
            AABB axisalignedbb = AABB.ofSize(vec3, (double)f, 1.0E-6D, (double)f);
            return this.level().getBlockStates(axisalignedbb).filter(Predicate.not(BlockBehaviour.BlockStateBase::isAir)).anyMatch((p_185969_) -> {
                BlockPos blockpos = BlockPos.containing(vec3.x, vec3.y, vec3.z);
                return p_185969_.isSuffocating(this.level(), blockpos) && Shapes.joinIsNotEmpty(p_185969_.getCollisionShape(this.level(), blockpos).move(vec3.x, vec3.y, vec3.z), Shapes.create(axisalignedbb), BooleanOp.AND);
            });
        }
    }

    public float getHeadHeight() {
        return Mth.clamp(this.entityData.get(HEAD_HEIGHT), -3, 3);
    }

    public void setHeadHeight(float height) {
        this.entityData.set(HEAD_HEIGHT, Mth.clamp(height, -3, 3));
    }

    public float getHeadYaw(float interp) {
        float f;
        if (interp == 0.0F) {
            f = getYHeadRot() - this.yBodyRot;
        } else {
            float yBodyRot1 = this.yBodyRotO + (this.yBodyRot - this.yBodyRotO) * interp;
            float yHeadRot1 = this.yHeadRotO + (getYHeadRot() - this.yHeadRotO) * interp;
            f = yHeadRot1 - yBodyRot1;
        }
        return Mth.clamp(Mth.wrapDegrees(f), -50, 50);
    }

    private void setPartPosition(EntityBrachiosaurusPart part, double offsetX, double offsetY, double offsetZ) {
        if(!this.isBaby()){
            part.setPos(this.getX() + offsetX * part.scale, this.getY() + offsetY * part.scale, this.getZ() + offsetZ * part.scale);
        }
    }

    @Override
    public boolean isMultipartEntity() {
        return !this.isBaby();
    }

    @Override
    public net.minecraftforge.entity.PartEntity<?>[] getParts() {
        return this.allParts;
    }

    public boolean attackEntityPartFrom(EntityBrachiosaurusPart part, DamageSource source, float amount) {
        return this.hurt(source, amount);
    }

    public void scaleParts() {
        for (EntityBrachiosaurusPart parts : allParts) {
            float prev = parts.scale;
            parts.scale = this.isBaby() ? 0.5F : 1F;
            if (prev != parts.scale) {
                parts.refreshDimensions();
            }
        }
    }

    @Override
    public void ageUp(int pAmount) {
        scaleParts();
        super.ageUp(pAmount);
    }

    public void aiStep() {
        super.aiStep();
        //scaleParts();

        if (this.horizontalCollision && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), this)) {
            boolean flag = false;
            AABB axisalignedbb = this.getBoundingBox().inflate(0.2D);
            for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(axisalignedbb.minX), Mth.floor(axisalignedbb.minY), Mth.floor(axisalignedbb.minZ), Mth.floor(axisalignedbb.maxX), Mth.floor(axisalignedbb.maxY), Mth.floor(axisalignedbb.maxZ))) {
                BlockState blockstate = this.level().getBlockState(blockpos);
                if (blockstate.is(UPTags.PASSIVE_BRACHI_BREAKABLES)) {
                    flag = this.level().destroyBlock(blockpos, true, this) || flag;
                }
            }
        }
        if (this.horizontalCollision && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), this) && this.isAggressive()) {
            boolean flag = false;
            AABB axisalignedbb = this.getBoundingBox().inflate(0.2D);
            for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(axisalignedbb.minX), Mth.floor(axisalignedbb.minY), Mth.floor(axisalignedbb.minZ), Mth.floor(axisalignedbb.maxX), Mth.floor(axisalignedbb.maxY), Mth.floor(axisalignedbb.maxZ))) {
                BlockState blockstate = this.level().getBlockState(blockpos);
                if (blockstate.is(UPTags.ANGRY_BRACHI_BREAKABLES)) {
                    flag = this.level().destroyBlock(blockpos, true, this) || flag;
                }
            }
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return UPEntities.BRACHI.get().create(serverLevel);
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

    @Override
    public boolean shouldStopMoving() {
        return false;
    }

    static class BrachiMeleeAttackGoal extends Goal {

        protected final EntityBrachiosaurus mob;
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

        public BrachiMeleeAttackGoal(EntityBrachiosaurus p_i1636_1_, double p_i1636_2_, boolean p_i1636_4_) {
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
                case 21:
                    tickStompAttack();
                    break;
                default:
                    this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                    this.ticksUntilNextAttack = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                    this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    this.doMovement(target, distance);
                    this.checkForCloseRangeAttack(distance, reach);
                    break;
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
                if (r <= 600) {
                    this.mob.setAnimationState(21);
                }
            }
        }

        protected boolean getRangeCheck () {
            return
            this.mob.distanceToSqr(this.mob.getTarget().getX(), this.mob.getTarget().getY(), this.mob.getTarget().getZ())
                    <=
                    1.8F * this.getAttackReachSqr(this.mob.getTarget());
        }

        protected void tickStompAttack () {
            animTime++;

            if (animTime <= 3) {
                this.mob.lookAt(Objects.requireNonNull(this.mob.getTarget()), 100000, 100000);
                this.mob.yBodyRot = this.mob.yHeadRot;
            }

            if(animTime==16) {
                preformStompAttack();
            }

            if(animTime>=19) {
                animTime=0;
                this.mob.setAnimationState(0);
                this.resetAttackCooldown();
                this.ticksUntilNextPathRecalculation = 0;
            }
        }

        protected void preformStompAttack () {
            Vec3 pos = mob.position();
            this.mob.playSound(UPSounds.BRACHI_STOMP.get(), 2.5F, 0.65F);
            HitboxHelper.LargeAttack(this.mob.damageSources().mobAttack(mob),36.0f, 1.4f, mob, pos,  8.5F, -Math.PI/2, Math.PI/2, -1.0f, 3.0f);
            if(this.mob.shakeCooldown <= 0 && UnusualPrehistoryConfig.SCREEN_SHAKE_BRACHI.get()) {
                double brachiShakeRange = UnusualPrehistoryConfig.SCREEN_SHAKE_BRACHI_RANGE.get();
                List<LivingEntity> list = this.mob.level().getEntitiesOfClass(LivingEntity.class, this.mob.getBoundingBox().inflate(brachiShakeRange));
                for (LivingEntity e : list) {
                    if (!(e instanceof EntityBrachiosaurus) && e.isAlive()) {
                        e.addEffect(new MobEffectInstance(UPEffects.SCREEN_SHAKE.get(), 15, 8, false, false, false));
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

    protected <E extends EntityBrachiosaurus> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        int animState = this.getAnimationState();

        if(!this.isFromBook()) {
            if (animState == 21) {
                event.getController().setAnimationSpeed(1.25F);
                return event.setAndContinue(BRACHI_ATTACK);
            }

            if (this.isLaunching()) {
                event.setAndContinue(BRACHI_LAUNCH);
                event.getController().setAnimationSpeed(1.0F);
                return PlayState.CONTINUE;
            }

            if (this.isInWater()) {
                if (this.isBaby()) {
                    event.setAndContinue(BRACHI_BABY_SWIM);
                    return PlayState.CONTINUE;
                }
                event.setAndContinue(BRACHI_SWIM);
                event.getController().setAnimationSpeed(1.5D);
            }

            if (event.isMoving() && !this.isInWater()) {
                if (this.isBaby()) {
                    event.setAndContinue(BRACHI_BABY_WALK);
                    return PlayState.CONTINUE;
                }
                event.setAndContinue(BRACHI_WALK);
                event.getController().setAnimationSpeed(1.5D);
            }

            else if (!this.isInWater()) {
                if (this.isBaby()) {
                    event.setAndContinue(BRACHI_BABY_IDLE);
                }
                event.setAndContinue(BRACHI_IDLE);
                event.getController().setAnimationSpeed(1.0F);
            }
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
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

}