package com.peeko32213.unusualprehistory.common.entity.eggs;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public interface IEggEntity {
    ResourceLocation getTexture();
    ResourceLocation getModel();
    int getHatchTime();
    int getCurrentHatchTime();
}
