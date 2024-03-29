package com.peeko32213.unusualprehistory.common.entity.msc.util.ranged;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class CustomAbstractRangedAttack implements IRangedAttack {

    public RangedMeleeMob parentEntity;
    public double xOffSetModifier = 2;
    public double entityHeightFraction = 0.5;
    public double zOffSetModifier = 2;
    public float damage = 1;
    public double accuracy = 0.95;

    public CustomAbstractRangedAttack(RangedMeleeMob parentEntity) {
        this.parentEntity = parentEntity;
    }

    public CustomAbstractRangedAttack(RangedMeleeMob parentEntity, double xOffSetModifier, double entityHeightFraction,
                                double zOffSetModifier, float damage) {
        this.parentEntity = parentEntity;
        this.xOffSetModifier = xOffSetModifier;
        this.entityHeightFraction = entityHeightFraction;
        this.zOffSetModifier = zOffSetModifier;
        this.damage = damage;
    }

    public CustomAbstractRangedAttack setProjectileOriginOffset(double x, double entityHeightFraction, double z) {
        xOffSetModifier = x;
        this.entityHeightFraction = entityHeightFraction;
        zOffSetModifier = z;
        return this;
    }

    public CustomAbstractRangedAttack setDamage(float damage) {
        this.damage = damage;
        return this;
    }

    private AttackSound sound;

    public CustomAbstractRangedAttack setSound(AttackSound sound) {
        this.sound = sound;
        return this;
    }

    public CustomAbstractRangedAttack setSound(SoundEvent sound, float volume, float pitch) {
        this.sound = new AttackSound(sound, volume, pitch);
        return this;
    }

    public CustomAbstractRangedAttack setAccuracy(double accuracy) {
        this.accuracy = accuracy;
        return this;
    }

    public double rollAccuracy(double directional) {
        return directional + (1.0D - accuracy) * directional * this.parentEntity.getRandom().nextGaussian();
    }

    public void shoot() {
        LivingEntity livingentity = this.parentEntity.getTarget();
        Level world = this.parentEntity.getCommandSenderWorld();
        Vec3 vector3d = this.parentEntity.getViewVector(1.0F);
        double d2 = livingentity.getX() - (this.parentEntity.getX() + vector3d.x * xOffSetModifier);
        double d3 = livingentity.getY(0.5D) - (this.parentEntity.getY(entityHeightFraction));
        double d4 = livingentity.getZ() - (this.parentEntity.getZ() + vector3d.z * zOffSetModifier);
        Projectile projectile = getProjectile(world, rollAccuracy(d2), rollAccuracy(d3), rollAccuracy(d4));
        projectile.setPos(this.parentEntity.getX() + vector3d.x * xOffSetModifier,
                this.parentEntity.getY(entityHeightFraction), this.parentEntity.getZ() + vector3d.z * zOffSetModifier);
        world.addFreshEntity(projectile);
        if (sound == null)
            getDefaultAttackSound().play(this.parentEntity);
        else
            sound.play(this.parentEntity);
    }
}