package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.common.data.PrehistoricEgg;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.modPrefix;

public class UPRegistry {
    static { init(); }

    public static final class Keys {
        public static final ResourceKey<Registry<PrehistoricEgg>> PREHISTORIC_EGG = key(modPrefix("prehistoric_egg").toString());

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
