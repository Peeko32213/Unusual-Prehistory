package com.peeko32213.unusualprehistory.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ClientUtils {

    public static List<UUID> blockedEntityRenders = new ArrayList<>();
    /**
     * Get the player on the client
     */
    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    /**
     * Gets the current level on the client
     */
    public static Level getLevel() {
        return Minecraft.getInstance().level;
    }

    public static Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    public static boolean isFirstPersonPlayer(Entity entity) {
        return entity.equals(Minecraft.getInstance().cameraEntity) && Minecraft.getInstance().options.getCameraType().isFirstPerson();
    }

    public static void blockRenderingEntity(UUID id) {
        blockedEntityRenders.add(id);
    }

    public static void releaseRenderingEntity(UUID id) {
        blockedEntityRenders.remove(id);
    }

    public static RenderType getRenderType(String location, GeoAnimatable entity, GeoRenderer renderer) {

        if (location == null) {
            // Fallback if not specified
            return ClientUtils.entityCutout(entity, renderer);
        }
        switch (location) {
            case "translucent":
                return ClientUtils.translucent(entity, renderer);
            case "cutout":
                return ClientUtils.entityCutout(entity, renderer);
            case "cutout_no_cull":
                return ClientUtils.entityCutoutNoCull(entity, renderer);
            default:
                return ClientUtils.entityCutout(entity, renderer);
        }
    }


    public static RenderType entityCutout(GeoAnimatable entity, GeoRenderer entityRenderer) {
        return RenderType.entityCutout(entityRenderer.getTextureLocation(entity));
    }

    public static RenderType entityCutoutNoCull(GeoAnimatable entity, GeoRenderer entityRenderer) {
        return RenderType.entityCutoutNoCull(entityRenderer.getTextureLocation(entity));
    }

    public static RenderType translucent(GeoAnimatable entity, GeoRenderer entityRenderer) {
        return RenderType.entityTranslucent(entityRenderer.getTextureLocation(entity));
    }
}
