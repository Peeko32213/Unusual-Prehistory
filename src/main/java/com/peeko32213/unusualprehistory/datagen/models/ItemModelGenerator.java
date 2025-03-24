package com.peeko32213.unusualprehistory.datagen.models;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.blocks.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;
import java.util.function.Supplier;

import static com.peeko32213.unusualprehistory.datagen.UPDatagenUtils.*;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, UnusualPrehistory.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels(){

        // Dryophyllum
        basicBlockItem(UPBlocks.DRYO_PLANKS);
        basicBlockItem(UPBlocks.DRYO_LOG);
        basicBlockItem(UPBlocks.STRIPPED_DRYO_LOG);
        basicBlockItem(UPBlocks.DRYO_WOOD);
        basicBlockItem(UPBlocks.STRIPPED_DRYO_WOOD);
        basicBlockItem(UPBlocks.DRYO_PRESSURE_PLATE);
        trapdoorBlockItem(UPBlocks.DRYO_TRAPDOOR);
        basicBlockItem(UPBlocks.DRYO_STAIRS);
        basicBlockItem(UPBlocks.DRYO_SLAB);
        modButtonBlockItem(UPBlocks.DRYO_BUTTON, UPBlocks.DRYO_PLANKS);
        fenceBlockItem(UPBlocks.DRYO_FENCE, UPBlocks.DRYO_PLANKS);
        basicBlockItem(UPBlocks.DRYO_FENCE_GATE);
        blockItemWithItemTexture(UPBlocks.DRYO_SIGN.getFirst());
        blockItemWithItemTexture(UPBlocks.DRYO_HANGING_SIGN.getFirst());
        blockItemWithItemTexture(UPBlocks.DRYO_DOOR);
        basicBlockItem(UPBlocks.DRYO_LEAVES);
        itemFromBlockTexture(UPBlocks.DRYO_SAPLING);
        basicItem(UPItems.DRYO_BOAT.getFirst());
        basicItem(UPItems.DRYO_BOAT.getSecond());
        basicItem(UPItems.DRYO_NUTS);

        // Foxii
        basicBlockItem(UPBlocks.FOXII_PLANKS);
        basicBlockItem(UPBlocks.FOXII_LOG);
        basicBlockItem(UPBlocks.STRIPPED_FOXII_LOG);
        basicBlockItem(UPBlocks.FOXII_WOOD);
        basicBlockItem(UPBlocks.STRIPPED_FOXII_WOOD);
        basicBlockItem(UPBlocks.FOXII_PRESSURE_PLATE);
        trapdoorBlockItem(UPBlocks.FOXII_TRAPDOOR);
        basicBlockItem(UPBlocks.FOXII_STAIRS);
        basicBlockItem(UPBlocks.FOXII_SLAB);
        modButtonBlockItem(UPBlocks.FOXII_BUTTON, UPBlocks.FOXII_PLANKS);
        fenceBlockItem(UPBlocks.FOXII_FENCE, UPBlocks.FOXII_PLANKS);
        basicBlockItem(UPBlocks.FOXII_FENCE_GATE);
        blockItemWithItemTexture(UPBlocks.FOXII_SIGN.getFirst());
        blockItemWithItemTexture(UPBlocks.FOXII_HANGING_SIGN.getFirst());
        blockItemWithItemTexture(UPBlocks.FOXII_DOOR);
        basicBlockItem(UPBlocks.FOXII_LEAVES);
        itemFromBlockTexture(UPBlocks.FOXII_SAPLING);
        basicItem(UPItems.FOXII_BOAT.getFirst());
        basicItem(UPItems.FOXII_BOAT.getSecond());

        // Ginkgo
        basicBlockItem(UPBlocks.GINKGO_PLANKS);
        basicBlockItem(UPBlocks.GINKGO_LOG);
        basicBlockItem(UPBlocks.STRIPPED_GINKGO_LOG);
        basicBlockItem(UPBlocks.GINKGO_WOOD);
        basicBlockItem(UPBlocks.STRIPPED_GINKGO_WOOD);
        basicBlockItem(UPBlocks.GINKGO_PRESSURE_PLATE);
        trapdoorBlockItem(UPBlocks.GINKGO_TRAPDOOR);
        basicBlockItem(UPBlocks.GINKGO_STAIRS);
        basicBlockItem(UPBlocks.GINKGO_SLAB);
        modButtonBlockItem(UPBlocks.GINKGO_BUTTON, UPBlocks.GINKGO_PLANKS);
        fenceBlockItem(UPBlocks.GINKGO_FENCE, UPBlocks.GINKGO_PLANKS);
        basicBlockItem(UPBlocks.GINKGO_FENCE_GATE);
        blockItemWithItemTexture(UPBlocks.GINKGO_SIGN.getFirst());
        blockItemWithItemTexture(UPBlocks.GINKGO_HANGING_SIGN.getFirst());
        blockItemWithItemTexture(UPBlocks.GINKGO_DOOR);
        basicBlockItem(UPBlocks.GINKGO_LEAVES);
        itemFromBlockTexture(UPBlocks.GINKGO_SAPLING);
        basicItem(UPItems.GINKGO_BOAT.getFirst());
        basicItem(UPItems.GINKGO_BOAT.getSecond());
        basicItem(UPItems.RAW_GINKGO_SEEDS);
        basicItem(UPItems.COOKED_GINKGO_SEEDS);
        basicItem(UPItems.GINKGO_FRUIT);

        // Petrified
        basicBlockItem(UPBlocks.PETRIFIED_PLANKS);
        basicBlockItem(UPBlocks.PETRIFIED_LOG);
        basicBlockItem(UPBlocks.STRIPPED_PETRIFIED_LOG);
        basicBlockItem(UPBlocks.PETRIFIED_WOOD);
        basicBlockItem(UPBlocks.STRIPPED_PETRIFIED_WOOD);
        basicBlockItem(UPBlocks.PETRIFIED_PRESSURE_PLATE);
        trapdoorBlockItem(UPBlocks.PETRIFIED_TRAPDOOR);
        basicBlockItem(UPBlocks.PETRIFIED_STAIRS);
        basicBlockItem(UPBlocks.PETRIFIED_SLAB);
        modButtonBlockItem(UPBlocks.PETRIFIED_BUTTON, UPBlocks.PETRIFIED_PLANKS);
        fenceBlockItem(UPBlocks.PETRIFIED_FENCE, UPBlocks.PETRIFIED_PLANKS);
        basicBlockItem(UPBlocks.PETRIFIED_FENCE_GATE);
        blockItemWithItemTexture(UPBlocks.PETRIFIED_SIGN.getFirst());
        blockItemWithItemTexture(UPBlocks.PETRIFIED_HANGING_SIGN.getFirst());
        blockItemWithItemTexture(UPBlocks.PETRIFIED_DOOR);
        itemFromBlockTexture(UPBlocks.PETRIFIED_BUSH);
        basicBlockItem(UPBlocks.POLISHED_PETRIFIED_WOOD);
        basicBlockItem(UPBlocks.POLISHED_PETRIFIED_WOOD_STAIRS);
        basicBlockItem(UPBlocks.POLISHED_PETRIFIED_WOOD_SLAB);
        wallBlockItem(UPBlocks.POLISHED_PETRIFIED_WOOD_WALL, UPBlocks.POLISHED_PETRIFIED_WOOD);

        // Zuloagae
        basicBlockItem(UPBlocks.ZULOAGAE_PLANKS);
        basicBlockItem(UPBlocks.ZULOAGAE_BLOCK);
        basicBlockItem(UPBlocks.STRIPPED_ZULOAGAE_BLOCK);
        basicBlockItem(UPBlocks.ZULOAGAE_PRESSURE_PLATE);
        trapdoorBlockItem(UPBlocks.ZULOAGAE_TRAPDOOR);
        basicBlockItem(UPBlocks.ZULOAGAE_STAIRS);
        basicBlockItem(UPBlocks.ZULOAGAE_SLAB);
        modButtonBlockItem(UPBlocks.ZULOAGAE_BUTTON, UPBlocks.ZULOAGAE_PLANKS);
        fenceBlockItem(UPBlocks.ZULOAGAE_FENCE, UPBlocks.ZULOAGAE_PLANKS);
        basicBlockItem(UPBlocks.ZULOAGAE_FENCE_GATE);
        blockItemWithItemTexture(UPBlocks.ZULOAGAE_SIGN.getFirst());
        blockItemWithItemTexture(UPBlocks.ZULOAGAE_HANGING_SIGN.getFirst());
        blockItemWithItemTexture(UPBlocks.ZULOAGAE_DOOR);

        // Fossils
        fossilTex(UPItems.PALEO_FOSSIL);
        fossilTex(UPItems.MEZO_FOSSIL);
        fossilTex(UPItems.PLANT_FOSSIL);
        fossilTex(UPItems.TAR_FOSSIL);
        fossilTex(UPItems.FROZEN_FOSSIL);
        fossilTex(UPItems.DEFROSTED_FROZEN_FOSSIL);
        fossilTex(UPItems.AMBER_FOSSIL);
        fossilTex(UPItems.OPAL_FOSSIL);
        fossilTex(UPItems.FIRE_OPAL_FOSSIL);
        fossilTex(UPItems.BOULDER_OPAL_FOSSIL);
        fossilTex(UPItems.BLACK_OPAL_FOSSIL);

        // DNA
        dnaTex(UPItems.ZULOAGAE_DNA);
        dnaTex(UPItems.RAIGUENRAYUN_DNA);
        dnaTex(UPItems.FOXII_DNA);
        dnaTex(UPItems.DRYO_DNA);
        dnaTex(UPItems.DIPLO_DNA);
        dnaTex(UPItems.KIMMER_DNA);
        dnaTex(UPItems.HYNERIA_DNA);
        dnaTex(UPItems.OPHIO_DNA);
        dnaTex(UPItems.PROTOSPHYRAENA_DNA);
        dnaTex(UPItems.PTERY_DNA);
        dnaTex(UPItems.EDAPHO_DNA);
        dnaTex(UPItems.XIPHACT_DNA);
        dnaTex(UPItems.OVIRAPTOR_DNA);
        dnaTex(UPItems.GLOBIDENS_DNA);
        dnaTex(UPItems.ESTEMMENO_DNA);
        dnaTex(UPItems.OTAROCYON_DNA);
        dnaTex(UPItems.LONGI_DNA);
        dnaTex(UPItems.TARTUO_DNA);
        dnaTex(UPItems.TANY_DNA);
        dnaTex(UPItems.PSITTACO_DNA);
        dnaTex(UPItems.KAPRO_DNA);
        dnaTex(UPItems.PSILO_DNA);
        dnaTex(UPItems.HYNERP_DNA);
        dnaTex(UPItems.BALAUR_DNA);
        dnaTex(UPItems.PTERODAUSTRO_DNA);
        dnaTex(UPItems.ARCHELON_DNA);
        dnaTex(UPItems.JAWLESS_FISH_DNA);
        dnaTex(UPItems.LEEDS_DNA);
        dnaTex(UPItems.AMMONITE_DNA);
        dnaTex(UPItems.COTY_DNA);
        dnaTex(UPItems.DUNK_DNA);
        dnaTex(UPItems.SCAU_DNA);
        dnaTex(UPItems.STETHA_DNA);
        dnaTex(UPItems.ANTARCTO_DNA);
        dnaTex(UPItems.ANURO_DNA);
        dnaTex(UPItems.AUSTRO_DNA);
        dnaTex(UPItems.BEELZ_DNA);
        dnaTex(UPItems.BRACHI_DNA);
        dnaTex(UPItems.ENCRUSTED_DNA);
        dnaTex(UPItems.ERYON_DNA);
        dnaTex(UPItems.HWACHA_DNA);
        dnaTex(UPItems.KENTRO_DNA);
        dnaTex(UPItems.MAJUNGA_DNA);
        dnaTex(UPItems.PACHY_DNA);
        dnaTex(UPItems.TRIKE_DNA);
        dnaTex(UPItems.TYRANNO_DNA);
        dnaTex(UPItems.ULUGH_DNA);
        dnaTex(UPItems.VELOCI_DNA);
        dnaTex(UPItems.BARINA_DNA);
        dnaTex(UPItems.GIGANTO_DNA);
        dnaTex(UPItems.MAMMOTH_DNA);
        dnaTex(UPItems.MEGALANIA_DNA);
        dnaTex(UPItems.MEGATHERIUM_DNA);
        dnaTex(UPItems.PALAEO_DNA);
        dnaTex(UPItems.PARACER_DNA);
        dnaTex(UPItems.SMILODON_DNA);
        dnaTex(UPItems.TALPANAS_DNA);
        dnaTex(UPItems.ANOSTYLOSTRAMA_DNA);
        dnaTex(UPItems.ARCHAEFRUCTUS_DNA);
        dnaTex(UPItems.ARCHAO_DNA);
        dnaTex(UPItems.BENNET_DNA);
        dnaTex(UPItems.CLATHRODICTYON_DNA);
        dnaTex(UPItems.GINKGO_DNA);
        dnaTex(UPItems.HORSETAIL_DNA);
        dnaTex(UPItems.LEEFRUCTUS_DNA);
        dnaTex(UPItems.NELUMBITES_DNA);
        dnaTex(UPItems.QUEREUXIA_DNA);
        dnaTex(UPItems.SARR_DNA);
        dnaTex(UPItems.ANIMAL_DNA_BOTTLES);
        dnaTex(UPItems.PLANT_DNA_BOTTLES);

        // Embryos
        embryoTex(UPItems.SMILODON_EMBRYO);
        embryoTex(UPItems.MAMMOTH_EMBRYO);
        embryoTex(UPItems.MEGATH_EMBRYO);
        embryoTex(UPItems.GIGANTO_EMBRYO);
        embryoTex(UPItems.PARACER_EMBRYO);
        embryoTex(UPItems.PALAEO_EMBRYO);
        embryoTex(UPItems.OTAROCYON_EMBRYO);

        // Eggs
        for(RegistryObject<?> object : UPEntities.prehistorics) {
            addPrehistoricEgg(object.getId());
        }

        // Fossil skeleton items
        singleTex(UPItems.TRIKE_SKELETON);
        singleTex(UPItems.TYRANNO_SKELETON);

        // Bottle items
        singleTex(UPItems.CAPTURED_KIMMER_BOTTLE);
        singleTex(UPItems.DORMANT_RAMPAGE_BOTTLE);
        singleTex(UPItems.YIXIAN_RAMPAGE_BOTTLE);

        // Animal items
        singleTex(UPItems.BEELZ_SALIVA);
        singleTex(UPItems.AUSTRO_FEATHER);
        singleTex(UPItems.VELOCI_FEATHERS);
        singleTex(UPItems.ANTARCTO_PLATE);
        singleTex(UPItems.MAJUNGA_SCUTE);
        singleTex(UPItems.SMILO_FUR);
        singleTex(UPItems.TRIKE_HORN);
        singleTex(UPItems.TYRANNO_SCALE);
        singleTex(UPItems.TYRANNO_TOOTH);
        singleTex(UPItems.RAW_COTY);
        singleTex(UPItems.COOKED_COTY);
        singleTex(UPItems.RAW_SCAU);
        singleTex(UPItems.COOKED_SCAU);
        singleTex(UPItems.GOLDEN_SCAU);
        singleTex(UPItems.PALAEO_SKIN);
        singleTex(UPItems.DINO_POUCH);
        singleTex(UPItems.OPALESCENT_PEARL);
        singleTex(UPItems.OPALESCENT_SHURIKEN);
        singleTex(UPItems.ZULOGAE_DISC);
        singleTex(UPItems.ENCASED_DISC);
        singleTex(UPItems.RAW_MAMMOTH);
        singleTex(UPItems.MAMMOTH_MEATBALL);
        singleTex(UPItems.COOKED_MAMMOTH);
        singleTex(UPItems.LEEDS_CAVIAR);
        singleTex(UPItems.CROCARINA);
        singleTex(UPItems.MAJUNGA_HELMET);
        singleTex(UPItems.AUSTRO_BOOTS);
        singleTex(UPItems.RED_FRUIT);
        singleTex(UPItems.RED_FRUIT_SCRAPS);
        singleTex(UPItems.WHITE_FRUIT);
        singleTex(UPItems.WHITE_FRUIT_SCRAPS);
        singleTex(UPItems.YELLOW_FRUIT);
        singleTex(UPItems.YELLOW_FRUIT_SCRAPS);
        singleTex(UPItems.BLUE_FRUIT);
        singleTex(UPItems.BLUE_FRUIT_SCRAPS);

        singleTex(UPItems.PSITTACOSAURUS_QUILL);
        singleTex(UPItems.PSITTACCO_ARROW);
        singleTex(UPItems.QUILL_REMEDY);

        // Amber
        singleTex(UPItems.AMBER);
        singleTex(UPItems.AMBER_IDOL);

        // Opal
        singleTex(UPItems.OPAL);
        singleTex(UPItems.FIRE_OPAL);
        singleTex(UPItems.BOULDER_OPAL);
        singleTex(UPItems.BLACK_OPAL);

        // Fossil ores
        toBlock(UPBlocks.STONE_FOSSIL);
        toBlock(UPBlocks.DEEPSLATE_FOSSIL);
        toBlock(UPBlocks.PLANT_FOSSIL);
        toBlock(UPBlocks.DEEPSLATE_PLANT_FOSSIL);
        toBlock(UPBlocks.STONE_TAR_FOSSIL);
        toBlock(UPBlocks.DEEPSLATE_TAR_FOSSIL);
        toBlock(UPBlocks.STONE_OPAL_FOSSIL);
        toBlock(UPBlocks.DEEPSLATE_OPAL_FOSSIL);
        toBlock(UPBlocks.STONE_AMBER_FOSSIL);
        toBlock(UPBlocks.DEEPSLATE_AMBER_FOSSIL);
        toBlock(UPBlocks.PERMAFROST);
        toBlock(UPBlocks.PERMAFROST_FOSSIL);

        basicBlockItem(UPBlocks.AMBER_BLOCK);

        // Opal blocks
        basicBlockItem(UPBlocks.OPAL_BLOCK);
        basicBlockItem(UPBlocks.BLACK_OPAL_BLOCK);

        basicBlockItem(UPBlocks.ASPHALT);

        for (Item i : BuiltInRegistries.ITEM) {
            if (i instanceof SpawnEggItem && Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(i)).getNamespace().equals(UnusualPrehistory.MODID)) {
                getBuilder(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(i)).getPath())
                        .parent(getExistingFile(new ResourceLocation("item/template_spawn_egg")));
            }
        }
    }

    private void basicItem(Supplier<? extends Item> item) {
        basicItem(item.get());
    }

    private void basicBlockItem(Supplier<? extends Block> blockForItem) {
        withExistingParent(name(blockForItem.get()), modBlockLocation(name(blockForItem.get())));
    }

    private void modButtonBlockItem(Supplier<? extends Block> blockForItem, Supplier<? extends Block> blockForTexture) {
        buttonInventory(name(blockForItem.get()), modBlockLocation(name(blockForTexture.get())));
    }

    private void wallBlockItem(Supplier<? extends Block> block, Supplier<? extends Block> blockForTexture) {
        wallInventory(name(block.get()), modBlockLocation(name(blockForTexture.get())));
    }

    private void itemFromBlockTexture(Supplier<? extends Block> block) {
        withExistingParent(name(block.get()), GENERATED).texture(LAYER0, modBlockLocation(name(block.get())));
    }

    private void blockItemWithItemTexture(Supplier<? extends Block> blockForItem) {
        basicItem(blockForItem.get().asItem());
    }

    private void fenceBlockItem(Supplier<? extends Block> block, Supplier<? extends Block> blockForTexture) {
        fenceInventory(name(block.get()), modBlockLocation(name(blockForTexture.get())));
    }

    private void basicBlockItemWithSuffix(Supplier<? extends Block> block, String suffix) {
        withExistingParent(name(block.get()), modBlockLocation(name(block.get()) + suffix));
    }

    private void trapdoorBlockItem(Supplier<? extends Block> block) {
        basicBlockItemWithSuffix(block, "_bottom");
    }

    private void toBlock(RegistryObject<Block> b) {
        toBlockModel(b, b.getId().getPath());
    }

    private void toBlockModel(RegistryObject<Block> b, String model) {
        toBlockModel(b, prefix("block/" + model));
    }

    private void toBlockModel(RegistryObject<Block> b, ResourceLocation model) {
        withExistingParent(b.getId().getPath(), model);
    }

    public void addPrehistoricEgg(Supplier<? extends EntityType<?>> dino) {
        generated( dino.get().getDescriptionId().replace("entity.unusualprehistory.", "") + "_entity_egg", prefix("item/" + dino.get().getDescriptionId().replace("entity.unusualprehistory.", "")  + "_egg"));
    }
    public void addPrehistoricEgg(ResourceLocation dino) {
        generated( dino.getPath() + "_egg", prefix("item/eggs/" + dino.getPath()  + "_egg"));
    }

    private ItemModelBuilder singleTex(RegistryObject<Item> item) {
        return generated(item.getId().getPath(), prefix("item/" + item.getId().getPath()));
    }

    private ItemModelBuilder fossilTex(RegistryObject<Item> item) {
        return generated(item.getId().getPath(), prefix("item/fossils/" + item.getId().getPath()));
    }

    private ItemModelBuilder dnaTex(RegistryObject<Item> item) {
        return generated(item.getId().getPath(), prefix("item/dna/" + item.getId().getPath()));
    }

    private ItemModelBuilder eggTex(RegistryObject<Item> item) {
        return generated(item.getId().getPath(), prefix("item/eggs/" + item.getId().getPath()));
    }

    private ItemModelBuilder embryoTex(RegistryObject<Item> item) {
        return generated(item.getId().getPath(), prefix("item/embryos/" + item.getId().getPath()));
    }

    private ItemModelBuilder generated(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, "item/generated");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }

    public static ResourceLocation prefix(String name){
        return new ResourceLocation(UnusualPrehistory.MODID, name);
    }
}
