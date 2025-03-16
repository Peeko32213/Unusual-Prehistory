package com.peeko32213.unusualprehistory.common.item.projectile;

import com.peeko32213.unusualprehistory.common.entity.projectile.RabiesFlaskEntity;
import com.peeko32213.unusualprehistory.common.item.UPDrinkItem;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;

public class RabiesFlaskItem extends UPDrinkItem {

    public RabiesFlaskItem(Properties properties, boolean hasPotionEffectTooltip, boolean hasCustomTooltip) {
        super(properties, hasPotionEffectTooltip, hasCustomTooltip);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (pPlayer.isShiftKeyDown()) {
            pPlayer.swing(InteractionHand.MAIN_HAND);
            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SPLASH_POTION_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));

            if (!pLevel.isClientSide) {
                RabiesFlaskEntity jar = new RabiesFlaskEntity(pLevel, pPlayer);
                jar.setItem(itemstack);
                jar.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 0.5F, 0.2F);
                pLevel.addFreshEntity(jar);
            }

            pPlayer.awardStat(Stats.ITEM_USED.get(this));
            if (!pPlayer.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            return InteractionResultHolder.fail(itemstack);

        } else {
            if (!pPlayer.isShiftKeyDown()) {
                pPlayer.startUsingItem(pHand);
                return InteractionResultHolder.consume(itemstack);
            } else {
                return InteractionResultHolder.fail(itemstack);
            }

        }

    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity consumer) {
            consumer.addEffect(new MobEffectInstance(UPEffects.YIXIAN_RAMPAGE.get(), -1));
            return super.finishUsingItem(stack, level, consumer);
    }

}