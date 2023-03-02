package com.peeko32213.unusualprehistory.datagen;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ItemTagsGenerator extends ItemTagsProvider {
    public ItemTagsGenerator(DataGenerator generatorIn, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, blockTagProvider, UnusualPrehistory.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        /**Example**/
        //tag(UPTags.ALLOWED_FRIDGE_ITEMS).add(UPItems.AMBER_FOSSIL.get());
    }



    @Override
    public String getName() { return UnusualPrehistory.MODID + " Item Tags";}
}
