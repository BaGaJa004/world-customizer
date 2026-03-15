package net.bagaja.worldcustomizer.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class WorldGenConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    // ---- COAL ----
    public static final ForgeConfigSpec.IntValue COAL_VEIN_SIZE;
    public static final ForgeConfigSpec.IntValue COAL_VEINS_PER_CHUNK;
    public static final ForgeConfigSpec.IntValue COAL_MIN_HEIGHT;
    public static final ForgeConfigSpec.IntValue COAL_MAX_HEIGHT;

    // ---- IRON ----
    public static final ForgeConfigSpec.IntValue IRON_VEIN_SIZE;
    public static final ForgeConfigSpec.IntValue IRON_VEINS_PER_CHUNK;
    public static final ForgeConfigSpec.IntValue IRON_MIN_HEIGHT;
    public static final ForgeConfigSpec.IntValue IRON_MAX_HEIGHT;

    // ---- GOLD ----
    public static final ForgeConfigSpec.IntValue GOLD_VEIN_SIZE;
    public static final ForgeConfigSpec.IntValue GOLD_VEINS_PER_CHUNK;
    public static final ForgeConfigSpec.IntValue GOLD_MIN_HEIGHT;
    public static final ForgeConfigSpec.IntValue GOLD_MAX_HEIGHT;

    // ---- DIAMOND ----
    public static final ForgeConfigSpec.IntValue DIAMOND_VEIN_SIZE;
    public static final ForgeConfigSpec.IntValue DIAMOND_VEINS_PER_CHUNK;
    public static final ForgeConfigSpec.IntValue DIAMOND_MIN_HEIGHT;
    public static final ForgeConfigSpec.IntValue DIAMOND_MAX_HEIGHT;

    // ---- REDSTONE ----
    public static final ForgeConfigSpec.IntValue REDSTONE_VEIN_SIZE;
    public static final ForgeConfigSpec.IntValue REDSTONE_VEINS_PER_CHUNK;
    public static final ForgeConfigSpec.IntValue REDSTONE_MIN_HEIGHT;
    public static final ForgeConfigSpec.IntValue REDSTONE_MAX_HEIGHT;

    // ---- LAPIS ----
    public static final ForgeConfigSpec.IntValue LAPIS_VEIN_SIZE;
    public static final ForgeConfigSpec.IntValue LAPIS_VEINS_PER_CHUNK;
    public static final ForgeConfigSpec.IntValue LAPIS_MIN_HEIGHT;
    public static final ForgeConfigSpec.IntValue LAPIS_MAX_HEIGHT;

    // ---- COPPER ----
    public static final ForgeConfigSpec.IntValue COPPER_VEIN_SIZE;
    public static final ForgeConfigSpec.IntValue COPPER_VEINS_PER_CHUNK;
    public static final ForgeConfigSpec.IntValue COPPER_MIN_HEIGHT;
    public static final ForgeConfigSpec.IntValue COPPER_MAX_HEIGHT;

    // ---- EMERALD ----
    public static final ForgeConfigSpec.IntValue EMERALD_VEIN_SIZE;
    public static final ForgeConfigSpec.IntValue EMERALD_VEINS_PER_CHUNK;
    public static final ForgeConfigSpec.IntValue EMERALD_MIN_HEIGHT;
    public static final ForgeConfigSpec.IntValue EMERALD_MAX_HEIGHT;

    static {
        BUILDER.comment("=== World Customizer - Ore Generation Settings ===",
                "veinSize       = blocks per vein",
                "veinsPerChunk  = veins attempted per chunk (0 = disabled)",
                "minHeight      = lowest Y level to spawn (-64 = bedrock)",
                "maxHeight      = highest Y level to spawn");

        BUILDER.push("coal");
        COAL_VEIN_SIZE       = BUILDER.comment("Default: 17").defineInRange("veinSize", 17, 1, 64);
        COAL_VEINS_PER_CHUNK = BUILDER.comment("Default: 20").defineInRange("veinsPerChunk", 20, 0, 100);
        COAL_MIN_HEIGHT      = BUILDER.comment("Default: 0") .defineInRange("minHeight", 0, -64, 320);
        COAL_MAX_HEIGHT      = BUILDER.comment("Default: 192").defineInRange("maxHeight", 192, -64, 320);
        BUILDER.pop();

        BUILDER.push("iron");
        IRON_VEIN_SIZE       = BUILDER.comment("Default: 9") .defineInRange("veinSize", 9, 1, 64);
        IRON_VEINS_PER_CHUNK = BUILDER.comment("Default: 10").defineInRange("veinsPerChunk", 10, 0, 100);
        IRON_MIN_HEIGHT      = BUILDER.comment("Default: -64").defineInRange("minHeight", -64, -64, 320);
        IRON_MAX_HEIGHT      = BUILDER.comment("Default: 72").defineInRange("maxHeight", 72, -64, 320);
        BUILDER.pop();

        BUILDER.push("gold");
        GOLD_VEIN_SIZE       = BUILDER.comment("Default: 9") .defineInRange("veinSize", 9, 1, 64);
        GOLD_VEINS_PER_CHUNK = BUILDER.comment("Default: 4") .defineInRange("veinsPerChunk", 4, 0, 100);
        GOLD_MIN_HEIGHT      = BUILDER.comment("Default: -64").defineInRange("minHeight", -64, -64, 320);
        GOLD_MAX_HEIGHT      = BUILDER.comment("Default: 32").defineInRange("maxHeight", 32, -64, 320);
        BUILDER.pop();

        BUILDER.push("diamond");
        DIAMOND_VEIN_SIZE       = BUILDER.comment("Default: 8").defineInRange("veinSize", 8, 1, 64);
        DIAMOND_VEINS_PER_CHUNK = BUILDER.comment("Default: 1").defineInRange("veinsPerChunk", 1, 0, 100);
        DIAMOND_MIN_HEIGHT      = BUILDER.comment("Default: -64").defineInRange("minHeight", -64, -64, 320);
        DIAMOND_MAX_HEIGHT      = BUILDER.comment("Default: 16").defineInRange("maxHeight", 16, -64, 320);
        BUILDER.pop();

        BUILDER.push("redstone");
        REDSTONE_VEIN_SIZE       = BUILDER.comment("Default: 8") .defineInRange("veinSize", 8, 1, 64);
        REDSTONE_VEINS_PER_CHUNK = BUILDER.comment("Default: 4") .defineInRange("veinsPerChunk", 4, 0, 100);
        REDSTONE_MIN_HEIGHT      = BUILDER.comment("Default: -64").defineInRange("minHeight", -64, -64, 320);
        REDSTONE_MAX_HEIGHT      = BUILDER.comment("Default: 15").defineInRange("maxHeight", 15, -64, 320);
        BUILDER.pop();

        BUILDER.push("lapis");
        LAPIS_VEIN_SIZE       = BUILDER.comment("Default: 7").defineInRange("veinSize", 7, 1, 64);
        LAPIS_VEINS_PER_CHUNK = BUILDER.comment("Default: 2").defineInRange("veinsPerChunk", 2, 0, 100);
        LAPIS_MIN_HEIGHT      = BUILDER.comment("Default: -64").defineInRange("minHeight", -64, -64, 320);
        LAPIS_MAX_HEIGHT      = BUILDER.comment("Default: 64").defineInRange("maxHeight", 64, -64, 320);
        BUILDER.pop();

        BUILDER.push("copper");
        COPPER_VEIN_SIZE       = BUILDER.comment("Default: 10").defineInRange("veinSize", 10, 1, 64);
        COPPER_VEINS_PER_CHUNK = BUILDER.comment("Default: 6") .defineInRange("veinsPerChunk", 6, 0, 100);
        COPPER_MIN_HEIGHT      = BUILDER.comment("Default: -16").defineInRange("minHeight", -16, -64, 320);
        COPPER_MAX_HEIGHT      = BUILDER.comment("Default: 112").defineInRange("maxHeight", 112, -64, 320);
        BUILDER.pop();

        BUILDER.push("emerald");
        EMERALD_VEIN_SIZE       = BUILDER.comment("Default: 3").defineInRange("veinSize", 3, 1, 64);
        EMERALD_VEINS_PER_CHUNK = BUILDER.comment("Default: 1").defineInRange("veinsPerChunk", 1, 0, 100);
        EMERALD_MIN_HEIGHT      = BUILDER.comment("Default: -16").defineInRange("minHeight", -16, -64, 320);
        EMERALD_MAX_HEIGHT      = BUILDER.comment("Default: 480").defineInRange("maxHeight", 480, -64, 480);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}