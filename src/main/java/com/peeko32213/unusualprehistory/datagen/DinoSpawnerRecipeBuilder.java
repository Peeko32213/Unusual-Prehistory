package com.peeko32213.unusualprehistory.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.peeko32213.unusualprehistory.core.registry.UPRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.function.Consumer;

public class DinoSpawnerRecipeBuilder {
    private final NonNullList<Ingredient> ingredients;
    private final RecipeSerializer<?> type;
    private final Item result;
    private final EntityType<?> entityType;
    public DinoSpawnerRecipeBuilder(RecipeSerializer<?> pType, NonNullList<Ingredient> ingredients, Item pResult) {
        this.type = pType;
        this.ingredients = ingredients;
        this.result = pResult;
        this.entityType = null;
    }

    public DinoSpawnerRecipeBuilder(RecipeSerializer<?> pType, NonNullList<Ingredient> ingredients, EntityType<?> pResult) {
        this.type = pType;
        this.ingredients = ingredients;
        this.result = null;
        this.entityType = pResult;
    }

    public static DinoSpawnerRecipeBuilder cultivating(NonNullList<Ingredient> ingredients, Item pResult) {
        return new DinoSpawnerRecipeBuilder(UPRecipes.CULTIVATOR_SERIALIZER.get(), ingredients, pResult);
    }

    public static DinoSpawnerRecipeBuilder incubating(NonNullList<Ingredient> ingredients, EntityType<?> pResult) {
        return new DinoSpawnerRecipeBuilder(UPRecipes.INCUBATOR_SERIALIZER.get(), ingredients, pResult);
    }

    public static DinoSpawnerRecipeBuilder analyzing(NonNullList<Ingredient> ingredients, Item pResult) {
        return new DinoSpawnerRecipeBuilder(UPRecipes.ANALYZER_SERIALIZER.get(), ingredients, pResult);
    }

    public void save(Consumer<FinishedRecipe> pRecipeConsumer, String pLocation) {
        this.save(pRecipeConsumer, new ResourceLocation(pLocation));
    }

    public void save(Consumer<FinishedRecipe> pRecipeConsumer, ResourceLocation pLocation) {
        pRecipeConsumer.accept(new DinoSpawnerRecipeBuilder.Result(pLocation, this.type, this.ingredients , this.result, this.entityType));
    }


    public static record Result(ResourceLocation id, RecipeSerializer<?> type, NonNullList<Ingredient> ingredients, Item result, EntityType<?> entityType) implements FinishedRecipe {
        public void serializeRecipeData(JsonObject p_266713_) {
            JsonArray jsonarray = new JsonArray();

            for(Ingredient ingredient : this.ingredients) {
                jsonarray.add(ingredient.toJson());
            }

            p_266713_.add("ingredients", jsonarray);
            if(type != null){
                p_266713_.addProperty("output", BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString());

            }
            if(result !=null) {
                JsonObject jsonobject = new JsonObject();
                jsonobject.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result).toString());
                p_266713_.add("output", jsonobject);
            }
            }

        /**
         * Gets the ID for the recipe.
         */
        public ResourceLocation getId() {
            return this.id;
        }

        public RecipeSerializer<?> getType() {
            return this.type;
        }

        /**
         * Gets the JSON for the advancement that unlocks this recipe. Null if there is no advancement.
         */
        @javax.annotation.Nullable
        public JsonObject serializeAdvancement() {
            return null;
        }

        /**
         * Gets the ID for the advancement associated with this recipe. Should not be null if {@link #getAdvancementJson}
         * is non-null.
         */
        @javax.annotation.Nullable
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
