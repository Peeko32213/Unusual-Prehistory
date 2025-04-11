package com.peeko32213.unusualprehistory.client.model;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class ModelLocations {

    public static ModelData AMBER_SHOT = ModelLocationBuilder.create("amber_shot").build();
    public static ModelData JARATE = ModelLocationBuilder.create("jarate").build();
    public static ModelData RABIES = ModelLocationBuilder.create("rabies").build();
    public static ModelData AMMONITE = ModelLocationBuilder.create("ammonite").build();
    public static ModelData ANURO = ModelLocationBuilder.create("anuro").build();
    public static ModelData BARINASUCHUS = ModelLocationBuilder.create("barinasuchus").build();
    public static ModelData BEELZEBUFO = ModelLocationBuilder.create("beelzebufo").build();
    public static ModelData BABY_BEELZEBUFO = ModelLocationBuilder.create("beelze_baby").build();
    public static ModelData ENCRUSTED = ModelLocationBuilder.create("encrusted").build();
    public static ModelData FURACACAUDA = ModelLocationBuilder.create("furacacauda").build();
    public static ModelData HWACHA_SPIKE = ModelLocationBuilder.create("pin").build();
    public static ModelData KIMMER = ModelLocationBuilder.create("kimmeridgebrachypteraeschnidium", "kimmer/kimmeridgebrachypteraeschnidium_pattern_a").build();
    public static ModelData SLUDGE = ModelLocationBuilder.create("sludge").build();
    public static ModelData TALPANAS = ModelLocationBuilder.create("talpanas").build();
    public static ModelData TARTUOSTEUS = ModelLocationBuilder.create("tartuosteus").build();
    public static ModelData REX = ModelLocationBuilder.create("rex").build();
    public static ModelData ANTARCTOPELTA = ModelLocationBuilder.create("antarctopelta").build();
    public static ModelData AUSTRORAPTOR = ModelLocationBuilder.create("austroraptor").build();
    public static ModelData ERYON = ModelLocationBuilder.create("eryon").build();
    public static ModelData PTERODAUSTRO = ModelLocationBuilder.create("pterodaustro").build();

    public static class ModelData {

        private final ResourceLocation model;
        private final ResourceLocation animation;
        private final Map<Integer, ResourceLocation> textures;
        private final float babyHeadScale;
        private final String headBone;

        public ModelData(ResourceLocation model, ResourceLocation animation, Map<Integer, ResourceLocation> textures, float babyHeadScale, String headBone) {
            this.model = model;
            this.animation = animation;
            this.textures = textures;
            this.babyHeadScale = babyHeadScale;
            this.headBone = headBone;
        }

        public ResourceLocation getAnimation() {
            return animation;
        }

        public ResourceLocation getModel() {
            return model;
        }

        public ResourceLocation getTexture(int variant) {
            return textures.getOrDefault(variant, textures.get(0)); // Default texture if variant not found
        }

        public Map<Integer, ResourceLocation> getTextures() {
            return textures;
        }

        public float getBabyHeadScale() {
            return babyHeadScale;
        }

        public String getHeadBone() {
            return headBone;
        }
    }
}
