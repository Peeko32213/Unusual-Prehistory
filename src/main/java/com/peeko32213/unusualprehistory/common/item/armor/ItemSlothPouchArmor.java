package com.peeko32213.unusualprehistory.common.item.armor;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.EntityBabyMegatherium;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.List;
import java.util.UUID;

public class ItemSlothPouchArmor extends GeoArmorItem implements IAnimatable {

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private int tameTime;
    /**
     * @param tameTime time it takes to tame the entity in ticks. 1200 ticks is one minute.
     * **/
    public ItemSlothPouchArmor(ArmorMaterial material, EquipmentSlot slot, int tameTime, Properties settings) {
        super(material, slot, settings);
        this.tameTime = tameTime;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        CompoundTag tag = stack.getTag();
        if (tag.contains("megatherium") && pHand == InteractionHand.MAIN_HAND) {
            if(!pLevel.isClientSide) {
                EntityBabyMegatherium babyMegatherium = UPEntities.BABY_MEGATHERIUM.get().create(pLevel);
                babyMegatherium.deserializeNBT(tag.getCompound("megatherium"));
                babyMegatherium.setPos(pPlayer.position());

                if (tag.getBoolean("tamed") && !babyMegatherium.isTame()) {
                    babyMegatherium.tame(pPlayer);
                }
                babyMegatherium.setAge(-24000);
                babyMegatherium.setUUID(UUID.randomUUID());
                pLevel.addFreshEntity(babyMegatherium);
            }
            ItemStack itemStack1 = UPItems.DINO_POUCH.get().getDefaultInstance();
            ItemStack itemstack2 = ItemUtils.createFilledResult(stack, pPlayer, itemStack1);
            return InteractionResultHolder.sidedSuccess(itemstack2, pLevel.isClientSide());
        } else if(!pPlayer.isShiftKeyDown()){
            return super.use(pLevel, pPlayer, pHand);
        }
        return InteractionResultHolder.fail(stack);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pLevel.isClientSide || pSlotId != EquipmentSlot.CHEST.getIndex()) return;
        if (!pStack.hasTag() || pStack.getTag() == null || !pStack.getTag().contains("megatherium") || !pStack.getTag().contains("gameTime")) addTag(pStack, pLevel);
        CompoundTag tag = pStack.getTag();
        if (tag.contains("gameTime") && tag.contains("megatherium")) {
            long currentTime = pLevel.getGameTime();
            long timeToCheck = tag.getLong("gameTime");
            long checkTime = currentTime - tameTime; //72000 is one hour
            if (timeToCheck < checkTime) {
                tag.putBoolean("tamed", true);
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    public ItemStack addTag(ItemStack stack, Level level){
        CompoundTag tag = stack.getTag();
        EntityBabyMegatherium entityBabyMegatherium = UPEntities.BABY_MEGATHERIUM.get().create(level);
        tag.put("megatherium", entityBabyMegatherium.serializeNBT());
        tag.putBoolean("tamed", entityBabyMegatherium.isTame());
        long currentTime = level.getGameTime();
        tag.putLong("gameTime", currentTime);
        stack.setTag(tag);
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getTag();
        if (tag.contains("gameTime") && tag.contains("megatherium")) {
            long currentTime = pLevel.getGameTime();
            long timeToCheck = tag.getLong("gameTime");
            int minutesLeft = (int) Math.ceil((((tameTime - (currentTime - timeToCheck))) * 0.05F)/ 60F);
            if(minutesLeft < 0){
                minutesLeft = 0;
            }
            if (tag.getBoolean("tamed")) {
                MutableComponent component = Component.translatable("unusualprehistory.megatherium_baby.tame_tooltip", tag.getBoolean("tamed")).withStyle(ChatFormatting.AQUA);
                pTooltipComponents.add(component);
            } else {
                MutableComponent component = Component.translatable("unusualprehistory.megatherium_baby.minutes_tooltip", minutesLeft).withStyle(ChatFormatting.AQUA);
                pTooltipComponents.add(component);
            }
        }


        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void registerControllers(AnimationData data) {
    }
}
