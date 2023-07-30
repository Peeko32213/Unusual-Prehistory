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
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemshedScaleArmor extends GeoArmorItem implements IAnimatable {
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

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public ItemshedScaleArmor(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder, double swimSpeed) {
        super(materialIn, slot, builder);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
        this.material = materialIn;
        this.slot = slot;
        this.defense = materialIn.getDefenseForSlot(slot);
        this.toughness = materialIn.getToughness();
        this.knockbackResistance = materialIn.getKnockbackResistance();
        UUID uuid = SWIM_SPEED_MOD_UUID[slot.getIndex()];
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
            if (!(armorStack.getItem() instanceof ItemshedScaleArmor)) {
                return false;
            }
        }

        ItemshedScaleArmor boots = ((ItemshedScaleArmor) player.getInventory().getArmor(0).getItem());
        ItemshedScaleArmor leggings = ((ItemshedScaleArmor) player.getInventory().getArmor(1).getItem());
        ItemshedScaleArmor breastplate = ((ItemshedScaleArmor) player.getInventory().getArmor(2).getItem());
        ItemshedScaleArmor helmet = ((ItemshedScaleArmor) player.getInventory().getArmor(3).getItem());

        return helmet.getMaterial() == material && breastplate.getMaterial() == material &&
                leggings.getMaterial() == material && boots.getMaterial() == material;
    }

    private boolean giveEffect(Player player) {
        Collection<MobEffectInstance> mobEffectInstances = player.getActiveEffects();
        boolean hasGivenEffect = false;
        for(MobEffectInstance mobEffectInstance : mobEffectInstances)
        {
            MobEffect effect = mobEffectInstance.getEffect();
            int duration = mobEffectInstance.getDuration();
            int amplifier = mobEffectInstance.getAmplifier();
            if(TO_CHANGE_MAP.containsKey(effect))
            {
                MobEffect givenEffect = TO_CHANGE_MAP.get(effect);
                MobEffectInstance toGiveInstance = new MobEffectInstance(givenEffect, duration, amplifier);
                player.addEffect(toGiveInstance);
                player.removeEffect(effect);
                hasGivenEffect = true;
            }
        }
        return hasGivenEffect;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == this.slot ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void registerControllers(AnimationData data) {
    }
}
