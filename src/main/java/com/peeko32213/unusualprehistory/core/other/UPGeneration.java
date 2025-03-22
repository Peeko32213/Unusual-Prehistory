//package com.peeko32213.unusualprehistory.core.registry;
//
//import com.peeko32213.unusualprehistory.core.UnusualPrehistory;
//import net.minecraft.data.worldgen.BiomeDefaultFeatures;
//import net.minecraft.data.worldgen.placement.VegetationPlacements;
//import net.minecraft.world.level.biome.BiomeGenerationSettings;
//import net.minecraft.data.worldgen.biome.OverworldBiomes;
//import net.minecraft.world.level.levelgen.GenerationStep;
//import net.minecraftforge.fml.common.Mod;
//
//@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID)
//public class UPGeneration {
//
//    public static void petrifiedForest(BiomeGenerationSettings.Builder generation) {
//        OverworldBiomes.globalOverworldGeneration(generation);
//        BiomeDefaultFeatures.addDefaultOres(generation);
//        BiomeDefaultFeatures.addDefaultSoftDisks(generation);
//        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH_BADLANDS);
//        BiomeDefaultFeatures.addSavannaExtraGrass(generation);
//        BiomeDefaultFeatures.addDefaultMushrooms(generation);
//        BiomeDefaultFeatures.addDefaultExtraVegetation(generation);
//    }
//}
