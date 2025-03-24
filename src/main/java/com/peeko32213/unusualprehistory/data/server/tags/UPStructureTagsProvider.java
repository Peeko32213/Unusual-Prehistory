package com.peeko32213.unusualprehistory.data.server.tags;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class UPStructureTagsProvider extends net.minecraft.data.tags.StructureTagsProvider {
    public UPStructureTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pPro, ExistingFileHelper existingFileHelper) {
        super(pOutput,pPro , UnusualPrehistory.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
    }
}
