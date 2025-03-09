package com.peeko32213.unusualprehistory.common.entity.animation.state;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;

public class WeightedSerializableStateHelper extends WeightedEntry.IntrusiveBase {


    public static final Codec<WeightedSerializableStateHelper> CODEC = RecordCodecBuilder.<WeightedSerializableStateHelper>create((instance) ->
            instance.group(
                    Weight.CODEC.fieldOf("weight").forGetter(WeightedEntry.IntrusiveBase::getWeight),
                    SerializableStateHelper.CODEC.fieldOf("state_helper").forGetter(WeightedSerializableStateHelper::getSerializableStateHelper)
            ).apply(instance, WeightedSerializableStateHelper::new));
    
    
    private final SerializableStateHelper serializableStateHelper;

    public WeightedSerializableStateHelper(int pWeight, SerializableStateHelper serializableStateHelper) {
        this(Weight.of(pWeight),  serializableStateHelper);
    }

    public WeightedSerializableStateHelper(Weight pWeight, SerializableStateHelper serializableStateHelper) {
        super(pWeight);
        this.serializableStateHelper = serializableStateHelper;
    }



    public SerializableStateHelper getSerializableStateHelper() {
        return serializableStateHelper;
    }
}
