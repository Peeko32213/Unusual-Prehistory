package com.peeko32213.unusualprehistory.core.registry.entities;

import com.peeko32213.unusualprehistory.common.data.PrehistoricEgg;
import com.peeko32213.unusualprehistory.common.item.PrehistoricEggItem;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.modPrefix;

public class UPPrehistoricEggRegistry {
    public static void bootstrap(BootstapContext<PrehistoricEgg> context) {
        for(RegistryObject<Item> item : UPItems.ITEMS.getEntries()) {
            if(item.get() instanceof PrehistoricEggItem eggItem) {
                ResourceLocation location = BuiltInRegistries.ENTITY_TYPE.getKey(eggItem.getEntity().get());
                ResourceKey<PrehistoricEgg> resourceKey = ResourceKey.create(UPRegistry.Keys.PREHISTORIC_EGG, modPrefix(location.getPath() + "_egg"));
                context.register(resourceKey, new PrehistoricEgg(eggItem.getEntity().get(), eggItem.getSize(), eggItem.getVariant(), eggItem.getHatchTime(), eggItem.getColor1(), eggItem.getColor2()));
            }
        }
    }
}
