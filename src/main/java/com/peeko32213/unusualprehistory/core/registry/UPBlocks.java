package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.WaterLilyBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class UPBlocks {


    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            UnusualPrehistory.MODID);

    public static final RegistryObject<Block> STONE_FOSSIL = registerBlock("stone_fossil",
            () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> DEEPSLATE_FOSSIL = registerBlock("deepslate_fossil",
            () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE).strength(4.5F, 3.0F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> ANALYZER = registerBlock("analyzer",
            () -> new BlockAnalyzer(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final Supplier<Block> STETHA_EGGS = create("stetha_eggs",
            () -> new BlockStethaEggs(BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).instabreak().noDrops().noOcclusion().noCollission()),
            entry -> new WaterLilyBlockItem(entry.get(), new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final Supplier<Block> BEELZE_EGGS = create("beelze_eggs",
            () -> new BlockBeelzeEggs(BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).instabreak().noDrops().noOcclusion().noCollission()),
            entry -> new WaterLilyBlockItem(entry.get(), new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final Supplier<Block> AMON_EGGS = create("ammon_eggs",
            () -> new BlockAmmoniteEggs(BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).instabreak().noDrops().noOcclusion().noCollission()),
            entry -> new WaterLilyBlockItem(entry.get(), new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final Supplier<Block> DUNK_EGGS = create("dunk_eggs",
            () -> new BlockDunkEggs(BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS).instabreak().noDrops().noOcclusion().noCollission()),
            entry -> new WaterLilyBlockItem(entry.get(), new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));


    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        UPItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));
        return block;
    }

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block, CreativeModeTab tab) {
        return create(key, block, entry -> new BlockItem(entry.get(), new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));
    }

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block, Function<Supplier<T>, Item> item) {
        Supplier<T> entry = create(key, block);
        UPItems.ITEMS.register(key, () -> item.apply(entry));
        return entry;
    }

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block) {
        return BLOCKS.register(key, block);
    }


    }
