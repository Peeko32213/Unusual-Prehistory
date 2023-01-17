package com.peeko32213.unusualprehistory.common.block.entity;

import com.peeko32213.unusualprehistory.common.block.BlockDNAFridge;
import com.peeko32213.unusualprehistory.common.screen.DNAFridgeMenu;
import com.peeko32213.unusualprehistory.core.registry.UPBlockEntities;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class DNAFridgeBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    private NonNullList<ItemStack> contents = NonNullList.withSize(54, ItemStack.EMPTY);
    private ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        protected void onOpen(Level level, BlockPos pos, BlockState state) {
            DNAFridgeBlockEntity.this.updateBlockState(state, true);
        }

        protected void onClose(Level level, BlockPos pos, BlockState state) {
            DNAFridgeBlockEntity.this.updateBlockState(state, false);
        }

        protected void openerCountChanged(Level level, BlockPos pos, BlockState sta, int arg1, int arg2) {
        }

        protected boolean isOwnContainer(Player p_155060_) {
            if (p_155060_.containerMenu instanceof DNAFridgeMenu) {
                Container container = ((DNAFridgeMenu) p_155060_.containerMenu).getContainer();
                return container == DNAFridgeBlockEntity.this;
            } else {
                return false;
            }
        }
    };

    public DNAFridgeBlockEntity(BlockPos pos, BlockState state) {
        super(UPBlockEntities.DNA_FRIDGE_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        ContainerHelper.saveAllItems(compound, this.contents);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.contents = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compound, this.contents);
    }

    @Override
    public int getContainerSize() {
        return 54;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.contents;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemsIn) {
        this.contents = itemsIn;
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("DNA Fridge");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return DNAFridgeMenu.fiveRows(id, player, this);
    }

    public void startOpen(Player pPlayer) {
        if (level != null && !this.remove && !pPlayer.isSpectator()) {
            this.openersCounter.incrementOpeners(pPlayer, level, this.getBlockPos(), this.getBlockState());
        }
    }

    public void stopOpen(Player pPlayer) {
        if (level != null && !this.remove && !pPlayer.isSpectator()) {
            this.openersCounter.decrementOpeners(pPlayer, level, this.getBlockPos(), this.getBlockState());
        }
    }

    public void recheckOpen() {
        if (level != null && !this.remove) {
            this.openersCounter.recheckOpeners(level, this.getBlockPos(), this.getBlockState());
        }
    }

    void updateBlockState(BlockState state, boolean open) {
        this.level.setBlock(this.getBlockPos(), state.setValue(BlockDNAFridge.OPEN, open), 3);
    }

    private void playSound(BlockState state, SoundEvent sound) {
        if (level == null) return;

        Vec3i cabinetFacingVector = state.getValue(BlockDNAFridge.FACING).getNormal();
        double x = (double) worldPosition.getX() + 0.5D + (double) cabinetFacingVector.getX() / 2.0D;
        double y = (double) worldPosition.getY() + 0.5D + (double) cabinetFacingVector.getY() / 2.0D;
        double z = (double) worldPosition.getZ() + 0.5D + (double) cabinetFacingVector.getZ() / 2.0D;
        level.playSound(null, x, y, z, sound, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
    }
    @Override
    public int[] getSlotsForFace(Direction direction) {
        return IntStream.range(0, this.getContainerSize()).toArray();
    }


    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction direction) {
        return direction != Direction.DOWN && stack.is(UPTags.ALLOWED_FRIDGE_ITEMS) && stack.getItem().canFitInsideContainerItems();
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
        return direction == Direction.DOWN ;
    }

    @Override
    public boolean triggerEvent(int i, int i1) {
        if (i == 1) {
            return true;
        }
        else {
            return super.triggerEvent(i, i1);
        }
    }

    @Override
    protected IItemHandler createUnSidedHandler() {
        return new SidedInvWrapper(this, Direction.DOWN);
    }

}