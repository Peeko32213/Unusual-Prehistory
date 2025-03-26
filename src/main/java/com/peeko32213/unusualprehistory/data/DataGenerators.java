package com.peeko32213.unusualprehistory.data;

import com.google.common.collect.Sets;
import com.peeko32213.unusualprehistory.data.client.LanguageGenerator;
import com.peeko32213.unusualprehistory.data.server.entitydata.EntityDataGenerator;
import com.peeko32213.unusualprehistory.data.server.entitydata.EntityGoalGenerator;
import com.peeko32213.unusualprehistory.data.server.loot.GlobalLootModifiersGenerator;
import com.peeko32213.unusualprehistory.data.server.loot.LootGenerator;
import com.peeko32213.unusualprehistory.data.client.models.BlockstateGenerator;
import com.peeko32213.unusualprehistory.data.client.models.ItemModelGenerator;
import com.peeko32213.unusualprehistory.data.server.recipes.UPRecipeGenerator;
import com.peeko32213.unusualprehistory.data.server.tags.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        if (event.includeServer()) registerServerProviders(event.getGenerator(), event);
        if (event.includeClient()) registerClientProviders(event.getGenerator(), event);
    }

    private static void registerClientProviders(DataGenerator generator, GatherDataEvent event) {
        ExistingFileHelper helper = event.getExistingFileHelper();
        PackOutput packOutput = generator.getPackOutput();
        generator.addProvider(true,new BlockstateGenerator(packOutput, helper));
        generator.addProvider(true,new ItemModelGenerator(packOutput, helper));
        generator.addProvider(true,new LanguageGenerator(packOutput));
    }

    private static void registerServerProviders(DataGenerator generator, GatherDataEvent event) {
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        Set<BlockStateGenerator> set = Sets.newHashSet();
        Consumer<BlockStateGenerator> consumer = set::add;
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(true,new UPEntityTagsProvider(packOutput, lookupProvider, helper));
        generator.addProvider(true,new UPPaintingTagsProvider(packOutput, lookupProvider, helper));
        generator.addProvider(true,new UPRecipeGenerator(packOutput));
        generator.addProvider(event.includeServer(), LootGenerator.create(packOutput));
        generator.addProvider(true,new UPBiomeTagsProvider(packOutput, lookupProvider, helper));
        generator.addProvider(true,new EntityGoalGenerator(packOutput));
        generator.addProvider(true,new UPInstrumentTagsProvider(packOutput, lookupProvider,helper));
        generator.addProvider(event.includeServer(), new EntityDataGenerator(packOutput));

//        generator.addProvider(true,new AdvancementProvider(packOutput, provider, helper));
//        generator.addProvider(event.includeServer(), new DamageTypeTagsGenerator(packOutput, lookupProvider, helper));

        generator.addProvider(event.includeServer(), new GlobalLootModifiersGenerator(packOutput));

        UPBlockTagsProvider blockTagGenerator = generator.addProvider(event.includeServer(),
                new UPBlockTagsProvider(packOutput, lookupProvider, helper));
        generator.addProvider(event.includeServer(), new UPItemTagsProvider(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), helper));

        DatapackBuiltinEntriesProvider datapackProvider = new RegistryDataGenerator(packOutput, lookupProvider);
        CompletableFuture<HolderLookup.Provider> customLookupProvider = datapackProvider.getRegistryProvider();
        generator.addProvider(event.includeServer(), datapackProvider);
    }

}
