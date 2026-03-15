package net.bagaja.worldcustomizer.config;

public class OreSettings {

    // ── Overworld Ores ──────────────────────────────────────────
    public static int COAL_VEIN_SIZE = 17,    COAL_VEINS_PER_CHUNK = 20,
            COAL_MIN_HEIGHT = 0,    COAL_MAX_HEIGHT = 192;
    public static int IRON_VEIN_SIZE = 9,     IRON_VEINS_PER_CHUNK = 10,
            IRON_MIN_HEIGHT = -64,  IRON_MAX_HEIGHT = 72;
    public static int GOLD_VEIN_SIZE = 9,     GOLD_VEINS_PER_CHUNK = 4,
            GOLD_MIN_HEIGHT = -64,  GOLD_MAX_HEIGHT = 32;
    public static int DIAMOND_VEIN_SIZE = 8,  DIAMOND_VEINS_PER_CHUNK = 1,
            DIAMOND_MIN_HEIGHT = -64, DIAMOND_MAX_HEIGHT = 16;
    public static int REDSTONE_VEIN_SIZE = 8, REDSTONE_VEINS_PER_CHUNK = 4,
            REDSTONE_MIN_HEIGHT = -64, REDSTONE_MAX_HEIGHT = 15;
    public static int LAPIS_VEIN_SIZE = 7,    LAPIS_VEINS_PER_CHUNK = 2,
            LAPIS_MIN_HEIGHT = -64, LAPIS_MAX_HEIGHT = 64;
    public static int COPPER_VEIN_SIZE = 10,  COPPER_VEINS_PER_CHUNK = 6,
            COPPER_MIN_HEIGHT = -16, COPPER_MAX_HEIGHT = 112;
    public static int EMERALD_VEIN_SIZE = 3,  EMERALD_VEINS_PER_CHUNK = 1,
            EMERALD_MIN_HEIGHT = -16, EMERALD_MAX_HEIGHT = 480;

    // ── Stone Variants ──────────────────────────────────────────
    public static int DIRT_VEIN_SIZE = 33,     DIRT_VEINS_PER_CHUNK = 7,
            DIRT_MIN_HEIGHT = 0,     DIRT_MAX_HEIGHT = 160;
    public static int GRAVEL_VEIN_SIZE = 33,   GRAVEL_VEINS_PER_CHUNK = 8,
            GRAVEL_MIN_HEIGHT = 0,   GRAVEL_MAX_HEIGHT = 160;
    public static int GRANITE_VEIN_SIZE = 64,  GRANITE_VEINS_PER_CHUNK = 2,
            GRANITE_MIN_HEIGHT = 0,  GRANITE_MAX_HEIGHT = 60;
    public static int DIORITE_VEIN_SIZE = 64,  DIORITE_VEINS_PER_CHUNK = 2,
            DIORITE_MIN_HEIGHT = 0,  DIORITE_MAX_HEIGHT = 60;
    public static int ANDESITE_VEIN_SIZE = 64, ANDESITE_VEINS_PER_CHUNK = 2,
            ANDESITE_MIN_HEIGHT = 0, ANDESITE_MAX_HEIGHT = 60;
    public static int TUFF_VEIN_SIZE = 64,     TUFF_VEINS_PER_CHUNK = 2,
            TUFF_MIN_HEIGHT = -64,   TUFF_MAX_HEIGHT = 0;
    public static int CALCITE_VEIN_SIZE = 64,  CALCITE_VEINS_PER_CHUNK = 2,
            CALCITE_MIN_HEIGHT = -64, CALCITE_MAX_HEIGHT = 0;
    public static int DEEPSLATE_VEIN_SIZE = 64, DEEPSLATE_VEINS_PER_CHUNK = 2,
            DEEPSLATE_MIN_HEIGHT = -64, DEEPSLATE_MAX_HEIGHT = -8;

    // ── Nether ──────────────────────────────────────────────────
    public static int ANCIENT_DEBRIS_VEIN_SIZE = 3, ANCIENT_DEBRIS_VEINS_PER_CHUNK = 1,
            ANCIENT_DEBRIS_MIN_HEIGHT = 8, ANCIENT_DEBRIS_MAX_HEIGHT = 24;
    public static int NETHER_GOLD_VEIN_SIZE = 10,   NETHER_GOLD_VEINS_PER_CHUNK = 10,
            NETHER_GOLD_MIN_HEIGHT = 10,  NETHER_GOLD_MAX_HEIGHT = 117;
    public static int NETHER_QUARTZ_VEIN_SIZE = 14, NETHER_QUARTZ_VEINS_PER_CHUNK = 16,
            NETHER_QUARTZ_MIN_HEIGHT = 10, NETHER_QUARTZ_MAX_HEIGHT = 117;

    // ── Dungeons ─────────────────────────────────────────────────
    public static int DUNGEON_COUNT = 10;       // surface caves (Y 0 to top)
    public static int DUNGEON_COUNT_DEEP = 4;   // deep caves (Y -64 to -1)
}