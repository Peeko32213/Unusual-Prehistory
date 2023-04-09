package com.peeko32213.unusualprehistory.common.item;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ItemEncyclopedia extends Item {

    public ItemEncyclopedia(Properties properties) {
        super(properties);
    }

    private boolean usedOnEntity = false;

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target, InteractionHand hand) {
        ItemStack itemStackIn = playerIn.getItemInHand(hand);
        if (playerIn instanceof ServerPlayer) {
            ServerPlayer serverplayerentity = (ServerPlayer)playerIn;
            serverplayerentity.awardStat(Stats.ITEM_USED.get(this));
        }
        if (playerIn.level.isClientSide && target.getEncodeId() != null && target.getEncodeId().contains(UnusualPrehistory.MODID + ":")) {
            usedOnEntity = true;
            String id = target.getEncodeId().replace(UnusualPrehistory.MODID + ":", "");
            UnusualPrehistory.PROXY.openBookGUI(itemStackIn, id);
        }
        return InteractionResult.PASS;
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemStackIn = playerIn.getItemInHand(handIn);
        if (!usedOnEntity) {
            if (playerIn instanceof ServerPlayer) {
                ServerPlayer serverplayerentity = (ServerPlayer) playerIn;
                CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, itemStackIn);
                serverplayerentity.awardStat(Stats.ITEM_USED.get(this));
            }
            if (worldIn.isClientSide) {
                UnusualPrehistory.PROXY.openBookGUI(itemStackIn);
            }
        }
        usedOnEntity = false;

        return new InteractionResultHolder(InteractionResult.PASS, itemStackIn);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("item.unusualprehistory.encyclopedia.desc"));
    }
}