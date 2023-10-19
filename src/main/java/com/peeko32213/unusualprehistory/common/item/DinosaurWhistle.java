package com.peeko32213.unusualprehistory.common.item;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ShriekParticleOption;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;


public class DinosaurWhistle extends Item {

    private Supplier<? extends EntityType> toWhistle;

    private final int MAX_DISTANCE = 64;

    public DinosaurWhistle(Properties pProperties,  Supplier<? extends EntityType> toWhistle) {
        super(pProperties);

        this.toWhistle = toWhistle;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if(itemStack.hasTag()) {
            CompoundTag tag = itemStack.getTag();
            int currentCommand = tag.getInt("command");
            if (pPlayer.isShiftKeyDown()) {

                if (currentCommand + 1 >= 2) {
                    tag.putInt("command", 0);
                } else {
                    tag.putInt("command", currentCommand + 1);
                }
                int currentCommand2 = tag.getInt("command");

                MutableComponent component = Component.literal(String.valueOf(currentCommand2)).withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC);
                pPlayer.sendSystemMessage(Component.translatable(UnusualPrehistory.MODID + ".barina_whistle.command_" + currentCommand2).withStyle(ChatFormatting.BLACK));
                return InteractionResultHolder.pass(itemStack);
            }

            LivingEntity livingEntity = (LivingEntity) toWhistle.get().create(pLevel);
            List<Entity> toWhistleList = pLevel.getEntities(livingEntity, pPlayer.getBoundingBox().inflate(10), EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(entity -> entity.getType() == toWhistle.get()));

            if (currentCommand == 0)
                performMoveCommand(pLevel, pPlayer, toWhistleList, itemStack);
            if (currentCommand == 1)
                performAttackCommand(pLevel, pPlayer, toWhistleList, itemStack);

        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public InteractionResultHolder<ItemStack> performMoveCommand(Level level, Player player, List<Entity> toWhistleList, ItemStack itemStack){
            BlockPos blockPlayerIsLookingAt = level.clip(new ClipContext(player.getEyePosition(1f),
                    (player.getEyePosition(1f).add(player.getViewVector(1f).scale(MAX_DISTANCE))),
                    ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player)).getBlockPos();

            toWhistleList.forEach(dino -> {
                ((Mob)dino).setTarget(null);
                ((Mob)dino).getNavigation().moveTo(blockPlayerIsLookingAt.getX(), blockPlayerIsLookingAt.getY(), blockPlayerIsLookingAt.getZ(), 2);
            });
            return InteractionResultHolder.success(itemStack);
    }

    public InteractionResultHolder<ItemStack> performAttackCommand(Level level, Player player, List<Entity> toWhistleList, ItemStack itemStack){
            BlockPos blockPlayerIsLookingAt = level.clip(new ClipContext(player.getEyePosition(1f),
                    (player.getEyePosition(1f).add(player.getViewVector(1f).scale(MAX_DISTANCE))),
                    ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player)).getBlockPos();


            List<LivingEntity> enemies = level.getEntitiesOfClass(LivingEntity.class, new AABB(blockPlayerIsLookingAt).inflate(10, 10, 10), EntitySelector.NO_CREATIVE_OR_SPECTATOR)
                    .stream()
                    .filter(e -> !toWhistleList.contains(e))
                    .collect(Collectors.toList());

            toWhistleList.forEach(dino -> {
                enemies.sort(Comparator.comparingDouble(dino::distanceToSqr));
                if (!enemies.isEmpty()) {
                    ((Mob) dino).setTarget(enemies.get(0));
                    enemies.remove(0);
                }
            });
            //if(pLevel instanceof ServerLevel serverLevel){
            //    serverLevel.sendParticles(ParticleTypes.SCULK_SOUL, (double)blockPlayerIsLookingAt.getX() + 0.5D, (double)blockPlayerIsLookingAt.getY() + 1.15D, (double)blockPlayerIsLookingAt.getZ() + 0.5D, 2, 0.2D, 0.0D, 0.2D, 0.0D);

            //}

            for(int j = 0; j < 10; ++j) {
                level.addParticle(new ShriekParticleOption(j * 10), true, (double) blockPlayerIsLookingAt.getX() + 0.5D, (double) blockPlayerIsLookingAt.getY() + 0.3, (double) blockPlayerIsLookingAt.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
            }
            return InteractionResultHolder.success(itemStack);
    }

}
