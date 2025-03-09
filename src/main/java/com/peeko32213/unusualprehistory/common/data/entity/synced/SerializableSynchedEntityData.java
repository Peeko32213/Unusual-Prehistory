package com.peeko32213.unusualprehistory.common.data.entity.synced;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public interface SerializableSynchedEntityData {
    ResourceLocation getId();

    String getValueAsString(Entity entity);
    void setValue(Entity entity, Object value);
    boolean isEqualToSValue(Entity entity, Object input);
    Class<? extends Entity> getEntityClass();
    void defineData(Entity entity, Object input);
    void saveToNBT(Entity entity, CompoundTag tag);
    void loadFromNBT(Entity entity, CompoundTag tag);
}