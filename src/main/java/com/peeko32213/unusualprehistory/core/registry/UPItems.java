package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.*;
import com.peeko32213.unusualprehistory.common.item.armor.ItemAustroBoots;
import com.peeko32213.unusualprehistory.common.item.armor.ItemMajungaHelmet;
import com.peeko32213.unusualprehistory.common.item.armor.ItemTyrantsCrown;
import com.peeko32213.unusualprehistory.common.item.armor.material.UPArmorMaterial;
import com.peeko32213.unusualprehistory.common.item.armor.shedscale.ItemshedScaleArmor;
import com.peeko32213.unusualprehistory.common.item.tool.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
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

    public static final RegistryObject<Item> TAR_FOSSIL = ITEMS.register("tar_fossil_item",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> FROZEN_FOSSIL = ITEMS.register("frozen_fossil_item",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> OPAL_CHUNK = ITEMS.register("opal_chunk",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> OPAL_FOSSIL = ITEMS.register("opal_fossil",
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

    public static final RegistryObject<Item> ZULOAGAE_FLASK = ITEMS.register("zuloagae_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> RAIGUENRAYUN_FLASK = ITEMS.register("raiguenrayun_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> FOXXI_FLASK = ITEMS.register("foxxi_flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> DRYO_FLASK = ITEMS.register("dryo_flask",
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

    public static final RegistryObject<Item> HANDMADE_BATTLEAXE = ITEMS.register("handmade_battleaxe",
            () -> new ItemHandmadeBattleaxe(UPItemTiers.HANDMADE, 5, -1.9F));

    public static final RegistryObject<Item> HANDMADE_CLUB = ITEMS.register("handmade_club",
            () -> new ItemHandmadeClub(UPItemTiers.HANDMADE, 8, -2.3F));

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
    public static final RegistryObject<ForgeSpawnEggItem> TALAPANAS_EGG = registerSpawnEggs("talapanas_spawn_egg",
            UPEntities.TALAPANAS , 0x1d1311, 0x3c4849);

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
    public static final RegistryObject<Item> MAJUNGA_HELMET = ITEMS.register("majunga_helmet",
            () -> new ItemMajungaHelmet(UPArmorMaterial.MAJUNGA, EquipmentSlot.HEAD,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> AUSTRO_BOOTS = ITEMS.register("austro_boots",
            () -> new ItemAustroBoots(UPArmorMaterial.AUSTRO, EquipmentSlot.FEET,
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

    public static final RegistryObject<Item> DEFROSTED_FROZEN_FOSSIL = ITEMS.register("defrosted_frozen_fossil",
            () -> new Item(new Item.Properties().food(ModFood.DEFROSTED_FOSSIL).tab(UnusualPrehistory.DINO_TAB)));
    public static final RegistryObject<Item> GINKGO_SIGN = ITEMS.register("ginkgo_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16).tab(UnusualPrehistory.DINO_TAB), UPBlocks.GINKGO_SIGN.get(), UPBlocks.GINKGO_WALL_SIGN.get()));

    public static final RegistryObject<Item> PETRIFIED_WOOD_SIGN = ITEMS.register("petrified_wood_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16).tab(UnusualPrehistory.DINO_TAB), UPBlocks.PETRIFIED_WOOD_SIGN.get(), UPBlocks.PETRIFIED_WOOD_WALL_SIGN.get()));

    public static final RegistryObject<Item> BARINA_WHISTLE = ITEMS.register("barina_whistle",
            () -> new MusicalTameItem(new Item.Properties().stacksTo(1).tab(UnusualPrehistory.DINO_TAB), UPEntities.BARINASUCHUS, UPSounds.CROCARINA, 200));

    public static final RegistryObject<Item> SHEDSCALE_HELMET = ITEMS.register("shedscale_helmet",
            () -> new ItemshedScaleArmor(UPArmorMaterial.SHEDSCALE, EquipmentSlot.HEAD,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB), 0.4D));

    public static final RegistryObject<Item> SHEDSCALE_CHESTPLATE = ITEMS.register("shedscale_chestplate",
            () -> new ItemshedScaleArmor(UPArmorMaterial.SHEDSCALE, EquipmentSlot.CHEST,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB), 1D));

    public static final RegistryObject<Item> SHEDSCALE_LEGGINGS = ITEMS.register("shedscale_leggings",
            () -> new ItemshedScaleArmor(UPArmorMaterial.SHEDSCALE, EquipmentSlot.LEGS,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB),  0.6D));

    public static final RegistryObject<Item> SHEDSCALE_BOOTS = ITEMS.register("shedscale_boots",
            () -> new ItemshedScaleArmor(UPArmorMaterial.SHEDSCALE, EquipmentSlot.FEET,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB), 0.4D));

    public static final RegistryObject<Item> TYRANTS_CROWN = ITEMS.register("tyrants_crown",
            () -> new ItemTyrantsCrown(UPArmorMaterial.TYRANTS, EquipmentSlot.HEAD,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> SMILODON_EMBRYO = ITEMS.register("smilodon_embryo",
            () -> new AnimalAttacherItem(new Item.Properties().stacksTo(16).tab(UnusualPrehistory.DINO_TAB), UPTags.SMILODON_EMBRYO_ATTACH_TO, UPEntities.SMILODON, 1000));

    public static final RegistryObject<Item> MAMMOTH_EMBRYO = ITEMS.register("mammoth_embryo",
            () -> new AnimalAttacherItem(new Item.Properties().stacksTo(16).tab(UnusualPrehistory.DINO_TAB), UPTags.MAMMOTH_EMBRYO_ATTACH_TO, UPEntities.MAMMOTH, 1000));

    public static final RegistryObject<Item> MEGATH_EMBRYO = ITEMS.register("megath_embryo",
            () -> new AnimalAttacherItem(new Item.Properties().stacksTo(16).tab(UnusualPrehistory.DINO_TAB), UPTags.MEGATH_EMBRYO_ATTACH_TO, UPEntities.MEGATHERIUM, 1000));

    public static final RegistryObject<Item> GIGANTO_EMBRYO = ITEMS.register("giganto_embryo",
            () -> new AnimalAttacherItem(new Item.Properties().stacksTo(16).tab(UnusualPrehistory.DINO_TAB), UPTags.GIGANTO_EMBRYO_ATTACH_TO, UPEntities.GIGANTOPITHICUS, 1000));

    public static final RegistryObject<Item> PARACER_EMBRYO = ITEMS.register("paracer_embryo",
            () -> new AnimalAttacherItem(new Item.Properties().stacksTo(16).tab(UnusualPrehistory.DINO_TAB), UPTags.PARACER_EMBRYO_ATTACH_TO, UPEntities.PARACERATHERIUM, 1000));

    public static final RegistryObject<Item> PALAEO_EMBRYO = ITEMS.register("palaeo_embryo",
            () -> new AnimalAttacherItem(new Item.Properties().stacksTo(16).tab(UnusualPrehistory.DINO_TAB), UPTags.PALAEO_EMBRYO_ATTACH_TO, UPEntities.PALAEOPHIS, 1000));

    private static RegistryObject<ForgeSpawnEggItem> registerSpawnEggs(String name, Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor) {
        return ITEMS.register(name, () -> new ForgeSpawnEggItem(type, backgroundColor, highlightColor,new Item.Properties().tab(UnusualPrehistory.DINO_SPAWN_EGGS)));
    }

    public static final RegistryObject<Item> TAB_EGG_ICON = ITEMS.register("tab_spawn_egg", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TAR_BUCKET =  ITEMS.register("tar_bucket", () -> new SolidBucketItem(UPBlocks.TAR.get(), SoundEvents.BUCKET_EMPTY_POWDER_SNOW, (new Item.Properties()).stacksTo(1).tab(CreativeModeTab.TAB_MISC)));

}
