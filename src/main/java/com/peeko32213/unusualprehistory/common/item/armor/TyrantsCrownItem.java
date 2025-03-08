package com.peeko32213.unusualprehistory.common.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.peeko32213.unusualprehistory.client.render.armor.TyrantsCrownRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;
import java.util.function.Consumer;

public class TyrantsCrownItem extends ArmorItem implements GeoItem {

    private static final UUID[] MAX_HEALTH_MOD_UUID = new UUID[]{UUID.fromString("258b3b82-58b8-4f1d-93d0-d1b4c9a417c6"), UUID.fromString("258b3b82-58b8-4f1d-93d0-d1b4c9a417c6"), UUID.fromString("258b3b82-58b8-4f1d-93d0-d1b4c9a417c6"), UUID.fromString("258b3b82-58b8-4f1d-93d0-d1b4c9a417c6")};

    protected final EquipmentSlot slot;
    private final int defense;
    private final float toughness;

    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public TyrantsCrownItem(ArmorMaterial material, ArmorItem.Type slot, Properties settings, double maxHealth) {
        super(material, slot, settings);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
        this.slot = slot.getSlot();
        this.defense = material.getDefenseForType(slot);
        this.toughness = material.getToughness();
        UUID uuid = MAX_HEALTH_MOD_UUID[slot.getSlot().getIndex()];
        attributeBuilder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", this.defense, AttributeModifier.Operation.ADDITION));
        attributeBuilder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Toughness modifier", this.toughness, AttributeModifier.Operation.ADDITION));
        attributeBuilder.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "Max health modifier", maxHealth, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = attributeBuilder.build();
    }

    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == this.slot ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new TyrantsCrownRenderer();

                // This prepares our GeoArmorRenderer for the current render frame.
                // These parameters may be null however, so we don't do anything further with them
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
