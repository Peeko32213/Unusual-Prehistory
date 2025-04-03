package com.peeko32213.unusualprehistory;

import com.peeko32213.unusualprehistory.client.event.ClientEvents;
import com.peeko32213.unusualprehistory.common.capabilities.UPAnimalCapability;
import com.peeko32213.unusualprehistory.common.capabilities.UPCapabilities;
import com.peeko32213.unusualprehistory.common.capabilities.UPEntityCapability;
import com.peeko32213.unusualprehistory.common.capabilities.UPPlayerCapability;
import com.peeko32213.unusualprehistory.common.data.PrehistoricEgg;
import com.peeko32213.unusualprehistory.common.data.attack.EntityAttack;
import com.peeko32213.unusualprehistory.common.data.entity.synced.SerializableSynchedDataRegistry;
import com.peeko32213.unusualprehistory.core.other.UPTabs;
import com.peeko32213.unusualprehistory.core.registry.blocks.UPBlockEntities;
import com.peeko32213.unusualprehistory.core.registry.blocks.UPBlockSubRegistryHelper;
import com.peeko32213.unusualprehistory.core.registry.blocks.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.entities.*;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import com.peeko32213.unusualprehistory.core.registry.items.UPRecipes;
import com.peeko32213.unusualprehistory.core.other.util.UPLootModifiers;
import com.peeko32213.unusualprehistory.core.events.ServerEvents;
import com.peeko32213.unusualprehistory.core.registry.*;
import com.peeko32213.unusualprehistory.core.registry.world.*;
import com.peeko32213.unusualprehistory.data.client.UPLanguageGenerator;
import com.peeko32213.unusualprehistory.data.client.models.UPBlockstateGenerator;
import com.peeko32213.unusualprehistory.data.client.models.UPItemModelGenerator;
import com.peeko32213.unusualprehistory.data.UPDatapackBuiltinEntriesProvider;
import com.peeko32213.unusualprehistory.data.server.entitydata.UPEntityDataGenerator;
import com.peeko32213.unusualprehistory.data.server.entitydata.UPEntityGoalGenerator;
import com.peeko32213.unusualprehistory.data.server.loot.UPGlobalLootModifiersProvider;
import com.peeko32213.unusualprehistory.data.server.loot.UPLootProvider;
import com.peeko32213.unusualprehistory.data.server.recipes.UPRecipeGenerator;
import com.peeko32213.unusualprehistory.data.server.tags.*;
import com.teamabnormals.blueprint.core.Blueprint;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DataPackRegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;

@Mod(UnusualPrehistory.MODID)
@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID)
public class UnusualPrehistory {

    public static final String MODID = "unusualprehistory";
    private static int packetsRegistered;
    public static final Logger LOGGER = LogManager.getLogger();
    //public static final SimpleChannel NETWORK_WRAPPER;
    public static CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    public static final RegistryHelper REGISTRY_HELPER = RegistryHelper.create(MODID, helper -> helper.putSubHelper(ForgeRegistries.BLOCKS, new UPBlockSubRegistryHelper(helper)));

    public static ResourceLocation modPrefix(String name) {
        return new ResourceLocation(UnusualPrehistory.MODID, name.toLowerCase(Locale.ROOT));
    }

    public static ResourceLocation blueprintPrefix(String name) {
        return new ResourceLocation(Blueprint.MOD_ID, name.toLowerCase(Locale.ROOT));
    }

    public UnusualPrehistory() {

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext context = ModLoadingContext.get();

        MinecraftForge.EVENT_BUS.register(new ServerEvents());
        PROXY.init();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> bus.addListener(ClientEvents::init));

        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);
        bus.addListener(this::dataSetup);
        bus.addListener(this::packSetup);

        context.registerConfig(ModConfig.Type.COMMON, UnusualPrehistoryConfig.COMMON);

        EntityActionsRegistry.register();
        REGISTRY_HELPER.register(bus);

        // Register stuff
        UPItems.ITEMS.register(bus);
        UPBlocks.BLOCKS.register(bus);
        UPTabs.TABS.register(bus);
        UPFeatures.FEATURES.register(bus);
        UPParticles.PARTICLE_TYPES.register(bus);
        UPAdvancementTriggerRegistry.init();
        UPTrunkPlacerType.TRUNK_PLACER_TYPES.register(bus);
        UPInstruments.INSTRUMENT.register(bus);
        UPPaintings.PAINTING_VARIANTS.register(bus);
        UPBlockEntities.BLOCK_ENTITIES.register(bus);
        UPMenuTypes.MENUS.register(bus);
        UPRecipes.SERIALIZERS.register(bus);
        UPEntities.ENTITIES.register(bus);
        UPLootModifiers.LOOT_MODIFIERS.register(bus);
        UPFeatureModifiers.FOLIAGE_PLACERS.register(bus);
        UPFeatureModifiers.PLACEMENT_MODIFIERS.register(bus);
        UPSounds.DEF_REG.register(bus);
        UPEffects.EFFECT_DEF_REG.register(bus);
        UPRecipes.TYPE_DEF_REG.register(bus);

        // Structures
        UPStructures.STRUCTURE_TYPES.register(bus);
        UPStructureProcessors.STRUCTURE_PROCESSORS.register(bus);

        //register custom registry
        UPRegistry.ENTITY_ATTACKS_TYPE_SERIALIZER.register(bus);
        UPAttackRegistry.ENTITY_ATTACK.register(bus);


        //Register goalsmith goals
        UPGoalRegistry.GOAL_TYPE_SERIALIZER.register(bus);
        UPSpawnPredicateRegistry.PREDICATE_SERIALIZER.register(bus);

        bus.addListener((DataPackRegistryEvent.NewRegistry event) -> {
            event.dataPackRegistry(UPRegistry.Keys.PREHISTORIC_EGG, PrehistoricEgg.CODEC);
            event.dataPackRegistry(UPRegistry.Keys.ENTITY_ATTACKS, EntityAttack.DIRECT_CODEC);
        });


//       If you want to debug comment these out otherwise it wont hotswap and also dont do anything with stuff that
//       triggers the capability class otherwise it also wont hotswap
        UPCapabilities.setupCapabilities();
        //bus.addListener(UPPlayerCapability::onPlayerCloned);
        //bus.addListener(UPPlayerCapability::onLivingDamage);
        //bus.addListener(UPPlayerCapability::onPlayerJoinWorld);
        //bus.addListener(UPAnimalCapability::tickAnimal);
        //bus.addListener(UPAnimalCapability::tickWaterAnimal);


    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            UPEntityPlacement.entityPlacement();
            SerializableSynchedDataRegistry.register();
        });
        UPMessages.register();
    }

    private void clientSetup(FMLClientSetupEvent event) {
        PROXY.clientInit();
    }

    private void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        boolean server = event.includeServer();
        boolean client = event.includeClient();

        UPDatapackBuiltinEntriesProvider datapackEntries = new UPDatapackBuiltinEntriesProvider(output, provider);
        generator.addProvider(server, datapackEntries);
        provider = datapackEntries.getRegistryProvider();

        // Server generators
        UPBlockTagsProvider blockTags = new UPBlockTagsProvider(output, provider, helper);
        generator.addProvider(server, blockTags);
        generator.addProvider(server, new UPItemTagsProvider(output, provider, blockTags.contentsGetter(), helper));
        generator.addProvider(server, new UPEntityTagsProvider(output, provider, helper));
        generator.addProvider(server, new UPBiomeTagsProvider(output, provider, helper));
        generator.addProvider(server, new UPRecipeGenerator(output));
        generator.addProvider(server, new UPPaintingTagsProvider(output, provider, helper));
        generator.addProvider(server, UPLootProvider.create(output));
        generator.addProvider(server, new UPEntityGoalGenerator(output));
        generator.addProvider(server, new UPInstrumentTagsProvider(output, provider,helper));
        generator.addProvider(server, new UPEntityDataGenerator(output));
        generator.addProvider(server, new UPGlobalLootModifiersProvider(output));
//        generator.addProvider(true,new AdvancementProvider(packOutput, provider, helper));
//        generator.addProvider(event.includeServer(), new DamageTypeTagsGenerator(packOutput, lookupProvider, helper));

        // Client generators
        generator.addProvider(client, new UPBlockstateGenerator(output, helper));
        generator.addProvider(client, new UPItemModelGenerator(output, helper));
        generator.addProvider(client, new UPLanguageGenerator(output));
    }

    // Credit: The Aether - https://github.com/The-Aether-Team/The-Aether/blob/1.20.1-develop/src/main/java/com/aetherteam/aether/Aether.java
    public void packSetup(AddPackFindersEvent event) {
        this.setupNaturalSpawnPack(event);
        this.setupNaturalGenPack(event);
        this.setupNoFossilsPack(event);
    }

    private void setupNaturalSpawnPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            Path resourcePath = ModList.get().getModFileById(UnusualPrehistory.MODID).getFile().findResource("builtin/natural_prehistoric_mob_spawns");
            PathPackResources pack = new PathPackResources(ModList.get().getModFileById(UnusualPrehistory.MODID).getFile().getFileName() + ":" + resourcePath, resourcePath, true);
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.unusualprehistory.natural_prehistoric_mob_spawns.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
            event.addRepositorySource((source) ->
                    source.accept(Pack.create(
                            "builtin/natural_prehistoric_mob_spawns",
                            Component.translatable("pack.unusualprehistory.natural_prehistoric_mob_spawns.title"),
                            false,
                            (string) -> pack,
                            new Pack.Info(metadata.getDescription(), metadata.getPackFormat(PackType.SERVER_DATA), metadata.getPackFormat(PackType.CLIENT_RESOURCES), FeatureFlagSet.of(), pack.isHidden()),
                            PackType.SERVER_DATA,
                            Pack.Position.TOP,
                            false,
                            create(decorateWithSource(), UnusualPrehistoryConfig.NATURAL_PREHISTORIC_MOB_SPAWNS.get()))
                    )
            );
        }
    }

    private void setupNaturalGenPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            Path resourcePath = ModList.get().getModFileById(UnusualPrehistory.MODID).getFile().findResource("builtin/natural_prehistoric_generation");
            PathPackResources pack = new PathPackResources(ModList.get().getModFileById(UnusualPrehistory.MODID).getFile().getFileName() + ":" + resourcePath, resourcePath, true);
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.unusualprehistory.natural_prehistoric_generation.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
            event.addRepositorySource((source) ->
                source.accept(Pack.create(
                    "builtin/natural_prehistoric_generation",
                    Component.translatable("pack.unusualprehistory.natural_prehistoric_generation.title"),
                    false,
                    (string) -> pack,
                    new Pack.Info(metadata.getDescription(), metadata.getPackFormat(PackType.SERVER_DATA), metadata.getPackFormat(PackType.CLIENT_RESOURCES), FeatureFlagSet.of(), pack.isHidden()),
                    PackType.SERVER_DATA,
                    Pack.Position.TOP,
                    false,
                    create(decorateWithSource(), UnusualPrehistoryConfig.NATURAL_PREHISTORIC_GENERATION.get()))
                )
            );
        }
    }

    private void setupNoFossilsPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            Path resourcePath = ModList.get().getModFileById(UnusualPrehistory.MODID).getFile().findResource("builtin/no_fossils");
            PathPackResources pack = new PathPackResources(ModList.get().getModFileById(UnusualPrehistory.MODID).getFile().getFileName() + ":" + resourcePath, resourcePath, true);
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.unusualprehistory.no_fossils.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
            event.addRepositorySource((source) ->
                    source.accept(Pack.create(
                            "builtin/no_fossils",
                            Component.translatable("pack.unusualprehistory.no_fossils.title"),
                            false,
                            (string) -> pack,
                            new Pack.Info(metadata.getDescription(), metadata.getPackFormat(PackType.SERVER_DATA), metadata.getPackFormat(PackType.CLIENT_RESOURCES), FeatureFlagSet.of(), pack.isHidden()),
                            PackType.SERVER_DATA,
                            Pack.Position.TOP,
                            false,
                            create(decorateWithSource(), UnusualPrehistoryConfig.NO_FOSSILS.get()))
                    )
            );
        }
    }

    static PackSource create(final UnaryOperator<Component> decorator, final boolean shouldAddAutomatically) {
        return new PackSource() {
            public @NotNull Component decorate(@NotNull Component component) {
                return decorator.apply(component);
            }

            public boolean shouldAddAutomatically() {
                return shouldAddAutomatically;
            }
        };
    }

    private static UnaryOperator<Component> decorateWithSource() {
        Component component = Component.translatable("pack.source.builtin");
        return (name) -> Component.translatable("pack.nameAndSource", name, component).withStyle(ChatFormatting.GRAY);
    }
}
