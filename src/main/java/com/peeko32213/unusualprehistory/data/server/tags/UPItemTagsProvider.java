package com.peeko32213.unusualprehistory.data.server.tags;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.other.tags.UPItemTags;
import com.peeko32213.unusualprehistory.core.registry.blocks.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class UPItemTagsProvider extends ItemTagsProvider {
    public UPItemTagsProvider(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                              CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, UnusualPrehistory.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {

        tag(UPItemTags.ALLOWED_FRIDGE_ITEMS)
                .addTag(UPItemTags.DNA_FLASKS);

        tag(UPItemTags.FILLED_FLASKS)
                .addTag(UPItemTags.DNA_FLASKS);

        tag(UPItemTags.DNA_FLASKS)
                .add(UPItems.AMMONITE_DNA.get())
                .add(UPItems.QUEREUXIA_DNA.get())
                .add(UPItems.NELUMBITES_DNA.get())
                .add(UPItems.CLATHRODICTYON_DNA.get())
                .add(UPItems.ARCHAEFRUCTUS_DNA.get())
                .add(UPItems.ANOSTYLOSTRAMA_DNA.get())
                .add(UPItems.LEEFRUCTUS_DNA.get())
                .add(UPItems.ARCHAO_DNA.get())
                .add(UPItems.SARR_DNA.get())
                .add(UPItems.BENNET_DNA.get())
                .add(UPItems.HORSETAIL_DNA.get())
                .add(UPItems.ERYON_DNA.get())
                .add(UPItems.PACHY_DNA.get())
                .add(UPItems.TRIKE_DNA.get())
                .add(UPItems.VELOCI_DNA.get())
                .add(UPItems.TYRANNO_DNA.get())
                .add(UPItems.BRACHI_DNA.get())
                .add(UPItems.SCAU_DNA.get())
                .add(UPItems.COTY_DNA.get())
                .add(UPItems.ANURO_DNA.get())
                .add(UPItems.BEELZ_DNA.get())
                .add(UPItems.MAJUNGA_DNA.get())
                .add(UPItems.DUNK_DNA.get())
                .add(UPItems.STETHA_DNA.get())
                .add(UPItems.HWACHA_DNA.get())
                .add(UPItems.KENTRO_DNA.get())
                .add(UPItems.ULUGH_DNA.get())
                .add(UPItems.AUSTRO_DNA.get())
                .add(UPItems.ANTARCTO_DNA.get())
                .add(UPItems.ENCRUSTED_DNA.get())
                .add(UPItems.GIGANTO_DNA.get())
                .add(UPItems.SMILODON_DNA.get())
                .add(UPItems.MEGATHERIUM_DNA.get())
                .add(UPItems.PARACER_DNA.get())
                .add(UPItems.MAMMOTH_DNA.get())
                .add(UPItems.BARINA_DNA.get())
                .add(UPItems.PALAEO_DNA.get())
                .add(UPItems.MEGALANIA_DNA.get())
                .add(UPItems.TALPANAS_DNA.get())
                .add(UPItems.ZULOAGAE_DNA.get())
                .add(UPItems.RAIGUENRAYUN_DNA.get())
                .add(UPItems.FOXII_DNA.get())
                .add(UPItems.GINKGO_DNA.get())
                .add(UPItems.DRYO_DNA.get())
                .add(UPItems.KIMMER_DNA.get())
                .add(UPItems.DIPLO_DNA.get())
                .add(UPItems.HYNERIA_DNA.get())
                .add(UPItems.OTAROCYON_DNA.get())
                .add(UPItems.LONGI_DNA.get())
                .add(UPItems.JAWLESS_FISH_DNA.get())
                .add(UPItems.TARTUO_DNA.get())
                .add(UPItems.TANY_DNA.get())
                .add(UPItems.PSITTACO_DNA.get())
                .add(UPItems.KAPRO_DNA.get())
                .add(UPItems.PSILO_DNA.get())
                .add(UPItems.OPHIO_DNA.get())
                .add(UPItems.HYNERP_DNA.get())
                .add(UPItems.BALAUR_DNA.get())
                .add(UPItems.PTERY_DNA.get())
                .add(UPItems.EDAPHO_DNA.get())
                .add(UPItems.HYNERP_DNA.get())
                .add(UPItems.LEEDS_DNA.get())
                .add(UPItems.PTERODAUSTRO_DNA.get())
                .add(UPItems.XIPHACT_DNA.get())
                .add(UPItems.OVIRAPTOR_DNA.get())
                .add(UPItems.GLOBIDENS_DNA.get())
                .add(UPItems.ARCHELON_DNA.get())
                .add(UPItems.ESTEMMENO_DNA.get())
        ;

        tag(UPItemTags.FOSSILS)
                .add(UPItems.AMBER_FOSSIL.get())
                .add(UPItems.PLANT_FOSSIL.get())
                .add(UPItems.MEZO_FOSSIL.get())
                .add(UPItems.PALEO_FOSSIL.get())
                .add(UPItems.FROZEN_FOSSIL.get())
                .add(UPItems.OPAL_FOSSIL.get())
                .add(UPItems.FIRE_OPAL_FOSSIL.get())
                .add(UPItems.BOULDER_OPAL_FOSSIL.get())
                .add(UPItems.BLACK_OPAL_FOSSIL.get())
                .add(UPItems.TAR_FOSSIL.get());

        tag(UPItemTags.ANALYZER_ITEMS_INPUT)
                .addTag(UPItemTags.FOSSILS)
                .addTag(UPItemTags.PETRIFIED_WOOD)
        ;

        tag(UPItemTags.KENTRO_FOOD)
                .add(UPBlocks.HORSETAIL.get().asItem())
                .add(UPBlocks.TALL_HORSETAIL.get().asItem())
        ;

        tag(UPItemTags.MAJUNGA_FOOD)
                .add(UPItems.RAW_COTY.get())
        ;

        tag(UPItemTags.ULUGH_FOOD)
                .add(UPItems.RAW_COTY.get())
        ;

        tag(UPItemTags.TRICERATOPS_FOOD)
                .add(UPItems.GINKGO_FRUIT.get())
                .add(UPBlocks.HORSETAIL.get().asItem())
                .add(UPBlocks.TALL_HORSETAIL.get().asItem())
                .add(Items.SWEET_BERRIES)
        ;

        tag(UPItemTags.TRICERATOPS_TAMES)
                .add(UPItems.GINKGO_FRUIT.get())
        ;

        tag(UPItemTags.COTY_FOOD)
                .add(Items.MELON_SLICE)
                .add(Items.MELON_SEEDS)
                .add(Items.GLISTERING_MELON_SLICE)
        ;
        tag(UPItemTags.COTY_FERMENTERS)
                .add(Items.SWEET_BERRIES)
                .add(Items.GLOW_BERRIES)
        ;

        tag(UPItemTags.HWACHA_FOOD)
        ;

        tag(UPItemTags.HWACHA_TAMES)
                .add(Items.BEEF)
                .add(Items.PORKCHOP)
                .add(Items.CHICKEN)
                .add(Items.MUTTON)
                .add(Items.RABBIT)
        ;

        tag(UPItemTags.BARINA_FOOD)
                .add(Items.BEEF)
                .add(Items.PORKCHOP)
                .add(Items.CHICKEN)
                .add(Items.MUTTON)
                .add(Items.RABBIT)
        ;

        tag(UPItemTags.PACHY_FOOD)
                .add(UPItems.RAW_GINKGO_SEEDS.get());

        tag(UPItemTags.EDAPHO_FOOD_ITEMS)
                .add(UPItems.RAW_GINKGO_SEEDS.get())
                .add(UPItems.GINKGO_FRUIT.get())
                .add(UPBlocks.HORSETAIL.get().asItem())
                .add(UPBlocks.TALL_HORSETAIL.get().asItem())
                .add(Items.CARROT)
                .add(Items.POTATO)
        ;

        tag(UPItemTags.ESTEMME_FOOD_ITEMS)
                .add(UPItems.RAW_COTY.get())
                .add(UPItems.GINKGO_FRUIT.get())
                .add(UPBlocks.HORSETAIL.get().asItem())
                .add(UPBlocks.TALL_HORSETAIL.get().asItem())
                .add(Items.MELON_SLICE)
                .add(Items.APPLE)
                .add(Items.SWEET_BERRIES)
                .add(Items.GLOW_BERRIES)
                .add(Items.BEEF)
                .add(Items.PORKCHOP)
                .add(Items.CHICKEN)
                .add(Items.RABBIT)
                .add(Items.MUTTON)
        ;

        tag(UPItemTags.ORGANIC_OOZE)
                .add(UPItems.ORGANIC_OOZE.get());

        tag(ItemTags.LEAVES)
                .add(UPBlocks.DRYO_LEAVES.get().asItem())
                .add(UPBlocks.GINKGO_LEAVES.get().asItem())
        ;

        tag(UPItemTags.PETRIFIED_WOOD)
                .add(UPBlocks.PETRIFIED_WOOD.get().asItem())
                .add(UPBlocks.STRIPPED_PETRIFIED_WOOD.get().asItem())
        ;

        tag(UPItemTags.FOXXI)
        ;

        tag(UPItemTags.DRYO)
                .add(UPBlocks.DRYO_LOG.get().asItem())
                .add(UPBlocks.DRYO_WOOD.get().asItem())
                .add(UPBlocks.STRIPPED_DRYO_WOOD.get().asItem())
                .add(UPBlocks.STRIPPED_DRYO_LOG.get().asItem())
        ;

        tag(UPItemTags.ZULOAGAE)
                .add(UPBlocks.ZULOAGAE_BLOCK.get().asItem())
                .add(UPBlocks.STRIPPED_ZULOAGAE_BLOCK.get().asItem())
        ;

        tag(UPItemTags.HYNERPETON_IGNITERS)
                .add(Items.FLINT_AND_STEEL)
                .add(Items.FIRE_CHARGE)
        ;

        tag(UPItemTags.OPAL_GEMS)
                .add(UPItems.OPAL.get())
                .add(UPItems.FIRE_OPAL.get())
                .add(UPItems.BOULDER_OPAL.get())
                .add(UPItems.BLACK_OPAL.get())
        ;

        tag(ItemTags.PLANKS)
                .add(UPBlocks.GINKGO_PLANKS.get().asItem())
                .add(UPBlocks.DRYO_PLANKS.get().asItem())
                .add(UPBlocks.ZULOAGAE_PLANKS.get().asItem())
        ;

        tag(ItemTags.LOGS_THAT_BURN)
                .add(UPBlocks.GINKGO_LOG.get().asItem())
                .add(UPBlocks.STRIPPED_GINKGO_LOG.get().asItem())
                .add(UPBlocks.GINKGO_WOOD.get().asItem())
                .add(UPBlocks.STRIPPED_GINKGO_WOOD.get().asItem())

                .add(UPBlocks.DRYO_LOG.get().asItem())
                .add(UPBlocks.STRIPPED_DRYO_LOG.get().asItem())
                .add(UPBlocks.DRYO_WOOD.get().asItem())
                .add(UPBlocks.STRIPPED_DRYO_WOOD.get().asItem())

                .add(UPBlocks.ZULOAGAE_BLOCK.get().asItem())
                .add(UPBlocks.STRIPPED_ZULOAGAE_BLOCK.get().asItem())

        ;

        tag(ItemTags.SAPLINGS)
                .add(UPBlocks.GINKGO_SAPLING.get().asItem())
                .add(UPBlocks.FOXII_SAPLING.get().asItem())
                .add(UPBlocks.DRYO_SAPLING.get().asItem())
                .add(UPBlocks.ZULOAGAE_SAPLING.get().asItem())
        ;

        tag(ItemTags.SMALL_FLOWERS)
                .add(UPBlocks.LEEFRUCTUS.get().asItem())
                .add(UPBlocks.SARACENIA.get().asItem())
                .add(UPBlocks.BENNETTITALES.get().asItem())
                .add(UPBlocks.ARCHAEOSIGILARIA.get().asItem());

        tag(ItemTags.TALL_FLOWERS)
                .add(UPBlocks.TALL_SARACENIA.get().asItem())
                .add(UPBlocks.RAIGUENRAYUN.get().asItem());

        tag(ItemTags.WOODEN_FENCES)
                .add(UPBlocks.GINKGO_FENCE.get().asItem())
                .add(UPBlocks.DRYO_FENCE.get().asItem())
                .add(UPBlocks.ZULOAGAE_FENCE.get().asItem());

        tag(ItemTags.WOODEN_DOORS)
                .add(UPBlocks.GINKGO_DOOR.get().asItem())
                .add(UPBlocks.DRYO_DOOR.get().asItem())
                .add(UPBlocks.ZULOAGAE_DOOR.get().asItem())
        ;

        tag(ItemTags.WOODEN_SLABS)
                .add(UPBlocks.GINKGO_SLAB.get().asItem())
                .add(UPBlocks.DRYO_SLAB.get().asItem())
                .add(UPBlocks.ZULOAGAE_SLAB.get().asItem())
        ;

        tag(ItemTags.WOODEN_STAIRS)
                .add(UPBlocks.GINKGO_STAIRS.get().asItem())
                .add(UPBlocks.DRYO_STAIRS.get().asItem())
                .add(UPBlocks.ZULOAGAE_STAIRS.get().asItem())
        ;

        tag(ItemTags.WOODEN_PRESSURE_PLATES)
                .add(UPBlocks.GINKGO_PRESSURE_PLATE.get().asItem())
                .add(UPBlocks.DRYO_PRESSURE_PLATE.get().asItem())
                .add(UPBlocks.ZULOAGAE_PRESSURE_PLATE.get().asItem())
        ;

        tag(ItemTags.WOODEN_TRAPDOORS)
                .add(UPBlocks.GINKGO_TRAPDOOR.get().asItem())
                .add(UPBlocks.DRYO_TRAPDOOR.get().asItem())
                .add(UPBlocks.ZULOAGAE_TRAPDOOR.get().asItem())
        ;

        tag(ItemTags.FISHES)
                .add(UPItems.RAW_SCAU.get())
                .add(UPItems.COOKED_SCAU.get())
                .add(UPItems.GOLDEN_SCAU.get())
        ;

        tag(ItemTags.BUTTONS)
                .add(UPBlocks.AMBER_BUTTON.get().asItem())
                .add(UPBlocks.GINKGO_BUTTON.get().asItem())
                .add(UPBlocks.DRYO_BUTTON.get().asItem())
                .add(UPBlocks.ZULOAGAE_BUTTON.get().asItem());
        ;

        tag(ItemTags.WOODEN_BUTTONS)
                .add(UPBlocks.GINKGO_BUTTON.get().asItem())
                .add(UPBlocks.DRYO_BUTTON.get().asItem())
                .add(UPBlocks.ZULOAGAE_BUTTON.get().asItem())
        ;

        tag(UPItemTags.ANALYZER_ITEMS_OUTPUT_MESOZOIC)
                .add(Items.COAL)
                .add(Items.COBBLESTONE)
                .add(Items.BONE)
                .add(UPItems.AMMONITE_DNA.get())
                .add(UPItems.ANURO_DNA.get())
                .add(UPItems.BEELZ_DNA.get())
                .add(UPItems.KENTRO_DNA.get())
                .add(UPItems.MAJUNGA_DNA.get())
                .add(UPItems.ANTARCTO_DNA.get())
                .add(UPItems.AUSTRO_DNA.get())
                .add(UPItems.VELOCI_DNA.get())
                .add(UPItems.PACHY_DNA.get())
                .add(UPItems.ERYON_DNA.get())
                .add(UPItems.ULUGH_DNA.get())
                .add(UPItems.KIMMER_DNA.get())
        ;

        tag(UPItemTags.ANALYZER_ITEMS_OUTPUT_AMBER)
                .add(UPItems.BRACHI_DNA.get())
                .add(UPItems.TYRANNO_DNA.get())
                .add(UPItems.TRIKE_DNA.get())
                .add(UPItems.HWACHA_DNA.get())
                .add(UPItems.ENCRUSTED_DNA.get())
        ;

        tag(UPItemTags.ANALYZER_ITEMS_OUTPUT_ENCRUSTED)
                .add(UPItems.ENCRUSTED_DNA.get());

        tag(UPItemTags.ANALYZER_ITEMS_OUTPUT_GINKGO)
                .add(UPItems.GINKGO_DNA.get());

        tag(UPItemTags.ANALYZER_ITEMS_OUTPUT_PETRIFIED)
                .add(UPItems.GINKGO_DNA.get())
                .add(UPItems.FOXII_DNA.get())
                .add(UPItems.DRYO_DNA.get());


        tag(UPItemTags.ANALYZER_ITEMS_OUTPUT_PALEO)
                .add(Items.COAL)
                .add(Items.COBBLESTONE)
                .add(Items.BONE)
                .add(UPItems.AMMONITE_DNA.get())
                .add(UPItems.SCAU_DNA.get())
                .add(UPItems.COTY_DNA.get())
                .add(UPItems.STETHA_DNA.get())
                .add(UPItems.DUNK_DNA.get())
                .add(UPItems.DIPLO_DNA.get())
                .add(UPItems.HYNERIA_DNA.get())
        ;

        tag(UPItemTags.ANALYZER_ITEMS_OUTPUT_PLANT)
                .add(Items.COAL)
                .add(Items.COBBLESTONE)
                .add(Blocks.MOSS_BLOCK.asItem())
                .add(Blocks.MOSS_CARPET.asItem())
                .add(Blocks.FERN.asItem())
                .add(Blocks.LARGE_FERN.asItem())
                .add(UPItems.HORSETAIL_DNA.get())
                .add(UPItems.NELUMBITES_DNA.get())
                .add(UPItems.ANOSTYLOSTRAMA_DNA.get())
                .add(UPItems.LEEFRUCTUS_DNA.get())
                .add(UPItems.BENNET_DNA.get())
                .add(UPItems.SARR_DNA.get())
                .add(UPItems.ARCHAO_DNA.get())
                .add(UPItems.QUEREUXIA_DNA.get())
                .add(UPItems.GINKGO_DNA.get())
                .add(UPItems.CLATHRODICTYON_DNA.get())
                .add(UPItems.ARCHAEFRUCTUS_DNA.get())
                .add(UPItems.ZULOAGAE_DNA.get())
                .add(UPItems.RAIGUENRAYUN_DNA.get())
                .add(UPItems.FOXII_DNA.get())
                .add(UPItems.DRYO_DNA.get());

        tag(UPItemTags.ANALYZER_ITEMS_OUTPUT_FROZEN)
                .add(Items.ICE)
                .add(UPItems.MAMMOTH_DNA.get())
                .add(UPItems.MEGATHERIUM_DNA.get())
                .add(UPItems.SMILODON_DNA.get());

        tag(UPItemTags.ANALYZER_ITEMS_OUTPUT_TAR)
                .add(Items.COAL)
                .add(UPItems.MEGALANIA_DNA.get())
                .add(UPItems.BARINA_DNA.get())
                .add(UPItems.PARACER_DNA.get())
                .add(UPItems.GIGANTO_DNA.get())
                .add(UPItems.TALPANAS_DNA.get())
        ;

        tag(UPItemTags.ANALYZER_ITEMS_OUTPUT_OPAL)
                .add(UPItems.PALAEO_DNA.get())
        ;

        tag(UPItemTags.ANALYZER_ITEMS_OUTPUT_RAW_COTY)
                .add(UPItems.COTY_DNA.get());

        tag(UPItemTags.ANALYZER_ITEMS_OUTPUT_TREE)
                .add(Items.COAL)
                .add(Items.COBBLESTONE)
                .add(UPItems.GINKGO_DNA.get());

        tag(ItemTags.MUSIC_DISCS)
                .add(UPItems.ZULOGAE_DISC.get())
                .add(UPItems.ENCASED_DISC.get())
        ;

        tag(ItemTags.ARROWS)
                .add(UPItems.PSITTACCO_ARROW.get())
        ;

    }

    @Override
    public @NotNull String getName() { return UnusualPrehistory.MODID + " Item Tags";}

}
