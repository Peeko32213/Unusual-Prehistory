package com.peeko32213.unusualprehistory.client.render;

import com.mojang.datafixers.util.Pair;
import com.peeko32213.unusualprehistory.core.registry.entities.UPBoatTypes;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class UPBoatRenderer extends BoatRenderer {
    private final Map<UPBoatTypes.UPBoatType, Pair<ResourceLocation, ListModel<Boat>>> boatResources;

    public UPBoatRenderer(EntityRendererProvider.Context context, boolean chest) {
        super(context, chest);
        this.boatResources = UPBoatTypes.createBoatResources(context, chest);
    }

    @Override
    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) {
        return boat instanceof HasUPBoatType hasUPBoatType ? this.boatResources.get(hasUPBoatType.getBoatType()) : super.getModelWithLocation(boat);
    }
}
