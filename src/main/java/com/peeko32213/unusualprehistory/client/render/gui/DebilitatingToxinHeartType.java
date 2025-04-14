package com.peeko32213.unusualprehistory.client.render.gui;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.tuple.Pair;

public enum DebilitatingToxinHeartType {
    DEBILITATING_TOXIN(2);

    public static final ResourceLocation ATLAS = new ResourceLocation(UnusualPrehistory.MODID, "textures/gui/icons.png");
    public static final int ATLAS_W = 128;
    public static final int ATLAS_H = 9;
    private static final int SIZE = 9;

    private final int verticalIndex;

    DebilitatingToxinHeartType(int verticalIndex) {
        this.verticalIndex = verticalIndex;
    }

    public Pair<Integer, Integer> getHeartPos(boolean hardcore) {
        int y = this.verticalIndex * SIZE;
        int xMult = hardcore ? 6 : 4;
        int x = xMult * SIZE;
        return Pair.of(x, y);
    }

    public Pair<Integer, Integer> getHalfHeartPos(boolean hardcore) {
        int y = this.verticalIndex * SIZE;
        int xMult = hardcore ? 7 : 5;
        int x = xMult * SIZE;
        return Pair.of(x, y);
    }

    public static DebilitatingToxinHeartType getType(Player player) {
        DebilitatingToxinHeartType type = null;
        if (player.hasEffect(UPEffects.PREVENT_CLICK.get())) {
            type = DebilitatingToxinHeartType.DEBILITATING_TOXIN;
        }
        return type;
    }
}
