package com.peeko32213.unusualprehistory.data.client.models;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
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

import static com.peeko32213.unusualprehistory.data.UPDatagenUtils.*;

public class UPItemModelGenerator extends ItemModelProvider {
    public UPItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
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

        // Plants
        itemFromBlockTexture(UPBlocks.ARCHAEOSIGILARIA);
        itemFromBlockTexture(UPBlocks.BENNETTITALES);
        itemFromBlockTexture(UPBlocks.HORSETAIL);
        itemFromBlockTexture(UPBlocks.LEEFRUCTUS);
        itemFromBlockTexture(UPBlocks.SARACENIA);
        itemFromBlockTexture(UPBlocks.ISOETES_BEESTONII);
        itemFromBlockTexture(UPBlocks.CLADOPHLEBIS);
        itemFromBlockTexture(UPBlocks.ARCHAEFRUCTUS);
        itemFromBlockTexture(UPBlocks.COOKSONIA);

        // Fossils
        fossilItem(UPItems.PALEO_FOSSIL);
        fossilItem(UPItems.MEZO_FOSSIL);
        fossilItem(UPItems.PLANT_FOSSIL);
        fossilItem(UPItems.TAR_FOSSIL);
        fossilItem(UPItems.FROZEN_FOSSIL);
        fossilItem(UPItems.DEFROSTED_FROZEN_FOSSIL);
        fossilItem(UPItems.AMBER_FOSSIL);
        fossilItem(UPItems.OPAL_FOSSIL);
        fossilItem(UPItems.FIRE_OPAL_FOSSIL);
        fossilItem(UPItems.BOULDER_OPAL_FOSSIL);
        fossilItem(UPItems.BLACK_OPAL_FOSSIL);

        // DNA
        dnaItem(UPItems.ZULOAGAE_DNA);
        dnaItem(UPItems.RAIGUENRAYUN_DNA);
        dnaItem(UPItems.FOXII_DNA);
        dnaItem(UPItems.DRYO_DNA);
        dnaItem(UPItems.DIPLO_DNA);
        dnaItem(UPItems.KIMMER_DNA);
        dnaItem(UPItems.HYNERIA_DNA);
        dnaItem(UPItems.OPHIO_DNA);
        dnaItem(UPItems.PROTOSPHYRAENA_DNA);
        dnaItem(UPItems.EDAPHO_DNA);
        dnaItem(UPItems.ESTEMMENO_DNA);
        dnaItem(UPItems.TARTUO_DNA);
        dnaItem(UPItems.PSITTACO_DNA);
        dnaItem(UPItems.KAPRO_DNA);
        dnaItem(UPItems.HYNERP_DNA);
        dnaItem(UPItems.PTERODAUSTRO_DNA);
        dnaItem(UPItems.JAWLESS_FISH_DNA);
        dnaItem(UPItems.LEEDS_DNA);
        dnaItem(UPItems.AMMONITE_DNA);
        dnaItem(UPItems.COTY_DNA);
        dnaItem(UPItems.DUNK_DNA);
        dnaItem(UPItems.SCAU_DNA);
        dnaItem(UPItems.STETHA_DNA);
        dnaItem(UPItems.ANTARCTO_DNA);
        dnaItem(UPItems.ANURO_DNA);
        dnaItem(UPItems.AUSTRO_DNA);
        dnaItem(UPItems.BEELZ_DNA);
        dnaItem(UPItems.BRACHI_DNA);
        dnaItem(UPItems.ENCRUSTED_DNA);
        dnaItem(UPItems.ERYON_DNA);
        dnaItem(UPItems.HWACHA_DNA);
        dnaItem(UPItems.KENTRO_DNA);
        dnaItem(UPItems.MAJUNGA_DNA);
        dnaItem(UPItems.PACHY_DNA);
        dnaItem(UPItems.TRIKE_DNA);
        dnaItem(UPItems.TYRANNO_DNA);
        dnaItem(UPItems.ULUGH_DNA);
        dnaItem(UPItems.VELOCI_DNA);
        dnaItem(UPItems.BARINA_DNA);
        dnaItem(UPItems.GIGANTO_DNA);
        dnaItem(UPItems.MAMMOTH_DNA);
        dnaItem(UPItems.MEGALANIA_DNA);
        dnaItem(UPItems.MEGATHERIUM_DNA);
        dnaItem(UPItems.PALAEO_DNA);
        dnaItem(UPItems.PARACER_DNA);
        dnaItem(UPItems.SMILODON_DNA);
        dnaItem(UPItems.TALPANAS_DNA);
        dnaItem(UPItems.TELECREX_DNA);
        dnaItem(UPItems.ANOSTYLOSTRAMA_DNA);
        dnaItem(UPItems.ARCHAEFRUCTUS_DNA);
        dnaItem(UPItems.ARCHAO_DNA);
        dnaItem(UPItems.BENNET_DNA);
        dnaItem(UPItems.CLATHRODICTYON_DNA);
        dnaItem(UPItems.GINKGO_DNA);
        dnaItem(UPItems.HORSETAIL_DNA);
        dnaItem(UPItems.LEEFRUCTUS_DNA);
        dnaItem(UPItems.NELUMBITES_DNA);
        dnaItem(UPItems.QUEREUXIA_DNA);
        dnaItem(UPItems.SARR_DNA);
        dnaItem(UPItems.PANACANTHOCARIS_DNA);

        // Eggs
        eggItem(UPItems.TELECREX_EGG);

        // Embryos
        embryoItem(UPItems.SMILODON_EMBRYO);
        embryoItem(UPItems.MAMMOTH_EMBRYO);
        embryoItem(UPItems.MEGATH_EMBRYO);
        embryoItem(UPItems.GIGANTO_EMBRYO);
        embryoItem(UPItems.PARACER_EMBRYO);
        embryoItem(UPItems.PALAEO_EMBRYO);
        embryoItem(UPItems.UNICORN_EMBRYO);

        // Eggs
        for(RegistryObject<?> object : UPEntities.prehistorics) {
            addPrehistoricEgg(object.getId());
        }

        // Fossil skeleton items
        basicItem(UPItems.TRIKE_SKELETON);
        basicItem(UPItems.TYRANNO_SKELETON);
        basicItem(UPItems.UNICORN_SKELETON);

        // Bottle items
        singleTex(UPItems.CAPTURED_KIMMER_BOTTLE);

        // Animal items
        singleTex(UPItems.BEELZ_SALIVA);
        singleTex(UPItems.AUSTRO_FEATHER);
        singleTex(UPItems.VELOCI_FEATHER);
        singleTex(UPItems.ANTARCTO_PLATE);
        singleTex(UPItems.MAJUNGA_SCUTE);
        singleTex(UPItems.SMILO_FUR);
        singleTex(UPItems.TRIKE_HORN);
        singleTex(UPItems.TYRANNO_SCALE);
        singleTex(UPItems.TYRANNO_TOOTH);
        singleTex(UPItems.PALAEO_SKIN);
        singleTex(UPItems.POUCH);
        singleTex(UPItems.OPALESCENT_PEARL);
        singleTex(UPItems.ZULOGAE_DISC);
        singleTex(UPItems.ENCASED_DISC);
        singleTex(UPItems.OPALESENCE_DISC);
        singleTex(UPItems.TARIFYING_DISC);
        singleTex(UPItems.MAMMOTH_MEATBALL);
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

        basicItem(UPItems.ADORNED_STAFF);
        basicItem(UPItems.AMBER_GUMMY);
        basicItem(UPItems.ORGANIC_OOZE);
        basicItem(UPItems.SHELL_SHARD);
        basicItem(UPBlocks.AMMONITE_SHELL.get().asItem());
        basicItem(UPItems.MEAT_ON_A_STICK);
        basicItem(UPItems.WARPICK);
        basicItem(UPItems.SHEDSCALE_BOOTS);
        basicItem(UPItems.SHEDSCALE_CHESTPLATE);
        basicItem(UPItems.SHEDSCALE_HELMET);
        basicItem(UPItems.SHEDSCALE_LEGGINGS);
        basicItem(UPItems.TYRANTS_CROWN);
        basicItem(UPItems.SLOTH_POUCH);
        basicItem(UPItems.GROG);
        basicItem(UPBlocks.ZULOAGAE.get().asItem());
        basicItem(UPBlocks.QUEREUXIA.get().asItem());

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
        toBlock(UPBlocks.STONE_AMBER_FOSSIL);
        toBlock(UPBlocks.DEEPSLATE_AMBER_FOSSIL);
        toBlock(UPBlocks.PERMAFROST);
        toBlock(UPBlocks.PERMAFROST_FOSSIL);

        // Opal ores
        toBlock(UPBlocks.OPAL_ORE);
        toBlock(UPBlocks.DEEPSLATE_OPAL_ORE);

        toBlock(UPBlocks.BLACK_OPAL_ORE);
        toBlock(UPBlocks.DEEPSLATE_BLACK_OPAL_ORE);

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

    private ItemModelBuilder fossilItem(RegistryObject<Item> item) {
        return generated(item.getId().getPath(), prefix("item/fossils/" + item.getId().getPath()));
    }

    private ItemModelBuilder dnaItem(RegistryObject<Item> item) {
        return generated(item.getId().getPath(), prefix("item/dna/" + item.getId().getPath()));
    }

    private ItemModelBuilder eggItem(RegistryObject<Item> item) {
        return generated(item.getId().getPath(), prefix("item/eggs/" + item.getId().getPath()));
    }

    private ItemModelBuilder embryoItem(RegistryObject<Item> item) {
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
