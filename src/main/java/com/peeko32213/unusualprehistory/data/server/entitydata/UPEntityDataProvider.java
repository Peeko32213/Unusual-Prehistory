package com.peeko32213.unusualprehistory.data.server.entitydata;

import com.google.common.collect.Sets;
import com.mojang.serialization.JsonOps;
import com.peeko32213.unusualprehistory.common.data.entity.PrehistoricEntityData;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.modPrefix;

public abstract class UPEntityDataProvider implements DataProvider {
    protected final PackOutput.PathProvider entityPathProvider;

    public UPEntityDataProvider(PackOutput pOutput) {
        this.entityPathProvider = pOutput.createPathProvider(PackOutput.Target.DATA_PACK, modPrefix("unusualprehistory/prehistoric_animal").getPath());
    }

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput) {
        Set<ResourceLocation> set = Sets.newHashSet();
        Set<ResourceLocation> taskSet = Sets.newHashSet();
        List<CompletableFuture<?>> list = new ArrayList<>();
        this.buildEntityData((puppet -> {
            if (!set.add(puppet.getLocation())) {
                throw new IllegalStateException("Duplicate entity " + puppet.getLocation());
            } else {

                PrehistoricEntityData.CODEC.encodeStart(JsonOps.INSTANCE, puppet.getEntityData())
                        .get()
                        .ifLeft(e -> list.add(DataProvider.saveStable(pOutput, e, this.entityPathProvider.json(puppet.getLocation()))))
                        .ifRight(partial -> LOGGER.error("Failed to create puppet {}, due to {}", puppet.getLocation(), partial));
            }
        }));
        return CompletableFuture.allOf(list.toArray((p_253414_) -> {
            return new CompletableFuture[p_253414_];
        }));
    }


    protected abstract void buildEntityData(Consumer<UPEntityDataConsumer> pWriter);

    @Override
    public String getName() {
        return "prehistoric entity data";
    }
}
