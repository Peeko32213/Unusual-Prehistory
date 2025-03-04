package com.peeko32213.unusualprehistory.common.entity.util.helper;

import com.mojang.serialization.Codec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntFunction;

public enum KimmeridgebrachypteraeschnidiumVariant implements StringRepresentable {
    YELLOW(0, "yellow"),
    BLUE(1, "blue"),
    RED(2, "red"),
    PINK(3, "pink");

    private static final IntFunction<KimmeridgebrachypteraeschnidiumVariant> BY_ID;
    public static final Codec<KimmeridgebrachypteraeschnidiumVariant> CODEC;
    final int id;
    private final String name;

    KimmeridgebrachypteraeschnidiumVariant(int j, String string2) {
        this.id = j;
        this.name = string2;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

    public int id() {
        return this.id;
    }

    public static KimmeridgebrachypteraeschnidiumVariant byId(int i) {
        return BY_ID.apply(i);
    }

    static {
        BY_ID = ByIdMap.sparse(KimmeridgebrachypteraeschnidiumVariant::id, KimmeridgebrachypteraeschnidiumVariant.values(), RED);
        CODEC = StringRepresentable.fromEnum(KimmeridgebrachypteraeschnidiumVariant::values);
    }
}
