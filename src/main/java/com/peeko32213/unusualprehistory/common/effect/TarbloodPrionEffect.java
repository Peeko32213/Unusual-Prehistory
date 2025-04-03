package com.peeko32213.unusualprehistory.common.effect;

import com.peeko32213.unusualprehistory.common.capabilities.UPCapabilities;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.blocks.UPBlocks;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TurtleEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static net.minecraft.world.level.block.MultifaceBlock.getFaceProperty;

public class TarbloodPrionEffect extends MobEffect {
    public TarbloodPrionEffect() {
        super(MobEffectCategory.HARMFUL, 6685988);
    }
    public static final UUID PRION_UUID = UUID.fromString("254b4a35-d1ed-4f34-abd1-910ac3525744");
    private int duration = -1;
    private final int color = 6685988;
    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {

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
                double blockRand = Math.random();
                int dmg = (int) (1*Math.random()+1);

                if (hitRand < 0.0001 * capability.playerTarbloodPrionTime && !serverPlayer.level().isClientSide()) {
                    serverPlayer.hurt(serverPlayer.damageSources().generic(), dmg);
                    serverPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40), serverPlayer);
                    //entities has a 1% chance to take damage every tick and also go blind for 2 ticks
                }
                if (blockRand < 0.0001 * capability.playerTarbloodPrionTime && (serverPlayer.level().getBlockState(serverPlayer.blockPosition()).canBeReplaced() || pLivingEntity.level().getBlockState(pLivingEntity.blockPosition()).isAir()) && pLivingEntity.level().getBlockState(pLivingEntity.blockPosition().below()).isSolid()) {
                    //entities trail tar
                    BlockState tar = UPBlocks.SPLATTERED_TAR.get().defaultBlockState().setValue(getFaceProperty(Direction.DOWN), Boolean.valueOf(true));
                    serverPlayer.level().setBlock(serverPlayer.blockPosition(), tar, 3);
                }

            });

        } else {
            pLivingEntity.getCapability(UPCapabilities.ANIMAL_CAPABILITY).ifPresent(capability -> {
                capability.animalTarbloodPrionTime += 1;

                double hitRand = Math.random();
                double blockRand = Math.random();
                int dmg = (int) (1*Math.random()+1);

                if (hitRand < 0.00001 * capability.animalTarbloodPrionTime && !pLivingEntity.level().isClientSide()) {
                    pLivingEntity.hurt(pLivingEntity.damageSources().generic(), dmg);
                    pLivingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40), pLivingEntity);
                    //entities has a 1% chance to take damage every tick and also go blind for 2 ticks
                }
                if (blockRand < 0.00001 * capability.animalTarbloodPrionTime && (pLivingEntity.level().getBlockState(pLivingEntity.blockPosition()).canBeReplaced() || pLivingEntity.level().getBlockState(pLivingEntity.blockPosition()).isAir()) && pLivingEntity.level().getBlockState(pLivingEntity.blockPosition().below()).isSolid()) {
                    //entities trail tar
                    BlockState tar = UPBlocks.SPLATTERED_TAR.get().defaultBlockState().setValue(getFaceProperty(Direction.DOWN), Boolean.valueOf(true));
                    pLivingEntity.level().setBlock(pLivingEntity.blockPosition(), tar, 3);
                }
                //slows the entity down, the speed scales off infection time
            });
        }

        Objects.requireNonNull(pLivingEntity.getAttributes().getInstance(Attributes.MOVEMENT_SPEED)).addTransientModifier(new AttributeModifier(PRION_UUID.toString(), -0.001, AttributeModifier.Operation.MULTIPLY_TOTAL));


        duration ++;
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        //btw this function is necessary for an effect to work

        return true;
        //effect will never expire
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return List.of();
    }


    public @NotNull String getDescriptionId() {
        return "unusualprehistory.potion.tarblood";
    }
}
