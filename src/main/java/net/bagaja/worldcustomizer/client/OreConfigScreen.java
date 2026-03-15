package net.bagaja.worldcustomizer.client;

import net.bagaja.worldcustomizer.config.OreSettings;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class OreConfigScreen extends Screen {

    private final Screen parent;
    private OreSliderList sliderList;

    public OreConfigScreen(Screen parent) {
        super(Component.literal("Ore Generation Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        // List fills between title (30px) and done button (36px from bottom)
        sliderList = new OreSliderList(this.minecraft, this.width, this.height - 30 - 36, 30, this.height - 36);
        this.addRenderableWidget(sliderList);

        // ── Overworld Ores ───────────────────────────────────────────────
        sliderList.addRow("Coal",
                v -> OreSettings.COAL_VEIN_SIZE = v,      1,  64, OreSettings.COAL_VEIN_SIZE,
                v -> OreSettings.COAL_VEINS_PER_CHUNK = v, 0, 100, OreSettings.COAL_VEINS_PER_CHUNK);
        sliderList.addRow("Iron",
                v -> OreSettings.IRON_VEIN_SIZE = v,      1,  64, OreSettings.IRON_VEIN_SIZE,
                v -> OreSettings.IRON_VEINS_PER_CHUNK = v, 0, 100, OreSettings.IRON_VEINS_PER_CHUNK);
        sliderList.addRow("Gold",
                v -> OreSettings.GOLD_VEIN_SIZE = v,      1,  64, OreSettings.GOLD_VEIN_SIZE,
                v -> OreSettings.GOLD_VEINS_PER_CHUNK = v, 0, 100, OreSettings.GOLD_VEINS_PER_CHUNK);
        sliderList.addRow("Diamond",
                v -> OreSettings.DIAMOND_VEIN_SIZE = v,   1,  64, OreSettings.DIAMOND_VEIN_SIZE,
                v -> OreSettings.DIAMOND_VEINS_PER_CHUNK = v, 0, 100, OreSettings.DIAMOND_VEINS_PER_CHUNK);
        sliderList.addRow("Redstone",
                v -> OreSettings.REDSTONE_VEIN_SIZE = v,  1,  64, OreSettings.REDSTONE_VEIN_SIZE,
                v -> OreSettings.REDSTONE_VEINS_PER_CHUNK = v, 0, 100, OreSettings.REDSTONE_VEINS_PER_CHUNK);
        sliderList.addRow("Lapis",
                v -> OreSettings.LAPIS_VEIN_SIZE = v,     1,  64, OreSettings.LAPIS_VEIN_SIZE,
                v -> OreSettings.LAPIS_VEINS_PER_CHUNK = v, 0, 100, OreSettings.LAPIS_VEINS_PER_CHUNK);
        sliderList.addRow("Copper",
                v -> OreSettings.COPPER_VEIN_SIZE = v,    1,  64, OreSettings.COPPER_VEIN_SIZE,
                v -> OreSettings.COPPER_VEINS_PER_CHUNK = v, 0, 100, OreSettings.COPPER_VEINS_PER_CHUNK);
        sliderList.addRow("Emerald",
                v -> OreSettings.EMERALD_VEIN_SIZE = v,   1,  64, OreSettings.EMERALD_VEIN_SIZE,
                v -> OreSettings.EMERALD_VEINS_PER_CHUNK = v, 0, 100, OreSettings.EMERALD_VEINS_PER_CHUNK);

        // ── Stone Variants ───────────────────────────────────────────────
        sliderList.addRow("Dirt",
                v -> OreSettings.DIRT_VEIN_SIZE = v,      1, 64, OreSettings.DIRT_VEIN_SIZE,
                v -> OreSettings.DIRT_VEINS_PER_CHUNK = v, 0, 20, OreSettings.DIRT_VEINS_PER_CHUNK);
        sliderList.addRow("Gravel",
                v -> OreSettings.GRAVEL_VEIN_SIZE = v,    1, 64, OreSettings.GRAVEL_VEIN_SIZE,
                v -> OreSettings.GRAVEL_VEINS_PER_CHUNK = v, 0, 20, OreSettings.GRAVEL_VEINS_PER_CHUNK);
        sliderList.addRow("Granite",
                v -> OreSettings.GRANITE_VEIN_SIZE = v,   1, 64, OreSettings.GRANITE_VEIN_SIZE,
                v -> OreSettings.GRANITE_VEINS_PER_CHUNK = v, 0, 20, OreSettings.GRANITE_VEINS_PER_CHUNK);
        sliderList.addRow("Diorite",
                v -> OreSettings.DIORITE_VEIN_SIZE = v,   1, 64, OreSettings.DIORITE_VEIN_SIZE,
                v -> OreSettings.DIORITE_VEINS_PER_CHUNK = v, 0, 20, OreSettings.DIORITE_VEINS_PER_CHUNK);
        sliderList.addRow("Andesite",
                v -> OreSettings.ANDESITE_VEIN_SIZE = v,  1, 64, OreSettings.ANDESITE_VEIN_SIZE,
                v -> OreSettings.ANDESITE_VEINS_PER_CHUNK = v, 0, 20, OreSettings.ANDESITE_VEINS_PER_CHUNK);
        sliderList.addRow("Tuff",
                v -> OreSettings.TUFF_VEIN_SIZE = v,      1, 64, OreSettings.TUFF_VEIN_SIZE,
                v -> OreSettings.TUFF_VEINS_PER_CHUNK = v, 0, 20, OreSettings.TUFF_VEINS_PER_CHUNK);
        sliderList.addRow("Calcite",
                v -> OreSettings.CALCITE_VEIN_SIZE = v,   1, 64, OreSettings.CALCITE_VEIN_SIZE,
                v -> OreSettings.CALCITE_VEINS_PER_CHUNK = v, 0, 20, OreSettings.CALCITE_VEINS_PER_CHUNK);
        sliderList.addRow("Deepslate",
                v -> OreSettings.DEEPSLATE_VEIN_SIZE = v, 1, 64, OreSettings.DEEPSLATE_VEIN_SIZE,
                v -> OreSettings.DEEPSLATE_VEINS_PER_CHUNK = v, 0, 20, OreSettings.DEEPSLATE_VEINS_PER_CHUNK);

        // ── Nether ───────────────────────────────────────────────────────
        sliderList.addRow("Anc. Debris",
                v -> OreSettings.ANCIENT_DEBRIS_VEIN_SIZE = v,     1, 16, OreSettings.ANCIENT_DEBRIS_VEIN_SIZE,
                v -> OreSettings.ANCIENT_DEBRIS_VEINS_PER_CHUNK = v, 0, 20, OreSettings.ANCIENT_DEBRIS_VEINS_PER_CHUNK);
        sliderList.addRow("Nether Gold",
                v -> OreSettings.NETHER_GOLD_VEIN_SIZE = v,        1, 64, OreSettings.NETHER_GOLD_VEIN_SIZE,
                v -> OreSettings.NETHER_GOLD_VEINS_PER_CHUNK = v,  0, 40, OreSettings.NETHER_GOLD_VEINS_PER_CHUNK);
        sliderList.addRow("Nether Quartz",
                v -> OreSettings.NETHER_QUARTZ_VEIN_SIZE = v,      1, 64, OreSettings.NETHER_QUARTZ_VEIN_SIZE,
                v -> OreSettings.NETHER_QUARTZ_VEINS_PER_CHUNK = v, 0, 40, OreSettings.NETHER_QUARTZ_VEINS_PER_CHUNK);

        // ── Dungeons ─────────────────────────────────────────────────
        sliderList.addRow("Dungeons (surface)",
                v -> OreSettings.DUNGEON_COUNT = v,      0, 30, OreSettings.DUNGEON_COUNT,
                v -> OreSettings.DUNGEON_COUNT_DEEP = v, 0, 30, OreSettings.DUNGEON_COUNT_DEEP);

        // ── Fluid Settings ───────────────────────────────────────────
        int buttonY = this.height - 28 - 30; // just above Done button
        int buttonW = 180;
        int cx = this.width / 2;

        this.addRenderableWidget(
                CycleButton.<OreSettings.FluidChoice>builder(f -> switch (f) {
                            case WATER -> Component.literal("Overworld Fluid: Water");
                            case LAVA  -> Component.literal("Overworld Fluid: Lava");
                            case AIR   -> Component.literal("Overworld Fluid: Air (dry world)");
                        })
                        .withValues(OreSettings.FluidChoice.values())
                        .withInitialValue(OreSettings.OVERWORLD_FLUID)
                        .create(cx - buttonW - 5, buttonY, buttonW, 20,
                                Component.literal("Overworld Fluid"),
                                (btn, val) -> OreSettings.OVERWORLD_FLUID = val)
        );

        this.addRenderableWidget(
                CycleButton.<OreSettings.FluidChoice>builder(f -> switch (f) {
                            case WATER -> Component.literal("Nether Fluid: Water");
                            case LAVA  -> Component.literal("Nether Fluid: Lava");
                            case AIR   -> Component.literal("Nether Fluid: Air");
                        })
                        .withValues(OreSettings.FluidChoice.values())
                        .withInitialValue(OreSettings.NETHER_FLUID)
                        .create(cx + 5, buttonY, buttonW, 20,
                                Component.literal("Nether Fluid"),
                                (btn, val) -> OreSettings.NETHER_FLUID = val)
        );

        // Done button
        this.addRenderableWidget(Button.builder(Component.literal("Done"),
                btn -> {
                    assert this.minecraft != null;
                    this.minecraft.setScreen(parent);
                }
        ).pos(this.width / 2 - 75, this.height - 26).width(150).build());
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderDirtBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);

        // Title
        graphics.drawCenteredString(this.font, "Ore Generation Settings",
                this.width / 2, 10, 0xFFFFFF);

        // Column headers just above the list
        int labelWidth = 76;
        int sliderAreaWidth = sliderList.getRowWidth() - labelWidth;
        int sliderWidth = sliderAreaWidth / 2;
        int listLeft = sliderList.getRowLeft();

        graphics.drawString(this.font, "Vein Size",
                listLeft + labelWidth, 20, 0xAAAAAA);
        graphics.drawString(this.font, "Veins / Chunk",
                listLeft + labelWidth + sliderWidth + 4, 20, 0xAAAAAA);
    }

    @Override
    public void onClose() {
        assert this.minecraft != null;
        this.minecraft.setScreen(parent);
    }
}