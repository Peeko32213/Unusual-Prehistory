package com.peeko32213.unusualprehistory.core.datagen.server;

import com.peeko32213.unusualprehistory.core.UnusualPrehistory;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.peeko32213.unusualprehistory.core.other.UPTags.*;
import static com.peeko32213.unusualprehistory.core.other.UPBlockTags.*;
import static com.peeko32213.unusualprehistory.core.registry.UPBlocks.*;

public class UPBlockTagsGenerator extends BlockTagsProvider {

    public UPBlockTagsGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper helper) {
        super(output, lookupProvider, UnusualPrehistory.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {

        this.tag(BlockTags.SAPLINGS).add(DRYO_SAPLING.get(), FOXII_SAPLING.get());
        this.tag(BlockTags.LEAVES).add(DRYO_LEAVES.get(), FOXII_LEAVES.get());
        this.tag(BlockTags.OVERWORLD_NATURAL_LOGS).add(DRYO_LOG.get(), FOXII_LOG.get());
        this.tag(BlockTags.LOGS_THAT_BURN).addTags(DRYO_LOGS, FOXII_LOGS);
        this.tag(BlockTags.PLANKS).add(DRYO_PLANKS.get(), FOXII_PLANKS.get());
        this.tag(BlockTags.WOODEN_BUTTONS).add(DRYO_BUTTON.get(), FOXII_BUTTON.get());
        this.tag(BlockTags.WOODEN_DOORS).add(DRYO_DOOR.get(), FOXII_DOOR.get());
        this.tag(BlockTags.WOODEN_FENCES).add(DRYO_FENCE.get(), FOXII_FENCE.get());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(DRYO_PRESSURE_PLATE.get(), FOXII_PRESSURE_PLATE.get());
        this.tag(BlockTags.WOODEN_SLABS).add(DRYO_SLAB.get(), FOXII_SLAB.get());
        this.tag(BlockTags.WOODEN_STAIRS).add(DRYO_STAIRS.get(), FOXII_STAIRS.get());
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(DRYO_TRAPDOOR.get(), FOXII_TRAPDOOR.get());
        this.tag(BlockTags.FENCE_GATES).add(DRYO_FENCE_GATE.get(), FOXII_FENCE_GATE.get());

        this.tag(BlockTags.STANDING_SIGNS).add(DRYO_SIGNS.getFirst().get(), FOXII_SIGNS.getFirst().get());
        this.tag(BlockTags.WALL_SIGNS).add(DRYO_SIGNS.getSecond().get(), FOXII_SIGNS.getSecond().get());
        this.tag(BlockTags.CEILING_HANGING_SIGNS).add(DRYO_HANGING_SIGNS.getFirst().get(), FOXII_HANGING_SIGNS.getFirst().get());
        this.tag(BlockTags.WALL_HANGING_SIGNS).add(DRYO_HANGING_SIGNS.getSecond().get(), FOXII_HANGING_SIGNS.getSecond().get());


        tag(DINO_NATURAL_SPAWNABLE)
                .addTag(BlockTags.ANIMALS_SPAWNABLE_ON)
                .addTags(BlockTags.TERRACOTTA)
                .addTags(BlockTags.SAND)
                .add(Blocks.SNOW_BLOCK)
                .add(Blocks.SNOW)
                .add(Blocks.PODZOL)
                .add(Blocks.MUD)
                .add(Blocks.COARSE_DIRT)
                .add(Blocks.ROOTED_DIRT)
                .add(Blocks.PACKED_MUD)
                .add(Blocks.SANDSTONE)
                .add(Blocks.RED_SAND)
        ;


        tag(ANGRY_BRACHI_BREAKABLES)
                .addTag(BlockTags.LEAVES)
                .addTag(BlockTags.PLANKS)
                .addTag(BlockTags.LOGS)
                .addTag(BlockTags.WOODEN_STAIRS)
                .addTag(BlockTags.WOODEN_SLABS)
                .addTag(BlockTags.WOOL)
                .add(Blocks.LILY_PAD)
                .add(Blocks.CACTUS)
                .add(Blocks.BAMBOO);

        tag(PASSIVE_BRACHI_BREAKABLES)
                ;

        tag(REX_BREAKABLES)
                .addTag(BlockTags.LEAVES)
                .addTag(BlockTags.PLANKS)
                .addTag(BlockTags.LOGS)
                .addTag(BlockTags.WOODEN_STAIRS)
                .addTag(BlockTags.WOODEN_SLABS)
                .addTag(BlockTags.WOOL)
                .add(Blocks.LILY_PAD)
                .add(Blocks.CACTUS)
                .add(Blocks.BAMBOO);

        tag(TRIKE_BREAKABLES)
                .addTag(BlockTags.LEAVES)
                .addTag(BlockTags.PLANKS)
                .addTag(BlockTags.LOGS)
                .addTag(BlockTags.WOODEN_STAIRS)
                .addTag(BlockTags.WOODEN_SLABS)
                .addTag(BlockTags.WOOL)
                .add(Blocks.LILY_PAD)
                .add(Blocks.CACTUS)
                .add(Blocks.BAMBOO);

        tag(TYRANNO_BREAKABLES)
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

        tag(DIPLO_DIGS)
                .addTag(BlockTags.SAND)
                .add(Blocks.MUD)
        ;

        tag(TRIKE_GRAZING_BLOCKS)
                .add(Blocks.GRASS_BLOCK)
                .add(Blocks.FERN)
                .add(Blocks.LARGE_FERN)
                .add(Blocks.GRASS)
                .add(HORSETAIL.get())
                .add(TALL_HORSETAIL.get())
        ;

        tag(COTY_GRAZING_BLOCKS)
                .add(Blocks.GRASS_BLOCK)
                .add(Blocks.FERN)
                .add(Blocks.LARGE_FERN)
                .add(Blocks.GRASS)
                .add(Blocks.MELON)
                .add(HORSETAIL.get())
                .add(TALL_HORSETAIL.get())
        ;

        tag(MEGATHERIUM_EATABLES)
                .addTag(BlockTags.LEAVES);

        tag(MEGATHERIUM_MINEABLES)
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

        tag(ERYON_DIGGABLES)
                .addTag(BlockTags.SAND);

        tag(TALPANAS_DIGGABLES)
                .add(Blocks.ROOTED_DIRT);

        tag(DINO_HATCHABLE_BLOCKS)
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
                .add(GINKGO_FENCE_GATE.get())
                .add(PETRIFIED_WOOD_FENCE_GATE.get());

        tag(BlockTags.FENCES)
                .add(GINKGO_FENCE.get())
                .add(PETRIFIED_WOOD_FENCE.get());

        tag(BlockTags.LOGS)
                .add(GINKGO_LOG.get())
                .add(GINKGO_WOOD.get())
                .add(STRIPPED_GINKGO_WOOD.get())
                .add(STRIPPED_GINKGO_LOG.get())
                .add(PETRIFIED_WOOD_LOG.get())
                .add(STRIPPED_PETRIFIED_WOOD.get())
                .add(STRIPPED_PETRIFIED_WOOD_LOG.get())
                .add(ZULOAGAE_BLOCK.get())
        ;

        tag(BlockTags.LOGS_THAT_BURN)
                .add(GINKGO_LOG.get())
                .add(GINKGO_WOOD.get())
                .add(STRIPPED_GINKGO_WOOD.get())
                .add(STRIPPED_GINKGO_LOG.get())
                .add(ZULOAGAE_BLOCK.get())

        ;

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(DEEPSLATE_FOSSIL.get())
                .add(DEEPSLATE_AMBER_FOSSIL.get())
                .add(DEEPSLATE_PLANT_FOSSIL.get())
                .add(STONE_FOSSIL.get())
                .add(STONE_AMBER_FOSSIL.get())
                .add(ANALYZER.get())
                .add(CULTIVATOR.get())
                .add(INCUBATOR.get())
                .add(AMMONITE_SHELL.get())
                .add(PLANT_FOSSIL.get())
                .add(DNA_FRIDGE.get())
                .add(CLATHRODICTYON_BLOCK.get())
                .add(ANOSTYLOSTROMA_BLOCK.get())
                .add(PETRIFIED_WOOD_LOG.get())
                .add(STRIPPED_PETRIFIED_WOOD.get())
                .add(STRIPPED_PETRIFIED_WOOD_LOG.get())
                .add(POLISHED_PETRIFIED_WOOD.get())
                .add(POLISHED_PETRIFIED_WOOD_SLAB.get())
                .add(POLISHED_PETRIFIED_WOOD_STAIRS.get())
                .add(PETRIFIED_WOOD_STAIRS.get())
                .add(PETRIFIED_WOOD_SLAB.get())
                .add(PETRIFIED_WOOD_PLANKS.get())
                .add(PETRIFIED_WOOD_PRESSURE_PLATE.get())
                .add(PETRIFIED_WOOD_TRAPDOOR.get())
                .add(AUSTRO_FOSSIL.get())
                .add(ULUGH_FOSSIL.get())
                .add(KENTRO_FOSSIL.get())
                .add(ANTARCTO_FOSSIL.get())
                .add(HWACHA_FOSSIL.get())
                .add(ERYON_FOSSIL.get())
                .add(VELOCI_FOSSIL.get())
                .add(PACHY_FOSSIL.get())
                .add(MAJUNGA_FOSSIL.get())
                .add(DUNK_FOSSIL.get())
                .add(BRACHI_FOSSIL.get())
                .add(BEELZE_FOSSIL.get())
                .add(SCAU_FOSSIL.get())
                .add(COTY_FOSSIL.get())
                .add(STETHA_FOSSIL.get())
                .add(ANURO_FOSSIL.get())
                .add(PETRIFIED_WOOD_FENCE.get())
                .add(PETRIFIED_WOOD_FENCE_GATE.get())
                .add(PETRIFIED_WOOD_SIGN.get())
                .add(PETRIFIED_WOOD_WALL_SIGN.get())
                .add(PETRIFIED_WOOD_DOOR.get())
                .add(STONE_OPAL_FOSSIL.get())
                .add(DEEPSLATE_OPAL_FOSSIL.get())
                .add(STONE_TAR_FOSSIL.get())
                .add(DEEPSLATE_TAR_FOSSIL.get())
                .add(PERMAFROST_FOSSIL.get())
                .add(PERMAFROST.get())
                .add(OPAL_BLOCK.get())
                .add(FIRE_OPAL_BLOCK.get())
                .add(BOULDER_OPAL_BLOCK.get())
                .add(BLACK_OPAL_BLOCK.get())
                .add(AMBER_BLOCK.get())

        ;

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(REX_BOOMBOX.get())
                .add(GINKGO_LOG.get())
                .add(GINKGO_WOOD.get())
                .add(GINKGO_PLANKS.get())
                .add(STRIPPED_GINKGO_WOOD.get())
                .add(STRIPPED_GINKGO_LOG.get())
                .add(PETRIFIED_WOOD_LOG.get())
                .add(STRIPPED_PETRIFIED_WOOD.get())
                .add(STRIPPED_PETRIFIED_WOOD_LOG.get())
                .add(GINKGO_PLANKS.get())
                .add(PETRIFIED_WOOD_PLANKS.get())
                .add(GINKGO_WALL_SIGN.get())
                .add(PETRIFIED_WOOD_WALL_SIGN.get())
                .add(GINKGO_DOOR.get())
                .add(PETRIFIED_WOOD_DOOR.get())
                .add(GINKGO_FENCE.get())
                .add(PETRIFIED_WOOD_FENCE.get())
                .add(GINKGO_PRESSURE_PLATE.get())
                .add(PETRIFIED_WOOD_PRESSURE_PLATE.get())
                .add(GINKGO_SLAB.get())
                .add(PETRIFIED_WOOD_SLAB.get())
                .add(POLISHED_PETRIFIED_WOOD_SLAB.get())
                .add(GINKGO_STAIRS.get())
                .add(PETRIFIED_WOOD_STAIRS.get())
                .add(POLISHED_PETRIFIED_WOOD_STAIRS.get())
                .add(GINKGO_TRAPDOOR.get())
                .add(PETRIFIED_WOOD_TRAPDOOR.get())
                .add(GINKGO_BUTTON.get())
                .add(AMBER_BUTTON.get())
                .add(PETRIFIED_WOOD_BUTTON.get())
                .add(GINKGO_BUTTON.get())
                .add(PETRIFIED_WOOD_BUTTON.get())
                .add(ZULOAGAE_BLOCK.get())
                .add(ZULOAGAE_PLANKS.get())
                .add(ZULOAGAE_BUTTON.get())
                .add(ZULOAGAE_DOOR.get())
                .add(ZULOAGAE_FENCE.get())
                .add(ZULOAGAE_FENCE_GATE.get())
                .add(ZULOAGAE_PRESSURE_PLATE.get())
                .add(ZULOAGAE_STAIRS.get())
                .add(ZULOAGAE_SLAB.get())
                .add(ZULOAGAE_TRAPDOOR.get())
        ;


        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(DEEPSLATE_FOSSIL.get())
                .add(STONE_FOSSIL.get())
                .add(ANALYZER.get())
                .add(CULTIVATOR.get())
                .add(INCUBATOR.get())
                .add(DNA_FRIDGE.get())
                .add(STONE_TAR_FOSSIL.get())
                .add(DEEPSLATE_TAR_FOSSIL.get())
                .add(AMBER_BLOCK.get())
                .add(OPAL_BLOCK.get())
                .add(FIRE_OPAL_BLOCK.get())
                .add(BOULDER_OPAL_BLOCK.get())
                .add(BLACK_OPAL_BLOCK.get())
                .add(ZULOAGAE_DOOR.get())
                .add(STONE_AMBER_FOSSIL.get())
                .add(STONE_OPAL_FOSSIL.get())
                .add(DEEPSLATE_AMBER_FOSSIL.get())
                .add(DEEPSLATE_OPAL_FOSSIL.get())
        ;

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
        ;

        tag(BlockTags.SIGNS)
                .add(GINKGO_SIGN.get())
                .add(PETRIFIED_WOOD_SIGN.get())
                .add(ZULOAGAE_SIGN.get())
        ;

        tag(BlockTags.WALL_SIGNS)
                .add(GINKGO_WALL_SIGN.get())
                .add(PETRIFIED_WOOD_WALL_SIGN.get())
                .add(ZULOAGAE_WALL_SIGN.get())
        ;

        tag(BlockTags.CEILING_HANGING_SIGNS)
                .add(GINKGO_HANGING_SIGN.get())
                .add(PETRIFIED_WOOD_HANGING_SIGN.get())
                .add(ZULOAGAE_HANGING_SIGN.get())
        ;

        tag(BlockTags.WALL_HANGING_SIGNS)
                .add(GINKGO_WALL_HANGING_SIGN.get())
                .add(PETRIFIED_WOOD_WALL_HANGING_SIGN.get())
                .add(ZULOAGAE_WALL_HANGING_SIGN.get())
        ;

        tag(BlockTags.TALL_FLOWERS)
                .add(TALL_SARACENIA.get())
                .add(RAIGUENRAYUN.get());

        tag(BlockTags.SMALL_FLOWERS)
                .add(LEEFRUCTUS.get())
                .add(SARACENIA.get())
        ;

        tag(BlockTags.PLANKS)
                .add(GINKGO_PLANKS.get())
                .add(PETRIFIED_WOOD_PLANKS.get())
                .add(ZULOAGAE_PLANKS.get())
        ;

        tag(BlockTags.WOODEN_DOORS)
                .add(GINKGO_DOOR.get())
                .add(PETRIFIED_WOOD_DOOR.get())
                .add(ZULOAGAE_DOOR.get())
        ;

        tag(BlockTags.WOODEN_FENCES)
                .add(GINKGO_FENCE.get())
                .add(PETRIFIED_WOOD_FENCE.get())
                .add(ZULOAGAE_FENCE.get())
        ;

        tag(BlockTags.PRESSURE_PLATES)
                .add(GINKGO_PRESSURE_PLATE.get())
                .add(PETRIFIED_WOOD_PRESSURE_PLATE.get())
                .add(ZULOAGAE_PRESSURE_PLATE.get())
        ;

        tag(BlockTags.SLABS)
                .add(GINKGO_SLAB.get())
                .add(PETRIFIED_WOOD_SLAB.get())
                .add(POLISHED_PETRIFIED_WOOD_SLAB.get())
                .add(ZULOAGAE_SLAB.get())
        ;


        tag(BlockTags.STAIRS)
                .add(GINKGO_STAIRS.get())
                .add(PETRIFIED_WOOD_STAIRS.get())
                .add(POLISHED_PETRIFIED_WOOD_STAIRS.get())
                .add(ZULOAGAE_STAIRS.get())
        ;

        tag(BlockTags.TRAPDOORS)
                .add(GINKGO_TRAPDOOR.get())
                .add(PETRIFIED_WOOD_TRAPDOOR.get())
                .add(ZULOAGAE_TRAPDOOR.get())
        ;

        tag(BlockTags.BUTTONS)
                .add(GINKGO_BUTTON.get())
                .add(AMBER_BUTTON.get())
                .add(PETRIFIED_WOOD_BUTTON.get())
                .add(ZULOAGAE_BUTTON.get())
        ;

        tag(BlockTags.WOODEN_BUTTONS)
                .add(GINKGO_BUTTON.get())
                .add(FOXII_BUTTON.get())
                .add(DRYO_BUTTON.get())
                .add(PETRIFIED_WOOD_BUTTON.get())
                .add(ZULOAGAE_BUTTON.get())
        ;

        tag(VELOCI_BUTTONS)
                .add(AMBER_BUTTON.get())
        ;

        tag(BlockTags.LEAVES)
                .add(GINKGO_LEAVES.get());

        tag(TAR_PIT_REPLACEABLE)
                .addTags(BlockTags.SAND)
                .addTags(BlockTags.DIRT)
                .addTags(BlockTags.BASE_STONE_OVERWORLD)
                .addTags(BlockTags.TERRACOTTA)
                .add(Blocks.SANDSTONE)
                .add(Blocks.RED_SANDSTONE)
        ;

        tag(CLUB_WHITELIST_BLOCKS)
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

        tag(ZULOAGAE_PLANTABLE_ON)
                .addTags(BlockTags.SAND)
                .addTags(BlockTags.DIRT)
                .add(ZULOAGAE.get())
                .add(ZULOAGAE_SAPLING.get())
                .add(Blocks.GRAVEL)
        ;
    }


    @Override
    public String getName() {
        return UnusualPrehistory.MODID + " Block Tags";
    }
}
