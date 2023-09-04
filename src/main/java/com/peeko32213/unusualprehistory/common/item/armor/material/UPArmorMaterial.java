package com.peeko32213.unusualprehistory.common.item.armor.material;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum UPArmorMaterial implements ArmorMaterial {

    AUSTRO("austro", 28, new int[]{1, 3, 4, 1}, 19, SoundEvents.ARMOR_EQUIP_LEATHER,
            2.0F, 0.0F, () -> Ingredient.of(UPItems.AUSTRO_FEATHER.get())),
    MAJUNGA("majunga", 28, new int[]{2, 5, 8, 3}, 19, SoundEvents.ARMOR_EQUIP_GOLD,
            2.0F, 0.0F, () -> Ingredient.of(UPItems.MAJUNGA_SCUTE.get())),
    TYRANTS("tyrants", 28, new int[]{5, 1, 1, 1}, 19, SoundEvents.ARMOR_EQUIP_GOLD,
            2.0F, 0.0F, () -> Ingredient.of(UPItems.REX_SCALE.get())),
    SHEDSCALE("shedscale", 28, new int[]{2, 5, 5, 2}, 19, SoundEvents.ARMOR_EQUIP_LEATHER,
            2.0F, 0.0F, () -> Ingredient.of(UPItems.RAW_STETHA.get())),

    SLOTH_POUCH("sloth_pouch", 28, new int[]{1, 1, 1, 1}, 19, SoundEvents.ARMOR_EQUIP_LEATHER,
            2.0F, 0.0F, () -> Ingredient.of(Items.RABBIT_HIDE));
    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    UPArmorMaterial(String p_40474_, int p_40475_, int[] p_40476_, int p_40477_,
                    SoundEvent p_40478_, float p_40479_, float p_40480_, Supplier<Ingredient> p_40481_) {
        this.name = p_40474_;
        this.durabilityMultiplier = p_40475_;
        this.slotProtections = p_40476_;
        this.enchantmentValue = p_40477_;
        this.sound = p_40478_;
        this.toughness = p_40479_;
        this.knockbackResistance = p_40480_;
        this.repairIngredient = new LazyLoadedValue<>(p_40481_);
    }

    public int getDurabilityForSlot(EquipmentSlot pSlot) {
        return HEALTH_PER_SLOT[pSlot.getIndex()] * this.durabilityMultiplier;
    }

    public int getDefenseForSlot(EquipmentSlot pSlot) {
        return this.slotProtections[pSlot.getIndex()];
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public SoundEvent getEquipSound() {
        return this.sound;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public String getName() {
        return UnusualPrehistory.MODID + ":" + this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
