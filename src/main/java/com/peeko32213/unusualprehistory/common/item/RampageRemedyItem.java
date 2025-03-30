package com.peeko32213.unusualprehistory.common.item;

import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RampageRemedyItem extends Item {

    public RampageRemedyItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (!pInteractionTarget.hasEffect(UPEffects.RABIES_VACCINE.get())) {
            pInteractionTarget.addEffect(new MobEffectInstance(UPEffects.RABIES_VACCINE.get(), 1800));
            pInteractionTarget.removeEffect(UPEffects.YIXIAN_RAMPAGE.get());
            pStack.shrink(1);
            pPlayer.addItem(new ItemStack(UPItems.PSITTACOSAURUS_QUILL.get(), 1));
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
        //Needle applies vaccine and then returns the quill
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pPlayer.hasEffect(UPEffects.RABIES_VACCINE.get())) {
            pPlayer.addEffect(new MobEffectInstance(UPEffects.RABIES_VACCINE.get(), 1800));
            pPlayer.removeEffect(UPEffects.YIXIAN_RAMPAGE.get());
            pPlayer.getItemInHand(pUsedHand).shrink(1);
            pPlayer.addItem(new ItemStack(UPItems.PSITTACOSAURUS_QUILL.get(), 1));
        }

        return super.use(pLevel, pPlayer, pUsedHand);
        //Needle applies vaccine and then returns the quill, same with player
    }
}
