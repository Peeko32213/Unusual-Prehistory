package com.peeko32213.unusualprehistory.common.block.entity;

import com.peeko32213.unusualprehistory.common.entity.EntitySmilodon;
import com.peeko32213.unusualprehistory.common.entity.IHatchableEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.baby.EntityBabySmilodon;
import com.peeko32213.unusualprehistory.common.message.SyncItemStackC2SPacket;
import com.peeko32213.unusualprehistory.common.message.SyncItemStackS2CPacket;
import com.peeko32213.unusualprehistory.common.recipe.IncubatorRecipe;
import com.peeko32213.unusualprehistory.core.registry.UPBlockEntities;
import com.peeko32213.unusualprehistory.core.registry.UPMessages;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.peeko32213.unusualprehistory.common.block.BlockIncubator.CRACKED;
import static com.peeko32213.unusualprehistory.common.block.BlockIncubator.FACING;

public class IncubatorBlockEntity extends BlockEntity implements ContainerListener {
    private BlockState blockstate;
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 1152;
    private List<ItemStack> inv = new ArrayList<>();
    private IncubatorBlockEntity blockEntity;
    public int tickCount = 0;
    public static final Logger LOGGER = LogManager.getLogger();
    public IncubatorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(UPBlockEntities.INCUBATOR_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.blockstate = pBlockState;
        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> IncubatorBlockEntity.this.progress;
                    case 1 -> IncubatorBlockEntity.this.tickCount;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> IncubatorBlockEntity.this.progress = value;
                    case 1 -> IncubatorBlockEntity.this.tickCount = value;
                }
            }

            public int getCount() {
                return 1;
            }
        };
        if(this.level != null) {
            this.blockEntity = (IncubatorBlockEntity) level.getBlockEntity(pPos);
            //initInventory();
        }
    }




    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, IncubatorBlockEntity pBlockEntity) {
        if(!pLevel.isClientSide){
            if(pBlockEntity.inv != null && !pBlockEntity.inv.isEmpty())
            {
                    UPMessages.sendToClients(new SyncItemStackS2CPacket(pBlockEntity.inv, pPos));
            }
        }
        pBlockEntity.tickCount++;

        if(hasRecipe(pBlockEntity)) {
            //spawnParticles(pLevel, pPos, pLevel.random, 0D,0.02);
            pBlockEntity.progress++;
            if(pBlockEntity.progress >= (pBlockEntity.maxProgress/2)){
                pBlockEntity.getBlockState().setValue(CRACKED, true);
            }

            setChanged(pLevel, pPos, pState);
            if(pBlockEntity.progress > pBlockEntity.maxProgress) {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    public static boolean hasRecipe(IncubatorBlockEntity entity) {
        Level level = entity.level;
        if(entity.inv == null) return false;
        SimpleContainer inventory = new SimpleContainer(entity.inv.size());
        for (int i = 0; i < entity.inv.size(); i++) {
            inventory.setItem(i, entity.inv.get(i));
        }
        Optional<IncubatorRecipe> match = level.getRecipeManager()
                .getRecipeFor(IncubatorRecipe.Type.INSTANCE, inventory, level);
        return match.isPresent();
    }

    private static void craftItem(IncubatorBlockEntity entity) {
        Level level = entity.level;
        if(entity.inv == null) return;
        SimpleContainer inventory = new SimpleContainer(entity.inv.size());
        for (int i = 0; i < entity.inv.size(); i++) {
            inventory.setItem(i, entity.inv.get(i));
        }

        Optional<IncubatorRecipe> match = level.getRecipeManager()
                .getRecipeFor(IncubatorRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            entity.inv.clear();
            ResourceLocation toSpawn = match.get().getOutput();
            if(!level.isClientSide){
                UPMessages.sendToClients(new SyncItemStackS2CPacket(entity.inv, entity.worldPosition));
                BlockPos pos = entity.worldPosition;
                Direction dir = entity.getBlockState().getValue(FACING);
                pos = pos.relative(dir,1);
                boolean entitySpawned = spawnEntity((ServerLevel) level, pos, toSpawn, match.get());

               // if(entity.getDestroyChance(level)){
                //
                if(entitySpawned) {
                    entity.level.destroyBlock(entity.worldPosition, true);
                }
                //}
            }

            entity.resetProgress();
        }
    }

    public ItemStack getRenderStack() {
        ItemStack stack = ItemStack.EMPTY;
        if(inv == null) return stack;
        if(!this.inv.isEmpty()){
            if(!inv.get(0).isEmpty())
            stack = inv.get(0);
        }

        return stack;
    }

    private static boolean spawnEntity(ServerLevel serverLevel, BlockPos pos, ResourceLocation toSpawn, IncubatorRecipe recipe){
        EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(toSpawn);
        LivingEntity livingEntity = (LivingEntity) entityType.create(serverLevel);
        if(livingEntity == null)
        {
            LOGGER.error("Invalid entity resourcelocation for {}", recipe.getId());
            return false;
        }

        livingEntity.setPos(Vec3.atCenterOf(pos));
        livingEntity.setUUID(UUID.randomUUID());

        if(livingEntity instanceof IHatchableEntity hatchableEntity){
            hatchableEntity.determineVariant(serverLevel.random.nextInt(100));
        }


        if(livingEntity instanceof Animal animal){
            animal.setAge(-24000);

            if(livingEntity instanceof EntitySmilodon smilodon){
                smilodon.determineVariant(0);
            } else if(livingEntity instanceof EntityBabySmilodon smilodon){
                smilodon.determineVariant(0);
            }

            serverLevel.addFreshEntity(animal);
        }

        else {
            serverLevel.addFreshEntity(livingEntity);
        }
        return true;
    }

    private boolean getDestroyChance(Level level){
        return level.random.nextInt(0,100) <= 30;
    }


    private void resetProgress() {
        this.progress = 0;
    }

    public static List<ItemStack> getInventory(Level level, BlockPos pos) {
        IncubatorBlockEntity incubatorBlockEntity = (IncubatorBlockEntity) level.getBlockEntity(pos);
        return incubatorBlockEntity.inv;
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide){
                UPMessages.sendToClients(new SyncItemStackC2SPacket(this, worldPosition));
            }
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandlerOptional = LazyOptional.of(() -> itemHandler);
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == null) {
                return lazyItemHandlerOptional.cast();
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandlerOptional = LazyOptional.of(() -> itemHandler);
        if(level != null && !level.isClientSide){
            UPMessages.sendToClients(new SyncItemStackC2SPacket(this.itemHandler, worldPosition));
            UPMessages.sendToClients(new SyncItemStackS2CPacket(inv, worldPosition));
            //this.blockEntity = (IncubatorBlockEntity) level.getBlockEntity(worldPosition);
            //this.blockEntity.initInventory();
        }
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compoundTag = new CompoundTag();
        return compoundTag;
    }


    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_IMMEDIATE);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandlerOptional.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        //if(inventory == null) initInventory();
        if (inv != null) {
            final ListTag nbttaglist = new ListTag();
            for (int i = 0; i < this.inv.size(); ++i) {
                final ItemStack itemstack = this.inv.get(i);
                if (!itemstack.isEmpty()) {
                    CompoundTag CompoundNBT = new CompoundTag();
                    CompoundNBT.put("Item", itemstack.serializeNBT());
                    nbttaglist.add(CompoundNBT);
                }
            }
            tag.put("Items", nbttaglist);
        }
        tag.putInt("analyzer.progress", progress);
        super.saveAdditional(tag);
    }



    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        if(inv == null) this.inv = new ArrayList<>();
        if (inv != null) {
            final ListTag nbttaglist = nbt.getList("Items", 10);
            for (int i = 0; i < nbttaglist.size(); ++i) {
                final CompoundTag CompoundNBT = nbttaglist.getCompound(i);
                ItemStack itemStack =  ItemStack.of(CompoundNBT.getCompound("Item"));
                this.inv.add(itemStack);
            }
        }


        progress = nbt.getInt("analyzer.progress");
    }

    public void setHandler(List<ItemStack> stacks) {
        inv.clear();
        inv.addAll(stacks);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(this.inv.size());
        for (int i = 0; i < this.inv.size(); i++) {
            inventory.setItem(i, this.inv.get(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
        this.inv.clear();
        if(!level.isClientSide){
            UPMessages.sendToClients(new SyncItemStackS2CPacket(inv, worldPosition));
        }
    }

    @Override
    public void containerChanged(Container pContainer) {
    }
}
