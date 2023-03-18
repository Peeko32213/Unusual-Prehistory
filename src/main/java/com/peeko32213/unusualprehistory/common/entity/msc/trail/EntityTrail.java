package com.peeko32213.unusualprehistory.common.entity.msc.trail;

import com.mojang.logging.LogUtils;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityTrail extends Projectile implements IEntityAdditionalSpawnData {
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(EntityTrail.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> SCALE = SynchedEntityData.defineId(EntityTrail.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> ROT_ANGLE = SynchedEntityData.defineId(EntityTrail.class, EntityDataSerializers.FLOAT);

    private static final Logger LOGGER = LogUtils.getLogger();
    public EntityTrail entityTrail;
    public EntityTrail(EntityType<? extends EntityTrail> type, Level level) {
        super(type, level);
        this.noCulling = true;
    }

    public EntityTrail(Entity entity, Vec3 pos, Level pLevel, int duration, float angle, float scale) {
        this(UPEntities.ENTITY_TRAIL.get(), pLevel);
        this.setOwner(entity);
        setDuration(duration);
        setAngle(angle);
        setScale(scale);
        //float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
        //float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
        //double d0 = entity.getX() - (double)f3 * 0.3D;
        //double d1 = entity.getEyeY();
        //double d2 = entity.getZ() - (double)f2 * 0.3D;
        this.setPos(pos);

    }

    public void tick() {
        if(this.getOwner() != null && getDuration() >= 0)
        {

            setDuration(getDuration() - 1);
            return;
        }
        this.discard();
    }
    public void onClientRemoval() {
        this.updateOwnerInfo((EntityTrail) null);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putUUID("owner", this.getOwner().getUUID());
        tag.putInt("duration", this.getDuration());
        tag.putFloat("rot_angle", this.getAngle());
        tag.putFloat("scale", this.getScale());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setDuration(tag.getInt("duration"));
        setAngle(tag.getFloat("rot_angle"));
        setScale(tag.getFloat("scale"));
        if(this.level instanceof ServerLevel serverLevel){
            this.setOwner(serverLevel.getEntity(tag.getUUID("owner")));
        }
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DURATION, Integer.valueOf(0));
        this.entityData.define(SCALE, Float.valueOf(0));
        this.entityData.define(ROT_ANGLE, Float.valueOf(0));
    }
    public int getDuration(){
        return this.entityData.get(DURATION).intValue();
    }
    public void setDuration(int duration){
        this.entityData.set(DURATION, Integer.valueOf(duration));
    }
    public float getAngle(){
        return this.entityData.get(ROT_ANGLE).floatValue();
    }
    public void setAngle(float angle){
        this.entityData.set(ROT_ANGLE, Float.valueOf(angle));
    }
    public float getScale(){
        return this.entityData.get(SCALE).floatValue();
    }
    public void setScale(float scale){
        this.entityData.set(SCALE, Float.valueOf(scale));
    }

    public Packet<?> getAddEntityPacket() {
        Entity entity = this.getOwner();
        return new ClientboundAddEntityPacket(this, entity == null ? this.getId() : entity.getId());
    }

    public void recreateFromPacket(ClientboundAddEntityPacket pPacket) {
        super.recreateFromPacket(pPacket);
        if (this.getEntityOwner() == null) {
            int i = pPacket.getData();
            LOGGER.error("Failed to recreate footprint on client. {} (id: {}) is not a valid owner.", this.level.getEntity(i), i);
            this.kill();
        }
    }
    @Nullable
    public LivingEntity getEntityOwner() {
        Entity entity = this.getOwner();
        return entity instanceof LivingEntity ? (LivingEntity) entity : null;
    }

    private void updateOwnerInfo(@Nullable EntityTrail entityTrail) {
        LivingEntity livingEntity = this.getEntityOwner();
        if (livingEntity != null) {
            this.entityTrail = entityTrail;
        }
    }

    @Nonnull
    @Override
    public EntityType<?> getType() {
        return UPEntities.ENTITY_TRAIL.get();
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {

    }
}
