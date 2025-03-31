package com.peeko32213.unusualprehistory.common.entity.projectile;

import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nullable;

public class OpalescentPearl extends ThrowableItemProjectile {

    private int bounces = 0;

    public OpalescentPearl(EntityType<? extends OpalescentPearl> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public OpalescentPearl(Level pLevel, LivingEntity pShooter) {
        super(UPEntities.OPALESCENT_PEARL.get(), pShooter, pLevel);
        this.noPhysics = true;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("bounces", this.bounces);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.bounces = compound.getInt("bounces");
    }

    protected Item getDefaultItem() {
        return UPItems.OPALESCENT_PEARL.get();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        Direction hitDirection = result.getDirection();
        Vector3f surfaceNormal = hitDirection.step();
        Vec3 velocity = this.getDeltaMovement();
        Vec3 newVel = new Vec3(velocity.toVector3f().reflect(surfaceNormal));
        bounce(newVel);
    }

    private void bounce(Vec3 newVel) {
        bounces++;
        Vec3 velocity = this.getDeltaMovement();
        float conservedEnergy = 0.75f;
        newVel = newVel.scale(conservedEnergy);
        this.setDeltaMovement(newVel);
        double missingDistance = velocity.subtract(this.position().subtract(new Vec3(xo, yo, zo))).length();
        Vec3 missingVel = newVel.normalize().scale(missingDistance);
        this.move(MoverType.SELF, missingVel);
        if (!level().isClientSide) {
            this.hasImpulse = true;
            if (bounces == 0) {
                this.playSound(SoundEvents.ENDER_EYE_DEATH, 0.8f, 1.0F);
            }
            if (bounces == 1) {
                this.playSound(SoundEvents.ENDER_EYE_DEATH, 0.8f, 1.25F);
            }
            if (bounces == 2) {
                this.playSound(SoundEvents.ENDER_EYE_DEATH, 0.8f, 1.5F);
            }
            if (bounces == 3) {
                this.playSound(SoundEvents.ENDER_EYE_DEATH, 0.8f, 1.75F);
            }
            if (bounces > 3) {
                teleport();
                this.playSound(SoundEvents.ENDER_EYE_DEATH, 0.8f, 1.0F);
                this.discard();
            }
        }
    }

    public void teleport(){
        for (int i = 0; i < 32; ++i) {
            this.level().addParticle(ParticleTypes.PORTAL, this.getX(), this.getY() + this.random.nextDouble() * 2.0D, this.getZ(), this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
        }
        if (!this.level().isClientSide && !this.isRemoved()) {
            Entity entity = this.getOwner();
            if (entity instanceof ServerPlayer) {
                ServerPlayer serverplayer = (ServerPlayer) entity;
                if (serverplayer.connection.connection.isConnected() && serverplayer.level() == this.level() && !serverplayer.isSleeping()) {
                        if (entity.isPassenger()) {
                            serverplayer.dismountTo(this.getX(), this.getY(), this.getZ());
                        } else {
                            entity.teleportTo(this.getX(), this.getY(), this.getZ());
                        }
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
