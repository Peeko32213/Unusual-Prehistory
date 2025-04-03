package com.peeko32213.unusualprehistory.data.server.tags;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.other.tags.UPBlockTags;
import com.peeko32213.unusualprehistory.core.registry.blocks.UPBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.peeko32213.unusualprehistory.core.registry.blocks.UPBlocks.*;

public class UPBlockTagsProvider extends BlockTagsProvider {

    public UPBlockTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
        super(output, provider, UnusualPrehistory.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {

        // Mineables
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(
                REX_BOOMBOX.get(),
                DRYO_LOG.get(), DRYO_WOOD.get(), STRIPPED_DRYO_WOOD.get(), STRIPPED_DRYO_LOG.get(),
                DRYO_PLANKS.get(), DRYO_DOOR.get(), DRYO_FENCE.get(), DRYO_PRESSURE_PLATE.get(), DRYO_SLAB.get(), DRYO_STAIRS.get(), DRYO_TRAPDOOR.get(), DRYO_BUTTON.get(),

                FOXII_LOG.get(), FOXII_WOOD.get(), STRIPPED_FOXII_WOOD.get(), STRIPPED_FOXII_LOG.get(),
                FOXII_PLANKS.get(), FOXII_DOOR.get(), FOXII_FENCE.get(), FOXII_PRESSURE_PLATE.get(), FOXII_SLAB.get(), FOXII_STAIRS.get(), FOXII_TRAPDOOR.get(), FOXII_BUTTON.get(),

                GINKGO_LOG.get(), GINKGO_WOOD.get(), STRIPPED_GINKGO_WOOD.get(), STRIPPED_GINKGO_LOG.get(),
                GINKGO_PLANKS.get(), GINKGO_DOOR.get(), GINKGO_FENCE.get(), GINKGO_PRESSURE_PLATE.get(), GINKGO_SLAB.get(), GINKGO_STAIRS.get(), GINKGO_TRAPDOOR.get(), GINKGO_BUTTON.get(),

                ZULOAGAE.get(),
                ZULOAGAE_BLOCK.get(), STRIPPED_ZULOAGAE_BLOCK.get(),
                ZULOAGAE_PLANKS.get(), ZULOAGAE_BUTTON.get(), ZULOAGAE_DOOR.get(), ZULOAGAE_FENCE.get(), ZULOAGAE_FENCE_GATE.get(), ZULOAGAE_PRESSURE_PLATE.get(), ZULOAGAE_STAIRS.get(), ZULOAGAE_SLAB.get(), ZULOAGAE_TRAPDOOR.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_HOE).add(
                DRYO_LEAVES.get(),
                FOXII_LEAVES.get(),
                GINKGO_LEAVES.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ANALYZER.get(), CULTIVATOR.get(), INCUBATOR.get(), DNA_FRIDGE.get(),
                AMMONITE_SHELL.get(),
                CLATHRODICTYON_BLOCK.get(),
                ANOSTYLOSTROMA_BLOCK.get(),
                PETRIFIED_LOG.get(), PETRIFIED_WOOD.get(), STRIPPED_PETRIFIED_WOOD.get(), STRIPPED_PETRIFIED_LOG.get(),
                PETRIFIED_PLANKS.get(), PETRIFIED_DOOR.get(), PETRIFIED_FENCE.get(), PETRIFIED_PRESSURE_PLATE.get(), PETRIFIED_SLAB.get(), PETRIFIED_STAIRS.get(), PETRIFIED_TRAPDOOR.get(), PETRIFIED_BUTTON.get(),

                AMBER_BLOCK.get(), AMBER_BUTTON.get(),

                AUSTRO_FOSSIL.get(), ULUGH_FOSSIL.get(), KENTRO_FOSSIL.get(), ANTARCTO_FOSSIL.get(), HWACHA_FOSSIL.get(), ERYON_FOSSIL.get(),
                VELOCI_FOSSIL.get(), PACHY_FOSSIL.get(), MAJUNGA_FOSSIL.get(), DUNK_FOSSIL.get(), BRACHI_FOSSIL.get(), BEELZE_FOSSIL.get(),
                SCAU_FOSSIL.get(), COTY_FOSSIL.get(), STETHA_FOSSIL.get(), ANURO_FOSSIL.get(),

                PLANT_FOSSIL.get(), DEEPSLATE_PLANT_FOSSIL.get(),
                STONE_FOSSIL.get(), DEEPSLATE_FOSSIL.get(),
                STONE_AMBER_FOSSIL.get(), DEEPSLATE_AMBER_FOSSIL.get(),
                OPAL_ORE.get(), DEEPSLATE_OPAL_ORE.get(),
                FIRE_OPAL_ORE.get(), DEEPSLATE_FIRE_OPAL_ORE.get(),
                BOULDER_OPAL_ORE.get(), DEEPSLATE_BOULDER_OPAL_ORE.get(),
                BLACK_OPAL_ORE.get(), DEEPSLATE_BLACK_OPAL_ORE.get(),
                STONE_TAR_FOSSIL.get(), DEEPSLATE_TAR_FOSSIL.get(),
                PERMAFROST.get(), PERMAFROST_FOSSIL.get(),
                OPAL_BLOCK.get(), FIRE_OPAL_BLOCK.get(), BOULDER_OPAL_BLOCK.get(), BLACK_OPAL_BLOCK.get()
        );

        this.tag(BlockTags.SWORD_EFFICIENT).add(
                FRUIT_LOOT_BOX.get()
        );

        // Tiers
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(
                ANALYZER.get(), CULTIVATOR.get(), INCUBATOR.get(), DNA_FRIDGE.get(),
                DEEPSLATE_FOSSIL.get(), STONE_FOSSIL.get(),
                STONE_TAR_FOSSIL.get(), DEEPSLATE_TAR_FOSSIL.get(),
                STONE_AMBER_FOSSIL.get(), DEEPSLATE_AMBER_FOSSIL.get(),
                AMBER_BLOCK.get(), AMBER_BUTTON.get(),
                OPAL_ORE.get(), DEEPSLATE_OPAL_ORE.get(),
                FIRE_OPAL_ORE.get(), DEEPSLATE_FIRE_OPAL_ORE.get(),
                BOULDER_OPAL_ORE.get(), DEEPSLATE_BOULDER_OPAL_ORE.get(),
                BLACK_OPAL_ORE.get(), DEEPSLATE_BLACK_OPAL_ORE.get(),
                OPAL_BLOCK.get(), FIRE_OPAL_BLOCK.get(), BOULDER_OPAL_BLOCK.get(), BLACK_OPAL_BLOCK.get()
        );

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL);

        // Flowers
        this.tag(BlockTags.FLOWER_POTS).add(
                POTTED_ARCHAEOSIGILARIA.get(),
                POTTED_BENNETTITALES.get(),
                POTTED_DRYO_SAPLING.get(),
                POTTED_HORSETAIL.get(),
                POTTED_LEEFRUCTUS.get(),
                POTTED_GINKGO_SAPLING.get(),
                POTTED_PETRIFIED_BUSH.get(),
                POTTED_SARACENIA.get(),
                POTTED_ZULOAGAE_SAPLING.get()
        );

        this.tag(BlockTags.FLOWERS).add(
                LEEFRUCTUS.get(),
                SARACENIA.get(),
                TALL_SARACENIA.get(),
                RAIGUENRAYUN.get()
        );

        this.tag(BlockTags.SMALL_FLOWERS).add(
                LEEFRUCTUS.get(),
                SARACENIA.get()
        );

        this.tag(BlockTags.TALL_FLOWERS).add(
                TALL_SARACENIA.get(),
                RAIGUENRAYUN.get()
        );

        // Wooden
        this.tag(BlockTags.SAPLINGS).add(
                DRYO_SAPLING.get(),
                FOXII_SAPLING.get(),
                GINKGO_SAPLING.get()
        );

        this.tag(BlockTags.LEAVES).add(
                DRYO_LEAVES.get(),
                FOXII_LEAVES.get(),
                GINKGO_LEAVES.get()
        );

        this.tag(BlockTags.LOGS).add(
                PETRIFIED_LOG.get(), PETRIFIED_WOOD.get(), STRIPPED_PETRIFIED_LOG.get(), STRIPPED_PETRIFIED_WOOD.get()
        );

        this.tag(BlockTags.LOGS_THAT_BURN).add(
                DRYO_LOG.get(), DRYO_WOOD.get(), STRIPPED_DRYO_LOG.get(), STRIPPED_DRYO_WOOD.get(),
                FOXII_LOG.get(), FOXII_WOOD.get(), STRIPPED_FOXII_LOG.get(), STRIPPED_FOXII_WOOD.get(),
                GINKGO_LOG.get(), GINKGO_WOOD.get(), STRIPPED_GINKGO_LOG.get(), STRIPPED_GINKGO_WOOD.get()
        );

        this.tag(BlockTags.OVERWORLD_NATURAL_LOGS).add(
                DRYO_LOG.get(),
                FOXII_LOG.get(),
                GINKGO_LOG.get()
        );

        this.tag(BlockTags.PLANKS).add(
                DRYO_PLANKS.get(),
                FOXII_PLANKS.get(),
                GINKGO_PLANKS.get(),
                PETRIFIED_PLANKS.get(),
                ZULOAGAE_PLANKS.get()
        );

        this.tag(BlockTags.STANDING_SIGNS).add(
                DRYO_SIGN.getFirst().get(),
                FOXII_SIGN.getFirst().get(),
                GINKGO_SIGN.getFirst().get(),
                PETRIFIED_SIGN.getFirst().get(),
                ZULOAGAE_SIGN.getFirst().get()
        );

        this.tag(BlockTags.WALL_SIGNS).add(
                DRYO_SIGN.getSecond().get(),
                FOXII_SIGN.getSecond().get(),
                GINKGO_SIGN.getSecond().get(),
                PETRIFIED_SIGN.getSecond().get(),
                ZULOAGAE_SIGN.getSecond().get()
        );

        this.tag(BlockTags.CEILING_HANGING_SIGNS).add(
                DRYO_HANGING_SIGN.getFirst().get(),
                FOXII_HANGING_SIGN.getFirst().get(),
                GINKGO_HANGING_SIGN.getFirst().get(),
                PETRIFIED_HANGING_SIGN.getFirst().get(),
                ZULOAGAE_HANGING_SIGN.getFirst().get()
        );

        this.tag(BlockTags.WALL_HANGING_SIGNS).add(
                DRYO_HANGING_SIGN.getSecond().get(),
                FOXII_HANGING_SIGN.getSecond().get(),
                GINKGO_HANGING_SIGN.getSecond().get(),
                PETRIFIED_HANGING_SIGN.getSecond().get(),
                ZULOAGAE_HANGING_SIGN.getSecond().get()
        );

        this.tag(BlockTags.WOODEN_BUTTONS).add(
                DRYO_BUTTON.get(),
                FOXII_BUTTON.get(),
                GINKGO_BUTTON.get(),
                ZULOAGAE_BUTTON.get()
        );

        this.tag(BlockTags.WOODEN_DOORS).add(
                DRYO_DOOR.get(),
                FOXII_DOOR.get(),
                GINKGO_DOOR.get(),
                ZULOAGAE_DOOR.get()
        );

        this.tag(BlockTags.WOODEN_FENCES).add(
                DRYO_FENCE.get(),
                FOXII_FENCE.get(),
                GINKGO_FENCE.get(),
                ZULOAGAE_FENCE.get()
        );

        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(
                DRYO_PRESSURE_PLATE.get(),
                FOXII_PRESSURE_PLATE.get(),
                GINKGO_PRESSURE_PLATE.get(),
                ZULOAGAE_PRESSURE_PLATE.get()
        );

        this.tag(BlockTags.WOODEN_SLABS).add(
                DRYO_SLAB.get(),
                FOXII_SLAB.get(),
                GINKGO_SLAB.get(),
                ZULOAGAE_SLAB.get()
        );

        this.tag(BlockTags.WOODEN_STAIRS).add(
                DRYO_STAIRS.get(),
                FOXII_STAIRS.get(),
                GINKGO_STAIRS.get(),
                ZULOAGAE_STAIRS.get()
        );

        this.tag(BlockTags.WOODEN_TRAPDOORS).add(
                DRYO_TRAPDOOR.get(),
                FOXII_TRAPDOOR.get(),
                GINKGO_TRAPDOOR.get(),
                ZULOAGAE_TRAPDOOR.get()
        );

        this.tag(BlockTags.FENCE_GATES).add(
                DRYO_FENCE_GATE.get(),
                FOXII_FENCE_GATE.get(),
                GINKGO_FENCE_GATE.get(),
                PETRIFIED_FENCE_GATE.get(),
                ZULOAGAE_FENCE_GATE.get()
        );

        this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(
                DRYO_FENCE_GATE.get(),
                FOXII_FENCE_GATE.get(),
                GINKGO_FENCE_GATE.get(),
                ZULOAGAE_FENCE_GATE.get()
        );

        this.tag(Tags.Blocks.FENCE_GATES).add(
                PETRIFIED_FENCE_GATE.get()
        );

        this.tag(BlockTags.WALLS).add(
                POLISHED_PETRIFIED_WOOD_WALL.get()
        );

        this.tag(BlockTags.PRESSURE_PLATES).add(
                DRYO_PRESSURE_PLATE.get(),
                ZULOAGAE_PRESSURE_PLATE.get()
        );

        this.tag(BlockTags.SLABS).add(
                DRYO_SLAB.get(),
                ZULOAGAE_SLAB.get()
        );

        this.tag(BlockTags.STAIRS).add(
                DRYO_STAIRS.get(),
                ZULOAGAE_STAIRS.get()
        );

        this.tag(BlockTags.BUTTONS).add(
                AMBER_BUTTON.get(),
                PETRIFIED_BUTTON.get()
        );

        this.tag(BlockTags.DOORS).add(
                PETRIFIED_DOOR.get()
        );

        this.tag(BlockTags.FENCES).add(
                PETRIFIED_FENCE.get()
        );

        this.tag(BlockTags.STONE_PRESSURE_PLATES).add(
                PETRIFIED_PRESSURE_PLATE.get()
        );

        this.tag(BlockTags.SLABS).add(
                PETRIFIED_SLAB.get(),
                POLISHED_PETRIFIED_WOOD_SLAB.get()
        );

        this.tag(BlockTags.STAIRS).add(
                PETRIFIED_STAIRS.get(),
                POLISHED_PETRIFIED_WOOD_STAIRS.get()
        );

        this.tag(BlockTags.TRAPDOORS).add(
                PETRIFIED_TRAPDOOR.get()
        );

        // UP Tags
        this.tag(UPBlockTags.DRYO_LOGS).add(DRYO_LOG.get(), DRYO_WOOD.get(), STRIPPED_DRYO_LOG.get(), STRIPPED_DRYO_WOOD.get());
        this.tag(UPBlockTags.FOXII_LOGS).add(FOXII_LOG.get(), FOXII_WOOD.get(), STRIPPED_FOXII_LOG.get(), STRIPPED_FOXII_WOOD.get());
        this.tag(UPBlockTags.GINKGO_LOGS).add(GINKGO_LOG.get(), GINKGO_WOOD.get(), STRIPPED_GINKGO_LOG.get(), STRIPPED_GINKGO_WOOD.get());
        this.tag(UPBlockTags.PETRIFIED_LOGS).add(PETRIFIED_LOG.get(), PETRIFIED_WOOD.get(), STRIPPED_PETRIFIED_LOG.get(), STRIPPED_PETRIFIED_WOOD.get());

        this.tag(UPBlockTags.ZULOAGAE_PLANTABLE_ON)
                .addTag(BlockTags.SAND)
                .addTag(BlockTags.DIRT)
                .add(ZULOAGAE.get(), ZULOAGAE_SAPLING.get(), Blocks.GRAVEL);

        this.tag(UPBlockTags.DINO_NATURAL_SPAWNABLE);

        this.tag(UPBlockTags.TYRANNO_BREAKABLES)
                .addTag(BlockTags.LEAVES)
                .addTag(BlockTags.PLANKS)
                .addTag(BlockTags.LOGS)
                .addTag(BlockTags.WOODEN_STAIRS)
                .addTag(BlockTags.WOODEN_SLABS)
                .addTag(BlockTags.WOOL)
                .add(Blocks.LILY_PAD, Blocks.CACTUS, Blocks.BAMBOO)
        ;

        this.tag(UPBlockTags.DIPLO_BURROWS)
                .addTag(BlockTags.SAND)
                .add(Blocks.MUD)
        ;

        this.tag(UPBlockTags.TRIKE_GRAZING_BLOCKS).add(
                Blocks.GRASS_BLOCK,
                Blocks.FERN,
                Blocks.LARGE_FERN,
                Blocks.GRASS,
                HORSETAIL.get(),
                TALL_HORSETAIL.get()
        );

        this.tag(UPBlockTags.COTY_GRAZING_BLOCKS).add(
                Blocks.GRASS_BLOCK,
                Blocks.FERN,
                Blocks.LARGE_FERN,
                Blocks.GRASS,
                Blocks.MELON,
                HORSETAIL.get(),
                TALL_HORSETAIL.get()
        );

        this.tag(UPBlockTags.MEGATHERIUM_MINEABLES)
                .addTag(BlockTags.LEAVES)
                .addTag(BlockTags.DIRT)
                .addTag(BlockTags.SAND)
                .addTag(BlockTags.PLANKS)
                .addTag(BlockTags.LOGS)
                .addTag(BlockTags.WOODEN_STAIRS)
                .addTag(BlockTags.WOODEN_SLABS)
                .add(Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW);

        this.tag(UPBlockTags.ERYON_DIGGABLES)
                .addTag(BlockTags.SAND);

        this.tag(UPBlockTags.TALPANAS_DIGGABLES).add(
                Blocks.ROOTED_DIRT
        );

        this.tag(UPBlockTags.EGG_ACCELERATORS)
                .addTag(BlockTags.WOOL)
                .add(Blocks.HAY_BLOCK);

        this.tag(UPBlockTags.VELOCI_BUTTONS).add(
                UPBlocks.AMBER_BUTTON.get()
        );

        this.tag(UPBlockTags.TAR_PIT_REPLACEABLE)
                .addTag(BlockTags.SAND)
                .addTag(BlockTags.DIRT)
                .addTag(BlockTags.BASE_STONE_OVERWORLD)
                .addTag(BlockTags.TERRACOTTA)
                .add(Blocks.SANDSTONE, Blocks.RED_SANDSTONE)
        ;

        this.tag(UPBlockTags.CLUB_WHITELIST_BLOCKS)
                .addTag(BlockTags.SAND)
                .addTag(BlockTags.DIRT)
                .addTag(BlockTags.BASE_STONE_OVERWORLD)
                .addTag(BlockTags.TERRACOTTA)
                .add(Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.TNT, Blocks.ANVIL, Blocks.CHIPPED_ANVIL, Blocks.DAMAGED_ANVIL);
    }
}
