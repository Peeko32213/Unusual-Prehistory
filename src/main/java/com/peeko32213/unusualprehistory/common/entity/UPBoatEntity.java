package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class UPBoatEntity extends Boat {
    private static final EntityDataAccessor<Integer> BOAT_TYPE = SynchedEntityData.defineId(UPBoatEntity.class, EntityDataSerializers.INT);

    public UPBoatEntity(EntityType<? extends UPBoatEntity> entityType, Level world) {
        super(entityType, world);
    }

    public UPBoatEntity(Level world, double x, double y, double z) {
        this(UPEntities.BOAT.get(), world);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BOAT_TYPE, BOAT_TYPE.getId());
    }

    @Override
    public Item getDropItem() {
        if(this.getUPBoatType() == BoatType.DRYO){
            return UPItems.DRYO_BOAT.get();
        }
        else if(this.getUPBoatType() == BoatType.FOXXI) {
            return UPItems.FOXXI_BOAT.get();
        }
        else {
            return UPItems.GINKGO_BOAT.get();
        }
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.contains("Type", 8)) {
            this.setUPBoatType(BoatType.byName(nbt.getString("Type")));
        }
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putString("Type", this.getUPBoatType().getName());
    }

    public BoatType getUPBoatType() {
        return BoatType.byId(this.entityData.get(BOAT_TYPE));
    }

    public void setUPBoatType(BoatType type) {
        this.entityData.set(BOAT_TYPE, type.ordinal());
    }

    public enum BoatType {
        DRYO(UPBlocks.DRYO_PLANKS.get(), "dryo"),
        FOXXI(UPBlocks.FOXXI_PLANKS.get(), "foxxi"),
        GINKGO(UPBlocks.GINKGO_PLANKS.get(), "ginkgo");

        private final String name;
        private final Block planks;

        BoatType(Block block, String name) {
            this.name = name;
            this.planks = block;
        }

        public String getName() {
            return this.name;
        }

        public Block getPlanks() {
            return this.planks;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static BoatType byId(int id) {
            BoatType[] aboat$type = values();
            if (id < 0 || id >= aboat$type.length) {
                id = 0;
            }

            return aboat$type[id];
        }

        public static BoatType byName(String name) {
            BoatType[] types = values();
            for (BoatType type : types) {
                if (type.getName().equals(name)) return type;
            }
            return types[0];
        }
    }
}