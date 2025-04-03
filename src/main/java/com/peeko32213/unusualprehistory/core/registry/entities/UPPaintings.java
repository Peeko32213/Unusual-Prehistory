package com.peeko32213.unusualprehistory.core.registry.entities;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public final class UPPaintings {
    public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, UnusualPrehistory.MODID);
    public static Map<String, String> PAINTING_TRANSLATIONS = new HashMap<>();

    // Paintings
    public static final RegistryObject<PaintingVariant> PERISCOPE = painting("periscope", "ChipsTheCat", 64, 48);

    public static RegistryObject<PaintingVariant> painting(String name, String author, int width, int height) {
        PAINTING_TRANSLATIONS.put(name, author);
        return PAINTING_VARIANTS.register(name, () -> new PaintingVariant(width, height));
    }
}