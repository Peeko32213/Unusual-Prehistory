package com.peeko32213.unusualprehistory.common.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

/**
 * Manages the JSON files for loot fruits and their trades.
 */
public class LootFruitJsonManager extends SimpleJsonResourceReloadListener {
    private static final Gson STANDARD_GSON = new Gson();
    public static final Logger LOGGER = LogManager.getLogger();

    /**
     * The map of trades for each item.
     */
    public static Map<Item, List<LootFruitCodec>> trades = new HashMap<>();
    public static Map<Integer, List<LootFruitCodec>> tierTrades = new HashMap<>();
    private final String folderName;

    /**
     * Constructs a new LootFruitJsonManager with the default folder name and Gson instance.
     */
    public LootFruitJsonManager() {
        this(prefix("trades").getPath(), STANDARD_GSON);
    }

    /**
     * Constructs a new LootFruitJsonManager with the specified folder name and Gson instance.
     *
     * @param folderName The folder name where the JSON files are located.
     * @param gson       The Gson instance to use for deserialization.
     */
    public LootFruitJsonManager(String folderName, Gson gson) {
        super(gson, folderName);
        this.folderName = folderName;
    }

    /**
     * Gets the map of trades for each item.
     *
     * @return The map of trades.
     */
    public static Map<Item, List<LootFruitCodec>> getTrades() {
        return trades;
    }

    public static Map<Integer, List<LootFruitCodec>> getTierTrades() {
        return tierTrades;
    }

    public static void setTierTrades(Map<Integer, List<LootFruitCodec>> tierTrades) {
        LootFruitJsonManager.tierTrades = tierTrades;
    }

    public static void setTrades(Map<Item, List<LootFruitCodec>> trades) {
        LootFruitJsonManager.trades = trades;
    }

    /**
     * Gets the loot for the specified item.
     *
     * @param item              The item to get the loot for.
     * @param lootFruitCodecList The list of loot fruits for the item.
     * @return The list of loot fruits for the item, or an empty list if no loot is found.
     */
    public static List<LootFruitCodec> getLoot(Item item, List<LootFruitCodec> lootFruitCodecList) {
        return trades.getOrDefault(item, lootFruitCodecList);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        tierTrades.clear();
        trades.clear();
        Map<Item, List<LootFruitCodec>> trades = new HashMap<>();
        Map<Integer, List<LootFruitCodec>> tradeTier = new HashMap<>();
        for (Map.Entry<ResourceLocation, JsonElement> entry : jsons.entrySet()) {
            ResourceLocation key = entry.getKey();
            JsonElement element = entry.getValue();
            LootFruitCodec.CODEC.decode(JsonOps.INSTANCE, element)
                    .get()
                    .ifLeft(result -> {
                        LootFruitCodec lootFruitCodec = result.getFirst();

                        List<LootFruitCodec> lootFruitCodecList = trades.getOrDefault(lootFruitCodec.getTradeItem(), new ArrayList<>());
                        lootFruitCodecList.add(lootFruitCodec);
                        trades.put(lootFruitCodec.getTradeItem(), lootFruitCodecList);

                        List<LootFruitCodec> lootFruitCodecTier = tradeTier.getOrDefault(lootFruitCodec.getTier(), new ArrayList<>());
                        lootFruitCodecTier.add(lootFruitCodec);
                        tradeTier.put(lootFruitCodec.getTier(), lootFruitCodecTier);
                    })
                    .ifRight(partial -> LOGGER.error("Failed to parse recipe JSON for {} due to: {}", this.folderName, partial.message()));
        }

        LootFruitJsonManager.trades.putAll(trades);
        LootFruitJsonManager.tierTrades.putAll(tradeTier);
        LOGGER.info("Data loader for {} loaded {} jsons", this.folderName, this.trades.size());
    }
}

