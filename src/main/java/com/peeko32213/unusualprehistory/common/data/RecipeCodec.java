package com.peeko32213.unusualprehistory.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public class RecipeCodec {
    public static Codec<RecipeCodec> CODEC = RecordCodecBuilder.create(inst -> inst
            .group(
                    ResourceLocation.CODEC.fieldOf("recipe").forGetter(r -> r.recipe),
                    Codec.intRange(0, 385).optionalFieldOf("x",0).forGetter(r -> r.x),
                    Codec.intRange(0, 194).optionalFieldOf("y",0).forGetter(r -> r.y),
                    Codec.BOOL.optionalFieldOf("shapeless", false).forGetter(s -> s.shapeless),
                    Codec.doubleRange(0, 5).optionalFieldOf("scale",1D).forGetter(r -> r.scale),
                    Codec.INT.fieldOf("page").forGetter(r -> r.page)

                    ).apply(inst, RecipeCodec::new)
    );



    private ResourceLocation recipe;
    private boolean shapeless;
    private int x;
    private int y;
    private double scale;
    private int page;

    public RecipeCodec(ResourceLocation recipe, int x, int y, boolean shapeless, double scale, int page) {
        this.recipe = recipe;
        this.x = x;
        this.y = y;
        this.shapeless = shapeless;
        this.scale = scale;
        this.page = page;
    }

    public ResourceLocation getRecipe() {
        return recipe;
    }

    public void setRecipe(ResourceLocation recipe) {
        this.recipe = recipe;
    }

    public int getPageNr() {
        return page;
    }

    public int getXLocation() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getYLocation() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }
}
