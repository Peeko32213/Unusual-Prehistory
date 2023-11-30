package com.peeko32213.unusualprehistory.datagen;

import com.mojang.logging.LogUtils;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.*;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.data.LanguageProvider;
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
        addTabName(UPTabs.UP_EGG_TAB.get(), "Unusual Spawn Eggs");

        //MISC
        addItem(UPItems.ENCYLOPEDIA, "Encyclopedia of Prehistory");
        addItem(UPItems.ADORNED_STAFF, "Adorned Staff");
        addItem(UPItems.ORGANIC_OOZE, "Organic Ooze");
        addItem(UPItems.DINO_POUCH, "Dino Pouch");
        addItem(UPItems.OPALESCENT_PEARL, "Opalescent Pearl");
        addItem(UPItems.OPALESCENT_SHURIKEN, "Opalescent Shuriken");
        addItem(UPItems.ZULOGAE_DISC, "Music Disc ");

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
        addItem(UPItems.TAR_FOSSIL, "Tar Fossil");
        addItem(UPItems.FROZEN_FOSSIL, "Frozen Fossil");
        addItem(UPItems.OPAL_CHUNK, "Opal Chunk");
        addItem(UPItems.OPAL_FOSSIL, "Opal Fossil");


        //EMBRYOS
        addItem(UPItems.SMILODON_EMBRYO, "Smilodon Embryo");
        addItem(UPItems.MAMMOTH_EMBRYO, "Mammoth Embryo");
        addItem(UPItems.MEGATH_EMBRYO, "Megatherium Embryo");
        addItem(UPItems.GIGANTO_EMBRYO, "Gigantopithecus Embryo");
        addItem(UPItems.PARACER_EMBRYO, "Paraceratherium Embryo");
        addItem(UPItems.PALAEO_EMBRYO, "Palaeolophis Embryo");

        //DINO DROPS
        addItem(UPItems.REX_SCALE, "Rex Scale");
        addItem(UPItems.MAJUNGA_SCUTE, "Majungasaurus Scute");
        addItem(UPItems.RAPTOR_FEATHERS, "Velociraptor Feathers");
        addItem(UPItems.REX_TOOTH, "Rex Tooth");
        addItem(UPItems.TRIKE_HORN, "Triceratops Horn");
        addItem(UPItems.FROG_SALIVA, "Frog Saliva");
        addItem(UPItems.ENCRUSTED_ORGAN, "Encrusted Organ");
        addItem(UPItems.AUSTRO_FEATHER, "Austroraptor Feather");
        addItem(UPItems.ANTARCTO_PLATE, "Antarctopelta Plate");
        addItem(UPItems.SMILO_FUR, "Smilodon Fur");
        addItem(UPItems.PALAEO_SKIN, "Discarded Palaeolophis Skin");
        addItem(UPItems.INSULATOR, "Insulator");
        addItem(UPItems.PSITTACO_QUIL, "Psittacosaurus Quil");

        //FOODS
        addItem(UPItems.GROG, "Flask of Grog");
        addItem(UPItems.MEAT_ON_A_STICK, "Coty Chop On A Stick");
        addItem(UPItems.RAW_STETHA, "Raw Stethacanthus");
        addItem(UPItems.COOKED_STETHA, "Cooked Stethacanthus");
        addItem(UPItems.RAW_COTY, "Raw Cotylorhynchus");
        addItem(UPItems.COOKED_COTY, "Cooked Cotylorhynchus");
        addItem(UPItems.RAW_SCAU, "Raw Scaumenacia");
        addItem(UPItems.COOKED_SCAU, "Cooked Scaumenacia");
        addItem(UPItems.GOLDEN_SCAU, "Golden Scaumenacia");
        addItem(UPItems.GINKGO_FRUIT, "Prehistoric Ginkgo Fruit");
        addItem(UPItems.RAW_GINKGO_SEEDS, "Raw Prehistoric Ginkgo Seeds");
        addItem(UPItems.COOKED_GINKGO_SEEDS, "Cooked Prehistoric Ginkgo Seeds");
        addItem(UPItems.AMBER_GUMMY, "Amber Gummy");
        addItem(UPItems.RAW_AUSTRO, "Raw Austroraptor");
        addItem(UPItems.COOKED_AUSTRO, "Cooked Austroraptor");
        addItem(UPItems.MEATY_BUFFET, "Meaty Buffet");
        addItem(UPItems.RED_FRUIT, "Exotic Fruit");
        addItem(UPItems.WHITE_FRUIT, "Luxurious Fruit");
        addItem(UPItems.YELLOW_FRUIT, "Redolent Fruit");
        addItem(UPItems.BLUE_FRUIT, "Salubrious Fruit");
        addItem(UPItems.DEFROSTED_FROZEN_FOSSIL, "Defrosted Frozen Fossil");
        addItem(UPItems.DRYO_NUTS, "Dryophyllum Nuts");
        addItem(UPItems.RAW_MAMMOTH, "Raw Mammoth Meat");
        addItem(UPItems.COOKED_MAMMOTH, "Cooked Mammoth");
        addItem(UPItems.MAMMOTH_MEATBALL, "Mammoth Meatball");
        addItem(UPItems.RAW_FURCACAUDA, "Raw Furcacauda");
        addItem(UPItems.COOKED_FURCACAUDA, "Cooked Furcacauda");
        addItem(UPItems.RAW_TARTU, "Raw Tartuosteus");
        addItem(UPItems.COOKED_TARTU, "Cooked Tartuosteus");
        addItem(UPItems.RAW_OPHIODON, "Raw Ophiodon");
        addItem(UPItems.COOKED_OPHIODON, "Cooked Ophiodon");

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
        addItem(UPItems.PALAEO_BUCKET, "Bucket of Baby Palaeolophis");
        addItem(UPItems.FURCA_BUCKET, "Bucket of Furcacauda");


        //EGGS
        addBlock(UPBlocks.STETHA_EGGS, "Stethacanthus Eggs");
        addBlock(UPBlocks.BEELZE_EGGS, "Beelzebufo Eggs");
        addBlock(UPBlocks.AMON_EGGS, "Ammonite Eggs");
        addBlock(UPBlocks.DUNK_EGGS, "Dunkleosteus Eggs");
        addBlock(UPBlocks.SCAU_EGGS, "Scaumenacia Eggs");
        addBlock(UPBlocks.ANURO_EGG, "Anurognathus Egg");
        addBlock(UPBlocks.MAJUNGA_EGG, "Majungasaurus Egg");
        addBlock(UPBlocks.COTY_EGG, "Cotylorhynchus Egg");
        addBlock(UPBlocks.BRACHI_EGG, "Brachiosaurus Egg");
        addBlock(UPBlocks.REX_EGG, "Tyrannosaurus Rex Egg");
        addBlock(UPBlocks.TRIKE_EGG, "Triceratops Egg");
        addBlock(UPBlocks.RAPTOR_EGG, "Velociraptor Egg");
        addBlock(UPBlocks.PACHY_EGG, "Pachycephalosaurus Egg");
        addBlock(UPBlocks.ENCRUSTED_SACK, "Encrusted Sack");
        addBlock(UPBlocks.ERYON_EGGS, "Eryon Eggs");
        addBlock(UPBlocks.AUSTRO_EGG, "Austroraptor Egg");
        addBlock(UPBlocks.KENTRO_EGG, "Kentrosaurus Egg");
        addBlock(UPBlocks.ULUGH_EGG, "Ulughbegsaurus Egg");
        addBlock(UPBlocks.ANTARCO_EGG, "Antarctopelta Egg");
        addBlock(UPBlocks.HWACHA_EGG, "Hwachavenator Egg");
        addBlock(UPBlocks.BARINA_EGG, "Barinasuchus Egg");
        addBlock(UPBlocks.TALPANAS_EGG, "Talpanas Egg");
        addBlock(UPBlocks.MEGALA_EGG, "Megalania Egg");
        addBlock(UPBlocks.FURCACAUDA_EGGS, "Furcacauda Eggs");


        //SPAWN EGGS
        addItem(UPItems.AMMON_EGG, "Ammonite Spawn Egg");
        addItem(UPItems.STETHA_EGG, "Stethacanthus Spawn Egg");
        addItem(UPItems.DUNK_EGG, "Dunkleosteus Spawn Egg");
        addItem(UPItems.MAJUNGA_EGG, "Majungasaurus Spawn Egg");
        addItem(UPItems.ANURO_EGG, "Anurognathus Spawn Egg");
        addItem(UPItems.BEELZ_EGG, "Beelzebufo Spawn Egg");
        addItem(UPItems.COTY_EGG, "Cotylorhynchus Spawn Egg");
        addItem(UPItems.SCAU_EGG, "Scaumenacia Spawn Egg");
        addItem(UPItems.BRACHI_EGG, "Brachiosaurus Spawn Egg");
        addItem(UPItems.REX_EGG, "Tyrannosaurus Rex Spawn Egg");
        addItem(UPItems.VELOCI_EGG, "Velociraptor Spawn Egg");
        addItem(UPItems.TRIKE_EGG, "Triceratops Spawn Egg");
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
        addItem(UPItems.SLUDGE_EGG, "Sludge Spawn Egg");
        addItem(UPItems.OTAROCYON_EGG, "Otarocyon Spawn Egg");
        addItem(UPItems.LONGISQUAMA_EGG, "Longisquama Spawn Egg");
        addItem(UPItems.FURCA_EGG, "Furcacauda Spawn Egg");
        addItem(UPItems.TARTUOSTEUS_EGG, "Tartuosteus Spawn Egg");
        addItem(UPItems.PSITTACO_EGG, "Psittacosaurus Spawn Egg");
        addItem(UPItems.TANY_EGG, "Tanystropheus Spawn Egg");

        //TAR
        addBlock(UPBlocks.TAR, "Tar Block");
        addBlock(UPBlocks.SPLATTERED_TAR, "Splattered Tar");
        addItem(UPItems.TAR_BUCKET, "Tar Bucket");

        //ONE PIECE ARMORS
        addItem(UPItems.MAJUNGA_HELMET, "Majungasaurus Helmet");
        addItem(UPItems.AUSTRO_BOOTS, "Austroraptor Boots");
        addItem(UPItems.TYRANTS_CROWN, "Tyrants Crown");
        addItem(UPItems.SLOTH_POUCH_ARMOR, "Sloth Pouch Armor");

        //SHEDSCALE ARMOR
        addItem(UPItems.SHEDSCALE_HELMET, "Shedscale Wraps");
        addItem(UPItems.SHEDSCALE_CHESTPLATE, "Shedscale Chestpiece");
        addItem(UPItems.SHEDSCALE_LEGGINGS, "Shedscale Pants");
        addItem(UPItems.SHEDSCALE_BOOTS, "Shedscale Shoes");

        //SHIELDS
        addItem(UPItems.VELOCI_SHIELD, "Veloci-Shield");
        addItem(UPItems.TRIKE_SHIELD, "Trike Shield");



        //FLASKS

        addItem(UPItems.FLASK, "Flask");
        addItem(UPItems.STETHA_FLASK, "Stethacanthus DNA Flask");
        addItem(UPItems.DUNK_FLASK, "Dunkleosteus DNA Flask");
        addItem(UPItems.MAJUNGA_FLASK, "Majungasaurus DNA Flask");
        addItem(UPItems.AMMONITE_FLASK, "Ammonite DNA Flask");
        addItem(UPItems.ANURO_FLASK, "Anurognathus DNA Flask");
        addItem(UPItems.BEELZ_FLASK, "Beelzebufo DNA Flask");
        addItem(UPItems.COTY_FLASK, "Cotylorhynchus DNA Flask");
        addItem(UPItems.SCAU_FLASK, "Scaumenacia DNA Flask");
        addItem(UPItems.BRACHI_FLASK, "Brachiosaurus DNA Flask");
        addItem(UPItems.REX_FLASK, "Tyrannosaurus Rex DNA Flask");
        addItem(UPItems.RAPTOR_FLASK, "Velociraptor DNA Flask");
        addItem(UPItems.TRIKE_FLASK, "Triceratops DNA Flask");
        addItem(UPItems.PACHY_FLASK, "Pachycephalosaurus DNA Flask");
        addItem(UPItems.ENCRUSTED_FLASK, "Encrusted DNA Flask");
        addItem(UPItems.ERYON_FLASK, "Eryon DNA Flask");
        addItem(UPItems.HORSETAIL_FLASK, "Prehistoric Horsetail DNA Flask");
        addItem(UPItems.TALL_HORSETAIL_FLASK, "Prehistoric Tall Horsetail DNA Flask");
        addItem(UPItems.LEEFRUCTUS_FLASK, "Leefructus DNA Flask");
        addItem(UPItems.GINKGO_FLASK, "Prehistoric Ginkgo DNA Flask");
        addItem(UPItems.BENNET_FLASK, "Bennettitales DNA Flask");
        addItem(UPItems.SARR_FLASK, "Sarracenia DNA Flask");
        addItem(UPItems.ARCHAO_FLASK, "Archaeosigillaria DNA Flask");
        addItem(UPItems.ANOSTYLOSTRAMA_FLASK, "Anostylostroma DNA Flask");
        addItem(UPItems.CLATHRODICTYON_FLASK, "Clathrodictyon DNA Flask");
        addItem(UPItems.ARCHAEFRUCTUS_FLASK, "Archaefructus DNA Flask");
        addItem(UPItems.NELUMBITES_FLASK, "Nelumbites DNA Flask");
        addItem(UPItems.QUEREUXIA_FLASK, "Quereuxia DNA Flask");
        addItem(UPItems.ANTARCTO_FLASK, "Antarctopelta DNA Flask");
        addItem(UPItems.AUSTRO_FLASK, "Austroraptor DNA Flask");
        addItem(UPItems.HWACHA_FLASK, "Hwachavenator DNA Flask");
        addItem(UPItems.KENTRO_FLASK, "Kentrosaurus DNA Flask");
        addItem(UPItems.ULUGH_FLASK, "Ulughbegsaurus DNA Flask");
        addItem(UPItems.GIGANTO_FLASK, "Gigantopithecus DNA Flask");
        addItem(UPItems.SMILO_FLASK, "Smilodon DNA Flask");
        addItem(UPItems.MEGATH_FLASK, "Megatherium DNA Flask");
        addItem(UPItems.PARACER_FLASK, "Paraceratherium DNA Flask");
        addItem(UPItems.MAMMOTH_FLASK, "Mammoth DNA Flask");
        addItem(UPItems.BARIN_FLASK, "Barinasuchus DNA Flask");
        addItem(UPItems.PALAEO_FLASK, "Palaeolophis DNA Flask");
        addItem(UPItems.MEGALA_FLASK, "Megalania DNA Flask");
        addItem(UPItems.TALPANAS_FLASK, "Talpanas DNA Flask");
        addItem(UPItems.ZULOAGAE_FLASK, "Zuloagae DNA Flask");
        addItem(UPItems.RAIGUENRAYUN_FLASK, "Raiguenrayun DNA Flask");
        addItem(UPItems.FOXXI_FLASK, "Foxii DNA Flask");
        addItem(UPItems.DRYO_FLASK, "Dryophyllum DNA Flask");
        addItem(UPItems.OTAROCYON_FLASK, "Otarocyon DNA Flask");
        addItem(UPItems.LONGI_FLASK, "Longisquama DNA Flask");
        addItem(UPItems.FURCA_FLASK, "Furcacauda DNA Flask");
        addItem(UPItems.TARTUO_FLASK, "Tartuosteus DNA Flask");
        addItem(UPItems.TANY_FLASK, "Tanystropheus DNA Flask");
        addItem(UPItems.PSITTACO_FLASK, "Psittacosaurus DNA Flask");
        addItem(UPItems.KAPRO_FLASK, "Kaprosuchus DNA Flask");
        addItem(UPItems.PSILO_FLASK, "Psilopterus DNA Flask");
        addItem(UPItems.OPHIO_FLASK, "Ophiodon DNA Flask");
        addItem(UPItems.DIPLO_FLASK, "Diplocaulus DNA Flask");
        addItem(UPItems.HYNERP_FLASK, "Hynerpeton DNA Flask");
        addItem(UPItems.BALAUR_FLASK, "Balaur DNA Flask");

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
        addBlock(UPBlocks.DEEPSLATE_PLANT_FOSSIL, "Deepslate Plant Fossil");
        addBlock(UPBlocks.PLANT_FOSSIL, "Plant Fossil Block");
        addBlock(UPBlocks.STONE_AMBER_FOSSIL, "Stone Amber Ore");
        addBlock(UPBlocks.DEEPSLATE_AMBER_FOSSIL, "Deepslate Amber Ore");
        addBlock(UPBlocks.ANALYZER, "Analyzer");
        addBlock(UPBlocks.CULTIVATOR, "Cultivator");
        addBlock(UPBlocks.DNA_FRIDGE, "DNA Fridge");
        addBlock(UPBlocks.AMBER_GLASS, "Amber Glass");
        addBlock(UPBlocks.REX_HEAD, "Rex Head Mount");
        addBlock(UPBlocks.AMMONITE_SHELL, "Ammonite Shell");
        addBlock(UPBlocks.REX_BOOMBOX, "Rex Boombox");
        addBlock(UPBlocks.AMBER_BLOCK, "Amber Block");
        addBlock(UPBlocks.OPAL_BLOCK, "Opal Block");
        addBlock(UPBlocks.INCUBATOR, "Incubator");
        addBlock(UPBlocks.ASPHALT, "Asphalt");
        addBlock(UPBlocks.GOLD_ENGRAVED_ASPHALT, "Gold Engraved Asphalt");
        addBlock(UPBlocks.QUARTZ_ENGRAVED_ASPHALT, "Quartz Engraved Asphalt");
        addBlock(UPBlocks.STONE_OPAL_FOSSIL, "Stone Opal Ore");
        addBlock(UPBlocks.DEEPSLATE_OPAL_FOSSIL, "Deepslate Opal Ore");
        addBlock(UPBlocks.STONE_TAR_FOSSIL, "Stone Tar Fossil Ore");
        addBlock(UPBlocks.DEEPSLATE_TAR_FOSSIL, "Deepslate Tar Fossil Ore");
        addBlock(UPBlocks.PERMAFROST_FOSSIL, "Permafrost Fossil Ore");
        addBlock(UPBlocks.PERMAFROST, "Permafrost");

        //PLANTS
        addBlock(UPBlocks.HORSETAIL, "Prehistoric Horsetail");
        addBlock(UPBlocks.TALL_HORSETAIL, "Prehistoric Tall Horsetail");
        addBlock(UPBlocks.LEEFRUCTUS, "Leefructus");
        addBlock(UPBlocks.BENNETTITALES, "Bennettitales");
        addBlock(UPBlocks.ARCHAEOSIGILARIA, "Archaeosigillaria");
        addBlock(UPBlocks.SARACENIA, "Sarracenia");
        addBlock(UPBlocks.TALL_SARACENIA, "Tall Sarracenia");
        addBlock(UPBlocks.GINKGO_SAPLING, "Prehistoric Ginkgo Sapling");
        addBlock(UPBlocks.ARCHAEFRUCTUS, "Archaefructus");
        addBlock(UPBlocks.NELUMBITES, "Nelumbites");
        addBlock(UPBlocks.QUEREUXIA, "Quereuxia");
        addBlock(UPBlocks.QUEREUXIA_TOP, "Quereuxia Clovers");
        addBlock(UPBlocks.PETRIFIED_BUSH, "Petrified Bush");
        addBlock(UPBlocks.ZULOAGAE, "Zuloagae");
        addBlock(UPBlocks.ZULOAGAE_SAPLING, "Zuloagae Sapling");
        addBlock(UPBlocks.RAIGUENRAYUN, "Raiguenrayun");

        //GINKGO
        addBlock(UPBlocks.GINKGO_PLANKS, "Prehistoric Ginkgo Planks");
        addBlock(UPBlocks.GINKGO_WOOD, "Prehistoric Ginkgo Wood");
        addBlock(UPBlocks.STRIPPED_GINKGO_WOOD, "Stripped Prehistoric Ginkgo Wood");
        addBlock(UPBlocks.GINKGO_LEAVES, "Prehistoric Ginkgo Leaves");
        addBlock(UPBlocks.GINKGO_LOG, "Prehistoric Ginkgo Log");
        addBlock(UPBlocks.STRIPPED_GINKGO_LOG, "Stripped Prehistoric Ginkgo Log");
        addBlock(UPBlocks.GINKGO_STAIRS, "Prehistoric Ginkgo Stairs");
        addBlock(UPBlocks.GINKGO_SLAB, "Prehistoric Ginkgo Slab");
        addBlock(UPBlocks.GINKGO_FENCE, "Prehistoric Ginkgo Fence");
        addBlock(UPBlocks.GINKGO_FENCE_GATE, "Prehistoric Ginkgo Fence Gate");
        addBlock(UPBlocks.GINKGO_DOOR, "Prehistoric Ginkgo Door");
        addBlock(UPBlocks.GINKGO_TRAPDOOR, "Prehistoric Ginkgo Trapdoor");
        addBlock(UPBlocks.GINKGO_BUTTON, "Prehistoric Ginkgo Button");
        addBlock(UPBlocks.GINKGO_PRESSURE_PLATE, "Prehistoric Ginkgo Pressure Plate");
        addItem(UPItems.GINKGO_SIGN, "Prehistoric Ginkgo Sign");
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
        addItem(UPItems.FOXXI_WOOD_SIGN, "Foxii Sign");

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
        addItem(UPItems.DRYO_WOOD_SIGN, "Dryophyllum Sign");

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
        addItem(UPItems.PETRIFIED_WOOD_SIGN, "Petrified Sign");
        addBlock(UPBlocks.POLISHED_PETRIFIED_WOOD, "Polished Petrified Wood");
        addBlock(UPBlocks.POLISHED_PETRIFIED_WOOD_STAIRS, "Polished Petrified Wood Stairs");
        addBlock(UPBlocks.POLISHED_PETRIFIED_WOOD_SLAB, "Polished Petrified Wood Slab");


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

        //ENTITIES
        addEntityType(UPEntities.AMMON, "Ammonite");
        addEntityType(UPEntities.STETHACANTHUS, "Stethacanthus");
        addEntityType(UPEntities.MAJUNGA, "Majungasaurus");
        addEntityType(UPEntities.ANURO, "Anurognathus");
        addEntityType(UPEntities.BEELZ, "Beelzebufo");
        addEntityType(UPEntities.BEELZE_TADPOLE, "Beelzebufo Tadpole");
        addEntityType(UPEntities.DUNK, "Dunkleosteus");
        addEntityType(UPEntities.BABY_DUNK, "Baby Dunkleosteus");
        addEntityType(UPEntities.COTY, "Cotylorhynchus");
        addEntityType(UPEntities.TRIKE, "Triceratops");
        addEntityType(UPEntities.PACHY, "Pachycephalosaurus");
        addEntityType(UPEntities.BRACHI, "Brachiosaurus");
        addEntityType(UPEntities.BRACHI_TEEN, "Teen Brachiosaurus");
        addEntityType(UPEntities.BABY_BRACHI, "Baby Brachiosaurus");
        addEntityType(UPEntities.VELOCI, "Velociraptor");
        addEntityType(UPEntities.REX, "Tyrannosaurus Rex");
        addEntityType(UPEntities.BABY_REX, "Baby Tyrannosaurus Rex");
        addEntityType(UPEntities.ERYON, "Eryon");
        addEntityType(UPEntities.ENCRUSTED, "Encrusted");
        addEntityType(UPEntities.SCAU, "Scaumenacia");
        addEntityType(UPEntities.AMBER_SHOT, "Amber Shot");
        addEntityType(UPEntities.HWACHA_SPIKE, "Hwachavenator Pin");
        addEntityType(UPEntities.AUSTRO, "Austroraptor");
        addEntityType(UPEntities.ANTARCO, "Antarctopelta");
        addEntityType(UPEntities.ULUG, "Ulughbegsaurus");
        addEntityType(UPEntities.KENTRO, "Kentrosaurus");
        addEntityType(UPEntities.HWACHA, "Hwachavenator");
        addEntityType(UPEntities.TALPANAS, "Talpanas");
        addEntityType(UPEntities.GIGANTOPITHICUS, "Gigantopithecus");
        addEntityType(UPEntities.SMILODON, "Smilodon");
        addEntityType(UPEntities.BARINASUCHUS, "Barinasuchus");
        addEntityType(UPEntities.PARACERATHERIUM, "Paraceratherium");
        addEntityType(UPEntities.MEGATHERIUM, "Megatherium");
        addEntityType(UPEntities.MAMMOTH, "Mammoth");
        addEntityType(UPEntities.MEGALANIA, "Megalania");
        addEntityType(UPEntities.PALAEOPHIS, "Palaeolophis");
        addEntityType(UPEntities.BABY_MEGATHERIUM, "Baby Megatherium");
        addEntityType(UPEntities.OPALESCENT_PEARL, "Opalescent Pearl");
        addEntityType(UPEntities.OPALESCENT_SHURIKEN, "Opalescent Shuriken");
        addEntityType(UPEntities.BABY_GIGANTO, "Baby Gigantopithecus");
        addEntityType(UPEntities.BABY_PARACER, "Baby Paraceratherium");
        addEntityType(UPEntities.BABY_MEGALANIA, "Baby Megalania");
        addEntityType(UPEntities.BABY_PALAEO, "Baby Palaeolophis");
        addEntityType(UPEntities.BABY_BARINA, "Baby Barinasuchus");
        addEntityType(UPEntities.BABY_SMILODON, "Baby Smilodon");
        addEntityType(UPEntities.BABY_MAMMOTH, "Baby Mammoth");
        addEntityType(UPEntities.PALAEOPHIS_PART, "Palaeophis Body");
        addEntityType(UPEntities.SLUDGE, "Sludge");
        addEntityType(UPEntities.ICEBERG_MAMMOTH, "Frozen Mammoth");
        addEntityType(UPEntities.ICEBERG_SMILODON, "Frozen Smilodon");
        addEntityType(UPEntities.OTAROCYON, "Otarocyon");
        addEntityType(UPEntities.LONGISQUAMA, "Longisquama");
        addEntityType(UPEntities.FURCA, "Furcacauda");
        addEntityType(UPEntities.TARTUOSTEUS, "Tartuosteus");
        addEntityType(UPEntities.PSITTACO, "Psittacosaurus");
        addEntityType(UPEntities.TANY, "Tanystropheus");

        //SOUNDS
        addSound(UPSounds.BEELZE_IDLE, "Massive croaks");
        addSound(UPSounds.BEELZE_ATTACK, "Slimey chomp");
        addSound(UPSounds.BEELZE_HURT, "Slimed hurt");
        addSound(UPSounds.BEELZE_DEATH, "Croaking death");
        addSound(UPSounds.MAJUNGA_IDLE, "Unusual idling");
        addSound(UPSounds.MAJUNGA_ATTACK, "Unusual bite");
        addSound(UPSounds.MAJUNGA_HURT, "Unusual hurt");
        addSound(UPSounds.MAJUNGA_DEATH, "Dying groans");
        addSound(UPSounds.MAJUNGA_STEP, "Unusual Stomping");
        addSound(UPSounds.ANURO_IDLE, "Small squak");
        addSound(UPSounds.ANURO_HURT, "Small hurt");
        addSound(UPSounds.ANURO_DEATH, "Dying squeal");
        addSound(UPSounds.COTY_IDLE, "Low groans");
        addSound(UPSounds.COTY_HURT, "Painful groan");
        addSound(UPSounds.COTY_DEATH, "Last groans");
        addSound(UPSounds.DUNK_HURT, "Hurtful Clunk");
        addSound(UPSounds.DUNK_ATTACK, "Strong bite");
        addSound(UPSounds.DUNK_DEATH, "Dying Creak");
        addSound(UPSounds.REX_IDLE, "Tyrant's droning");
        addSound(UPSounds.REX_STOMP_ATTACK, "Powerful stomp");
        addSound(UPSounds.REX_BITE, "Chomp");
        addSound(UPSounds.REX_TAIL_SWIPE, "Stong slash");
        addSound(UPSounds.REX_HURT, "Painful grunts");
        addSound(UPSounds.REX_STEP, "Forceful stomp");
        addSound(UPSounds.REX_DEATH, "Dying roar");
        addSound(UPSounds.TRIKE_IDLE, "Stout death");
        addSound(UPSounds.TRIKE_HURT, "Sternful groan");
        addSound(UPSounds.TRIKE_DEATH, "Stout death");
        addSound(UPSounds.BRACHI_IDLE, "Graceful bellows");
        addSound(UPSounds.BRACHI_HURT, "Disgraced bellows");
        addSound(UPSounds.BRACHI_TOSS, "Blessed toss");
        addSound(UPSounds.BRACHI_STEP, "Gargantuan stomp");
        addSound(UPSounds.BRACHI_DEATH, "Mournful bellows");
        addSound(UPSounds.ENCRUSTED_IDLE, "Bzzzzzzzz");
        addSound(UPSounds.ENCRUSTED_HURT, "Bzzt");
        addSound(UPSounds.ENCRUSTED_MELEE, "Zzzt");
        addSound(UPSounds.ENCRUSTED_SPIT, "Pzzt");
        addSound(UPSounds.ENCRUSTED_DEATH, "Zzzzz");
        addSound(UPSounds.RAPTOR_IDLE, "Docile cooing");
        addSound(UPSounds.RAPTOR_HURT, "Painful scream");
        addSound(UPSounds.RAPTOR_ATTACK, "Veloci-slash");
        addSound(UPSounds.RAPTOR_SEARCH, "Curious coos");
        addSound(UPSounds.RAPTOR_DEATH, "Shrill screech");
        addSound(UPSounds.PACHY_IDLE, "Gruff Grunts");
        addSound(UPSounds.PACHY_HURT, "Painful Grunt");
        addSound(UPSounds.PACHY_HEADBUTT, "Sternful Bonk");
        addSound(UPSounds.PACHY_KICK, "Powerful Kick");
        addSound(UPSounds.PACHY_DEATH, "Dying cries");
        addSound(UPSounds.ERYON_IDLE, "Crabful Noises");
        addSound(UPSounds.ERYON_HURT, "Crustaceous Grunts");
        addSound(UPSounds.ERYON_DEATH, "Crabby Death");
        addSound(UPSounds.AUSTRO_BITE, "Squakful Nip");
        addSound(UPSounds.AUSTRO_DEATH, "Dying Coos");
        addSound(UPSounds.AUSTRO_HURT, "Sharp Squak");
        addSound(UPSounds.AUSTRO_IDLE, "Graceful Coos");
        addSound(UPSounds.AUSTRO_PREEN, "Graceful Preening");
        addSound(UPSounds.HWACHA_DEATH, "Prickly Groans");
        addSound(UPSounds.HWACHA_HURT, "Prickly Grunt");
        addSound(UPSounds.HWACHA_IDLE, "Spiked Hums");
        addSound(UPSounds.HWACHA_SHOOT, "Shooting");
        addSound(UPSounds.KENTRO_DEATH, "Yearning Growls");
        addSound(UPSounds.KENTRO_HURT, "Painful Growl");
        addSound(UPSounds.KENTRO_IDLE, "Low Growl");
        addSound(UPSounds.TAIL_SWIPE, "Strong Slash");
        addSound(UPSounds.ULUGH_BITE, "Gnawing Ugh");
        addSound(UPSounds.ULUGH_DEATH, "Dying Ugh");
        addSound(UPSounds.ULUGH_HURT, "Painful Ugh");
        addSound(UPSounds.ULUGH_IDLE, "Idle Ugh");
        addSound(UPSounds.ULUGH_STEP, "Ughing Step");
        addSound(UPSounds.ANTARCTO_DEATH, "Hardtoned Death");
        addSound(UPSounds.ANTARCTO_HURT, "Annoyed Gruff");
        addSound(UPSounds.ANTARCTO_IDLE, "Hard Gruff");
        addSound(UPSounds.REX_BOOMBOX, "Boom");
        addSound(UPSounds.GIGANTO_IDLE, "Ooo OO Ah Ah");
        addSound(UPSounds.GIGANTO_DEATH, "Oooo Ahhh");
        addSound(UPSounds.GIGANTO_HURT, "Ooof Ahhf");
        addSound(UPSounds.GIGANTO_TRADE, "OOOoooooOO");
        addSound(UPSounds.MAMMOTH_DEATH, "Dying Pawoo");
        addSound(UPSounds.MAMMOTH_HURT, "Painful Pawoo");
        addSound(UPSounds.MAMMOTH_IDLE, "Powerful Pawoo");
        addSound(UPSounds.PARACER_DEATH, "Sternful Groan");
        addSound(UPSounds.PARACER_HURT, "Sternful Grunt");
        addSound(UPSounds.PARACER_IDLE, "Sternful Gruff");
        addSound(UPSounds.PARACER_STOMP, "Sternful Stomp");
        addSound(UPSounds.TALPANAS_DEATH, "Dying Quack");
        addSound(UPSounds.TALPANAS_HURT, "Painful Quack");
        addSound(UPSounds.TALPANAS_IDLE, "Idling Quack");
        addSound(UPSounds.TALPANAS_PANIC, "Panicked Quack");
        addSound(UPSounds.CROCARINA, "Soothing Tunes");
        addSound(UPSounds.BARINA_DEATH, "Crocodilain Groans");
        addSound(UPSounds.BARINA_HURT, "Crocodilain Pain");
        addSound(UPSounds.BARINA_IDLE, "Crocodilain Growls");
        addSound(UPSounds.ZULOGAE_DISC, "Music Disc");
        addSound(UPSounds.MEGATHER_DEATH, "Odd Death Noise");
        addSound(UPSounds.MEGATHER_HURT, "Odd Hurt Noise");
        addSound(UPSounds.MEGATHER_IDLE, "Odd Idling");
        addSound(UPSounds.SMILODON_DEATH, "Dying Snarl");
        addSound(UPSounds.SMILODON_HURT, "Painful Snarl");
        addSound(UPSounds.SMILODON_IDLE, "Snarl");
        addSound(UPSounds.PALAEO_HURT, "Painful Hiss");
        addSound(UPSounds.PALAEO_DEATH, "Dying Hiss");
        addSound(UPSounds.PALAEO_IDLE, "Idling Hissing");
        addSound(UPSounds.PALAEO_BITE, "Hissing Snap");
        addSound(UPSounds.TAR_POP, "Tar Popping");
        addSound(UPSounds.TAR_AMBIENT, "Tar Settling");
        addSound(UPSounds.MEGALANIA_DEATH, "Dying Hissing");
        addSound(UPSounds.MEGALANIA_HURT, "Distressed Hissing");
        addSound(UPSounds.MEGALANIA_IDLE, "Idling Hiss");
        addSound(UPSounds.SLUDGE_DEATH, "Gurgling Death");
        addSound(UPSounds.SLUDGE_HURT, "Painful Gurgles");
        addSound(UPSounds.SLUDGE_IDLE, "Gurgles");
        addSound(UPSounds.SLUDGE_SLAM, "Wet Slam");
        addSound(UPSounds.SLUDGE_SLAP, "Wet Slap");

        add("entity.unusualprehistory.all.command_0", "%s is wandering");
        add("entity.unusualprehistory.all.command_1", "%s is following");
        add("entity.unusualprehistory.all.command_2", "%s is staying");
        add("block.unusualprehistory.majunga_egg.desc", "%s x %s");
        add("block.unusualprehistory.coty_egg.desc", "%s x %s");
        add("item.unusualprehistory.encyclopedia.desc", "By Award Winning Scientist, Peeko Noneyah");
        add("hwachavenator.shooting_too_close.message", "You are trying to shoot too close to Hwachavenator!");
        add("hwachavenator.fail_shoot.message", "You failed to shoot!");
        add("player_capability.amber_protection_full", "You are at full Amber Protection and won't gain any more!");
        add("dinosaur.start_riding.attack_key", "Press G to Attack!");
        add("death.attack.hwacha_0", "%s was turned into a pincushion");
        add("death.attack.hwacha_1", "%s was shredded");
        add("death.attack.hwacha_2", "%s was turned to fine paste");
        add("death.attack.hwacha_3", "%s was shot");
        add("instrument.unusualprehistory.ocarina", "Crocarina");

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
        add("unusualprehistory.advancement.interact_rex.desc", "Revive a Tyrannosaurus Rex");

        add("unusualprehistory.advancement.rex_passify", "Down With the King!");
        add("unusualprehistory.advancement.rex_passify.desc", "Beat a T Rex to its last breath and spare it");

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
        add("unusualprehistory.advancement.ginkgo.desc", "Revive a Prehistoric Ginkgo Tree");

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
        add("item.unusualprehistory.zulogae_disc.desc", " Shroomy - Zulogae");

        add("death.attack.sludge_0.player", "%s was slapped to death by %s");


        add("unusualprehistory.megalania_damage", " %s Venomous Coating");
        add("unusualprehistory.battle_axe", "An axe made for battle. Cutting wood effectively goes poorly..");
        add("unusualprehistory.fruit_loot_box.salubrious", "Salubrious Fruit Loot Box");
        add("unusualprehistory.fruit_loot_box.luxurious", "Luxurious Fruit Loot Box");
        add("unusualprehistory.fruit_loot_box.redolent", "Redolent Fruit Loot Box");
        add("unusualprehistory.fruit_loot_box.exotic", "Exotic Fruit Loot Box");
        add("unusualprehistory.fruit_loot_box.default", "Default Fruit Loot Box");
        add("unusualprehistory.attacher.animal_has_embryo", "%s already has an embryo");
        add("unusualprehistory.attacher.animal_not_correct", "%s cannot be inserted into %s");
        add("unusualprehistory.beelzebufo.meat_stick", "Hold coty on a stick to control");
        add("unusualprehistory.musical_tame.tame", "%s is impressed by your musical prowess, it wants to be with you");
        add("unusualprehistory.musical_tame.too_early", "You haven't played enough of the song yet");
        add("unusualprehistory.musical_tame.fail_tame", "%s was not impressed and doesn't want to be with you");
        add("unusualprehistory.musical_tame.no_entity_found", "Nobody Can Hear You!");
        add("unusualprehistory.attacher.embryo_attached", "Successfully attached embryo to %s!");
        add("unusualprehistory.megatherium_baby.tame_tooltip", "Baby Megatherium is Tamed!");
        add("unusualprehistory.megatherium_baby.minutes_tooltip", "%s Minutes left until Baby Megatherium is Tamed!");



        //EFFECTS
        addEffect(UPEffects.SCREEN_SHAKE, "Screen Shake");
        addEffect(UPEffects.BRACHI_PROTECTION, "Brachi's Protection");
        addEffect(UPEffects.PREVENT_CLICK, "Debilitating Toxin");
        addEffect(UPEffects.PACHYS_MIGHT, "Pachy's Might");
        addEffect(UPEffects.HEALTH_REDUCTION, "Draining Venom");

        //BLOCK ENTITY TRANSLATABLE
        addBETranslatable("analyzer", "                             Analyzer");
        addBETranslatable("analyzer_jei", "Analyzer");
        addBETranslatable("dna_fridge", "DNA Fridge");
        addBETranslatable("cultivator", "Cultivator");
        addBETranslatable("cultivator_jei", "Cultivator");
    }

    @Override
    public String getName() {
        return  UnusualPrehistory.MODID  + " Languages: en_us";
    }

    public void addBETranslatable(String beName,String name){
        add(UnusualPrehistory.MODID + ".blockentity." + beName, name);
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
}
