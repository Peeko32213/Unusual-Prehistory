package com.peeko32213.unusualprehistory.client.model;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.resources.ResourceLocation;

public class ModelLocations {

    public static ModelData AMBER_SHOT = new ModelData("amber_shot");
    public static ModelData JARATE = new ModelData("jarate");
    public static ModelData AMMONITE = new ModelData("ammonite");
    public static ModelData ANURO = new ModelData("anuro");
    public static ModelData BABY_DUNK = new ModelData("baby_dunk");
    public static ModelData BARINASUCHUS = new ModelData("barinasuchus");
    public static ModelData BEELZEBUFO = new ModelData("beelzebufo");
    public static ModelData BABY_BEELZEBUFO = new ModelData("beelze_baby");
    public static ModelData BOOK_SNAKE = new ModelData("palaeophis/palaeophis");
    public static ModelData ENCRUSTED = new ModelData("encrusted");
    public static ModelData FURACACAUDA = new ModelData("furacacauda");
    public static ModelData HWACHA_SPIKE = new ModelData("pin");
    public static ModelData HYPERNETON = new ModelData("hynerpeton");
    public static ModelData KIMMER = new ModelData("kimmeridgebrachypteraeschnidium", "kimmer/kimmeridgebrachypteraeschnidium_pattern_a");
    public static ModelData SLUDGE = new ModelData("sludge");
    public static ModelData TALPANAS = new ModelData("talpanas");
    public static ModelData TARTUOSTEUS = new ModelData("tartuosteus");
    public static ModelData REX = new ModelData("rex");
    public static ModelData ANTARCTOPELTA = new ModelData("antarctopelta");
    public static ModelData AUSTRORAPTOR = new ModelData("austroraptor");
    public static ModelData ERYON = new ModelData("eryon");
    public static ModelData PTERODAUSTRO = new ModelData("pterodaustro");

    public static class ModelData {
        private ResourceLocation model;
        private ResourceLocation texture;
        private ResourceLocation animation;


        public ModelData(String name, String texture){
            this.model = new ResourceLocation(UnusualPrehistory.MODID, "geo/" + name + ".geo.json");
            this.texture = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/" + texture + ".png");
            this.animation = new ResourceLocation(UnusualPrehistory.MODID, "animations/" + name + ".animation.json");
        }
        public ModelData(String name){
            this.model = new ResourceLocation(UnusualPrehistory.MODID, "geo/" + name + ".geo.json");
            this.texture = new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/" + name + ".png");
            this.animation = new ResourceLocation(UnusualPrehistory.MODID, "animations/" + name + ".animation.json");
        }


        public ResourceLocation getAnimation() {
            return animation;
        }

        public ResourceLocation getModel() {
            return model;
        }

        public ResourceLocation getTexture() {
            return texture;
        }

    }
}
