package com.peeko32213.unusualprehistory.core.registry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.UPBoatEntity;

public class UPModelLayers {

    public static ModelLayerLocation createChestBoat(UPBoatEntity.BoatType type) {
        return create("chest_boat/" + type.getName(), "main");
    }

    public static ModelLayerLocation createBoat(UPBoatEntity.BoatType type) {
        return create("boat/" + type.getName(), "main");
    }

    public static ModelLayerLocation create(String name) {
        return create(name, "main");
    }

    private static ModelLayerLocation create(String id, String layer) {
        return new ModelLayerLocation(new ResourceLocation(UnusualPrehistory.MODID, id), layer);
    }

}