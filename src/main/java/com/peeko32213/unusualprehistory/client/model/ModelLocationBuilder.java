package com.peeko32213.unusualprehistory.client.model;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ModelLocationBuilder {
    private final String modid;
    private final String entityName;
    private final String texture;
    private final Map<Integer, ResourceLocation> textures = new HashMap<>();
    private float babyHeadScale = 1F;
    private String headBone = "head";

    private ModelLocationBuilder(String modid, String entityName, @Nullable String texture) {
        this.modid = modid;
        this.texture = texture;
        this.entityName = entityName;
        setDefaultTexture(texture == null ? entityName : texture);
    }

    public ModelLocationBuilder addVariant(int variant, String textureName) {
        textures.put(variant, new ResourceLocation(modid, "textures/entity/" + textureName + ".png"));
        return this;
    }

    public ModelLocationBuilder addVariantWithPath(int variant, String path, String textureName) {
        textures.put(variant, new ResourceLocation(modid, "textures/entity/" + path + "/" + textureName + ".png"));
        return this;
    }

    public ModelLocationBuilder setDefaultTexture(String textureName) {
        textures.put(0, new ResourceLocation(modid, "textures/entity/" + textureName + ".png"));
        return this;
    }

    public ModelLocationBuilder setBabyHeadScale(float scale) {
        this.babyHeadScale = scale;
        return this;
    }

    public ModelLocationBuilder setHeadBone(String headBone) {
        this.headBone = headBone;
        return this;
    }

    public ModelLocations.ModelData build() {
        return new ModelLocations.ModelData(
                new ResourceLocation(modid, "geo/" + entityName + ".geo.json"),
                new ResourceLocation(modid, "animations/" + entityName + ".animation.json"),
                textures,
                babyHeadScale,
                headBone
        );
    }


    public static ModelLocationBuilder create(String entityName) {
        return new ModelLocationBuilder(UnusualPrehistory.MODID, entityName, null);
    }

    public static ModelLocationBuilder create(String entityName, String texture) {
        return new ModelLocationBuilder(UnusualPrehistory.MODID, entityName, texture);
    }
}

// --- Static Factory Method to Avoid Typing 'new' ---



