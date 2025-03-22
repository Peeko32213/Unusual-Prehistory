package com.peeko32213.unusualprehistory.common.data.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.data.codec.MobEffectInstanceCodec;
import com.peeko32213.unusualprehistory.core.other.UPTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class WideRangeEffectData 
{
    public static final Codec<WideRangeEffectData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MobEffectInstanceCodec.CODEC.fieldOf("effect").forGetter(WideRangeEffectData::getMobEffectInstance),
            Codec.DOUBLE.fieldOf("effect_range").forGetter(WideRangeEffectData::getEffectRange),
            TagKey.codec(Registries.ENTITY_TYPE).optionalFieldOf("entities_to_target", UPTags.NONE_ENTITY_TAG).forGetter(WideRangeEffectData::getEntitiesToTarget)
    ).apply(instance, WideRangeEffectData::new));
    private final MobEffectInstanceCodec mobEffectInstance;
    private final double effectRange;
    private final TagKey<EntityType<?>> entitiesToTarget;
    
    public WideRangeEffectData(MobEffectInstanceCodec mobEffectInstance, double effectRange, TagKey<EntityType<?>> entitiesToTarget) {
        this.mobEffectInstance = mobEffectInstance;
        this.effectRange = effectRange;
        this.entitiesToTarget = entitiesToTarget;
    }

    public MobEffectInstanceCodec getMobEffectInstance() {
        return mobEffectInstance;
    }

    public TagKey<EntityType<?>> getEntitiesToTarget() {
        return entitiesToTarget;
    }

    public double getEffectRange() {
        return effectRange;
    }

    public boolean causeWideRangeEffect(Entity entity) {
        boolean hasShaken = false;
        List<LivingEntity> list = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(effectRange), e -> e.getType().is(entitiesToTarget));
        for (LivingEntity e : list) {
            if (e.isAlive()) {
                hasShaken = true;
                e.addEffect(getMobEffectInstance().getMobEffectInstance());
            }
        }
        return hasShaken;

    }
}
