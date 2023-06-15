package com.peeko32213.unusualprehistory.datagen;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ItemTagsGenerator extends ItemTagsProvider {
    public ItemTagsGenerator(DataGenerator generatorIn, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, blockTagProvider, UnusualPrehistory.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        /**Example**/
        //tag(UPTags.ALLOWED_FRIDGE_ITEMS).add(UPItems.AMBER_FOSSIL.get());
        tag(UPTags.ALLOWED_FRIDGE_ITEMS)
                .addTag(UPTags.DNA_FLASKS);

        tag(UPTags.FILLED_FLASKS)
                .addTag(UPTags.DNA_FLASKS);

        tag(UPTags.DNA_FLASKS)
                .add(UPItems.AMMONITE_FLASK.get())
                .add(UPItems.QUEREUXIA_FLASK.get())
                .add(UPItems.NELUMBITES_FLASK.get())
                .add(UPItems.CLATHRODICTYON_FLASK.get())
                .add(UPItems.ARCHAEFRUCTUS_FLASK.get())
                .add(UPItems.ANOSTYLOSTRAMA_FLASK.get())
                .add(UPItems.LEEFRUCTUS_FLASK.get())
                .add(UPItems.ARCHAO_FLASK.get())
                .add(UPItems.SARR_FLASK.get())
                .add(UPItems.BENNET_FLASK.get())
                .add(UPItems.GINKGO_FLASK.get())
                .add(UPItems.TALL_HORSETAIL_FLASK.get())
                .add(UPItems.HORSETAIL_FLASK.get())
                .add(UPItems.ERYON_FLASK.get())
                .add(UPItems.PACHY_FLASK.get())
                .add(UPItems.TRIKE_FLASK.get())
                .add(UPItems.RAPTOR_FLASK.get())
                .add(UPItems.REX_FLASK.get())
                .add(UPItems.BRACHI_FLASK.get())
                .add(UPItems.SCAU_FLASK.get())
                .add(UPItems.COTY_FLASK.get())
                .add(UPItems.ANURO_FLASK.get())
                .add(UPItems.BEELZ_FLASK.get())
                .add(UPItems.MAJUNGA_FLASK.get())
                .add(UPItems.DUNK_FLASK.get())
                .add(UPItems.STETHA_FLASK.get())
                .add(UPItems.HWACHA_FLASK.get())
                .add(UPItems.KENTRO_FLASK.get())
                .add(UPItems.ULUGH_FLASK.get())
                .add(UPItems.AUSTRO_FLASK.get())
                .add(UPItems.ANTARCTO_FLASK.get())
                .add(UPItems.ENCRUSTED_FLASK.get())
                .add(UPItems.GIGANTO_FLASK.get())
                .add(UPItems.SMILO_FLASK.get())
                .add(UPItems.MEGATH_FLASK.get())
                .add(UPItems.PARACER_FLASK.get())
                .add(UPItems.MAMMOTH_FLASK.get())
                .add(UPItems.BARIN_FLASK.get())
                .add(UPItems.PALAEO_FLASK.get())
                .add(UPItems.MEGALA_FLASK.get())
                .add(UPItems.TALAPANAS_FLASK.get());

        tag(UPTags.FOSSILS)
                .add(UPItems.AMBER_FOSSIL.get())
                .add(UPItems.PLANT_FOSSIL.get())
                .add(UPItems.MEZO_FOSSIL.get())
                .add(UPItems.PALEO_FOSSIL.get());

        tag(UPTags.ANALYZER_ITEMS_INPUT)
                .addTag(UPTags.FOSSILS);

        tag(UPTags.KENTRO_FOOD)
                .add(UPBlocks.HORSETAIL.get().asItem());

        tag(UPTags.MAJUNGA_FOOD)
                .add(UPItems.RAW_COTY.get());

        tag(UPTags.ORANGE_ULUGH_FOOD)
                .add(UPItems.RAW_COTY.get());
        tag(UPTags.YELLOW_ULUGH_FOOD)
                .add(UPItems.GOLDEN_SCAU.get());
        tag(UPTags.WHITE_ULUGH_FOOD)
                .add(UPItems.RAW_AUSTRO.get());
        tag(UPTags.BLUE_ULUGH_FOOD)
                .add(UPItems.RAW_SCAU.get());
        tag(UPTags.BROWN_ULUGH_FOOD)
                .add(UPItems.RAW_STETHA.get());
        tag(UPTags.TRIKE_FOOD)
                .add(UPItems.GINKGO_FRUIT.get());

        tag(UPTags.HWACHA_FOOD)
                .add(UPItems.MEATY_BUFFET.get());

        tag(UPTags.PACHY_FOOD)
                .add(UPItems.RAW_GINKGO_SEEDS.get());

        tag(UPTags.ORGANIC_OOZE)
                .add(UPItems.ORGANIC_OOZE.get());

        tag(ItemTags.LEAVES)
                .add(UPBlocks.FOXXI_LEAVES.get().asItem())
                .add(UPBlocks.DRYO_LEAVES.get().asItem())
                .add(UPBlocks.GINKGO_LEAVES.get().asItem());

        tag(UPTags.PETRIFIED_WOOD)
                .add(UPBlocks.PETRIFIED_WOOD_LOG.get().asItem())
                .add(UPBlocks.PETRIFIED_WOOD.get().asItem())
                .add(UPBlocks.STRIPPED_PETRIFIED_WOOD.get().asItem())
                .add(UPBlocks.STRIPPED_PETRIFIED_WOOD_LOG.get().asItem());

        tag(ItemTags.PLANKS)
                .add(UPBlocks.GINKGO_PLANKS.get().asItem())
                .add(UPBlocks.PETRIFIED_WOOD_PLANKS.get().asItem())
                .add(UPBlocks.DRYO_PLANKS.get().asItem())
                .add(UPBlocks.FOXXI_PLANKS.get().asItem());

        tag(ItemTags.SAPLINGS)
                .add(UPBlocks.GINKGO_SAPLING.get().asItem());

        tag(ItemTags.SIGNS)
                .add(UPBlocks.GINKGO_SIGN.get().asItem())
                .add(UPBlocks.PETRIFIED_WOOD_SIGN.get().asItem());

        tag(ItemTags.SMALL_FLOWERS)
                .add(UPBlocks.LEEFRUCTUS.get().asItem())
                .add(UPBlocks.SARACENIA.get().asItem())
                .add(UPBlocks.HORSETAIL.get().asItem())
                .add(UPBlocks.BENNETTITALES.get().asItem())
                .add(UPBlocks.ARCHAEOSIGILARIA.get().asItem())
                .add(UPBlocks.PETRIFIED_BUSH.get().asItem());

        tag(ItemTags.TALL_FLOWERS)
                .add(UPBlocks.TALL_SARACENIA.get().asItem())
                .add(UPBlocks.TALL_HORSETAIL.get().asItem());

        tag(ItemTags.WOODEN_FENCES)
                .add(UPBlocks.GINKGO_FENCE.get().asItem())
                .add(UPBlocks.FOXXI_FENCE.get().asItem())
                .add(UPBlocks.DRYO_FENCE.get().asItem())
                .add(UPBlocks.PETRIFIED_WOOD_FENCE.get().asItem());

        tag(ItemTags.FISHES)
                .add(UPItems.RAW_STETHA.get())
                .add(UPItems.COOKED_STETHA.get())
                .add(UPItems.RAW_SCAU.get())
                .add(UPItems.COOKED_SCAU.get())
                .add(UPItems.GOLDEN_SCAU.get());

        tag(ItemTags.BUTTONS)
                .add(UPBlocks.AMBER_BUTTON.get().asItem())
                .add(UPBlocks.GINKGO_BUTTON.get().asItem())
                .add(UPBlocks.FOXXI_BUTTON.get().asItem())
                .add(UPBlocks.DRYO_BUTTON.get().asItem())
                .add(UPBlocks.PETRIFIED_WOOD_BUTTON.get().asItem());

        tag(ItemTags.WOODEN_BUTTONS)
                .add(UPBlocks.GINKGO_BUTTON.get().asItem())
                .add(UPBlocks.FOXXI_BUTTON.get().asItem())
                .add(UPBlocks.DRYO_BUTTON.get().asItem())
                .add(UPBlocks.PETRIFIED_WOOD_BUTTON.get().asItem());


        tag(UPTags.ANALYZER_ITEMS_OUTPUT_MESOZOIC)
                .add(Items.COAL)
                .add(Items.COBBLESTONE)
                .add(Items.BONE)
                .add(UPItems.AMMONITE_FLASK.get())
                .add(UPItems.ANURO_FLASK.get())
                .add(UPItems.BEELZ_FLASK.get())
                .add(UPItems.KENTRO_FLASK.get())
                .add(UPItems.MAJUNGA_FLASK.get())
                .add(UPItems.ANTARCTO_FLASK.get())
                .add(UPItems.AUSTRO_FLASK.get())
                .add(UPItems.RAPTOR_FLASK.get())
                .add(UPItems.PACHY_FLASK.get())
                .add(UPItems.ERYON_FLASK.get())
                .add(UPItems.ULUGH_FLASK.get());

        tag(UPTags.ANALYZER_ITEMS_OUTPUT_AMBER)
                .add(UPItems.BRACHI_FLASK.get())
                .add(UPItems.REX_FLASK.get())
                .add(UPItems.TRIKE_FLASK.get())
                .add(UPItems.HWACHA_FLASK.get())
                .add(UPItems.ENCRUSTED_FLASK.get());

        tag(UPTags.ANALYZER_ITEMS_OUTPUT_ENCRUSTED)
                .add(UPItems.ENCRUSTED_FLASK.get());

        tag(UPTags.ANALYZER_ITEMS_OUTPUT_GINKGO)
                .add(UPItems.GINKGO_FLASK.get());


        tag(UPTags.ANALYZER_ITEMS_OUTPUT_PALEO)
                .add(Items.COAL)
                .add(Items.COBBLESTONE)
                .add(Items.BONE)
                .add(UPItems.AMMONITE_FLASK.get())
                .add(UPItems.SCAU_FLASK.get())
                .add(UPItems.COTY_FLASK.get())
                .add(UPItems.STETHA_FLASK.get())
                .add(UPItems.DUNK_FLASK.get());

        tag(UPTags.ANALYZER_ITEMS_OUTPUT_PLANT)
                .add(Items.COAL)
                .add(Items.COBBLESTONE)
                .add(Blocks.MOSS_BLOCK.asItem())
                .add(Blocks.MOSS_CARPET.asItem())
                .add(Blocks.FERN.asItem())
                .add(Blocks.LARGE_FERN.asItem())
                .add(UPItems.HORSETAIL_FLASK.get())
                .add(UPItems.NELUMBITES_FLASK.get())
                .add(UPItems.ANOSTYLOSTRAMA_FLASK.get())
                .add(UPItems.LEEFRUCTUS_FLASK.get())
                .add(UPItems.BENNET_FLASK.get())
                .add(UPItems.SARR_FLASK.get())
                .add(UPItems.ARCHAO_FLASK.get())
                .add(UPItems.QUEREUXIA_FLASK.get())
                .add(UPItems.GINKGO_FLASK.get())
                .add(UPItems.CLATHRODICTYON_FLASK.get())
                .add(UPItems.ARCHAEFRUCTUS_FLASK.get());

        tag(UPTags.ANALYZER_ITEMS_OUTPUT_RAW_COTY)
                .add(UPItems.COTY_FLASK.get());

        tag(UPTags.ANALYZER_ITEMS_OUTPUT_RAW_SCAU)
                .add(UPItems.SCAU_FLASK.get());
        tag(UPTags.ANALYZER_ITEMS_OUTPUT_RAW_STETHA)
                .add(UPItems.STETHA_FLASK.get());

        tag(UPTags.ANALYZER_ITEMS_OUTPUT_TREE)
                .add(Items.COAL)
                .add(Items.COBBLESTONE)
                .add(UPItems.GINKGO_FLASK.get());
    }



    @Override
    public String getName() { return UnusualPrehistory.MODID + " Item Tags";}
}
