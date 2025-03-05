package com.peeko32213.unusualprehistory.datagen;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
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

import java.util.function.Supplier;

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

        singleTex(UPItems.SMILO_FUR);
        singleTex(UPItems.PALAEO_SKIN);
        singleTex(UPItems.DINO_POUCH);
        singleTex(UPItems.OPALESCENT_PEARL);
        singleTex(UPItems.OPALESCENT_SHURIKEN);
        toBlock(UPBlocks.PERMAFROST);
        toBlock(UPBlocks.PERMAFROST_FOSSIL);
        singleTex(UPItems.DRYO_NUTS);
        singleTex(UPItems.ZULOGAE_DISC);
        singleTex(UPItems.ENCASED_DISC);
        singleTex(UPItems.RAW_MAMMOTH);
        singleTex(UPItems.MAMMOTH_MEATBALL);
        singleTex(UPItems.COOKED_MAMMOTH);
        singleTex(UPItems.RAW_OPHIODON);
        singleTex(UPItems.COOKED_OPHIODON);

        flaskTex(UPItems.ZULOAGAE_FLASK);
        flaskTex(UPItems.RAIGUENRAYUN_FLASK);
        flaskTex(UPItems.FOXXI_FLASK);
        flaskTex(UPItems.DRYO_FLASK);
        flaskTex(UPItems.DIPLO_FLASK);
        flaskTex(UPItems.KIMMER_FLASK);
        flaskTex(UPItems.HYNERIA_FLASK);
        flaskTex(UPItems.OPHIO_FLASK);
        flaskTex(UPItems.PROTOSPHYRAENA_FLASK);
        flaskTex(UPItems.PTERY_FLASK);
        flaskTex(UPItems.EDAPHO_FLASK);
        flaskTex(UPItems.XIPHACT_FLASK);
        flaskTex(UPItems.OVIRAPTOR_FLASK);
        flaskTex(UPItems.GLOBIDENS_FLASK);
        flaskTex(UPItems.ESTEMMENO_FLASK);
        flaskTex(UPItems.OTAROCYON_FLASK);
        flaskTex(UPItems.LONGI_FLASK);
        flaskTex(UPItems.TARTUO_FLASK);
        flaskTex(UPItems.TANY_FLASK);
        flaskTex(UPItems.PSITTACO_FLASK);
        flaskTex(UPItems.KAPRO_FLASK);
        flaskTex(UPItems.PSILO_FLASK);
        flaskTex(UPItems.HYNERP_FLASK);
        flaskTex(UPItems.BALAUR_FLASK);
        flaskTex(UPItems.PTERODAUSTRO_FLASK);
        flaskTex(UPItems.ARCHELON_FLASK);
        flaskTex(UPItems.JAWLESS_FISH_FLASK);
        flaskTex(UPItems.LEEDS_FLASK);

        flaskTex(UPItems.CAPTURED_KIMMER_FLASK);
        flaskTex(UPItems.DORMANT_RAMPAGE_FLASK);
        flaskTex(UPItems.YIXIAN_RAMPAGE_FLASK);

        flaskTex(UPItems.ANIMAL_DNA_FLASKS);
        flaskTex(UPItems.PLANT_DNA_FLASKS);

        singleTex(UPItems.OTAROCYON_EMBRYO);
        singleTex(UPItems.LEEDS_CAVIAR);

        singleTex(UPItems.DRYO_SIGN);
        singleTex(UPItems.DRYO_HANGING_SIGN);
        singleTex(UPItems.DRYO_BOAT);
        singleTex(UPItems.DRYO_CHEST_BOAT);

        singleTex(UPItems.GINKGO_SIGN);
        singleTex(UPItems.GINKGO_HANGING_SIGN);
        singleTex(UPItems.GINKGO_BOAT);
        singleTex(UPItems.GINKGO_CHEST_BOAT);

        singleTex(UPItems.FOXXI_BOAT);
        singleTex(UPItems.FOXXI_CHEST_BOAT);
        singleTex(UPItems.FOXII_SIGN);
        singleTex(UPItems.FOXII_HANGING_SIGN);

        singleTex(UPItems.PETRIFIED_WOOD_SIGN);
        singleTex(UPItems.PETRIFIED_WOOD_HANGING_SIGN);

        singleTex(UPItems.ZULOAGAE_SIGN);
        singleTex(UPItems.ZULOAGAE_HANGING_SIGN);
        singleTex(UPItems.RAW_JAWLESS_FISH);
        singleTex(UPItems.COOKED_JAWLESS_FISH);
        singleTex(UPItems.RAW_TARTU);
        singleTex(UPItems.COOKED_TARTU);
        singleTex(UPItems.PSITTACOSAURUS_QUILL);
        singleTex(UPItems.PSITTACCO_ARROW);
        singleTex(UPItems.AMBER_IDOL);
        singleTex(UPItems.JARATE);
        singleTex(UPItems.QUILL_REMEDY);
        singleTex(UPItems.RAW_DUNK);
        singleTex(UPItems.COOKED_DUNK);
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

//    public void addDinoEgg(Supplier<? extends EntityType<?>> dino) {
//        generated( dino.get().getDescriptionId().replace("entity.unusualprehistory.", "") + "_entity_egg", prefix("item/" + dino.get().getDescriptionId().replace("entity.unusualprehistory.", "")  + "_egg"));
//    }
//    public void addDinoEgg(ResourceLocation dino) {
//        generated( dino.getPath() + "_entity_egg", prefix("item/" + dino.getPath()  + "_egg"));
//    }

    private ItemModelBuilder singleTex(RegistryObject<Item> item) {
        return generated(item.getId().getPath(), prefix("item/" + item.getId().getPath()));
    }

    private ItemModelBuilder flaskTex(RegistryObject<Item> item) {
        return generated(item.getId().getPath(), prefix("item/flasks/" + item.getId().getPath()));
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
