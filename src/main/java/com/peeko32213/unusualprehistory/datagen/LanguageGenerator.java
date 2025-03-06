package com.peeko32213.unusualprehistory.datagen;

import com.mojang.logging.LogUtils;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.*;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.function.Supplier;

public class LanguageGenerator extends LanguageProvider {
    public LanguageGenerator(PackOutput output) {
        super(output, UnusualPrehistory.MODID, "en_us");
    }
    private static final Logger LOGGER = LogUtils.getLogger();
    @Override
    protected void addTranslations(){

        //TABS
        addTabName(UPTabs.UP_TAB.get(), "Unusual Prehistory");

        //MISC
        addItem(UPItems.ENCYLOPEDIA, "Encyclopedia of Prehistory");
        addItem(UPItems.ADORNED_STAFF, "Adorned Staff");
        addItem(UPItems.ORGANIC_OOZE, "Organic Ooze");
        addItem(UPItems.DINO_POUCH, "Empty Pouch");
        addItem(UPItems.OPALESCENT_PEARL, "Opalescent Pearl");
        addItem(UPItems.OPALESCENT_SHURIKEN, "Opalescent Shuriken");

        addItem(UPItems.ZULOGAE_DISC, "Music Disc");
        addItem(UPItems.ENCASED_DISC, "Music Disc");

        addItem(UPItems.ANIMAL_DNA_FLASKS, "Flask of Prehistoric Animal DNA");
        addItem(UPItems.PLANT_DNA_FLASKS, "Flask of Prehistoric Plant DNA");

        addBlock(UPBlocks.FOSSIL_ORES, "Fossils");

        //TOOLS
        addItem(UPItems.WARPICK, "War Pick");
        addItem(UPItems.PRIMAL_MACUAHUITL, "Primal Macuahuitl");
        addItem(UPItems.BARINA_WHISTLE, "Crocorina");
        addItem(UPItems.HANDMADE_SPEAR, "Handmade Spear");
        addItem(UPItems.HANDMADE_BATTLEAXE, "Handmade Battle Axe");
        addItem(UPItems.HANDMADE_CLUB, "Handmade Club");

        //FOSSILS
        addItem(UPItems.SHELL_SHARD, "Shell Shard");
        addItem(UPItems.PALEO_FOSSIL, "Paleozoic Fossil");
        addItem(UPItems.MEZO_FOSSIL, "Mesozoic Fossil");
        addItem(UPItems.PLANT_FOSSIL, "Plant Fossil");
        addItem(UPItems.AMBER_FOSSIL, "Amber Fossil");
        addItem(UPItems.AMBER_SHARDS, "Amber Chunk");
        addItem(UPItems.FROZEN_FOSSIL, "Frozen Meat");
        addItem(UPItems.TAR_FOSSIL, "Tar Fossil");
        addItem(UPItems.OPAL_CHUNK, "Opal Chunk");
        addItem(UPItems.OPAL_FOSSIL, "Opal Fossil");

        //EMBRYOS
        addItem(UPItems.SMILODON_EMBRYO, "Smilodon Embryo");
        addItem(UPItems.MAMMOTH_EMBRYO, "Mammoth Embryo");
        addItem(UPItems.MEGATH_EMBRYO, "Megatherium Embryo");
        addItem(UPItems.GIGANTO_EMBRYO, "Gigantopithecus Embryo");
        addItem(UPItems.PARACER_EMBRYO, "Paraceratherium Embryo");
        addItem(UPItems.PALAEO_EMBRYO, "Palaeolophis Embryo");
        addItem(UPItems.OTAROCYON_EMBRYO, "Otarocyon Embryo");

        //DINO DROPS
        addItem(UPItems.REX_SCALE, "Tyrannosaurus Scale");
        addItem(UPItems.MAJUNGA_SCUTE, "Majungasaurus Scute");
        addItem(UPItems.RAPTOR_FEATHERS, "Velociraptor Feathers");
        addItem(UPItems.REX_TOOTH, "Tyrannosaurus Tooth");
        addItem(UPItems.TRIKE_HORN, "Triceratops Horn");
        addItem(UPItems.FROG_SALIVA, "Frog Saliva");
        addItem(UPItems.ENCRUSTED_ORGAN, "Encrusted Organ");
        addItem(UPItems.AUSTRO_FEATHER, "Austroraptor Feather");
        addItem(UPItems.ANTARCTO_PLATE, "Antarctopelta Plate");
        addItem(UPItems.SMILO_FUR, "Smilodon Fur");
        addItem(UPItems.PALAEO_SKIN, "Discarded Palaeolophis Skin");
        addItem(UPItems.PSITTACOSAURUS_QUILL, "Psittacosaurus Quill");
        addItem(UPItems.AMBER_IDOL, "Amber Idol");
        addItem(UPItems.JARATE, "Flask of Jarate");
        addItem(UPItems.YIXIAN_RAMPAGE_FLASK, "Flask of Yixian Rampage");
        addItem(UPItems.DORMANT_RAMPAGE_FLASK, "Flask of Dormant Yixian Rampage");
        addItem(UPItems.QUILL_REMEDY, "Rampage Remedy Quill");
        addItem(UPItems.PSITTACCO_ARROW, "Psittacosaurus Arrow");

        //FOODS
        addItem(UPItems.GROG, "Flask of Grog");
        addItem(UPItems.MEAT_ON_A_STICK, "Coty Chop on a Stick");
        addItem(UPItems.RAW_STETHA, "Raw Stethacanthus");
        addItem(UPItems.COOKED_STETHA, "Cooked Stethacanthus");
        addItem(UPItems.RAW_COTY, "Raw Cotylorhynchus");
        addItem(UPItems.COOKED_COTY, "Cooked Cotylorhynchus");
        addItem(UPItems.RAW_SCAU, "Raw Scaumenacia");
        addItem(UPItems.COOKED_SCAU, "Cooked Scaumenacia");
        addItem(UPItems.GOLDEN_SCAU, "Golden Scaumenacia");
        addItem(UPItems.GINKGO_FRUIT, "Ginkgo Fruit");
        addItem(UPItems.RAW_GINKGO_SEEDS, "Raw Ginkgo Seeds");
        addItem(UPItems.COOKED_GINKGO_SEEDS, "Cooked Ginkgo Seeds");
        addItem(UPItems.AMBER_GUMMY, "Amber Gummy");
        addItem(UPItems.RAW_AUSTRO, "Raw Austroraptor");
        addItem(UPItems.COOKED_AUSTRO, "Cooked Austroraptor");
        addItem(UPItems.MEATY_BUFFET, "Meaty Buffet");
        addItem(UPItems.RED_FRUIT, "Exotic Fruit");
        addItem(UPItems.WHITE_FRUIT, "Luxurious Fruit");
        addItem(UPItems.YELLOW_FRUIT, "Redolent Fruit");
        addItem(UPItems.BLUE_FRUIT, "Salubrious Fruit");
        addItem(UPItems.DEFROSTED_FROZEN_FOSSIL, "Defrosted Fossil");
        addItem(UPItems.DRYO_NUTS, "Dryophyllum Nuts");
        addItem(UPItems.RAW_MAMMOTH, "Raw Mammoth");
        addItem(UPItems.COOKED_MAMMOTH, "Cooked Mammoth");
        addItem(UPItems.MAMMOTH_MEATBALL, "Mammoth Meatball");
        addItem(UPItems.LEEDS_CAVIAR, "Leedsichthys Caviar");

        //SCRAPS
        addItem(UPItems.RED_FRUIT_SCRAPS, "Exotic Fruit Scraps");
        addItem(UPItems.WHITE_FRUIT_SCRAPS, "Luxurious Fruit Scraps");
        addItem(UPItems.YELLOW_FRUIT_SCRAPS, "Redolent Fruit Scraps");
        addItem(UPItems.BLUE_FRUIT_SCRAPS, "Salubrious Fruit Scraps");

        //BUCKETS
        addItem(UPItems.BEELZE_BUCKET, "Bucket of Beelzebufo Tadpole");
        addItem(UPItems.AMMON_BUCKET, "Bucket of Ammonite");
        addItem(UPItems.STETHA_BUCKET, "Bucket of Stethacanthus");
        addItem(UPItems.SCAU_BUCKET, "Bucket of Scaumenacia");
        addItem(UPItems.DUNK_BUCKET, "Bucket of Baby Dunkleosteus");
        addItem(UPItems.PALAEO_BUCKET, "Bucket of Palaeolophis Hatchling");

        //EGGS
        addBlock(UPBlocks.STETHA_EGGS, "Stethacanthus Eggs");
        addBlock(UPBlocks.BEELZE_EGGS, "Beelzebufo Spawn");
        addBlock(UPBlocks.AMON_EGGS, "Ammonite Eggs");
        addBlock(UPBlocks.DUNK_EGGS, "Dunkleosteus Eggs");
        addBlock(UPBlocks.SCAU_EGGS, "Scaumenacia Eggs");
        addBlock(UPBlocks.KIMMER_EGGS, "Kimmeridgebrachypteraeschnidium Eggs");
        addBlock(UPBlocks.COTY_EGG, "Cotylorhynchus Egg");
        addBlock(UPBlocks.ENCRUSTED_SACK, "Encrusted Sack");
        addBlock(UPBlocks.ERYON_EGGS, "Eryon Eggs");
        addBlock(UPBlocks.BARINA_EGG, "Barinasuchus Egg");
        addBlock(UPBlocks.TALPANAS_EGG, "Talpanas Egg");
        addBlock(UPBlocks.MEGALA_EGG, "Megalania Egg");
        addBlock(UPBlocks.DIPLOCAULUS_EGGS, "Diplocaulus Eggs");
        addBlock(UPBlocks.HYNERIA_EGGS, "Hyneria Eggs");
        addBlock(UPBlocks.OPHIDION_EGGS, "Ophiodon Eggs");
        addBlock(UPBlocks.TARTUO_EGGS, "Tartuosteus Eggs");

        //SPAWN EGGS
        addItem(UPItems.AMMON_EGG, "Ammonite Spawn Egg");
        addItem(UPItems.STETHA_EGG, "Stethacanthus Spawn Egg");
        addItem(UPItems.DUNK_EGG, "Dunkleosteus Spawn Egg");
        addItem(UPItems.MAJUNGA_EGG, "Majungasaurus Spawn Egg");
        addItem(UPItems.ANURO_EGG, "Anurognathus Spawn Egg");
        addItem(UPItems.BEELZ_EGG, "Beelzebufo Spawn Egg");
        addItem(UPItems.BEELZ_TADPOLE_EGG, "Beelzebufo Tadpole Spawn Egg");
        addItem(UPItems.COTY_EGG, "Cotylorhynchus Spawn Egg");
        addItem(UPItems.SCAU_EGG, "Scaumenacia Spawn Egg");
        addItem(UPItems.BRACHI_EGG, "Brachiosaurus Spawn Egg");
        addItem(UPItems.TYRANNOSAURUS_EGG, "Tyrannosaurus Spawn Egg");
        addItem(UPItems.VELOCIRAPTOR_EGG, "Velociraptor Spawn Egg");
        addItem(UPItems.TRICERATOPS_EGG, "Triceratops Spawn Egg");
        addItem(UPItems.PACHY_EGG, "Pachycephalosaurus Spawn Egg");
        addItem(UPItems.ENCRUSTED_EGG, "Encrusted Spawn Egg");
        addItem(UPItems.ERYON_EGG, "Eryon Spawn Egg");
        addItem(UPItems.AUSTRO_EGG, "Austroraptor Spawn Egg");
        addItem(UPItems.ANTARCO_EGG, "Antarctopelta Spawn Egg");
        addItem(UPItems.ULUG_EGG, "Ulughbegsaurus Spawn Egg");
        addItem(UPItems.KENTRO_EGG, "Kentrosaurus Spawn Egg");
        addItem(UPItems.HWACHA_EGG, "Hwachavenator Spawn Egg");
        addItem(UPItems.TALPANAS_EGG, "Talpanas Spawn Egg");
        addItem(UPItems.GIGANTO_EGG, "Gigantopithecus Spawn Egg");
        addItem(UPItems.BARINA_EGG, "Barinasuchus Spawn Egg");
        addItem(UPItems.MEGATH_EGG, "Megatherium Spawn Egg");
        addItem(UPItems.SMILO_EGG, "Smilodon Spawn Egg");
        addItem(UPItems.PARACER_EGG, "Paraceratherium Spawn Egg");
        addItem(UPItems.MAMMOTH_EGG, "Mammoth Spawn Egg");
        addItem(UPItems.MEGALANIA_EGG, "Megalania Spawn Egg");
        addItem(UPItems.PALAEOPHIS_EGG, "Palaeolophis Spawn Egg");
        addItem(UPItems.PALAEOPHIS_BABY_EGG, "Palaeolophis Hatchling Spawn Egg");
        addItem(UPItems.SLUDGE_EGG, "Sludge Spawn Egg");
        addItem(UPItems.KIMMER_EGG, "Kimmeridgebrachypteraeschnidium Spawn Egg");
        addItem(UPItems.DIPLOCAULUS_EGG, "Diplocaulus Spawn Egg");
        addItem(UPItems.HYNERIA_EGG, "Hyneria Spawn Egg");
        addItem(UPItems.OPHIODON_EGG, "Ophiodon Ozymandias Spawn Egg");
        addItem(UPItems.PROTOSPHYRAENA_EGG, "Protosphyraena Spawn Egg");
        addItem(UPItems.BALAUR_EGG, "Balaur Spawn Egg");
        addItem(UPItems.OTAROCYON_EGG, "Otarocyon Spawn Egg");
        addItem(UPItems.LEEDS_EGG, "Leedsichthys Spawn Egg");
        addItem(UPItems.ARCHELON_EGG, "Archelon Spawn Egg");
        addItem(UPItems.TARTUOSTEUS_EGG, "Tartuosteus Spawn Egg");
        addItem(UPItems.HYNERPETON_EGG, "Hynerpeton Spawn Egg");
        addItem(UPItems.JAWLESS_FISH_EGG, "Jawless Fish Spawn Egg");
        addItem(UPItems.PSITTACO_EGG, "Psittacosaurus Spawn Egg");
        addItem(UPItems.KAPROSUCUHS_EGG, "Kaprosuchus Spawn Egg");
        addItem(UPItems.LONGISQUAMA_EGG, "Longisquama Spawn Egg");
        addItem(UPItems.PTERODAUSTRO_EGG, "Pteradaustro Spawn Egg");
        addItem(UPItems.TANY_EGG, "Tanystropheus Spawn Egg");
        addItem(UPItems.PSILOPTERUS_EGG, "Psilopterus Spawn Egg");
        addItem(UPItems.XIPH_EGG, "Xiphactinus Spawn Egg");
        addItem(UPItems.OVIRAPTOR_EGG, "Oviraptor Spawn Egg");
        addItem(UPItems.ESTEMMENOSUCHUS_EGG, "Estemmenosuchus Spawn Egg");
        addItem(UPItems.PTERYGOTUS_EGG, "Pterygotus Spawn Egg");
        addItem(UPItems.GLOBIDENS_EGG, "Globidens Spawn Egg");
        addItem(UPItems.EDAPHOSAURUS_EGG, "Edaphosaurus Spawn Egg");
        addItem(UPItems.TELECREX_EGG, "Telecrex Spawn Egg");

        //TAR
        addBlock(UPBlocks.TAR, "Tar Block");
        addBlock(UPBlocks.SPLATTERED_TAR, "Splattered Tar");
        addItem(UPItems.TAR_BUCKET, "Tar Bucket");

        //ONE PIECE ARMORS
        addItem(UPItems.MAJUNGA_HELMET, "Majungasaurus Helmet");
        addItem(UPItems.AUSTRO_BOOTS, "Austroraptor Boots");
        addItem(UPItems.TYRANTS_CROWN, "Tyrant's Crown");
        addItem(UPItems.SLOTH_POUCH_ARMOR, "Sloth Pouch");

        //SHEDSCALE ARMOR
        addItem(UPItems.SHEDSCALE_HELMET, "Shedscale Wraps");
        addItem(UPItems.SHEDSCALE_CHESTPLATE, "Shedscale Tunic");
        addItem(UPItems.SHEDSCALE_LEGGINGS, "Shedscale Leggings");
        addItem(UPItems.SHEDSCALE_BOOTS, "Shedscale Boots");

        //SHIELDS
        addItem(UPItems.VELOCI_SHIELD, "Veloci-Shield");
        addItem(UPItems.TRIKE_SHIELD, "Triceratops Shield");

        // Boats
        addItem(UPItems.DRYO_BOAT, "Dryophyllum Boat");
        addItem(UPItems.FOXXI_BOAT, "Foxii Boat");
        addItem(UPItems.GINKGO_BOAT, "Ginkgo Boat");

        addItem(UPItems.DRYO_CHEST_BOAT, "Dryophyllum Boat with Chest");
        addItem(UPItems.FOXXI_CHEST_BOAT, "Foxii Boat with Chest");
        addItem(UPItems.GINKGO_CHEST_BOAT, "Ginkgo Boat with Chest");

        //FLASKS
        addItem(UPItems.FLASK, "Glass Flask");
        addItem(UPItems.CAPTURED_KIMMER_FLASK, "Flask of Kimmeridgebrachypteraeschnidium");
        addItem(UPItems.STETHA_FLASK, "Flask of Stethacanthus DNA");
        addItem(UPItems.DUNK_FLASK, "Flask of Dunkleosteus DNA");
        addItem(UPItems.MAJUNGA_FLASK, "Flask of Majungasaurus DNA");
        addItem(UPItems.AMMONITE_FLASK, "Flask of Ammonite DNA");
        addItem(UPItems.ANURO_FLASK, "Flask of Anurognathus DNA");
        addItem(UPItems.BEELZ_FLASK, "Flask of Beelzebufo DNA");
        addItem(UPItems.COTY_FLASK, "Flask of Cotylorhynchus DNA");
        addItem(UPItems.SCAU_FLASK, "Flask of Scaumenacia DNA");
        addItem(UPItems.BRACHI_FLASK, "Flask of Brachiosaurus DNA");
        addItem(UPItems.TYRANNO_FLASK, "Flask of Tyrannosaurus DNA");
        addItem(UPItems.VELOCI_FLASK, "Flask of Velociraptor DNA");
        addItem(UPItems.TRIKE_FLASK, "Flask of Triceratops DNA");
        addItem(UPItems.PACHY_FLASK, "Flask of Pachycephalosaurus DNA");
        addItem(UPItems.ENCRUSTED_FLASK, "Flask of Encrusted DNA");
        addItem(UPItems.ERYON_FLASK, "Flask of Eryon DNA");
        addItem(UPItems.HORSETAIL_FLASK, "Flask of Horsetail DNA");
        addItem(UPItems.LEEFRUCTUS_FLASK, "Flask of Leefructus DNA");
        addItem(UPItems.GINKGO_FLASK, "Flask of Ginkgo DNA");
        addItem(UPItems.BENNET_FLASK, "Flask of Bennettitales DNA");
        addItem(UPItems.SARR_FLASK, "Flask of Sarracenia DNA");
        addItem(UPItems.ARCHAO_FLASK, "Flask of Archaeosigillaria DNA");
        addItem(UPItems.ANOSTYLOSTRAMA_FLASK, "Flask of Anostylostroma DNA");
        addItem(UPItems.CLATHRODICTYON_FLASK, "Flask of Clathrodictyon DNA");
        addItem(UPItems.ARCHAEFRUCTUS_FLASK, "Flask of Archaefructus DNA");
        addItem(UPItems.NELUMBITES_FLASK, "Flask of Nelumbites DNA");
        addItem(UPItems.QUEREUXIA_FLASK, "Flask of Quereuxia DNA");
        addItem(UPItems.ANTARCTO_FLASK, "Flask of Antarctopelta DNA");
        addItem(UPItems.AUSTRO_FLASK, "Flask of Austroraptor DNA");
        addItem(UPItems.HWACHA_FLASK, "Flask of Hwachavenator DNA");
        addItem(UPItems.KENTRO_FLASK, "Flask of Kentrosaurus DNA");
        addItem(UPItems.ULUGH_FLASK, "Flask of Ulughbegsaurus DNA");
        addItem(UPItems.GIGANTO_FLASK, "Flask of Gigantopithecus DNA");
        addItem(UPItems.SMILODON_FLASK, "Flask of Smilodon DNA");
        addItem(UPItems.MEGATHERIUM_FLASK, "Flask of Megatherium DNA");
        addItem(UPItems.PARACER_FLASK, "Flask of Paraceratherium DNA");
        addItem(UPItems.MAMMOTH_FLASK, "Flask of Mammoth DNA");
        addItem(UPItems.BARINA_FLASK, "Flask of Barinasuchus DNA");
        addItem(UPItems.PALAEO_FLASK, "Flask of Palaeolophis DNA");
        addItem(UPItems.MEGALANIA_FLASK, "Flask of Megalania DNA");
        addItem(UPItems.TALPANAS_FLASK, "Flask of Talpanas DNA");
        addItem(UPItems.ZULOAGAE_FLASK, "Flask of Zuloagae DNA");
        addItem(UPItems.RAIGUENRAYUN_FLASK, "Flask of Raiguenrayun DNA");
        addItem(UPItems.FOXXI_FLASK, "Flask of Foxii DNA");
        addItem(UPItems.DRYO_FLASK, "Flask of Dryophyllum DNA");
        addItem(UPItems.KIMMER_FLASK, "Flask of Kimmeridgebrachypteraeschnidium DNA");
        addItem(UPItems.DIPLO_FLASK, "Flask of Diplocaulus DNA");
        addItem(UPItems.HYNERIA_FLASK, "Flask of Hyneria DNA");
        addItem(UPItems.OPHIO_FLASK, "Flask of Ophiodon Ozymandias DNA");
        addItem(UPItems.ESTEMMENO_FLASK, "Flask of Estemmenosuchus DNA");
        addItem(UPItems.XIPHACT_FLASK, "Flask of Xiphactinus DNA");
        addItem(UPItems.PTERY_FLASK, "Flask of Pterygotus DNA");
        addItem(UPItems.EDAPHO_FLASK, "Flask of Edaphosaurus DNA");
        addItem(UPItems.OVIRAPTOR_FLASK, "Flask of Oviraptor DNA");
        addItem(UPItems.GLOBIDENS_FLASK, "Flask of Globidens DNA");
        addItem(UPItems.OTAROCYON_FLASK, "Flask of Otarocyon DNA");
        addItem(UPItems.LONGI_FLASK, "Flask of Longisquama DNA");
        addItem(UPItems.JAWLESS_FISH_FLASK, "Flask of Furcacauda DNA");
        addItem(UPItems.TARTUO_FLASK, "Flask of Tartuosteus DNA");
        addItem(UPItems.TANY_FLASK, "Flask of Tanystropheus DNA");
        addItem(UPItems.PSITTACO_FLASK, "Flask of Psittacosaurus DNA");
        addItem(UPItems.KAPRO_FLASK, "Flask of Kaprosuchus DNA");
        addItem(UPItems.PSILO_FLASK, "Flask of Psilopterus DNA");
        addItem(UPItems.HYNERP_FLASK, "Flask of Hynerpeton DNA");
        addItem(UPItems.BALAUR_FLASK, "Flask of Balaur DNA");
        addItem(UPItems.LEEDS_FLASK, "Flask of Leedsichthys DNA");
        addItem(UPItems.PTERODAUSTRO_FLASK, "Flask of Pterodaustro DNA");
        addItem(UPItems.ARCHELON_FLASK, "Flask of Archelon DNA");
        addItem(UPItems.PROTOSPHYRAENA_FLASK, "Flask of Protosphyraena DNA");

        //CORALS
        addItem(UPItems.CLATHRODICTYON_FAN, "Clathrodictyon Coral Fan");
        addItem(UPItems.DEAD_CLATHRODICTYON_FAN, "Dead Clathrodictyon Coral Fan");
        addBlock(UPBlocks.DEAD_CLATHRODICTYON, "Dead Clathrodictyon Coral");
        addBlock(UPBlocks.DEAD_CLATHRODICTYON_BLOCK, "Dead Clathrodictyon Block");
        addBlock(UPBlocks.CLATHRODICTYON, "Clathrodictyon Coral");
        addBlock(UPBlocks.CLATHRODICTYON_BLOCK, "Clathrodictyon Coral Block");
        addBlock(UPBlocks.ANOSTYLOSTROMA_BLOCK, "Anostylostroma Coral Block");

        //BLOCKS
        addBlock(UPBlocks.STONE_FOSSIL, "Stone Fossil");
        addBlock(UPBlocks.DEEPSLATE_FOSSIL, "Deepslate Fossil");
        addBlock(UPBlocks.PLANT_FOSSIL, "Stone Plant Fossil");
        addBlock(UPBlocks.DEEPSLATE_PLANT_FOSSIL, "Deepslate Plant Fossil");
        addBlock(UPBlocks.STONE_TAR_FOSSIL, "Stone Tar Fossil");
        addBlock(UPBlocks.DEEPSLATE_TAR_FOSSIL, "Deepslate Tar Fossil");
        addBlock(UPBlocks.STONE_AMBER_FOSSIL, "Amber Ore");
        addBlock(UPBlocks.DEEPSLATE_AMBER_FOSSIL, "Deepslate Amber Ore");
        addBlock(UPBlocks.STONE_OPAL_FOSSIL, "Opal Ore");
        addBlock(UPBlocks.DEEPSLATE_OPAL_FOSSIL, "Deepslate Opal Ore");
        addBlock(UPBlocks.PERMAFROST_FOSSIL, "Permafrost Fossil");
        addBlock(UPBlocks.PERMAFROST, "Permafrost");
        addBlock(UPBlocks.ANALYZER, "Analyzer");
        addBlock(UPBlocks.CULTIVATOR, "Cultivator");
        addBlock(UPBlocks.DNA_FRIDGE, "DNA Fridge");
        addBlock(UPBlocks.AMBER_GLASS, "Amber Glass");
        addBlock(UPBlocks.AMBER_GLASS_PANE, "Amber Glass Pane");
        addBlock(UPBlocks.REX_HEAD, "Tyrannosaurus Head Mount");
        addBlock(UPBlocks.AMMONITE_SHELL, "Ammonite Shell");
        addBlock(UPBlocks.REX_BOOMBOX, "Tyrannosaurus Boombox");
        addBlock(UPBlocks.AMBER_BLOCK, "Amber Block");
        addBlock(UPBlocks.OPAL_BLOCK, "Opal Block");
        addBlock(UPBlocks.INCUBATOR, "Incubator");
        addBlock(UPBlocks.ASPHALT, "Asphalt");
        addBlock(UPBlocks.GOLD_ENGRAVED_ASPHALT, "Gold Engraved Asphalt");
        addBlock(UPBlocks.QUARTZ_ENGRAVED_ASPHALT, "Quartz Engraved Asphalt");

        //PLANTS
        addBlock(UPBlocks.HORSETAIL, "Horsetail");
        addBlock(UPBlocks.TALL_HORSETAIL, "Tall Horsetail");
        addBlock(UPBlocks.LEEFRUCTUS, "Leefructus");
        addBlock(UPBlocks.BENNETTITALES, "Bennettitales");
        addBlock(UPBlocks.ARCHAEOSIGILARIA, "Archaeosigillaria");
        addBlock(UPBlocks.SARACENIA, "Sarracenia");
        addBlock(UPBlocks.TALL_SARACENIA, "Tall Sarracenia");
        addBlock(UPBlocks.GINKGO_SAPLING, "Ginkgo Sapling");
        addBlock(UPBlocks.ARCHAEFRUCTUS, "Archaefructus");
        addBlock(UPBlocks.NELUMBITES, "Nelumbites");
        addBlock(UPBlocks.QUEREUXIA, "Quereuxia");
        addBlock(UPBlocks.QUEREUXIA_TOP, "Quereuxia Clovers");
        addBlock(UPBlocks.PETRIFIED_BUSH, "Petrified Bush");
        addBlock(UPBlocks.ZULOAGAE, "Zuloagae");
        addBlock(UPBlocks.ZULOAGAE_SAPLING, "Zuloagae Sapling");
        addBlock(UPBlocks.RAIGUENRAYUN, "Raiguenrayun");

        //GINKGO
        addBlock(UPBlocks.GINKGO_PLANKS, "Ginkgo Planks");
        addBlock(UPBlocks.GINKGO_WOOD, "Ginkgo Wood");
        addBlock(UPBlocks.STRIPPED_GINKGO_WOOD, "Stripped Ginkgo Wood");
        addBlock(UPBlocks.GINKGO_LEAVES, "Ginkgo Leaves");
        addBlock(UPBlocks.GINKGO_LOG, "Ginkgo Log");
        addBlock(UPBlocks.STRIPPED_GINKGO_LOG, "Stripped Ginkgo Log");
        addBlock(UPBlocks.GINKGO_STAIRS, "Ginkgo Stairs");
        addBlock(UPBlocks.GINKGO_SLAB, "Ginkgo Slab");
        addBlock(UPBlocks.GINKGO_FENCE, "Ginkgo Fence");
        addBlock(UPBlocks.GINKGO_FENCE_GATE, "Ginkgo Fence Gate");
        addBlock(UPBlocks.GINKGO_DOOR, "Ginkgo Door");
        addBlock(UPBlocks.GINKGO_TRAPDOOR, "Ginkgo Trapdoor");
        addBlock(UPBlocks.GINKGO_BUTTON, "Ginkgo Button");
        addBlock(UPBlocks.GINKGO_PRESSURE_PLATE, "Ginkgo Pressure Plate");
        addItem(UPItems.GINKGO_SIGN, "Ginkgo Sign");
        addItem(UPItems.GINKGO_HANGING_SIGN, "Ginkgo Hanging Sign");
        addBlock(UPBlocks.AMBER_BUTTON, "Amber Button");

        //FOXXI
        addBlock(UPBlocks.FOXXI_PLANKS, "Foxii Planks");
        addBlock(UPBlocks.FOXXI_WOOD, "Foxii Wood");
        addBlock(UPBlocks.STRIPPED_FOXXI_WOOD, "Stripped Foxii Wood");
        addBlock(UPBlocks.FOXXI_LEAVES, "Foxii Leaves");
        addBlock(UPBlocks.FOXXI_LOG, "Foxii Log");
        addBlock(UPBlocks.STRIPPED_FOXXI_LOG, "Stripped Foxii Log");
        addBlock(UPBlocks.FOXXI_STAIRS, "Foxii Stairs");
        addBlock(UPBlocks.FOXXI_SLAB, "Foxii Slab");
        addBlock(UPBlocks.FOXXI_FENCE, "Foxii Fence");
        addBlock(UPBlocks.FOXXI_FENCE_GATE, "Foxii Fence Gate");
        addBlock(UPBlocks.FOXXI_BUTTON, "Foxii Button");
        addBlock(UPBlocks.FOXXI_PRESSURE_PLATE, "Foxii Pressure Plate");
        addBlock(UPBlocks.FOXXI_DOOR, "Foxii Door");
        addBlock(UPBlocks.FOXXI_TRAPDOOR, "Foxii Trapdoor");
        addBlock(UPBlocks.FOXII_SAPLING, "Foxii Sapling");
        addItem(UPItems.FOXII_SIGN, "Foxii Sign");
        addItem(UPItems.FOXII_HANGING_SIGN, "Foxii Hanging Sign");

        //DRYO
        addBlock(UPBlocks.DRYO_PLANKS, "Dryophyllum Planks");
        addBlock(UPBlocks.DRYO_WOOD, "Dryophyllum Wood");
        addBlock(UPBlocks.STRIPPED_DRYO_WOOD, "Stripped Dryophyllum Wood");
        addBlock(UPBlocks.DRYO_LEAVES, "Dryophyllum Leaves");
        addBlock(UPBlocks.DRYO_LOG, "Dryophyllum Log");
        addBlock(UPBlocks.STRIPPED_DRYO_LOG, "Stripped Dryophyllum Log");
        addBlock(UPBlocks.DRYO_STAIRS, "Dryophyllum Stairs");
        addBlock(UPBlocks.DRYO_SLAB, "Dryophyllum Slab");
        addBlock(UPBlocks.DRYO_FENCE, "Dryophyllum Fence");
        addBlock(UPBlocks.DRYO_FENCE_GATE, "Dryophyllum Fence Gate");
        addBlock(UPBlocks.DRYO_BUTTON, "Dryophyllum Button");
        addBlock(UPBlocks.DRYO_PRESSURE_PLATE, "Dryophyllum Pressure Plate");
        addBlock(UPBlocks.DRYO_DOOR, "Dryophyllum Door");
        addBlock(UPBlocks.DRYO_TRAPDOOR, "Dryophyllum Trapdoor");
        addBlock(UPBlocks.DRYO_SAPLING, "Dryophyllum Sapling");
        addItem(UPItems.DRYO_SIGN, "Dryophyllum Sign");
        addItem(UPItems.DRYO_HANGING_SIGN, "Dryophyllum Hanging Sign");

        //PETRIFIED
        addBlock(UPBlocks.PETRIFIED_WOOD_PLANKS, "Petrified Planks");
        addBlock(UPBlocks.PETRIFIED_WOOD, "Petrified Wood");
        addBlock(UPBlocks.STRIPPED_PETRIFIED_WOOD, "Stripped Petrified Wood");
        addBlock(UPBlocks.PETRIFIED_WOOD_LOG, "Petrified Log");
        addBlock(UPBlocks.STRIPPED_PETRIFIED_WOOD_LOG, "Stripped Petrified Log");
        addBlock(UPBlocks.PETRIFIED_WOOD_STAIRS, "Petrified Stairs");
        addBlock(UPBlocks.PETRIFIED_WOOD_SLAB, "Petrified Slab");
        addBlock(UPBlocks.PETRIFIED_WOOD_FENCE, "Petrified Fence");
        addBlock(UPBlocks.PETRIFIED_WOOD_FENCE_GATE, "Petrified Fence Gate");
        addBlock(UPBlocks.PETRIFIED_WOOD_DOOR, "Petrified Door");
        addBlock(UPBlocks.PETRIFIED_WOOD_TRAPDOOR, "Petrified Trapdoor");
        addBlock(UPBlocks.PETRIFIED_WOOD_BUTTON, "Petrified Button");
        addBlock(UPBlocks.PETRIFIED_WOOD_PRESSURE_PLATE, "Petrified Pressure Plate");
        addBlock(UPBlocks.POLISHED_PETRIFIED_WOOD, "Polished Petrified Wood");
        addBlock(UPBlocks.POLISHED_PETRIFIED_WOOD_STAIRS, "Polished Petrified Wood Stairs");
        addBlock(UPBlocks.POLISHED_PETRIFIED_WOOD_SLAB, "Polished Petrified Wood Slab");
        addItem(UPItems.PETRIFIED_WOOD_SIGN, "Petrified Sign");
        addItem(UPItems.PETRIFIED_WOOD_HANGING_SIGN, "Petrified Hanging Sign");

        //FOXXI
        addBlock(UPBlocks.ZULOAGAE_BLOCK, "Zuloagae Block");
        addBlock(UPBlocks.ZULOAGAE_PLANKS, "Zuloagae Planks");
        addBlock(UPBlocks.STRIPPED_ZULOAGAE_BLOCK, "Stripped Zuloagae Block");
        addBlock(UPBlocks.ZULOAGAE_STAIRS, "Zuloagae Stairs");
        addBlock(UPBlocks.ZULOAGAE_SLAB, "Zuloagae Slab");
        addBlock(UPBlocks.ZULOAGAE_FENCE, "Zuloagae Fence");
        addBlock(UPBlocks.ZULOAGAE_FENCE_GATE, "Zuloagae Fence Gate");
        addBlock(UPBlocks.ZULOAGAE_BUTTON, "Zuloagae Button");
        addBlock(UPBlocks.ZULOAGAE_PRESSURE_PLATE, "Zuloagae Pressure Plate");
        addBlock(UPBlocks.ZULOAGAE_DOOR, "Zuloagae Door");
        addBlock(UPBlocks.ZULOAGAE_TRAPDOOR, "Zuloagae Trapdoor");
//        addBlock(UPBlocks.ZULOAGAE_SIGN, "Zuloagae Sign");
//        addBlock(UPBlocks.ZULOAGAE_WALL_SIGN, "Zuloagae Wall Sign");
//        addBlock(UPBlocks.ZULOAGAE_HANGING_SIGN, "Zuloagae Hanging Sign");
//        addBlock(UPBlocks.ZULOAGAE_WALL_HANGING_SIGN, "Zuloagae Wall Hanging Sign");
        addItem(UPItems.ZULOAGAE_SIGN, "Zuloagae Sign");
        addItem(UPItems.ZULOAGAE_HANGING_SIGN, "Zuloagae Hanging Sign");

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

        // Paleo mobs
        addEntityType(UPEntities.AMMON, "Ammonite");
        addEntityType(UPEntities.COTY, "Cotylorhynchus");
        addEntityType(UPEntities.DIPLOCAULUS, "Diplocaulus");
        addEntityType(UPEntities.DUNK, "Dunkleosteus");
        addEntityType(UPEntities.BABY_DUNK, "Baby Dunkleosteus");
        addEntityType(UPEntities.EDAPHOSAURUS, "Edaphosaurus");
        addEntityType(UPEntities.ESTEMMENOSUCHUS, "Estemmenosuchus");
        addEntityType(UPEntities.HYNERIA, "Hyneria");
        addEntityType(UPEntities.HYNERPETON, "Hynerpeton");
        addEntityType(UPEntities.JAWLESS_FISH, "Jawless Fish");
        addEntityType(UPEntities.PTERYGOTUS, "Pterygotus");
        addEntityType(UPEntities.SCAU, "Scaumenacia");
        addEntityType(UPEntities.STETHACANTHUS, "Stethacanthus");
        addEntityType(UPEntities.TARTUOSTEUS, "Tartuosteus");

        // Meso mobs
        addEntityType(UPEntities.ANTARCO, "Antarctopelta");
        addEntityType(UPEntities.ANURO, "Anurognathus");
        addEntityType(UPEntities.ARCHELON, "Archelon");
        addEntityType(UPEntities.AUSTRO, "Austroraptor");
        addEntityType(UPEntities.BALAUR, "Balaur");
        addEntityType(UPEntities.BEELZ, "Beelzebufo");
        addEntityType(UPEntities.BEELZE_TADPOLE, "Beelzebufo Tadpole");
        addEntityType(UPEntities.BRACHI, "Brachiosaurus");
        addEntityType(UPEntities.ERYON, "Eryon");
        addEntityType(UPEntities.HWACHA, "Hwachavenator");
        addEntityType(UPEntities.KAPROSUCHUS, "Kaprosuchus");
        addEntityType(UPEntities.KENTRO, "Kentrosaurus");
        addEntityType(UPEntities.KIMMER, "Kimmeridgebrachypteraeschnidium");
        addEntityType(UPEntities.LEEDSICHTHYS, "Leedsichthys");
        addEntityType(UPEntities.LONGISQUAMA, "Longisquama");
        addEntityType(UPEntities.MAJUNGA, "Majungasaurus");
        addEntityType(UPEntities.OVIRAPTOR, "Oviraptor");
        addEntityType(UPEntities.PACHY, "Pachycephalosaurus");
        addEntityType(UPEntities.PROTOSPHYRAENA, "Protosphyraena");
        addEntityType(UPEntities.PSITTACO, "Psittacosaurus");
        addEntityType(UPEntities.PTERODAUSTRO, "Pterodaustro");
        addEntityType(UPEntities.TANY, "Tanystropheus");
        addEntityType(UPEntities.TRICERATOPS, "Triceratops");
        addEntityType(UPEntities.TYRANNOSAURUS, "Tyrannosaurus");
        addEntityType(UPEntities.ULUG, "Ulughbegsaurus");
        addEntityType(UPEntities.VELOCIRAPTOR, "Velociraptor");
        addEntityType(UPEntities.XIPH, "Xiphactinus");

        // Ceno mobs
        addEntityType(UPEntities.BARINASUCHUS, "Barinasuchus");
        addEntityType(UPEntities.GIGANTOPITHICUS, "Gigantopithecus");
        addEntityType(UPEntities.MAMMOTH, "Mammoth");
        addEntityType(UPEntities.MEGALANIA, "Megalania");
        addEntityType(UPEntities.MEGATHERIUM, "Megatherium");
        addEntityType(UPEntities.OPHIODON, "Ophiodon Ozymandias");
        addEntityType(UPEntities.OTAROCYON, "Otarocyon");
        addEntityType(UPEntities.PALAEOPHIS, "Palaeolophis");
        addEntityType(UPEntities.PALAEOPHIS_PART, "Palaeophis Body");
        addEntityType(UPEntities.BABY_PALAEO, "Palaeolophis Hatchling");
        addEntityType(UPEntities.PARACERATHERIUM, "Paraceratherium");
        addEntityType(UPEntities.PSILOPTERUS, "Psilopterus");
        addEntityType(UPEntities.SMILODON, "Smilodon");
        addEntityType(UPEntities.TALPANAS, "Talpanas");
        addEntityType(UPEntities.TELECREX, "Telecrex");

        // Misc mobs
        addEntityType(UPEntities.ENCRUSTED, "Encrusted");
        addEntityType(UPEntities.SLUDGE, "Sludge");

        // Non-living mobs
        addEntityType(UPEntities.AMBER_SHOT, "Amber Shot");
        addEntityType(UPEntities.HWACHA_SPIKE, "Hwachavenator Pin");
        addEntityType(UPEntities.ICEBERG_MAMMOTH, "Frozen Mammoth");
        addEntityType(UPEntities.ICEBERG_SMILODON, "Frozen Smilodon");
        addEntityType(UPEntities.OPALESCENT_PEARL, "Opalescent Pearl");
        addEntityType(UPEntities.OPALESCENT_SHURIKEN, "Opalescent Shuriken");
        addEntityType(UPEntities.JARATE, "Jarate");

        // Boats
        addEntityType(UPEntities.BOAT, "Boat");
        addEntityType(UPEntities.CHEST_BOAT, "Boat with Chest");

        // Eggs
        addPrehistoricEgg(UPEntities.ANTARCO, "Antarctopelta Egg");
        addPrehistoricEgg(UPEntities.ANURO, "Anurognathus Egg");
        addPrehistoricEgg(UPEntities.ARCHELON, "Archelon Egg");
        addPrehistoricEgg(UPEntities.AUSTRO, "Austroraptor Egg");
        addPrehistoricEgg(UPEntities.BALAUR, "Balaur Egg");

        //SOUNDS
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

        addSound(UPSounds.EDAPHO_IDLE, "Edaphosaurus groans");
        addSound(UPSounds.EDAPHO_HURT, "Edaphosaurus hurts");
        addSound(UPSounds.EDAPHO_DEATH, "Edaphosaurus dies");

        addSound(UPSounds.ESTEMME_IDLE, "Estemmenosuchus groans");
        addSound(UPSounds.ESTEMME_HURT, "Estemmenosuchus hurts");
        addSound(UPSounds.ESTEMME_DEATH, "Estemmenosuchus dies");

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

        addSound(UPSounds.RAPTOR_IDLE, "Velociraptor coos");
        addSound(UPSounds.RAPTOR_HURT, "Velociraptor hurts");
        addSound(UPSounds.RAPTOR_ATTACK, "Velociraptor veloci-slashes");
        addSound(UPSounds.RAPTOR_SEARCH, "Velociraptor coos curiously");
        addSound(UPSounds.RAPTOR_DEATH, "Velociraptor dies");

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

        addSound(UPSounds.CROCARINA, "Crocarina plays");

        addSound(UPSounds.BARINA_DEATH, "Barinasuchus dies");
        addSound(UPSounds.BARINA_HURT, "Barinasuchus hurts");
        addSound(UPSounds.BARINA_IDLE, "Barinasuchus growls");
        addSound(UPSounds.BARINA_BITE, "Barinasuchus chomps");

        addSound(UPSounds.ZULOGAE_DISC, "Music Disc");
        addSound(UPSounds.ENCASED_DISC, "Music Disc");

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

        addSound(UPSounds.JARATE_EXPLODE, "Jarate impacts");
        addSound(UPSounds.JARATE_SNIPER, "Jarate!");

        add("entity.unusualprehistory.all.command_0", "%s is wandering");
        add("entity.unusualprehistory.all.command_1", "%s is following");
        add("entity.unusualprehistory.all.command_2", "%s is staying");
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
        add("item.unusualprehistory.zulogae_disc.desc", "Shroomy - Zulogae");
        add("item.unusualprehistory.encased_disc.desc", "TheValiantSquidward - Encased");

        add("death.attack.sludge_0.player", "%s was slapped to death by %s");

        add("unusualprehistory.megalania_damage", " %s Venomous Coating");
        add("unusualprehistory.battle_axe", "An axe made for battle. Cutting wood effectively goes poorly..");
        add("unusualprehistory.shedscale_bonus", "Inverts some negative effects while wearing full set");
        add("unusualprehistory.fruit_loot_box.salubrious", "§rSalubrious Fruit Block");
        add("unusualprehistory.fruit_loot_box.luxurious", "§rLuxurious Fruit Block");
        add("unusualprehistory.fruit_loot_box.redolent", "§rRedolent Fruit Block");
        add("unusualprehistory.fruit_loot_box.exotic", "§rExotic Fruit Block");
        add("unusualprehistory.fruit_loot_box.default", "§rFruit Block");
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

        add("death.attack.tar_0", "%s suffocated in tar");
        add("death.attack.tar_1", "%s was preserved in tar");
        add("death.attack.tar_0.entity", "%s suffocated in tar");
        add("death.attack.tar_1.entity", "%s was preserved in tar");

        //EFFECTS
        addEffect(UPEffects.SCREEN_SHAKE, "Screen Shake");
        addEffect(UPEffects.BRACHI_PROTECTION, "Brachi's Protection");
        addEffect(UPEffects.PREVENT_CLICK, "Debilitating Toxin");
        addEffect(UPEffects.PACHYS_MIGHT, "Pachy's Might");
        addEffect(UPEffects.HEALTH_REDUCTION, "Draining Venom");
        addEffect(UPEffects.PISSED_UPON, "Soaked in Jarate");
        addEffect(UPEffects.YIXIAN_RAMPAGE, "Yixian Rampage");
        addEffect(UPEffects.RABIES_VACCINE, "M-Lyssavirus Vaccine");

        //BLOCK ENTITY TRANSLATABLE
        addBETranslatable("analyzer", "                            Analyzer");
        addBETranslatable("analyzer_jei", "Analyzer");
        addBETranslatable("dna_fridge", "DNA Fridge");
        addBETranslatable("cultivator", "Cultivator");
        addBETranslatable("cultivator_jei", "Cultivator");

        // Paintings
        add(UPPaintings.PERISCOPE, "Periscope", "ChipsTheCat");

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

    public void addDinoSpawnEgg(Supplier<? extends EntityType<?>> dino, String name) {
        add("item.unusualprehistory." + dino.get().getDescriptionId().replace("entity.unusualprehistory.", "") + "_spawn_egg", name + " Spawn Egg");
    }

    public void addSound(Supplier<? extends SoundEvent> key, String name){
        add(UnusualPrehistory.MODID + ".sound.subtitle." + key.get().getLocation().getPath(), name);
    }

    private void add(RegistryObject<PaintingVariant> variant, String title, String author) {
        ResourceLocation name = variant.getId();
        String key = "painting." + name.getNamespace() + "." + name.getPath() + ".";
        this.add(key + "title", title);
        this.add(key + "author", author);
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
}

//        addItem(UPItems.ARTHROPLEURA_FLASK, "Arthropleura DNA Flask");
//        addItem(UPItems.SCUTO_FLASK, "Scutosaurus DNA Flask");
//        addItem(UPItems.ENCHODUS_FLASK, "Enchodus DNA Flask");
//        addItem(UPItems.IGUANODON_FLASK, "Iguanodon DNA Flask");


//        addBlock(UPBlocks.ELECTRIC_PILLAR, "Electric Pillar");
