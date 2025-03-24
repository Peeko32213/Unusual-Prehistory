package com.peeko32213.unusualprehistory.common.data.entity.generic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.data.codec.MobEffectInstanceCodec;
import com.peeko32213.unusualprehistory.common.data.entity.WideRangeEffectData;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import com.peeko32213.unusualprehistory.core.other.tags.UPEntityTypeTags;
import net.minecraft.world.entity.Mob;

public class CooldownWideRangeEffectData {
    public static final Codec<CooldownWideRangeEffectData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("cooldown_time").forGetter(CooldownWideRangeEffectData::getCooldownTime),
            WideRangeEffectData.CODEC.fieldOf("wide_range_effect").forGetter(CooldownWideRangeEffectData::getWideRangeEffectData)
    ).apply(instance, CooldownWideRangeEffectData::new));

    private final WideRangeEffectData wideRangeEffectData;
    private final int cooldownTime;



    private int shakeCooldown;
    public CooldownWideRangeEffectData(int cooldownTime, WideRangeEffectData wideRangeEffectData) {
        this.cooldownTime = cooldownTime;
        this.wideRangeEffectData = wideRangeEffectData;
    }


    public int getCooldownTime() {
        return cooldownTime;
    }

    public WideRangeEffectData getWideRangeEffectData() {
        return wideRangeEffectData;
    }

    public boolean performCooldownWideRangeEffect(Mob entity) {
        if(--this.shakeCooldown <= 0) {
            boolean hasShakenEntities = getWideRangeEffectData().causeWideRangeEffect(entity);
            this.shakeCooldown = cooldownTime;
            return hasShakenEntities;
        }
        return false;
    }

    public static CooldownWideRangeEffectData getDefaultInstance() {
        return new CooldownWideRangeEffectData(0, new WideRangeEffectData(new MobEffectInstanceCodec(UPEffects.SCREEN_SHAKE.get(), 0,0,false,false,false),0, UPEntityTypeTags.NONE_ENTITY_TAG));
    }
    
}
