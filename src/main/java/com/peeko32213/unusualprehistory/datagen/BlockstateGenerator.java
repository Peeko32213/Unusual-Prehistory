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

        createFlatWaterEgg(UPBlocks.OPHIDION_EGGS.get());
        createFlatWaterEgg(UPBlocks.HYNERIA_EGGS.get());
        createFlatWaterEgg(UPBlocks.TARTUO_EGGS.get());

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
        signBlock(UPBlocks.PETRIFIED_WOOD_SIGN.get(), UPBlocks.PETRIFIED_WOOD_WALL_SIGN.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_wood_sign"));
        hangingSignBlock(UPBlocks.PETRIFIED_WOOD_HANGING_SIGN.get(), UPBlocks.PETRIFIED_WOOD_WALL_HANGING_SIGN.get(), blockTexture(UPBlocks.PETRIFIED_WOOD_PLANKS.get()));

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
        signBlock(UPBlocks.FOXII_SIGN.get(), UPBlocks.FOXII_WALL_SIGN.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/foxxi_sign"));
        hangingSignBlock(UPBlocks.FOXII_HANGING_SIGN.get(), UPBlocks.FOXII_WALL_HANGING_SIGN.get(), blockTexture(UPBlocks.FOXXI_PLANKS.get()));

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
        signBlock(UPBlocks.DRYO_SIGN.get(), UPBlocks.DRYO_WALL_SIGN.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/dryo_sign"));
        hangingSignBlock(UPBlocks.DRYO_HANGING_SIGN.get(), UPBlocks.DRYO_WALL_HANGING_SIGN.get(), blockTexture(UPBlocks.DRYO_PLANKS.get()));

        //Asphalt
        simpleBlock(UPBlocks.ASPHALT.get());

        //Fossils Ores
        simpleBlock(UPBlocks.STONE_FOSSIL.get());
        simpleBlock(UPBlocks.DEEPSLATE_FOSSIL.get());

        simpleBlock(UPBlocks.PLANT_FOSSIL.get());
        simpleBlock(UPBlocks.DEEPSLATE_PLANT_FOSSIL.get());

        simpleBlock(UPBlocks.STONE_AMBER_FOSSIL.get());
        simpleBlock(UPBlocks.DEEPSLATE_AMBER_FOSSIL.get());

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

        signBlock(UPBlocks.ZULOAGAE_SIGN.get(), UPBlocks.ZULOAGAE_WALL_SIGN.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/zuloagae_sign"));
        hangingSignBlock(UPBlocks.ZULOAGAE_HANGING_SIGN.get(), UPBlocks.ZULOAGAE_WALL_HANGING_SIGN.get(), blockTexture(UPBlocks.ZULOAGAE_PLANKS.get()));

        // Potted plants
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

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        hangingSignBlock(signBlock, wallSignBlock, sign);
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign) {
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }

    public void createFlatWaterEgg(Block block){
        createFlatWaterEgg(block, "" );
        flatWaterEgg(block);
        singleTexWaterEgg(block);
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

    public ResourceLocation blockTextureEggs(Block block) {
        ResourceLocation name = key(block);
        return new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/eggs/" + name.getPath());
    }

    public ModelFile createFlatWaterEgg(Block block, String modifier){
        String baseName = getName(block);
        return models().singleTexture("block/eggs/" + modifier + baseName.replace(UnusualPrehistory.MODID + ":", ""), new ResourceLocation(UnusualPrehistory.MODID, "block/template_eggs/template_flat_water_egg"), blockTextureEggs(block));
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
        return generated(getName(block), new ResourceLocation(UnusualPrehistory.MODID,"item/eggs/" + getName(block)));
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
