package com.peeko32213.unusualprehistory.datagen;

import com.mojang.logging.LogUtils;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockstateGenerator extends BlockStateProvider {
    public BlockstateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, UnusualPrehistory.MODID, exFileHelper);
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    private String blockName(Block block) {
        return block.getLootTable().getPath();
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }
    private String getName(Block block) {
        return key(block).toString().replace(UnusualPrehistory.MODID + ":", "");
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

    public void simpleBlockItem(Block block, ModelFile model) {
        itemModels().getBuilder(key(block).getPath()).parent(model);
    }


    @Override
    protected void registerStatesAndModels() {
        /**Example**/
        //simpleBlock(UPBlocks.STRIPPED_GINKGO_LOG.get());
        //logBlock(UPBlocks.GINKGO_LOG.get());
        //This one makes a json file with model as template_+egg name as parent
        createEgg(UPBlocks.COTY_EGG.get());
        createEgg(UPBlocks.MAJUNGA_EGG.get());
        createSingleEgg(UPBlocks.BRACHI_EGG.get());
        createEgg(UPBlocks.ANURO_EGG.get());
        //This one makes a json file with default models as template_medium_eggs, etc as parent
        createEggDefaultMedium(UPBlocks.TRIKE_EGG.get());
        createEggDefaultMedium(UPBlocks.REX_EGG.get());
        createCustomEgg(UPBlocks.ULUGH_EGG.get(), "majunga");
        createCustomEgg(UPBlocks.ANTARCO_EGG.get(), "majunga");
        createCustomEgg(UPBlocks.KENTRO_EGG.get(), "majunga");
        createCustomEgg(UPBlocks.HWACHA_EGG.get(), "majunga");
        createCustomEgg(UPBlocks.AUSTRO_EGG.get(), "majunga");
        createEggDefaultSmall(UPBlocks.RAPTOR_EGG.get());
        createEggDefaultSmall(UPBlocks.PACHY_EGG.get());

        createFlatWaterEgg(UPBlocks.DUNK_EGGS.get());
        createFlatWaterEgg(UPBlocks.STETHA_EGGS.get());
        createFlatWaterEgg(UPBlocks.BEELZE_EGGS.get());
        createFlatWaterEgg(UPBlocks.AMON_EGGS.get());
        createFlatWaterEgg(UPBlocks.SCAU_EGGS.get());
        createFlatWaterEgg(UPBlocks.ERYON_EGGS.get());
        simpleBlock(UPBlocks.PETRIFIED_WOOD.get());

        simpleBlock(UPBlocks.STRIPPED_PETRIFIED_WOOD.get());
        simpleBlockItem(UPBlocks.STRIPPED_PETRIFIED_WOOD.get(), existingModel((getName(UPBlocks.STRIPPED_PETRIFIED_WOOD.get()))));
        simpleBlock(UPBlocks.POLISHED_PETRIFIED_WOOD.get());
        logBlock(UPBlocks.PETRIFIED_WOOD_LOG.get());
        logBlock(UPBlocks.STRIPPED_PETRIFIED_WOOD_LOG.get());
        trapdoorBlock(UPBlocks.PETRIFIED_WOOD_TRAPDOOR.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_wood_trapdoor"), true);
        simpleBlock(UPBlocks.PETRIFIED_WOOD_PLANKS.get());
        slabBlock(UPBlocks.PETRIFIED_WOOD_SLAB.get(), resourceBlock(getName(UPBlocks.PETRIFIED_WOOD.get())), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_planks"));
        slabBlock((SlabBlock) UPBlocks.POLISHED_PETRIFIED_WOOD_SLAB.get(), resourceBlock(getName(UPBlocks.POLISHED_PETRIFIED_WOOD.get())), new ResourceLocation(UnusualPrehistory.MODID, "block/polished_petrified_wood"));


        stairsBlock(UPBlocks.PETRIFIED_WOOD_STAIRS.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_planks"));
        stairsBlock((StairBlock) UPBlocks.POLISHED_PETRIFIED_WOOD_STAIRS.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/polished_petrified_wood"));

        doorBlock(UPBlocks.PETRIFIED_WOOD_DOOR.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_wood_door_bottom"),new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_wood_door_top"));
        pressurePlateBlock(UPBlocks.PETRIFIED_WOOD_PRESSURE_PLATE.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_planks"));
        buttonBlock(UPBlocks.PETRIFIED_WOOD_BUTTON.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_planks"));
        fenceBlock(UPBlocks.PETRIFIED_WOOD_FENCE.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_planks"));
        fenceGateBlock(UPBlocks.PETRIFIED_WOOD_FENCE_GATE.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_planks"));
        //signBlock((StandingSignBlock) UPBlocks.PETRIFIED_WOOD_SIGN.get(), (WallSignBlock) UPBlocks.PETRIFIED_WOOD_WALL_SIGN.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_wood_sign"));
    }

    public void createEgg(Block block){
        createEgg(block, "");
        createEgg(block, "two_");
        createEgg(block, "three_");
        createEgg(block, "four_");
        eggBlockVariantY(block);
        singleTex(block);
    }

    public void createCustomEgg(Block block, String modifier){
        createCustomEgg(block, "", modifier);
        createCustomEgg(block, "two_", modifier);
        createCustomEgg(block, "three_", modifier);
        createCustomEgg(block, "four_", modifier);
        eggBlockVariantY(block);
        singleTex(block);
    }

    public void createEggDefaultSmall(Block block){
        createEggDefaultSmall(block, "");
        createEggDefaultSmall(block, "two_");
        createEggDefaultSmall(block, "three_");
        createEggDefaultSmall(block, "four_");
        eggBlockVariantY(block);
        singleTex(block);
    }

    public void createEggDefaultMedium(Block block){
        createEggDefaultMedium(block, "");
        createEggDefaultMedium(block, "two_");
        createEggDefaultMedium(block, "three_");
        createEggDefaultMedium(block, "four_");
        eggBlockVariantY(block);
        singleTex(block);
    }

    public void createEggDefaultLarge(Block block){
        createEggDefaultLarge(block, "");
        eggBlockSingleVariantY(block);
        singleTex(block);
    }

    public void createSingleEgg(Block block){
        createSingleEgg(block, "" );
        createSlightltyCrackedSingleEgg(block, "");
        createVeryCrackedSingleEgg(block, "" );
        eggBlockSingleVariantY(block);
        singleTex(block);
    }

    public void createFlatWaterEgg(Block block){
        createFlatWaterEgg(block, "" );
        flatWaterEgg(block);
        singleTexWaterEgg(block);
    }

    private void eggBlockVariantY(Block block) {
        getVariantBuilder(block).forAllStatesExcept((state) -> {
            int eggs = state.getValue(BlockStateProperties.EGGS);
            int hatch = state.getValue(BlockStateProperties.HATCH);
            return createVariants(existingModel(createEggModel(eggs, hatch, block)));
        });
    }

    private void eggBlockSingleVariantY(Block block) {
        getVariantBuilder(block).forAllStatesExcept((state) -> {
            int eggs = state.getValue(BlockStateProperties.EGGS);
            int hatch = state.getValue(BlockStateProperties.HATCH);
            return createVariants(existingModel(createEggModelSingle(eggs, hatch, block)));
        });
    }


    private void eggBlock(Block block) {
        getVariantBuilder(block).forAllStatesExcept((state) -> {
            int eggs = state.getValue(BlockStateProperties.EGGS);
            int hatch = state.getValue(BlockStateProperties.HATCH);
            return ConfiguredModel.builder().modelFile(existingModel(createEggModel(eggs, hatch, block)))
                    .build();
        });
    }

    private void eggBlockSingle(Block block) {
        getVariantBuilder(block).forAllStatesExcept((state) -> {
            int eggs = state.getValue(BlockStateProperties.EGGS);
            int hatch = state.getValue(BlockStateProperties.HATCH);
            return ConfiguredModel.builder().modelFile(existingModel(createEggModelSingle(eggs, hatch, block)))
                    .build();
        });
    }

    private void flatWaterEgg(Block block) {
        getVariantBuilder(block).forAllStatesExcept((state) -> {
            return ConfiguredModel.builder().modelFile(existingModel("eggs/"+getName(block)))
                    .build();
        });
    }

    private ConfiguredModel[] createVariants(ModelFile model){
        List<ConfiguredModel> activeModels = new ArrayList<>();
        for(ModelFile modelFile : Arrays.asList(model)){
            activeModels.add(new ConfiguredModel(modelFile,0,0,false));
            activeModels.add(new ConfiguredModel(modelFile,0,90,false));
            activeModels.add(new ConfiguredModel(modelFile,0,180,false));
            activeModels.add(new ConfiguredModel(modelFile,0,270,false));
        }
        return Arrays.copyOfRange(activeModels.toArray(new ConfiguredModel[0]), 0,4) ;
    }


    public void createEgg(Block block, String modifier){
        createSingleEgg(block, modifier);
        createSlightltyCrackedSingleEgg(block, modifier);
        createVeryCrackedSingleEgg(block, modifier);
    }
    public void createCustomEgg(Block block, String modifier, String modifier2){
        createSingleEggDefault(block, modifier, modifier2);
        createSlightltyCrackedSingleEggDefault(block, modifier, modifier2);
        createVeryCrackedSingleEggDefault(block, modifier , modifier2);
    }

    public void createEggDefaultSmall(Block block, String modifier){
        createSingleEggDefault(block, modifier, "small");
        createSlightltyCrackedSingleEggDefault(block, modifier, "small");
        createVeryCrackedSingleEggDefault(block, modifier , "small");
    }

    public void createEggDefaultMedium(Block block, String modifier){
        createSingleEggDefault(block, modifier, "medium");
        createSlightltyCrackedSingleEggDefault(block, modifier, "medium");
        createVeryCrackedSingleEggDefault(block, modifier , "medium");
    }

    public void createEggDefaultLarge(Block block, String modifier){
        createSingleEggDefault(block, modifier, "large");
        createSlightltyCrackedSingleEggDefault(block, modifier, "large");
        createVeryCrackedSingleEggDefault(block, modifier , "large");
    }


    public ModelFile createFlatWaterEgg(Block block, String modifier){
        String baseName = getName(block);
        return models().singleTexture("block/eggs/" + modifier + baseName.replace(UnusualPrehistory.MODID + ":", ""), new ResourceLocation(UnusualPrehistory.MODID, "block/template_eggs/template_flat_water_egg"), blockTexture(block));
    }

    public ModelFile createSingleEgg(Block block, String modifier){
        String baseName = getName(block);
        return models().singleTexture("block/eggs/" + modifier + baseName.replace(UnusualPrehistory.MODID + ":", ""), new ResourceLocation(UnusualPrehistory.MODID, "block/template_eggs/template_" + modifier +  baseName), blockTexture(block));
    }

    public ModelFile createSlightltyCrackedSingleEgg(Block block, String modifier){
        String baseName = getName(block);
        return models().singleTexture( "block/eggs/" +modifier + "slightly_cracked_" + baseName.replace(UnusualPrehistory.MODID + ":", ""), new ResourceLocation(UnusualPrehistory.MODID, "block/template_eggs/template_" + baseName), new ResourceLocation(UnusualPrehistory.MODID, "block/"+getName(block)+"_slightly_cracked"));
    }

    public ModelFile createVeryCrackedSingleEgg(Block block, String modifier){
        String baseName = getName(block);
        return models().singleTexture( "block/eggs/" +modifier + "very_cracked_" +baseName.replace(UnusualPrehistory.MODID + ":", ""), new ResourceLocation(UnusualPrehistory.MODID, "block/template_eggs/template_" + baseName), new ResourceLocation(UnusualPrehistory.MODID, "block/"+getName(block)+"_very_cracked"));
    }

    public ModelFile createSingleEggDefault(Block block, String modifier, String modifier2){
        String baseName = getName(block);
        return models().singleTexture("block/eggs/" + modifier + baseName.replace(UnusualPrehistory.MODID + ":", ""), new ResourceLocation(UnusualPrehistory.MODID, "block/template_eggs/template_" +modifier+ modifier2 + "_eggs"), blockTexture(block));
    }

    public ModelFile createSlightltyCrackedSingleEggDefault(Block block, String modifier, String modifier2){
        String baseName = getName(block);
        return models().singleTexture( "block/eggs/" +modifier + "slightly_cracked_" + baseName.replace(UnusualPrehistory.MODID + ":", ""), new ResourceLocation(UnusualPrehistory.MODID, "block/template_eggs/template_" +modifier + modifier2 + "_eggs" ), new ResourceLocation(UnusualPrehistory.MODID, "block/"+getName(block)+"_slightly_cracked"));
    }

    public ModelFile createVeryCrackedSingleEggDefault(Block block, String modifier, String modifier2){
        String baseName = getName(block);
        return models().singleTexture( "block/eggs/" +modifier + "very_cracked_" +baseName.replace(UnusualPrehistory.MODID + ":", ""), new ResourceLocation(UnusualPrehistory.MODID, "block/template_eggs/template_" +modifier + modifier2 + "_eggs"),  new ResourceLocation(UnusualPrehistory.MODID, "block/"+getName(block)+"_very_cracked"));
    }


    private String createEggModel(Integer pEgg, Integer pVariantId, Block block) {
        switch (pVariantId) {
            case 0:
                return this.createEggModel(pEgg, "", key(block).toString());
            case 1:
                return this.createEggModel(pEgg, "slightly_cracked_", key(block).toString());
            case 2:
                return this.createEggModel(pEgg, "very_cracked_", key(block).toString());
            default:
                throw new UnsupportedOperationException();
        }
    }

    private String createEggModelSingle(Integer pEgg, Integer pVariantId, Block block) {
        switch (pVariantId) {
            case 0:
                return this.createEggModelSingle(pEgg, "", key(block).toString());
            case 1:
                return this.createEggModelSingle(pEgg, "slightly_cracked_", key(block).toString());
            case 2:
                return this.createEggModelSingle(pEgg, "very_cracked_", key(block).toString());
            default:
                throw new UnsupportedOperationException();
        }
    }

    private String createEggModel(int pHatchAmount, String pVariantName, String baseName) {
        switch (pHatchAmount) {
            case 1:
                return "eggs/" +pVariantName + baseName.replace(UnusualPrehistory.MODID + ":", "");
            case 2:
                return "eggs/" +"two_" + pVariantName + baseName.replace(UnusualPrehistory.MODID + ":", "");
            case 3:
                return "eggs/" +"three_" + pVariantName + baseName.replace(UnusualPrehistory.MODID + ":", "");
            case 4:
                return "eggs/" +"four_" + pVariantName + baseName.replace(UnusualPrehistory.MODID + ":", "");
            default:
                throw new UnsupportedOperationException();
        }
    }

    private String createEggModelSingle(int pHatchAmount, String pVariantName, String baseName) {
        String s = "eggs/" + pVariantName + baseName.replace(UnusualPrehistory.MODID + ":", "");
        switch (pHatchAmount) {
            case 1:
                return s;
            case 2:
                return s;
            case 3:
                return s;
            case 4:
                return s;
            default:
                throw new UnsupportedOperationException();
        }
    }
    private BlockModelBuilder singleTex(Block block) {
        return generated(getName(block), new ResourceLocation(UnusualPrehistory.MODID,"item/" + getName(block).replace("eggs", "egg")));
    }

    private BlockModelBuilder singleTexWaterEgg(Block block) {
        return generated(getName(block), new ResourceLocation(UnusualPrehistory.MODID,"item/" + getName(block)));
    }

    private BlockModelBuilder generated(String name, ResourceLocation... layers) {
        BlockModelBuilder builder = models().withExistingParent("item/" + name, "item/generated");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }


    public void tomatoBlock(Block block, ResourceLocation texture, String renderType) {
        tomatoBlock(block, key(block).toString(), texture, renderType);
    }

    public void tomatoBlock(Block block, String baseName, ResourceLocation texture, String renderType) {
        ModelFile age1 = models().cross(baseName + "_stage0", new ResourceLocation(texture.toString() + "_stage0")).renderType(renderType);
        ModelFile age2 = models().cross(baseName + "_stage1", new ResourceLocation(texture.toString() + "_stage1")).renderType(renderType);
        ModelFile age3 = models().cross(baseName + "_stage2", new ResourceLocation(texture.toString() + "_stage2")).renderType(renderType);
        ModelFile age4 = models().cross(baseName + "_stage3", new ResourceLocation(texture.toString() + "_stage3")).renderType(renderType);

        tomatoBlock(block, age1, age2, age3, age4);
    }

    private void tomatoBlock(Block block, ModelFile age1, ModelFile age2, ModelFile age3, ModelFile age4) {
        getVariantBuilder(block).forAllStatesExcept((state -> {
            switch (state.getValue(BlockStateProperties.AGE_3)) {
                default:
                    return ConfiguredModel.builder().modelFile(age1).build();
                case 1:
                    return ConfiguredModel.builder().modelFile(age2).build();
                case 2:
                    return ConfiguredModel.builder().modelFile(age3).build();
                case 3:
                    return ConfiguredModel.builder().modelFile(age4).build();
            }
        }));
    }

    @Override
    public String getName() {
        return "Block States: " + UnusualPrehistory.MODID;
    }
}
