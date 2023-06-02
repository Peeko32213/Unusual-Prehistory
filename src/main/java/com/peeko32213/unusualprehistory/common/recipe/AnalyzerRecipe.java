package com.peeko32213.unusualprehistory.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.data.AnalyzerRecipeCodec;
import com.peeko32213.unusualprehistory.common.data.ItemWeightedPair;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class AnalyzerRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    //private final ResourceLocation output;
    //private final NonNullList<Ingredient> recipeItems;
    private Map<Item, List<ItemWeightedPair>> recipes = new HashMap();

    public AnalyzerRecipe(Map<Item, List<ItemWeightedPair>> recipes, ResourceLocation id) {
        this.recipes = recipes;
        this.id = id;
        //this.output = output;
        //this.recipeItems = recipeItems;
    }



    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return !recipes.isEmpty() && recipes.containsKey(pContainer.getItem(1));
    }
    public static final Logger LOGGER = LogManager.getLogger();
    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        ServerLevel level = server.getLevel(Level.OVERWORLD);
        // ensure level exists
        if(null == level) {
            return ItemStack.EMPTY;
        }

        Item outputItem = null;
        ItemStack inputStack = pContainer.getItem(1);
        List<ItemWeightedPair> outputs = recipes.get(inputStack.getItem());

        int totalWeight = 0;
        for(ItemWeightedPair itemWeightedPair : outputs){
            totalWeight+=itemWeightedPair.getWeight();
        }

        int randomNr = level.random.nextInt(totalWeight);
        int cumulativeWeight = 0;

        for(ItemWeightedPair itemWeightedPair : outputs){
            cumulativeWeight+= itemWeightedPair.getWeight();
            if(randomNr < cumulativeWeight){
                outputItem = itemWeightedPair.getItem();
                break;
            }
        }

        ItemStack outputStack = new ItemStack(outputItem);
        if(outputStack == null){
            return ItemStack.EMPTY;
        }
        return outputStack;
    }



    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    //public ResourceLocation getResultLootTable() {
    //    return output;
    //}

    public static class Type implements RecipeType<AnalyzerRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
    }

    //@Override
    //public NonNullList<Ingredient> getIngredients() {
    //    return recipeItems;
    //}

    public static class Serializer implements RecipeSerializer<AnalyzerRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(UnusualPrehistory.MODID,"analyzing");
        Map<Item, List<ItemWeightedPair>> recipeList = new HashMap();
        @Override
        public AnalyzerRecipe fromJson(ResourceLocation id, JsonObject json) {
            //ResourceLocation output = ResourceLocation.tryParse(GsonHelper.getAsString(json, "output"));
            //JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            //NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            Map<Item, List<ItemWeightedPair>> recipeList = new HashMap();
            AnalyzerRecipeCodec.CODEC.decode(JsonOps.INSTANCE, json)
                    .get()
                            .ifLeft(result -> {
                                AnalyzerRecipeCodec analyzerRecipe = result.getFirst();
                                List<ItemWeightedPair> weightedPairs = recipeList.getOrDefault(analyzerRecipe.getItem(), new ArrayList<>());
                                weightedPairs.addAll(analyzerRecipe.getItemWeightedPairs());
                                recipeList.put(analyzerRecipe.getItem(), weightedPairs);
                            })
                                    .ifRight(partial -> LOGGER.error("Failed to parse recipe json for {} due to: {}", "analyzers", partial.message()));

            //for (int i = 0; i < inputs.size(); i++) {
            //    inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            //}
            this.recipeList = recipeList;
            return new AnalyzerRecipe(recipeList,id);
        }

        @Override
        public AnalyzerRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ResourceLocation output = buf.readResourceLocation();
            return new AnalyzerRecipe(recipeList, id);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, AnalyzerRecipe recipe) {
           buf.writeInt(recipe.getIngredients().size());
           //for (List<ItemWeightedPair> ing : recipe.recipes.values().stream().toList()) {
           //    for(ItemWeightedPair itemWeightedPair : ing){
           //        Ingredient ingredient = Ingredient.of(itemWeightedPair.getItem());
           //        ingredient.toNetwork(buf);
           //    }
           //}
          // buf.writeResourceLocation(recipe.output);
        }


        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return INSTANCE;
        }


        public ResourceLocation getRegistryName() {
            return ID;
        }


        public Class<RecipeSerializer<?>> getRegistryType() {
            return Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}
