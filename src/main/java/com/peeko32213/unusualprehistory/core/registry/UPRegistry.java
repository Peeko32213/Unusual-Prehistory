package com.peeko32213.unusualprehistory.core.registry;

import com.mojang.serialization.Codec;
import com.peeko32213.unusualprehistory.common.data.PrehistoricEgg;
import com.peeko32213.unusualprehistory.common.data.attack.EntityAttack;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

import static com.peeko32213.unusualprehistory.core.UnusualPrehistory.prefix;

public class UPRegistry {
    static { init(); }
    public static final DeferredRegister<Codec<? extends EntityAttack>> ENTITY_ATTACKS_TYPE_SERIALIZER = DeferredRegister.create(UPRegistry.Keys.ENTITY_ATTACKS_TYPE_SERIALIZER, UPRegistry.Keys.ENTITY_ATTACKS_TYPE_SERIALIZER.location().getNamespace());
    public static final Supplier<IForgeRegistry<Codec<? extends EntityAttack>>> ENTITY_ATTACK_TYPE_SERIALIZER_SUPPLIER = ENTITY_ATTACKS_TYPE_SERIALIZER.makeRegistry(() -> new RegistryBuilder<Codec<? extends EntityAttack>>().disableSaving().disableSync());


    public static final class Keys {
        //public static final ResourceKey<Registry<CustomLevelRendererType<?>>> CUSTOM_LEVEL_RENDER_TYPE_SERIALIZER = key(prefix("custom_level_renderer_type_serializer").toString());

        public static final ResourceKey<Registry<Codec<? extends EntityAttack>>> ENTITY_ATTACKS_TYPE_SERIALIZER = key(prefix("entity_attacks_type_serializer").toString());
        public static final ResourceKey<Registry<EntityAttack>> ENTITY_ATTACKS = key(prefix("entity_attacks_type").toString());
        public static final ResourceKey<Registry<PrehistoricEgg>> PREHISTORIC_EGG = key(prefix("prehistoric_egg").toString());


        private static <T> ResourceKey<Registry<T>> key(String name)
        {
            return ResourceKey.createRegistryKey(ResourceLocation.tryParse(name));
        }
        private static void init() {}

    }

    private static void init()
    {
        Keys.init();
    }
}
