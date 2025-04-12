//package com.peeko32213.unusualprehistory.common.message;
//
//import com.google.gson.JsonObject;
//import com.mojang.serialization.JsonOps;
//import com.peeko32213.unusualprehistory.UnusualPrehistory;
//import com.peeko32213.unusualprehistory.client.animation.ServerResourceCache;
//import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.nbt.ListTag;
//import net.minecraft.nbt.NbtOps;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//import net.minecraft.resources.ResourceLocation;
//import net.neoforged.neoforge.network.handling.PlayPayloadContext;
//import software.bernie.geckolib.util.JsonUtil;
//
//import java.util.Map;
//
//public record SynchResourceObjectToClientPacket<D>(int entityId, Map<ResourceLocation, JsonObject> map, boolean models) implements CustomPacketPayload {
//    //private static final Codec<Map<ResourceLocation, JsonObject>> QUESTS_MAPPER = Codec.unboundedMap(ResourceLocation.CODEC,  Codec..CODEC)
//    //        .xmap(Map::copyOf, Map::copyOf)
//    //        .orElse(e -> {LOGGER.error("Failed to parse Quest Entries can't send packet! Due to " + e);},
//    //                new HashMap<>());
//
//
//    public static final ResourceLocation ID = new ResourceLocation(UnusualPrehistory.MODID, "synch_resource_object_to_client_packet");
//
//
//    @Override
//    public void write(FriendlyByteBuf buffer) {
//        CompoundTag tag = new CompoundTag();
//        ListTag tags = new ListTag();
//        for( Map.Entry<ResourceLocation, JsonObject> mapEntry : map.entrySet()) {
//            CompoundTag tagEntry = new CompoundTag();
//            tagEntry.putString("rl", mapEntry.getKey().toString());
//            CompoundTag tag1 = (CompoundTag) JsonOps.INSTANCE.convertTo(NbtOps.INSTANCE, mapEntry.getValue());
//            //tagEntry.putString("object", mapEntry.getValue().getAsString());
//            tagEntry.put("object", tag1);
//            tags.add(tagEntry);
//        }
//        tag.put("map", tags);
//        buffer.writeNbt(tag);
//        buffer.writeVarInt(entityId);
//        buffer.writeBoolean(models);
//        //JsonOps.INSTANCE.convertTo(NbtOps.INSTANCE, );
//    }
//
//    public static <D> SynchResourceObjectToClientPacket<D> decode(FriendlyByteBuf buffer) {
//        CompoundTag tag = buffer.readNbt();
//        ListTag tags = tag.getList("map", 10);
//        Map<ResourceLocation, JsonObject> jsonObjectMap = new Object2ObjectOpenHashMap<>();
//        for (int i = 0; i < tags.size(); i++) {
//            CompoundTag entry = tags.getCompound(i);
//            String rlString = entry.getString("rl");
//            ResourceLocation location = new ResourceLocation(rlString);
//            //String string = entry.getString("object");
//            CompoundTag tag1 = entry.getCompound("object");
//            JsonObject object = (JsonObject) NbtOps.INSTANCE.convertTo(JsonOps.INSTANCE, tag1);
//            //JsonObject object = JsonUtil.GEO_GSON.fromJson(string, JsonObject.class);
//            jsonObjectMap.put(location, object);
//        }
//        int id = buffer.readVarInt();
//        boolean models = buffer.readBoolean();
//        return new SynchResourceObjectToClientPacket<>(id, jsonObjectMap, models);
//    }
//
//    public void receivePacket(PlayPayloadContext context) {
//        context.workHandler().execute(() -> {
//            //Entity entity = ClientUtils.getLevel().getEntity(this.entityId);
//            if(models) {
//                ServerResourceCache.setModels(map);
//            }
//            else {
//                ServerResourceCache.setAnimations(map);
//            }
//        });
//    }
//
//    @Override
//    public ResourceLocation id() {
//        return ID;
//    }
//}
