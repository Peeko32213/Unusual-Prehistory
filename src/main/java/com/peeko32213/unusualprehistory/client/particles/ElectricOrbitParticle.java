package com.peeko32213.unusualprehistory.client.particles;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ElectricOrbitParticle extends AbstractTrailParticle {

    private static final ResourceLocation TRAIL_TEXTURE = new ResourceLocation(UnusualPrehistory.MODID, "textures/particle/trail.png");
    protected double orbitX;
    protected double orbitY;
    protected double orbitZ;
    protected double orbitDistance;
    protected Vec3 orbitOffset;
    protected boolean reverseOrbit;
    protected int orbitAxis;

    protected float orbitSpeed = 1F;

    public ElectricOrbitParticle(ClientLevel world, double x, double y, double z, double xd, double yd, double zd) {
        super(world, x, y, z, 0, 0, 0);

        this.trailA = 0.8F;
        this.lifetime = 50 + this.random.nextInt(30);
        this.gravity = 0;
        this.orbitX = xd;
        this.orbitY = yd;
        this.orbitZ = zd;
        this.orbitDistance = 1F;
        orbitOffset = new Vec3(random.nextDouble() - 0.5F, random.nextDouble() - 0.5F, random.nextDouble() - 0.5F);
        this.reverseOrbit = random.nextBoolean();
        this.orbitAxis = random.nextInt(2);
        this.orbitSpeed = 1 + random.nextFloat() * 3F;
    }

    public Vec3 getOrbitPosition(float angle) {
        Vec3 center = new Vec3(orbitX, orbitY, orbitZ);
        Vec3 add = orbitOffset.scale(orbitDistance);
        float rot = angle * (reverseOrbit ? -orbitSpeed : orbitSpeed) * (float) (Math.PI / 180F);
        switch (orbitAxis) {
            case 0:
                add = add.xRot(rot);
                break;
            case 1:
                add = add.yRot(rot);
                break;
            case 2:
                add = add.zRot(rot);
                break;
        }
        return center.add(add);
    }

    public void tick() {
        Vec3 vec3 = getOrbitPosition(age);
        Vec3 movement = vec3.subtract(this.x, this.y, this.z).normalize().scale(orbitSpeed * 0.01F);
        this.xd += movement.x;
        this.yd += movement.y;
        this.zd += movement.z;
        float fade = 1F - age / (float) lifetime;
        this.trailA = 0.8F * fade;
        super.tick();
    }

    @Override
    public float getTrailHeight() {
        return 0.15F;
    }

    public int getLightColor(float f) {
        return 240;
    }

    @Override
    public ResourceLocation getTrailTexture() {
        return TRAIL_TEXTURE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class PillarFactory implements ParticleProvider<SimpleParticleType> {

        public PillarFactory() {
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            ElectricOrbitParticle particle = new ElectricOrbitParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.trailR = 0.1F + worldIn.random.nextFloat() * 1F;
            particle.trailG = 0.1F + worldIn.random.nextFloat() * 1F;
            particle.trailB = 0.8F + worldIn.random.nextFloat() * 1F;
            return particle;
        }
    }
}
