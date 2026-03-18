package net.bagaja.worldcustomizer.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.narration.NarratableEntry;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.IntConsumer;

public class OreSliderList extends ContainerObjectSelectionList<OreSliderList.@NotNull SliderRow> {

    public OreSliderList(Minecraft mc, int width, int height, int y0, int y1) {
        super(mc, width, y1 - y0, y0, 24);
    }

    @Override
    public int getRowWidth() {
        return this.width - 20;
    }

    public void addRow(String name,
                       IntConsumer onVeinSize, int vsMin, int vsMax, int vsInit,
                       IntConsumer onCount,    int vcMin, int vcMax, int vcInit,
                       IntConsumer onMinY,     int mnMin, int mnMax, int mnInit,
                       IntConsumer onMaxY,     int mxMin, int mxMax, int mxInit) {
        addEntry(new SliderRow(name, this.getRowWidth(),
                onVeinSize, vsMin, vsMax, vsInit,
                onCount,    vcMin, vcMax, vcInit,
                onMinY,     mnMin, mnMax, mnInit,
                onMaxY,     mxMin, mxMax, mxInit));
    }

    public static class SliderRow extends ContainerObjectSelectionList.Entry<@NotNull SliderRow> {

        private final String name;
        private final int rowWidth;
        private final OreSlider veinSizeSlider;
        private final OreSlider countSlider;
        private final OreSlider minYSlider;
        private final OreSlider maxYSlider;

        public SliderRow(String name, int rowWidth,
                         IntConsumer onVeinSize, int vsMin, int vsMax, int vsInit,
                         IntConsumer onCount,    int vcMin, int vcMax, int vcInit,
                         IntConsumer onMinY,     int mnMin, int mnMax, int mnInit,
                         IntConsumer onMaxY,     int mxMin, int mxMax, int mxInit) {
            this.name     = name;
            this.rowWidth = rowWidth;

            int labelW  = 70;
            int spacing = 3;
            int totalSliderW = rowWidth - labelW - spacing * 3;
            int sw = totalSliderW / 4;

            this.veinSizeSlider = new OreSlider(0, 0, sw, 20,
                    "Size", vsMin, vsMax, vsInit, onVeinSize);
            this.countSlider    = new OreSlider(0, 0, sw, 20,
                    "Count", vcMin, vcMax, vcInit, onCount);
            this.minYSlider     = new OreSlider(0, 0, sw, 20,
                    "Min Y", mnMin, mnMax, mnInit, onMinY);
            this.maxYSlider     = new OreSlider(0, 0, sw, 20,
                    "Max Y", mxMin, mxMax, mxInit, onMaxY);
        }

        @Override
        public void renderContent(GuiGraphics graphics, int mouseX, int mouseY,
                                  boolean hovered, float partialTick) {
            int labelW  = 70;
            int spacing = 3;
            int totalSliderW = rowWidth - labelW - spacing * 3;
            int sw = totalSliderW / 4;

            int left    = this.getX();
            int top     = this.getY();
            int height  = this.getHeight();
            int sliderY = top + (height - 20) / 2;

            // Label
            graphics.drawString(Minecraft.getInstance().font, name,
                    left, top + (height - 9) / 2, 0xFFFFFFFF);

            // Four sliders side by side
            veinSizeSlider.setX(left + labelW);
            veinSizeSlider.setY(sliderY);
            veinSizeSlider.setWidth(sw);
            veinSizeSlider.render(graphics, mouseX, mouseY, partialTick);

            countSlider.setX(left + labelW + sw + spacing);
            countSlider.setY(sliderY);
            countSlider.setWidth(sw);
            countSlider.render(graphics, mouseX, mouseY, partialTick);

            minYSlider.setX(left + labelW + (sw + spacing) * 2);
            minYSlider.setY(sliderY);
            minYSlider.setWidth(sw);
            minYSlider.render(graphics, mouseX, mouseY, partialTick);

            maxYSlider.setX(left + labelW + (sw + spacing) * 3);
            maxYSlider.setY(sliderY);
            maxYSlider.setWidth(sw);
            maxYSlider.render(graphics, mouseX, mouseY, partialTick);
        }

        @Override
        public @NotNull List<? extends net.minecraft.client.gui.components.events.GuiEventListener> children() {
            return List.of(veinSizeSlider, countSlider, minYSlider, maxYSlider);
        }

        @Override
        public @NotNull List<? extends NarratableEntry> narratables() {
            return List.of(veinSizeSlider, countSlider, minYSlider, maxYSlider);
        }
    }
}