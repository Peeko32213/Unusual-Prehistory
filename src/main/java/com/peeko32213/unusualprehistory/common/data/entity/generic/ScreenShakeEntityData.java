package com.peeko32213.unusualprehistory.common.data.entity.generic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.data.codec.MobEffectInstanceCodec;
import com.peeko32213.unusualprehistory.common.data.entity.WideRangeEffectData;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import com.peeko32213.unusualprehistory.core.other.tags.UPEntityTypeTags;
import net.minecraft.world.entity.Mob;

public class ScreenShakeEntityData {


    public static final Codec<ScreenShakeEntityData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("causes_screen_shake",false).forGetter(ScreenShakeEntityData::isCausesScreenShake),
            CooldownWideRangeEffectData.CODEC.fieldOf("wide_range_effect_with_cooldown").forGetter(ScreenShakeEntityData::getWideRangeEffectData)
    ).apply(instance, ScreenShakeEntityData::new));
    private final boolean causesScreenShake;
    private final CooldownWideRangeEffectData wideRangeEffectData;


    public ScreenShakeEntityData(boolean causesScreenShake, CooldownWideRangeEffectData wideRangeEffectData) {
        this.causesScreenShake = causesScreenShake;
        this.wideRangeEffectData = wideRangeEffectData;
    }

    public boolean isCausesScreenShake() {
        return causesScreenShake;
    }


    public CooldownWideRangeEffectData getWideRangeEffectData() {
        return wideRangeEffectData;
    }

    public boolean performScreenShake(Mob entity) {
        if(!causesScreenShake) return false;
        return getWideRangeEffectData().performCooldownWideRangeEffect(entity);
    }

    public static ScreenShakeEntityData getDefaultInstance() {
        return new ScreenShakeEntityData(false, new CooldownWideRangeEffectData(100, new WideRangeEffectData(new MobEffectInstanceCodec(UPEffects.SCREEN_SHAKE.get(), 4,10,false,false,false),0.2, UPEntityTypeTags.NONE_ENTITY_TAG)));
    }
}
