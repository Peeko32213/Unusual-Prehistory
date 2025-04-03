package com.peeko32213.unusualprehistory.common.item;

import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TarbloodPrionNeedleItem extends Item {

    public TarbloodPrionNeedleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (!pInteractionTarget.hasEffect(UPEffects.TARBLOOD_PRION.get())) {
            pInteractionTarget.addEffect(new MobEffectInstance(UPEffects.TARBLOOD_PRION.get(), 120000));
            pStack.shrink(1);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pPlayer.hasEffect(UPEffects.TARBLOOD_PRION.get())) {
            pPlayer.addEffect(new MobEffectInstance(UPEffects.TARBLOOD_PRION.get(), 120000));
            pPlayer.getItemInHand(pUsedHand).shrink(1);
        }

        return super.use(pLevel, pPlayer, pUsedHand);
        //Needle applies vaccine and then returns the quill, same with player
    }

}
