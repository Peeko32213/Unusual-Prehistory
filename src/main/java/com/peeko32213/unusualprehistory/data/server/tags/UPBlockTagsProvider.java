package com.peeko32213.unusualprehistory.data.server.tags;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.other.tags.UPBlockTags;
import com.peeko32213.unusualprehistory.core.registry.blocks.UPBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class UPBlockTagsProvider extends BlockTagsProvider {
    public UPBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, UnusualPrehistory.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {

        tag(UPBlockTags.DINO_NATURAL_SPAWNABLE)
        ;


        tag(UPBlockTags.ANGRY_BRACHI_BREAKABLES)
                .addTag(BlockTags.LEAVES)
                .addTag(BlockTags.PLANKS)
                .addTag(BlockTags.LOGS)
                .addTag(BlockTags.WOODEN_STAIRS)
                .addTag(BlockTags.WOODEN_SLABS)
                .addTag(BlockTags.WOOL)
                .add(Blocks.LILY_PAD)
                .add(Blocks.CACTUS)
                .add(Blocks.BAMBOO);

        tag(UPBlockTags.PASSIVE_BRACHI_BREAKABLES)
                ;

        tag(UPBlockTags.REX_BREAKABLES)
                .addTag(BlockTags.LEAVES)
                .addTag(BlockTags.PLANKS)
                .addTag(BlockTags.LOGS)
                .addTag(BlockTags.WOODEN_STAIRS)
                .addTag(BlockTags.WOODEN_SLABS)
                .addTag(BlockTags.WOOL)
                .add(Blocks.LILY_PAD)
                .add(Blocks.CACTUS)
                .add(Blocks.BAMBOO);

        tag(UPBlockTags.TRIKE_BREAKABLES)
                .addTag(BlockTags.LEAVES)
                .addTag(BlockTags.PLANKS)
                .addTag(BlockTags.LOGS)
                .addTag(BlockTags.WOODEN_STAIRS)
                .addTag(BlockTags.WOODEN_SLABS)
                .addTag(BlockTags.WOOL)
                .add(Blocks.LILY_PAD)
                .add(Blocks.CACTUS)
                .add(Blocks.BAMBOO);

        tag(UPBlockTags.TYRANNO_BREAKABLES)
                .addTag(BlockTags.LEAVES)
                .addTag(BlockTags.PLANKS)
                .addTag(BlockTags.LOGS)
                .addTag(BlockTags.WOODEN_STAIRS)
                .addTag(BlockTags.WOODEN_SLABS)
                .addTag(BlockTags.WOOL)
                .add(Blocks.LILY_PAD)
                .add(Blocks.CACTUS)
                .add(Blocks.BAMBOO)
        ;

        tag(UPBlockTags.DIPLO_DIGS)
                .addTag(BlockTags.SAND)
                .add(Blocks.MUD)
        ;

        tag(UPBlockTags.TRIKE_GRAZING_BLOCKS)
                .add(Blocks.GRASS_BLOCK)
                .add(Blocks.FERN)
                .add(Blocks.LARGE_FERN)
                .add(Blocks.GRASS)
                .add(UPBlocks.HORSETAIL.get())
                .add(UPBlocks.TALL_HORSETAIL.get())
        ;

        tag(UPBlockTags.COTY_GRAZING_BLOCKS)
                .add(Blocks.GRASS_BLOCK)
                .add(Blocks.FERN)
                .add(Blocks.LARGE_FERN)
                .add(Blocks.GRASS)
                .add(Blocks.MELON)
                .add(UPBlocks.HORSETAIL.get())
                .add(UPBlocks.TALL_HORSETAIL.get())
        ;

        tag(UPBlockTags.MEGATHERIUM_EATABLES)
                .addTag(BlockTags.LEAVES);

        tag(UPBlockTags.MEGATHERIUM_MINEABLES)
                .addTag(BlockTags.LEAVES)
                .addTag(BlockTags.DIRT)
                .addTag(BlockTags.SAND)
                .addTag(BlockTags.PLANKS)
                .addTag(BlockTags.LOGS)
                .addTag(BlockTags.WOODEN_STAIRS)
                .addTag(BlockTags.WOODEN_SLABS)
                .add(Blocks.SNOW)
                .add(Blocks.GRASS)
                .add(Blocks.TALL_GRASS)
                .add(Blocks.DEAD_BUSH)
                .add(Blocks.SNOW_BLOCK)
                .add(Blocks.SNOW_BLOCK)
                .add(Blocks.SNOW_BLOCK)
                .add(Blocks.POWDER_SNOW);

        tag(UPBlockTags.ERYON_DIGGABLES)
                .addTag(BlockTags.SAND);

        tag(UPBlockTags.TALPANAS_DIGGABLES)
                .add(Blocks.ROOTED_DIRT);

        tag(UPBlockTags.DINO_HATCHABLE_BLOCKS)
                .addTag(BlockTags.SAND)
                .addTag(BlockTags.DIRT)
                .addTag(BlockTags.LOGS)
                .addTag(BlockTags.LEAVES)
                .add(Blocks.GRASS_BLOCK)
                .add(Blocks.PODZOL)
                .add(Blocks.MOSS_BLOCK)
                .add(Blocks.MYCELIUM)
                .addTag(BlockTags.BASE_STONE_OVERWORLD)
                .add(Blocks.GRAVEL)
                .add(Blocks.HAY_BLOCK);


        tag(BlockTags.FENCE_GATES)
                .add(UPBlocks.DRYO_FENCE_GATE.get());

        tag(BlockTags.FENCES)
                .add(UPBlocks.DRYO_FENCE.get());

        tag(BlockTags.LOGS)
                .add(UPBlocks.PETRIFIED_WOOD.get())
                .add(UPBlocks.STRIPPED_PETRIFIED_WOOD.get())
                .add(UPBlocks.DRYO_LOG.get())
                .add(UPBlocks.DRYO_WOOD.get())
                .add(UPBlocks.STRIPPED_DRYO_WOOD.get())
                .add(UPBlocks.STRIPPED_DRYO_LOG.get())
                .add(UPBlocks.ZULOAGAE_BLOCK.get())
                ;

        tag(BlockTags.LOGS_THAT_BURN)
                .add(UPBlocks.PETRIFIED_WOOD.get())
                .add(UPBlocks.STRIPPED_PETRIFIED_WOOD.get())
                .add(UPBlocks.DRYO_LOG.get())
                .add(UPBlocks.DRYO_WOOD.get())
                .add(UPBlocks.STRIPPED_DRYO_WOOD.get())
                .add(UPBlocks.STRIPPED_DRYO_LOG.get())
                .add(UPBlocks.ZULOAGAE_BLOCK.get())

        ;

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(UPBlocks.DEEPSLATE_FOSSIL.get())
                .add(UPBlocks.DEEPSLATE_AMBER_FOSSIL.get())
                .add(UPBlocks.DEEPSLATE_PLANT_FOSSIL.get())
                .add(UPBlocks.STONE_FOSSIL.get())
                .add(UPBlocks.STONE_AMBER_FOSSIL.get())
                .add(UPBlocks.ANALYZER.get())
                .add(UPBlocks.CULTIVATOR.get())
                .add(UPBlocks.INCUBATOR.get())
                .add(UPBlocks.AMMONITE_SHELL.get())
                .add(UPBlocks.PLANT_FOSSIL.get())
                .add(UPBlocks.DNA_FRIDGE.get())
                .add(UPBlocks.CLATHRODICTYON_BLOCK.get())
                .add(UPBlocks.ANOSTYLOSTROMA_BLOCK.get())
                .add(UPBlocks.PETRIFIED_WOOD.get())
                .add(UPBlocks.STRIPPED_PETRIFIED_WOOD.get())
                .add(UPBlocks.AUSTRO_FOSSIL.get())
                .add(UPBlocks.ULUGH_FOSSIL.get())
                .add(UPBlocks.KENTRO_FOSSIL.get())
                .add(UPBlocks.ANTARCTO_FOSSIL.get())
                .add(UPBlocks.HWACHA_FOSSIL.get())
                .add(UPBlocks.ERYON_FOSSIL.get())
                .add(UPBlocks.VELOCI_FOSSIL.get())
                .add(UPBlocks.PACHY_FOSSIL.get())
                .add(UPBlocks.MAJUNGA_FOSSIL.get())
                .add(UPBlocks.DUNK_FOSSIL.get())
                .add(UPBlocks.BRACHI_FOSSIL.get())
                .add(UPBlocks.BEELZE_FOSSIL.get())
                .add(UPBlocks.SCAU_FOSSIL.get())
                .add(UPBlocks.COTY_FOSSIL.get())
                .add(UPBlocks.STETHA_FOSSIL.get())
                .add(UPBlocks.ANURO_FOSSIL.get())
                .add(UPBlocks.OPAL_ORE.get())
                .add(UPBlocks.DEEPSLATE_OPAL_ORE.get())
                .add(UPBlocks.FIRE_OPAL_ORE.get())
                .add(UPBlocks.DEEPSLATE_FIRE_OPAL_ORE.get())
                .add(UPBlocks.BOULDER_OPAL_ORE.get())
                .add(UPBlocks.DEEPSLATE_BOULDER_OPAL_ORE.get())
                .add(UPBlocks.BLACK_OPAL_ORE.get())
                .add(UPBlocks.DEEPSLATE_BLACK_OPAL_ORE.get())
                .add(UPBlocks.STONE_TAR_FOSSIL.get())
                .add(UPBlocks.DEEPSLATE_TAR_FOSSIL.get())
                .add(UPBlocks.PERMAFROST_FOSSIL.get())
                .add(UPBlocks.PERMAFROST.get())
                .add(UPBlocks.OPAL_BLOCK.get())
                .add(UPBlocks.FIRE_OPAL_BLOCK.get())
                .add(UPBlocks.BOULDER_OPAL_BLOCK.get())
                .add(UPBlocks.BLACK_OPAL_BLOCK.get())
                .add(UPBlocks.AMBER_BLOCK.get())

        ;

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(UPBlocks.REX_BOOMBOX.get())
                .add(UPBlocks.PETRIFIED_WOOD.get())
                .add(UPBlocks.STRIPPED_PETRIFIED_WOOD.get())
                .add(UPBlocks.DRYO_LOG.get())
                .add(UPBlocks.DRYO_WOOD.get())
                .add(UPBlocks.STRIPPED_DRYO_WOOD.get())
                .add(UPBlocks.STRIPPED_DRYO_LOG.get())
                .add(UPBlocks.DRYO_PLANKS.get())
                .add(UPBlocks.DRYO_DOOR.get())
                .add(UPBlocks.DRYO_FENCE.get())
                .add(UPBlocks.DRYO_PRESSURE_PLATE.get())
                .add(UPBlocks.DRYO_SLAB.get())
                .add(UPBlocks.DRYO_STAIRS.get())
                .add(UPBlocks.DRYO_TRAPDOOR.get())
                .add(UPBlocks.AMBER_BUTTON.get())
                .add(UPBlocks.DRYO_BUTTON.get())
                .add(UPBlocks.DRYO_BUTTON.get())
                .add(UPBlocks.ZULOAGAE_BLOCK.get())
                .add(UPBlocks.ZULOAGAE_PLANKS.get())
                .add(UPBlocks.ZULOAGAE_BUTTON.get())
                .add(UPBlocks.ZULOAGAE_DOOR.get())
                .add(UPBlocks.ZULOAGAE_FENCE.get())
                .add(UPBlocks.ZULOAGAE_FENCE_GATE.get())
                .add(UPBlocks.ZULOAGAE_PRESSURE_PLATE.get())
                .add(UPBlocks.ZULOAGAE_STAIRS.get())
                .add(UPBlocks.ZULOAGAE_SLAB.get())
                .add(UPBlocks.ZULOAGAE_TRAPDOOR.get())
        ;


        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(UPBlocks.DEEPSLATE_FOSSIL.get())
                .add(UPBlocks.STONE_FOSSIL.get())
                .add(UPBlocks.ANALYZER.get())
                .add(UPBlocks.CULTIVATOR.get())
                .add(UPBlocks.INCUBATOR.get())
                .add(UPBlocks.DNA_FRIDGE.get())
                .add(UPBlocks.STONE_TAR_FOSSIL.get())
                .add(UPBlocks.DEEPSLATE_TAR_FOSSIL.get())
                .add(UPBlocks.AMBER_BLOCK.get())
                .add(UPBlocks.OPAL_BLOCK.get())
                .add(UPBlocks.FIRE_OPAL_BLOCK.get())
                .add(UPBlocks.BOULDER_OPAL_BLOCK.get())
                .add(UPBlocks.BLACK_OPAL_BLOCK.get())
                .add(UPBlocks.ZULOAGAE_DOOR.get())
                .add(UPBlocks.STONE_AMBER_FOSSIL.get())
                .add(UPBlocks.OPAL_ORE.get())
                .add(UPBlocks.DEEPSLATE_AMBER_FOSSIL.get())
                .add(UPBlocks.DEEPSLATE_OPAL_ORE.get())
        ;

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
        ;

        tag(BlockTags.SIGNS)
        ;

        tag(BlockTags.WALL_SIGNS)
        ;

        tag(BlockTags.CEILING_HANGING_SIGNS)
        ;

        tag(BlockTags.WALL_HANGING_SIGNS)
        ;

        tag(BlockTags.TALL_FLOWERS)
                .add(UPBlocks.TALL_SARACENIA.get())
                .add(UPBlocks.RAIGUENRAYUN.get());

        tag(BlockTags.SMALL_FLOWERS)
                .add(UPBlocks.LEEFRUCTUS.get())
                .add(UPBlocks.SARACENIA.get())
        ;

        tag(BlockTags.PLANKS)
                .add(UPBlocks.ZULOAGAE_PLANKS.get())
                .add(UPBlocks.DRYO_PLANKS.get())
        ;

        tag(BlockTags.WOODEN_DOORS)
                .add(UPBlocks.DRYO_DOOR.get())
                .add(UPBlocks.ZULOAGAE_DOOR.get())
        ;

        tag(BlockTags.WOODEN_FENCES)
                .add(UPBlocks.DRYO_FENCE.get())
                .add(UPBlocks.ZULOAGAE_FENCE.get())
        ;

        tag(BlockTags.PRESSURE_PLATES)
                .add(UPBlocks.DRYO_PRESSURE_PLATE.get())
                .add(UPBlocks.ZULOAGAE_PRESSURE_PLATE.get())
        ;

        tag(BlockTags.SLABS)
                .add(UPBlocks.DRYO_SLAB.get())
                .add(UPBlocks.ZULOAGAE_SLAB.get())
        ;


        tag(BlockTags.STAIRS)
                .add(UPBlocks.DRYO_STAIRS.get())
                .add(UPBlocks.ZULOAGAE_STAIRS.get())
        ;

        tag(BlockTags.TRAPDOORS)
                .add(UPBlocks.DRYO_TRAPDOOR.get())
                .add(UPBlocks.ZULOAGAE_TRAPDOOR.get())
        ;

        tag(BlockTags.BUTTONS)
                .add(UPBlocks.AMBER_BUTTON.get())
                .add(UPBlocks.DRYO_BUTTON.get())
                .add(UPBlocks.ZULOAGAE_BUTTON.get())
        ;

        tag(BlockTags.WOODEN_BUTTONS)
                .add(UPBlocks.DRYO_BUTTON.get())
                .add(UPBlocks.ZULOAGAE_BUTTON.get())
        ;

        tag(UPBlockTags.VELOCI_BUTTONS)
                .add(UPBlocks.AMBER_BUTTON.get())
        ;

        tag(BlockTags.LEAVES)
                .add(UPBlocks.DRYO_LEAVES.get())
        ;

        tag(UPBlockTags.TAR_PIT_REPLACEABLE)
                .addTags(BlockTags.SAND)
                .addTags(BlockTags.DIRT)
                .addTags(BlockTags.BASE_STONE_OVERWORLD)
                .addTags(BlockTags.TERRACOTTA)
                .add(Blocks.SANDSTONE)
                .add(Blocks.RED_SANDSTONE)
        ;

        tag(UPBlockTags.CLUB_WHITELIST_BLOCKS)
                .addTags(BlockTags.SAND)
                .addTags(BlockTags.DIRT)
                .addTags(BlockTags.BASE_STONE_OVERWORLD)
                .addTags(BlockTags.TERRACOTTA)
                .add(Blocks.SANDSTONE)
                .add(Blocks.RED_SANDSTONE)
                .add(Blocks.TNT)
                .add(Blocks.ANVIL)
                .add(Blocks.CHIPPED_ANVIL)
                .add(Blocks.DAMAGED_ANVIL)
        ;

        tag(UPBlockTags.ZULOAGAE_PLANTABLE_ON)
                .addTags(BlockTags.SAND)
                .addTags(BlockTags.DIRT)
                .add(UPBlocks.ZULOAGAE.get(), UPBlocks.ZULOAGAE_SAPLING.get(), Blocks.GRAVEL);

    }


    @Override
    public String getName() {
        return UnusualPrehistory.MODID + " Block Tags";
    }
}
