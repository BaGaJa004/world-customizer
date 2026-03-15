package net.bagaja.worldcustomizer.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.IntConsumer;

public class OreSliderList extends ContainerObjectSelectionList<OreSliderList.SliderRow> {

    // Constructor params: minecraft, width, height, y0, y1, itemHeight
    public OreSliderList(Minecraft mc, int width, int height, int y0, int y1) {
        super(mc, width, y1 - y0, y0, 24); // height = y1-y0, itemHeight = 24
        this.setRenderBackground(false);
    }

    @Override
    public int getRowWidth() {
        return this.width - 20; // leave room for scrollbar
    }

    @Override
    protected int getScrollbarPosition() {
        return this.getX() + this.width - 6;
    }

    public void addRow(String name,
                       IntConsumer onVeinSize, int vsMin, int vsMax, int vsInit,
                       IntConsumer onVeinsChunk, int vcMin, int vcMax, int vcInit) {
        addEntry(new SliderRow(name, this.getRowWidth(),
                onVeinSize, vsMin, vsMax, vsInit,
                onVeinsChunk, vcMin, vcMax, vcInit));
    }

    // ── Row Entry ────────────────────────────────────────────────────────────
    public static class SliderRow extends ContainerObjectSelectionList.Entry<SliderRow> {

        private final OreSlider veinSizeSlider;
        private final OreSlider veinsChunkSlider;
        private final String name;
        private final int rowWidth;

        public SliderRow(String name, int rowWidth,
                         IntConsumer onVeinSize, int vsMin, int vsMax, int vsInit,
                         IntConsumer onVeinsChunk, int vcMin, int vcMax, int vcInit) {
            this.name = name;
            this.rowWidth = rowWidth;

            int sliderWidth = (rowWidth - 80) / 2; // 80px for the label
            this.veinSizeSlider  = new OreSlider(0, 0, sliderWidth, 20,
                    "Vein Size", vsMin, vsMax, vsInit, onVeinSize);
            this.veinsChunkSlider = new OreSlider(0, 0, sliderWidth, 20,
                    "Count", vcMin, vcMax, vcInit, onVeinsChunk);
        }

        @Override
        public void render(GuiGraphics graphics, int index, int top, int left,
                           int width, int height, int mouseX, int mouseY,
                           boolean hovered, float partialTick) {
            int labelWidth = 76;
            int sliderWidth = (rowWidth - labelWidth) / 2;
            int gap = 4;

            // Draw ore name label, vertically centered in the row
            graphics.drawString(
                    Minecraft.getInstance().font,
                    name,
                    left,
                    top + (height - 9) / 2,  // 9 = font height
                    0xFFFFFF
            );

            // Position and render vein size slider
            veinSizeSlider.setX(left + labelWidth);
            veinSizeSlider.setY(top + (height - 20) / 2);
            veinSizeSlider.setWidth(sliderWidth - gap);
            veinSizeSlider.render(graphics, mouseX, mouseY, partialTick);

            // Position and render veins/chunk slider
            veinsChunkSlider.setX(left + labelWidth + sliderWidth + gap);
            veinsChunkSlider.setY(top + (height - 20) / 2);
            veinsChunkSlider.setWidth(sliderWidth - gap);
            veinsChunkSlider.render(graphics, mouseX, mouseY, partialTick);
        }

        @Override
        public List<? extends net.minecraft.client.gui.components.events.GuiEventListener> children() {
            return List.of(veinSizeSlider, veinsChunkSlider);
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of(veinSizeSlider, veinsChunkSlider);
        }
    }
}