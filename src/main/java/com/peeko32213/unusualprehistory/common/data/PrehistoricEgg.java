package com.peeko32213.unusualprehistory.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.entity.custom.eggs.EggSize;
import com.peeko32213.unusualprehistory.common.entity.custom.eggs.EggVariant;
import com.peeko32213.unusualprehistory.core.other.util.CodecUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;

public class PrehistoricEgg {

    public static final Codec<PrehistoricEgg> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity").forGetter(PrehistoricEgg::getEntityType),
                    EggSize.CODEC.fieldOf("egg_size").forGetter(PrehistoricEgg::getEggSize),
                    EggVariant.CODEC.fieldOf("egg_variant").forGetter(PrehistoricEgg::getVariant),
                    Codec.INT.fieldOf("hatch_time").forGetter(PrehistoricEgg::getHatchTime),
                    CodecUtils.HEX_OR_INT_CODEC.fieldOf("egg_base_color").forGetter(PrehistoricEgg::getEggBaseColor),
                    CodecUtils.HEX_OR_INT_CODEC.fieldOf("egg_secondary_color").forGetter(PrehistoricEgg::getEggSpotColor)
            ).apply(instance, PrehistoricEgg::new)
    );

    private final EntityType<?> entityType;
    private final EggSize eggSize;
    private final EggVariant variant;
    private final int hatchTime;
    private final int eggBaseColor;
    private final int eggSpotColor;


    public PrehistoricEgg(EntityType<?> entityType, EggSize eggSize, EggVariant variant, int hatchTime, int eggBaseColor, int eggSpotColor) {
        this.entityType = entityType;
        this.eggSize = eggSize;
        this.variant = variant;
        this.hatchTime = hatchTime;
        this.eggBaseColor = eggBaseColor;
        this.eggSpotColor = eggSpotColor;
    }

    public EntityType<?> getEntityType() {
        return entityType;
    }

    public EggSize getEggSize() {
        return eggSize;
    }

    public EggVariant getVariant() {
        return variant;
    }

    public int getHatchTime() {
        return hatchTime;
    }

    public int getEggBaseColor() {
        return eggBaseColor;
    }

    public int getEggSpotColor() {
        return eggSpotColor;
    }
}
