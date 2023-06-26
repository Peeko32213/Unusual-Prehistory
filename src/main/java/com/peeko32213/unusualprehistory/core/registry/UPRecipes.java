package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.recipe.AnalyzerRecipe;
import com.peeko32213.unusualprehistory.common.recipe.CultivatorRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class UPRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, UnusualPrehistory.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, UnusualPrehistory.MODID);


    public static final RegistryObject<RecipeSerializer<AnalyzerRecipe>> ANALYZER_SERIALIZER =
            SERIALIZERS.register("analyzing", () -> AnalyzerRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<CultivatorRecipe>> CULTIVATOR_SERIALIZER =
            SERIALIZERS.register("cultivator", () -> CultivatorRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeType<AnalyzerRecipe>> ANALYZING_TYPE =
            RECIPE_TYPES.register("analyzing", () -> RecipeType.simple(prefix("analyzing")));
    public static final RegistryObject<RecipeType<CultivatorRecipe>> CULTIVATOR_TYPE =
            RECIPE_TYPES.register("cultivator", () -> RecipeType.simple(prefix("cultivator")));



}
