package com.peeko32213.unusualprehistory.datagen;

import com.google.common.collect.Sets;
import com.peeko32213.unusualprehistory.datagen.advancements.AdvancementProvider;
import com.peeko32213.unusualprehistory.datagen.loot.GlobalLootModifiersGenerator;
import com.peeko32213.unusualprehistory.datagen.loot.LootGenerator;
import com.peeko32213.unusualprehistory.datagen.tags.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
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
    public static void gatherData(GatherDataEvent evt) {
        if (evt.includeServer())
            registerServerProviders(evt.getGenerator(), evt);

    }
    private static void registerServerProviders(DataGenerator generator, GatherDataEvent evt) {
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper helper = evt.getExistingFileHelper();
        Set<BlockStateGenerator> set = Sets.newHashSet();
        Consumer<BlockStateGenerator> consumer = set::add;
        CompletableFuture<HolderLookup.Provider> provider = evt.getLookupProvider();
        CompletableFuture<HolderLookup.Provider> lookupProvider = evt.getLookupProvider();

        generator.addProvider(true,new EntityTagsGenerator(packOutput, lookupProvider, helper));

        generator.addProvider(true,new PaintingTagsProvider(packOutput, lookupProvider, helper));

        generator.addProvider(true,new RecipeGenerator(packOutput));

        generator.addProvider(true,new BlockstateGenerator(packOutput, helper));

        generator.addProvider(true,new ItemModelGenerator(packOutput, helper));

        generator.addProvider(true,new LanguageGenerator(packOutput));

        generator.addProvider(evt.includeServer(), LootGenerator.create(packOutput));

        generator.addProvider(true,new BiomeTagsProvider(packOutput, lookupProvider, helper));

        generator.addProvider(true,new InstrumentTagsGenerator(packOutput, lookupProvider,helper));

        generator.addProvider(true,new AdvancementProvider(packOutput, provider, helper));

        generator.addProvider(evt.includeServer(), new GlobalLootModifiersGenerator(packOutput));

        BlockTagsGenerator blockTagGenerator = generator.addProvider(evt.includeServer(),
                new BlockTagsGenerator(packOutput, lookupProvider, helper));
        generator.addProvider(evt.includeServer(), new ItemTagsGenerator(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), helper));
    }

}
