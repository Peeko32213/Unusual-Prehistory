package com.peeko32213.unusualprehistory;

import com.peeko32213.unusualprehistory.common.config.UnusualPrehistoryConfig;
import com.peeko32213.unusualprehistory.common.networking.UPMessages;
import com.peeko32213.unusualprehistory.common.world.feature.UPPlacedFeatures;
import com.peeko32213.unusualprehistory.core.events.ServerEvents;
import com.peeko32213.unusualprehistory.core.registry.*;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.peeko32213.unusualprehistory.core.registry.UPSignTypes.GINKGO;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(UnusualPrehistory.MODID)
public class UnusualPrehistory {
    public static final String MODID = "unusualprehistory";
    public static final List<Runnable> CALLBACKS = new ArrayList<>();
    public static final Logger LOGGER = LogManager.getLogger();
    public static CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public static SimpleChannel NETWORK;


    public UnusualPrehistory() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus eventBus = MinecraftForge.EVENT_BUS;
        modEventBus.addListener(this::commonSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, UnusualPrehistoryConfig.CONFIG_BUILDER);
        UPItems.ITEMS.register(modEventBus);
        UPBlocks.BLOCKS.register(modEventBus);
        UPBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        UPMenuTypes.MENUS.register(modEventBus);
        UPRecipes.SERIALIZERS.register(modEventBus);
        UPEntities.ENTITIES.register(modEventBus);
        UPFeatureModifiers.FOLIAGE_PLACERS.register(modEventBus);
        UPFeatureModifiers.PLACEMENT_MODIFIERS.register(modEventBus);
        UPSounds.DEF_REG.register(modEventBus);
        UPEffects.EFFECT_DEF_REG.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(new ServerEvents());
        eventBus.register(this);


    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            UPConfiguredFeatures.init();
            UPPlacedFeatures.init();
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(UPBlocks.HORSETAIL.getId(), UPBlocks.POTTED_HORSETAIL);
            Sheets.addWoodType(GINKGO);
        });

        UPMessages.register();
    }

    public static final CreativeModeTab DINO_TAB = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            return UPItems.AMMONITE_SHELL_ICON.get().getDefaultInstance();
        }
    };

}
