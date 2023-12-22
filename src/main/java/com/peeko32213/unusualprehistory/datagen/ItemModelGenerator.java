package com.peeko32213.unusualprehistory.datagen;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, UnusualPrehistory.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels(){
        for (Item i : BuiltInRegistries.ITEM) {
            if (i instanceof SpawnEggItem && ForgeRegistries.ITEMS.getKey(i).getNamespace().equals(UnusualPrehistory.MODID)) {
                getBuilder(ForgeRegistries.ITEMS.getKey(i).getPath())
                        .parent(getExistingFile(new ResourceLocation("item/template_spawn_egg")));
            }
        }

        singleTex(UPItems.SMILODON_EMBRYO);
        singleTex(UPItems.MAMMOTH_EMBRYO);
        singleTex(UPItems.MEGATH_EMBRYO);
        singleTex(UPItems.GIGANTO_EMBRYO);
        singleTex(UPItems.PARACER_EMBRYO);
        singleTex(UPItems.PALAEO_EMBRYO);
        singleTex(UPItems.BARINA_WHISTLE);
        singleTex(UPItems.ZULOAGAE_FLASK);
        singleTex(UPItems.RAIGUENRAYUN_FLASK);
        singleTex(UPItems.FOXXI_FLASK);
        singleTex(UPItems.DRYO_FLASK);
        singleTex(UPItems.SMILO_FUR);
        singleTex(UPItems.PALAEO_SKIN);
        singleTex(UPItems.DINO_POUCH);
        singleTex(UPItems.OPALESCENT_PEARL);
        singleTex(UPItems.OPALESCENT_SHURIKEN);
        toBlock(UPBlocks.PERMAFROST);
        toBlock(UPBlocks.PERMAFROST_FOSSIL);
        singleTex(UPItems.INSULATOR);
        singleTex(UPItems.DRYO_NUTS);
        singleTex(UPItems.ZULOGAE_DISC);
        singleTex(UPItems.RAW_MAMMOTH);
        singleTex(UPItems.MAMMOTH_MEATBALL);
        singleTex(UPItems.COOKED_MAMMOTH);
        singleTex(UPItems.FURCA_FLASK);
        singleTex(UPItems.OTAROCYON_FLASK);
        singleTex(UPItems.LONGI_FLASK);
        singleTex(UPItems.RAW_FURCACAUDA);
        singleTex(UPItems.COOKED_FURCACAUDA);
        singleTex(UPItems.RAW_TARTU);
        singleTex(UPItems.COOKED_TARTU);
        singleTex(UPItems.RAW_OPHIODON);
        singleTex(UPItems.COOKED_OPHIODON);
        singleTex(UPItems.PSITTACO_QUIL);
        singleTex(UPItems.FURCA_FLASK);
        singleTex(UPItems.TARTUO_FLASK);
        singleTex(UPItems.TANY_FLASK);
        singleTex(UPItems.PSITTACO_FLASK);
        singleTex(UPItems.KAPRO_FLASK);
        singleTex(UPItems.PSILO_FLASK);
        singleTex(UPItems.OPHIO_FLASK);
        singleTex(UPItems.DIPLO_FLASK);
        singleTex(UPItems.HYNERP_FLASK);
        singleTex(UPItems.BALAUR_FLASK);
        singleTex(UPItems.PSITTACCO_ARROW);
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

    private ItemModelBuilder singleTex(RegistryObject<Item> item) {
        return generated(item.getId().getPath(), prefix("item/" + item.getId().getPath()));
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
