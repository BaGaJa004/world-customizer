package net.bagaja.worldcustomizer.config;

public class OreSettings {
    // These hold the "pending" values shown in the GUI
    // Defaults match what's in WorldGenConfig
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
}