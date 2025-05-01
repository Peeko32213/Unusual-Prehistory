package com.peeko32213.unusualprehistory.core.registry.entities;

import com.mojang.datafixers.util.Pair;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ChestRaftModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.RaftModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;

public final class UPBoatTypes {
    public static final ResourceLocation UNDEFINED_BOAT_LOCATION = new ResourceLocation("oak");
    private static final Map<ResourceLocation, UPBoatType> BOATS = new HashMap();

    public UPBoatTypes() {
    }

    public static synchronized void registerType(ResourceLocation name, Supplier<Item> boat, Supplier<Item> chestBoat, Supplier<Block> plank, boolean raft) {
        BOATS.put(name, new UPBoatType(name, boat, chestBoat, plank, raft));
    }

    @Nullable
    public static UPBoatType getType(ResourceLocation name) {
        return BOATS.get(name);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        LayerDefinition boatModel = BoatModel.createBodyModel();
        LayerDefinition chestBoatModel = ChestBoatModel.createBodyModel();
        LayerDefinition raftModel = RaftModel.createBodyModel();
        LayerDefinition chestRaftModel = ChestRaftModel.createBodyModel();
        BOATS.forEach((name, type) -> {
            if (name != UNDEFINED_BOAT_LOCATION) {
                boolean isRaft = type.isRaft();
                event.registerLayerDefinition(type.getBoatModelLayerLocation(), isRaft ? () -> raftModel : () -> boatModel);
                event.registerLayerDefinition(type.getChestBoatModelLayerLocation(), isRaft ? () -> chestRaftModel : () -> chestBoatModel);
            }
        });
    }

    @OnlyIn(Dist.CLIENT)
    public static IdentityHashMap<UPBoatType, Pair<ResourceLocation, ListModel<Boat>>> createBoatResources(EntityRendererProvider.Context context, boolean chest) {
        IdentityHashMap<UPBoatType, Pair<ResourceLocation, ListModel<Boat>>> boatTypeToModel = new IdentityHashMap();
        BOATS.values().forEach((type) -> boatTypeToModel.put(type, Pair.of(chest ? type.getChestVariantTexture() : type.getTexture(), createBoatModel(context, type, chest))));
        return boatTypeToModel;
    }

    @OnlyIn(Dist.CLIENT)
    private static ListModel<Boat> createBoatModel(EntityRendererProvider.Context context, UPBoatType type, boolean chest) {
        ModelPart modelpart = context.bakeLayer(chest ? type.getChestBoatModelLayerLocation() : type.getBoatModelLayerLocation());
        if (type.isRaft()) {
            return chest ? new ChestRaftModel(modelpart) : new RaftModel(modelpart);
        } else {
            return chest ? new ChestBoatModel(modelpart) : new BoatModel(modelpart);
        }
    }

    static {
        registerType(UNDEFINED_BOAT_LOCATION, () -> Items.OAK_BOAT, () -> Items.OAK_CHEST_BOAT, () -> Blocks.OAK_PLANKS, false);
    }

    public static class UPBoatType {
        private final ResourceLocation name;
        private final Supplier<Item> boat;
        private final Supplier<Item> chestBoat;
        private final Supplier<Block> plank;
        private final boolean raft;
        private final ResourceLocation texture;
        private final ResourceLocation chestVariantTexture;

        public UPBoatType(ResourceLocation name, Supplier<Item> boat, Supplier<Item> chestBoat, Supplier<Block> plank, boolean raft) {
            this.name = name;
            this.boat = boat;
            this.chestBoat = chestBoat;
            this.plank = plank;
            this.raft = raft;
            String namespace = name.getNamespace();
            String path = name.getPath();
            this.texture = new ResourceLocation(namespace, "textures/entity/boat/" + path + ".png");
            this.chestVariantTexture = new ResourceLocation(namespace, "textures/entity/chest_boat/" + path + ".png");
        }

        public ResourceLocation getName() {
            return this.name;
        }

        public Item getBoatItem() {
            return this.boat.get();
        }

        public Item getChestBoatItem() {
            return this.chestBoat.get();
        }

        public Item getPlankItem() {
            return this.plank.get().asItem();
        }

        public boolean isRaft() {
            return this.raft;
        }

        public ResourceLocation getTexture() {
            return this.texture;
        }

        public ResourceLocation getChestVariantTexture() {
            return this.chestVariantTexture;
        }

        @OnlyIn(Dist.CLIENT)
        public ModelLayerLocation getBoatModelLayerLocation() {
            ResourceLocation name = this.getName();
            String namespace = name.getNamespace();
            String path = name.getPath();
            return new ModelLayerLocation(new ResourceLocation(namespace, "boat/" + path), "main");
        }

        @OnlyIn(Dist.CLIENT)
        public ModelLayerLocation getChestBoatModelLayerLocation() {
            ResourceLocation name = this.getName();
            String namespace = name.getNamespace();
            String path = name.getPath();
            return new ModelLayerLocation(new ResourceLocation(namespace, "chest_boat/" + path), "main");
        }
    }
}

