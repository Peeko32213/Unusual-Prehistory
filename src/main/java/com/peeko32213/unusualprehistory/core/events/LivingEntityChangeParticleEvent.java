package com.peeko32213.unusualprehistory.core.events;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 * <p>
 * An event fired whenever a {@link LivingEntity} spawns a particle in
 * {@link LivingEntity#tickEffects}. This event is {@link Cancelable}, meaning if it is canceled,
 * the particle will not be spawned.
 * </p>
 *
 * <p>
 * Use {@link #setParticleData(ParticleOptions)} and the other setter methods to change what
 * particle is spawned and where, or cancel the event to prevent spawning entirely.
 * </p>
 */
@Cancelable
public class LivingEntityChangeParticleEvent extends Event {

    /**
     * The entity that spawned the particle.
     */
    private final LivingEntity entity;

    /**
     * The type of particle to spawn.
     */
    private ParticleOptions particleData;

    /**
     * The X coordinate for where the particle will be spawned.
     */
    private double x;

    /**
     * The Y coordinate for where the particle will be spawned.
     */
    private double y;

    /**
     * The Z coordinate for where the particle will be spawned.
     */
    private double z;

    /**
     * The particle’s motion speed along the X axis.
     */
    private double xSpeed;

    /**
     * The particle’s motion speed along the Y axis.
     */
    private double ySpeed;

    /**
     * The particle’s motion speed along the Z axis.
     */
    private double zSpeed;

    /**
     * Constructs a new {@code LivingEntityParticleEvent}.
     *
     * @param entity        The living entity that is spawning the particle.
     * @param particleData  The particle type to be spawned.
     * @param x             The X coordinate for where the particle is spawned.
     * @param y             The Y coordinate for where the particle is spawned.
     * @param z             The Z coordinate for where the particle is spawned.
     * @param xSpeed        The particle's motion speed along the X axis.
     * @param ySpeed        The particle's motion speed along the Y axis.
     * @param zSpeed        The particle's motion speed along the Z axis.
     */
    public LivingEntityChangeParticleEvent(
            LivingEntity entity,
            ParticleOptions particleData,
            double x,
            double y,
            double z,
            double xSpeed,
            double ySpeed,
            double zSpeed
    ) {
        this.entity = entity;
        this.particleData = particleData;
        this.x = x;
        this.y = y;
        this.z = z;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.zSpeed = zSpeed;
    }

    /**
     * Gets the {@link LivingEntity} that is spawning the particle.
     *
     * @return The living entity that spawns the particle.
     */
    public LivingEntity getEntity() {
        return entity;
    }

    /**
     * Gets the current {@link ParticleOptions} for this event.
     *
     * @return The particle type to be spawned.
     */
    public ParticleOptions getParticleData() {
        return particleData;
    }

    /**
     * Sets a new {@link ParticleOptions} that will be used in place of the original
     * particle type.
     *
     * @param particleData The new particle type to spawn.
     */
    public void setParticleData(ParticleOptions particleData) {
        this.particleData = particleData;
    }

    /**
     * Gets the X coordinate at which the particle will be spawned.
     *
     * @return The X coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the X coordinate at which the particle will be spawned.
     *
     * @param x The new X coordinate.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the Y coordinate at which the particle will be spawned.
     *
     * @return The Y coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the Y coordinate at which the particle will be spawned.
     *
     * @param y The new Y coordinate.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets the Z coordinate at which the particle will be spawned.
     *
     * @return The Z coordinate.
     */
    public double getZ() {
        return z;
    }

    /**
     * Sets the Z coordinate at which the particle will be spawned.
     *
     * @param z The new Z coordinate.
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Gets the motion speed along the X axis for the particle.
     *
     * @return The particle's X speed.
     */
    public double getXSpeed() {
        return xSpeed;
    }

    /**
     * Sets the motion speed along the X axis for the particle.
     *
     * @param xSpeed The new X speed.
     */
    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    /**
     * Gets the motion speed along the Y axis for the particle.
     *
     * @return The particle's Y speed.
     */
    public double getYSpeed() {
        return ySpeed;
    }

    /**
     * Sets the motion speed along the Y axis for the particle.
     *
     * @param ySpeed The new Y speed.
     */
    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    /**
     * Gets the motion speed along the Z axis for the particle.
     *
     * @return The particle's Z speed.
     */
    public double getZSpeed() {
        return zSpeed;
    }

    /**
     * Sets the motion speed along the Z axis for the particle.
     *
     * @param zSpeed The new Z speed.
     */
    public void setZSpeed(double zSpeed) {
        this.zSpeed = zSpeed;
    }
}