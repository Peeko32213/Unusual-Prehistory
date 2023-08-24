package com.peeko32213.unusualprehistory.common.block;

import com.peeko32213.unusualprehistory.common.block.entity.FruitLootBoxEntity;
import com.peeko32213.unusualprehistory.common.data.ItemWeightedPairCodec;
import com.peeko32213.unusualprehistory.common.data.LootFruitCodec;
import com.peeko32213.unusualprehistory.common.data.LootFruitJsonManager;
import com.peeko32213.unusualprehistory.common.data.RollableItemCodec;
import com.peeko32213.unusualprehistory.core.registry.UPBlockEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockFruitLootBox extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final IntegerProperty LOOT_BOX = IntegerProperty.create("loot_box", 1, 5);
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16D, 16.0D);

    public static final Logger LOGGER = LogManager.getLogger();
    public BlockFruitLootBox(Properties pProperties) {
        super(pProperties);
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return pFacing == Direction.DOWN && !this.canSurvive(pState, pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        ItemStack itemStack = new ItemStack(this);
        if(player.level.isClientSide) {
            //ServerLevel serverLevel = (ServerLevel) player.level;
            CompoundTag tag = new CompoundTag();
            FruitLootBoxEntity fruitLootBox = ((FruitLootBoxEntity) level.getBlockEntity(pos));

            String translationKey = fruitLootBox.getTranslationKey();
            int color = fruitLootBox.getColor();
            Item tradeItem = fruitLootBox.getTradeItem();
            int modelData = fruitLootBox.getCustomModelData();
            tag.putString("translationKey", translationKey);
            tag.putInt("color", color);
            tag.putInt("CustomModelData", modelData);
            tag.put("tradeItem", tradeItem.getDefaultInstance().serializeNBT());
            itemStack.setTag(tag);
        }
        return itemStack;
    }

    @Override
    public void playerDestroy(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState, @Nullable BlockEntity pBlockEntity, ItemStack pTool) {
        //FruitLootBoxEntity fruitLootBox = ((FruitLootBoxEntity)pLevel.getBlockEntity(pPos));
        //Item item = Item.byBlock(fruitLootBox.getBlockState().getBlock());
        //CompoundTag tag = item.getDefaultInstance().getTag();
        //if(tag != null) {
        //    item.getDefaultInstance().getOrCreateTag().putInt("color", fruitLootBox.getColor());
//
        //}
        super.playerDestroy(pLevel, pPlayer, pPos, pState, pBlockEntity, pTool);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        pLevel.sendBlockUpdated(pPos, pState, pState,Block.UPDATE_IMMEDIATE);
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if(pLevel.isClientSide) return;
        FruitLootBoxEntity fruitLootBox = ((FruitLootBoxEntity)pLevel.getBlockEntity(pPos));
        CompoundTag tag = pStack.getTag();
        if(tag != null) {
            fruitLootBox.setColor(tag.getInt("color"));
            Item tradeItem = ItemStack.of(tag.getCompound("tradeItem")).getItem();
            fruitLootBox.setTradeItem(tradeItem);
            if (LootFruitJsonManager.getLoot(tradeItem, null) == null) return;
            fruitLootBox.setColor(LootFruitJsonManager.getLoot(tradeItem, null).get(0).getColor().getValue());
            fruitLootBox.setLootFruits(LootFruitJsonManager.getLoot(tradeItem, null));
            int modelData = tag.getInt("CustomModelData");
            fruitLootBox.setCustomModelData(modelData);
            fruitLootBox.setTranslationKey(tag.getString("translationKey"));
            pLevel.setBlockAndUpdate(pPos, pState.setValue(LOOT_BOX, modelData));
            pLevel.sendBlockUpdated(pPos, pState, pState,Block.UPDATE_IMMEDIATE);
            super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        }
    }

    public static float getCustomModelData(CompoundTag tag){
        boolean flag = tag.contains("CustomModelData");
        if(flag){
            float modelData = tag.getInt("CustomModelData");
            return modelData;
        }
        return 0F;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide) {
            FruitLootBoxEntity fruitLootBox = ((FruitLootBoxEntity) pLevel.getBlockEntity(pPos));
            ItemStack itemStack = pPlayer.getItemInHand(pHand);
            try {
                int size = fruitLootBox.getLootFruits().size();
                if (!fruitLootBox.getLootFruits().isEmpty() && !pLevel.isClientSide) {
                    int randomNr = pLevel.random.nextInt(size);
                    fruitLootBox.getLootFruits().get(randomNr).getItems().forEach(rollableItemCodec -> {
                        rollableItemCodec.dropItem(pLevel, pPos, rollableItemCodec);
                    });
                }
                pLevel.destroyBlock(pPos, false, pPlayer);
                return InteractionResult.SUCCESS;
            } catch (Exception e) {
                LOGGER.info("failed due to ", e);
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    private static final LootFruitCodec LOOT_FRUIT = new LootFruitCodec(2, "unusualprehistory.loot_fruit_box.default", Items.BAMBOO, Collections.emptyList(), TextColor.fromRgb(12345), 2);
    private static final List<LootFruitCodec> LOOT_FRUIT_LIST = new ArrayList<>() {{
        add(LOOT_FRUIT);
    }};
    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
            ItemStack istack = new ItemStack(this);
            boolean isEmpty = LootFruitJsonManager.getTierTrades().isEmpty();
            if(!isEmpty) {
                int lowestKey = Collections.min(LootFruitJsonManager.getTierTrades().keySet());
                List<LootFruitCodec> lootFruitItem= LootFruitJsonManager.getTierTrades().getOrDefault(lowestKey, LOOT_FRUIT_LIST);
                CompoundTag lootFruitTag = istack.getOrCreateTag();
                int color = lootFruitItem.get(0).getColor().getValue();
                lootFruitTag.putString("translationKey", lootFruitItem.get(0).getTranslationKey());
                lootFruitTag.putInt("color", color);
                lootFruitTag.put("tradeItem", lootFruitItem.get(0).getTradeItem().getDefaultInstance().serializeNBT());
                lootFruitTag.putInt("CustomModelData", lootFruitItem.get(0).getCustomModelData());
                istack.setTag(lootFruitTag);
                list.add(istack);
            } else {
                List<ItemWeightedPairCodec> itemWeightedPairCodecs = new ArrayList<>();
                itemWeightedPairCodecs.add(new ItemWeightedPairCodec(UPItems.PALEO_FOSSIL.get(), 100, 1));
                List<RollableItemCodec> rollableItemCodecs =  new ArrayList<>();
                rollableItemCodecs.add(new RollableItemCodec(1, itemWeightedPairCodecs));
                LootFruitCodec lootFruitCodec = LOOT_FRUIT;
                CompoundTag lootFruitTag = istack.getOrCreateTag();
                if(lootFruitCodec == null) return;
                int color = lootFruitCodec.getColor().getValue();
                lootFruitTag.putString("translationKey", lootFruitCodec.getTranslationKey());
                lootFruitTag.putInt("color", color);
                lootFruitTag.putInt("CustomModelData", lootFruitCodec.getCustomModelData());
                lootFruitTag.put("tradeItem", lootFruitCodec.getTradeItem().getDefaultInstance().serializeNBT());
                istack.setTag(lootFruitTag);
                list.add(istack);
            }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return UPBlockEntities.FRUIT_LOOT_BOX_BLOCK_ENTITY.get().create(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LOOT_BOX);
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        if(pStack.hasTag()){
            CompoundTag tag = pStack.getTag();
            String translationKey = tag.getString("translationKey");
            ItemStack tradeItem = ItemStack.of(tag.getCompound("tradeItem"));
            int modelData = tag.getInt("CustomModelData");
            MutableComponent component = Component.translatable("unusualprehistory.fruit_loot_box.loot_box");
            if(modelData == 1){
                pStack.setHoverName(Component.translatable(translationKey).withStyle(ChatFormatting.WHITE));
            }
            if(modelData == 2){
                pStack.setHoverName(Component.translatable(translationKey).withStyle(ChatFormatting.WHITE));
            }
            if(modelData == 3){
                pStack.setHoverName(Component.translatable(translationKey).withStyle(ChatFormatting.YELLOW));
            }
            if(modelData == 4){
                pStack.setHoverName(Component.translatable(translationKey).withStyle(ChatFormatting.AQUA));
            }
            if(modelData == 5){
                pStack.setHoverName(Component.translatable(translationKey).withStyle(ChatFormatting.LIGHT_PURPLE));
            }


        }


    }
}
