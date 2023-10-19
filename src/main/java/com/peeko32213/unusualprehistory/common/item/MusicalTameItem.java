package com.peeko32213.unusualprehistory.common.item;

import com.peeko32213.unusualprehistory.common.entity.EntityBarinasuchus;
import com.peeko32213.unusualprehistory.core.registry.UPAdvancementTriggerRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class MusicalTameItem extends Item {

    private Supplier<? extends EntityType<?>> toTame;
    private TagKey<Instrument> instrument;

    public MusicalTameItem(Properties pProperties, Supplier<? extends EntityType<?>> toTame, TagKey<Instrument> instrument) {
        super(pProperties);
        this.toTame = toTame;
        this.instrument = instrument;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        Optional<? extends Holder<Instrument>> optional = this.getInstrument(itemstack);
        if (optional.isPresent()) {
            Instrument instrument = optional.get().value();
            pPlayer.startUsingItem(pUsedHand);
            play(pLevel, pPlayer, instrument);
            pPlayer.getCooldowns().addCooldown(this, instrument.useDuration());
            pPlayer.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.consume(itemstack);
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        Optional<ResourceKey<Instrument>> optional = this.getInstrument(pStack).flatMap(Holder::unwrapKey);
        if (optional.isPresent()) {
            MutableComponent mutablecomponent = Component.translatable(Util.makeDescriptionId("instrument", optional.get().location()));
            pTooltipComponents.add(mutablecomponent.withStyle(ChatFormatting.GRAY));
        }

    }

    public static ItemStack create(Item pItem, Holder<Instrument> pInstrument) {
        ItemStack itemstack = new ItemStack(pItem);
        setSoundVariantId(itemstack, pInstrument);
        return itemstack;
    }

    private static void setSoundVariantId(ItemStack pStack, Holder<Instrument> pSoundVariantId) {
        CompoundTag compoundtag = pStack.getOrCreateTag();
        compoundtag.putString("instrument", pSoundVariantId.unwrapKey().orElseThrow(() -> {
            return new IllegalStateException("Invalid instrument");
        }).location().toString());
    }


    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (pLivingEntity instanceof ServerPlayer player) {
            int i = this.getUseDuration(pStack) - pTimeCharged;
            if (i < 80) {
                player.sendSystemMessage(Component.translatable("unusualprehistory.musical_tame.too_early").withStyle(ChatFormatting.GRAY));
                return;
            }
            LivingEntity toTame = (LivingEntity) this.toTame.get().create(pLevel);
            List<TamableAnimal> toTameList = pLevel.getEntitiesOfClass(TamableAnimal.class, player.getBoundingBox().inflate(10), EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(entity -> entity.getType() == toTame.getType() && !((TamableAnimal) entity).isTame()));
            toTameList.sort(Comparator.comparingDouble(player::distanceToSqr));
            if (!toTameList.isEmpty()) {
                toTameList.get(0).tame(player);
                MutableComponent mutableComponent = Component.translatable(toTame.getType().getDescriptionId()).withStyle(ChatFormatting.GOLD);

                if (toTameList.get(0).isTame()) {
                    //TODO even though this is a general class it will only trigger for barina for now
                    if(toTameList.get(0) instanceof EntityBarinasuchus) {
                        UPAdvancementTriggerRegistry.BARINA_TRIGGER.trigger(player, this.getDefaultInstance());
                    }
                    player.sendSystemMessage(Component.translatable("unusualprehistory.musical_tame.tame", mutableComponent).withStyle(ChatFormatting.GREEN));
                } else {
                    player.sendSystemMessage(Component.translatable("unusualprehistory.musical_tame.fail_tame", mutableComponent).withStyle(ChatFormatting.RED));
                }
            } else {
                player.sendSystemMessage(Component.translatable("unusualprehistory.musical_tame.no_entity_found").withStyle(ChatFormatting.GRAY));
            }

            player.awardStat(Stats.ITEM_USED.get(this));

        }
    }

    public int getUseDuration(ItemStack pStack) {
        Optional<? extends Holder<Instrument>> optional = this.getInstrument(pStack);
        return optional.map((p_248418_) -> {
            return p_248418_.value().useDuration();
        }).orElse(0);
    }

    private Optional<? extends Holder<Instrument>> getInstrument(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTag();
        if (compoundtag != null && compoundtag.contains("instrument", 8)) {
            ResourceLocation resourcelocation = ResourceLocation.tryParse(compoundtag.getString("instrument"));
            if (resourcelocation != null) {
                return BuiltInRegistries.INSTRUMENT.getHolder(ResourceKey.create(Registries.INSTRUMENT, resourcelocation));
            }
        }

        Iterator<Holder<Instrument>> iterator = BuiltInRegistries.INSTRUMENT.getTagOrEmpty(this.instrument).iterator();
        return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.empty();
    }

    private static void play(Level pLevel, Player pPlayer, Instrument pInstrument) {
        SoundEvent soundevent = pInstrument.soundEvent().value();
        float f = pInstrument.range() / 16.0F;
        pLevel.playSound(pPlayer, pPlayer, soundevent, SoundSource.RECORDS, f, 1.0F);
        pLevel.gameEvent(GameEvent.INSTRUMENT_PLAY, pPlayer.position(), GameEvent.Context.of(pPlayer));
    }

    //@Override
    //public UseAnim getUseAnimation(ItemStack pStack) {
    //    return UseAnim.TOOT_HORN;
    //}
}
