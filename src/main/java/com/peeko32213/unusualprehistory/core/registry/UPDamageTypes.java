package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.projectile.OpalescentShuriken;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.Nullable;

public class UPDamageTypes {

    public static final ResourceKey<DamageType> SHURIKEN = createKey("opalescent_shuriken");
    public static final ResourceKey<DamageType> TAR = createKey("tar_suffocation");

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(SHURIKEN, new DamageType("unusualprehistory.opalescent_shuriken", 0.1F));
        context.register(TAR, new DamageType("unusualprehistory.tar_suffocation", 0.1F));
    }

    public static DamageSource shuriken(Level level, OpalescentShuriken shuriken, @Nullable Entity indirectEntity) {
        return level.damageSources().source(SHURIKEN, shuriken, indirectEntity);
    }

    public static DamageSource tar(Level level) {
        return level.damageSources().source(TAR);
    }

    public static ResourceKey<DamageType> createKey(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(UnusualPrehistory.MODID, name));
    }

}