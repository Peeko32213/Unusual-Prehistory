package com.peeko32213.unusualprehistory.common.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.tags.TagKey;
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

    protected static Map<Item, List<ItemWeightedPairCodec>> recipeList = new HashMap<>();
    protected static Map<ResourceLocation, AnalyzerRecipeCodec> recipeListRl = new HashMap<>();
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
    public static Map<Item, List<ItemWeightedPairCodec>> getRecipes() {
        return recipeList;
    }

    public static void setRecipeList(Map<Item, List<ItemWeightedPairCodec>> recipeList) {
        AnalyzerRecipeJsonManager.recipeList = recipeList;
    }

    /**
     * Retrieves the list of item-weighted pairs for the specified item.
     *
     * @param item The item to retrieve the weighted pairs for.
     * @return The list of item-weighted pairs.
     */
    public static List<ItemWeightedPairCodec> getItems(Item item) {
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

        List<ItemWeightedPairCodec> outputs = recipeList.get(input);

        int totalWeight = 0;
        for (ItemWeightedPairCodec itemWeightedPair : outputs) {
            totalWeight += itemWeightedPair.getWeight();
        }

        int randomNr = level.random.nextInt(totalWeight);
        int cumulativeWeight = 0;

        for (ItemWeightedPairCodec itemWeightedPair : outputs) {
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


    /**
     *
     * @param level the level for populating the map
     * @implNote used to populate the transmutationmap, we parse through the tags and items and put them in the map with the corresponding codec
     *
     *
     * */
    public static void populateRecipeMap(Level level) {
        recipeList.clear();
        Map<Item, List<ItemWeightedPairCodec>> weightedPairsTemp = new HashMap<>();
        for (ResourceLocation resourceLocation : recipeListRl.keySet()) {
            AnalyzerRecipeCodec analyzerRecipeCodec = recipeListRl.get(resourceLocation);

            List<ResourceLocation> tagsRL = analyzerRecipeCodec.getInputItemTags();
            List<ResourceLocation> itemsRL = analyzerRecipeCodec.getInputItems();

            for (ResourceLocation tag : tagsRL) {
                TagKey<Item> itemTagKey = TagKey.create(Registry.ITEM_REGISTRY, tag);
                level.registryAccess().registry(Registry.ITEM_REGISTRY).ifPresent(reg -> {
                    Iterable<Holder<Item>> itemHolder = reg.getTagOrEmpty(itemTagKey);

                    for (Holder<Item> tempItemHolder : itemHolder) {
                        Item item = tempItemHolder.get();
                        if(weightedPairsTemp.containsKey(item)) LOGGER.warn("Item {} for resourceLocation {} already has items assigned, adding more", item, resourceLocation);
                        List<ItemWeightedPairCodec> weightedPairsList = weightedPairsTemp.getOrDefault(item, new ArrayList<>());
                        weightedPairsList.addAll(analyzerRecipeCodec.getItemWeightedPairs());
                        weightedPairsTemp.put(item, weightedPairsList);

                    }
                    if (!itemHolder.iterator().hasNext()) {
                        LOGGER.error("Tag for {} does not have any items!", itemTagKey);
                        LOGGER.error("Analyzer recipe for {} might not have any items assigned, or it uses items from a mod, but the mod isn't installed!", resourceLocation);
                    }
                });
            }

            for (ResourceLocation items : itemsRL) {
                level.registryAccess().registry(Registry.ITEM_REGISTRY).ifPresent(reg -> {
                    Item item = reg.get(items);
                    if(item == null) {
                        LOGGER.error("Item {} does not exist, currently parsing through {}!", items, resourceLocation);
                        LOGGER.error("Recipe for {} might not have any items assigned, or it uses items from a mod, but the mod isn't installed!", resourceLocation);
                        return;
                    }
                    if(weightedPairsTemp.containsKey(item)) LOGGER.warn("Item {} for resourceLocation {} already has items assigned, adding more", item, resourceLocation);
                    List<ItemWeightedPairCodec> weightedPairsList = weightedPairsTemp.getOrDefault(item, new ArrayList<>());
                    weightedPairsList.addAll(analyzerRecipeCodec.getItemWeightedPairs());
                    weightedPairsTemp.put(item, weightedPairsList);
                });
            }
        }
        LOGGER.info("Registered {} jsons with items!", weightedPairsTemp.keySet().size());
        recipeList.putAll(weightedPairsTemp);
        weightedPairsTemp.clear();
    }



    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        recipeList.clear();
        Map<ResourceLocation, AnalyzerRecipeCodec> recipeList = new HashMap<>();
        for (Map.Entry<ResourceLocation, JsonElement> entry : jsons.entrySet()) {
            ResourceLocation key = entry.getKey();
            JsonElement element = entry.getValue();
            AnalyzerRecipeCodec.CODEC.decode(JsonOps.INSTANCE, element)
                    .get()
                    .ifLeft(result -> {
                        AnalyzerRecipeCodec analyzerRecipe = result.getFirst();
                        recipeList.put(key, analyzerRecipe);
                    })
                    .ifRight(partial -> LOGGER.error("Failed to parse recipe JSON for {} due to: {}", this.folderName, partial.message()));
        }
        this.recipeListRl = recipeList;
        LOGGER.info("Data loader for {} loaded {} jsons", this.folderName, this.recipeListRl.size());
    }
}
