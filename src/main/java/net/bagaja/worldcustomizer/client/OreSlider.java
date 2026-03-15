package net.bagaja.worldcustomizer.client;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class OreSlider extends AbstractSliderButton {

    private final String label;
    private final int minValue;
    private final int maxValue;
    private int currentValue;
    private final java.util.function.IntConsumer onChange;

    public OreSlider(int x, int y, int width, int height,
                     String label, int minValue, int maxValue, int initialValue,
                     java.util.function.IntConsumer onChange) {
        super(x, y, width, height,
                Component.literal(label + ": " + initialValue),
                // Convert initial value to 0.0-1.0 range
                (double)(initialValue - minValue) / (double)(maxValue - minValue)
        );
        this.label = label;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currentValue = initialValue;
        this.onChange = onChange;
    }

    @Override
    protected void updateMessage() {
        setMessage(Component.literal(label + ": " + currentValue));
    }

    @Override
    protected void applyValue() {
        currentValue = (int) Mth.lerp(value, minValue, maxValue);
        onChange.accept(currentValue);
    }

    public int getCurrentValue() {
        return currentValue;
    }
}