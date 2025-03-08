package com.peeko32213.unusualprehistory.common.item;

import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.MegatheriumEntity;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;

public class DinoPouchItem extends Item  {
    public DinoPouchItem(Properties pProperties) {
        super(pProperties);
    }
    //For some reason this wasnt triggering,so for some godly reason I have to trigger it using the ServerEvnt.java......

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (pInteractionTarget instanceof MegatheriumEntity entityBabyMegatherium && entityBabyMegatherium.isBaby() && !pPlayer.level().isClientSide) {
            ItemStack slotPouch = UPItems.SLOTH_POUCH_ARMOR.get().getDefaultInstance();
            CompoundTag tag = slotPouch.getTag();
            Level level = pPlayer.level();
            tag.put("megatherium", entityBabyMegatherium.serializeNBT());
            tag.putBoolean("tamed", entityBabyMegatherium.isTame());
            long currentTime = level.getGameTime();
            tag.putLong("gameTime", currentTime);
            slotPouch.setTag(tag);
            entityBabyMegatherium.discard();
            ItemStack itemstack2 = ItemUtils.createFilledResult(pStack, pPlayer,slotPouch);
            if(itemstack2.getItem().getDefaultInstance().is(UPItems.SLOTH_POUCH_ARMOR.get())){
                boolean canPlace = pPlayer.getInventory().add(itemstack2);
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return InteractionResult.FAIL;
    }
}