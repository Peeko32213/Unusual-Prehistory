package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.client.render.HasUPBoatType;
import com.peeko32213.unusualprehistory.core.registry.entities.UPBoatTypes;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

public class UPChestBoat extends ChestBoat implements HasUPBoatType {
    private static final EntityDataAccessor<String> BOAT_TYPE;

    public UPChestBoat(EntityType<? extends Boat> type, Level level) {
        super(type, level);
    }

    public UPChestBoat(Level level, ResourceLocation type, double x, double y, double z) {
        super(UPEntities.CHEST_BOAT.get(), level);
        this.setType(type);
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    public UPChestBoat(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(UPEntities.CHEST_BOAT.get(), level);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BOAT_TYPE, UPBoatTypes.UNDEFINED_BOAT_LOCATION.toString());
    }

    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Type", this.entityData.get(BOAT_TYPE));
    }

    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Type", 8)) {
            String type = compound.getString("Type");
            ResourceLocation name = new ResourceLocation(type);
            if (UPBoatTypes.getType(name) != null) {
                this.setType(name);
            } else {
                this.setType(UPBoatTypes.UNDEFINED_BOAT_LOCATION);
            }
        } else {
            this.setType(UPBoatTypes.UNDEFINED_BOAT_LOCATION);
        }

    }

//    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
//        this.lastYd = this.getDeltaMovement().y;
//        if (!this.isPassenger()) {
//            if (onGroundIn) {
//                if (this.fallDistance > 3.0F) {
//                    if (this.status != Status.ON_LAND) {
//                        this.fallDistance = 0.0F;
//                        return;
//                    }
//                    this.causeFallDamage(this.fallDistance, 1.0F, this.damageSources().fall());
//                    if (!this.level().isClientSide && this.isAlive()) {
//                        this.kill();
//                        if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
//                            for(int i = 0; i < 3; ++i) {
//                                this.spawnAtLocation(this.getBoatType().getPlankItem());
//                            }
//
//                            for(int j = 0; j < 2; ++j) {
//                                this.spawnAtLocation(Items.STICK);
//                            }
//                        }
//                    }
//                }
//                this.fallDistance = 0.0F;
//            } else if (!this.level().getFluidState(this.blockPosition().below()).is(FluidTags.WATER) && y < (double)0.0F) {
//                this.fallDistance = (float)((double)this.fallDistance - y);
//            }
//        }
//    }

    public Item getDropItem() {
        return this.getBoatType().getChestBoatItem();
    }

    public Boat.Type getVariant() {
        return Type.OAK;
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void setType(ResourceLocation type) {
        this.entityData.set(BOAT_TYPE, type.toString());
    }

    public UPBoatTypes.UPBoatType getBoatType() {
        return UPBoatTypes.getType(new ResourceLocation(this.entityData.get(BOAT_TYPE)));
    }

    static {
        BOAT_TYPE = SynchedEntityData.defineId(UPChestBoat.class, EntityDataSerializers.STRING);
    }
}
