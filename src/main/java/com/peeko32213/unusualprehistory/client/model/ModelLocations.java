package com.peeko32213.unusualprehistory.client.model;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.resources.ResourceLocation;

public class ModelLocations {

    public static ModelData AMBER_SHOT = new ModelData("amber_shot");
    public static ModelData JARATE = new ModelData("jarate");
    public static ModelData AMMONITE = new ModelData("ammonite");
    public static ModelData ANURO = new ModelData("anuro");
    public static ModelData BABY_BARINA = new ModelData("baby_barinasuchus");
    public static ModelData BABY_BRACHI = new ModelData("baby_brachi");
    public static ModelData BABY_DUNK = new ModelData("baby_dunk");
    public static ModelData BABY_GIGAN = new ModelData("baby_gigantopithicus");
    public static ModelData BABY_MAMMOTH = new ModelData("baby_mammoth");
    public static ModelData BABY_MEGATHERIUM = new ModelData("baby_megatherium");
    public static ModelData BABY_MEGALANIA = new ModelData("baby_megalania");
    public static ModelData BABY_PALEOLOPHIS = new ModelData("baby_palaeophis");
    public static ModelData BABY_PARACERATHERIUM = new ModelData("baby_paraceratherium");
    public static ModelData BABY_REX = new ModelData("baby_rex");
    public static ModelData BABY_SMILODON = new ModelData("baby_smilodon");
    public static ModelData BARINASUCHUS = new ModelData("barinasuchus");
    public static ModelData BEELZEBUFO = new ModelData("beelzebufo");
    public static ModelData BABY_BEELZEBUFO = new ModelData("beelze_baby");
    public static ModelData BOOK_SNAKE = new ModelData("palaeophis");
    public static ModelData BRACHIOSAURUS = new ModelData("brachiosaurus");
    public static ModelData BRACHI_TEEN = new ModelData("brachi_teen");
    public static ModelData DIPLOCAULUS = new ModelData("diplocaulus");
    public static ModelData ENCRUSTED = new ModelData("encrusted");
    public static ModelData FURACACAUDA = new ModelData("furacacauda");
    public static ModelData GIGANTOPITHICUS = new ModelData("gigantopithicus");
    public static ModelData HWACHA_SPIKE = new ModelData("pin");
    public static ModelData HYPERNETON = new ModelData("hynerpeton");
    public static ModelData KIMMER = new ModelData("kimmeridgebrachypteraeschnidium", "kimmer/kimmeridgebrachypteraeschnidium_pattern_a");
    public static ModelData MAMMOTH = new ModelData("mammoth");
    public static ModelData MEGALANIA = new ModelData("megalania");
    public static ModelData MEGATHERIUM = new ModelData("megatherium");
    public static ModelData PALAEOPHIS_HEAD = new ModelData("palaeophis_head");
    public static ModelData PARACERATHERIUM = new ModelData("paraceratherium");
    public static ModelData SCAUMENACIA = new ModelData("scaumenacia");
    public static ModelData SLUDGE = new ModelData("sludge");
    public static ModelData SMILODON = new ModelData("smilodon");
    public static ModelData STETHACANTUS = new ModelData("stethacanthus");
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
