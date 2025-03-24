package com.peeko32213.unusualprehistory.core.other.tags;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Instrument;

public class UPInstrumentTags {

    public static final TagKey<Instrument> OCARINA_WHISTLE = instrumentTag("ocarina_whistle");

    private static TagKey<Instrument> instrumentTag(String name) {
        return TagKey.create(Registries.INSTRUMENT, new ResourceLocation(UnusualPrehistory.MODID, name));
    }
}
