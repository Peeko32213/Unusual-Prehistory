package com.peeko32213.unusualprehistory.data;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPDamageTypes;
import com.peeko32213.unusualprehistory.core.registry.entities.UPPrehistoricEggRegistry;
import com.peeko32213.unusualprehistory.core.registry.UPRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RegistryDataGenerator extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(UPRegistry.Keys.PREHISTORIC_EGG, UPPrehistoricEggRegistry::bootstrap)
            .add(Registries.DAMAGE_TYPE, UPDamageTypes::bootstrap)
        ;


    public RegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries,BUILDER, Set.of("minecraft", UnusualPrehistory.MODID));
    }
}