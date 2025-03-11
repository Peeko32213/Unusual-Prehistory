package com.peeko32213.unusualprehistory.common.data.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

public class MobEffectInstanceCodec {

    public static final Codec<MobEffectInstanceCodec> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.MOB_EFFECT.byNameCodec().fieldOf("mob_effect").forGetter(MobEffectInstanceCodec::getMobEffect),
            Codec.INT.fieldOf("amplifier").forGetter(MobEffectInstanceCodec::getAmplifier),
            Codec.INT.fieldOf("screen_shake_amplifier").forGetter(MobEffectInstanceCodec::getDuration),
            Codec.BOOL.optionalFieldOf("ambient",false).forGetter(MobEffectInstanceCodec::isAmbient),
            Codec.BOOL.optionalFieldOf("visible",true).forGetter(MobEffectInstanceCodec::isVisible),
            Codec.BOOL.optionalFieldOf("show_icon",true).forGetter(MobEffectInstanceCodec::isShowIcon)
    ).apply(instance, MobEffectInstanceCodec::new));


    private final MobEffect mobEffect;
    private final int amplifier;
    private final int duration;
    private final boolean ambient;
    private final boolean visible;
    private final boolean showIcon;

    public MobEffectInstanceCodec(MobEffect mobEffect, int amplifier, int duration, boolean ambient, boolean visible, boolean showIcon) {

        this.mobEffect = mobEffect;
        this.amplifier = amplifier;
        this.duration = duration;
        this.ambient = ambient;
        this.visible = visible;
        this.showIcon = showIcon;
    }

    public MobEffect getMobEffect() {
        return mobEffect;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isAmbient() {
        return ambient;
    }

    public boolean isShowIcon() {
        return showIcon;
    }

    public boolean isVisible() {
        return visible;
    }

    public MobEffectInstance getMobEffectInstance() {
        return new MobEffectInstance(getMobEffect(), getDuration(), getAmplifier(), isAmbient(), isVisible(), isShowIcon());
    }
}
