package com.peeko32213.unusualprehistory.common.block.entity;

import com.peeko32213.unusualprehistory.common.data.LootFruitCodec;
import com.peeko32213.unusualprehistory.common.data.LootFruitJsonManager;
import com.peeko32213.unusualprehistory.core.registry.UPBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FruitLootBoxEntity extends BlockEntity {

    //private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public FruitLootBoxEntity(BlockPos pPos, BlockState pBlockState) {
        super(UPBlockEntities.FRUIT_LOOT_BOX_BLOCK_ENTITY.get(), pPos, pBlockState);
    }
    private Item tradeItem;
    private int color;

    private  List<LootFruitCodec> lootFruits;

    private int CustomModelData;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


    public void setTradeItem(Item tradeItem) {
        this.tradeItem = tradeItem;
    }

    public Item getTradeItem() {
        return tradeItem;
    }


    public void setLootFruits(List<LootFruitCodec> lootFruits) {
        this.lootFruits = lootFruits;
    }

    public int getCustomModelData() {
        return CustomModelData;
    }

    public void setCustomModelData(int customModelData) {
        CustomModelData = customModelData;
    }

    public List<LootFruitCodec> getLootFruits() {
        return lootFruits;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("color", getColor());
        pTag.put("tradeItem", getTradeItem().getDefaultInstance().serializeNBT());
        pTag.putInt("modelData", getCustomModelData());
    }


    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.color = pTag.getInt("color");
        this.setTradeItem(ItemStack.of(pTag.getCompound("tradeItem")).getItem());

        if(getTradeItem() != null){
           setLootFruits(LootFruitJsonManager.getLoot(getTradeItem(), getLootFruits()));
        }

        this.setCustomModelData(pTag.getInt("modelData"));
    }


    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        this.color = tag.getInt("color");
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("color", this.getColor());
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
}
