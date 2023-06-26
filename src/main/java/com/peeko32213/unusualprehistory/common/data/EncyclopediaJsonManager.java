package com.peeko32213.unusualprehistory.common.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class EncyclopediaJsonManager extends SimpleJsonResourceReloadListener {

    private static final Gson STANDARD_GSON = new Gson();
    public static final Logger LOGGER = LogManager.getLogger();
    private final String folderName;

    protected static Map<ResourceLocation, EncyclopediaCodec> encyclopediaEntries = new HashMap<>();
    protected static EncyclopediaCodec rootPage;


    public EncyclopediaJsonManager()
    {
        this(prefix("encyclopedia").getPath(), STANDARD_GSON);
    }

    public static Map<ResourceLocation, EncyclopediaCodec> getEncyclopediaEntries() {
        return encyclopediaEntries;
    }

    public static EncyclopediaCodec getRootPage() {
        return rootPage;
    }

    public EncyclopediaJsonManager(String folderName, Gson gson) {
        super(gson, folderName);
        this.folderName = folderName;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        Map<ResourceLocation, EncyclopediaCodec> encyclopedia = new HashMap<>();
        AtomicReference<EncyclopediaCodec> rootPageR = new AtomicReference<>();
        for (Map.Entry<ResourceLocation, JsonElement> entry : jsons.entrySet()) {
            ResourceLocation key = entry.getKey();
            JsonElement element = entry.getValue();
            EncyclopediaCodec.CODEC.decode(JsonOps.INSTANCE, element)
                    .get()
                    .ifLeft(result -> {
                        EncyclopediaCodec encyclopediaCodec = result.getFirst();

                        if(key.equals(prefix("root"))){
                            rootPageR.set(encyclopediaCodec);

                        } else {
                            encyclopediaCodec.getEntityButtons().forEach(b -> {
                                if(b.getLinkedPage().equals("")){
                                    b.setLinkedPage(String.valueOf(key));
                                }
                            });
                            encyclopedia.put(key,encyclopediaCodec);
                        }


                    })
                    .ifRight(partial -> LOGGER.error("Failed to parse recipe JSON for {} due to: {}", this.folderName, partial.message()));
        }

        this.rootPage = rootPageR.get();
        this.encyclopediaEntries = encyclopedia;
        LOGGER.info("Data loader for {} loaded {} jsons", this.folderName, this.encyclopediaEntries.size());
    }
}
