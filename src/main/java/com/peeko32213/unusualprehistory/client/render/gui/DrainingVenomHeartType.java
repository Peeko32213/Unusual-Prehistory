package com.peeko32213.unusualprehistory.client.render.gui;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public enum DrainingVenomHeartType {
    DRAINING_VENOM(0);

    public static final ResourceLocation ATLAS = new ResourceLocation(UnusualPrehistory.MODID, "textures/gui/icons.png");
    public static final int ATLAS_W = 128;
    public static final int ATLAS_H = 9;
    private static final int SIZE = 9;

    private final int verticalIndex;

    DrainingVenomHeartType(int verticalIndex) {
        this.verticalIndex = verticalIndex;
    }

    public Pair<Integer, Integer> getHeartPos(boolean hardcore) {
        int y = this.verticalIndex * SIZE;
        int xMult = hardcore ? 2 : 0;
        int x = xMult * SIZE;
        return Pair.of(x, y);
    }

    public Pair<Integer, Integer> getHalfHeartPos(boolean hardcore) {
        int y = this.verticalIndex * SIZE;
        int xMult = hardcore ? 3 : 1;
        int x = xMult * SIZE;
        return Pair.of(x, y);
    }

    public static DrainingVenomHeartType getType(Player player) {
        DrainingVenomHeartType type = null;
        if (player.hasEffect(UPEffects.HEALTH_REDUCTION.get())) {
            type = DrainingVenomHeartType.DRAINING_VENOM;
        }
        return type;
    }
}
