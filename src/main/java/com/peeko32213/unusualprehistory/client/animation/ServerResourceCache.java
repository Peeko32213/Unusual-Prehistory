package com.peeko32213.unusualprehistory.client.animation;

import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.GeckoLibException;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.loading.FileLoader;
import software.bernie.geckolib.loading.json.FormatVersion;
import software.bernie.geckolib.loading.json.raw.Model;
import software.bernie.geckolib.loading.object.BakedAnimations;
import software.bernie.geckolib.loading.object.BakedModelFactory;
import software.bernie.geckolib.loading.object.GeometryTree;
import software.bernie.geckolib.util.JsonUtil;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ServerResourceCache {
    private static final Set<String> EXCLUDED_NAMESPACES = ObjectOpenHashSet.of("moreplayermodels", "customnpcs", "gunsrpg");

    private static Map<ResourceLocation, BakedAnimations> ANIMATIONS = Collections.emptyMap();
    private static Map<ResourceLocation, BakedGeoModel> MODELS = Collections.emptyMap();

    private static Map<ResourceLocation, JsonObject> MODEL_OBJECT = Collections.emptyMap();
    private static Map<ResourceLocation, JsonObject> ANIMATION_OBJECT = Collections.emptyMap();
    public static Map<ResourceLocation, BakedAnimations> getBakedAnimations() {
        //if (!GeckoLib.hasInitialized)
        //    throw new RuntimeException("GeckoLib was never initialized! Please read the documentation!");

        return ANIMATIONS;
    }

    public static Map<ResourceLocation, BakedGeoModel> getBakedModels() {
        //if (!GeckoLib.hasInitialized)
        //    throw new RuntimeException("GeckoLib was never initialized! Please read the documentation!");

        return MODELS;
    }

    public static Map<ResourceLocation, JsonObject> getModelObjects() {
        return MODEL_OBJECT;
    }

    public static Map<ResourceLocation, JsonObject> getAnimationObject() {
        return ANIMATION_OBJECT;
    }


    public static void setAnimations(Map<ResourceLocation, JsonObject> objectMap) {
        Map<ResourceLocation, BakedAnimations> animationsMap = new Object2ObjectOpenHashMap<>();

        for(Map.Entry<ResourceLocation, JsonObject> entries : objectMap.entrySet()) {
            BakedAnimations animations = loadAnimationsFile(entries.getKey(), entries.getValue());
            animationsMap.put(entries.getKey(), animations);
        }
        ServerResourceCache.ANIMATIONS = animationsMap;
    }

    public static void setModels(Map<ResourceLocation, JsonObject> objectMap) {
        Map<ResourceLocation, BakedGeoModel> models = new Object2ObjectOpenHashMap<>();

        for(Map.Entry<ResourceLocation, JsonObject> entries : objectMap.entrySet()) {
            Model model = loadModelFile(entries.getKey(), entries.getValue());
            if (model.formatVersion() != FormatVersion.V_1_12_0)
                throw new GeckoLibException(entries.getKey(), "Unsupported geometry json version. Supported versions: 1.12.0");

            BakedGeoModel bakedGeoModel =  BakedModelFactory.getForNamespace(entries.getKey().getNamespace()).constructGeoModel(GeometryTree.fromModel(model));
            models.put(entries.getKey(), bakedGeoModel);
        }
        ServerResourceCache.MODELS = models;
    }

    public static Model loadModelFile(ResourceLocation location, JsonObject object) {
        return JsonUtil.GEO_GSON.fromJson(object, Model.class);
    }

    public static BakedAnimations loadAnimationsFile(ResourceLocation location, JsonObject object) {
        return JsonUtil.GEO_GSON.fromJson(GsonHelper.getAsJsonObject(object, "animations"), BakedAnimations.class);
    }

    //public static void registerReloadListener() {
//
//
    //    resourceManager.registerReloadListener(ServerResourceCache::reload);
    //}

    public static CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier stage, ResourceManager resourceManager,
                                                 ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor,
                                                 Executor gameExecutor) {
        Map<ResourceLocation, BakedAnimations> animations = new Object2ObjectOpenHashMap<>();
        Map<ResourceLocation, BakedGeoModel> models = new Object2ObjectOpenHashMap<>();
        Map<ResourceLocation, JsonObject> modelObject = new Object2ObjectOpenHashMap<>();
        Map<ResourceLocation, JsonObject> animationObject = new Object2ObjectOpenHashMap<>();
        return CompletableFuture.allOf(
                        loadAnimations(backgroundExecutor, resourceManager, animations::put),
                        loadModels(backgroundExecutor, resourceManager, models::put),
                        loadObject(backgroundExecutor, resourceManager, modelObject::put, "geo"),
                        loadObject(backgroundExecutor, resourceManager, animationObject::put, "animations"))
                .thenCompose(stage::wait).thenAcceptAsync(empty -> {
                    ServerResourceCache.ANIMATION_OBJECT = animationObject;
                    ServerResourceCache.MODEL_OBJECT = modelObject;
                    ServerResourceCache.ANIMATIONS = animations;
                    ServerResourceCache.MODELS = models;
                }, gameExecutor);
    }

    private static CompletableFuture<Void> loadObject(Executor backgroundExecutor, ResourceManager resourceManager, BiConsumer<ResourceLocation, JsonObject> elementConsumer, String type) {
        return loadResources(backgroundExecutor, resourceManager, type, resource ->
                FileLoader.loadFile(resource, resourceManager), elementConsumer);
    }


    private static CompletableFuture<Void> loadAnimations(Executor backgroundExecutor, ResourceManager resourceManager, BiConsumer<ResourceLocation, BakedAnimations> elementConsumer) {
        return loadResources(backgroundExecutor, resourceManager, "animations", resource ->
                FileLoader.loadAnimationsFile(resource, resourceManager), elementConsumer);
    }

    private static CompletableFuture<Void> loadModels(Executor backgroundExecutor, ResourceManager resourceManager, BiConsumer<ResourceLocation, BakedGeoModel> elementConsumer) {
        return loadResources(backgroundExecutor, resourceManager, "geo", resource -> {
            Model model = FileLoader.loadModelFile(resource, resourceManager);

            if (model.formatVersion() != FormatVersion.V_1_12_0)
                throw new GeckoLibException(resource, "Unsupported geometry json version. Supported versions: 1.12.0");

            return BakedModelFactory.getForNamespace(resource.getNamespace()).constructGeoModel(GeometryTree.fromModel(model));
        }, elementConsumer);
    }

    private static <T> CompletableFuture<Void> loadResources(Executor executor, ResourceManager resourceManager, String type, Function<ResourceLocation, T> loader, BiConsumer<ResourceLocation, T> map) {
        return CompletableFuture.supplyAsync(
        () -> resourceManager.listResources(type, fileName -> fileName.toString().endsWith(".json")), executor)
        .thenApplyAsync(resources -> {
            Map<ResourceLocation, CompletableFuture<T>> tasks = new Object2ObjectOpenHashMap<>();

            for (ResourceLocation resource : resources.keySet()) {
                tasks.put(resource, CompletableFuture.supplyAsync(() -> loader.apply(resource), executor));
            }

            return tasks;
        }, executor)
        .thenAcceptAsync(tasks -> {
            for (Map.Entry<ResourceLocation, CompletableFuture<T>> entry : tasks.entrySet()) {
                // Skip known namespaces that use an "animation" folder as well
                if (!EXCLUDED_NAMESPACES.contains(entry.getKey().getNamespace().toLowerCase(Locale.ROOT)))
                    map.accept(entry.getKey(), entry.getValue().join());
            }
        }, executor);
    }
}
