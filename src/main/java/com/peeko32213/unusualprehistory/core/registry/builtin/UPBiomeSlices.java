//package com.peeko32213.unusualprehistory.core.registry.builtin;
//
//import com.mojang.datafixers.util.Pair;
//import com.peeko32213.unusualprehistory.core.UnusualPrehistory;
//import com.teamabnormals.blueprint.common.world.modification.ModdedBiomeSlice;
//import com.teamabnormals.blueprint.core.registry.BlueprintDataPackRegistries;
//import com.teamabnormals.blueprint.core.util.BiomeUtil;
//import net.minecraft.core.Holder;
//import net.minecraft.core.HolderGetter;
//import net.minecraft.core.HolderSet;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.data.worldgen.BootstapContext;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.level.biome.Biome;
//import net.minecraft.world.level.biome.Biomes;
//import net.minecraft.world.level.biome.FixedBiomeSource;
//import net.minecraft.world.level.dimension.LevelStem;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//public class UPBiomeSlices {
//    public static final ResourceKey<ModdedBiomeSlice> PETRIFIED_FOREST = createKey("petrified_forest");
//
//    public static void bootstrap(BootstapContext<ModdedBiomeSlice> context) {
//        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
//        context.register(PETRIFIED_FOREST, new ModdedBiomeSlice(28,
//                new BiomeUtil.OverlayModdedBiomeProvider(List.of(
//                        Pair.of(HolderSet.direct(Stream.of(Biomes.BADLANDS, Biomes.ERODED_BADLANDS).map(biomes::getOrThrow
//                        ).collect(Collectors.toList())), new FixedBiomeSource(Holder.direct(biomes.getOrThrow(UPBiomes.PETRIFIED_FOREST)).get()))
//                )), LevelStem.OVERWORLD));
//    }
//
//    public static ResourceKey<ModdedBiomeSlice> createKey(String name) {
//        return ResourceKey.create(BlueprintDataPackRegistries.MODDED_BIOME_SLICES, new ResourceLocation(UnusualPrehistory.MODID, name));
//    }
//}
