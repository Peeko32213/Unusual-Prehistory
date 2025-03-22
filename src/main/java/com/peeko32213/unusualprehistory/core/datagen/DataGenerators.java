package com.peeko32213.unusualprehistory.core.datagen;

import com.google.common.collect.Sets;
import com.peeko32213.unusualprehistory.core.datagen.client.UPLanguageGenerator;
import com.peeko32213.unusualprehistory.core.datagen.server.*;
import com.peeko32213.unusualprehistory.core.datagen.client.UPBlockstateGenerator;
import com.peeko32213.unusualprehistory.core.datagen.client.UPItemModelGenerator;
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

import static com.peeko32213.unusualprehistory.core.UnusualPrehistory.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        if (event.includeServer())
            registerServerProviders(event.getGenerator(), event);
    }

    private static void registerServerProviders(DataGenerator generator, GatherDataEvent event) {
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        Set<BlockStateGenerator> set = Sets.newHashSet();
        Consumer<BlockStateGenerator> consumer = set::add;
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(true,new UPEntityTagsGenerator(packOutput, lookupProvider, helper));

        generator.addProvider(true,new UPPaintingTagsProvider(packOutput, lookupProvider, helper));

        generator.addProvider(true,new UPRecipeGenerator(packOutput));

        generator.addProvider(true,new UPBlockstateGenerator(packOutput, helper));

        generator.addProvider(true,new UPItemModelGenerator(packOutput, helper));

        generator.addProvider(true,new UPLanguageGenerator(packOutput));

        generator.addProvider(event.includeServer(), UPLootGenerator.create(packOutput));

        generator.addProvider(true,new UPBiomeTagsProvider(packOutput, lookupProvider, helper));

        generator.addProvider(true,new UPEntityGoalGenerator(packOutput));

        generator.addProvider(true,new UPInstrumentTagsGenerator(packOutput, lookupProvider,helper));

        generator.addProvider(event.includeServer(), new UPEntityDataGenerator(packOutput));

//        generator.addProvider(true,new AdvancementProvider(packOutput, provider, helper));
//        generator.addProvider(evt.includeServer(), new DamageTypeTagsGenerator(packOutput, lookupProvider, helper));

        generator.addProvider(event.includeServer(), new UPGlobalLootModifiersGenerator(packOutput));

        UPBlockTagsGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
                new UPBlockTagsGenerator(packOutput, lookupProvider, helper));
        generator.addProvider(event.includeServer(), new UPItemTagsGenerator(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), helper));

        DatapackBuiltinEntriesProvider datapackProvider = new RegistryDataGenerator(packOutput, lookupProvider);
        CompletableFuture<HolderLookup.Provider> customLookupProvider = datapackProvider.getRegistryProvider();
        generator.addProvider(event.includeServer(), datapackProvider);


    }

}
