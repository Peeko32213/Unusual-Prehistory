package com.peeko32213.unusualprehistory.common.item;

import com.peeko32213.unusualprehistory.common.capabilities.UPAnimalCapability;
import com.peeko32213.unusualprehistory.common.capabilities.UPCapabilities;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

import java.util.function.Supplier;

public class AnimalAttacherItem extends Item {


    private TagKey<EntityType<?>> attachTo;
    private Supplier<? extends EntityType> toAttach;
    private int timer;

    public AnimalAttacherItem(Properties pProperties, TagKey<EntityType<?>> attachTo, Supplier<? extends EntityType> toAttach, int timer) {
        super(pProperties);
        this.attachTo = attachTo;
        this.toAttach = toAttach;
        this.timer = timer;
    }


    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (attachTo == null || toAttach == null) return InteractionResult.FAIL;
        if (pInteractionTarget instanceof Animal animal) {
            if (animal.getType().is(attachTo) && !animal.isBaby()) {
                if (pPlayer.getLevel().isClientSide()) return InteractionResult.PASS;
                LazyOptional<UPAnimalCapability> animalCap = animal.getCapability(UPCapabilities.ANIMAL_CAPABILITY);
                ServerLevel serverLevel = (ServerLevel) pPlayer.getLevel();
                animalCap.ifPresent(capability -> {
                    if (capability.getEmbryoAnimal() == null || capability.getEmbryoAnimal().equals("")) {
                        LivingEntity livingEntity = (LivingEntity) toAttach.get().create(serverLevel);
                        capability.setEmbryoAnimal(livingEntity.getEncodeId());
                        capability.setTimer(this.timer);
                        if (!pPlayer.isCreative()) {
                            pStack.shrink(1);
                        }
                        MutableComponent animalComponent = Component.translatable(pInteractionTarget.getType().getDescriptionId()).withStyle(ChatFormatting.GOLD);
                        pPlayer.sendSystemMessage(Component.translatable("unusualprehistory.attacher.embryo_attached", animalComponent).withStyle(ChatFormatting.GREEN));

                    } else {
                        MutableComponent animalComponent = Component.translatable(pInteractionTarget.getType().getDescriptionId()).withStyle(ChatFormatting.GOLD);
                        pPlayer.sendSystemMessage(Component.translatable("unusualprehistory.attacher.animal_has_embryo", animalComponent).withStyle(ChatFormatting.RED));

                    }
                });
            }
        }

        if (pInteractionTarget instanceof WaterAnimal waterAnimal) {
            if (waterAnimal.getType().is(attachTo) && !waterAnimal.isBaby()) {
                if (pPlayer.getLevel().isClientSide()) return InteractionResult.PASS;
                LazyOptional<UPAnimalCapability> animalCap = waterAnimal.getCapability(UPCapabilities.ANIMAL_CAPABILITY);
                ServerLevel serverLevel = (ServerLevel) pPlayer.getLevel();
                animalCap.ifPresent(capability -> {
                    if (capability.getEmbryoAnimal() == null || capability.getEmbryoAnimal().equals("")) {
                        LivingEntity livingEntity = (LivingEntity) toAttach.get().create(serverLevel);
                        capability.setEmbryoAnimal(livingEntity.getEncodeId());
                        capability.setTimer(this.timer);
                        if (!pPlayer.isCreative()) {
                            pStack.shrink(1);
                        }
                        MutableComponent animalComponent = Component.translatable(pInteractionTarget.getType().getDescriptionId()).withStyle(ChatFormatting.GOLD);
                        pPlayer.sendSystemMessage(Component.translatable("unusualprehistory.attacher.embryo_attached", animalComponent).withStyle(ChatFormatting.GREEN));

                    } else {
                        MutableComponent animalComponent = Component.translatable(pInteractionTarget.getType().getDescriptionId()).withStyle(ChatFormatting.GOLD);
                        pPlayer.sendSystemMessage(Component.translatable("unusualprehistory.attacher.animal_has_embryo", animalComponent).withStyle(ChatFormatting.RED));

                    }
                });
            } else {
                MutableComponent animalComponent = Component.translatable(pInteractionTarget.getType().getDescriptionId()).withStyle(ChatFormatting.GOLD);
                MutableComponent toAttachComponent = Component.translatable(toAttach.get().getDescriptionId()).withStyle(ChatFormatting.GOLD);
                pPlayer.sendSystemMessage(Component.translatable("unusualprehistory.attacher.animal_not_correct", toAttachComponent, animalComponent).withStyle(ChatFormatting.RED));
                return InteractionResult.FAIL;
            }
        }
            return InteractionResult.SUCCESS;
        }

    }

