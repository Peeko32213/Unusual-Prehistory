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

/**
 * Manages analyzer recipe JSON files and provides methods to access and retrieve recipe information.
 */
public class AnalyzerRecipeJsonManager extends SimpleJsonResourceReloadListener {
    private static final Gson STANDARD_GSON = new Gson();
    public static final Logger LOGGER = LogManager.getLogger();

    public static Map<Item, List<ItemWeightedPair>> recipeList = new HashMap<>();
    private final String folderName;

    /**
     * Constructs a new AnalyzerRecipeJsonManager with the default folder name and Gson instance.
     */
    public AnalyzerRecipeJsonManager() {
        this(prefix("analyzer").getPath(), STANDARD_GSON);
    }

    /**
     * Constructs a new AnalyzerRecipeJsonManager with the specified folder name and Gson instance.
     *
     * @param folderName The folder name where the JSON files are located.
     * @param gson       The Gson instance to use for deserialization.
     */
    public AnalyzerRecipeJsonManager(String folderName, Gson gson) {
        super(gson, folderName);
        this.folderName = folderName;
    }

    /**
     * Retrieves the recipe list containing mappings of items to weighted pairs.
     *
     * @return The recipe list.
     */
    public static Map<Item, List<ItemWeightedPair>> getRecipes() {
        return recipeList;
    }

    /**
     * Retrieves the list of item-weighted pairs for the specified item.
     *
     * @param item The item to retrieve the weighted pairs for.
     * @return The list of item-weighted pairs.
     */
    public static List<ItemWeightedPair> getItems(Item item) {
        return recipeList.getOrDefault(item, Collections.emptyList());
    }

    /**
     * Checks if the specified item has a recipe defined.
     *
     * @param item The item to check for a recipe.
     * @return {@code true} if the item has a recipe, {@code false} otherwise.
     */
    public static boolean containsRecipe(Item item) {
        return recipeList.containsKey(item);
    }

    /**
     * Generates a random item stack based on the specified input and level.
     *
     * @param input The input item.
     * @param level The level.
     * @return The generated random item stack.
     */
    public static ItemStack getRandomItemStack(Item input, Level level) {
        // Ensure level exists
        if (null == level) {
            return ItemStack.EMPTY;
        }

        Item outputItem = null;

        List<ItemWeightedPair> outputs = recipeList.get(input);

        int totalWeight = 0;
        for (ItemWeightedPair itemWeightedPair : outputs) {
            totalWeight += itemWeightedPair.getWeight();
        }

        int randomNr = level.random.nextInt(totalWeight);
        int cumulativeWeight = 0;

        for (ItemWeightedPair itemWeightedPair : outputs) {
            cumulativeWeight += itemWeightedPair.getWeight();
            if (randomNr < cumulativeWeight) {
                outputItem = itemWeightedPair.getItem();
                break;
            }
        }

        ItemStack outputStack = new ItemStack(outputItem);
        if (outputStack == null) {
            return ItemStack.EMPTY;
        }
        return outputStack;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        Map<Item, List<ItemWeightedPair>> recipeList = new HashMap<>();

        for (Map.Entry<ResourceLocation, JsonElement> entry : jsons.entrySet()) {
            ResourceLocation key = entry.getKey();
            JsonElement element = entry.getValue();
            AnalyzerRecipeCodec.CODEC.decode(JsonOps.INSTANCE, element)
                    .get()
                    .ifLeft(result -> {
                        AnalyzerRecipeCodec analyzerRecipe = result.getFirst();

                        List<ItemWeightedPair> weightedPairs = new ArrayList<>();
                        for (Item item : analyzerRecipe.getItem()) {
                            weightedPairs = recipeList.getOrDefault(item, new ArrayList<>());
                            weightedPairs.addAll(analyzerRecipe.getItemWeightedPairs());
                            recipeList.put(item, weightedPairs);
                        }
                    })
                    .ifRight(partial -> LOGGER.error("Failed to parse recipe JSON for {} due to: {}", this.folderName, partial.message()));
        }

        this.recipeList = recipeList;
    }
}
