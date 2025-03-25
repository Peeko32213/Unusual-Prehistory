package com.peeko32213.unusualprehistory.common.entity.projectile;

import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
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

import javax.annotation.Nullable;

public class OpalescentPearl extends ThrowableItemProjectile {

    public OpalescentPearl(EntityType<? extends OpalescentPearl> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public OpalescentPearl(Level pLevel, LivingEntity pShooter) {
        super(UPEntities.OPALESCENT_PEARL.get(), pShooter, pLevel);
        this.noPhysics = true;
    }

    protected Item getDefaultItem() {
        return UPItems.OPALESCENT_PEARL.get();
    }

    public void tick() {
        Entity entity = this.getOwner();
        if (entity instanceof Player && !entity.isAlive() || tickCount > 200) {
            this.discard();
        } else if(!this.level().getBlockState(this.blockPosition()).is(Blocks.BEDROCK) && !(this.position().y() <= -64)) {
            this.noPhysics = true;
            RandomSource rand = this.level().random;
            if(rand.nextInt(0,128) < 7){
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
            this.setOwner(null);
        }

        return super.changeDimension(pServer, teleporter);
    }
}
