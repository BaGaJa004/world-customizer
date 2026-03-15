package net.bagaja.worldcustomizer.client;

import net.bagaja.worldcustomizer.config.OreSettings;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class OreConfigScreen extends Screen {

    private final Screen parent;

    public OreConfigScreen(Screen parent) {
        super(Component.literal("Ore Generation Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = 35;
        int sliderWidth = 200;
        int sliderHeight = 20;
        int gap = 26;
        int col1 = centerX - 210;
        int col2 = centerX + 10;

        addOreRow("Coal",     col1, col2, startY + gap * 0, sliderWidth, sliderHeight,
                v -> OreSettings.COAL_VEIN_SIZE = v,     1,  64, OreSettings.COAL_VEIN_SIZE,
                v -> OreSettings.COAL_VEINS_PER_CHUNK = v, 0, 100, OreSettings.COAL_VEINS_PER_CHUNK);

        addOreRow("Iron",     col1, col2, startY + gap * 1, sliderWidth, sliderHeight,
                v -> OreSettings.IRON_VEIN_SIZE = v,     1,  64, OreSettings.IRON_VEIN_SIZE,
                v -> OreSettings.IRON_VEINS_PER_CHUNK = v, 0, 100, OreSettings.IRON_VEINS_PER_CHUNK);

        addOreRow("Gold",     col1, col2, startY + gap * 2, sliderWidth, sliderHeight,
                v -> OreSettings.GOLD_VEIN_SIZE = v,     1,  64, OreSettings.GOLD_VEIN_SIZE,
                v -> OreSettings.GOLD_VEINS_PER_CHUNK = v, 0, 100, OreSettings.GOLD_VEINS_PER_CHUNK);

        addOreRow("Diamond",  col1, col2, startY + gap * 3, sliderWidth, sliderHeight,
                v -> OreSettings.DIAMOND_VEIN_SIZE = v,  1,  64, OreSettings.DIAMOND_VEIN_SIZE,
                v -> OreSettings.DIAMOND_VEINS_PER_CHUNK = v, 0, 100, OreSettings.DIAMOND_VEINS_PER_CHUNK);

        addOreRow("Redstone", col1, col2, startY + gap * 4, sliderWidth, sliderHeight,
                v -> OreSettings.REDSTONE_VEIN_SIZE = v, 1,  64, OreSettings.REDSTONE_VEIN_SIZE,
                v -> OreSettings.REDSTONE_VEINS_PER_CHUNK = v, 0, 100, OreSettings.REDSTONE_VEINS_PER_CHUNK);

        addOreRow("Lapis",    col1, col2, startY + gap * 5, sliderWidth, sliderHeight,
                v -> OreSettings.LAPIS_VEIN_SIZE = v,    1,  64, OreSettings.LAPIS_VEIN_SIZE,
                v -> OreSettings.LAPIS_VEINS_PER_CHUNK = v, 0, 100, OreSettings.LAPIS_VEINS_PER_CHUNK);

        addOreRow("Copper",   col1, col2, startY + gap * 6, sliderWidth, sliderHeight,
                v -> OreSettings.COPPER_VEIN_SIZE = v,   1,  64, OreSettings.COPPER_VEIN_SIZE,
                v -> OreSettings.COPPER_VEINS_PER_CHUNK = v, 0, 100, OreSettings.COPPER_VEINS_PER_CHUNK);

        addOreRow("Emerald",  col1, col2, startY + gap * 7, sliderWidth, sliderHeight,
                v -> OreSettings.EMERALD_VEIN_SIZE = v,  1,  64, OreSettings.EMERALD_VEIN_SIZE,
                v -> OreSettings.EMERALD_VEINS_PER_CHUNK = v, 0, 100, OreSettings.EMERALD_VEINS_PER_CHUNK);

        // Spacer label
        // Stone Variants
        addOreRow("Dirt",     col1, col2, startY + gap * 9,  sliderWidth, sliderHeight,
                v -> OreSettings.DIRT_VEIN_SIZE = v,     1, 64, OreSettings.DIRT_VEIN_SIZE,
                v -> OreSettings.DIRT_VEINS_PER_CHUNK = v, 0, 20, OreSettings.DIRT_VEINS_PER_CHUNK);
        addOreRow("Gravel",   col1, col2, startY + gap * 10, sliderWidth, sliderHeight,
                v -> OreSettings.GRAVEL_VEIN_SIZE = v,   1, 64, OreSettings.GRAVEL_VEIN_SIZE,
                v -> OreSettings.GRAVEL_VEINS_PER_CHUNK = v, 0, 20, OreSettings.GRAVEL_VEINS_PER_CHUNK);
        addOreRow("Granite",  col1, col2, startY + gap * 11, sliderWidth, sliderHeight,
                v -> OreSettings.GRANITE_VEIN_SIZE = v,  1, 64, OreSettings.GRANITE_VEIN_SIZE,
                v -> OreSettings.GRANITE_VEINS_PER_CHUNK = v, 0, 20, OreSettings.GRANITE_VEINS_PER_CHUNK);
        addOreRow("Diorite",  col1, col2, startY + gap * 12, sliderWidth, sliderHeight,
                v -> OreSettings.DIORITE_VEIN_SIZE = v,  1, 64, OreSettings.DIORITE_VEIN_SIZE,
                v -> OreSettings.DIORITE_VEINS_PER_CHUNK = v, 0, 20, OreSettings.DIORITE_VEINS_PER_CHUNK);
        addOreRow("Andesite", col1, col2, startY + gap * 13, sliderWidth, sliderHeight,
                v -> OreSettings.ANDESITE_VEIN_SIZE = v, 1, 64, OreSettings.ANDESITE_VEIN_SIZE,
                v -> OreSettings.ANDESITE_VEINS_PER_CHUNK = v, 0, 20, OreSettings.ANDESITE_VEINS_PER_CHUNK);
        addOreRow("Tuff",     col1, col2, startY + gap * 14, sliderWidth, sliderHeight,
                v -> OreSettings.TUFF_VEIN_SIZE = v,     1, 64, OreSettings.TUFF_VEIN_SIZE,
                v -> OreSettings.TUFF_VEINS_PER_CHUNK = v, 0, 20, OreSettings.TUFF_VEINS_PER_CHUNK);
        addOreRow("Calcite",  col1, col2, startY + gap * 15, sliderWidth, sliderHeight,
                v -> OreSettings.CALCITE_VEIN_SIZE = v,  1, 64, OreSettings.CALCITE_VEIN_SIZE,
                v -> OreSettings.CALCITE_VEINS_PER_CHUNK = v, 0, 20, OreSettings.CALCITE_VEINS_PER_CHUNK);
        addOreRow("Deepslate",col1, col2, startY + gap * 16, sliderWidth, sliderHeight,
                v -> OreSettings.DEEPSLATE_VEIN_SIZE = v,1, 64, OreSettings.DEEPSLATE_VEIN_SIZE,
                v -> OreSettings.DEEPSLATE_VEINS_PER_CHUNK = v, 0, 20, OreSettings.DEEPSLATE_VEINS_PER_CHUNK);

        // Nether
        addOreRow("Anc. Debris", col1, col2, startY + gap * 17, sliderWidth, sliderHeight,
                v -> OreSettings.ANCIENT_DEBRIS_VEIN_SIZE = v, 1, 16, OreSettings.ANCIENT_DEBRIS_VEIN_SIZE,
                v -> OreSettings.ANCIENT_DEBRIS_VEINS_PER_CHUNK = v, 0, 20, OreSettings.ANCIENT_DEBRIS_VEINS_PER_CHUNK);
        addOreRow("Nether Gold", col1, col2, startY + gap * 18, sliderWidth, sliderHeight,
                v -> OreSettings.NETHER_GOLD_VEIN_SIZE = v, 1, 64, OreSettings.NETHER_GOLD_VEIN_SIZE,
                v -> OreSettings.NETHER_GOLD_VEINS_PER_CHUNK = v, 0, 40, OreSettings.NETHER_GOLD_VEINS_PER_CHUNK);
        addOreRow("Nether Quartz", col1, col2, startY + gap * 19, sliderWidth, sliderHeight,
                v -> OreSettings.NETHER_QUARTZ_VEIN_SIZE = v, 1, 64, OreSettings.NETHER_QUARTZ_VEIN_SIZE,
                v -> OreSettings.NETHER_QUARTZ_VEINS_PER_CHUNK = v, 0, 40, OreSettings.NETHER_QUARTZ_VEINS_PER_CHUNK);

        this.addRenderableWidget(Button.builder(Component.literal("Done"),
                btn -> this.minecraft.setScreen(parent)
        ).pos(centerX - 75, this.height - 28).width(150).build());
    }

    private void addOreRow(String name, int col1, int col2, int y, int w, int h,
                           java.util.function.IntConsumer onVeinSize, int vsMin, int vsMax, int vsInit,
                           java.util.function.IntConsumer onVeinsChunk, int vcMin, int vcMax, int vcInit) {
        this.addRenderableWidget(new OreSlider(col1, y, w, h,
                name + " Vein Size", vsMin, vsMax, vsInit, onVeinSize));
        this.addRenderableWidget(new OreSlider(col2, y, w, h,
                name + " Count", vcMin, vcMax, vcInit, onVeinsChunk));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderDirtBackground(graphics);
        graphics.drawCenteredString(this.font, "Ore Generation Settings", this.width / 2, 10, 0xFFFFFF);
        graphics.drawString(this.font, "Vein Size",       this.width / 2 - 210, 25, 0xAAAAAA);
        graphics.drawString(this.font, "Veins per Chunk", this.width / 2 + 10,  25, 0xAAAAAA);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(parent);
    }
}