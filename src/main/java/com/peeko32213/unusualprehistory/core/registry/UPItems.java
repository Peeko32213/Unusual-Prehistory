package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.*;
import com.peeko32213.unusualprehistory.common.item.armor.ItemAustroBoots;
import com.peeko32213.unusualprehistory.common.item.armor.ItemMajungaHelmet;
import com.peeko32213.unusualprehistory.common.item.armor.ItemSlothPouchArmor;
import com.peeko32213.unusualprehistory.common.item.armor.ItemTyrantsCrown;
import com.peeko32213.unusualprehistory.common.item.armor.material.UPArmorMaterial;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemShedscaleArmor;
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
    private UPItems() {
    }

    public static Item.Properties drinkItem() {
        return new Item.Properties().craftRemainder(UPItems.FLASK.get()).stacksTo(16);
    }

    public static Item.Properties soupItem() {
        return new Item.Properties().craftRemainder(Items.BOWL).stacksTo(8);
    }


    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            UnusualPrehistory.MODID);

    public static final RegistryObject<Item> ENCYLOPEDIA = ITEMS.register("encyclopedia",
            () -> new ItemEncyclopedia(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> AMMONITE_SHELL_ICON = ITEMS.register("ammonite_shell_icon",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SHELL_SHARD = ITEMS.register("shell_shard",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PALEO_FOSSIL = ITEMS.register("paleo_fossil",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MEZO_FOSSIL = ITEMS.register("mezo_fossil",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PLANT_FOSSIL = ITEMS.register("plant_fossil_item",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TAR_FOSSIL = ITEMS.register("tar_fossil_item",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FROZEN_FOSSIL = ITEMS.register("frozen_fossil_item",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> OPAL_CHUNK = ITEMS.register("opal_chunk",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> OPAL_FOSSIL = ITEMS.register("opal_fossil",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FLASK = ITEMS.register("flask",
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
            () -> new ItemVelociShield(new Item.Properties().durability(800).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> TRIKE_SHIELD = ITEMS.register("trike_shield",
            () -> new ItemTrikeShield(new Item.Properties().durability(1300).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> GROG = ITEMS.register("grog",
            () -> new ModItemDrinkable(drinkItem().food(ModFood.GROG), true, false));

    public static final RegistryObject<Item> MEATY_BUFFET = ITEMS.register("meaty_buffet",
            () -> new ModItemConsumable(new Item.Properties().food(ModFood.MEATY_BUFFET).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> MAJUNGA_SCUTE = ITEMS.register("majunga_scute",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AMBER_SHARDS = ITEMS.register("amber_shard",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> AMBER_FOSSIL = ITEMS.register("amber_fossil",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ADORNED_STAFF = ITEMS.register("adorned_staff",
            () -> new Item(new Item.Properties().durability(100).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> REX_TOOTH = ITEMS.register("rex_tooth",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STETHA_FLASK = ITEMS.register("stetha_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DUNK_FLASK = ITEMS.register("dunk_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> COTY_FLASK = ITEMS.register("coty_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ANURO_FLASK = ITEMS.register("anuro_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BEELZ_FLASK = ITEMS.register("beelz_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MAJUNGA_FLASK = ITEMS.register("majunga_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> AMMONITE_FLASK = ITEMS.register("ammonite_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> HORSETAIL_FLASK = ITEMS.register("horsetail_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TALL_HORSETAIL_FLASK = ITEMS.register("tall_horsetail_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LEEFRUCTUS_FLASK = ITEMS.register("leefructus_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ARCHAO_FLASK = ITEMS.register("archao_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BENNET_FLASK = ITEMS.register("bennet_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GINKGO_FLASK = ITEMS.register("ginkgo_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SARR_FLASK = ITEMS.register("sarr_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SCAU_FLASK = ITEMS.register("scau_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BRACHI_FLASK = ITEMS.register("brachi_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> REX_FLASK = ITEMS.register("rex_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TRIKE_FLASK = ITEMS.register("trike_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAPTOR_FLASK = ITEMS.register("raptor_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PACHY_FLASK = ITEMS.register("pachy_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ENCRUSTED_FLASK = ITEMS.register("encrusted_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ERYON_FLASK = ITEMS.register("eryon_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ANOSTYLOSTRAMA_FLASK = ITEMS.register("anostylostroma_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ARCHAEFRUCTUS_FLASK = ITEMS.register("archaefructus_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CLATHRODICTYON_FLASK = ITEMS.register("clathrodictyon_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> NELUMBITES_FLASK = ITEMS.register("nelumbites_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> QUEREUXIA_FLASK = ITEMS.register("quereuxia_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ULUGH_FLASK = ITEMS.register("ulugh_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> AUSTRO_FLASK = ITEMS.register("austro_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> HWACHA_FLASK = ITEMS.register("hwacha_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ANTARCTO_FLASK = ITEMS.register("antarcto_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> KENTRO_FLASK = ITEMS.register("kentro_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GIGANTO_FLASK = ITEMS.register("giganto_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SMILO_FLASK = ITEMS.register("smilo_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MEGATH_FLASK = ITEMS.register("megath_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PARACER_FLASK = ITEMS.register("paracer_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MAMMOTH_FLASK = ITEMS.register("mammoth_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BARIN_FLASK = ITEMS.register("barin_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PALAEO_FLASK = ITEMS.register("palaeo_flask",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MEGALA_FLASK = ITEMS.register("megala_flask",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TALPANAS_FLASK = ITEMS.register("talpanas_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ZULOAGAE_FLASK = ITEMS.register("zuloagae_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAIGUENRAYUN_FLASK = ITEMS.register("raiguenrayun_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FOXXI_FLASK = ITEMS.register("foxxi_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DRYO_FLASK = ITEMS.register("dryo_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> OTAROCYON_FLASK = ITEMS.register("otarocyon_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LONGI_FLASK = ITEMS.register("longisquama_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FURCA_FLASK = ITEMS.register("furcacauda_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TARTUO_FLASK = ITEMS.register("tartuo_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TANY_FLASK = ITEMS.register("tany_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PSITTACO_FLASK = ITEMS.register("psittaco_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> KAPRO_FLASK = ITEMS.register("kapro_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PSILO_FLASK = ITEMS.register("psilo_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> OPHIO_FLASK = ITEMS.register("ophio_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DIPLO_FLASK = ITEMS.register("diplo_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> HYNERP_FLASK = ITEMS.register("hynerp_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BALAUR_FLASK = ITEMS.register("balaur_flask",
            () -> new Item(new Item.Properties()));
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
            () -> new ItemWarpick(UPItemTiers.SHELL, 3, -2.8F));

    public static final RegistryObject<Item> PRIMAL_MACUAHUITL = ITEMS.register("primal_macuahuitl",
            () -> new ItemPrimalMacuahuitl(UPItemTiers.SHELL, 6, -1.8F));

    public static final RegistryObject<Item> HANDMADE_SPEAR = ITEMS.register("handmade_spear",
            () -> new ItemHandmadeSpear(UPItemTiers.HANDMADE, 3, -1.5F));

    public static final RegistryObject<Item> HANDMADE_BATTLEAXE = ITEMS.register("handmade_battleaxe",
            () -> new ItemHandmadeBattleaxe(UPItemTiers.HANDMADE, 5, -1.9F));

    public static final RegistryObject<Item> HANDMADE_CLUB = ITEMS.register("handmade_club",
            () -> new ItemHandmadeClub(UPItemTiers.HANDMADE, 8, -2.3F));

    public static final RegistryObject<Item> RAW_STETHA = ITEMS.register("raw_stetha",
            () -> new Item(new Item.Properties().food(ModFood.RAW_STETHA)));

    public static final RegistryObject<Item> COOKED_STETHA = ITEMS.register("cooked_stetha",
            () -> new Item(new Item.Properties().food(ModFood.COOKED_STETHA)));

    public static final RegistryObject<Item> RAW_COTY = ITEMS.register("raw_coty",
            () -> new Item(new Item.Properties().food(ModFood.RAW_COTY)));

    public static final RegistryObject<Item> COOKED_COTY = ITEMS.register("cooked_coty",
            () -> new Item(new Item.Properties().food(ModFood.COOKED_COTY)));

    public static final RegistryObject<Item> RAW_SCAU = ITEMS.register("raw_scau",
            () -> new Item(new Item.Properties().food(ModFood.RAW_SCAU)));

    public static final RegistryObject<Item> COOKED_SCAU = ITEMS.register("cooked_scau",
            () -> new Item(new Item.Properties().food(ModFood.COOKED_SCAU)));

    public static final RegistryObject<Item> GINKGO_FRUIT = ITEMS.register("ginkgo_fruit",
            () -> new Item(new Item.Properties().food(ModFood.GINKGO_FRUIT)));

    public static final RegistryObject<Item> RAW_GINKGO_SEEDS = ITEMS.register("raw_ginkgo_seeds",
            () -> new Item(new Item.Properties().food(ModFood.RAW_GINKGO_SEEDS)));

    public static final RegistryObject<Item> COOKED_GINKGO_SEEDS = ITEMS.register("cooked_ginkgo_seeds",
            () -> new Item(new Item.Properties().food(ModFood.COOKED_GINKGO_SEEDS)));
    public static final RegistryObject<Item> GOLDEN_SCAU = ITEMS.register("golden_scau",
            () -> new Item(new Item.Properties().food(ModFood.GOLDEN_SCAU)));
    public static final RegistryObject<Item> RAW_AUSTRO = ITEMS.register("raw_austro",
            () -> new Item(new Item.Properties().food(ModFood.RAW_AUSTRO)));
    public static final RegistryObject<Item> COOKED_AUSTRO = ITEMS.register("cooked_austro",
            () -> new Item(new Item.Properties().food(ModFood.COOKED_AUSTRO)));
    public static final RegistryObject<Item> AMBER_GUMMY = ITEMS.register("amber_gummy",
            () -> new AmberGummyItem(new Item.Properties().food(ModFood.AMBER_GUMMY)));
    public static final RegistryObject<Item> STETHA_BUCKET = ITEMS.register("stetha_bucket",
            () -> new ItemModFishBucket(UPEntities.STETHACANTHUS, () -> Fluids.WATER, Items.BUCKET, false,
                    new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> AMMON_BUCKET = ITEMS.register("ammon_bucket",
            () -> new ItemModFishBucket(UPEntities.AMMON, () -> Fluids.WATER, Items.BUCKET, false,
                    new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> BEELZE_BUCKET = ITEMS.register("beelze_bucket",
            () -> new ItemModFishBucket(UPEntities.BEELZE_TADPOLE, () -> Fluids.WATER, Items.BUCKET, false,
                    new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DUNK_BUCKET = ITEMS.register("dunk_bucket",
            () -> new ItemModFishBucket(UPEntities.BABY_DUNK, () -> Fluids.WATER, Items.BUCKET, false,
                    new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SCAU_BUCKET = ITEMS.register("scau_bucket",
            () -> new ItemModFishBucket(UPEntities.SCAU, () -> Fluids.WATER, Items.BUCKET, false,
                    new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> PALAEO_BUCKET = ITEMS.register("palaeo_bucket",
            () -> new ItemModFishBucket(UPEntities.BABY_PALAEO, () -> Fluids.WATER, Items.BUCKET, false,
                    new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> FURCA_BUCKET = ITEMS.register("furca_bucket",
            () -> new ItemModFishBucket(UPEntities.FURCA, () -> Fluids.WATER, Items.BUCKET, false,
                    new Item.Properties().stacksTo(1)));

    public static final RegistryObject<ForgeSpawnEggItem> STETHA_EGG = registerSpawnEggs("stetha_spawn_egg",
           UPEntities.STETHACANTHUS , 0x754123, 0xb59a6e);

    public static final RegistryObject<ForgeSpawnEggItem> MAJUNGA_EGG = registerSpawnEggs("majunga_spawn_egg",
           UPEntities.MAJUNGA , 0x1d600e, 0xacd35d);

    public static final RegistryObject<ForgeSpawnEggItem> ANURO_EGG = registerSpawnEggs("anuro_spawn_egg",
            UPEntities.ANURO , 0x353121, 0xf4dd48);

    public static final RegistryObject<ForgeSpawnEggItem> BEELZ_EGG = registerSpawnEggs("beelz_spawn_egg",
            UPEntities.BEELZ , 0x443f13, 0xa5db90);

    public static final RegistryObject<ForgeSpawnEggItem> AMMON_EGG = registerSpawnEggs("ammon_spawn_egg",
            UPEntities.AMMON , 0x402018, 0x99895c);

    public static final RegistryObject<ForgeSpawnEggItem> DUNK_EGG = registerSpawnEggs("dunk_spawn_egg",
            UPEntities.DUNK , 0x611f0d, 0xb07b42);

    public static final RegistryObject<ForgeSpawnEggItem> COTY_EGG = registerSpawnEggs("coty_spawn_egg",
            UPEntities.COTY , 0x9d5333, 0xd19c8e);

    public static final RegistryObject<ForgeSpawnEggItem> SCAU_EGG = registerSpawnEggs("scau_spawn_egg",
            UPEntities.SCAU , 0x909da1, 0x5094c1);

    public static final RegistryObject<ForgeSpawnEggItem> TRIKE_EGG =registerSpawnEggs("trike_spawn_egg",
            UPEntities.TRIKE , 0x47302c, 0xffcb23);

    public static final RegistryObject<ForgeSpawnEggItem> PACHY_EGG = registerSpawnEggs("pachy_spawn_egg",
            UPEntities.PACHY , 0x282d3d, 0x5d7170);

    public static final RegistryObject<ForgeSpawnEggItem> BRACHI_EGG =registerSpawnEggs("brachi_spawn_egg",
           UPEntities.BRACHI , 0x5e6f9a, 0xc7e1e4);

    public static final RegistryObject<ForgeSpawnEggItem> VELOCI_EGG = registerSpawnEggs("veloci_spawn_egg",
            UPEntities.VELOCI , 0x774228, 0xcb09464);

    public static final RegistryObject<ForgeSpawnEggItem> REX_EGG = registerSpawnEggs("rex_spawn_egg",
            UPEntities.REX , 0x31171c, 0xb96a53);

    public static final RegistryObject<ForgeSpawnEggItem> ENCRUSTED_EGG = registerSpawnEggs("encrusted_spawn_egg",
            UPEntities.ENCRUSTED , 0x482300, 0xffc656);

    public static final RegistryObject<ForgeSpawnEggItem> ERYON_EGG = registerSpawnEggs("eryon_spawn_egg",
            UPEntities.ERYON , 0x1d2110, 0xe4b423);
    public static final RegistryObject<ForgeSpawnEggItem> AUSTRO_EGG = registerSpawnEggs("austroraptor_spawn_egg",
            UPEntities.AUSTRO , 0xcfb9b4, 0xcf683a);

    public static final RegistryObject<ForgeSpawnEggItem> ANTARCO_EGG = registerSpawnEggs("antarcto_spawn_egg",
            UPEntities.ANTARCO , 0x5a120d, 0xe5ce7a);

    public static final RegistryObject<ForgeSpawnEggItem> ULUG_EGG = registerSpawnEggs("ulugh_spawn_egg",
            UPEntities.ULUG , 0x3a2424, 0xdbd8ce);

    public static final RegistryObject<ForgeSpawnEggItem> KENTRO_EGG = registerSpawnEggs("kentro_spawn_egg",
            UPEntities.KENTRO , 0x122407, 0xddcca4);
    public static final RegistryObject<ForgeSpawnEggItem> HWACHA_EGG = registerSpawnEggs("hwacha_spawn_egg",
            UPEntities.HWACHA , 0x14191b, 0xf4f435);
    public static final RegistryObject<ForgeSpawnEggItem> GIGANTO_EGG = registerSpawnEggs("giganto_spawn_egg",
            UPEntities.GIGANTOPITHICUS , 0x7c3c23, 0x665f58);
    public static final RegistryObject<ForgeSpawnEggItem> TALPANAS_EGG = registerSpawnEggs("talpanas_spawn_egg",
            UPEntities.TALPANAS , 0x1d1311, 0x3c4849);

    public static final RegistryObject<ForgeSpawnEggItem> BARINA_EGG = registerSpawnEggs("barinasuchus_spawn_egg",
            UPEntities.BARINASUCHUS , 0x0e0b03, 0xbea61e);

    public static final RegistryObject<ForgeSpawnEggItem> MEGATH_EGG = registerSpawnEggs("megatherium_spawn_egg",
            UPEntities.MEGATHERIUM , 0x65352a, 0x9a7c51);

    public static final RegistryObject<ForgeSpawnEggItem> SMILO_EGG = registerSpawnEggs("smilodon_spawn_egg",
            UPEntities.SMILODON , 0x64311a, 0x9b6f46);

    public static final RegistryObject<ForgeSpawnEggItem> PARACER_EGG = registerSpawnEggs("paraceratherium_spawn_egg",
            UPEntities.PARACERATHERIUM , 0x564642, 0x9a9490);

    public static final RegistryObject<ForgeSpawnEggItem> MAMMOTH_EGG = registerSpawnEggs("mammoth_spawn_egg",
            UPEntities.MAMMOTH , 0x180a08, 0x5e5333);

    public static final RegistryObject<ForgeSpawnEggItem> PALAEOPHIS_EGG =registerSpawnEggs("palaophis_spawn_egg",
            UPEntities.PALAEOPHIS , 0x211d4b, 0xa1b7c1);

    public static final RegistryObject<ForgeSpawnEggItem> MEGALANIA_EGG = registerSpawnEggs("megalania_spawn_egg",
            UPEntities.MEGALANIA , 0x2e2319, 0x96874b);

    public static final RegistryObject<ForgeSpawnEggItem> SLUDGE_EGG = registerSpawnEggs("sludge_spawn_egg",
            UPEntities.SLUDGE , 0x0a090a, 0x282627);

    public static final RegistryObject<ForgeSpawnEggItem> OTAROCYON_EGG = registerSpawnEggs("otarocyon_spawn_egg",
            UPEntities.OTAROCYON , 0x1d121b, 0x681523);

    public static final RegistryObject<ForgeSpawnEggItem> LONGISQUAMA_EGG = registerSpawnEggs("longisquama_spawn_egg",
            UPEntities.LONGISQUAMA , 0x5e4fa7, 0x7edbdd);

    public static final RegistryObject<ForgeSpawnEggItem> FURCA_EGG = registerSpawnEggs("furcacauda_spawn_egg",
            UPEntities.FURCA , 0x9a32af, 0xe7cd39);

    public static final RegistryObject<ForgeSpawnEggItem> TARTUOSTEUS_EGG = registerSpawnEggs("tartuosteus_spawn_egg",
            UPEntities.TARTUOSTEUS , 0x12352f, 0x73a658);

    public static final RegistryObject<ForgeSpawnEggItem> PSITTACO_EGG = registerSpawnEggs("psittaco_spawn_egg",
            UPEntities.PSITTACO , 0xa04f2a, 0xd6b560);

    public static final RegistryObject<ForgeSpawnEggItem> TANY_EGG = registerSpawnEggs("tany_spawn_egg",
            UPEntities.TANY , 0x08090d, 0xf2f6f8);

    public static final RegistryObject<ForgeSpawnEggItem> KAPROSUCUHS_EGG = registerSpawnEggs("kapro_spawn_egg",
            UPEntities.KAPROSUCHUS , 0x322f2c, 0xf6f050);

    public static final RegistryObject<ForgeSpawnEggItem> PSILOPTERUS_EGG = registerSpawnEggs("psilo_spawn_egg",
            UPEntities.PSILOPTERUS , 0xc59d7d, 0xc27d28);

    public static final RegistryObject<ForgeSpawnEggItem> DIPLOCAULUS_EGG = registerSpawnEggs("diplocaulus_spawn_egg",
            UPEntities.DIPLOCAULUS , 0x1b0e05, 0xf1fa59);

    public static final RegistryObject<ForgeSpawnEggItem> HYNERPETON_EGG = registerSpawnEggs("hynerpeton_spawn_egg",
            UPEntities.HYNERPETON , 0x1c1614, 0xb6a339);

    public static final RegistryObject<ForgeSpawnEggItem> BALAUR_EGG = registerSpawnEggs("balaur_spawn_egg",
            UPEntities.BALAUR , 0x1f6731, 0xe5cb36);

    public static final RegistryObject<Item> MAJUNGA_HELMET = ITEMS.register("majunga_helmet",
            () -> new ItemMajungaHelmet(UPArmorMaterial.MAJUNGA, ArmorItem.Type.HELMET,
                    new Item.Properties()));

    public static final RegistryObject<Item> AUSTRO_BOOTS = ITEMS.register("austro_boots",
            () -> new ItemAustroBoots(UPArmorMaterial.AUSTRO, ArmorItem.Type.BOOTS,
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
            () -> new Item(new Item.Properties().food(ModFood.RED_FRUIT)));

    public static final RegistryObject<Item> WHITE_FRUIT = ITEMS.register("white_fruit",
            () -> new Item(new Item.Properties().food(ModFood.WHITE_FRUIT)));

    public static final RegistryObject<Item> YELLOW_FRUIT = ITEMS.register("yellow_fruit",
            () -> new Item(new Item.Properties().food(ModFood.YELLOW_FRUIT)));

    public static final RegistryObject<Item> BLUE_FRUIT = ITEMS.register("blue_fruit",
            () -> new Item(new Item.Properties().food(ModFood.BLUE_FRUIT)));

    public static final RegistryObject<Item> DEFROSTED_FROZEN_FOSSIL = ITEMS.register("defrosted_frozen_fossil",
            () -> new Item(new Item.Properties().food(ModFood.DEFROSTED_FOSSIL)));
    public static final RegistryObject<Item> GINKGO_SIGN = ITEMS.register("ginkgo_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), UPBlocks.GINKGO_SIGN.get(), UPBlocks.GINKGO_WALL_SIGN.get()));

    public static final RegistryObject<Item> PETRIFIED_WOOD_SIGN = ITEMS.register("petrified_wood_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), UPBlocks.PETRIFIED_WOOD_SIGN.get(), UPBlocks.PETRIFIED_WOOD_WALL_SIGN.get()));

    public static final RegistryObject<Item> FOXXI_WOOD_SIGN = ITEMS.register("foxxi_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), UPBlocks.FOXXI_SIGN.get(), UPBlocks.FOXXI_WALL_SIGN.get()));

    public static final RegistryObject<Item> DRYO_WOOD_SIGN = ITEMS.register("dryo_sign",
           () -> new SignItem(new Item.Properties().stacksTo(16), UPBlocks.DRYO_SIGN.get(), UPBlocks.DRYO_WALL_SIGN.get()));

    public static final RegistryObject<Item> BARINA_WHISTLE = ITEMS.register("barina_whistle",
            () -> new MusicalTameItem(new Item.Properties().stacksTo(1), UPEntities.BARINASUCHUS, UPTags.OCARINA_WHISTLE));

    public static final RegistryObject<Item> SHEDSCALE_HELMET = ITEMS.register("shedscale_helmet",
            () -> new ItemShedscaleArmor(UPArmorMaterial.SHEDSCALE, ArmorItem.Type.HELMET,
                    new Item.Properties(), 0.4D));

    public static final RegistryObject<Item> SHEDSCALE_CHESTPLATE = ITEMS.register("shedscale_chestplate",
            () -> new ItemShedscaleArmor(UPArmorMaterial.SHEDSCALE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties(), 1D));

    public static final RegistryObject<Item> SHEDSCALE_LEGGINGS = ITEMS.register("shedscale_leggings",
            () -> new ItemShedscaleArmor(UPArmorMaterial.SHEDSCALE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties(),  0.6D));

    public static final RegistryObject<Item> SHEDSCALE_BOOTS = ITEMS.register("shedscale_boots",
            () -> new ItemShedscaleArmor(UPArmorMaterial.SHEDSCALE, ArmorItem.Type.BOOTS,
                    new Item.Properties(), 0.4D));

    public static final RegistryObject<Item> TYRANTS_CROWN = ITEMS.register("tyrants_crown",
            () -> new ItemTyrantsCrown(UPArmorMaterial.TYRANTS, ArmorItem.Type.HELMET,
                    new Item.Properties()));

    public static final RegistryObject<Item> SLOTH_POUCH_ARMOR = ITEMS.register("sloth_pouch_armor",
            () -> new ItemSlothPouchArmor(UPArmorMaterial.SLOTH_POUCH, ArmorItem.Type.CHESTPLATE, 6000,
                    new Item.Properties()));
    public static final RegistryObject<Item> DINO_POUCH = ITEMS.register("dino_pouch",
            () -> new DinoPouchItem(new Item.Properties()));
    public static final RegistryObject<Item> SMILODON_EMBRYO = ITEMS.register("smilodon_embryo",
            () -> new AnimalAttacherItem(new Item.Properties().stacksTo(16), UPTags.SMILODON_EMBRYO_ATTACH_TO, UPEntities.BABY_SMILODON, 1000));

    public static final RegistryObject<Item> MAMMOTH_EMBRYO = ITEMS.register("mammoth_embryo",
            () -> new AnimalAttacherItem(new Item.Properties().stacksTo(16), UPTags.MAMMOTH_EMBRYO_ATTACH_TO, UPEntities.BABY_MAMMOTH, 1000));

    public static final RegistryObject<Item> MEGATH_EMBRYO = ITEMS.register("megath_embryo",
            () -> new AnimalAttacherItem(new Item.Properties().stacksTo(16), UPTags.MEGATH_EMBRYO_ATTACH_TO, UPEntities.BABY_MEGATHERIUM, 1000));

    public static final RegistryObject<Item> GIGANTO_EMBRYO = ITEMS.register("giganto_embryo",
            () -> new AnimalAttacherItem(new Item.Properties().stacksTo(16), UPTags.GIGANTO_EMBRYO_ATTACH_TO, UPEntities.BABY_GIGANTO, 1000));

    public static final RegistryObject<Item> PARACER_EMBRYO = ITEMS.register("paracer_embryo",
            () -> new AnimalAttacherItem(new Item.Properties().stacksTo(16), UPTags.PARACER_EMBRYO_ATTACH_TO, UPEntities.BABY_PARACER, 1000));

    public static final RegistryObject<Item> PALAEO_EMBRYO = ITEMS.register("palaeo_embryo",
            () -> new AnimalAttacherItem(new Item.Properties().stacksTo(16), UPTags.PALAEO_EMBRYO_ATTACH_TO, UPEntities.BABY_PALAEO, 1000));

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
            () -> new IncreaseAgeItem(new Item.Properties().food(ModFood.DRYO_NUTS), UPTags.HERBIVORES,10));

    public static final RegistryObject<Item> RAW_MAMMOTH = ITEMS.register("raw_mammoth",
            () -> new Item(new Item.Properties().food(ModFood.RAW_MAMMOTH)));

    public static final RegistryObject<Item> COOKED_MAMMOTH = ITEMS.register("cooked_mammoth",
            () -> new Item(new Item.Properties().food(ModFood.COOKED_MAMMOTH)));

    public static final RegistryObject<Item> RAW_FURCACAUDA = ITEMS.register("raw_furcacauda",
            () -> new Item(new Item.Properties().food(ModFood.RAW_FURCA)));

    public static final RegistryObject<Item> COOKED_FURCACAUDA = ITEMS.register("cooked_furcacauda",
            () -> new Item(new Item.Properties().food(ModFood.COOKED_FURCA)));

    public static final RegistryObject<Item> RAW_TARTU = ITEMS.register("raw_tartuosteus",
            () -> new Item(new Item.Properties().food(ModFood.RAW_TARTU)));

    public static final RegistryObject<Item> COOKED_TARTU = ITEMS.register("cooked_tartuosteus",
            () -> new Item(new Item.Properties().food(ModFood.COOKED_TARTU)));

    public static final RegistryObject<Item> RAW_OPHIODON = ITEMS.register("raw_ophiodon",
            () -> new Item(new Item.Properties().food(ModFood.RAW_OPHIODON)));

    public static final RegistryObject<Item> COOKED_OPHIODON = ITEMS.register("cooked_ophiodon",
            () -> new Item(new Item.Properties().food(ModFood.COOKED_OPHIODON)));
    public static final RegistryObject<Item> MAMMOTH_MEATBALL = ITEMS.register("mammoth_meatball",
            () -> new ModItemDrinkable(soupItem().food(ModFood.MAMMOTH_MEATBALL), true, false));
    public static final RegistryObject<Item> ZULOGAE_DISC = ITEMS.register("zulogae_disc",
            () -> new RecordItem(15, UPSounds.ZULOGAE_DISC, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 157));

    public static final RegistryObject<Item> PSITTACO_QUIL = ITEMS.register("psittaco_quil",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PSITTACCO_ARROW = ITEMS.register("psittaco_arrow", () -> new PsittaccoArrow(new Item.Properties().rarity(Rarity.RARE)));

    private static RegistryObject<ForgeSpawnEggItem> registerSpawnEggs(String name, Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor) {
        return ITEMS.register(name, () -> new ForgeSpawnEggItem(type, backgroundColor, highlightColor,new Item.Properties()));
    }
}
