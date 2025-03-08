package com.peeko32213.unusualprehistory.common.item;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;

import java.util.List;

public class RampageRemedyItem extends Item {

    public RampageRemedyItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        pInteractionTarget.addEffect(new MobEffectInstance(UPEffects.RABIES_VACCINE.get(), -1));
        pStack.shrink(1);
        pPlayer.addItem(new ItemStack(UPItems.PSITTACOSAURUS_QUILL.get(), 1));
        return InteractionResult.SUCCESS;
        //Needle applies vaccine and then returns the quill
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.addEffect(new MobEffectInstance(UPEffects.RABIES_VACCINE.get(), -1));
        pPlayer.getItemInHand(pUsedHand).shrink(1);
        pPlayer.addItem(new ItemStack(UPItems.PSITTACOSAURUS_QUILL.get(), 1));
        return super.use(pLevel, pPlayer, pUsedHand);
        //Needle applies vaccine and then returns the quill, same with player
    }
}
