package com.peeko32213.unusualprehistory.common.block.entity;

import com.peeko32213.unusualprehistory.common.recipe.CultivatorRecipe;
import com.peeko32213.unusualprehistory.common.screen.CultivatorMenu;
import com.peeko32213.unusualprehistory.core.registry.UPBlockEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Optional;

public class CultivatorBlockEntity extends BlockEntity implements MenuProvider {

    public CultivatorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(UPBlockEntities.CULTIVATOR_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                switch (index) {
                    case 0: return CultivatorBlockEntity.this.progress;
                    case 1: return CultivatorBlockEntity.this.maxProgress;
                    case 2: return CultivatorBlockEntity.this.fuel;
                    case 3: return CultivatorBlockEntity.this.maxFuel;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: CultivatorBlockEntity.this.progress = value; break;
                    case 1: CultivatorBlockEntity.this.maxProgress = value; break;
                    case 2: CultivatorBlockEntity.this.fuel = value; break;
                    case 3: CultivatorBlockEntity.this.maxFuel = value; break;
                }
            }

            public int getCount() {
                return 4;
            }
        };
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };


    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;
    private int fuel = 0;
    private int maxFuel = 144;

    @Override
    public Component getDisplayName() {
        return new TextComponent("Cultivator");
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("analyzer.progress", progress);
        super.saveAdditional(tag);
    }



    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("analyzer.progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, CultivatorBlockEntity pBlockEntity) {
        if(hasRecipe(pBlockEntity)) {
            pBlockEntity.progress++;
            pBlockEntity.depleteFuel();
            setChanged(pLevel, pPos, pState);
            if(pBlockEntity.progress > pBlockEntity.maxProgress) {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            if(!pBlockEntity.hasFuel()) {
                pBlockEntity.refuel();
            }
            setChanged(pLevel, pPos, pState);
        }
    }

    private static boolean hasRecipe(CultivatorBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        Optional<CultivatorRecipe> match = level.getRecipeManager()
                .getRecipeFor(CultivatorRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().assemble(inventory))
                && canDiscardFlask(inventory, new ItemStack(UPItems.FLASK.get()))
                && entity.hasFuel();
    }

    private static void craftItem(CultivatorBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<CultivatorRecipe> match = level.getRecipeManager()
                .getRecipeFor(CultivatorRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            entity.itemHandler.extractItem(0,1, false);
            entity.itemHandler.insertItem(2, match.get().assemble(inventory), false);
            entity.itemHandler.insertItem(3, new ItemStack(UPItems.FLASK.get()), false);
            entity.resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void depleteFuel() {
        // reduce fuel amount
        this.fuel--;
        if(this.fuel <= 0) {
            fuel = 0;
            refuel();
        }
    }

    private void refuel() {
        // attempt to refuel
        ItemStack fuelStack = itemHandler.getStackInSlot(1);
        int fuelAmount = getFuelAmount(fuelStack);
        if(fuelAmount > 0) {
            // reduce stack size
            itemHandler.extractItem(1, 1, false);
            fuel = fuelAmount;
        }
    }

    private boolean hasFuel() {
        return fuel > 0;
    }

    private int getFuelAmount(final ItemStack fuelStack) {
        if(!fuelStack.isEmpty() && fuelStack.is(UPItems.ORGANIC_OOZE.get())) {
            return maxFuel;
        }
        return 0;
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(2).getItem() == output.getItem() || inventory.getItem(2).isEmpty();
    }

    private static boolean canDiscardFlask(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(3).getItem() == output.getItem() || inventory.getItem(3).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount()
                && inventory.getItem(3).getMaxStackSize() > inventory.getItem(3).getCount();
    }

    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new CultivatorMenu(pContainerId, pInventory, this, this.data);
    }
}
