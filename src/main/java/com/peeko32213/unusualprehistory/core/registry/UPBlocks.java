package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.block.BlockAnalyzer;
import com.peeko32213.unusualprehistory.common.block.BlockStethaEgg;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class UPBlocks {


    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            UnusualPrehistory.MODID);

    public static final RegistryObject<Block> STONE_FOSSIL = registerBlock("stone_fossil",
            () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));

    public static final RegistryObject<Block> DEEPSLATE_FOSSIL = registerBlock("deepslate_fossil",
            () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));

    public static final RegistryObject<Block> ANALYZER = registerBlock("analyzer",
            () -> new BlockAnalyzer(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> STETHA_EGGS = registerBlock("stetha_eggs",
            () -> new BlockStethaEgg(BlockBehaviour.Properties.copy(Blocks.TURTLE_EGG).instabreak().noOcclusion().noCollission().sound(SoundType.METAL)));

    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        UPItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));
        return block;
    }

    public static <B extends Block> RegistryObject<B> registerNoTabBlock(String name, Supplier<? extends B> supplier) {
        return BLOCKS.register(name, supplier);
    }



    }
