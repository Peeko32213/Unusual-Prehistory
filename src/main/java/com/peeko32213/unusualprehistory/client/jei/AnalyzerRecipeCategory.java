package com.peeko32213.unusualprehistory.client.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.data.AnalyzerRecipeJsonManager;
import com.peeko32213.unusualprehistory.common.data.ItemWeightedPairCodec;
import com.peeko32213.unusualprehistory.common.recipe.AnalyzerRecipe;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AnalyzerRecipeCategory implements IRecipeCategory<AnalyzerRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(UnusualPrehistory.MODID, "analyzing");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(UnusualPrehistory.MODID, "textures/gui/analyzer_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public AnalyzerRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 80);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(UPBlocks.ANALYZER.get()));
    }

    @Override
    public RecipeType<AnalyzerRecipe> getRecipeType() {
        return JEIPlugin.ANALYZER_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(UnusualPrehistory.MODID + ".blockentity.analyzer_jei");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    private final ItemStack ANALYZER_FLASK = new ItemStack(UPItems.FLASK.get());


    @Override
    public void draw(AnalyzerRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
    }

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AnalyzerRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 33, 5).addItemStack(ANALYZER_FLASK);

        Ingredient ingredient = recipe.getIngredients().get(0);
        ItemStack input = ingredient.getItems()[0];

        builder.addSlot(RecipeIngredientRole.INPUT, 80, 5).addIngredients(ingredient);
        List<ItemStack> outputs = new ArrayList<>();
        List<ItemWeightedPairCodec> itemWeightedPairs = AnalyzerRecipeJsonManager.getItems(input.getItem());
        for (ItemWeightedPairCodec weightedPair : itemWeightedPairs) {
            outputs.add(new ItemStack(weightedPair.getItem()));
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 35, 52).addItemStacks(outputs);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 53, 52).addItemStacks(outputs);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 71, 52).addItemStacks(outputs);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 89, 52).addItemStacks(outputs);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 107, 52).addItemStacks(outputs);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 125, 52).addItemStacks(outputs);
    }


}
