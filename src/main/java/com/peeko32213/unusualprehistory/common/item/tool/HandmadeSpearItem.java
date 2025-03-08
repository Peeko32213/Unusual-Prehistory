package com.peeko32213.unusualprehistory.common.item.tool;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.peeko32213.unusualprehistory.client.model.tool.HandmadeSpearModel;
import com.peeko32213.unusualprehistory.client.render.tool.ToolRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;
import java.util.function.Consumer;

public class HandmadeSpearItem extends SwordItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final UUID RANGE = UUID.fromString("20D3EB3F-226F-4325-873E-9B0932E4E5C6");

    public HandmadeSpearItem(Tier tier, int attackDamage, float attackSpeed) {
        super(tier, attackDamage, attackSpeed, new Properties()
                .stacksTo(1)
                .defaultDurability(tier.getUses() * 3)
        );
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
        attributeBuilder.putAll(super.getDefaultAttributeModifiers(slot));
        attributeBuilder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(RANGE, "Range modifier", 1.5, AttributeModifier.Operation.ADDITION));
        return slot == EquipmentSlot.MAINHAND ? attributeBuilder.build() : super.getDefaultAttributeModifiers(slot);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private final ToolRenderer<HandmadeSpearItem> renderer = new ToolRenderer<>(new HandmadeSpearModel());

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return this.renderer;
            }
        });
    }

    @Override
    public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction) {
        return toolAction != ToolActions.SWORD_SWEEP && super.canPerformAction(stack, toolAction);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "idle", state -> PlayState.CONTINUE).triggerableAnim("animation.handmade_spear.idle", DefaultAnimations.IDLE));
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, LivingEntity targetEntity, LivingEntity attacker) {
        double xRatio = -Mth.sin(attacker.getYRot() * 0.017453292F);
        double zRatio = Mth.cos(attacker.getYRot() * 0.017453292F);
        float strength = -0.2F;
        float f = Mth.sqrt((float) (xRatio * xRatio + zRatio * zRatio));
        if (!targetEntity.isInWater() && !targetEntity.isSwimming()){
            targetEntity.setDeltaMovement((targetEntity.getDeltaMovement().x / 2) - xRatio / (double) f * (double) strength, 0.7D * (1.0D - targetEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)), (targetEntity.getDeltaMovement().z / 2) - zRatio / (double) f * (double) strength);
            targetEntity.setOnGround(false);
        }
        return super.hurtEnemy(stack, targetEntity, attacker);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player livingEntityIn, @NotNull InteractionHand hand) {
        if (!livingEntityIn.isInWater() && !livingEntityIn.isSwimming()) {
            double xRatio = -Mth.sin(livingEntityIn.getYRot() * 0.017453292F);
            double zRatio = Mth.cos(livingEntityIn.getYRot() * 0.017453292F);
            float strength = -0.2F;
            float f = Mth.sqrt((float) (xRatio * xRatio + zRatio * zRatio));
            livingEntityIn.setDeltaMovement((livingEntityIn.getDeltaMovement().x / 2) - xRatio / (double) f * (double) strength, 0.68D * (1.0D - livingEntityIn.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)), (livingEntityIn.getDeltaMovement().z / 2) - zRatio / (double) f * (double) strength);
            livingEntityIn.setOnGround(false);
            ItemStack itemstack = livingEntityIn.getItemInHand(hand);
            livingEntityIn.getCooldowns().addCooldown(this, 30);
            itemstack.hurtAndBreak(1, livingEntityIn, (player) -> {
                player.broadcastBreakEvent(livingEntityIn.getUsedItemHand());
            });
        }
        return super.use(level, livingEntityIn, hand);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.category.canEnchant(stack.getItem())
                && enchantment != Enchantments.BINDING_CURSE
                && enchantment != Enchantments.SHARPNESS
                && enchantment != Enchantments.BANE_OF_ARTHROPODS
                && enchantment != Enchantments.SMITE
                && enchantment != Enchantments.FIRE_ASPECT
                && enchantment != Enchantments.SWEEPING_EDGE
        ;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
