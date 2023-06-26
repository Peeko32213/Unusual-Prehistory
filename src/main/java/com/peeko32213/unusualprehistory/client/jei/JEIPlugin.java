package com.peeko32213.unusualprehistory.client.jei;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.recipe.AnalyzerRecipe;
import com.peeko32213.unusualprehistory.common.recipe.CultivatorRecipe;
import com.peeko32213.unusualprehistory.core.registry.UPRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    public static RecipeType<AnalyzerRecipe> ANALYZER_TYPE =
            new RecipeType<>(AnalyzerRecipeCategory.UID, AnalyzerRecipe.class);
    public static RecipeType<CultivatorRecipe> CULTIVATOR_TYPE =
            new RecipeType<>(CultivatorRecipeCategory.UID, CultivatorRecipe.class);
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(UnusualPrehistory.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new
                AnalyzerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new
                CultivatorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }




    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<AnalyzerRecipe> recipesAnalyzer = rm.getAllRecipesFor(UPRecipes.ANALYZING_TYPE.get());
        registration.addRecipes(ANALYZER_TYPE, recipesAnalyzer);
        List<CultivatorRecipe> recipesCultivator = rm.getAllRecipesFor(UPRecipes.CULTIVATOR_TYPE.get());
        registration.addRecipes(CULTIVATOR_TYPE, recipesCultivator);

    }
}
