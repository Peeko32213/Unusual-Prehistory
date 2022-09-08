package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.item.ItemModFishBucket;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UPItems {


    private UPItems() {
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            UnusualPrehistory.MODID);

    public static final RegistryObject<Item> AMMONITE_SHELL = ITEMS.register("ammonite_shell",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> PALEO_FOSSIL = ITEMS.register("paleo_fossil",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> MEZO_FOSSIL = ITEMS.register("mezo_fossil",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> FLASK = ITEMS.register("flask",
            () -> new Item(new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> STETHA_FLASK = ITEMS.register("stetha_flask",
            () -> new Item(new Item.Properties().stacksTo(8).tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> DUNK_FLASK = ITEMS.register("dunk_flask",
            () -> new Item(new Item.Properties().stacksTo(8).tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> COTY_FLASK = ITEMS.register("coty_flask",
            () -> new Item(new Item.Properties().stacksTo(8).tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> ANURO_FLASK = ITEMS.register("anuro_flask",
            () -> new Item(new Item.Properties().stacksTo(8).tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> BEELZ_FLASK = ITEMS.register("beelz_flask",
            () -> new Item(new Item.Properties().stacksTo(8).tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> MAJUNGA_FLASK = ITEMS.register("majunga_flask",
            () -> new Item(new Item.Properties().stacksTo(8).tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> AMMONITE_FLASK = ITEMS.register("ammonite_flask",
            () -> new Item(new Item.Properties().stacksTo(8).tab(UnusualPrehistory.DINO_TAB)));

    public static final RegistryObject<Item> STETHA_BUCKET = ITEMS.register("stetha_bucket",
            () -> new ItemModFishBucket(UPEntities.STETHACANTHUS, () -> Fluids.WATER, Items.BUCKET, false,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB).stacksTo(1)));

    public static final RegistryObject<Item> AMMON_BUCKET = ITEMS.register("ammon_bucket",
            () -> new ItemModFishBucket(UPEntities.AMMON, () -> Fluids.WATER, Items.BUCKET, false,
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
            () -> new ForgeSpawnEggItem(UPEntities.COTY , 0xab211e, 0xf2d0c3,
                    new Item.Properties().tab(UnusualPrehistory.DINO_TAB)));


}
