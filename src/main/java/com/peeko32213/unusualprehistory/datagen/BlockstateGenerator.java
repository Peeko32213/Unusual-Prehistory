package com.peeko32213.unusualprehistory.datagen;

import com.mojang.logging.LogUtils;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
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

import static net.minecraftforge.client.model.generators.ModelProvider.BLOCK_FOLDER;

public class BlockstateGenerator extends BlockStateProvider {
    public BlockstateGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, UnusualPrehistory.MODID, exFileHelper);
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
        createCustomEggCustom(UPBlocks.ULUGH_EGG.get(), "majunga", "ulughbegsaurus_egg");
        createCustomEggCustom(UPBlocks.ANTARCO_EGG.get(), "majunga", "antarctopelta_eggs");
        createCustomEggCustom(UPBlocks.KENTRO_EGG.get(), "majunga", "kentrosaurus_egg");
        createCustomEggCustom(UPBlocks.HWACHA_EGG.get(), "majunga", "hwachavenator_egg");
        createCustomEggCustom(UPBlocks.AUSTRO_EGG.get(), "majunga", "austroraptor_eggs");
        createEggDefaultSmallCustom(UPBlocks.RAPTOR_EGG.get(), "veloci_eggs");
        createEggDefaultSmall(UPBlocks.PACHY_EGG.get());
        createSingleEgg(UPBlocks.TALPANAS_EGG.get());
        createEggDefaultSmallCustom(UPBlocks.BARINA_EGG.get(), "barinasuchus_eggs");
        createEggDefaultMediumCustom(UPBlocks.MEGALA_EGG.get(), "megalania_eggs");

        // Unused 1.6 stuff
        createFlatWaterEgg(UPBlocks.OPHIDION_EGGS.get());
        createFlatWaterEgg(UPBlocks.HYNERIA_EGGS.get());
//        createFlatWaterEgg(UPBlocks.TARTUO_EGGS.get());

        createFlatWaterEgg(UPBlocks.DUNK_EGGS.get());
        createFlatWaterEgg(UPBlocks.STETHA_EGGS.get());
        createFlatWaterEgg(UPBlocks.BEELZE_EGGS.get());
        createFlatWaterEgg(UPBlocks.AMON_EGGS.get());
        createFlatWaterEgg(UPBlocks.SCAU_EGGS.get());
        createFlatWaterEgg(UPBlocks.ERYON_EGGS.get());
        createFlatWaterEgg(UPBlocks.KIMMER_EGGS.get());
        createFlatWaterEgg(UPBlocks.DIPLOCAULUS_EGGS.get());
//        createFlatWaterEgg(UPBlocks.FURCACAUDA_EGGS.get());


        simpleBlock(UPBlocks.PETRIFIED_WOOD.get());
        logBlock(UPBlocks.STRIPPED_PETRIFIED_WOOD.get());
        simpleBlockItem(UPBlocks.STRIPPED_PETRIFIED_WOOD.get(), existingModel((getName(UPBlocks.STRIPPED_PETRIFIED_WOOD.get()))));
        simpleBlock(UPBlocks.POLISHED_PETRIFIED_WOOD.get());
        logBlock(UPBlocks.PETRIFIED_WOOD_LOG.get());
        logBlock(UPBlocks.STRIPPED_PETRIFIED_WOOD_LOG.get());
        trapdoorBlock(UPBlocks.PETRIFIED_WOOD_TRAPDOOR.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_wood_trapdoor"), true);
        simpleBlock(UPBlocks.PETRIFIED_WOOD_PLANKS.get());
        slabBlock(UPBlocks.PETRIFIED_WOOD_SLAB.get(), resourceBlock(getName(UPBlocks.PETRIFIED_WOOD_PLANKS.get())), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_planks"));
        slabBlock((SlabBlock) UPBlocks.POLISHED_PETRIFIED_WOOD_SLAB.get(), resourceBlock(getName(UPBlocks.POLISHED_PETRIFIED_WOOD.get())), new ResourceLocation(UnusualPrehistory.MODID, "block/polished_petrified_wood"));

        stairsBlock(UPBlocks.PETRIFIED_WOOD_STAIRS.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_planks"));
        stairsBlock((StairBlock) UPBlocks.POLISHED_PETRIFIED_WOOD_STAIRS.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/polished_petrified_wood"));

        doorBlock(UPBlocks.PETRIFIED_WOOD_DOOR.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_wood_door_bottom"),new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_wood_door_top"));
        pressurePlateBlock(UPBlocks.PETRIFIED_WOOD_PRESSURE_PLATE.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_planks"));
        buttonBlock(UPBlocks.PETRIFIED_WOOD_BUTTON.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_planks"));
        fenceBlock(UPBlocks.PETRIFIED_WOOD_FENCE.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_planks"));
        fenceGateBlock(UPBlocks.PETRIFIED_WOOD_FENCE_GATE.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_planks"));
        signBlock((StandingSignBlock) UPBlocks.PETRIFIED_WOOD_SIGN.get(), (WallSignBlock) UPBlocks.PETRIFIED_WOOD_WALL_SIGN.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_wood_sign"));

        //Foxxi

        simpleBlock(UPBlocks.STRIPPED_FOXXI_WOOD.get());
        simpleBlockItem(UPBlocks.STRIPPED_FOXXI_WOOD.get(), existingModel((getName(UPBlocks.STRIPPED_FOXXI_WOOD.get()))));
        logBlock(UPBlocks.FOXXI_LOG.get());
        simpleBlockItem(UPBlocks.FOXXI_LOG.get(), existingModel((getName(UPBlocks.FOXXI_LOG.get()))));
        logBlock(UPBlocks.FOXXI_WOOD.get());
        simpleBlockItem(UPBlocks.FOXXI_WOOD.get(), existingModel((getName(UPBlocks.FOXXI_WOOD.get()))));
        logBlock(UPBlocks.STRIPPED_FOXXI_LOG.get());
        simpleBlockItem(UPBlocks.STRIPPED_FOXXI_LOG.get(), existingModel((getName(UPBlocks.STRIPPED_FOXXI_LOG.get()))));
        simpleBlock(UPBlocks.FOXXI_PLANKS.get());
        simpleBlockItem(UPBlocks.FOXXI_PLANKS.get(), existingModel((getName(UPBlocks.FOXXI_PLANKS.get()))));
        simpleBlock(UPBlocks.FOXXI_LEAVES.get());
        simpleBlockItem(UPBlocks.FOXXI_LEAVES.get(), existingModel((getName(UPBlocks.FOXXI_LEAVES.get()))));
        slabBlock(UPBlocks.FOXXI_SLAB.get(), resourceBlock(getName(UPBlocks.FOXXI_PLANKS.get())), new ResourceLocation(UnusualPrehistory.MODID, "block/foxxi_planks"));
        simpleBlockItem(UPBlocks.FOXXI_SLAB.get(), existingModel((getName(UPBlocks.FOXXI_SLAB.get()))));

        doorBlock(UPBlocks.FOXXI_DOOR.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/foxxi_door_bottom"),new ResourceLocation(UnusualPrehistory.MODID, "block/foxxi_door_top"));
        trapdoorBlock(UPBlocks.FOXXI_TRAPDOOR.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/foxxi_trapdoor"), true);
        stairsBlock(UPBlocks.FOXXI_STAIRS.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/foxxi_planks"));
        simpleBlockItem(UPBlocks.FOXXI_STAIRS.get(), existingModel((getName(UPBlocks.FOXXI_STAIRS.get()))));
        pressurePlateBlock(UPBlocks.FOXXI_PRESSURE_PLATE.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/foxxi_planks"));
        simpleBlockItem(UPBlocks.FOXXI_PRESSURE_PLATE.get(), existingModel((getName(UPBlocks.FOXXI_PRESSURE_PLATE.get()))));
        buttonBlock(UPBlocks.FOXXI_BUTTON.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/foxxi_planks"));
        fenceBlock(UPBlocks.FOXXI_FENCE.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/foxxi_planks"));
        fenceGateBlock(UPBlocks.FOXXI_FENCE_GATE.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/foxxi_planks"));
        signBlock((StandingSignBlock) UPBlocks.FOXXI_SIGN.get(), (WallSignBlock) UPBlocks.FOXXI_WALL_SIGN.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/foxxi_sign"));

        //Dryophyllum

        logBlock(UPBlocks.STRIPPED_DRYO_WOOD.get());
        simpleBlockItem(UPBlocks.STRIPPED_DRYO_WOOD.get(), existingModel((getName(UPBlocks.STRIPPED_DRYO_WOOD.get()))));
        logBlock(UPBlocks.DRYO_LOG.get());
        simpleBlockItem(UPBlocks.DRYO_LOG.get(), existingModel((getName(UPBlocks.DRYO_LOG.get()))));
        logBlock(UPBlocks.DRYO_WOOD.get());
        simpleBlockItem(UPBlocks.DRYO_WOOD.get(), existingModel((getName(UPBlocks.DRYO_WOOD.get()))));
        logBlock(UPBlocks.STRIPPED_DRYO_LOG.get());
        simpleBlockItem(UPBlocks.STRIPPED_DRYO_LOG.get(), existingModel((getName(UPBlocks.STRIPPED_DRYO_LOG.get()))));
        simpleBlock(UPBlocks.DRYO_PLANKS.get());
        simpleBlockItem(UPBlocks.DRYO_PLANKS.get(), existingModel((getName(UPBlocks.DRYO_PLANKS.get()))));
        simpleBlock(UPBlocks.DRYO_LEAVES.get());
        simpleBlockItem(UPBlocks.DRYO_LEAVES.get(), existingModel((getName(UPBlocks.DRYO_LEAVES.get()))));
        slabBlock(UPBlocks.DRYO_SLAB.get(), resourceBlock(getName(UPBlocks.DRYO_PLANKS.get())), new ResourceLocation(UnusualPrehistory.MODID, "block/dryo_planks"));
        simpleBlockItem(UPBlocks.DRYO_SLAB.get(), existingModel((getName(UPBlocks.DRYO_SLAB.get()))));

        doorBlock(UPBlocks.DRYO_DOOR.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/dryo_door_bottom"),new ResourceLocation(UnusualPrehistory.MODID, "block/dryo_door_top"));
        trapdoorBlock(UPBlocks.DRYO_TRAPDOOR.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/dryo_trapdoor"), true);
        stairsBlock(UPBlocks.DRYO_STAIRS.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/dryo_planks"));
        simpleBlockItem(UPBlocks.DRYO_STAIRS.get(), existingModel((getName(UPBlocks.DRYO_STAIRS.get()))));
        pressurePlateBlock(UPBlocks.DRYO_PRESSURE_PLATE.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/dryo_planks"));
        simpleBlockItem(UPBlocks.DRYO_PRESSURE_PLATE.get(), existingModel((getName(UPBlocks.DRYO_PRESSURE_PLATE.get()))));
        buttonBlock(UPBlocks.DRYO_BUTTON.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/dryo_planks"));
        fenceBlock(UPBlocks.DRYO_FENCE.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/dryo_planks"));
        fenceGateBlock(UPBlocks.DRYO_FENCE_GATE.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/dryo_planks"));
        signBlock((StandingSignBlock) UPBlocks.DRYO_SIGN.get(), (WallSignBlock) UPBlocks.DRYO_WALL_SIGN.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/dryo_sign"));

        //Asphalt

        simpleBlock(UPBlocks.ASPHALT.get());

        //Fossils Ores
        simpleBlock(UPBlocks.STONE_OPAL_FOSSIL.get());
        simpleBlock(UPBlocks.DEEPSLATE_OPAL_FOSSIL.get());
        simpleBlock(UPBlocks.STONE_TAR_FOSSIL.get());
        simpleBlock(UPBlocks.DEEPSLATE_TAR_FOSSIL.get());
        simpleBlock(UPBlocks.PERMAFROST.get());
        simpleBlock(UPBlocks.PERMAFROST_FOSSIL.get());

        //Zuloagae

        logBlock(UPBlocks.STRIPPED_ZULOAGAE_BLOCK.get());
        simpleBlockItem(UPBlocks.STRIPPED_ZULOAGAE_BLOCK.get(), existingModel((getName(UPBlocks.STRIPPED_ZULOAGAE_BLOCK.get()))));
        logBlock(UPBlocks.ZULOAGAE_BLOCK.get());
        simpleBlockItem(UPBlocks.ZULOAGAE_BLOCK.get(), existingModel((getName(UPBlocks.ZULOAGAE_BLOCK.get()))));
        simpleBlock(UPBlocks.ZULOAGAE_PLANKS.get());
        simpleBlockItem(UPBlocks.ZULOAGAE_PLANKS.get(), existingModel((getName(UPBlocks.ZULOAGAE_PLANKS.get()))));
        slabBlock(UPBlocks.ZULOAGAE_SLAB.get(), resourceBlock(getName(UPBlocks.ZULOAGAE_PLANKS.get())), new ResourceLocation(UnusualPrehistory.MODID, "block/zuloagae_planks"));
        simpleBlockItem(UPBlocks.ZULOAGAE_SLAB.get(), existingModel((getName(UPBlocks.ZULOAGAE_SLAB.get()))));

        doorBlock(UPBlocks.ZULOAGAE_DOOR.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/zuloagae_door_bottom"),new ResourceLocation(UnusualPrehistory.MODID, "block/zuloagae_door_top"));
        trapdoorBlock(UPBlocks.ZULOAGAE_TRAPDOOR.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/zuloagae_trapdoor"), true);
        stairsBlock(UPBlocks.ZULOAGAE_STAIRS.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/zuloagae_planks"));
        simpleBlockItem(UPBlocks.ZULOAGAE_STAIRS.get(), existingModel((getName(UPBlocks.ZULOAGAE_STAIRS.get()))));
        pressurePlateBlock(UPBlocks.ZULOAGAE_PRESSURE_PLATE.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/zuloagae_planks"));
        simpleBlockItem(UPBlocks.ZULOAGAE_PRESSURE_PLATE.get(), existingModel((getName(UPBlocks.ZULOAGAE_PRESSURE_PLATE.get()))));
        buttonBlock(UPBlocks.ZULOAGAE_BUTTON.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/zuloagae_planks"));
        fenceBlock(UPBlocks.ZULOAGAE_FENCE.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/zuloagae_planks"));
        fenceGateBlock(UPBlocks.ZULOAGAE_FENCE_GATE.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/zuloagae_planks"));
        createPottedPlant(UPBlocks.ARCHAEOSIGILARIA, UPBlocks.POTTED_ARCHAEOSIGILARIA,"cutout");
        createPottedPlant(UPBlocks.BENNETTITALES, UPBlocks.POTTED_BENNETTITALES,"cutout");
        createPottedPlant(UPBlocks.HORSETAIL, UPBlocks.POTTED_HORSETAIL,"cutout");
        createPottedPlant(UPBlocks.LEEFRUCTUS, UPBlocks.POTTED_LEEFRUCTUS,"cutout");
        createPottedPlant(UPBlocks.SARACENIA, UPBlocks.POTTED_SARACENIA,"cutout");
        createPottedPlant(UPBlocks.GINKGO_SAPLING, UPBlocks.POTTED_GINKGO_SAPLING,"cutout");
        createPottedPlant(UPBlocks.PETRIFIED_BUSH, UPBlocks.POTTED_PETRIFIED_BUSH,"cutout");
        createPottedPlant(UPBlocks.DRYO_SAPLING, UPBlocks.POTTED_DRYO,"cutout");
        createPottedPlant(UPBlocks.ZULOAGAE_SAPLING, UPBlocks.POTTED_ZULOGAE,"cutout");
        //createPottedPlant(UPBlocks.FOXII_SAPLING, UPBlocks.POTTED_FOXXI,"cutout");
    }


    public void createEggCustom(Block block, String name){
        createEgg(block, "");
        createEgg(block, "two_");
        createEgg(block, "three_");
        createEgg(block, "four_");
        eggBlockVariantY(block);
        singleTexCustom(block,name);
    }
    public void createEgg(Block block){
        createEgg(block, "");
        createEgg(block, "two_");
        createEgg(block, "three_");
        createEgg(block, "four_");
        eggBlockVariantY(block);
        singleTex(block);
    }
    public void createCustomEggCustom(Block block, String modifier, String name){
        createCustomEgg(block, "", modifier);
        createCustomEgg(block, "two_", modifier);
        createCustomEgg(block, "three_", modifier);
        createCustomEgg(block, "four_", modifier);
        eggBlockVariantY(block);
        singleTexCustom(block,name);
    }

    public void createCustomEgg(Block block, String modifier){
        createCustomEgg(block, "", modifier);
        createCustomEgg(block, "two_", modifier);
        createCustomEgg(block, "three_", modifier);
        createCustomEgg(block, "four_", modifier);
        eggBlockVariantY(block);
        singleTex(block);
    }

    public void createEggDefaultSmallCustom(Block block, String name){
        createEggDefaultSmall(block, "");
        createEggDefaultSmall(block, "two_");
        createEggDefaultSmall(block, "three_");
        createEggDefaultSmall(block, "four_");
        eggBlockVariantY(block);
        singleTexCustom(block,name);
    }

    public void createEggDefaultSmall(Block block){
        createEggDefaultSmall(block, "");
        createEggDefaultSmall(block, "two_");
        createEggDefaultSmall(block, "three_");
        createEggDefaultSmall(block, "four_");
        eggBlockVariantY(block);
        singleTex(block);
    }
    public void createEggDefaultMediumCustom(Block block, String name){
        createEggDefaultMedium(block, "");
        createEggDefaultMedium(block, "two_");
        createEggDefaultMedium(block, "three_");
        createEggDefaultMedium(block, "four_");
        eggBlockVariantY(block);
        singleTexCustom(block,name);
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
        getVariantBuilder(block).forAllStatesExcept((state) -> ConfiguredModel.builder().modelFile(existingModel("eggs/"+getName(block)))
                .build());
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
    public ResourceLocation blockTextureEggs(Block block) {
        ResourceLocation name = key(block);
        return new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/eggs/" + name.getPath());
    }

    public ModelFile createFlatWaterEgg(Block block, String modifier){
        String baseName = getName(block);
        return models().singleTexture("block/eggs/" + modifier + baseName.replace(UnusualPrehistory.MODID + ":", ""), new ResourceLocation(UnusualPrehistory.MODID, "block/template_eggs/template_flat_water_egg"), blockTextureEggs(block));
    }

    public ModelFile createSingleEgg(Block block, String modifier){
        String baseName = getName(block);
        return models().singleTexture("block/eggs/" + modifier + baseName.replace(UnusualPrehistory.MODID + ":", ""), new ResourceLocation(UnusualPrehistory.MODID, "block/template_eggs/template_" + modifier +  baseName), blockTextureEggs(block));
    }

    public ModelFile createSlightltyCrackedSingleEgg(Block block, String modifier){
        String baseName = getName(block);
        return models().singleTexture( "block/eggs/" +modifier + "slightly_cracked_" + baseName.replace(UnusualPrehistory.MODID + ":", ""), new ResourceLocation(UnusualPrehistory.MODID, "block/template_eggs/template_" + baseName), new ResourceLocation(UnusualPrehistory.MODID, "block/eggs/"+getName(block)+"_slightly_cracked"));
    }

    public ModelFile createVeryCrackedSingleEgg(Block block, String modifier){
        String baseName = getName(block);
        return models().singleTexture( "block/eggs/" +modifier + "very_cracked_" +baseName.replace(UnusualPrehistory.MODID + ":", ""), new ResourceLocation(UnusualPrehistory.MODID, "block/template_eggs/template_" + baseName), new ResourceLocation(UnusualPrehistory.MODID, "block/eggs/"+getName(block)+"_very_cracked"));
    }

    public ModelFile createSingleEggDefault(Block block, String modifier, String modifier2){
        String baseName = getName(block);
        return models().singleTexture("block/eggs/" + modifier + baseName.replace(UnusualPrehistory.MODID + ":", ""), new ResourceLocation(UnusualPrehistory.MODID, "block/template_eggs/template_" +modifier+ modifier2 + "_eggs"), blockTextureEggs(block));
    }

    public ModelFile createSlightltyCrackedSingleEggDefault(Block block, String modifier, String modifier2){
        String baseName = getName(block);
        return models().singleTexture( "block/eggs/" +modifier + "slightly_cracked_" + baseName.replace(UnusualPrehistory.MODID + ":", ""), new ResourceLocation(UnusualPrehistory.MODID, "block/template_eggs/template_" +modifier + modifier2 + "_eggs" ), new ResourceLocation(UnusualPrehistory.MODID, "block/eggs/"+getName(block)+"_slightly_cracked"));
    }

    public ModelFile createVeryCrackedSingleEggDefault(Block block, String modifier, String modifier2){
        String baseName = getName(block);
        return models().singleTexture( "block/eggs/" +modifier + "very_cracked_" +baseName.replace(UnusualPrehistory.MODID + ":", ""), new ResourceLocation(UnusualPrehistory.MODID, "block/template_eggs/template_" +modifier + modifier2 + "_eggs"),  new ResourceLocation(UnusualPrehistory.MODID, "block/eggs/"+getName(block)+"_very_cracked"));
    }


    private String createEggModel(Integer pEgg, Integer pVariantId, Block block) {
        return switch (pVariantId) {
            case 0 -> this.createEggModel(pEgg, "", key(block).toString());
            case 1 -> this.createEggModel(pEgg, "slightly_cracked_", key(block).toString());
            case 2 -> this.createEggModel(pEgg, "very_cracked_", key(block).toString());
            default -> throw new UnsupportedOperationException();
        };
    }

    private String createEggModelSingle(Integer pEgg, Integer pVariantId, Block block) {
        return switch (pVariantId) {
            case 0 -> this.createEggModelSingle(pEgg, "", key(block).toString());
            case 1 -> this.createEggModelSingle(pEgg, "slightly_cracked_", key(block).toString());
            case 2 -> this.createEggModelSingle(pEgg, "very_cracked_", key(block).toString());
            default -> throw new UnsupportedOperationException();
        };
    }

    private String createEggModel(int pHatchAmount, String pVariantName, String baseName) {
        return switch (pHatchAmount) {
            case 1 -> "eggs/" + pVariantName + baseName.replace(UnusualPrehistory.MODID + ":", "");
            case 2 -> "eggs/" + "two_" + pVariantName + baseName.replace(UnusualPrehistory.MODID + ":", "");
            case 3 -> "eggs/" + "three_" + pVariantName + baseName.replace(UnusualPrehistory.MODID + ":", "");
            case 4 -> "eggs/" + "four_" + pVariantName + baseName.replace(UnusualPrehistory.MODID + ":", "");
            default -> throw new UnsupportedOperationException();
        };
    }

    private String createEggModelSingle(int pHatchAmount, String pVariantName, String baseName) {
        String s = "eggs/" + pVariantName + baseName.replace(UnusualPrehistory.MODID + ":", "");
        return switch (pHatchAmount) {
            case 1 -> s;
            case 2 -> s;
            case 3 -> s;
            case 4 -> s;
            default -> throw new UnsupportedOperationException();
        };
    }

    private BlockModelBuilder singleTexCustom(Block block,String name) {
        return generated(getName(block), new ResourceLocation(UnusualPrehistory.MODID,"item/" + name.replace("eggs", "egg")));
    }
    private BlockModelBuilder singleTex(Block block) {
        return generated(getName(block), new ResourceLocation(UnusualPrehistory.MODID,"item/" + getName(block).replace("eggs", "egg")));
    }

    public ModelFile singleTexture(String name, ResourceLocation parent, String textureKey, ResourceLocation texture, String renderType) {
        return models().withExistingParent(name, parent)
                .texture(textureKey, texture).renderType(renderType);
    }

    private void createPottedPlant(RegistryObject<Block> plant, RegistryObject<Block> pottedPlant, String renderType){
        ConfiguredModel cFfile = new ConfiguredModel(pottedPlant(name(pottedPlant.get()), blockTexture(plant.get()), renderType));
        getVariantBuilder(pottedPlant.get()).partialState().setModels(cFfile);
        //impleBlockItem(plant.get(), file);
    }

    public ModelFile pottedPlant(String name, ResourceLocation plant, String renderType) {
        return singleTexture(name, BLOCK_FOLDER + "/flower_pot_cross", "plant", plant, renderType);
    }

    private ModelFile singleTexture(String name, String parent, String textureKey, ResourceLocation texture, String renderType) {
        return singleTexture(name, mcLoc(parent), textureKey, texture, renderType);
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

    private String name(Block block) {
        return key(block).getPath();
    }

    @Override
    public String getName() {
        return "Block States: " + UnusualPrehistory.MODID;
    }
}
