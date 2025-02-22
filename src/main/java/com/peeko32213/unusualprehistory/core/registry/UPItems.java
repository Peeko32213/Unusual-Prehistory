package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.UPBoatEntity;
import com.peeko32213.unusualprehistory.common.item.*;
import com.peeko32213.unusualprehistory.common.item.armor.AustroraptorBootsItem;
import com.peeko32213.unusualprehistory.common.item.armor.MajungasaurusHelmetItem;
import com.peeko32213.unusualprehistory.common.item.armor.SlothPouchItem;
import com.peeko32213.unusualprehistory.common.item.armor.TyrantsCrownItem;
import com.peeko32213.unusualprehistory.common.item.armor.material.UPArmorMaterial;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ShedscaleArmorItem;
import com.peeko32213.unusualprehistory.common.item.tool.*;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class UPItems {

    public static Item.Properties drinkItem() {
        return new Item.Properties().craftRemainder(UPItems.FLASK.get()).stacksTo(16);
    }

    public static Item.Properties soupItem() {
        return new Item.Properties().craftRemainder(Items.BOWL).stacksTo(8);
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            UnusualPrehistory.MODID);

    public static final RegistryObject<Item> ENCYLOPEDIA = ITEMS.register("encyclopedia",
            () -> new EncyclopediaItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SHELL_SHARD = ITEMS.register("shell_shard",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PALEO_FOSSIL = ITEMS.register("paleo_fossil",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MEZO_FOSSIL = ITEMS.register("mezo_fossil",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PLANT_FOSSIL = ITEMS.register("plant_fossil_item",
            () -> new Item(new Item.Properties()));

//    public static final RegistryObject<Item> TAR_FOSSIL = ITEMS.register("tar_fossil_item",
//            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FROZEN_FOSSIL = ITEMS.register("frozen_fossil_item",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> AMBER_SHARDS = ITEMS.register("amber_shard",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> AMBER_FOSSIL = ITEMS.register("amber_fossil",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> OPAL_CHUNK = ITEMS.register("opal_chunk",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> OPAL_FOSSIL = ITEMS.register("opal_fossil",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MEAT_ON_A_STICK = ITEMS.register("meat_on_a_stick",
            () -> new Item(new Item.Properties().durability(130)));

    public static final RegistryObject<Item> TRIKE_HORN = ITEMS.register("trike_horn",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> REX_SCALE = ITEMS.register("rex_scale",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAPTOR_FEATHERS = ITEMS.register("raptor_feathers",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> VELOCI_SHIELD = ITEMS.register("veloci_shield",
            () -> new VelociraptorShieldItem(new Item.Properties().durability(800).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> TRIKE_SHIELD = ITEMS.register("trike_shield",
            () -> new TriceratopsShieldItem(new Item.Properties().durability(1300).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> MEATY_BUFFET = ITEMS.register("meaty_buffet",
            () -> new ModItemConsumable(new Item.Properties().food(UPFood.MEATY_BUFFET).stacksTo(16).craftRemainder(Items.BOWL)));

    public static final RegistryObject<Item> MAJUNGA_SCUTE = ITEMS.register("majunga_scute",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ADORNED_STAFF = ITEMS.register("adorned_staff",
            () -> new Item(new Item.Properties().durability(100).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> REX_TOOTH = ITEMS.register("rex_tooth",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FLASK = ITEMS.register("flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GROG = ITEMS.register("grog",
            () -> new ModItemDrinkable(drinkItem().food(UPFood.GROG), true, false));

    public static final RegistryObject<Item> CAPTURED_KIMMER_FLASK = ITEMS.register("captured_kimmer_flask",
            () -> new Item(new Item.Properties()));

    //Paleo flasks
    public static final RegistryObject<Item> AMMONITE_FLASK = ITEMS.register("ammonite_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> COTY_FLASK = ITEMS.register("coty_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DUNK_FLASK = ITEMS.register("dunk_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SCAU_FLASK = ITEMS.register("scau_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STETHA_FLASK = ITEMS.register("stetha_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DIPLO_FLASK = ITEMS.register("diplo_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> HYNERIA_FLASK = ITEMS.register("hyneria_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ESTEMMENO_FLASK = ITEMS.register("estemmeno_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> EDAPHO_FLASK = ITEMS.register("edapho_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PTERY_FLASK = ITEMS.register("ptery_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> JAWLESS_FISH_FLASK = ITEMS.register("jawless_fish_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TARTUO_FLASK = ITEMS.register("tartuo_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> HYNERP_FLASK = ITEMS.register("hynerp_flask",
            () -> new Item(new Item.Properties()));

    //Meso flasks
    public static final RegistryObject<Item> ANTARCTO_FLASK = ITEMS.register("antarcto_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ANURO_FLASK = ITEMS.register("anuro_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> AUSTRO_FLASK = ITEMS.register("austro_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BEELZ_FLASK = ITEMS.register("beelz_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BRACHI_FLASK = ITEMS.register("brachi_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ENCRUSTED_FLASK = ITEMS.register("encrusted_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ERYON_FLASK = ITEMS.register("eryon_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> HWACHA_FLASK = ITEMS.register("hwacha_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> KENTRO_FLASK = ITEMS.register("kentro_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> KIMMER_FLASK = ITEMS.register("kimmer_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MAJUNGA_FLASK = ITEMS.register("majunga_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PACHY_FLASK = ITEMS.register("pachy_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PROTOSPHYRAENA_FLASK = ITEMS.register("protosphyraena_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TRIKE_FLASK = ITEMS.register("trike_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> REX_FLASK = ITEMS.register("rex_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ULUGH_FLASK = ITEMS.register("ulugh_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAPTOR_FLASK = ITEMS.register("raptor_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> OVIRAPTOR_FLASK = ITEMS.register("oviraptor_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GLOBIDENS_FLASK = ITEMS.register("globidens_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> XIPHACT_FLASK = ITEMS.register("xiphact_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LONGI_FLASK = ITEMS.register("longisquama_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> KAPRO_FLASK = ITEMS.register("kapro_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BALAUR_FLASK = ITEMS.register("balaur_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LEEDS_FLASK = ITEMS.register("leeds_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PTERODAUSTRO_FLASK = ITEMS.register("pterodaustro_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ARCHELON_FLASK = ITEMS.register("archelon_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PSITTACO_FLASK = ITEMS.register("psittaco_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TANY_FLASK = ITEMS.register("tany_flask",
            () -> new Item(new Item.Properties()));

    //Ceno flasks
    public static final RegistryObject<Item> BARIN_FLASK = ITEMS.register("barin_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GIGANTO_FLASK = ITEMS.register("giganto_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MAMMOTH_FLASK = ITEMS.register("mammoth_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MEGALA_FLASK = ITEMS.register("megala_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MEGATH_FLASK = ITEMS.register("megath_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> OPHIO_FLASK = ITEMS.register("ophio_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PALAEO_FLASK = ITEMS.register("palaeo_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PARACER_FLASK = ITEMS.register("paracer_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SMILO_FLASK = ITEMS.register("smilo_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TALPANAS_FLASK = ITEMS.register("talpanas_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> OTAROCYON_FLASK = ITEMS.register("otarocyon_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PSILO_FLASK = ITEMS.register("psilo_flask",
            () -> new Item(new Item.Properties()));

    //Plant flasks
    public static final RegistryObject<Item> ANOSTYLOSTRAMA_FLASK = ITEMS.register("anostylostroma_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ARCHAEFRUCTUS_FLASK = ITEMS.register("archaefructus_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ARCHAO_FLASK = ITEMS.register("archao_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BENNET_FLASK = ITEMS.register("bennet_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CLATHRODICTYON_FLASK = ITEMS.register("clathrodictyon_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DRYO_FLASK = ITEMS.register("dryo_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FOXXI_FLASK = ITEMS.register("foxxi_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GINKGO_FLASK = ITEMS.register("ginkgo_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> HORSETAIL_FLASK = ITEMS.register("horsetail_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LEEFRUCTUS_FLASK = ITEMS.register("leefructus_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> NELUMBITES_FLASK = ITEMS.register("nelumbites_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> QUEREUXIA_FLASK = ITEMS.register("quereuxia_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAIGUENRAYUN_FLASK = ITEMS.register("raiguenrayun_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SARR_FLASK = ITEMS.register("sarr_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ZULOAGAE_FLASK = ITEMS.register("zuloagae_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GIGANTO_EMBRYO = ITEMS.register("giganto_embryo",
            () -> new AnimalAttacherItem(new Item.Properties().stacksTo(16), UPTags.GIGANTO_EMBRYO_ATTACH_TO, UPEntities.GIGANTOPITHICUS, 1000));

    public static final RegistryObject<Item> MAMMOTH_EMBRYO = ITEMS.register("mammoth_embryo",
            () -> new AnimalAttacherItem(new Item.Properties().stacksTo(16), UPTags.MAMMOTH_EMBRYO_ATTACH_TO, UPEntities.MAMMOTH, 1000));

    public static final RegistryObject<Item> MEGATH_EMBRYO = ITEMS.register("megath_embryo",
            () -> new AnimalAttacherItem(new Item.Properties().stacksTo(16), UPTags.MEGATH_EMBRYO_ATTACH_TO, UPEntities.MEGATHERIUM, 1000));

    public static final RegistryObject<Item> PALAEO_EMBRYO = ITEMS.register("palaeo_embryo",
            () -> new AnimalAttacherItem(new Item.Properties().stacksTo(16), UPTags.PALAEO_EMBRYO_ATTACH_TO, UPEntities.BABY_PALAEO, 1000));

    public static final RegistryObject<Item> PARACER_EMBRYO = ITEMS.register("paracer_embryo",
            () -> new AnimalAttacherItem(new Item.Properties().stacksTo(16), UPTags.PARACER_EMBRYO_ATTACH_TO, UPEntities.PARACERATHERIUM, 1000));

    public static final RegistryObject<Item> SMILODON_EMBRYO = ITEMS.register("smilodon_embryo",
            () -> new AnimalAttacherItem(new Item.Properties().stacksTo(16), UPTags.SMILODON_EMBRYO_ATTACH_TO, UPEntities.SMILODON, 1000));

    public static final RegistryObject<Item> OTAROCYON_EMBRYO = ITEMS.register("otarocyon_embryo",
            () -> new AnimalAttacherItem(new Item.Properties().stacksTo(16), UPTags.OTAROCYON_EMBRYO_ATTACH_TO, UPEntities.OTAROCYON, 1000));

    public static final RegistryObject<Item> ORGANIC_OOZE = ITEMS.register("organic_ooze",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FROG_SALIVA = ITEMS.register("frog_saliva",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> AUSTRO_FEATHER = ITEMS.register("austro_feather",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ENCRUSTED_ORGAN = ITEMS.register("encrusted_organ",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ANTARCTO_PLATE = ITEMS.register("antarcto_plate",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WARPICK = ITEMS.register("warpick",
            () -> new WarpickItem(UPItemTiers.SHELL, 3, -2.8F));

    public static final RegistryObject<Item> PRIMAL_MACUAHUITL = ITEMS.register("primal_macuahuitl",
            () -> new PrimalMacuahuitlItem(UPItemTiers.SHELL, 6, -1.8F));

    public static final RegistryObject<Item> HANDMADE_SPEAR = ITEMS.register("handmade_spear",
            () -> new HandmadeSpearItem(UPItemTiers.HANDMADE, 0, -2.4F));

    public static final RegistryObject<Item> HANDMADE_BATTLEAXE = ITEMS.register("handmade_battleaxe",
            () -> new HandmadeBattleaxeItem(UPItemTiers.HANDMADE, 5, -1.9F));

    public static final RegistryObject<Item> HANDMADE_CLUB = ITEMS.register("handmade_club",
            () -> new HandmadeClubItem(UPItemTiers.HANDMADE, 8, -2.3F));

    public static final RegistryObject<Item> RAW_STETHA = ITEMS.register("raw_stetha",
            () -> new Item(new Item.Properties().food(UPFood.RAW_STETHA)));

    public static final RegistryObject<Item> COOKED_STETHA = ITEMS.register("cooked_stetha",
            () -> new Item(new Item.Properties().food(UPFood.COOKED_STETHA)));

    public static final RegistryObject<Item> RAW_COTY = ITEMS.register("raw_coty",
            () -> new Item(new Item.Properties().food(UPFood.RAW_COTY)));

    public static final RegistryObject<Item> COOKED_COTY = ITEMS.register("cooked_coty",
            () -> new Item(new Item.Properties().food(UPFood.COOKED_COTY)));

    public static final RegistryObject<Item> RAW_SCAU = ITEMS.register("raw_scau",
            () -> new Item(new Item.Properties().food(UPFood.RAW_SCAU)));

    public static final RegistryObject<Item> COOKED_SCAU = ITEMS.register("cooked_scau",
            () -> new Item(new Item.Properties().food(UPFood.COOKED_SCAU)));

    public static final RegistryObject<Item> GINKGO_FRUIT = ITEMS.register("ginkgo_fruit",
            () -> new Item(new Item.Properties().food(UPFood.GINKGO_FRUIT)));

    public static final RegistryObject<Item> RAW_GINKGO_SEEDS = ITEMS.register("raw_ginkgo_seeds",
            () -> new Item(new Item.Properties().food(UPFood.RAW_GINKGO_SEEDS)));

    public static final RegistryObject<Item> COOKED_GINKGO_SEEDS = ITEMS.register("cooked_ginkgo_seeds",
            () -> new Item(new Item.Properties().food(UPFood.COOKED_GINKGO_SEEDS)));

    public static final RegistryObject<Item> GOLDEN_SCAU = ITEMS.register("golden_scau",
            () -> new Item(new Item.Properties().food(UPFood.GOLDEN_SCAU)));

    public static final RegistryObject<Item> RAW_AUSTRO = ITEMS.register("raw_austro",
            () -> new Item(new Item.Properties().food(UPFood.RAW_AUSTRO)));

    public static final RegistryObject<Item> COOKED_AUSTRO = ITEMS.register("cooked_austro",
            () -> new Item(new Item.Properties().food(UPFood.COOKED_AUSTRO)));

    public static final RegistryObject<Item> AMBER_GUMMY = ITEMS.register("amber_gummy",
            () -> new AmberGummyItem(new Item.Properties().food(UPFood.AMBER_GUMMY)));

    public static final RegistryObject<Item> STETHA_BUCKET = ITEMS.register("stetha_bucket",
            () -> new UPFishBucketItem(UPEntities.STETHACANTHUS, () -> Fluids.WATER, Items.BUCKET, false,
                    new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> AMMON_BUCKET = ITEMS.register("ammon_bucket",
            () -> new UPFishBucketItem(UPEntities.AMMON, () -> Fluids.WATER, Items.BUCKET, false,
                    new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> BEELZE_BUCKET = ITEMS.register("beelze_bucket",
            () -> new UPFishBucketItem(UPEntities.BEELZE_TADPOLE, () -> Fluids.WATER, Items.BUCKET, false,
                    new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> DUNK_BUCKET = ITEMS.register("dunk_bucket",
            () -> new UPFishBucketItem(UPEntities.BABY_DUNK, () -> Fluids.WATER, Items.BUCKET, false,
                    new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SCAU_BUCKET = ITEMS.register("scau_bucket",
            () -> new UPFishBucketItem(UPEntities.SCAU, () -> Fluids.WATER, Items.BUCKET, false,
                    new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> PALAEO_BUCKET = ITEMS.register("palaeo_bucket",
            () -> new UPFishBucketItem(UPEntities.BABY_PALAEO, () -> Fluids.WATER, Items.BUCKET, false, new Item.Properties().stacksTo(1)));

    // Paleo spawn eggs
    public static final RegistryObject<ForgeSpawnEggItem> AMMON_EGG = registerSpawnEggs("ammon_spawn_egg", UPEntities.AMMON , 0x402018, 0x99895c);
    public static final RegistryObject<ForgeSpawnEggItem> COTY_EGG = registerSpawnEggs("coty_spawn_egg", UPEntities.COTY , 0xb56534, 0xdfd7ad);
    public static final RegistryObject<ForgeSpawnEggItem> DIPLOCAULUS_EGG = registerSpawnEggs("diplocaulus_spawn_egg", UPEntities.DIPLOCAULUS , 0x1b0e05, 0xf1fa59);
    public static final RegistryObject<ForgeSpawnEggItem> DUNK_EGG = registerSpawnEggs("dunk_spawn_egg", UPEntities.DUNK , 0x611f0d, 0xb07b42);
    public static final RegistryObject<ForgeSpawnEggItem> HYNERIA_EGG =registerSpawnEggs("hyneria_spawn_egg", UPEntities.HYNERIA , 0x1a2121, 0xbd2e2e);
    public static final RegistryObject<ForgeSpawnEggItem> HYNERPETON_EGG = registerSpawnEggs("hynerpeton_spawn_egg", UPEntities.HYNERPETON , 0x1c1614, 0xb6a339);
    public static final RegistryObject<ForgeSpawnEggItem> JAWLESS_FISH_EGG = registerSpawnEggs("jawless_fish_spawn_egg", UPEntities.JAWLESS_FISH , 0x9a32af, 0xe7cd39);
    public static final RegistryObject<ForgeSpawnEggItem> SCAU_EGG = registerSpawnEggs("scau_spawn_egg", UPEntities.SCAU , 0x909da1, 0x5094c1);
    public static final RegistryObject<ForgeSpawnEggItem> STETHA_EGG = registerSpawnEggs("stetha_spawn_egg", UPEntities.STETHACANTHUS , 0x754123, 0xb59a6e);
    public static final RegistryObject<ForgeSpawnEggItem> TARTUOSTEUS_EGG = registerSpawnEggs("tartuosteus_spawn_egg", UPEntities.TARTUOSTEUS , 0x12352f, 0x73a658);

    // Meso spawn eggs
    public static final RegistryObject<ForgeSpawnEggItem> ANTARCO_EGG = registerSpawnEggs("antarcto_spawn_egg", UPEntities.ANTARCO , 0x39332d, 0xe6e1d4);
    public static final RegistryObject<ForgeSpawnEggItem> ANURO_EGG = registerSpawnEggs("anuro_spawn_egg", UPEntities.ANURO , 0x7d7968, 0xf4dd48);
    public static final RegistryObject<ForgeSpawnEggItem> ARCHELON_EGG = registerSpawnEggs("archelon_spawn_egg", UPEntities.ARCHELON , 0x618b89, 0x171923);
    public static final RegistryObject<ForgeSpawnEggItem> AUSTRO_EGG = registerSpawnEggs("austroraptor_spawn_egg", UPEntities.AUSTRO , 0xfff9f7, 0xc72727);
    public static final RegistryObject<ForgeSpawnEggItem> BALAUR_EGG = registerSpawnEggs("balaur_spawn_egg", UPEntities.BALAUR , 0x1d1d28, 0x3b8a34);
    public static final RegistryObject<ForgeSpawnEggItem> BEELZ_EGG = registerSpawnEggs("beelz_spawn_egg", UPEntities.BEELZ , 0x443f13, 0xa5db90);
    public static final RegistryObject<ForgeSpawnEggItem> BEELZ_TADPOLE_EGG = registerSpawnEggs("beelz_tadpole_spawn_egg", UPEntities.BEELZE_TADPOLE , 0x71b460, 0xd2bd7e);
    public static final RegistryObject<ForgeSpawnEggItem> BRACHI_EGG =registerSpawnEggs("brachi_spawn_egg", UPEntities.BRACHI , 0x5e6f9a, 0xc7e1e4);
    public static final RegistryObject<ForgeSpawnEggItem> ERYON_EGG = registerSpawnEggs("eryon_spawn_egg", UPEntities.ERYON , 0x1d2110, 0xe4b423);
    public static final RegistryObject<ForgeSpawnEggItem> HWACHA_EGG = registerSpawnEggs("hwacha_spawn_egg", UPEntities.HWACHA , 0x14191b, 0xf4f435);
    public static final RegistryObject<ForgeSpawnEggItem> KAPROSUCUHS_EGG = registerSpawnEggs("kapro_spawn_egg", UPEntities.KAPROSUCHUS , 0x322f2c, 0xf6f050);
    public static final RegistryObject<ForgeSpawnEggItem> KENTRO_EGG = registerSpawnEggs("kentro_spawn_egg", UPEntities.KENTRO , 0x122407, 0xddcca4);
    public static final RegistryObject<ForgeSpawnEggItem> KIMMER_EGG = registerSpawnEggs("kimmer_spawn_egg", UPEntities.KIMMER , 0x0a1206, 0xd2aa25);
    public static final RegistryObject<ForgeSpawnEggItem> LEEDS_EGG = registerSpawnEggs("leedsichthys_spawn_egg", UPEntities.LEEDSICHTHYS , 0x505b67, 0xd9dada);
    public static final RegistryObject<ForgeSpawnEggItem> LONGISQUAMA_EGG = registerSpawnEggs("longisquama_spawn_egg", UPEntities.LONGISQUAMA , 0x5e4fa7, 0x7edbdd);
    public static final RegistryObject<ForgeSpawnEggItem> MAJUNGA_EGG = registerSpawnEggs("majunga_spawn_egg", UPEntities.MAJUNGA , 0x1d600e, 0xacd35d);
    public static final RegistryObject<ForgeSpawnEggItem> PACHY_EGG = registerSpawnEggs("pachy_spawn_egg", UPEntities.PACHY , 0x282d3d, 0x5d7170);
    public static final RegistryObject<ForgeSpawnEggItem> PROTOSPHYRAENA_EGG = registerSpawnEggs("protosphyraena_spawn_egg", UPEntities.PROTOSPHYRAENA , 0x283545, 0x462828);
    public static final RegistryObject<ForgeSpawnEggItem> PSITTACO_EGG = registerSpawnEggs("psittacosaurus_spawn_egg", UPEntities.PSITTACO , 0xa04f2a, 0xd6b560);
    public static final RegistryObject<ForgeSpawnEggItem> PTERODAUSTRO_EGG =registerSpawnEggs("pterodaustro_spawn_egg", UPEntities.PTERODAUSTRO , 0xc93660, 0xfff3fd);
    public static final RegistryObject<ForgeSpawnEggItem> TANY_EGG = registerSpawnEggs("tanystropheus_spawn_egg", UPEntities.TANY , 0x08090d, 0xf2f6f8);
    public static final RegistryObject<ForgeSpawnEggItem> TRIKE_EGG =registerSpawnEggs("trike_spawn_egg", UPEntities.TRIKE , 0x47302c, 0xffcb23);
    public static final RegistryObject<ForgeSpawnEggItem> REX_EGG = registerSpawnEggs("rex_spawn_egg", UPEntities.REX , 0x31171c, 0xb96a53);
    public static final RegistryObject<ForgeSpawnEggItem> ULUG_EGG = registerSpawnEggs("ulugh_spawn_egg", UPEntities.ULUG , 0x3a2424, 0xdbd8ce);
    public static final RegistryObject<ForgeSpawnEggItem> VELOCI_EGG = registerSpawnEggs("veloci_spawn_egg", UPEntities.VELOCI , 0x774228, 0xcb09464);
    public static final RegistryObject<ForgeSpawnEggItem> XIPH_EGG = registerSpawnEggs("xiphactinus_spawn_egg", UPEntities.XIPH , 0x9eacbe, 0x21262a);

    // Ceno spawn eggs
    public static final RegistryObject<ForgeSpawnEggItem> BARINA_EGG = registerSpawnEggs("barinasuchus_spawn_egg", UPEntities.BARINASUCHUS , 0x0e0b03, 0xbea61e);
    public static final RegistryObject<ForgeSpawnEggItem> GIGANTO_EGG = registerSpawnEggs("giganto_spawn_egg", UPEntities.GIGANTOPITHICUS , 0x7c3c23, 0x665f58);
    public static final RegistryObject<ForgeSpawnEggItem> MAMMOTH_EGG = registerSpawnEggs("mammoth_spawn_egg", UPEntities.MAMMOTH , 0x180a08, 0x5e5333);
    public static final RegistryObject<ForgeSpawnEggItem> MEGALANIA_EGG = registerSpawnEggs("megalania_spawn_egg", UPEntities.MEGALANIA , 0x2e2319, 0x96874b);
    public static final RegistryObject<ForgeSpawnEggItem> MEGATH_EGG = registerSpawnEggs("megatherium_spawn_egg", UPEntities.MEGATHERIUM , 0x65352a, 0x9a7c51);
    public static final RegistryObject<ForgeSpawnEggItem> OPHIODON_EGG = registerSpawnEggs("ophiodon_spawn_egg", UPEntities.OPHIODON , 0x5b8486, 0xe141b34);
    public static final RegistryObject<ForgeSpawnEggItem> OTAROCYON_EGG = registerSpawnEggs("otarocyon_spawn_egg", UPEntities.OTAROCYON , 0x281b25, 0x681523);
    public static final RegistryObject<ForgeSpawnEggItem> PALAEOPHIS_EGG =registerSpawnEggs("palaeophis_spawn_egg", UPEntities.PALAEOPHIS , 0x211d4b, 0xa1b7c1);
    public static final RegistryObject<ForgeSpawnEggItem> PALAEOPHIS_BABY_EGG =registerSpawnEggs("palaeophis_hatchling_spawn_egg", UPEntities.BABY_PALAEO , 0x3a4172, 0x8da5b3);
    public static final RegistryObject<ForgeSpawnEggItem> PARACER_EGG = registerSpawnEggs("paraceratherium_spawn_egg", UPEntities.PARACERATHERIUM , 0x564642, 0x9a9490);
    public static final RegistryObject<ForgeSpawnEggItem> PSILOPTERUS_EGG = registerSpawnEggs("psilopterus_spawn_egg", UPEntities.PSILOPTERUS , 0x3f3428, 0xe4ceb0);
    public static final RegistryObject<ForgeSpawnEggItem> SMILO_EGG = registerSpawnEggs("smilodon_spawn_egg", UPEntities.SMILODON , 0xd5ced4, 0x605a69);
    public static final RegistryObject<ForgeSpawnEggItem> TALPANAS_EGG = registerSpawnEggs("talpanas_spawn_egg", UPEntities.TALPANAS , 0x1d1311, 0x3c4849);


    // Misc spawn eggs
    public static final RegistryObject<ForgeSpawnEggItem> ENCRUSTED_EGG = registerSpawnEggs("encrusted_spawn_egg", UPEntities.ENCRUSTED , 0x482300, 0xffc656);
    public static final RegistryObject<ForgeSpawnEggItem> SLUDGE_EGG = registerSpawnEggs("sludge_spawn_egg", UPEntities.SLUDGE , 0x0a090a, 0x282627);

    public static final RegistryObject<Item> MAJUNGA_HELMET = ITEMS.register("majunga_helmet",
            () -> new MajungasaurusHelmetItem(UPArmorMaterial.MAJUNGA, ArmorItem.Type.HELMET,
                    new Item.Properties()));

    public static final RegistryObject<Item> AUSTRO_BOOTS = ITEMS.register("austro_boots",
            () -> new AustroraptorBootsItem(UPArmorMaterial.AUSTRO, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    public static final RegistryObject<Item> CLATHRODICTYON_FAN = ITEMS.register("clathrodictyon_fan",
            () -> new StandingAndWallBlockItem(UPBlocks.CLATHRODICTYON_FAN.get(), UPBlocks.CLATHRODICTYON_WALL_FAN.get(), new Item.Properties(), Direction.DOWN));

    public static final RegistryObject<Item> DEAD_CLATHRODICTYON_FAN = ITEMS.register("dead_clathrodictyon_fan",
            () -> new StandingAndWallBlockItem(UPBlocks.DEAD_CLATHRODICTYON_FAN.get(), UPBlocks.DEAD_CLATHRODICTYON_WALL_FAN.get(),
                    new Item.Properties(), Direction.DOWN));

    public static final RegistryObject<Item> RED_FRUIT_SCRAPS = ITEMS.register("red_fruit_scraps",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WHITE_FRUIT_SCRAPS = ITEMS.register("white_fruit_scraps",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> YELLOW_FRUIT_SCRAPS = ITEMS.register("yellow_fruit_scraps",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BLUE_FRUIT_SCRAPS = ITEMS.register("blue_fruit_scraps",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RED_FRUIT = ITEMS.register("red_fruit",
            () -> new Item(new Item.Properties().food(UPFood.RED_FRUIT)));

    public static final RegistryObject<Item> WHITE_FRUIT = ITEMS.register("white_fruit",
            () -> new Item(new Item.Properties().food(UPFood.WHITE_FRUIT)));

    public static final RegistryObject<Item> YELLOW_FRUIT = ITEMS.register("yellow_fruit",
            () -> new Item(new Item.Properties().food(UPFood.YELLOW_FRUIT)));

    public static final RegistryObject<Item> BLUE_FRUIT = ITEMS.register("blue_fruit",
            () -> new Item(new Item.Properties().food(UPFood.BLUE_FRUIT)));

    public static final RegistryObject<Item> RAW_OPHIODON = ITEMS.register("raw_ophiodon",
            () -> new Item(new Item.Properties().food(UPFood.RAW_OPHIODON)));
    public static final RegistryObject<Item> COOKED_OPHIODON = ITEMS.register("cooked_ophiodon",
            () -> new Item(new Item.Properties().food(UPFood.COOKED_OPHIODON)));

    public static final RegistryObject<Item> DEFROSTED_FROZEN_FOSSIL = ITEMS.register("defrosted_frozen_fossil",
            () -> new Item(new Item.Properties().food(UPFood.DEFROSTED_FOSSIL)));

    public static final RegistryObject<Item> GINKGO_SIGN = ITEMS.register("ginkgo_sign", () -> new SignItem(new Item.Properties().stacksTo(16), UPBlocks.GINKGO_SIGN.get(), UPBlocks.GINKGO_WALL_SIGN.get()));
    public static final RegistryObject<Item> GINKGO_HANGING_SIGN = ITEMS.register("ginkgo_hanging_sign", () -> new HangingSignItem(UPBlocks.GINKGO_HANGING_SIGN.get(), UPBlocks.GINKGO_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> PETRIFIED_WOOD_SIGN = ITEMS.register("petrified_sign", () -> new SignItem(new Item.Properties().stacksTo(16), UPBlocks.PETRIFIED_WOOD_SIGN.get(), UPBlocks.PETRIFIED_WOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> PETRIFIED_WOOD_HANGING_SIGN = ITEMS.register("petrified_hanging_sign", () -> new HangingSignItem(UPBlocks.PETRIFIED_WOOD_HANGING_SIGN.get(), UPBlocks.PETRIFIED_WOOD_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> FOXII_SIGN = ITEMS.register("foxii_sign", () -> new SignItem(new Item.Properties().stacksTo(16), UPBlocks.FOXII_SIGN.get(), UPBlocks.FOXII_WALL_SIGN.get()));
    public static final RegistryObject<Item> FOXII_HANGING_SIGN = ITEMS.register("foxii_hanging_sign", () -> new HangingSignItem(UPBlocks.FOXII_HANGING_SIGN.get(), UPBlocks.FOXII_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> DRYO_SIGN = ITEMS.register("dryo_sign", () -> new SignItem(new Item.Properties().stacksTo(16), UPBlocks.DRYO_SIGN.get(), UPBlocks.DRYO_WALL_SIGN.get()));
    public static final RegistryObject<Item> DRYO_HANGING_SIGN = ITEMS.register("dryo_hanging_sign", () -> new HangingSignItem(UPBlocks.DRYO_HANGING_SIGN.get(), UPBlocks.DRYO_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> ZULOAGAE_SIGN = ITEMS.register("zuloagae_sign", () -> new SignItem(new Item.Properties().stacksTo(16), UPBlocks.ZULOAGAE_SIGN.get(), UPBlocks.ZULOAGAE_WALL_SIGN.get()));
    public static final RegistryObject<Item> ZULOAGAE_HANGING_SIGN = ITEMS.register("zuloagae_hanging_sign", () -> new HangingSignItem(UPBlocks.ZULOAGAE_HANGING_SIGN.get(), UPBlocks.ZULOAGAE_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> DRYO_BOAT = ITEMS.register("dryophyllum_boat", () -> new UPBoatItem(false, UPBoatEntity.BoatType.DRYO, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FOXXI_BOAT = ITEMS.register("foxii_boat", () -> new UPBoatItem(false, UPBoatEntity.BoatType.FOXXI, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GINKGO_BOAT = ITEMS.register("ginkgo_boat", () -> new UPBoatItem(false, UPBoatEntity.BoatType.GINKGO, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> DRYO_CHEST_BOAT = ITEMS.register("dryophyllum_chest_boat", () -> new UPBoatItem(true, UPBoatEntity.BoatType.DRYO, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FOXXI_CHEST_BOAT = ITEMS.register("foxii_chest_boat", () -> new UPBoatItem(true, UPBoatEntity.BoatType.FOXXI, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GINKGO_CHEST_BOAT = ITEMS.register("ginkgo_chest_boat", () -> new UPBoatItem(true, UPBoatEntity.BoatType.GINKGO, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> BARINA_WHISTLE = ITEMS.register("barina_whistle",
            () -> new MusicalTameItem(new Item.Properties().stacksTo(1), UPEntities.BARINASUCHUS, UPTags.OCARINA_WHISTLE));

    public static final RegistryObject<Item> SHEDSCALE_HELMET = ITEMS.register("shedscale_helmet",
            () -> new ShedscaleArmorItem(UPArmorMaterial.SHEDSCALE, ArmorItem.Type.HELMET,
                    new Item.Properties(), 0.4D));

    public static final RegistryObject<Item> SHEDSCALE_CHESTPLATE = ITEMS.register("shedscale_chestplate",
            () -> new ShedscaleArmorItem(UPArmorMaterial.SHEDSCALE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties(), 1D));

    public static final RegistryObject<Item> SHEDSCALE_LEGGINGS = ITEMS.register("shedscale_leggings",
            () -> new ShedscaleArmorItem(UPArmorMaterial.SHEDSCALE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties(),  0.6D));

    public static final RegistryObject<Item> SHEDSCALE_BOOTS = ITEMS.register("shedscale_boots",
            () -> new ShedscaleArmorItem(UPArmorMaterial.SHEDSCALE, ArmorItem.Type.BOOTS,
                    new Item.Properties(), 0.4D));

    public static final RegistryObject<Item> TYRANTS_CROWN = ITEMS.register("tyrants_crown",
            () -> new TyrantsCrownItem(UPArmorMaterial.TYRANTS, ArmorItem.Type.HELMET,
                    new Item.Properties()));

    public static final RegistryObject<Item> SLOTH_POUCH_ARMOR = ITEMS.register("sloth_pouch_armor",
            () -> new SlothPouchItem(UPArmorMaterial.SLOTH_POUCH, ArmorItem.Type.CHESTPLATE, 6000,
                    new Item.Properties()));

    public static final RegistryObject<Item> DINO_POUCH = ITEMS.register("dino_pouch",
            () -> new DinoPouchItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> TAR_BUCKET =  ITEMS.register("tar_bucket",
            () -> new SolidBucketItem(UPBlocks.TAR.get(), SoundEvents.BUCKET_EMPTY_POWDER_SNOW, (new Item.Properties()).stacksTo(1).craftRemainder(Items.BUCKET)));

    public static final RegistryObject<Item> OPALESCENT_PEARL =  ITEMS.register("opalescent_pearl", () -> new OpalescentPearlItem((new Item.Properties()).stacksTo(16)));

    public static final RegistryObject<Item> OPALESCENT_SHURIKEN =  ITEMS.register("opalescent_shuriken", () -> new OpalescentShurikenItem((new Item.Properties()).stacksTo(16)));

    public static final RegistryObject<Item> SMILO_FUR = ITEMS.register("smilo_fur",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PALAEO_SKIN = ITEMS.register("palaeo_skin",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> INSULATOR = ITEMS.register("insulator",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DRYO_NUTS = ITEMS.register("dryo_nuts",
            () -> new IncreaseAgeItem(new Item.Properties().food(UPFood.DRYO_NUTS), UPTags.HERBIVORES,10));

    public static final RegistryObject<Item> RAW_MAMMOTH = ITEMS.register("raw_mammoth",
            () -> new Item(new Item.Properties().food(UPFood.RAW_MAMMOTH)));

    public static final RegistryObject<Item> COOKED_MAMMOTH = ITEMS.register("cooked_mammoth",
            () -> new Item(new Item.Properties().food(UPFood.COOKED_MAMMOTH)));

    public static final RegistryObject<Item> MAMMOTH_MEATBALL = ITEMS.register("mammoth_meatball",
            () -> new ModItemDrinkable(soupItem().food(UPFood.MAMMOTH_MEATBALL).stacksTo(16), true, false));

    public static final RegistryObject<Item> ZULOGAE_DISC = ITEMS.register("zulogae_disc",
            () -> new RecordItem(15, UPSounds.ZULOGAE_DISC, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 157 * 20));

    public static final RegistryObject<Item> ENCASED_DISC = ITEMS.register("encased_disc",
            () -> new RecordItem(15, UPSounds.ENCASED_DISC, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 157 * 20));

    public static final RegistryObject<Item> LEEDS_CAVIAR = ITEMS.register("leedsichthys_caviar",
            () -> new ModItemConsumable(new Item.Properties().food(UPFood.LEEDS_CAVIAR).craftRemainder(Items.BOWL)));

    public static final RegistryObject<Item> LEEDS_SLICE = ITEMS.register("leedsichthys_slice",
            () -> new Item(new Item.Properties().food(UPFood.LEEDS_SLICE)));

    public static final RegistryObject<Item> PSITTACOSAURUS_QUILL = ITEMS.register("psittacosaurus_quill",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> YIXIAN_RAMPAGE_FLASK = ITEMS.register("yixian_rampage_flask", () -> new ModItemDrinkable(drinkItem().stacksTo(16).food(UPFood.YIXIAN_SALIVA), true, false));
    public static final RegistryObject<Item> DORMANT_RAMPAGE = ITEMS.register("dormant_rampage", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> JAWLESS_FISH_BUCKET = ITEMS.register("jawless_fish_bucket",
            () -> new UPFishBucketItem(UPEntities.JAWLESS_FISH, () -> Fluids.WATER, Items.BUCKET, false,
                    new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> JARATE =  ITEMS.register("jarate", () -> new JarateItem((new Item.Properties()).stacksTo(16)));

    public static final RegistryObject<Item> QUILL_REMEDY = ITEMS.register("quill_remedy", () -> new RampageRemedyItem(new Item.Properties()));

    public static final RegistryObject<Item> RAW_JAWLESS_FISH = ITEMS.register("raw_jawless_fish",
            () -> new Item(new Item.Properties().food(UPFood.RAW_JAWLESS_FISH)));

    public static final RegistryObject<Item> COOKED_JAWLESS_FISH = ITEMS.register("cooked_jawless_fish",
            () -> new Item(new Item.Properties().food(UPFood.COOKED_FURCA)));

    public static final RegistryObject<Item> RAW_TARTU = ITEMS.register("raw_tartuosteus",
            () -> new Item(new Item.Properties().food(UPFood.RAW_TARTU)));

    public static final RegistryObject<Item> COOKED_TARTU = ITEMS.register("cooked_tartuosteus",
            () -> new Item(new Item.Properties().food(UPFood.COOKED_TARTU)));

    public static final RegistryObject<Item> RAW_DUNK = ITEMS.register("raw_dunkleosteus",
            () -> new Item(new Item.Properties().food(UPFood.RAW_DUNK)));

    public static final RegistryObject<Item> COOKED_DUNK = ITEMS.register("cooked_dunkleosteus",
            () -> new Item(new Item.Properties().food(UPFood.COOKED_DUNK)));

    public static final RegistryObject<Item> AMBER_IDOL = ITEMS.register("amber_idol",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PSITTACCO_ARROW = ITEMS.register("psittaco_arrow", () -> new PsittaccoArrow(new Item.Properties()));

    private static RegistryObject<ForgeSpawnEggItem> registerSpawnEggs(String name, Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor) {
        return ITEMS.register(name, () -> new ForgeSpawnEggItem(type, backgroundColor, highlightColor,new Item.Properties()));
    }
}

//    public static final RegistryObject<Item> PTERYDACTYLUS_FLASK = ITEMS.register("pterydactylus_flask",
//            () -> new Item(new Item.Properties()));
//    public static final RegistryObject<Item> ERETMORPHIS_FLASK = ITEMS.register("eretmorhipis_flask",
//            () -> new Item(new Item.Properties()));
//    public static final RegistryObject<Item> ARTHROPLEURA_FLASK = ITEMS.register("arthropleura_flask",
//            () -> new Item(new Item.Properties()));
//    public static final RegistryObject<Item> SCUTO_FLASK = ITEMS.register("scuto_flask",
//            () -> new Item(new Item.Properties()));
//    public static final RegistryObject<Item> ENCHODUS_FLASK = ITEMS.register("enchodus_flask",
//            () -> new Item(new Item.Properties()));
//    public static final RegistryObject<Item> IGUANODON_FLASK = ITEMS.register("iguanodon_flask",
//            () -> new Item(new Item.Properties()));







