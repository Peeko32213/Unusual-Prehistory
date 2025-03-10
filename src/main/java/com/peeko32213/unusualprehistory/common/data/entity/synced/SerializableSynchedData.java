package com.peeko32213.unusualprehistory.common.data.entity.synced;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.function.Function;

public class SerializableSynchedData<T> implements SerializableSynchedEntityData {
    private final ResourceLocation id;
    private final EntityDataAccessor<T> accessor;
    private final T defaultValue;
    private final Function<T, String> toStringConverter;
    private final Function<String, T> fromStringConverter;
    private final Class<? extends Entity> entityClass;

    public SerializableSynchedData(
            ResourceLocation id,
            Class<? extends Entity> entityClass,
            EntityDataSerializer<T> serializer,
            T defaultValue,
            Function<T, String> toStringConverter,
            Function<String, T> fromStringConverter
    ) {
        this.id = id;
        this.accessor = SynchedEntityData.defineId(entityClass, serializer);
        this.defaultValue = defaultValue;
        this.toStringConverter = toStringConverter;
        this.fromStringConverter = fromStringConverter;
        this.entityClass = entityClass;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    private T getValue(Entity entity) {
        return entity.getEntityData().get(accessor);
    }

    private void setValueInternal(Entity entity, T value) {
        entity.getEntityData().set(accessor, value);
    }


    @Override
    public String getValueAsString(Entity entity) {
        return toStringConverter.apply(getValue(entity));
    }

    @Override
    public void setValue(Entity entity, Object value) {
        if(value == null) return;
        setValueInternal(entity, fromStringConverter.apply(value.toString()));
    }

    @Override
    public boolean isEqualToValue(Entity entity, Object input) {
        if(input == null) return false;
        return getValueAsString(entity).equals(input.toString());
    }

    @Override
    public Class<? extends Entity> getEntityClass() {
        return entityClass;
    }

    @Override
    public void defineData(Entity entity) {
        entity.getEntityData().define(accessor, fromStringConverter.apply(defaultValue.toString()));
    }

    @Override
    public void saveToNBT(Entity entity, CompoundTag tag) {
        String valueAsString = getValueAsString(entity);
        if (!valueAsString.equals(toStringConverter.apply(defaultValue))) {
            tag.putString(id.toString(), valueAsString);
        }
    }

    @Override
    public void loadFromNBT(Entity entity, CompoundTag tag) {
        if (tag.contains(id.toLanguageKey())) {
            setValue(entity, tag.getString(id.toString()));
        }
    }
}