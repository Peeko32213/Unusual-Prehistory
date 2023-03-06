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
    public static ForgeConfigSpec.ConfigValue<Double> SCREEN_SHAKE_BRACHI_RANGE;
    public static ForgeConfigSpec.ConfigValue<Double> SCREEN_SHAKE_TEEN_BRACHI_RANGE;
    public static ForgeConfigSpec.ConfigValue<Double> SCREEN_SHAKE_REX_RANGE;
    public static ForgeConfigSpec.ConfigValue<Float> BRACHI_SOUND_VOLUME;
    public static ForgeConfigSpec.ConfigValue<Float> BRACHI_TEEN_SOUND_VOLUME;
    public static ForgeConfigSpec.ConfigValue<Float> REX_SOUND_VOLUME;
    public static ForgeConfigSpec.ConfigValue<Integer> SCREEN_SHAKE_BRACHI_AMPLIFIER;
    public static ForgeConfigSpec.ConfigValue<Integer> SCREEN_SHAKE_TEEN_BRACHI_AMPLIFIER;
    public static ForgeConfigSpec.ConfigValue<Integer> SCREEN_SHAKE_REX_AMPLIFIER;
    public static ForgeConfigSpec.ConfigValue<Boolean> BRACHI_EXPERIMENTAL_FOOTPRINTS;
    private static void setupConfig(ForgeConfigSpec.Builder builder) {
        builder.comment(UnusualPrehistory.MODID + " Config");

        SCREEN_SHAKE = builder.comment("All screen shakes").define("screen_shake", true);
        SCREEN_SHAKE_BRACHI = builder.comment("Screen shake brachi").define("screen_shake_brachi", true);
        SCREEN_SHAKE_BRACHI_RANGE = builder.comment("Screen shake brachi range").define("screen_shake_brachi_range", 10.0D);
        SCREEN_SHAKE_TEEN_BRACHI_RANGE = builder.comment("Screen shake brachi teen range").define("screen_shake_brachi_teen_range", 5.0D);
        SCREEN_SHAKE_BRACHI_AMPLIFIER = builder.comment("Screen shake brachi amplifier").define("screen_shake_brachi_amplifier", 2);
        SCREEN_SHAKE_TEEN_BRACHI_AMPLIFIER = builder.comment("Screen shake brachi teen amplifier").define("screen_shake_brachi_teen_amplifier", 1);
        BRACHI_SOUND_VOLUME = builder.comment("Brachi sound volume").define("brachi_sound_volume", 3.0F);
        BRACHI_TEEN_SOUND_VOLUME = builder.comment("Brachi teen sound volume").define("brachi_teen_sound_volume", 1.5F);
        BRACHI_EXPERIMENTAL_FOOTPRINTS = builder.comment("Brachi footprints").define("brachi_experimental_footprints", false);


        SCREEN_SHAKE_REX = builder.comment("Screen shake rex").define("screen_shake_rex", true);
        SCREEN_SHAKE_REX_RANGE = builder.comment("Screen shake rex range").define("screen_shake_rex_range", 10.0D);
        SCREEN_SHAKE_REX_AMPLIFIER = builder.comment("Screen shake rex amplifier").define("screen_shake_rex_amplifier", 2);
        REX_SOUND_VOLUME = builder.comment("Rex sound volume").define("rex_sound_volume", 0.5F);
    }
}
