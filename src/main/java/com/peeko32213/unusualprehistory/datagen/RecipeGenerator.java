package com.peeko32213.unusualprehistory.datagen;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
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

import static com.peeko32213.unusualprehistory.datagen.ItemModelGenerator.prefix;

public class RecipeGenerator extends UPRecipeProvider implements IConditionBuilder {
    public RecipeGenerator(PackOutput pGenerator) {
        super(pGenerator);
    }
    public static final int FAST_COOKING = 100;		// 5 seconds
    public static final int NORMAL_COOKING = 200;	// 10 seconds
    public static final int SLOW_COOKING = 300;		// 15 seconds
    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {


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

        // Foxxi
        makePlanks(UPBlocks.FOXXI_PLANKS, UPTags.FOXXI).save(consumer);

        makeWood(UPBlocks.FOXXI_WOOD, UPBlocks.FOXXI_LOG).save(consumer);

        makeStairs(UPBlocks.FOXXI_STAIRS, UPBlocks.FOXXI_PLANKS).save(consumer);

        makeSlab(UPBlocks.FOXXI_SLAB, UPBlocks.FOXXI_PLANKS).save(consumer);

        makeFence(UPBlocks.FOXXI_FENCE, UPBlocks.FOXXI_PLANKS).save(consumer);

        makeFenceGate(UPBlocks.FOXXI_FENCE_GATE, UPBlocks.FOXXI_PLANKS).save(consumer);

        makeDoor(UPBlocks.FOXXI_DOOR, UPBlocks.FOXXI_PLANKS).save(consumer);

        makeTrapdoor(UPBlocks.FOXXI_TRAPDOOR, UPBlocks.FOXXI_PLANKS).save(consumer);

        makeButton(UPBlocks.FOXXI_BUTTON, UPBlocks.FOXXI_PLANKS).save(consumer);

        makePressurePlate(UPBlocks.FOXXI_PRESSURE_PLATE, UPBlocks.FOXXI_PLANKS).save(consumer);

        makeSign(UPBlocks.FOXII_SIGN, UPBlocks.FOXXI_PLANKS).save(consumer);

        // Dryo
        makePlanks(UPBlocks.DRYO_PLANKS, UPTags.DRYO).save(consumer);

        makeWood(UPBlocks.DRYO_WOOD, UPBlocks.DRYO_LOG).save(consumer);

        makeStairs(UPBlocks.DRYO_STAIRS, UPBlocks.DRYO_PLANKS).save(consumer);

        makeSlab(UPBlocks.DRYO_SLAB, UPBlocks.DRYO_PLANKS).save(consumer);

        makeFence(UPBlocks.DRYO_FENCE, UPBlocks.DRYO_PLANKS).save(consumer);

        makeFenceGate(UPBlocks.DRYO_FENCE_GATE, UPBlocks.DRYO_PLANKS).save(consumer);

        makeDoor(UPBlocks.DRYO_DOOR, UPBlocks.DRYO_PLANKS).save(consumer);

        makeTrapdoor(UPBlocks.DRYO_TRAPDOOR, UPBlocks.DRYO_PLANKS).save(consumer);

        makeButton(UPBlocks.DRYO_BUTTON, UPBlocks.DRYO_PLANKS).save(consumer);

        makePressurePlate(UPBlocks.DRYO_PRESSURE_PLATE, UPBlocks.DRYO_PLANKS).save(consumer);

        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, UPItems.OPALESCENT_PEARL.get(), RecipeCategory.BUILDING_BLOCKS,UPBlocks.OPAL_BLOCK.get().asItem());

//        makeSign(UPBlocks.DRYO_SIGN, UPBlocks.DRYO_PLANKS).save(consumer);

        //oreSmelting();

        // Zuloagae
        makePlanks(UPBlocks.ZULOAGAE_PLANKS, UPTags.ZULOAGAE).save(consumer);

        makeStairs(UPBlocks.ZULOAGAE_STAIRS, UPBlocks.ZULOAGAE_PLANKS).save(consumer);

        makeSlab(UPBlocks.ZULOAGAE_SLAB, UPBlocks.ZULOAGAE_PLANKS).save(consumer);

        makeFence(UPBlocks.ZULOAGAE_FENCE, UPBlocks.ZULOAGAE_PLANKS).save(consumer);

        makeFenceGate(UPBlocks.ZULOAGAE_FENCE_GATE, UPBlocks.ZULOAGAE_PLANKS).save(consumer);

        makeDoor(UPBlocks.ZULOAGAE_DOOR, UPBlocks.ZULOAGAE_PLANKS).save(consumer);

        makeTrapdoor(UPBlocks.ZULOAGAE_TRAPDOOR, UPBlocks.ZULOAGAE_PLANKS).save(consumer);

        makeButton(UPBlocks.ZULOAGAE_BUTTON, UPBlocks.ZULOAGAE_PLANKS).save(consumer);

        makePressurePlate(UPBlocks.ZULOAGAE_PRESSURE_PLATE, UPBlocks.ZULOAGAE_PLANKS).save(consumer);

        // cultivating
        cultivating(consumer, UPItems.KIMMER_FLASK.get(), UPBlocks.KIMMER_EGGS.get().asItem());

        cultivating(consumer, UPItems.BALAUR_FLASK.get(), UPEntities.BALAUR.get());
    }



    protected static void incubating(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemStack ingredients, EntityType<?> result){
        Ingredient ingredient = Ingredient.of(ingredients);
        NonNullList<Ingredient> ingredients1 = NonNullList.of(Ingredient.EMPTY, ingredient);
        PrehistoricSpawnerRecipeBuilder.incubating(ingredients1, result).save(finishedRecipeConsumer, prefix("incubating/" + getEntityName(result) + "_from_incubating"));
    }

    protected static void cultivating(Consumer<FinishedRecipe> finishedRecipeConsumer, Item ingredients, Item result){
        Ingredient ingredient = Ingredient.of(ingredients);
        NonNullList<Ingredient> ingredients1 = NonNullList.of(Ingredient.EMPTY, ingredient);
        PrehistoricSpawnerRecipeBuilder.cultivating(ingredients1, result).save(finishedRecipeConsumer, prefix("cultivating/" + getItemName(result) + "_from_cultivating"));
    }

    protected static void cultivating(Consumer<FinishedRecipe> finishedRecipeConsumer, Item ingredients, EntityType<?> result){
        Ingredient ingredient = Ingredient.of(ingredients);
        NonNullList<Ingredient> ingredients1 = NonNullList.of(Ingredient.EMPTY, ingredient);
        String name = BuiltInRegistries.ENTITY_TYPE.getKey(result).toString();
        Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(name + "_entity_egg"));
        PrehistoricSpawnerRecipeBuilder.cultivating(ingredients1, item).save(finishedRecipeConsumer, prefix("cultivating/" + getItemName(item) + "_from_cultivating"));
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

//        incubating(consumer, UPItems.OTAROCYON_EMBRYO.get().getDefaultInstance(), UPEntities.OTAROCYON.get());
//        cultivating(consumer, UPItems.LONGI_FLASK.get(), UPEntities.LONGISQUAMA.get());
//        cultivating(consumer, UPItems.TARTUO_FLASK.get(), UPBlocks.TARTUO_EGGS.get().asItem());
//        cultivating(consumer, UPItems.TANY_FLASK.get(), UPEntities.TANY.get());
//        cultivating(consumer, UPItems.PSITTACO_FLASK.get(), UPEntities.PSITTACO.get());
//        cultivating(consumer, UPItems.KAPRO_FLASK.get(), UPEntities.KAPROSUCHUS.get());
//        cultivating(consumer, UPItems.PSILO_FLASK.get(), UPEntities.PSILOPTERUS.get());
//        cultivating(consumer, UPItems.OPHIO_FLASK.get(), UPBlocks.OPHIDION_EGGS.get().asItem());
//        cultivating(consumer, UPItems.DIPLO_FLASK.get(), UPBlocks.DIPLOCAULUS_EGGS.get().asItem());
//        cultivating(consumer, UPItems.HYNERIA_FLASK.get(), UPBlocks.HYNERIA_EGGS.get().asItem());
//        cultivating(consumer, UPItems.HYNERP_FLASK.get(), UPEntities.HYNERPETON.get());
//        cultivating(consumer, UPItems.LEEDS_FLASK.get(), UPEntities.LEEDSICHTHYS.get());
//        cultivating(consumer, UPItems.PTERODAUSTRO_FLASK.get(), UPEntities.PTERODAUSTRO.get());
//        cultivating(consumer, UPItems.ARCHELON_FLASK.get(), UPEntities.ARCHELON.get());
//        cultivating(consumer, UPItems.PROTOSPHYRAENA_FLASK.get(), UPEntities.PROTOSPHYRAENA.get());
//        cultivating(consumer, UPItems.JAWLESS_FISH_FLASK.get(), UPBlocks.FURCACAUDA_EGGS.get().asItem());