package com.peeko32213.unusualprehistory.common.entity.custom.eggs;

import net.minecraft.resources.ResourceLocation;

public interface IEggEntity {
    ResourceLocation getTexture();
    ResourceLocation getModel();
    ResourceLocation getCrackTexture();
    ResourceLocation getSpotTexture();
    ResourceLocation getAnimation();
    int getHatchTime();
    int getCurrentHatchTime();
}
