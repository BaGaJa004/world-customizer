package net.bagaja.worldcustomizer.client;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ScreenEvent;

import java.util.Objects;

public class CreateWorldScreenHandler {

    public static void onScreenInit(ScreenEvent.Init.Post event) {
        if (!(event.getScreen() instanceof CreateWorldScreen screen)) return;

        Button oreButton = Button.builder(
                Component.literal("Ore Generation..."),
                btn -> Objects.requireNonNull(screen.getMinecraft()).setScreen(new OreConfigScreen(screen))
        ).pos(screen.width - 110, screen.height - 26).width(105).build();

        event.addListener(oreButton);
    }
}