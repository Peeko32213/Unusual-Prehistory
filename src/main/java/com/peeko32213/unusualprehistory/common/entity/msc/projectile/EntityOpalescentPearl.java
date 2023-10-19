package com.peeko32213.unusualprehistory.common.entity.msc.projectile;

import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;

public class EntityOpalescentPearl extends ThrowableItemProjectile {

    public EntityOpalescentPearl(EntityType<? extends EntityOpalescentPearl> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public EntityOpalescentPearl(Level pLevel, LivingEntity pShooter) {
        super(UPEntities.OPALESCENT_PEARL.get(), pShooter, pLevel);
        this.noPhysics = true;

    }

    protected Item getDefaultItem() {
        return UPItems.OPALESCENT_PEARL.get();
    }

    /**
     * Called when the arrow hits an entity
     */
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        pResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);



    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        Entity entity = this.getOwner();
        if (entity instanceof Player && !entity.isAlive() || tickCount > 200) {
            this.discard();
        } else if(!this.level().getBlockState(this.blockPosition()).is(Blocks.BEDROCK) && !(this.position().y() <= -64)) {
            this.noPhysics = true;
            RandomSource rand = this.level().random;
            if(rand.nextInt(0,100) < 10){
                randomTeleport();
            }
            super.tick();
        }
    }

    public void randomTeleport(){
        for (int i = 0; i < 32; ++i) {
            this.level().addParticle(ParticleTypes.PORTAL, this.getX(), this.getY() + this.random.nextDouble() * 2.0D, this.getZ(), this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
        }

        if (!this.level().isClientSide && !this.isRemoved()) {
            Entity entity = this.getOwner();
            if (entity instanceof ServerPlayer) {
                ServerPlayer serverplayer = (ServerPlayer) entity;
                if (serverplayer.connection.connection.isConnected() && serverplayer.level() == this.level() && !serverplayer.isSleeping()) {
                        //if (this.random.nextFloat() < 0.05F && this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
                        //    Endermite endermite = EntityType.ENDERMITE.create(this.level);
                        //    endermite.moveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
                        //    this.level.addFreshEntity(endermite);
                        //}

                        if (entity.isPassenger()) {
                            serverplayer.dismountTo(this.getX(), this.getY(), this.getZ());
                        } else {
                            entity.teleportTo(this.getX(), this.getY(), this.getZ());
                        }

                        //entity.teleportTo(this.getTargetX(), event.getTargetY(), event.getTargetZ());
                        entity.resetFallDistance();
                        entity.hurt(this.damageSources().fall(), 2);
                }
            } else if (entity != null) {
                entity.teleportTo(this.getX(), this.getY(), this.getZ());
                entity.resetFallDistance();
            }

            this.discard();
        }
    }

    @Nullable
    public Entity changeDimension(ServerLevel pServer, net.minecraftforge.common.util.ITeleporter teleporter) {
        Entity entity = this.getOwner();
        if (entity != null && entity.level().dimension() != pServer.dimension()) {
            this.setOwner((Entity) null);
        }

        return super.changeDimension(pServer, teleporter);
    }
}
