package com.peeko32213.unusualprehistory.common.entity.msc.util.helper;

import com.mojang.serialization.Codec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum KimmerVariant implements StringRepresentable {
    YELLOW(0, "yellow"),
    BLUE(1, "blue"),
    RED(2, "red"),
    PINK(3, "pink");

    private static final IntFunction<KimmerVariant> BY_ID;
    public static final Codec<KimmerVariant> CODEC;
    final int id;
    private final String name;

    KimmerVariant(int j, String string2) {
        this.id = j;
        this.name = string2;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public int id() {
        return this.id;
    }

    public static KimmerVariant byId(int i) {
        return BY_ID.apply(i);
    }

    static {
        BY_ID = ByIdMap.sparse(KimmerVariant::id, KimmerVariant.values(), RED);
        CODEC = StringRepresentable.fromEnum(KimmerVariant::values);
    }
}
