package com.peeko32213.unusualprehistory.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public class EncyclopediaPictureCodec {

    public static Codec<EncyclopediaPictureCodec> CODEC = RecordCodecBuilder.create(inst -> inst
            .group(
                    ResourceLocation.CODEC.fieldOf("picture_location").forGetter(r -> r.pictureLocation),
                    Codec.INT.fieldOf("page").forGetter(r -> r.pageNr),
                    Codec.intRange(0, 385).optionalFieldOf("x",0).forGetter(r -> r.xLocation),
                    Codec.intRange(0, 194).optionalFieldOf("y",0).forGetter(r -> r.yLocation),
                    Codec.intRange(0, 400).optionalFieldOf("xSize",16).forGetter(r -> r.xSize),
                    Codec.intRange(0, 400).optionalFieldOf("ySize",16).forGetter(r -> r.ySize)
            ).apply(inst, EncyclopediaPictureCodec::new)
    );
    private final ResourceLocation pictureLocation;
    private int pageNr;
    private int xLocation;
    private int yLocation;
    private int xSize;
    private int ySize;
    public EncyclopediaPictureCodec(ResourceLocation pictureLocation,int pageNr , int xLocation, int yLocation,int xSize,  int ySize ) {
        this.pageNr = pageNr;
        this.pictureLocation = pictureLocation;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public ResourceLocation getPictureLocation() {
        return pictureLocation;
    }

    public int getxLocation() {
        return xLocation;
    }

    public int getyLocation() {
        return yLocation;
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public int getPageNr() {
        return pageNr;
    }
}
