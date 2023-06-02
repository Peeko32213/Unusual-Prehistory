package com.peeko32213.unusualprehistory.client.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.recipe.CultivatorRecipe;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CultivatorRecipeCategory implements IRecipeCategory<CultivatorRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(UnusualPrehistory.MODID, "cultivator");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(UnusualPrehistory.MODID, "textures/gui/cultivator_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public CultivatorRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 80);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(UPBlocks.CULTIVATOR.get()));
    }

    @Override
    public RecipeType<CultivatorRecipe> getRecipeType() {
        return JEIPlugin.CULTIVATOR_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(UnusualPrehistory.MODID + ".blockentity.cultivator_jei");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }
    private final ItemStack ORGANIC_OOZE = new ItemStack(UPItems.ORGANIC_OOZE.get());
    private final ItemStack FLASK = new ItemStack(UPItems.FLASK.get());

    @Override
    public void draw(CultivatorRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
    }

    public static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CultivatorRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 33, 31).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 64).addItemStack(ORGANIC_OOZE);
        

        builder.addSlot(RecipeIngredientRole.OUTPUT, 128, 31).addItemStack(recipe.getResultItem());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 152, 64).addItemStack(FLASK);
    }
}
