package com.peeko32213.unusualprehistory.common.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import com.peeko32213.unusualprehistory.common.data.entity.PrehistoricEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class PrehistoricEntityJsonDataManager extends SimpleJsonResourceReloadListener {

    private static final Gson STANDARD_GSON = new Gson();
    public static final Logger LOGGER = LogManager.getLogger();
    private final String folderName;

    protected static Map<ResourceLocation, PrehistoricEntityData> prehistoricEntityData = new HashMap<>();

    public PrehistoricEntityJsonDataManager() {
        this("unusualprehistory/prehistoric_animal", STANDARD_GSON);
    }

    public static Map<ResourceLocation, PrehistoricEntityData> getPrehistoricEntityData() {
        return prehistoricEntityData;
    }

    public static PrehistoricEntityData getPrehistoricEntityData(ResourceLocation location) {
        return getPrehistoricEntityData().getOrDefault(location, PrehistoricEntityData.getDefaultInstance());
    }

    public PrehistoricEntityJsonDataManager(String folderName, Gson gson) {
        super(gson, folderName);
        this.folderName = folderName;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        Map<ResourceLocation, PrehistoricEntityData> prehistoricEntityData = new HashMap<>();
        this.prehistoricEntityData.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : jsons.entrySet()) {
            ResourceLocation key = entry.getKey();
            JsonElement element = entry.getValue();
            PrehistoricEntityData.CODEC.decode(JsonOps.INSTANCE, element)
                    .get()
                    .ifLeft(result -> {
                        PrehistoricEntityData codec = result.getFirst();
                        prehistoricEntityData.put(key, codec);
                    })
                    .ifRight(partial -> LOGGER.error("Failed to parse puppet Prehistoric Data for {} due to: {}", key, partial.message()));


        }

        this.prehistoricEntityData = prehistoricEntityData;
        LOGGER.info("Data loader for {} loaded {} jsons", this.folderName, this.prehistoricEntityData.size());
    }
}
