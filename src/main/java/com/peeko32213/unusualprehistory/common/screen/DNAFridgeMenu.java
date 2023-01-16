package com.peeko32213.unusualprehistory.common.screen;

import com.peeko32213.unusualprehistory.core.registry.UPMenuTypes;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.crafting.ConditionalRecipe;

import static net.minecraft.world.level.block.ChestBlock.getContainer;

public class DNAFridgeMenu extends AbstractContainerMenu {

    private static final int SLOTS_PER_ROW = 9;
    private final Container container;
    private final int containerRows;
    public DNAFridgeMenu(int id, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(UPMenuTypes.DNA_FRIDGE_MENU.get(), id, inventory, new SimpleContainer(45), 5);
    }


    public static DNAFridgeMenu fiveRows(int id, Inventory inventory, Container container) {
        return new DNAFridgeMenu(UPMenuTypes.DNA_FRIDGE_MENU.get(), id, inventory, container,5);
    }



    public DNAFridgeMenu(MenuType<?> menuType, int id, Inventory inventory, Container container, int containerRows) {
        super(menuType, id);
        checkContainerSize(container, containerRows * 9);
        this.container = container;
        this.containerRows = containerRows;
        container.startOpen(inventory.player);
        int i = (this.containerRows - 4) * 18;


        for(int j = 0; j < this.containerRows; ++j) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new DNAFridgeMenu.DNAFridgeSlot(container, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inventory, i1, 8 + i1 * 18, 161 + i));
        }

    }


    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    public ItemStack quickMoveStack(Player player, int containerRows) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(containerRows);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (containerRows < this.containerRows * 9) {
                if (!this.moveItemStackTo(itemstack1, this.containerRows * 9, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.containerRows * 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    public Container getContainer() {
        return this.container;
    }

    public int getRowCount() {
        return this.containerRows;
    }


    static class DNAFridgeSlot extends Slot {
        public DNAFridgeSlot(Container container, int slots, int x, int y) {
            super(container, slots, x, y);
        }

        public boolean mayPlace(ItemStack stack) {
            return super.mayPlace(stack) && stack.is(UPTags.ALLOWED_FRIDGE_ITEMS);
        }
    }
}
