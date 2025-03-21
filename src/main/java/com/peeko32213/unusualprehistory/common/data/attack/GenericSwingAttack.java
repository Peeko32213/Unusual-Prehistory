package com.peeko32213.unusualprehistory.common.data.attack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.common.data.entity.generic.SoundData;
import com.peeko32213.unusualprehistory.core.registry.entities.UPAttackRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.PathfinderMob;

public class GenericSwingAttack implements EntityAttack {


    public static final Codec<GenericSwingAttack> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            SoundData.CODEC.fieldOf("sound").forGetter(GenericSwingAttack::getSoundData),
            EntityAttack.DIRECT_CODEC.fieldOf("attack").forGetter(GenericSwingAttack::getEntityAttack)
    ).apply(instance, GenericSwingAttack::new));


    private final SoundData soundData;
    private final EntityAttack entityAttack;

    public GenericSwingAttack(SoundData soundData, EntityAttack entityAttack) {
        this.soundData = soundData;
        this.entityAttack = entityAttack;
    }

    public SoundData getSoundData() {
        return soundData;
    }

    public EntityAttack getEntityAttack() {
        return entityAttack;
    }

    @Override
    public void performAttack(PathfinderMob entity) {
        entity.playSound(soundData.getSoundEvent(), soundData.getVolume(), soundData.getPitch());
        entity.swing(InteractionHand.MAIN_HAND);
        entityAttack.performAttack(entity);
    }

    @Override
    public Codec<? extends EntityAttack> codec() {
        return UPAttackRegistry.GENERIC_SWING_ATTACK.get();

    }
}
