package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UPParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, UnusualPrehistory.MODID);
    public static final RegistryObject<SimpleParticleType> TAR_BUBBLE = PARTICLE_TYPES.register("tar_bubble", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ELECTRIC_ORBIT = PARTICLE_TYPES.register("electric_orbit", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ELECTRIC_ATTACK = PARTICLE_TYPES.register("electric_attack", () -> new SimpleParticleType(false));
}
