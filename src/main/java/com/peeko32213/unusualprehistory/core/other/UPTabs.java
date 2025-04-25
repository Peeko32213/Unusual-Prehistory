package com.peeko32213.unusualprehistory.core.other;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.data.RollableItemCodec;
import com.peeko32213.unusualprehistory.common.data.encyclopedia.ItemWeightedPairCodec;
import com.peeko32213.unusualprehistory.common.data.lootfruit.LootFruitCodec;
import com.peeko32213.unusualprehistory.common.data.lootfruit.LootFruitJsonManager;
import com.peeko32213.unusualprehistory.common.item.MusicalTameItem;
import com.peeko32213.unusualprehistory.common.item.PrehistoricEggItem;
import com.peeko32213.unusualprehistory.core.other.tags.UPInstrumentTags;
import com.peeko32213.unusualprehistory.core.other.tags.UPItemTags;
import com.peeko32213.unusualprehistory.core.registry.UPEnchantments;
import com.peeko32213.unusualprehistory.core.registry.blocks.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
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
                    if (!item.get().getDefaultInstance().is(UPItemTags.HIDDEN_ITEMS)) {

                        // Spawn eggs
                        UPItems.ITEMS.getEntries().forEach(spawnEgg -> {
                            if ((spawnEgg.get() instanceof ForgeSpawnEggItem)) {
                                entries.accept(spawnEgg.get());
                            }
                        });

                        // Peeko's treacherous time travel logbook
                        entries.accept(UPItems.ENCYLOPEDIA.get());

                        // Fossils
                        entries.accept(UPItems.PALEO_FOSSIL.get());
                        entries.accept(UPItems.MEZO_FOSSIL.get());
                        entries.accept(UPItems.PLANT_FOSSIL.get());
                        entries.accept(UPItems.TAR_FOSSIL.get());
                        entries.accept(UPItems.FROZEN_FOSSIL.get());
                        entries.accept(UPItems.DEFROSTED_FROZEN_FOSSIL.get());
                        entries.accept(UPItems.AMBER.get());
                        entries.accept(UPItems.AMBER_FOSSIL.get());
                        entries.accept(UPItems.OPAL.get());
                        entries.accept(UPItems.OPAL_FOSSIL.get());
                        entries.accept(UPItems.FIRE_OPAL.get());
                        entries.accept(UPItems.FIRE_OPAL_FOSSIL.get());
                        entries.accept(UPItems.BOULDER_OPAL.get());
                        entries.accept(UPItems.BOULDER_OPAL_FOSSIL.get());
                        entries.accept(UPItems.BLACK_OPAL.get());
                        entries.accept(UPItems.BLACK_OPAL_FOSSIL.get());
                        entries.accept(UPBlocks.STONE_FOSSIL.get());
                        entries.accept(UPBlocks.DEEPSLATE_FOSSIL.get());
                        entries.accept(UPBlocks.PLANT_FOSSIL.get());
                        entries.accept(UPBlocks.DEEPSLATE_PLANT_FOSSIL.get());
                        entries.accept(UPBlocks.STONE_TAR_FOSSIL.get());
                        entries.accept(UPBlocks.DEEPSLATE_TAR_FOSSIL.get());
                        entries.accept(UPBlocks.PERMAFROST.get());
                        entries.accept(UPBlocks.PERMAFROST_FOSSIL.get());
                        entries.accept(UPBlocks.STONE_AMBER_FOSSIL.get());
                        entries.accept(UPBlocks.DEEPSLATE_AMBER_FOSSIL.get());
                        entries.accept(UPBlocks.OPAL_ORE.get());
                        entries.accept(UPBlocks.DEEPSLATE_OPAL_ORE.get());
                        entries.accept(UPBlocks.FIRE_OPAL_ORE.get());
                        entries.accept(UPBlocks.DEEPSLATE_FIRE_OPAL_ORE.get());
                        entries.accept(UPBlocks.BOULDER_OPAL_ORE.get());
                        entries.accept(UPBlocks.DEEPSLATE_BOULDER_OPAL_ORE.get());
                        entries.accept(UPBlocks.BLACK_OPAL_ORE.get());
                        entries.accept(UPBlocks.DEEPSLATE_BLACK_OPAL_ORE.get());

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
                        entries.accept(UPBlocks.FIRE_OPAL_BLOCK.get());
                        entries.accept(UPBlocks.BOULDER_OPAL_BLOCK.get());
                        entries.accept(UPBlocks.BLACK_OPAL_BLOCK.get());
                        entries.accept(UPItems.OPALESCENT_PEARL.get());
                        entries.accept(UPItems.OPALESCENT_SHURIKEN.get());

                        // Discs
                        entries.accept(UPItems.ZULOGAE_DISC.get());
                        entries.accept(UPItems.ENCASED_DISC.get());
                        entries.accept(UPItems.OPALESENCE_DISC.get());
                        entries.accept(UPItems.TARIFYING_DISC.get());

                        // Science gadgets and stuff
                        entries.accept(UPBlocks.ANALYZER.get());
                        entries.accept(UPBlocks.CULTIVATOR.get());
                        entries.accept(UPBlocks.INCUBATOR.get());
                        entries.accept(UPBlocks.DNA_FRIDGE.get());

                        // Mob items
                        entries.accept(UPItems.ORGANIC_OOZE.get());
                        entries.accept(UPItems.BEELZ_SALIVA.get());
                        entries.accept(UPItems.SHELL_SHARD.get());
                        entries.accept(UPBlocks.AMMONITE_SHELL.get());
                        entries.accept(UPItems.AUSTRO_FEATHER.get());
                        entries.accept(UPItems.VELOCI_FEATHERS.get());
                        entries.accept(UPItems.ANTARCTO_PLATE.get());
                        entries.accept(UPItems.MAJUNGA_SCUTE.get());
                        entries.accept(UPItems.PSITTACOSAURUS_QUILL.get());
                        entries.accept(UPItems.PSITTACCO_ARROW.get());
                        entries.accept(UPItems.TRIKE_HORN.get());
                        entries.accept(UPItems.TYRANNO_SCALE.get());
                        entries.accept(UPItems.TYRANNO_TOOTH.get());
                        entries.accept(UPItems.PALAEO_SKIN.get());
                        entries.accept(UPItems.SMILO_FUR.get());

                        // Foods
                        entries.accept(UPItems.LEEDS_CAVIAR.get());
                        entries.accept(UPItems.RAW_GINKGO_SEEDS.get());
                        entries.accept(UPItems.COOKED_GINKGO_SEEDS.get());
                        entries.accept(UPItems.GINKGO_FRUIT.get());
                        entries.accept(UPItems.DRYO_NUTS.get());
                        entries.accept(UPItems.MAMMOTH_MEATBALL.get());

                        // Gambling fruit
                        addTagToLootFruit(entries, UPBlocks.FRUIT_LOOT_BOX.get().asItem());
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
                            generateInstrumentTypes(entries, p_270036_, UPItems.CROCARINA.get());
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
                        entries.accept(UPItems.POUCH.get());
                        entries.accept(UPItems.SLOTH_POUCH.get());
                        entries.accept(UPItems.AUSTRO_BOOTS.get());
                        entries.accept(UPItems.VELOCI_SHIELD.get());
                        entries.accept(UPItems.TRIKE_SHIELD.get());

                        // Buckets
                        entries.accept(UPItems.AMMON_BUCKET.get());
                        entries.accept(UPItems.BEELZE_BUCKET.get());
                        entries.accept(UPItems.JAWLESS_FISH_BUCKET.get());
                        entries.accept(UPItems.PALAEO_BUCKET.get());
                        entries.accept(UPItems.PANACAN_BUCKET.get());
                        entries.accept(UPItems.SCAU_BUCKET.get());
                        entries.accept(UPItems.STETHA_BUCKET.get());

                        // Rex blocks
                        entries.accept(UPBlocks.REX_BOOMBOX.get());
                        entries.accept(UPBlocks.REX_HEAD.get());

                        // Fossil skeletons
                        entries.accept(UPItems.TRIKE_SKELETON.get());
                        entries.accept(UPItems.TYRANNO_SKELETON.get());

                        // Fossil mounts
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

                        // Misc bottles
                        entries.accept(UPItems.GROG.get());
                        entries.accept(UPItems.CAPTURED_KIMMER_BOTTLE.get());

                        // Paleo DNA
                        entries.accept(UPItems.AMMONITE_DNA.get());
                        entries.accept(UPItems.COTY_DNA.get());
                        entries.accept(UPItems.DIPLO_DNA.get());
                        entries.accept(UPItems.DUNK_DNA.get());
                        entries.accept(UPItems.EDAPHO_DNA.get());
                        entries.accept(UPItems.ESTEMMENO_DNA.get());
                        entries.accept(UPItems.HYNERIA_DNA.get());
                        entries.accept(UPItems.HYNERP_DNA.get());
                        entries.accept(UPItems.JAWLESS_FISH_DNA.get());
                        entries.accept(UPItems.SCAU_DNA.get());
                        entries.accept(UPItems.STETHA_DNA.get());
                        entries.accept(UPItems.TARTUO_DNA.get());

                        // Meso DNA
                        entries.accept(UPItems.ANTARCTO_DNA.get());
                        entries.accept(UPItems.ANURO_DNA.get());
                        entries.accept(UPItems.AUSTRO_DNA.get());
                        entries.accept(UPItems.BEELZ_DNA.get());
                        entries.accept(UPItems.BRACHI_DNA.get());
                        entries.accept(UPItems.ENCRUSTED_DNA.get());
                        entries.accept(UPItems.ERYON_DNA.get());
                        entries.accept(UPItems.HWACHA_DNA.get());
                        entries.accept(UPItems.KAPRO_DNA.get());
                        entries.accept(UPItems.KENTRO_DNA.get());
                        entries.accept(UPItems.KIMMER_DNA.get());
                        entries.accept(UPItems.LEEDS_DNA.get());
                        entries.accept(UPItems.MAJUNGA_DNA.get());
                        entries.accept(UPItems.PACHY_DNA.get());
                        entries.accept(UPItems.PANACANTHOCARIS_DNA.get());
                        entries.accept(UPItems.PROTOSPHYRAENA_DNA.get());
                        entries.accept(UPItems.PSITTACO_DNA.get());
                        entries.accept(UPItems.PTERODAUSTRO_DNA.get());
                        entries.accept(UPItems.TRIKE_DNA.get());
                        entries.accept(UPItems.TYRANNO_DNA.get());
                        entries.accept(UPItems.ULUGH_DNA.get());
                        entries.accept(UPItems.VELOCI_DNA.get());

                        // Ceno DNA
                        entries.accept(UPItems.BARINA_DNA.get());
                        entries.accept(UPItems.GIGANTO_DNA.get());
                        entries.accept(UPItems.MAMMOTH_DNA.get());
                        entries.accept(UPItems.MEGALANIA_DNA.get());
                        entries.accept(UPItems.MEGATHERIUM_DNA.get());
                        entries.accept(UPItems.OPHIO_DNA.get());
                        entries.accept(UPItems.PALAEO_DNA.get());
                        entries.accept(UPItems.PARACER_DNA.get());
                        entries.accept(UPItems.SMILODON_DNA.get());
                        entries.accept(UPItems.TALPANAS_DNA.get());
                        entries.accept(UPItems.TELECREX_DNA.get());

                        // Plant DNA
                        entries.accept(UPItems.ARCHAO_DNA.get());
                        entries.accept(UPItems.BENNET_DNA.get());
                        entries.accept(UPItems.DRYO_DNA.get());
                        entries.accept(UPItems.FOXII_DNA.get());
                        entries.accept(UPItems.GINKGO_DNA.get());
                        entries.accept(UPItems.HORSETAIL_DNA.get());
                        entries.accept(UPItems.LEEFRUCTUS_DNA.get());
                        entries.accept(UPItems.RAIGUENRAYUN_DNA.get());
                        entries.accept(UPItems.SARR_DNA.get());
                        entries.accept(UPItems.ZULOAGAE_DNA.get());

                        // Water plant DNA
                        entries.accept(UPItems.ARCHAEFRUCTUS_DNA.get());
                        entries.accept(UPItems.NELUMBITES_DNA.get());
                        entries.accept(UPItems.QUEREUXIA_DNA.get());

                        // Coral DNA
                        entries.accept(UPItems.ANOSTYLOSTRAMA_DNA.get());
                        entries.accept(UPItems.CLATHRODICTYON_DNA.get());

                        // Water eggs
                        entries.accept(UPBlocks.AMON_EGGS.get());
                        entries.accept(UPBlocks.DIPLOCAULUS_EGGS.get());
                        entries.accept(UPBlocks.DUNK_EGGS.get());
                        entries.accept(UPBlocks.HYNERIA_EGGS.get());
                        entries.accept(UPBlocks.SCAU_EGGS.get());
                        entries.accept(UPBlocks.STETHA_EGGS.get());
                        entries.accept(UPBlocks.TARTUO_EGGS.get());
                        entries.accept(UPBlocks.BEELZE_EGGS.get());
                        entries.accept(UPBlocks.ERYON_EGGS.get());
                        entries.accept(UPBlocks.KIMMER_EGGS.get());
                        entries.accept(UPBlocks.OPHIDION_EGGS.get());

                        // Eggs
                        entries.accept(UPItems.TELECREX_EGG.get());

                        // Entity eggs
                        UPItems.ITEMS.getEntries().forEach(eggItem -> {
                            if ((eggItem.get() instanceof PrehistoricEggItem)) {
                                entries.accept(eggItem.get());
                            }
                        });

                        // Embryos
                        entries.accept(UPItems.GIGANTO_EMBRYO.get());
                        entries.accept(UPItems.MAMMOTH_EMBRYO.get());
                        entries.accept(UPItems.MEGATH_EMBRYO.get());
                        entries.accept(UPItems.PALAEO_EMBRYO.get());
                        entries.accept(UPItems.PARACER_EMBRYO.get());
                        entries.accept(UPItems.SMILODON_EMBRYO.get());

                        // Plants
                        entries.accept(UPBlocks.ARCHAEFRUCTUS.get());
                        entries.accept(UPBlocks.ARCHAEOSIGILARIA.get());
                        entries.accept(UPBlocks.BENNETTITALES.get());
                        entries.accept(UPBlocks.CALAMOPHYTON.get());
                        entries.accept(UPBlocks.CLADOPHLEBIS.get());
                        entries.accept(UPBlocks.HORSETAIL.get());
                        entries.accept(UPBlocks.TALL_HORSETAIL.get());
                        entries.accept(UPBlocks.ISOETES_BEESTONII.get());
                        entries.accept(UPBlocks.LEEFRUCTUS.get());
                        entries.accept(UPBlocks.NELUMBITES.get());
                        entries.accept(UPBlocks.QUEREUXIA.get());
                        entries.accept(UPBlocks.QUEREUXIA_TOP.get());
                        entries.accept(UPBlocks.RAIGUENRAYUN.get());
                        entries.accept(UPBlocks.SARACENIA.get());
                        entries.accept(UPBlocks.TALL_SARACENIA.get());
                        entries.accept(UPBlocks.DRYO_SAPLING.get());
                        entries.accept(UPBlocks.FOXII_SAPLING.get());
                        entries.accept(UPBlocks.GINKGO_SAPLING.get());
                        entries.accept(UPBlocks.PETRIFIED_BUSH.get());
                        entries.accept(UPBlocks.ZULOAGAE.get());
                        entries.accept(UPBlocks.ANOSTYLOSTROMA_BLOCK.get());
                        entries.accept(UPBlocks.CLATHRODICTYON_BLOCK.get());
                        entries.accept(UPBlocks.CLATHRODICTYON.get());
                        entries.accept(UPBlocks.CLATHRODICTYON_FAN.get());
                        entries.accept(UPBlocks.DEAD_CLATHRODICTYON_BLOCK.get());
                        entries.accept(UPBlocks.DEAD_CLATHRODICTYON.get());
                        entries.accept(UPBlocks.DEAD_CLATHRODICTYON_FAN.get());

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
                        entries.accept(UPBlocks.DRYO_SIGN.getFirst().get());
                        entries.accept(UPBlocks.DRYO_HANGING_SIGN.getFirst().get());
                        entries.accept(UPItems.DRYO_BOAT.getFirst().get());
                        entries.accept(UPItems.DRYO_BOAT.getSecond().get());
                        entries.accept(UPBlocks.DRYO_LEAVES.get());

                        // Foxii wood
                        entries.accept(UPBlocks.FOXII_LOG.get());
                        entries.accept(UPBlocks.FOXII_WOOD.get());
                        entries.accept(UPBlocks.STRIPPED_FOXII_LOG.get());
                        entries.accept(UPBlocks.STRIPPED_FOXII_WOOD.get());
                        entries.accept(UPBlocks.FOXII_PLANKS.get());
                        entries.accept(UPBlocks.FOXII_STAIRS.get());
                        entries.accept(UPBlocks.FOXII_SLAB.get());
                        entries.accept(UPBlocks.FOXII_FENCE.get());
                        entries.accept(UPBlocks.FOXII_FENCE_GATE.get());
                        entries.accept(UPBlocks.FOXII_DOOR.get());
                        entries.accept(UPBlocks.FOXII_TRAPDOOR.get());
                        entries.accept(UPBlocks.FOXII_PRESSURE_PLATE.get());
                        entries.accept(UPBlocks.FOXII_BUTTON.get());
                        entries.accept(UPBlocks.FOXII_SIGN.getFirst().get());
                        entries.accept(UPBlocks.FOXII_HANGING_SIGN.getFirst().get());
                        entries.accept(UPItems.FOXII_BOAT.getFirst().get());
                        entries.accept(UPItems.FOXII_BOAT.getSecond().get());
                        entries.accept(UPBlocks.FOXII_LEAVES.get());

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
                        entries.accept(UPBlocks.GINKGO_SIGN.getFirst().get());
                        entries.accept(UPBlocks.GINKGO_HANGING_SIGN.getFirst().get());
                        entries.accept(UPItems.GINKGO_BOAT.getFirst().get());
                        entries.accept(UPItems.GINKGO_BOAT.getSecond().get());
                        entries.accept(UPBlocks.GINKGO_LEAVES.get());

                        // Petrified wood
                        entries.accept(UPBlocks.PETRIFIED_LOG.get());
                        entries.accept(UPBlocks.PETRIFIED_WOOD.get());
                        entries.accept(UPBlocks.STRIPPED_PETRIFIED_LOG.get());
                        entries.accept(UPBlocks.STRIPPED_PETRIFIED_WOOD.get());
                        entries.accept(UPBlocks.PETRIFIED_PLANKS.get());
                        entries.accept(UPBlocks.PETRIFIED_STAIRS.get());
                        entries.accept(UPBlocks.PETRIFIED_SLAB.get());
                        entries.accept(UPBlocks.POLISHED_PETRIFIED_WOOD.get());
                        entries.accept(UPBlocks.POLISHED_PETRIFIED_WOOD_STAIRS.get());
                        entries.accept(UPBlocks.POLISHED_PETRIFIED_WOOD_SLAB.get());
                        entries.accept(UPBlocks.POLISHED_PETRIFIED_WOOD_WALL.get());
                        entries.accept(UPBlocks.PETRIFIED_FENCE.get());
                        entries.accept(UPBlocks.PETRIFIED_FENCE_GATE.get());
                        entries.accept(UPBlocks.PETRIFIED_DOOR.get());
                        entries.accept(UPBlocks.PETRIFIED_TRAPDOOR.get());
                        entries.accept(UPBlocks.PETRIFIED_PRESSURE_PLATE.get());
                        entries.accept(UPBlocks.PETRIFIED_BUTTON.get());
                        entries.accept(UPBlocks.PETRIFIED_SIGN.getFirst().get());
                        entries.accept(UPBlocks.PETRIFIED_HANGING_SIGN.getFirst().get());

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
                        entries.accept(UPBlocks.ZULOAGAE_SIGN.getFirst().get());
                        entries.accept(UPBlocks.ZULOAGAE_HANGING_SIGN.getFirst().get());

                        UPEnchantments.addAllEnchantsToCreativeTab(entries, UPEnchantments.VELOCI_SHIELD);

                        // Unsorted stuff
                        if (!(item.get() instanceof ForgeSpawnEggItem) && !item.get().getDefaultInstance().is(UPItems.CROCARINA.get()) && !item.get().getDefaultInstance().is(UPBlocks.FRUIT_LOOT_BOX.get().asItem()) && !(item.get() instanceof PrehistoricEggItem)) {
                            entries.accept(item.get());
                        }
                    }
                }
            })
            .build();

    public static final RegistryObject<CreativeModeTab> UP_TAB = TABS.register("unusual_prehistory", () -> UP);

    private static void generateInstrumentTypes(CreativeModeTab.Output pOutput, HolderLookup<Instrument> pInstruments, Item pItem) {
        pInstruments.get(UPInstrumentTags.OCARINA_WHISTLE).ifPresent((p_270021_) -> {
            p_270021_.stream().map((p_269995_) -> {
                return MusicalTameItem.create(pItem, p_269995_);
            }).forEach((p_270011_) -> {
                pOutput.accept(p_270011_, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
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

    private static void addTagToLootFruit(CreativeModeTab.Output output, Item item) {
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
                    output.accept(istack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
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
            output.accept(istack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
