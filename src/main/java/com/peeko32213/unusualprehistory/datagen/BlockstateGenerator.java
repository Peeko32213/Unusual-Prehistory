package com.peeko32213.unusualprehistory.datagen;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockstateGenerator extends BlockStateProvider {
    public BlockstateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, UnusualPrehistory.MODID, exFileHelper);
    }

    private String blockName(Block block) {
        return block.getLootTable().getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(UnusualPrehistory.MODID, "block/" + path);
    }

    public ModelFile existingModel(Block block) {
        return new ModelFile.ExistingModelFile(resourceBlock(blockName(block)), models().existingFileHelper);
    }

    public ModelFile existingModel(String path) {
        return new ModelFile.ExistingModelFile(resourceBlock(path), models().existingFileHelper);
    }
    @Override
    protected void registerStatesAndModels(){
        /**Example**/
        //simpleBlock(UPBlocks.STRIPPED_GINKGO_LOG.get());
        //logBlock(UPBlocks.GINKGO_LOG.get());


    }


    @Override
    public String getName() {
        return "Block States: " + UnusualPrehistory.MODID;
    }
}
