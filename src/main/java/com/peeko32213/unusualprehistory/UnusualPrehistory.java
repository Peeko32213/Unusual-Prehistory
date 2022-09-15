package com.peeko32213.unusualprehistory;

import com.peeko32213.unusualprehistory.client.event.ClientEvents;
import com.peeko32213.unusualprehistory.common.screen.AnalyzerScreen;
import com.peeko32213.unusualprehistory.common.world.feature.UPPlacedFeatures;
import com.peeko32213.unusualprehistory.core.event.WorldEvents;
import com.peeko32213.unusualprehistory.core.registry.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(UnusualPrehistory.MODID)
public class UnusualPrehistory {
    public static final String MODID = "unusualprehistory";
    public static final List<Runnable> CALLBACKS = new ArrayList<>();
    public static final Logger LOGGER = LogManager.getLogger();

    public UnusualPrehistory() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus eventBus = MinecraftForge.EVENT_BUS;
        modEventBus.addListener(this::commonSetup);

        UPItems.ITEMS.register(modEventBus);
        UPBlocks.BLOCKS.register(modEventBus);
        UPBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        UPMenuTypes.MENUS.register(modEventBus);
        UPRecipes.SERIALIZERS.register(modEventBus);
        UPEntities.ENTITIES.register(modEventBus);

        eventBus.register(this);
        eventBus.register(new WorldEvents());


    }



    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            UPConfiguredFeatures.init();
            UPPlacedFeatures.init();
        });
    }

    @Nonnull
    public Block retreiveBlock(ResourceLocation name) {
        final Optional<Block> block = ForgeRegistries.BLOCKS.getEntries().stream()
                .filter(entry -> entry.getKey().getRegistryName().equals(name)).map(Map.Entry::getValue).findFirst();
        return block.orElse(Blocks.AIR);
    }

    public static final CreativeModeTab DINO_TAB = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            return UPItems.AMMONITE_SHELL.get().getDefaultInstance();
        }
    };

}
