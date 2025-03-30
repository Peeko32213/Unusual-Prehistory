package com.peeko32213.unusualprehistory.common.entity.projectile;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying.TelecrexEntity;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

public class TelecrexEgg extends ThrowableItemProjectile {

    public TelecrexEgg(EntityType egg, Level level) {
        super(egg, level);
    }

    public TelecrexEgg(Level worldIn, LivingEntity throwerIn) {
        super(UPEntities.TELECREX_EGG.get(), throwerIn, worldIn);
    }

    public TelecrexEgg(Level worldIn, double x, double y, double z) {
        super(UPEntities.TELECREX_EGG.get(), x, y, z, worldIn);
    }

    public TelecrexEgg(PlayMessages.SpawnEntity spawnEntity, Level world) {
        this(UPEntities.TELECREX_EGG.get(), world);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return (Packet<ClientGamePacketListener>) NetworkHooks.getEntitySpawningPacket(this);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - (double)0.5F) * 0.08, ((double)this.random.nextFloat() - (double)0.5F) * 0.08, ((double)this.random.nextFloat() - (double)0.5F) * 0.08);
            }
        }
    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        pResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
    }

    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            TelecrexEntity telecrex = UPEntities.TELECREX.get().create(this.level());
            if (telecrex != null) {
                telecrex.setAge(-24000);
                telecrex.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                this.level().addFreshEntity(telecrex);
            }
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    protected Item getDefaultItem() {
        return UPItems.TELECREX_EGG.get();
    }
}
