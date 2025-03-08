package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.common.data.PrehistoricEgg;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;
import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefixS;

public class UPRegistry {
    static { init(); }


    public static final class Keys {
        //public static final ResourceKey<Registry<CustomLevelRendererType<?>>> CUSTOM_LEVEL_RENDER_TYPE_SERIALIZER = key(prefix("custom_level_renderer_type_serializer").toString());
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
