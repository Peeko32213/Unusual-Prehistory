package com.peeko32213.unusualprehistory.common.entity.custom.eggs;

import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum EggSize implements StringRepresentable {
    SMALL("small", 0),
    NORMAL("normal", 1),
    MEDIUM("medium", 2),
    BIG("big", 3);

    public static final Codec<EggSize> CODEC = StringRepresentable.fromEnum(EggSize::values);
    private final String name;
    private final Component displayName;
    private final int sizeNr;


    EggSize(String sizeName, int sizeNr) {
        this.name = sizeName;
        this.sizeNr = sizeNr;
        this.displayName = Component.translatable("entity.egg_" + this.name);
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }

    public Component displayName() {
        return this.displayName;
    }

    public int getSizeNr() {
        return sizeNr;
    }
}
