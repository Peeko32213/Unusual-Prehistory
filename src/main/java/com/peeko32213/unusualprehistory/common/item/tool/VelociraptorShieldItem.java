package com.peeko32213.unusualprehistory.common.item.tool;

import com.peeko32213.unusualprehistory.core.registry.UPEnchantments;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class VelociraptorShieldItem extends Item implements Equipable {

    public VelociraptorShieldItem(Properties group) {
        super(group);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        return UseAnim.BLOCK;
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.OFFHAND;
    }

    @Override
    public int getEnchantmentValue() {
        return 12;
    }

//    @Override
//    public boolean canPerformAction(@NotNull ItemStack stack, net.minecraftforge.common.@NotNull ToolAction toolAction) {
//        return net.minecraftforge.common.ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction);
//    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player livingEntity, @NotNull InteractionHand hand) {
        ItemStack itemstack = livingEntity.getItemInHand(hand);

        int longDash = EnchantmentHelper.getItemEnchantmentLevel(UPEnchantments.LONG_DASH.get(), itemstack);
        int shortDash = EnchantmentHelper.getItemEnchantmentLevel(UPEnchantments.SHORT_DASH.get(), itemstack);

        Vec3 view = livingEntity.getViewVector(1.0F);

        if (!livingEntity.isFallFlying()) {
            livingEntity.swing(hand);

            if (longDash > 0) {
                livingEntity.setDeltaMovement(view.multiply(1.25D + (longDash / 1.5), 1.0D, 1.25D + (longDash / 1.5)));
                livingEntity.getCooldowns().addCooldown(this, 50 + (longDash * 12));
            } else if (shortDash > 0) {
                livingEntity.setDeltaMovement(view.multiply(0.75D, 0.6D, 0.75D));
                livingEntity.getCooldowns().addCooldown(this, 50 - (shortDash * 10));
            } else {
                livingEntity.setDeltaMovement(view.multiply(1.25D, 1.0D, 1.25D));
                livingEntity.getCooldowns().addCooldown(this, 50);
            }

            itemstack.hurtAndBreak(1, livingEntity, (player) -> {
                player.broadcastBreakEvent(livingEntity.getUsedItemHand());
            });
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    public boolean isValidRepairItem(ItemStack stack, ItemStack stack2) {
        return UPItems.AMBER.get() == stack2.getItem() || super.isValidRepairItem(stack, stack2);
    }
}
