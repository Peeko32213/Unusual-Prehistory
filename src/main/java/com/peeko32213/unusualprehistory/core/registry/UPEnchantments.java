package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.enchantments.UPWeaponEnchantment;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = EventBusSubscriber.Bus.MOD)
public class UPEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, UnusualPrehistory.MODID);

    public static final EnchantmentCategory VELOCI_SHIELD = EnchantmentCategory.create("veloci_shield", (item -> item == UPItems.VELOCI_SHIELD.get()));

    // Unusual Prehistory categories
    public static final RegistryObject<Enchantment> LONG_DASH = ENCHANTMENTS.register("long_dash", () -> new UPWeaponEnchantment("long_dash", Enchantment.Rarity.UNCOMMON, VELOCI_SHIELD, 3, 20, EquipmentSlot.MAINHAND));

    // Vanilla categories

    public static boolean areCompatible(UPWeaponEnchantment enchantment1, Enchantment enchantment2) {
        return true;
    }

    public static void addAllEnchantsToCreativeTab(CreativeModeTab.Output output, EnchantmentCategory enchantmentCategory){
        for (RegistryObject<Enchantment> enchantObject : ENCHANTMENTS.getEntries()) {
            if (enchantObject.isPresent()) {
                Enchantment enchant = enchantObject.get();
                if(enchant.category == enchantmentCategory){
                    EnchantmentInstance instance = new EnchantmentInstance(enchant, enchant.getMaxLevel());
                    output.accept(EnchantedBookItem.createForEnchantment(instance));
                }
            }
        }
    }
}