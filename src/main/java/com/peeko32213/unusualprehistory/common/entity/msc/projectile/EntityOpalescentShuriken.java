package com.peeko32213.unusualprehistory.common.entity.msc.projectile;

import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;

public class EntityOpalescentShuriken extends ThrowableItemProjectile {
    private ItemStack toPlaceStack;
    public EntityOpalescentShuriken(EntityType<? extends EntityOpalescentShuriken> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public EntityOpalescentShuriken(Level pLevel, LivingEntity pShooter, ItemStack item) {
        super(UPEntities.OPALESCENT_SHURIKEN.get(), pShooter, pLevel);
        this.noPhysics = true;
        this.toPlaceStack = item.copy();
    }

    protected Item getDefaultItem() {
        return UPItems.OPALESCENT_SHURIKEN.get();
    }

    /**
     * Called when the arrow hits an entity
     */
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        pResult.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 0.0F);
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if(toPlaceStack != null) {
            double damage = toPlaceStack.getMaxDamage() / 10F;
            double currentDamage = toPlaceStack.getDamageValue();

            this.toPlaceStack.setDamageValue((int) (currentDamage + damage));
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        Entity entity = this.getOwner();
        if (entity instanceof Player && !entity.isAlive()) {
            this.discard();
        } else {
            if(tickCount < 200) {
                this.noPhysics = true;
                RandomSource rand = this.level.random;
                if (rand.nextInt(0, 100) < 10) {
                    randomTeleport();
                }
            } else {
                dropStack();
                this.discard();
            }


            super.tick();
        }

    }

    public void dropStack(){
        if(toPlaceStack != null && !(toPlaceStack.getDamageValue() > toPlaceStack.getMaxDamage())) {
            Level level = this.level;
            ItemEntity item = new ItemEntity(level, this.getX(), this.getY(), this.getZ(), toPlaceStack);
            level.addFreshEntity(item);
        }
    }

    public void randomTeleport() {
        for (int i = 0; i < 32; ++i) {
            this.level.addParticle(ParticleTypes.PORTAL, this.getX(), this.getY() + this.random.nextDouble() * 2.0D, this.getZ(), this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
        }

        if (!this.level.isClientSide && !this.isRemoved()) {
            Entity entity = this.getOwner();
            if (entity instanceof ServerPlayer) {
                ServerPlayer serverplayer = (ServerPlayer) entity;
                if (serverplayer.connection.getConnection().isConnected() && serverplayer.level == this.level && !serverplayer.isSleeping()) {
                    if (entity.isPassenger()) {
                        serverplayer.dismountTo(this.getX(), this.getY(), this.getZ());
                    } else {
                        entity.teleportTo(this.getX(), this.getY(), this.getZ());
                    }

                    entity.resetFallDistance();
                    entity.hurt(DamageSource.FALL, 2);
                }
            } else if (entity != null) {
                entity.teleportTo(this.getX(), this.getY(), this.getZ());
                entity.resetFallDistance();
            }
            dropStack();
            this.discard();
        }
    }

    @Nullable
    public Entity changeDimension(ServerLevel pServer, net.minecraftforge.common.util.ITeleporter teleporter) {
        Entity entity = this.getOwner();
        if (entity != null && entity.level.dimension() != pServer.dimension()) {
            this.setOwner((Entity) null);
        }

        return super.changeDimension(pServer, teleporter);
    }

    @Override
    protected float getGravity() {
        return 0.01F;
    }
}
