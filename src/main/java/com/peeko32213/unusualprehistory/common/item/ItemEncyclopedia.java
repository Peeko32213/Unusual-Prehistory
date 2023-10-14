package com.peeko32213.unusualprehistory.common.item;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

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
        if (playerIn.level().isClientSide && target.getEncodeId() != null && !target.getEncodeId().contains("minecraft:")) {
            usedOnEntity = true;

            String id = target.getEncodeId().split(":")[1];
            ResourceLocation resourceLocation = prefix("dinosaurs/"+id);


            UnusualPrehistory.PROXY.openBookGUI(resourceLocation);
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
                UnusualPrehistory.PROXY.openBookGUI(prefix("root"));
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