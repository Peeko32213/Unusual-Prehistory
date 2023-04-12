package com.peeko32213.unusualprehistory.datagen;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class RecipeGenerator extends UPRecipeProvider implements IConditionBuilder {
    public RecipeGenerator(DataGenerator pGenerator) {
        super(pGenerator);
    }
    public static final int FAST_COOKING = 100;		// 5 seconds
    public static final int NORMAL_COOKING = 200;	// 10 seconds
    public static final int SLOW_COOKING = 400;		// 20 seconds
    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        makePlanks(UPBlocks.PETRIFIED_WOOD_PLANKS, UPTags.PETRIFIED_WOOD).save(consumer);

        makeWood(UPBlocks.PETRIFIED_WOOD, UPBlocks.PETRIFIED_WOOD_LOG).save(consumer);

        makeSign(UPBlocks.PETRIFIED_WOOD_SIGN, UPBlocks.PETRIFIED_WOOD_PLANKS).save(consumer);

        makeStairs(UPBlocks.PETRIFIED_WOOD_STAIRS, UPBlocks.PETRIFIED_WOOD_PLANKS).save(consumer);
        makeStairs(UPBlocks.POLISHED_PETRIFIED_WOOD_STAIRS, UPBlocks.POLISHED_PETRIFIED_WOOD).save(consumer);

        makeSlab(UPBlocks.PETRIFIED_WOOD_SLAB, UPBlocks.PETRIFIED_WOOD_PLANKS).save(consumer);

        makeFence(UPBlocks.PETRIFIED_WOOD_FENCE, UPBlocks.PETRIFIED_WOOD_PLANKS).save(consumer);

        makeFenceGate(UPBlocks.PETRIFIED_WOOD_FENCE_GATE, UPBlocks.PETRIFIED_WOOD_PLANKS).save(consumer);

        makeDoor(UPBlocks.PETRIFIED_WOOD_DOOR, UPBlocks.PETRIFIED_WOOD_PLANKS).save(consumer);

        makeTrapdoor(UPBlocks.PETRIFIED_WOOD_TRAPDOOR, UPBlocks.PETRIFIED_WOOD_PLANKS).save(consumer);

        makeButton(UPBlocks.PETRIFIED_WOOD_BUTTON, UPBlocks.PETRIFIED_WOOD_PLANKS).save(consumer);

        makePressurePlate(UPBlocks.PETRIFIED_WOOD_PRESSURE_PLATE, UPBlocks.PETRIFIED_WOOD_PLANKS).save(consumer);

        makeBricks(UPBlocks.POLISHED_PETRIFIED_WOOD, UPBlocks.PETRIFIED_WOOD).save(consumer);


        petrifiedWoodStonecutting(UPBlocks.POLISHED_PETRIFIED_WOOD.get()).save(consumer, name("polished_petrified_wood_stonecutting"));
        petrifiedWoodStonecutting(UPBlocks.POLISHED_PETRIFIED_WOOD_SLAB.get(), 2).save(consumer, name("polished_petrified_wood_slab_stonecutting"));
        petrifiedWoodStonecutting(UPBlocks.POLISHED_PETRIFIED_WOOD_STAIRS.get()).save(consumer, name("polished_petrified_wood_stairs_stonecutting"));

        polishedPetrifiedWoodStonecutting(UPBlocks.POLISHED_PETRIFIED_WOOD_SLAB.get(), 2).save(consumer, name("polished_petrified_wood_to_polished_petrified_wood_slab_stonecutting"));
        polishedPetrifiedWoodStonecutting(UPBlocks.POLISHED_PETRIFIED_WOOD_STAIRS.get()).save(consumer, name("polished_petrified_wood_to_polished_petrified_wood_stairs_stonecutting"));

        //oreSmelting();
    }



    //Wrappers for conditionals
    private void wrap(RecipeBuilder builder, String name, Consumer<FinishedRecipe> consumer, ICondition... conds) {
        wrap(builder, UnusualPrehistory.MODID, name, consumer, conds);
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
