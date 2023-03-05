package com.peeko32213.unusualprehistory.datagen;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockTagsGenerator extends BlockTagsProvider {
    public BlockTagsGenerator(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, UnusualPrehistory.MODID, helper);
    }

    @Override
    protected void addTags(){
        /**Example**/
        //tag(BlockTags.MINEABLE_WITH_HOE)
        //        .add(UPBlocks.AMBER_BLOCK.get());
        tag(BlockTags.PLANKS).add(UPBlocks.GINKGO_PLANKS.get());
    }


    @Override
    public String getName() {
        return UnusualPrehistory.MODID + " Block Tags";
    }
}
