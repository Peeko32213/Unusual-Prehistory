package com.peeko32213.unusualprehistory;

import com.peeko32213.unusualprehistory.common.networking.UPMessages;
import com.peeko32213.unusualprehistory.common.world.feature.UPPlacedFeatures;
import com.peeko32213.unusualprehistory.core.events.ServerEvents;
import com.peeko32213.unusualprehistory.core.events.WorldEvents;
import com.peeko32213.unusualprehistory.core.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.simple.SimpleChannel;
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
    public static CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public static SimpleChannel NETWORK;


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
        UPFeatureModifiers.FOLIAGE_PLACERS.register(modEventBus);
        UPFeatureModifiers.PLACEMENT_MODIFIERS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(new ServerEvents());
        eventBus.register(this);
        eventBus.register(new WorldEvents());


    }


    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            UPConfiguredFeatures.init();
            UPPlacedFeatures.init();
        });

        UPMessages.register();
    }

    @Nonnull
    public Block retreiveBlock(ResourceLocation name) {
        final Optional<Block> block = ForgeRegistries.BLOCKS.getEntries().stream()
                .filter(entry -> entry.getKey().getRegistryName().equals(name)).map(Map.Entry::getValue).findFirst();
        return block.orElse(Blocks.AIR);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(UPBlocks.HORSETAIL.getId(), UPBlocks.POTTED_HORSETAIL);
        });
    }

    public static final CreativeModeTab DINO_TAB = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            return UPItems.AMMONITE_SHELL_ICON.get().getDefaultInstance();
        }
    };

}
