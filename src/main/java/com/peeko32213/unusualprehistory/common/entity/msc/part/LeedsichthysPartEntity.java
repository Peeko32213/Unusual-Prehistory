package com.peeko32213.unusualprehistory.common.entity.msc.part;

import com.google.common.collect.ImmutableList;
import com.peeko32213.unusualprehistory.common.entity.LeedsichthysEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces.IHurtableMultipart;
import com.peeko32213.unusualprehistory.common.message.UPMessageHurtMultipart;
import com.peeko32213.unusualprehistory.core.registry.UPMessages;
import com.peeko32213.unusualprehistory.core.registry.util.UPMath;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class LeedsichthysPartEntity extends LivingEntity implements IHurtableMultipart, GeoAnimatable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);


    private static final EntityDataAccessor<Integer> BODYINDEX = SynchedEntityData.defineId(LeedsichthysPartEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BODY_TYPE = SynchedEntityData.defineId(LeedsichthysPartEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> TARGET_YAW = SynchedEntityData.defineId(LeedsichthysPartEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Optional<UUID>> CHILD_UUID = SynchedEntityData.defineId(LeedsichthysPartEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Optional<UUID>> PARENT_UUID = SynchedEntityData.defineId(LeedsichthysPartEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    public EntityDimensions multipartSize;
    private int headEntityId = -1;
    private double prevHeight = 0;
    public LeedsichthysPartEntity(EntityType t, Level world) {
        super(t, world);
        multipartSize = t.getDimensions();
    }

    public LeedsichthysPartEntity(EntityType t, LivingEntity parent) {
        super(t, parent.level());
        this.setParent(parent);
    }

    @Override
    public InteractionResult interact(Player p_19978_, InteractionHand p_19979_) {
        return this.getParent() == null ? super.interact(p_19978_, p_19979_) : this.getParent().interact(p_19978_, p_19979_);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1000.0D)
                .add(Attributes.ATTACK_DAMAGE, 20.0D)
                .add(Attributes.ARMOR, 50.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 50.0D)
                .add(Attributes.FOLLOW_RANGE, 12.0D);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source == this.damageSources().inWall() || source == this.damageSources().fallingBlock(source.getEntity()) || super.isInvulnerableTo(source);
    }

    @Override
    public void tick() {
        super.tick();

        isInsidePortal = false;
        this.setDeltaMovement(Vec3.ZERO);
        if (this.tickCount > 1) {
            final Entity parent = getParent();
            refreshDimensions();
            if (!level().isClientSide) {
                if (parent == null) {
                    this.remove(RemovalReason.DISCARDED);
                }
                if (parent != null) {
                    if (parent instanceof final LivingEntity livingEntityParent) {
                        if (livingEntityParent.hurtTime > 0 || livingEntityParent.deathTime > 0) {
                            UPMessages.sendMSGToAll(new UPMessageHurtMultipart(this.getId(), parent.getId(), 0));
                            this.hurtTime = livingEntityParent.hurtTime;
                            this.deathTime = livingEntityParent.deathTime;
                        }
                    }
                    if (parent.isRemoved()) {
                        this.remove(RemovalReason.DISCARDED);
                    }
                } else if (tickCount > 20) {
                    remove(RemovalReason.DISCARDED);
                }
            }
        }
    }

    public Vec3 tickMultipartPosition(int headId, LeedsichthysPartIndex parentIndex, Vec3 parentPosition, float parentXRot, float parentYRot, float ourYRot, boolean doHeight) {
        final Vec3 parentButt = parentPosition.add(calcOffsetVec(-parentIndex.getBackOffset() * this.getScale(), parentXRot, parentYRot));
        final Vec3 ourButt = parentButt.add(calcOffsetVec((-this.getPartType().getBackOffset() - 0.5F * this.getBbWidth()) * this.getScale(), this.getXRot(), ourYRot));
        final Vec3 avg = new Vec3((parentButt.x + ourButt.x) / 2F, (parentButt.y + ourButt.y) / 2F, (parentButt.z + ourButt.z) / 2F);
        final double d0 = parentButt.x - ourButt.x;
//        final double d1 = parentButt.y - ourButt.y;
        final double d2 = parentButt.z - ourButt.z;
        final double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        final double hgt = doHeight ? (getLowPartHeight(parentButt.x, parentButt.y, parentButt.z) + getHighPartHeight(ourButt.x, ourButt.y, ourButt.z)) : 0;
        if (Math.abs(hgt - prevHeight) > 0.2F) {
            prevHeight = hgt;
        }
        final double partYDest = Mth.clamp(this.getScale() * prevHeight, -0.6F, 0.6F);
        final float f = (float) (Mth.atan2(d2, d0) * 57.2957763671875D) - 90.0F;
        final float rawAngle = Mth.wrapDegrees((float) (-(Mth.atan2(partYDest, d3) * UPMath.oneEightyDividedByFloatPi)));
        final float f2 = this.limitAngle(this.getXRot(), rawAngle, 5F);
        this.setXRot(f2);
        this.setYRot(f);
        this.yHeadRot = f;
        this.moveTo(avg.x, avg.y, avg.z, f, f2);
        headEntityId = headId;
        return avg;
    }

    public double getLowPartHeight(double x, double yIn, double z) {
        if (isFluidAt(x, yIn, z))
            return 0.0D;

        double checkAt = 0D;
        while (checkAt > -3D && !isOpaqueBlockAt(x,yIn + checkAt, z)) {
            checkAt -= 0.2D;
        }

        return checkAt;
    }

    public double getHighPartHeight(double x, double yIn, double z) {
        if (isFluidAt(x, yIn, z))
            return 0.0D;

        double checkAt = 0D;
        while (checkAt <= 3D) {
            if (isOpaqueBlockAt(x, yIn + checkAt, z)) {
                checkAt += 0.2D;
            } else {
                break;
            }
        }

        return checkAt;
    }

    public boolean isOpaqueBlockAt(double x, double y, double z) {
        if (this.noPhysics) {
            return false;
        } else {
            final double d = 1D;
            final Vec3 vec3 = new Vec3(x, y, z);
            final AABB axisAlignedBB = AABB.ofSize(vec3, d, 1.0E-6D, d);
            return this.level().getBlockStates(axisAlignedBB).filter(Predicate.not(BlockBehaviour.BlockStateBase::isAir)).anyMatch((p_185969_) -> {
                BlockPos blockpos = BlockPos.containing(vec3.x, vec3.y, vec3.z);
                return p_185969_.isSuffocating(this.level(), blockpos) && Shapes.joinIsNotEmpty(p_185969_.getCollisionShape(this.level(), blockpos).move(vec3.x, vec3.y, vec3.z), Shapes.create(axisAlignedBB), BooleanOp.AND);
            });
        }
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public boolean isPushedByFluid() {
        return false;
    }

    public boolean isFluidAt(double x, double y, double z) {
        if (this.noPhysics) {
            return false;
        } else {
            return !level().getFluidState(BlockPos.containing(x, y, z)).isEmpty();
        }
    }

    public boolean hurtHeadId(DamageSource source, float f) {
        if (headEntityId != -1) {
            Entity e = level().getEntity(headEntityId);
            if (e instanceof LeedsichthysEntity) {
                return e.hurt(source, f);
            }
        }
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float damage) {
        if(source != this.damageSources().inWall() && source != this.damageSources().fallingBlock(source.getEntity()) && !super.isInvulnerableTo(source)) {
            final Entity parent = getParent();
            if(!level().isClientSide){
                if(source.getEntity() instanceof ServerPlayer player) {
                   // UPMessages.sendToPlayer(new UPMessageHurtMultipart(this.getId(), parent.getId(), damage),player);
                }
            }

            return hurtHeadId(source, damage);
        } else {
            return false;
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CHILD_UUID, Optional.empty());
        this.entityData.define(PARENT_UUID, Optional.empty());
        this.entityData.define(BODYINDEX, 0);
        this.entityData.define(BODY_TYPE, LeedsichthysPartIndex.BODY.ordinal());
        this.entityData.define(TARGET_YAW, 0F);
    }


    public void pushEntities() {
        final List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().expandTowards(0.2D, 0.0D, 0.2D));
        final Entity parent = this.getParent();
        if (parent != null) {
            entities.stream().filter(entity -> !entity.is(parent) && !(entity instanceof LeedsichthysPartEntity || entity instanceof LeedsichthysEntity) && entity.isPushable()).forEach(entity -> entity.push(parent));
        }
    }

    public boolean isNoGravity() {
        return false;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return ImmutableList.of();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slotIn) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot p_21036_, ItemStack p_21037_) {

    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    public void onAttackedFromServer(LivingEntity parent, float damage, DamageSource damageSource) {
        if (parent.deathTime > 0)
            this.deathTime = parent.deathTime;

        if (parent.hurtTime > 0)
            this.hurtTime = parent.hurtTime;
    }

    public Entity getParent() {
        if (!level().isClientSide) {
            final UUID id = getParentId();
            if (id != null) {
                return ((ServerLevel) level()).getEntity(id);
            }
        }

        return null;
    }

    public void setParent(Entity entity) {
        this.setParentId(entity.getUUID());
    }

    @Nullable
    public UUID getParentId() {
        return this.entityData.get(PARENT_UUID).orElse(null);
    }

    public void setParentId(@Nullable UUID uniqueId) {
        this.entityData.set(PARENT_UUID, Optional.ofNullable(uniqueId));
    }

    public Entity getChild() {
        if (!level().isClientSide) {
            final UUID id = getChildId();
            if (id != null) {
                return ((ServerLevel) level()).getEntity(id);
            }
        }

        return null;
    }

    @Nullable
    public UUID getChildId() {
        return this.entityData.get(CHILD_UUID).orElse(null);
    }

    public void setChildId(@Nullable UUID uniqueId) {
        this.entityData.set(CHILD_UUID, Optional.ofNullable(uniqueId));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.getParentId() != null) {
            compound.putUUID("ParentUUID", this.getParentId());
        }
        if (this.getChildId() != null) {
            compound.putUUID("ChildUUID", this.getChildId());
        }
        compound.putInt("BodyModel", getPartType().ordinal());
        compound.putInt("BodyIndex", getBodyIndex());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.hasUUID("ParentUUID")) {
            this.setParentId(compound.getUUID("ParentUUID"));
        }
        if (compound.hasUUID("ChildUUID")) {
            this.setChildId(compound.getUUID("ChildUUID"));
        }
        this.setPartType(LeedsichthysPartIndex.fromOrdinal(compound.getInt("BodyModel")));
        this.setBodyIndex(compound.getInt("BodyIndex"));
    }

    @Override
    public boolean is(Entity entity) {
        return this == entity || this.getParent() == entity;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    public int getBodyIndex() {
        return this.entityData.get(BODYINDEX);
    }

    public void setBodyIndex(int index) {
        this.entityData.set(BODYINDEX, index);
    }

    public LeedsichthysPartIndex getPartType() {
        return LeedsichthysPartIndex.fromOrdinal(this.entityData.get(BODY_TYPE));
    }

    public void setPartType(LeedsichthysPartIndex index) {
        this.entityData.set(BODY_TYPE, index.ordinal());
    }

    public void setTargetYaw(float f) {
        this.entityData.set(TARGET_YAW, f);
    }

    @Override
    public float getYRot() {
        return super.getYRot();
    }


    @Override
    public Vec3 calcOffsetVec(float offsetZ, float xRot, float yRot) {
        return IHurtableMultipart.super.calcOffsetVec(offsetZ, xRot, yRot);
    }
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
    @Override
    public float limitAngle(float sourceAngle, float targetAngle, float maximumChange) {
        return IHurtableMultipart.super.limitAngle(sourceAngle, targetAngle, maximumChange);
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }
}
