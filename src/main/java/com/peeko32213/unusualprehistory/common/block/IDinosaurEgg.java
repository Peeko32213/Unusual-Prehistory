package com.peeko32213.unusualprehistory.common.block;

import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public interface IDinosaurEgg {
    int getEggCount();
    int getHatchTimeMax();
    int getHatchTimeMin();
    Supplier<? extends EntityType<?>> getDinosaur();
}
