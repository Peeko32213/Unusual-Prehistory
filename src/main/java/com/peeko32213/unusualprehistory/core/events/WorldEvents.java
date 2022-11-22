package com.peeko32213.unusualprehistory.core.events;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.world.feature.UPPlacedFeatures;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WorldEvents {

    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {
        Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
        Biome.BiomeCategory category = event.getCategory();
        BiomeGenerationSettingsBuilder builder = event.getGeneration();

        if (category == Biome.BiomeCategory.NETHER || category == Biome.BiomeCategory.THEEND || category == Biome.BiomeCategory.NONE) return;


        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UPPlacedFeatures.STONE_FOSSIL_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UPPlacedFeatures.DEEPSLATE_FOSSIL_ORE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, UPPlacedFeatures.PLANT_FOSSIL_ORE);

        }

    }

