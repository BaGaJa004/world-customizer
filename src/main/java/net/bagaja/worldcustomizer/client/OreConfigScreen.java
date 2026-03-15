package net.bagaja.worldcustomizer.client;

import net.bagaja.worldcustomizer.config.OreSettings;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

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
            case 1 -> initOreList(OVERWORLD_ORE_ROWS);
            case 2 -> initOreList(STONE_VARIANT_ROWS);
            case 3 -> initOreList(NETHER_ROWS);
        }
    }

    // ── Page 0: Basic Settings ───────────────────────────────────
    private void initBasicSettings() {
        int col1 = this.width / 2 - 205;
        int col2 = this.width / 2 + 5;
        int bw   = 200;
        int bh   = 20;
        int startY = 36;
        int gap  = 28;
        int row  = 0;

        // Overworld Fluid
        this.addRenderableWidget(
                CycleButton.<OreSettings.FluidChoice>builder(f -> Component.literal(switch (f) {
                            case WATER -> "Water";
                            case LAVA  -> "Lava";
                            case AIR   -> "Air (dry)";
                        }))
                        .withValues(OreSettings.FluidChoice.values())
                        .withInitialValue(OreSettings.OVERWORLD_FLUID)
                        .create(col1, startY, bw, bh,
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
                        .create(col2, startY, bw, bh,
                                Component.literal("Nether Fluid"),
                                (btn, val) -> OreSettings.NETHER_FLUID = val));
        row++;

        // Dungeon count sliders (surface)
        this.addRenderableWidget(new OreSlider(col1, startY + gap * row, bw, bh,
                "Dungeons (surface)", 0, 30, OreSettings.DUNGEON_COUNT,
                v -> OreSettings.DUNGEON_COUNT = v));

        // Dungeon count sliders (deep)
        this.addRenderableWidget(new OreSlider(col2, startY + gap * row, bw, bh,
                "Dungeons (deep)", 0, 30, OreSettings.DUNGEON_COUNT_DEEP,
                v -> OreSettings.DUNGEON_COUNT_DEEP = v));
        row++;
    }

    // ── Pages 1-3: Ore Slider Lists ──────────────────────────────
    private record OreRow(String name,
                          java.util.function.IntConsumer onVeinSize, int vsMin, int vsMax, int vsInit,
                          java.util.function.IntConsumer onCount,    int vcMin, int vcMax, int vcInit) {}

    private void initOreList(OreRow[] rows) {
        int topY    = 36;
        int bottomY = this.height - 36;
        OreSliderList list = new OreSliderList(
                this.minecraft, this.width, bottomY - topY, topY, bottomY);
        for (OreRow r : rows) {
            list.addRow(r.name(),
                    r.onVeinSize(), r.vsMin(), r.vsMax(), r.vsInit(),
                    r.onCount(),   r.vcMin(), r.vcMax(), r.vcInit());
        }
        this.addRenderableWidget(list);
    }

    // ── Ore row definitions ──────────────────────────────────────

    private static final OreRow[] OVERWORLD_ORE_ROWS = {
            new OreRow("Coal",
                    v -> OreSettings.COAL_VEIN_SIZE = v,      1, 64, OreSettings.COAL_VEIN_SIZE,
                    v -> OreSettings.COAL_VEINS_PER_CHUNK = v, 0, 100, OreSettings.COAL_VEINS_PER_CHUNK),
            new OreRow("Iron",
                    v -> OreSettings.IRON_VEIN_SIZE = v,      1, 64, OreSettings.IRON_VEIN_SIZE,
                    v -> OreSettings.IRON_VEINS_PER_CHUNK = v, 0, 100, OreSettings.IRON_VEINS_PER_CHUNK),
            new OreRow("Gold",
                    v -> OreSettings.GOLD_VEIN_SIZE = v,      1, 64, OreSettings.GOLD_VEIN_SIZE,
                    v -> OreSettings.GOLD_VEINS_PER_CHUNK = v, 0, 100, OreSettings.GOLD_VEINS_PER_CHUNK),
            new OreRow("Diamond",
                    v -> OreSettings.DIAMOND_VEIN_SIZE = v,   1, 64, OreSettings.DIAMOND_VEIN_SIZE,
                    v -> OreSettings.DIAMOND_VEINS_PER_CHUNK = v, 0, 100, OreSettings.DIAMOND_VEINS_PER_CHUNK),
            new OreRow("Redstone",
                    v -> OreSettings.REDSTONE_VEIN_SIZE = v,  1, 64, OreSettings.REDSTONE_VEIN_SIZE,
                    v -> OreSettings.REDSTONE_VEINS_PER_CHUNK = v, 0, 100, OreSettings.REDSTONE_VEINS_PER_CHUNK),
            new OreRow("Lapis",
                    v -> OreSettings.LAPIS_VEIN_SIZE = v,     1, 64, OreSettings.LAPIS_VEIN_SIZE,
                    v -> OreSettings.LAPIS_VEINS_PER_CHUNK = v, 0, 100, OreSettings.LAPIS_VEINS_PER_CHUNK),
            new OreRow("Copper",
                    v -> OreSettings.COPPER_VEIN_SIZE = v,    1, 64, OreSettings.COPPER_VEIN_SIZE,
                    v -> OreSettings.COPPER_VEINS_PER_CHUNK = v, 0, 100, OreSettings.COPPER_VEINS_PER_CHUNK),
            new OreRow("Emerald",
                    v -> OreSettings.EMERALD_VEIN_SIZE = v,   1, 64, OreSettings.EMERALD_VEIN_SIZE,
                    v -> OreSettings.EMERALD_VEINS_PER_CHUNK = v, 0, 100, OreSettings.EMERALD_VEINS_PER_CHUNK),
    };

    private static final OreRow[] STONE_VARIANT_ROWS = {
            new OreRow("Dirt",
                    v -> OreSettings.DIRT_VEIN_SIZE = v,      1, 64, OreSettings.DIRT_VEIN_SIZE,
                    v -> OreSettings.DIRT_VEINS_PER_CHUNK = v, 0, 20, OreSettings.DIRT_VEINS_PER_CHUNK),
            new OreRow("Gravel",
                    v -> OreSettings.GRAVEL_VEIN_SIZE = v,    1, 64, OreSettings.GRAVEL_VEIN_SIZE,
                    v -> OreSettings.GRAVEL_VEINS_PER_CHUNK = v, 0, 20, OreSettings.GRAVEL_VEINS_PER_CHUNK),
            new OreRow("Granite",
                    v -> OreSettings.GRANITE_VEIN_SIZE = v,   1, 64, OreSettings.GRANITE_VEIN_SIZE,
                    v -> OreSettings.GRANITE_VEINS_PER_CHUNK = v, 0, 20, OreSettings.GRANITE_VEINS_PER_CHUNK),
            new OreRow("Diorite",
                    v -> OreSettings.DIORITE_VEIN_SIZE = v,   1, 64, OreSettings.DIORITE_VEIN_SIZE,
                    v -> OreSettings.DIORITE_VEINS_PER_CHUNK = v, 0, 20, OreSettings.DIORITE_VEINS_PER_CHUNK),
            new OreRow("Andesite",
                    v -> OreSettings.ANDESITE_VEIN_SIZE = v,  1, 64, OreSettings.ANDESITE_VEIN_SIZE,
                    v -> OreSettings.ANDESITE_VEINS_PER_CHUNK = v, 0, 20, OreSettings.ANDESITE_VEINS_PER_CHUNK),
            new OreRow("Tuff",
                    v -> OreSettings.TUFF_VEIN_SIZE = v,      1, 64, OreSettings.TUFF_VEIN_SIZE,
                    v -> OreSettings.TUFF_VEINS_PER_CHUNK = v, 0, 20, OreSettings.TUFF_VEINS_PER_CHUNK),
            new OreRow("Calcite",
                    v -> OreSettings.CALCITE_VEIN_SIZE = v,   1, 64, OreSettings.CALCITE_VEIN_SIZE,
                    v -> OreSettings.CALCITE_VEINS_PER_CHUNK = v, 0, 20, OreSettings.CALCITE_VEINS_PER_CHUNK),
            new OreRow("Deepslate",
                    v -> OreSettings.DEEPSLATE_VEIN_SIZE = v, 1, 64, OreSettings.DEEPSLATE_VEIN_SIZE,
                    v -> OreSettings.DEEPSLATE_VEINS_PER_CHUNK = v, 0, 20, OreSettings.DEEPSLATE_VEINS_PER_CHUNK),
    };

    private static final OreRow[] NETHER_ROWS = {
            new OreRow("Anc. Debris",
                    v -> OreSettings.ANCIENT_DEBRIS_VEIN_SIZE = v,      1, 16, OreSettings.ANCIENT_DEBRIS_VEIN_SIZE,
                    v -> OreSettings.ANCIENT_DEBRIS_VEINS_PER_CHUNK = v, 0, 20, OreSettings.ANCIENT_DEBRIS_VEINS_PER_CHUNK),
            new OreRow("Nether Gold",
                    v -> OreSettings.NETHER_GOLD_VEIN_SIZE = v,         1, 64, OreSettings.NETHER_GOLD_VEIN_SIZE,
                    v -> OreSettings.NETHER_GOLD_VEINS_PER_CHUNK = v,   0, 40, OreSettings.NETHER_GOLD_VEINS_PER_CHUNK),
            new OreRow("Nether Quartz",
                    v -> OreSettings.NETHER_QUARTZ_VEIN_SIZE = v,       1, 64, OreSettings.NETHER_QUARTZ_VEIN_SIZE,
                    v -> OreSettings.NETHER_QUARTZ_VEINS_PER_CHUNK = v, 0, 40, OreSettings.NETHER_QUARTZ_VEINS_PER_CHUNK),
    };

    // ── Defaults reset ───────────────────────────────────────────
    private void resetDefaults() {
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
        OreSettings.OVERWORLD_FLUID = OreSettings.FluidChoice.WATER;
        OreSettings.NETHER_FLUID    = OreSettings.FluidChoice.LAVA;
    }

    // ── Rendering ────────────────────────────────────────────────
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
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
            int labelWidth = 76;
            int listLeft   = this.width / 2 - (this.width - 20) / 2 + 2;
            int sliderArea = (this.width - 20) - labelWidth;
            int sliderW    = sliderArea / 2;
            graphics.drawString(this.font, "Vein Size",
                    listLeft + labelWidth, 36, 0xAAAAAA);
            graphics.drawString(this.font, "Veins / Chunk",
                    listLeft + labelWidth + sliderW + 4, 36, 0xAAAAAA);
        }
    }

    @Override
    public void onClose() {
        assert this.minecraft != null;
        this.minecraft.setScreen(parent);
    }
}