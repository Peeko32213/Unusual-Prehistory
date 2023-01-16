package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.screen.AnalyzerMenu;
import com.peeko32213.unusualprehistory.common.screen.CultivatorMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UPMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, UnusualPrehistory.MODID);

    public static final RegistryObject<MenuType<AnalyzerMenu>> ANALYZER_MENU =
            registerMenuType(AnalyzerMenu::new, "analyzer_menu");

    public static final RegistryObject<MenuType<CultivatorMenu>> CULTIVATOR_MENU =
            registerMenuType(CultivatorMenu::new, "cultivator_menu");

        public static final RegistryObject<MenuType<DNAFridgeMenu>> DNA_FRIDGE_MENU =
            registerMenuType(DNAFridgeMenu::new,"dna_fridge_menu");
    
    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

}
