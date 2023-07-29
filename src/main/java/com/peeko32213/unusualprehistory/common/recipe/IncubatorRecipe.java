package com.peeko32213.unusualprehistory.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class IncubatorRecipe implements Recipe<SimpleContainer> {


    //A weighted example https://github.com/skyjay1/Pelagic-Prehistory/blob/main-1.19.2/src/main/java/pelagic_prehistory/recipe/AnalyzerRecipe.java
    private final ResourceLocation id;
    private final ResourceLocation output;
    private final NonNullList<Ingredient> recipeItems;

    public IncubatorRecipe(ResourceLocation id,ResourceLocation output,
                           NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return !recipeItems.isEmpty() && recipeItems.get(0).test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        //This does nothing
        return ItemStack.EMPTY;
    }



    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    public ResourceLocation getOutput() {
        return output;
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
        return UPRecipes.INCUBATOR_TYPE.get();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    public static class Serializer implements RecipeSerializer<IncubatorRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(UnusualPrehistory.MODID,"incubating");

        @Override
        public IncubatorRecipe fromJson(ResourceLocation id, JsonObject json) {
            ResourceLocation output = ResourceLocation.tryParse(GsonHelper.getAsString(json, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new IncubatorRecipe(id,output ,inputs);
        }

        @Override
        public IncubatorRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ResourceLocation output = buf.readResourceLocation();
            return new IncubatorRecipe(id,output , inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, IncubatorRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeResourceLocation(recipe.output);
        }
    }
}
