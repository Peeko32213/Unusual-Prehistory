package com.peeko32213.unusualprehistory.common.item.armor.shedscale;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.peeko32213.unusualprehistory.common.item.armor.material.UPArmorMaterial;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.*;

public class ItemShedscaleArmor extends ArmorItem implements GeoItem {
    private static final Map<MobEffect, MobEffect> TO_CHANGE_MAP = new HashMap<>() {{
        put(MobEffects.POISON, MobEffects.REGENERATION);
        put(MobEffects.MOVEMENT_SLOWDOWN, MobEffects.MOVEMENT_SPEED);
        put(MobEffects.DIG_SLOWDOWN, MobEffects.DIG_SPEED);
        put(MobEffects.HARM, MobEffects.HEAL);
        put(MobEffects.CONFUSION, MobEffects.NIGHT_VISION);
        put(MobEffects.HUNGER, MobEffects.SATURATION);
        put(MobEffects.BLINDNESS, MobEffects.NIGHT_VISION);
        put(MobEffects.WITHER, MobEffects.REGENERATION);
        put(MobEffects.LEVITATION, MobEffects.SLOW_FALLING);
        put(MobEffects.UNLUCK, MobEffects.LUCK);
    }};

    private static final UUID[] SWIM_SPEED_MOD_UUID = new UUID[]{UUID.fromString("480d87c2-2f0a-11ee-be56-0242ac120002"), UUID.fromString("480d8ad8-2f0a-11ee-be56-0242ac120002"), UUID.fromString("480d8c36-2f0a-11ee-be56-0242ac120002"), UUID.fromString("480d8da8-2f0a-11ee-be56-0242ac120002")};

    protected final EquipmentSlot slot;
    private final int defense;
    private final float toughness;
    protected final float knockbackResistance;
    protected final ArmorMaterial material;

    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ItemShedscaleArmor(ArmorMaterial materialIn, ArmorItem.Type slot, Properties builder, double swimSpeed) {
        super(materialIn, slot, builder);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
        this.material = materialIn;
        this.slot = slot.getSlot();
        this.defense = materialIn.getDefenseForType(slot);
        this.toughness = materialIn.getToughness();
        this.knockbackResistance = materialIn.getKnockbackResistance();
        UUID uuid = SWIM_SPEED_MOD_UUID[slot.getSlot().getIndex()];
        attributeBuilder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", (double)this.defense, AttributeModifier.Operation.ADDITION));
        attributeBuilder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", (double)this.toughness, AttributeModifier.Operation.ADDITION));
        attributeBuilder.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(uuid, "Swim Speed Mod", swimSpeed, AttributeModifier.Operation.ADDITION));

        if (this.knockbackResistance > 0) {
            attributeBuilder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", (double)this.knockbackResistance, AttributeModifier.Operation.ADDITION));
        }



        this.defaultModifiers = attributeBuilder.build();
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if(level.isClientSide) return;
        if (hasFullSuitOfArmorOn(player) && hasCorrectArmorOn(UPArmorMaterial.SHEDSCALE, player)){
            giveEffect(player);
        }
    }

    private boolean hasFullSuitOfArmorOn(Player player) {
        ItemStack boots = player.getInventory().getArmor(0);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack breastplate = player.getInventory().getArmor(2);
        ItemStack helmet = player.getInventory().getArmor(3);

        return !helmet.isEmpty() && !breastplate.isEmpty()
                && !leggings.isEmpty() && !boots.isEmpty();
    }

    private boolean hasCorrectArmorOn(ArmorMaterial material, Player player) {
        for (ItemStack armorStack : player.getInventory().armor) {
            if (!(armorStack.getItem() instanceof ItemShedscaleArmor)) {
                return false;
            }
        }

        ItemShedscaleArmor boots = ((ItemShedscaleArmor) player.getInventory().getArmor(0).getItem());
        ItemShedscaleArmor leggings = ((ItemShedscaleArmor) player.getInventory().getArmor(1).getItem());
        ItemShedscaleArmor breastplate = ((ItemShedscaleArmor) player.getInventory().getArmor(2).getItem());
        ItemShedscaleArmor helmet = ((ItemShedscaleArmor) player.getInventory().getArmor(3).getItem());

        return helmet.getMaterial() == material && breastplate.getMaterial() == material &&
                leggings.getMaterial() == material && boots.getMaterial() == material;
    }

    private boolean giveEffect(Player player) {
        // Create a copy of the player's active effects
        Collection<MobEffectInstance> mobEffectInstances = new ArrayList<>(player.getActiveEffects());

        // Create a map to store the modified effects
        Map<MobEffect, MobEffectInstance> modifiedEffects = new HashMap<>();

        boolean hasGivenEffect = false;

        for (MobEffectInstance mobEffectInstance : mobEffectInstances) {
            MobEffect effect = mobEffectInstance.getEffect();

            if (TO_CHANGE_MAP.containsKey(effect)) {
                // Modify the effect and add it to the modifiedEffects map
                MobEffect givenEffect = TO_CHANGE_MAP.get(effect);
                int duration = mobEffectInstance.getDuration();
                int amplifier = mobEffectInstance.getAmplifier();
                MobEffectInstance toGiveInstance = new MobEffectInstance(givenEffect, duration, amplifier);
                modifiedEffects.put(givenEffect, toGiveInstance);
                hasGivenEffect = true;
            } else {
                // Add the unaffected effects to the modifiedEffects map
                modifiedEffects.put(effect, mobEffectInstance);
            }
        }

        // Remove all effects from the player
        player.removeAllEffects();

        // Add the modified effects back to the player
        for (MobEffectInstance modifiedEffect : modifiedEffects.values()) {
            player.addEffect(modifiedEffect);
        }

        return hasGivenEffect;
    }


    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == this.slot ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
