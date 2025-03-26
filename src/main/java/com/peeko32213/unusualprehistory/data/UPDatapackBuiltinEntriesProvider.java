package com.peeko32213.unusualprehistory.data;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPDamageTypes;
import com.peeko32213.unusualprehistory.core.registry.UPRegistry;
import com.peeko32213.unusualprehistory.core.registry.entities.UPPrehistoricEggRegistry;
import com.peeko32213.unusualprehistory.core.registry.world.UPBiomeSlices;
import com.peeko32213.unusualprehistory.core.registry.world.UPBiomes;
import com.teamabnormals.blueprint.core.registry.BlueprintDataPackRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class UPDatapackBuiltinEntriesProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, UPBiomes::bootstrap)
            .add(BlueprintDataPackRegistries.MODDED_BIOME_SLICES, UPBiomeSlices::bootstrap)
            .add(UPRegistry.Keys.PREHISTORIC_EGG, UPPrehistoricEggRegistry::bootstrap)
            .add(Registries.DAMAGE_TYPE, UPDamageTypes::bootstrap)
    ;

    public UPDatapackBuiltinEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, BUILDER, Set.of(UnusualPrehistory.MODID));
    }
}
