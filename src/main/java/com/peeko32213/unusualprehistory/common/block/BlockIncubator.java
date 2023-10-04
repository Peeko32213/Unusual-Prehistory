package com.peeko32213.unusualprehistory.common.block;

import com.peeko32213.unusualprehistory.common.block.entity.IncubatorBlockEntity;
import com.peeko32213.unusualprehistory.common.message.SyncItemStackS2CPacket;
import com.peeko32213.unusualprehistory.common.recipe.IncubatorRecipe;
import com.peeko32213.unusualprehistory.core.registry.UPBlockEntities;
import com.peeko32213.unusualprehistory.core.registry.UPMessages;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class BlockIncubator extends BaseEntityBlock {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final BooleanProperty CRACKED = BooleanProperty.create("cracked");
    public BlockIncubator(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(CRACKED, false));
    }

    /* BLOCK ENTITY */
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide()) {
            IncubatorBlockEntity entity = (IncubatorBlockEntity)pLevel.getBlockEntity(pPos);
            List<ItemStack> container = IncubatorBlockEntity.getInventory(pLevel, pPos);
            if(!itemStack.isEmpty() && hasRecipe(pLevel, itemStack)){
                if(container.isEmpty()) {
                    ItemStack itemStack1 = itemStack.copy();
                    itemStack1.setCount(1);
                    container.add(itemStack1);
                    if(!pLevel.isClientSide){
                        UPMessages.sendToClients(new SyncItemStackS2CPacket(container, pPos));
                    }
                    if(!pPlayer.isCreative()) {
                        itemStack.shrink(1);
                    }
                    return InteractionResult.sidedSuccess(pLevel.isClientSide());
                } else {
                    return InteractionResult.FAIL;
                }
            } else {
                if(!container.isEmpty()){
                    entity.drops();
                    return InteractionResult.sidedSuccess(pLevel.isClientSide());
                } else {
                    return InteractionResult.FAIL;
                }
            }

        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }
    public static boolean hasRecipe(Level level, ItemStack itemStack) {
        SimpleContainer inventory = new SimpleContainer(1);
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            inventory.setItem(i, itemStack);
        }
        Optional<IncubatorRecipe> match = level.getRecipeManager()
                .getRecipeFor(IncubatorRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent();
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof IncubatorBlockEntity) {
                ((IncubatorBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }


    public BlockState rotate(BlockState p_52716_, Rotation p_52717_) {
        return p_52716_.setValue(FACING, p_52717_.rotate(p_52716_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_52713_, Mirror p_52714_) {
        return p_52713_.rotate(p_52714_.getRotation(p_52713_.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, CRACKED);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new IncubatorBlockEntity(pPos, pState);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
        return createTickerHelper(p_152182_, UPBlockEntities.INCUBATOR_BLOCK_ENTITY.get(), IncubatorBlockEntity::tick);
    }
}
