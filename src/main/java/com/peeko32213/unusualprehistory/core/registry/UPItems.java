package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.*;
import com.peeko32213.unusualprehistory.common.item.armor.AustroArmorMaterial;
import com.peeko32213.unusualprehistory.common.item.armor.ItemAustroBoots;
import com.peeko32213.unusualprehistory.common.item.armor.ItemMajungaHelmet;
import com.peeko32213.unusualprehistory.common.item.armor.MajungaArmorMaterial;
import com.peeko32213.unusualprehistory.common.item.tool.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UPItems {
    private UPItems() {
    }

    public static Item.Properties drinkItem() {
        return new Item.Properties().craftRemainder(UPItems.FLASK.get()).stacksTo(16).tab(UnusualPrehistory.DINO_TAB);
    }


    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            UnusualPrehistory.MODID);

    public static final RegistryObject<Item> ENCYLOPEDIA = ITEMS.register("encyclopedia",
            () -> new ItemEncyclopedia(new Item.Properties().tab(UnusualPrehistory.DINO_TAB).stacksTo(1)));
    public static final RegistryObject<Item> AMMONITE_SHELL_ICON = ITEMS.register("ammonite_shell_icon",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SHELL_SHARD = ITEMS.register("shell_shard",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> PALEO_FOSSIL = ITEMS.register("paleo_fossil",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> MEZO_FOSSIL = ITEMS.register("mezo_fossil",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> PLANT_FOSSIL = ITEMS.register("plant_fossil_item",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> FLASK = ITEMS.register("flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Item> MEAT_ON_A_STICK = ITEMS.register("meat_on_a_stick",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB).durability(130)));

    public static final RegistryObject<Item> TRIKE_HORN = ITEMS.register("trike_horn",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Item> REX_SCALE = ITEMS.register("rex_scale",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Item> RAPTOR_FEATHERS = ITEMS.register("raptor_feathers",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Item> VELOCI_SHIELD = ITEMS.register("veloci_shield",
            () -> new ItemVelociShield(new Item.Properties().durability(800).rarity(Rarity.UNCOMMON).tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Item> TRIKE_SHIELD = ITEMS.register("trike_shield",
            () -> new ItemTrikeShield(new Item.Properties().durability(1300).rarity(Rarity.UNCOMMON).tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Item> GROG = ITEMS.register("grog",
            () -> new ModItemDrinkable(drinkItem().food(ModFood.GROG), true, false));

    public static final RegistryObject<Item> MEATY_BUFFET = ITEMS.register("meaty_buffet",
            () -> new ModItemConsumable(new Item.Properties().tab(UnusualPrehistory.DINO_TAB).food(ModFood.MEATY_BUFFET).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> MAJUNGA_SCUTE = ITEMS.register("majunga_scute",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Item> AMBER_SHARDS = ITEMS.register("amber_shard",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> AMBER_FOSSIL = ITEMS.register("amber_fossil",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Item> ADORNED_STAFF = ITEMS.register("adorned_staff",
            () -> new Item(new Item.Properties().durability(100).rarity(Rarity.UNCOMMON).tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Item> REX_TOOTH = ITEMS.register("rex_tooth",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> STETHA_FLASK = ITEMS.register("stetha_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> DUNK_FLASK = ITEMS.register("dunk_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> COTY_FLASK = ITEMS.register("coty_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> ANURO_FLASK = ITEMS.register("anuro_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> BEELZ_FLASK = ITEMS.register("beelz_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> MAJUNGA_FLASK = ITEMS.register("majunga_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> AMMONITE_FLASK = ITEMS.register("ammonite_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> HORSETAIL_FLASK = ITEMS.register("horsetail_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> TALL_HORSETAIL_FLASK = ITEMS.register("tall_horsetail_flask",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LEEFRUCTUS_FLASK = ITEMS.register("leefructus_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> ARCHAO_FLASK = ITEMS.register("archao_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> BENNET_FLASK = ITEMS.register("bennet_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> GINKGO_FLASK = ITEMS.register("ginkgo_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> SARR_FLASK = ITEMS.register("sarr_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> SCAU_FLASK = ITEMS.register("scau_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> BRACHI_FLASK = ITEMS.register("brachi_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> REX_FLASK = ITEMS.register("rex_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> TRIKE_FLASK = ITEMS.register("trike_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> RAPTOR_FLASK = ITEMS.register("raptor_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> PACHY_FLASK = ITEMS.register("pachy_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> ENCRUSTED_FLASK = ITEMS.register("encrusted_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> ERYON_FLASK = ITEMS.register("eryon_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> ANOSTYLOSTRAMA_FLASK = ITEMS.register("anostylostroma_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> ARCHAEFRUCTUS_FLASK = ITEMS.register("archaefructus_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> CLATHRODICTYON_FLASK = ITEMS.register("clathrodictyon_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> NELUMBITES_FLASK = ITEMS.register("nelumbites_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> QUEREUXIA_FLASK = ITEMS.register("quereuxia_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> ULUGH_FLASK = ITEMS.register("ulugh_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> AUSTRO_FLASK = ITEMS.register("austro_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> HWACHA_FLASK = ITEMS.register("hwacha_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> ANTARCTO_FLASK = ITEMS.register("antarcto_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> KENTRO_FLASK = ITEMS.register("kentro_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> GIGANTO_FLASK = ITEMS.register("giganto_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> SMILO_FLASK = ITEMS.register("smilo_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> MEGATH_FLASK = ITEMS.register("megath_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> PARACER_FLASK = ITEMS.register("paracer_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> MAMMOTH_FLASK = ITEMS.register("mammoth_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> BARIN_FLASK = ITEMS.register("barin_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> PALAEO_FLASK = ITEMS.register("palaeo_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> MEGALA_FLASK = ITEMS.register("megala_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> TALAPANAS_FLASK = ITEMS.register("talapanas_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Item> ORGANIC_OOZE = ITEMS.register("organic_ooze",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Item> FROG_SALIVA = ITEMS.register("frog_saliva",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> AUSTRO_FEATHER = ITEMS.register("austro_feather",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Item> ENCRUSTED_ORGAN = ITEMS.register("encrusted_organ",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Item> ANTARCTO_PLATE = ITEMS.register("antarcto_plate",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Item> WARPICK = ITEMS.register("warpick",
            () -> new ItemWarpick(UPItemTiers.SHELL, 3, -2.8F));

    public static final RegistryObject<Item> PRIMAL_MACUAHUITL = ITEMS.register("primal_macuahuitl",
            () -> new ItemPrimalMacuahuitl(UPItemTiers.SHELL, 6, -1.8F));

    public static final RegistryObject<Item> HANDMADE_SPEAR = ITEMS.register("handmade_spear",
            () -> new ItemHandmadeSpear(UPItemTiers.HANDMADE, 3, -1.5F));

    public static final RegistryObject<Item> RAW_STETHA = ITEMS.register("raw_stetha",
            () -> new Item(new Item.Properties().food(ModFood.RAW_STETHA).tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> COOKED_STETHA = ITEMS.register("cooked_stetha",
            () -> new Item(new Item.Properties().food(ModFood.COOKED_STETHA).tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> RAW_COTY = ITEMS.register("raw_coty",
            () -> new Item(new Item.Properties().food(ModFood.RAW_COTY).tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> COOKED_COTY = ITEMS.register("cooked_coty",
            () -> new Item(new Item.Properties().food(ModFood.COOKED_COTY).tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> RAW_SCAU = ITEMS.register("raw_scau",
            () -> new Item(new Item.Properties().food(ModFood.RAW_SCAU).tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> COOKED_SCAU = ITEMS.register("cooked_scau",
            () -> new Item(new Item.Properties().food(ModFood.COOKED_SCAU).tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> GINKGO_FRUIT = ITEMS.register("ginkgo_fruit",
            () -> new Item(new Item.Properties().food(ModFood.GINKGO_FRUIT).tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> RAW_GINKGO_SEEDS = ITEMS.register("raw_ginkgo_seeds",
            () -> new Item(new Item.Properties().food(ModFood.RAW_GINKGO_SEEDS).tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> COOKED_GINKGO_SEEDS = ITEMS.register("cooked_ginkgo_seeds",
            () -> new Item(new Item.Properties().food(ModFood.COOKED_GINKGO_SEEDS).tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Item> GOLDEN_SCAU = ITEMS.register("golden_scau",
            () -> new Item(new Item.Properties().food(ModFood.GOLDEN_SCAU).tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Item> RAW_AUSTRO = ITEMS.register("raw_austro",
            () -> new Item(new Item.Properties().food(ModFood.RAW_AUSTRO).tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Item> COOKED_AUSTRO = ITEMS.register("cooked_austro",
            () -> new Item(new Item.Properties().food(ModFood.COOKED_AUSTRO).tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Item> AMBER_GUMMY = ITEMS.register("amber_gummy",
            () -> new AmberGummyItem(new Item.Properties().food(ModFood.AMBER_GUMMY).tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Item> STETHA_BUCKET = ITEMS.register("stetha_bucket",
            () -> new ItemModFishBucket(UPEntities.STETHACANTHUS, () -> Fluids.WATER, Items.BUCKET, false,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB).stacksTo(1)));

    public static final RegistryObject<Item> AMMON_BUCKET = ITEMS.register("ammon_bucket",
            () -> new ItemModFishBucket(UPEntities.AMMON, () -> Fluids.WATER, Items.BUCKET, false,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB).stacksTo(1)));

    public static final RegistryObject<Item> BEELZE_BUCKET = ITEMS.register("beelze_bucket",
            () -> new ItemModFishBucket(UPEntities.BEELZE_TADPOLE, () -> Fluids.WATER, Items.BUCKET, false,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB).stacksTo(1)));
    public static final RegistryObject<Item> DUNK_BUCKET = ITEMS.register("dunk_bucket",
            () -> new ItemModFishBucket(UPEntities.BABY_DUNK, () -> Fluids.WATER, Items.BUCKET, false,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB).stacksTo(1)));

    public static final RegistryObject<Item> SCAU_BUCKET = ITEMS.register("scau_bucket",
            () -> new ItemModFishBucket(UPEntities.SCAU, () -> Fluids.WATER, Items.BUCKET, false,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB).stacksTo(1)));

    public static final RegistryObject<ForgeSpawnEggItem> STETHA_EGG = ITEMS.register("stetha_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.STETHACANTHUS , 0x754123, 0xb59a6e,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> MAJUNGA_EGG = ITEMS.register("majunga_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.MAJUNGA , 0x1d600e, 0xacd35d,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> ANURO_EGG = ITEMS.register("anuro_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.ANURO , 0x353121, 0xf4dd48,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> BEELZ_EGG = ITEMS.register("beelz_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.BEELZ , 0x443f13, 0xa5db90,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> AMMON_EGG = ITEMS.register("ammon_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.AMMON , 0x402018, 0x99895c,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> DUNK_EGG = ITEMS.register("dunk_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.DUNK , 0x611f0d, 0xb07b42,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> COTY_EGG = ITEMS.register("coty_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.COTY , 0x9d5333, 0xd19c8e,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> SCAU_EGG = ITEMS.register("scau_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.SCAU , 0x909da1, 0x5094c1,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> TRIKE_EGG = ITEMS.register("trike_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.TRIKE , 0x47302c, 0xffcb23,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> PACHY_EGG = ITEMS.register("pachy_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.PACHY , 0x282d3d, 0x5d7170,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> BRACHI_EGG = ITEMS.register("brachi_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.BRACHI , 0x5e6f9a, 0xc7e1e4,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> VELOCI_EGG = ITEMS.register("veloci_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.VELOCI , 0x774228, 0xcb09464,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> REX_EGG = ITEMS.register("rex_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.REX , 0x31171c, 0xb96a53,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> ENCRUSTED_EGG = ITEMS.register("encrusted_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.ENCRUSTED , 0x482300, 0xffc656,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> ERYON_EGG = ITEMS.register("eryon_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.ERYON , 0x1d2110, 0xe4b423,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<ForgeSpawnEggItem> AUSTRO_EGG = ITEMS.register("austroraptor_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.AUSTRO , 0xcfb9b4, 0xcf683a,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> ANTARCO_EGG = ITEMS.register("antarcto_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.ANTARCO , 0x5a120d, 0xe5ce7a,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> ULUG_EGG = ITEMS.register("ulugh_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.ULUG , 0x3a2424, 0xdbd8ce,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> KENTRO_EGG = ITEMS.register("kentro_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.KENTRO , 0x122407, 0xddcca4,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<ForgeSpawnEggItem> HWACHA_EGG = ITEMS.register("hwacha_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.HWACHA , 0x14191b, 0xf4f435,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<ForgeSpawnEggItem> GIGANTO_EGG = ITEMS.register("giganto_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.GIGANTOPITHICUS , 0x7c3c23, 0x665f58,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<ForgeSpawnEggItem> TALAPANAS_EGG = ITEMS.register("talapanas_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.TALAPANAS , 0x1d1311, 0x3c4849,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> BARINA_EGG = ITEMS.register("barinasuchus_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.BARINASUCHUS , 0x0e0b03, 0xbea61e,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> MEGATH_EGG = ITEMS.register("megatherium_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.MEGATHERIUM , 0x65352a, 0x9a7c51,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> SMILO_EGG = ITEMS.register("smilodon_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.SMILODON , 0x64311a, 0x9b6f46,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> PARACER_EGG = ITEMS.register("paraceratherium_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.PARACERATHERIUM , 0x564642, 0x9a9490,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> MAMMOTH_EGG = ITEMS.register("mammoth_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.MAMMOTH , 0x180a08, 0x5e5333,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> PALAEOPHIS_EGG = ITEMS.register("palaophis_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.PALAEOPHIS , 0x211d4b, 0xa1b7c1,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<ForgeSpawnEggItem> MEGALANIA_EGG = ITEMS.register("megalania_spawn_egg",
            () -> new ForgeSpawnEggItem(UPEntities.MEGALANIA , 0x411611, 0xd57738,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Item> MAJUNGA_HELMET = ITEMS.register("majunga_helmet",
            () -> new ItemMajungaHelmet(MajungaArmorMaterial.MAJUNGA, EquipmentSlot.HEAD,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> AUSTRO_BOOTS = ITEMS.register("austro_boots",
            () -> new ItemAustroBoots(AustroArmorMaterial.AUSTRO, EquipmentSlot.FEET,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> CLATHRODICTYON_FAN = ITEMS.register("clathrodictyon_fan",
            () -> new StandingAndWallBlockItem(UPBlocks.CLATHRODICTYON_FAN.get(), UPBlocks.CLATHRODICTYON_WALL_FAN.get(),
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> DEAD_CLATHRODICTYON_FAN = ITEMS.register("dead_clathrodictyon_fan",
            () -> new StandingAndWallBlockItem(UPBlocks.DEAD_CLATHRODICTYON_FAN.get(), UPBlocks.DEAD_CLATHRODICTYON_WALL_FAN.get(),
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> RED_FRUIT_SCRAPS = ITEMS.register("red_fruit_scraps",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> WHITE_FRUIT_SCRAPS = ITEMS.register("white_fruit_scraps",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> YELLOW_FRUIT_SCRAPS = ITEMS.register("yellow_fruit_scraps",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> BLUE_FRUIT_SCRAPS = ITEMS.register("blue_fruit_scraps",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> RED_FRUIT = ITEMS.register("red_fruit",
            () -> new Item(new Item.Properties().food(ModFood.RED_FRUIT).tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> WHITE_FRUIT = ITEMS.register("white_fruit",
            () -> new Item(new Item.Properties().food(ModFood.WHITE_FRUIT).tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> YELLOW_FRUIT = ITEMS.register("yellow_fruit",
            () -> new Item(new Item.Properties().food(ModFood.YELLOW_FRUIT).tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> BLUE_FRUIT = ITEMS.register("blue_fruit",
            () -> new Item(new Item.Properties().food(ModFood.BLUE_FRUIT).tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Item> GINKGO_SIGN = ITEMS.register("ginkgo_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16).tab(UnusualPrehistory.DINO_TAB), UPBlocks.GINKGO_SIGN.get(), UPBlocks.GINKGO_WALL_SIGN.get()));

    public static final RegistryObject<Item> PETRIFIED_WOOD_SIGN = ITEMS.register("petrified_wood_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16).tab(UnusualPrehistory.DINO_TAB), UPBlocks.PETRIFIED_WOOD_SIGN.get(), UPBlocks.PETRIFIED_WOOD_WALL_SIGN.get()));

    public static final RegistryObject<Item> SMILODON_EMBRYO = ITEMS.register("smilodon_embryo",
            () -> new AnimalAttacherItem(new Item.Properties().stacksTo(16).tab(UnusualPrehistory.DINO_TAB), UPTags.SMILODON_EMBRYO_ATTACH_TO, UPEntities.SMILODON, 100));


    public static final RegistryObject<Item> BARINA_WHISTLE = ITEMS.register("barina_whistle",
            () -> new DinosaurWhistle(new Item.Properties().stacksTo(16).tab(UnusualPrehistory.DINO_TAB), UPEntities.BARINASUCHUS));

}
