//package com.peeko32213.unusualprehistory.common.data.entity;
//
//import com.mojang.serialization.Codec;
//import com.mojang.serialization.codecs.RecordCodecBuilder;
//import com.peeko32213.unusualprehistory.common.data.codec.NullableFieldCodec;
//import com.peeko32213.unusualprehistory.common.data.entity.generic.EntitySpawnData;
//import net.minecraft.util.random.WeightedRandomList;
//
//public class PrehistoricEntityData {
//
//    public static final Codec<PrehistoricEntityData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
//            //NullableFieldCodec.makeDefaultableField("attributes", AttributesModifier.CODEC, AttributesModifier.getDefaultInstance()).forGetter(PrehistoricEntityData::getAttributeModifiers),
//            NullableFieldCodec.makeDefaultableField("entity_spawn_definitions", EntitySpawnData.CODEC, EntitySpawnData.getDefaultInstance()).forGetter(PrehistoricEntityData::getSpawnData),
//            WeightedRandomList.codec(WeightedVariantData.CODEC).fieldOf("variants").forGetter(PrehistoricEntityData::getVariantDataWeightedRandomList)
//    ).apply(instance, PrehistoricEntityData::new));
//
//
//
//    private final WeightedRandomList<WeightedVariantData> variantDataWeightedRandomList;
//    private final EntitySpawnData spawnData;
//
//
//    public PrehistoricEntityData(EntitySpawnData spawnData, WeightedRandomList<WeightedVariantData> variantDataWeightedRandomList) {
//        this.spawnData = spawnData;
//        this.variantDataWeightedRandomList = variantDataWeightedRandomList;
//    }
//
//    public EntitySpawnData getSpawnData() {
//        return spawnData;
//    }
//
//    public WeightedRandomList<WeightedVariantData> getVariantDataWeightedRandomList() {
//        return variantDataWeightedRandomList;
//    }
//
//    public VariantData getVariantById(int id) {
//        return getVariantDataWeightedRandomList().unwrap().stream()
//                .map(WeightedVariantData::getVariantData)
//                .filter(variant -> variant.getVariantId() == id)
//                .findFirst()
//                .orElse(VariantData.getDefaultInstance());
//    }
//
//    public static PrehistoricEntityData getDefaultInstance() {
//        return new PrehistoricEntityData(EntitySpawnData.getDefaultInstance(), WeightedRandomList.create());
//    }
//
//}
