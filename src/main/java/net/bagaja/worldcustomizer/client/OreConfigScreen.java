package net.bagaja.worldcustomizer.client;

import net.bagaja.worldcustomizer.config.OreSettings;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class OreConfigScreen extends Screen {

    private final Screen parent;
    private int page = 0;
    private static final int TOTAL_PAGES = 4;

    private static final String[] PAGE_TITLES = {
            "Basic Settings",
            "Overworld Ores",
            "Stone Variants",
            "Nether"
    };

    // One slider list per ore page (pages 1-3)
    private OreSliderList[] sliderLists;

    public OreConfigScreen(Screen parent) {
        super(Component.literal("Customize World Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int bw = 100; // button width
        int bh = 20;  // button height
        int bottom = this.height - 28;

        // ── Navigation ───────────────────────────────────────────
        // Navigation buttons
        Button prevButton = Button.builder(Component.literal("< Previous Page"),
                        btn -> {
                            page--;
                            rebuildWidgets();
                        })
                .pos(4, 4).width(120).build();
        Button nextButton = Button.builder(Component.literal("Next Page >"),
                        btn -> {
                            page++;
                            rebuildWidgets();
                        })
                .pos(this.width - 124, 4).width(120).build();
        this.addRenderableWidget(prevButton);
        this.addRenderableWidget(nextButton);
        prevButton.active = page > 0;
        nextButton.active = page < TOTAL_PAGES - 1;

        // ── Bottom buttons ───────────────────────────────────────
        this.addRenderableWidget(Button.builder(Component.literal("Defaults"),
                        btn -> { resetDefaults(); rebuildWidgets(); })
                .pos(this.width / 2 - 154, bottom).width(100).build());

        this.addRenderableWidget(Button.builder(Component.literal("Done"),
                        btn -> {
                            assert this.minecraft != null;
                            this.minecraft.setScreen(parent);
                        })
                .pos(this.width / 2 + 54, bottom).width(100).build());

        // ── Page content ─────────────────────────────────────────
        switch (page) {
            case 0 -> initBasicSettings();

            case 1 -> initOreList(1);
            case 2 -> initOreList(2);
            case 3 -> initOreList(3);
        }
    }

    // ── Page 0: Basic Settings ───────────────────────────────────
    private void initBasicSettings() {
        int col1 = this.width / 2 - 205;
        int col2 = this.width / 2 + 5;
        int bw   = 200;
        int bh   = 20;
        int startY = 50;
        int gap  = 28;
        int row  = 0;

        // Overworld Fluid
        CycleButton<Boolean> enabledBtn = CycleButton.onOffBuilder(OreSettings.CUSTOM_SETTINGS_ENABLED)
                .create(this.width / 2 - 100, startY, 200, bh,
                        Component.literal("Custom World Generation"),
                        (btn, val) -> OreSettings.CUSTOM_SETTINGS_ENABLED = val);
        enabledBtn.setTooltip(Tooltip.create(Component.literal(
                "When OFF, all settings below are ignored and vanilla world generation is used.")));
        this.addRenderableWidget(enabledBtn);
        row++;

        // Overworld Fluid + Nether Fluid
        this.addRenderableWidget(
                CycleButton.<OreSettings.FluidChoice>builder(f -> Component.literal(switch (f) {
                            case WATER -> "Water";
                            case LAVA  -> "Lava";
                            case AIR   -> "Air (dry)";
                        }))
                        .withValues(OreSettings.FluidChoice.values())
                        .withInitialValue(OreSettings.OVERWORLD_FLUID)
                        .create(col1, startY + gap * row, bw, bh,
                                Component.literal("Overworld Fluid"),
                                (btn, val) -> OreSettings.OVERWORLD_FLUID = val));

        // Nether Fluid
        this.addRenderableWidget(
                CycleButton.<OreSettings.FluidChoice>builder(f -> Component.literal(switch (f) {
                            case WATER -> "Water";
                            case LAVA  -> "Lava";
                            case AIR   -> "Air";
                        }))
                        .withValues(OreSettings.FluidChoice.values())
                        .withInitialValue(OreSettings.NETHER_FLUID)
                        .create(col2, startY + gap * row, bw, bh,
                                Component.literal("Nether Fluid"),
                                (btn, val) -> OreSettings.NETHER_FLUID = val));
        row++;

        // Surface dungeons
        this.addRenderableWidget(new OreSlider(col1, startY + gap * row, bw, bh,
                "Surface dungeons", 0, 30, OreSettings.DUNGEON_COUNT,
                v -> OreSettings.DUNGEON_COUNT = v)
                .withTooltip(Tooltip.create(Component.literal(
                        "Dungeon rooms with a spawner and chests, generated between Y=0 and the surface. Vanilla: 10"))));

        // Deep dungeons
        this.addRenderableWidget(new OreSlider(col2, startY + gap * row, bw, bh,
                "Deep dungeons", 0, 30, OreSettings.DUNGEON_COUNT_DEEP,
                v -> OreSettings.DUNGEON_COUNT_DEEP = v)
                .withTooltip(Tooltip.create(Component.literal(
                        "Dungeon rooms with a spawner and chests, generated between Y=-64 and Y=-1. Vanilla: 4"))));
        row++;

        // Caves
        CycleButton<Boolean> cavesBtn = CycleButton.onOffBuilder(OreSettings.CAVES_ENABLED)
                .create(col1, startY + gap * row, bw, bh,
                        Component.literal("Caves"),
                        (btn, val) -> OreSettings.CAVES_ENABLED = val);
        cavesBtn.setTooltip(Tooltip.create(Component.literal(
                "Toggles cave carver generation. Disabling removes all cave tunnels and rooms. Vanilla: On")));
        this.addRenderableWidget(cavesBtn);

        // Ravines
        CycleButton<Boolean> ravinesBtn = CycleButton.onOffBuilder(OreSettings.RAVINES_ENABLED)
                .create(col2, startY + gap * row, bw, bh,
                        Component.literal("Ravines"),
                        (btn, val) -> OreSettings.RAVINES_ENABLED = val);
        ravinesBtn.setTooltip(Tooltip.create(Component.literal(
                "Toggles ravine (canyon) generation. Disabling removes all surface and underground ravines. Vanilla: On")));
        this.addRenderableWidget(ravinesBtn);
        row++;

        // Lakes
        CycleButton<Boolean> lakesBtn = CycleButton.onOffBuilder(OreSettings.LAKES_ENABLED)
                .create(col1, startY + gap * row, bw, bh,
                        Component.literal("Lakes"),
                        (btn, val) -> OreSettings.LAKES_ENABLED = val);
        lakesBtn.setTooltip(Tooltip.create(Component.literal(
                "Surface water and lava lake generation. Vanilla: On")));
        this.addRenderableWidget(lakesBtn);

        // Local Modifications
        CycleButton<Boolean> localModBtn = CycleButton.onOffBuilder(OreSettings.LOCAL_MODIFICATIONS_ENABLED)
                .create(col2, startY + gap * row, bw, bh,
                        Component.literal("Local Modifications"),
                        (btn, val) -> OreSettings.LOCAL_MODIFICATIONS_ENABLED = val);
        localModBtn.setTooltip(Tooltip.create(Component.literal(
                "Icebergs, forest rocks, and other local terrain modifications. Vanilla: On")));
        this.addRenderableWidget(localModBtn);
        row++;

        // Surface Structures
        CycleButton<Boolean> surfStructBtn = CycleButton.onOffBuilder(OreSettings.SURFACE_STRUCTURES_ENABLED)
                .create(col1, startY + gap * row, bw, bh,
                        Component.literal("Surface Structures"),
                        (btn, val) -> OreSettings.SURFACE_STRUCTURES_ENABLED = val);
        surfStructBtn.setTooltip(Tooltip.create(Component.literal(
                "Small surface features like desert wells and bonus chests. Vanilla: On")));
        this.addRenderableWidget(surfStructBtn);

        // Underground Decoration
        CycleButton<Boolean> underDecBtn = CycleButton.onOffBuilder(OreSettings.UNDERGROUND_DECORATION_ENABLED)
                .create(col2, startY + gap * row, bw, bh,
                        Component.literal("Underground Deco"),
                        (btn, val) -> OreSettings.UNDERGROUND_DECORATION_ENABLED = val);
        underDecBtn.setTooltip(Tooltip.create(Component.literal(
                "Glow lichen, dripstone, sculk, fossils and other underground decorations. Vanilla: On")));
        this.addRenderableWidget(underDecBtn);
        row++;

        // Fluid Springs
        CycleButton<Boolean> fluidBtn = CycleButton.onOffBuilder(OreSettings.FLUID_SPRINGS_ENABLED)
                .create(col1, startY + gap * row, bw, bh,
                        Component.literal("Fluid Springs"),
                        (btn, val) -> OreSettings.FLUID_SPRINGS_ENABLED = val);
        fluidBtn.setTooltip(Tooltip.create(Component.literal(
                "Underground water and lava spring sources in cave walls. Vanilla: On")));
        this.addRenderableWidget(fluidBtn);

        // Vegetation
        CycleButton<Boolean> vegetalBtn = CycleButton.onOffBuilder(OreSettings.VEGETAL_DECORATION_ENABLED)
                .create(col2, startY + gap * row, bw, bh,
                        Component.literal("Vegetation"),
                        (btn, val) -> OreSettings.VEGETAL_DECORATION_ENABLED = val);
        vegetalBtn.setTooltip(Tooltip.create(Component.literal(
                "All plant and tree generation. Disabling creates a bare stone world. Vanilla: On")));
        this.addRenderableWidget(vegetalBtn);
        row++;
    }

    private void initOreList(int pageIndex) {
        int topY    = 46;
        int bottomY = this.height - 36;
        OreSliderList list = new OreSliderList(
                this.minecraft, this.width, bottomY - topY, topY, bottomY);

        switch (pageIndex) {
            case 1 -> {
                list.addRow("Coal",
                        v -> OreSettings.COAL_VEIN_SIZE = v,      1,  64, OreSettings.COAL_VEIN_SIZE,
                        v -> OreSettings.COAL_VEINS_PER_CHUNK = v, 0, 100, OreSettings.COAL_VEINS_PER_CHUNK,
                        v -> OreSettings.COAL_MIN_HEIGHT = v,    -64, 320, OreSettings.COAL_MIN_HEIGHT,
                        v -> OreSettings.COAL_MAX_HEIGHT = v,    -64, 320, OreSettings.COAL_MAX_HEIGHT);
                list.addRow("Iron",
                        v -> OreSettings.IRON_VEIN_SIZE = v,      1,  64, OreSettings.IRON_VEIN_SIZE,
                        v -> OreSettings.IRON_VEINS_PER_CHUNK = v, 0, 100, OreSettings.IRON_VEINS_PER_CHUNK,
                        v -> OreSettings.IRON_MIN_HEIGHT = v,    -64, 320, OreSettings.IRON_MIN_HEIGHT,
                        v -> OreSettings.IRON_MAX_HEIGHT = v,    -64, 320, OreSettings.IRON_MAX_HEIGHT);
                list.addRow("Gold",
                        v -> OreSettings.GOLD_VEIN_SIZE = v,      1,  64, OreSettings.GOLD_VEIN_SIZE,
                        v -> OreSettings.GOLD_VEINS_PER_CHUNK = v, 0, 100, OreSettings.GOLD_VEINS_PER_CHUNK,
                        v -> OreSettings.GOLD_MIN_HEIGHT = v,    -64, 320, OreSettings.GOLD_MIN_HEIGHT,
                        v -> OreSettings.GOLD_MAX_HEIGHT = v,    -64, 320, OreSettings.GOLD_MAX_HEIGHT);
                list.addRow("Diamond",
                        v -> OreSettings.DIAMOND_VEIN_SIZE = v,   1,  64, OreSettings.DIAMOND_VEIN_SIZE,
                        v -> OreSettings.DIAMOND_VEINS_PER_CHUNK = v, 0, 100, OreSettings.DIAMOND_VEINS_PER_CHUNK,
                        v -> OreSettings.DIAMOND_MIN_HEIGHT = v, -64, 320, OreSettings.DIAMOND_MIN_HEIGHT,
                        v -> OreSettings.DIAMOND_MAX_HEIGHT = v, -64, 320, OreSettings.DIAMOND_MAX_HEIGHT);
                list.addRow("Redstone",
                        v -> OreSettings.REDSTONE_VEIN_SIZE = v,  1,  64, OreSettings.REDSTONE_VEIN_SIZE,
                        v -> OreSettings.REDSTONE_VEINS_PER_CHUNK = v, 0, 100, OreSettings.REDSTONE_VEINS_PER_CHUNK,
                        v -> OreSettings.REDSTONE_MIN_HEIGHT = v,-64, 320, OreSettings.REDSTONE_MIN_HEIGHT,
                        v -> OreSettings.REDSTONE_MAX_HEIGHT = v,-64, 320, OreSettings.REDSTONE_MAX_HEIGHT);
                list.addRow("Lapis",
                        v -> OreSettings.LAPIS_VEIN_SIZE = v,     1,  64, OreSettings.LAPIS_VEIN_SIZE,
                        v -> OreSettings.LAPIS_VEINS_PER_CHUNK = v, 0, 100, OreSettings.LAPIS_VEINS_PER_CHUNK,
                        v -> OreSettings.LAPIS_MIN_HEIGHT = v,   -64, 320, OreSettings.LAPIS_MIN_HEIGHT,
                        v -> OreSettings.LAPIS_MAX_HEIGHT = v,   -64, 320, OreSettings.LAPIS_MAX_HEIGHT);
                list.addRow("Copper",
                        v -> OreSettings.COPPER_VEIN_SIZE = v,    1,  64, OreSettings.COPPER_VEIN_SIZE,
                        v -> OreSettings.COPPER_VEINS_PER_CHUNK = v, 0, 100, OreSettings.COPPER_VEINS_PER_CHUNK,
                        v -> OreSettings.COPPER_MIN_HEIGHT = v,  -64, 320, OreSettings.COPPER_MIN_HEIGHT,
                        v -> OreSettings.COPPER_MAX_HEIGHT = v,  -64, 320, OreSettings.COPPER_MAX_HEIGHT);
                list.addRow("Emerald",
                        v -> OreSettings.EMERALD_VEIN_SIZE = v,   1,  64, OreSettings.EMERALD_VEIN_SIZE,
                        v -> OreSettings.EMERALD_VEINS_PER_CHUNK = v, 0, 100, OreSettings.EMERALD_VEINS_PER_CHUNK,
                        v -> OreSettings.EMERALD_MIN_HEIGHT = v, -64, 320, OreSettings.EMERALD_MIN_HEIGHT,
                        v -> OreSettings.EMERALD_MAX_HEIGHT = v, -64, 320, OreSettings.EMERALD_MAX_HEIGHT);
            }
            case 2 -> {
                list.addRow("Dirt",
                        v -> OreSettings.DIRT_VEIN_SIZE = v,      1, 64, OreSettings.DIRT_VEIN_SIZE,
                        v -> OreSettings.DIRT_VEINS_PER_CHUNK = v, 0, 20, OreSettings.DIRT_VEINS_PER_CHUNK,
                        v -> OreSettings.DIRT_MIN_HEIGHT = v,    -64, 320, OreSettings.DIRT_MIN_HEIGHT,
                        v -> OreSettings.DIRT_MAX_HEIGHT = v,    -64, 320, OreSettings.DIRT_MAX_HEIGHT);
                list.addRow("Gravel",
                        v -> OreSettings.GRAVEL_VEIN_SIZE = v,    1, 64, OreSettings.GRAVEL_VEIN_SIZE,
                        v -> OreSettings.GRAVEL_VEINS_PER_CHUNK = v, 0, 20, OreSettings.GRAVEL_VEINS_PER_CHUNK,
                        v -> OreSettings.GRAVEL_MIN_HEIGHT = v,  -64, 320, OreSettings.GRAVEL_MIN_HEIGHT,
                        v -> OreSettings.GRAVEL_MAX_HEIGHT = v,  -64, 320, OreSettings.GRAVEL_MAX_HEIGHT);
                list.addRow("Granite",
                        v -> OreSettings.GRANITE_VEIN_SIZE = v,   1, 64, OreSettings.GRANITE_VEIN_SIZE,
                        v -> OreSettings.GRANITE_VEINS_PER_CHUNK = v, 0, 20, OreSettings.GRANITE_VEINS_PER_CHUNK,
                        v -> OreSettings.GRANITE_MIN_HEIGHT = v, -64, 320, OreSettings.GRANITE_MIN_HEIGHT,
                        v -> OreSettings.GRANITE_MAX_HEIGHT = v, -64, 320, OreSettings.GRANITE_MAX_HEIGHT);
                list.addRow("Diorite",
                        v -> OreSettings.DIORITE_VEIN_SIZE = v,   1, 64, OreSettings.DIORITE_VEIN_SIZE,
                        v -> OreSettings.DIORITE_VEINS_PER_CHUNK = v, 0, 20, OreSettings.DIORITE_VEINS_PER_CHUNK,
                        v -> OreSettings.DIORITE_MIN_HEIGHT = v, -64, 320, OreSettings.DIORITE_MIN_HEIGHT,
                        v -> OreSettings.DIORITE_MAX_HEIGHT = v, -64, 320, OreSettings.DIORITE_MAX_HEIGHT);
                list.addRow("Andesite",
                        v -> OreSettings.ANDESITE_VEIN_SIZE = v,  1, 64, OreSettings.ANDESITE_VEIN_SIZE,
                        v -> OreSettings.ANDESITE_VEINS_PER_CHUNK = v, 0, 20, OreSettings.ANDESITE_VEINS_PER_CHUNK,
                        v -> OreSettings.ANDESITE_MIN_HEIGHT = v,-64, 320, OreSettings.ANDESITE_MIN_HEIGHT,
                        v -> OreSettings.ANDESITE_MAX_HEIGHT = v,-64, 320, OreSettings.ANDESITE_MAX_HEIGHT);
                list.addRow("Tuff",
                        v -> OreSettings.TUFF_VEIN_SIZE = v,      1, 64, OreSettings.TUFF_VEIN_SIZE,
                        v -> OreSettings.TUFF_VEINS_PER_CHUNK = v, 0, 20, OreSettings.TUFF_VEINS_PER_CHUNK,
                        v -> OreSettings.TUFF_MIN_HEIGHT = v,    -64, 320, OreSettings.TUFF_MIN_HEIGHT,
                        v -> OreSettings.TUFF_MAX_HEIGHT = v,    -64, 320, OreSettings.TUFF_MAX_HEIGHT);
                list.addRow("Calcite",
                        v -> OreSettings.CALCITE_VEIN_SIZE = v,   1, 64, OreSettings.CALCITE_VEIN_SIZE,
                        v -> OreSettings.CALCITE_VEINS_PER_CHUNK = v, 0, 20, OreSettings.CALCITE_VEINS_PER_CHUNK,
                        v -> OreSettings.CALCITE_MIN_HEIGHT = v, -64, 320, OreSettings.CALCITE_MIN_HEIGHT,
                        v -> OreSettings.CALCITE_MAX_HEIGHT = v, -64, 320, OreSettings.CALCITE_MAX_HEIGHT);
                list.addRow("Deepslate",
                        v -> OreSettings.DEEPSLATE_VEIN_SIZE = v, 1, 64, OreSettings.DEEPSLATE_VEIN_SIZE,
                        v -> OreSettings.DEEPSLATE_VEINS_PER_CHUNK = v, 0, 20, OreSettings.DEEPSLATE_VEINS_PER_CHUNK,
                        v -> OreSettings.DEEPSLATE_MIN_HEIGHT = v,-64, 320, OreSettings.DEEPSLATE_MIN_HEIGHT,
                        v -> OreSettings.DEEPSLATE_MAX_HEIGHT = v,-64, 320, OreSettings.DEEPSLATE_MAX_HEIGHT);
                list.addRow("Clay",
                        v -> OreSettings.CLAY_VEIN_SIZE = v,      1, 64, OreSettings.CLAY_VEIN_SIZE,
                        v -> OreSettings.CLAY_VEINS_PER_CHUNK = v, 0, 60, OreSettings.CLAY_VEINS_PER_CHUNK,
                        v -> OreSettings.CLAY_MIN_HEIGHT = v,      0, 320, OreSettings.CLAY_MIN_HEIGHT,
                        v -> OreSettings.CLAY_MAX_HEIGHT = v,      0, 320, OreSettings.CLAY_MAX_HEIGHT);
            }
            case 3 -> {
                list.addRow("Anc. Debris",
                        v -> OreSettings.ANCIENT_DEBRIS_VEIN_SIZE = v,      1, 16, OreSettings.ANCIENT_DEBRIS_VEIN_SIZE,
                        v -> OreSettings.ANCIENT_DEBRIS_VEINS_PER_CHUNK = v, 0, 20, OreSettings.ANCIENT_DEBRIS_VEINS_PER_CHUNK,
                        v -> OreSettings.ANCIENT_DEBRIS_MIN_HEIGHT = v,      0, 128, OreSettings.ANCIENT_DEBRIS_MIN_HEIGHT,
                        v -> OreSettings.ANCIENT_DEBRIS_MAX_HEIGHT = v,      0, 128, OreSettings.ANCIENT_DEBRIS_MAX_HEIGHT);
                list.addRow("Nether Gold",
                        v -> OreSettings.NETHER_GOLD_VEIN_SIZE = v,         1, 64, OreSettings.NETHER_GOLD_VEIN_SIZE,
                        v -> OreSettings.NETHER_GOLD_VEINS_PER_CHUNK = v,   0, 40, OreSettings.NETHER_GOLD_VEINS_PER_CHUNK,
                        v -> OreSettings.NETHER_GOLD_MIN_HEIGHT = v,         0, 128, OreSettings.NETHER_GOLD_MIN_HEIGHT,
                        v -> OreSettings.NETHER_GOLD_MAX_HEIGHT = v,         0, 128, OreSettings.NETHER_GOLD_MAX_HEIGHT);
                list.addRow("Nether Quartz",
                        v -> OreSettings.NETHER_QUARTZ_VEIN_SIZE = v,       1, 64, OreSettings.NETHER_QUARTZ_VEIN_SIZE,
                        v -> OreSettings.NETHER_QUARTZ_VEINS_PER_CHUNK = v, 0, 40, OreSettings.NETHER_QUARTZ_VEINS_PER_CHUNK,
                        v -> OreSettings.NETHER_QUARTZ_MIN_HEIGHT = v,       0, 128, OreSettings.NETHER_QUARTZ_MIN_HEIGHT,
                        v -> OreSettings.NETHER_QUARTZ_MAX_HEIGHT = v,       0, 128, OreSettings.NETHER_QUARTZ_MAX_HEIGHT);
                list.addRow("Blackstone",
                        v -> OreSettings.BLACKSTONE_VEIN_SIZE = v,      1, 64, OreSettings.BLACKSTONE_VEIN_SIZE,
                        v -> OreSettings.BLACKSTONE_VEINS_PER_CHUNK = v, 0, 20, OreSettings.BLACKSTONE_VEINS_PER_CHUNK,
                        v -> OreSettings.BLACKSTONE_MIN_HEIGHT = v,      0, 128, OreSettings.BLACKSTONE_MIN_HEIGHT,
                        v -> OreSettings.BLACKSTONE_MAX_HEIGHT = v,      0, 128, OreSettings.BLACKSTONE_MAX_HEIGHT);
                list.addRow("Soul Sand",
                        v -> OreSettings.SOUL_SAND_VEIN_SIZE = v,       1, 64, OreSettings.SOUL_SAND_VEIN_SIZE,
                        v -> OreSettings.SOUL_SAND_VEINS_PER_CHUNK = v,  0, 30, OreSettings.SOUL_SAND_VEINS_PER_CHUNK,
                        v -> OreSettings.SOUL_SAND_MIN_HEIGHT = v,       0, 128, OreSettings.SOUL_SAND_MIN_HEIGHT,
                        v -> OreSettings.SOUL_SAND_MAX_HEIGHT = v,       0, 128, OreSettings.SOUL_SAND_MAX_HEIGHT);
                list.addRow("Magma",
                        v -> OreSettings.MAGMA_VEIN_SIZE = v,           1, 64, OreSettings.MAGMA_VEIN_SIZE,
                        v -> OreSettings.MAGMA_VEINS_PER_CHUNK = v,      0, 20, OreSettings.MAGMA_VEINS_PER_CHUNK,
                        v -> OreSettings.MAGMA_MIN_HEIGHT = v,           0, 128, OreSettings.MAGMA_MIN_HEIGHT,
                        v -> OreSettings.MAGMA_MAX_HEIGHT = v,           0, 128, OreSettings.MAGMA_MAX_HEIGHT);
            }
        }

        this.addRenderableWidget(list);
    }

    // ── Defaults reset ───────────────────────────────────────────
    private void resetDefaults() {
        OreSettings.CUSTOM_SETTINGS_ENABLED = true;

        // Overworld ores
        OreSettings.COAL_VEIN_SIZE = 17;    OreSettings.COAL_VEINS_PER_CHUNK = 20;
        OreSettings.IRON_VEIN_SIZE = 9;     OreSettings.IRON_VEINS_PER_CHUNK = 10;
        OreSettings.GOLD_VEIN_SIZE = 9;     OreSettings.GOLD_VEINS_PER_CHUNK = 4;
        OreSettings.DIAMOND_VEIN_SIZE = 8;  OreSettings.DIAMOND_VEINS_PER_CHUNK = 1;
        OreSettings.REDSTONE_VEIN_SIZE = 8; OreSettings.REDSTONE_VEINS_PER_CHUNK = 4;
        OreSettings.LAPIS_VEIN_SIZE = 7;    OreSettings.LAPIS_VEINS_PER_CHUNK = 2;
        OreSettings.COPPER_VEIN_SIZE = 10;  OreSettings.COPPER_VEINS_PER_CHUNK = 6;
        OreSettings.EMERALD_VEIN_SIZE = 3;  OreSettings.EMERALD_VEINS_PER_CHUNK = 1;
        // Stone variants
        OreSettings.DIRT_VEIN_SIZE = 33;     OreSettings.DIRT_VEINS_PER_CHUNK = 7;
        OreSettings.GRAVEL_VEIN_SIZE = 33;   OreSettings.GRAVEL_VEINS_PER_CHUNK = 8;
        OreSettings.GRANITE_VEIN_SIZE = 64;  OreSettings.GRANITE_VEINS_PER_CHUNK = 2;
        OreSettings.DIORITE_VEIN_SIZE = 64;  OreSettings.DIORITE_VEINS_PER_CHUNK = 2;
        OreSettings.ANDESITE_VEIN_SIZE = 64; OreSettings.ANDESITE_VEINS_PER_CHUNK = 2;
        OreSettings.TUFF_VEIN_SIZE = 64;     OreSettings.TUFF_VEINS_PER_CHUNK = 2;
        OreSettings.CALCITE_VEIN_SIZE = 64;  OreSettings.CALCITE_VEINS_PER_CHUNK = 2;
        OreSettings.DEEPSLATE_VEIN_SIZE = 64;OreSettings.DEEPSLATE_VEINS_PER_CHUNK = 2;
        // Nether
        OreSettings.ANCIENT_DEBRIS_VEIN_SIZE = 3;  OreSettings.ANCIENT_DEBRIS_VEINS_PER_CHUNK = 1;
        OreSettings.NETHER_GOLD_VEIN_SIZE = 10;    OreSettings.NETHER_GOLD_VEINS_PER_CHUNK = 10;
        OreSettings.NETHER_QUARTZ_VEIN_SIZE = 14;  OreSettings.NETHER_QUARTZ_VEINS_PER_CHUNK = 16;
        // Basic
        OreSettings.DUNGEON_COUNT = 10;
        OreSettings.DUNGEON_COUNT_DEEP = 4;
        OreSettings.CAVES_ENABLED   = true;
        OreSettings.RAVINES_ENABLED = true;
        OreSettings.LAKES_ENABLED               = true;
        OreSettings.LOCAL_MODIFICATIONS_ENABLED = true;
        OreSettings.SURFACE_STRUCTURES_ENABLED  = true;
        OreSettings.UNDERGROUND_DECORATION_ENABLED = true;
        OreSettings.FLUID_SPRINGS_ENABLED       = true;
        OreSettings.VEGETAL_DECORATION_ENABLED  = true;
        OreSettings.OVERWORLD_FLUID = OreSettings.FluidChoice.WATER;
        OreSettings.NETHER_FLUID    = OreSettings.FluidChoice.LAVA;
        // Min/max heights - overworld ores
        OreSettings.COAL_MIN_HEIGHT = 0;      OreSettings.COAL_MAX_HEIGHT = 192;
        OreSettings.IRON_MIN_HEIGHT = -64;    OreSettings.IRON_MAX_HEIGHT = 72;
        OreSettings.GOLD_MIN_HEIGHT = -64;    OreSettings.GOLD_MAX_HEIGHT = 32;
        OreSettings.DIAMOND_MIN_HEIGHT = -64; OreSettings.DIAMOND_MAX_HEIGHT = 16;
        OreSettings.REDSTONE_MIN_HEIGHT = -64;OreSettings.REDSTONE_MAX_HEIGHT = 15;
        OreSettings.LAPIS_MIN_HEIGHT = -64;   OreSettings.LAPIS_MAX_HEIGHT = 64;
        OreSettings.COPPER_MIN_HEIGHT = -16;  OreSettings.COPPER_MAX_HEIGHT = 112;
        OreSettings.EMERALD_MIN_HEIGHT = -16; OreSettings.EMERALD_MAX_HEIGHT = 480;
        // Stone variants
        OreSettings.DIRT_MIN_HEIGHT = 0;      OreSettings.DIRT_MAX_HEIGHT = 160;
        OreSettings.GRAVEL_MIN_HEIGHT = 0;    OreSettings.GRAVEL_MAX_HEIGHT = 160;
        OreSettings.GRANITE_MIN_HEIGHT = 0;   OreSettings.GRANITE_MAX_HEIGHT = 60;
        OreSettings.DIORITE_MIN_HEIGHT = 0;   OreSettings.DIORITE_MAX_HEIGHT = 60;
        OreSettings.ANDESITE_MIN_HEIGHT = 0;  OreSettings.ANDESITE_MAX_HEIGHT = 60;
        OreSettings.TUFF_MIN_HEIGHT = -64;    OreSettings.TUFF_MAX_HEIGHT = 0;
        OreSettings.CALCITE_MIN_HEIGHT = -64; OreSettings.CALCITE_MAX_HEIGHT = 0;
        OreSettings.DEEPSLATE_MIN_HEIGHT = -64; OreSettings.DEEPSLATE_MAX_HEIGHT = -8;

        OreSettings.CLAY_VEIN_SIZE = 33;       OreSettings.CLAY_VEINS_PER_CHUNK = 46;
        OreSettings.CLAY_MIN_HEIGHT = 0;       OreSettings.CLAY_MAX_HEIGHT = 160;
        OreSettings.BLACKSTONE_VEIN_SIZE = 33; OreSettings.BLACKSTONE_VEINS_PER_CHUNK = 2;
        OreSettings.BLACKSTONE_MIN_HEIGHT = 5; OreSettings.BLACKSTONE_MAX_HEIGHT = 31;
        OreSettings.SOUL_SAND_VEIN_SIZE = 12;  OreSettings.SOUL_SAND_VEINS_PER_CHUNK = 12;
        OreSettings.SOUL_SAND_MIN_HEIGHT = 0;  OreSettings.SOUL_SAND_MAX_HEIGHT = 64;
        OreSettings.MAGMA_VEIN_SIZE = 33;      OreSettings.MAGMA_VEINS_PER_CHUNK = 4;
        OreSettings.MAGMA_MIN_HEIGHT = 27;     OreSettings.MAGMA_MAX_HEIGHT = 37;
        // Nether
        OreSettings.ANCIENT_DEBRIS_MIN_HEIGHT = 8;  OreSettings.ANCIENT_DEBRIS_MAX_HEIGHT = 24;
        OreSettings.NETHER_GOLD_MIN_HEIGHT = 10;    OreSettings.NETHER_GOLD_MAX_HEIGHT = 117;
        OreSettings.NETHER_QUARTZ_MIN_HEIGHT = 10;  OreSettings.NETHER_QUARTZ_MAX_HEIGHT = 117;
    }

    // ── Rendering ────────────────────────────────────────────────
    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderDirtBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);

        // Title
        graphics.drawCenteredString(this.font,
                "Customize World Settings", this.width / 2, 4, 0xFFFFFF);

        // Page indicator
        graphics.drawCenteredString(this.font,
                "Page " + (page + 1) + " of " + TOTAL_PAGES,
                this.width / 2, 14, 0xAAAAAA);

        // Page title
        graphics.drawCenteredString(this.font,
                PAGE_TITLES[page], this.width / 2, 24, 0xFFFFFF);

        // Column headers for ore pages
        if (page > 0) {
            int labelW   = 70;
            int listLeft = this.width / 2 - (this.width - 20) / 2 + 2;
            int totalSliderW = (this.width - 20) - labelW - 9; // 3 * spacing(3)
            int sw = totalSliderW / 4;
            int headerY = 37;
            graphics.drawString(this.font, "Vein Size",
                    listLeft + labelW, headerY, 0xAAAAAA);
            graphics.drawString(this.font, "Count",
                    listLeft + labelW + sw + 3, headerY, 0xAAAAAA);
            graphics.drawString(this.font, "Min Y",
                    listLeft + labelW + (sw + 3) * 2, headerY, 0xAAAAAA);
            graphics.drawString(this.font, "Max Y",
                    listLeft + labelW + (sw + 3) * 3, headerY, 0xAAAAAA);
        }
    }

    @Override
    public void onClose() {
        assert this.minecraft != null;
        this.minecraft.setScreen(parent);
    }
}