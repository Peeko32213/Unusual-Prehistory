package com.peeko32213.unusualprehistory.common.item.tool;

import com.peeko32213.unusualprehistory.core.registry.UPEnchantments;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;

public class VelociraptorShieldItem extends ShieldItem {

    public VelociraptorShieldItem(Properties properties) {
        super(properties);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
    }

    @Override
    public int getEnchantmentValue() {
        return 12;
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        int longDash = EnchantmentHelper.getItemEnchantmentLevel(UPEnchantments.LONG_DASH.get(), itemstack);

        Vec3 view = player.getViewVector(1.0F);

        if (player.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !player.onGround() && !player.isInFluidType()) {

            player.setDeltaMovement(view.multiply(player.isFallFlying() ? 1.3D + (longDash / 2.5) : 1.8D + (longDash / 1.5), player.isFallFlying() ? 1.0D : 1.5D, player.isFallFlying() ? 1.3D + (longDash / 2.5) : 1.8D + (longDash / 1.5)));
            player.getCooldowns().addCooldown(this, player.isFallFlying() ? 60 + (longDash * 20) : 40 + (longDash * 16));
            itemstack.hurtAndBreak(1, player, (player1) -> player.broadcastBreakEvent(player.getUsedItemHand()));

            for (int i = 0; i < 6 + player.getRandom().nextInt(8); i++) {
                player.level().addParticle(ParticleTypes.CLOUD, player.getRandomX(0.8F), player.getY() + 0.3F + player.getRandom().nextFloat() * 0.2F, player.getRandomZ(0.8F), player.getRandom().nextInt(1), player.getRandom().nextInt(1), player.getRandom().nextInt(1));
            }
        }
        else {
            player.startUsingItem(hand);
            itemstack.hurtAndBreak(1, player, (player1) -> player.broadcastBreakEvent(player.getUsedItemHand()));
        }

        return InteractionResultHolder.consume(itemstack);
    }

    public boolean isValidRepairItem(ItemStack stack, ItemStack stack2) {
        return UPItems.AMBER.get() == stack2.getItem() || super.isValidRepairItem(stack, stack2);
    }
}
