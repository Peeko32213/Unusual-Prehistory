package com.peeko32213.unusualprehistory.common.data.attack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.data.entity.generic.CooldownWideRangeEffectData;
import com.peeko32213.unusualprehistory.common.data.entity.generic.SoundData;
import com.peeko32213.unusualprehistory.core.registry.UPAttackRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.PathfinderMob;

public class StompAttack implements EntityAttack {


    public static final Codec<StompAttack> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            SoundData.CODEC.fieldOf("sound").forGetter(StompAttack::getSoundData),
            EntityAttack.DIRECT_CODEC.fieldOf("attack").forGetter(StompAttack::getEntityAttack),
            CooldownWideRangeEffectData.CODEC.fieldOf("cooldown_wide_range_effect").forGetter(StompAttack::getWideRangeEffectData)
    ).apply(instance, StompAttack::new));


    private final SoundData soundData;
    private final EntityAttack entityAttack;
    private final CooldownWideRangeEffectData wideRangeEffectData;

    public StompAttack(SoundData soundData, EntityAttack entityAttack,CooldownWideRangeEffectData wideRangeEffectData) {
        this.soundData = soundData;
        this.entityAttack = entityAttack;
        this.wideRangeEffectData = wideRangeEffectData;
    }

    public SoundData getSoundData() {
        return soundData;
    }

    public EntityAttack getEntityAttack() {
        return entityAttack;
    }

    public CooldownWideRangeEffectData getWideRangeEffectData() {
        return wideRangeEffectData;
    }

    @Override
    public void performAttack(PathfinderMob entity) {
        entity.playSound(soundData.getSoundEvent(), soundData.getVolume(), soundData.getPitch());
        entityAttack.performAttack(entity);
        wideRangeEffectData.performCooldownWideRangeEffect(entity);
    }

    @Override
    public Codec<? extends EntityAttack> codec() {
        return UPAttackRegistry.STOMP_ATTACK.get();

    }
}
