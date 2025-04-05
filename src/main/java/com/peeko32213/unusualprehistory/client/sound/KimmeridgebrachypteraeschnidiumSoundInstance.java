package com.peeko32213.unusualprehistory.client.sound;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying.KimmeridgebrachypteraeschnidiumEntity;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KimmeridgebrachypteraeschnidiumSoundInstance extends AbstractTickableSoundInstance {

    protected final KimmeridgebrachypteraeschnidiumEntity kimmer;

    public KimmeridgebrachypteraeschnidiumSoundInstance(KimmeridgebrachypteraeschnidiumEntity dragonfly) {
        super(UPSounds.KIMMER_FLAP.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
        this.kimmer = dragonfly;
        this.x = (float)dragonfly.getX();
        this.y = (float)dragonfly.getY();
        this.z = (float)dragonfly.getZ();
        this.looping = true;
        this.delay = 0;
        this.volume = 0.0f;
    }

    @Override
    public void tick() {
        if (this.kimmer.isRemoved()) {
            this.stop();
            return;
        }
        this.x = (float)this.kimmer.getX();
        this.y = (float)this.kimmer.getY();
        this.z = (float)this.kimmer.getZ();
        float horizontalDistance = (float)this.kimmer.getDeltaMovement().horizontalDistance();
        if (horizontalDistance >= 0.01f) {
            this.pitch = Mth.lerp(Mth.clamp(horizontalDistance, this.getMinPitch(), this.getMaxPitch()), this.getMinPitch(), this.getMaxPitch());
            this.volume = Mth.lerp(Mth.clamp(horizontalDistance, 0.0f, 0.15f), 0.0f, 0.3f);
        } else {
            this.pitch = 0.0f;
            this.volume = 0.0f;
        }
    }

    private float getMinPitch() {
        return 1.5f;
    }

    private float getMaxPitch() {
        return 1.75f;
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    @Override
    public boolean canPlaySound() {
        return !this.kimmer.isSilent();
    }
}
