package net.bagaja.worldcustomizer;

import net.bagaja.worldcustomizer.config.OreSettings;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.LevelEvent;

public class WorldEventHandler {

    public static void onLevelLoad(LevelEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (!level.dimension().equals(Level.OVERWORLD)) return;

        MinecraftServer server = level.getServer();
        if (server == null) return;

        OreSettings.load(server);
    }

    public static void onLevelSave(LevelEvent.Save event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (!level.dimension().equals(Level.OVERWORLD)) return;

        MinecraftServer server = level.getServer();
        if (server == null) return;

        OreSettings.save(server); // always save, no condition
    }
}