package com.peeko32213.unusualprehistory.common.block.entity;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.block.BlockCultivator;
import com.peeko32213.unusualprehistory.common.networking.UPMessages;
import com.peeko32213.unusualprehistory.common.networking.packet.SyncItemStackC2SPacket;
import com.peeko32213.unusualprehistory.common.recipe.CultivatorRecipe;
import com.peeko32213.unusualprehistory.common.screen.CultivatorMenu;
import com.peeko32213.unusualprehistory.core.registry.UPBlockEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Random;

import static com.peeko32213.unusualprehistory.common.block.BlockCultivator.HALF;

public class CultivatorBlockEntity extends BlockEntity implements MenuProvider {
    private static final TagKey<Item> ORGANIC_OOZE = ForgeRegistries.ITEMS.tags().createTagKey(new ResourceLocation(UnusualPrehistory.MODID, "organic_ooze"));
    private BlockState blockstate;
    public int ticksExisted;

    public static void commonTick(Level level, BlockPos pos, BlockState state, CultivatorBlockEntity entity) {
        entity.tick();
    }

    public void tick() {
        ticksExisted++;
    }

    public CultivatorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(UPBlockEntities.CULTIVATOR_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        this.blockstate = pBlockState;
        this.data = new ContainerData() {
            public int get(int index) {
                switch (index) {
                    case 0: return CultivatorBlockEntity.this.progress;
                    case 1: return CultivatorBlockEntity.this.maxProgress;
                    case 2: return CultivatorBlockEntity.this.fuel;
                    case 3: return CultivatorBlockEntity.this.maxFuel;
                    case 4: return CultivatorBlockEntity.this.tickCount;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: CultivatorBlockEntity.this.progress = value; break;
                    case 1: CultivatorBlockEntity.this.maxProgress = value; break;
                    case 2: CultivatorBlockEntity.this.fuel = value; break;
                    case 3: CultivatorBlockEntity.this.maxFuel = value; break;
                    case 4: CultivatorBlockEntity.this.tickCount = value; break;

                }
            }

            public int getCount() {
                return 5;
            }
        };
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide){
                UPMessages.sendToClients(new SyncItemStackC2SPacket(this, worldPosition));
            }
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
            if(blockstate.getValue(HALF) == DoubleBlockHalf.UPPER){
                return itemHandler.extractItem(slot, amount, simulate);
            }

            if((slot == 2) || (slot == 3)){
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

            if(slot == 0 && stack.is(UPTags.DNA_FLASKS)){
                return itemHandler.insertItem(slot, stack, simulate);
            }
            if(slot == 1 && stack.is(UPItems.ORGANIC_OOZE.get())) {
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
    private int maxProgress = 1152;
    private int fuel = 0;
    private int maxFuel = 790;

    public int tickCount = 0;

    @Override
    public Component getDisplayName() {
        return Component.translatable("Cultivator");
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
        if(level != null && !level.isClientSide){
            UPMessages.sendToClients(new SyncItemStackC2SPacket(this.itemHandler, worldPosition));
        }
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

    public static void spawnParticles(LevelAccessor levelAccessor, BlockPos pos, RandomSource random, double speedXZModifier, double speedYModifier){
        Level level = (Level) levelAccessor;
        level.addAlwaysVisibleParticle(ParticleTypes.BUBBLE_POP, true,(double)pos.getX() + 0.2 + random.nextDouble() * 0.4 , (double)(pos.getY() + 0.5 + random.nextDouble()), (double)pos.getZ() + 0.2 + random.nextDouble() * 0.6,  speedXZModifier, speedYModifier, speedXZModifier);
    }

    public ItemStack getRenderStack() {
        ItemStack stack;

        if(!itemHandler.getStackInSlot(2).isEmpty()){
            stack = itemHandler.getStackInSlot(2);
        } else{
            stack = itemHandler.getStackInSlot(0);
        }
        return stack;
    }
    public void setHandler(ItemStackHandler itemStackHandler) {
        for(int i = 0; i < itemStackHandler.getSlots(); i++){
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, CultivatorBlockEntity pBlockEntity, RandomSource random) {
        pBlockEntity.tickCount++;

        if(hasRecipe(pBlockEntity)) {
            spawnParticles(pLevel, pPos, pLevel.random, 0D,0.02);
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
        if(!fuelStack.isEmpty() && fuelStack.is(ORGANIC_OOZE)) {
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
