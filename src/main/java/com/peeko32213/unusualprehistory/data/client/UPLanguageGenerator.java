package com.peeko32213.unusualprehistory.data.client;

import com.mojang.logging.LogUtils;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.other.UPTabs;
import com.peeko32213.unusualprehistory.core.registry.*;
import com.peeko32213.unusualprehistory.core.registry.blocks.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.entities.UPPaintings;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import com.peeko32213.unusualprehistory.core.other.util.UPTextUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class UPLanguageGenerator extends LanguageProvider {
    public UPLanguageGenerator(PackOutput output) {
        super(output, UnusualPrehistory.MODID, "en_us");
    }
    private static final Logger LOGGER = LogUtils.getLogger();
    @Override
    protected void addTranslations(){

        // Creative tab
        addTabName(UPTabs.UP_TAB.get(), "Unusual Prehistory");

        // Blocks
        UPBlocks.AUTO_TRANSLATE.forEach(this::forBlock);

        // Items
        UPItems.AUTO_TRANSLATE.forEach(this::forItem);

        // Paintings
        UPPaintings.PAINTING_TRANSLATIONS.forEach(this::addPainting);

        // Untranslated blocks

        addBlock(UPBlocks.DRYO_LOG, "Dryophyllum Log");
        addBlock(UPBlocks.STRIPPED_DRYO_LOG, "Stripped Dryophyllum Log");
        addBlock(UPBlocks.DRYO_WOOD, "Dryophyllum Wood");
        addBlock(UPBlocks.STRIPPED_DRYO_WOOD, "Stripped Dryophyllum Wood");
        addBlock(UPBlocks.DRYO_PLANKS, "Dryophyllum Planks");
        addBlock(UPBlocks.DRYO_STAIRS, "Dryophyllum Stairs");
        addBlock(UPBlocks.DRYO_SLAB, "Dryophyllum Slab");
        addBlock(UPBlocks.DRYO_FENCE, "Dryophyllum Fence");
        addBlock(UPBlocks.DRYO_FENCE_GATE, "Dryophyllum Fence Gate");
        addBlock(UPBlocks.DRYO_DOOR, "Dryophyllum Door");
        addBlock(UPBlocks.DRYO_TRAPDOOR, "Dryophyllum Trapdoor");
        addBlock(UPBlocks.DRYO_PRESSURE_PLATE, "Dryophyllum Pressure Plate");
        addBlock(UPBlocks.DRYO_BUTTON, "Dryophyllum Button");
        addBlock(UPBlocks.DRYO_LEAVES, "Dryophyllum Leaves");
        addBlock(UPBlocks.DRYO_SAPLING, "Dryophyllum Sapling");

        addBlock(UPBlocks.FOXII_LOG, "Foxii Log");
        addBlock(UPBlocks.STRIPPED_FOXII_LOG, "Stripped Foxii Log");
        addBlock(UPBlocks.FOXII_WOOD, "Foxii Wood");
        addBlock(UPBlocks.STRIPPED_FOXII_WOOD, "Stripped Foxii Wood");
        addBlock(UPBlocks.FOXII_PLANKS, "Foxii Planks");
        addBlock(UPBlocks.FOXII_STAIRS, "Foxii Stairs");
        addBlock(UPBlocks.FOXII_SLAB, "Foxii Slab");
        addBlock(UPBlocks.FOXII_FENCE, "Foxii Fence");
        addBlock(UPBlocks.FOXII_FENCE_GATE, "Foxii Fence Gate");
        addBlock(UPBlocks.FOXII_DOOR, "Foxii Door");
        addBlock(UPBlocks.FOXII_TRAPDOOR, "Foxii Trapdoor");
        addBlock(UPBlocks.FOXII_PRESSURE_PLATE, "Foxii Pressure Plate");
        addBlock(UPBlocks.FOXII_BUTTON, "Foxii Button");
        addBlock(UPBlocks.FOXII_LEAVES, "Foxii Leaves");

        addBlock(UPBlocks.PETRIFIED_LOG, "Petrified Log");
        addBlock(UPBlocks.STRIPPED_PETRIFIED_LOG, "Stripped Petrified Log");
        addBlock(UPBlocks.PETRIFIED_STAIRS, "Petrified Stairs");
        addBlock(UPBlocks.PETRIFIED_SLAB, "Petrified Slab");
        addBlock(UPBlocks.PETRIFIED_FENCE, "Petrified Fence");
        addBlock(UPBlocks.PETRIFIED_FENCE_GATE, "Petrified Fence Gate");
        addBlock(UPBlocks.PETRIFIED_DOOR, "Petrified Door");
        addBlock(UPBlocks.PETRIFIED_TRAPDOOR, "Petrified Trapdoor");
        addBlock(UPBlocks.PETRIFIED_PRESSURE_PLATE, "Petrified Pressure Plate");
        addBlock(UPBlocks.PETRIFIED_BUTTON, "Petrified Button");

        addBlock(UPBlocks.FOSSIL_ORES, "Fossils");

        // Block Eggs
        addBlock(UPBlocks.STETHA_EGGS, "Stethacanthus Eggs");
        addBlock(UPBlocks.BEELZE_EGGS, "Beelzebufo Spawn");
        addBlock(UPBlocks.AMON_EGGS, "Ammonite Eggs");
        addBlock(UPBlocks.DUNK_EGGS, "Dunkleosteus Eggs");
        addBlock(UPBlocks.SCAU_EGGS, "Scaumenacia Eggs");
        addBlock(UPBlocks.KIMMER_EGGS, "Kimmeridgebrachypteraeschnidium Eggs");
        addBlock(UPBlocks.ERYON_EGGS, "Eryon Eggs");
        addBlock(UPBlocks.DIPLOCAULUS_EGGS, "Diplocaulus Eggs");
        addBlock(UPBlocks.HYNERIA_EGGS, "Hyneria Eggs");
        addBlock(UPBlocks.OPHIDION_EGGS, "Ophiodon Eggs");
        addBlock(UPBlocks.TARTUO_EGGS, "Tartuosteus Eggs");

        // Tar
        addBlock(UPBlocks.TAR, "Tar Block");
        addBlock(UPBlocks.SPLATTERED_TAR, "Splattered Tar");

        addBlock(UPBlocks.STONE_FOSSIL, "Stone Fossil");
        addBlock(UPBlocks.DEEPSLATE_FOSSIL, "Deepslate Fossil");
        addBlock(UPBlocks.PLANT_FOSSIL, "Stone Plant Fossil");
        addBlock(UPBlocks.DEEPSLATE_PLANT_FOSSIL, "Deepslate Plant Fossil");
        addBlock(UPBlocks.STONE_TAR_FOSSIL, "Stone Tar Fossil");
        addBlock(UPBlocks.DEEPSLATE_TAR_FOSSIL, "Deepslate Tar Fossil");
        addBlock(UPBlocks.STONE_AMBER_FOSSIL, "Amber Ore");
        addBlock(UPBlocks.DEEPSLATE_AMBER_FOSSIL, "Deepslate Amber Ore");
        addBlock(UPBlocks.PERMAFROST_FOSSIL, "Permafrost Fossil");
        addBlock(UPBlocks.PERMAFROST, "Permafrost");
        addBlock(UPBlocks.ANALYZER, "Analyzer");
        addBlock(UPBlocks.CULTIVATOR, "Cultivator");
        addBlock(UPBlocks.DNA_FRIDGE, "DNA Fridge");
        addBlock(UPBlocks.REX_HEAD, "Tyrannosaurus Head Mount");
        addBlock(UPBlocks.AMMONITE_SHELL, "Ammonite Shell");
        addBlock(UPBlocks.REX_BOOMBOX, "Tyrannosaurus Boombox");
        addBlock(UPBlocks.INCUBATOR, "Incubator");
        addBlock(UPBlocks.ASPHALT, "Asphalt");
        addBlock(UPBlocks.GOLD_ENGRAVED_ASPHALT, "Gold Engraved Asphalt");
        addBlock(UPBlocks.QUARTZ_ENGRAVED_ASPHALT, "Quartz Engraved Asphalt");

        // Untranslated items

        addItem(UPItems.DRYO_BOAT.getFirst(), "Dryophyllum Boat");
        addItem(UPItems.DRYO_BOAT.getSecond(), "Dryophyllum Chest Boat");
        addItem(UPItems.FOXII_BOAT.getFirst(), "Foxii Boat");
        addItem(UPItems.FOXII_BOAT.getSecond(), "Foxii Chest Boat");
        addItem(UPItems.GINKGO_BOAT.getFirst(), "Ginkgo Boat");
        addItem(UPItems.GINKGO_BOAT.getSecond(), "Ginkgo Chest Boat");

        addMusicDisc(UPItems.ZULOGAE_DISC, "Shroomy - Zulogae");
        addMusicDisc(UPItems.ENCASED_DISC, "TheValiantSquidward - Encased");
        addMusicDisc(UPItems.OPALESENCE_DISC, "ChipsTheCat - Opalesence");

        addItem(UPItems.ANIMAL_DNA_BOTTLES, "Bottle of Prehistoric Animal DNA");
        addItem(UPItems.PLANT_DNA_BOTTLES, "Bottle of Prehistoric Plant DNA");

        addItem(UPItems.YIXIAN_RAMPAGE_BOTTLE, "Bottle of Yixian Rampage");
        addItem(UPItems.DORMANT_RAMPAGE_BOTTLE, "Bottle of Dormant Yixian Rampage");
        addItem(UPItems.QUILL_REMEDY, "Rampage Remedy Quill");

        // Foods
        addItem(UPItems.GROG, "Bottle of Grog");
        addItem(UPItems.MEAT_ON_A_STICK, "Meat on a Stick");
        addItem(UPItems.RAW_COTY, "Raw Cotylorhynchus");
        addItem(UPItems.RAW_SCAU, "Raw Scaumenacia");
        addItem(UPItems.RAW_MAMMOTH, "Raw Mammoth");

        // Buckets
        addBucketItem(UPItems.BEELZE_BUCKET.get());
        addBucketItem(UPItems.AMMON_BUCKET.get());
        addBucketItem(UPItems.STETHA_BUCKET.get());
        addBucketItem(UPItems.SCAU_BUCKET.get());
        addBucketItem(UPItems.PALAEO_BUCKET.get());
        addBucketItem(UPItems.JAWLESS_FISH_BUCKET.get());

        // Entity Eggs
        addPrehistoricEgg(UPEntities.COTY, "Cotylorhynchus Egg");
        addPrehistoricEgg(UPEntities.EDAPHOSAURUS, "Edaphosaurus Egg");
        addPrehistoricEgg(UPEntities.ESTEMMENOSUCHUS, "Estemmenosuchus Egg");
        addPrehistoricEgg(UPEntities.HYNERPETON, "Hynerpeton Egg");
        addPrehistoricEgg(UPEntities.ANTARCO, "Antarctopelta Egg");
        addPrehistoricEgg(UPEntities.ANURO, "Anurognathus Egg");
        addPrehistoricEgg(UPEntities.ARCHELON, "Archelon Egg");
        addPrehistoricEgg(UPEntities.AUSTRO, "Austroraptor Egg");
        addPrehistoricEgg(UPEntities.BALAUR, "Balaur Egg");
        addPrehistoricEgg(UPEntities.BRACHI, "Brachiosauurs Egg");
        addPrehistoricEgg(UPEntities.ENCRUSTED, "Encrusted Sack");
        addPrehistoricEgg(UPEntities.GLOBIDENS, "Globidens Egg");
        addPrehistoricEgg(UPEntities.HWACHA, "Hwachavenator Egg");
        addPrehistoricEgg(UPEntities.KAPROSUCHUS, "Kaprosuchus Egg");
        addPrehistoricEgg(UPEntities.KENTRO, "Kentrosaurus Egg");
        addPrehistoricEgg(UPEntities.LONGISQUAMA, "Longisquama Egg");
        addPrehistoricEgg(UPEntities.MAJUNGA, "Majungasaurus Egg");
        addPrehistoricEgg(UPEntities.OVIRAPTOR, "Oviraptor Egg");
        addPrehistoricEgg(UPEntities.PACHY, "Pachycephalosaurus Egg");
        addPrehistoricEgg(UPEntities.PSITTACO, "Psittacosaurus Egg");
        addPrehistoricEgg(UPEntities.PTERODAUSTRO, "Pterodaustro Egg");
        addPrehistoricEgg(UPEntities.TANY, "Tanystropheus Egg");
        addPrehistoricEgg(UPEntities.TRICERATOPS, "Triceratops Egg");
        addPrehistoricEgg(UPEntities.TYRANNOSAURUS, "Tyrannosaurus Egg");
        addPrehistoricEgg(UPEntities.ULUG, "Ulughbegsaurus Egg");
        addPrehistoricEgg(UPEntities.VELOCIRAPTOR, "Velociraptor Egg");
        addPrehistoricEgg(UPEntities.BARINASUCHUS, "Barinasuchus Egg");
        addPrehistoricEgg(UPEntities.MEGALANIA, "Megalania Egg");
        addPrehistoricEgg(UPEntities.PSILOPTERUS, "Psilopterus Egg");
        addPrehistoricEgg(UPEntities.TALPANAS, "Talpanas Egg");

        // Tyrant's crown
        addItem(UPItems.TYRANTS_CROWN, "Tyrant's Crown");

        // Shedscale armor
        addItem(UPItems.SHEDSCALE_HELMET, "Shedscale Wraps");
        addItem(UPItems.SHEDSCALE_CHESTPLATE, "Shedscale Tunic");
        addItem(UPItems.SHEDSCALE_LEGGINGS, "Shedscale Leggings");
        addItem(UPItems.SHEDSCALE_BOOTS, "Shedscale Boots");

        // Shields
        addItem(UPItems.VELOCI_SHIELD, "Veloci-Shield");

        // Bottles
        addItem(UPItems.CAPTURED_KIMMER_BOTTLE, "Bottle of Kimmeridgebrachypteraeschnidium");

        // DNA
        addDNAItem(UPItems.STETHA_DNA.get());
        addDNAItem(UPItems.DUNK_DNA.get());
        addDNAItem(UPItems.MAJUNGA_DNA.get());
        addDNAItem(UPItems.AMMONITE_DNA.get());
        addDNAItem(UPItems.ANURO_DNA.get());
        addDNAItem(UPItems.BEELZ_DNA.get());
        addDNAItem(UPItems.COTY_DNA.get());
        addDNAItem(UPItems.SCAU_DNA.get());
        addDNAItem(UPItems.BRACHI_DNA.get());
        addDNAItem(UPItems.TYRANNO_DNA.get());
        addDNAItem(UPItems.VELOCI_DNA.get());
        addDNAItem(UPItems.TRIKE_DNA.get());
        addDNAItem(UPItems.PACHY_DNA.get());
        addDNAItem(UPItems.ENCRUSTED_DNA.get());
        addDNAItem(UPItems.ERYON_DNA.get());
        addDNAItem(UPItems.HORSETAIL_DNA.get());
        addDNAItem(UPItems.LEEFRUCTUS_DNA.get());
        addDNAItem(UPItems.GINKGO_DNA.get());
        addDNAItem(UPItems.BENNET_DNA.get());
        addDNAItem(UPItems.SARR_DNA.get());
        addDNAItem(UPItems.ARCHAO_DNA.get());
        addDNAItem(UPItems.ANOSTYLOSTRAMA_DNA.get());
        addDNAItem(UPItems.CLATHRODICTYON_DNA.get());
        addDNAItem(UPItems.ARCHAEFRUCTUS_DNA.get());
        addDNAItem(UPItems.NELUMBITES_DNA.get());
        addDNAItem(UPItems.QUEREUXIA_DNA.get());
        addDNAItem(UPItems.ANTARCTO_DNA.get());
        addDNAItem(UPItems.AUSTRO_DNA.get());
        addDNAItem(UPItems.HWACHA_DNA.get());
        addDNAItem(UPItems.KENTRO_DNA.get());
        addDNAItem(UPItems.ULUGH_DNA.get());
        addDNAItem(UPItems.GIGANTO_DNA.get());
        addDNAItem(UPItems.SMILODON_DNA.get());
        addDNAItem(UPItems.MEGATHERIUM_DNA.get());
        addDNAItem(UPItems.PARACER_DNA.get());
        addDNAItem(UPItems.MAMMOTH_DNA.get());
        addDNAItem(UPItems.BARINA_DNA.get());
        addDNAItem(UPItems.PALAEO_DNA.get());
        addDNAItem(UPItems.MEGALANIA_DNA.get());
        addDNAItem(UPItems.TALPANAS_DNA.get());
        addDNAItem(UPItems.TELECREX_DNA.get());
        addDNAItem(UPItems.ZULOAGAE_DNA.get());
        addDNAItem(UPItems.RAIGUENRAYUN_DNA.get());
        addDNAItem(UPItems.FOXII_DNA.get());
        addDNAItem(UPItems.DRYO_DNA.get());
        addDNAItem(UPItems.KIMMER_DNA.get());
        addDNAItem(UPItems.DIPLO_DNA.get());
        addDNAItem(UPItems.HYNERIA_DNA.get());
        addDNAItem(UPItems.OPHIO_DNA.get());
        addDNAItem(UPItems.ESTEMMENO_DNA.get());
        addDNAItem(UPItems.XIPHACT_DNA.get());
        addDNAItem(UPItems.PTERY_DNA.get());
        addDNAItem(UPItems.EDAPHO_DNA.get());
        addDNAItem(UPItems.OVIRAPTOR_DNA.get());
        addDNAItem(UPItems.GLOBIDENS_DNA.get());
        addDNAItem(UPItems.OTAROCYON_DNA.get());
        addDNAItem(UPItems.LONGI_DNA.get());
        addDNAItem(UPItems.JAWLESS_FISH_DNA.get());
        addDNAItem(UPItems.TARTUO_DNA.get());
        addDNAItem(UPItems.TANY_DNA.get());
        addDNAItem(UPItems.PSITTACO_DNA.get());
        addDNAItem(UPItems.PSILO_DNA.get());
        addDNAItem(UPItems.HYNERP_DNA.get());
        addDNAItem(UPItems.BALAUR_DNA.get());
        addDNAItem(UPItems.LEEDS_DNA.get());
        addDNAItem(UPItems.PTERODAUSTRO_DNA.get());
        addDNAItem(UPItems.ARCHELON_DNA.get());
        addDNAItem(UPItems.PROTOSPHYRAENA_DNA.get());
        addDNAItem(UPItems.PANACANTHOCARIS_DNA.get());
        addDNAItem(UPItems.KAPRO_DNA.get());

        // Corals
        addItem(UPItems.CLATHRODICTYON_FAN, "Clathrodictyon Coral Fan");
        addItem(UPItems.DEAD_CLATHRODICTYON_FAN, "Dead Clathrodictyon Coral Fan");
        addBlock(UPBlocks.DEAD_CLATHRODICTYON, "Dead Clathrodictyon Coral");
        addBlock(UPBlocks.DEAD_CLATHRODICTYON_BLOCK, "Dead Clathrodictyon Block");
        addBlock(UPBlocks.CLATHRODICTYON, "Clathrodictyon Coral");
        addBlock(UPBlocks.CLATHRODICTYON_BLOCK, "Clathrodictyon Coral Block");
        addBlock(UPBlocks.ANOSTYLOSTROMA_BLOCK, "Anostylostroma Coral Block");

        //PLANTS
        addBlock(UPBlocks.HORSETAIL, "Horsetail");
        addBlock(UPBlocks.TALL_HORSETAIL, "Tall Horsetail");
        addBlock(UPBlocks.LEEFRUCTUS, "Leefructus");
        addBlock(UPBlocks.BENNETTITALES, "Bennettitales");
        addBlock(UPBlocks.ARCHAEOSIGILARIA, "Archaeosigillaria");
        addBlock(UPBlocks.SARACENIA, "Sarracenia");
        addBlock(UPBlocks.TALL_SARACENIA, "Tall Sarracenia");
        addBlock(UPBlocks.ARCHAEFRUCTUS, "Archaefructus");
        addBlock(UPBlocks.NELUMBITES, "Nelumbites");
        addBlock(UPBlocks.QUEREUXIA, "Quereuxia");
        addBlock(UPBlocks.QUEREUXIA_TOP, "Quereuxia Clovers");
        addBlock(UPBlocks.RAIGUENRAYUN, "Raiguenrayun");

        //FOSSILS
        addBlock(UPBlocks.COTY_FOSSIL, "Cotylorhynchus Fossil");
        addBlock(UPBlocks.STETHA_FOSSIL, "Stethacanthus Fossil");
        addBlock(UPBlocks.ANURO_FOSSIL, "Anurognathus Fossil");
        addBlock(UPBlocks.SCAU_FOSSIL, "Scaumenacia Fossil");
        addBlock(UPBlocks.BEELZE_FOSSIL, "Beelzebufo Fossil");
        addBlock(UPBlocks.BRACHI_FOSSIL, "Brachiosaurus Fossil");
        addBlock(UPBlocks.DUNK_FOSSIL, "Dunkleosteus Fossil");
        addBlock(UPBlocks.MAJUNGA_FOSSIL, "Majungasaurus Fossil");
        addBlock(UPBlocks.PACHY_FOSSIL, "Pachycephalosaurus Fossil");
        addBlock(UPBlocks.VELOCI_FOSSIL, "Velociraptor Fossil");
        addBlock(UPBlocks.ERYON_FOSSIL, "Eryon Fossil");
        addBlock(UPBlocks.AUSTRO_FOSSIL, "Austroraptor Fossil");
        addBlock(UPBlocks.ULUGH_FOSSIL, "Ulughbegsaurus Fossil");
        addBlock(UPBlocks.KENTRO_FOSSIL, "Kentrosaurus Fossil");
        addBlock(UPBlocks.ANTARCTO_FOSSIL, "Antarctopelta Fossil");
        addBlock(UPBlocks.HWACHA_FOSSIL, "Hwachavenator Fossil");

        // Entities

        // Skeletons
        forEntity(UPEntities.TRIKE_SKELETON);
        forEntity(UPEntities.TYRANNO_SKELETON);
        forEntity(UPEntities.UNICORN_SKELETON);

        // Paleo mobs
        forEntity(UPEntities.DIPLOCAULUS);
        forEntity(UPEntities.EDAPHOSAURUS);
        forEntity(UPEntities.ESTEMMENOSUCHUS);
        forEntity(UPEntities.HYNERIA);
        forEntity(UPEntities.HYNERPETON);
        forEntity(UPEntities.JAWLESS_FISH);
        forEntity(UPEntities.PTERYGOTUS);
        forEntity(UPEntities.TARTUOSTEUS);

        // Meso mobs
        forEntity(UPEntities.ARCHELON);
        forEntity(UPEntities.BALAUR);
        forEntity(UPEntities.KAPROSUCHUS);
        forEntity(UPEntities.KIMMER);
        forEntity(UPEntities.LEEDSICHTHYS);
        forEntity(UPEntities.LONGISQUAMA);
        forEntity(UPEntities.OVIRAPTOR);
        forEntity(UPEntities.PROTOSPHYRAENA);
        forEntity(UPEntities.PSITTACO);
        forEntity(UPEntities.PTERODAUSTRO);
        forEntity(UPEntities.TANY);
        forEntity(UPEntities.XIPH);

        // Ceno mobs
        forEntity(UPEntities.OPHIODON);
        forEntity(UPEntities.OTAROCYON);
        forEntity(UPEntities.PSILOPTERUS);
        forEntity(UPEntities.TELECREX);
        forEntity(UPEntities.UNICORN);

        // Monsters
        forEntity(UPEntities.ENCRUSTED);
        forEntity(UPEntities.SLUDGE);

        // Projectiles
        forEntity(UPEntities.OPALESCENT_PEARL);
        forEntity(UPEntities.OPALESCENT_SHURIKEN);

        // Entities with the wrong ids (curses upon you Peeko!)

        // Paleo mobs
        addEntityType(UPEntities.AMMON, "Ammonite");
        addEntityType(UPEntities.COTY, "Cotylorhynchus");
        addEntityType(UPEntities.DUNK, "Dunkleosteus");
        addEntityType(UPEntities.SCAU, "Scaumenacia");
        addEntityType(UPEntities.STETHACANTHUS, "Stethacanthus");

        // Meso mobs
        addEntityType(UPEntities.ANTARCO, "Antarctopelta");
        addEntityType(UPEntities.ANURO, "Anurognathus");
        addEntityType(UPEntities.AUSTRO, "Austroraptor");
        addEntityType(UPEntities.BEELZ, "Beelzebufo");
        addEntityType(UPEntities.BEELZE_TADPOLE, "Beelzebufo Tadpole");
        addEntityType(UPEntities.BRACHI, "Brachiosaurus");
        addEntityType(UPEntities.ERYON, "Eryon");
        addEntityType(UPEntities.HWACHA, "Hwachavenator");
        addEntityType(UPEntities.KENTRO, "Kentrosaurus");
        addEntityType(UPEntities.MAJUNGA, "Majungasaurus");
        addEntityType(UPEntities.PACHY, "Pachycephalosaurus");
        addEntityType(UPEntities.TRICERATOPS, "Triceratops");
        addEntityType(UPEntities.TYRANNOSAURUS, "Tyrannosaurus");
        addEntityType(UPEntities.ULUG, "Ulughbegsaurus");
        addEntityType(UPEntities.VELOCIRAPTOR, "Velociraptor");

        // Ceno mobs
        addEntityType(UPEntities.BARINASUCHUS, "Barinasuchus");
        addEntityType(UPEntities.GIGANTOPITHICUS, "Gigantopithecus");
        addEntityType(UPEntities.MAMMOTH, "Mammoth");
        addEntityType(UPEntities.MEGALANIA, "Megalania");
        addEntityType(UPEntities.MEGATHERIUM, "Megatherium");
        addEntityType(UPEntities.PALAEOPHIS, "Palaeolophis");
        addEntityType(UPEntities.PALAEOPHIS_PART, "Palaeophis Body");
        addEntityType(UPEntities.BABY_PALAEO, "Palaeolophis Hatchling");
        addEntityType(UPEntities.PARACERATHERIUM, "Paraceratherium");
        addEntityType(UPEntities.SMILODON, "Smilodon");
        addEntityType(UPEntities.TALPANAS, "Talpanas");

        // Projectiles
        addEntityType(UPEntities.AMBER_SHOT, "Amber Shot");
        addEntityType(UPEntities.HWACHA_SPIKE, "Hwachavenator Pin");

        // Misc entities
        addEntityType(UPEntities.ICEBERG_MAMMOTH, "Frozen Mammoth");
        addEntityType(UPEntities.ICEBERG_SMILODON, "Frozen Smilodon");

        // Sounds
        addSound(UPSounds.BEELZE_IDLE, "Beelzebufo croaks");
        addSound(UPSounds.BEELZE_ATTACK, "Beelzebufo chomps");
        addSound(UPSounds.BEELZE_HURT, "Beelzebufo hurts");
        addSound(UPSounds.BEELZE_DEATH, "Beelzebufo dies");

        addSound(UPSounds.MAJUNGA_IDLE, "Majungasaurus growls");
        addSound(UPSounds.MAJUNGA_ATTACK, "Majungasaurus bites");
        addSound(UPSounds.MAJUNGA_HURT, "Majungasaurus hurts");
        addSound(UPSounds.MAJUNGA_DEATH, "Majungasaurus dies");
        addSound(UPSounds.MAJUNGA_STEP, "Heavy footsteps");

        addSound(UPSounds.ANURO_IDLE, "Anurognathus squaks");
        addSound(UPSounds.ANURO_HURT, "Anurognathus hurts");
        addSound(UPSounds.ANURO_DEATH, "Anurognathus dies");

        addSound(UPSounds.COTY_IDLE, "Cotylorhynchus groans");
        addSound(UPSounds.COTY_HURT, "Cotylorhynchus hurts");
        addSound(UPSounds.COTY_DEATH, "Cotylorhynchus dies");
        addSound(UPSounds.COTY_BURP, "Cotylorhynchus burps");

        addSound(UPSounds.EDAPHO_IDLE, "Edaphosaurus groans");
        addSound(UPSounds.EDAPHO_HURT, "Edaphosaurus hurts");
        addSound(UPSounds.EDAPHO_DEATH, "Edaphosaurus dies");

        addSound(UPSounds.ESTEMME_IDLE, "Estemmenosuchus groans");
        addSound(UPSounds.ESTEMME_HURT, "Estemmenosuchus hurts");
        addSound(UPSounds.ESTEMME_DEATH, "Estemmenosuchus dies");

        addSound(UPSounds.UNICORN_IDLE, "Unicorn groans");
        addSound(UPSounds.UNICORN_HURT, "Unicorn hurts");
        addSound(UPSounds.UNICORN_DEATH, "Unicorn dies");

        addSound(UPSounds.AMMONITE_HURT, "Ammonite hurts");
        addSound(UPSounds.AMMONITE_DEATH, "Ammonite dies");
        addSound(UPSounds.AMMONITE_FLOP, "Ammonite flops");

        addSound(UPSounds.DUNK_HURT, "Dunkleosteus hurts");
        addSound(UPSounds.DUNK_ATTACK, "Dunkleosteus bites");
        addSound(UPSounds.DUNK_DEATH, "Dunkleosteus dies");

        addSound(UPSounds.DIPLO_IDLE, "Diplocaulus croaks");
        addSound(UPSounds.DIPLO_HURT, "Diplocaulus hurts");
        addSound(UPSounds.DIPLO_DEATH, "Diplocaulus dies");

        addSound(UPSounds.HYNERIA_ATTACK, "Hyneria bites");
        addSound(UPSounds.HYNERIA_HURT, "Hyneria hurts");
        addSound(UPSounds.HYNERIA_DEATH, "Hyneria dies");
        addSound(UPSounds.HYNERIA_JUMP, "Hyneria jumps");

        addSound(UPSounds.TYRANNO_IDLE, "Tyrannosaurus drones");
        addSound(UPSounds.TYRANNO_STOMP_ATTACK, "Tyrannosaurus stomps");
        addSound(UPSounds.TYRANNO_BITE, "Tyrannosaurus chomps");
        addSound(UPSounds.TYRANNO_TAIL_SWIPE, "Tyrannosaurus swipes");
        addSound(UPSounds.TYRANNO_HURT, "Tyrannosaurus hurts");
        addSound(UPSounds.TYRANNO_DEATH, "Tyrannosaurus dies");
        addSound(UPSounds.TYRANNO_ROAR, "Tyrannosaurus roars");
        addSound(UPSounds.TYRANNO_SNIFF, "Tyrannosaurus sniffs");
        addSound(UPSounds.TYRANNO_REVIVE, "Tyrannosaurus was pacified");

        addSound(UPSounds.TYRANNO_STEP, "Forceful footsteps");

        addSound(UPSounds.TRIKE_IDLE, "Triceratops stouts");
        addSound(UPSounds.TRIKE_HURT, "Triceratops hurts");
        addSound(UPSounds.TRIKE_DEATH, "Triceratops dies");
        addSound(UPSounds.TRIKE_CHATTER, "Triceratops chatters");
        addSound(UPSounds.TRIKE_WARN, "Triceratops warns");

        addSound(UPSounds.BRACHI_IDLE, "Brachiosaurus bellows");
        addSound(UPSounds.BRACHI_HURT, "Brachiosaurus hurts");
        addSound(UPSounds.BRACHI_TOSS, "Brachiosaurus tosses");
        addSound(UPSounds.BRACHI_STEP, "Massive footsteps");
        addSound(UPSounds.BRACHI_DEATH, "Brachiosaurus dies");
        addSound(UPSounds.BRACHI_STOMP, "Brachiosaurus stomps");

        addSound(UPSounds.ENCRUSTED_IDLE, "Encrusted bzzzzzzzzs");
        addSound(UPSounds.ENCRUSTED_HURT, "Encrusted hurts");
        addSound(UPSounds.ENCRUSTED_MELEE, "Encrusted zzzts");
        addSound(UPSounds.ENCRUSTED_SPIT, "Encrusted pzzts");
        addSound(UPSounds.ENCRUSTED_DEATH, "Encrusted dies");

        addSound(UPSounds.VELOCIRAPTOR_IDLE, "Velociraptor coos");
        addSound(UPSounds.VELOCIRAPTOR_HURT, "Velociraptor hurts");
        addSound(UPSounds.VELOCIRAPTOR_ATTACK, "Velociraptor veloci-slashes");
        addSound(UPSounds.VELOCIRAPTOR_SEARCH, "Velociraptor coos curiously");
        addSound(UPSounds.VELOCIRAPTOR_DEATH, "Velociraptor dies");

        addSound(UPSounds.PSILO_IDLE, "Psilopterus squawks");
        addSound(UPSounds.PSILO_HURT, "Psilopterus hurts");
        addSound(UPSounds.PSILO_DEATH, "Psilopterus dies");

        addSound(UPSounds.NYCTORAPTOR_IDLE, "Nyctoraptor hisses");
        addSound(UPSounds.NYCTORAPTOR_HURT, "Nyctoraptor hurts");
        addSound(UPSounds.NYCTORAPTOR_CHATTER, "Nyctoraptor screeches");
        addSound(UPSounds.NYCTORAPTOR_DEATH, "Nyctoraptor dies");

        addSound(UPSounds.OVIRAPTOR_IDLE, "Oviraptor squawks");
        addSound(UPSounds.OVIRAPTOR_HURT, "Oviraptor hurts");
        addSound(UPSounds.OVIRAPTOR_DEATH, "Oviraptor dies");

        addSound(UPSounds.BALAUR_IDLE, "Balaur chirps");
        addSound(UPSounds.BALAUR_HURT, "Balaur hurts");
        addSound(UPSounds.BALAUR_DEATH, "Balaur dies");

        addSound(UPSounds.PACHY_IDLE, "Pachycephalosaurus grunts");
        addSound(UPSounds.PACHY_HURT, "Pachycephalosaurus hurts");
        addSound(UPSounds.PACHY_HEADBUTT, "Pachycephalosaurus bonks");
        addSound(UPSounds.PACHY_KICK, "Pachycephalosaurus kicks");
        addSound(UPSounds.PACHY_DEATH, "Pachycephalosaurus dies");

        addSound(UPSounds.ERYON_IDLE, "Eryon noises");
        addSound(UPSounds.ERYON_HURT, "Eryon hurts");
        addSound(UPSounds.ERYON_DEATH, "Eryon dies");

        addSound(UPSounds.AUSTRO_BITE, "Austroraptor nips");
        addSound(UPSounds.AUSTRO_DEATH, "Austroraptor dies");
        addSound(UPSounds.AUSTRO_HURT, "Austroraptor hurts");
        addSound(UPSounds.AUSTRO_IDLE, "Austroraptor coos");
        addSound(UPSounds.AUSTRO_PREEN, "Austroraptor preens");

        addSound(UPSounds.HWACHA_DEATH, "Hwachavenator dies");
        addSound(UPSounds.HWACHA_HURT, "Hwachavenator hurts");
        addSound(UPSounds.HWACHA_IDLE, "Hwachavenator hums");
        addSound(UPSounds.HWACHA_ROAR, "Hwachavenator screeches");
        addSound(UPSounds.HWACHA_SHOOT, "Hwachavenator shoots");

        addSound(UPSounds.KENTRO_DEATH, "Kentrosaurus dies");
        addSound(UPSounds.KENTRO_HURT, "Kentrosaurus hurts");
        addSound(UPSounds.KENTRO_IDLE, "Kentrosaurus growls");

        addSound(UPSounds.TAIL_SWIPE, "Tail swipes");

        addSound(UPSounds.ULUGH_BITE, "Ulughbegsaurus gnaws");
        addSound(UPSounds.ULUGH_DEATH, "Ulughbegsaurus dies");
        addSound(UPSounds.ULUGH_HURT, "Ulughbegsaurus hurts");
        addSound(UPSounds.ULUGH_IDLE, "Ulughbegsaurus groans");

        addSound(UPSounds.ULUGH_STEP, "Heavy footsteps");

        addSound(UPSounds.ANTARCTO_DEATH, "Antarctopelta dies");
        addSound(UPSounds.ANTARCTO_HURT, "Antarctopelta hurts");
        addSound(UPSounds.ANTARCTO_IDLE, "Antarctopelta gruffs");

        addSound(UPSounds.REX_BOOMBOX, "Tyrannosaurus Boombox plays");

        addSound(UPSounds.GIGANTO_IDLE, "Gigantopithicus Ooo OO Ah Ahs");
        addSound(UPSounds.GIGANTO_DEATH, "Gigantopithicus dies");
        addSound(UPSounds.GIGANTO_HURT, "Gigantopithicus hurts");
        addSound(UPSounds.GIGANTO_TRADE, "Gigantopithicus OOOoooooOOs");

        addSound(UPSounds.MAMMOTH_DEATH, "Mammoth dies");
        addSound(UPSounds.MAMMOTH_HURT, "Mammoth hurts");
        addSound(UPSounds.MAMMOTH_IDLE, "Mammoth pawoos");

        addSound(UPSounds.PARACER_DEATH, "Paraceratherium dies");
        addSound(UPSounds.PARACER_HURT, "Paraceratherium hurts");
        addSound(UPSounds.PARACER_IDLE, "Paraceratherium grunts");
        addSound(UPSounds.PARACER_STOMP, "Paraceratherium stomps");

        addSound(UPSounds.TALPANAS_DEATH, "Talpanas dies");
        addSound(UPSounds.TALPANAS_HURT, "Talpanas hurts");
        addSound(UPSounds.TALPANAS_IDLE, "Talpanas quacks");
        addSound(UPSounds.TALPANAS_PANIC, "Talpanas panics");

        addSound(UPSounds.TELECREX_DEATH, "Telecrex dies");
        addSound(UPSounds.TELECREX_HURT, "Telecrex hurts");
        addSound(UPSounds.TELECREX_IDLE, "Telecrex squawks");
        addSound(UPSounds.TELECREX_FLAP, "Telecrex flaps");

        addSound(UPSounds.CROCARINA, "Crocarina plays");

        addSound(UPSounds.BARINA_DEATH, "Barinasuchus dies");
        addSound(UPSounds.BARINA_HURT, "Barinasuchus hurts");
        addSound(UPSounds.BARINA_IDLE, "Barinasuchus growls");
        addSound(UPSounds.BARINA_BITE, "Barinasuchus chomps");

        addSound(UPSounds.ZULOGAE_DISC, "Music Disc");
        addSound(UPSounds.ENCASED_DISC, "Music Disc");
        addSound(UPSounds.OPALESENCE_DISC, "Music Disc");

        addSound(UPSounds.MEGATHER_DEATH, "Megatherium dies");
        addSound(UPSounds.MEGATHER_HURT, "Megatherium hurts");
        addSound(UPSounds.MEGATHER_IDLE, "Megatherium groans");
        addSound(UPSounds.SMILODON_DEATH, "Smilodon dies");
        addSound(UPSounds.SMILODON_HURT, "Smilodon hurts");
        addSound(UPSounds.SMILODON_IDLE, "Smilodon snarls");

        addSound(UPSounds.PALAEO_HURT, "Palaeophis hurts");
        addSound(UPSounds.PALAEO_DEATH, "Palaeophis dies");
        addSound(UPSounds.PALAEO_IDLE, "Palaeophis hisses");
        addSound(UPSounds.PALAEO_BITE, "Palaeophis snaps");

        addSound(UPSounds.TAR_POP, "Tar pops");
        addSound(UPSounds.TAR_AMBIENT, "Tar settles");

        addSound(UPSounds.MEGALANIA_DEATH, "Megalania dies");
        addSound(UPSounds.MEGALANIA_HURT, "Megalania hurts");
        addSound(UPSounds.MEGALANIA_IDLE, "Megalania hisses");
        addSound(UPSounds.MEGALANIA_BITE, "Megalania bites");

        addSound(UPSounds.GLOBIDENS_DEATH, "Globidens dies");
        addSound(UPSounds.GLOBIDENS_HURT, "Globidens hurts");
        addSound(UPSounds.GLOBIDENS_IDLE, "Globidens growls");

        addSound(UPSounds.SLUDGE_DEATH, "Sludge dies");
        addSound(UPSounds.SLUDGE_HURT, "Sludge hurts");
        addSound(UPSounds.SLUDGE_IDLE, "Sludge gurgles");
        addSound(UPSounds.SLUDGE_SLAM, "Sludge slams");
        addSound(UPSounds.SLUDGE_SLAP, "Sludge slaps");

        addSound(UPSounds.LEEDS_IDLE, "Leedsichthys bellows");
        addSound(UPSounds.LEEDS_FLOP, "Leedsichthys flops");
        addSound(UPSounds.LEEDS_HURT, "Leedsichthys hurts");
        addSound(UPSounds.LEEDS_DEATH, "Leedsichthys dies");

        addSound(UPSounds.KIMMER_FLAP, "Kimmeridgebrachypteraeschnidium buzzes");
        addSound(UPSounds.KIMMER_HURT, "Kimmeridgebrachypteraeschnidium hurts");
        addSound(UPSounds.KIMMER_DEATH, "Kimmeridgebrachypteraeschnidium dies");

        add("entity.unusualprehistory.all.command_0", "%s is wandering");
        add("entity.unusualprehistory.all.command_1", "%s is following");
        add("entity.unusualprehistory.all.command_2", "%s is staying");

        add("entity.unusualprehistory.skeleton.natural_false", "Set %s \"Natural\" to false");
        add("entity.unusualprehistory.skeleton.natural_true", "Set %s \"Natural\" to true");

        add("block.unusualprehistory.majunga_egg.desc", "%s x %s");
        add("block.unusualprehistory.coty_egg.desc", "%s x %s");
        add("item.unusualprehistory.encyclopedia.desc", "By Award Winning Scientist, Peeko Noneyah");
        add("hwachavenator.shooting_too_close.message", "You are trying to shoot too close to Hwachavenator!");
        add("hwachavenator.fail_shoot.message", "You failed to shoot!");
        add("player_capability.amber_protection_full", "You are at full Amber Protection and won't gain any more!");
        add("dinosaur.start_riding.attack_key", "Press G to attack!");
        add("death.attack.hwacha_0", "%s was turned into a pincushion");
        add("death.attack.hwacha_1", "%s was shredded");
        add("death.attack.hwacha_2", "%s was turned to fine paste");
        add("death.attack.hwacha_3", "%s was shot");
        add("instrument.unusualprehistory.ocarina", "Crocodilian Lullaby");
        add("entity.tyrannosaurus.revive.message", "Revived %s");

        add("unusualprehistory.advancement.root", "Unusual Prehistory");
        add("unusualprehistory.advancement.root.desc", "Begin Your Journey Into the World of Prehistory!");

        add("unusualprehistory.advancement.acquire_fossil", "Odd Looking Rocks");
        add("unusualprehistory.advancement.acquire_fossil.desc", "Find your first Fossil");

        add("unusualprehistory.advancement.craft_analyzer", "Sciency Science Doodad");
        add("unusualprehistory.advancement.craft_analyzer.desc", "Craft a Analyzer");

        add("unusualprehistory.advancement.acquire_amber_fossil", "Unusual Park");
        add("unusualprehistory.advancement.acquire_amber_fossil.desc", "Unlock the treasures of Amber Fossils");

        add("unusualprehistory.advancement.acquire_adorned_staff", "Hammond's Staff");
        add("unusualprehistory.advancement.acquire_adorned_staff.desc", "Craft a Adorned Staff!");

        add("unusualprehistory.advancement.craft_cultivator", "Its Time to Cook Jesse");
        add("unusualprehistory.advancement.craft_cultivator.desc", "Craft a Cultivator");

        add("unusualprehistory.advancement.obtain_egg", "E G G S");
        add("unusualprehistory.advancement.obtain_egg.desc", "Acquire your first Prehistoric Egg!");

        add("unusualprehistory.advancement.obtain_embryo", "Funny Gummies");
        add("unusualprehistory.advancement.obtain_embryo.desc", "Acquire your first Embryo!");

        add("unusualprehistory.advancement.interact_ammonite", "One Shell of a Time");
        add("unusualprehistory.advancement.interact_ammonite.desc", "Revive a Ammonite");

        add("unusualprehistory.advancement.obtain_ammon_drop", "No Ammonites Were Harmed....");
        add("unusualprehistory.advancement.obtain_ammon_drop.desc", "Acquire Shell Shards");

        add("unusualprehistory.advancement.obtain_ammon_weapon", "Diggy Diggy Slash!");
        add("unusualprehistory.advancement.obtain_ammon_weapon.desc", "Craft a War Pick!");

        add("unusualprehistory.advancement.interact_antarcto", "Primal Grudge");
        add("unusualprehistory.advancement.interact_antarcto.desc", "Revive a Antarctopelta");

        add("unusualprehistory.advancement.obtain_antarcto_weapon", "Can Slice the Head of a Horse Clean Off...");
        add("unusualprehistory.advancement.obtain_antarcto_weapon.desc", "Acquire a Primal Macuahuitl");

        add("unusualprehistory.advancement.interact_austro", "The Graceful Raptor");
        add("unusualprehistory.advancement.interact_austro.desc", "Revive a Austroraptor");

        add("unusualprehistory.advancement.obtain_austro_boots", "So Long Fall Damage");
        add("unusualprehistory.advancement.obtain_austro_boots.desc", "Acquire pair of Austro Boots");

        add("unusualprehistory.advancement.interact_beelze", "A frogge biþ a large beaste wiþ foure leggys");
        add("unusualprehistory.advancement.interact_beelze.desc", "Revive a Beelzebufo");

        add("unusualprehistory.advancement.obtain_meat_stick", "What a Novel Concept!");
        add("unusualprehistory.advancement.obtain_meat_stick.desc", "Craft Meat on a Stick to Control a Beelzebufo!");

        add("unusualprehistory.advancement.interact_pachy", "Thick Skulled");
        add("unusualprehistory.advancement.interact_pachy.desc", "Revive a Pachycephalosaurus");

        add("unusualprehistory.advancement.interact_ulugh", "How Do You Say This?");
        add("unusualprehistory.advancement.interact_ulugh.desc", "Revive a Ulughbegsaurus");

        add("unusualprehistory.advancement.interact_kentro", "Spiked Nugget");
        add("unusualprehistory.advancement.interact_kentro.desc", "Revive a Kentrosaurus");

        add("unusualprehistory.advancement.interact_stetha", "Not Quite a Shark");
        add("unusualprehistory.advancement.interact_stetha.desc", "Revive a Stethacanthus");

        add("unusualprehistory.advancement.interact_eryon", "Diggy Diggy Hole");
        add("unusualprehistory.advancement.interact_eryon.desc", "Revive a Eryon");

        add("unusualprehistory.advancement.obtain_fossil_stand", "Gotta Collect Em All!");
        add("unusualprehistory.advancement.obtain_fossil_stand.desc", "Acquire your first Fossil Stand!");

        add("unusualprehistory.advancement.interact_hwacha", "It Costs 400,000 Dollars to Fire This Weapon, for 12 Seconds");
        add("unusualprehistory.advancement.interact_hwacha.desc", "Revive a Hwachavenator");

        add("unusualprehistory.advancement.interact_brachi", "Hows the View Up There?");
        add("unusualprehistory.advancement.interact_brachi.desc", "Revive a Brachiosaurus");

        add("unusualprehistory.advancement.interact_scau", "Shark Bait Hoo Ha Ha");
        add("unusualprehistory.advancement.interact_scau.desc", "Revive a Scaumenacia");

        add("unusualprehistory.advancement.obtain_golden_scau", "The Buddha");
        add("unusualprehistory.advancement.obtain_golden_scau.desc", "Craft an appetizing Golden Scaumenacia");

        add("unusualprehistory.advancement.interact_majunga", "Are we in San Diego?");
        add("unusualprehistory.advancement.interact_majunga.desc", "Revive an Majungasaurus");

        add("unusualprehistory.advancement.obtain_majunga_helmet", "Thy Strength Befits a Crown");
        add("unusualprehistory.advancement.obtain_majunga_helmet.desc", "Craft a Majunga Helmet");

        add("unusualprehistory.advancement.interact_veloci", "Clever Girl");
        add("unusualprehistory.advancement.interact_veloci.desc", "Revive a Velociraptor");

        add("unusualprehistory.advancement.obtain_veloci_shield", "Zooooooom");
        add("unusualprehistory.advancement.obtain_veloci_shield.desc", "Craft a Veolcishield");

        add("unusualprehistory.advancement.interact_dunk", "Definitely not a Shark");
        add("unusualprehistory.advancement.interact_dunk.desc", "Revive a Dunkleosteus");

        add("unusualprehistory.advancement.interact_anuro", "No Thoughts");
        add("unusualprehistory.advancement.interact_anuro.desc", "Revive a Anurognathus");

        add("unusualprehistory.advancement.interact_trike", "A Challenger to the Throne");
        add("unusualprehistory.advancement.interact_trike.desc", "Revive a Triceratops");

        add("unusualprehistory.advancement.obtain_trike_shield", "Parry This!");
        add("unusualprehistory.advancement.obtain_trike_shield.desc", "Acquire a Trike Shield");

        add("unusualprehistory.advancement.interact_coty", "Where do you Work Out?");
        add("unusualprehistory.advancement.interact_coty.desc", "Revive a Cotylorhynchus");

        add("unusualprehistory.advancement.obtain_grog", "Drunken Sailor");
        add("unusualprehistory.advancement.obtain_grog.desc", "Gather some Grog!");

        add("unusualprehistory.advancement.interact_rex", "King of the Terrible Lizards");
        add("unusualprehistory.advancement.interact_rex.desc", "Revive a Tyrannosaurus");

        add("unusualprehistory.advancement.rex_passify", "Down With the King!");
        add("unusualprehistory.advancement.rex_passify.desc", "Beat a Tyrannosaurus to its last breath and spare it");

        add("unusualprehistory.advancement.interact_smilo", "Humanities Greatest Foe");
        add("unusualprehistory.advancement.interact_smilo.desc", "Revive a Smilodon");

        add("unusualprehistory.advancement.obtain_insulator", "Embryonic Key");
        add("unusualprehistory.advancement.obtain_insulator.desc", "Craft a Insulator");

        add("unusualprehistory.advancement.craft_incubator", "GET IN THE POD");
        add("unusualprehistory.advancement.craft_incubator.desc", "Craft a Birthing Pod");

        add("unusualprehistory.advancement.interact_palaeo", "Hitbox Nightmare");
        add("unusualprehistory.advancement.interact_palaeo.desc", "Revive a Palaeophis");

        add("unusualprehistory.advancement.obtain_shedscale", "Cover me In Skin");
        add("unusualprehistory.advancement.obtain_shedscale.desc", "Obtain a Piece of Shedscale Armor");

        add("unusualprehistory.advancement.interact_giganto", "Give Orange Me Give Eat Orange");
        add("unusualprehistory.advancement.interact_giganto.desc", "Revive a Gigantopithecus");

        add("unusualprehistory.advancement.obtain_monkey_weapon", "Blood, Sweat, and Bamboo");
        add("unusualprehistory.advancement.obtain_monkey_weapon.desc", "Acquire All Handmade Weapons!");

        add("unusualprehistory.advancement.obtain_monkey_fruits", "Strange Fruits");
        add("unusualprehistory.advancement.obtain_monkey_fruits.desc", "Acquire Any of the Fruits from Gigantopithecus");

        add("unusualprehistory.advancement.interact_talpanas", "Duck Fortress");
        add("unusualprehistory.advancement.interact_talpanas.desc", "Revive a Talpanas");

        add("unusualprehistory.advancement.interact_mammoth", "Ice Age Super Star");
        add("unusualprehistory.advancement.interact_mammoth.desc", "Revive a Mammoth");

        add("unusualprehistory.advancement.interact_barina", "Crikey!");
        add("unusualprehistory.advancement.interact_barina.desc", "Revive a Barinasuchus");

        add("unusualprehistory.advancement.tame_barina", "Crocarina of Time");
        add("unusualprehistory.advancement.tame_barina.desc", "Use a Crocarina to Tame a Barinasuchus");

        add("unusualprehistory.advancement.interact_megala", "Health Sapper");
        add("unusualprehistory.advancement.interact_megala.desc", "Revive a Megalania");

        add("unusualprehistory.advancement.interact_otarocyon", "Creatures of the Night");
        add("unusualprehistory.advancement.interact_otarocyon.desc", "Revive a Otarocyon");

        add("unusualprehistory.advancement.petrified_wood", "Odd Looking Wood");
        add("unusualprehistory.advancement.petrified_wood.desc", "Find Some Petrified Wood");

        add("unusualprehistory.advancement.prehistoric_plants", "Unusual Botany");
        add("unusualprehistory.advancement.prehistoric_plants.desc", "Revive your First Prehistoric Plant");

        add("unusualprehistory.advancement.ginkgo", "Priest of Hiroshima");
        add("unusualprehistory.advancement.ginkgo.desc", "Revive a Ginkgo Tree");

        add("unusualprehistory.advancement.foxii", "Is it Foxxi or Foxii?");
        add("unusualprehistory.advancement.foxii.desc", "Revive a Metasequoia Foxii");

        add("unusualprehistory.advancement.dryo", "Almost Cherry");
        add("unusualprehistory.advancement.dryo.desc", "Revive a Dryophyllum Tree");

        add("unusualprehistory.advancement.zuloagae", "Bamboo of the Gods");
        add("unusualprehistory.advancement.zuloagae.desc", "Revive Zuloagae");

        add("unusualprehistory.advancement.interact_paracer", "Ticked Off");
        add("unusualprehistory.advancement.interact_paracer.desc", "Revive a Paraceratherium");

        add("unusualprehistory.advancement.opal_fossil", "The Power of the Abyss in the Palm of My Hand...");
        add("unusualprehistory.advancement.opal_fossil.desc", "Obtain a Opal Fossil");

        add("unusualprehistory.advancement.opal_shuriken", "Shuriken, Japan");
        add("unusualprehistory.advancement.opal_shuriken.desc", "Obtain Opalscent Shuriken");

        add("unusualprehistory.advancement.opal_pearl", "Gamebreaker");
        add("unusualprehistory.advancement.opal_pearl.desc", "Obtain Opalscent Pearl");

        add("unusualprehistory.advancement.mammoth_meatball", "Meatball of Valhalla");
        add("unusualprehistory.advancement.mammoth_meatball.desc", "Through Unspeakable Crimes, Obtain a Mammoth Meatball");

        add("unusualprehistory.advancement.amber_gummy", "Extra Protection");
        add("unusualprehistory.advancement.amber_gummy.desc", "Obtain Amber Gummies");

        add("unusualprehistory.advancement.interact_encrusted", "Chaos Theory");
        add("unusualprehistory.advancement.interact_encrusted.desc", "Revive a Encrusted");

        add("unusualprehistory.advancement.interact_sludge", "It Came from The Pit");
        add("unusualprehistory.advancement.interact_sludge.desc", "Encounter a Sludge");

        add("death.attack.hwacha_0.player", "%s was turned into a pincushion by %s");
        add("death.attack.hwacha_1.player", "%s was shredded by %s");
        add("death.attack.hwacha_2.player", "%s was turned to fine paste by %s");
        add("death.attack.hwacha_3.player", "%s was shot by %s");

        add("death.attack.sludge_0.player", "%s was slapped to death by %s");

        add("unusualprehistory.megalania_damage", "%s Venomous Coating");
        add("unusualprehistory.battle_axe", "An axe made for battle. Cutting wood effectively goes poorly..");
        add("unusualprehistory.shedscale_bonus", "Inverts some negative effects while wearing full set");
        add("unusualprehistory.fruit_loot_box.salubrious", "Salubrious Fruit Block");
        add("unusualprehistory.fruit_loot_box.luxurious", "Luxurious Fruit Block");
        add("unusualprehistory.fruit_loot_box.redolent", "Redolent Fruit Block");
        add("unusualprehistory.fruit_loot_box.exotic", "Exotic Fruit Block");
        add("unusualprehistory.fruit_loot_box.default", "Fruit Block");
        add("unusualprehistory.attacher.animal_has_embryo", "%s already has an embryo");
        add("unusualprehistory.attacher.animal_not_correct", "%s cannot be inserted into %s");
        add("unusualprehistory.beelzebufo.meat_stick", "Hold coty on a stick to control");
        add("unusualprehistory.musical_tame.tame", "Tamed %s");
        add("unusualprehistory.attacher.embryo_attached", "Successfully attached embryo to %s!");
        add("unusualprehistory.megatherium_baby.tame_tooltip", "Tamed Megatherium");
        add("unusualprehistory.megatherium_baby.minutes_tooltip", "%s minutes left until Megatherium is tamed!");
        add("unusualprehistory.megatherium_baby.minute_tooltip", "%s minute left until Megatherium is tamed!");
        add("unusualprehistory.encyclopedia.clathrodictyon", "Clathrodictyon");
        add("unusualprehistory.encyclopedia.dryo_sapling", "Dryophyllum");
        add("unusualprehistory.encyclopedia.foxii", "Foxii");
        add("unusualprehistory.encyclopedia.ginkgo_sapling", "Ginkgo");
        add("unusualprehistory.encyclopedia.dinosaurs", "Animals of Prehistory");
        add("unusualprehistory.encyclopedia.help", "Oh no, something went wrong!");
        add("unusualprehistory.encyclopedia.plants","Plants of Prehistory");
        add("unusualprehistory.root", "Encyclopedia of Prehistory");
        add("unusualprehistory.encyclopedia.root", "Unusual Prehistory");
        add("unusualprehistory.root.1", "Fossils");
        add("unusualprehistory.root.2", "Basics of Revival");
        add("unusualprehistory.root.3", "Plants of Prehistory");
        add("unusualprehistory.root.4", "Animals of Prehistory");
        add("unusualprehistory.encyclopedia.tutorial", "Intro to Revival");
        add("unusualprehistory.encyclopedia.fossils", "Fossils");

        // Kimmer bottle
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_base_color.0", "Black Body");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_base_color.1", "Blue Body");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_base_color.2", "Brown Body");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_base_color.3", "Cyan Body");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_base_color.4", "Gray Body");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_base_color.5", "Green Body");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_base_color.6", "Light Blue Body");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_base_color.7", "Light Gray Body");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_base_color.8", "Lime Body");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_base_color.9", "Magenta Body");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_base_color.10", "Orange Body");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_base_color.11", "Pink Body");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_base_color.12", "Purple Body");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_base_color.13", "Red Body");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_base_color.14", "White Body");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_base_color.15", "Yellow Body");

        add("unusualprehistory.kimmeridgebrachypteraeschnidium_wing_color.0", "Black Wings");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_wing_color.1", "Blue Wings");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_wing_color.2", "Brown Wings");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_wing_color.3", "Cyan Wings");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_wing_color.4", "Gray Wings");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_wing_color.5", "Green Wings");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_wing_color.6", "Light Blue Wings");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_wing_color.7", "Light Gray Wings");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_wing_color.8", "Lime Wings");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_wing_color.9", "Magenta Wings");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_wing_color.10", "Orange Wings");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_wing_color.11", "Pink Wings");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_wing_color.12", "Purple Wings");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_wing_color.13", "Red Wings");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_wing_color.14", "White Wings");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_wing_color.15", "Yellow Wings");

        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern_color.0", "Black");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern_color.1", "Blue");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern_color.2", "Brown");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern_color.3", "Cyan");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern_color.4", "Gray");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern_color.5", "Green");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern_color.6", "Light Blue");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern_color.7", "Light Gray");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern_color.8", "Lime");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern_color.9", "Magenta");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern_color.10", "Orange");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern_color.11", "Pink");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern_color.12", "Purple");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern_color.13", "Red");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern_color.14", "White");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern_color.15", "Yellow");

        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern.stripe", "Stripes");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern.tailshade", "Tail");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern.topshade", "Back");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern.halfshade", "Duality");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern.large_stripe", "Large Stripes");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern.racing_stripe", "Racing Stripe");
        add("unusualprehistory.kimmeridgebrachypteraeschnidium_pattern.large_racing_stripe", "Large Racing Stripe");

        // Effects
        addEffect(UPEffects.SCREEN_SHAKE, "Screen Shake");
        addEffect(UPEffects.BRACHI_PROTECTION, "Brachi's Protection");
        addEffect(UPEffects.PREVENT_CLICK, "Debilitating Toxin");
        addEffect(UPEffects.PACHYS_MIGHT, "Pachy's Might");
        addEffect(UPEffects.HEALTH_REDUCTION, "Draining Venom");
        addEffect(UPEffects.YIXIAN_RAMPAGE, "Yixian Rampage");
        addEffect(UPEffects.RABIES_VACCINE, "M-Lyssavirus Vaccine");
        addEffect(UPEffects.TARBLOOD_PRION, "Tarblood Prion");

        // Block entities
        addBETranslatable("analyzer", "                          Analyzer");
        addBETranslatable("analyzer_jei", "Analyzer");
        addBETranslatable("dna_fridge", "DNA Fridge");
        addBETranslatable("cultivator", "Cultivator");
        addBETranslatable("cultivator_jei", "Cultivator");

        // Built in datapacks
        add("pack.unusualprehistory.natural_prehistoric_generation.title", "Natural Prehistoric Generation");
        add("pack.unusualprehistory.natural_prehistoric_generation.description", "Naturally generating prehistoric plants and things");
    }

    @Override
    public String getName() {
        return  UnusualPrehistory.MODID  + " Languages: en_us";
    }

    public void addBETranslatable(String beName,String name){
        add(UnusualPrehistory.MODID + ".blockentity." + beName, name);
    }

    public void addPrehistoricEgg(Supplier<? extends EntityType<?>> dino, String name) {
        add("item.unusualprehistory." + dino.get().getDescriptionId().replace("entity.unusualprehistory.", "") + "_egg", name);
    }

    private void addDNAItem(Item... items) {
        List.of(items).forEach((item -> this.add(item, "Bottle of " + format(ForgeRegistries.ITEMS.getKey(item)).replace(" Dna Bottle", "") + " DNA")));
    }

    private void addBucketItem(Item... items) {
        List.of(items).forEach((item -> this.add(item, "Bucket of " + format(ForgeRegistries.ITEMS.getKey(item)).replace(" Bucket", ""))));
    }

    public void addSound(Supplier<? extends SoundEvent> key, String name){
        add(UnusualPrehistory.MODID + ".sound.subtitle." + key.get().getLocation().getPath(), name);
    }

    public void addTabName(CreativeModeTab key, String name){
        add(key.getDisplayName().getString(), name);
    }

    public void add(CreativeModeTab key, String name) {
        add(key.getDisplayName().getString(), name);
    }

    public void addPotion(Supplier<? extends Potion> key, String name, String regName) {
        add(key.get(), name, regName);
    }

    public void addEnchantDescription(String description, Enchantment enchantment){
        add(enchantment.getDescriptionId() + ".desc", description);
    }

    public void add(Potion key, String name, String regName) {
        add("item.minecraft.potion.effect." + regName, name);
        add("item.minecraft.splash_potion.effect." + regName, "Splash " + name);
        add("item.minecraft.lingering_potion.effect." + regName, "Lingering " + name);
    }

    protected void addPainting(String name, String author) {
        add("painting." + UnusualPrehistory.MODID + "." + name + ".title",  UPTextUtils.createTranslation(name));
        add("painting." + UnusualPrehistory.MODID + "." + name + ".author",  author);
    }

    protected void addMusicDisc(Supplier<? extends Item> item, String description) {
        String disc = item.get().getDescriptionId();
        add(disc, "Music Disc");
        add(disc + ".desc", description);
    }

    protected void forBlock(Supplier<? extends Block> block) {
        addBlock(block, UPTextUtils.createTranslation(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block.get())).getPath()));
    }

    protected void forItem(Supplier<? extends Item> item) {
        addItem(item, UPTextUtils.createTranslation(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.get())).getPath()));
    }

    protected void forEntity(Supplier<? extends EntityType<?>> entity) {
        addEntityType(entity, UPTextUtils.createTranslation(Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(entity.get())).getPath()));
    }

    private String format(ResourceLocation registryName) {
        return WordUtils.capitalizeFully(registryName.getPath().replace("_", " "));
    }
}
