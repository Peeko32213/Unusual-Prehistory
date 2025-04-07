package com.peeko32213.unusualprehistory.data.server.tags;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.other.tags.UPItemTags;
import com.peeko32213.unusualprehistory.core.registry.blocks.UPBlocks;
import com.teamabnormals.blueprint.core.data.server.tags.BlueprintItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.peeko32213.unusualprehistory.core.registry.items.UPItems.*;

public class UPItemTagsProvider extends BlueprintItemTagsProvider {

    public UPItemTagsProvider(PackOutput output, CompletableFuture<Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> lookup, ExistingFileHelper helper) {
        super(UnusualPrehistory.MODID, output, provider, lookup, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.copyWoodsetTags();

        this.copy(BlockTags.SLABS, ItemTags.SLABS);
        this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
        this.copy(BlockTags.WALLS, ItemTags.WALLS);
        this.copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
        this.copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);

        this.copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
        this.copy(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
        this.copy(BlockTags.FLOWERS, ItemTags.FLOWERS);

        this.tag(UPItemTags.ALLOWED_FRIDGE_ITEMS).addTag(UPItemTags.DNA_BOTTLES);
        this.tag(UPItemTags.FILLED_FLASKS).addTag(UPItemTags.DNA_BOTTLES);

        this.tag(UPItemTags.DNA_BOTTLES).add(
                AMMONITE_DNA.get(),
                QUEREUXIA_DNA.get(),
                NELUMBITES_DNA.get(),
                CLATHRODICTYON_DNA.get(),
                ARCHAEFRUCTUS_DNA.get(),
                ANOSTYLOSTRAMA_DNA.get(),
                LEEFRUCTUS_DNA.get(),
                ARCHAO_DNA.get(),
                SARR_DNA.get(),
                BENNET_DNA.get(),
                HORSETAIL_DNA.get(),
                ERYON_DNA.get(),
                PACHY_DNA.get(),
                TRIKE_DNA.get(),
                VELOCI_DNA.get(),
                TYRANNO_DNA.get(),
                BRACHI_DNA.get(),
                SCAU_DNA.get(),
                COTY_DNA.get(),
                ANURO_DNA.get(),
                BEELZ_DNA.get(),
                MAJUNGA_DNA.get(),
                DUNK_DNA.get(),
                STETHA_DNA.get(),
                HWACHA_DNA.get(),
                KENTRO_DNA.get(),
                ULUGH_DNA.get(),
                AUSTRO_DNA.get(),
                ANTARCTO_DNA.get(),
                ENCRUSTED_DNA.get(),
                GIGANTO_DNA.get(),
                SMILODON_DNA.get(),
                TELECREX_DNA.get(),
                MEGATHERIUM_DNA.get(),
                PARACER_DNA.get(),
                MAMMOTH_DNA.get(),
                BARINA_DNA.get(),
                PALAEO_DNA.get(),
                MEGALANIA_DNA.get(),
                TALPANAS_DNA.get(),
                ZULOAGAE_DNA.get(),
                RAIGUENRAYUN_DNA.get(),
                FOXII_DNA.get(),
                GINKGO_DNA.get(),
                DRYO_DNA.get(),
                KIMMER_DNA.get(),
                DIPLO_DNA.get(),
                HYNERIA_DNA.get(),
                OTAROCYON_DNA.get(),
                LONGI_DNA.get(),
                JAWLESS_FISH_DNA.get(),
                TARTUO_DNA.get(),
                TANY_DNA.get(),
                PSITTACO_DNA.get(),
                KAPRO_DNA.get(),
                PSILO_DNA.get(),
                OPHIO_DNA.get(),
                HYNERP_DNA.get(),
                BALAUR_DNA.get(),
                PTERY_DNA.get(),
                EDAPHO_DNA.get(),
                HYNERP_DNA.get(),
                LEEDS_DNA.get(),
                PTERODAUSTRO_DNA.get(),
                XIPHACT_DNA.get(),
                OVIRAPTOR_DNA.get(),
                GLOBIDENS_DNA.get(),
                ARCHELON_DNA.get(),
                ESTEMMENO_DNA.get(),
                PANACANTHOCARIS_DNA.get()
        );

        this.tag(UPItemTags.FOSSILS).add(
                AMBER_FOSSIL.get(),
                PLANT_FOSSIL.get(),
                MEZO_FOSSIL.get(),
                PALEO_FOSSIL.get(),
                FROZEN_FOSSIL.get(),
                OPAL_FOSSIL.get(), FIRE_OPAL_FOSSIL.get(), BOULDER_OPAL_FOSSIL.get(), BLACK_OPAL_FOSSIL.get(),
                TAR_FOSSIL.get()
        );

        this.tag(UPItemTags.ANALYZER_ITEMS_INPUT).addTag(UPItemTags.FOSSILS);

        this.tag(UPItemTags.KENTRO_FOOD).add(
                UPBlocks.HORSETAIL.get().asItem(),
                UPBlocks.TALL_HORSETAIL.get().asItem()
        );

        this.tag(UPItemTags.MAJUNGA_FOOD).add(
                RAW_COTY.get()
        );

        this.tag(UPItemTags.ULUGH_FOOD).add(
                RAW_COTY.get()
        );

        this.tag(UPItemTags.TRICERATOPS_FOOD).add(
                GINKGO_FRUIT.get(),
                UPBlocks.HORSETAIL.get().asItem(),
                UPBlocks.TALL_HORSETAIL.get().asItem(),
                Items.SWEET_BERRIES
        );

        this.tag(UPItemTags.TRICERATOPS_TAMES).add(
                GINKGO_FRUIT.get()
        );

        this.tag(UPItemTags.COTY_FOOD).add(
                Items.MELON_SLICE,
                Items.MELON_SEEDS,
                Items.GLISTERING_MELON_SLICE
        );

        this.tag(UPItemTags.COTY_FERMENTERS).add(
                Items.SWEET_BERRIES,
                Items.GLOW_BERRIES
        );

        this.tag(UPItemTags.HWACHA_FOOD);

        this.tag(UPItemTags.HWACHA_TAMES).add(
                Items.BEEF,
                Items.PORKCHOP,
                Items.CHICKEN,
                Items.MUTTON,
                Items.RABBIT
        );

        this.tag(UPItemTags.BARINA_FOOD).add(
                Items.BEEF,
                Items.PORKCHOP,
                Items.CHICKEN,
                Items.MUTTON,
                Items.RABBIT
        );

        this.tag(UPItemTags.PACHY_FOOD)
                .add(RAW_GINKGO_SEEDS.get());

        this.tag(UPItemTags.EDAPHO_FOOD_ITEMS).add(
                RAW_GINKGO_SEEDS.get(),
                GINKGO_FRUIT.get(),
                UPBlocks.HORSETAIL.get().asItem(),
                UPBlocks.TALL_HORSETAIL.get().asItem(),
                Items.CARROT,
                Items.POTATO
        );

        this.tag(UPItemTags.DUNK_FOOD_PASSIFY).add(
                GOLDEN_SCAU.get()
        );

        this.tag(UPItemTags.DUNK_FOOD).add(
                RAW_SCAU.get()
        );

        this.tag(UPItemTags.ESTEMME_FOOD_ITEMS).add(
                RAW_COTY.get(),
                GINKGO_FRUIT.get(),
                UPBlocks.HORSETAIL.get().asItem(),
                UPBlocks.TALL_HORSETAIL.get().asItem(),
                Items.MELON_SLICE,
                Items.APPLE,
                Items.SWEET_BERRIES,
                Items.GLOW_BERRIES,
                Items.BEEF,
                Items.PORKCHOP,
                Items.CHICKEN,
                Items.RABBIT,
                Items.MUTTON
        );

        this.tag(UPItemTags.UNICORN_FOOD_ITEMS).add(
                Items.COOKIE,
                Items.PUMPKIN_PIE,
                Items.CAKE
        );

        this.tag(UPItemTags.ORGANIC_OOZE).add(
                ORGANIC_OOZE.get()
        );

        this.tag(UPItemTags.HYNERPETON_IGNITERS).add(
                Items.FLINT_AND_STEEL,
                Items.FIRE_CHARGE
        );

        this.tag(UPItemTags.TELECREX_FOOD).add(
                Items.MELON_SEEDS,
                Items.BEETROOT_SEEDS,
                Items.PUMPKIN_SEEDS,
                Items.WHEAT_SEEDS
        );

        this.tag(UPItemTags.OPAL_GEMS).add(
                OPAL.get(),
                FIRE_OPAL.get(),
                BOULDER_OPAL.get(),
                BLACK_OPAL.get()
        );

        this.tag(UPItemTags.HIDDEN_ITEMS).add(
                UNICORN_SKELETON.get(),
                UNICORN_EMBRYO.get(),
                PLANT_DNA_BOTTLES.get(),
                ANIMAL_DNA_BOTTLES.get(),
                UPBlocks.FOSSIL_ORES.get().asItem()
        );

        this.tag(ItemTags.FISHES).add(
                RAW_SCAU.get(),
                COOKED_SCAU.get(),
                GOLDEN_SCAU.get()
        );

        this.tag(ItemTags.MUSIC_DISCS).add(
                ZULOGAE_DISC.get(),
                ENCASED_DISC.get(),
                OPALESENCE_DISC.get()
        );

        this.tag(ItemTags.ARROWS).add(
                PSITTACCO_ARROW.get()
        );

    }

    @Override
    public void copyWoodsetTags() {
        this.copyWoodenTags();
        this.copyLeavesTags();
    }

    @Override
    public void copyLeavesTags() {
        this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
    }

    @Override
    public void copyWoodenTags() {
        this.copyWoodenTags(true);
    }

    @Override
    public void copyWoodenTags(boolean flammable) {
        this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
        this.copy(!flammable ? BlockTags.LOGS : BlockTags.LOGS_THAT_BURN, !flammable ? ItemTags.LOGS : ItemTags.LOGS_THAT_BURN);
        this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        this.copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
        this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        this.copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
        this.copy(BlockTags.CEILING_HANGING_SIGNS, ItemTags.HANGING_SIGNS);
    }
}
