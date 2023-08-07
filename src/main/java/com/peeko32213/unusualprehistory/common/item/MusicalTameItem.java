package com.peeko32213.unusualprehistory.common.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;

import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

public class MusicalTameItem extends Item {

    private Supplier<? extends EntityType> toTame;
    private Supplier<? extends SoundEvent> toPlay;
    private int duration;

    public MusicalTameItem(Properties pProperties, Supplier<? extends EntityType> toTame, Supplier<? extends SoundEvent> toPlay, int duration) {
        super(pProperties);
        this.toTame = toTame;
        this.toPlay = toPlay;
        this.duration = duration;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pLevel.playSound(null, pPlayer.blockPosition(), this.toPlay.get(), SoundSource.MUSIC, 1F, 1F);
        pPlayer.startUsingItem(pHand);
        return InteractionResultHolder.consume(itemstack);
    }


    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (pLivingEntity instanceof Player player) {

            int i = this.getUseDuration(pStack) - pTimeCharged;
            if (i > 0) return;
                LivingEntity toTame = (LivingEntity) this.toTame.get().create(pLevel);
                List<TamableAnimal> toTameList = pLevel.getEntitiesOfClass(TamableAnimal.class, player.getBoundingBox().inflate(10), EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(entity -> entity.getType() == toTame.getType()));
                toTameList.sort(Comparator.comparingDouble(player::distanceToSqr));
                if (!toTameList.isEmpty()) {
                    toTameList.get(0).tame(player);
                }
                player.awardStat(Stats.ITEM_USED.get(this));

        }
    }

    public int getUseDuration(ItemStack pStack) {
        return duration;
    }
}
