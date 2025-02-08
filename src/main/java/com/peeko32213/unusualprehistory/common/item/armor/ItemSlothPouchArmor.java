package com.peeko32213.unusualprehistory.common.item.armor;

import com.peeko32213.unusualprehistory.client.render.armor.SlothPouchArmorRenderer;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.EntityBabyMegatherium;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class ItemSlothPouchArmor extends ArmorItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int tameTime;
    /**
     * @param tameTime time it takes to tame the entity in ticks. 1200 ticks is one minute.
     * **/
    public ItemSlothPouchArmor(ArmorMaterial material, ArmorItem.Type slot, int tameTime, Properties settings) {
        super(material, slot, settings);
        this.tameTime = tameTime;
    }
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new SlothPouchArmorRenderer();

                // This prepares our GeoArmorRenderer for the current render frame.
                // These parameters may be null however, so we don't do anything further with them
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return this.renderer;
            }
        });
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
                    babyMegatherium.setOwnerUUID(pPlayer.getUUID());
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
                MutableComponent component = Component.translatable("unusualprehistory.megatherium_baby.tame_tooltip", tag.getBoolean("tamed")).withStyle(ChatFormatting.GRAY);
                pTooltipComponents.add(component);
            } else {
                MutableComponent component = Component.translatable("unusualprehistory.megatherium_baby.minutes_tooltip", minutesLeft).withStyle(ChatFormatting.GRAY);
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
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
