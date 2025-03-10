package com.peeko32213.unusualprehistory.common.data.entity.generic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class ScreenShakeEntityData {


    public static final Codec<ScreenShakeEntityData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("causes_screen_shake",false).forGetter(ScreenShakeEntityData::isCausesScreenShake),
            Codec.DOUBLE.optionalFieldOf("screen_shake_range",0D).forGetter(ScreenShakeEntityData::getScreenShakeRange),
            Codec.INT.optionalFieldOf("screen_shake_amplifier",0).forGetter(ScreenShakeEntityData::getScreenShakeAmplifier)
    ).apply(instance, ScreenShakeEntityData::new));
    private final boolean causesScreenShake;
    private final double screenShakeRange;
    private final int screenShakeAmplifier;

    public ScreenShakeEntityData(boolean causesScreenShake, double screenShakeRange, int screenShakeAmplifier) {
        this.causesScreenShake = causesScreenShake;
        this.screenShakeRange = screenShakeRange;
        this.screenShakeAmplifier = screenShakeAmplifier;
    }

    public double getScreenShakeRange() {
        return screenShakeRange;
    }

    public int getScreenShakeAmplifier() {
        return screenShakeAmplifier;
    }

    public boolean isCausesScreenShake() {
        return causesScreenShake;
    }

    public static ScreenShakeEntityData getDefaultInstance() {
        return new ScreenShakeEntityData(false,0D,0);
    }
}
