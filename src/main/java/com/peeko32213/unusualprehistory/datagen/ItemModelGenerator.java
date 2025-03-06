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

import java.util.Objects;
import java.util.function.Supplier;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
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
        flaskTex(UPItems.AMMONITE_FLASK);
        flaskTex(UPItems.COTY_FLASK);
        flaskTex(UPItems.DUNK_FLASK);
        flaskTex(UPItems.SCAU_FLASK);
        flaskTex(UPItems.STETHA_FLASK);
        flaskTex(UPItems.ANTARCTO_FLASK);
        flaskTex(UPItems.ANURO_FLASK);
        flaskTex(UPItems.AUSTRO_FLASK);
        flaskTex(UPItems.BEELZ_FLASK);
        flaskTex(UPItems.BRACHI_FLASK);
        flaskTex(UPItems.ENCRUSTED_FLASK);
        flaskTex(UPItems.ERYON_FLASK);
        flaskTex(UPItems.HWACHA_FLASK);
        flaskTex(UPItems.KENTRO_FLASK);
        flaskTex(UPItems.MAJUNGA_FLASK);
        flaskTex(UPItems.PACHY_FLASK);
        flaskTex(UPItems.TRIKE_FLASK);
        flaskTex(UPItems.TYRANNO_FLASK);
        flaskTex(UPItems.ULUGH_FLASK);
        flaskTex(UPItems.VELOCI_FLASK);
        flaskTex(UPItems.BARINA_FLASK);
        flaskTex(UPItems.GIGANTO_FLASK);
        flaskTex(UPItems.MAMMOTH_FLASK);
        flaskTex(UPItems.MEGALANIA_FLASK);
        flaskTex(UPItems.MEGATHERIUM_FLASK);
        flaskTex(UPItems.PALAEO_FLASK);
        flaskTex(UPItems.PARACER_FLASK);
        flaskTex(UPItems.SMILODON_FLASK);
        flaskTex(UPItems.TALPANAS_FLASK);
        flaskTex(UPItems.ANOSTYLOSTRAMA_FLASK);
        flaskTex(UPItems.ARCHAEFRUCTUS_FLASK);
        flaskTex(UPItems.ARCHAO_FLASK);
        flaskTex(UPItems.BENNET_FLASK);
        flaskTex(UPItems.CLATHRODICTYON_FLASK);
        flaskTex(UPItems.GINKGO_FLASK);
        flaskTex(UPItems.HORSETAIL_FLASK);
        flaskTex(UPItems.LEEFRUCTUS_FLASK);
        flaskTex(UPItems.NELUMBITES_FLASK);
        flaskTex(UPItems.QUEREUXIA_FLASK);
        flaskTex(UPItems.SARR_FLASK);

        flaskTex(UPItems.FLASK);
        flaskTex(UPItems.CAPTURED_KIMMER_FLASK);
        flaskTex(UPItems.DORMANT_RAMPAGE_FLASK);
        flaskTex(UPItems.YIXIAN_RAMPAGE_FLASK);
        flaskTex(UPItems.JARATE);

        flaskTex(UPItems.ANIMAL_DNA_FLASKS);
        flaskTex(UPItems.PLANT_DNA_FLASKS);

        embryoTex(UPItems.SMILODON_EMBRYO);
        embryoTex(UPItems.MAMMOTH_EMBRYO);
        embryoTex(UPItems.MEGATH_EMBRYO);
        embryoTex(UPItems.GIGANTO_EMBRYO);
        embryoTex(UPItems.PARACER_EMBRYO);
        embryoTex(UPItems.PALAEO_EMBRYO);
        embryoTex(UPItems.OTAROCYON_EMBRYO);

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
        singleTex(UPItems.PSITTACOSAURUS_QUILL);
        singleTex(UPItems.PSITTACCO_ARROW);
        singleTex(UPItems.AMBER_IDOL);
        singleTex(UPItems.QUILL_REMEDY);

        for(RegistryObject<?> object : UPEntities.dinos) {
            addPrehistoricEgg(object.getId());
        }
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

    private ItemModelBuilder flaskTex(RegistryObject<Item> item) {
        return generated(item.getId().getPath(), prefix("item/flasks/" + item.getId().getPath()));
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
