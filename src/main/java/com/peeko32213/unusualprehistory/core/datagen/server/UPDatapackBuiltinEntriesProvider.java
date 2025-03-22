//package com.peeko32213.unusualprehistory.datagen.server;
//
//import com.peeko32213.unusualprehistory.core.UnusualPrehistory;
//import com.peeko32213.unusualprehistory.core.registry.UPDamageTypes;
//import com.peeko32213.unusualprehistory.core.registry.UPConfiguredFeatures;
//import com.peeko32213.unusualprehistory.core.registry.UPPlacedFeatures;
//import com.peeko32213.unusualprehistory.core.registry.builtin.UPBiomeModifiers;
//import com.peeko32213.unusualprehistory.core.registry.builtin.UPBiomeSlices;
//import com.peeko32213.unusualprehistory.core.registry.builtin.UPBiomes;
//import com.teamabnormals.blueprint.core.registry.BlueprintDataPackRegistries;
//import net.minecraft.core.RegistrySetBuilder;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.core.HolderLookup.Provider;
//import net.minecraft.data.PackOutput;
//import net.minecraftforge.registries.ForgeRegistries;
//import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
//
//import java.util.Set;
//import java.util.concurrent.CompletableFuture;
//
//public class UPDatapackBuiltinEntriesProvider extends DatapackBuiltinEntriesProvider{
//
//    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
//            .add(Registries.DAMAGE_TYPE, UPDamageTypes::bootstrap)
//            .add(Registries.CONFIGURED_FEATURE, UPConfiguredFeatures::bootstrap)
//            .add(Registries.PLACED_FEATURE, UPPlacedFeatures::bootstrap)
//            .add(Registries.BIOME, UPBiomes::bootstrap)
//            .add(BlueprintDataPackRegistries.MODDED_BIOME_SLICES, UPBiomeSlices::bootstrap)
//            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, UPBiomeModifiers::bootstrap);
//
//    public UPDatapackBuiltinEntriesProvider(PackOutput output, CompletableFuture<Provider> provider) {
//        super(output, provider, BUILDER, Set.of(UnusualPrehistory.MODID, "minecraft"));
//    }
//}
