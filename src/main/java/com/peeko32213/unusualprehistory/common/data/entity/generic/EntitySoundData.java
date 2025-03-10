package com.peeko32213.unusualprehistory.common.data.entity.generic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public class EntitySoundData {

    public static final Codec<EntitySoundData> CODEC = RecordCodecBuilder.create(inst ->
            inst.group(
                    BuiltInRegistries.SOUND_EVENT.byNameCodec().fieldOf("ambient_sound").forGetter(EntitySoundData::getAmbientSound),
                    BuiltInRegistries.SOUND_EVENT.byNameCodec().fieldOf("hurt_sound").forGetter(EntitySoundData::getHurtSound),
                    BuiltInRegistries.SOUND_EVENT.byNameCodec().fieldOf("death_sound").forGetter(EntitySoundData::getDeathSound),
                    Codec.FLOAT.fieldOf("volume").forGetter(EntitySoundData::getSoundVolume),
                    Codec.FLOAT.fieldOf("pitch").forGetter(EntitySoundData::getVoicePitch)
            ).apply(inst, EntitySoundData::new));
    
    private final SoundEvent ambientSound;
    private final SoundEvent hurtSound;
    private final SoundEvent deathSound;
    private final float soundVolume;
    private final float voicePitch;

    public EntitySoundData(SoundEvent ambientSound, SoundEvent hurtSound, SoundEvent deathSound, float soundVolume, float voicePitch) {
        this.ambientSound = ambientSound;
        this.hurtSound = hurtSound;
        this.deathSound = deathSound;
        this.soundVolume = soundVolume;
        this.voicePitch = voicePitch;
    }

    public float getSoundVolume() {
        return soundVolume;
    }

    public float getVoicePitch() {
        return voicePitch;
    }

    public SoundEvent getAmbientSound() {
        return ambientSound;
    }

    public SoundEvent getDeathSound() {
        return deathSound;
    }

    public SoundEvent getHurtSound() {
        return hurtSound;
    }

    public static EntitySoundData getDefaultInstance() {
        return new EntitySoundData(UPSounds.TYRANNO_IDLE.get(), UPSounds.TALPANAS_HURT.get(), UPSounds.TYRANNO_DEATH.get(), 1.25F, 1F);
    }
}
