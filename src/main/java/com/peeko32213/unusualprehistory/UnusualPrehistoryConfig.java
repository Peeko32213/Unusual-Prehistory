package com.peeko32213.unusualprehistory;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class UnusualPrehistoryConfig {

    public static ConfigValue<Boolean> SCREEN_SHAKE;
    public static ConfigValue<Boolean> SCREEN_SHAKE_BRACHI;
    public static ConfigValue<Double> SCREEN_SHAKE_BRACHI_RANGE;
    public static ConfigValue<Integer> SCREEN_SHAKE_BRACHI_AMPLIFIER;
    public static ConfigValue<Double> SCREEN_SHAKE_TEEN_BRACHI_RANGE;
    public static ConfigValue<Integer> SCREEN_SHAKE_TEEN_BRACHI_AMPLIFIER;
    public static ConfigValue<Boolean> SCREEN_SHAKE_PARACERATHERIUM;
    public static ConfigValue<Double> SCREEN_SHAKE_PARACERATHERIUM_RANGE;
    public static ConfigValue<Integer> SCREEN_SHAKE_PARACERATHERIUM_AMPLIFIER;
    public static ConfigValue<Boolean> SCREEN_SHAKE_REX;
    public static ConfigValue<Double> SCREEN_SHAKE_REX_RANGE;
    public static ConfigValue<Integer> SCREEN_SHAKE_REX_AMPLIFIER;
    public static ConfigValue<Boolean> REX_COLLISION;
    public static ConfigValue<Boolean> PARACERATHERIUM_COLLISON;
    public static ConfigValue<Boolean> TRIKE_COLLISON;
    public static ConfigValue<Boolean> MAMMOTH_COLLISON;
    public static ConfigValue<Boolean> BRACHI_COLLISON;
    public static ConfigValue<Boolean> NATURAL_PREHISTORIC_MOB_SPAWNS;
    public static ConfigValue<Boolean> NATURAL_PREHISTORIC_GENERATION;
    public static ConfigValue<Boolean> NO_FOSSILS;

    public static final ForgeConfigSpec COMMON;
        static {

            ForgeConfigSpec.Builder CONFIG_BUILDER = new ForgeConfigSpec.Builder();
            CONFIG_BUILDER.comment("Unusual Prehistory Configs").push("unusual_prehistory_common");

            CONFIG_BUILDER.comment("Screen shake configs").push("screen_shake_config");
            SCREEN_SHAKE = CONFIG_BUILDER.comment("All screen shakes").define("screen_shake", true);
            SCREEN_SHAKE_BRACHI = CONFIG_BUILDER.comment("Brachiosaurus screen shake").define("screen_shake_brachi", true);
            SCREEN_SHAKE_BRACHI_RANGE = CONFIG_BUILDER.comment("Brachiosaurus screen shake range").define("screen_shake_brachi_range", 10.0D);
            SCREEN_SHAKE_TEEN_BRACHI_RANGE = CONFIG_BUILDER.comment("Adolescent Brachiosaurus screen shake range").define("screen_shake_brachi_teen_range", 5.0D);
            SCREEN_SHAKE_BRACHI_AMPLIFIER = CONFIG_BUILDER.comment("Brachiosaurus screen shake amplifier").define("screen_shake_brachi_amplifier", 1);
            SCREEN_SHAKE_TEEN_BRACHI_AMPLIFIER = CONFIG_BUILDER.comment("Adolescent Brachiosaurus screen shake amplifier").define("screen_shake_brachi_teen_amplifier", 0);
            SCREEN_SHAKE_PARACERATHERIUM = CONFIG_BUILDER.comment("Paraceratherium screen shake").define("screen_shake_paraceratherium", true);
            SCREEN_SHAKE_PARACERATHERIUM_RANGE = CONFIG_BUILDER.comment("Paraceratherium screen shake range").define("screen_shake_paraceratherium_range", 8.0D);
            SCREEN_SHAKE_PARACERATHERIUM_AMPLIFIER = CONFIG_BUILDER.comment("Paraceratherium screen shake amplifier").define("screen_shake_paraceratherium_amplifier", 1);
            SCREEN_SHAKE_REX = CONFIG_BUILDER.comment("Tyrannosaurus screen shake").define("screen_shake_rex", true);
            SCREEN_SHAKE_REX_RANGE = CONFIG_BUILDER.comment("Tyrannosaurus screen shake range").define("screen_shake_rex_range", 8.0D);
            SCREEN_SHAKE_REX_AMPLIFIER = CONFIG_BUILDER.comment("Tyrannosaurus screen shake amplifier").define("screen_shake_rex_amplifier", 1);

            CONFIG_BUILDER.pop();
            CONFIG_BUILDER.comment("Collision configs").push("collision_config");;
            BRACHI_COLLISON = CONFIG_BUILDER.comment("Brachiosaurus hitbox collision").define("brachi_collision", true);
            MAMMOTH_COLLISON = CONFIG_BUILDER.comment("Mammoth hitbox collision").define("mammoth_collision", false);
            PARACERATHERIUM_COLLISON = CONFIG_BUILDER.comment("Paraceratherium hitbox collision").define("paraceratherium_collision", true);
            REX_COLLISION = CONFIG_BUILDER.comment("Tyrannosaurus hitbox collision").define("rex_collision", false);
            TRIKE_COLLISON = CONFIG_BUILDER.comment("Triceratops hitbox collision").define("trike_collision", false);

            CONFIG_BUILDER.pop();
            CONFIG_BUILDER.comment("Misc configs").push("misc_config");
            NATURAL_PREHISTORIC_MOB_SPAWNS = CONFIG_BUILDER.comment("Automatically enable the natural prehistoric mob spawns datapack").define("natural_prehistoric_mob_spawns", false);
            NATURAL_PREHISTORIC_GENERATION = CONFIG_BUILDER.comment("Automatically enable the natural prehistoric generation datapack").define("natural_prehistoric_generation", false);
            NO_FOSSILS = CONFIG_BUILDER.comment("Automatically enable the no fossils datapack").define("no_fossils", false);

            COMMON = CONFIG_BUILDER.build();
    }
}
