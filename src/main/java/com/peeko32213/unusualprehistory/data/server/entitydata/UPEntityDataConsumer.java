package com.peeko32213.unusualprehistory.data.server.entitydata;

import com.peeko32213.unusualprehistory.common.data.entity.PrehistoricEntityData;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public class UPEntityDataConsumer {
    private ResourceLocation location;

    public UPEntityDataConsumer(EntityType<?> targetEntity, PrehistoricEntityData entityData) {
        this.location = BuiltInRegistries.ENTITY_TYPE.getKey(targetEntity);
        this.entityData =entityData;
    }


    private PrehistoricEntityData entityData;

    public UPEntityDataConsumer(ResourceLocation loc, PrehistoricEntityData entityData) {
        this.location = loc;
        this.entityData = entityData;
    }

    // Getter for location
    public ResourceLocation getLocation() {
        return location;
    }

    // Setter for location
    public void setLocation(ResourceLocation location) {
        this.location = location;
    }

    // Getter for puppetData
    public PrehistoricEntityData getEntityData() {
        return entityData;
    }

}
