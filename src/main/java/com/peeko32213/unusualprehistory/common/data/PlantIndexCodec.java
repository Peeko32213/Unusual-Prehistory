package com.peeko32213.unusualprehistory.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class PlantIndexCodec {

    public static Codec<PlantIndexCodec> CODEC = RecordCodecBuilder.create(inst -> inst
            .group(
                    Codec.INT.fieldOf("page").forGetter(r -> r.pageNr),
                    Codec.intRange(0, 385).optionalFieldOf("xLocation",0).forGetter(r -> r.xLocation),
                    Codec.intRange(0, 194).optionalFieldOf("yLocation",0).forGetter(r -> r.yLocation),
                    Codec.intRange(0, 400).optionalFieldOf("columns",16).forGetter(r -> r.colums),
                    Codec.intRange(0, 400).optionalFieldOf("rows",16).forGetter(r -> r.rows)
            ).apply(inst, PlantIndexCodec::new)
    );


    private int pageNr;
    private int xLocation;
    private int yLocation;
    private int colums;
    private int rows;
    public PlantIndexCodec(int pageNr , int xLocation, int yLocation, int colums, int rows ) {
        this.pageNr = pageNr;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.colums = colums;
        this.rows = rows;
    }

    public int getPageNr() {
        return pageNr;
    }

    public int getyLocation() {
        return yLocation;
    }

    public int getxLocation() {
        return xLocation;
    }

    public int getColums() {
        return colums;
    }

    public int getRows() {
        return rows;
    }
}
