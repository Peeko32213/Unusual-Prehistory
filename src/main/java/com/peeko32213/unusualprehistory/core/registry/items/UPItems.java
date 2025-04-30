package com.peeko32213.unusualprehistory.core.registry.items;

import com.mojang.datafixers.util.Pair;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.*;
import com.peeko32213.unusualprehistory.common.item.armor.*;
import com.peeko32213.unusualprehistory.common.item.armor.material.UPArmorMaterial;
import com.peeko32213.unusualprehistory.common.item.projectile.*;
import com.peeko32213.unusualprehistory.common.item.tool.*;
import com.peeko32213.unusualprehistory.core.other.tags.UPInstrumentTags;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.other.tags.UPEntityTypeTags;
import com.peeko32213.unusualprehistory.core.registry.blocks.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UPItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UnusualPrehistory.MODID);
    public static final ItemSubRegistryHelper HELPER = UnusualPrehistory.REGISTRY_HELPER.getItemSubHelper();
    public static List<RegistryObject<? extends Item>> AUTO_TRANSLATE = new ArrayList<>();

    public static final RegistryObject<Item> ENCYLOPEDIA = translatedItem("encyclopedia", () -> new EncyclopediaItem(new Item.Properties().stacksTo(1)));

    // Fossils
    public static final RegistryObject<Item> PALEO_FOSSIL = translatedItem("paleozoic_fossil", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MEZO_FOSSIL = translatedItem("mesozoic_fossil", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PLANT_FOSSIL = translatedItem("plant_fossil", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FROZEN_FOSSIL = translatedItem("frozen_meat", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TAR_FOSSIL = translatedItem("tar_fossil", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> AMBER = translatedItem("amber", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AMBER_FOSSIL = translatedItem("amber_fossil", () -> new Item(new Item.Properties()));

    // Opal
    public static final RegistryObject<Item> OPAL = translatedItem("opal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> OPAL_FOSSIL = translatedItem("opal_fossil", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FIRE_OPAL = translatedItem("fire_opal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FIRE_OPAL_FOSSIL = translatedItem("fire_opal_fossil", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BOULDER_OPAL = translatedItem("boulder_opal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BOULDER_OPAL_FOSSIL = translatedItem("boulder_opal_fossil", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLACK_OPAL = translatedItem("black_opal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLACK_OPAL_FOSSIL = translatedItem("black_opal_fossil", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SHELL_SHARD = translatedItem("shell_shard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MEAT_ON_A_STICK = item("meat_on_a_stick", () -> new Item(new Item.Properties().durability(130)));
    public static final RegistryObject<Item> TRIKE_HORN = translatedItem("triceratops_horn", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TRIKE_SHIELD = translatedItem("triceratops_shield", () -> new TriceratopsShieldItem(new Item.Properties().durability(1300).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> TYRANNO_SCALE = translatedItem("tyrannosaurus_scale", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TYRANNO_TOOTH = translatedItem("tyrannosaurus_tooth", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VELOCI_FEATHER = translatedItem("velociraptor_feather", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VELOCI_SHIELD = item("veloci_shield", () -> new VelociraptorShieldItem(new Item.Properties().durability(800).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MAJUNGA_SCUTE = translatedItem("majungasaurus_scute", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ADORNED_STAFF = translatedItem("adorned_staff", () -> new Item(new Item.Properties().durability(100).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> GROG = item("grog_bottle", () -> new UPDrinkItem(new Item.Properties().food(UPFood.GROG).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));
    public static final RegistryObject<Item> CAPTURED_KIMMER_BOTTLE = item("kimmeridgebrachypteraeschnidium_bottle", () -> new CaptureBottleItem(UPEntities.KIMMER::get, Items.GLASS_BOTTLE, false, new Item.Properties().stacksTo(1)));

    // Fossil skeletons
    public static final RegistryObject<Item> TRIKE_SKELETON = translatedItem("triceratops_skeleton", () -> new SkeletonItem(UPEntities.TRIKE_SKELETON, new Item.Properties()));
    public static final RegistryObject<Item> TYRANNO_SKELETON = translatedItem("tyrannosaurus_skeleton", () -> new SkeletonItem(UPEntities.TYRANNO_SKELETON, new Item.Properties()));
    public static final RegistryObject<Item> UNICORN_SKELETON = translatedItem("unicorn_skeleton", () -> new SkeletonItem(UPEntities.UNICORN_SKELETON, new Item.Properties()));

    // Palaeo dna
    public static final RegistryObject<Item> AMMONITE_DNA = dnaItem("ammonite");
    public static final RegistryObject<Item> COTY_DNA = dnaItem("cotylorhynchus");
    public static final RegistryObject<Item> DUNK_DNA = dnaItem("dunkleosteus");
    public static final RegistryObject<Item> SCAU_DNA = dnaItem("scaumenacia");
    public static final RegistryObject<Item> STETHA_DNA = dnaItem("stethacanthus");
    public static final RegistryObject<Item> DIPLO_DNA = dnaItem("diplocaulus");
    public static final RegistryObject<Item> HYNERIA_DNA = dnaItem("hyneria");
    public static final RegistryObject<Item> ESTEMMENO_DNA = dnaItem("estemmenosuchus");
    public static final RegistryObject<Item> EDAPHO_DNA = dnaItem("edaphosaurus");
    public static final RegistryObject<Item> JAWLESS_FISH_DNA = dnaItem("jawless_fish");
    public static final RegistryObject<Item> TARTUO_DNA = dnaItem("tartuosteus");
    public static final RegistryObject<Item> HYNERP_DNA = dnaItem("hynerpeton");

    // Meso dna
    public static final RegistryObject<Item> ANTARCTO_DNA = dnaItem("antarctopelta");
    public static final RegistryObject<Item> ANURO_DNA = dnaItem("anurognathus");
    public static final RegistryObject<Item> AUSTRO_DNA = dnaItem("austroraptor");
    public static final RegistryObject<Item> BEELZ_DNA = dnaItem("beelzebufo");
    public static final RegistryObject<Item> BRACHI_DNA = dnaItem("brachiosaurus");
    public static final RegistryObject<Item> ENCRUSTED_DNA = dnaItem("encrusted");
    public static final RegistryObject<Item> ERYON_DNA = dnaItem("eryon");
    public static final RegistryObject<Item> HWACHA_DNA = dnaItem("hwachavenator");
    public static final RegistryObject<Item> KENTRO_DNA = dnaItem("kentrosaurus");
    public static final RegistryObject<Item> KIMMER_DNA = dnaItem("kimmeridgebrachypteraeschnidium");
    public static final RegistryObject<Item> MAJUNGA_DNA = dnaItem("majungasaurus");
    public static final RegistryObject<Item> PACHY_DNA = dnaItem("pachycephalosaurus");
    public static final RegistryObject<Item> PANACANTHOCARIS_DNA = dnaItem("panacanthocaris");
    public static final RegistryObject<Item> PROTOSPHYRAENA_DNA = dnaItem("protosphyraena");
    public static final RegistryObject<Item> TRIKE_DNA = dnaItem("triceratops");
    public static final RegistryObject<Item> TYRANNO_DNA = dnaItem("tyrannosaurus");
    public static final RegistryObject<Item> ULUGH_DNA = dnaItem("ulughbegsaurus");
    public static final RegistryObject<Item> VELOCI_DNA = dnaItem("velociraptor");
    public static final RegistryObject<Item> LEEDS_DNA = dnaItem("leedsichthys");
    public static final RegistryObject<Item> KAPRO_DNA = dnaItem("kaprosuchus");
    public static final RegistryObject<Item> PTERODAUSTRO_DNA = dnaItem("pterodaustro");
    public static final RegistryObject<Item> PSITTACO_DNA = dnaItem("psittacosaurus");

    // Ceno dna
    public static final RegistryObject<Item> BARINA_DNA = dnaItem("barinasuchus");
    public static final RegistryObject<Item> GIGANTO_DNA = dnaItem("gigantopithecus");
    public static final RegistryObject<Item> MAMMOTH_DNA = dnaItem("mammoth");
    public static final RegistryObject<Item> MEGALANIA_DNA = dnaItem("megalania");
    public static final RegistryObject<Item> MEGATHERIUM_DNA = dnaItem("megatherium");
    public static final RegistryObject<Item> OPHIO_DNA = dnaItem("ophiodon_ozymandias");
    public static final RegistryObject<Item> PALAEO_DNA = dnaItem("palaeophis");
    public static final RegistryObject<Item> PARACER_DNA = dnaItem("paraceratherium");
    public static final RegistryObject<Item> SMILODON_DNA = dnaItem("smilodon");
    public static final RegistryObject<Item> TALPANAS_DNA = dnaItem("talpanas");
    public static final RegistryObject<Item> TELECREX_DNA = dnaItem("telecrex");

    // Plant dna
    public static final RegistryObject<Item> ANOSTYLOSTRAMA_DNA = dnaItem("anostylostroma");
    public static final RegistryObject<Item> ARCHAEFRUCTUS_DNA = dnaItem("archaefructus");
    public static final RegistryObject<Item> ARCHAO_DNA = dnaItem("archaeosigillaria");
    public static final RegistryObject<Item> BENNET_DNA = dnaItem("bennettitales");
    public static final RegistryObject<Item> CLATHRODICTYON_DNA = dnaItem("clathrodictyon");
    public static final RegistryObject<Item> DRYO_DNA = dnaItem("dryophyllum");
    public static final RegistryObject<Item> FOXII_DNA = dnaItem("foxii");
    public static final RegistryObject<Item> GINKGO_DNA = dnaItem("ginkgo");
    public static final RegistryObject<Item> HORSETAIL_DNA = dnaItem("horsetail");
    public static final RegistryObject<Item> LEEFRUCTUS_DNA = dnaItem("leefructus");
    public static final RegistryObject<Item> NELUMBITES_DNA = dnaItem("nelumbites");
    public static final RegistryObject<Item> QUEREUXIA_DNA = dnaItem("quereuxia");
    public static final RegistryObject<Item> RAIGUENRAYUN_DNA = dnaItem("raiguenrayun");
    public static final RegistryObject<Item> SARR_DNA = dnaItem("sarracenia");
    public static final RegistryObject<Item> ZULOAGAE_DNA = dnaItem("zuloagae");

    // Embryos
    public static final RegistryObject<Item> GIGANTO_EMBRYO = translatedItem("gigantopithecus_embryo", () -> new AnimalAttacherItem(new Item.Properties(), UPEntityTypeTags.GIGANTO_EMBRYO_ATTACH_TO, UPEntities.GIGANTOPITHICUS, 1000));
    public static final RegistryObject<Item> MAMMOTH_EMBRYO = translatedItem("mammoth_embryo", () -> new AnimalAttacherItem(new Item.Properties(), UPEntityTypeTags.MAMMOTH_EMBRYO_ATTACH_TO, UPEntities.MAMMOTH, 1000));
    public static final RegistryObject<Item> MEGATH_EMBRYO = translatedItem("megatherium_embryo", () -> new AnimalAttacherItem(new Item.Properties(), UPEntityTypeTags.MEGATH_EMBRYO_ATTACH_TO, UPEntities.MEGATHERIUM, 1000));
    public static final RegistryObject<Item> PALAEO_EMBRYO = translatedItem("palaeophis_embryo", () -> new AnimalAttacherItem(new Item.Properties(), UPEntityTypeTags.PALAEO_EMBRYO_ATTACH_TO, UPEntities.BABY_PALAEO, 1000));
    public static final RegistryObject<Item> PARACER_EMBRYO = translatedItem("paraceratherium_embryo", () -> new AnimalAttacherItem(new Item.Properties(), UPEntityTypeTags.PARACER_EMBRYO_ATTACH_TO, UPEntities.PARACERATHERIUM, 1000));
    public static final RegistryObject<Item> SMILODON_EMBRYO = translatedItem("smilodon_embryo", () -> new AnimalAttacherItem(new Item.Properties(), UPEntityTypeTags.SMILODON_EMBRYO_ATTACH_TO, UPEntities.SMILODON, 1000));
    public static final RegistryObject<Item> UNICORN_EMBRYO = translatedItem("unicorn_embryo", () -> new AnimalAttacherItem(new Item.Properties(), UPEntityTypeTags.OTAROCYON_EMBRYO_ATTACH_TO, UPEntities.UNICORN, 1000));

    public static final RegistryObject<Item> ORGANIC_OOZE = translatedItem("organic_ooze", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BEELZ_SALIVA = translatedItem("beelzebufo_saliva", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AUSTRO_FEATHER = translatedItem("austroraptor_feather", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ANTARCTO_PLATE = translatedItem("antarctopelta_plate", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WARPICK = translatedItem("war_pick", () -> new WarpickItem(UPItemTiers.SHELL, 3, -2.8F));
    public static final RegistryObject<Item> PRIMAL_MACUAHUITL = translatedItem("primal_macuahuitl", () -> new PrimalMacuahuitlItem(UPItemTiers.SHELL, 5, -1.8F));
    public static final RegistryObject<Item> HANDMADE_SPEAR = translatedItem("handmade_spear", () -> new HandmadeSpearItem(UPItemTiers.HANDMADE, 0, -2.4F));
    public static final RegistryObject<Item> HANDMADE_BATTLEAXE = translatedItem("handmade_battleaxe", () -> new HandmadeBattleaxeItem(UPItemTiers.HANDMADE, 5, -1.9F));
    public static final RegistryObject<Item> HANDMADE_CLUB = translatedItem("handmade_club", () -> new HandmadeClubItem(UPItemTiers.HANDMADE, 8, -2.3F));
    public static final RegistryObject<Item> GINKGO_FRUIT = translatedItem("ginkgo_fruit", () -> new Item(new Item.Properties().food(UPFood.GINKGO_FRUIT)));
    public static final RegistryObject<Item> RAW_GINKGO_SEEDS = translatedItem("ginkgo_seeds", () -> new Item(new Item.Properties().food(UPFood.RAW_GINKGO_SEEDS)));
    public static final RegistryObject<Item> COOKED_GINKGO_SEEDS = translatedItem("cooked_ginkgo_seeds", () -> new Item(new Item.Properties().food(UPFood.COOKED_GINKGO_SEEDS)));
    public static final RegistryObject<Item> AMBER_GUMMY = translatedItem("amber_gummy", () -> new AmberGummyItem(new Item.Properties().food(UPFood.AMBER_GUMMY)));

    // Buckets
    public static final RegistryObject<Item> STETHA_BUCKET = item("stethacanthus_bucket", () -> new UPFishBucketItem(UPEntities.STETHACANTHUS, () -> Fluids.WATER, Items.BUCKET, false, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> AMMON_BUCKET = item("ammonite_bucket", () -> new UPFishBucketItem(UPEntities.AMMON, () -> Fluids.WATER, Items.BUCKET, false, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BEELZE_BUCKET = item("beelzebufo_tadpole_bucket", () -> new UPFishBucketItem(UPEntities.BEELZE_TADPOLE, () -> Fluids.WATER, Items.BUCKET, false, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SCAU_BUCKET = item("scaumenacia_bucket", () -> new UPFishBucketItem(UPEntities.SCAU, () -> Fluids.WATER, Items.BUCKET, false, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PALAEO_BUCKET = item("palaeophis_hatchling_bucket", () -> new UPFishBucketItem(UPEntities.BABY_PALAEO, () -> Fluids.WATER, Items.BUCKET, false, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PANACAN_BUCKET = item("panacanthocaris_bucket", () -> new UPFishBucketItem(UPEntities.PANACANTHOCARIS, () -> Fluids.WATER, Items.BUCKET, false, new Item.Properties().stacksTo(1)));

    // Spawn eggs
    public static final RegistryObject<Item> AMMON_SPAWN_EGG = spawnEgg("ammonite", UPEntities.AMMON , 0x402018, 0x99895c);
    public static final RegistryObject<Item> ANTARCO_SPAWN_EGG = spawnEgg("antarctopelta", UPEntities.ANTARCO , 0x39332d, 0xe6e1d4);
    public static final RegistryObject<Item> ANURO_SPAWN_EGG = spawnEgg("anurognathus", UPEntities.ANURO , 0x7d7968, 0xf4dd48);
    public static final RegistryObject<Item> AUSTRO_SPAWN_EGG = spawnEgg("austroraptor", UPEntities.AUSTRO , 0xa19d97, 0xdc4c39);
    public static final RegistryObject<Item> BARINA_SPAWN_EGG = spawnEgg("barinasuchus", UPEntities.BARINASUCHUS , 0x0e0b03, 0xbea61e);
    public static final RegistryObject<Item> BEELZ_SPAWN_EGG = spawnEgg("beelzebufo", UPEntities.BEELZ , 0x5d9439, 0x7457c5);
    public static final RegistryObject<Item> BEELZ_TADPOLE_SPAWN_EGG = spawnEgg("beelzebufo_tadpole", UPEntities.BEELZE_TADPOLE , 0x71b460, 0xd2bd7e);
    public static final RegistryObject<Item> BRACHI_SPAWN_EGG = spawnEgg("brachiosaurus", UPEntities.BRACHI , 0x5e6f9a, 0xc7e1e4);
    public static final RegistryObject<Item> COTY_SPAWN_EGG = spawnEgg("cotylorhynchus", UPEntities.COTY , 0xc26940, 0xebe0c5);
    public static final RegistryObject<Item> DIPLOCAULUS_SPAWN_EGG = spawnEgg("diplocaulus", UPEntities.DIPLOCAULUS , 0x21212e, 0xfc9214);
    public static final RegistryObject<Item> DUNK_SPAWN_EGG = spawnEgg("dunkleosteus", UPEntities.DUNK , 0x417a69, 0x825147);
    public static final RegistryObject<Item> EDAPHOSAURUS_SPAWN_EGG = spawnEgg("edaphosaurus", UPEntities.EDAPHOSAURUS , 0x3d879d, 0xcaf986);
    public static final RegistryObject<Item> ENCRUSTED_SPAWN_EGG = spawnEgg("encrusted", UPEntities.ENCRUSTED , 0x8c5302, 0xffb12a);
    public static final RegistryObject<Item> ERYON_SPAWN_EGG = spawnEgg("eryon", UPEntities.ERYON , 0x1d2110, 0xe4b423);
    public static final RegistryObject<Item> ESTEMMENOSUCHUS_SPAWN_EGG = spawnEgg("estemmenosuchus", UPEntities.ESTEMMENOSUCHUS , 0x1e202d, 0x7e4995);
    public static final RegistryObject<Item> GIGANTO_SPAWN_EGG = spawnEgg("gigantopithecus", UPEntities.GIGANTOPITHICUS , 0x7c3c23, 0x665f58);
    public static final RegistryObject<Item> HWACHA_SPAWN_EGG = spawnEgg("hwachavenator", UPEntities.HWACHA , 0x2b554a, 0xffed18);
    public static final RegistryObject<Item> HYNERIA_SPAWN_EGG =spawnEgg("hyneria", UPEntities.HYNERIA , 0x1a2121, 0xbd2e2e);
    public static final RegistryObject<Item> HYNERPETON_SPAWN_EGG = spawnEgg("hynerpeton", UPEntities.HYNERPETON , 0x1c1614, 0xb6a339);
    public static final RegistryObject<Item> JAWLESS_FISH_SPAWN_EGG = spawnEgg("jawless_fish", UPEntities.JAWLESS_FISH , 0x438174, 0xcb6415);
    public static final RegistryObject<Item> KAPROSUCUHS_SPAWN_EGG = spawnEgg("kaprosuchus", UPEntities.KAPROSUCHUS , 0x3a2523, 0xf18f4d);
    public static final RegistryObject<Item> KENTRO_SPAWN_EGG = spawnEgg("kentrosaurus", UPEntities.KENTRO , 0x657341, 0x181a14);
    public static final RegistryObject<Item> KIMMER_SPAWN_EGG = spawnEgg("kimmeridgebrachypteraeschnidium", UPEntities.KIMMER , 0xf77efc, 0x44b0ef);
    public static final RegistryObject<Item> LEEDS_SPAWN_EGG = spawnEgg("leedsichthys", UPEntities.LEEDSICHTHYS , 0x505b67, 0xd9dada);
    public static final RegistryObject<Item> MAJUNGA_SPAWN_EGG = spawnEgg("majungasaurus", UPEntities.MAJUNGA , 0x1d600e, 0xacd35d);
    public static final RegistryObject<Item> MAMMOTH_SPAWN_EGG = spawnEgg("mammoth", UPEntities.MAMMOTH , 0x180a08, 0x5e5333);
    public static final RegistryObject<Item> MEGALANIA_SPAWN_EGG = spawnEgg("megalania", UPEntities.MEGALANIA , 0x4f432b, 0x3ae3fd);
    public static final RegistryObject<Item> MEGATH_SPAWN_EGG = spawnEgg("megatherium", UPEntities.MEGATHERIUM , 0x221e1b, 0xc9bfa1);
    public static final RegistryObject<Item> OPHIODON_SPAWN_EGG = spawnEgg("ophiodon_ozymandias", UPEntities.OPHIODON , 0x476272, 0x27374e);
    public static final RegistryObject<Item> PACHY_SPAWN_EGG = spawnEgg("pachycephalosaurus", UPEntities.PACHY , 0x852d2d, 0xf8b209);
    public static final RegistryObject<Item> PALAEOPHIS_SPAWN_EGG = spawnEgg("palaeophis", UPEntities.PALAEOPHIS , 0x211d4b, 0xa1b7c1);
    public static final RegistryObject<Item> PALAEOPHIS_HATCHLING_SPAWN_EGG = spawnEgg("palaeophis_hatchling", UPEntities.BABY_PALAEO , 0x3a4172, 0x8da5b3);
    public static final RegistryObject<Item> PANACANTHOCARIS_SPAWN_EGG = spawnEgg("panacanthocaris", UPEntities.PANACANTHOCARIS , 0x6c7180, 0xefee49);
    public static final RegistryObject<Item> PARACER_SPAWN_EGG = spawnEgg("paraceratherium", UPEntities.PARACERATHERIUM , 0x4a4343, 0xaf9f4b);
    public static final RegistryObject<Item> PROTOSPHYRAENA_SPAWN_EGG = spawnEgg("protosphyraena", UPEntities.PROTOSPHYRAENA , 0x283545, 0x462828);
    public static final RegistryObject<Item> PSITTACO_SPAWN_EGG = spawnEgg("psittacosaurus", UPEntities.PSITTACO , 0xa04f2a, 0xd6b560);
    public static final RegistryObject<Item> PTERODAUSTRO_SPAWN_EGG = spawnEgg("pterodaustro", UPEntities.PTERODAUSTRO , 0xf6887f, 0x1b1419);
    public static final RegistryObject<Item> SCAU_SPAWN_EGG = spawnEgg("scaumenacia", UPEntities.SCAU , 0x687076, 0x376f97);
    public static final RegistryObject<Item> SLUDGE_SPAWN_EGG = spawnEgg("sludge", UPEntities.SLUDGE , 0x0a090a, 0x282627);
    public static final RegistryObject<Item> SMILO_SPAWN_EGG = spawnEgg("smilodon", UPEntities.SMILODON , 0x704333, 0xc8c5c2);
    public static final RegistryObject<Item> STETHA_SPAWN_EGG = spawnEgg("stethacanthus", UPEntities.STETHACANTHUS , 0x853028, 0xffc400);
    public static final RegistryObject<Item> TALPANAS_SPAWN_EGG = spawnEgg("talpanas", UPEntities.TALPANAS , 0x30241a, 0xb2dee0);
    public static final RegistryObject<Item> TARTUOSTEUS_SPAWN_EGG = spawnEgg("tartuosteus", UPEntities.TARTUOSTEUS , 0x508b38, 0x852525);
    public static final RegistryObject<Item> TELECREX_SPAWN_EGG = spawnEgg("telecrex", UPEntities.TELECREX , 0x221d37, 0x770f38);
    public static final RegistryObject<Item> TRICERATOPS_SPAWN_EGG = spawnEgg("triceratops", UPEntities.TRICERATOPS, 0x45452a, 0x9c2f2f);
    public static final RegistryObject<Item> TYRANNOSAURUS_SPAWN_EGG = spawnEgg("tyrannosaurus", UPEntities.TYRANNOSAURUS, 0x3e2025, 0xa23a47);
    public static final RegistryObject<Item> ULUG_SPAWN_EGG = spawnEgg("ulughbegsaurus", UPEntities.ULUG , 0x6e5953, 0x466dc2);
    public static final RegistryObject<Item> VELOCIRAPTOR_SPAWN_EGG = spawnEgg("velociraptor", UPEntities.VELOCIRAPTOR, 0xdfd9c7, 0x4a4242);

    // Eggs
    public static final RegistryObject<Item> TELECREX_EGG = translatedItem("telecrex_egg", () -> new ThrowableEggItem(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> MAJUNGA_HELMET = translatedItem("majungasaurus_helmet", () -> new MajungasaurusHelmetItem(UPArmorMaterial.MAJUNGA, ArmorItem.Type.HELMET, new Item.Properties(), 3.0));
    public static final RegistryObject<Item> AUSTRO_BOOTS = translatedItem("austroraptor_boots", () -> new AustroraptorBootsItem(UPArmorMaterial.AUSTRO, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> CLATHRODICTYON_FAN = item("clathrodictyon_fan", () -> new StandingAndWallBlockItem(UPBlocks.CLATHRODICTYON_FAN.get(), UPBlocks.CLATHRODICTYON_WALL_FAN.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item> DEAD_CLATHRODICTYON_FAN = item("dead_clathrodictyon_fan", () -> new StandingAndWallBlockItem(UPBlocks.DEAD_CLATHRODICTYON_FAN.get(), UPBlocks.DEAD_CLATHRODICTYON_WALL_FAN.get(), new Item.Properties(), Direction.DOWN));

    public static final RegistryObject<Item> RED_FRUIT_SCRAPS = translatedItem("exotic_fruit_scraps", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WHITE_FRUIT_SCRAPS = translatedItem("luxurious_fruit_scraps", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> YELLOW_FRUIT_SCRAPS = translatedItem("redolant_fruit_scraps", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLUE_FRUIT_SCRAPS = translatedItem("salabrious_fruit_scraps", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RED_FRUIT = translatedItem("exotic_fruit", () -> new Item(new Item.Properties().food(UPFood.RED_FRUIT)));
    public static final RegistryObject<Item> WHITE_FRUIT = translatedItem("luxurious_fruit", () -> new Item(new Item.Properties().food(UPFood.WHITE_FRUIT)));
    public static final RegistryObject<Item> YELLOW_FRUIT = translatedItem("redolant_fruit", () -> new Item(new Item.Properties().food(UPFood.YELLOW_FRUIT)));
    public static final RegistryObject<Item> BLUE_FRUIT = translatedItem("salabrious_fruit", () -> new Item(new Item.Properties().food(UPFood.BLUE_FRUIT)));

    public static final RegistryObject<Item> DEFROSTED_FROZEN_FOSSIL = translatedItem("defrosted_meat", () -> new Item(new Item.Properties().food(UPFood.DEFROSTED_FOSSIL)));

    // Boats
    public static final Pair<RegistryObject<Item>, RegistryObject<Item>> DRYO_BOAT = HELPER.createBoatAndChestBoatItem("dryophyllum", UPBlocks.DRYO_PLANKS);
    public static final Pair<RegistryObject<Item>, RegistryObject<Item>> FOXII_BOAT = HELPER.createBoatAndChestBoatItem("foxii", UPBlocks.FOXII_PLANKS);
    public static final Pair<RegistryObject<Item>, RegistryObject<Item>> GINKGO_BOAT = HELPER.createBoatAndChestBoatItem("ginkgo", UPBlocks.GINKGO_PLANKS);

    public static final RegistryObject<Item> CROCARINA = translatedItem("crocarina", () -> new MusicalTameItem(new Item.Properties().stacksTo(1), UPEntities.BARINASUCHUS, UPInstrumentTags.OCARINA_WHISTLE));

    public static final RegistryObject<Item> SHEDSCALE_HELMET = item("shedscale_helmet", () -> new ShedscaleArmorItem(UPArmorMaterial.SHEDSCALE, ArmorItem.Type.HELMET, new Item.Properties(), 0.2));
    public static final RegistryObject<Item> SHEDSCALE_CHESTPLATE = item("shedscale_chestplate", () -> new ShedscaleArmorItem(UPArmorMaterial.SHEDSCALE, ArmorItem.Type.CHESTPLATE, new Item.Properties(), 0.2));
    public static final RegistryObject<Item> SHEDSCALE_LEGGINGS = item("shedscale_leggings", () -> new ShedscaleArmorItem(UPArmorMaterial.SHEDSCALE, ArmorItem.Type.LEGGINGS, new Item.Properties(),  0.2));
    public static final RegistryObject<Item> SHEDSCALE_BOOTS = item("shedscale_boots", () -> new ShedscaleArmorItem(UPArmorMaterial.SHEDSCALE, ArmorItem.Type.BOOTS, new Item.Properties(), 0.2));

    public static final RegistryObject<Item> TYRANTS_CROWN = item("tyrants_crown", () -> new TyrantsCrownItem(UPArmorMaterial.TYRANTS, ArmorItem.Type.HELMET, new Item.Properties(), 4.0));

    public static final RegistryObject<Item> SLOTH_POUCH = translatedItem("sloth_pouch", () -> new SlothPouchItem(UPArmorMaterial.SLOTH_POUCH, ArmorItem.Type.CHESTPLATE, 6000, new Item.Properties()));
    public static final RegistryObject<Item> POUCH = translatedItem("pouch", () -> new DinoPouchItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> TAR_BUCKET =  translatedItem("tar_bucket", () -> new SolidBucketItem(UPBlocks.TAR.get(), SoundEvents.BUCKET_EMPTY_POWDER_SNOW, (new Item.Properties()).stacksTo(1).craftRemainder(Items.BUCKET)));

    public static final RegistryObject<Item> OPALESCENT_PEARL =  translatedItem("opalescent_pearl", () -> new OpalescentPearlItem((new Item.Properties()).stacksTo(16)));
    public static final RegistryObject<Item> OPALESCENT_SHURIKEN =  translatedItem("opalescent_shuriken", () -> new OpalescentShurikenItem((new Item.Properties()).stacksTo(64)));

    public static final RegistryObject<Item> SMILO_FUR = translatedItem("smilodon_fur", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PALAEO_SKIN = translatedItem("shed_palaeophis_skin", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DRYO_NUTS = translatedItem("dryophyllum_nuts", () -> new IncreaseAgeItem(new Item.Properties().food(UPFood.DRYO_NUTS), UPEntityTypeTags.HERBIVORES,10));

    public static final RegistryObject<Item> MAMMOTH_MEATBALL = translatedItem("mammoth_meatball", () -> new UPConsumableItem(new Item.Properties().food(UPFood.MAMMOTH_MEATBALL).craftRemainder(Items.BOWL).stacksTo(16)));

    public static final RegistryObject<Item> ZULOGAE_DISC = ITEMS.register("zulogae_disc", () -> new RecordItem(15, UPSounds.ZULOGAE_DISC, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 157 * 20));
    public static final RegistryObject<Item> ENCASED_DISC = ITEMS.register("encased_disc", () -> new RecordItem(15, UPSounds.ENCASED_DISC, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 2100));
    public static final RegistryObject<Item> OPALESENCE_DISC = ITEMS.register("opalescence_disc", () -> new RecordItem(15, UPSounds.OPALESENCE_DISC, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 2960));
    public static final RegistryObject<Item> TARIFYING_DISC = ITEMS.register("tarifying_disc", () -> new RecordItem(15, UPSounds.TARIFYING_DISC, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 5180));

    public static final RegistryObject<Item> LEEDS_CAVIAR = translatedItem("leedsichthys_caviar", () -> new UPConsumableItem(new Item.Properties().food(UPFood.LEEDS_CAVIAR).craftRemainder(Items.BOWL).stacksTo(16)));
    
    public static final RegistryObject<Item> PSITTACOSAURUS_QUILL = translatedItem("psittacosaurus_quill", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> JAWLESS_FISH_BUCKET = ITEMS.register("jawless_fish_bucket", () -> new UPFishBucketItem(UPEntities.JAWLESS_FISH, () -> Fluids.WATER, Items.BUCKET, false, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> AMBER_IDOL = translatedItem("amber_idol", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PSITTACCO_ARROW = translatedItem("psittacosaurus_arrow", () -> new PsittaccoArrow(new Item.Properties()));

    private static RegistryObject<Item> spawnEgg(String name, RegistryObject type, int primaryColor, int secondaryColor) {
        return translatedItem(name + "_spawn_egg", () -> new ForgeSpawnEggItem(type, primaryColor, secondaryColor, new Item.Properties()));
    }

    private static RegistryObject<Item> dnaItem(String name) {
        return item(name + "_dna_bottle", () -> new Item(new Item.Properties()));
    }

    public static <I extends Item> RegistryObject<I> translatedItem(String name, Supplier<? extends I> supplier) {
        RegistryObject<I> item = ITEMS.register(name, supplier);
        AUTO_TRANSLATE.add(item);
        return item;
    }

    public static <I extends Item> RegistryObject<I> item(String name, Supplier<? extends I> supplier) {
        RegistryObject<I> item = ITEMS.register(name, supplier);
        return item;
    }
}