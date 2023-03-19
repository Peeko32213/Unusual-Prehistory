package com.peeko32213.unusualprehistory.common.block.entity;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.recipe.AnalyzerRecipe;
import com.peeko32213.unusualprehistory.common.screen.AnalyzerMenu;
import com.peeko32213.unusualprehistory.core.registry.UPBlockEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Optional;

public class AnalyzerBlockEntity extends BlockEntity implements MenuProvider {

    // Add new flasks to the item tag

    public AnalyzerBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(UPBlockEntities.ANALYZER_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                switch (index) {
                    case 0: return AnalyzerBlockEntity.this.progress;
                    case 1: return AnalyzerBlockEntity.this.maxProgress;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: AnalyzerBlockEntity.this.progress = value; break;
                    case 1: AnalyzerBlockEntity.this.maxProgress = value; break;
                }
            }

            public int getCount() {
                return 8;
            }
        };
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(8) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }


        @Override
        public int getSlotLimit(int slot) {
            return 64;
        }
    };


    private IItemHandler hopperHandler = new IItemHandler() {
        @Override
        public int getSlots() {
            return itemHandler.getSlots();
        }

        @NotNull
        @Override
        public ItemStack getStackInSlot(int slot) {
            return itemHandler.getStackInSlot(slot);
        }

        @NotNull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {


            if(slot != 0 && slot != 1){
                return itemHandler.extractItem(slot, amount, simulate);
            }
            return ItemStack.EMPTY;
        }

        @NotNull
        @Override
        public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if(stack.isEmpty()){
                return stack;
            }

            if(slot == 0 && stack.is(UPItems.FLASK.get())){
                return itemHandler.insertItem(slot, stack, simulate);
            }
            if(slot == 1 && stack.is(UPTags.ANALYZER_ITEMS_INPUT)) {
                return itemHandler.insertItem(slot, stack, simulate);
            }
            return stack;
        }
        @Override
        public int getSlotLimit(int slot) {
            return itemHandler.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return itemHandler.isItemValid(slot, stack);
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandlerOptional = LazyOptional.of(() -> itemHandler);
    private LazyOptional<IItemHandler> hopperHandlerOptional = LazyOptional.of(() -> hopperHandler);

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 144;

    @Override
    public Component getDisplayName() {
        return Component.translatable(UnusualPrehistory.MODID + ".blockentity.analyzer");
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == null) {
                return lazyItemHandlerOptional.cast();
            } else{
                return hopperHandlerOptional.cast();
            }

        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandlerOptional = LazyOptional.of(() -> itemHandler);
        hopperHandlerOptional = LazyOptional.of(() -> hopperHandler);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandlerOptional.invalidate();
        hopperHandlerOptional.invalidate();
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

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, AnalyzerBlockEntity pBlockEntity) {
        if(hasRecipe(pBlockEntity)) {
            pBlockEntity.progress = Math.min(pBlockEntity.progress + 1, pBlockEntity.maxProgress);
            setChanged(pLevel, pPos, pState);
            if(pBlockEntity.progress >= pBlockEntity.maxProgress && !pLevel.isClientSide()) {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private static boolean hasRecipe(AnalyzerBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 1; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<AnalyzerRecipe> match = level.getRecipeManager()
                .getRecipeFor(AnalyzerRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && hasFlaskInWaterSlot(entity);
    }

    private static boolean hasFlaskInWaterSlot(AnalyzerBlockEntity entity) {
        return entity.itemHandler.getStackInSlot(0).getItem() == UPItems.FLASK.get();
    }

    private static void craftItem(AnalyzerBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<AnalyzerRecipe> match = level.getRecipeManager()
                .getRecipeFor(AnalyzerRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            // determine result item
            ItemStack result = match.get().assemble(inventory);
            // remove first input
            entity.itemHandler.extractItem(1, 1, false);
            // attempt to remove flask
            if(isFilledFlask(result)) {
                entity.itemHandler.extractItem(0, 1, false);
            }
            // attempt to insert result item
            boolean success = false;
            for(int i = 2, n = entity.itemHandler.getSlots(); i < n; i++) {
                if(entity.itemHandler.insertItem(i, result, false).isEmpty()) {
                    success = true;
                    break;
                }
            }
            // reset progress
            entity.resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        for(int slot = 2, n = inventory.getContainerSize(); slot < n; slot++) {
            if(inventory.getItem(slot).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isFilledFlask(ItemStack itemStack) {
        return itemStack.is(UPTags.FILLED_FLASKS);
    }

    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new AnalyzerMenu(pContainerId, pInventory, this, this.data);
    }
}
