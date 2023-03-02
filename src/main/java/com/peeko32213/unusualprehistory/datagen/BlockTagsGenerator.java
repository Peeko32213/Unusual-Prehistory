package com.peeko32213.unusualprehistory.datagen;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
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
    }


    @Override
    public String getName() {
        return UnusualPrehistory.MODID + " Block Tags";
    }
}
