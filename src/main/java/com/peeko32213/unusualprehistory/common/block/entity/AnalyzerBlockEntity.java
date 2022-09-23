package com.peeko32213.unusualprehistory.common.block.entity;

import com.peeko32213.unusualprehistory.common.recipe.AnalyzerRecipe;
import com.peeko32213.unusualprehistory.common.screen.AnalyzerMenu;
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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Random;

public class AnalyzerBlockEntity extends BlockEntity implements MenuProvider {

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
                return 10;
            }
        };
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(10) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };


    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 144;

    @Override
    public Component getDisplayName() {
        return new TextComponent("                             Analyzer");
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
        for (int i = 1; i < 4; i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<AnalyzerRecipe> match = level.getRecipeManager()
                .getRecipeFor(AnalyzerRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getResultItem())
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
            // damage and/or remove inputs
            entity.itemHandler.extractItem(0,1, false);
            entity.itemHandler.extractItem(1,1, false);
            entity.itemHandler.getStackInSlot(2).hurt(1, new Random(), null);
            // determine result item
            ItemStack result = match.get().assemble(inventory);
            // attempt to insert result item
            boolean success = false;
            for(int i = 4, n = entity.itemHandler.getSlots(); i < n; i++) {
                if(entity.itemHandler.insertItem(i, result, false).isEmpty()) {
                    success = true;
                    break;
                }
            }
            // reset progress
            entity.resetProgress();
            // drop item when full
            if(!success) {
                Vec3 vec = Vec3.atBottomCenterOf(entity.worldPosition.above());
                entity.level.addFreshEntity(new ItemEntity(entity.level, vec.x, vec.y, vec.z, result));
            }
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(5).getItem() == output.getItem() || inventory.getItem(5).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(5).getMaxStackSize() > inventory.getItem(5).getCount();
    }

    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new AnalyzerMenu(pContainerId, pInventory, this, this.data);
    }
}
