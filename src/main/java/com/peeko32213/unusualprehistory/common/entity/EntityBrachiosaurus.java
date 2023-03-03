package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.config.UnusualPrehistoryConfig;
import com.peeko32213.unusualprehistory.common.entity.part.EntityBrachiosaurusPart;
import com.peeko32213.unusualprehistory.common.entity.trail.EntityTrail;
import com.peeko32213.unusualprehistory.common.entity.util.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.util.CustomRandomStrollGoal;
import com.peeko32213.unusualprehistory.common.entity.util.CustomRideGoal;
import com.peeko32213.unusualprehistory.common.entity.util.HitboxHelper;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
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
import net.minecraft.world.entity.ai.navigation.PathNavigation;
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
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class EntityBrachiosaurus extends Animal implements IAnimatable {
    private static final EntityDataAccessor<Boolean> LAUNCHING = SynchedEntityData.defineId(EntityBrachiosaurus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> HEAD_HEIGHT = SynchedEntityData.defineId(EntityBrachiosaurus.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(EntityBrachiosaurus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> COMBAT_STATE = SynchedEntityData.defineId(EntityBrachiosaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ENTITY_STATE = SynchedEntityData.defineId(EntityBrachiosaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(EntityBrachiosaurus.class, EntityDataSerializers.INT);

    public static final Logger LOGGER = LogManager.getLogger();
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public float prevHeadHeight = 0F;
    private int headPeakCooldown = 0;
    public final EntityBrachiosaurusPart neck;
    public final EntityBrachiosaurusPart[] theEntireNeck;
    public final EntityBrachiosaurusPart[] allParts;
    private int ridingTime = 0;
    private int entityToLaunchId = -1;

    private int shakeCooldown = 0;
    private int footPrintCooldown = 0;
    public EntityBrachiosaurus(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.maxUpStep = 2.0f;
        this.neck = new EntityBrachiosaurusPart(this, 3,9.3F);
        this.theEntireNeck = new EntityBrachiosaurusPart[]{this.neck};
        this.allParts = new EntityBrachiosaurusPart[]{this.neck};
    }

    public PathNavigation createNavigation(Level world) {
        return new GroundPathNavigation(this, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 400.0D)
                .add(Attributes.ARMOR, 15.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 10.5D)
                .add(Attributes.ATTACK_KNOCKBACK, 2.0D);
    }

    protected void registerGoals() {
        super.registerGoals();
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
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.0D, Ingredient.of(Items.MELON), false));
        this.goalSelector.addGoal(3, new CustomRandomStrollGoal(this, 30, 1.0D, 100, 34));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.98F;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }


    @Nullable
    public Entity getControllingPassenger() {
        if (this.isSaddled()) {
            for (Entity passenger : this.getPassengers()) {
                if (passenger instanceof Player) {
                    Player player = (Player) passenger;
                    return player;
                }
            }
        }
        return null;
    }
    public void positionRider(Entity passenger) {
        float ySin = Mth.sin(this.yBodyRot * ((float)Math.PI / 180F));
        float yCos = Mth.cos(this.yBodyRot * ((float)Math.PI / 180F));
        passenger.setPos(this.getX() + (double)(-1.5F * ySin), this.getY() + this.getPassengersRidingOffset() + passenger.getMyRidingOffset() - 0.1F, this.getZ() - (double)(-1.5F * yCos));
    }

    public double getPassengersRidingOffset() {
        return 15.3D;
    }

    public boolean canBeCollidedWith() {
        return true;
    }

    protected void collideWithEntity(Entity entityIn) {
        entityIn.push(this);
    }

    public void collideWithNearbyEntities() {

    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if (item == Items.SADDLE && !this.isSaddled() && !this.isBaby()) {
            if (!player.isCreative()) {
                itemstack.shrink(1);
            }
            this.setSaddled(true);
            return InteractionResult.SUCCESS;
        } else if (itemstack.getItem() == Items.SHEARS && this.isSaddled()) {
            this.setSaddled(false);
            this.spawnAtLocation(Items.SADDLE);
            return InteractionResult.SUCCESS;
        }
        InteractionResult type = super.mobInteract(player, hand);
        if (type != InteractionResult.SUCCESS && !isFood(itemstack) && isSaddled()) {
            if (!player.isShiftKeyDown()) {
                player.startRiding(this);
                return InteractionResult.SUCCESS;
            }
        }
        return type;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HEAD_HEIGHT, 0F);
        this.getEntityData().define(SADDLED, false);
        this.entityData.define(LAUNCHING, Boolean.valueOf(false));
        this.entityData.define(ANIMATION_STATE, 0);
        this.entityData.define(COMBAT_STATE, 0);
        this.entityData.define(ENTITY_STATE, 0);

    }

    public void addAdditionalSaveData(CompoundTag p_31808_) {
        super.addAdditionalSaveData(p_31808_);
        p_31808_.putBoolean("Saddle", this.isSaddled());
    }

    public void readAdditionalSaveData(CompoundTag p_31795_) {
        super.readAdditionalSaveData(p_31795_);
        this.setSaddled(p_31795_.getBoolean("Saddle"));
    }

    public boolean isLaunching() {
        return this.entityData.get(LAUNCHING).booleanValue();
    }

    public void setLaunching(boolean charging) {
        this.entityData.set(LAUNCHING, Boolean.valueOf(charging));
    }


    public boolean isSaddled() {
        return this.entityData.get(SADDLED).booleanValue();
    }

    public void setSaddled(boolean saddled) {
        this.entityData.set(SADDLED, Boolean.valueOf(saddled));
    }

    protected void dropEquipment() {
        super.dropEquipment();
        if (this.isSaddled()) {
            if (!this.level.isClientSide) {
                this.spawnAtLocation(Items.SADDLE);
            }
        }
        this.setSaddled(false);
    }

    public int getMaxHeadXRot() {
        return 50;
    }

    public int getMaxHeadYRot() {
        return 50;
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

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(UPSounds.BRACHI_STEP.get(), 0.15F, 1.0F);
    }

    public void tick() {
        super.tick();
        prevHeadHeight = this.getHeadHeight();
        float neckBase = 5.5F;
        if (!this.isNoAi()) {
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
            Entity launch = this.level.getEntity(entityToLaunchId);
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
                    if (rider instanceof Player) {
                        Player player = (Player) rider;
                        player.addEffect(new MobEffectInstance(UPEffects.BRACHI_PROTECTION.get(), 200));
                    }
                }
            }
        }
        if (!level.isClientSide) {
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
                for (Entity entity : this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(1.0D))) {
                    if (!(entity instanceof EntityBrachiosaurus) && !entity.isPassengerOfSameVehicle(this)) {
                        entity.hurt(DamageSource.mobAttack(this), 4F + random.nextFloat() * 3.0F);
                        if (entity.isOnGround()) {
                            double d0 = entity.getX() - this.getX();
                            double d1 = entity.getZ() - this.getZ();
                            double d2 = Math.max(d0 * d0 + d1 * d1, 0.001D);
                            float f = 0.5F;
                            entity.push(d0 / d2 * f, f, d1 / d2 * f);
                        }
                    }
                }
                maxUpStep = 2;
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
        if(this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6){
            if(this.shakeCooldown <= 0 && UnusualPrehistoryConfig.SCREEN_SHAKE_BRACHI.get()) {
                double brachiShakeRange = UnusualPrehistoryConfig.SCREEN_SHAKE_BRACHI_RANGE.get();
                int brachiShakeAmp= UnusualPrehistoryConfig.SCREEN_SHAKE_BRACHI_AMPLIFIER.get();
                float brachiMoveSoundVolume= UnusualPrehistoryConfig.BRACHI_SOUND_VOLUME.get();
                List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(brachiShakeRange));
                for (LivingEntity e : list) {
                    if (!(e instanceof EntityBrachiosaurus) && e.isAlive()) {
                        e.addEffect(new MobEffectInstance(UPEffects.SCREEN_SHAKE.get(), 20, brachiShakeAmp, false, false, false));
                        this.playSound(UPSounds.BRACHI_STEP.get(), brachiMoveSoundVolume, 0.40F);
                    }
                }
                shakeCooldown = 10;
            }

            if(this.footPrintCooldown <= 0) {
                float ySin = Mth.sin(this.yBodyRot * ((float) Math.PI / 180F));
                float yCos = Mth.cos(this.yBodyRot * ((float) Math.PI / 180F));
                //TODO fix the position of the trail
                EntityTrail entityTrail = new EntityTrail(this, this.position().add(1.5*ySin,0.01,1.5*yCos), this.level, 50, (float) this.getLookAngle().x, 1.0F);
                EntityTrail entityTrail2 = new EntityTrail(this, this.position().add(1.5*ySin,0.01,-1.5*yCos), this.level, 50, (float) this.getLookAngle().x,1.0F);
                EntityTrail entityTrail3 = new EntityTrail(this, this.position().add(-1.5*ySin,0.01,-1.5*yCos), this.level, 50, (float) this.getLookAngle().x,1.0F);
                EntityTrail entityTrail4 = new EntityTrail(this, this.position().add(-1.5*ySin,0.01,1.5*yCos), this.level, 50, (float) this.getLookAngle().x,1.0F);
                this.level.addFreshEntity(entityTrail);
                this.level.addFreshEntity(entityTrail2);
                this.level.addFreshEntity(entityTrail3);
                this.level.addFreshEntity(entityTrail4);
                footPrintCooldown = 100;
            }
        }
        shakeCooldown--;
        footPrintCooldown--;
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
            return this.level.getBlockStates(axisalignedbb).filter(Predicate.not(BlockBehaviour.BlockStateBase::isAir)).anyMatch((p_185969_) -> {
                BlockPos blockpos = new BlockPos(vec3);
                return p_185969_.isSuffocating(this.level, blockpos) && Shapes.joinIsNotEmpty(p_185969_.getCollisionShape(this.level, blockpos).move(vec3.x, vec3.y, vec3.z), Shapes.create(axisalignedbb), BooleanOp.AND);
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
        part.setPos(this.getX() + offsetX * part.scale, this.getY() + offsetY * part.scale, this.getZ() + offsetZ * part.scale);
    }

    public boolean canPassThrough(BlockPos mutablePos, BlockState blockstate, VoxelShape voxelshape) {
        return blockstate.getBlock() == Blocks.BAMBOO || blockstate.is(BlockTags.LEAVES);
    }



    @Override
    public boolean isMultipartEntity() {
        return true;
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

    public void aiStep() {
        super.aiStep();
        scaleParts();

        if (this.horizontalCollision && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this)) {
            boolean flag = false;
            AABB axisalignedbb = this.getBoundingBox().inflate(0.2D);
            for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(axisalignedbb.minX), Mth.floor(axisalignedbb.minY), Mth.floor(axisalignedbb.minZ), Mth.floor(axisalignedbb.maxX), Mth.floor(axisalignedbb.maxY), Mth.floor(axisalignedbb.maxZ))) {
                BlockState blockstate = this.level.getBlockState(blockpos);
                if (blockstate.is(UPTags.PASSIVE_BRACHI_BREAKABLES)) {
                    flag = this.level.destroyBlock(blockpos, true, this) || flag;
                }
            }
        }
        if (this.horizontalCollision && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this) && this.isAggressive()) {
            boolean flag = false;
            AABB axisalignedbb = this.getBoundingBox().inflate(0.2D);
            for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(axisalignedbb.minX), Mth.floor(axisalignedbb.minY), Mth.floor(axisalignedbb.minZ), Mth.floor(axisalignedbb.maxX), Mth.floor(axisalignedbb.maxY), Mth.floor(axisalignedbb.maxZ))) {
                BlockState blockstate = this.level.getBlockState(blockpos);
                if (blockstate.is(UPTags.ANGRY_BRACHI_BREAKABLES)) {
                    flag = this.level.destroyBlock(blockpos, true, this) || flag;
                }
            }
        }
    }



    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
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
            long i = this.mob.level.getGameTime();

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
            this.mob.getNavigation().stop();
            if(animTime==5) {
                preformStompAttack();
            }
            if(animTime>=9) {
                animTime=0;

                this.mob.setAnimationState(0);
                this.resetAttackCooldown();
                this.ticksUntilNextPathRecalculation = 0;
            }
        }

        protected void preformStompAttack () {
            Vec3 pos = mob.position();
            HitboxHelper.LargeAttack(DamageSource.mobAttack(mob),25.0f, 2.5f, mob, pos,  7.0F, -Math.PI/2, Math.PI/2, -1.0f, 3.0f);
            if (this.mob.shakeCooldown <= 0 && UnusualPrehistoryConfig.SCREEN_SHAKE_BRACHI.get()) {
                double brachiShakeRange = UnusualPrehistoryConfig.SCREEN_SHAKE_BRACHI_RANGE.get();
                List<LivingEntity> list = this.mob.level.getEntitiesOfClass(LivingEntity.class, this.mob.getBoundingBox().inflate(brachiShakeRange));
                for (LivingEntity e : list) {
                    if (!(e instanceof EntityBrachiosaurus) && e.isAlive()) {
                        e.addEffect(new MobEffectInstance(UPEffects.SCREEN_SHAKE.get(), 50, 6, false, false, false));
                        this.mob.playSound(UPSounds.BRACHI_STEP.get(), 2F, 0.4F);
                    }
                }
                this.mob.shakeCooldown = 10;
            }
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

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        int animState = this.getAnimationState();
        {
            switch (animState) {

                case 21:
                    event.getController().setAnimation(new AnimationBuilder().playOnce("animation.brachiosaurus.attack"));
                    break;
                default:
                    if (this.isLaunching()) {
                        event.getController().setAnimation(new AnimationBuilder().playOnce("animation.brachiosaurus.launch"));
                        event.getController().setAnimationSpeed(1.0F);
                    }
                    if(event.isMoving()){
                        event.getController().setAnimation(new AnimationBuilder().loop("animation.brachiosaurus.walk"));
                        event.getController().setAnimationSpeed(1.5D);
                    }else{
                        event.getController().setAnimation(new AnimationBuilder().loop("animation.brachiosaurus.idle"));
                        event.getController().setAnimationSpeed(1.0F);
                    }
                    break;

            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(5);
        AnimationController<EntityBrachiosaurus> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        data.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }


}