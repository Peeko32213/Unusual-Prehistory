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
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.List;

public class FruitLootBoxEntity extends BlockEntity {

    //private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public FruitLootBoxEntity(BlockPos pPos, BlockState pBlockState) {
        super(UPBlockEntities.FRUIT_LOOT_BOX_BLOCK_ENTITY.get(), pPos, pBlockState);
    }
    private Item tradeItem;
    private int color;

    private  List<LootFruitCodec> lootFruits;

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

    public List<LootFruitCodec> getLootFruits() {
        return lootFruits;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("color", getColor());
        pTag.put("tradeItem", getTradeItem().getDefaultInstance().serializeNBT());

    }


    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.color = pTag.getInt("color");
        this.setTradeItem(ItemStack.of(pTag.getCompound("tradeItem")).getItem());

        if(getTradeItem() != null){
           setLootFruits(LootFruitJsonManager.getLoot(getTradeItem(), getLootFruits()));
        }

        //((FruitLootBoxEntity)this.level.getBlockEntity(this.getBlockPos())).setColor(pTag.getInt("color"));
        //setColor(pTag.getInt("color"));



        //this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(),3);
        //if(this.level !=null) {
        //    this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_IMMEDIATE);
        //}

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
