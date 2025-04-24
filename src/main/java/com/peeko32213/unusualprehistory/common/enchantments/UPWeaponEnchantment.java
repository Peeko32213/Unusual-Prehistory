package com.peeko32213.unusualprehistory.common.enchantments;

import com.peeko32213.unusualprehistory.core.registry.UPEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class UPWeaponEnchantment extends Enchantment {

    private int levels;
    private int minXP;
    private String registryName;

    public UPWeaponEnchantment(String name, Rarity rarity, EnchantmentCategory category, int levels, int minXP, EquipmentSlot... equipmentSlot) {
        super(rarity, category, equipmentSlot);
        this.levels = levels;
        this.minXP = minXP;
        this.registryName = name;
    }

    public int getMinCost(int i) {
        return 1 + (i - 1) * minXP;
    }

    public int getMaxCost(int i) {
        return super.getMinCost(i) + 30;
    }

    public int getMaxLevel() {
        return levels;
    }


    protected boolean checkCompatibility(Enchantment enchantment) {
        return this != enchantment && UPEnchantments.areCompatible(this, enchantment);
    }

    public boolean isTradeable() {
        return true;
    }

    public boolean isDiscoverable() {
        return true;
    }

    public boolean isAllowedOnBooks() {
        return true;
    }

    public String getName(){
        return registryName;
    }
}
