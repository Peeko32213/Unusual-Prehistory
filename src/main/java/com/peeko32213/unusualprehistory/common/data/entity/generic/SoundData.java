package com.peeko32213.unusualprehistory.common.data.entity.generic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.core.registry.util.CodecUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class SoundData {
    public static final Codec<SoundData> CODEC = RecordCodecBuilder.create(inst ->
            inst.group(
                    BuiltInRegistries.SOUND_EVENT.byNameCodec().fieldOf("sound_to_play").forGetter(SoundData::getSoundEvent),
                    CodecUtils.SOUND_SOURCE_CODEC.fieldOf("source_source").forGetter(SoundData::getSource),
                    Codec.FLOAT.fieldOf("volume").forGetter(SoundData::getVolume),
                    Codec.INT.fieldOf("pitch").forGetter(SoundData::getPitch)
            ).apply(inst, SoundData::new));
    private final SoundEvent event;
    private final SoundSource source;
    private final float volume;
    private final int pitch;

    public SoundData(SoundEvent event, SoundSource source, float volume, int pitch) {
        this.event = event;
        this.source = source;
        this.volume = volume;
        this.pitch = pitch;
    }


    public SoundEvent getSoundEvent() {
        return event;
    }

    public SoundSource getSource() {
        return source;
    }


    public float getVolume() {
        return volume;
    }

    public int getPitch() {
        return pitch;
    }
}
