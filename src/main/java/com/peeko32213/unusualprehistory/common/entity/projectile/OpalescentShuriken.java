package com.peeko32213.unusualprehistory.common.entity.projectile;

import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;

public class OpalescentShuriken extends ThrowableItemProjectile implements IEntityAdditionalSpawnData {

    public OpalescentShuriken(EntityType<? extends OpalescentShuriken> type, Level worldIn) {
        super(type, worldIn);
    }

    public OpalescentShuriken(Level pLevel, LivingEntity pShooter, ItemStack item) {
        super(UPEntities.OPALESCENT_SHURIKEN.get(), pShooter, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return UPItems.OPALESCENT_SHURIKEN.get();
    }

    public float getDamage() {
        return 6.0F;
    }

    public float getKnockback() {
        return 0.25F;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

        Level level = level();
        if (!level.isClientSide) {
            level.broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.spawnAtLocation(getDefaultItem());
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        entity.hurt(damageSources().thrown(this, this.getOwner()), this.getDamage());

        if (!level().isClientSide() && entity instanceof LivingEntity) {
            Vec3 motion = this.getDeltaMovement().normalize();
            ((LivingEntity) entity).knockback(this.getKnockback(), -motion.x, -motion.z);
        }
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeItem(this.getItemRaw());
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        this.setItem(additionalData.readItem());
    }

    @Nonnull
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected float getGravity() {
        return 0.0225F;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }
}
