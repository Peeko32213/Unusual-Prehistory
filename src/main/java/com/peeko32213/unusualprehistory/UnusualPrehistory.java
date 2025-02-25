package com.peeko32213.unusualprehistory;

import com.peeko32213.unusualprehistory.client.event.ClientEvents;
import com.peeko32213.unusualprehistory.core.registry.util.UPLootModifiers;
import com.peeko32213.unusualprehistory.core.events.ServerEvents;
import com.peeko32213.unusualprehistory.core.registry.*;
import net.minecraft.ChatFormatting;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
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
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(UnusualPrehistory.MODID)
public class UnusualPrehistory {
    public static final String MODID = "unusualprehistory";
    private static int packetsRegistered;
    public static final List<Runnable> CALLBACKS = new ArrayList<>();
    public static final Logger LOGGER = LogManager.getLogger();
    //public static final SimpleChannel NETWORK_WRAPPER;
    public static CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public UnusualPrehistory() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus eventBus = MinecraftForge.EVENT_BUS;
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modEventBus.addListener(ClientEvents::init));
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::setupClient);
        modEventBus.addListener(this::packSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, UnusualPrehistoryConfig.COMMON);

        // Register stuff
        UPItems.ITEMS.register(modEventBus);
        UPBlocks.BLOCKS.register(modEventBus);
        UPTabs.TABS.register(modEventBus);
        UPFeatures.FEATURES.register(modEventBus);
        UPParticles.PARTICLE_TYPES.register(modEventBus);
        UPAdvancementTriggerRegistry.init();
        UPTrunkPlacerType.TRUNK_PLACER_TYPES.register(modEventBus);
        UPInstruments.INSTRUMENT.register(modEventBus);
        UPPaintings.PAINTING_VARIANTS.register(modEventBus);
        UPBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        UPMenuTypes.MENUS.register(modEventBus);
        UPRecipes.SERIALIZERS.register(modEventBus);
        UPEntities.ENTITIES.register(modEventBus);
        UPLootModifiers.LOOT_MODIFIERS.register(modEventBus);
        UPFeatureModifiers.FOLIAGE_PLACERS.register(modEventBus);
        UPFeatureModifiers.PLACEMENT_MODIFIERS.register(modEventBus);
        UPSounds.DEF_REG.register(modEventBus);
        UPEffects.EFFECT_DEF_REG.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(new ServerEvents());
        PROXY.init();
//       If you want to debug comment these out otherwise it wont hotswap and also dont do anything with stuff that
//       triggers the capability class otherwise it also wont hotswap
//       UPCapabilities.setupCapabilities();
//       eventBus.addListener(UPPlayerCapability::onPlayerCloned);
//       eventBus.addListener(UPPlayerCapability::onLivingDamage);
//       eventBus.addListener(UPPlayerCapability::onPlayerJoinWorld);
//       eventBus.addListener(UPAnimalCapability::tickAnimal);
//       eventBus.addListener(UPAnimalCapability::tickWaterAnimal);
    }

//    Not sure if we need this but w/e this will give players a better reason as to why the mod isn't working when geckolib isnt added
    public static void checkForGeckoLib(){
            if(ModList.get().isLoaded("geckolib")){
                LOGGER.debug("Geckolib loaded correctly!");
            } else {
                try {
                    LOGGER.debug("Geckolib3 version 1.20.1:4.2.4 and up didn't seem to be loaded!");
                    throw new Exception("Something went wrong setting up compat");
                }
                catch (Exception e) {
                        CrashReport crashreport = CrashReport.forThrowable(e, "Geckolib3 version 1.20.1:4.2.4 and up didn't seem to be loaded!");
                        crashreport.addCategory("Mod not loaded");
                        throw new ReportedException(crashreport);
                }
            }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {
            UPEntityPlacement.entityPlacement();

            //Todo add this to own class
            addToFlowerPot(UPBlocks.HORSETAIL, UPBlocks.POTTED_HORSETAIL);
            addToFlowerPot(UPBlocks.LEEFRUCTUS, UPBlocks.POTTED_LEEFRUCTUS);
            addToFlowerPot(UPBlocks.BENNETTITALES, UPBlocks.POTTED_BENNETTITALES);
            addToFlowerPot(UPBlocks.ARCHAEOSIGILARIA, UPBlocks.POTTED_ARCHAEOSIGILARIA);
            addToFlowerPot(UPBlocks.PETRIFIED_BUSH, UPBlocks.POTTED_PETRIFIED_BUSH);
            addToFlowerPot(UPBlocks.SARACENIA, UPBlocks.POTTED_SARACENIA);
            addToFlowerPot(UPBlocks.GINKGO_SAPLING, UPBlocks.POTTED_GINKGO_SAPLING);
            //addToFlowerPot(UPBlocks.FOXII_SAPLING.getId(), UPBlocks.POTTED_FOXXI);
            addToFlowerPot(UPBlocks.ZULOAGAE_SAPLING, UPBlocks.POTTED_ZULOGAE);
            addToFlowerPot(UPBlocks.DRYO_SAPLING, UPBlocks.POTTED_DRYO);

            //Todo add this to own class
            addToComposter(UPBlocks.HORSETAIL.get().asItem(), 0.4f);
            addToComposter(UPBlocks.TALL_HORSETAIL.get().asItem(), 0.8f);
            addToComposter(UPBlocks.LEEFRUCTUS.get().asItem(), 0.4f);
            addToComposter(UPBlocks.BENNETTITALES.get().asItem(), 0.4f);
            addToComposter(UPBlocks.ARCHAEOSIGILARIA.get().asItem(), 0.4f);
            addToComposter(UPBlocks.SARACENIA.get().asItem(), 0.4f);
            addToComposter(UPBlocks.TALL_SARACENIA.get().asItem(), 0.8f);
            addToComposter(UPBlocks.RAIGUENRAYUN.get().asItem(), 0.8f);
            addToComposter(UPBlocks.GINKGO_LEAVES.get().asItem(), 0.4f);
            addToComposter(UPBlocks.FOXXI_LEAVES.get().asItem(), 0.4f);
            addToComposter(UPBlocks.GINKGO_SAPLING.get().asItem(), 0.4f);
            addToComposter(UPBlocks.ARCHAEFRUCTUS.get().asItem(), 0.4f);
            addToComposter(UPBlocks.NELUMBITES.get().asItem(), 0.4f);
            addToComposter(UPBlocks.NELUMBITES.get().asItem(), 0.4f);
            addToComposter(UPBlocks.QUEREUXIA.get().asItem(), 0.2f);
            addToComposter(UPBlocks.QUEREUXIA_TOP.get().asItem(), 0.2f);
            addToComposter(UPBlocks.PETRIFIED_BUSH.get().asItem(), 0.2f);
            addToComposter(UPBlocks.ZULOAGAE.get().asItem(), 0.2f);
        });

        event.enqueueWork(UPDispenserRegistry::registerDispenserBehaviour);
        UPMessages.register();
    }

    public static void addToFlowerPot(RegistryObject<Block> plantBlockLoc, Supplier<? extends Block> pottedPlantBlock){
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(plantBlockLoc.getId(),pottedPlantBlock);
    }
    public static void addToComposter(ItemLike item, float amountOfCompost){
        ComposterBlock.COMPOSTABLES.put(item, amountOfCompost);
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MODID, name.toLowerCase(Locale.ROOT));
    }

    public static String prefixS(String name) {
        return new ResourceLocation(MODID, name.toLowerCase(Locale.ROOT)).toString();
    }

    public static MutableComponent getTranslation(String key, Object... args) {
        return Component.translatable("unusualprehistory." + key, args);
    }

    private void setupClient(FMLClientSetupEvent event) {
        PROXY.clientInit();
    }

    public void packSetup(AddPackFindersEvent event) {

        // Data Packs
        this.setupNaturalGenPack(event);

    }

    // Shoutout Aether

    private void setupNaturalGenPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            Path resourcePath = ModList.get().getModFileById(UnusualPrehistory.MODID).getFile().findResource("packs/natural_prehistoric_generation");
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
