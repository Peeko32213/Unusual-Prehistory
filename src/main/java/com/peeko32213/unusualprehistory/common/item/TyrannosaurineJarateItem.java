package com.peeko32213.unusualprehistory.common.item;

import com.peeko32213.unusualprehistory.common.entity.msc.projectile.EntityOpalescentPearl;
import com.peeko32213.unusualprehistory.common.entity.msc.projectile.EntityTyrannosaurineJarate;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.level.Level;

public class TyrannosaurineJarateItem extends Item {

    public TyrannosaurineJarateItem(Properties pProperties) {
        super(pProperties);
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        //TODO: throws a bit farther than a normal potion, kinda like an AC brick
        //TODO: obtained from right clicking any tyrannosaur with a flask whilst standing behind it, 50% chance to piss it off

        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pLevel.playSound((Player)null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SPLASH_POTION_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        double perhaps = Math.random();
        if (perhaps <= 0.1) {
            pLevel.playSound((Player)null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), UPSounds.JARATE_SNIPER.get(), SoundSource.NEUTRAL, 0.5F, 1F);
        }

        if (!pLevel.isClientSide) {
            EntityTyrannosaurineJarate jar = new EntityTyrannosaurineJarate(pLevel, pPlayer);
            jar.setItem(itemstack);
            jar.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 0.5F, 0.2F);
            pLevel.addFreshEntity(jar);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    public Rarity getRarity(ItemStack pStack) {
        return Rarity.UNCOMMON;
        //epic rarity on default
    }
}
