package com.peeko32213.unusualprehistory.data.server.recipes;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.blocks.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static com.peeko32213.unusualprehistory.data.client.models.UPItemModelGenerator.prefix;

public class UPRecipeGenerator extends UPRecipeProvider implements IConditionBuilder {
    public UPRecipeGenerator(PackOutput pGenerator) {
        super(pGenerator);
    }
    public static final int FAST_COOKING = 100;		// 5 seconds
    public static final int NORMAL_COOKING = 200;	// 10 seconds
    public static final int SLOW_COOKING = 300;		// 15 seconds
    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {

        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, UPItems.OPAL.get(), RecipeCategory.BUILDING_BLOCKS,UPBlocks.OPAL_BLOCK.get().asItem());
        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, UPItems.FIRE_OPAL.get(), RecipeCategory.BUILDING_BLOCKS,UPBlocks.FIRE_OPAL_BLOCK.get().asItem());
        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, UPItems.BOULDER_OPAL.get(), RecipeCategory.BUILDING_BLOCKS,UPBlocks.BOULDER_OPAL_BLOCK.get().asItem());
        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, UPItems.BLACK_OPAL.get(), RecipeCategory.BUILDING_BLOCKS,UPBlocks.BLACK_OPAL_BLOCK.get().asItem());

        //oreSmelting();

        // cultivating
        cultivating(consumer, UPItems.KIMMER_DNA.get(), UPBlocks.KIMMER_EGGS.get().asItem());
    }



    protected static void incubating(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemStack ingredients, EntityType<?> result){
        Ingredient ingredient = Ingredient.of(ingredients);
        NonNullList<Ingredient> ingredients1 = NonNullList.of(Ingredient.EMPTY, ingredient);
        UPCustomRecipeBuilder.incubating(ingredients1, result).save(finishedRecipeConsumer, prefix("incubating/" + getEntityName(result) + "_from_incubating"));
    }

    protected static void cultivating(Consumer<FinishedRecipe> finishedRecipeConsumer, Item ingredients, Item result){
        Ingredient ingredient = Ingredient.of(ingredients);
        NonNullList<Ingredient> ingredients1 = NonNullList.of(Ingredient.EMPTY, ingredient);
        UPCustomRecipeBuilder.cultivating(ingredients1, result).save(finishedRecipeConsumer, prefix("cultivating/" + getItemName(result) + "_from_cultivating"));
    }

    protected static void cultivating(Consumer<FinishedRecipe> finishedRecipeConsumer, Item ingredients, EntityType<?> result){
        Ingredient ingredient = Ingredient.of(ingredients);
        NonNullList<Ingredient> ingredients1 = NonNullList.of(Ingredient.EMPTY, ingredient);
        String name = BuiltInRegistries.ENTITY_TYPE.getKey(result).toString();
        Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(name + "_entity_egg"));
        UPCustomRecipeBuilder.cultivating(ingredients1, item).save(finishedRecipeConsumer, prefix("cultivating/" + getItemName(item) + "_from_cultivating"));
    }

    //Wrappers for conditionals
    private void wrap(RecipeBuilder builder, String name, Consumer<FinishedRecipe> consumer, ICondition... conds) {
        wrap(builder, UnusualPrehistory.MODID, name, consumer, conds);
    }

    protected static String getEntityName(EntityType<?> pItemLike) {
        return BuiltInRegistries.ENTITY_TYPE.getKey(pItemLike).getPath();
    }
    private ResourceLocation name(String name) {
        return new ResourceLocation(UnusualPrehistory.MODID, name);
    }

    private void wrap(RecipeBuilder builder, String modid, String name, Consumer<FinishedRecipe> consumer, ICondition... conds) {
        ResourceLocation loc = new ResourceLocation(modid, name);
        ConditionalRecipe.Builder cond;
        if (conds.length > 1) {
            cond = ConditionalRecipe.builder().addCondition(and(conds));
        } else if (conds.length == 1) {
            cond = ConditionalRecipe.builder().addCondition(conds[0]);
        } else {
            cond = ConditionalRecipe.builder();
        }
        FinishedRecipe[] recipe = new FinishedRecipe[1];
        builder.save(f -> recipe[0] = f, loc);
        cond.addRecipe(recipe[0])
                .generateAdvancement()
                .build(consumer, loc);
    }
}