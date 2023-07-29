package com.peeko32213.unusualprehistory.common.networking.packet;

import com.peeko32213.unusualprehistory.common.block.entity.CultivatorBlockEntity;
import com.peeko32213.unusualprehistory.common.block.entity.IncubatorBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class SyncItemStackS2CPacket {
    private final List<ItemStack>  itemStacks;
    private final BlockPos pos;

    public SyncItemStackS2CPacket(List<ItemStack> itemStacks, BlockPos pos) {
        this.itemStacks = itemStacks;
        this.pos = pos;
    }

    public SyncItemStackS2CPacket(FriendlyByteBuf buf) {
        List<ItemStack> collection = buf.readCollection(ArrayList::new, FriendlyByteBuf::readItem);
        itemStacks = new ArrayList<>();
        for (int i = 0; i < collection.size(); i++) {
            itemStacks.addAll(collection);
        }

        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        Collection<ItemStack> list = new ArrayList<>();
        for(int i = 0; i < itemStacks.size(); i++) {
            list.add(itemStacks.get(i));
        }

        buf.writeCollection(list, FriendlyByteBuf::writeItem);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof IncubatorBlockEntity blockEntity) {
                blockEntity.setHandler(this.itemStacks);
            }
        });
        return true;
    }
}