package com.peeko32213.unusualprehistory.common.block;

import com.peeko32213.unusualprehistory.common.block.entity.FruitLootBoxEntity;
import com.peeko32213.unusualprehistory.common.data.LootFruitCodec;
import com.peeko32213.unusualprehistory.common.data.LootFruitJsonManager;
import com.peeko32213.unusualprehistory.core.registry.UPBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class BlockFruitLootBox extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16D, 16.0D);

    public static final Logger LOGGER = LogManager.getLogger();
    public BlockFruitLootBox(Properties pProperties) {
        super(pProperties);
    }


    @javax.annotation.Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState blockstate = pContext.getLevel().getBlockState(pContext.getClickedPos());
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        boolean flag = fluidstate.getType().is( FluidTags.WATER);
        return super.getStateForPlacement(pContext).setValue(WATERLOGGED, Boolean.valueOf(flag));
    }
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return pFacing == Direction.DOWN && !this.canSurvive(pState, pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return canSupportCenter(pLevel, pPos.below(), Direction.UP);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        ItemStack itemStack = new ItemStack(this);
        FruitLootBoxEntity fruitLootBox = ((FruitLootBoxEntity)level.getBlockEntity(pos));
        itemStack.getOrCreateTag().putInt("color",fruitLootBox.getColor());

        return itemStack;
    }

    @Override
    public void playerDestroy(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState, @Nullable BlockEntity pBlockEntity, ItemStack pTool) {
        FruitLootBoxEntity fruitLootBox = ((FruitLootBoxEntity)pLevel.getBlockEntity(pPos));
        Item item = Item.byBlock(fruitLootBox.getBlockState().getBlock());
        item.getDefaultInstance().getOrCreateTag().putInt("color", fruitLootBox.getColor());
        super.playerDestroy(pLevel, pPlayer, pPos, pState, pBlockEntity, pTool);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        pLevel.sendBlockUpdated(pPos, pState, pState,Block.UPDATE_IMMEDIATE);
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        FruitLootBoxEntity fruitLootBox = ((FruitLootBoxEntity)pLevel.getBlockEntity(pPos));
        fruitLootBox.setColor(pStack.getOrCreateTag().getInt("color"));
        Item tradeItem = ItemStack.of(pStack.getOrCreateTag().getCompound("tradeItem")).getItem();
        fruitLootBox.setTradeItem(tradeItem);
        fruitLootBox.setColor(LootFruitJsonManager.getLoot(tradeItem, null).get(0).getColor().getValue());
        fruitLootBox.setLootFruits(LootFruitJsonManager.getLoot(tradeItem, null));
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        LOGGER.info("Level " + pLevel + "Color " + ((FruitLootBoxEntity) pLevel.getBlockEntity(pPos)).getColor());


        FruitLootBoxEntity fruitLootBox = ((FruitLootBoxEntity) pLevel.getBlockEntity(pPos));
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        LOGGER.info("showData " + itemStack.serializeNBT());

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
        } catch (Exception e){
            LOGGER.info("failed due to ", e);
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
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
        pBuilder.add(WATERLOGGED);
    }
}
