package com.peeko32213.unusualprehistory.data.client.models;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.blocks.UPBlocks;
import com.teamabnormals.blueprint.common.block.sign.BlueprintCeilingHangingSignBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintStandingSignBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintWallHangingSignBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintWallSignBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static com.peeko32213.unusualprehistory.data.UPDatagenUtils.*;
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

        // Dryophyllum
        basicBlock(UPBlocks.DRYO_PLANKS);
        modSignBlock(UPBlocks.DRYO_SIGN, UPBlocks.DRYO_PLANKS);
        modHangingSignBlock(UPBlocks.DRYO_HANGING_SIGN, UPBlocks.STRIPPED_DRYO_LOG);
        modLogBlock(UPBlocks.DRYO_LOG);
        modLogBlock(UPBlocks.STRIPPED_DRYO_LOG);
        modWoodBlock(UPBlocks.DRYO_WOOD, UPBlocks.DRYO_LOG);
        modWoodBlock(UPBlocks.STRIPPED_DRYO_WOOD, UPBlocks.STRIPPED_DRYO_LOG);
        modFenceBlock(UPBlocks.DRYO_FENCE, UPBlocks.DRYO_PLANKS);
        modFenceGateBlock(UPBlocks.DRYO_FENCE_GATE, UPBlocks.DRYO_PLANKS);
        modTrapdoorWithRenderType(UPBlocks.DRYO_TRAPDOOR, "cutout");
        modDoorBlockWithRenderType(UPBlocks.DRYO_DOOR, "cutout");
        modPressurePlateBlock(UPBlocks.DRYO_PRESSURE_PLATE, UPBlocks.DRYO_PLANKS);
        modStairsBlock(UPBlocks.DRYO_STAIRS, UPBlocks.DRYO_PLANKS);
        modSlabBlock(UPBlocks.DRYO_SLAB, UPBlocks.DRYO_PLANKS);
        basicButtonBlock(UPBlocks.DRYO_BUTTON, UPBlocks.DRYO_PLANKS);
        basicBlockWithRenderType(UPBlocks.DRYO_LEAVES, "cutout");
        plantWithPottedBlock(UPBlocks.DRYO_SAPLING, UPBlocks.POTTED_DRYO_SAPLING);

        // Foxii
        basicBlock(UPBlocks.FOXII_PLANKS);
        modSignBlock(UPBlocks.FOXII_SIGN, UPBlocks.FOXII_PLANKS);
        modHangingSignBlock(UPBlocks.FOXII_HANGING_SIGN, UPBlocks.STRIPPED_FOXII_LOG);
        modLogBlock(UPBlocks.FOXII_LOG);
        modLogBlock(UPBlocks.STRIPPED_FOXII_LOG);
        modWoodBlock(UPBlocks.FOXII_WOOD, UPBlocks.FOXII_LOG);
        modWoodBlock(UPBlocks.STRIPPED_FOXII_WOOD, UPBlocks.STRIPPED_FOXII_LOG);
        modFenceBlock(UPBlocks.FOXII_FENCE, UPBlocks.FOXII_PLANKS);
        modFenceGateBlock(UPBlocks.FOXII_FENCE_GATE, UPBlocks.FOXII_PLANKS);
        modTrapdoorBlock(UPBlocks.FOXII_TRAPDOOR);
        modDoorBlock(UPBlocks.FOXII_DOOR);
        modPressurePlateBlock(UPBlocks.FOXII_PRESSURE_PLATE, UPBlocks.FOXII_PLANKS);
        modStairsBlock(UPBlocks.FOXII_STAIRS, UPBlocks.FOXII_PLANKS);
        modSlabBlock(UPBlocks.FOXII_SLAB, UPBlocks.FOXII_PLANKS);
        basicBlockWithRenderType(UPBlocks.FOXII_LEAVES, "cutout");
        basicButtonBlock(UPBlocks.FOXII_BUTTON, UPBlocks.FOXII_PLANKS);

        // Ginkgo
        basicBlock(UPBlocks.GINKGO_PLANKS);
        modSignBlock(UPBlocks.GINKGO_SIGN, UPBlocks.GINKGO_PLANKS);
        modHangingSignBlock(UPBlocks.GINKGO_HANGING_SIGN, UPBlocks.STRIPPED_GINKGO_LOG);
        modLogBlock(UPBlocks.GINKGO_LOG);
        modLogBlock(UPBlocks.STRIPPED_GINKGO_LOG);
        modWoodBlock(UPBlocks.GINKGO_WOOD, UPBlocks.GINKGO_LOG);
        modWoodBlock(UPBlocks.STRIPPED_GINKGO_WOOD, UPBlocks.STRIPPED_GINKGO_LOG);
        modFenceBlock(UPBlocks.GINKGO_FENCE, UPBlocks.GINKGO_PLANKS);
        modFenceGateBlock(UPBlocks.GINKGO_FENCE_GATE, UPBlocks.GINKGO_PLANKS);
        modTrapdoorWithRenderType(UPBlocks.GINKGO_TRAPDOOR, "cutout");
        modDoorBlockWithRenderType(UPBlocks.GINKGO_DOOR, "cutout");
        modPressurePlateBlock(UPBlocks.GINKGO_PRESSURE_PLATE, UPBlocks.GINKGO_PLANKS);
        modStairsBlock(UPBlocks.GINKGO_STAIRS, UPBlocks.GINKGO_PLANKS);
        modSlabBlock(UPBlocks.GINKGO_SLAB, UPBlocks.GINKGO_PLANKS);
        basicButtonBlock(UPBlocks.GINKGO_BUTTON, UPBlocks.GINKGO_PLANKS);
        basicBlockWithRenderType(UPBlocks.GINKGO_LEAVES, "cutout");
        plantWithPottedBlock(UPBlocks.GINKGO_SAPLING, UPBlocks.POTTED_GINKGO_SAPLING);

        // Petrified
        basicBlock(UPBlocks.PETRIFIED_PLANKS);
        modSignBlock(UPBlocks.PETRIFIED_SIGN, UPBlocks.PETRIFIED_PLANKS);
        modHangingSignBlock(UPBlocks.PETRIFIED_HANGING_SIGN, UPBlocks.STRIPPED_PETRIFIED_LOG);
        modLogBlock(UPBlocks.PETRIFIED_LOG);
        modLogBlock(UPBlocks.STRIPPED_PETRIFIED_LOG);
        modWoodBlock(UPBlocks.PETRIFIED_WOOD, UPBlocks.PETRIFIED_LOG);
        modWoodBlock(UPBlocks.STRIPPED_PETRIFIED_WOOD, UPBlocks.STRIPPED_PETRIFIED_LOG);
        modFenceBlock(UPBlocks.PETRIFIED_FENCE, UPBlocks.PETRIFIED_PLANKS);
        modFenceGateBlock(UPBlocks.PETRIFIED_FENCE_GATE, UPBlocks.PETRIFIED_PLANKS);
        modTrapdoorWithRenderType(UPBlocks.PETRIFIED_TRAPDOOR, "cutout");
        modDoorBlockWithRenderType(UPBlocks.PETRIFIED_DOOR, "cutout");
        modPressurePlateBlock(UPBlocks.PETRIFIED_PRESSURE_PLATE, UPBlocks.PETRIFIED_PLANKS);
        modStairsBlock(UPBlocks.PETRIFIED_STAIRS, UPBlocks.PETRIFIED_PLANKS);
        modSlabBlock(UPBlocks.PETRIFIED_SLAB, UPBlocks.PETRIFIED_PLANKS);
        basicButtonBlock(UPBlocks.PETRIFIED_BUTTON, UPBlocks.PETRIFIED_PLANKS);
        plantWithPottedBlock(UPBlocks.PETRIFIED_BUSH, UPBlocks.POTTED_PETRIFIED_BUSH);

        basicBlock(UPBlocks.POLISHED_PETRIFIED_WOOD);
        modStairsBlock(UPBlocks.POLISHED_PETRIFIED_WOOD_STAIRS, UPBlocks.POLISHED_PETRIFIED_WOOD);
        modSlabBlock(UPBlocks.POLISHED_PETRIFIED_WOOD_SLAB, UPBlocks.POLISHED_PETRIFIED_WOOD);
        modWallBlock(UPBlocks.POLISHED_PETRIFIED_WOOD_WALL, UPBlocks.POLISHED_PETRIFIED_WOOD);

        // Zuloagae
        basicBlock(UPBlocks.ZULOAGAE_PLANKS);
        modSignBlock(UPBlocks.ZULOAGAE_SIGN, UPBlocks.ZULOAGAE_PLANKS);
        modHangingSignBlock(UPBlocks.ZULOAGAE_HANGING_SIGN, UPBlocks.STRIPPED_ZULOAGAE_BLOCK);
        modLogBlock(UPBlocks.ZULOAGAE_BLOCK);
        modLogBlock(UPBlocks.STRIPPED_ZULOAGAE_BLOCK);
        modFenceBlock(UPBlocks.ZULOAGAE_FENCE, UPBlocks.ZULOAGAE_PLANKS);
        modFenceGateBlock(UPBlocks.ZULOAGAE_FENCE_GATE, UPBlocks.ZULOAGAE_PLANKS);
        modTrapdoorWithRenderType(UPBlocks.ZULOAGAE_TRAPDOOR, "cutout");
        modDoorBlockWithRenderType(UPBlocks.ZULOAGAE_DOOR, "cutout");
        modPressurePlateBlock(UPBlocks.ZULOAGAE_PRESSURE_PLATE, UPBlocks.ZULOAGAE_PLANKS);
        modStairsBlock(UPBlocks.ZULOAGAE_STAIRS, UPBlocks.ZULOAGAE_PLANKS);
        modSlabBlock(UPBlocks.ZULOAGAE_SLAB, UPBlocks.ZULOAGAE_PLANKS);
        basicButtonBlock(UPBlocks.ZULOAGAE_BUTTON, UPBlocks.ZULOAGAE_PLANKS);
        plantWithPottedBlock(UPBlocks.ZULOAGAE_SAPLING, UPBlocks.POTTED_ZULOAGAE_SAPLING);

        // Water eggs
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

        basicBlock(UPBlocks.ASPHALT);

        // Fossil ores
        basicBlock(UPBlocks.STONE_FOSSIL);
        basicBlock(UPBlocks.DEEPSLATE_FOSSIL);

        basicBlock(UPBlocks.PLANT_FOSSIL);
        basicBlock(UPBlocks.DEEPSLATE_PLANT_FOSSIL);

        basicBlock(UPBlocks.STONE_AMBER_FOSSIL);
        basicBlock(UPBlocks.DEEPSLATE_AMBER_FOSSIL);

        basicBlock(UPBlocks.OPAL_ORE);
        basicBlock(UPBlocks.DEEPSLATE_OPAL_ORE);
        basicBlock(UPBlocks.BLACK_OPAL_ORE);
        basicBlock(UPBlocks.DEEPSLATE_BLACK_OPAL_ORE);

        basicBlock(UPBlocks.STONE_TAR_FOSSIL);
        basicBlock(UPBlocks.DEEPSLATE_TAR_FOSSIL);

        basicBlock(UPBlocks.PERMAFROST);
        basicBlock(UPBlocks.PERMAFROST_FOSSIL);

        basicBlock(UPBlocks.AMBER_BLOCK);

        basicBlock(UPBlocks.OPAL_BLOCK);
        basicBlock(UPBlocks.BLACK_OPAL_BLOCK);

        // Potted plants
        createPottedPlant(UPBlocks.ARCHAEOSIGILARIA, UPBlocks.POTTED_ARCHAEOSIGILARIA,"cutout");
        createPottedPlant(UPBlocks.BENNETTITALES, UPBlocks.POTTED_BENNETTITALES,"cutout");
        createPottedPlant(UPBlocks.HORSETAIL, UPBlocks.POTTED_HORSETAIL,"cutout");
        createPottedPlant(UPBlocks.LEEFRUCTUS, UPBlocks.POTTED_LEEFRUCTUS,"cutout");
        createPottedPlant(UPBlocks.SARACENIA, UPBlocks.POTTED_SARACENIA,"cutout");
    }

    private void basicBlock(Supplier<? extends Block> block) {
        simpleBlock(block.get());
    }

    private void basicBlockWithRenderType(Supplier<? extends Block> block, String renderType) {
        ModelFile modelFile = models().cubeAll(name(block.get()), blockTexture(block.get())).renderType(renderType);
        getVariantBuilder(block.get()).partialState().setModels(new ConfiguredModel(modelFile));
    }

    private void pillarBlock(Supplier<? extends Block> block, String topTexture) {
        axisBlock((RotatedPillarBlock) block.get(), modBlockLocation(name(block.get())), modBlockLocation(topTexture));
    }

    private void modLogBlock(Supplier<? extends Block> block) {
        this.logBlock((RotatedPillarBlock) block.get());
    }

    private void modWoodBlock(Supplier<? extends Block> block, Supplier<? extends Block> blockForTexture) {
        axisBlock((RotatedPillarBlock) block.get(),
                models().cubeColumn(name(block.get()), modBlockLocation(name(blockForTexture.get())), modBlockLocation(name(blockForTexture.get()))),
                models().cubeColumnHorizontal(name(block.get()), modBlockLocation(name(blockForTexture.get())), modBlockLocation(name(blockForTexture.get()))));
    }

    private void modFenceBlock(Supplier<? extends Block> block, Supplier<? extends Block> blockForTexture) {
        fenceBlock((FenceBlock) block.get(), modBlockLocation(name(blockForTexture.get())));
    }

    private void modFenceGateBlock(Supplier<? extends Block> block, Supplier<? extends Block> blockForTexture) {
        fenceGateBlock((FenceGateBlock) block.get(), modBlockLocation(name(blockForTexture.get())));
    }

    private void modPressurePlateBlock(Supplier<? extends Block> block, Supplier<? extends Block> blockForTexture) {
        pressurePlateBlock((PressurePlateBlock) block.get(), modBlockLocation(name(blockForTexture.get())));
    }

    private void modDoorBlock(Supplier<? extends Block> block) {
        doorBlock((DoorBlock) block.get(), modBlockLocation(name(block.get()) + "_bottom"), modBlockLocation(name(block.get()) + "_top"));
    }
    private void modDoorBlockWithRenderType(Supplier<? extends Block> block, String rendertype) {
        doorBlockWithRenderType((DoorBlock) block.get(), modBlockLocation(name(block.get()) + "_bottom"), modBlockLocation(name(block.get()) + "_top"), rendertype);
    }

    private void modTrapdoorBlock(Supplier<? extends Block> block) {
        trapdoorBlock((TrapDoorBlock) block.get(), modBlockLocation(name(block.get())), true);
    }
    private void modTrapdoorWithRenderType(Supplier<? extends Block> block, String renderType) {
        trapdoorBlockWithRenderType((TrapDoorBlock)block.get(), modBlockLocation(name(block.get())), true, renderType);
    }

    private void modSignBlock(Pair<RegistryObject<BlueprintStandingSignBlock>, RegistryObject<BlueprintWallSignBlock>> pair, Supplier<? extends Block> blockForTexture) {
        signBlock(pair.getFirst().get(), pair.getSecond().get(), modBlockLocation(name(blockForTexture.get())));
    }
    private void modHangingSignBlock(Pair<RegistryObject<BlueprintCeilingHangingSignBlock>, RegistryObject<BlueprintWallHangingSignBlock>> pair, Supplier<? extends Block> blockForTexture) {
        ModelFile sign = models().sign(name(pair.getFirst().get()), modBlockLocation(name(blockForTexture.get())));
        simpleBlock(pair.getFirst().get(), sign);
        simpleBlock(pair.getSecond().get(), sign);
    }

    private void basicButtonBlock(Supplier<? extends Block> block, Supplier<? extends Block> blockForTexture) {
        buttonBlock((ButtonBlock)block.get(), blockTexture(blockForTexture.get()));
    }

    private void modStairsBlock(Supplier<? extends Block> block, Supplier<? extends Block> blockForTexture) {
        stairsBlock((StairBlock) block.get(), blockTexture(blockForTexture.get()));
    }
    private void modWallBlock(Supplier<? extends Block> block, Supplier<? extends Block> blockForTexture) {
        wallBlock((WallBlock) block.get(), blockTexture(blockForTexture.get()));
    }

    private void modSlabBlock(Supplier<? extends Block> block, Supplier<? extends Block> blockForTexture) {
        slabBlock((SlabBlock) block.get(), blockTexture(blockForTexture.get()), blockTexture(blockForTexture.get()));
    }

    private void modCrossBlock(Supplier<? extends Block> block, String renderType) {
        getVariantBuilder(block.get()).forAllStates(blockState -> ConfiguredModel.builder()
                .modelFile(models().cross(name(block.get()), modBlockLocation(name(block.get()))).renderType(renderType)).build());
    }

    private void plantWithPottedBlock(Supplier<? extends Block> plant, Supplier<? extends Block> potted_plant) {
        modCrossBlock(plant, "cutout");
        simpleBlock(potted_plant.get(), models().withExistingParent(name(potted_plant.get()), POTTED_CROSS)
                .texture(PLANT, modBlockLocation(name(plant.get()))).renderType("cutout"));
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
        return models().singleTexture("block/eggs/" + modifier + baseName.replace(UnusualPrehistory.MODID + ":", ""), new ResourceLocation(UnusualPrehistory.MODID, "block/templates/flat_water_egg"), blockTextureEggs(block));
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