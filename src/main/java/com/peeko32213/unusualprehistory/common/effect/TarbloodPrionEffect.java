package com.peeko32213.unusualprehistory.common.effect;

import com.peeko32213.unusualprehistory.common.capabilities.UPCapabilities;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.blocks.UPBlocks;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static net.minecraft.world.level.block.MultifaceBlock.getFaceProperty;

public class TarbloodPrionEffect extends MobEffect {

    private static final UUID TARBLOOD_UUID = UUID.fromString("420a7b73-9f67-4cee-847d-c773bc297d05");

    public TarbloodPrionEffect() {
        super(MobEffectCategory.HARMFUL, 0x121010);
    }

    private int duration = -1;

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        AttributeInstance attributeinstance = pLivingEntity.getAttribute(Attributes.MOVEMENT_SPEED);

//        if (attributeinstance != null) {
//            float levelScale = (1 + pAmplifier) * 1.5F;
//            float f = (-0.001F * levelScale);
//            removeTarbloodModifier(pLivingEntity);
//            attributeinstance.addTransientModifier(new AttributeModifier(TARBLOOD_UUID, "Tarblood movement penalty", -0.001, AttributeModifier.Operation.MULTIPLY_TOTAL));
//        }

        if (pLivingEntity.isDeadOrDying() && pLivingEntity.tickCount%5 == 0) {
            pLivingEntity.level().playSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), UPSounds.TAR_POP.get(), SoundSource.NEUTRAL, 0.75F, 1F / (pLivingEntity.level().getRandom().nextFloat() * 0.4F + 0.8F));
            pLivingEntity.level().playSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), UPSounds.TAR_POP.get(), SoundSource.NEUTRAL, 0.75F, 0.75F/ (pLivingEntity.level().getRandom().nextFloat() * 0.4F + 0.8F));
            //funny death sound
        }

        if (!pLivingEntity.level().isClientSide && pLivingEntity instanceof ServerPlayer serverPlayer) {
            serverPlayer.getCapability(UPCapabilities.PLAYER_CAPABILITY).ifPresent(capability -> {
                if (capability.playerTarbloodPrionTime >= 24000/4) {
                    serverPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, -1), serverPlayer);
                    //blinds player screen
                }

                capability.playerTarbloodPrionTime += 1;
                //increase tick
                //slows the player down, the speed scales off infection time

                double hitRand = Math.random();
                int dmg = (int) (1*Math.random()+1);

                if (hitRand < 0.00001 * capability.playerTarbloodPrionTime && !serverPlayer.level().isClientSide()) {
                    serverPlayer.hurt(serverPlayer.damageSources().generic(), dmg);
                    serverPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40), serverPlayer);
                    //entities has a 1% chance to take damage every tick and also go blind for 2 ticks
                    if ((serverPlayer.level().getBlockState(serverPlayer.blockPosition()).canBeReplaced() || serverPlayer.level().getBlockState(serverPlayer.blockPosition()).isAir()) && serverPlayer.level().getBlockState(serverPlayer.blockPosition().below()).isSolid()) {
                        BlockState tar = UPBlocks.SPLATTERED_TAR.get().defaultBlockState().setValue(getFaceProperty(Direction.DOWN), Boolean.valueOf(true));
                        serverPlayer.level().setBlock(serverPlayer.blockPosition(), tar, 3);
                        //entities trail tar
                    }
                }

            });

        } else {
            pLivingEntity.getCapability(UPCapabilities.ANIMAL_CAPABILITY).ifPresent(capability -> {
                capability.animalTarbloodPrionTime += 1;

                double hitRand = Math.random();
                int dmg = (int) (1*Math.random()+1);

                if (hitRand < 0.00001 * capability.animalTarbloodPrionTime && !pLivingEntity.level().isClientSide()) {
                    pLivingEntity.hurt(pLivingEntity.damageSources().generic(), dmg);
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40), pLivingEntity);
                    //entities has a 1% chance to take damage every tick and also go blind for 2 ticks
                    if ((pLivingEntity.level().getBlockState(pLivingEntity.blockPosition()).canBeReplaced() || pLivingEntity.level().getBlockState(pLivingEntity.blockPosition()).isAir()) && pLivingEntity.level().getBlockState(pLivingEntity.blockPosition().below()).isSolid()) {
                        BlockState tar = UPBlocks.SPLATTERED_TAR.get().defaultBlockState().setValue(getFaceProperty(Direction.DOWN), Boolean.valueOf(true));
                        pLivingEntity.level().setBlock(pLivingEntity.blockPosition(), tar, 3);
                        //entities trail tar
                    }
                }
                //slows the entity down, the speed scales off infection time
            });
        }
        Objects.requireNonNull(pLivingEntity.getAttributes().getInstance(Attributes.MOVEMENT_SPEED)).addTransientModifier(new AttributeModifier(TARBLOOD_UUID.toString(), -0.001, AttributeModifier.Operation.MULTIPLY_TOTAL));
        duration ++;
    }

    // todo: make attribute clear when effect is lost

    public void addAttributeModifiers(LivingEntity entity, AttributeMap map, int i) {
        super.addAttributeModifiers(entity, map, i);
    }

    public void removeAttributeModifiers(LivingEntity entity, AttributeMap map, int i) {
        super.removeAttributeModifiers(entity, map, i);
        removeTarbloodModifier(entity);
    }

    protected void removeTarbloodModifier(LivingEntity living) {
        AttributeInstance attributeinstance = living.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attributeinstance != null) {
            if (attributeinstance.getModifier(TARBLOOD_UUID) != null) {
                attributeinstance.removeModifier(TARBLOOD_UUID);
            }
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return List.of();
    }

    public @NotNull String getDescriptionId() {
        return "unusualprehistory.potion.tarblood";
    }
}
