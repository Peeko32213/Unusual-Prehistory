package com.peeko32213.unusualprehistory.common.capabilities;

import com.peeko32213.unusualprehistory.common.entity.EntitySmilodon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;

public class UPAnimalCapability implements INBTSerializable<CompoundTag> {

    private String embryoAnimal = "";

    private int timer = 0;

    private static final String BASE_EMBRYO = "";
    private static final int RESET_TIMER = 0;

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();

        nbt.putString("animal", this.embryoAnimal);
        nbt.putInt("timer", this.timer);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.embryoAnimal = nbt.getString("animal");
        this.timer = nbt.getInt("timer");
    }


    public static void tickAnimal(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof Animal) || event.getEntity().getLevel().isClientSide) return;

        ServerLevel serverLevel = (ServerLevel) event.getEntity().getLevel();
        LazyOptional<UPAnimalCapability> animalCap = event.getEntity().getCapability(UPCapabilities.ANIMAL_CAPABILITY);
        animalCap.ifPresent(capability -> {
            if (capability.embryoAnimal == null || capability.getEmbryoAnimal().equals(BASE_EMBRYO)) {
                return;
            }

            if (capability.timer > 0) {
                capability.timer = capability.timer - 1;
                return;
            }

            if(capability.embryoAnimal != null && !capability.embryoAnimal.equals(BASE_EMBRYO)){
                ResourceLocation entityRl = new ResourceLocation(capability.embryoAnimal);
                EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(entityRl);
                LivingEntity livingEntity = (LivingEntity) entityType.create(serverLevel);
                livingEntity.setPos(event.getEntity().position());
                livingEntity.setUUID(UUID.randomUUID());
                if(livingEntity instanceof EntitySmilodon entitySmilodon)
                {
                    entitySmilodon.setAge(-24000);
                    serverLevel.addFreshEntity(entitySmilodon);
                }

                if(livingEntity instanceof Animal animal){
                    animal.setAge(-24000);

                    if(livingEntity instanceof EntitySmilodon entitySmilodon)
                    {
                        entitySmilodon.setVariant(1);
                    }
                    serverLevel.addFreshEntity(animal);


                } else {
                    serverLevel.addFreshEntity(livingEntity);
                }

                capability.setEmbryoAnimal(BASE_EMBRYO);
                capability.setTimer(RESET_TIMER);
            }
        });
    }

    public void setEmbryoAnimal(String embryoAnimal) {
        this.embryoAnimal = embryoAnimal;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public String getEmbryoAnimal() {
        return embryoAnimal;
    }

    public int getTimer() {
        return timer;
    }
}

