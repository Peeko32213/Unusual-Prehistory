package com.peeko32213.unusualprehistory.common.entity.msc.part;

import com.peeko32213.unusualprehistory.common.entity.EntityPalaeophis;
import com.peeko32213.unusualprehistory.common.message.UPMessageHurtMultipart;
import com.peeko32213.unusualprehistory.core.registry.UPMessages;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.entity.PartEntity;

import java.util.List;

public class EntityPalaeophisPart extends PartEntity<EntityPalaeophis> {

    private final EntityDimensions size;
    public float scale = 1;
    private int headEntityId = -1;

    public EntityPalaeophisPart(EntityPalaeophis parent, float sizeX, float sizeY) {
        super(parent);
        this.size = EntityDimensions.scalable(sizeX, sizeY);
        this.refreshDimensions();
    }

    public EntityPalaeophisPart(EntityPalaeophis entityPalaeophis, float sizeX, float sizeY, EntityDimensions size) {
        super(entityPalaeophis);
        this.size = size;
    }

    public void collideWithNearbyEntities() {
        final List<Entity> entities = this.level.getEntities(this, this.getBoundingBox().expandTowards(0.2D, 0.0D, 0.2D));
        Entity parent = this.getParent();
        if (parent != null) {
            entities.stream().filter(entity -> entity != parent && !(entity instanceof EntityPalaeophisPart && ((EntityPalaeophisPart) entity).getParent() == parent) && entity.isPushable()).forEach(entity -> entity.push(parent));
        }
    }

    @Override
    public InteractionResult interact(Player p_19978_, InteractionHand p_19979_) {
        return this.getParent() == null ? super.interact(p_19978_, p_19979_) : this.getParent().interact(p_19978_, p_19979_);
    }


    protected void collideWithEntity(Entity entityIn) {
        entityIn.push(this);
    }

    public boolean isPickable() {
        return true;
    }

    public boolean hurtHeadId(DamageSource source, float f) {
        if (headEntityId != -1) {
            Entity e = level.getEntity(headEntityId);
            if (e instanceof EntityPalaeophis) {
                return e.hurt(source, f);
            }
        }
        return false;
    }

    public boolean hurt(DamageSource source, float amount) {
        if(level.isClientSide && this.getParent() != null && !this.getParent().isInvulnerableTo(source)){
            UPMessages.sendToClients(new UPMessageHurtMultipart(this.getId(), this.getParent().getId(), amount, source.msgId));
        }
        return !this.isInvulnerableTo(source) && this.getParent().attackEntityPartFrom(this, source, amount);
    }

    public boolean is(Entity entityIn) {
        return this == entityIn || this.getParent() == entityIn;
    }

    public Packet<?> getAddEntityPacket() {
        throw new UnsupportedOperationException();
    }

    public EntityDimensions getDimensions(Pose poseIn) {
        return this.size.scale(scale);
    }

    @Override
    protected void defineSynchedData() {

    }

    public void tick(){
        super.tick();
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {

    }
}