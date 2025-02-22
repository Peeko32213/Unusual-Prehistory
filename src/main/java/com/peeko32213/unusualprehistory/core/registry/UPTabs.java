package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.data.ItemWeightedPairCodec;
import com.peeko32213.unusualprehistory.common.data.LootFruitCodec;
import com.peeko32213.unusualprehistory.common.data.LootFruitJsonManager;
import com.peeko32213.unusualprehistory.common.data.RollableItemCodec;
import com.peeko32213.unusualprehistory.common.item.MusicalTameItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UPTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UnusualPrehistory.MODID);

    private static final CreativeModeTab UP = new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 9)
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .title(Component.translatable("itemGroup.unusual_prehistory"))
            .icon(() -> new ItemStack(UPItems.ENCYLOPEDIA.get()))
            .displayItems((d, entries) ->{

                for(RegistryObject<Item> item : UPItems.ITEMS.getEntries()){
                    // Peeko's treacherous time travel logbook
                    entries.accept(UPItems.ENCYLOPEDIA.get());

                    // Fossils
                    entries.accept(UPItems.PALEO_FOSSIL.get());
                    entries.accept(UPItems.MEZO_FOSSIL.get());
                    entries.accept(UPItems.PLANT_FOSSIL.get());
                    entries.accept(UPItems.FROZEN_FOSSIL.get());
                    entries.accept(UPItems.DEFROSTED_FROZEN_FOSSIL.get());
                    entries.accept(UPItems.AMBER_SHARDS.get());
                    entries.accept(UPItems.AMBER_FOSSIL.get());
                    entries.accept(UPItems.OPAL_CHUNK.get());
                    entries.accept(UPItems.OPAL_FOSSIL.get());
                    entries.accept(UPBlocks.STONE_FOSSIL.get());
                    entries.accept(UPBlocks.DEEPSLATE_FOSSIL.get());
                    entries.accept(UPBlocks.PERMAFROST_FOSSIL.get());
                    entries.accept(UPBlocks.PERMAFROST.get());
                    entries.accept(UPBlocks.STONE_AMBER_FOSSIL.get());
                    entries.accept(UPBlocks.DEEPSLATE_AMBER_FOSSIL.get());
                    entries.accept(UPBlocks.STONE_OPAL_FOSSIL.get());
                    entries.accept(UPBlocks.DEEPSLATE_OPAL_FOSSIL.get());

                    // Misc amber & opal stuff
                    entries.accept(UPItems.TAR_BUCKET.get());
                    entries.accept(UPBlocks.SPLATTERED_TAR.get());
                    entries.accept(UPBlocks.ASPHALT.get());
                    entries.accept(UPBlocks.GOLD_ENGRAVED_ASPHALT.get());
                    entries.accept(UPBlocks.QUARTZ_ENGRAVED_ASPHALT.get());
                    entries.accept(UPBlocks.AMBER_BLOCK.get());
                    entries.accept(UPBlocks.AMBER_GLASS.get());
                    entries.accept(UPBlocks.AMBER_GLASS_PANE.get());
                    entries.accept(UPBlocks.AMBER_BUTTON.get());
                    entries.accept(UPItems.ADORNED_STAFF.get());
                    entries.accept(UPItems.AMBER_GUMMY.get());
                    entries.accept(UPItems.AMBER_IDOL.get());
                    entries.accept(UPBlocks.OPAL_BLOCK.get());
                    entries.accept(UPItems.OPALESCENT_PEARL.get());
                    entries.accept(UPItems.OPALESCENT_SHURIKEN.get());

                    // Discs
                    entries.accept(UPItems.ZULOGAE_DISC.get());
                    entries.accept(UPItems.ENCASED_DISC.get());

                    // Science gadgets and stuff
                    entries.accept(UPBlocks.ANALYZER.get());
                    entries.accept(UPBlocks.CULTIVATOR.get());
                    entries.accept(UPBlocks.INCUBATOR.get());
                    entries.accept(UPBlocks.DNA_FRIDGE.get());

                    // Mob items
                    entries.accept(UPItems.ORGANIC_OOZE.get());
                    entries.accept(UPItems.FROG_SALIVA.get());
                    entries.accept(UPItems.SHELL_SHARD.get());
                    entries.accept(UPBlocks.AMMONITE_SHELL.get());
                    entries.accept(UPItems.AUSTRO_FEATHER.get());
                    entries.accept(UPItems.RAPTOR_FEATHERS.get());
                    entries.accept(UPItems.ANTARCTO_PLATE.get());
                    entries.accept(UPItems.MAJUNGA_SCUTE.get());
                    entries.accept(UPItems.PSITTACOSAURUS_QUILL.get());
                    entries.accept(UPItems.QUILL_REMEDY.get());
                    entries.accept(UPItems.PSITTACCO_ARROW.get());
                    entries.accept(UPItems.TRIKE_HORN.get());
                    entries.accept(UPItems.REX_SCALE.get());
                    entries.accept(UPItems.REX_TOOTH.get());
                    entries.accept(UPItems.ENCRUSTED_ORGAN.get());
                    entries.accept(UPItems.PALAEO_SKIN.get());
                    entries.accept(UPItems.SMILO_FUR.get());
                    entries.accept(UPItems.INSULATOR.get());

                    // Foods
                    entries.accept(UPItems.RAW_COTY.get());
                    entries.accept(UPItems.COOKED_COTY.get());
                    entries.accept(UPItems.RAW_DUNK.get());
                    entries.accept(UPItems.COOKED_DUNK.get());
                    entries.accept(UPItems.RAW_JAWLESS_FISH.get());
                    entries.accept(UPItems.COOKED_JAWLESS_FISH.get());
                    entries.accept(UPItems.RAW_SCAU.get());
                    entries.accept(UPItems.COOKED_SCAU.get());
                    entries.accept(UPItems.GOLDEN_SCAU.get());
                    entries.accept(UPItems.RAW_STETHA.get());
                    entries.accept(UPItems.COOKED_STETHA.get());
                    entries.accept(UPItems.RAW_TARTU.get());
                    entries.accept(UPItems.COOKED_TARTU.get());
                    entries.accept(UPItems.LEEDS_SLICE.get());
                    entries.accept(UPItems.LEEDS_CAVIAR.get());
                    entries.accept(UPItems.RAW_OPHIODON.get());
                    entries.accept(UPItems.COOKED_OPHIODON.get());
                    entries.accept(UPItems.RAW_AUSTRO.get());
                    entries.accept(UPItems.COOKED_AUSTRO.get());
                    entries.accept(UPItems.MEATY_BUFFET.get());
                    entries.accept(UPItems.RAW_GINKGO_SEEDS.get());
                    entries.accept(UPItems.COOKED_GINKGO_SEEDS.get());
                    entries.accept(UPItems.GINKGO_FRUIT.get());
                    entries.accept(UPItems.DRYO_NUTS.get());
                    entries.accept(UPItems.RAW_MAMMOTH.get());
                    entries.accept(UPItems.COOKED_MAMMOTH.get());
                    entries.accept(UPItems.MAMMOTH_MEATBALL.get());

                    // Gambling fruit
                    addTagToLootFruit(entries, UPBlocks.FRUIT_LOOT_BOX.get().asItem(),CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    entries.accept(UPItems.RED_FRUIT_SCRAPS.get());
                    entries.accept(UPItems.RED_FRUIT.get());
                    entries.accept(UPItems.WHITE_FRUIT_SCRAPS.get());
                    entries.accept(UPItems.WHITE_FRUIT.get());
                    entries.accept(UPItems.YELLOW_FRUIT_SCRAPS.get());
                    entries.accept(UPItems.YELLOW_FRUIT.get());
                    entries.accept(UPItems.BLUE_FRUIT_SCRAPS.get());
                    entries.accept(UPItems.BLUE_FRUIT.get());

                    // Crocarina
                    d.holders().lookup(Registries.INSTRUMENT).ifPresent((p_270036_) -> {
                        generateInstrumentTypes(entries, p_270036_, UPItems.BARINA_WHISTLE.get(), UPTags.OCARINA_WHISTLE, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    });

                    // Tools and armor
                    entries.accept(UPItems.MEAT_ON_A_STICK.get());
                    entries.accept(UPItems.WARPICK.get());
                    entries.accept(UPItems.PRIMAL_MACUAHUITL.get());
                    entries.accept(UPItems.HANDMADE_SPEAR.get());
                    entries.accept(UPItems.HANDMADE_BATTLEAXE.get());
                    entries.accept(UPItems.HANDMADE_CLUB.get());
                    entries.accept(UPItems.SHEDSCALE_HELMET.get());
                    entries.accept(UPItems.SHEDSCALE_CHESTPLATE.get());
                    entries.accept(UPItems.SHEDSCALE_LEGGINGS.get());
                    entries.accept(UPItems.SHEDSCALE_BOOTS.get());
                    entries.accept(UPItems.MAJUNGA_HELMET.get());
                    entries.accept(UPItems.TYRANTS_CROWN.get());
                    entries.accept(UPItems.DINO_POUCH.get());
                    entries.accept(UPItems.SLOTH_POUCH_ARMOR.get());
                    entries.accept(UPItems.AUSTRO_BOOTS.get());
                    entries.accept(UPItems.VELOCI_SHIELD.get());
                    entries.accept(UPItems.TRIKE_SHIELD.get());

                    // Buckets
                    entries.accept(UPItems.AMMON_BUCKET.get());
                    entries.accept(UPItems.DUNK_BUCKET.get());
                    entries.accept(UPItems.JAWLESS_FISH_BUCKET.get());
                    entries.accept(UPItems.SCAU_BUCKET.get());
                    entries.accept(UPItems.STETHA_BUCKET.get());
                    entries.accept(UPItems.BEELZE_BUCKET.get());
                    entries.accept(UPItems.PALAEO_BUCKET.get());

                    // Fossil mounts
                    entries.accept(UPBlocks.REX_BOOMBOX.get());
                    entries.accept(UPBlocks.REX_HEAD.get());
                    entries.accept(UPBlocks.COTY_FOSSIL.get());
                    entries.accept(UPBlocks.DUNK_FOSSIL.get());
                    entries.accept(UPBlocks.SCAU_FOSSIL.get());
                    entries.accept(UPBlocks.STETHA_FOSSIL.get());
                    entries.accept(UPBlocks.ANTARCTO_FOSSIL.get());
                    entries.accept(UPBlocks.ANURO_FOSSIL.get());
                    entries.accept(UPBlocks.AUSTRO_FOSSIL.get());
                    entries.accept(UPBlocks.BEELZE_FOSSIL.get());
                    entries.accept(UPBlocks.BRACHI_FOSSIL.get());
                    entries.accept(UPBlocks.ERYON_FOSSIL.get());
                    entries.accept(UPBlocks.HWACHA_FOSSIL.get());
                    entries.accept(UPBlocks.KENTRO_FOSSIL.get());
                    entries.accept(UPBlocks.MAJUNGA_FOSSIL.get());
                    entries.accept(UPBlocks.PACHY_FOSSIL.get());
                    entries.accept(UPBlocks.ULUGH_FOSSIL.get());
                    entries.accept(UPBlocks.VELOCI_FOSSIL.get());

                    // Misc flasks
                    entries.accept(UPItems.FLASK.get());
                    entries.accept(UPItems.GROG.get());
                    entries.accept(UPItems.CAPTURED_KIMMER_FLASK.get());
                    entries.accept(UPItems.JARATE.get());
                    entries.accept(UPItems.DORMANT_RAMPAGE.get());
                    entries.accept(UPItems.YIXIAN_RAMPAGE_FLASK.get());

                    // Paleo DNA
                    entries.accept(UPItems.AMMONITE_FLASK.get());
                    entries.accept(UPItems.COTY_FLASK.get());
                    entries.accept(UPItems.DIPLO_FLASK.get());
                    entries.accept(UPItems.DUNK_FLASK.get());
                    entries.accept(UPItems.EDAPHO_FLASK.get());
                    entries.accept(UPItems.ESTEMMENO_FLASK.get());
                    entries.accept(UPItems.HYNERIA_FLASK.get());
                    entries.accept(UPItems.HYNERP_FLASK.get());
                    entries.accept(UPItems.JAWLESS_FISH_FLASK.get());
                    entries.accept(UPItems.PTERY_FLASK.get());
                    entries.accept(UPItems.SCAU_FLASK.get());
                    entries.accept(UPItems.STETHA_FLASK.get());
                    entries.accept(UPItems.TARTUO_FLASK.get());

                    // Meso DNA
                    entries.accept(UPItems.ANTARCTO_FLASK.get());
                    entries.accept(UPItems.ANURO_FLASK.get());
                    entries.accept(UPItems.ARCHELON_FLASK.get());
                    entries.accept(UPItems.AUSTRO_FLASK.get());
                    entries.accept(UPItems.BALAUR_FLASK.get());
                    entries.accept(UPItems.BEELZ_FLASK.get());
                    entries.accept(UPItems.BRACHI_FLASK.get());
                    entries.accept(UPItems.ENCRUSTED_FLASK.get());
                    entries.accept(UPItems.ERYON_FLASK.get());
                    entries.accept(UPItems.GLOBIDENS_FLASK.get());
                    entries.accept(UPItems.HWACHA_FLASK.get());
                    entries.accept(UPItems.KAPRO_FLASK.get());
                    entries.accept(UPItems.KENTRO_FLASK.get());
                    entries.accept(UPItems.KIMMER_FLASK.get());
                    entries.accept(UPItems.LEEDS_FLASK.get());
                    entries.accept(UPItems.LONGI_FLASK.get());
                    entries.accept(UPItems.MAJUNGA_FLASK.get());
                    entries.accept(UPItems.OVIRAPTOR_FLASK.get());
                    entries.accept(UPItems.PACHY_FLASK.get());
                    entries.accept(UPItems.PROTOSPHYRAENA_FLASK.get());
                    entries.accept(UPItems.PSITTACO_FLASK.get());
                    entries.accept(UPItems.PTERODAUSTRO_FLASK.get());
                    entries.accept(UPItems.TANY_FLASK.get());
                    entries.accept(UPItems.TRIKE_FLASK.get());
                    entries.accept(UPItems.REX_FLASK.get());
                    entries.accept(UPItems.ULUGH_FLASK.get());
                    entries.accept(UPItems.RAPTOR_FLASK.get());
                    entries.accept(UPItems.XIPHACT_FLASK.get());

                    // Ceno DNA
                    entries.accept(UPItems.BARIN_FLASK.get());
                    entries.accept(UPItems.GIGANTO_FLASK.get());
                    entries.accept(UPItems.MAMMOTH_FLASK.get());
                    entries.accept(UPItems.MEGALA_FLASK.get());
                    entries.accept(UPItems.MEGATH_FLASK.get());
                    entries.accept(UPItems.OPHIO_FLASK.get());
                    entries.accept(UPItems.OTAROCYON_FLASK.get());
                    entries.accept(UPItems.PALAEO_FLASK.get());
                    entries.accept(UPItems.PARACER_FLASK.get());
                    entries.accept(UPItems.PSILO_FLASK.get());
                    entries.accept(UPItems.SMILO_FLASK.get());
                    entries.accept(UPItems.TALPANAS_FLASK.get());

                    // Plant DNA
                    entries.accept(UPItems.ANOSTYLOSTRAMA_FLASK.get());
                    entries.accept(UPItems.ARCHAEFRUCTUS_FLASK.get());
                    entries.accept(UPItems.ARCHAO_FLASK.get());
                    entries.accept(UPItems.BENNET_FLASK.get());
                    entries.accept(UPItems.CLATHRODICTYON_FLASK.get());
                    entries.accept(UPItems.DRYO_FLASK.get());
                    entries.accept(UPItems.FOXXI_FLASK.get());
                    entries.accept(UPItems.GINKGO_FLASK.get());
                    entries.accept(UPItems.HORSETAIL_FLASK.get());
                    entries.accept(UPItems.LEEFRUCTUS_FLASK.get());
                    entries.accept(UPItems.NELUMBITES_FLASK.get());
                    entries.accept(UPItems.QUEREUXIA_FLASK.get());
                    entries.accept(UPItems.RAIGUENRAYUN_FLASK.get());
                    entries.accept(UPItems.SARR_FLASK.get());
                    entries.accept(UPItems.ZULOAGAE_FLASK.get());

                    // Paleo eggs
                    entries.accept(UPBlocks.AMON_EGGS.get());
                    entries.accept(UPBlocks.COTY_EGG.get());
                    entries.accept(UPBlocks.DIPLOCAULUS_EGGS.get());
                    entries.accept(UPBlocks.DUNK_EGGS.get());
                    entries.accept(UPBlocks.HYNERIA_EGGS.get());
                    entries.accept(UPBlocks.SCAU_EGGS.get());
                    entries.accept(UPBlocks.STETHA_EGGS.get());
                    entries.accept(UPBlocks.TARTUO_EGGS.get());

                    // Meso eggs
                    entries.accept(UPBlocks.ANTARCO_EGG.get());
                    entries.accept(UPBlocks.ANURO_EGG.get());
                    entries.accept(UPBlocks.AUSTRO_EGG.get());
                    entries.accept(UPBlocks.BEELZE_EGGS.get());
                    entries.accept(UPBlocks.BRACHI_EGG.get());
                    entries.accept(UPBlocks.ENCRUSTED_SACK.get());
                    entries.accept(UPBlocks.ERYON_EGGS.get());
                    entries.accept(UPBlocks.HWACHA_EGG.get());
                    entries.accept(UPBlocks.KENTRO_EGG.get());
                    entries.accept(UPBlocks.KIMMER_EGGS.get());
                    entries.accept(UPBlocks.MAJUNGA_EGG.get());
                    entries.accept(UPBlocks.PACHY_EGG.get());
                    entries.accept(UPBlocks.TRIKE_EGG.get());
                    entries.accept(UPBlocks.REX_EGG.get());
                    entries.accept(UPBlocks.ULUGH_EGG.get());
                    entries.accept(UPBlocks.RAPTOR_EGG.get());

                    // Ceno eggs & embryos
                    entries.accept(UPBlocks.BARINA_EGG.get());
                    entries.accept(UPItems.GIGANTO_EMBRYO.get());
                    entries.accept(UPItems.MAMMOTH_EMBRYO.get());
                    entries.accept(UPBlocks.MEGALA_EGG.get());
                    entries.accept(UPItems.MEGATH_EMBRYO.get());
                    entries.accept(UPBlocks.OPHIDION_EGGS.get());
                    entries.accept(UPItems.OTAROCYON_EMBRYO.get());
                    entries.accept(UPItems.PALAEO_EMBRYO.get());
                    entries.accept(UPItems.PARACER_EMBRYO.get());
                    entries.accept(UPItems.SMILODON_EMBRYO.get());
                    entries.accept(UPBlocks.TALPANAS_EGG.get());

                    // Plants
                    entries.accept(UPBlocks.ANOSTYLOSTROMA_BLOCK.get());
                    entries.accept(UPBlocks.ARCHAEFRUCTUS.get());
                    entries.accept(UPBlocks.ARCHAEOSIGILARIA.get());
                    entries.accept(UPBlocks.BENNETTITALES.get());
                    entries.accept(UPBlocks.CLATHRODICTYON_BLOCK.get());
                    entries.accept(UPBlocks.CLATHRODICTYON.get());
                    entries.accept(UPBlocks.CLATHRODICTYON_FAN.get());
                    entries.accept(UPBlocks.DEAD_CLATHRODICTYON_BLOCK.get());
                    entries.accept(UPBlocks.DEAD_CLATHRODICTYON.get());
                    entries.accept(UPBlocks.DEAD_CLATHRODICTYON_FAN.get());
                    entries.accept(UPBlocks.DRYO_SAPLING.get());
                    entries.accept(UPBlocks.FOXII_SAPLING.get());
                    entries.accept(UPBlocks.GINKGO_SAPLING.get());
                    entries.accept(UPBlocks.HORSETAIL.get());
                    entries.accept(UPBlocks.TALL_HORSETAIL.get());
                    entries.accept(UPBlocks.LEEFRUCTUS.get());
                    entries.accept(UPBlocks.NELUMBITES.get());
                    entries.accept(UPBlocks.QUEREUXIA.get());
                    entries.accept(UPBlocks.QUEREUXIA_TOP.get());
                    entries.accept(UPBlocks.RAIGUENRAYUN.get());
                    entries.accept(UPBlocks.SARACENIA.get());
                    entries.accept(UPBlocks.TALL_SARACENIA.get());
                    entries.accept(UPBlocks.ZULOAGAE.get());

                    // Petrified wood
                    entries.accept(UPBlocks.PETRIFIED_WOOD_LOG.get());
                    entries.accept(UPBlocks.PETRIFIED_WOOD.get());
                    entries.accept(UPBlocks.STRIPPED_PETRIFIED_WOOD_LOG.get());
                    entries.accept(UPBlocks.STRIPPED_PETRIFIED_WOOD.get());
                    entries.accept(UPBlocks.PETRIFIED_WOOD_PLANKS.get());
                    entries.accept(UPBlocks.PETRIFIED_WOOD_STAIRS.get());
                    entries.accept(UPBlocks.PETRIFIED_WOOD_SLAB.get());
                    entries.accept(UPBlocks.PETRIFIED_WOOD_FENCE.get());
                    entries.accept(UPBlocks.PETRIFIED_WOOD_FENCE_GATE.get());
                    entries.accept(UPBlocks.PETRIFIED_WOOD_PRESSURE_PLATE.get());
                    entries.accept(UPBlocks.PETRIFIED_WOOD_BUTTON.get());
                    entries.accept(UPBlocks.PETRIFIED_WOOD_DOOR.get());
                    entries.accept(UPBlocks.PETRIFIED_WOOD_TRAPDOOR.get());
                    entries.accept(UPBlocks.PETRIFIED_WOOD_SIGN.get());
                    entries.accept(UPBlocks.PETRIFIED_WOOD_HANGING_SIGN.get());
                    entries.accept(UPBlocks.POLISHED_PETRIFIED_WOOD.get());
                    entries.accept(UPBlocks.POLISHED_PETRIFIED_WOOD_STAIRS.get());
                    entries.accept(UPBlocks.POLISHED_PETRIFIED_WOOD_SLAB.get());
                    entries.accept(UPBlocks.PETRIFIED_BUSH.get());

                    // Dryo wood
                    entries.accept(UPBlocks.DRYO_LOG.get());
                    entries.accept(UPBlocks.DRYO_WOOD.get());
                    entries.accept(UPBlocks.STRIPPED_DRYO_LOG.get());
                    entries.accept(UPBlocks.STRIPPED_DRYO_WOOD.get());
                    entries.accept(UPBlocks.DRYO_PLANKS.get());
                    entries.accept(UPBlocks.DRYO_STAIRS.get());
                    entries.accept(UPBlocks.DRYO_SLAB.get());
                    entries.accept(UPBlocks.DRYO_FENCE.get());
                    entries.accept(UPBlocks.DRYO_FENCE_GATE.get());
                    entries.accept(UPBlocks.DRYO_DOOR.get());
                    entries.accept(UPBlocks.DRYO_TRAPDOOR.get());
                    entries.accept(UPBlocks.DRYO_PRESSURE_PLATE.get());
                    entries.accept(UPBlocks.DRYO_BUTTON.get());
                    entries.accept(UPBlocks.DRYO_SIGN.get());
                    entries.accept(UPBlocks.DRYO_HANGING_SIGN.get());
                    entries.accept(UPItems.DRYO_BOAT.get());
                    entries.accept(UPItems.DRYO_CHEST_BOAT.get());
                    entries.accept(UPBlocks.DRYO_LEAVES.get());

                    // Foxii wood
                    entries.accept(UPBlocks.FOXXI_LOG.get());
                    entries.accept(UPBlocks.FOXXI_WOOD.get());
                    entries.accept(UPBlocks.STRIPPED_FOXXI_LOG.get());
                    entries.accept(UPBlocks.STRIPPED_FOXXI_WOOD.get());
                    entries.accept(UPBlocks.FOXXI_PLANKS.get());
                    entries.accept(UPBlocks.FOXXI_STAIRS.get());
                    entries.accept(UPBlocks.FOXXI_SLAB.get());
                    entries.accept(UPBlocks.FOXXI_FENCE.get());
                    entries.accept(UPBlocks.FOXXI_FENCE_GATE.get());
                    entries.accept(UPBlocks.FOXXI_DOOR.get());
                    entries.accept(UPBlocks.FOXXI_TRAPDOOR.get());
                    entries.accept(UPBlocks.FOXXI_PRESSURE_PLATE.get());
                    entries.accept(UPBlocks.FOXXI_BUTTON.get());
                    entries.accept(UPBlocks.FOXII_SIGN.get());
                    entries.accept(UPBlocks.FOXII_HANGING_SIGN.get());
                    entries.accept(UPItems.FOXXI_BOAT.get());
                    entries.accept(UPItems.FOXXI_CHEST_BOAT.get());
                    entries.accept(UPBlocks.FOXXI_LEAVES.get());

                    // Ginkgo wood
                    entries.accept(UPBlocks.GINKGO_LOG.get());
                    entries.accept(UPBlocks.GINKGO_WOOD.get());
                    entries.accept(UPBlocks.STRIPPED_GINKGO_LOG.get());
                    entries.accept(UPBlocks.STRIPPED_GINKGO_WOOD.get());
                    entries.accept(UPBlocks.GINKGO_PLANKS.get());
                    entries.accept(UPBlocks.GINKGO_STAIRS.get());
                    entries.accept(UPBlocks.GINKGO_SLAB.get());
                    entries.accept(UPBlocks.GINKGO_FENCE.get());
                    entries.accept(UPBlocks.GINKGO_FENCE_GATE.get());
                    entries.accept(UPBlocks.GINKGO_DOOR.get());
                    entries.accept(UPBlocks.GINKGO_TRAPDOOR.get());
                    entries.accept(UPBlocks.GINKGO_PRESSURE_PLATE.get());
                    entries.accept(UPBlocks.GINKGO_BUTTON.get());
                    entries.accept(UPBlocks.GINKGO_SIGN.get());
                    entries.accept(UPBlocks.GINKGO_HANGING_SIGN.get());
                    entries.accept(UPItems.GINKGO_BOAT.get());
                    entries.accept(UPItems.GINKGO_CHEST_BOAT.get());
                    entries.accept(UPBlocks.GINKGO_LEAVES.get());

                    // Zuloagae wood
                    entries.accept(UPBlocks.ZULOAGAE_BLOCK.get());
                    entries.accept(UPBlocks.STRIPPED_ZULOAGAE_BLOCK.get());
                    entries.accept(UPBlocks.ZULOAGAE_PLANKS.get());
                    entries.accept(UPBlocks.ZULOAGAE_STAIRS.get());
                    entries.accept(UPBlocks.ZULOAGAE_SLAB.get());
                    entries.accept(UPBlocks.ZULOAGAE_FENCE.get());
                    entries.accept(UPBlocks.ZULOAGAE_FENCE_GATE.get());
                    entries.accept(UPBlocks.ZULOAGAE_DOOR.get());
                    entries.accept(UPBlocks.ZULOAGAE_TRAPDOOR.get());
                    entries.accept(UPBlocks.ZULOAGAE_PRESSURE_PLATE.get());
                    entries.accept(UPBlocks.ZULOAGAE_BUTTON.get());
                    entries.accept(UPBlocks.ZULOAGAE_SIGN.get());
                    entries.accept(UPBlocks.ZULOAGAE_HANGING_SIGN.get());

                    // Spawn eggs
                    UPItems.ITEMS.getEntries().forEach(spawnEgg ->{
                        if((spawnEgg.get() instanceof ForgeSpawnEggItem)) {
                            entries.accept(spawnEgg.get());
                        }
                    });

                    // Unsorted stuff
                    if(!(item.get() instanceof ForgeSpawnEggItem) && !item.get().getDefaultInstance().is(UPItems.BARINA_WHISTLE.get()) && !item.get().getDefaultInstance().is(UPBlocks.FRUIT_LOOT_BOX.get().asItem())) {
                        entries.accept(item.get());
                    }
                }
            })
            .build();

    public static final RegistryObject<CreativeModeTab> UP_TAB = TABS.register("unusual_prehistory", () -> UP);


    private static void generateInstrumentTypes(CreativeModeTab.Output pOutput, HolderLookup<Instrument> pInstruments, Item pItem, TagKey<Instrument> pInstrument, CreativeModeTab.TabVisibility pTabVisibility) {
        pInstruments.get(pInstrument).ifPresent((p_270021_) -> {
            p_270021_.stream().map((p_269995_) -> {
                return MusicalTameItem.create(pItem, p_269995_);
            }).forEach((p_270011_) -> {
                pOutput.accept(p_270011_, pTabVisibility);
            });
        });
    }

    private static void generateEnchantsForBoots(CreativeModeTab.Output output, Item item, CreativeModeTab.TabVisibility tabVisibility) {
        ItemStack soulsuckerBoots = new ItemStack(item);
        soulsuckerBoots.enchant(Enchantments.SOUL_SPEED, 3);
        output.accept(soulsuckerBoots, tabVisibility);
    }

    private static void addTagToDinosaurWhistle(CreativeModeTab.Output output, Item item, CreativeModeTab.TabVisibility tabVisibility) {
        ItemStack dinoWhistle = new ItemStack(item);
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("command", 0);
        dinoWhistle.setTag(compoundTag);
        output.accept(dinoWhistle, tabVisibility);
    }

    private static final LootFruitCodec LOOT_FRUIT = new LootFruitCodec(2, "unusualprehistory.loot_fruit_box.default", Items.BAMBOO, Collections.emptyList(), TextColor.fromRgb(12345), 2);
    private static final List<LootFruitCodec> LOOT_FRUIT_LIST = new ArrayList<>() {{
        add(LOOT_FRUIT);
    }};

    private static void addTagToLootFruit(CreativeModeTab.Output output, Item item, CreativeModeTab.TabVisibility tabVisibility) {
        boolean isEmpty = LootFruitJsonManager.getTierTrades().isEmpty();
        if(!isEmpty) {
            Map<Integer, List<LootFruitCodec>> lootFruitItem= LootFruitJsonManager.getTierTrades();
            for(List<LootFruitCodec> lootFruitCodecs : lootFruitItem.values()){
                for(LootFruitCodec lootFruitCodec : lootFruitCodecs){

                    ItemStack istack = new ItemStack(item);
                    CompoundTag lootFruitTag = istack.getOrCreateTag();
                    int color = lootFruitCodec.getColor().getValue();
                    lootFruitTag.putString("translationKey", lootFruitCodec.getTranslationKey());
                    lootFruitTag.putInt("color", color);
                    lootFruitTag.put("tradeItem", lootFruitCodec.getTradeItem().getDefaultInstance().serializeNBT());
                    lootFruitTag.putInt("CustomModelData", lootFruitCodec.getCustomModelData());
                    istack.setTag(lootFruitTag);
                    output.accept(istack, tabVisibility);
                }
            }


        } else {
            ItemStack istack = new ItemStack(item);
            List<ItemWeightedPairCodec> itemWeightedPairCodecs = new ArrayList<>();
            itemWeightedPairCodecs.add(new ItemWeightedPairCodec(UPItems.PALEO_FOSSIL.get(), 100, 1));
            List<RollableItemCodec> rollableItemCodecs =  new ArrayList<>();
            rollableItemCodecs.add(new RollableItemCodec(1, itemWeightedPairCodecs));
            LootFruitCodec lootFruitCodec = LOOT_FRUIT;
            CompoundTag lootFruitTag = istack.getOrCreateTag();
            if(lootFruitCodec == null) return;
            int color = lootFruitCodec.getColor().getValue();
            lootFruitTag.putString("translationKey", lootFruitCodec.getTranslationKey());
            lootFruitTag.putInt("color", color);
            lootFruitTag.putInt("CustomModelData", lootFruitCodec.getCustomModelData());
            lootFruitTag.put("tradeItem", lootFruitCodec.getTradeItem().getDefaultInstance().serializeNBT());
            istack.setTag(lootFruitTag);
            output.accept(istack, tabVisibility);
        }
    }

}
