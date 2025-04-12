package com.peeko32213.unusualprehistory.common.entity.custom.part;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.XiphactinusEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.AABB;

public class XiphactinusPartEntity extends UPMultipartEntity<XiphactinusEntity> {

    private final Entity connectedTo;
    private EntityDimensions size;
    public float scale = 1;

    public XiphactinusPartEntity(XiphactinusEntity parent, Entity connectedTo, float sizeXZ, float sizeY) {
        super(parent);
        this.connectedTo = connectedTo;
        this.size = EntityDimensions.fixed(sizeXZ, sizeY);
        this.refreshDimensions();
    }

    public EntityDimensions getDimensions(Pose pose) {
        return size;
    }

    public AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(1.0D, 1.0D, 1.0D);
    }
}
