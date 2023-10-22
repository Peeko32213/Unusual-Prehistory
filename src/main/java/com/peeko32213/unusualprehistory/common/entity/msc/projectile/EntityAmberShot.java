package com.peeko32213.unusualprehistory.common.entity.msc.projectile;

import com.peeko32213.unusualprehistory.common.entity.EntityAntarctopelta;
import com.peeko32213.unusualprehistory.common.entity.msc.util.ranged.BetterAbstractHurtingProjectile;
import com.peeko32213.unusualprehistory.common.entity.msc.util.ranged.RangedMeleeMob;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class EntityAmberShot extends BetterAbstractHurtingProjectile implements GeoAnimatable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected int timeInAir;
    protected boolean inAir;
    private int ticksInAir;
    private static final RawAnimation AMBER_SHOT = RawAnimation.begin().thenPlay("animation.amber_shot.move");

    public EntityAmberShot(EntityType<? extends EntityAmberShot> p_i50160_1_, Level p_i50160_2_) {
        super(p_i50160_1_, p_i50160_2_);
    }

    public EntityAmberShot(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ,
                             float directHitDamage) {
        super(UPEntities.AMBER_SHOT.get(), shooter, accelX, accelY, accelZ, worldIn);
        this.directHitDamage = directHitDamage;
    }

    public EntityAmberShot(Level worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(UPEntities.AMBER_SHOT.get(), x, y, z, accelX, accelY, accelZ, worldIn);
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        super.shoot(x, y, z, velocity, inaccuracy);
        this.ticksInAir = 0;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putShort("life", (short) this.ticksInAir);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.ticksInAir = compound.getShort("life");
    }

    private float directHitDamage = 3;

    public void setDirectHitDamage(float directHitDamage) {
        this.directHitDamage = directHitDamage;
    }

    @Override
    public void tick() {
        Entity entity = this.getOwner();
        if (this.level().isClientSide
                || (entity == null || entity.isAlive()) && this.level().hasChunkAt(this.blockPosition())) {
            super.tick();
            HitResult raytraceresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
            if (raytraceresult.getType() != HitResult.Type.MISS
                    && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                this.onHit(raytraceresult);
            }
            this.checkInsideBlocks();
            Vec3 vector3d = this.getDeltaMovement();
            double d0 = this.getX() + vector3d.x;
            double d1 = this.getY() + vector3d.y;
            double d2 = this.getZ() + vector3d.z;
            ProjectileUtil.rotateTowardsMovement(this, 0.2F);
            float f = this.getInertia();
            if (this.isInWater()) {
                for (int i = 0; i < 4; ++i) {
                    this.level().addParticle(ParticleTypes.DRIPPING_HONEY, d0 - vector3d.x * 0.25D, d1 - vector3d.y * 0.25D,
                            d2 - vector3d.z * 0.25D, vector3d.x, vector3d.y, vector3d.z);
                }
                f = 0.8F;
            }
            this.setDeltaMovement(vector3d.add(this.xPower, this.yPower, this.zPower).scale((double) f));
            this.setPos(d0, d1, d2);
        } else {
            this.remove(RemovalReason.KILLED);
        }
    }



    protected boolean shouldBurn() {
        return false;
    }


    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return (Packet<ClientGamePacketListener>) NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean isNoGravity() {
        if (this.isInWater()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    protected void onHitEntity(EntityHitResult p_213868_1_) {
        super.onHitEntity(p_213868_1_);
        if (!this.level().isClientSide) {
            Entity entity = p_213868_1_.getEntity();
            Entity entity1 = this.getOwner();
            if (!(entity instanceof RangedMeleeMob))
                entity.hurt(this.damageSources().mobAttack((LivingEntity) entity1), directHitDamage);
            this.remove(RemovalReason.KILLED);
            if (entity1 instanceof LivingEntity) {
                if (!(entity instanceof RangedMeleeMob))
                    this.doEnchantDamageEffects((LivingEntity) entity1, entity);
            }
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    protected <E extends EntityAmberShot> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        event.setAndContinue(AMBER_SHOT);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
        return null;
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }
}

