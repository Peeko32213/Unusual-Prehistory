package com.peeko32213.unusualprehistory.common.item;

import com.peeko32213.unusualprehistory.common.capabilities.UPCapabilities;
import com.peeko32213.unusualprehistory.common.message.AmberProtectionSyncS2CPacket;
import com.peeko32213.unusualprehistory.core.registry.UPMessages;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AmberGummyItem extends Item {
    public AmberGummyItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack itemStack =  super.finishUsingItem(stack, level, entity);
        if(!level.isClientSide && entity instanceof ServerPlayer serverPlayer){
            serverPlayer.getCapability(UPCapabilities.PLAYER_CAPABILITY).ifPresent(capability -> {
                if(capability.amberProtection >= serverPlayer.getMaxHealth()){
                    serverPlayer.displayClientMessage(Component.translatable("player_capability.amber_protection_full").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC), true);
                    return;
                }
                capability.amberProtection += 2;
                //serverPlayer.displayClientMessage(Component.literal("Amount of amber prot: ").append(String.valueOf(capability.amberProtection)), true);
                UPMessages.sendToPlayer(new AmberProtectionSyncS2CPacket(capability.amberProtection), serverPlayer);
            });
        }


        return itemStack;
    }


}
