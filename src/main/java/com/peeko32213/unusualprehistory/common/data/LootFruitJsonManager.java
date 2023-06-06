package com.peeko32213.unusualprehistory.common.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class LootFruitJsonManager  extends SimpleJsonResourceReloadListener {
    private static final Gson STANDARD_GSON = new Gson();
    public static final Logger LOGGER = LogManager.getLogger();

    public static Map<Item, List<LootFruitCodec>> trades = new HashMap<>();
    private final String folderName;


    /**
     * Constructs a new AnalyzerRecipeJsonManager with the default folder name and Gson instance.
     */
    public LootFruitJsonManager() {
        this(prefix("trades").getPath(), STANDARD_GSON);
    }

    /**
     * Constructs a new AnalyzerRecipeJsonManager with the specified folder name and Gson instance.
     *
     * @param folderName The folder name where the JSON files are located.
     * @param gson       The Gson instance to use for deserialization.
     */
    public LootFruitJsonManager(String folderName, Gson gson) {
        super(gson, folderName);
        this.folderName = folderName;
    }


    public static Map<Item, List<LootFruitCodec>> getTrades() {
        return trades;
    }

    public static List<LootFruitCodec> getLoot(Item item, List<LootFruitCodec> lootFruitCodecList){
        return trades.getOrDefault(item, lootFruitCodecList);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        Map<Item, List<LootFruitCodec>> trades = new HashMap<>();

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
                    })
                    .ifRight(partial -> LOGGER.error("Failed to parse recipe JSON for {} due to: {}", this.folderName, partial.message()));
        }

        this.trades = trades;
    }
}
