package com.peeko32213.unusualprehistory.common.item;

import com.peeko32213.unusualprehistory.common.entity.BarinasuchusEntity;
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
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class MusicalTameItem extends Item {

    private final Supplier<? extends EntityType<?>> toTame;
    private final TagKey<Instrument> instrument;

    public MusicalTameItem(Properties pProperties, Supplier<? extends EntityType<?>> toTame, TagKey<Instrument> instrument) {
        super(pProperties);
        this.toTame = toTame;
        this.instrument = instrument;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        Optional<? extends Holder<Instrument>> optional = this.getInstrument(itemstack);
        if (optional.isPresent()) {
            Instrument instrument = optional.get().value();
            pPlayer.startUsingItem(pUsedHand);
            play(pLevel, pPlayer, instrument);
            pPlayer.getCooldowns().addCooldown(this, 120);
            pPlayer.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.consume(itemstack);
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
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
        compoundtag.putString("instrument", pSoundVariantId.unwrapKey().orElseThrow(() -> new IllegalStateException("Invalid instrument")).location().toString());
    }

    // TODO add tame/fail tame particles where the barinasuchus is and make the tame chance random, remove action bar message in favor of particle feedback
    @Override
    public void releaseUsing(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity, int pTimeCharged) {
        if (pLivingEntity instanceof ServerPlayer player) {
            LivingEntity toTame = (LivingEntity) this.toTame.get().create(pLevel);
            List<TamableAnimal> toTameList = pLevel.getEntitiesOfClass(TamableAnimal.class, player.getBoundingBox().inflate(10), EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(entity -> entity.getType() == toTame.getType() && !((TamableAnimal) entity).isTame()));
            toTameList.sort(Comparator.comparingDouble(player::distanceToSqr));
            if (!toTameList.isEmpty()) {
                toTameList.get(0).tame(player);

                MutableComponent mutableComponentTame = Component.translatable(toTame.getType().getDescriptionId()).withStyle(ChatFormatting.GREEN);

                if (toTameList.get(0).isTame()) {
                    if(toTameList.get(0) instanceof BarinasuchusEntity) {
                        UPAdvancementTriggerRegistry.BARINA_TRIGGER.trigger(player, player.blockPosition(), this.getDefaultInstance());
                    }
                    player.displayClientMessage(Component.translatable("unusualprehistory.musical_tame.tame", mutableComponentTame).withStyle(ChatFormatting.GREEN), true);
                }
            }
            else {
                return;
            }
            player.awardStat(Stats.ITEM_USED.get(this));
        }
    }

    public int getUseDuration(@NotNull ItemStack pStack) {
        Optional<? extends Holder<Instrument>> optional = this.getInstrument(pStack);
        return optional.map((p_248418_) -> p_248418_.value().useDuration()).orElse(0);
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

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return UseAnim.TOOT_HORN;
    }
}