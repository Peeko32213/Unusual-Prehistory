package com.peeko32213.unusualprehistory.common.block.entity;

import com.peeko32213.unusualprehistory.common.block.IDinosaurEgg;
import com.peeko32213.unusualprehistory.common.entity.IHatchableEntity;
import com.peeko32213.unusualprehistory.core.registry.UPBlockEntities;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.peeko32213.unusualprehistory.common.block.BlockDinosaurLandEggsBase.EGGS;
import static com.peeko32213.unusualprehistory.common.block.BlockDinosaurLandEggsBase.HATCH;

public class DinosaurLandEggsBlockEntity extends BlockEntity {

    private int tickCount;

    public DinosaurLandEggsBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(UPBlockEntities.DINO_LAND_EGGS.get(), pPos, pBlockState);
    }

    public static void tick(@NotNull Level level, BlockPos pos, BlockState state, DinosaurLandEggsBlockEntity blockEntity) {
        if(state.getBlock() instanceof IDinosaurEgg dinosaurEgg && level instanceof ServerLevel serverLevel){
            if(blockEntity.tickCount++ % dinosaurEgg.getHatchTimeMax() == 0) {
                blockEntity.growEgg(state, serverLevel, pos, blockEntity, dinosaurEgg);
            }
        }
    }
    public void saveChangeIntProperty(Level level, BlockPos pos, BlockState state, IntegerProperty property, int value){
        if(state.hasProperty(property) && pos != null){
            level.setBlockAndUpdate(pos, state.setValue(property, value));
        }
    }

    public void growEgg(BlockState state, ServerLevel worldIn, BlockPos pos, DinosaurLandEggsBlockEntity entity, IDinosaurEgg egg) {
        if (hasProperHabitat(worldIn, pos)) {
            RandomSource random = entity.getLevel().random;
            int i = state.getValue(HATCH);
            if (i < 2) {
                worldIn.playSound(null, pos, SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                entity.saveChangeIntProperty(worldIn, pos, state, HATCH, i + 1);
            } else {
                worldIn.playSound(null, pos, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                worldIn.removeBlock(pos, false);
                for (int j = 0; j < state.getValue(EGGS); ++j) {
                    worldIn.levelEvent(4001, pos, Block.getId(state));
                    Mob dinosaurToSpawn = (Mob) egg.getDinosaur().get().create(worldIn);
                    if(dinosaurToSpawn instanceof IHatchableEntity hatchableEntity){
                        hatchableEntity.determineVariant(random.nextInt(100));
                    }


                    if(dinosaurToSpawn instanceof Animal animal){
                        animal.setAge(-24000);
                    }

                    dinosaurToSpawn.restrictTo(pos, 20);
                    dinosaurToSpawn.moveTo((double) pos.getX() + 0.3D + (double) j * 0.2D, pos.getY(), (double) pos.getZ() + 0.3D, 0.0F, 0.0F);
                    if (!worldIn.isClientSide) {
                        worldIn.addFreshEntity(dinosaurToSpawn);
                    }
                }
            }
        }
    }

    public static boolean hasProperHabitat(BlockGetter reader, BlockPos blockReader) {
        return isProperHabitat(reader, blockReader.below());
    }

    public static boolean isProperHabitat(BlockGetter reader, BlockPos pos) {
        return reader.getBlockState(pos).is(UPTags.DINO_HATCHABLE_BLOCKS);
    }


    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("ticks", tickCount);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.tickCount = pTag.getInt("ticks");
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        this.tickCount = tag.getInt("ticks");
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("ticks", tickCount);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
