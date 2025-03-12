package com.peeko32213.unusualprehistory.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

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
}
