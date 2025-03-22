package com.peeko32213.unusualprehistory.core.datagen.client;

import com.peeko32213.unusualprehistory.core.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.teamabnormals.blueprint.core.data.client.BlueprintItemModelProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;
import java.util.function.Supplier;

public class UPItemModelGenerator extends BlueprintItemModelProvider {
    public UPItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, UnusualPrehistory.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels(){
        for (Item i : BuiltInRegistries.ITEM) {
            if (i instanceof SpawnEggItem && Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(i)).getNamespace().equals(UnusualPrehistory.MODID)) {
                getBuilder(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(i)).getPath())
                        .parent(getExistingFile(new ResourceLocation("item/template_spawn_egg")));
            }
        }

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
        singleTex(UPItems.DRYO_NUTS);
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

        singleTex(UPItems.DRYO_BOAT);
        singleTex(UPItems.DRYO_CHEST_BOAT);

        singleTex(UPItems.GINKGO_SIGN);
        singleTex(UPItems.GINKGO_HANGING_SIGN);
        singleTex(UPItems.GINKGO_BOAT);
        singleTex(UPItems.GINKGO_CHEST_BOAT);

        singleTex(UPItems.FOXXI_BOAT);
        singleTex(UPItems.FOXXI_CHEST_BOAT);

        singleTex(UPItems.PETRIFIED_WOOD_SIGN);
        singleTex(UPItems.PETRIFIED_WOOD_HANGING_SIGN);

        singleTex(UPItems.ZULOAGAE_SIGN);
        singleTex(UPItems.ZULOAGAE_HANGING_SIGN);
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

        toBlock(UPBlocks.AMBER_BLOCK);

        // Opal blocks
        toBlock(UPBlocks.OPAL_BLOCK);
        toBlock(UPBlocks.BLACK_OPAL_BLOCK);
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
