package com.peeko32213.unusualprehistory.common.data.attack;

import com.mojang.serialization.Codec;
import com.peeko32213.unusualprehistory.core.registry.entities.UPAttackRegistry;
import net.minecraft.world.entity.PathfinderMob;

public class NoneAttack implements EntityAttack {

    public static final NoneAttack INSTANCE = new NoneAttack();

    public static final Codec<NoneAttack> CODEC = Codec.unit(INSTANCE);

    public NoneAttack() {
    }

    @Override
    public void performAttack(PathfinderMob entity) {
    }

    @Override
    public Codec<? extends EntityAttack> codec() {
        return UPAttackRegistry.NONE_ATTACK.get();
    }
}
