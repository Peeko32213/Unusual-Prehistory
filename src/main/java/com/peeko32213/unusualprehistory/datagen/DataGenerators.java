package com.peeko32213.unusualprehistory.datagen;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import com.peeko32213.unusualprehistory.core.registry.UPConfiguredFeatures;
import com.peeko32213.unusualprehistory.core.registry.UPPlacedFeatures;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent evt) {
        if (evt.includeServer())
            registerServerProviders(evt.getGenerator(), evt);

        if (evt.includeClient())
            registerClientProviders(evt.getGenerator(), evt);


    }

    private static void registerClientProviders(DataGenerator generator, GatherDataEvent evt) {
        ExistingFileHelper helper = evt.getExistingFileHelper();
        generator.addProvider(true,new BlockTagsGenerator(generator, helper));
    }

    private static void registerServerProviders(DataGenerator generator, GatherDataEvent evt) {
        ExistingFileHelper helper = evt.getExistingFileHelper();
        BlockTagsGenerator blockTags = new BlockTagsGenerator(generator, helper);
        Set<BlockStateGenerator> set = Sets.newHashSet();
        Consumer<BlockStateGenerator> consumer = set::add;
        generator.addProvider(true,new EntityTagsGenerator(generator, helper));
        generator.addProvider(true,new RecipeGenerator(generator));
        generator.addProvider(true,new BlockstateGenerator(generator, helper));
        generator.addProvider(true,new ItemTagsGenerator(generator, blockTags, helper));
        generator.addProvider(true,new ItemModelGenerator(generator, helper));
        generator.addProvider(true,new LanguageGenerator(generator));
        generator.addProvider(true,new LootGenerator(generator));
        generator.addProvider(true,new BiomeTagsProvider(generator, helper));
        generator.addProvider(true,new InstrumentTagsGenerator(generator, helper));
        generator.addProvider(true,new AdvancementGenerator(generator, helper));

        final RegistryAccess registries = RegistryAccess.builtinCopy();
        final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, registries);
        final DataProvider configuredFeatureProvider = JsonCodecProvider.forDatapackRegistry(generator, helper, MODID, ops, Registry.CONFIGURED_FEATURE_REGISTRY, getConfiguredFeatures(registries));
        generator.addProvider(true, configuredFeatureProvider);

        final DataProvider placedFeatureProvider = JsonCodecProvider.forDatapackRegistry(generator, helper, MODID, ops, Registry.PLACED_FEATURE_REGISTRY, getPlacedFeatures(registries));
        generator.addProvider(true, placedFeatureProvider);
    }

    public static Map<ResourceLocation, ConfiguredFeature<?, ?>> getConfiguredFeatures(RegistryAccess registries) {
        Map<ResourceLocation, ConfiguredFeature<?, ?>> map = new HashMap<>();

        for (int i = 0; i < UPConfiguredFeatures.configuredFeatureList.size(); i++) {
            ResourceLocation RL = new ResourceLocation(MODID, UPConfiguredFeatures.configuredFeatureList.get(i));
            Registry<ConfiguredFeature<?, ?>> configuredFeatures = registries.registryOrThrow(Registry.CONFIGURED_FEATURE_REGISTRY);
            ConfiguredFeature<?, ?> PF = configuredFeatures.get(RL);
            map.put(RL, PF);
        }

        return map;
    }

    public static Map<ResourceLocation, PlacedFeature> getPlacedFeatures(RegistryAccess registries) {
        Map<ResourceLocation, PlacedFeature> map = new HashMap<>();

        for (int i = 0; i < UPPlacedFeatures.placedFeatureList.size(); i++) {
            ResourceLocation RL = new ResourceLocation(MODID, UPPlacedFeatures.placedFeatureList.get(i));
            Registry<PlacedFeature> placedFeatures = registries.registryOrThrow(Registry.PLACED_FEATURE_REGISTRY);
            PlacedFeature PF = placedFeatures.get(RL);
            map.put(RL, PF);
        }

        return map;
    }
}
