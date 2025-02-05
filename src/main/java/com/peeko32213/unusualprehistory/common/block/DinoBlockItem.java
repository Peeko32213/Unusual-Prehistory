//package com.peeko32213.unusualprehistory.common.block;
//
//import com.peeko32213.unusualprehistory.common.entity.eggs.DinosaurLandEgg;
//import net.minecraft.network.chat.Component;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.InteractionResult;
//import net.minecraft.world.InteractionResultHolder;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.BlockItem;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.ItemUtils;
//import net.minecraft.world.item.context.BlockPlaceContext;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.Block;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Supplier;
//
//public class DinoBlockItem extends BlockItem {
//    private static final Map<Supplier<? extends EntityType<?>>, ItemStack> TO_REPLACE = new HashMap<>();
//    public DinoBlockItem(Block pBlock, Properties pProperties) {
//        super(pBlock, pProperties);
//    }
//
//    @Override
//    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
//        Player player = pPlayer;
//        ItemStack itemstack = player.getItemInHand(pUsedHand);
//        BlockDinosaurLandEggs dinosaur = (BlockDinosaurLandEggs) getBlock();
//        ItemStack item = TO_REPLACE.get(dinosaur.getDinosaur());
//        if(item == null) return sendMessage(player, true, itemstack);
//        ItemStack itemstack2 = ItemUtils.createFilledResult(itemstack, player, item);
//        if(itemstack2 == null ) return sendMessage(player, true, itemstack);
//        if(itemstack2.isEmpty()) return sendMessage(player, true, itemstack);
//        sendMessage(player, false, itemstack);
//        return InteractionResultHolder.consume(itemstack2);
//    }
//
//    @Override
//    public InteractionResult place(BlockPlaceContext pContext) {
//        Player player = pContext.getPlayer();
//        ItemStack itemstack = pContext.getItemInHand();
//        BlockDinosaurLandEggs dinosaur = (BlockDinosaurLandEggs) getBlock();
//        ItemStack item = TO_REPLACE.get(dinosaur.getDinosaur());
//        if(item == null) return sendMessage(player, true);
//        ItemStack itemstack2 = ItemUtils.createFilledResult(itemstack, player, item);
//        if(itemstack2 == null ) return sendMessage(player, true);
//        if(itemstack2.isEmpty()) return sendMessage(player, true);
//        sendMessage(player, false, itemstack);
//        return InteractionResult.CONSUME;
//    }
//
//
//    public InteractionResult sendMessage(Player player, boolean fail) {
//        if(!fail) {
//            player.sendSystemMessage(Component.literal("Your item has been replaced with the new version!"));
//            return InteractionResult.CONSUME;
//        }
//        player.sendSystemMessage(Component.literal("Your item could not be replaced!"));
//        return InteractionResult.FAIL;
//    }
//
//    public InteractionResultHolder sendMessage(Player player, boolean fail, ItemStack itemStack) {
//        if(!fail) {
//            player.sendSystemMessage(Component.literal("Your item has been replaced with the new version!"));
//            return InteractionResultHolder.fail(itemStack);
//        }
//        player.sendSystemMessage(Component.literal("Your item could not be replaced!"));
//        return InteractionResultHolder.fail(itemStack);
//    }
//
//    public static void registerReplacementItem(Supplier<? extends EntityType<?>> dinosaur, ItemStack itemStack) {
//        TO_REPLACE.put(dinosaur, itemStack);
//    }
//}
