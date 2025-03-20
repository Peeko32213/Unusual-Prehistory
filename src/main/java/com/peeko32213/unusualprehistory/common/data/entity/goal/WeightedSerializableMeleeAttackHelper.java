package com.peeko32213.unusualprehistory.common.data.entity.goal;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;

public class WeightedSerializableMeleeAttackHelper extends WeightedEntry.IntrusiveBase {


    public static final Codec<WeightedSerializableMeleeAttackHelper> CODEC = RecordCodecBuilder.<WeightedSerializableMeleeAttackHelper>create((instance) ->
            instance.group(
                    Weight.CODEC.fieldOf("weight").forGetter(IntrusiveBase::getWeight),
                    SerializableRandomMeleeAttackHelper.CODEC.fieldOf("melee_attack_helper").forGetter(WeightedSerializableMeleeAttackHelper::getSerializableStateHelper)
            ).apply(instance, WeightedSerializableMeleeAttackHelper::new));


    private final SerializableRandomMeleeAttackHelper serializableStateHelper;

    public WeightedSerializableMeleeAttackHelper(int pWeight, SerializableRandomMeleeAttackHelper serializableStateHelper) {
        this(Weight.of(pWeight),  serializableStateHelper);
    }

    public WeightedSerializableMeleeAttackHelper(Weight pWeight, SerializableRandomMeleeAttackHelper serializableStateHelper) {
        super(pWeight);
        this.serializableStateHelper = serializableStateHelper;
    }



    public SerializableRandomMeleeAttackHelper getSerializableStateHelper() {
        return serializableStateHelper;
    }
}
