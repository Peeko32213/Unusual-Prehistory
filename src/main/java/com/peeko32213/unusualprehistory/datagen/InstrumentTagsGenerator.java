package com.peeko32213.unusualprehistory.datagen;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPInstruments;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Instrument;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class InstrumentTagsGenerator extends TagsProvider<Instrument> {


    protected InstrumentTagsGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, Registries.INSTRUMENT, pLookupProvider, UnusualPrehistory.MODID, existingFileHelper);
    }


    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        tag(UPTags.OCARINA_WHISTLE)
                .add(UPInstruments.OCARINA_WHISTLE_RK);
    }
}
