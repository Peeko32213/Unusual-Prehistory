package com.peeko32213.unusualprehistory.datagen;

import com.mojang.logging.LogUtils;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.data.LanguageProvider;
import org.slf4j.Logger;

import java.util.function.Supplier;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.DINO_TAB;

public class LanguageGenerator extends LanguageProvider {
    public LanguageGenerator(DataGenerator gen){
        super(gen, UnusualPrehistory.MODID, "en_us");
    }
    private static final Logger LOGGER = LogUtils.getLogger();
    @Override
    protected void addTranslations(){


        //TABS
        addTabName(DINO_TAB, "Unusual Prehistory");

        //MISC
        addItem(UPItems.ENCYLOPEDIA, "Encyclopedia of Prehistory");
        addItem(UPItems.ADORNED_STAFF, "Adorned Staff");
        addItem(UPItems.ORGANIC_OOZE, "Organic Ooze");

        //TOOLS
        addItem(UPItems.WARPICK, "War Pick");
        addItem(UPItems.PRIMAL_MACUAHUITL, "Primal Macuahuitl");

        //FOSSILS
        addItem(UPItems.SHELL_SHARD, "Shell Shard");
        addItem(UPItems.PALEO_FOSSIL, "Paleozoic Fossil");
        addItem(UPItems.MEZO_FOSSIL, "Mesozoic Fossil");
        addItem(UPItems.PLANT_FOSSIL, "Plant Fossil");
        addItem(UPItems.AMBER_FOSSIL, "Amber Fossil");
        addItem(UPItems.AMBER_SHARDS, "Amber Chunk");



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

        //BUCKETS
        addItem(UPItems.BEELZE_BUCKET, "Bucket of Beelzebufo Tadpole");
        addItem(UPItems.AMMON_BUCKET, "Bucket of Ammonite");
        addItem(UPItems.STETHA_BUCKET, "Bucket of Stethacanthus");
        addItem(UPItems.SCAU_BUCKET, "Bucket of Scaumenacia");
        addItem(UPItems.DUNK_BUCKET, "Bucket of Baby Dunkleosteus");


        //EGGS
        addBlock(UPBlocks.STETHA_EGGS, "Stethacanthus Eggs");
        addBlock(UPBlocks.BEELZE_EGGS, "Beelzebufo Eggs");
        addBlock(UPBlocks.AMON_EGGS, "Ammonite Eggs");
        addBlock(UPBlocks.DUNK_EGGS, "Dunkleosteus Eggs");
        addBlock(UPBlocks.SCAU_EGGS, "Scaumenacia Eggs");
        addBlock(UPBlocks.ANURO_EGG, "Anurognathus Eggs");
        addBlock(UPBlocks.MAJUNGA_EGG, "Majungasaurus Eggs");
        addBlock(UPBlocks.COTY_EGG, "Cotylorhynchus Eggs");
        addBlock(UPBlocks.BRACHI_EGG, "Brachiosaurus Eggs");
        addBlock(UPBlocks.REX_EGG, "Tyrannosaurus Rex Eggs");
        addBlock(UPBlocks.TRIKE_EGG, "Triceratops Eggs");
        addBlock(UPBlocks.RAPTOR_EGG, "Velociraptor Eggs");
        addBlock(UPBlocks.PACHY_EGG, "Pachycephalosaurus Eggs");
        addBlock(UPBlocks.ENCRUSTED_SACK, "Encrusted Sack");
        addBlock(UPBlocks.ERYON_EGGS, "Eryon Eggs");
        addBlock(UPBlocks.AUSTRO_EGG, "Austroraptor Eggs");
        addBlock(UPBlocks.KENTRO_EGG, "Kentrosaurus Eggs");
        addBlock(UPBlocks.ULUGH_EGG, "Ulughbegsaurus Eggs");
        addBlock(UPBlocks.ANTARCO_EGG, "Antarctopelta Eggs");
        addBlock(UPBlocks.HWACHA_EGG, "Hwachavenator Eggs");


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
        addItem(UPItems.TALAPANAS_EGG, "Talapanas Spawn Egg");
        addItem(UPItems.GIGANTO_EGG, "Gigantopithicus Spawn Egg");
        addItem(UPItems.BARINA_EGG, "Barinasuchus Spawn Egg");
        addItem(UPItems.MEGATH_EGG, "Megatherium Spawn Egg");
        addItem(UPItems.SMILO_EGG, "Smilodon Spawn Egg");
        addItem(UPItems.PARACER_EGG, "Paraceratherium Spawn Egg");
        addItem(UPItems.MAMMOTH_EGG, "Mammoth Spawn Egg");
        addItem(UPItems.MEGALANIA_EGG, "Megalania Spawn Egg");
        addItem(UPItems.PALAEOPHIS_EGG, "Palaophis Spawn Egg");


        //ARMOR
        addItem(UPItems.MAJUNGA_HELMET, "Majungasaurus Helmet");
        addItem(UPItems.AUSTRO_BOOTS, "Austroraptor Boots");


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
        addBlock(UPBlocks.DNA_FRIDGE, "Dna Fridge");
        addBlock(UPBlocks.AMBER_GLASS, "Amber Glass");
        addBlock(UPBlocks.REX_HEAD, "Rex Head Mount");
        addBlock(UPBlocks.AMMONITE_SHELL, "Ammonite Shell");
        addBlock(UPBlocks.REX_BOOMBOX, "Rex Boombox");

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
        addBlock(UPBlocks.AMBER_BLOCK, "Amber Block");
        addBlock(UPBlocks.PETRIFIED_BUSH, "Petrified Bush");

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
        addEntityType(UPEntities.ENTITY_TRAIL, "Trail");
        addEntityType(UPEntities.AUSTRO, "Austroraptor");
        addEntityType(UPEntities.ANTARCO, "Antarctopelta");
        addEntityType(UPEntities.ULUG, "Ulughbegsaurus");
        addEntityType(UPEntities.KENTRO, "Kentrosaurus");
        addEntityType(UPEntities.HWACHA, "Hwachavenator");
        addEntityType(UPEntities.TALAPANAS, "Talapanas");
        addEntityType(UPEntities.GIGANTOPITHICUS, "Gigantopithicus");
        addEntityType(UPEntities.SMILODON, "Smilodon");
        addEntityType(UPEntities.BARINASUCHUS, "Barinasuchus");
        addEntityType(UPEntities.PARACERATHERIUM, "Paraceratherium");
        addEntityType(UPEntities.MEGATHERIUM, "Megatherium");
        addEntityType(UPEntities.MAMMOTH, "Mammoth");
        addEntityType(UPEntities.MEGALANIA, "Megalania");
        addEntityType(UPEntities.PALAEOPHIS, "Palaophis");


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

        add("death.attack.hwacha_0.player", "%s was turned into a pincushion by %s");
        add("death.attack.hwacha_1.player", "%s was shredded by %s");
        add("death.attack.hwacha_2.player", "%s was turned to fine paste by %s");
        add("death.attack.hwacha_3.player", "%s was shot by %s");

        //EFFECTS
        addEffect(UPEffects.SCREEN_SHAKE, "Screen Shake");
        addEffect(UPEffects.BRACHI_PROTECTION, "Brachi's Protection");
        addEffect(UPEffects.PREVENT_CLICK, "Debilitating Toxin");
        addEffect(UPEffects.PACHYS_MIGHT, "Pachy's Might");

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
