package com.peeko32213.unusualprehistory.common.config;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraftforge.common.ForgeConfigSpec;

public class UnusualPrehistoryConfig {

    public static final ForgeConfigSpec CONFIG_BUILDER;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        CONFIG_BUILDER = configBuilder.build();
    }

    public static ForgeConfigSpec.ConfigValue<Boolean> SCREEN_SHAKE;
    public static ForgeConfigSpec.ConfigValue<Boolean> SCREEN_SHAKE_BRACHI;
    public static ForgeConfigSpec.ConfigValue<Boolean> SCREEN_SHAKE_REX;
    private static void setupConfig(ForgeConfigSpec.Builder builder) {
        builder.comment(UnusualPrehistory.MODID + " Config");

        SCREEN_SHAKE = builder.comment("All screen shakes").define("screen_shake", true);
        SCREEN_SHAKE_BRACHI = builder.comment("Screen shake brachi").define("screen_shake_brachi", true);
        SCREEN_SHAKE_REX = builder.comment("Screen shake rex").define("screen_shake_rex", true);
    }
}
