package com.peeko32213.unusualprehistory.common.data.entity.synced;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.TyrannosaurusEntity;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.checkerframework.checker.units.qual.A;
import software.bernie.geckolib.core.animation.Animation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class SerializableSynchedDataRegistry {


    private static final Map<Class<? extends Entity>, Map<ResourceLocation, SerializableSynchedEntityData>> ENTITY_DATA_MAP = new HashMap<>();
    private static final BiMap<ResourceLocation, SerializableSynchedEntityData> ALL_DATA_MAP = HashBiMap.create();

    public static final Codec<SerializableSynchedEntityData> CODEC = ExtraCodecs.stringResolverCodec(sa -> ALL_DATA_MAP.inverse().get(sa).toString(), key -> ALL_DATA_MAP.get(new ResourceLocation(key)));


//    public static final SerializableSynchedData<Integer> REX_VARIANT = new SerializableSynchedData<>(prefix("rex_variant"), TyrannosaurusEntity.class, EntityDataSerializers.INT, 0, Object::toString, Integer::parseInt);
//    public static final SerializableSynchedData<Integer> REX_ANIMATION_STATE = new SerializableSynchedData<>(prefix("rex_animation_state"), TyrannosaurusEntity.class, EntityDataSerializers.INT, 0, Object::toString, Integer::parseInt);
//    public static final SerializableSynchedData<Boolean> REX_EEPY = new SerializableSynchedData<>(prefix("rex_eepy"), TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN, false, Object::toString, Boolean::parseBoolean);
//    public static final SerializableSynchedData<Boolean> REX_PASSIVE = new SerializableSynchedData<>(prefix("rex_passive"), TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN, false, Object::toString, Boolean::parseBoolean);
//    public static final SerializableSynchedData<Boolean> REX_IDLE_1_AC = new SerializableSynchedData<>(prefix("rex_idle_1_ac"),TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN, false, Object::toString, Boolean::parseBoolean);
//    public static final SerializableSynchedData<Boolean> REX_IDLE_2_AC = new SerializableSynchedData<>(prefix("rex_idle_2_ac"),TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN, false, Object::toString, Boolean::parseBoolean);
//    public static final SerializableSynchedData<Boolean> REX_IDLE_3_AC = new SerializableSynchedData<>(prefix("rex_idle_3_ac"),TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN, false, Object::toString, Boolean::parseBoolean);
//    public static final SerializableSynchedData<Boolean> REX_IDLE_4_AC = new SerializableSynchedData<>(prefix("rex_idle_4_ac"),TyrannosaurusEntity.class, EntityDataSerializers.BOOLEAN, false, Object::toString, Boolean::parseBoolean);


    public static void register() {
//        register(REX_VARIANT);
//        register(REX_ANIMATION_STATE);
//        register(REX_EEPY);
//        register(REX_PASSIVE);
//        register(REX_IDLE_1_AC);
//        register(REX_IDLE_2_AC);
//        register(REX_IDLE_3_AC);
//        register(REX_IDLE_4_AC);
    }

    public static void register(SerializableSynchedEntityData data) {
        // Store in per-entity map
        ENTITY_DATA_MAP
            .computeIfAbsent(data.getEntityClass(), k -> new HashMap<>())
            .put(data.getId(), data);

        // Store in global map
        ALL_DATA_MAP.put(data.getId(), data);
    }


    /**
     * Get all SynchedDataHandler instances for a specific entity class.
     */
    public static Map<ResourceLocation, SerializableSynchedEntityData> getForEntityClass(Class<? extends Entity> entityClass) {
        return ENTITY_DATA_MAP.getOrDefault(entityClass, new HashMap<>());
    }

    /**
     * Get a specific SynchedDataHandler by key, regardless of entity class.
     */
    public static SerializableSynchedEntityData getByKey(ResourceLocation key) {
        return ALL_DATA_MAP.get(key);
    }

    /**
     * Get all registered keys, regardless of entity class.
     */
    public static Set<ResourceLocation> getAllKeys() {
        return ALL_DATA_MAP.keySet();
    }





}
