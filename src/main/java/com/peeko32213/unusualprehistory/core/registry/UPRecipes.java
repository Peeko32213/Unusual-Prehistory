package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.recipe.AnalyzerRecipe;
import com.peeko32213.unusualprehistory.common.recipe.CultivatorRecipe;
import com.peeko32213.unusualprehistory.common.recipe.IncubatorRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class UPRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, UnusualPrehistory.MODID);

   public static final RegistryObject<RecipeSerializer<AnalyzerRecipe>> ANALYZER_SERIALIZER =
           SERIALIZERS.register("analyzing", () -> AnalyzerRecipe.Serializer.INSTANCE);

   public static final RegistryObject<RecipeSerializer<CultivatorRecipe>> CULTIVATOR_SERIALIZER =
           SERIALIZERS.register("cultivator", () -> CultivatorRecipe.Serializer.INSTANCE);

   public static final RegistryObject<RecipeSerializer<IncubatorRecipe>> INCUBATOR_SERIALIZER =
           SERIALIZERS.register("incubating", () -> IncubatorRecipe.Serializer.INSTANCE);

}
