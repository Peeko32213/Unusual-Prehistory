package com.peeko32213.unusualprehistory;

import com.peeko32213.unusualprehistory.client.event.ClientEvents;
import com.peeko32213.unusualprehistory.common.capabilities.UPAnimalCapability;
import com.peeko32213.unusualprehistory.common.capabilities.UPCapabilities;
import com.peeko32213.unusualprehistory.common.capabilities.UPPlayerCapability;
import com.peeko32213.unusualprehistory.common.config.UnusualPrehistoryConfig;
import com.peeko32213.unusualprehistory.core.events.ServerEvents;
import com.peeko32213.unusualprehistory.core.registry.*;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

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
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, UnusualPrehistoryConfig.CONFIG_BUILDER);
        UPItems.ITEMS.register(modEventBus);
        UPBlocks.BLOCKS.register(modEventBus);
        UPTabs.TABS.register(modEventBus);
        UPFeatures.FEATURES.register(modEventBus);
        UPParticles.PARTICLE_TYPES.register(modEventBus);
        UPAdvancementTriggerRegistry.init();
        UPTrunkPlacerType.TRUNK_PLACER_TYPES.register(modEventBus);
        UPInstruments.INSTRUMENT.register(modEventBus);
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
        //If you want to debug comment these out otherwise it wont hotswap and also dont do anything with stuff that
        // triggers the capability class otherwise it also wont hotswap
       UPCapabilities.setupCapabilities();
       eventBus.addListener(UPPlayerCapability::onPlayerCloned);
       eventBus.addListener(UPPlayerCapability::onLivingDamage);
       eventBus.addListener(UPPlayerCapability::onPlayerJoinWorld);
       eventBus.addListener(UPAnimalCapability::tickAnimal);
       eventBus.addListener(UPAnimalCapability::tickWaterAnimal);
    }

    //Not sure if we need this but w/e this will give players a better reason as to why the mod isn't working when geckolib
    // isnt added
    public static void checkForGeckoLib(){
            if(ModList.get().isLoaded("geckolib")){
                LOGGER.debug("Geckolib loaded correctly!");
            } else {
                try {
                    LOGGER.debug("Geckolib3 version 3.1.39 and up didn't seem to be loaded!");
                    throw new Exception("Something went wrong setting up compat");
                }
                catch (Exception e) {
                        CrashReport crashreport = CrashReport.forThrowable(e, "Geckolib3 version 3.1.39 and up didn't seem to be loaded!");
                        crashreport.addCategory("Mod not loaded");
                        throw new ReportedException(crashreport);
                }
            }
    }




    private void commonSetup(final FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {
            UPEntityPlacement.entityPlacement();

            //Todo add this to own class
            addToFlowerPot(UPBlocks.HORSETAIL.getId(), UPBlocks.POTTED_HORSETAIL);
            addToFlowerPot(UPBlocks.LEEFRUCTUS.getId(), UPBlocks.POTTED_LEEFRUCTUS);
            addToFlowerPot(UPBlocks.BENNETTITALES.getId(), UPBlocks.POTTED_BENNETTITALES);
            addToFlowerPot(UPBlocks.ARCHAEOSIGILARIA.getId(), UPBlocks.POTTED_ARCHAEOSIGILARIA);
            addToFlowerPot(UPBlocks.PETRIFIED_BUSH.getId(), UPBlocks.POTTED_PETRIFIED_BUSH);
            addToFlowerPot(UPBlocks.SARACENIA.getId(), UPBlocks.POTTED_SARACENIA);
            addToFlowerPot(UPBlocks.GINKGO_SAPLING.getId(), UPBlocks.POTTED_GINKGO_SAPLING);

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

    public static void addToFlowerPot(ResourceLocation plantBlockLoc, Supplier<? extends Block> pottedPlantBlock){
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(plantBlockLoc,pottedPlantBlock);
    }
    public static void addToComposter(ItemLike item, float amountOfCompost){
        ComposterBlock.COMPOSTABLES.put(item, amountOfCompost);
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MODID, name.toLowerCase(Locale.ROOT));
    }

    public static MutableComponent getTranslation(String key, Object... args) {
        return Component.translatable("unusualprehistory." + key, args);
    }

    private void setupClient(FMLClientSetupEvent event) {
        PROXY.clientInit();
    }
}
