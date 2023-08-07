package com.peeko32213.unusualprehistory.datagen;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPInstruments;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Instrument;

public class InstrumentTagsGenerator extends TagsProvider<Instrument> {

    public InstrumentTagsGenerator(DataGenerator pGenerator, @org.jetbrains.annotations.Nullable net.minecraftforge.common.data.ExistingFileHelper existingFileHelper) {
        super(pGenerator, Registry.INSTRUMENT, UnusualPrehistory.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(UPTags.OCARINA_WHISTLE)
                .add(UPInstruments.OCARINA_WHISTLE.get());
    }
}
