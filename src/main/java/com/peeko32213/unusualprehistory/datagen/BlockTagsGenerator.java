package com.peeko32213.unusualprehistory.datagen;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockTagsGenerator extends BlockTagsProvider {
    public BlockTagsGenerator(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, UnusualPrehistory.MODID, helper);
    }

    @Override
    protected void addTags(){
        /**Example**/
        //tag(BlockTags.MINEABLE_WITH_HOE)
        //        .add(UPBlocks.AMBER_BLOCK.get());
        //UP Block Tags

        tag(UPTags.DINO_NATURAL_SPAWNABLE)
                .addTag(BlockTags.ANIMALS_SPAWNABLE_ON)
                .addTags(BlockTags.TERRACOTTA)
                .addTags(BlockTags.SAND);


        tag(UPTags.ANGRY_BRACHI_BREAKABLES)
                .addTag(BlockTags.LEAVES)
                .addTag(BlockTags.PLANKS)
                .addTag(BlockTags.LOGS)
                .addTag(BlockTags.WOODEN_STAIRS)
                .addTag(BlockTags.WOODEN_SLABS)
                .addTag(BlockTags.WOOL)
                .addTag(BlockTags.CROPS)
                .addTag(BlockTags.FLOWERS)
                .add(Blocks.LILY_PAD)
                .add(Blocks.CACTUS)
                .add(Blocks.BAMBOO)
                .add(Blocks.GRASS)
                .add(Blocks.FERN)
                .add(Blocks.LARGE_FERN)
                .add(Blocks.TALL_GRASS);

        tag(UPTags.PASSIVE_BRACHI_BREAKABLES)
                .addTag(BlockTags.LEAVES)
                .addTag(BlockTags.PLANKS)
                .addTag(BlockTags.CROPS)
                .addTag(BlockTags.FLOWERS)
                .add(Blocks.LILY_PAD)
                .add(Blocks.CACTUS)
                .add(Blocks.BAMBOO)
                .add(Blocks.GRASS)
                .add(Blocks.FERN)
                .add(Blocks.LARGE_FERN)
                .add(Blocks.TALL_GRASS);

        tag(UPTags.REX_BREAKABLES)
                .addTag(BlockTags.LEAVES)
                .addTag(BlockTags.PLANKS)
                .addTag(BlockTags.LOGS)
                .addTag(BlockTags.WOODEN_STAIRS)
                .addTag(BlockTags.WOODEN_SLABS)
                .addTag(BlockTags.WOOL)
                .addTag(BlockTags.CROPS)
                .addTag(BlockTags.FLOWERS)
                .add(Blocks.LILY_PAD)
                .add(Blocks.CACTUS)
                .add(Blocks.BAMBOO)
                .add(Blocks.GRASS)
                .add(Blocks.FERN)
                .add(Blocks.LARGE_FERN)
                .add(Blocks.TALL_GRASS);

        tag(UPTags.TRIKE_BREAKABLES)
                .addTag(BlockTags.LEAVES)
                .addTag(BlockTags.PLANKS)
                .addTag(BlockTags.LOGS)
                .addTag(BlockTags.WOODEN_STAIRS)
                .addTag(BlockTags.WOODEN_SLABS)
                .addTag(BlockTags.WOOL)
                .add(Blocks.LILY_PAD)
                .add(Blocks.CACTUS)
                .add(Blocks.BAMBOO);

        tag(UPTags.MEGATHERIUM_EATABLES)
                .addTag(BlockTags.LEAVES);

        tag(UPTags.ERYON_DIGGABLES)
                .add(Blocks.SAND);

        tag(UPTags.TALAPANAS_DIGGABLES)
                .add(Blocks.ROOTED_DIRT);


        tag(UPTags.DINO_HATCHABLE_BLOCKS)
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
                .add(UPBlocks.GINKGO_FENCE_GATE.get())
                .add(UPBlocks.FOXXI_FENCE_GATE.get())
                .add(UPBlocks.DRYO_FENCE_GATE.get())
                .add(UPBlocks.PETRIFIED_WOOD_FENCE_GATE.get());

        tag(BlockTags.FENCES)
                .add(UPBlocks.GINKGO_FENCE.get())
                .add(UPBlocks.FOXXI_FENCE.get())
                .add(UPBlocks.DRYO_FENCE.get())
                .add(UPBlocks.PETRIFIED_WOOD_FENCE.get());

        tag(BlockTags.LOGS)
                .add(UPBlocks.GINKGO_LOG.get())
                .add(UPBlocks.GINKGO_WOOD.get())
                .add(UPBlocks.STRIPPED_GINKGO_WOOD.get())
                .add(UPBlocks.STRIPPED_GINKGO_LOG.get())
                .add(UPBlocks.PETRIFIED_WOOD_LOG.get())
                .add(UPBlocks.PETRIFIED_WOOD.get())
                .add(UPBlocks.STRIPPED_PETRIFIED_WOOD.get())
                .add(UPBlocks.STRIPPED_PETRIFIED_WOOD_LOG.get())
                .add(UPBlocks.DRYO_LOG.get())
                .add(UPBlocks.DRYO_WOOD.get())
                .add(UPBlocks.STRIPPED_DRYO_WOOD.get())
                .add(UPBlocks.STRIPPED_DRYO_LOG.get())
                .add(UPBlocks.FOXXI_LOG.get())
                .add(UPBlocks.FOXXI_WOOD.get())
                .add(UPBlocks.STRIPPED_FOXXI_WOOD.get())
                .add(UPBlocks.STRIPPED_FOXXI_LOG.get());


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
                .add(UPBlocks.PETRIFIED_WOOD_LOG.get())
                .add(UPBlocks.PETRIFIED_WOOD.get())
                .add(UPBlocks.STRIPPED_PETRIFIED_WOOD.get())
                .add(UPBlocks.STRIPPED_PETRIFIED_WOOD_LOG.get())
                .add(UPBlocks.POLISHED_PETRIFIED_WOOD.get())
                .add(UPBlocks.POLISHED_PETRIFIED_WOOD_SLAB.get())
                .add(UPBlocks.POLISHED_PETRIFIED_WOOD_STAIRS.get())
                .add(UPBlocks.PETRIFIED_WOOD_STAIRS.get())
                .add(UPBlocks.PETRIFIED_WOOD_SLAB.get())
                .add(UPBlocks.PETRIFIED_WOOD_PLANKS.get())
                .add(UPBlocks.PETRIFIED_WOOD_PRESSURE_PLATE.get())
                .add(UPBlocks.PETRIFIED_WOOD_TRAPDOOR.get())
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
                .add(UPBlocks.PETRIFIED_WOOD_FENCE.get())
                .add(UPBlocks.PETRIFIED_WOOD_FENCE_GATE.get())
                .add(UPBlocks.PETRIFIED_WOOD_SIGN.get())
                .add(UPBlocks.PETRIFIED_WOOD_WALL_SIGN.get())
                .add(UPBlocks.PETRIFIED_WOOD_DOOR.get());

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(UPBlocks.REX_BOOMBOX.get());


        tag(BlockTags.NEEDS_IRON_TOOL)
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
                .add(UPBlocks.PETRIFIED_WOOD_LOG.get())
                .add(UPBlocks.PETRIFIED_WOOD.get())
                .add(UPBlocks.STRIPPED_PETRIFIED_WOOD.get())
                .add(UPBlocks.STRIPPED_PETRIFIED_WOOD_LOG.get())
                .add(UPBlocks.POLISHED_PETRIFIED_WOOD.get())
                .add(UPBlocks.POLISHED_PETRIFIED_WOOD_SLAB.get())
                .add(UPBlocks.POLISHED_PETRIFIED_WOOD_STAIRS.get())
                .add(UPBlocks.PETRIFIED_WOOD_STAIRS.get())
                .add(UPBlocks.PETRIFIED_WOOD_SLAB.get())
                .add(UPBlocks.PETRIFIED_WOOD_PLANKS.get())
                .add(UPBlocks.PETRIFIED_WOOD_PRESSURE_PLATE.get())
                .add(UPBlocks.PETRIFIED_WOOD_TRAPDOOR.get())
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
                .add(UPBlocks.PETRIFIED_WOOD_FENCE.get())
                .add(UPBlocks.PETRIFIED_WOOD_FENCE_GATE.get())
                .add(UPBlocks.PETRIFIED_WOOD_SIGN.get())
                .add(UPBlocks.PETRIFIED_WOOD_WALL_SIGN.get())
                .add(UPBlocks.REX_BOOMBOX.get());

        tag(BlockTags.STANDING_SIGNS)
                .add(UPBlocks.GINKGO_SIGN.get())
                .add(UPBlocks.PETRIFIED_WOOD_SIGN.get());

        tag(BlockTags.TALL_FLOWERS)
                .add(UPBlocks.TALL_SARACENIA.get())
                .add(UPBlocks.TALL_HORSETAIL.get());


        tag(BlockTags.SMALL_FLOWERS)
                .add(UPBlocks.LEEFRUCTUS.get())
                .add(UPBlocks.SARACENIA.get())
                .add(UPBlocks.HORSETAIL.get())
                .add(UPBlocks.BENNETTITALES.get())
                .add(UPBlocks.ARCHAEOSIGILARIA.get())
                .add(UPBlocks.PETRIFIED_BUSH.get());


        tag(BlockTags.WALL_SIGNS)
                .add(UPBlocks.GINKGO_WALL_SIGN.get())
                .add(UPBlocks.PETRIFIED_WOOD_WALL_SIGN.get());

        tag(BlockTags.WOODEN_DOORS)
                .add(UPBlocks.GINKGO_DOOR.get())
                .add(UPBlocks.FOXXI_DOOR.get())
                .add(UPBlocks.DRYO_DOOR.get())
                .add(UPBlocks.PETRIFIED_WOOD_DOOR.get());

        tag(BlockTags.WOODEN_FENCES)
                .add(UPBlocks.GINKGO_FENCE.get())
                .add(UPBlocks.FOXXI_FENCE.get())
                .add(UPBlocks.DRYO_FENCE.get())
                .add(UPBlocks.PETRIFIED_WOOD_FENCE.get());

        tag(BlockTags.PRESSURE_PLATES)
                .add(UPBlocks.GINKGO_PRESSURE_PLATE.get())
                .add(UPBlocks.FOXXI_PRESSURE_PLATE.get())
                .add(UPBlocks.DRYO_PRESSURE_PLATE.get())
                .add(UPBlocks.PETRIFIED_WOOD_PRESSURE_PLATE.get());

        tag(BlockTags.SLABS)
                .add(UPBlocks.GINKGO_SLAB.get())
                .add(UPBlocks.PETRIFIED_WOOD_SLAB.get())
                .add(UPBlocks.FOXXI_SLAB.get())
                .add(UPBlocks.DRYO_SLAB.get())
                .add(UPBlocks.POLISHED_PETRIFIED_WOOD_SLAB.get());


        tag(BlockTags.STAIRS)
                .add(UPBlocks.GINKGO_STAIRS.get())
                .add(UPBlocks.PETRIFIED_WOOD_STAIRS.get())
                .add(UPBlocks.FOXXI_STAIRS.get())
                .add(UPBlocks.DRYO_STAIRS.get())
                .add(UPBlocks.POLISHED_PETRIFIED_WOOD_STAIRS.get());

        tag(BlockTags.TRAPDOORS)
                .add(UPBlocks.GINKGO_TRAPDOOR.get())
                .add(UPBlocks.FOXXI_TRAPDOOR.get())
                .add(UPBlocks.DRYO_TRAPDOOR.get())
                .add(UPBlocks.PETRIFIED_WOOD_TRAPDOOR.get());

        tag(BlockTags.BUTTONS)
                .add(UPBlocks.GINKGO_BUTTON.get())
                .add(UPBlocks.AMBER_BUTTON.get())
                .add(UPBlocks.FOXXI_BUTTON.get())
                .add(UPBlocks.DRYO_BUTTON.get())
                .add(UPBlocks.PETRIFIED_WOOD_BUTTON.get());

        tag(BlockTags.WOODEN_BUTTONS)
                .add(UPBlocks.GINKGO_BUTTON.get())
                .add(UPBlocks.FOXXI_BUTTON.get())
                .add(UPBlocks.DRYO_BUTTON.get())
                .add(UPBlocks.PETRIFIED_WOOD_BUTTON.get());

        tag(BlockTags.CORAL_BLOCKS)
                .add(UPBlocks.CLATHRODICTYON_BLOCK.get())
                .add(UPBlocks.ANOSTYLOSTROMA_BLOCK.get());

        tag(BlockTags.CORAL_PLANTS)
                .add(UPBlocks.CLATHRODICTYON.get());

        tag(BlockTags.CORALS)
                .add(UPBlocks.CLATHRODICTYON_FAN.get());

        tag(BlockTags.WALL_CORALS)
                .add(UPBlocks.CLATHRODICTYON_WALL_FAN.get());

        tag(BlockTags.LEAVES)
                .add(UPBlocks.FOXXI_LEAVES.get())
                .add(UPBlocks.DRYO_LEAVES.get())
                .add(UPBlocks.GINKGO_LEAVES.get());


        tag(UPTags.TAR_PIT_REPLACEABLE)
                .addTags(BlockTags.SAND)
                .addTags(BlockTags.DIRT)
                .addTags(BlockTags.BASE_STONE_OVERWORLD)
                .addTags(BlockTags.TERRACOTTA)
                .add(Blocks.SANDSTONE)
                .add(Blocks.RED_SANDSTONE);
    }


    @Override
    public String getName() {
        return UnusualPrehistory.MODID + " Block Tags";
    }
}
