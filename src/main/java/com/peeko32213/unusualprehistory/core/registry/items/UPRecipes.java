package com.peeko32213.unusualprehistory.core.registry.items;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.recipe.AnalyzerRecipe;
import com.peeko32213.unusualprehistory.common.recipe.CultivatorRecipe;
import com.peeko32213.unusualprehistory.common.recipe.IncubatorRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UPRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, UnusualPrehistory.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPE_DEF_REG = DeferredRegister.create(Registries.RECIPE_TYPE, UnusualPrehistory.MODID);

   public static final RegistryObject<RecipeSerializer<AnalyzerRecipe>> ANALYZER_SERIALIZER =
           SERIALIZERS.register("analyzing", () -> AnalyzerRecipe.Serializer.INSTANCE);

   public static final RegistryObject<RecipeSerializer<CultivatorRecipe>> CULTIVATOR_SERIALIZER =
           SERIALIZERS.register("cultivator", () -> CultivatorRecipe.Serializer.INSTANCE);

   public static final RegistryObject<RecipeSerializer<IncubatorRecipe>> INCUBATOR_SERIALIZER =
           SERIALIZERS.register("incubating", () -> IncubatorRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeType<CultivatorRecipe>> CULTIVATOR_RECIPE = TYPE_DEF_REG.register("cultivator", () -> new RecipeType<>() {
    });
}
