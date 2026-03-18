package net.bagaja.worldcustomizer.config;

import com.google.gson.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;
import java.nio.file.*;

public class OreSettings {
    public static boolean CUSTOM_SETTINGS_ENABLED = true;

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
    public static int CLAY_VEIN_SIZE = 4,  CLAY_VEINS_PER_CHUNK = 46,
            CLAY_MIN_HEIGHT = 0,  CLAY_MAX_HEIGHT = 160;

    // ── Nether ──────────────────────────────────────────────────
    public static int ANCIENT_DEBRIS_VEIN_SIZE = 3, ANCIENT_DEBRIS_VEINS_PER_CHUNK = 1,
            ANCIENT_DEBRIS_MIN_HEIGHT = 8, ANCIENT_DEBRIS_MAX_HEIGHT = 24;
    public static int NETHER_GOLD_VEIN_SIZE = 10,   NETHER_GOLD_VEINS_PER_CHUNK = 10,
            NETHER_GOLD_MIN_HEIGHT = 10,  NETHER_GOLD_MAX_HEIGHT = 117;
    public static int NETHER_QUARTZ_VEIN_SIZE = 14, NETHER_QUARTZ_VEINS_PER_CHUNK = 16,
            NETHER_QUARTZ_MIN_HEIGHT = 10, NETHER_QUARTZ_MAX_HEIGHT = 117;
    public static int BLACKSTONE_VEIN_SIZE = 33,  BLACKSTONE_VEINS_PER_CHUNK = 2,
            BLACKSTONE_MIN_HEIGHT = 5,  BLACKSTONE_MAX_HEIGHT = 31;
    public static int SOUL_SAND_VEIN_SIZE = 12,   SOUL_SAND_VEINS_PER_CHUNK = 12,
            SOUL_SAND_MIN_HEIGHT = 0,   SOUL_SAND_MAX_HEIGHT = 64;
    public static int MAGMA_VEIN_SIZE = 33,       MAGMA_VEINS_PER_CHUNK = 4,
            MAGMA_MIN_HEIGHT = 27,      MAGMA_MAX_HEIGHT = 37;

    // ── Dungeons ─────────────────────────────────────────────────
    public static int DUNGEON_COUNT = 10;
    public static int DUNGEON_COUNT_DEEP = 4;

    // ── Fluid Settings ───────────────────────────────────────────
    public enum FluidChoice { WATER, LAVA, AIR }
    public static FluidChoice OVERWORLD_FLUID = FluidChoice.WATER;
    public static FluidChoice NETHER_FLUID    = FluidChoice.LAVA;

    // ── Cave / Ravine Settings ───────────────────────────────────
    public static boolean CAVES_ENABLED   = true;
    public static boolean RAVINES_ENABLED = true;

    public static boolean LAKES_ENABLED              = true;
    public static boolean LOCAL_MODIFICATIONS_ENABLED = true;
    public static boolean SURFACE_STRUCTURES_ENABLED  = true;
    public static boolean UNDERGROUND_DECORATION_ENABLED = true;
    public static boolean FLUID_SPRINGS_ENABLED       = true;
    public static boolean VEGETAL_DECORATION_ENABLED  = true;

    // ── Save / Load ──────────────────────────────────────────────
    private static final String FILE_NAME = "worldcustomizer.json";

    public static void save(MinecraftServer server) {
        try {
            Path path = server.getWorldPath(LevelResource.ROOT).resolve(FILE_NAME);
            JsonObject json = new JsonObject();

            json.addProperty("custom_settings_enabled", CUSTOM_SETTINGS_ENABLED);

            // Ores
            saveOre(json, "coal",     COAL_VEIN_SIZE,     COAL_VEINS_PER_CHUNK,     COAL_MIN_HEIGHT,     COAL_MAX_HEIGHT);
            saveOre(json, "iron",     IRON_VEIN_SIZE,     IRON_VEINS_PER_CHUNK,     IRON_MIN_HEIGHT,     IRON_MAX_HEIGHT);
            saveOre(json, "gold",     GOLD_VEIN_SIZE,     GOLD_VEINS_PER_CHUNK,     GOLD_MIN_HEIGHT,     GOLD_MAX_HEIGHT);
            saveOre(json, "diamond",  DIAMOND_VEIN_SIZE,  DIAMOND_VEINS_PER_CHUNK,  DIAMOND_MIN_HEIGHT,  DIAMOND_MAX_HEIGHT);
            saveOre(json, "redstone", REDSTONE_VEIN_SIZE, REDSTONE_VEINS_PER_CHUNK, REDSTONE_MIN_HEIGHT, REDSTONE_MAX_HEIGHT);
            saveOre(json, "lapis",    LAPIS_VEIN_SIZE,    LAPIS_VEINS_PER_CHUNK,    LAPIS_MIN_HEIGHT,    LAPIS_MAX_HEIGHT);
            saveOre(json, "copper",   COPPER_VEIN_SIZE,   COPPER_VEINS_PER_CHUNK,   COPPER_MIN_HEIGHT,   COPPER_MAX_HEIGHT);
            saveOre(json, "emerald",  EMERALD_VEIN_SIZE,  EMERALD_VEINS_PER_CHUNK,  EMERALD_MIN_HEIGHT,  EMERALD_MAX_HEIGHT);
            saveOre(json, "dirt",     DIRT_VEIN_SIZE,     DIRT_VEINS_PER_CHUNK,     DIRT_MIN_HEIGHT,     DIRT_MAX_HEIGHT);
            saveOre(json, "gravel",   GRAVEL_VEIN_SIZE,   GRAVEL_VEINS_PER_CHUNK,   GRAVEL_MIN_HEIGHT,   GRAVEL_MAX_HEIGHT);
            saveOre(json, "granite",  GRANITE_VEIN_SIZE,  GRANITE_VEINS_PER_CHUNK,  GRANITE_MIN_HEIGHT,  GRANITE_MAX_HEIGHT);
            saveOre(json, "diorite",  DIORITE_VEIN_SIZE,  DIORITE_VEINS_PER_CHUNK,  DIORITE_MIN_HEIGHT,  DIORITE_MAX_HEIGHT);
            saveOre(json, "andesite", ANDESITE_VEIN_SIZE, ANDESITE_VEINS_PER_CHUNK, ANDESITE_MIN_HEIGHT, ANDESITE_MAX_HEIGHT);
            saveOre(json, "tuff",     TUFF_VEIN_SIZE,     TUFF_VEINS_PER_CHUNK,     TUFF_MIN_HEIGHT,     TUFF_MAX_HEIGHT);
            saveOre(json, "calcite",  CALCITE_VEIN_SIZE,  CALCITE_VEINS_PER_CHUNK,  CALCITE_MIN_HEIGHT,  CALCITE_MAX_HEIGHT);
            saveOre(json, "deepslate",DEEPSLATE_VEIN_SIZE,DEEPSLATE_VEINS_PER_CHUNK,DEEPSLATE_MIN_HEIGHT,DEEPSLATE_MAX_HEIGHT);
            saveOre(json, "clay", CLAY_VEIN_SIZE, CLAY_VEINS_PER_CHUNK, CLAY_MIN_HEIGHT, CLAY_MAX_HEIGHT);
            saveOre(json, "ancient_debris", ANCIENT_DEBRIS_VEIN_SIZE, ANCIENT_DEBRIS_VEINS_PER_CHUNK,
                    ANCIENT_DEBRIS_MIN_HEIGHT, ANCIENT_DEBRIS_MAX_HEIGHT);
            saveOre(json, "nether_gold",   NETHER_GOLD_VEIN_SIZE,   NETHER_GOLD_VEINS_PER_CHUNK,
                    NETHER_GOLD_MIN_HEIGHT,   NETHER_GOLD_MAX_HEIGHT);
            saveOre(json, "nether_quartz", NETHER_QUARTZ_VEIN_SIZE, NETHER_QUARTZ_VEINS_PER_CHUNK,
                    NETHER_QUARTZ_MIN_HEIGHT, NETHER_QUARTZ_MAX_HEIGHT);
            saveOre(json, "blackstone", BLACKSTONE_VEIN_SIZE, BLACKSTONE_VEINS_PER_CHUNK,
                    BLACKSTONE_MIN_HEIGHT, BLACKSTONE_MAX_HEIGHT);
            saveOre(json, "soul_sand",  SOUL_SAND_VEIN_SIZE,  SOUL_SAND_VEINS_PER_CHUNK,
                    SOUL_SAND_MIN_HEIGHT,  SOUL_SAND_MAX_HEIGHT);
            saveOre(json, "magma",      MAGMA_VEIN_SIZE,      MAGMA_VEINS_PER_CHUNK,
                    MAGMA_MIN_HEIGHT,      MAGMA_MAX_HEIGHT);

            // Basic
            json.addProperty("dungeon_count",      DUNGEON_COUNT);
            json.addProperty("dungeon_count_deep",  DUNGEON_COUNT_DEEP);
            json.addProperty("overworld_fluid",     OVERWORLD_FLUID.name());
            json.addProperty("nether_fluid",        NETHER_FLUID.name());

            json.addProperty("caves_enabled",   CAVES_ENABLED);
            json.addProperty("ravines_enabled", RAVINES_ENABLED);

            json.addProperty("lakes_enabled",                LAKES_ENABLED);
            json.addProperty("local_modifications_enabled",  LOCAL_MODIFICATIONS_ENABLED);
            json.addProperty("surface_structures_enabled",   SURFACE_STRUCTURES_ENABLED);
            json.addProperty("underground_decoration_enabled", UNDERGROUND_DECORATION_ENABLED);
            json.addProperty("fluid_springs_enabled",        FLUID_SPRINGS_ENABLED);
            json.addProperty("vegetal_decoration_enabled",   VEGETAL_DECORATION_ENABLED);

            Files.writeString(path, new GsonBuilder().setPrettyPrinting().create().toJson(json));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load(MinecraftServer server) {
        try {
            Path path = server.getWorldPath(LevelResource.ROOT).resolve(FILE_NAME);
            if (!Files.exists(path)) return; // no file = vanilla defaults

            JsonObject json = JsonParser.parseString(Files.readString(path)).getAsJsonObject();

            if (json.has("custom_settings_enabled"))
                CUSTOM_SETTINGS_ENABLED = json.get("custom_settings_enabled").getAsBoolean();

            loadOre(json, "coal",     vs -> COAL_VEIN_SIZE = vs,     vc -> COAL_VEINS_PER_CHUNK = vc,
                    mn -> COAL_MIN_HEIGHT = mn, mx -> COAL_MAX_HEIGHT = mx);
            loadOre(json, "iron",     vs -> IRON_VEIN_SIZE = vs,     vc -> IRON_VEINS_PER_CHUNK = vc,
                    mn -> IRON_MIN_HEIGHT = mn, mx -> IRON_MAX_HEIGHT = mx);
            loadOre(json, "gold",     vs -> GOLD_VEIN_SIZE = vs,     vc -> GOLD_VEINS_PER_CHUNK = vc,
                    mn -> GOLD_MIN_HEIGHT = mn, mx -> GOLD_MAX_HEIGHT = mx);
            loadOre(json, "diamond",  vs -> DIAMOND_VEIN_SIZE = vs,  vc -> DIAMOND_VEINS_PER_CHUNK = vc,
                    mn -> DIAMOND_MIN_HEIGHT = mn, mx -> DIAMOND_MAX_HEIGHT = mx);
            loadOre(json, "redstone", vs -> REDSTONE_VEIN_SIZE = vs, vc -> REDSTONE_VEINS_PER_CHUNK = vc,
                    mn -> REDSTONE_MIN_HEIGHT = mn, mx -> REDSTONE_MAX_HEIGHT = mx);
            loadOre(json, "lapis",    vs -> LAPIS_VEIN_SIZE = vs,    vc -> LAPIS_VEINS_PER_CHUNK = vc,
                    mn -> LAPIS_MIN_HEIGHT = mn, mx -> LAPIS_MAX_HEIGHT = mx);
            loadOre(json, "copper",   vs -> COPPER_VEIN_SIZE = vs,   vc -> COPPER_VEINS_PER_CHUNK = vc,
                    mn -> COPPER_MIN_HEIGHT = mn, mx -> COPPER_MAX_HEIGHT = mx);
            loadOre(json, "emerald",  vs -> EMERALD_VEIN_SIZE = vs,  vc -> EMERALD_VEINS_PER_CHUNK = vc,
                    mn -> EMERALD_MIN_HEIGHT = mn, mx -> EMERALD_MAX_HEIGHT = mx);
            loadOre(json, "dirt",     vs -> DIRT_VEIN_SIZE = vs,     vc -> DIRT_VEINS_PER_CHUNK = vc,
                    mn -> DIRT_MIN_HEIGHT = mn, mx -> DIRT_MAX_HEIGHT = mx);
            loadOre(json, "gravel",   vs -> GRAVEL_VEIN_SIZE = vs,   vc -> GRAVEL_VEINS_PER_CHUNK = vc,
                    mn -> GRAVEL_MIN_HEIGHT = mn, mx -> GRAVEL_MAX_HEIGHT = mx);
            loadOre(json, "granite",  vs -> GRANITE_VEIN_SIZE = vs,  vc -> GRANITE_VEINS_PER_CHUNK = vc,
                    mn -> GRANITE_MIN_HEIGHT = mn, mx -> GRANITE_MAX_HEIGHT = mx);
            loadOre(json, "diorite",  vs -> DIORITE_VEIN_SIZE = vs,  vc -> DIORITE_VEINS_PER_CHUNK = vc,
                    mn -> DIORITE_MIN_HEIGHT = mn, mx -> DIORITE_MAX_HEIGHT = mx);
            loadOre(json, "andesite", vs -> ANDESITE_VEIN_SIZE = vs, vc -> ANDESITE_VEINS_PER_CHUNK = vc,
                    mn -> ANDESITE_MIN_HEIGHT = mn, mx -> ANDESITE_MAX_HEIGHT = mx);
            loadOre(json, "tuff",     vs -> TUFF_VEIN_SIZE = vs,     vc -> TUFF_VEINS_PER_CHUNK = vc,
                    mn -> TUFF_MIN_HEIGHT = mn, mx -> TUFF_MAX_HEIGHT = mx);
            loadOre(json, "calcite",  vs -> CALCITE_VEIN_SIZE = vs,  vc -> CALCITE_VEINS_PER_CHUNK = vc,
                    mn -> CALCITE_MIN_HEIGHT = mn, mx -> CALCITE_MAX_HEIGHT = mx);
            loadOre(json, "deepslate",vs -> DEEPSLATE_VEIN_SIZE = vs,vc -> DEEPSLATE_VEINS_PER_CHUNK = vc,
                    mn -> DEEPSLATE_MIN_HEIGHT = mn, mx -> DEEPSLATE_MAX_HEIGHT = mx);
            loadOre(json, "clay",      vs -> CLAY_VEIN_SIZE = vs,      vc -> CLAY_VEINS_PER_CHUNK = vc,
                    mn -> CLAY_MIN_HEIGHT = mn,      mx -> CLAY_MAX_HEIGHT = mx);
            loadOre(json, "ancient_debris", vs -> ANCIENT_DEBRIS_VEIN_SIZE = vs,
                    vc -> ANCIENT_DEBRIS_VEINS_PER_CHUNK = vc,
                    mn -> ANCIENT_DEBRIS_MIN_HEIGHT = mn, mx -> ANCIENT_DEBRIS_MAX_HEIGHT = mx);
            loadOre(json, "nether_gold", vs -> NETHER_GOLD_VEIN_SIZE = vs,
                    vc -> NETHER_GOLD_VEINS_PER_CHUNK = vc,
                    mn -> NETHER_GOLD_MIN_HEIGHT = mn, mx -> NETHER_GOLD_MAX_HEIGHT = mx);
            loadOre(json, "nether_quartz", vs -> NETHER_QUARTZ_VEIN_SIZE = vs,
                    vc -> NETHER_QUARTZ_VEINS_PER_CHUNK = vc,
                    mn -> NETHER_QUARTZ_MIN_HEIGHT = mn, mx -> NETHER_QUARTZ_MAX_HEIGHT = mx);
            loadOre(json, "blackstone", vs -> BLACKSTONE_VEIN_SIZE = vs, vc -> BLACKSTONE_VEINS_PER_CHUNK = vc,
                    mn -> BLACKSTONE_MIN_HEIGHT = mn, mx -> BLACKSTONE_MAX_HEIGHT = mx);
            loadOre(json, "soul_sand",  vs -> SOUL_SAND_VEIN_SIZE = vs,  vc -> SOUL_SAND_VEINS_PER_CHUNK = vc,
                    mn -> SOUL_SAND_MIN_HEIGHT = mn,  mx -> SOUL_SAND_MAX_HEIGHT = mx);
            loadOre(json, "magma",      vs -> MAGMA_VEIN_SIZE = vs,      vc -> MAGMA_VEINS_PER_CHUNK = vc,
                    mn -> MAGMA_MIN_HEIGHT = mn,      mx -> MAGMA_MAX_HEIGHT = mx);

            if (json.has("dungeon_count"))     DUNGEON_COUNT      = json.get("dungeon_count").getAsInt();
            if (json.has("dungeon_count_deep")) DUNGEON_COUNT_DEEP = json.get("dungeon_count_deep").getAsInt();
            if (json.has("overworld_fluid"))   OVERWORLD_FLUID    = FluidChoice.valueOf(json.get("overworld_fluid").getAsString());
            if (json.has("nether_fluid"))      NETHER_FLUID       = FluidChoice.valueOf(json.get("nether_fluid").getAsString());

            if (json.has("caves_enabled"))   CAVES_ENABLED   = json.get("caves_enabled").getAsBoolean();
            if (json.has("ravines_enabled")) RAVINES_ENABLED = json.get("ravines_enabled").getAsBoolean();

            if (json.has("lakes_enabled"))                LAKES_ENABLED                = json.get("lakes_enabled").getAsBoolean();
            if (json.has("local_modifications_enabled"))  LOCAL_MODIFICATIONS_ENABLED  = json.get("local_modifications_enabled").getAsBoolean();
            if (json.has("surface_structures_enabled"))   SURFACE_STRUCTURES_ENABLED   = json.get("surface_structures_enabled").getAsBoolean();
            if (json.has("underground_decoration_enabled")) UNDERGROUND_DECORATION_ENABLED = json.get("underground_decoration_enabled").getAsBoolean();
            if (json.has("fluid_springs_enabled"))        FLUID_SPRINGS_ENABLED        = json.get("fluid_springs_enabled").getAsBoolean();
            if (json.has("vegetal_decoration_enabled"))   VEGETAL_DECORATION_ENABLED   = json.get("vegetal_decoration_enabled").getAsBoolean();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveOre(JsonObject json, String key,
                                int veinSize, int count, int minY, int maxY) {
        JsonObject ore = new JsonObject();
        ore.addProperty("vein_size", veinSize);
        ore.addProperty("count",     count);
        ore.addProperty("min_y",     minY);
        ore.addProperty("max_y",     maxY);
        json.add(key, ore);
    }

    private static void loadOre(JsonObject json, String key,
                                java.util.function.IntConsumer veinSize,
                                java.util.function.IntConsumer count,
                                java.util.function.IntConsumer minY,
                                java.util.function.IntConsumer maxY) {
        if (!json.has(key)) return;
        JsonObject ore = json.getAsJsonObject(key);
        if (ore.has("vein_size")) veinSize.accept(ore.get("vein_size").getAsInt());
        if (ore.has("count"))     count.accept(ore.get("count").getAsInt());
        if (ore.has("min_y"))     minY.accept(ore.get("min_y").getAsInt());
        if (ore.has("max_y"))     maxY.accept(ore.get("max_y").getAsInt());
    }

    public static void loadFromPath(Path path) {
        try {
            if (!Files.exists(path)) return;

            JsonObject json = JsonParser.parseString(Files.readString(path)).getAsJsonObject();

            if (json.has("custom_settings_enabled"))
                CUSTOM_SETTINGS_ENABLED = json.get("custom_settings_enabled").getAsBoolean();

            loadOre(json, "coal",     vs -> COAL_VEIN_SIZE = vs,     vc -> COAL_VEINS_PER_CHUNK = vc,
                    mn -> COAL_MIN_HEIGHT = mn, mx -> COAL_MAX_HEIGHT = mx);
            loadOre(json, "iron",     vs -> IRON_VEIN_SIZE = vs,     vc -> IRON_VEINS_PER_CHUNK = vc,
                    mn -> IRON_MIN_HEIGHT = mn, mx -> IRON_MAX_HEIGHT = mx);
            loadOre(json, "gold",     vs -> GOLD_VEIN_SIZE = vs,     vc -> GOLD_VEINS_PER_CHUNK = vc,
                    mn -> GOLD_MIN_HEIGHT = mn, mx -> GOLD_MAX_HEIGHT = mx);
            loadOre(json, "diamond",  vs -> DIAMOND_VEIN_SIZE = vs,  vc -> DIAMOND_VEINS_PER_CHUNK = vc,
                    mn -> DIAMOND_MIN_HEIGHT = mn, mx -> DIAMOND_MAX_HEIGHT = mx);
            loadOre(json, "redstone", vs -> REDSTONE_VEIN_SIZE = vs, vc -> REDSTONE_VEINS_PER_CHUNK = vc,
                    mn -> REDSTONE_MIN_HEIGHT = mn, mx -> REDSTONE_MAX_HEIGHT = mx);
            loadOre(json, "lapis",    vs -> LAPIS_VEIN_SIZE = vs,    vc -> LAPIS_VEINS_PER_CHUNK = vc,
                    mn -> LAPIS_MIN_HEIGHT = mn, mx -> LAPIS_MAX_HEIGHT = mx);
            loadOre(json, "copper",   vs -> COPPER_VEIN_SIZE = vs,   vc -> COPPER_VEINS_PER_CHUNK = vc,
                    mn -> COPPER_MIN_HEIGHT = mn, mx -> COPPER_MAX_HEIGHT = mx);
            loadOre(json, "emerald",  vs -> EMERALD_VEIN_SIZE = vs,  vc -> EMERALD_VEINS_PER_CHUNK = vc,
                    mn -> EMERALD_MIN_HEIGHT = mn, mx -> EMERALD_MAX_HEIGHT = mx);
            loadOre(json, "dirt",     vs -> DIRT_VEIN_SIZE = vs,     vc -> DIRT_VEINS_PER_CHUNK = vc,
                    mn -> DIRT_MIN_HEIGHT = mn, mx -> DIRT_MAX_HEIGHT = mx);
            loadOre(json, "gravel",   vs -> GRAVEL_VEIN_SIZE = vs,   vc -> GRAVEL_VEINS_PER_CHUNK = vc,
                    mn -> GRAVEL_MIN_HEIGHT = mn, mx -> GRAVEL_MAX_HEIGHT = mx);
            loadOre(json, "granite",  vs -> GRANITE_VEIN_SIZE = vs,  vc -> GRANITE_VEINS_PER_CHUNK = vc,
                    mn -> GRANITE_MIN_HEIGHT = mn, mx -> GRANITE_MAX_HEIGHT = mx);
            loadOre(json, "diorite",  vs -> DIORITE_VEIN_SIZE = vs,  vc -> DIORITE_VEINS_PER_CHUNK = vc,
                    mn -> DIORITE_MIN_HEIGHT = mn, mx -> DIORITE_MAX_HEIGHT = mx);
            loadOre(json, "andesite", vs -> ANDESITE_VEIN_SIZE = vs, vc -> ANDESITE_VEINS_PER_CHUNK = vc,
                    mn -> ANDESITE_MIN_HEIGHT = mn, mx -> ANDESITE_MAX_HEIGHT = mx);
            loadOre(json, "tuff",     vs -> TUFF_VEIN_SIZE = vs,     vc -> TUFF_VEINS_PER_CHUNK = vc,
                    mn -> TUFF_MIN_HEIGHT = mn, mx -> TUFF_MAX_HEIGHT = mx);
            loadOre(json, "calcite",  vs -> CALCITE_VEIN_SIZE = vs,  vc -> CALCITE_VEINS_PER_CHUNK = vc,
                    mn -> CALCITE_MIN_HEIGHT = mn, mx -> CALCITE_MAX_HEIGHT = mx);
            loadOre(json, "deepslate",vs -> DEEPSLATE_VEIN_SIZE = vs,vc -> DEEPSLATE_VEINS_PER_CHUNK = vc,
                    mn -> DEEPSLATE_MIN_HEIGHT = mn, mx -> DEEPSLATE_MAX_HEIGHT = mx);
            loadOre(json, "ancient_debris", vs -> ANCIENT_DEBRIS_VEIN_SIZE = vs,
                    vc -> ANCIENT_DEBRIS_VEINS_PER_CHUNK = vc,
                    mn -> ANCIENT_DEBRIS_MIN_HEIGHT = mn, mx -> ANCIENT_DEBRIS_MAX_HEIGHT = mx);
            loadOre(json, "nether_gold", vs -> NETHER_GOLD_VEIN_SIZE = vs,
                    vc -> NETHER_GOLD_VEINS_PER_CHUNK = vc,
                    mn -> NETHER_GOLD_MIN_HEIGHT = mn, mx -> NETHER_GOLD_MAX_HEIGHT = mx);
            loadOre(json, "nether_quartz", vs -> NETHER_QUARTZ_VEIN_SIZE = vs,
                    vc -> NETHER_QUARTZ_VEINS_PER_CHUNK = vc,
                    mn -> NETHER_QUARTZ_MIN_HEIGHT = mn, mx -> NETHER_QUARTZ_MAX_HEIGHT = mx);

            if (json.has("dungeon_count"))      DUNGEON_COUNT      = json.get("dungeon_count").getAsInt();
            if (json.has("dungeon_count_deep")) DUNGEON_COUNT_DEEP = json.get("dungeon_count_deep").getAsInt();
            if (json.has("overworld_fluid"))    OVERWORLD_FLUID    = FluidChoice.valueOf(json.get("overworld_fluid").getAsString());
            if (json.has("nether_fluid"))       NETHER_FLUID       = FluidChoice.valueOf(json.get("nether_fluid").getAsString());

            if (json.has("caves_enabled"))   CAVES_ENABLED   = json.get("caves_enabled").getAsBoolean();
            if (json.has("ravines_enabled")) RAVINES_ENABLED = json.get("ravines_enabled").getAsBoolean();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}